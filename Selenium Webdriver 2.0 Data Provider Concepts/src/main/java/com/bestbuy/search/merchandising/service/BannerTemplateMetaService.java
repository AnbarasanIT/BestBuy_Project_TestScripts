package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.BannerTemplateMetaDAO;
import com.bestbuy.search.merchandising.domain.BannerTemplateMeta;

/**
 * @author Kalaiselvi Jaganathan
 * Service for BannerTemplate
 */
public class BannerTemplateMetaService extends BaseService<Long,BannerTemplateMeta> implements IBannerTemplateMetaService{
	
	@Autowired
	public void setDao(BannerTemplateMetaDAO dao) {
		this.baseDAO = dao;
	}
}
