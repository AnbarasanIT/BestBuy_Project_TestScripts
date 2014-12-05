package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.domain.PromoSku;

/**
 * @author Lakshmi.Valluripalli
 *
 */
public class PromoBaseWrapper{

	@NotNull @Size(min=1,message="Field.NotEmpty") 
	private String promoName;
	@NotNull(message="Field.NotEmpty")
	private List<String> skuIds;
	@NotNull(message="Field.NotEmpty")
	private Date startDate;
	@NotNull(message="Field.NotEmpty")
	private Date endDate;
	
	/**
	 * Default Constructor
	 */
	public PromoBaseWrapper(){}
	
	/**
	 * Constructor for the promoWrapper
	 * @param promoName
	 * @param startDate 
	 * @param skuIds
	 * @param startDate
	 * @param endDate
	 */
	public PromoBaseWrapper(String promoName, List<PromoSku> promoSkus, Date startDate, Date endDate) {
		this.promoName = promoName;
		this.skuIds = new ArrayList<String>();
        this.startDate = startDate;
        this.endDate = endDate;
		for(PromoSku sku : promoSkus){
			if(sku.getId() != null){
				this.skuIds.add(sku.getSkuId());
			}
	    }
	}
	/**
	 * @return the promoQuery
	 */
	public String getPromoName() {
		return promoName;
	}
	/**
	 * @param promoQuery the promoQuery to set
	 */
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}
	
	public List<String> getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(List<String> skuIds) {
		this.skuIds = skuIds;
	}
	
	/**
	 * @return the startDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
