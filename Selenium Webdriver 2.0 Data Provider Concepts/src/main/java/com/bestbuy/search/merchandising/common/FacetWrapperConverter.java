package com.bestbuy.search.merchandising.common;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrderPK;
import com.bestbuy.search.merchandising.domain.FacetsDisplayOrder;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.domain.common.DisplayModeEnum;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.service.IAttrValSortTypeService;
import com.bestbuy.search.merchandising.service.IAttributeValueService;
import com.bestbuy.search.merchandising.service.ICategoryFacetService;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.IFacetService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.CategoryWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Map the wrapper to entity
 */
public class FacetWrapperConverter {

	@Autowired
	private UserUtil userUtil;

	@Autowired
	IAttrValSortTypeService sortTypeService;

	@Autowired
	IAttributeValueService attrValueService;

	@Autowired
	private ICategoryNodeService categoryNodeService;

	@Autowired
	private IFacetService facetService;

	@Autowired
	private ICategoryFacetService categoryFacetService;
	
	@Autowired
	private IStatusService statusService;

	/**
	 * @param categoryFacetService the categoryFacetService to set
	 */
	public void setCategoryFacetService(ICategoryFacetService categoryFacetService) {
		this.categoryFacetService = categoryFacetService;
	}	

	/**
	 * @param userUtil the userUtil to set
	 */
	public void setAuthenticationAuthorizationTool(
			UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	/**
	 * @param To set attrValueService
	 */
	public void setAttrValueService(IAttributeValueService attrValueService) {
		this.attrValueService = attrValueService;
	}
	
	/**
	 * Method to set status service
	 * 
	 * @param IStatusService statusService
	 */
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}

	/**
	 * @param To set categoryNodeService
	 */
	public void setCategoryNodeService(ICategoryNodeService categoryNodeService) {
		this.categoryNodeService = categoryNodeService;
	}

	/**
	 * @param to set the facetService 
	 */
	public void setFacetService(IFacetService facetService) {
		this.facetService = facetService;
	}

	/**
	 * Method to convert banner wrapper to banner entity.
	 * @param bannerwrapper
	 * @return
	 * @throws ServiceException
	 */
	public Facet wrapperConverter(FacetWrapper facetWrapper,Facet facet) throws ServiceException,ParseException{

		if(facetWrapper != null){

			if(facetWrapper.getSystemName() != null && !facetWrapper.getSystemName().trim().isEmpty()){
				facet.setSystemName(facetWrapper.getSystemName().trim());
			}

			if(facetWrapper.getDisplayName() != null && !facetWrapper.getDisplayName().trim().isEmpty()){
				facet.setDisplayName(facetWrapper.getDisplayName().trim());
			}

			//Set the facet display - hidden(N) or displayed(Y)
			if(facetWrapper.getFacetDisplay() != null && !facetWrapper.getFacetDisplay().trim().isEmpty()){
				facet.setDisplay(getDisplayMode(facetWrapper.getFacetDisplay()));
			}
			if(facetWrapper.getDisplayRecurrence() != null && !facetWrapper.getDisplayRecurrence() .trim().isEmpty()){
				facet.setDisplayRecurrence(getDisplayMode(facetWrapper.getDisplayRecurrence() ));
			}
			if(facetWrapper.getDisplayFacetRemoveLink() != null && !facetWrapper.getDisplayFacetRemoveLink().trim().isEmpty()){
				facet.setDisplayFacetRemoveLink(getDisplayMode(facetWrapper.getDisplayFacetRemoveLink()));
			}
			if(facetWrapper.getDisplayMobileFacet() != null && !facetWrapper.getDisplayMobileFacet().trim().isEmpty()){
				facet.setDisplayMobileFacet(getDisplayMode(facetWrapper.getDisplayMobileFacet()));
			}
			if(facetWrapper.getDisplayMobileFacetRemoveLink() != null && !facetWrapper.getDisplayMobileFacetRemoveLink().trim().isEmpty()){
				facet.setDisplayMobileFacetRemoveLink(getDisplayMode(facetWrapper.getDisplayMobileFacetRemoveLink()));
			}

			if(facetWrapper.getMinValue() != null){
				facet.setMinAttrValue(facetWrapper.getMinValue());
			}

			if(facetWrapper.getMaxValue() != null){
				facet.setMaxAttrValue(facetWrapper.getMaxValue());
			}	

			if(facetWrapper.getStartDate()!=null){
				facet.setStartDate(facetWrapper.getStartDate());
			}

			if(facetWrapper.getEndDate()!=null){
				facet.setEndDate(facetWrapper.getEndDate());
			}

			//Set the display mode - SEARCH, BROWSE, SEARCH&BROWSE
			if(facetWrapper.getDisplayMode() != null && !facetWrapper.getDisplayMode().trim().isEmpty()){
				String facetDisplayMode = facetWrapper.getDisplayMode().trim();
				if(facetDisplayMode.equalsIgnoreCase(DisplayModeEnum.SEARCH.toString()))
				{
					facet.setDisplayMode(DisplayModeEnum.SEARCH);
				}else if(facetDisplayMode.equalsIgnoreCase(DisplayModeEnum.BROWSE.toString()))
				{
					facet.setDisplayMode(DisplayModeEnum.BROWSE);
				}else if(facetDisplayMode.equalsIgnoreCase(DisplayModeEnum.SEARCH_BROWSE.toString()))
				{
					facet.setDisplayMode(DisplayModeEnum.SEARCH_BROWSE);
				}
			}

			//Set the created and modified date
			Date currentdate=new Date();
			//Set the created and modified user
			Users userId = userUtil.getUser();
			if(facet.getCreatedBy() == null){
				facet.setCreatedBy(userId);
				facet.setCreatedDate(currentdate);
			}
			facet.setUpdatedBy(userId);
			facet.setUpdatedDate(currentdate);

			if(facetWrapper.getGlossaryItem() != null){
				facet.setGlossaryItem(facetWrapper.getGlossaryItem());
			}

			if(facetWrapper.getSortType() != null && !facetWrapper.getSortType().trim().isEmpty()){
				facet.setAttrValSortType(facetWrapper.getSortType().trim());
			}
		}
		return facet;
	}

	/**
	 * returns the list of FacetAttributeValueOrder data from the UI wrapper
	 * @param attributeValueWrapper
	 * @param facet
	 * @throws ServiceException
	 */
	public List<FacetAttributeValueOrder> getAttributeValues(List<AttributeValueWrapper> attributeValueWrapper,Facet facet) throws ServiceException{
		List<FacetAttributeValueOrder> attrValueOrders = new ArrayList<FacetAttributeValueOrder>();
		for(AttributeValueWrapper attrValWrapper:attributeValueWrapper){
			FacetAttributeValueOrder valueOrder = new FacetAttributeValueOrder();
			valueOrder = getAttributeValue(attrValWrapper, facet, valueOrder);
			attrValueOrders.add(valueOrder);
		}
		return attrValueOrders;
	}

	/**
	 * returns the FacetAttributeValueOrder data from the UI wrapper
	 * @param attrValWrapper
	 * @param facet
	 * @param valueOrder
	 * @return FacetAttributeValueOrder
	 * @throws ServiceException
	 */
	public FacetAttributeValueOrder getAttributeValue(AttributeValueWrapper attrValWrapper,Facet facet,FacetAttributeValueOrder valueOrder) throws ServiceException
	{
		if(valueOrder == null){
			valueOrder = new FacetAttributeValueOrder();
		}
		FacetAttributeValueOrderPK valueOrderPK = new FacetAttributeValueOrderPK();
		valueOrder.setFacetAttributeValueOrderPK(valueOrderPK);
		if(attrValWrapper.getAttributeValueId() != null ){
			AttributeValue attrValue = attrValueService.retrieveById(attrValWrapper.getAttributeValueId());
			valueOrderPK.setAttributeValue(attrValue);
		}
		valueOrder.setDisplayOrder(attrValWrapper.getSortOrder());
		if(attrValWrapper.getAttrValuedisplay() != null && !attrValWrapper.getAttrValuedisplay().isEmpty()){
			valueOrder.setFacetDoNotInclude(getDisplayMode(attrValWrapper.getAttrValuedisplay()));
		}
		valueOrderPK.setFacet(facet);
		return valueOrder;
	}

	/**
	 * 
	 * @param category
	 */
	public List<CategoryFacet> getCategoryFacets(List<CategoryWrapper> categoryWrappers,Facet facet) throws ServiceException{
		List<CategoryFacet> categoryFacets = new ArrayList<CategoryFacet>();
		for(CategoryWrapper categoryWrapper:categoryWrappers){
			CategoryFacet categoryFacet = new CategoryFacet();
			categoryFacet=getCategoryFacet(categoryWrapper,facet,categoryFacet);
			categoryFacets.add(categoryFacet);
		}

		categoryFacetService.setDisplayOrder(categoryFacets);

		return categoryFacets;
	}

	/**
	 * 
	 * @param categoryWrapper
	 * @param facet
	 * @param categoryFacet
	 * @return CategoryFacet
	 * @throws ServiceException
	 */
	public CategoryFacet getCategoryFacet(CategoryWrapper categoryWrapper,Facet facet,CategoryFacet categoryFacet) throws ServiceException{
		DisplayEnum displayContextEnum = null;
		Category categoryNode =  null;
		DisplayEnum applyToSubcategories = null;
		if(categoryWrapper.getCategoryId() != null && !categoryWrapper.getDisplayContext().trim().isEmpty()){
			categoryNode = categoryNodeService.retrieveById(categoryWrapper.getCategoryId());
			categoryFacet.setCategoryNode(categoryNode);
		}
		if(categoryWrapper.getDisplayContext() != null && !categoryWrapper.getDisplayContext().trim().isEmpty()){
			displayContextEnum = getDisplayMode(categoryWrapper.getDisplayContext().trim());
			categoryFacet.setDisplay(displayContextEnum);
		}
		if(categoryWrapper.getDisplayDepFacet() != null && !categoryWrapper.getDisplayDepFacet().trim().isEmpty()){
			categoryFacet.setDepFacetDisplay(getDisplayMode(categoryWrapper.getDisplayDepFacet().trim()));
		}
		
		if(StringUtils.isNotEmpty(categoryWrapper.getDisplayDepFacet()) && categoryWrapper.getDisplayDepFacet().equalsIgnoreCase("N")){
			categoryFacet.setAttributeValue(null);
			categoryFacet.setDepFacet(null);
		}
		else{
			if(categoryWrapper.getFacetAttributeValueId() != null ){
				AttributeValue attrValue = attrValueService.retrieveById(categoryWrapper.getFacetAttributeValueId());
				categoryFacet.setAttributeValue(attrValue);
			}
			if(categoryWrapper.getDepFacetId() != null){
				Facet depFacet =  facetService.retrieveById(categoryWrapper.getDepFacetId());
				categoryFacet.setDepFacet(depFacet);
			}
		}
		if(categoryWrapper.getApplySubCategory() != null && !categoryWrapper.getApplySubCategory().trim().isEmpty()){
			applyToSubcategories = getDisplayMode(categoryWrapper.getApplySubCategory());
			categoryFacet.setApplySubCategory(applyToSubcategories);
			if (applyToSubcategories.equals(DisplayEnum.N)) {
				if (categoryNode != null) {
					removeFacetDisplayOrdersForChildPaths(categoryNode.getCategoryPath(), facet);
				}
			}
		}

		if (displayContextEnum != null) {
			if (displayContextEnum.equals(DisplayEnum.Y) &&
					categoryWrapper.getDepFacetId() == null) {
				addFacetDisplayOrders(facet);
			} else {
				removeFacetDisplayOrders(facet);
			}
		}
		categoryFacet.setFacet(facet);
		categoryFacet.setIsActive("Y");

		return categoryFacet;
	}

	/**
	 * Method that returns the DisplayEnum depending on the
	 * 'Y'/'N' values received from UI wrapper
	 * @param mode
	 * @return
	 */
	private DisplayEnum getDisplayMode(String mode){
		if(mode.equalsIgnoreCase(DisplayEnum.Y.toString()))
		{ 
			return DisplayEnum.Y;
		}else{
			return DisplayEnum.N;
		}
	}

	/**
	 * Method to set the facet attribute value orders to the facet
	 * @param facet
	 * @param facetWrapper
	 * @return List<FacetAttributeValueOrder>
	 * @throws DataAcessException
	 * @throws ServiceException
	 * @throws ParseException
	 */
	public void updateFacetValueOrders(Facet facet,List<AttributeValueWrapper> attributeValueWrappers) throws DataAcessException, ServiceException, ParseException
	{
		
		List<FacetAttributeValueOrder> facetAttributeValueOrdersDB=facet.getFacetAttributeOrder();
		if(facetAttributeValueOrdersDB != null && facetAttributeValueOrdersDB.size() > 0){
			//Compare the wrapper facetAttributeValueorder info with the on in DB
			for(Iterator<FacetAttributeValueOrder> iterator = facetAttributeValueOrdersDB.iterator(); iterator.hasNext(); ){
				boolean isExist = false;
				FacetAttributeValueOrder facetAttributeValueOrderDB = iterator.next();
				for(AttributeValueWrapper attributeValueWrapper : attributeValueWrappers)
				{
					if(facetAttributeValueOrderDB.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValueId().equals(attributeValueWrapper.getAttributeValueId())){
						getAttributeValue(attributeValueWrapper, facet, facetAttributeValueOrderDB);
						attributeValueWrappers.remove(attributeValueWrapper);
						isExist = true;
						break;
					}
				}
				if(!isExist){
					iterator.remove();
				}
			}
			
		}
		//Check if there is any new facet attribute value during update then to insert attribute valu entity data
		for(AttributeValueWrapper attributeValueWrapper : attributeValueWrappers)
		{
			FacetAttributeValueOrder attributeValueOrder = getAttributeValue(attributeValueWrapper, facet, new FacetAttributeValueOrder());
			facetAttributeValueOrdersDB.add(attributeValueOrder);
		}	
		
	}

	public void updateCategoryFacets(List<CategoryWrapper> categoryWrappers,
			Facet facet) throws ServiceException {
		// Remove facet display orders for the removed category paths
		removeFacetDisplayOrdersForPath(categoryWrappers, facet);
		
		List<CategoryFacet> categoryFacetsDB = facet.getCategoryFacet();
		if (categoryFacetsDB != null && categoryFacetsDB.size() > 0) {
			// Compare the wrapper facetAttributeValueorder info with the on in
			// DB
			for (Iterator<CategoryFacet> iterator = categoryFacetsDB.iterator(); iterator
					.hasNext();) {
				boolean isExist = false;
				CategoryFacet categoryFacetDB = iterator.next();
				for (CategoryWrapper categoryWrapper : categoryWrappers) {
					if (categoryFacetDB.getCategoryNode().getCategoryNodeId()
							.equals(categoryWrapper.getCategoryId())) {
						getCategoryFacet(categoryWrapper, facet,
								categoryFacetDB);
						categoryWrappers.remove(categoryWrapper);
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					iterator.remove();
				}
			}

		}

		for (CategoryWrapper categoryWrapper : categoryWrappers) {
			CategoryFacet categoryFacet = getCategoryFacet(categoryWrapper,
					facet, new CategoryFacet());
			categoryFacetsDB.add(categoryFacet);
		}
	}
	
	/**
	 * Remove facet display orders for the category paths
	 * 
	 * @param categoryWrappers
	 * @param facet
	 */
	private void removeFacetDisplayOrdersForPath(List<CategoryWrapper> categoryWrappers, Facet facet) throws ServiceException {
		Status deleteStatus = null;
		if (categoryWrappers != null && categoryWrappers.size() > 0 &&
				facet.getFacetDisplayOrder() != null && facet.getFacetDisplayOrder().size() > 0) {
			for (FacetsDisplayOrder facetDisplayOrder : facet.getFacetDisplayOrder()) {
				boolean exists = false;
				for (CategoryWrapper categoryWrapper : categoryWrappers) {
					if (facetDisplayOrder.getCategory().getCategoryNodeId().equals(categoryWrapper.getCategoryId())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					if (deleteStatus == null) {
						try {
							deleteStatus = statusService.retrieveById(DELETE_STATUS);
						} catch (ServiceException e) {
							throw new ServiceException(e);
						}
					}
					if (deleteStatus != null) {
						facetDisplayOrder.setStatus(deleteStatus);
					}
				}
			}
		}
	}
	
	/**
	 * Remove facet display orders for dependent facet
	 * 
	 * @param categoryWrappers
	 * @param facet
	 */
	private void removeFacetDisplayOrders(Facet facet) throws ServiceException {
		Status deleteStatus = null;
		if (facet.getFacetDisplayOrder() != null && facet.getFacetDisplayOrder().size() > 0) {
			for (FacetsDisplayOrder facetDisplayOrder : facet.getFacetDisplayOrder()) {
				if (deleteStatus == null) {
					try {
						deleteStatus = statusService.retrieveById(DELETE_STATUS);
					} catch (ServiceException e) {
						throw new ServiceException(e);
					}
				}
				if (deleteStatus != null) {
					facetDisplayOrder.setStatus(deleteStatus);
				}
			}
		}
	}
	
	/**
	 * Remove facet display orders for dependent facet
	 * 
	 * @param categoryWrappers
	 * @param facet
	 */
	private void addFacetDisplayOrders(Facet facet) throws ServiceException {
		Status approveStatus = null;
		if (facet.getCategoryFacet() != null && facet.getCategoryFacet().size() > 0) {
			for (CategoryFacet categoryFacet : facet.getCategoryFacet()) {
				if (categoryFacet.getCategoryNode().getFacetDisplayOrder() != null &&
						categoryFacet.getCategoryNode().getFacetDisplayOrder().size() > 0) {
					Long maxValue = 0L;
					FacetsDisplayOrder facetDisplayOrder = null;
					boolean hasValidDisplayOrder = false;
					for (FacetsDisplayOrder fdo : categoryFacet.getCategoryNode().getFacetDisplayOrder()) {
						if (fdo.getFacet().getFacetId().longValue() == facet.getFacetId().longValue() 
								&& fdo.getStatus().getStatusId().longValue() == APPROVE_STATUS.longValue()) {
							hasValidDisplayOrder = true;
						}
						if (fdo.getFacet().getFacetId().longValue() == facet.getFacetId().longValue() 
								&& fdo.getStatus().getStatusId().longValue() == DELETE_STATUS.longValue()) {
							facetDisplayOrder = fdo;
						}
					}
					if (!hasValidDisplayOrder) {
						if (facetDisplayOrder == null) {
							facetDisplayOrder = new FacetsDisplayOrder();
							facetDisplayOrder.setCategory(categoryFacet.getCategoryNode());
							facetDisplayOrder.setFacet(facet);
						}
						if (approveStatus == null) {
							try {
								approveStatus = statusService.retrieveById(APPROVE_STATUS);
							} catch (ServiceException e) {
								throw new ServiceException(e);
							}
						}
						facetDisplayOrder.setStatus(approveStatus);
						if (categoryFacet.getCategoryNode().getFacetDisplayOrder() != null && 
								categoryFacet.getCategoryNode().getFacetDisplayOrder().size() > 0) {
							for (FacetsDisplayOrder fdo : categoryFacet.getCategoryNode().getFacetDisplayOrder()) {
								if (fdo.getStatus().getStatusId().longValue() == APPROVE_STATUS.longValue() &&
										!(fdo.getFacet().getFacetId().longValue() == facet.getFacetId().longValue()) &&
										fdo.getDisplayOrder() > maxValue) {
									maxValue = fdo.getDisplayOrder();
								}
							}
							facetDisplayOrder.setDisplayOrder(maxValue.longValue() + 1);
						}
						facet.getFacetDisplayOrder().add(facetDisplayOrder);
						categoryFacet.getCategoryNode().getFacetDisplayOrder().add(facetDisplayOrder);
					}
				}
			}
		}
	}
	
	/**
	 * Method to remove facet display orders for child paths
	 * 
	 * @param categoryPath
	 * @param facet
	 * @throws ServiceException
	 */
	private void removeFacetDisplayOrdersForChildPaths(String categoryPath, Facet facet) throws ServiceException {
		List<Long> displayOrderIds = null;
		Status deleteStatus = null;
		try {
			displayOrderIds = facetService.getDisplayOrderIdsForChildPaths(categoryPath);
			if (displayOrderIds != null && displayOrderIds.size() > 0 &&
					facet.getFacetDisplayOrder() != null && facet.getFacetDisplayOrder().size() > 0) {
				for (FacetsDisplayOrder fdo : facet.getFacetDisplayOrder()) {
					if (displayOrderIds.contains(fdo.getId())) {
						if (deleteStatus == null) {
							try {
								deleteStatus = statusService.retrieveById(DELETE_STATUS);
							} catch (ServiceException e) {
								throw new ServiceException(e);
							}
						}
						if (deleteStatus != null) {
							fdo.setStatus(deleteStatus);
						}
					}
				}
			}
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}
}
