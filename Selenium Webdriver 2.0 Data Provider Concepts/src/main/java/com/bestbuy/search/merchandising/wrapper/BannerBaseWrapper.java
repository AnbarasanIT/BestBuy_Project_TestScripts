package com.bestbuy.search.merchandising.wrapper;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.wrapper.ContextBaseWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class BannerBaseWrapper {
	
	@NotNull @Size(min=1, message="Field.NotEmpty")
	private String bannerName;
	private String bannerTypeName;
	@NotNull(message="Field.NotEmpty")
	private Date startDate;
	@NotNull(message="Field.NotEmpty")
	private Date endDate;
	private String templateType;
	private Long documentId; 
	private List<ContextBaseWrapper> context;
	private List<BannerTemplateWrapper> bannerTemplates;
	
	/**
	 * @return the assetId
	 */
	public Long getDocumentId() {
		return documentId;
	}
	/**
	 * @param To set the assetId
	 */
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	
	/**
	 * @return the bannerName
	 */
	public String getBannerName() {
		return bannerName;
	}
	/**
	 * @param to set the bannerName 
	 */
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}
	
	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}
	/**
	 * @param to set the templateType 
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	/**
	 * @return the context
	 */
	public List<ContextBaseWrapper> getContext() {
		return context;
	}
	/**
	 * @param to set the context
	 */
	public void setContext(List<ContextBaseWrapper> context) {
		this.context = context;
	}
	/**
	 * @return the bannerTemplates
	 */
	public List<BannerTemplateWrapper> getBannerTemplates() {
		return bannerTemplates;
	}
	/**
	 * @param to set the bannerTemplates
	 */
	public void setBannerTemplates(List<BannerTemplateWrapper> bannerTemplates) {
		this.bannerTemplates = bannerTemplates;
	}
	
	public String getBannerTypeName() {
		return bannerTypeName;
	}
	public void setBannerTypeName(String bannerTypeName) {
		this.bannerTypeName = bannerTypeName;
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
