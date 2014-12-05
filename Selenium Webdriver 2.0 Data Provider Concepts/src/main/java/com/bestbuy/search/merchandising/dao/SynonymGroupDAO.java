/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Synonym;

/**
 * CRUD operations for SynonymGroupDAO
 * 
 * @author Lakshmi Valluripalli
 *         Modified By : Kalaiselvi.Jaganathan 17-Aug-2012 Added loadSynonymsGroups
 */
public class SynonymGroupDAO extends BaseDAO<Long, Synonym> implements ISynonymGroupDAO {

  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(SynonymGroupDAO.class.getName());

  /**
   * Method to load the Synonym data
   * 
   * @throws ServiceException
   */
  public List<Synonym> listSynonymGroups(SearchCriteria criteria) throws ServiceException {
    List<Synonym> synonymGroups = null;
    try {
      synonymGroups = (List<Synonym>) this.executeQuery(criteria);
    } catch (DataAcessException e) {
      log.error("SynonymGroupDAO", e, ErrorType.APPLICATION, "error while retrieving the Synonym from DB");
      throw new ServiceException(e);
    }
    return synonymGroups;
  }
}
