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
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.service.IBannerService;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.DuplicateCheckWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 *Class to create/edit/delete/load the banner
 * @author Chanchal Kumari,09/24/2012
 * Modified By Kalaiselvi Jaganathan - Added create/edit/load Banner and delete context functionality
 * Modified By Chanchal Kumari - Added returned entity to the response.
 **/

@RequestMapping("/banner")
@Controller
public class BannerController  extends BaseController{

	private final static BTLogger log = BTLogger.getBTLogger(BannerController.class.getName());

	@Autowired
	private IBannerService bannerService;

	@Autowired
	private GeneralWorkflow generalWorkflow;

	public void setBannerService(IBannerService bannerService) {
		this.bannerService = bannerService;
	}

	/**
	 * @param synonymWorkflow the synonymWorkflow to set
	 */
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	/**
	 * Method to load the list of banners from DB
	 * @return @ResponseBody
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadBanners",method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadBanners(@RequestBody PaginationWrapper paginationWrapper){
		try {
			List<Banner> banners = bannerService.loadBanners(paginationWrapper);
			List<BannerWrapper> wrappers = (List<BannerWrapper>) (List<?>)BannerWrapper.fetchBanners(banners);
			
			for(BannerWrapper bannerWrapper : wrappers){
				bannerWrapper.setActions(BannerWrapper.fetchActions(generalWorkflow.getActionsForStatus(bannerWrapper.getStatus())));
			}
			
			merchandisingBaseResponse.setRows((List<IWrapper>)(List<?>)wrappers);
			merchandisingBaseResponse.setData(paginationWrapper);
			merchandisingBaseResponse.setSuccessCode("Listing.Success", "Banner");
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Listing.Error", "Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, merchandisingBaseResponse.getMessage());
		}
		
		return merchandisingBaseResponse;
	}

	/**
	 * Method to delete a Banner
	 * @param redirectId
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> delete(@PathVariable Long id) {
		try {
			if (id != null) {
				IWrapper wrapper = bannerService.deleteBanner(id);
				merchandisingBaseResponse.setData(wrapper);
				merchandisingBaseResponse.setSuccessCode("Delete.Success", "Banner");
			} else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Delete.Error","Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to delete a banner");
		}
		return merchandisingBaseResponse;
	}
	
	/**
	 * Approves a banner with the given id
	 * @param id A Long with the banner id of the banner that we want to approve
	 * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the banner information
	 * @author chanchal.kumari
	 */
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> approve(@PathVariable Long id) {
		try {
			if(id != null) {
				IWrapper wrapper = bannerService.approveBanner(id);	
				merchandisingBaseResponse.setData(wrapper);
				merchandisingBaseResponse.setSuccessCode("Approve.Success", "Banner");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} 
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Approve.Error", "Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to approve a banner");
		}

		return merchandisingBaseResponse;
	}	

	/**
	 * Reject a banner with the given id
	 * @param id A Long with the banner id of the banner that we want to approve
	 * @return A MerchandisingBaseResponse<IWrapper> which is the generic response wrapping the banner information
	 * @author chanchal.kumari
	 */
	@RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> reject(@PathVariable Long id) {
		try {
			if(id != null) {
				IWrapper wrapper = bannerService.rejectBanner(id);	
				merchandisingBaseResponse.setData(wrapper);			
				merchandisingBaseResponse.setSuccessCode("Reject.Success", "Banner");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		}
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Reject.Error", "Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to reject a banner");
		}

		return merchandisingBaseResponse;
	}	

	/**
	 * Method to create a new banner
	 * @param bannerWrapper
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> create(@RequestBody BannerWrapper bannerWrapper) {
		try {
			if (bannerWrapper != null) {
				IWrapper wrapper = bannerService.createBanner(bannerWrapper);	
				merchandisingBaseResponse.setData(wrapper);
				merchandisingBaseResponse.setSuccessCode("Create.Success", "Banner");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Create.Error", "Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to create a banner");
		}
		return merchandisingBaseResponse;
	}

	/**
	 * Method to UPDATE the existing banner data
	 * 
	 * @param redirectWrapper
	 * @return JsonResponse
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> update(@RequestBody BannerWrapper bannerWrapper) {
		try {
			if (bannerWrapper != null) {
				IWrapper wrapper = bannerService.updateBanner(bannerWrapper);
				merchandisingBaseResponse.setData(wrapper);
				merchandisingBaseResponse.setSuccessCode("Update.Success", "Banner");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Update.Error", "Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to update an existing banner");
		}
		return merchandisingBaseResponse;
	}

	/**
	 * Method to load the data for edit pop-up banner
	 *
	 * @param BannerId
	 * @return JsonResponse
	 */

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> loadeditbanner(@PathVariable Long id) {
		try {
			if(id != null) {
				merchandisingBaseResponse.setData(bannerService.loadeditbanner(id));
				merchandisingBaseResponse.setSuccessCode("Retrive.Success", "Banner");
			}
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} 
		catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Retrive.Error", "Banner");
			log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to edit an existing banner");
		}
		return merchandisingBaseResponse;
	}

	/**
	 * Method to delete a Context
	 * @param Context ID
	 * @return MerchandisingBaseResponse<IWrapper>
	 */
	@RequestMapping(value = "/delete/context/{id}",method = RequestMethod.PUT)
	public @ResponseBody MerchandisingBaseResponse<IWrapper> deleteContext(@PathVariable Long id) {
		try {
			if (id != null) {
				bannerService.deleteBannerContext(id);
				merchandisingBaseResponse.setSuccessCode("Delete.Success", "BannerContext");
			} 
			else {
				merchandisingBaseResponse.setErrorCode("Request.NoData");
			}
		} catch (Exception e) {
			merchandisingBaseResponse.setErrorCode("Delete.Error", "BannerContext");
      log.error("BannerController", e, ErrorType.APPLICATION, "An exception was thrown while attempting to delete a context");
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
    return checkIfExists(duplicateCheckWrapper, bannerService);
  }
}

