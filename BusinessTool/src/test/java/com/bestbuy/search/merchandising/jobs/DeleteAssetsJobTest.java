/**
 * 
 */
package com.bestbuy.search.merchandising.jobs;


import static org.mockito.Mockito.mock;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.service.StatusService;

/**
 *  Unit Test class for DeleteAssetsJob
 *  @author Lakshmi.Valluripalli
 */
public class DeleteAssetsJobTest {
	
	private final static Logger logger = Logger.getLogger(DeleteAssetsJobTest.class);
	
	
	JobExecutionContext ctx;
	ApplicationContext appCtx;
	Scheduler scheduler;
	SchedulerContext context;
	JobDataMap dataMap;
	StatusService statusService;
	SearchCriteria criteria;
	
	/**
	 * Create the mocks before the test case starts execution
	 */
	@Before
	public void init() {
		ctx = mock(JobExecutionContext.class);
		scheduler = mock(Scheduler.class);
		context = mock(SchedulerContext.class);
		appCtx = mock(ApplicationContext.class);
		criteria = mock(SearchCriteria.class);
		statusService = mock(StatusService.class);
	    dataMap = new JobDataMap();
		dataMap.put("daasServiceURI", "http://localhost:8080/test");
		dataMap.put("status", "3");
	}
	
	/**
	 * Tests the Execute Method of DeleteAssetsJob
	 * @throws JobExecutionException
	 */
	@Test
	public void testJobExecute() throws JobExecutionException{
		/*try{
			DeleteAssetsJob job = new DeleteAssetsJob();
			
			
			when(ctx.getScheduler()).thenReturn(scheduler);
			when(scheduler.getContext()).thenReturn(context);
			when(context.get("applicationContext")).thenReturn(appCtx);
			when(appCtx.getBean("assetsService")).thenReturn(assetsService);
			when(appCtx.getBean("statusService")).thenReturn(statusService);
			when(ctx.getMergedJobDataMap()).thenReturn(dataMap);
			when(assetsService.loadAssets(Mockito.any(SearchCriteria.class))).thenReturn(assets);
			doAnswer(new Answer() {
		 	     public Object answer(InvocationOnMock invocation) {
		 	         Object[] args = invocation.getArguments();
		 	        AssetsService mock = (AssetsService) invocation.getMock();
		 		    return null;
		 	     }
		 	    }).when(assetsService).deleteAssets(assets);
			job.execute(ctx);
		}catch(Exception e){
			logger.error("Error while running unitTest for SynonymGroupJob",e);
		}*/
	}
}
