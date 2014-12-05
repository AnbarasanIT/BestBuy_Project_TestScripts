package com.bestbuy.search.merchandising.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEXT_KEYWORDS")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for Banners - keyword
 */
public class ContextKeyword implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="contextId", column=@Column(name="CONTEXT_ID")),
        @AttributeOverride(name="keyword", column=@Column(name="KEYWORD"))
    })
	private ContextKeywordPK id;
		
	@MapsId("contextId")
	@ManyToOne
	private Context contextKeyword;

	

	/**
	 * @return the contextKeyword
	 */
	public Context getContextKeyword() {
		return contextKeyword;
	}

	/**
	 * @param To set the contextKeyword
	 */
	public void setContextKeyword(Context contextKeyword) {
		this.contextKeyword = contextKeyword;
	}

	public ContextKeywordPK getId() {
		return id;
	}
	
	/**
	 * @param To set the contextKeyword
	 */
	public void setId(ContextKeywordPK id) {
		this.id = id;
	}
}
