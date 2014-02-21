package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;

/**
 * Defines Keyword Redirect DAO
 * @author Lakshmi.Valluripalli - 12 Sep 2012
 *
 */
public interface IKeywordRedirectDAO extends IBaseDAO<Long,KeywordRedirect>{

	/**
	 * Method to load the redirects from the Keyword_Redorect entity
	 * @throws ServiceException
	 */
	public List<KeywordRedirect> loadRedirects(SearchCriteria criteria) throws DataAcessException;
}
