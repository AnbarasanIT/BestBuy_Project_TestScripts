/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STATUS")
/**
 * @author Kalaiselvi.Jaganathan
 * STATUS entity to store all the status information
 * 3 - Approved
 * 6 - Rejected
 */
public class Status implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STATUS_ID",insertable = true, updatable = true,nullable=false,length=19)
	private Long statusId;

	@Column(name = "STATUS_NAME",unique = false, nullable = true, insertable = true, updatable = true,length=255)
	private String status;

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
	
	
}
