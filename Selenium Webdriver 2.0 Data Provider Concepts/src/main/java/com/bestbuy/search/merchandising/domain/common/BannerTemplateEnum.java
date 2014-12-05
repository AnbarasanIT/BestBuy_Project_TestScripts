package com.bestbuy.search.merchandising.domain.common;

/**
 * @author Lakshmi Valluripalli
 *
 */
public enum BannerTemplateEnum {
	
	HTML_ONLY("HTML_ONLY"),
	HEADER1_3SKU("1HEADER_3SKU"),
	HEADER3_3SKU("3HEADER_3SKU");
	
	private String templateName;
	
	private BannerTemplateEnum(String templateName){
		this.templateName = templateName;
	}
	
	public String getDisplay(){
		return templateName;
	}
}
