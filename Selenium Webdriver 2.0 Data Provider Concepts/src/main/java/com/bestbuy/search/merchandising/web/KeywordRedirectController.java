package com.bestbuy.search.merchandising.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.service.IKeywordRedirectService;
import com.bestbuy.search.merchandising.service.ISearchProfileService;
import com.bestbuy.search.merchandising.service.IStatusService;
import com.bestbuy.search.merchandising.wrapper.DuplicateCheckWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Class to create/edit/delete/load the redirect URLs
 * 
 * @author Chanchal Kumari,09/08/2012
 *         Modified By Kalaiselvi Jaganathan Added update and load(initial data load for create and edit pop-up) methods
 *         Modified By Chanchal Kumari ,Date - 9th NOv 2012
 */

@RequestMapping("/redirect")
@Controller
public class KeywordRedirectController extends BaseController {

  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(KeywordRedirectController.class.getName());

  @Autowired
  private IKeywordRedirectService keywordRedirectService;

  @Autowired
  private IStatusService statusService;

  @Autowired
  private ISearchProfileService searchProfileService;

  public void setKeywordRedirectService(IKeywordRedirectService keywordRedirectService) {
    this.keywordRedirectService = keywordRedirectService;
  }

  public enum redirectType {
    URL
  };

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
  }

  /**
   * Method to load the list of Redirect URL's from DB
   * 
   * @return @ResponseBody
   */
  @RequestMapping(value = "/loadRedirects", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> loadRedirects(@RequestBody PaginationWrapper paginationWrapper) {
    try {
      @SuppressWarnings("unchecked")
      List<IWrapper> wrappers = (List<IWrapper>) (List<?>) keywordRedirectService.loadRedirects(paginationWrapper);
      merchandisingBaseResponse.setRows(wrappers);
      merchandisingBaseResponse.setData(paginationWrapper);
      merchandisingBaseResponse.setSuccessCode("Listing.Success", "Redirect");
    }

    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Listing.Error", "Redirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, merchandisingBaseResponse.getMessage());
    }
    return merchandisingBaseResponse;
  }

  /**
   * Method to load the redirect URL
   * 
   * @param redirectWrapper
   * @return JsonResponse
   */
  @RequestMapping(value = "/loadRedirect/{id}", method = RequestMethod.GET)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> loadRedirect(@PathVariable Long id) {
    try {
      if (id != null) {
        @SuppressWarnings("unchecked")
        List<IWrapper> wrappers = (List<IWrapper>) (List<?>) keywordRedirectService.loadRedirect(id);
        merchandisingBaseResponse.setRows(wrappers);
        merchandisingBaseResponse.setSuccessCode("Retrive.Success", "Loaded Requested Data");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Retrieve.Error", "Keyword Redirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error loading the redirect URL");
    }

    return merchandisingBaseResponse;
  }

  /**
   * Method to create a new redirect URL
   * 
   * @param redirectWrapper
   * @return MerchandisingBaseResponse<IWrapper>
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> create(@RequestBody KeywordRedirectWrapper redirectWrapper) {
    try {
      if (redirectWrapper != null) {
        IWrapper wrapper = keywordRedirectService.createKeywordRedirect(redirectWrapper);
        merchandisingBaseResponse.setData(wrapper);
        merchandisingBaseResponse.setSuccessCode("Create.Success", "KeywordRedirect");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Create.Error", "KeywordRedirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error creating a new redirect");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Method to UPDATE the existing keyword redirect data
   * 
   * @param redirectWrapper
   * @return JsonResponse
   */
  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> update(@RequestBody KeywordRedirectWrapper redirectWrapper) {
    try {
      if (redirectWrapper != null) {
        IWrapper wrapper = keywordRedirectService.updateRedirect(redirectWrapper);
        merchandisingBaseResponse.setData(wrapper);
        merchandisingBaseResponse.setSuccessCode("Update.Success", "KeywordRedirect");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Update.Error", "KeywordRedirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error updating an existing redirect");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Method to delete a redirect URL
   * 
   * @param redirectWrapper
   * @return JsonResponse
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper wrapper = keywordRedirectService.deleteRedirect(id);
        merchandisingBaseResponse.setData(wrapper);
        merchandisingBaseResponse.setSuccessCode("Delete.Success", "KeywordRedirect");
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Delete.Error", "KeywordRedirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error deleting a redirect URL");
    }
    return merchandisingBaseResponse;
  }

  /**
   * Method to load intial data for create keyword redirect
   * 
   * @param redirectWrapper
   * @return JsonResponse
   */
  @RequestMapping(value = "/load", method = RequestMethod.GET)
  public @ResponseBody
  Object loadRedirectInfo() {
    Map<String, List> merchconstants = new HashMap<String, List>();
    try {
      ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      HttpSession session = attribute.getRequest().getSession(true);
      if (session != null && session.getAttribute("merchconstants") != null) {
        return session.getAttribute("merchconstants");
      } else {
        {
          // Get the status data from STATUS table
          List<Status> status = new ArrayList<Status>();
          status = (List<Status>) statusService.retrieveAll();
          // Get the search profile data
          List<SearchProfile> searchProfile = new ArrayList<SearchProfile>();
          searchProfile = (List<SearchProfile>) searchProfileService.retrieveAll();
          // Get the redirect type
          List<redirectType> type = new ArrayList<redirectType>();
          // Save the data into a map
          type.add(redirectType.URL);
          merchconstants.put("Status", status);
          merchconstants.put("SearchProfile", searchProfile);
          merchconstants.put("RedirectType", type);
          session.setAttribute("merchconstants", merchconstants);
          return session.getAttribute("merchconstants");
        }
      }
    } catch (Exception e) {
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error getting initial redirect data");
      return null;
    }
  }

  /**
   * Approves a keyword redirect with the given id
   * 
   * @param id
   *          A Long with the Keyword Redirect id of the synonym that we want to approve
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the response data
   * @author Ramiro.Serrato
   */
  @RequestMapping(value = "/approve/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> approve(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper wrapper = keywordRedirectService.approveKeywordRedirect(id);
        merchandisingBaseResponse.setData(wrapper);
        merchandisingBaseResponse.setSuccessCode("Approve.Success", "Keyword Redirect");
      }

      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    }

    catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Approve.Error", "Keyword Redirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error approving redirect with id: " + id);
    }

    return merchandisingBaseResponse;
  }

  /**
   * Rejects a keyword redirect with the given id
   * 
   * @param id
   *          A Long with the Keyword Redirect id of the synonym that we want to reject
   * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the response data
   * @author Ramiro.Serrato
   */
  @RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> reject(@PathVariable Long id) {
    try {
      if (id != null) {
        IWrapper wrapper = keywordRedirectService.rejectKeywordRedirect(id);
        merchandisingBaseResponse.setData(wrapper);
        merchandisingBaseResponse.setSuccessCode("Reject.Success", "Keyword Redirect");
      }

      else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
        log.info(merchandisingBaseResponse.getMessage());
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("Reject.Error", "Keyword Redirect");
      log.error("KeywordRedirectController", e, ErrorType.APPLICATION, "Error rejecting redirect with id: " + id);
    }

    return merchandisingBaseResponse;
  }

  /**
   * @param keyValueWrapper
   * @return
   */
  @RequestMapping(value = "/exists", method = RequestMethod.POST)
  public @ResponseBody
  MerchandisingBaseResponse<IWrapper> isExists(@RequestBody DuplicateCheckWrapper duplicateCheckWrapper) {
    return checkIfExists(duplicateCheckWrapper, keywordRedirectService);
  }

}
