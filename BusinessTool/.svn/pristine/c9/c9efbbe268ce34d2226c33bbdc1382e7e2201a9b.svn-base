package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class KeywordRedirectDAO extends BaseDAO<Long,KeywordRedirect> implements IKeywordRedirectDAO{
	
	/**
	 * Method to load the redirects from the Keyword_Redorect entity
	 * @throws DataAcessException 
	 * @throws ServiceException
	 */
	public List<KeywordRedirect> load(SearchCriteria criteria) throws DataAcessException{
		
		 return (List<KeywordRedirect>) this.executeQuery(criteria);
	}
	
	
	public List<KeywordRedirect> loadRedirects(SearchCriteria criteria) throws DataAcessException{
		
		 return (List<KeywordRedirect>) this.executeQueryWithCriteria(criteria);
	}
		
}
