/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Synonym;

/**
 *  interface that defines the structure of SynonymGroup DAO
 * @author Lakshmi Valluripalli
 */
public interface ISynonymGroupDAO extends IBaseDAO<Long,Synonym> {

  public List<Synonym> listSynonymGroups(SearchCriteria criteria) throws ServiceException;

}
