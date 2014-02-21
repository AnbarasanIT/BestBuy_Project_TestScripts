package com.bestbuy.search.merchandising.unittest.common;

import static org.mockito.Mockito.mock;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.BannerWrapperConverter;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.service.SearchProfileService;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Unit Test case for banner wrapper converter class
 */
public class BannerWrapperConverterTest {

	SearchProfileService searchProfileService;
	UsersService usersService;
	StatusService statusService;
	BannerWrapperConverter bannerWrapperConverter;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerWrapperConverter = new BannerWrapperConverter();
		searchProfileService = mock(SearchProfileService.class);
		usersService = mock(UsersService.class);
		statusService = mock(StatusService.class);
		bannerWrapperConverter=mock(BannerWrapperConverter.class);
	}
	
	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		searchProfileService = null;
		usersService = null;
		statusService = null;
		bannerWrapperConverter=null;
	}
	
	@Test
	public void testWrapperConverter() throws ServiceException, ParseException {
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		Banner banner = BaseData.getBannerData();
		bannerWrapperConverter.wrapperConverter(bannerWrapper,banner);
		//assertTrue(banner.getBannerId() == 1L);
	}
}
