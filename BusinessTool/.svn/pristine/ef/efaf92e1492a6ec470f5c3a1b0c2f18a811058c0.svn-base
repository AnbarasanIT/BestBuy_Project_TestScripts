/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;

/**
 * Dao interface for the database related operations on a table entity BoostAndBlocks.
 * 
 * @author Sreedhar Patlola
 *
 */
public interface IBoostAndBlockDao extends IBaseDAO<Long, BoostAndBlock> {
  
  /**
   * 
   * Loads the BoostsAndBlocks entities based on the search criteria 
   * 
   * @param criteria the search criteria
   * @return the list of BoostsAndBlocks
   * @throws DataAcessException the DataAcessException
   */
  public List<BoostAndBlock> retrieveBoostsAndBlocks(SearchCriteria criteria) throws DataAcessException;
  

}
