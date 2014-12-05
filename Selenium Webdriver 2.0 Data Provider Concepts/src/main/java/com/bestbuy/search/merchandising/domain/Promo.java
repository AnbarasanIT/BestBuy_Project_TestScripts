package com.bestbuy.search.merchandising.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Chanchal.Kumari
 * Entity Mapping of Promo related tables
 */

@Entity
@Table(name = "PROMOS")
@SequenceGenerator(sequenceName="PROMO_SEQ",name="PROMO_SEQ_GEN")
public class Promo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="PROMO_SEQ_GEN",strategy=GenerationType.SEQUENCE)
	@Column(name = "PROMO_ID" , nullable=false, length=19)
	private Long promoId;
	
	@Column(name = "PROMO_NAME", unique=false, nullable=false, length=255)
	private String promoName;
	
	@OneToMany(mappedBy="promo",cascade = {CascadeType.ALL},fetch = FetchType.LAZY,orphanRemoval=true)
	private List<PromoSku> promoSku;

	/**
	 * @return the promoName
	 */
	public String getPromoName() {
		return promoName;
	}

	/**
	 * @param promoName the promoName to set
	 */
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}
	
	/**
	 * gets the PromoId
	 * @return
	 */
	public Long getPromoId() {
		return promoId;
	}
	
	/**
	 * sets the promoId
	 * @param promoId
	 */
	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	/**
	 * gets the promoSku
	 * @return
	 */
	public List<PromoSku> getPromoSku() {
		return promoSku;
	}
	
	/**
	 * sets the promoSKU
	 * @param promoSku
	 */
	public void setPromoSku(List<PromoSku> promoSku) {
		this.promoSku = promoSku;
	}
}
