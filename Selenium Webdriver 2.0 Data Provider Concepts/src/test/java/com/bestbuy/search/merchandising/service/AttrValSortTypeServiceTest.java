package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.AttrValSortTypeDAO;
import com.bestbuy.search.merchandising.domain.AttrValSortType;
import com.bestbuy.search.merchandising.service.AttrValSortTypeService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class AttrValSortTypeServiceTest {
	
	AttrValSortTypeDAO attrValSortTypeDAO;
	AttrValSortTypeService attrValSortTypeService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		attrValSortTypeService = new AttrValSortTypeService();
		attrValSortTypeDAO = mock(AttrValSortTypeDAO.class);
		attrValSortTypeService.setDao(attrValSortTypeDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		attrValSortTypeService = null;
		attrValSortTypeDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		AttrValSortType attrValSortType = BaseData.getAttrValSortType();
		when(attrValSortTypeService.save(attrValSortType)).thenReturn(attrValSortType);
		attrValSortType=attrValSortTypeDAO.save(attrValSortType);
		assertNotNull(attrValSortType);
	}

}
