/**
 * 
 */
package com.bestbuy.search.merchandising.jobs;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.domain.PromoSku;
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.IBoostAndBlockService;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.IPromoService;
import com.bestbuy.search.merchandising.service.ISearchProfileService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.service.IUsersService;
import com.bestbuy.search.merchandising.service.RoleService;
import com.bestbuy.search.merchandising.unittest.common.MockHttpServer;

/**
 * Test class to test promo group job
 * 
 * @author a948063
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/it-applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class PromoJobTest {
	private JobExecutionContext jobContext;
	private JobDataMap jobDataMap;
	private static MockHttpServer server;
	private ServerThread serverThread;
	private boolean isStarted = false;
	
	@InjectMocks
	private PromoJob promoJob;
	
	@Resource(name="promoCronJobTriggerAF")
	private CronTriggerBean promoJobTriggerAF;
	
	@Resource(name="promoCronJobTrigger")
	private CronTriggerBean promoJobTriggerCF;
	
	@Resource(type=org.springframework.scheduling.quartz.SchedulerFactoryBean.class)
	private SchedulerFactoryBean schedulerFactory;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IUsersService usersService;
	
	@Autowired
	private IPromoService promoService;
	
	@Autowired
	private ICategoryNodeService categoryNodeService;
	
	@Autowired
	private IBoostAndBlockService boostAndBlockService;
	
	@Autowired
	private ISearchProfileService searchProfileService;
	
	/**
	 * Before class method (runs once during initialization)
	 * 
	 */
	@BeforeClass
	public static void beforeClass() {
		server = new MockHttpServer(8888);
		server.setMockResponseContent("success");
		server.startServer();
	}
	
	/**
	 * Before method (runs before every test method)
	 * 
	 * @throws SchedulerException
	 */
	@Before
	public void before() throws SchedulerException {
		if (!isStarted) {
			serverThread = new ServerThread();
			serverThread.start();
			isStarted = true;
		}
		MockitoAnnotations.initMocks(this);
		jobContext = mock(JobExecutionContext.class);
		jobDataMap = new JobDataMap();
		jobDataMap.put("daasServiceURI", "http://localhost:"+server.getServerPort()+"/");
		jobDataMap.put("source", "BBY.com");
		jobDataMap.put("requestorId", "Dotcom");
		when(jobContext.getMergedJobDataMap()).thenReturn(jobDataMap);
		when(jobContext.getScheduler()).thenReturn(schedulerFactory.getScheduler());
	}
	
	/**
	 * After class method (runs once while teardown)
	 * 
	 */
	@AfterClass
	public static void after() {
		server.stopServer();
	}
	
	/**
	 * Method to test execution of AF job for promos
	 * 
	 * @throws JobExecutionException
	 * @throws ServiceException
	 */
	@Test
	public void testExecutePromoAF() throws JobExecutionException, ServiceException {
		jobDataMap.put("status", "3,8");
		Promo promo = this.prepareAFPromos();
		when(jobContext.getTrigger()).thenReturn(promoJobTriggerAF);
		promoJob.execute(jobContext);
		assertEquals(promo.getStatus().getStatusId().longValue(), 10L);
	}
	
	/**
	 * Method to test execution of CF job for promos
	 * 
	 * @throws JobExecutionException
	 * @throws ServiceException
	 */
	@Test
	public void testExecutePromoCF() throws JobExecutionException, ServiceException {
		jobDataMap.put("status", "10,8");
		Promo promo = this.prepareCFPromos();
		when(jobContext.getTrigger()).thenReturn(promoJobTriggerCF);
		promoJob.execute(jobContext);
		assertEquals(promo.getStatus().getStatusId().longValue(), 8L);
	}
	
	/**
	 * Method to test execution of AF job for boost and block
	 * 
	 * @throws JobExecutionException
	 * @throws ServiceException
	 */
	@Test
	public void testExecuteBoostAndBlockAF() throws JobExecutionException, ServiceException {
		jobDataMap.put("status", "3,8");
		BoostAndBlock boostAndBlock = this.prepareAFBoostAndBlocks();
		when(jobContext.getTrigger()).thenReturn(promoJobTriggerAF);
		promoJob.execute(jobContext);
		assertEquals(boostAndBlock.getStatus().getStatusId().longValue(), 10L);
	}
	
	/**
	 * Method to test execution of CF job
	 * 
	 * @throws JobExecutionException
	 * @throws ServiceException
	 */
	@Test
	public void testExecuteBoostAndBlockCF() throws JobExecutionException, ServiceException {
		jobDataMap.put("status", "10,8");
		BoostAndBlock boostAndBlock = this.prepareCFBoostAndBlocks();
		when(jobContext.getTrigger()).thenReturn(promoJobTriggerCF);
		promoJob.execute(jobContext);
		assertEquals(boostAndBlock.getStatus().getStatusId().longValue(), 8L);
	}
	
	/**
	 * Method to prepare AF promos
	 * 
	 * @throws ServiceException
	 */
	private Promo prepareAFPromos() throws ServiceException {
		// Retrieve status from db
		Status approvedStatus = statusService.retrieveById(3L);
		// Prepare synonyms data
		return this.preparePromos(approvedStatus);
	}
	
	/**
	 * Method to prepare CF promos
	 * 
	 * @throws ServiceException
	 */
	private Promo prepareCFPromos() throws ServiceException {
		// Retrieve status from db
		Status afPublishedStatus = statusService.retrieveById(10L);
		// Prepare synonyms data
		return this.preparePromos(afPublishedStatus);
	}
	
	/**
	 * Method to prepare AF boost and blocks
	 * 
	 * @throws ServiceException
	 */
	private BoostAndBlock prepareAFBoostAndBlocks() throws ServiceException {
		// Retrieve status from db
		Status approvedStatus = statusService.retrieveById(3L);
		// Prepare synonyms data
		return this.prepareBoostAndBlocks(approvedStatus);
	}
	
	/**
	 * Method to prepare CF boost and blocks
	 * 
	 * @throws ServiceException
	 */
	private BoostAndBlock prepareCFBoostAndBlocks() throws ServiceException {
		// Retrieve status from db
		Status afPublishedStatus = statusService.retrieveById(10L);
		// Prepare synonyms data
		return this.prepareBoostAndBlocks(afPublishedStatus);
	}
	
	/**
	 * Method to prepare promos
	 * 
	 * @param Status status
	 * @return Promo
	 * @throws ServiceException
	 */
	private Promo preparePromos(Status status) throws ServiceException {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		
		// Retrieve admin role from db
		Role role = roleService.retrieveById(1);
				
		// Create test user
		Users user = new Users();
		user.setActive(true);
		user.setEmail("test.user@test.com");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setLoginName("a123456");
		user.setPassword("test");
		user.setRole(role);
		user.setUpdatedBy("test");
		user.setUpdatedDate(new Date());
		usersService.save(user);
		
		// Create promo with Approved status
		Promo promo = new Promo();
		promo.setCreatedBy(user);
		promo.setCreatedDate(new Date());
		promo.setEndDate(new Date(tomorrow.getTimeInMillis()));
		promo.setPromoName("TestPromo");
		promo.setStartDate(new Date(yesterday.getTimeInMillis()));
		promo.setStatus(status);
		promo.setUpdatedBy(user);
		promo.setUpdatedDate(new Date());
		promo.setPromoSku(new ArrayList<PromoSku>());
		
		PromoSku promoSku = new PromoSku();
		promoSku.setPromo(promo);
		promoSku.setSkuId("12345");
		promo.getPromoSku().add(promoSku);
		
		promoService.save(promo);
		return promo;
	}
	
	/**
	 * Method to prepare boost and blocks
	 * 
	 * @param Status status
	 * @return BoostAndBlock
	 * @throws ServiceException
	 */
	private BoostAndBlock prepareBoostAndBlocks(Status status) throws ServiceException {
		// Retrieve admin role from db
		Role role = roleService.retrieveById(1);
		
		// Retrieve search profile from db
		SearchProfile searchProfile = searchProfileService.retrieveById(1L);
		
		// Create test user
		Users user = new Users();
		user.setActive(true);
		user.setEmail("test.user@test.com");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setLoginName("a123456");
		user.setPassword("test");
		user.setRole(role);
		user.setUpdatedBy("test");
		user.setUpdatedDate(new Date());
		usersService.save(user);
		
		// Create category
		Category category = new Category();
		category.setCategoryNodeId("test"+(new Date()).getTime());
		category.setCategoryPath("Best%20Buy|Test%20Boost%And%Block");
		category.setIsActive("Y");
		category.setModifiedDate(new Date());
		categoryNodeService.save(category);
		
		// Create boost and block with Approved status
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setCategoryId(category);
		boostAndBlock.setCreatedBy(user);
		boostAndBlock.setCreatedDate(new Date());
		boostAndBlock.setEndDate(new Date());
		boostAndBlock.setSearchProfileId(searchProfile);
		boostAndBlock.setStartDate(new Date());
		boostAndBlock.setStatus(status);
		boostAndBlock.setTerm("test");
		boostAndBlock.setUpdatedBy(user);
		boostAndBlock.setUpdatedDate(new Date());
		
		boostAndBlockService.save(boostAndBlock);
		return boostAndBlock;
	}
	
	/**
	 * Server thread inner class
	 * 
	 * @author a948063
	 *
	 */
	private class ServerThread extends Thread {
		/**
		 * Run method
		 * 
		 */
		public void run() {
			server.run();
		}
	}
}
