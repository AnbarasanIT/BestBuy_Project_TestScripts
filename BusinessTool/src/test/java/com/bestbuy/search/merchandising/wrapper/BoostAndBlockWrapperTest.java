package com.bestbuy.search.merchandising.wrapper;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.BoostAndBlockWrapperConverter;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.service.BoostAndBlockService;
import com.bestbuy.search.merchandising.service.CategoryNodeService;
import com.bestbuy.search.merchandising.service.SearchProfileService;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

public class BoostAndBlockWrapperTest {
	
	StatusService statusService=null;
	UsersService usersService=null;
	BoostAndBlockWrapperConverter boostAndBlockWrapperConverter = null;
	UserUtil userUtil = null;
	CategoryNodeService categoryNodeService = null;
	SearchProfileService searchProfileService = null;
	BoostAndBlockService  boostAndBlockService= null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		
		statusService = mock(StatusService.class);	
		boostAndBlockWrapperConverter = mock(BoostAndBlockWrapperConverter.class);
		userUtil = mock(UserUtil.class);
		categoryNodeService= mock(CategoryNodeService.class);
		searchProfileService=mock(SearchProfileService.class);
		boostAndBlockService = new BoostAndBlockService();
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		statusService = null;
		boostAndBlockWrapperConverter=null;
		userUtil=null;
		categoryNodeService=null;
		searchProfileService=null;
		boostAndBlockService = null;
	}
	
	@Test
	public void testGetBoostAndBlocks() {
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
		List<BoostAndBlock> boostAndBlocks = new ArrayList<BoostAndBlock>();
		boostAndBlocks.add(boostAndBlock);
		List<IWrapper> wrappers = BoostAndBlockWrapper.getBoostAndBlocks(boostAndBlocks);
		assertNotNull(wrappers);
	}
	
	@Test
	public void testBoostBlockWrapper() throws ServiceException, ParseException, DataAcessException {
		setData();
		//Get the BoostBlock wrapper
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get(0);
		//Loading the user for retrieveById
		Users user = BaseData.getUsers();
		when(userUtil.getUser()).thenReturn(user);
		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);
		boostAndBlockWrapperConverter.wrapperConverter(boostAndBlockWrapper, new BoostAndBlock());
		boostAndBlockWrapperConverter.wrapperConverter(null, new BoostAndBlock());
		boostAndBlockWrapper = BaseData.getBoostAndBlockNullWrappers().get(0);
		boostAndBlockWrapper.setStatusId(1L);
		BoostAndBlock boostAndBlock1 = BaseData.getBoostAndBlock();
		boostAndBlock1.setCreatedBy(user);
		boostAndBlockWrapperConverter.wrapperConverter(boostAndBlockWrapper, boostAndBlock1);
	}
	
	public void setData() throws ServiceException{
		//createBoostBlockWrapperConverter object
		boostAndBlockWrapperConverter = new BoostAndBlockWrapperConverter();
		//boostAndBlockWrapperConverter.setStatusService(statusService);
		boostAndBlockWrapperConverter.setUserUtil(userUtil);
		boostAndBlockWrapperConverter.setCategoryNodeService(categoryNodeService);
		boostAndBlockWrapperConverter.setSearchProfileService(searchProfileService);
	}
	
	@Test
	public void testGetProducts() throws ServiceException{
		setData();
		List<BoostBlockProductWrapper> productWrappers =BaseData.getBoostBlockProductWrapper();
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		boostAndBlockWrapperConverter.getProducts(productWrappers, boostAndBlock);
		//NUll Check
		List<BoostBlockProductWrapper> productWrapper =BaseData.getBoostBlockProductNullWrapper();
		boostAndBlockWrapperConverter.getProducts(productWrapper, boostAndBlock);
	}
	
	@Test
	public void testUpdateProducts() throws DataAcessException, ServiceException, ParseException{
		setData();
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		List<BoostBlockProductWrapper> boostBlockProductWrappers=BaseData.getBoostBlockProductWrapper();
		boostAndBlockWrapperConverter.updateProducts(boostAndBlock, boostBlockProductWrappers);
		boostAndBlock=BaseData.getBoostAndBlockList().get(0);
		boostAndBlockWrapperConverter.updateProducts(boostAndBlock, boostBlockProductWrappers);
		boostBlockProductWrappers=BaseData.getBoostBlockProductWrappers();
		boostAndBlock=BaseData.getBoostAndBlockList().get(0);
		boostAndBlockWrapperConverter.updateProducts(boostAndBlock, boostBlockProductWrappers);
	}
}
