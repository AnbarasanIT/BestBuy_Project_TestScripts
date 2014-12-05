package com.bestbuy.search.merchandising.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ATTRIBUTES")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for Attributes
 */
public class Attribute implements java.io.Serializable,Comparable<Attribute>{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ATTRIBUTE_ID", insertable = true, updatable = true,nullable=false,length=20)
	private Long attributeId;
	
	@Column(name = "ATTRIBUTE_NAME", unique = true, nullable = true, insertable = true, updatable = true, length=255)
	private String attributeName;
	
	@Column(name = "IS_ACTIVE", nullable = true, insertable = true, updatable = true, length=1)
	private String isActive = "Y";
	

	@OneToMany(mappedBy="attribute",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private List <AttributeValue> attributeValue = new ArrayList <AttributeValue>();

	/**
	 * @return the attributeValue
	 */
	public List<AttributeValue> getAttributeValue() {
		return attributeValue;
	}

	/**
	 * @param To set the attributeValue
	 */
	public void setAttributeValue(List<AttributeValue> attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @return the attributeId
	 */
	public Long getAttributeId() {
		return attributeId;
	}

	/**
	 * @param To set the attributeId
	 */
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param To set the attributeName
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	
	@Override
	public int compareTo(Attribute attribute) {
	   return this.attributeName.compareToIgnoreCase(attribute.getAttributeName());

	 }

	@Override
	public String toString() {
		if(attributeValue != null && attributeValue.size() > 0){
			Collections.sort(attributeValue);
			return "Attribute [attributeId=" + attributeId + ", attributeName="
			+ attributeName + ", isActive=" + isActive + ", attributeValue="
			+ attributeValue.toString() + "]";
		}else{
			return "Attribute [attributeId=" + attributeId + ", attributeName="
					+ attributeName + ", isActive=" + isActive + "]";
		}
		//tostring on the list
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributeId == null) ? 0 : attributeId.hashCode());
		result = prime * result
				+ ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result
				+ ((attributeValue == null) ? 0 : attributeValue.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
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
		Attribute other = (Attribute) obj;
		if (attributeId == null) {
			if (other.attributeId != null){
				return false;
			}
		} else if (!attributeId.equals(other.attributeId)){
			return false;
		}
		if (attributeName == null) {
			if (other.attributeName != null){
				return false;
			}
		} else if (!attributeName.equals(other.attributeName)){
			return false;
		}
		if (attributeValue == null) {
			if (other.attributeValue != null){
				return false;
			}
		} else if (!attributeValue.equals(other.attributeValue)){
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null){
				return false;
			}
		} else if (!isActive.equals(other.isActive)){
			return false;
		}
		return true;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	} 
}
