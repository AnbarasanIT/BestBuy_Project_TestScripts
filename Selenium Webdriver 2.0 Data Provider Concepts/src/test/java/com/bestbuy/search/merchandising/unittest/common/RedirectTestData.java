package com.bestbuy.search.merchandising.unittest.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;


/**
 * Redirect URL  Test Data
 * @author chanchal Kumari
 * @Dated 10th Sep 2012
 */

public class RedirectTestData {

	/**
	 * creates Redirect POJO Object 
	 * @return RedirectWrapper
	 */

	public static KeywordRedirectWrapper getRedirectWrapper() {
		KeywordRedirectWrapper redirectWrapper = new KeywordRedirectWrapper();
		redirectWrapper.setRedirectId(1L);
		redirectWrapper.setRedirectTerm("testcasetesing5000000000");
		redirectWrapper.setRedirectType("hsdjhjd");
		redirectWrapper.setStatusId(2l);
		redirectWrapper.setEndDate(new Date());
		redirectWrapper.setStartDate(new Date());
		redirectWrapper.setRedirectUrl("http://www.bestbuy.com");
		redirectWrapper.setSearchProfileId(1l);
		return redirectWrapper;
	}
	public static KeywordRedirectWrapper getCreateRedirectWrapper() {
		KeywordRedirectWrapper redirectWrapper = new KeywordRedirectWrapper();
		redirectWrapper.setRedirectTerm("testcasetesing5000000000");
		redirectWrapper.setRedirectType("hsdjhjd");
		redirectWrapper.setStatusId(2l);
		redirectWrapper.setEndDate(new Date());
		redirectWrapper.setStartDate(new Date());
		redirectWrapper.setRedirectUrl("http://www.bestbuy.com");
		redirectWrapper.setSearchProfileId(1l);
		return redirectWrapper;
	}



	/**
	 * Created the User Object
	 * @return Users
	 */
	public static Users getUsers(){
		Users user = new Users();
		user.setLoginName("A1003132");
		user.setFirstName("Chanchal");
		user.setLastName("Kumari");
		user.setEmail("Chanchal.Kumari@bestbuy.com");
		return user;
	}

	/**
	 * creates the Status Object for create
	 * @return Status
	 */
	public static Status getStatus(){
		Status status = new Status();
		status.setStatus("Draft");
		status.setStatusId(2L);
		return status;
	}

	/**
	 * creates KeywordRedirect Object 
	 * @return KeywordRedirect
	 */
	public static KeywordRedirect getKeywordRedirect(){
		KeywordRedirect keywordRedirect = new KeywordRedirect();
		keywordRedirect.setKeyword("chanchal");
		keywordRedirect.setRedirectString("http://www.bestbuy.com");
		keywordRedirect.setRedirectType("hsdjhjd");
		keywordRedirect.setSearchProfile(getSearchProfile());
		return keywordRedirect;
	}

	/**
	 * creates KeywordRedirect Object 
	 * for persisted mock
	 * @return KeywordRedirect
	 */
	public static KeywordRedirect getSavedKeywordRedirect(){
		KeywordRedirect keywordRedirect = new KeywordRedirect();
		keywordRedirect.setKeywordId(1l);
		keywordRedirect.setKeyword("chanchal");
		keywordRedirect.setRedirectString("bestbuy.com");
		keywordRedirect.setRedirectType("hsdjhjd");
		keywordRedirect.setSearchProfile(getSearchProfile());
		return keywordRedirect;
	}

	/**
	 * Created the searchProfile Object
	 * @return searchProfile
	 */
	public static SearchProfile getSearchProfile(){
		SearchProfile searchProfile = new SearchProfile();
		searchProfile.setProfileName("Global_Profile");
		searchProfile.setSearchProfileId(1l);
		return searchProfile;
	}

	/**
	 * creates Redirect POJO Object for update
	 * @return RedirectWrapper
	 */

	public static KeywordRedirectWrapper getUpdateRedirectWrapper() {
		KeywordRedirectWrapper redirectWrapper = new KeywordRedirectWrapper();
		redirectWrapper.setRedirectId(1L);
		redirectWrapper.setRedirectTerm("term");
		redirectWrapper.setRedirectType("URL");
		redirectWrapper.setStatusId(2l);
		redirectWrapper.setEndDate(new Date());
		redirectWrapper.setStartDate(new Date());
		redirectWrapper.setRedirectUrl("http://www.bestbuy.com");
		redirectWrapper.setSearchProfileId(1l);
		return redirectWrapper;
	}
	
	public static List<KeywordRedirectWrapper> getListOfWrappers() {
		ArrayList<KeywordRedirectWrapper> wrappers = new ArrayList<KeywordRedirectWrapper>();
		
		// if needed feed the list here
		
		return wrappers;
	}
}
