package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.AF_PUBLISH_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.EXPIRED_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.NOT_VALID_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.REJECT_STATUS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.dao.BannerDAO;
import com.bestbuy.search.merchandising.dao.BoostAndBlockDao;
import com.bestbuy.search.merchandising.dao.CategoryNodeDAO;
import com.bestbuy.search.merchandising.dao.FacetDAO;
import com.bestbuy.search.merchandising.dao.PromoDAO;
import com.bestbuy.search.merchandising.dao.SynonymGroupDAO;
import com.bestbuy.search.merchandising.service.BannerService;
import com.bestbuy.search.merchandising.service.BoostAndBlockService;
import com.bestbuy.search.merchandising.service.FacetService;
import com.bestbuy.search.merchandising.service.IAttributeService;
import com.bestbuy.search.merchandising.service.IAttributeValueService;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.IKeywordRedirectService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.service.ISynonymService;
import com.bestbuy.search.merchandising.service.PromoService;
import com.bestbuy.search.merchandising.service.SolrSearchService;

/**
 * Base Job class that reads the Parameters from the Job Context
 * @author Lakshmi.Valluripalli
 *
 */
public abstract class  BaseJob implements Job {
	private final static String APP_CTX_KEY = "applicationContext"; 
	private final static Logger logger = Logger.getLogger(BaseJob.class); 
	public final static String JOB_GROUP = "DEFAULT";
	public final static String SYNONYM_JOB = "synonymGroupJob";
	public final static String SYNONYM_JOB_AF = "synonymGroupJobAF";
	public final static String KEYWORD_JOB = "keywordRedirectJob";
	public final static String KEYWORD_JOB_AF = "keywordRedirectJobAF";
	public final static String PROMO_JOB = "promoJob";
	public final static String PROMO_JOB_AF = "promoJobAF";
	public final static String BANNER_JOB = "bannerJob";
	public final static String BANNER_JOB_AF = "bannerJobAF";
	public final static String FACET_JOB = "facetJob";
	public final static String FACET_JOB_AF = "facetJobAF";
	
	private IStatusService statusService;
	private ISynonymService synonymService;
	private PromoService promoService;
	private IKeywordRedirectService keywordRedirectService;
	private RestTemplate restTemplate;
	private String daasServiceUrl;
	private String source;
	private String  assetStatus;
	private JdbcTemplate jdbcTemplate;
	private BannerService bannerService;
	private EntityManager entityManager;
	private BannerDAO bannerDAO;
	private JpaTransactionManager transactionManager;
	private String status;
	private PromoDAO promoDAO;
	private FacetDAO facetDAO;
	private SynonymGroupDAO synonymGroupDAO;
	private FacetService facetService;
	private CategoryNodeDAO categoryNodeDAO;
	private SolrSearchService solrSearchService;
	private ICategoryNodeService categoryNodeService;
	//private IAttributeMetaService attributeMetaService;
	//private IAttributeValueMetaService attributeValueMetaService;
	private IAttributeService attributeService;
	private IAttributeValueService attributeValueService;
	private BoostAndBlockDao boostAndBlockDao;
	private BoostAndBlockService boostAndBlockService;
	private String requestorId;
	
	/**
	 * Method that populates the parameters from Application context and the Job data Map
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public void populateBeans(JobExecutionContext jobContext)
		    throws JobExecutionException {
		ApplicationContext context = getApplicationContext(jobContext);
		this.transactionManager=(JpaTransactionManager) context.getBean("transactionManager");     
		this.entityManager = transactionManager.getEntityManagerFactory().createEntityManager();
		this.statusService =  (IStatusService)context.getBean("statusService");
		this.synonymService = (ISynonymService)context.getBean("synonymService");
		this.categoryNodeService = (ICategoryNodeService)context.getBean("categoryNodeService");
		this.synonymGroupDAO = new SynonymGroupDAO();
		synonymGroupDAO.setEntityManager(entityManager);
		this.keywordRedirectService = (IKeywordRedirectService)context.getBean("keywordRedirectService");
		this.promoService = (PromoService)context.getBean("promoService");
		this.restTemplate =  (RestTemplate)context.getBean("restTemplate");
		this.jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
		this.daasServiceUrl = (String)getDataValue(jobContext,"daasServiceURI");
		this.source = (String)getDataValue(jobContext,"source");
		this.assetStatus = (String)getDataValue(jobContext,"status");
		this.bannerService =(BannerService) context.getBean("bannerService");
		this.bannerDAO =  new BannerDAO();
		this.bannerDAO.setEntityManager(entityManager);
		this.status=(String)getDataValue(jobContext,"status");
		this.promoDAO = new PromoDAO();
		this.promoDAO.setEntityManager(entityManager);
		this.facetService = (FacetService) context.getBean("facetService");
		this.facetDAO = new FacetDAO();
		this.facetDAO.setEntityManager(entityManager);
		this.categoryNodeDAO = new CategoryNodeDAO();
		this.categoryNodeDAO.setEntityManager(entityManager);
		this.solrSearchService = (SolrSearchService) context.getBean("solrSearchService");
		this.attributeService = (IAttributeService)context.getBean("attributeService");
		this.attributeValueService = (IAttributeValueService)context.getBean("attributeValueService");
		this.boostAndBlockDao = new BoostAndBlockDao();
		this.boostAndBlockDao.setEntityManager(entityManager);
		this.boostAndBlockService = (BoostAndBlockService)context.getBean("boostAndBlockService");
		this.requestorId = (String)getDataValue(jobContext,"requestorId");
	}
	
	
	/**
	 * Spring Application Context
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private ApplicationContext getApplicationContext(JobExecutionContext context)
		    throws JobExecutionException {
		ApplicationContext appCtx = null;
		try{
			 appCtx = (ApplicationContext)context.getScheduler().getContext().get(APP_CTX_KEY);
	         if (appCtx == null) {
	            throw new JobExecutionException(
	                    "No application context available in scheduler context for key \"" + APP_CTX_KEY + "\"");
	         }
		}catch(SchedulerException se){
			throw new JobExecutionException(
                    "Error while reading application Context from scheduler");
		}
        return appCtx;
	}
	
	/**
	 * get the Value for the Specific key from Job DataMap
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private Object getDataValue(JobExecutionContext jobContext,String key)
		    throws JobExecutionException {
        
       if(jobContext.getMergedJobDataMap().containsKey(key)){
        	return jobContext.getMergedJobDataMap().get(key);
        }else{
        	return "";
        }
	}
	
	/**
	 * Method to insert the new Job Execution Status to DB
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Long insertJobStatus(final String type, final String jobInsertSql) throws Exception{
		final Date date = new Date();
		Long rowIndex = null;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
		  public PreparedStatement createPreparedStatement(Connection connection)
		      throws SQLException {
		    PreparedStatement ps = connection.prepareStatement(jobInsertSql,new String[] {"JOB_EXECUTION_ID"});
		    ps.setString(1,type);
		    ps.setTimestamp(2,new java.sql.Timestamp(date.getTime()));
		    ps.setString(3,"POSTED");
		    return ps;
		  }
		}, keyHolder);
		if(keyHolder.getKey() != null){
			rowIndex =  keyHolder.getKey().longValue();
		}
		return rowIndex;
	}
	
	/**
	 * Method that updates the JobStatus to DB
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Date selectJobSuccessTime(final String type,final String startTimeSql) throws Exception{
		List results = getJdbcTemplate().queryForList(startTimeSql, type);
		Date startTime = null;
		for (Object result : results) {
            HashMap map = (HashMap) result;
            for (Object key : map.keySet()) {
                startTime = (Date) map.get(key);
            }
        }
		return startTime;
	}
	
	/**
	 * Method that updates the JobStatus to DB
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int updateJobStatus(final Long jobId,final String status,final String response, final String jobSql) throws Exception{
		int result = getJdbcTemplate().update(new PreparedStatementCreator() {
			  public PreparedStatement createPreparedStatement(Connection connection)
				      throws SQLException {
				    PreparedStatement ps = connection.prepareStatement(jobSql);
				    ps.setTimestamp(1,new java.sql.Timestamp(new Date().getTime()));
				    ps.setString(2,status);
				    ps.setString(3,response);
				    ps.setTimestamp(4,new java.sql.Timestamp(new Date().getTime()));
				    ps.setLong(5,jobId);
				    return ps;
				  }
				});
		return result;
	}
	
	
	/**
	 * Method to check the current Jobs Execution
	 * @param jobContext
	 * @param jobName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean isJobExecuting(JobExecutionContext jobContext,String jobName) throws Exception{
		boolean isExecuting = false;
		Scheduler scheduler = jobContext.getScheduler();
		if(scheduler != null && scheduler.getCurrentlyExecutingJobs() != null){
			List<JobExecutionContext> currentJobs = (List<JobExecutionContext>)scheduler.getCurrentlyExecutingJobs();
			if (currentJobs != null && currentJobs.size() > 0) {
				for (JobExecutionContext jobCtx:currentJobs) {
					if (jobCtx.getTrigger().equals(jobContext.getTrigger()) && !jobCtx.getJobInstance().equals(this)) {
			            logger.info("There's another instance running, so suspending the " + this);
			            return true;
			         }
				}
			}
		}

		logger.info(" >>>>>> Started executing Job "+jobContext.getTrigger().getFullJobName());
		return isExecuting;
	}
	
	/**
	 * Method to get criteria by update date for AF
	 * 
	 * @param type
	 * @param sql
	 * @return SearchCriteria
	 * @throws Exception
	 */
	public SearchCriteria criteriaByUpdateDateAF(String type, String sql) throws Exception {
		return this.criteriaByUpdateDate(type, sql, APPROVE_STATUS);
	}
	
	/**
	 * Method to get criteria by update date for CF
	 * 
	 * @param type
	 * @param sql
	 * @return SearchCriteria
	 * @throws Exception
	 */
	public SearchCriteria criteriaByUpdateDateCF(String type, String sql) throws Exception {
		return this.criteriaByUpdateDate(type, sql, AF_PUBLISH_STATUS);
	}
	
	/**
	 * criteria that loads all the data based on the time from Last job run
	 * @param type
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public SearchCriteria criteriaByUpdateDate(String type,String sql, Long status) throws Exception{
		SearchCriteria criteria = new SearchCriteria();
		Date updatedTime = selectJobSuccessTime(type, sql);
		if (updatedTime != null) {
			Map<String,Object>	greaterEqCriteria = criteria.getGreaterEqCriteria();
			greaterEqCriteria.put("obj.updatedDate",updatedTime);
		}
		List<Long> statusIds = new ArrayList<Long>();
		statusIds.add(DELETE_STATUS);//Deleted
		statusIds.add(REJECT_STATUS);//Rejected
		statusIds.add(NOT_VALID_STATUS);//Invalidated
		statusIds.add(EXPIRED_STATUS);//Expired
		statusIds.add(status);//Expired
		Map<String,Object>	inCriteria = criteria.getInCriteria();
		inCriteria.put("obj.status.statusId", statusIds);
		return criteria;
	}
	
	/**
	 * Method to get criteria by start date for CF
	 * 
	 * @param type
	 * @return SearchCriteria
	 * @throws Exception
	 */
	public SearchCriteria criteriaByStartDateCF(String type) throws Exception {
		return this.criteriaByStartDate(type, AF_PUBLISH_STATUS);
	}
	
	/**
	 * Method to get criteria by start date for AF
	 * 
	 * @param type
	 * @return SearchCriteria
	 * @throws Exception
	 */
	public SearchCriteria criteriaByStartDateAF(String type) throws Exception {
		return this.criteriaByStartDate(type, APPROVE_STATUS);
	}
	
	/**
	 * criteria that loads all the data based todays StartDate and Approved Status
	 * @param type
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public SearchCriteria criteriaByStartDate(String type, Long status) throws Exception{
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	whereCriteria = criteria.getSearchTerms();
		whereCriteria.put("obj.status.statusId", status);
		if(!type.equals("Synonyms") && !type.equals("BoostsAndBlocks")){
			Map<String,Object>	lesserEqCriteria = criteria.getLesserEqCriteria();
			lesserEqCriteria.put("obj.startDate",new Date());
		}
		return criteria;
	}
	
	/**
	 * criteria that loads all the data based todays StartDate and Approved Status
	 * @param type
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public SearchCriteria criteriaForLoad(String type,String jobName,JobExecutionContext jobContext) throws Exception{
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	inCriteria = criteria.getInCriteria();
		List<Long> statusIds = new ArrayList<Long>();
		String[] stat = getAssetStatus().split(",");
		if(stat.length > 0){
			for(int i = 0;i<stat.length;i++){
				statusIds.add(Long.parseLong(stat[i].trim()));
			}
		}
		inCriteria.put("obj.status.statusId",statusIds);
		if(jobName != null && jobContext.getTrigger().getName().equals(jobName)){
			Map<String,Object>	lesserEqCriteria = criteria.getLesserEqCriteria();
			lesserEqCriteria.put("obj.startDate",new Timestamp((new Date()).getTime()));
			if(!type.equals("Redirects") && !type.equals("Facets")){
				Map<String,Object>	greaterEqCriteria = criteria.getGreaterEqCriteria();
				greaterEqCriteria.put("obj.endDate",new Timestamp((new Date()).getTime()));
			}
		}
		return criteria;
	}

	/**
	 * gets the Instance of IStatusService
	 * @return
	 */
	public IStatusService getStatusService() {
		return statusService;
	}
	
	/**
	 * gets the Instance of RestTemplate
	 * @return
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	/**
	 * gets the daasServiceURL 
	 * @return
	 */
	public String getDaasServiceUrl() {
		return daasServiceUrl;
	}
	
	/**
	 * gets the Source of the Data
	 * @return
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * gets the AssetStatus
	 * @return
	 */
	public String getAssetStatus() {
		return assetStatus;
	}
	
	/**
	 * gets the ISynonymGroupService Instance
	 * @return ISynonymGroupService
	 */
	public ISynonymService getSynonymGroupService() {
		return synonymService;
	}
	
	/**
	 * gets the JdbcTemplate Instance
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	/**
	 * gets the instance of promoService
	 * @return
	 */
	public PromoService getPromoService() {
		return promoService;
	}
	
	/**
	 * sets the promo Service Instance
	 * @param promoService
	 */
	public void setPromoService(PromoService promoService) {
		this.promoService = promoService;
	}
	
	/**
	 * gets the Instance of IKeywordRedirectService
	 * @return
	 */
	public IKeywordRedirectService getKeywordRedirectService() {
		return keywordRedirectService;
	}
	
	/**
	 * sets the instance of IKeywordRedirectService
	 * @param keywordRedirectService
	 */
	public void setKeywordRedirectService(
			IKeywordRedirectService keywordRedirectService) {
		this.keywordRedirectService = keywordRedirectService;
	}
	
	/**
	 * @return the bannerService
	 */
	public BannerService getBannerService() {
		return bannerService;
	}

	/**
	 * @param to set the bannerService
	 */
	public void setBannerService(BannerService bannerService) {
		this.bannerService = bannerService;
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param to set the entityManager 
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @return the transactionManager
	 */
	public JpaTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param to set the transactionManager 
	 */
	public void setTransactionManager(JpaTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * gets the Status
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * gets the instance of PromoDAO
	 * @return
	 */
	public PromoDAO getPromoDAO() {
		return promoDAO;
	}
	
	/**
	 * sets the instance of PromoDAO
	 * @param promoDAO
	 */
	public void setPromoDAO(PromoDAO promoDAO) {
		this.promoDAO = promoDAO;
	}
	
	/**
	 * gets the instance of BannerDAO
	 * @return
	 */
	public BannerDAO getBannerDAO() {
		return bannerDAO;
	}
	
	/**
	 * sets the Instance of BannerDAO
	 * @param bannerDAO
	 */
	public void setBannerDAO(BannerDAO bannerDAO) {
		this.bannerDAO = bannerDAO;
	}

	/**
	 * gets the instance of FacetDAO
	 * @return
	 */
	public FacetDAO getFacetDAO() {
		return facetDAO;
	}
	
	/**
	 * sets the instance of FacetDAO
	 * @param facetDAO
	 */
	public void setFacetDAO(FacetDAO facetDAO) {
		this.facetDAO = facetDAO;
	}
	
	/**
	 * gets the instance of facetService
	 * @return
	 */
	public FacetService getFacetService() {
		return facetService;
	}
	
	/**
	 * sets the instance of facetService
	 * @param facetService
	 */
	public void setFacetService(FacetService facetService) {
		this.facetService = facetService;
	}
	
	/**
	 * gets the instance of SynonymGroupDAO 
	 * @return
	 */
	public SynonymGroupDAO getSynonymGroupDAO() {
		return synonymGroupDAO;
	}

	/**
	 * sets the instance of synonymGroupDAO
	 * @param synonymGroupDAO
	 */
	public void setSynonymGroupDAO(SynonymGroupDAO synonymGroupDAO) {
		this.synonymGroupDAO = synonymGroupDAO;
	}

	public SolrSearchService getSolrSearchService() {
		return solrSearchService;
	}

	public void setSolrSearchService(SolrSearchService solrSearchService) {
		this.solrSearchService = solrSearchService;
	}

	public IAttributeService getAttributeService() {
		return attributeService;
	}

	public void setAttributeService(IAttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public IAttributeValueService getAttributeValueService() {
		return attributeValueService;
	}

	public void setAttributeValueService(
			IAttributeValueService attributeValueService) {
		this.attributeValueService = attributeValueService;
	}
	
	public BoostAndBlockDao getBoostAndBlockDao() {
		return boostAndBlockDao;
	}

	public void setBoostAndBlockDao(BoostAndBlockDao boostAndBlockDao) {
		this.boostAndBlockDao = boostAndBlockDao;
	}

	public BoostAndBlockService getBoostAndBlockService() {
		return boostAndBlockService;
	}

	public void setBoostAndBlockService(BoostAndBlockService boostAndBlockService) {
		this.boostAndBlockService = boostAndBlockService;
	}


	public CategoryNodeDAO getCategoryNodeDAO() {
		return categoryNodeDAO;
	}


	public void setCategoryNodeDAO(CategoryNodeDAO categoryNodeDAO) {
		this.categoryNodeDAO = categoryNodeDAO;
	}
	public ICategoryNodeService getCategoryNodeService() {
		return categoryNodeService;
	}

	public void setCategoryNodeService(ICategoryNodeService categoryNodeService) {
		this.categoryNodeService = categoryNodeService;
	}

	public String getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(String requestorId) {
		this.requestorId = requestorId;
	}
}
