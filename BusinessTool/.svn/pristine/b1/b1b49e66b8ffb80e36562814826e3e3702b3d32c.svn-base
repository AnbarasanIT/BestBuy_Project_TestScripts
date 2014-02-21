
package com.bestbuy.search.merchandising.web;

import static com.bestbuy.search.merchandising.common.ErrorType.ATTRIBUTES_JOB;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.service.IAttributeService;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.AttributeWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * Rest Controller to post and read the attribute values
 * @author Lakshmi.Valluripalli
 */
@RequestMapping("/attributes")
@Controller
public class AttributeController {
	
	private final static BTLogger log = BTLogger.getBTLogger(AttributeController.class.getName());
	
	@Autowired
	IAttributeService attributeService;
	
	@Autowired
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

	public void setMerchandisingBaseResponse(
			MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
		this.merchandisingBaseResponse = merchandisingBaseResponse;
	}
	
	/**
	 * Sends the list of Attribute Names
	 * @param attributeMeta
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> getAttributes(){
		List<Attribute> attributes = null;
		try {
			attributes = attributeService.loadAttributes();
			List<IWrapper> wrappers = AttributeWrapper.getAttributes(attributes);
			merchandisingBaseResponse.setRows(wrappers);
			merchandisingBaseResponse.setSuccessCode("000001", "Attribute");
		} 
		
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("000001", "Attribute");
			log.error("AttributeController", e, ErrorType.APPLICATION, "Error while loading the attributes");
		} 
		return merchandisingBaseResponse;
	} 
	
	
	/**
	 * Sends the list of Attribute Names thats matched the criteria
	 * @param attributeMeta
	 */
	@RequestMapping(value = "{attrName}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> getAttributes(@PathVariable String attrName){
		try{
			List<Attribute> attributes = attributeService.partialMatchAttribute(attrName);
			if(attributes != null){
				List<IWrapper> wrappers = AttributeWrapper.getAttributes(attributes);
				merchandisingBaseResponse.setRows(wrappers);
				merchandisingBaseResponse.setSuccessCode("000001", "Attribute");
			}else{
				merchandisingBaseResponse.setSuccessCode("000001", "No Values for this Attribute "+attrName);
			}
			
		}catch(Exception e){
			merchandisingBaseResponse.setErrorCode("000001", "AttributeValues for Attribute "+attrName);
      log.error("AttributeController", e, ErrorType.APPLICATION, "Error while loading the attribute values for attribute :   " + attrName);
		}
		return merchandisingBaseResponse;
	}
	
	
	
	/**
	 * Sends the list of Attribute Values
	 * @param attributeMeta
	 */
	@RequestMapping(value = "/values/{attrId}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> getAttributeValues(@PathVariable Long attrId){
		try{
			List<AttributeValue> attributeValues = attributeService.loadAttributeValues(attrId);
			if(attributeValues != null && attributeValues.size() > 0){
				List<IWrapper> wrappers = AttributeValueWrapper.getAttributeValueWrapper(attributeValues);
				merchandisingBaseResponse.setRows(wrappers);
				merchandisingBaseResponse.setSuccessCode("000001", "AttributeValue");
			}else{
				merchandisingBaseResponse.setSuccessCode("000001", "No Values for this Attribute "+attrId);
			}
			
		}catch(Exception e){
			merchandisingBaseResponse.setErrorCode("000001", "AttributeValues for Attribute "+attrId);
			log.error("AttributeController", e, ErrorType.APPLICATION, "Error while loading the attribute values for attribute ID:   " + attrId);
		}
		return merchandisingBaseResponse;
	}
}
