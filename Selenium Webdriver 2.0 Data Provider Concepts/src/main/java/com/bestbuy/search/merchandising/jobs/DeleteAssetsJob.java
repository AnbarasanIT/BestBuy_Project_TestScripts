package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.service.CategoryNodeService;

/**
 * Job to Delete all the Assets with Status Deleted
 * Time Interval for this job is configured in the merc.job.properties
 * @author Lakshmi.Valluripalli
 */
@Component
public class DeleteAssetsJob extends BaseJob{
	
  private final static BTLogger log = BTLogger.getBTLogger(DeleteAssetsJob.class.getName()); 
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		try{ 
			 log.info("*** Starting Delete Assets Job ***");	

			 populateBeans(jobContext);
			 SearchCriteria criteria = new SearchCriteria();
			 Map<String,Object>	whereCriteria = criteria.getSearchTerms();
			 whereCriteria.put("obj.assetStatus.statusId",DELETE_STATUS);
			
			 log.info("*** Assets Deletion Completed Succesfully ***");
		}catch(Exception e){
			log.error("DeleteAssetsJob", e, ErrorType.PUBLISHING, "Error while retrieving the Assets from the database");
		}
	}
}
