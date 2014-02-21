package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.AF_PUBLISH_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetsDisplayOrder;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.wrapper.FacetBaseWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetPublishWrapper;

/**
 * Job to publish the changes of Facets to DAAS
 * 
 * @author Lakshmi.Valluripalli
 */
@Component
public class FacetJob extends BaseJob {
  private final static BTLogger log = BTLogger.getBTLogger(FacetJob.class.getName());
	private final static String TYPE = "Facets";
	List<Facet> cfFacetsPublished = new ArrayList<Facet>();
	List<Facet> afFacetsPublished = new ArrayList<Facet>();
	private final static String CRON_JOB_NAME="facetCronJobTrigger";
	

	/**
	 * Method Publishes the Approved Facets to Daas
	 * 
	 * @param JobExecutionContext jobContext
	 * @throws JobExecutionException 
	 */
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		try {
			populateBeans(jobContext);
			if (!isJobExecuting(jobContext, FACET_JOB) || (!isJobExecuting(jobContext, FACET_JOB_AF))) {
				Long count = isDataChange(jobContext);
				if (count > 0) { 
					List<FacetPublishWrapper> facetWrappers = prepareFacets(jobContext);
					if (facetWrappers != null && facetWrappers.size() > 0) {
						if(jobContext.getTrigger().getName().equals(CRON_JOB_NAME)){
							publishData(MerchandisingConstants.JOB_INSERT_SQL, 
									MerchandisingConstants.UPDATE_JOB_STATUS_Sql, facetWrappers,true);
						} else {
							publishData(MerchandisingConstants.AF_JOB_INSERT_SQL, 
									MerchandisingConstants.AF_UPDATE_JOB_STATUS_Sql, facetWrappers,false);
						}
					}  
				}
				cfFacetsPublished = null;
				afFacetsPublished = null;
			}
		} catch(Exception e) {
			cfFacetsPublished = null;
			afFacetsPublished = null;
			log.error("FacetJob", e ,ErrorType.PUBLISHING,"Error while publishing Facets");
		}
	}

	/**
	 * Method prepares facet Object as per the DAAS Contract,and also marks the facets 
	 * that are in approved state to published which are later saved to DB.
	 * 
	 * @param JobExecutionContext jobContext
	 * @return List<FacetBaseWrapper>
	 * @throws Exception
	 */
	private List<FacetPublishWrapper> prepareFacets(JobExecutionContext jobContext) throws Exception{
		Map<String, TreeMap<Long, FacetBaseWrapper>> orderedFacetCategoryMap = 
				new HashMap<String, TreeMap<Long, FacetBaseWrapper>>();
		List<FacetPublishWrapper> categoryFacetsWrapper = new ArrayList<FacetPublishWrapper>();
		List<Facet> facets = loadFacets(jobContext);
		Map<String, Category> categories = getAllCategories(facets);
		Integer count = 1;
		for (Facet facet:facets) {
			if (facet.getCategoryFacet() != null && facet.getCategoryFacet().size() > 0) {
				for (CategoryFacet categoryFacet:facet.getCategoryFacet()) {
					if (categoryFacet.getApplySubCategory().equals(DisplayEnum.Y)) {
						for (String key : categories.keySet()) {
							if (key.indexOf(categoryFacet.getCategoryNode().getCategoryPath()+"|") != -1) {
								count = addToFacetCategoryMap(categories.get(key), orderedFacetCategoryMap, categoryFacet, facet, count);
							}
						}
					} 
					count = addToFacetCategoryMap(categoryFacet.getCategoryNode(), orderedFacetCategoryMap, categoryFacet, facet, count);
				}
				if (facet.getStatus().getStatusId() == AF_PUBLISH_STATUS){
					cfFacetsPublished.add(facet);
				} else if (facet.getStatus().getStatusId() == APPROVE_STATUS) {
					afFacetsPublished.add(facet);
				}
			}
		}
		if (orderedFacetCategoryMap != null && orderedFacetCategoryMap.size() > 0) {
			for (Map.Entry<String, TreeMap<Long, FacetBaseWrapper>> entry : orderedFacetCategoryMap.entrySet()) {
				long displayOrder = 1L;
				FacetPublishWrapper facetPublishWrapper = new FacetPublishWrapper();
				facetPublishWrapper.setCategory(getCategoryNodeService().getCategoryNodeId(entry.getKey()));
				facetPublishWrapper.setFacets(new ArrayList<FacetBaseWrapper>());
				for (FacetBaseWrapper fbw : entry.getValue().values()) {
					fbw.setFacetDisplayOrder(displayOrder);
					facetPublishWrapper.getFacets().add(fbw);
					displayOrder++;
				}
				categoryFacetsWrapper.add(facetPublishWrapper);
			}
		}
		return categoryFacetsWrapper;
	}
	
	/**
	 * Method to add to facet category map
	 * 
	 * @param facetCategoryMap
	 * @param categoryFacet
	 * @param facet
	 */
	private Integer addToFacetCategoryMap(Category category, Map<String, TreeMap<Long, FacetBaseWrapper>> orderedFacetCategoryMap, 
			CategoryFacet categoryFacet, Facet facet, Integer count) {
		FacetBaseWrapper facetBaseWrapper = new FacetBaseWrapper(facet,categoryFacet);
		if (!orderedFacetCategoryMap.containsKey(category.getCategoryPath())) {
			orderedFacetCategoryMap.put(category.getCategoryPath(), 
					new TreeMap<Long, FacetBaseWrapper>());
		}
		if (category.getFacetDisplayOrder() != null && category.getFacetDisplayOrder().size() > 0) {
			boolean exists = false;
			for (FacetsDisplayOrder fdo : category.getFacetDisplayOrder()) {
				if (fdo.getFacet().getFacetId().longValue() == facet.getFacetId().longValue()) {
					orderedFacetCategoryMap.get(category.getCategoryPath()).
						put(fdo.getDisplayOrder(), facetBaseWrapper);
					exists = true;
					break;
				}
			}
			if (!exists) {
				if (orderedFacetCategoryMap.get(category.getCategoryPath()).containsKey(facet.getCreatedDate().getTime())) {
					orderedFacetCategoryMap.get(category.getCategoryPath()).
						put((facet.getCreatedDate().getTime() + count), facetBaseWrapper);
					count++;
				} else {
					orderedFacetCategoryMap.get(category.getCategoryPath()).
						put(facet.getCreatedDate().getTime(), facetBaseWrapper);
				}
			}
		} else {
			if (orderedFacetCategoryMap.get(category.getCategoryPath()).containsKey(facet.getCreatedDate().getTime())) {
				orderedFacetCategoryMap.get(category.getCategoryPath()).
					put((facet.getCreatedDate().getTime() + count), facetBaseWrapper);
				count++;
			} else {
				orderedFacetCategoryMap.get(category.getCategoryPath()).
					put(facet.getCreatedDate().getTime(), facetBaseWrapper);
			}
		}
		return count;
	}
	
	/**
	 * Method to get all category facets by path
	 * 
	 * @param facets
	 * @return Map<String, CategoryFacet>
	 */
	private Map<String, Category> getAllCategories(List<Facet> facets) {
		Map<String, Category> categoryFacets = null;
		if (facets != null && facets.size() > 0) {
			List<Long> statusIds = new ArrayList<Long>();
			String[] stat = getAssetStatus().split(",");
			if(stat.length > 0){
				for(int i = 0;i<stat.length;i++){
					statusIds.add(Long.parseLong(stat[i].trim()));
				}
			}
			for (Facet facet : facets) {
				if (statusIds.contains(facet.getStatus().getStatusId()) &&
						facet.getCategoryFacet() != null && facet.getCategoryFacet().size() > 0) {
					for (CategoryFacet categoryFacet : facet.getCategoryFacet()) {
						if (categoryFacets == null) {
							categoryFacets = new HashMap<String, Category>();
						}
						if (!categoryFacets.containsKey(categoryFacet.getCategoryNode().getCategoryPath())) {
							categoryFacets.put(categoryFacet.getCategoryNode().getCategoryPath(), categoryFacet.getCategoryNode());
						}
					}
				}
			}
		}
		return categoryFacets;
	}

	/**
	 * Method that publishes the Facets to DAAS Service
	 * @param uri
	 * @param Facets
	 * @return String
	 */
	private String publishFacets(String daasUri,List<FacetPublishWrapper> facets) throws Exception {
		String response = "ERROR";
		try {
			DaasRequestWrapper<FacetPublishWrapper> requestWrapper = 
					new DaasRequestWrapper<FacetPublishWrapper>(TYPE,getSource(),facets);
			ObjectMapper mapper = new ObjectMapper();
			String facetJson = mapper.writeValueAsString(requestWrapper);
			log.info("****  Pushing Facets data to DAAS *****"+facetJson);
			log.info("Publishing Facets data to URI:.."+daasUri);
			ResponseEntity<String> rssResponse = getRestTemplate().exchange(daasUri,
					HttpMethod.POST,
					ResponseUtility.getRequestEntity(getRequestorId(), facetJson, "application/text"), 
					String.class);
			response = rssResponse.getBody().toString();
			log.info("Response from Daas Facets:.."+response);
		} catch(Exception e) {
			log.error("FacetJob", e, ErrorType.PUBLISHING, "Error while publishing the Facets");
			throw e;
		}
		return response;
	}
	
	/**
	 * Method used to check whether there is any change in the DATA.
	 * 
	 * @param JobExecutionContext jobContext
	 * @return Long
	 * @throws Exception 
	 */
	public Long isDataChange(JobExecutionContext jobContext) throws Exception {
		Long count = 0l;
		if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
			SearchCriteria searchCriteria = criteriaByStartDateCF(TYPE);
			count = getFacetDAO().getCount(searchCriteria);
			if (count <= 0) {
				//check for Deleted/Rejected/Invalid Status
				searchCriteria = criteriaByUpdateDateCF(TYPE, MerchandisingConstants.CF_JOB_SUCCESS_TIME_SQL);
				if (searchCriteria != null) {
					count = getFacetDAO().getCount(searchCriteria);
				}
			}
			log.info("CF: Job Number of new/updated facet records available for publishing "+count);
		} else {
			SearchCriteria searchCriteria = criteriaByUpdateDateAF(TYPE, MerchandisingConstants.AF_JOB_SUCCESS_TIME_SQL);
			if (searchCriteria != null) {
				count = getFacetDAO().getCount(searchCriteria);
			}
			log.info("AF: Job Number of new/updated facet records available for publishing "+count);
		}
		return count;
	}

	/**
	 * Method to load the Facets (Approved/Published)
	 * @return
	 * @throws ServiceException
	 */
	private List<Facet> loadFacets(JobExecutionContext jobContext) throws Exception{
		SearchCriteria searchCriteria = criteriaForLoad(TYPE,CRON_JOB_NAME,jobContext);
		searchCriteria.setOrderByCriteria("obj.createdDate asc, upper(obj.systemName) asc");
		List<Facet> facets  =  (List<Facet>)getFacetDAO().executeQuery(searchCriteria);
		return facets;
	}

	/**
	 * Insert/Update the status in BATCH_JOB_EXECUTION table for CF and AF_BATCH_JOB_EXECUTION table for AF 
	 * @param insertJobSql
	 * @param updateJobStatusSql
	 * @param redirectsWrappers
	 * @throws Exception
	 */
	private void publishData(String insertJobSql, String updateJobStatusSql, List<FacetPublishWrapper> facetWrappers,
			boolean isCFJobRunning) throws Exception {
		Long jobId = 0L;
		jobId = insertJobStatus(TYPE, insertJobSql);
		String response = publishFacets(getDaasServiceUrl(), facetWrappers);
		//update all the facets which are published.
		if (response != null && response.toLowerCase().contains("success")) {
			updateJobStatus(jobId, "SUCCESS", response, updateJobStatusSql);
			if (isCFJobRunning) {
				for (Facet facet : cfFacetsPublished) {
					getFacetService().publishFacet(facet.getFacetId(),facet.getStatus().getStatus());
				}
			} else {
				for (Facet facet : afFacetsPublished) {
					getFacetService().publishFacet(facet.getFacetId(),facet.getStatus().getStatus());	
				}
			}
		} else {
			updateJobStatus(jobId, "ERROR", response,updateJobStatusSql);
		}
	}
	
}