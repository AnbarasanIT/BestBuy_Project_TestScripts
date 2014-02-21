/**
 * 
 */
package com.bestbuy.search.merchandising.web;

import static com.bestbuy.search.merchandising.common.ErrorType.APPLICATION;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.service.AdminService;
import com.bestbuy.search.merchandising.service.SkuHistoryService;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.SkuHistoryWrapper;
import com.bestbuy.search.merchandising.wrapper.UserWrapper;

/**
 * Controller for the Admin related operations.
 * 
 * @author Sreedhar Patlola
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(FacetController.class.getName());

  @Autowired
  AdminService adminService;

  @Autowired
  SkuHistoryService skuHistoryService;

  @Autowired
  private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

  /**
   * Method to load the list of the current users.
   * 
   * @return the MerchandisingBaseResponse obj.
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> loadUsers() {

    try {
      List<UserWrapper> wrappers = (List<UserWrapper>) (List<?>) adminService.load();
      if (CollectionUtils.isNotEmpty(wrappers)) {
        for (UserWrapper userWrapper : wrappers) {
          userWrapper.setActions(getValidActions());

        }
        merchandisingBaseResponse.setRows((List<IWrapper>) (List<?>) wrappers);
      }
      merchandisingBaseResponse.setSuccessCode("Listing.Success", "User");
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Listing.Error", "User");

      log.error("AdminController", e, APPLICATION, "There was trouble getting the list of current users");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Method to create a new user
   * 
   * @param userWrapper
   *          the UserWrapper obj.
   * @return the MerchandisingBaseResponse obj.
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> create(@RequestBody UserWrapper userWrapper) {
    try {
      if (userWrapper != null) {
        userWrapper.setActive(true);
        IWrapper persistedUserWrapper = adminService.createUser(userWrapper);
        merchandisingBaseResponse.setData(persistedUserWrapper);
        merchandisingBaseResponse.setSuccessCode("Create.Success", "user");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Create.Error", "user");
      log.error("AdminController", e, APPLICATION, "There was trouble creating a new user");
    }

    return merchandisingBaseResponse;
  }

  /**
   * Method to update a user
   * 
   * @param userWrapper
   *          the userWrapper obj.
   * @return the MerchandisingBaseResponse obj.
   */
  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> update(@RequestBody UserWrapper userWrapper) {
    try {
      if (userWrapper != null) {
        IWrapper updatedPromoWrapper = adminService.updateUser(userWrapper);
        merchandisingBaseResponse.setData(updatedPromoWrapper);
        merchandisingBaseResponse.setSuccessCode("Update.Success", "user");
      }

      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.debug(merchandisingBaseResponse.getMessage());
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Update.Error", "user");
      log.error("AdminController", e, APPLICATION, "An exception was thrown while updating a user");
    }

    return merchandisingBaseResponse;
  }

  /**
   * Method to load the user to edit.
   * 
   * @param id
   *          the id of a user
   * @return the MerchandisingBaseResponse obj.
   */
  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> edit(@PathVariable Long id) {
    try {
      if (id != null) {
        merchandisingBaseResponse.setData(adminService.getUser(id));
        merchandisingBaseResponse.setSuccessCode("Retrive.Success", "User");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Retrive.Error", "User");
      log.error("AdminController", e, APPLICATION, "An exception was thrown while editing a user");
    }

    return merchandisingBaseResponse;
  }

  /**
   * Method to delete the user.
   * 
   * @param id
   *          the id of a user
   * @return the MerchandisingBaseResponse obj.
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper deletedWrapper = adminService.deleteUser(id);
        merchandisingBaseResponse.setData(deletedWrapper);
        merchandisingBaseResponse.setSuccessCode("Delete.Success", "User");
      }

      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Delete.Error", "User");
      log.error("AdminController", e, APPLICATION, "An exception was thrown while deleting a user");
    }
    return merchandisingBaseResponse;
  }

  public static List<KeyValueWrapper> getValidActions() {
    List<KeyValueWrapper> dropdowns = new ArrayList<KeyValueWrapper>();
    dropdowns.add(new KeyValueWrapper(EDIT.getName(), EDIT.getName()));
    return dropdowns;
  }

  /**
   * @param adminService
   *          the adminService to set
   */
  public void setAdminService(AdminService adminService) {
    this.adminService = adminService;
  }

  /**
   * @param merchandisingBaseResponse
   *          the merchandisingBaseResponse to set
   */
  public void setMerchandisingBaseResponse(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
    this.merchandisingBaseResponse = merchandisingBaseResponse;
  }

  /**
   * Method to load the list of the Skus.
   * 
   * @return the MerchandisingBaseResponse obj.
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/loadSkus", method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> loadSkus() {
    try {
      List<SkuHistoryWrapper> wrappers = (List<SkuHistoryWrapper>) (List<?>) skuHistoryService.load();
      if (CollectionUtils.isNotEmpty(wrappers)) {

        merchandisingBaseResponse.setRows((List<IWrapper>) (List<?>) wrappers);
      }
      merchandisingBaseResponse.setSuccessCode("Listing.Success", "SkuHistory");
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Listing.Error", "SkuHistory");  
      log.error("AdminController", e, APPLICATION, "An exception was thrown while loading the list of SKUs");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Method to add the sku with delete action.
   * 
   * @param id
   *          the id of a user
   * @return the MerchandisingBaseResponse obj.
   */
  @RequestMapping(value = "/addSku/{skuId}/{action}", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> addSku(@PathVariable String skuId, @PathVariable String action) {
    try {
      if (skuId != null) {
        IWrapper deletedWrapper = skuHistoryService.addSku(skuId, action);
        merchandisingBaseResponse.setData(deletedWrapper);
        merchandisingBaseResponse.setSuccessCode("RemoveSku.Success");
      }
      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    }
    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("RemoveSku.Error");
      log.error("AdminController", e, APPLICATION, "An exception was thrown while adding SKU with delete action");
    }

    return merchandisingBaseResponse;
  }

}
