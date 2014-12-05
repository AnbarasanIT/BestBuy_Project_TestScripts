package com.bestbuy.search.merchandising.wrapper;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.BANNER_TEMPLATE_1HEADER;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.BANNER_TEMPLATE_HTML;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.common.ValidWrapper;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.ContextFacet;
import com.bestbuy.search.merchandising.domain.ContextKeyword;

/**
 *wrapper class created for banner functionality
 *@author Chanchal Kumari
 *@Date 20th Sep 2012.
 *@Modified By Kalaiselvi Jaganathan - Added functionality for loading data for edit popup
 */
@ValidWrapper
public class BannerWrapper extends BannerBaseWrapper implements IWrapper {
	public static boolean sortDesc = true;

	private int id;
	private Long bannerId;
	private String bannerType;
	
	@NotNull(message="Field.NotEmpty")
	private Long bannerPriority;
	private String modifiedBy;
	private Date modifiedDate;
	private Long statusId;
	private String status;
	private String bannerTemplate;
	private List<ContextWrapper> contexts;
	private List<KeyValueWrapper> actions;
	private List<BannerTemplateWrapper> templates;
	
	/**
	 * @return the templates
	 */
	public List<BannerTemplateWrapper> getTemplates() {
		return templates;
	}
	/**
	 * @param To set the templates
	 */
	public void setTemplates(List<BannerTemplateWrapper> templates) {
		this.templates = templates;
	}
	
	/**
	 *  bannerType to set
	 * @param bannerType 
	 */
	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}
	
	public String getBannerType() {
		return bannerType;
	}

	/**
	 * @return the bannerPriority
	 */
	public Long getBannerPriority() {
		return bannerPriority;
	}
	/**
	 * @param bannerPriority the bannerPriority to set
	 */
	public void setBannerPriority(Long bannerPriority) {
		this.bannerPriority = bannerPriority;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the stausId
	 */
	public Long getStatusId() {
		return statusId;
	}
	/**
	 * @param stausId the stausId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the modifiedDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the bannerId
	 */
	public Long getBannerId() {
		return bannerId;
	}
	/**
	 * @param bannerId the bannerId to set
	 */
	public void setBannerId(Long bannerId) {
		this.bannerId = bannerId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the bannerTemplate
	 */
	public String getBannerTemplate() {
		return bannerTemplate;
	}
	/**
	 * @param bannerTemplate the bannerTemplate to set
	 */
	public void setBannerTemplate(String bannerTemplate) {
		this.bannerTemplate = bannerTemplate;
	}
	/**
	 * @return the actions
	 */
	public List<KeyValueWrapper> getActions() {
		return actions;
	}
	/**
	 * @param actions the actions to set
	 */
	public void setActions(List<KeyValueWrapper> actions) {
		this.actions = actions;
	}

	/**
	 * @return the contexts
	 */
	public List<ContextWrapper> getContexts() {
		return contexts;
	}
	/**
	 * @param contexts the contexts to set
	 */
	public void setContexts(List<ContextWrapper> contexts) {
		this.contexts = contexts;
	}
	/**
	 * Overrides the compareTo method from the Comparable interface, Compares this object to the specified BannerWrapper
	 * @param wrapper An IWrapper object that is a valid BannerWrapper
	 * @author chanchal.kumari
	 */
	@Override
	public int compareTo(IWrapper wrapper) {		
		BannerWrapper bannerWrapper = (BannerWrapper) wrapper;
		if(modifiedDate != null){
			int comparison = modifiedDate.compareTo(bannerWrapper.modifiedDate);  // by default compare to gives an asc ordering
			return sortDesc ? (-1 * comparison) : comparison;
		}else{
			return -1;
		}
	}
	/**
	 * Method to set the row ID's
	 * @param bannerWrappers
	 * @return List<IWrapper>
	 * @author chanchal.kumari
	 */
	public static List<IWrapper> sortRowIds(List<IWrapper> bannerWrappers) {
		int i = 0;
		for(IWrapper wrapper : bannerWrappers){
			BannerWrapper bannerWrapper = (BannerWrapper) wrapper;
			bannerWrapper.setId(i);
			bannerWrappers.set(i,bannerWrapper);			
			i++;
		}
		return bannerWrappers;
	}

	/**
	 * Retreieves a list of IWrapper object from the list of banner, buy extracting all the information inside those entity objects and
	 * storing it in a UI Json serializing friendly object, the BannerWrapper
	 * @param Banners The list of Banner objects to be parsed
	 * @return A list of IWrapper with the extracted information
	 * 
	 */
	public static List<IWrapper> fetchBanners(List<Banner> banners) {
		List<IWrapper> bannerWrappers = new ArrayList<IWrapper>(banners.size());
		int i = 0;
		for(Banner  banner : banners){
			BannerWrapper bannerWrapper = loadBannerWrapper(banner);
			bannerWrapper.setId(i);
			bannerWrappers.add(bannerWrapper);
			i++;
		}
		return bannerWrappers;
	}
	
	/**
	 * Method to get the actions
	 * @param actions
	 * @return List<KeyValueWrapper>
	 */
	public static List<KeyValueWrapper> fetchActions(Set<String> actions) {
		List<KeyValueWrapper> dropdowns = new ArrayList<KeyValueWrapper>();

		for(String action : actions) {
			KeyValueWrapper dropdown = new KeyValueWrapper();
			dropdown.setKey(action);
			dropdown.setValue(action);
			dropdowns.add(dropdown);
		}
		return dropdowns;
	}

	/**
	 * Method to retrieve data for a specific banner and set the banner wrapper
	 * @param banner
	 * @return BannerWrapper
	 * @throws ServiceException
	 */
	public static BannerWrapper loadBannerWrapper(Banner banner){ 
		BannerWrapper bannerWrapper = new BannerWrapper();
		if(banner != null){
			List<Context> contexts = banner.getContexts();
			bannerWrapper.setBannerId(banner.getBannerId());
			bannerWrapper.setBannerName(banner.getBannerName());
			bannerWrapper.setBannerPriority(banner.getBannerPriority());
			bannerWrapper.setBannerType(banner.getBannerType());
			bannerWrapper.setBannerTemplate(banner.getBannerTemplate());
			bannerWrapper.setModifiedDate(banner.getUpdatedDate());
			if(banner.getUpdatedBy() != null) {
				bannerWrapper.setModifiedBy(banner.getUpdatedBy().getFirstName()+ "." +banner.getUpdatedBy().getLastName());
			}
			bannerWrapper.setStartDate(banner.getStartDate());
			if(banner.getEndDate() != null){
				bannerWrapper.setEndDate(banner.getEndDate());
			}
			if(banner.getStatus() != null){
				bannerWrapper.setStatusId(banner.getStatus().getStatusId());
				bannerWrapper.setStatus(banner.getStatus().getStatus());
			}
			bannerWrapper.setContexts(getContextWrapper(contexts));
			
			//Banner Templates
			if(banner.getBannerTemplates() != null && banner.getBannerTemplates().size() > 0){
				if(banner.getBannerTemplate().equalsIgnoreCase(BANNER_TEMPLATE_HTML)){
					if( banner.getBannerTemplates().get(0) != null){
						BannerTemplate bannerTemplate = banner.getBannerTemplates().get(0);
						bannerWrapper.setDocumentId(Long.parseLong(bannerTemplate.getSku()));
					}
				}else{
					bannerWrapper.setTemplates(getBannerTemplateWrapper(banner.getBannerTemplates(),banner.getBannerTemplate()));
				}
			}
		}
		return bannerWrapper;
	}

	/**
	 * Get the context data for the edit pop-up
	 * @param contexts
	 * @return List<ContextWrapper> 
	 * @throws ServiceException
	 */
	public static List<ContextWrapper> getContextWrapper(List<Context> contexts){
		List<ContextWrapper> contextWrappers = new ArrayList<ContextWrapper>();
		if(contexts != null){
			ContextWrapper contextWrapper = null;
			//Loop through the list of contexts for the banner and set the contextwrapper
			for(Context context : contexts){
				contextWrapper= new ContextWrapper();
				contextWrapper.setContextId(context.getContextId());
				if(context.getCategoryNode() != null){
					contextWrapper.setContextPathId(context.getCategoryNode().getCategoryNodeId());
					contextWrapper.setCategoryPath(context.getCategoryNode().getCategoryPath());
				}
				contextWrapper.setValid(context.getIsActive());
				contextWrapper.setInherit(context.getInheritable());
				
				if(context.getSearchProfile() != null){
					contextWrapper.setSearchProfileType(context.getSearchProfile().getProfileName());
					contextWrapper.setSearchProfileId(context.getSearchProfile().getSearchProfileId());
				}
				StringBuffer facetValues=new StringBuffer();
				//Looping through ContextFacet list and retrieve the facet value also add comma between two facet value
				if(context.getContextFacet() != null && context.getContextFacet().size() > 0){
					List<ContextFacetWrapper> contextFacetWrappers = new ArrayList<ContextFacetWrapper>();
					for(ContextFacet ctxFacet:context.getContextFacet()){
						ContextFacetWrapper facetWrapper = new ContextFacetWrapper();
						if(ctxFacet.getContextFacetId() != null && ctxFacet.getContextFacetId().getFacet() != null){
							facetWrapper.setFacetId(ctxFacet.getContextFacetId().getFacet().getFacetId());
						}
						if(ctxFacet.getContextFacetId() != null && ctxFacet.getContextFacetId().getAttributeValue() != null){
							facetWrapper.setAttributeValueId(ctxFacet.getContextFacetId().getAttributeValue().getAttributeValueId());
						}
						contextFacetWrappers.add(facetWrapper);
					}
					contextWrapper.setContextFacetWrapper(contextFacetWrappers);
				}
				
				String facetValuesString = (facetValues.toString()).trim(); //Remove the extra spaces in the string

				//Remove the trailing comma
				facetValuesString = facetValuesString.endsWith(",") ? facetValuesString.substring(0, facetValuesString.length() - 1) : facetValuesString;
				contextWrapper.setFacetValues(facetValuesString);

				StringBuffer keywordValues=new StringBuffer();

				//Looping through keyword list and retrieve the keyword also add comma between two keyword
				for (ContextKeyword keyword : context.getContextKeyword()){	
					if(keyword.getId() != null){
						keywordValues.append(keyword.getId().getKeyword());
						keywordValues.append(", ");
					}
				}

				String keywordString = (keywordValues.toString()).trim(); //Remove the extra spaces in the string

				//Remove the trailing comma
				keywordString = keywordString.endsWith(",") ? keywordString.substring(0, keywordString.length() - 1) : keywordString;
				contextWrapper.setKeywords(keywordString);
				contextWrappers.add(contextWrapper);
			}
		}
		return contextWrappers;
	}
	
	
	/**
	 * Get the 3Header_3SKU template data for edit pop-up
	 * @param bannerSkuTemplates
	 * @return bannerTemplateWrappers
	 */
	public static List<BannerTemplateWrapper> getBannerTemplateWrapper(List<BannerTemplate> bannerTemplates,String bannerType){
		List<BannerTemplateWrapper> bannerTemplateWrappers = new ArrayList<BannerTemplateWrapper>();
		
		if (bannerType.equalsIgnoreCase(BANNER_TEMPLATE_1HEADER)){
			List<BannerSkuWrapper> bannerSkuWrappers= new  ArrayList<BannerSkuWrapper>();
			BannerTemplateWrapper templateWrapper = new BannerTemplateWrapper();
			for(BannerTemplate bannerTemplate:bannerTemplates){
				BannerSkuWrapper bannerSkuWrapper = new BannerSkuWrapper();
				bannerSkuWrapper.setSkuIds(bannerTemplate.getSku());
				bannerSkuWrapper.setSkuPosition(bannerTemplate.getSkuPosition());
				templateWrapper.setBannerHeader(bannerTemplate.getTemplateHeader());
				bannerSkuWrappers.add(bannerSkuWrapper);
			}
			templateWrapper.setBannerSlotSkuLists(bannerSkuWrappers);
			bannerTemplateWrappers.add(templateWrapper);
		}else{
			for(BannerTemplate bannerTemplate:bannerTemplates){
				List<BannerSkuWrapper> bannerSkuWrappers= new  ArrayList<BannerSkuWrapper>();
				BannerTemplateWrapper templateWrapper = new BannerTemplateWrapper();
				BannerSkuWrapper bannerSkuWrapper = new BannerSkuWrapper();
				bannerSkuWrapper.setSkuIds(bannerTemplate.getSku());
				bannerSkuWrapper.setSkuPosition(bannerTemplate.getSkuPosition());
				templateWrapper.setBannerHeader(bannerTemplate.getTemplateHeader());
				bannerSkuWrappers.add(bannerSkuWrapper);
				templateWrapper.setBannerSlotSkuLists(bannerSkuWrappers);
				bannerTemplateWrappers.add(templateWrapper);
			}
		}
		return bannerTemplateWrappers;
	}
	
	/**
	 * Method to set the sort the rows in the response object.
	 * @param merchandisingBaseResponse
	 * @param wrappers
	 * @author Chanchal.Kumari
	 */
	public static void setAndSortRows(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse,List<IWrapper> wrappers){
		merchandisingBaseResponse.setRows(wrappers);
		merchandisingBaseResponse.sortRows();
		merchandisingBaseResponse.setRows(BannerWrapper.sortRowIds(merchandisingBaseResponse.getRows()));  // jqgrid needs sorted ids but they got unsorted when sorted by date
		
	}
}
