package com.bestbuy.search.merchandising.wrapper;


/**
 * @author Kalaiselvi Jaganathan
 * JSON structure for BoostBlockProductWrapper
 */
public class BoostBlockProductWrapper {

	private String productName;
	private Long skuId;
	private Integer position;
	private Long productId;

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param to set the productName 
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the skuId
	 */
	public Long getSkuId() {
		return skuId;
	}
	/**
	 * @param to set the skuId 
	 */
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param to set the position 
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * @param to set the productId 
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
