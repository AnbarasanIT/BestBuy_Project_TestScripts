package com.bestbuy.search.merchandising.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.service.IAttributeValueService;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *
 */

@RequestMapping("/attributeValue")
@Controller
public class AttributeValueController {
	
	private final static BTLogger log = BTLogger.getBTLogger(AttributeValueController.class.getName());
	
	@Autowired
	private IAttributeValueService attributeValueService;
	
	@Autowired
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
		
	/**
	 * Load the attribute values for an attribute
	 * @param paginationWrapper
	 * @return
	 */
	@RequestMapping(value = "/loadAttrValue", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadAttrValue(@RequestBody PaginationWrapper paginationWrapper){

		try {
			List<IWrapper> wrappers = attributeValueService.loadAttrValue(paginationWrapper);

			merchandisingBaseResponse.setRows(wrappers);
			merchandisingBaseResponse.setData(paginationWrapper);
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "AttributeValue");
		}
		catch (Exception e) {
			log.error("AttributeValueController", e, ErrorType.APPLICATION, "Error while loading the attribute values");
			merchandisingBaseResponse.setErrorCode("Listing.Error", "AttributeValue");
		}

		return merchandisingBaseResponse;
	}

}
