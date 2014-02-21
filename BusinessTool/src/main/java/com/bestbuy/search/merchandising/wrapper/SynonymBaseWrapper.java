package com.bestbuy.search.merchandising.wrapper;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;

/**
 * @author Lakshmi.Valluripalli
 *
 */
public class SynonymBaseWrapper {
	
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String primaryTerm;
	private List<String> synonymTerms;
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String directionality;
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String exactMatch;
	private String synonymListType;
	private Date startDate;
	private Date endDate;
	
	/**
	 * Default constructor
	 */
	public SynonymBaseWrapper(){} 
	
	/**
	 * 
	 * @param primaryTerm
	 * @param term
	 * @param directionality
	 * @param exactMatch
	 * @param synonymListType
	 * @param startDate 
	 * @param endDate 
	 */
	public SynonymBaseWrapper(String primaryTerm, List<String> synonymTerms,
			String directionality, String exactMatch,
			String synonymListType, Date startDate, Date endDate) {
		super();
		this.primaryTerm = primaryTerm;
		this.synonymTerms = synonymTerms;
		this.directionality = directionality;
		this.exactMatch = exactMatch;
		this.synonymListType = synonymListType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * gets the primaryTerm
	 * @return
	 */
	public String getPrimaryTerm() {
		return primaryTerm;
	}
	
	/**
	 * sets the primaryTerm
	 * @param primaryTerm
	 */
	public void setPrimaryTerm(String primaryTerm) {
		this.primaryTerm = primaryTerm;
	}
	
	/**
	 * gets the Directionality
	 * @return
	 */
	public String getDirectionality() {
		return directionality;
	}
	
	/**
	 * sets the Directionality
	 * @param directionality
	 */
	public void setDirectionality(String directionality) {
		this.directionality = directionality;
	}
	
	/**
	 * gets the ExactMatch
	 * @return
	 */
	public String getExactMatch() {
		return exactMatch;
	}
	
	/**
	 * sets the ExaxtMatch
	 * @param exactMatch
	 */
	public void setExactMatch(String exactMatch) {
		this.exactMatch = exactMatch;
	}
	
	/**
	 * gets the SynonymListType
	 * @return
	 */
	public String getSynonymListType() {
		return synonymListType;
	}
	
	/**
	 * sets the synonymListType
	 * @param synonymListType
	 */
	public void setSynonymListType(String synonymListType) {
		this.synonymListType = synonymListType;
	}
	
	/**
	 * gets the SynonymTerms
	 * @return
	 */
	public List<String> getSynonymTerms() {
		return synonymTerms;
	}
	
	/**
	 * sets the SynonymTerms
	 * @param synonymTerms
	 */
	public void setSynonymTerms(List<String> synonymTerms) {
		this.synonymTerms = synonymTerms;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
