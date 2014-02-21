/**
 * Nov 7, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.common;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrderPK;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetOrderWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Ramiro.Serrato
 *
 */
public class FacetDisplayOrderTestHelper extends BaseData {
	private List<CategoryFacetOrderWrapper> categoryFacetWrappers;
	
	private CategoryFacetOrderWrapper categoryFacetWrapper1;
	private CategoryFacetOrderWrapper categoryFacetWrapper2;
	private CategoryFacetOrderWrapper categoryFacetWrapper3;
	
	private CategoryFacet categoryFacet1;
	private CategoryFacet categoryFacet2;
	private CategoryFacet categoryFacet3;
	private List<CategoryFacet> categoryFacets;
	
	private Category category1;
	
	private Facet facet1; 
	private Facet facet2;
	private Facet facet3;
	
	private AttributeValue attributeValue1;
	private AttributeValue attributeValue2;
	private AttributeValue attributeValue3;
	private AttributeValue attributeValue4;
	private AttributeValue attributeValue5;
	private AttributeValue attributeValue6;
	private AttributeValue attributeValue7;
	private AttributeValue attributeValue8;
	private AttributeValue attributeValue9;
	private AttributeValue attributeValue10;
	private AttributeValue attributeValue11;
	private AttributeValue attributeValue12;
	private AttributeValue attributeValue13;
	private AttributeValue attributeValue14;
	
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK1;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK2;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK3;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK4;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK5;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK6;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK7;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK8;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK9;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK10;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK11;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK12;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK13;
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK14;
	
	private FacetAttributeValueOrder facetAttributeValueOrder1;
	private FacetAttributeValueOrder facetAttributeValueOrder2;
	private FacetAttributeValueOrder facetAttributeValueOrder3;
	private FacetAttributeValueOrder facetAttributeValueOrder4;
	private FacetAttributeValueOrder facetAttributeValueOrder5;
	private FacetAttributeValueOrder facetAttributeValueOrder6;
	private FacetAttributeValueOrder facetAttributeValueOrder7;
	private FacetAttributeValueOrder facetAttributeValueOrder8;
	private FacetAttributeValueOrder facetAttributeValueOrder9;
	private FacetAttributeValueOrder facetAttributeValueOrder10;
	private FacetAttributeValueOrder facetAttributeValueOrder11;
	private FacetAttributeValueOrder facetAttributeValueOrder12;
	private FacetAttributeValueOrder facetAttributeValueOrder13;
	private FacetAttributeValueOrder facetAttributeValueOrder14;
	
	private List<FacetAttributeValueOrder> values1;
	private List<FacetAttributeValueOrder> values2;
	private List<FacetAttributeValueOrder> values3;
	
	private Status statusDeleted;
	
	public FacetDisplayOrderTestHelper(){		
		Status status = new Status();
		status.setStatus("Draft");
		status.setStatusId(2L); //AF
		
		Status status1 = new Status();
		status1.setStatus("Approved");
		status1.setStatusId(3L); //AF
		
		statusDeleted = new Status();
		statusDeleted.setStatus("Deleted");
		
		facet1 = new Facet();
		facet1.setFacetId(1L);
		facet1.setDisplay(DisplayEnum.Y);
		facet1.setDisplayName("Brand");
		facet1.setSystemName("Sys Brand");
		facet1.setStatus(status1);
		
		facet2 = new Facet();
		facet2.setFacetId(2L);
		facet2.setDisplay(DisplayEnum.N);		
		facet2.setDisplayName("Screen Size");
		facet2.setSystemName("Sys Screen Size"); 
		facet2.setStatus(status);
		
		facet3 = new Facet();
		facet3.setFacetId(3L);
		facet3.setDisplay(DisplayEnum.Y);		
		facet3.setDisplayName("Other Brand");
		facet3.setSystemName("More brands"); 
		facet3.setStatus(status);		
		
		attributeValue1 = new AttributeValue();
		attributeValue1.setAttributeValue("Samsung");
		
		attributeValue2 = new AttributeValue();
		attributeValue2.setAttributeValue("Sony");
		
		attributeValue3 = new AttributeValue();
		attributeValue3.setAttributeValue("Panasonic");
		
		attributeValue4 = new AttributeValue();
		attributeValue4.setAttributeValue("Westinghouse");
		
		attributeValue5 = new AttributeValue();
		attributeValue5.setAttributeValue("Texas Instruments");
		
		attributeValue6 = new AttributeValue();
		attributeValue6.setAttributeValue("Apple");
		
		attributeValue7 = new AttributeValue();
		attributeValue7.setAttributeValue("Hewlett Packard");
		
		attributeValue8 = new AttributeValue();
		attributeValue8.setAttributeValue("US Robotics");
		
		attributeValue9 = new AttributeValue();
		attributeValue9.setAttributeValue("Venturer Electronics");
		
		attributeValue10 = new AttributeValue();
		attributeValue10.setAttributeValue("19\"");
		
		attributeValue11 = new AttributeValue();
		attributeValue11.setAttributeValue("20\"");
		
		attributeValue12 = new AttributeValue();
		attributeValue12.setAttributeValue("32\"");
		
		attributeValue13 = new AttributeValue();
		attributeValue13.setAttributeValue("50\"");
		
		attributeValue14 = new AttributeValue();
		attributeValue14.setAttributeValue("Nakazaki");
		
		facetAttributeValueOrderPK1 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK1.setAttributeValue(attributeValue1);
		facetAttributeValueOrderPK1.setFacet(facet1);
		
		facetAttributeValueOrderPK2 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK2.setAttributeValue(attributeValue2);
		facetAttributeValueOrderPK2.setFacet(facet1);
		
		facetAttributeValueOrderPK3 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK3.setAttributeValue(attributeValue3);
		facetAttributeValueOrderPK3.setFacet(facet1);
		
		facetAttributeValueOrderPK4 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK4.setAttributeValue(attributeValue4);
		facetAttributeValueOrderPK4.setFacet(facet1);
		
		facetAttributeValueOrderPK5 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK5.setAttributeValue(attributeValue5);
		facetAttributeValueOrderPK5.setFacet(facet1);
		
		facetAttributeValueOrderPK6 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK6.setAttributeValue(attributeValue6);
		facetAttributeValueOrderPK6.setFacet(facet1);
		
		facetAttributeValueOrderPK7 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK7.setAttributeValue(attributeValue7);
		facetAttributeValueOrderPK7.setFacet(facet1);
		
		facetAttributeValueOrderPK8 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK8.setAttributeValue(attributeValue8);
		facetAttributeValueOrderPK8.setFacet(facet1);
		
		facetAttributeValueOrderPK9 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK9.setAttributeValue(attributeValue9);
		facetAttributeValueOrderPK9.setFacet(facet1);
		
		facetAttributeValueOrderPK10 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK10.setAttributeValue(attributeValue10);
		facetAttributeValueOrderPK10.setFacet(facet2);
		
		facetAttributeValueOrderPK11 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK11.setAttributeValue(attributeValue11);
		facetAttributeValueOrderPK11.setFacet(facet2);
		
		facetAttributeValueOrderPK12 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK12.setAttributeValue(attributeValue12);
		facetAttributeValueOrderPK12.setFacet(facet2);
		
		facetAttributeValueOrderPK13 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK13.setAttributeValue(attributeValue13);
		facetAttributeValueOrderPK13.setFacet(facet2);
		
		facetAttributeValueOrderPK14 = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK14.setAttributeValue(attributeValue14);
		facetAttributeValueOrderPK14.setFacet(facet3);
		
		facetAttributeValueOrder1 = new FacetAttributeValueOrder();
		facetAttributeValueOrder1.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK1);
		
		facetAttributeValueOrder2 = new FacetAttributeValueOrder();
		facetAttributeValueOrder2.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK2);
		
		facetAttributeValueOrder3 = new FacetAttributeValueOrder();
		facetAttributeValueOrder3.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK3);
		
		facetAttributeValueOrder4 = new FacetAttributeValueOrder();
		facetAttributeValueOrder4.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK4);
		
		facetAttributeValueOrder5 = new FacetAttributeValueOrder();
		facetAttributeValueOrder5.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK5);
		
		facetAttributeValueOrder6 = new FacetAttributeValueOrder();
		facetAttributeValueOrder6.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK6);
		
		facetAttributeValueOrder7 = new FacetAttributeValueOrder();
		facetAttributeValueOrder7.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK7);
		
		facetAttributeValueOrder8 = new FacetAttributeValueOrder();
		facetAttributeValueOrder8.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK8);
		
		facetAttributeValueOrder9 = new FacetAttributeValueOrder();
		facetAttributeValueOrder9.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK9);
		
		facetAttributeValueOrder10 = new FacetAttributeValueOrder();
		facetAttributeValueOrder10.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK10);
		
		facetAttributeValueOrder11 = new FacetAttributeValueOrder();
		facetAttributeValueOrder11.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK11);
		
		facetAttributeValueOrder12 = new FacetAttributeValueOrder();
		facetAttributeValueOrder12.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK12);
		
		facetAttributeValueOrder13 = new FacetAttributeValueOrder();
		facetAttributeValueOrder13.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK13);
		
		facetAttributeValueOrder14 = new FacetAttributeValueOrder();
		facetAttributeValueOrder14.setFacetAttributeValueOrderPK(facetAttributeValueOrderPK14);
		
		values1 = new ArrayList<FacetAttributeValueOrder>();
		values2 = new ArrayList<FacetAttributeValueOrder>();
		values3 = new ArrayList<FacetAttributeValueOrder>();
		
		values1.add(facetAttributeValueOrder1);
		values1.add(facetAttributeValueOrder2);
		values1.add(facetAttributeValueOrder3);
		values1.add(facetAttributeValueOrder4);
		values1.add(facetAttributeValueOrder5);
		values1.add(facetAttributeValueOrder6);
		values1.add(facetAttributeValueOrder7);
		values1.add(facetAttributeValueOrder8);
		values1.add(facetAttributeValueOrder9);
		
		values2.add(facetAttributeValueOrder10);
		values2.add(facetAttributeValueOrder11);
		values2.add(facetAttributeValueOrder12);
		values2.add(facetAttributeValueOrder13);
		
		values3.add(facetAttributeValueOrder14);
		
		facet1.setFacetAttributeOrder(values1);
		facet2.setFacetAttributeOrder(values2);
		facet3.setFacetAttributeOrder(values3);
		
		category1 = new Category();
		category1.setCategoryNodeId("cat1");
		
		// Adding 2 facets for the same category
		categoryFacet1 = new CategoryFacet();
		categoryFacet1.setCatgFacetId(1L);
		categoryFacet1.setFacet(facet1);
		categoryFacet1.setCategoryNode(category1);
		categoryFacet1.setDisplay(DisplayEnum.Y);
		categoryFacet1.setDisplayOrder(0L);
		categoryFacet1.setIsActive("Y");
		
		categoryFacet2 = new CategoryFacet();
		categoryFacet2.setCatgFacetId(2L);
		categoryFacet2.setFacet(facet2);
		categoryFacet2.setCategoryNode(category1);
		categoryFacet2.setDisplay(DisplayEnum.Y);
		categoryFacet2.setDisplayOrder(1L);	
		categoryFacet2.setIsActive("Y");
		
		categoryFacet3 = new CategoryFacet();
		categoryFacet3.setCatgFacetId(3L);
		categoryFacet3.setFacet(facet3);
		categoryFacet3.setCategoryNode(category1);
		categoryFacet3.setDisplay(DisplayEnum.Y);
		categoryFacet3.setDisplayOrder(1L);		
		categoryFacet3.setDepFacet(facet1);
		categoryFacet3.setIsActive("Y");
		
		categoryFacets = new ArrayList<CategoryFacet>();
		categoryFacets.add(categoryFacet1);
		categoryFacets.add(categoryFacet2);
		categoryFacets.add(categoryFacet3);
		
		categoryFacetWrapper1 = new CategoryFacetOrderWrapper();
		categoryFacetWrapper1.setCategoryFacetId(1L);
		categoryFacetWrapper1.setDisplayName("Brand");
		categoryFacetWrapper1.setSystemName("Sys Brand");
		categoryFacetWrapper1.setDisplayOrder(0L);
		categoryFacetWrapper1.setStatus("Approved");
		categoryFacetWrapper1.setValues("Samsung, Sony, Panasonic, Westinghouse, Texas Instruments, Apple, Hewlett Packard, US Robotics, Vent...");
		categoryFacetWrapper1.setCategoryNodeId("cat00000");
		
		categoryFacetWrapper2 = new CategoryFacetOrderWrapper();
		categoryFacetWrapper2.setCategoryFacetId(2L);
		categoryFacetWrapper2.setDisplayName("Screen Size");
		categoryFacetWrapper2.setSystemName("Sys Screen Size");
		categoryFacetWrapper2.setDisplayOrder(1L);
		categoryFacetWrapper2.setStatus("Draft");
		categoryFacetWrapper2.setValues("19\", 20\", 32\", 50\"");
		categoryFacetWrapper1.setCategoryNodeId("cat00000");
		
		categoryFacetWrapper3 = new CategoryFacetOrderWrapper();
		categoryFacetWrapper3.setCategoryFacetId(3L);
		categoryFacetWrapper3.setDisplayName("Other Brand");
		categoryFacetWrapper3.setSystemName("More brands");
		categoryFacetWrapper3.setDisplayOrder(1L);
		categoryFacetWrapper3.setStatus("Draft");
		categoryFacetWrapper3.setValues("Nakazaki");
		categoryFacetWrapper1.setCategoryNodeId("cat00000");
		
		categoryFacetWrappers = new ArrayList<CategoryFacetOrderWrapper>();
		
		categoryFacetWrappers.add(categoryFacetWrapper1);
		categoryFacetWrappers.add(categoryFacetWrapper2);
		categoryFacetWrappers.add(categoryFacetWrapper3);
	}

	/**
	 * @return the statusDeleted
	 */
	public Status getStatusDeleted() {
		return statusDeleted;
	}

	/**
	 * @param statusDeleted the statusDeleted to set
	 */
	public void setStatusDeleted(Status statusDeleted) {
		this.statusDeleted = statusDeleted;
	}

	/**
	 * @return the categoryFacetWrappers
	 */
	@SuppressWarnings("unchecked")
	public List<IWrapper> getFacetCategoryWrappers() {
		return (List<IWrapper>) (List<?>) categoryFacetWrappers;
	}

	public CategoryFacet getCategoryFacet1() {
		return categoryFacet1;
	}

	public void setCategoryFacet1(CategoryFacet categoryFacet1) {
		this.categoryFacet1 = categoryFacet1;
	}

	public CategoryFacet getCategoryFacet2() {
		return categoryFacet2;
	}

	public void setCategoryFacet2(CategoryFacet categoryFacet2) {
		this.categoryFacet2 = categoryFacet2;
	}

	public List<CategoryFacet> getCategoryFacets() {
		return categoryFacets;
	}

	public void setCategoryFacets(List<CategoryFacet> categoryFacets) {
		this.categoryFacets = categoryFacets;
	}

	/**
	 * @return the category1
	 */
	public Category getCategory1() {
		return category1;
	}

	/**
	 * @param category1 the category1 to set
	 */
	public void setCategoryNode1(Category category1) {
		this.category1 = category1;
	}

	/**
	 * @return the facet1
	 */
	public Facet getFacet1() {
		return facet1;
	}

	/**
	 * @param facet1 the facet1 to set
	 */
	public void setFacet1(Facet facet1) {
		this.facet1 = facet1;
	}

	/**
	 * @return the facet2
	 */
	public Facet getFacet2() {
		return facet2;
	}

	/**
	 * @param facet2 the facet2 to set
	 */
	public void setFacet2(Facet facet2) {
		this.facet2 = facet2;
	}

	/**
	 * @param categoryFacetWrappers the categoryFacetWrappers to set
	 */
	public void setCategoryFacetWrappers(List<CategoryFacetOrderWrapper> categoryFacetWrappers) {
		this.categoryFacetWrappers = categoryFacetWrappers;
	}

	/**
	 * @return the categoryFacetWrappers
	 */
	public List<CategoryFacetOrderWrapper> getCategoryFacetWrappers() {
		return categoryFacetWrappers;
	}
	
	
}
