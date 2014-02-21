package com.bestbuy.search.merchandising.jobs;
import static com.bestbuy.search.merchandising.common.ErrorType.PUBLISHING;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.EXPIRED_STATUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.domain.Status;

/**
 *Job to Move all the Assets that reached End date to Expired
 *Time Interval for this job is configured in the merc.job.properties
 * @author Lakshmi.Valluripalli
 */
@Component
public class ExpiredAssetsJob extends BaseJob{
	
  private final static BTLogger log = BTLogger.getBTLogger(ExpiredAssetsJob.class.getName());
  
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		try{ 
			 log.info("** Expired Job Started ****");
			 populateBeans(jobContext);
			 SearchCriteria criteria = new SearchCriteria();
			 Map<String,Object>	notInCriteria = criteria.getNotInCriteria();
			 List<Long> notInCriteriaList = new ArrayList<Long>();
			 notInCriteriaList.add(EXPIRED_STATUS);
			 notInCriteriaList.add(DELETE_STATUS);
			 notInCriteria.put("obj.status.statusId", notInCriteriaList);
			 Status status = getStatusService().retrieveById(EXPIRED_STATUS);
			 Map<String,Object>	lesserCriteria = criteria.getLesserCriteria();
			 lesserCriteria.put("obj.endDate",new Date());
			 updateRedirectStatus(criteria, status);
			 updatePromoStatus(criteria, status);
			 updateBannerStatus(criteria, status);
			 updateFacetStatus(criteria, status);
			 
			 log.info("** Expired Job Completed and trigger the jobs****");
		}catch(Exception se){
			log.error("ExpiredAssetsJob", se, PUBLISHING,"Error while Executind the Expired Job");
		}
	} 

	
	/**
	 * Updates the status of Promos that reaches the
	 * end date to expired in the DataBase 
	 * @param criteria
	 */
	private boolean updatePromoStatus(SearchCriteria criteria,Status status){
		boolean isPromoExp = false;
		try{
			//Move all the Expired Promos to Expired Status
			 List<Promo> promos  =  (List<Promo>)getPromoDAO().executeQuery(criteria);
			 if(promos != null && promos.size() > 0){
				 log.info("** Total Number of promos to be moved to Expired Status ****"+promos.size());
				 for(Promo promo:promos){
					 promo.setUpdatedDate(new Date());
					 promo.setStatus(status);
				 }
				 getPromoService().updatePromos(promos);
				 isPromoExp = true;
			 }
		}catch(Exception e){
			log.error("ExpiredAssetsJob", e ,PUBLISHING,"Error while retrieving the promos from the database");
		}
		return isPromoExp;
	}
	
	/**
	 * Updates the status of redirects that reaches the
	 * end date to expired in the DataBase 
	 * @param criteria
	 */
	private boolean updateRedirectStatus(SearchCriteria criteria,Status status){
		boolean isRedirectExp = false;
		try{
			 //Move all the Expired keywordRedirects to Expired Status
			 List<KeywordRedirect> redirects  =  (List<KeywordRedirect>)getKeywordRedirectService().executeQuery(criteria);
			 if(redirects != null && redirects.size() > 0){
				 log.info("** Total Number of redirects to be moved to Expired Status ****"+redirects.size());
				 for(KeywordRedirect redirect:redirects){
					 redirect.setUpdatedDate(new Date());
					 redirect.setStatus(status);
				 }
				 getKeywordRedirectService().updateRedirects(redirects);
				 isRedirectExp = true;
			 }
		}catch(Exception e){
			log.error("ExpiredAssetsJob", e ,PUBLISHING,"Error while retrieving the redirects from the database");
		}
		return isRedirectExp;
	}
	
	/**
	 * Updates the status of facets that reaches the
	 * end date to expired in the DataBase 
	 * @param criteria
	 */
	private boolean updateFacetStatus(SearchCriteria criteria,Status status){
		boolean isFacetExp = false;
		try{
			//Move all the Expired keywordRedirects to Expired Status
			 List<Facet> facets  =  (List<Facet>)getFacetDAO().executeQuery(criteria);
			 if(facets != null && facets.size() > 0){
				 log.info("** Total Number of facets to be moved to Expired Status ****"+facets.size());
				 for(Facet facet:facets){
					 facet.setStatus(status);
					 facet.setUpdatedDate(new Date());
				 }
				 getFacetService().updateFacets(facets);
				 isFacetExp = true;
			 }
		}catch(Exception e){
			log.error("ExpiredAssetsJob", e ,PUBLISHING,"Error while retrieving the facets from the database");
		}
		return isFacetExp;
	}
	
	/**
	 * Updates the status of banners that reaches the
	 * end date to expired in the DataBase  
	 * @param criteria
	 */
	private boolean updateBannerStatus(SearchCriteria criteria,Status status){
		boolean isBannerExp = false;
		try{
			//Move all the Expired keywordRedirects to Expired Status
			 List<Banner> banners  =  (List<Banner>)getBannerDAO().executeQuery(criteria);
			 if(banners != null && banners.size() > 0){
				 log.info("** Total Number of Banners to be moved to Expired Status ****"+banners.size());
				 for(Banner banner:banners){
					 banner.setStatus(status);
					 banner.setUpdatedDate(new Date());
				 }
				 getBannerService().updateBanners(banners);
				 isBannerExp = true;
			 }
			
		}catch(Exception e){
			log.error("ExpiredAssetsJob", e ,PUBLISHING,"Error while retrieving the Banners from the database");
		}
		return isBannerExp;
	}
}
