/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;

/**
 *  Dao Implementation class for an interface IBoostAndBlockProductDao.
 * 
 * @author Sreedhar Patlola
 */
public class BoostAndBlockProductDao extends BaseDAO<Long, BoostAndBlockProduct> implements IBoostAndBlockProductDao {

  @Override
  public List<BoostAndBlockProduct> retrieveBoostsAndBlocksProducts(SearchCriteria criteria) throws DataAcessException {

    return (List<BoostAndBlockProduct>) this.executeQuery(criteria);

  }
}
