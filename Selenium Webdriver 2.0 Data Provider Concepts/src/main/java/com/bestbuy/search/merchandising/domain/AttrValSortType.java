package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTR_VAL_SORT_TYPE")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for attribute Value sort type
 */
public class AttrValSortType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VAL_SORT_TYPE_ID", nullable=false, length=1)
	private Long valSortTypeId;
	
	@Column(name = "ATTR_VAL_SORT_TYPE", nullable=true, length=100)
	private String attrValSortType;

	/**
	 * @return the valSortTypeId
	 */
	public Long getValSortTypeId() {
		return valSortTypeId;
	}

	/**
	 * @param To set the valSortTypeId
	 */
	public void setValSortTypeId(Long valSortTypeId) {
		this.valSortTypeId = valSortTypeId;
	}

	/**
	 * @return the attrValSortType
	 */
	public String getAttrValSortType() {
		return attrValSortType;
	}

	/**
	 * @param To set the attrValSortType
	 */
	public void setAttrValSortType(String attrValSortType) {
		this.attrValSortType = attrValSortType;
	}

}
