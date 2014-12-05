/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This the base Entity Class for all the domain classes
 * @author Lakshmi Valluripalli
 */
@MappedSuperclass
public abstract class BaseEntity {
	
	@OneToOne
	@JoinColumn(name="STATUS_ID", referencedColumnName="STATUS_ID")
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", unique = false, nullable = true, insertable = true, updatable = true, length=7)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", unique = false, nullable = true, insertable = true, updatable = true ,length=7)
	private Date endDate;
	
	@OneToOne
	@JoinColumn(name="CREATED_BY", referencedColumnName="USER_ID")
	private Users createdBy;
	
	@Column(name = "CREATED_DATE", unique = false, nullable = false, insertable = true, updatable = true ,length=7)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToOne
	@JoinColumn(name="UPDATED_BY", referencedColumnName="USER_ID")
	private Users updatedBy;
	
	@Column(name = "UPDATED_DATE", unique = false, nullable = true, insertable = true, updatable = true ,length=7)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	/**
	 * gets the status
	 * @return
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * set the status
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * gets StartDate
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * sets the StartDate
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * getsEndDate
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * sets EndDate
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * gets CreatedBy
	 * @return
	 */
	public Users getCreatedBy() {
		return createdBy;
	}

	/**
	 * sets createdBy
	 * @param createdBy
	 */
	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * gets the createdDate
	 * @return
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * sets the createDate
	 * @param createdDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * gets the updatedBy
	 * @return
	 */
	public Users getUpdatedBy() {
		return updatedBy;
	}
	
	/**
	 * sets the updatedBy
	 * @param updatedBy
	 */
	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	/**
	 * gets the updatedDate
	 * @return
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * sets the updatedDate
	 * @param updatedDate
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
