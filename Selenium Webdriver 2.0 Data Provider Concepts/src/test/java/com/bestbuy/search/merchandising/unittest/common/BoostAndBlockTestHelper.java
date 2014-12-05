/**
 * Dec 3, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.common;

/**
 * @author Ramiro.Serrato
 *
 */
public class BoostAndBlockTestHelper {
	private String searchTerm;
	private Long searchProfileId;
	private String categoryId;
	
	public BoostAndBlockTestHelper() {
		searchTerm = "Test term";
		searchProfileId = 1L;
		categoryId = "cat1";
	}

	/**
	 * @return the searchTerm
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * @param searchTerm the searchTerm to set
	 */
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	/**
	 * @return the searchProfileId
	 */
	public Long getSearchProfileId() {
		return searchProfileId;
	}

	/**
	 * @param searchProfileId the searchProfileId to set
	 */
	public void setSearchProfileId(Long searchProfileId) {
		this.searchProfileId = searchProfileId;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
