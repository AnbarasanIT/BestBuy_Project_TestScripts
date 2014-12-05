package com.bestbuy.search.merchandising.wrapper;


import java.util.Date;

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
public class KeywordRedirectBaseWrapper {
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String redirectTerm;
	
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String redirectType;
	private String searchProfileType;
	
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String redirectUrl;
	
	@NotNull(message="Field.NotEmpty")
	private Date startDate;
	private Date endDate;
	
	/**
	 * Default Constructor
	 */
	public KeywordRedirectBaseWrapper(){}
	
	/**
	 * Constructor for the redirectBaseWrapper
	 * @param redirectTerm
	 * @param redirectType
	 * @param searchProfileType
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param redirectUrl
	 * @param endDate 
	 * @param startDate 
	 */
	public KeywordRedirectBaseWrapper(String redirectTerm, String redirectType,
			String searchProfileType,String redirectUrl, Date startDate, Date endDate) {
		this.redirectTerm = redirectTerm;
		this.redirectType = redirectType;
		this.searchProfileType = searchProfileType;
		this.redirectUrl = redirectUrl;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getRedirectTerm() {
		return redirectTerm;
	}
	public void setRedirectTerm(String redirectTerm) {
		this.redirectTerm = redirectTerm;
	}
	public String getRedirectType() {
		return redirectType;
	}
	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}
	public String getSearchProfileType() {
		return searchProfileType;
	}
	public void setSearchProfileType(String searchProfileType) {
		this.searchProfileType = searchProfileType;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
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
