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
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymTerm;
import com.bestbuy.search.merchandising.domain.SynonymTermPK;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.service.ISynonymService;
import com.bestbuy.search.merchandising.service.ISynonymTypeService;
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
public class SynonymGroupJobTest {
	private JobExecutionContext jobContext;
	private JobDataMap jobDataMap;
	private static MockHttpServer server;
	private ServerThread serverThread;
	private boolean isStarted = false;
	
	@InjectMocks
	private SynonymGroupJob synonymGroupJob;
	
	@Resource(name="synonymCronJobTriggerAF")
	private CronTriggerBean synonymGroupJobTriggerAF;
	
	@Resource(name="synonymCronJobTrigger")
	private CronTriggerBean synonymGroupJobTriggerCF;
	
	@Resource(type=org.springframework.scheduling.quartz.SchedulerFactoryBean.class)
	private SchedulerFactoryBean schedulerFactory;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private ISynonymTypeService synonymTypeService;
	
	@Autowired
	private IUsersService usersService;
	
	@Autowired
	private ISynonymService synonymService;
	
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
		Synonym synonym = this.prepareAFSynonyms();
		when(jobContext.getTrigger()).thenReturn(synonymGroupJobTriggerAF);
		synonymGroupJob.execute(jobContext);
		assertEquals(synonym.getStatus().getStatusId().longValue(), 10L);
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
		Synonym synonym = this.prepareCFSynonyms();
		when(jobContext.getTrigger()).thenReturn(synonymGroupJobTriggerCF);
		synonymGroupJob.execute(jobContext);
		assertEquals(synonym.getStatus().getStatusId().longValue(), 8L);
	}
	
	/**
	 * Method to prepare AF synonyms
	 * 
	 * @throws ServiceException
	 */
	private Synonym prepareAFSynonyms() throws ServiceException {
		// Retrieve status from db
		Status approvedStatus = statusService.retrieveById(3L);
		// Prepare synonyms data
		return this.prepareSynonyms(approvedStatus);
	}
	
	/**
	 * Method to prepare CF synonyms
	 * 
	 * @throws ServiceException
	 */
	private Synonym prepareCFSynonyms() throws ServiceException {
		// Retrieve status from db
		Status afPublishedStatus = statusService.retrieveById(10L);
		// Prepare synonyms data
		return this.prepareSynonyms(afPublishedStatus);
	}
	
	/**
	 * Method to prepare synonyms
	 * 
	 * @param Status status
	 * @throws ServiceException
	 */
	private Synonym prepareSynonyms(Status status) throws ServiceException {
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		
		// Retrieve admin role from db
		Role role = roleService.retrieveById(1);
		
		// Retrieve synonym type
		SynonymType synonymType = synonymTypeService.retrieveById(1133827231862L);
				
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
		
		// Create synonym with Approved status
		Synonym synonym = new Synonym();
		synonym.setCreatedBy(user);
		synonym.setCreatedDate(new Date());
		synonym.setDirectionality("One-way");
		synonym.setEndDate(new Date(tomorrow.getTimeInMillis()));
		synonym.setExactMatch("Exact");
		synonym.setPrimaryTerm("ipad");
		synonym.setStartDate(new Date(yesterday.getTimeInMillis()));
		synonym.setStatus(status);
		synonym.setSynListId(synonymType);
		synonym.setUpdatedBy(user);
		synonym.setUpdatedDate(new Date());
		synonym.setSynonymGroupTerms(new ArrayList<SynonymTerm>());
		
		// Create synonym terms
		SynonymTerm synonymTerm = new SynonymTerm();
		synonymTerm.setSynonymTerms(new SynonymTermPK());
		synonymTerm.getSynonymTerms().setSynonym(synonym);
		synonymTerm.getSynonymTerms().setSynTerm("notepad");
		
		// Add synonym term
		synonym.getSynonymGroupTerms().add(synonymTerm);
		
		synonymService.save(synonym);
		
		return synonym;
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
