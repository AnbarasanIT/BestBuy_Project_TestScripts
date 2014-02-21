package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.common.ValidWrapper;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.domain.PromoSku;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;

/**
 * @author a1003132
*/

@ValidWrapper
public class PromoWrapper extends PromoBaseWrapper implements IWrapper{
public static boolean sortDesc = true;
	
	private int id;
	
	private Long promoId;
	private String modifiedBy;
	private Date modifiedDate;
	private Date createdDate;
	private String createdBy;
	private Long statusId;
	private String status;
	private List<KeyValueWrapper> actions;
	/**
	 * @return the promoId
	 */
	public Long getPromoId() {
		return promoId;
	}
	/**
	 * @param promoId the promoId to set
	 */
	public void setPromoId(Long promoId) {
		this.promoId = promoId;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @return the sortDesc
	 */
	public static boolean isSortDesc() {
		return sortDesc;
	}
	/**
	 * @param sortDesc the sortDesc to set
	 */
	public static void setSortDesc(boolean sortDesc) {
		PromoWrapper.sortDesc = sortDesc;
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
	 * Overrides the compareTo method from the Comparable interface, Compares this object to the specified PromoWrapper
	 * @param wrapper An IWrapper object that is a valid PromoWrapper
	 * @author chanchal.kumari
	 */
	@Override
	public int compareTo(IWrapper wrapper) {		
		PromoWrapper promoWrapper = (PromoWrapper) wrapper;
		if(modifiedDate != null){
			int comparison = modifiedDate.compareTo(promoWrapper.modifiedDate);  // by default compare to gives an asc ordering
			return sortDesc ? (-1 * comparison) : comparison;
		}else{
			return -1;
		}
	}
	
	public static List<IWrapper> sortRowIds(List<IWrapper> promoWrappers) {
		int i = 0;
		
		for(IWrapper wrapper : promoWrappers){
			PromoWrapper promoWrapper = (PromoWrapper) wrapper;
			promoWrapper.setId(i);
			promoWrappers.set(i, promoWrapper);			
			i++;
		}
		
		return promoWrappers;
	}

	
	/**
	 * Retreieves a list of IWrapper object from the list of Promo, buy extracting all the information inside those entity objects and
	 * storing it in a UI Json serializing friendly object, the PromoWrapper
	 * @param Banners The list of Promo objects to be parsed
	 * @return A list of IWrapper with the extracted information
	 * 
	 */
  public static List<IWrapper> getPromos(List<Promo> promos) {

    List<Long> uniqueIdList = new ArrayList<Long>();
    List<IWrapper> promoWrappers = new ArrayList<IWrapper>(promos.size());
    int i = 0;
    PromoWrapper promoWrapper = null;
    Users user = null;
    Status status = null;

    for (Promo promo : promos) {
      if (!uniqueIdList.contains(promo.getPromoId())) {
        uniqueIdList.add(promo.getPromoId());
        promoWrapper = new PromoWrapper();
        promoWrapper.setPromoId(promo.getPromoId());
        promoWrapper.setPromoName(promo.getPromoName());
        ArrayList<String> skuIds = new ArrayList<String>();

        for (PromoSku promoSku : promo.getPromoSku()) {
          skuIds.add(promoSku.getSkuId());
        }

        promoWrapper.setSkuIds(skuIds);
        promoWrapper.setModifiedDate(promo.getUpdatedDate());
        user = promo.getUpdatedBy();

        if (user != null) {
          promoWrapper.setModifiedBy(user.getFirstName() + "." + user.getLastName());
        }

        promoWrapper.setCreatedDate(promo.getCreatedDate());
        user = promo.getCreatedBy();

        if (user != null) {
          promoWrapper.setCreatedBy(user.getFirstName() + "." + user.getLastName());
        }

        promoWrapper.setStartDate(promo.getStartDate());
        promoWrapper.setEndDate(promo.getEndDate());
        status = promo.getStatus();

        if (status != null) {
          promoWrapper.setStatus(status.getStatus());
          promoWrapper.setStatusId(status.getStatusId());
        }

        promoWrapper.setId(i);
        promoWrappers.add(promoWrapper);
        i++;
      }
    }

    return promoWrappers;
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
	//	merchandisingBaseResponse.setRows(PromoWrapper.sortRowIds(merchandisingBaseResponse.getRows()));  // jqgrid needs sorted ids but they got unsorted when sorted by date
		
	}

}
