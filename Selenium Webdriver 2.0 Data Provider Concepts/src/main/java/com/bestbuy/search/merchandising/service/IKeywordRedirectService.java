
package com.bestbuy.search.merchandising.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Defines the Redirect Functionality Structure
 *@author chanchal kumari
 * Date 10th Sep 2012 
 */

public interface IKeywordRedirectService extends IBaseService<Long, KeywordRedirect> {

	/**
	 * Method to Create the redirect URL
	 * @param keywordRedirectWrapper
	 * @throws ServiceException
	 */
	public IWrapper createKeywordRedirect(KeywordRedirectWrapper keywordRedirectWrapper) throws ServiceException,ParseException;
	
	/**
	 * Update existing keyword redirect 
	 * @param KeywordRedirectWrapper
	 * @return  IWrapper
	 * @throws ServiceException
	 * @author chanchal.Kumari
	 */
	
	public IWrapper updateRedirect(KeywordRedirectWrapper keywordRedirectWrapper) throws ServiceException;
	
	/**
	 * Update existing keyword redirect  List
	 * @param KeywordRedirectWrapper
	 * @return  IWrapper
	 * @throws ServiceException
	 * @author chanchal.Kumari
	 */
	
	public void updateRedirects(List<KeywordRedirect> redirects) throws ServiceException;
	
	/**
	 * Method to load the redirects from the Keyword_Redorect entity
	 * @throws ServiceException
	 */
	public  List<KeywordRedirectWrapper> loadRedirects(PaginationWrapper paginationWrapper) throws ServiceException;
	
	/**
	 * Method to delete the Redirect from DB
	 * @param id
	 * @throws ServiceException
	 */
	public IWrapper deleteRedirect(Long id) throws ServiceException,ParseException;

	/**
	 * Approves a Keyword Redirect with the given id
	 * @param id A Long with the Keyword Redirect id that we want to approve
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper approveKeywordRedirect(Long id) throws ServiceException;
	
	/**
	 * Rejects a Keyword Redirect with the given id
	 * @param id A Long with the Keyword Redirect id that we want to reject
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper rejectKeywordRedirect(Long id) throws ServiceException;
	
	/**
	 * Change the status to a Keyword Redirect
	 * @param redirect the Keyword Redirect to be updated
	 * @param status The status name for the new status to be set
	 * @throws ServiceException
	 * @author chanchal.Kumari
	 */
	public IWrapper updateKeywordRedirectStatus(KeywordRedirect redirect , String statusString) throws ServiceException;
	
	/**
	 * Loads a redirect into the wrapper to be returned by the REST service
	 * @param redirectId The id of the keyword redirect to be retrieved
	 * @return A List KeywordRedirectWrapper object with the requested information
	 * @author Ramiro.Serrato
	 */
	public List<KeywordRedirectWrapper> loadRedirect(@RequestParam Long redirectId) throws ServiceException;
	
	/**
	 * Change the status of the KeywordRedirect to published
	 * @param id The asset id for this keyword redirect
	 * @throws ServiceException If any error occurs
	 * @author Ramiro.Serrato
	 */
	public void publishKeywordRedirect(Long id) throws ServiceException;
	
}
