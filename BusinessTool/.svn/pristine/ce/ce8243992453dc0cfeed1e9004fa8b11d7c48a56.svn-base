package com.bestbuy.search.merchandising.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.foundation.common.logger.SplunkLogger;
import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.service.CategoryNodeService;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.ICategoryService;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * controller to save the category tree recieved from DAAS to DB and 
 * resends it to UI
 * @author Lakshmi.Valluripalli
 */
@RequestMapping("/category")
@Controller
public class CategoryTreeController {
  private final static BTLogger log = BTLogger.getBTLogger(CategoryTreeController.class.getName());
	
  @Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ICategoryNodeService categoryNodeService;
	
	@Autowired
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
	
	public void setMerchandisingBaseResponse(
			MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
		this.merchandisingBaseResponse = merchandisingBaseResponse;
	}
	
	
	
	/**
   * Invokes DasS Category tree Service to trigger the job.
   * @param String 
   */
  @RequestMapping(method = RequestMethod.GET, value="/loadTree")
  public @ResponseBody MerchandisingBaseResponse<IWrapper>  invokeCategoryTreeService() {
    try {
      categoryNodeService.invokeCategoryTreeService();
      merchandisingBaseResponse.setSuccessCode("000002", "CategoryTree");
      log.info("Category Tree Load Request Sent.");
    } catch (Exception e) {
    	merchandisingBaseResponse.setErrorCode("000003", "Category Tree Load Request Failed");
    	log.error("CategoryTreeController", e , ErrorType.CATEGORY_TREE, "Category Tree Load Request Failed");
    }
    return merchandisingBaseResponse;
  } 
	
	/**
	 * Receives the Category tree from daas
	 * @param String 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper>  postCategory(@RequestBody String categoryXml) {
		try {
			categoryNodeService.postCategoryTree(categoryXml);
			merchandisingBaseResponse.setSuccessCode("000002", "CategoryTree");
			log.info("Category Trees Parsed and Validated:..");
		} catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("000003", "CategoryTree Parsing Failed");
			log.error("CategoryTreeController", e , ErrorType.CATEGORY_TREE, "CategoryTree Parsing Failed ");
		}
		return merchandisingBaseResponse;
	}	
	
	/**
	 * returns the categoryTree with latest version to UI
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getCategory() {
		String categoryTree = null;
		try {
			categoryTree =  categoryService.loadCategory();
		}catch (Exception e) {
			log.error("CategoryTreeController", e ,ErrorType.CATEGORY_TREE, "Error while sending Category Tree:..");
		}
		return new ResponseEntity<String>(categoryTree, ResponseUtility.getRequestHeaders(null, "text/plain"), HttpStatus.OK);
	}	
}
