package com.bestbuy.search.merchandising.service;

import java.text.ParseException;
import java.util.List;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Defines the Banner Functionality Structure
 *@author chanchal kumari
 * Date 24th Sep 2012 
 */
public interface IBannerService extends IBaseService<Long, Banner>{
	
	/**
	 * Method to load the banners from the Banners entity
	 * @throws ServiceException
	 */
	public List<Banner> loadBanners(PaginationWrapper paginationWrapper) throws ServiceException;
	
	/**
	 * Deletes a banner with the given id, it is a soft delete this means the method will just update the status of the asset to Deleted
	 * @param id A Long with the SynonymGroup id of the banner that we want to delete
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper deleteBanner(Long bannerId) throws ServiceException,ParseException;

	public IWrapper approveBanner(Long id) throws ServiceException;
	public IWrapper rejectBanner(Long id) throws ServiceException;
	
	/**
	 * Method to Create the Banner
	 * @param bannerWrapper
	 * @throws ServiceException
	 */
	public IWrapper createBanner(BannerWrapper bannerWrapper) throws ServiceException,ParseException;
	
	/**
	 * Method to update the Banner
	 * @param bannerWrapper
	 * @throws ServiceException
	 * @throws DataAcessException 
	 */
	public IWrapper updateBanner(BannerWrapper bannerWrapper) throws ServiceException,ParseException,DataAcessException;
	
	/**
	 * Publishes the banner, by changing the status to Published
	 * @param id A long with the asset id
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public void publishBanner(Long id) throws ServiceException;
	
	/**
	 * Makes the banner invalid, by changing the status to Invalid
	 * @param id A long with the asset id
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper invalidBanner(Long id) throws ServiceException;
	
	/**
	 * Updates the status to a banner, also logs the change in the history table
	 * @param asset The Asset which represents the banner to be updated
	 * @param status The status name for the new status to be set
	 * @throws ServiceException 
	 * @author Ramiro.Serrato
	 */
	public IWrapper updateStatusForBanner(Banner banner, String status) throws ServiceException;
	
	/**
	 * Get the banner data for the banner id
	 * @param bannerId
	 * @return
	 * @throws ServiceException
	 */
	public BannerWrapper loadeditbanner(Long bannerId) throws ServiceException;
	
	/**
	 * Method to delete the banner context
	 * @param bannerId
	 * @throws ServiceException
	 * @throws ParseException
	 */
	public void deleteBannerContext(Long bannerId) throws ServiceException,ParseException;
	
	
	/**
	 * Method to Update the List of banners
	 * @param banners
	 * @throws ServiceException
	 */
	public void updateBanners(List<Banner> banners) throws ServiceException;

}
