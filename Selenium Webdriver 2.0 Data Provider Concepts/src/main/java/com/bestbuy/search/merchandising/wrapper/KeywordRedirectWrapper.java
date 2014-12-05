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
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;

/**
 * wrapper class created for redirect URL functionality
 * @author Lakshmi Valluripalli
 * @Date 11th Sep 2012.
 */

@ValidWrapper
public class KeywordRedirectWrapper extends KeywordRedirectBaseWrapper implements IWrapper{
	public static boolean sortDesc = true;
	
	private Long redirectId;
	private Long statusId;
	private Long searchProfileId;
	private String modifiedBy;
	private Date modifiedDate;
	private Date createdDate;
	private String createdBy;
	private String status;
	
	private int id;
	
	private List<KeyValueWrapper> actions;	

	public List<KeyValueWrapper> getActions() {
		return actions;
	}

	public void setActions(List<KeyValueWrapper> actions) {
		this.actions = actions;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getRedirectId() {
		return redirectId;
	}

	public void setRedirectId(Long redirectId) {
		this.redirectId = redirectId;
	}

	public Long getSearchProfileId() {
		return searchProfileId;
	}

	public void setSearchProfileId(Long searchProfileId) {
		this.searchProfileId = searchProfileId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static boolean isSortDesc() {
		return sortDesc;
	}

	public static void setSortDesc(boolean sortDesc) {
		KeywordRedirectWrapper.sortDesc = sortDesc;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Overrides the compareTo method from the Comparable interface, Compares this object to the specified RedirectWrapper
	 * @param wrapper An IWrapper object that is a valid RedirectWrapper
	 */
	@Override
	public int compareTo(IWrapper wrapper) {		
		KeywordRedirectWrapper redirectWrapper = (KeywordRedirectWrapper) wrapper;
		if(modifiedDate != null){
			int comparison = modifiedDate.compareTo(redirectWrapper.getModifiedDate());  // by default compare to gives an asc ordering
			return sortDesc ? (-1 * comparison) : comparison;
		}else{
			return -1;
		}
	}

	/**
	 * Retreieves a list of IWrapper object from the list of KeywordRedirect, buy extracting all the information inside those entity objects and
	 * storing it in a UI Json serializing friendly object, the KeywordRedirectWrapper
	 * @param KeywordRedirect The list of KeywordRedirect objects to be parsed
	 * @return A list of IWrapper with the extracted information
	 * 
	 */
	public static List<IWrapper> getRedirects(List<KeywordRedirect> keywordRedirects) {

		List<IWrapper> redirectWrappers = new ArrayList<IWrapper>(keywordRedirects.size());

		int i = 0; 
		for(KeywordRedirect keywordRedirect : keywordRedirects){
			KeywordRedirectWrapper redirectWrapper = new KeywordRedirectWrapper();
			redirectWrapper.setRedirectId(keywordRedirect.getKeywordId());

			redirectWrapper.setRedirectTerm(keywordRedirect.getKeyword());
			redirectWrapper.setRedirectType(keywordRedirect.getRedirectType());
			redirectWrapper.setRedirectUrl(keywordRedirect.getRedirectString());
			
			if(keywordRedirect.getSearchProfile() != null){
				redirectWrapper.setSearchProfileId(keywordRedirect.getSearchProfile().getSearchProfileId());
				redirectWrapper.setSearchProfileType(keywordRedirect.getSearchProfile().getProfileName());
			}
			
				redirectWrapper.setStartDate(keywordRedirect.getStartDate());
				redirectWrapper.setEndDate(keywordRedirect.getEndDate());
				Date modifiedDate = keywordRedirect.getUpdatedDate();
				redirectWrapper.setModifiedDate(modifiedDate);
				Users user= null; 
				user = keywordRedirect.getUpdatedBy();
				
				if(user != null){
					redirectWrapper.setModifiedBy(user.getFirstName()+"."+user.getLastName());
				}
				
				Status status = keywordRedirect.getStatus();
				if(status != null){
					redirectWrapper.setStatus(status.getStatus());
					redirectWrapper.setStatusId(status.getStatusId());
				}
				redirectWrapper.setId(i);
				redirectWrappers.add(redirectWrapper);
				i++;
			}
	
		return redirectWrappers;
	}

	/**
	 * Method to set the row ID's
	 * @param redirectWrappers
	 * @return
	 */
	public static List<IWrapper> sortRowIds(List<IWrapper> redirectWrappers) {
		int i = 0;
		for(IWrapper wrapper : redirectWrappers){
			KeywordRedirectWrapper redirectWrapper = (KeywordRedirectWrapper) wrapper;
			redirectWrapper.setId(i);
			redirectWrappers.set(i,redirectWrapper);			
			i++;
		}
		return redirectWrappers;
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
}
