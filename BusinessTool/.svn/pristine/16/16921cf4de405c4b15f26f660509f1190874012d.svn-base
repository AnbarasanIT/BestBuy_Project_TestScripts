package com.bestbuy.search.merchandising.web;

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
import com.bestbuy.search.merchandising.service.IPromoService;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.wrapper.DuplicateCheckWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * Class to create/edit/delete/load the promo
 * 
 * @author Chanchal Kumari.
 *         Date - 4th Oct 2012
 **/

@RequestMapping("/promo")
@Controller
public class PromoController extends BaseController {

  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(PromoController.class.getName());

  @Autowired
  private IPromoService promoService;

  @Autowired
  private GeneralWorkflow generalWorkflow;

 
  public void setPromoService(IPromoService promoService) {
    this.promoService = promoService;
  }

  public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
    this.generalWorkflow = generalWorkflow;
  }

  /**
   * Method to load the list of banners from DB
   * 
   * @return MerchandisingBaseResponse<IWrapper>
   * @author Chanchal.Kumari
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/searchPromo", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> loadPromos(@RequestBody PaginationWrapper paginationWrapper) {
    try {
      List<PromoWrapper> wrappers = (List<PromoWrapper>) (List<?>) promoService.loadPromos(paginationWrapper);

      if (wrappers != null && wrappers.size() > 0) {
        for (PromoWrapper promoWrapper : wrappers) {
          promoWrapper.setActions(PromoWrapper.getValidActions(generalWorkflow.getActionsForStatus(promoWrapper.getStatus())));
        }
        PromoWrapper.setAndSortRows(merchandisingBaseResponse, (List<IWrapper>) (List<?>) wrappers);
      }

      merchandisingBaseResponse.setData(paginationWrapper);

      merchandisingBaseResponse.setSuccessCode("Listing.Success", "Promo");
    }

    catch (Exception e) {
      log.error("PromoController", e, ErrorType.APPLICATION, "Error while listing the promos");
      merchandisingBaseResponse.setErrorCode("Listing.Error", "Promo");
    }

    return merchandisingBaseResponse;
  }

  /**
   * Method to create a new Promo
   * 
   * @param promoWrapper
   *          A promo wrapper with the json for the object
   * @return MerchandisingBaseResponse<IWrapper> A base response with a request status
   * @author Chanchal.Kumari
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> create(@RequestBody PromoWrapper promoWrapper) {
    try {
      if (promoWrapper != null) {
        IWrapper persistedPromoWrapper = promoService.createPromo(promoWrapper);
        merchandisingBaseResponse.setData(persistedPromoWrapper);
        merchandisingBaseResponse.setSuccessCode("Create.Success", "Promo");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Create.Error", "Promo");
      log.error("PromoController", e, ErrorType.APPLICATION, "Error creating a new promo");
    }

    return merchandisingBaseResponse;
  }

  /**
   * Delete an existing promo
   * 
   * @param id
   * @return MerchandisingBaseResponse
   * @author Chanchal.Kumari
   *         Date - 10th Oct 2012
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper deletedWrapper = promoService.deletePromo(id);
        merchandisingBaseResponse.setData(deletedWrapper);
        merchandisingBaseResponse.setSuccessCode("Delete.Success", "Promo");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Delete.Error", "Promo");
      log.error("PromoController", e, ErrorType.APPLICATION, "Error deleting promo with id: " + id);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Approves a Promo with the given id
   * 
   * @param id
   *          A Long with the promo id of the promo that we want to approve
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the promo information
   * @author Chanchal.Kumari
   */
  @RequestMapping(value = "/approve/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> approve(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper approvedWrapper = promoService.approvePromo(id);
        merchandisingBaseResponse.setData(approvedWrapper);
        merchandisingBaseResponse.setSuccessCode("Approve.Success", "Promo");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Approve.Error", "Promo");
      log.error("PromoController", e, ErrorType.APPLICATION, "Error approving promo with id: " + id);
    }
    return merchandisingBaseResponse;
  }

  /**
   * Rejects a promo with the given id
   * 
   * @param id
   *          A Long with the promo id of the promo that we want to reject
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the promo information
   * @author Chanchal.Kumari
   *         Date - 10th Oct 2012
   */
  @RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> reject(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper rejectedWrapper = promoService.rejectPromo(id);
        merchandisingBaseResponse.setData(rejectedWrapper);
        merchandisingBaseResponse.setSuccessCode("Reject.Success", "Promo");
      }

      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.error(merchandisingBaseResponse.getMessage());
      }
    }

    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Reject.Error", "Promo");
      log.error("PromoController", e, ErrorType.APPLICATION, "Error rejecting promo with id: " + id);
    }

    return merchandisingBaseResponse;
  }

  /**
   * Gets the promo information by providing the id
   * 
   * @param id
   *          A Long with the promo id of the promo that we want to retrieve
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the promo information
   * @author chanchal.kumari
   *         Date - 11th Oct 2012
   */
  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> getPromo(@PathVariable Long id) {
    try {
      if (id != null) {
        merchandisingBaseResponse.setData(promoService.getPromo(id));
        merchandisingBaseResponse.setSuccessCode("Retrive.Success", "Promo");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Retrive.Error", "Promo");
      log.error("PromoController", e, ErrorType.APPLICATION, "Error retrieving promo with id: " + id);
    }

    return merchandisingBaseResponse;
  }

  /**
   * Update an existing promo
   * 
   * @param promoWrapper
   * @return MerchandisingBaseResponse<IWrapper>
   * @author Chanchal.Kumari
   *         Date 11th Oct 2012
   */
  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> update(@RequestBody PromoWrapper promoWrapper) {
    try {
      if (promoWrapper != null) {
        IWrapper updatedPromoWrapper = promoService.updatePromo(promoWrapper);
        merchandisingBaseResponse.setData(updatedPromoWrapper);
        merchandisingBaseResponse.setSuccessCode("Update.Success", "Promo");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Update.Error", "Promo");
      log.error("PromoController", e, ErrorType.APPLICATION, "Error updating promo");
    }
    return merchandisingBaseResponse;
  }
  
  
  /**
   * @param keyValueWrapper
   * @return
   */
  @RequestMapping(value = "/exists", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> isExists(@RequestBody DuplicateCheckWrapper duplicateCheckWrapper ) {
    return super.checkIfExists(duplicateCheckWrapper, promoService);
   }
}
