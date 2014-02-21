package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;

/**
 * @author Kalaiselvi Jaganathan
 * JSON Structure for BoostAndBlockWrapper
 * Added method for getBoostBlock()
 */
public class BoostAndBlockWrapper implements IWrapper{

	public static boolean sortDesc = true;

	private int id;
	private Long boostBlockId;
	private String searchTerm;
	private String categoryId;
	private String categoryPath;
	private String searchProfileName;
	private Long searchProfileId;
	private Date startDate;
	private Date endDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Long statusId;
	private String status;
	private String createdBy;
	private Date createdDate;
	private List<BoostBlockProductWrapper> boostProduct;
	private List<BoostBlockProductWrapper> blockProduct;

	private List<KeyValueWrapper> actions;

	/**
	 * @return the startDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param to set the id 
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the boostBlockId
	 */
	public Long getBoostBlockId() {
		return boostBlockId;
	}
	/**
	 * @param to set the boostBlockId 
	 */
	public void setBoostBlockId(Long boostBlockId) {
		this.boostBlockId = boostBlockId;
	}
	/**
	 * @return the searchTerm
	 */
	public String getSearchTerm() {
		return searchTerm;
	}
	/**
	 * @param to set the searchTerm 
	 */
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param to set the categoryId 
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the searchProfileName
	 */
	public String getSearchProfileName() {
		return searchProfileName;
	}
	/**
	 * @param to set the searchProfileName 
	 */
	public void setSearchProfileName(String searchProfileName) {
		this.searchProfileName = searchProfileName;
	}
	/**
	 * @return the searchProfileId
	 */
	public Long getSearchProfileId() {
		return searchProfileId;
	}
	/**
	 * @param to set the searchProfileId 
	 */
	public void setSearchProfileId(Long searchProfileId) {
		this.searchProfileId = searchProfileId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param to set the status 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param to set the createdBy 
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param to set the createdDate 
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the categoryPath
	 */
	public String getCategoryPath() {
		return categoryPath;
	}
	/**
	 * @param to set the categoryPath 
	 */
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	/**
	 * @return the boostProduct
	 */
	public List<BoostBlockProductWrapper> getBoostProduct() {
		return boostProduct;
	}
	/**
	 * @param to set the boostProduct 
	 */
	public void setBoostProduct(List<BoostBlockProductWrapper> boostProduct) {
		this.boostProduct = boostProduct;
	}
	/**
	 * @return the blockProduct
	 */
	public List<BoostBlockProductWrapper> getBlockProduct() {
		return blockProduct;
	}
	/**
	 * @param to set the blockProduct 
	 */
	public void setBlockProduct(List<BoostBlockProductWrapper> blockProduct) {
		this.blockProduct = blockProduct;
	}

	public List<KeyValueWrapper> getActions() {
		return actions;
	}
	public void setActions(List<KeyValueWrapper> actions) {
		this.actions = actions;
	}
	/**
	 * Overrides the compareTo method from the Comparable interface, Compares this object to the specified Boost&Block
	 * @param wrapper An IWrapper object that is a valid Boost&BlockWrapper
	 * @author chanchal.kumari
	 */
	@Override
	public int compareTo(IWrapper wrapper) {		
		BoostAndBlockWrapper boostAndBlockWrapper = (BoostAndBlockWrapper) wrapper;
		if(modifiedDate != null){
			int comparison = modifiedDate.compareTo(boostAndBlockWrapper.modifiedDate);
			return sortDesc ? (-1 * comparison) : comparison;
		}else{
			return -1;
		}
	}

	public static List<IWrapper> sortRowIds(List<IWrapper> boostAndBlockWrappers) {
		int i = 0;

		for(IWrapper wrapper : boostAndBlockWrappers){
			BoostAndBlockWrapper boostAndBlockWrapper = (BoostAndBlockWrapper) wrapper;
			boostAndBlockWrapper.setId(i);
			boostAndBlockWrappers.set(i, boostAndBlockWrapper);			
			i++;
		}

		return boostAndBlockWrappers;
	}

	public static List<KeyValueWrapper> getValidActions(Set<String> actions) {
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
	 * Method to set the sort the rows in the response object.
	 * @param merchandisingBaseResponse
	 * @param wrappers
	 * @author Chanchal.Kumari
	 */
	public static void setAndSortRows(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse,List<IWrapper> wrappers){
		merchandisingBaseResponse.setRows(wrappers);
	//	merchandisingBaseResponse.sortRows(); // sort by ModifiedDate
	//	merchandisingBaseResponse.setRows(BoostAndBlockWrapper.sortRowIds(merchandisingBaseResponse.getRows()));  // jqgrid needs sorted ids but they got unsorted when sorted by date

	}

	/**
	 * @author Chanchal.Kumari
	 */
	public static List<IWrapper> getBoostAndBlocks(List<BoostAndBlock> boostAndBlocks) {
			
			List<IWrapper> boostAndBlockWrappers = new ArrayList<IWrapper>(boostAndBlocks.size());
			int i = 0;
			BoostAndBlockWrapper boostAndBlockWrapper = null;
			
			for(BoostAndBlock  boostAndBlock : boostAndBlocks){
				boostAndBlockWrapper = getBoostBlock(boostAndBlock);
				boostAndBlockWrapper.setId(i);
				boostAndBlockWrappers.add(boostAndBlockWrapper);
				i++;
			}

			return boostAndBlockWrappers;
		}


	/**
	 * Get the one record of the block and boost data
	 * @param boostAndBlock
	 * @return BoostAndBlockWrapper
	 */
	public static BoostAndBlockWrapper getBoostBlock(BoostAndBlock boostAndBlock) {
		BoostAndBlockWrapper boostAndBlockWrapper = new BoostAndBlockWrapper();

		if(boostAndBlock.getCategoryId() != null){
			String categoryPath=boostAndBlock.getCategoryId().getCategoryPath();
			categoryPath=categoryPath.replaceAll("\n", "");
			boostAndBlockWrapper.setCategoryId(boostAndBlock.getCategoryId().getCategoryNodeId());
			boostAndBlockWrapper.setCategoryPath(categoryPath);
		}

		if(boostAndBlock.getCreatedDate() != null){
			boostAndBlockWrapper.setCreatedDate(boostAndBlock.getCreatedDate());
		}

		if(boostAndBlock.getCreatedBy() != null){
			boostAndBlockWrapper.setCreatedBy(boostAndBlock.getCreatedBy().getLoginName());
		}

		if(boostAndBlock.getId() != null){
			boostAndBlockWrapper.setBoostBlockId(boostAndBlock.getId());
		}

		if(boostAndBlock.getSearchProfileId() != null){
			boostAndBlockWrapper.setSearchProfileId(boostAndBlock.getSearchProfileId().getSearchProfileId());
			boostAndBlockWrapper.setSearchProfileName(boostAndBlock.getSearchProfileId().getProfileName());
		}

		if (boostAndBlock.getStatus() != null) {
			boostAndBlockWrapper.setStatus(boostAndBlock.getStatus().getStatus());
			boostAndBlockWrapper.setStatusId(boostAndBlock.getStatus().getStatusId());
		}
		
		if(boostAndBlock.getTerm() != null){
			boostAndBlockWrapper.setSearchTerm(boostAndBlock.getTerm());
		}

		if(boostAndBlock.getUpdatedDate() != null){
			boostAndBlockWrapper.setModifiedDate(boostAndBlock.getUpdatedDate());
		}

		if(boostAndBlock.getUpdatedBy() != null){
			boostAndBlockWrapper.setModifiedBy(boostAndBlock.getUpdatedBy().getFirstName()+"."+boostAndBlock.getUpdatedBy().getLastName());
		}
		List<BoostBlockProductWrapper> blockProductWrappers = new ArrayList<BoostBlockProductWrapper>();
		List<BoostBlockProductWrapper> boostProductWrappers = new ArrayList<BoostBlockProductWrapper>();
		if(boostAndBlock.getBoostAndBlockProducts() != null){
			for(BoostAndBlockProduct boostAndBlockProduct:boostAndBlock.getBoostAndBlockProducts()){
				BoostBlockProductWrapper blockProductWrapper = new BoostBlockProductWrapper();
				BoostBlockProductWrapper boostProductWrapper = new BoostBlockProductWrapper();
				if(boostAndBlockProduct.getPosition().equals(-1)){
					blockProductWrapper.setProductId(boostAndBlockProduct.getId());
					blockProductWrapper.setPosition(boostAndBlockProduct.getPosition());
					blockProductWrapper.setProductName(boostAndBlockProduct.getProductName());
					blockProductWrapper.setSkuId(boostAndBlockProduct.getSkuId());
					blockProductWrappers.add(blockProductWrapper);
				}
				else{
					boostProductWrapper.setProductId(boostAndBlockProduct.getId());
					boostProductWrapper.setPosition(boostAndBlockProduct.getPosition());
					boostProductWrapper.setProductName(boostAndBlockProduct.getProductName());
					boostProductWrapper.setSkuId(boostAndBlockProduct.getSkuId());
					boostProductWrappers.add(boostProductWrapper);
				}
			}
		}

		boostAndBlockWrapper.setBlockProduct(blockProductWrappers);
		boostAndBlockWrapper.setBoostProduct(boostProductWrappers);
		return boostAndBlockWrapper;
	}
}
