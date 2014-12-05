package com.bestbuy.search.merchandising.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FACETS_DISPLAY_ORDER")
@SequenceGenerator(sequenceName = "FACETS_DISPLAY_ORDER_SEQ", name = "FACETS_DISPLAY_ORDER_SEQ_GEN")
/**
 * @author Chanchal Kumari
 */
public class FacetsDisplayOrder {

	@Id
	@GeneratedValue(generator = "FACETS_DISPLAY_ORDER_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORY_NODE_ID", referencedColumnName = "CATEGORY_NODE_ID")
	private Category category;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FACET_ID", referencedColumnName = "FACET_ID")
	private Facet facet;

	@Column(name = "DISPLAY_ORDER", length = 8)
	private Long displayOrder;
	
	@OneToOne
	@JoinColumn(name="STATUS_ID", referencedColumnName="STATUS_ID")
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Facet getFacet() {
		return facet;
	}

	public void setFacet(Facet facet) {
		this.facet = facet;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	

}
