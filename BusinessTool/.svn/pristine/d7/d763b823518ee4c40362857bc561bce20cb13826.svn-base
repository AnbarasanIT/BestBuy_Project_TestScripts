
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
import org.springframework.stereotype.Component;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymTerm;
import com.bestbuy.search.merchandising.wrapper.SynonymBaseWrapper;

/**
 * Job to publish the changes of Synonym Group to DAAS
 * @author Lakshmi.Valluripalli
 */
@Component
public class SynonymGroupJob extends BaseJob {
  private final static BTLogger log = BTLogger.getBTLogger(SynonymGroupJob.class.getName());
	private final static String TYPE = "Synonyms";
	private final static String CRON_JOB_NAME="synonymCronJobTrigger"; 
	List<Synonym> cfPublishedSynonym = new ArrayList<Synonym>();
	List<Synonym> afPublishedSynonym = new ArrayList<Synonym>();

	/**
	 * Method that loads the approved synonyms and publish them to the DAAS
	 * 
	 * @param JobExecutionContext jobContext
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		try {
			populateBeans(jobContext);
			if ((!isJobExecuting(jobContext, SYNONYM_JOB)) || (!isJobExecuting(jobContext, SYNONYM_JOB_AF))) {
				Long count = isDataChange(jobContext);
				if (count > 0) {
					List<Synonym> synonyms  = loadSynonyms(jobContext);
					if (synonyms != null && synonyms.size() > 0) {
						List<SynonymBaseWrapper> synonymWrappers = prepareSynonyms(synonyms);
						if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
							publishData(MerchandisingConstants.JOB_INSERT_SQL, 
									MerchandisingConstants.UPDATE_JOB_STATUS_Sql, synonymWrappers,true);
						} else {
							publishData(MerchandisingConstants.AF_JOB_INSERT_SQL, 
									MerchandisingConstants.AF_UPDATE_JOB_STATUS_Sql, synonymWrappers,false);
						}
					}
				}
				cfPublishedSynonym = null;
				afPublishedSynonym = null;
			}
		} catch(Exception e) {
			cfPublishedSynonym = null;
			afPublishedSynonym = null;
			log.error("SynonymGroupJob", e ,ErrorType.PUBLISHING,"Error while publishing the synonyms");
		}
	}


	/**
	 * Method prepares Synonyms Object as per the DAAS Contract,and also marks the assets 
	 * object that are in approved state to published which are later saved to DB.
	 * 
	 * @param assets
	 * @returnList<SynonymBaseWrapper>
	 */
	private List<SynonymBaseWrapper> prepareSynonyms(List<Synonym> synonyms) throws ServiceException {
		List<SynonymBaseWrapper> synonymWrappers = new ArrayList<SynonymBaseWrapper>();
		for (Synonym synonym : synonyms) {
			List<String> terms =  null;
			if (synonym.getSynonymGroupTerms() != null) {
				terms = new ArrayList<String>();
				for (SynonymTerm groupTerm : synonym.getSynonymGroupTerms()) {
					terms.add(groupTerm.getSynonymTerms().getSynTerm());
				}
				//This fix is added for acheiving the functionality of Fast with Solr(This is due to replacement in solr)
				/*Not for MVP
				 * if(synonym.getDirectionality() != null && 
						synonym.getDirectionality().equals(SYNONYM_TYPE_ONEWAY)){
					terms.add(synonym.getPrimaryTerm());
				}*/
				
			}
			SynonymBaseWrapper synonymWrapper = new SynonymBaseWrapper(synonym.getPrimaryTerm(),
					terms,synonym.getDirectionality(),synonym.getExactMatch(),
					synonym.getSynListId().getSynonymListType(),synonym.getStartDate(),synonym.getEndDate());
			synonymWrappers.add(synonymWrapper);
			if (synonym.getStatus().getStatusId() == AF_PUBLISH_STATUS) {
				cfPublishedSynonym.add(synonym);
			} else if (synonym.getStatus().getStatusId() == APPROVE_STATUS) {
				afPublishedSynonym.add(synonym);
			}

		}
		return synonymWrappers;
	}

	/**
	 * Method that publishes the synonyms to Daas Service
	 * @param uri
	 * @param synonyms
	 * @return String
	 */
	private String publishSynonyms(String daasUri,List<SynonymBaseWrapper> synonyms) throws Exception {
		String response = "ERROR";
		try {
			DaasRequestWrapper<SynonymBaseWrapper> requestWrapper = 
					new DaasRequestWrapper<SynonymBaseWrapper>(TYPE,getSource(),synonyms);
			ObjectMapper mapper = new ObjectMapper();
			String synonymJson = mapper.writeValueAsString(requestWrapper);
			log.info("****  Pushing synonym data to DAAS *****"+synonymJson);
			log.info("Publishing synonym data to URI:.."+daasUri);
			ResponseEntity<String> rssResponse = getRestTemplate().exchange(daasUri,
					HttpMethod.POST,
					ResponseUtility.getRequestEntity(getRequestorId(), synonymJson, "application/text"), 
					String.class);
			response = rssResponse.getBody().toString();
			log.info("Response from Daas Synonyms:.."+response);
		} catch(Exception e) {
			response = e.getLocalizedMessage();
			throw e;
		}
		return response;
	}

	
	/**
	 * Method used to check whether there is any change in the DATA.
	 * @return count
	 * @throws Exception 
	 */
	public Long isDataChange(JobExecutionContext jobContext) throws Exception{
		Long count = 0l;
		if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
			SearchCriteria searchCriteria = criteriaByStartDateCF(TYPE);
			count = getSynonymGroupDAO().getCount(searchCriteria);
			if (count <= 0) {
				//check for Deleted/Rejected/Invalid Status
				searchCriteria = criteriaByUpdateDateCF(TYPE,MerchandisingConstants.CF_JOB_SUCCESS_TIME_SQL);
				if (searchCriteria != null) {
					count = getSynonymGroupDAO().getCount(searchCriteria);
				}
			}
			log.info("CF Job Number of new/updated synonym records available for publishing "+count);
		} else {
			SearchCriteria searchCriteria = criteriaByUpdateDateAF(TYPE,MerchandisingConstants.AF_JOB_SUCCESS_TIME_SQL);
			if (searchCriteria != null) {
				count = getSynonymGroupDAO().getCount(searchCriteria);
			}
			log.info("AF Job Number of new/updated synonym records available for publishing "+count);
		}
		return count;
	}


	/**
	 * MEthod to load the Synonyms with status Approve/Publishing from the DB
	 * @return
	 * @throws Exception 
	 * @throws ServiceException
	 */
	private List<Synonym> loadSynonyms(JobExecutionContext jobContext) throws Exception{
		SearchCriteria searchCriteria = criteriaForLoad(TYPE, null, jobContext);
		List<Synonym> synonyms = (List<Synonym>)getSynonymGroupDAO().executeQuery(searchCriteria);
		return synonyms;
	}

	/**
	 * Insert/Update the status in BATCH_JOB_EXECUTION table for CF and AF_BATCH_JOB_EXECUTION table for AF 
	 * @param insertJobSql
	 * @param updateJobStatusSql
	 * @param redirectsWrappers
	 * @throws Exception
	 */
	private void publishData(String insertJobSql, String updateJobStatusSql, List<SynonymBaseWrapper> synonyms,boolean isCFJobRunning) throws Exception {
		Long jobId = 0L;
		//if new synonyms are available publish them to daas
		jobId = insertJobStatus(TYPE, insertJobSql);
		String response = publishSynonyms(getDaasServiceUrl(), synonyms);
		//update all the synonyms which are published.
		if (response != null && response.toLowerCase().contains("success")) {
			updateJobStatus(jobId, "SUCCESS", response, updateJobStatusSql);
			if (isCFJobRunning) {
				log.info("changing the synonym data to Published Status");
				for (Synonym synonym : cfPublishedSynonym) {
					getSynonymGroupService().publishSynonym(synonym.getId(),synonym.getStatus().getStatus());
				}
			} else {
				log.info("changing the synonym data to AF Published Status");
				for (Synonym synonym : afPublishedSynonym) {
					getSynonymGroupService().publishSynonym(synonym.getId(),synonym.getStatus().getStatus());
				}
			}
		} else {
			updateJobStatus(jobId, "ERROR", response,updateJobStatusSql);
		}
	}
}