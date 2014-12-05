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
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockBaseWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoBaseWrapper;

/**
 * Job to publish the changes of Synonym Group to DAAS
 * 
 * @author Lakshmi.Valluripalli
 */
@Component
public class PromoJob extends BaseJob{
  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(PromoJob.class.getName());
  
	private final static String TYPE_PROMO = "Promos";
	private final static String TYPE_BOOSTBLOCK = "BoostsAndBlocks";
	private final static String CRON_JOB_NAME="promoCronJobTrigger";
	List<Promo> cfPromosPublished = new ArrayList<Promo>();
	List<Promo> afPromosPublished = new ArrayList<Promo>();
	List<BoostAndBlock> cfBoostBlockPublished = new ArrayList<BoostAndBlock>(); 
	List<BoostAndBlock> afBoostBlockPublished = new ArrayList<BoostAndBlock>(); 

	/**
	 * Method that loads the approved Promos and publish them to the DAAS
	 * 
	 * @param JobExecutionContext jobContext
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		try {
			populateBeans(jobContext);
			if ((!isJobExecuting(jobContext, PROMO_JOB)) || (!isJobExecuting(jobContext, PROMO_JOB_AF))) {
				if (isPromoDataChange(jobContext) > 0 || isBoostDataChange(jobContext) > 0) {
					List<Promo> promos = loadPromos(jobContext);
					List<BoostAndBlock> boostAndBlocks = loadBoostAndBlocks(jobContext);
					if ((promos != null  && promos.size() > 0) || (boostAndBlocks != null  && boostAndBlocks.size() > 0)) {
						List<PromoBaseWrapper> promoWrappers = preparePromos(promos);
						List<BoostAndBlockBaseWrapper> boostAndBlockWrappers = prepareBoostAndBlock(boostAndBlocks);
						if ((promoWrappers != null && promoWrappers.size() > 0)|| (boostAndBlockWrappers != null && boostAndBlockWrappers.size() > 0)) {
							if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
								publishData(MerchandisingConstants.JOB_INSERT_SQL, MerchandisingConstants.UPDATE_JOB_STATUS_Sql,
										promoWrappers,boostAndBlockWrappers,true);
							} else {
								publishData(MerchandisingConstants.AF_JOB_INSERT_SQL, MerchandisingConstants.AF_UPDATE_JOB_STATUS_Sql,
										promoWrappers,boostAndBlockWrappers,false);
							}
						}
					}
				}
				cfPromosPublished = null;
				cfBoostBlockPublished = null;
				afPromosPublished = null;
				afBoostBlockPublished = null;
			}
			
		} catch(Exception e) {
			cfPromosPublished = null;
			cfBoostBlockPublished = null;
			afPromosPublished = null;
			afBoostBlockPublished = null;
			log.error("PromoJob", e ,ErrorType.PUBLISHING, "Error while publishing promos and BoostAndBlocks");
		}
	}

	/**
	 * Method prepares Boost And Block Object as per the DAAS Contract,and also marks the assets 
	 * object that are in approved state to published which are later saved to DB.
	 * 
	 * @param List<BoostAndBlock> boostAndBlocks
	 * @return List<BoostAndBlockBaseWrapper>
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	public List<BoostAndBlockBaseWrapper> prepareBoostAndBlock(List<BoostAndBlock> boostAndBlocks) throws Exception {
		List<BoostAndBlockBaseWrapper> boostAndBlockBaseWrappers = new ArrayList<BoostAndBlockBaseWrapper>();
		List<Long> boostSkuIds = null;
		List<Long> blockSkuIds = null;
		for (BoostAndBlock boostAndBlock:boostAndBlocks) {
			boostSkuIds = new ArrayList<Long>();
			blockSkuIds = new ArrayList<Long>();
			for (BoostAndBlockProduct boostAndBlockProduct : boostAndBlock.getBoostAndBlockProducts()) {
				if (boostAndBlockProduct.getPosition() == -1) {
					blockSkuIds.add(boostAndBlockProduct.getSkuId());
				} else {
					boostSkuIds.add(boostAndBlockProduct.getSkuId());
				}
			}
			String categoryPath = boostAndBlock.getCategoryId().getCategoryPath().replace("/", "|").replace("\n", "");
			boostAndBlock.getCategoryId().setCategoryPath(categoryPath);
			BoostAndBlockBaseWrapper boostAndBlockBaseWrapper = new BoostAndBlockBaseWrapper(boostAndBlock.getTerm().toLowerCase(),
					getCategoryNodeService().getCategoryNodeId(boostAndBlock.getCategoryId().getCategoryPath()), boostAndBlock.getSearchProfileId().getProfileName(),
					boostSkuIds, blockSkuIds);
			boostAndBlockBaseWrappers.add(boostAndBlockBaseWrapper);
			if (boostAndBlock.getStatus().getStatusId() == AF_PUBLISH_STATUS) {
				cfBoostBlockPublished.add(boostAndBlock);
			} else if (boostAndBlock.getStatus().getStatusId() == APPROVE_STATUS) {
				afBoostBlockPublished.add(boostAndBlock);
			}
		} 
		return boostAndBlockBaseWrappers;
	}
	
	/**
	 * Method prepares Promo Object as per the DAAS Contract,and also marks the assets 
	 * object that are in approved state to published which are later saved to DB.
	 * 
	 * @param List<Promo> promos
	 * @return List<SynonymBaseWrapper>
	 * @throws ServiceException
	 */
	private List<PromoBaseWrapper> preparePromos(List<Promo> promos) throws ServiceException {
		List<PromoBaseWrapper> promoWrappers = new ArrayList<PromoBaseWrapper>();
		for (Promo promo:promos) {
			PromoBaseWrapper promoWrapper = new PromoBaseWrapper(promo.getPromoName(), promo.getPromoSku(), 
					promo.getStartDate(), promo.getEndDate());
			promoWrappers.add(promoWrapper);
			//set this asset status to published
			if (promo.getStatus().getStatusId() ==  AF_PUBLISH_STATUS) {
				cfPromosPublished.add(promo);
			} else if (promo.getStatus().getStatusId() ==  APPROVE_STATUS) {
				afPromosPublished.add(promo);
			}
		}
		return promoWrappers;
	}
	
	/**
	 * Method that publishes the Promos and BoostAndBlocks to Daas Service
	 * 
	 * @param uri
	 * @param promos
	 * @param boostAndBlocks
	 * @return String
	 */
  private String publish(String daasUri, List<PromoBaseWrapper> promos, List<BoostAndBlockBaseWrapper> boostAndBlocks) throws Exception {
    String response = "ERROR";

    List<DaasRequestWrapper> requestWrappers = new ArrayList<DaasRequestWrapper>();
    DaasRequestWrapper<PromoBaseWrapper> requestPromoWrapper = new DaasRequestWrapper<PromoBaseWrapper>(TYPE_PROMO, getSource(), promos);
    DaasRequestWrapper<BoostAndBlockBaseWrapper> requestBoostAndBlockWrapper = new DaasRequestWrapper<BoostAndBlockBaseWrapper>(TYPE_BOOSTBLOCK, getSource(), boostAndBlocks);
    requestWrappers.add(requestPromoWrapper);
    requestWrappers.add(requestBoostAndBlockWrapper);
    ObjectMapper mapper = new ObjectMapper();
    String promoJson = mapper.writeValueAsString(requestWrappers);
    String promosAndBoostsAndBlocksJson = "{\"promosandboostsblocks\":" + promoJson + "}";
    log.info("****  Pushing Promo data to DAAS *****" + promosAndBoostsAndBlocksJson);
    log.info("Publishing promo data to URI:.." + daasUri);
    ResponseEntity<String> rssResponse = getRestTemplate().exchange(daasUri, HttpMethod.POST, ResponseUtility.getRequestEntity(getRequestorId(), promosAndBoostsAndBlocksJson, "application/text"), String.class);
    response = rssResponse.getBody().toString();
    log.info("Response from Daas promos and BoostAndBlocks:.." + response);

    return response;
  }
	
	/**
	 * Method used to check weather there is any change inthe DATA.
	 * 
	 * @param JobExecutionContext jobContext
	 * @return Long
	 * @throws Exception 
	 */
	private Long isPromoDataChange(JobExecutionContext jobContext) throws Exception {
		Long count = 0l;
		if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
			SearchCriteria searchCriteria = criteriaByStartDateCF(TYPE_PROMO);
			count = getPromoDAO().getCount(searchCriteria);
			if (count <= 0) {
				//check for Deleted/Rejected/Invalid Status
				searchCriteria = criteriaByUpdateDateCF(TYPE_PROMO+"_"+TYPE_BOOSTBLOCK, 
						MerchandisingConstants.CF_JOB_SUCCESS_TIME_SQL);
				if (searchCriteria != null) {
					count = getPromoDAO().getCount(searchCriteria);
				}
			}
			log.info("CF: Number of new/updated promo records available for publishing "+count);
		} else {
			SearchCriteria searchCriteria = criteriaByUpdateDateAF(TYPE_PROMO+"_"+TYPE_BOOSTBLOCK,
					MerchandisingConstants.AF_JOB_SUCCESS_TIME_SQL);
			if (searchCriteria != null) {
				count = getPromoDAO().getCount(searchCriteria);
			}
			log.info("AF: Number of new/updated promo records available for publishing "+count);
		}
		return count;
	}
	
	/**
	 * Method used to check weather there is any change inthe DATA.
	 * 
	 * @param JobExecutionContext jobContext
	 * @return Long
	 * @throws Exception 
	 */
	private Long isBoostDataChange(JobExecutionContext jobContext) throws Exception {
		Long count = 0l;
		if (jobContext.getTrigger().getName().equals(CRON_JOB_NAME)) {
			SearchCriteria searchCriteria = criteriaByStartDateCF(TYPE_BOOSTBLOCK);
			count = getBoostAndBlockDao().getCount(searchCriteria);
			if (count <= 0) {
				//check for Deleted/Rejected/Invalid Status
				searchCriteria = criteriaByUpdateDateCF(TYPE_PROMO+"_"+TYPE_BOOSTBLOCK,MerchandisingConstants.CF_JOB_SUCCESS_TIME_SQL);
				if (searchCriteria != null) {
					count = getBoostAndBlockDao().getCount(searchCriteria);
				}
			}
			log.info("CF: number of new/updated boost&block records available for publishing "+count);
		} else {
			SearchCriteria searchCriteria = criteriaByUpdateDateAF(TYPE_PROMO+"_"+TYPE_BOOSTBLOCK,MerchandisingConstants.AF_JOB_SUCCESS_TIME_SQL);
			if (searchCriteria != null) {
				count = getBoostAndBlockDao().getCount(searchCriteria);
			}
			log.info("AF: number of new/updated boost&block records available for publishing "+count);
		}
		return count;
	}

	/**
	 * MEthod to load the Promos with status Approve/Publishing from the DB
	 * 
	 * @param JobExecutionContext jobContext
	 * @return List<Promo>
	 * @throws Exception 
	 * @throws ServiceException
	 */
	private List<Promo> loadPromos(JobExecutionContext jobContext) throws Exception {
		SearchCriteria searchCriteria = criteriaForLoad(TYPE_PROMO,CRON_JOB_NAME,jobContext);
		List<Promo> promos = (List<Promo>)getPromoDAO().executeQuery(searchCriteria);
		return promos;
	}
	
	/**
	 * MEthod to load the BoostAndBlocks with status Approve/Publishing from the DB
	 * 
	 * @param JobExecutionContext jobContext
	 * @return List<BoostAndBlock>
	 * @throws Exception
	 * @author chanchal.kumari
	 */
	private List<BoostAndBlock> loadBoostAndBlocks(JobExecutionContext jobContext) throws Exception{
		SearchCriteria searchCriteria = criteriaForLoad(TYPE_PROMO,null,jobContext);
		List<BoostAndBlock> boostAndBlocks = (List<BoostAndBlock>)getBoostAndBlockDao().executeQuery(searchCriteria);
		return boostAndBlocks;
	}
	
	/**
	 * Insert/Update the status in BATCH_JOB_EXECUTION table for CF and AF_BATCH_JOB_EXECUTION table for AF 
	 * 
	 * @param String insertJobSql
	 * @param String updateJobStatusSql
	 * @param List<PromoBaseWrapper> promoWrappers
	 * @param List<BoostAndBlockBaseWrapper> boostAndBlockWrappers
	 * @param boolean isCFJobRunning
	 * @throws Exception
	 */
	private void publishData(String insertJobSql, String updateJobStatusSql, List<PromoBaseWrapper> promoWrappers,
					List<BoostAndBlockBaseWrapper> boostAndBlockWrappers, boolean isCFJobRunning) throws Exception {
		Long jobId = 0L;
		jobId = insertJobStatus(TYPE_PROMO+"_"+TYPE_BOOSTBLOCK,insertJobSql);
		String response = publish(getDaasServiceUrl(),promoWrappers,boostAndBlockWrappers);
		if (response != null && response.toLowerCase().contains("success")) {
			updateJobStatus(jobId, "SUCCESS", response, updateJobStatusSql);
			if (isCFJobRunning) {
				log.info("changing the promo data to Published Status");
				for(Promo promo : cfPromosPublished){
					getPromoService().publishPromo(promo.getPromoId(), promo.getStatus().getStatus());
				}
				log.info("changing the boost and block data to Published Status");
				for (BoostAndBlock boostAndBlock : cfBoostBlockPublished) {
					getBoostAndBlockService().publishBoostBlock(boostAndBlock.getId());
				}
			} else {
				log.info("changing the promo data to AF Published Status");
				for (Promo promo : afPromosPublished) {
					getPromoService().publishPromo(promo.getPromoId(), promo.getStatus().getStatus());
				}
				log.info("changing the boost and block data to AF Published Status");
				for (BoostAndBlock boostAndBlock : afBoostBlockPublished) {
					getBoostAndBlockService().publishBoostBlock(boostAndBlock.getId());
				}
			}
		} else {
			updateJobStatus(jobId, "ERROR", response,updateJobStatusSql);
		}
	}
}