/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;

/**
 * Dao Implementation class for an interface IBoostAndBlockDao.
 * 
 * @author Sreedhar Patlola
 */
public class BoostAndBlockDao extends BaseDAO<Long, BoostAndBlock> implements IBoostAndBlockDao {

  @Override
  public List<BoostAndBlock> retrieveBoostsAndBlocks(SearchCriteria criteria) throws DataAcessException {

    return (List<BoostAndBlock>) this.executeQueryWithCriteria(criteria);

  }

}
