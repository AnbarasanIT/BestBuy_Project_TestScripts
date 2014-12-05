package com.bestbuy.search.merchandising.jobs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BannerDAO;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.IBannerService;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.IContextService;
import com.bestbuy.search.merchandising.service.ISearchProfileService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.service.IUsersService;
import com.bestbuy.search.merchandising.service.RoleService;
import com.bestbuy.search.merchandising.unittest.common.MockHttpServer;

/**
 * Test class to test banner job
 * 
 * @author a948063
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/it-applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class BannerJobTest {
	private JobExecutionContext jobContext;
	private JobDataMap jobDataMap;
	private static MockHttpServer server;
	private ServerThread serverThread;
	private boolean isStarted = false;
	BannerDAO bannerDAO;
	
	@InjectMocks
	private BannerJob bannerJob;
	
	@Resource(name="bannerCronJobTriggerAF")
	private CronTriggerBean bannerCronJobTriggerAF;
	
	@Resource(name="bannerCronJobTrigger")
	private CronTriggerBean bannerCronJobTriggerCF;
	
	@Resource(type=org.springframework.scheduling.quartz.SchedulerFactoryBean.class)
	private SchedulerFactoryBean schedulerFactory;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IBannerService bannerService;
	
	@Autowired
	private IUsersService usersService;
	
	@Autowired
	private ICategoryNodeService categoryNodeService;
	
	@Autowired
	private ISearchProfileService searchProfileService;
	
	@Autowired
	private IContextService contextService;
	
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
		bannerDAO=mock(BannerDAO.class);
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
	 * Method to test execution of AF job
	 * @throws Exception 
	 */
	@Test
	public void testExecuteAF() throws Exception {
		jobDataMap.put("status", "3,8,10");
		Banner banner = this.prepareAFBanners();
		when(jobContext.getTrigger()).thenReturn(bannerCronJobTriggerAF);
		bannerJob.execute(jobContext);
		assertEquals(banner.getStatus().getStatusId().longValue(), 10L);
	}
	
	/**
	 * Method to test execution of CF job
	 * 
	 * @throws JobExecutionException
	 * @throws ServiceException
	 */
	@Test 
	public void testExecuteCF() throws JobExecutionException, ServiceException {
		jobDataMap.put("status", "10,8");
		Banner banner = this.prepareCFBanners();
		when(jobContext.getTrigger()).thenReturn(bannerCronJobTriggerCF);
		bannerJob.execute(jobContext);
		assertEquals(banner.getStatus().getStatusId().longValue(), 8L);
	}
	
	/**
	 * Method to prepare AF banners
	 * 
	 * @throws ServiceException
	 */
	private Banner prepareAFBanners() throws ServiceException {
		// Retrieve status from db
		Status approvedStatus = statusService.retrieveById(3L);
		// Prepare synonyms data
		return this.prepareBanners(approvedStatus);
	}
	
	/**
	 * Method to prepare CF banners
	 * 
	 * @throws ServiceException
	 */
	private Banner prepareCFBanners() throws ServiceException {
		// Retrieve status from db
		Status afPublishedStatus = statusService.retrieveById(10L);
		// Prepare synonyms data
		return this.prepareBanners(afPublishedStatus);
	}
	
	/**
	 * Method to prepare banners
	 * 
	 * @param Status status
	 * @throws ServiceException
	 */
	private Banner prepareBanners(Status status) throws ServiceException {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		
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
		
		// Create banner with Approved status
		Banner banner = new Banner();
		banner.setBannerName("test");
		banner.setBannerPriority(1L);
		banner.setBannerTemplate("HTML_ONLY");
		banner.setBannerType("1");
		banner.setStatus(status);
		banner.setStartDate(new Date(yesterday.getTimeInMillis()));
		banner.setEndDate(new Date(tomorrow.getTimeInMillis()));
		banner.setUpdatedDate(new Date());
		banner.setCreatedDate(new Date());
		banner.setCreatedBy(user);
		banner.setUpdatedBy(user);
		banner.setContexts(new ArrayList<Context>());
		
		// Create category
		Category category = new Category();
		category.setCategoryNodeId("test"+(new Date()).getTime());
		category.setCategoryPath("Best%20Buy|Test%20Boost%And%Block");
		category.setIsActive("Y");
		category.setModifiedDate(new Date());
		categoryNodeService.save(category);
		
		// Create context
		Context context = new Context();
		context.setCategoryNode(category);
		context.setInheritable(1L);
		context.setSearchProfile(searchProfile);
		context.setIsActive("Y");
		contextService.save(context);
		banner.getContexts().add(context);
		
		bannerService.save(banner);
		return banner;
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
