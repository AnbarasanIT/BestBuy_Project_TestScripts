package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.AF_PUBLISH_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectBaseWrapper;

/**
 * Job to publish the changes of Keyword redirect to DAAS
 * 
 * @author Lakshmi.Valluripalli
 */
public class KeywordRedirectJob extends BaseJob{
  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(KeywordRedirectJob.class.getName()); 
  
	private final static String TYPE = "Redirects";
	private final static String CRON_JOB_NAME="keywordRedirectCronJobTrigger";
	List<KeywordRedirect> cfKeywordsPublished = new ArrayList<KeywordRedirect>();
	List<KeywordRedirect> afKeywordsPublished = new ArrayList<KeywordRedirect>();

	/**
	 * Method to execute the job to publish keyword redirects
	 * 
	 * @param JobExecutionContext jobContext
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		try {
			populateBeans(jobContext);
			if ((!isJobExecuting(jobContext, KEYWORD_JOB)) || (!isJobExecuting(jobContext, KEYWORD_JOB_AF))) {
				Long count = isDataChange(jobContext);
				if (count > 0) {
					List<KeywordRedirect> redirects = loadRedirects(jobContext);
					List<KeywordRedirectBaseWrapper> redirectsWrapper = prepareRedirects(redirects);
					if (redirectsWrapper != null && redirectsWrapper.size() > 0) {
						if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
							publishData(MerchandisingConstants.JOB_INSERT_SQL, 
									MerchandisingConstants.UPDATE_JOB_STATUS_Sql, redirectsWrapper,true);
						} else {
							publishData(MerchandisingConstants.AF_JOB_INSERT_SQL,
									MerchandisingConstants.AF_UPDATE_JOB_STATUS_Sql, redirectsWrapper,false);
						}
					}
				}
				cfKeywordsPublished = null;
				afKeywordsPublished = null;
			}
		} catch(Exception se) {
			cfKeywordsPublished = null;
			afKeywordsPublished = null;
			log.error("KeywordRedirectJob", se ,ErrorType.PUBLISHING, "Error while publishing the redirects");
		}
	}

	/**
	 * Method prepares Redirects Object as per the DAAS Contract,and also marks the assets 
	 * object that are in approved state to published which are later saved to DB.
	 * 
	 * @param List<KeywordRedirect> keywordRedirects
	 * @return List<KeywordRedirectBaseWrapper>
	 * @throws ServiceException
	 */
	private List<KeywordRedirectBaseWrapper> prepareRedirects(List<KeywordRedirect> keywordRedirects) throws ServiceException {
		List<KeywordRedirectBaseWrapper> redirects = new ArrayList<KeywordRedirectBaseWrapper>();
		if (keywordRedirects != null && keywordRedirects.size() > 0) {
			for (KeywordRedirect keywordRedirect:keywordRedirects) {
				KeywordRedirectBaseWrapper redirect = new KeywordRedirectBaseWrapper(keywordRedirect.getKeyword(),
						keywordRedirect.getRedirectType(),
						keywordRedirect.getSearchProfile().getProfileName(),
						keywordRedirect.getRedirectString(),keywordRedirect.getStartDate(),keywordRedirect.getEndDate());
				redirects.add(redirect);
				//add the keyword to published list
				if (keywordRedirect.getStatus().getStatusId() == AF_PUBLISH_STATUS) {
					cfKeywordsPublished.add(keywordRedirect);
				} else if (keywordRedirect.getStatus().getStatusId() == APPROVE_STATUS) {
					afKeywordsPublished.add(keywordRedirect);
				}
			}
		}
		return redirects;
	}

	/**
	 * Method that publishes the redirects to DAAS Service
	 * @param uri
	 * @param redirects
	 * @return String
	 */
	private String publishRedirects(String daasUri, List<KeywordRedirectBaseWrapper> redirects) throws Exception{
		String response = "ERROR";
			log.info("preparing redirects Json Data .. "+redirects.toString());
			DaasRequestWrapper<KeywordRedirectBaseWrapper> requestWrapper = 
					new DaasRequestWrapper<KeywordRedirectBaseWrapper>(TYPE,getSource(),redirects);
			ObjectMapper mapper = new ObjectMapper();
			String redirectJson = mapper.writeValueAsString(requestWrapper);
			log.info("****  Pushing Redirects data to DAAS *****=>"+redirectJson);
			log.info("Publishing Redirects data to URI:.."+daasUri);
			ResponseEntity<String> rssResponse = getRestTemplate().exchange(daasUri,
					HttpMethod.POST,
					ResponseUtility.getRequestEntity(getRequestorId(), redirectJson, "application/text"), 
					String.class);
			response = rssResponse.getBody().toString();
			log.info("Response from Daas Redirects:.."+response);
		return response;
	}
	
	/**
	 * Method used to check whether there is any change in the DATA.
	 * @return count
	 * @throws Exception 
	 */
	public Long isDataChange(JobExecutionContext jobContext) throws Exception {
		Long count = 0l;
		if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
			SearchCriteria searchCriteria = criteriaByStartDateCF(TYPE);
			count = getKeywordRedirectService().getCount(searchCriteria);
			if (count <= 0) {
				//check for Deleted/Rejected/Invalid Status
				searchCriteria = criteriaByUpdateDateCF(TYPE,MerchandisingConstants.CF_JOB_SUCCESS_TIME_SQL);
				if (searchCriteria != null) {
					count = getKeywordRedirectService().getCount(searchCriteria);
				}
			}
			log.info("CF: Job Number of new/updated redirects records available for publishing "+count);
		} else {
			SearchCriteria searchCriteria = criteriaByUpdateDateAF(TYPE,MerchandisingConstants.AF_JOB_SUCCESS_TIME_SQL);
			if (searchCriteria != null) {
				count = getKeywordRedirectService().getCount(searchCriteria);
			}
			log.info("AF: Job Number of new/updated redirects records available for publishing "+count);
		}
		return count;
	}
	

	/**
	 * Get the list of redirects based on the criteria
	 * @param jobContext
	 * @param criteria
	 * @return List<KeywordRedirect>
	 * @throws Exception
	 */
	private List<KeywordRedirect> loadRedirects(JobExecutionContext jobContext) throws Exception{
		SearchCriteria searchCriteria = criteriaForLoad(TYPE,CRON_JOB_NAME,jobContext);
		List<KeywordRedirect> redirects = (List<KeywordRedirect>) getKeywordRedirectService().executeQuery(searchCriteria);
		return redirects;
	}

	/**
	 * Insert/Update the status in BATCH_JOB_EXECUTION table for CF and AF_BATCH_JOB_EXECUTION table for AF 
	 * @param insertJobSql
	 * @param updateJobStatusSql
	 * @param redirectsWrappers
	 * @throws Exception
	 */
	private void publishData(String insertJobSql, String updateJobStatusSql, List<KeywordRedirectBaseWrapper> redirectsWrappers,
			boolean isCFJobRunning) throws Exception {
		Long jobId = 0L;
		jobId = insertJobStatus(TYPE, insertJobSql);
		String response = publishRedirects(getDaasServiceUrl(),redirectsWrappers);
		//update all the keywords which are published.
		if (response != null && response.toLowerCase().contains("success")) {
			updateJobStatus(jobId, "SUCCESS", response, updateJobStatusSql);
			if (isCFJobRunning) {
				log.info("changing the redirect data to Published Status");
				for (KeywordRedirect keywordRedirect : cfKeywordsPublished) {
					getKeywordRedirectService().publishKeywordRedirect(keywordRedirect.getKeywordId());
				}
			} else {
				log.info("changing the redirect data to AF Published Status");
				for (KeywordRedirect keywordRedirect : afKeywordsPublished) {
					getKeywordRedirectService().publishKeywordRedirect(keywordRedirect.getKeywordId());
				}
			}
		} else {
			updateJobStatus(jobId, "ERROR", response,updateJobStatusSql);
		}
	}
}