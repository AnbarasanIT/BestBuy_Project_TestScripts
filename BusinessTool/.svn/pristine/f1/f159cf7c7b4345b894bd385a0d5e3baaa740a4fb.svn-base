package com.bestbuy.search.merchandising.common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.ISearchProfileService;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.BoostBlockProductWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Common method for create and update boost and block data
 */
public class BoostAndBlockWrapperConverter {

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private ICategoryNodeService categoryNodeService;

	@Autowired
	private ISearchProfileService searchProfileService;

	/**
	 * @param to set the userUtil 
	 */
	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	/**
	 * @param to set the categoryNodeService 
	 */
	public void setCategoryNodeService(ICategoryNodeService categoryNodeService) {
		this.categoryNodeService = categoryNodeService;
	}

	/**
	 * @param to set the searchProfileService 
	 */
	public void setSearchProfileService(ISearchProfileService searchProfileService) {
		this.searchProfileService = searchProfileService;
	}


	/**
	 * Method to convert facet wrapper to facet entity.
	 * @param facetWrapper
	 * @return
	 * @throws ServiceException
	 */
	public BoostAndBlock wrapperConverter(BoostAndBlockWrapper boostAndBlockWrapper, BoostAndBlock boostAndBlock) throws ServiceException,ParseException{

		if(boostAndBlockWrapper != null){
			if(boostAndBlockWrapper.getSearchTerm() != null && !boostAndBlockWrapper.getSearchTerm().isEmpty()){
				boostAndBlock.setTerm(boostAndBlockWrapper.getSearchTerm());
			}
			if(boostAndBlockWrapper.getCategoryId() != null && !boostAndBlockWrapper.getCategoryId().isEmpty()){
				Category categoryNode =  categoryNodeService.retrieveById(boostAndBlockWrapper.getCategoryId());
				boostAndBlock.setCategoryId(categoryNode);
			}
			if(boostAndBlockWrapper.getSearchProfileId() != null){
				SearchProfile searchProfile = searchProfileService.retrieveById(boostAndBlockWrapper.getSearchProfileId());
				boostAndBlock.setSearchProfileId(searchProfile);
			}
			//Set the created and modified date
			Date currentdate=new Date();
			//Set the created and modified user
			Users userId = userUtil.getUser();
			if(boostAndBlock.getCreatedBy() == null){
				boostAndBlock.setCreatedBy(userId);
				boostAndBlock.setCreatedDate(currentdate);
			}
			boostAndBlock.setUpdatedBy(userId);
			boostAndBlock.setUpdatedDate(currentdate);
		}
		return boostAndBlock;
	}

	/**
	 * Get the product data for Boost and Block
	 * @param productWrapper
	 * @return BoostAndBlockProduct list
	 */
	public List<BoostAndBlockProduct> getProducts(List<BoostBlockProductWrapper> productWrappers, BoostAndBlock boostAndBlock){
		List<BoostAndBlockProduct>  products= new ArrayList<BoostAndBlockProduct>();
		for(BoostBlockProductWrapper productWrapper:productWrappers){
			BoostAndBlockProduct product = getProduct(productWrapper,boostAndBlock, new BoostAndBlockProduct());
			products.add(product);
		}
		return products;
	}

	public BoostAndBlockProduct getProduct(BoostBlockProductWrapper productWrapper, BoostAndBlock boostAndBlock, BoostAndBlockProduct product){
		if(productWrapper.getProductName() != null){
			product.setProductName(productWrapper.getProductName());
		}
		if(productWrapper.getSkuId() != null){
			product.setSkuId(productWrapper.getSkuId());
		}
		if(productWrapper.getPosition() != null){
			product.setPosition(productWrapper.getPosition());
		}
		product.setBoostAndBlock(boostAndBlock);
		return product;
	}

	/**
	 * Method to update the Boost and Block Product data
	 * @param boostAndBlock
	 * @param boostBlockProductWrappers
	 * @return List of BoostAndBlockProduct
	 * @throws DataAcessException,ServiceException, ParseException
	 */
	public List<BoostAndBlockProduct> updateProducts(BoostAndBlock boostAndBlock, List<BoostBlockProductWrapper> boostBlockProductWrappers) throws DataAcessException, ServiceException, ParseException
	{
		List<BoostAndBlockProduct> boostAndBlockProducts = new ArrayList<BoostAndBlockProduct>();
		List<BoostAndBlockProduct> boostAndBlockProductsDB =boostAndBlock.getBoostAndBlockProducts();
		if(boostAndBlockProductsDB != null && boostAndBlockProductsDB.size() > 0){
			for(Iterator<BoostAndBlockProduct> iterator = boostAndBlockProductsDB.iterator(); iterator.hasNext(); ){
				boolean isExist = false;
				BoostAndBlockProduct boostAndBlockProductDB = iterator.next();
				for(BoostBlockProductWrapper boostBlockProductWrapper : boostBlockProductWrappers)
				{
					if(boostAndBlockProductDB.getId().equals(boostBlockProductWrapper.getProductId())){
						getProduct(boostBlockProductWrapper, boostAndBlock, boostAndBlockProductDB );
						boostBlockProductWrappers.remove(boostBlockProductWrapper);
						isExist = true;
						break;
					}
				}
				if(!isExist){
					iterator.remove();
				}
			}
			boostAndBlockProducts= boostAndBlockProductsDB;	
		}
		//Check if there is any new boost or block item during update to insert product entity data
		for(BoostBlockProductWrapper boostBlockProductWrapper : boostBlockProductWrappers)
		{
			BoostAndBlockProduct boostAndBlockProduct = getProduct(boostBlockProductWrapper, boostAndBlock, new BoostAndBlockProduct());
			boostAndBlockProducts.add(boostAndBlockProduct);
		}	
		return boostAndBlockProducts;
	}
}
