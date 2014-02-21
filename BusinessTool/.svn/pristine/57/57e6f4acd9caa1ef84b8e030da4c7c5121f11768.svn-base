package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ATTRIBUTE_VALUES")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for Attribute Value
 */
public class AttributeValue implements java.io.Serializable,Comparable<AttributeValue> {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ATTRIBUTE_VALUE_ID", insertable = true, updatable = true,nullable=false,length=20)
	private Long attributeValueId;
	
	@Column(name = "ATTRIBUTE_VALUE", nullable = true, insertable = true, updatable = true, length=255)
	private String attributeValue;
	
	@Column(name = "IS_ACTIVE", nullable = true, insertable = true, updatable = true, length=5)
	private String isActive = "Y";
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ATTRIBUTE_ID",referencedColumnName="ATTRIBUTE_ID")
	private Attribute attribute;
	
	public AttributeValue(){}
	
	public AttributeValue(Attribute attribute){
		this.attribute = attribute;
	}
	
	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @return the attributeValueId
	 */
	public Long getAttributeValueId() {
		return attributeValueId;
	}

	/**
	 * @param To set the attributeValueId
	 */
	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
	
	@Override
	public int compareTo(AttributeValue attrValue) {
	   return this.attributeValue.compareToIgnoreCase(attrValue.getAttributeValue());

	 }

	@Override
	public String toString() {
		return "AttributeValue [attributeValueId=" + attributeValueId
				+ ", attributeValue=" + attributeValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributeValue == null) ? 0 : attributeValue.hashCode());
		result = prime
				* result
				+ ((attributeValueId == null) ? 0 : attributeValueId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		AttributeValue other = (AttributeValue) obj;
		if (attributeValue == null) {
			if (other.attributeValue != null){
				return false;
			}
		} else if (!attributeValue.equals(other.attributeValue)){
			return false;
		}
		if (attributeValueId == null) {
			if (other.attributeValueId != null){
				return false;
			}
		} else if (!attributeValueId.equals(other.attributeValueId)){
			return false;
		}
		return true;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


}
