package com.bestbuy.search.merchandising.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author Kalaiselvi.Jaganathan
 * 
 * Entity Mapping of Synonym related tables
 * Composite key mapping to get the synonym terms and to avoid duplicate synonym terms for the same primary term
 *
 */

@Entity
@Table(name = "SYNONYM_TERMS")
@Configurable

public class SynonymTerm {

	@EmbeddedId
	private SynonymTermPK synonymGroupTerms;

	/**
     * returns the set of synonym terms
     * @return synonymGroupTerms
     */
	public SynonymTermPK getSynonymTerms() {
		return this.synonymGroupTerms;
	}

	/**
     * Sets the list synonymterms
     * @param synonymGroupTerms
     */
	public void setSynonymTerms(SynonymTermPK synonymGroupTerms) {
		this.synonymGroupTerms = synonymGroupTerms;
	}


}