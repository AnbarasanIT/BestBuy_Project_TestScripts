/**
 * 
 */
package com.bestbuy.search.merchandising.common;

/**
 * Enum for Banner Type
 * @author Chanchal.Kumari
 *
 */
public enum BannerType {
	KEYWORD_TOP(2l),
    BROWSE_TOP(1l);
    
    public Long id;

	BannerType(Long id) {
		this.id = id;
	}
	
	public static BannerType getById(Long id)
    {
    	BannerType bannerType = null;
	    for (BannerType bannerTypeObj : BannerType.values())
	    {
	        if(id == bannerTypeObj.id)
	        {
	          	bannerType = bannerTypeObj;
	            break;
	        }
	    }
	    return bannerType;
    }
 }
