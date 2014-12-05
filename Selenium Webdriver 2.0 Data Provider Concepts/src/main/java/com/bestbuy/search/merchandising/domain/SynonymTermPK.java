/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author a1007483
 * Embeddable to Synonym
 */
@Embeddable
public class SynonymTermPK implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "SYNONYM_ID",referencedColumnName="ID")
	private Synonym synonym;

	@Column(name = "TERM",unique = false, nullable = true, insertable = true, updatable = true,length=255)
	private String synTerm;
	
	/**
	 * returns the SynonymGroupID
	 * @return
	 */
	public Synonym getSynonym() {
		return synonym;
	}
	
	/**
	 * sets the synonym group Id
	 * @param synonymGroupId
	 */
	public void setSynonym(Synonym synonym) {
		this.synonym = synonym;
	}
	
	/**
	 * returns the Synonym Term
	 * @return
	 */
	public String getSynTerm() {
		return synTerm;
	}
	
	/**
	 * sets the Synonym Term
	 * @param synTerm
	 */
	public void setSynTerm(String synTerm) {
		this.synTerm = synTerm;
	}
	
}
