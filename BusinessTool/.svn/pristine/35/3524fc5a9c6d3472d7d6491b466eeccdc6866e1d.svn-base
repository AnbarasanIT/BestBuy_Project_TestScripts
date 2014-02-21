/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;

/**
 * Dao interface for the database related operations on a table entity BoostAndBlockProducts.
 * 
 * @author Sreedhar Patlola
 */
public interface IBoostAndBlockProductDao extends IBaseDAO<Long, BoostAndBlockProduct> {

  /**
   * Loads the BoostsAndBlocksProducts entities based on the search criteria
   * 
   * @param criteria
   *          the search criteria
   * @return the list of BoostsAndBlocksProducts
   * @throws DataAcessException
   *           the DataAcessException
   */
  public List<BoostAndBlockProduct> retrieveBoostsAndBlocksProducts(SearchCriteria criteria) throws DataAcessException;

}
