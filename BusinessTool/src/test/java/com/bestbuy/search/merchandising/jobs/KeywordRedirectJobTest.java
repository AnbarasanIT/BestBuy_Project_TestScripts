package com.bestbuy.search.merchandising.jobs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.IKeywordRedirectService;
import com.bestbuy.search.merchandising.service.ISearchProfileService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.service.IUsersService;
import com.bestbuy.search.merchandising.service.RoleService;
import com.bestbuy.search.merchandising.unittest.common.MockHttpServer;

/**
 * Test class to test synonym group job
 * 
 * @author a948063
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/it-applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class KeywordRedirectJobTest {
	private JobExecutionContext jobContext;
	private JobDataMap jobDataMap;
	private static MockHttpServer server;
	private ServerThread serverThread;
	private boolean isStarted = false;
	
	@InjectMocks
	private KeywordRedirectJob keywordRedirectJob;
	
	@Resource(name="keywordRedirectCronJobTriggerAF")
	private CronTriggerBean keywordRedirectCronJobTriggerAF;
	
	@Resource(name="keywordRedirectCronJobTrigger")
	private CronTriggerBean keywordRedirectCronJobTriggerCF;
	
	@Resource(type=org.springframework.scheduling.quartz.SchedulerFactoryBean.class)
	private SchedulerFactoryBean schedulerFactory;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IKeywordRedirectService keywordRedirectService;
	
	@Autowired
	private IUsersService usersService;
	
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
	 * Method to test execution of AF job
	 * 
	 * @throws JobExecutionException
	 * @throws ServiceException
	 */
	@Test
	public void testExecuteAF() throws JobExecutionException, ServiceException {
		jobDataMap.put("status", "3,8");
		KeywordRedirect keywordRedirect = this.prepareAFKeywordRedirects();
		when(jobContext.getTrigger()).thenReturn(keywordRedirectCronJobTriggerAF);
		keywordRedirectJob.execute(jobContext);
		assertEquals(keywordRedirect.getStatus().getStatusId().longValue(), 10L);
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
		KeywordRedirect keywordRedirect = this.prepareCFKeywordRedirects();
		when(jobContext.getTrigger()).thenReturn(keywordRedirectCronJobTriggerCF);
		keywordRedirectJob.execute(jobContext);
		assertEquals(keywordRedirect.getStatus().getStatusId().longValue(), 8L);
	}
	
	/**
	 * Method to prepare AF synonyms
	 * 
	 * @throws ServiceException
	 */
	private KeywordRedirect prepareAFKeywordRedirects() throws ServiceException {
		// Retrieve status from db
		Status approvedStatus = statusService.retrieveById(3L);
		// Prepare synonyms data
		return this.prepareKeywordRedirects(approvedStatus);
	}
	
	/**
	 * Method to prepare CF synonyms
	 * 
	 * @throws ServiceException
	 */
	private KeywordRedirect prepareCFKeywordRedirects() throws ServiceException {
		// Retrieve status from db
		Status afPublishedStatus = statusService.retrieveById(10L);
		// Prepare synonyms data
		return this.prepareKeywordRedirects(afPublishedStatus);
	}
	
	/**
	 * Method to prepare keyword redirects
	 * 
	 * @param Status status
	 * @throws ServiceException
	 */
	private KeywordRedirect prepareKeywordRedirects(Status status) throws ServiceException {
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
		
		// Create keyword redirects with Approved status
		KeywordRedirect keywordRedirect = new KeywordRedirect();
		keywordRedirect.setCreatedBy(user);
		keywordRedirect.setCreatedDate(new Date());
		keywordRedirect.setEndDate(new Date(tomorrow.getTimeInMillis()));
		keywordRedirect.setKeyword("testkeyword");
		keywordRedirect.setRedirectString("www.test.com");
		keywordRedirect.setRedirectType("URL");
		keywordRedirect.setStatus(status);
		keywordRedirect.setSearchProfile(searchProfile);
		keywordRedirect.setStartDate(new Date(yesterday.getTimeInMillis()));
		keywordRedirect.setUpdatedDate(new Date());
		keywordRedirect.setUpdatedBy(user);
		keywordRedirectService.save(keywordRedirect);
		
		return keywordRedirect;
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
