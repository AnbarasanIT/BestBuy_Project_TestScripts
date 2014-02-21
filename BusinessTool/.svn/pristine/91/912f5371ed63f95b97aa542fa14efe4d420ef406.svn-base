package com.bestbuy.search.merchandising.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SYNONYM_TYPES")
/**
 * @author Kalaiselvi.Jaganathan
 *
 * Synonym list type
 * 1133827231862	Global_syn_list1
 * 1147192016834	Artist
 * 1147192098290	music
 * 1147192120811	song
 */

public class SynonymType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable=false, length=19)
	private Long synonymListId;
	
	@Column(name = "NAME", nullable=true, length=255)
	private String synonymListType;
	
	@OneToOne
	@JoinColumn(name="CREATED_BY", referencedColumnName="USER_ID")
	private Users createdUser;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", unique = false, nullable = false, insertable = true, updatable = true ,length=7)
	private Date createdDate;
	
	@OneToOne
	@JoinColumn(name="UPDATED_BY", referencedColumnName="USER_ID")
	private Users updatedUser;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE", unique = false, nullable = true, insertable = true, updatable = true ,length=7)
	private Date updatedDate;
	
	/**
     * returns the synonym list type of the primary term
     * @return synonymListType
     */
	public String getSynonymListType() {
		return synonymListType;
	}

	/**
     * Sets the Synonym list type of the primary term
     * @param synonymListType
     */
	public void setSynonymListType(String synonymListType) {
		this.synonymListType = synonymListType;
	}
	
	/**
     * returns the synonym list id of the primary term
     * @return synonymListId
     */
	public Long getSynonymListId() {
		return synonymListId;
	}

	/**
     * Sets the Synonym list id of the primary term
     * @param synonymListId
     */
	public void setSynonymListId(Long synonymListId) {
		this.synonymListId = synonymListId;
	}

	public Users getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Users createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Users getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(Users updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
