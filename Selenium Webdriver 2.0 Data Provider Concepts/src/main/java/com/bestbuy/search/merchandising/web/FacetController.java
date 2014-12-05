
package com.bestbuy.search.merchandising.web;

import java.util.List;

import org.apache.log4j.Logger;
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
import com.bestbuy.search.merchandising.dao.FacetAttributeValueOrderDAO;
import com.bestbuy.search.merchandising.service.IFacetService;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.wrapper.DuplicateCheckWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Class to create/edit/delete/load the facets
 * @author Chanchal Kumari
 * Date - 24th Oct 2012
 **/

@RequestMapping("/facets")
@Controller
public class FacetController extends BaseController {

  private final static BTLogger log = BTLogger.getBTLogger(FacetController.class.getName());

	@Autowired
	private IFacetService facetService;

	@Autowired
	private GeneralWorkflow generalWorkflow;

	/**
	 * @param to set the generalWorkflow 
	 */
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	public void setFacetService(IFacetService facetService) {
		this.facetService = facetService;
	}

	/**
	 * Method to load the list of facets from DB
	 * @return @ResponseBody
	 * @author Chanchal Kumari
	 * Date - 24th Oct 2012
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchFacets", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadFacets(@RequestBody PaginationWrapper paginationWrapper){

		try {
			List<FacetWrapper> wrappers = facetService.loadFacets(paginationWrapper);

			if(wrappers != null && wrappers.size() > 0){
				for(FacetWrapper facetWrapper : wrappers){
					facetWrapper.setActions(FacetWrapper.getActions(generalWorkflow.getActionsForStatus(facetWrapper.getStatus())));
				}
			}
			merchandisingBaseResponse.setRows((List<IWrapper>)(List<?>)wrappers);
			merchandisingBaseResponse.setData(paginationWrapper);
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "Facet");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Listing.Error", "Facet");
			log.error("FacetController", e, ErrorType.APPLICATION, "Error while listing the Facets");
		}

		return merchandisingBaseResponse;
	}

	/**
	 * Delete an existing facet
	 * @param id
	 * @return MerchandisingBaseResponse
	 * @author Chanchal.Kumari
	 * Date - 25th Oct 2012
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id){
		try{
			if(id != null){
				facetService.deleteFacet(id);
				merchandisingBaseResponse.setSuccessCode("Delete.Success", "Facet");
			}
			else{
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		}

		catch(Exception e){	
			merchandisingBaseResponse.setErrorCode("Delete.Error", "Facet");
			log.error("FacetController", e, ErrorType.APPLICATION, "Error deleting an existing facet");
		}

		return merchandisingBaseResponse;
	}

	/**
	 * Approves a Facet with the given id
	 * @param id A Long with the Facet id of the Facet that we want to approve
	 * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the Facet information
	 * @author Chanchal.Kumari
	 */
	@RequestMapping(value = "/approve", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> approve(@RequestParam("id") Long id) {
		try {
			if(id != null) {
				facetService.approveFacet(id);				
				merchandisingBaseResponse.setSuccessCode("Approve.Success", "Facet");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
				log.info(merchandisingBaseResponse.getMessage());
			}
		} 

		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Approve.Error", "Facet");
			log.error("FacetController", e, ErrorType.APPLICATION, "Error approving facets with Id: " + id);
		}

		return merchandisingBaseResponse;
	}	

	/**
	 * Rejects a Facet with the given id
	 * @param id A Long with the Facet id of the Facet that we want to reject
	 * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the Facet information
	 * @author Chanchal.Kumari
	 * Date - 10th Oct 2012
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> reject(@RequestParam("id") Long id) {
		try {
			if(id != null) {
				facetService.rejectFacet(id);				
				merchandisingBaseResponse.setSuccessCode("Reject.Success", "Facet");
			}

			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
				log.info(merchandisingBaseResponse.getMessage());
			}
		}

		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Reject.Error", "Facet");
			log.error("FacetController", e, ErrorType.APPLICATION, "Error rejecting facet with ID: " + id);
		}

		return merchandisingBaseResponse;
	}

	/**
	 * Method to create a new Facet
	 * @param facetWrapper
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> create(@RequestBody FacetWrapper facetWrapper) {
		try {
			if (facetWrapper != null) {
				//Call the service to create facet by passing the facetwrapper
				facetService.createFacet(facetWrapper);
				merchandisingBaseResponse.setSuccessCode("Create.Success", "Facet");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			log.error("FacetController", e, ErrorType.APPLICATION, "Error creating a new facet");
			merchandisingBaseResponse.setErrorCode("Create.Error", "Facet");
			return merchandisingBaseResponse;
		}
		return merchandisingBaseResponse;
	}

	/**
	 * Method to update a Facet
	 * @param facetWrapper
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> update(@RequestBody FacetWrapper facetWrapper) {
		try {
			if (facetWrapper != null) {
				//Call the service to update facet by passing the facetwrapper
				facetService.updateFacet(facetWrapper);
				merchandisingBaseResponse.setSuccessCode("Update.Success", "Facet");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			log.error("FacetController", e, ErrorType.APPLICATION, "Error updating a facet");
			merchandisingBaseResponse.setErrorCode("Update.Error", "Facet");
			return merchandisingBaseResponse;
		}
		return merchandisingBaseResponse;
	}

	/**
	 * @param id
	 * @return MerchandisingBaseResponse
	 * @author Chanchal.Kumari
	 * Date - 16th Nov 2012
	 */
	@RequestMapping(value = "/exists", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> isExists(@RequestBody DuplicateCheckWrapper duplicateCheckWrapper) {
	  return super.checkIfExists(duplicateCheckWrapper, facetService);
  }

	/**
	 * Method to load the edit pop-up data for a facet
	 *
	 * @param facetId
	 * @return JsonResponse
	 */

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadEditFacet(@PathVariable Long id) {
		try {
			if(id != null) {
				merchandisingBaseResponse.setData(facetService.loadEditFacetData(id));
				merchandisingBaseResponse.setSuccessCode("Retrive.Success", "Facet");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} 
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Retrive.Error", "Facet");
			log.error("FacetController", e, ErrorType.APPLICATION, "Error loading facet data with id: " + id);
		}
		return merchandisingBaseResponse;
	}
	
	/**
	 * Load the attributes for a facet
	 * @param catgId
	 * @return merchandisingBaseResponse
	 */
	@RequestMapping( value = "attributeValue/{id}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadFacetAttributeValues(@PathVariable Long id) {
		try {
			if(id != null) {
				//Get the list of attribute values for the given facet
				List<IWrapper> wrappers = facetService.loadFacetAttributeValues(id);
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
			log.error("FacetController", e, ErrorType.APPLICATION, "Error loading facet data with ID: " + id);
		}	
		return merchandisingBaseResponse;
	}
}
