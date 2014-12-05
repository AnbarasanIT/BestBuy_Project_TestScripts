package com.bestbuy.search.merchandising.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SEARCH_PROFILES")
/**
 * @author Kalaiselvi Jaganathan
 * Search Profile
 */
public class SearchProfile implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEARCH_PROFILE_ID", insertable = true, updatable = true, nullable=false, length=20)
	private Long searchProfileId;

	@Column(name = "PROFILE_NAME", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String profileName;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="SYNONYM_LIST_ID", referencedColumnName="ID")
	private SynonymType synonymListId;

	@Column(name = "RANK_PROFILE_NAME", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String rankProfileName;

	@Column(name = "SEARCH_FIELD_NAME", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String searchFieldName;

	@Column(name = "COLLECTIONS", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String collections;

	@Column(name = "TOP_CATEGORY_ID", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String topCategoryId;

	@Column(name = "DEFAULT_PATH", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String defaultPath;

	@Column(name = "PIPELINE_NAME", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String pipelineName;

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_DATE", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private Date modifiedDate;

	@Column(name = "LASTMODIFIER_ID", unique = false, nullable = false, insertable = true, updatable = true, length=20)
	private String lastModifiedID;

	public Long getSearchProfileId() {
		return searchProfileId;
	}

	public void setSearchProfileId(Long searchProfileId) {
		this.searchProfileId = searchProfileId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public SynonymType getSynonymListId() {
		return synonymListId;
	}

	public void setSynonymListId(SynonymType synonymListId) {
		this.synonymListId = synonymListId;
	}

	public String getRankProfileName() {
		return rankProfileName;
	}

	public void setRankProfileName(String rankProfileName) {
		this.rankProfileName = rankProfileName;
	}

	public String getSearchFieldName() {
		return searchFieldName;
	}

	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}

	public String getCollections() {
		return collections;
	}

	public void setCollections(String collections) {
		this.collections = collections;
	}

	public String getTopCategoryId() {
		return topCategoryId;
	}

	public void setTopCategoryId(String topCategoryId) {
		this.topCategoryId = topCategoryId;
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getLastModifiedID() {
		return lastModifiedID;
	}

	public void setLastModifiedID(String lastModifiedID) {
		this.lastModifiedID = lastModifiedID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collections == null) ? 0 : collections.hashCode());
		result = prime * result
				+ ((defaultPath == null) ? 0 : defaultPath.hashCode());
		result = prime * result
				+ ((lastModifiedID == null) ? 0 : lastModifiedID.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((pipelineName == null) ? 0 : pipelineName.hashCode());
		result = prime * result
				+ ((profileName == null) ? 0 : profileName.hashCode());
		result = prime * result
				+ ((rankProfileName == null) ? 0 : rankProfileName.hashCode());
		result = prime * result
				+ ((searchFieldName == null) ? 0 : searchFieldName.hashCode());
		result = prime * result
				+ ((searchProfileId == null) ? 0 : searchProfileId.hashCode());
		result = prime * result
				+ ((synonymListId == null) ? 0 : synonymListId.hashCode());
		result = prime * result
				+ ((topCategoryId == null) ? 0 : topCategoryId.hashCode());
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
		SearchProfile other = (SearchProfile) obj;
		if (collections == null) {
			if (other.collections != null){
				return false;
			}
		} else if (!collections.equals(other.collections)){
			return false;
		}
		if (defaultPath == null) {
			if (other.defaultPath != null){
				return false;
			}
		} else if (!defaultPath.equals(other.defaultPath)){
			return false;
		}
		if (lastModifiedID == null) {
			if (other.lastModifiedID != null){
				return false;
			}
		} else if (!lastModifiedID.equals(other.lastModifiedID)){
			return false;
		}
		if (modifiedDate == null) {
			if (other.modifiedDate != null){
				return false;
			}
		} else if (!modifiedDate.equals(other.modifiedDate)){
			return false;
		}
		if (pipelineName == null) {
			if (other.pipelineName != null){
				return false;
			}
		} else if (!pipelineName.equals(other.pipelineName)){
			return false;
		}
		if (profileName == null) {
			if (other.profileName != null){
				return false;
			}
		} else if (!profileName.equals(other.profileName)){
			return false;
		}
		if (rankProfileName == null) {
			if (other.rankProfileName != null){
				return false;
			}
		} else if (!rankProfileName.equals(other.rankProfileName)){
			return false;
		}
		if (searchFieldName == null) {
			if (other.searchFieldName != null){
				return false;
			}
		} else if (!searchFieldName.equals(other.searchFieldName)){
			return false;
		}
		if (searchProfileId == null) {
			if (other.searchProfileId != null){
				return false;
			}
		} else if (!searchProfileId.equals(other.searchProfileId)){
			return false;
		}
		if (synonymListId == null) {
			if (other.synonymListId != null){
				return false;
			}
		} else if (!synonymListId.equals(other.synonymListId)){
			return false;
		}
		if (topCategoryId == null) {
			if (other.topCategoryId != null){
				return false;
			}
		} else if (!topCategoryId.equals(other.topCategoryId)){
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "SearchProfile [searchProfileId=" + searchProfileId
				+ ", profileName=" + profileName + ", synonymListId="
				+ synonymListId + ", rankProfileName=" + rankProfileName
				+ ", searchFieldName=" + searchFieldName + ", collections="
				+ collections + ", topCategoryId=" + topCategoryId
				+ ", defaultPath=" + defaultPath + ", pipelineName="
				+ pipelineName + ", modifiedDate=" + modifiedDate
				+ ", lastModifiedID=" + lastModifiedID + "]";
	}
}
