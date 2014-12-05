 package com.bestbuy.search.merchandising.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author a940715
 *
 */
@Entity
@Table(name = "SYNONYMS")
@SequenceGenerator(sequenceName="SYNONYMS_SEQ",name="SYNONYMS_SEQ_GEN")
/**
 * @author Kalaiselvi.Jaganathan
 * 
 * Entity Mapping of Synonym related tables
 * 
 *
 */
public class Synonym extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SYNONYMS_SEQ_GEN",strategy=GenerationType.SEQUENCE)
	@Column(name = "ID", length=19)
	private Long id;
	
	@Column(name = "PRIMARY_TERM", unique=false, nullable=false, length=255)
	private String primaryTerm;

	@Column(name = "DIRECTIONALITY", nullable=true, length=255)
	private String directionality;

	@Column(name = "MATCH_TYPE", nullable=true, length=255)
	private String exactMatch;

	@OneToOne
	@JoinColumn(name="TYPE_ID", referencedColumnName="ID")
	private SynonymType synListId;
	
	@OrderBy("synonymGroupTerms.synTerm")
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="synonymGroupTerms.synonym",orphanRemoval=true)
	private List<SynonymTerm> synonymGroupTerms = new ArrayList<SynonymTerm>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPrimaryTerm() {
		return primaryTerm;
	}

	public void setPrimaryTerm(String primaryTerm) {
		this.primaryTerm = primaryTerm;
	}

	public String getDirectionality() {
		return directionality;
	}

	public void setDirectionality(String directionality) {
		this.directionality = directionality;
	}

	public String getExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(String exactMatch) {
		this.exactMatch = exactMatch;
	}

	public SynonymType getSynListId() {
		return synListId;
	}

	public void setSynListId(SynonymType synListId) {
		this.synListId = synListId;
	}

	
	public List<SynonymTerm> getSynonymGroupTerms() {
		return synonymGroupTerms;
	}

	public void setSynonymGroupTerms(List<SynonymTerm> synonymGroupTerms) {
		this.synonymGroupTerms = synonymGroupTerms;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((directionality == null) ? 0 : directionality.hashCode());
		result = prime * result
				+ ((exactMatch == null) ? 0 : exactMatch.hashCode());
		result = prime * result
				+ ((primaryTerm == null) ? 0 : primaryTerm.hashCode());
		result = prime * result
				+ ((synListId == null) ? 0 : synListId.hashCode());
		result = prime
				* result
				+ ((synonymGroupTerms == null) ? 0 : synonymGroupTerms
						.hashCode());
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
		Synonym other = (Synonym) obj;
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} else if (!id.equals(other.id)){
			return false;
		}
		if (directionality == null) {
			if (other.directionality != null){
				return false;
			}
		} else if (!directionality.equals(other.directionality)){
			return false;
		}
		if (exactMatch == null) {
			if (other.exactMatch != null){
				return false;
			}
		} else if (!exactMatch.equals(other.exactMatch)){
			return false;
		}
		if (primaryTerm == null) {
			if (other.primaryTerm != null){
				return false;
			}
		} else if (!primaryTerm.equals(other.primaryTerm)){
			return false;
		}
		if (synListId == null) {
			if (other.synListId != null){
				return false;
			}
		} else if (!synListId.equals(other.synListId)){
			return false;
		}
		if (synonymGroupTerms == null) {
			if (other.synonymGroupTerms != null){
				return false;
			}
		} else if (!synonymGroupTerms.equals(other.synonymGroupTerms)){
			return false;
		}
		return true;
	}

}