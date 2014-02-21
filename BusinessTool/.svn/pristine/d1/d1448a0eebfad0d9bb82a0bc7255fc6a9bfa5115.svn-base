package com.bestbuy.search.merchandising.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.service.IBoostAndBlockService;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.ResponseStatusEnum;

/**
 * This class contains the REST API and controller methods for the Boost and Block functionality
 * @author Ramiro.Serrato
 * @Modified By Kalaiselvi Jaganathan Added create/update/loadEditBoostBlock function
 * @ Modified By Chanchal.Kumari ,Added method to load the BoostAndBlock
 */
@RequestMapping("/boostAndBlock")
@Controller
public class BoostAndBlockController {
	
  private final static BTLogger log = BTLogger.getBTLogger(BoostAndBlockController.class.getName());

	@Autowired
	private IBoostAndBlockService boostAndBlockService;
	
	@Autowired
	private GeneralWorkflow generalWorkflow;

	@Autowired
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
	
	public class TempWrapper implements IWrapper{
		private Boolean valid;
		
		public IWrapper setValid(Boolean valid) {
			this.valid = valid;
			return this;
		}
		
		public Boolean getValid() {
			return valid;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(IWrapper o) {
			// TODO Auto-generated method stub
			return 0;
		}
	};	
	
	/**
	 * @param to set the boostAndBlockService 
	 */
	public void setBoostAndBlockService(IBoostAndBlockService boostAndBlockService) {
		this.boostAndBlockService = boostAndBlockService;
	}
	
	/**
	 * @param to set the merchandisingBaseResponse 
	 */
	public void setMerchandisingBaseResponse(
			MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
		this.merchandisingBaseResponse = merchandisingBaseResponse;
	}
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	/**
	 * Method to load the list of Boost and Block from DB
	 * @return  MerchandisingBaseResponse<IWrapper>
	 * @author Chanchal.Kumari
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchBoostBlock", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> load(@RequestBody PaginationWrapper paginationWrapper){
		try {
			List<BoostAndBlockWrapper> wrappers = (List<BoostAndBlockWrapper>) (List<?>)boostAndBlockService.load(paginationWrapper);
			
			if(wrappers != null && wrappers.size() > 0){
				for(BoostAndBlockWrapper boostAndBlockWrapper : wrappers){
					boostAndBlockWrapper.setActions(BoostAndBlockWrapper.getValidActions(generalWorkflow.getActionsForStatus(boostAndBlockWrapper.getStatus())));
				}
				BoostAndBlockWrapper.setAndSortRows(merchandisingBaseResponse, (List<IWrapper>) (List<?>)wrappers);
			}
			 
      // Change done for the STP 1229 -Start
      merchandisingBaseResponse.setData(paginationWrapper);
      // Change done for the STP 1229 -End
      
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "BoostAndBlock");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Listing.Error", "BoostAndBlock");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error while listing the boosts and blocks");
		}
		
		return merchandisingBaseResponse;
	}
	
	/**
	 * Method to create a new BoostAndBlock
	 * @param BoostAndBlockWrapper
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> create(@RequestBody BoostAndBlockWrapper boostAndBlockWrapper) {
		try {
			if (boostAndBlockWrapper != null) {
				//Call the service to create BoostAndBlock by passing the BoostAndBlockwrapper
				boostAndBlockService.createBoostAndBlock(boostAndBlockWrapper);
				merchandisingBaseResponse.setSuccessCode("Create.Success", "BoostAndBlock");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While Creating the BoostAndBlock");
			merchandisingBaseResponse.setErrorCode("Create.Error", "BoostAndBlock");
		}
		return merchandisingBaseResponse;
	}
	
	/**
	 * Method to update a BoostAndBlock
	 * @param BoostAndBlockWrapper
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> update(@RequestBody BoostAndBlockWrapper boostAndBlockWrapper) {
		try {
			if (boostAndBlockWrapper != null) {
				//Call the service to update BoostAndBlock by passing the BoostAndBlockwrapper
				IWrapper boostBlockWrapper = boostAndBlockService.updateBoostAndBlock(boostAndBlockWrapper);
				merchandisingBaseResponse.setData(boostBlockWrapper);
				merchandisingBaseResponse.setSuccessCode("Update.Success", "BoostAndBlock");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Update.Error", "BoostAndBlock");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While Updating a BoostAndBlock");
		}
		return merchandisingBaseResponse;
	}

	/**
	 * Method to load the edit pop-up data for a Boost and Block
	 *
	 * @param boostBlockId
	 * @return JsonResponse
	 */

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadEditBoostBlock(@PathVariable Long id) {
		try {
			if(id != null) {
				merchandisingBaseResponse.setData(boostAndBlockService.loadEditBoostBlockData(id));
				merchandisingBaseResponse.setSuccessCode("Retrive.Success", "BoostAndBlock");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} 
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Retrive.Error", "BoostAndBlock");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While editing a BoostAndBlock");
		}
		
		return merchandisingBaseResponse;
	}
	
	/**
	 * This method performs a validation in order to see if the new Boost or Block to be created already exists 
	 * or the search term is currently being used for a promo page
	 * @param searchTerm The search term for the boost or block to be created
	 * @param searchProfileId The search profile id
	 * @param categoryId The id of the category
	 * @return A generic base response with the results of the operation
	 * @author Ramiro.Serrato
	 */
	@RequestMapping(value = "/validation", method = RequestMethod.GET)	
	public @ResponseBody MerchandisingBaseResponse<IWrapper> validateNewBoostAndBlock(
			@RequestParam("searchTerm") String searchTerm,
			@RequestParam("searchProfileId") Long searchProfileId,
			@RequestParam("categoryId") String categoryId) {

		List<String> errorMessages = new ArrayList<String>();
		try {
			
			if(StringUtils.isEmpty(searchTerm)) {
				merchandisingBaseResponse.setErrorCode("Request.NullDataInRequest");
				errorMessages.add("SearchTerm:" + "cannot be empty");
			}
			
			if (StringUtils.isEmpty(categoryId)) {
				merchandisingBaseResponse.setErrorCode("Request.NullDataInRequest");
				errorMessages.add("categorySeq:" + "cannot be empty");
			}
			
			if(searchTerm != null && searchProfileId != null && categoryId != null) {
				Integer validation = boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
				
				switch(validation) {
				case 0:  // successful validation
					merchandisingBaseResponse.setData(new TempWrapper().setValid(true));
					merchandisingBaseResponse.setSuccessCode("BoostAndBlock.NewValidationSuccess");
					break;
				case 1:  // there is currently another boost and block with that combination
					merchandisingBaseResponse.setData(new TempWrapper().setValid(false));
					merchandisingBaseResponse.setErrorCode("BoostAndBlock.NewValidationUniqueInvalid");
					errorMessages.add("SearchTerm:" + merchandisingBaseResponse.getMessage());
					break;
				default: // 2: there is a promo with that search term in the name
					merchandisingBaseResponse.setData(new TempWrapper().setValid(false));
					merchandisingBaseResponse.setErrorCode("BoostAndBlock.NewValidationPromoInvalid");
					errorMessages.add("SearchTerm:" + merchandisingBaseResponse.getMessage());
					break;
				}
				
				if (!errorMessages.isEmpty()) {
					merchandisingBaseResponse.setStatus(ResponseStatusEnum.ERROR);
					merchandisingBaseResponse.setGeneralPurposeMessage(errorMessages);
				}
				return merchandisingBaseResponse;
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NullDataInRequest");
			}
		}
		catch(Exception e) {
			merchandisingBaseResponse.setErrorCode("BoostAndBlock.NewValidationError");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While editing a BoostAndBlock");
		}
		
		if (!errorMessages.isEmpty()) {
			merchandisingBaseResponse.setStatus(ResponseStatusEnum.ERROR);
			merchandisingBaseResponse.setGeneralPurposeMessage(errorMessages);
		}
		return merchandisingBaseResponse;
	}
	
	/**
	 * Delete an existing BoostAndBlock
	 * @param id
	 * @return MerchandisingBaseResponse
	 * @author Chanchal.Kumari
	 * Date - 4th Dec 2012
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id){
		try{
			if(id != null){
				IWrapper deletedWrapper = boostAndBlockService.deleteBoostAndBlock(id);
				merchandisingBaseResponse.setData(deletedWrapper);
				merchandisingBaseResponse.setSuccessCode("Delete.Success", "BoostAndBlock");
			}
			
			else{
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		}
		catch(Exception e){	
			merchandisingBaseResponse.setErrorCode("Delete.Error", "BoostAndBlock");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While deleting a BoostAndBlock");
		}
		
		return merchandisingBaseResponse;
	}
	
	/**
	 * Approve an existing BoostAndBlock
	 * @param id
	 * @return MerchandisingBaseResponse
	 * @author Chanchal.Kumari
	 * Date - 4th Dec 2012
	 */
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> approve(@PathVariable Long id){
		try{
			if(id != null){
				IWrapper deletedWrapper = boostAndBlockService.approveBoostAndBlock(id);
				merchandisingBaseResponse.setData(deletedWrapper);
				merchandisingBaseResponse.setSuccessCode("Approve.Success", "BoostAndBlock");
			}
			else{
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		}
		catch(Exception e){	
			merchandisingBaseResponse.setErrorCode("Approve.Error", "BoostAndBlock");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While approving a BoostAndBlock");
		}
		
		return merchandisingBaseResponse;
	}
	
	/**
	 * Approve an existing BoostAndBlock
	 * @param id
	 * @return MerchandisingBaseResponse
	 * @author Chanchal.Kumari
	 * Date - 4th Dec 2012
	 */
	@RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> reject(@PathVariable Long id){
		try{
			if(id != null){
				IWrapper deletedWrapper = boostAndBlockService.rejectBoostAndBlock(id);
				merchandisingBaseResponse.setData(deletedWrapper);
				merchandisingBaseResponse.setSuccessCode("Reject.Success", "BoostAndBlock");
			}
			else{
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		}
		catch(Exception e){	
			merchandisingBaseResponse.setErrorCode("Reject.Error", "BoostAndBlock");
			log.error("BoostAndBlockController", e, ErrorType.APPLICATION, "Error While approving an existing BoostAndBlock");
		}
		
		return merchandisingBaseResponse;
	}
}
