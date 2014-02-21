package com.bestbuy.search.merchandising.web;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.ISynonymService;
import com.bestbuy.search.merchandising.wrapper.DuplicateCheckWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * @author Kalaiselvi.Jaganathan
 *         Modified By Chanchal Kumari Added delete and update method.
 *         SynonymGroupController - Controller for synonym Entity
 *         CRUD operations for synonyms
 */
@RequestMapping("/synonymgroups")
@Controller
public class SynonymController extends BaseController {

  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(SynonymController.class.getName());

  @Autowired
  private ISynonymService synonymService;

  public void setSynonymGroupService(ISynonymService synonymService) {
    this.synonymService = synonymService;
  }

  /**
   * This controller call the service (SynonymGroupService) which calls the DAO (SynonymGroupDAO, BaseDAO)
   * to retrieve the synonym related data from the entities(tables) and map it to a JSON Object for UI
   * 
   * @return JqgridWrapper
   */
  @RequestMapping(value = "/loadSynonyms", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> list(@RequestBody PaginationWrapper paginationWrapper) {
    try {
      @SuppressWarnings("unchecked")
      List<IWrapper> wrappers = (List<IWrapper>) (List<?>) synonymService.listSynonyms(paginationWrapper);

      merchandisingBaseResponse.setRows(wrappers);
      merchandisingBaseResponse.setData(paginationWrapper);
      merchandisingBaseResponse.setSuccessCode("Listing.Success", "Synonym");
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Listing.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error retrieving Synonym data");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Create a new synonym
   * 
   * @param synonymWrapper
   * @return MerchandisingBaseResponse
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> create(@RequestBody SynonymWrapper synonymWrapper) {
    try {
      if (synonymWrapper != null) {
        IWrapper persistedPromoWrapper = synonymService.createSynonym(synonymWrapper);
        merchandisingBaseResponse.setData(persistedPromoWrapper);
        merchandisingBaseResponse.setSuccessCode("Create.Success", "Synonym");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Create.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error creating a new Synonym");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Initial data load for create synonym
   * 
   * @return String
   */
  @RequestMapping(value = "/load", method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<String> load() throws JSONException, ServiceException {
    String responseData = "";

    try {
      responseData = synonymService.loadSynonym();
    } catch (Exception e) {
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error creating a new Synonym");
    }
    return new ResponseEntity<String>(responseData, ResponseUtility.getRequestHeaders(null), HttpStatus.OK);
  }

  /**
   * Delete an existing synonym
   * 
   * @param id
   * @return MerchandisingBaseResponse
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper synonymIWrapper = synonymService.deleteSynonym(id);
        merchandisingBaseResponse.setData(synonymIWrapper);
        merchandisingBaseResponse.setSuccessCode("Delete.Success", "Synonym");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Delete.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error deleting synonym with id: " + id);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Update an existing synonym
   */
  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> update(@RequestBody SynonymWrapper synonymWrapper) {
    try {
      if (synonymWrapper != null) {
        IWrapper synonymIWrapper = synonymService.update(synonymWrapper);
        merchandisingBaseResponse.setData(synonymIWrapper);
        merchandisingBaseResponse.setSuccessCode("Update.Success", "Synonym");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Update.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error updating a synonym" + synonymWrapper);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Gets the synonym information by providing the id
   * 
   * @param id
   *          A Long with the SynonymGroup id of the synonym that we want to retrieve
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the Synonym information
   * @author Ramiro.Serrato
   */
  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> getSynonym(@PathVariable Long id) {
    try {
      if (id != null) {
        merchandisingBaseResponse.setData(synonymService.getSynonym(id));
        merchandisingBaseResponse.setSuccessCode("Retrive.Success", "Synonym");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Retrive.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error getting synonym info with id: " + id);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Approves a synonym with the given id
   * 
   * @param id
   *          A Long with the SynonymGroup id of the synonym that we want to approve
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the Synonym information
   * @author Ramiro.Serrato
   */
  @RequestMapping(value = "/approve/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> approve(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper synonymIWrapper = synonymService.approveSynonym(id);
        merchandisingBaseResponse.setData(synonymIWrapper);
        merchandisingBaseResponse.setSuccessCode("Approve.Success", "Synonym");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Approve.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error approving synonym with id: " + id);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Rejects a synonym with the given id
   * 
   * @param id
   *          A Long with the SynonymGroup id of the synonym that we want to reject
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the Synonym information
   * @author Ramiro.Serrato
   */
  @RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> reject(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper synonymIWrapper = synonymService.rejectSynonym(id);
        merchandisingBaseResponse.setData(synonymIWrapper);
        merchandisingBaseResponse.setSuccessCode("Reject.Success", "Synonym");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Reject.Error", "Synonym");
      log.error("SynonymController", e, ErrorType.APPLICATION, "Error retrieving synonym with id: " + id);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Temporary method for mocking a response for the validation stuff
   * 
   * @return A MerchandisingBaseResponse<IWrapper>
   * @author Ramiro.Serrato
   */
  @RequestMapping(value = "/mock", method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> validatorMock() {
    merchandisingBaseResponse.setErrorCode("000002");
    List<String> lista = new ArrayList<String>();
    lista.add("primaryTerm,The primary term must be valid");
    merchandisingBaseResponse.setGeneralPurposeMessage(lista);

    return merchandisingBaseResponse;
  }

  /**
   * @param keyValueWrapper
   * @return
   */
  @RequestMapping(value = "/checkIfExists", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> checkIfExists(@RequestBody DuplicateCheckWrapper duplicateCheckWrapper) {
    return checkIfExists(duplicateCheckWrapper, synonymService);
  }
}