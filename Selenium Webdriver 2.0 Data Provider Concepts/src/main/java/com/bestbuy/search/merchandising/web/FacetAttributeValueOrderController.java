package com.bestbuy.search.merchandising.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.service.IFacetAttributeValueOrderService;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * @author Kalaiselvi Jaganathan
 * Retrieve the list of attribute values for a facet
 */
@RequestMapping("/facetAttributeValue")
@Controller
public class FacetAttributeValueOrderController {
	
  private final static BTLogger log = BTLogger.getBTLogger(FacetAttributeValueOrderController.class.getName());

	@Autowired
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

	@Autowired
	private IFacetAttributeValueOrderService facetAttributeValueOrderService;

	public void setMerchandisingBaseResponse(
			MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
		this.merchandisingBaseResponse = merchandisingBaseResponse;
	}
	
	/**
	 * @param to set the facetAttributeValueOrderService 
	 */
	public void setFacetAttributeValueOrderService(
			IFacetAttributeValueOrderService facetAttributeValueOrderService) {
		this.facetAttributeValueOrderService = facetAttributeValueOrderService;
	}

	/**
	 * Load the facets for a category
	 * @param catgId
	 * @return merchandisingBaseResponse
	 */
	@RequestMapping( value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadFacetValues(@PathVariable Long id) {
		try {
			if(id != null) {
				//Get the list of facets for the given facet id
				List<IWrapper> wrappers = facetAttributeValueOrderService.loadFacetValues(id);
				merchandisingBaseResponse.setRows(wrappers);	
				merchandisingBaseResponse.setSuccessCode("LoadFacetValues.Success", "Facet");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
				log.info(merchandisingBaseResponse.getMessage());
			}
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("LoadFacetValues.Error", "Facet");
			log.error("", e, ErrorType.APPLICATION,  merchandisingBaseResponse.getMessage());
		}	
		return merchandisingBaseResponse;
	}
}
