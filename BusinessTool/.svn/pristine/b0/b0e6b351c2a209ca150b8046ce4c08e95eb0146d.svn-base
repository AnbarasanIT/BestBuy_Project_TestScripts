package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;

/**
 * @author Kalaiselvi Jaganathan
 *         CRUD operations for Banners
 *         Modified By Chanchal.Kumari added loadBanners method.
 */
public class BannerDAO extends BaseDAO<Long, Banner> implements IBannerDAO {

  private final static BTLogger log = BTLogger.getBTLogger(BannerDAO.class.getName());

  /**
   * Method to load the banners from the Banner entity
   * 
   * @throws ServiceException
   */
  public List<Banner> loadBanners(SearchCriteria criteria) throws ServiceException {
    List<Banner> banners = null;
    try {
      banners = (List<Banner>) this.executeQuery(criteria);
    } catch (DataAcessException e) {
      log.error("BannerDao", e, ErrorType.APPLICATION, "DAO error while retrieving the banners from DB");
      throw new ServiceException(e);
    }
    return banners;
  }
}
