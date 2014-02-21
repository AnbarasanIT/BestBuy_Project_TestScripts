package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.AF_PUBLISH_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.BANNER_TEMPLATE_HTML;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.BannerType;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.ContextFacet;
import com.bestbuy.search.merchandising.domain.ContextKeyword;
import com.bestbuy.search.merchandising.wrapper.BannerBaseWrapper;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextBaseWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextFacetBaseWrapper;

/**
 * Job to publish the changes of Banners to DAAS
 * 
 * @author Kalaiselvi Jaganathan
 */
public class BannerJob extends BaseJob {
  private final static BTLogger log = BTLogger.getBTLogger(BannerJob.class.getName());

  private final static String TYPE = "Banners";
  private final static String CRON_JOB_NAME = "bannerCronJobTrigger";
  List<Banner> cfBannerPublished = new ArrayList<Banner>();
  List<Banner> afBannerPublished = new ArrayList<Banner>();

  /**
   * Method that loads the approved banners and publish them to the DAAS
   * 
   * @param JobExecutionContext
   *          jobContext
   * @throws JobExecutionException
   */
  @Override
  public void execute(JobExecutionContext jobContext) throws JobExecutionException {
    try {
      populateBeans(jobContext);
      if ((!isJobExecuting(jobContext, BANNER_JOB)) || (!isJobExecuting(jobContext, BANNER_JOB_AF))) {
        Long count = isDataChange(jobContext);
        if (count > 0) {
          List<Banner> banners = loadBanners(jobContext);
          if (banners != null && banners.size() > 0) {
            List<BannerBaseWrapper> bannerWrapper = prepareBanners(banners);
            if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
              publishData(MerchandisingConstants.JOB_INSERT_SQL, MerchandisingConstants.UPDATE_JOB_STATUS_Sql, bannerWrapper, true);
            } else {
              publishData(MerchandisingConstants.AF_JOB_INSERT_SQL, MerchandisingConstants.AF_UPDATE_JOB_STATUS_Sql, bannerWrapper, false);
            }
          }
        }
        cfBannerPublished = null;
        afBannerPublished = null;
      }
    } catch (Exception e) {
      cfBannerPublished = null;
      afBannerPublished = null;
      log.error("BannerJob", e, ErrorType.PUBLISHING, "Error while publishing the Banners to DAAS");
    }
  }

  /**
   * Method used to check whether there is any change in the DATA.
   * 
   * @param JobExecutionContext
   *          jobContext
   * @return count
   * @throws Exception
   */
  public Long isDataChange(JobExecutionContext jobContext) throws Exception {
    Long count = 0l;
    if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
      SearchCriteria searchCriteria = criteriaByStartDateCF(TYPE);
      count = getBannerDAO().getCount(searchCriteria);
      if (count <= 0) {
        // check for Deleted/Rejected/Invalid Status
        searchCriteria = criteriaByUpdateDateCF(TYPE, MerchandisingConstants.CF_JOB_SUCCESS_TIME_SQL);
        if (searchCriteria != null) {
          count = getBannerDAO().getCount(searchCriteria);
        }
      }
      log.info("CF: Job Number of new/updated banner records available for publishing " + count);
    } else {
      SearchCriteria searchCriteria = criteriaByUpdateDateAF(TYPE, MerchandisingConstants.AF_JOB_SUCCESS_TIME_SQL);
      if (searchCriteria != null) {
        count = getBannerDAO().getCount(searchCriteria);
      }
      log.info("AF: Job Number of new/updated banner records available for publishing " + count);
    }
    return count;
  }

  /**
   * Method to load banners
   * 
   * @param JobExecutionContext
   *          jobContext
   * @return List<Banner>
   * @throws Exception
   */
  private List<Banner> loadBanners(JobExecutionContext jobContext) throws Exception {
    SearchCriteria searchCriteria = criteriaForLoad(TYPE, CRON_JOB_NAME, jobContext);
    List<Banner> banners = (List<Banner>) getBannerDAO().executeQuery(searchCriteria);
    return banners;
  }

  /**
   * Method prepares Banner Object as per the DAAS Contract,and also marks the assets
   * object that are in approved state to published which are later saved to DB.
   * 
   * @param List
   *          <Banner> banners
   * @return List<BannerBaseWrapper>
   * @throws ServiceException
   */
  private List<BannerBaseWrapper> prepareBanners(List<Banner> banners) throws ServiceException {
    List<BannerBaseWrapper> bannerWrappers = new ArrayList<BannerBaseWrapper>();
    for (Banner banner : banners) {
      BannerBaseWrapper bannerWrapper = new BannerBaseWrapper();
      bannerWrapper.setBannerName(banner.getBannerName());
      bannerWrapper.setStartDate(banner.getStartDate());
      bannerWrapper.setEndDate(banner.getEndDate());
      if (banner.getBannerType().equals("1")) {
        bannerWrapper.setBannerTypeName(BannerType.BROWSE_TOP.toString());
      } else {
        bannerWrapper.setBannerTypeName(BannerType.KEYWORD_TOP.toString());
      }
      bannerWrapper.setTemplateType(banner.getBannerTemplate());
      List<Context> contexts = banner.getContexts();
      bannerWrapper.setContext(getContextWrapper(contexts));
      if (banner.getBannerTemplates() != null) {
        if (banner.getBannerTemplate().equalsIgnoreCase(BANNER_TEMPLATE_HTML)) {
          if (banner.getBannerTemplates() != null && banner.getBannerTemplates().size() > 0 && banner.getBannerTemplates().get(0) != null) {
            BannerTemplate bannerTemplate = banner.getBannerTemplates().get(0);
            bannerWrapper.setDocumentId(Long.parseLong(bannerTemplate.getSku()));
          }
        } else {
          bannerWrapper.setBannerTemplates(BannerWrapper.getBannerTemplateWrapper(banner.getBannerTemplates(), banner.getBannerTemplate()));
        }
      }
      if (banner.getStatus().getStatusId() == AF_PUBLISH_STATUS) {
        cfBannerPublished.add(banner);
      } else if (banner.getStatus().getStatusId() == APPROVE_STATUS) {
        afBannerPublished.add(banner);
      }
      bannerWrappers.add(bannerWrapper);

    }
    return bannerWrappers;
  }

  /**
   * Method that publishes the banners to DAAS Service
   * 
   * @param uri
   * @param banners
   * @return String
   */
  private String publishBanners(String daasUri, List<BannerBaseWrapper> banners) throws Exception {
    String response = "ERROR";
    try {
      DaasRequestWrapper<BannerBaseWrapper> requestWrapper = new DaasRequestWrapper<BannerBaseWrapper>(TYPE, getSource(), banners);
      ObjectMapper mapper = new ObjectMapper();
      String bannerJson = mapper.writeValueAsString(requestWrapper);
      log.info("****  Pushing banner data to DAAS *****" + bannerJson);
      log.info("Publishing banner data to URI:.." + daasUri);
      ResponseEntity<String> rssResponse = getRestTemplate().exchange(daasUri, HttpMethod.POST, ResponseUtility.getRequestEntity(getRequestorId(), bannerJson, "application/text"), String.class);
      response = rssResponse.getBody().toString();
      log.info("Response from Daas Banners:.." + response);
    } catch (Exception e) {
      response = e.getLocalizedMessage();
      throw e;
    }
    return response;
  }

  /**
   * Get the context data
   * 
   * @param contexts
   * @return List of ContextBaseWrapper
   */
  private List<ContextBaseWrapper> getContextWrapper(List<Context> contexts) throws ServiceException {
    List<ContextBaseWrapper> contextWrappers = new ArrayList<ContextBaseWrapper>();
    if (contexts != null) {
      ContextBaseWrapper contextBaseWrapper = null;
      // Loop through the list of contexts for the banner and set the contextwrapper
      for (Context context : contexts) {
        contextBaseWrapper = new ContextBaseWrapper();
        if (context.getSearchProfile() != null) {
          contextBaseWrapper.setSearchProfileType(context.getSearchProfile().getProfileName());
        }
        if (context.getCategoryNode() != null) {
          contextBaseWrapper.setCategoryPath(getCategoryNodeService().getCategoryNodeId(context.getCategoryNode().getCategoryPath().replace("/", "|").replace("\n", "")));
        }
        if (context.getInheritable().equals(0L)) {
          contextBaseWrapper.setApplyToSubCategories("No");
        } else {
          contextBaseWrapper.setApplyToSubCategories("Yes");
        }

        List<ContextFacetBaseWrapper> facetWrappers = new ArrayList<ContextFacetBaseWrapper>();
        // Looping through ContextFacet list and retrieve the facet value also add comma between two facet value
        for (ContextFacet facet : context.getContextFacet()) {
          ContextFacetBaseWrapper facetWrapper = new ContextFacetBaseWrapper();
          if (facet.getContextFacetId() != null && facet.getContextFacetId().getFacet() != null && facet.getContextFacetId().getFacet().getAttribute() != null) {
            facetWrapper.setFacetName(facet.getContextFacetId().getFacet().getAttribute().getAttributeName());
          }
          if (facet.getContextFacetId() != null && facet.getContextFacetId().getAttributeValue() != null) {
            facetWrapper.setAttributeValueName(facet.getContextFacetId().getAttributeValue().getAttributeValue());
          }
          facetWrappers.add(facetWrapper);
        }
        contextBaseWrapper.setFacets(facetWrappers);
        StringBuffer keywordValues = new StringBuffer();

        // Looping through keyword list and retrieve the keyword also add comma between two keyword
        for (ContextKeyword keyword : context.getContextKeyword()) {
          if (keyword.getId() != null) {
            keywordValues.append(keyword.getId().getKeyword());
            keywordValues.append(", ");
          }
        }

        String keywordString = (keywordValues.toString()).trim(); // Remove the extra spaces in the string

        // Remove the trailing comma
        keywordString = keywordString.endsWith(",") ? keywordString.substring(0, keywordString.length() - 1) : keywordString;
        contextBaseWrapper.setKeywords(keywordString);
        contextWrappers.add(contextBaseWrapper);
      }
    }
    return contextWrappers;
  }

  /**
   * Insert/Update the status in BATCH_JOB_EXECUTION table for CF and AF_BATCH_JOB_EXECUTION table for AF
   * 
   * @param String
   *          insertJobSql
   * @param String
   *          updateJobStatusSql
   * @param List
   *          <BannerBaseWrapper> bannerWrapper
   * @param boolean isCFJobRunning
   * @throws Exception
   */
  private void publishData(String insertJobSql, String updateJobStatusSql, List<BannerBaseWrapper> bannerWrapper, boolean isCFJobRunning) throws Exception {
    Long jobId = 0L;
    jobId = insertJobStatus(TYPE, insertJobSql);
    String response = publishBanners(getDaasServiceUrl(), bannerWrapper);

    // update all the keywords which are published.
    if (response != null && response.toLowerCase().contains("success")) {
      updateJobStatus(jobId, "SUCCESS", response, updateJobStatusSql);
      if (isCFJobRunning) {
        log.info("changing the banner data to Published Status");
        for (Banner bannerPublish : cfBannerPublished) {
          getBannerService().publishBanner(bannerPublish.getBannerId());
        }
      } else {
        log.info("changing the banner data to AF Published Status");
        for (Banner bannerPublish : afBannerPublished) {
          getBannerService().publishBanner(bannerPublish.getBannerId());
        }
      }
    } else {
      updateJobStatus(jobId, "ERROR", response, updateJobStatusSql);
    }
  }
}
