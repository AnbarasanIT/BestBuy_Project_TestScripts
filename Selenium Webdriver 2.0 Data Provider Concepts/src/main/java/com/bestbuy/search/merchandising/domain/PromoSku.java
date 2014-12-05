package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Chancha Kumari
 */

@Entity
@Table(name = "PROMO_SKUS")
public class PromoSku implements java.io.Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID" , nullable=false, length=19)
	private Long id;
	
	@Column(name = "SKU_ID")
    private String skuId;
	
	@ManyToOne
	@JoinColumn(name = "PROMO_ID",referencedColumnName="PROMO_ID")
	private Promo promo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}
}
