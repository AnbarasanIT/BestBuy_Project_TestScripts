package com.bestbuy.search.merchandising.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.service.CategoryFacetService;
import com.bestbuy.search.merchandising.service.ICategoryFacetService;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetOrderWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * @author Kalaiselvi Jaganathan
 * Retrieves the list of facets for a category path
 */
@RequestMapping("/categoryFacet")
@Controller
public class CategoryFacetController {

  private final static BTLogger log = BTLogger.getBTLogger(CategoryFacetController.class.getName());

	@Autowired
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

	@Autowired
	private ICategoryFacetService categoryFacetService;

	public void setMerchandisingBaseResponse(
			MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
		this.merchandisingBaseResponse = merchandisingBaseResponse;
	}
	
	/**
	 * @param categoryFacetService the categoryFacetService to set
	 */
	public void setCategoryFacetService(CategoryFacetService categoryFacetService) {
		this.categoryFacetService = categoryFacetService;
	}

	/** 
	 * Load the facets for a category
	 * @param catgId
	 * @return merchandisingBaseResponse
	 */
	@RequestMapping( value = "/{catgId}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadFacetCategory(@PathVariable String catgId) {
		try {
			if(catgId != null) {
				//Get the list of facets for the given category id
				List<IWrapper> wrappers = categoryFacetService.loadDepFacetsForCategory(catgId, null);
				merchandisingBaseResponse.setRows(wrappers);	
				merchandisingBaseResponse.setSuccessCode("LoadFacetCategory.Success", "Facet");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
				log.info(merchandisingBaseResponse.getMessage());
			}
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("LoadFacetCategory.Error", "Facet");
			log.error("CategoryFacetController", e, ErrorType.APPLICATION, "Error loading facets for category id: " + catgId);
		}	
		return merchandisingBaseResponse;
	}
	
	/** 
	 * Load the facets for a category
	 * @param catgId
	 * @return merchandisingBaseResponse
	 */
	@RequestMapping(value = "depFacetsForCategory/{catgId}/{facetId}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadDepFacetForCategory(@PathVariable String catgId,
			@PathVariable String facetId) {
		try {
			if(catgId != null) {
				//Get the list of facets for the given category id
				List<IWrapper> wrappers = categoryFacetService.loadDepFacetsForCategory(catgId, facetId);
				merchandisingBaseResponse.setRows(wrappers);	
				merchandisingBaseResponse.setSuccessCode("LoadFacetCategory.Success", "Facet");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
				log.info(merchandisingBaseResponse.getMessage());
			}
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("LoadFacetCategory.Error", "Facet");
			log.error("CategoryFacetController", e, ErrorType.APPLICATION, "Error loading facets for category id: " + catgId);
		}	
		return merchandisingBaseResponse;
	}
	
	 /** 
   * Load the facets for a category
   * @param catgId
   * @return merchandisingBaseResponse
   */
  @RequestMapping( value = "/banner/{catgId}", method = RequestMethod.GET)
  public @ResponseBody MerchandisingBaseResponse<IWrapper> loadFacetCategoryForBanner(@PathVariable String catgId) {
    try {
      if(catgId != null) {
        //Get the list of facets for the given category id
        List<IWrapper> wrappers = categoryFacetService.loadFacetCategory(catgId, true);
        merchandisingBaseResponse.setRows(wrappers);  
        merchandisingBaseResponse.setSuccessCode("LoadFacetCategory.Success", "Facet");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("LoadFacetCategory.Error", "Facet");
      log.error("CategoryFacetController", e, ErrorType.APPLICATION, "An error occurred when loading facets for category ID: " + catgId);
    } 
    return merchandisingBaseResponse;
  }
	
	/**
	 * Loads the list of facets for the given category (page), this list is to be displayed in the facet display order page
	 * @param categoryId The id of the category
	 * @return The list of CategoryFacetOrderWrapper with the facets in this context (page)
	 * @author Ramiro.Serrato
	 */
	@RequestMapping( value = "/displayOrder/{categoryId}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadFacetsForCategoryDisplayOrder(@PathVariable String categoryId) {
		try {
			List<IWrapper> wrappers = categoryFacetService.loadCategoryFacetsForDisplayOrder(categoryId);
			merchandisingBaseResponse.setRows(wrappers);	
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "Facet Display Order");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Listing.Error", "Facet Display Order");
			log.error("CategoryFacetController", e, ErrorType.APPLICATION, "An error occurred when loading facets for category ID: " + categoryId);
		}	
		
		return merchandisingBaseResponse;
	}	
	
	/**
	 * REST Service POST - Updates the display order to the DB for the categoryFacetWrapper object list given as input 
	 * @param wrappers The wrappers that contain the categoryFacet id and display order for each object to be updated
	 * @return A MerchandisingBaseResponse with the response of this REST service
	 * @author Ramiro.Serrato
	 */
	@RequestMapping( value = "/displayOrder", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> updateDisplayOrder(@RequestBody CategoryFacetOrderWrapper[] wrappers) {
		try {
			List<IWrapper> resultWrappers = categoryFacetService.updateCategoryFacetsOrder(Arrays.asList((wrappers)));
			merchandisingBaseResponse.setRows(resultWrappers);	
			merchandisingBaseResponse.setSuccessCode("Update.Success", "Facet Display Order");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Update.Error", "Facet Display Order");
			log.error("CategoryFacetController", e, ErrorType.APPLICATION, "An error occurred while updating facet ordering");
		}	
		
		return merchandisingBaseResponse;
	}

	/**
	 * Gets the list of dependant facets which depend on facets assigned to the given category (Page)
	 * @param categoryId The id of the category (Page)
	 * @return A MerchandisingBaseResponse with the response of this REST service
	 * @author Ramiro.Serrato
	 */
	@RequestMapping( value = "/dependantFacets/{categoryId}", method = RequestMethod.GET)	
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadDependantFacetsForCategoryDisplayOrder(@PathVariable String categoryId) {
		try {
			List<IWrapper> wrappers = categoryFacetService.loadDependantFacetsForCategoryDisplayOrder(categoryId);
			merchandisingBaseResponse.setRows(wrappers);	
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "Dependant Facets");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Listing.Error", "Dependant Facets");
			log.error("CategoryFacetController", e, ErrorType.APPLICATION, "An error occurred when loading facets for category ID: " + categoryId);
		}	
		
		return merchandisingBaseResponse;		
	}

	/**
	 * Gets the list of hidden facets that are assigned to the given category (Page)
	 * @param categoryId The id of the category (Page)
	 * @return A MerchandisingBaseResponse with the response of this REST service
	 * @author Ramiro.Serrato
	 */
	@RequestMapping( value = "/hiddenFacets/{categoryId}", method = RequestMethod.GET)	
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadHiddenFacetsForCategoryDisplayOrder(@PathVariable String categoryId) {
		try {
			List<IWrapper> wrappers = categoryFacetService.loadHiddenFacetsForCategoryDisplayOrder(categoryId);
			merchandisingBaseResponse.setRows(wrappers);	
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "Hidden Facets");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Listing.Error", "Hidden Facets");
			log.error("CategoryFacetController", e, ErrorType.APPLICATION, "An error occurred when listing hidden facets for category ID: " + categoryId);
		}	
		
		return merchandisingBaseResponse;			
	}	
}
