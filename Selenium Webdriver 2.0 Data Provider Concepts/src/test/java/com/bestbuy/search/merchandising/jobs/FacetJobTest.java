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
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.domain.common.DisplayModeEnum;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.IFacetService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.service.IUsersService;
import com.bestbuy.search.merchandising.service.RoleService;
import com.bestbuy.search.merchandising.unittest.common.MockHttpServer;

/**
 * Test class to test facet job
 * 
 * @author a948063
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/it-applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class FacetJobTest {
	private JobExecutionContext jobContext;
	private JobDataMap jobDataMap;
	private static MockHttpServer server;
	private ServerThread serverThread;
	private boolean isStarted = false;
	
	@InjectMocks
	private FacetJob facetJob;
	
	@Resource(name="facetCronJobTriggerAF")
	private CronTriggerBean facetCronJobTriggerAF;
	
	@Resource(name="facetCronJobTrigger")
	private CronTriggerBean facetCronJobTriggerCF;
	
	@Resource(type=org.springframework.scheduling.quartz.SchedulerFactoryBean.class)
	private SchedulerFactoryBean schedulerFactory;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IFacetService facetService;
	
	@Autowired
	private IUsersService usersService;
	
	@Autowired
	private ICategoryNodeService categoryNodeService;
	
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
		Facet facet = this.prepareAFFacets();
		when(jobContext.getTrigger()).thenReturn(facetCronJobTriggerAF);
		facetJob.execute(jobContext);
		assertEquals(facet.getStatus().getStatusId().longValue(), 10L);
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
		Facet facet = this.prepareCFFacets();
		when(jobContext.getTrigger()).thenReturn(facetCronJobTriggerCF);
		facetJob.execute(jobContext);
		assertEquals(facet.getStatus().getStatusId().longValue(), 8L);
	}
	
	/**
	 * Method to prepare AF facets
	 * 
	 * @throws ServiceException
	 */
	private Facet prepareAFFacets() throws ServiceException {
		// Retrieve status from db
		Status approvedStatus = statusService.retrieveById(3L);
		// Prepare synonyms data
		return this.prepareFacets(approvedStatus);
	}
	
	/**
	 * Method to prepare CF facets
	 * 
	 * @throws ServiceException
	 */
	private Facet prepareCFFacets() throws ServiceException {
		// Retrieve status from db
		Status afPublishedStatus = statusService.retrieveById(10L);
		// Prepare synonyms data
		return this.prepareFacets(afPublishedStatus);
	}
	
	/**
	 * Method to prepare facets
	 * 
	 * @param Status status
	 * @throws ServiceException
	 */
	private Facet prepareFacets(Status status) throws ServiceException {
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
		
		// Create facet with Approved status
		Facet facet = new Facet();
		facet.setDisplayName("Test Facet");
		facet.setSystemName("testFacet");
		facet.setDisplayMode(DisplayModeEnum.SEARCH);
		facet.setDisplay(DisplayEnum.Y);
		facet.setDisplayRecurrence(DisplayEnum.Y);
		facet.setDisplayFacetRemoveLink(DisplayEnum.Y);
		facet.setDisplayMobileFacet(DisplayEnum.Y);
		facet.setDisplayMobileFacetRemoveLink(DisplayEnum.Y);
		facet.setGlossaryItem(123L);
		facet.setAttrValSortType("num_spec_AZ");
		facet.setMinAttrValue(1L);
		facet.setMaxAttrValue(5L);
		facet.setStartDate(new Date(yesterday.getTimeInMillis()));
		facet.setEndDate(new Date(tomorrow.getTimeInMillis()));
		facet.setCreatedDate(new Date());
		facet.setCreatedBy(user);
		facet.setUpdatedDate(new Date());
		facet.setUpdatedBy(user);
		facet.setStatus(status);
		facet.setCategoryFacet(new ArrayList<CategoryFacet>());
		
		// Create attribute
		Attribute attribute = new Attribute();
		attribute.setAttributeName("test_facet");
		attribute.setIsActive("Y");
		facet.setAttribute(attribute);
		
		// Create category
		Category category = new Category();
		category.setCategoryNodeId("test"+(new Date()).getTime());
		category.setCategoryPath("Best%20Buy|Test%20Boost%And%Block");
		category.setIsActive("Y");
		category.setModifiedDate(new Date());
		categoryNodeService.save(category);
		
		// Create category facet
		CategoryFacet categoryFacet = new CategoryFacet();
		categoryFacet.setCategoryNode(category);
		categoryFacet.setFacet(facet);
		categoryFacet.setApplySubCategory(DisplayEnum.Y);
		categoryFacet.setDepFacetDisplay(DisplayEnum.N);
		categoryFacet.setDisplayOrder(1L);
		categoryFacet.setDisplay(DisplayEnum.Y);
		categoryFacet.setIsActive("Y");
		facet.getCategoryFacet().add(categoryFacet);
		
		facetService.save(facet);
		return facet;
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
