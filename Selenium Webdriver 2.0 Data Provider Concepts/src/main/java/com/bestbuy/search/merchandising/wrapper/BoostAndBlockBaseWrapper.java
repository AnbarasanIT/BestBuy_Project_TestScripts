package com.bestbuy.search.merchandising.wrapper;

import java.util.List;
/**
 * @author Chanchal.Kumari
 *
 */
public class BoostAndBlockBaseWrapper {
	
	private String searchTerm;
	private String categoryPath;
	private String searchProfileName;

	private List<Long> boostProduct;
	
	private List<Long> blockProduct;
	
	
	/**
	 * Default Constructor
	 */
	public BoostAndBlockBaseWrapper(){}
	
	/**
	 * Constructor for the promoWrapper
	 * @param searchTerm
	 * @param categoryPath 
	 * @param searchProfileName
	 * @param boostProduct
	 * @param blockProduct
	 */
	public BoostAndBlockBaseWrapper(String searchTerm, String categoryPath, String searchProfileName,List<Long> boostProduct,List<Long> blockProduct) {
		this.searchTerm = searchTerm;
		this.categoryPath = categoryPath;
		this.searchProfileName = searchProfileName;
		this.boostProduct = boostProduct;
		this.blockProduct = blockProduct;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public String getSearchProfileName() {
		return searchProfileName;
	}

	public void setSearchProfileName(String searchProfileName) {
		this.searchProfileName = searchProfileName;
	}

	public List<Long> getBoostProduct() {
		return boostProduct;
	}

	public void setBoostProduct(List<Long> boostProduct) {
		this.boostProduct = boostProduct;
	}

	public List<Long> getBlockProduct() {
		return blockProduct;
	}

	public void setBlockProduct(List<Long> blockProduct) {
		this.blockProduct = blockProduct;
	}

}
