package com.bestbuy.search.merchandising.wrapper;

import java.util.List;


/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class BannerTemplateWrapper { 
	
	private String bannerHeader;
	private List<BannerSkuWrapper> bannerSlotSkuLists;
	
	/**
	 * @return the bannerHeader
	 */
	public String getBannerHeader() {
		return bannerHeader;
	}
	/**
	 * @param to set the bannerHeader
	 */
	public void setBannerHeader(String bannerHeader) {
		this.bannerHeader = bannerHeader;
	}
	/**
	 * @return the bannerSlotSkuLists
	 */
	public List<BannerSkuWrapper> getBannerSlotSkuLists() {
		return bannerSlotSkuLists;
	}
	/**
	 * @param to set the bannerSlotSkuLists
	 */
	public void setBannerSlotSkuLists(List<BannerSkuWrapper> bannerSlotSkuLists) {
		this.bannerSlotSkuLists = bannerSlotSkuLists;
	}
}
