package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Kalaiselvi Jaganathan
 * Redirects for Keyword Term
 * @Modified By Chanchal.Kumari Removed the assets and assetHistory.
 */

@Entity
@Table(name = "KEYWORD_REDIRECTS")
@SequenceGenerator(sequenceName="REDIRECTS_SEQ",name="REDIRECTS_SEQ_GEN")
public class KeywordRedirect extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="REDIRECTS_SEQ_GEN",strategy=GenerationType.SEQUENCE)
	@Column(name = "KEYWORD_ID", length=19)
	private Long keywordId;
	
	@Column(name = "KEYWORD",nullable = false, length=150)
	private String keyword;
	
	@Column(name = "REDIRECT_VALUE",nullable = false,length=2048)//is this unique
	private String redirectString;

	@Column(name = "REDIRECT_TYPE",nullable = false,length=10)//create a enum with tha Check constraint in the database
	private String redirectType;
	
	@OneToOne
	@JoinColumn(name="SEARCH_PROFILE_ID", referencedColumnName="SEARCH_PROFILE_ID")
	private SearchProfile searchProfile;
	
	/**
	 * gets the keywordId
	 * @return
	 */
	public Long getKeywordId() {
		return keywordId;
	}
	
	/**
	 * sets the keywordId
	 * @param keywordId
	 */
	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}
	
	/**
	 * gets the keyword
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * sets the keyword
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	/**
	 * gets the redirectString
	 * @return
	 */
	public String getRedirectString() {
		return redirectString;
	}
	
	/**
	 * sets the redirectString
	 * @param redirectString
	 */
	public void setRedirectString(String redirectString) {
		this.redirectString = redirectString;
	}
	
	/**
	 * gets the redirectType
	 * @return
	 */
	public String getRedirectType() {
		return redirectType;
	}
	
	/**
	 * sets the redirectType
	 * @param redirectType
	 */
	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}
	
	/**
	 * gets the SearchProfile
	 * @return
	 */
	public SearchProfile getSearchProfile() {
		return searchProfile;
	}
	
	/**
	 * sets the searchProfile
	 * @param searchProfile
	 */
	public void setSearchProfile(SearchProfile searchProfile) {
		this.searchProfile = searchProfile;
	}
}
