package com.bestbuy.search.merchandising.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.domain.BannerTemplateMeta;
import com.bestbuy.search.merchandising.service.IBannerTemplateMetaService;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
@RequestMapping("/bannertemplate")
@Controller
public class BannerTemplateController {

  private final static BTLogger log = BTLogger.getBTLogger(BannerTemplateController.class.getName());

	@Autowired
	private IBannerTemplateMetaService bannerTemplateService;

	public void setBannerTemplateService(
			IBannerTemplateMetaService bannerTemplateService) {
		this.bannerTemplateService = bannerTemplateService;
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public @ResponseBody Object loadBannerTemplate() {
		Map<String,List> bannerTemplate=new HashMap<String, List>();
		try{
			//TODO Kalai - Need to change the logic to use cache instead of database hit every time
			//Get the banner template data from template table
			List<BannerTemplateMeta> template = new ArrayList<BannerTemplateMeta>();
			template = (List<BannerTemplateMeta>) bannerTemplateService.retrieveAll();	
			bannerTemplate.put("banner_template",template);
			return bannerTemplate;
		}catch(Exception e){
			log.error("BannerTemplateController", e, ErrorType.APPLICATION, "Error While getting data from banner template table");
			return null;
		}
	}
}
