/**
 * 
 */
package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.common.ValidWrapper;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymTerm;
import com.bestbuy.search.merchandising.domain.Users;

/**
 * @author a1007483
 *
 */

@ValidWrapper
@JsonIgnoreProperties({"synonymTerms"})
public class SynonymWrapper extends SynonymBaseWrapper implements IWrapper {
	
	public static boolean sortDesc = true;
	private int id;
	
	@NotNull
	private Long listId;
	private Long synonymId;
	private Date createdDate;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedDate;
	private Long statusId;
	private String status;
	@NotNull(message="Field.NotEmpty")
	private List<String> term;
	private List<KeyValueWrapper> actions;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public Long getSynonymId() {
		return synonymId;
	}

	public void setSynonymId(Long synonymId) {
		this.synonymId = synonymId;
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

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Overrides the compareTo method from the Comparable interface, Compares this object to the specified SynonymWrapper
	 * @param wrapper An IWrapper object that is a valid SynonymWrapper
	 * @author Ramiro.Serrato
	 */
	@Override
	public int compareTo(IWrapper wrapper) {		
		SynonymWrapper synonymWrapper = (SynonymWrapper) wrapper;
		if(modifiedDate != null && synonymWrapper.getModifiedDate() != null){
			int comparison = modifiedDate.compareTo(synonymWrapper.modifiedDate);
			return sortDesc ? (-1 * comparison) : comparison;
		}
		
		else{
			return 0;
		}
	}
	
	/**
	 * Retreieves a list of IWrapper object from the list of SynonymGroup, buy extracting all the information inside those entity objects and
	 * storing it in a UI Json serializing friendly object, the SynonymWrapper
	 * @param synonymGroups The list of SynonymGroup objects to be parsed
	 * @return A list of IWrapper with the extracted information
	 * @author Ramiro.Serrato
	 * Modified By Chanchal.Kumari,Date : 28th Nov 2012
	 */
  public static List<IWrapper> getSynonyms(List<Synonym> synonyms) {
    List<Long> uniqueIdList = new ArrayList<Long>();
    List<IWrapper> synonymWrappers = new ArrayList<IWrapper>(synonyms.size());

    int i = 0;
    SynonymWrapper synonymWrapper = null;
    List<String> terms = null;
    Users user = null;

    for (Synonym synonym : synonyms) {
      if (!uniqueIdList.contains(synonym.getId())) {
        uniqueIdList.add(synonym.getId());
        synonymWrapper = new SynonymWrapper();
        synonymWrapper.setPrimaryTerm(synonym.getPrimaryTerm());
        terms = new ArrayList<String>();

        for (SynonymTerm term : synonym.getSynonymGroupTerms()) {
          terms.add(term.getSynonymTerms().getSynTerm());
        }

        synonymWrapper.setTerm(terms);
        user = synonym.getUpdatedBy();

        if (user != null) {
          String fullName = user.getFirstName() + "." + user.getLastName();
          synonymWrapper.setModifiedBy(fullName);
        }

        synonymWrapper.setStatus(synonym.getStatus().getStatus());
        synonymWrapper.setSynonymListType(synonym.getSynListId().getSynonymListType());
        synonymWrapper.setDirectionality(synonym.getDirectionality());
        synonymWrapper.setExactMatch(synonym.getExactMatch());
        synonymWrapper.setModifiedDate(synonym.getUpdatedDate());

        Long primaryID = synonym.getId();
        synonymWrapper.setSynonymId(primaryID);
        synonymWrapper.setId(i);
        synonymWrappers.add(synonymWrapper);
        i++;
      }
    }

    return synonymWrappers;
  }
	
	public static List<IWrapper> sortRowIds(List<IWrapper> synonymWrappers) {
		int i = 0;
		
		for(IWrapper wrapper : synonymWrappers){
			SynonymWrapper synonymWrapper = (SynonymWrapper) wrapper;
			synonymWrapper.setId(i);
			synonymWrappers.set(i, synonymWrapper);			
			i++;
		}
		
		return synonymWrappers;
	}

	public List<KeyValueWrapper> getActions() {
		return actions;
	}

	public void setActions(List<KeyValueWrapper> actions) {
		this.actions = actions;
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

	public List<String> getTerm() {
		return term;
	}

	public void setTerm(List<String> term) {
		this.term = term;
	}


}
