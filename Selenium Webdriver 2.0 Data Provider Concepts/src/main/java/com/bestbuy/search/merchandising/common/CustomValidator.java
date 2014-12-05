package com.bestbuy.search.merchandising.common;
import static com.bestbuy.search.merchandising.common.ErrorType.APPLICATION;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bestbuy.search.merchandising.service.IBannerService;
import com.bestbuy.search.merchandising.service.IFacetService;
import com.bestbuy.search.merchandising.service.IKeywordRedirectService;
import com.bestbuy.search.merchandising.service.IPromoService;
import com.bestbuy.search.merchandising.service.ISynonymService;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

@Configurable
public class CustomValidator implements ConstraintValidator<ValidWrapper, IWrapper> {

  private final static BTLogger log = BTLogger.getBTLogger(CustomValidator.class.getName());

  private ValidWrapper validWrapper;

  @Autowired
  private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

  @Autowired
  private ISynonymService synonymService;

  @Autowired
  private IBannerService bannerService;
  
  @Autowired
  private IPromoService promoService;
  
  
  @Autowired
  private IFacetService facetService;
  
  @Autowired
  private IKeywordRedirectService keywordRedirectService;


  public void setMerchandisingBaseResponse(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
    this.merchandisingBaseResponse = merchandisingBaseResponse;
  }

  @Override
  public void initialize(ValidWrapper validWrapper) {
    this.validWrapper = validWrapper;
  }

  public boolean isValid(IWrapper wrapper, ConstraintValidatorContext cvc) {

    if (wrapper instanceof PromoWrapper) {
      return validatePromo((PromoWrapper) wrapper, cvc);
    }

    if (wrapper instanceof KeywordRedirectWrapper) {
      return validateRedirect((KeywordRedirectWrapper) wrapper, cvc);
    }

    if (wrapper instanceof BannerWrapper) {
      return validateBanner((BannerWrapper) wrapper, cvc);
    }

    if (wrapper instanceof FacetWrapper) {
      return validateFacet((FacetWrapper) wrapper, cvc);
    }

    if (wrapper instanceof SynonymWrapper) {
      return validateSynonym((SynonymWrapper) wrapper, cvc);
    }

    return true;
  }

  public boolean validateSynonym(SynonymWrapper synonymWrapper, ConstraintValidatorContext cvc) {
    boolean valid = true;

    try {
      if (synonymWrapper.getPrimaryTerm() != null) {
        boolean isNewInput = true;
        if (synonymWrapper.getSynonymId() != null) {
          SynonymWrapper currentSynonym = synonymService.getSynonym(synonymWrapper.getSynonymId());
          if (!StringUtils.equals(currentSynonym.getPrimaryTerm(), synonymWrapper.getPrimaryTerm())) {
            isNewInput = true;
          } else {
            isNewInput = false;
          }

        }
        valid = !synonymService.isExists(new KeyValueWrapper("primaryTerm", synonymWrapper.getPrimaryTerm()), isNewInput);
        processConstraintViolation("Bean.NotValidPrimaryTerm", "primaryTerm", cvc);
      }
    } catch (Throwable t) {
      merchandisingBaseResponse.setErrorCode("Validation.Error");
      log.error("CustomValidator.validateSynonym", t , APPLICATION, "Error during synonym validation");
    }
    return valid;
  }
 
  public boolean validatePromo(PromoWrapper promoWrapper, ConstraintValidatorContext cvc) {

    boolean valid = true;
    
    try {
      if (promoWrapper.getPromoName() != null) {
        boolean isNewInput = true;
        if (promoWrapper.getPromoId() != null) {
          PromoWrapper currentPromo = promoService.getPromo(promoWrapper.getPromoId());
          if (!StringUtils.equals(currentPromo.getPromoName(), promoWrapper.getPromoName())) {
            isNewInput = true;
          } else {
            isNewInput = false;
          }

        }
        valid = !promoService.isExists(new KeyValueWrapper("promoName", promoWrapper.getPromoName()), isNewInput);
        processConstraintViolation("Bean.NotValidPromoName", "promoName", cvc);
      }

    } catch (Throwable t) {
      merchandisingBaseResponse.setErrorCode("Validation.Error");
      log.error("CustomValidator.validatePromo", t, APPLICATION, merchandisingBaseResponse.getMessage());
    }

    if (promoWrapper.getEndDate() != null && promoWrapper.getStartDate().after(promoWrapper.getEndDate())) {
      processConstraintViolation("Bean.NotValidEndDate", "endDate", cvc);
      valid = false;
    }

    return valid;
  }

  public boolean validateRedirect(KeywordRedirectWrapper redirectWrapper, ConstraintValidatorContext cvc) {

    boolean valid = true;

    if (redirectWrapper.getEndDate() != null && redirectWrapper.getStartDate().after(redirectWrapper.getEndDate())) {
      processConstraintViolation("Bean.NotValidEndDate", "endDate", cvc);
      valid = false;
    }
    
    try {
      if (redirectWrapper.getRedirectTerm() != null) {
        boolean isNewInput = true;
        if (redirectWrapper.getRedirectId() != null) {
          List<KeywordRedirectWrapper> loadRedirects = keywordRedirectService.loadRedirect(redirectWrapper.getRedirectId());
          KeywordRedirectWrapper currentRedirect = loadRedirects.get(0);
          if (!StringUtils.equals(currentRedirect.getRedirectTerm(), redirectWrapper.getRedirectTerm())) {
            isNewInput = true;
          } else {
            isNewInput = false;
          }

        }
        valid = !keywordRedirectService.isExists(new KeyValueWrapper("redirectTerm", redirectWrapper.getRedirectTerm()), isNewInput);
        processConstraintViolation("Bean.NotValidKeyword", "redirectTerm", cvc);
      }

    } catch (Throwable t) {
      merchandisingBaseResponse.setErrorCode("Validation.Error");
      log.error("CustomValidator.validateRedirect", t, APPLICATION, merchandisingBaseResponse.getMessage());
    }

    return valid;
  }

  public boolean validateBanner(BannerWrapper bannerWrapper, ConstraintValidatorContext cvc) {

    boolean valid = true;

    try {
      if (bannerWrapper.getBannerName() != null) {
        boolean isNewInput = true;
        if (bannerWrapper.getBannerId() != null) {
          BannerWrapper currentBanner = bannerService.loadeditbanner(bannerWrapper.getBannerId());
          if (!StringUtils.equals(currentBanner.getBannerName(), bannerWrapper.getBannerName())) {
            isNewInput = true;
          } else {
            isNewInput = false;
          }

        }
        valid = !bannerService.isExists(new KeyValueWrapper("bannerName", bannerWrapper.getBannerName()), isNewInput);
        processConstraintViolation("Bean.NotValidBannerName", "bannerName", cvc);
      }

    } catch (Throwable t) {
      merchandisingBaseResponse.setErrorCode("Validation.Error");
      log.error("CustomValidator.validateBanner", t, APPLICATION, merchandisingBaseResponse.getMessage());
    }

    if (bannerWrapper.getEndDate() != null && bannerWrapper.getStartDate().after(bannerWrapper.getEndDate())) {
      processConstraintViolation("Bean.NotValidEndDate", "endDate", cvc);
      valid = false;
    }
    if (bannerWrapper.getContexts() != null) {
      for (int i = 0; i < bannerWrapper.getContexts().size(); i++) {
        if (bannerWrapper.getContexts().get(i).getKeywords().equals("")) {
          processConstraintViolation("Bean.NotValidKeyWord", "<context[" + i + "]>keywords", cvc);
          valid = false;
        }
      }

    }
    if (! CollectionUtils.isNotEmpty(bannerWrapper.getContexts())){
   	 processConstraintViolation("Bean.NotValidCategoryWrapper","addPages", cvc);
        valid = false;
    }
    return valid;
  }

  public boolean validateFacet(FacetWrapper facetWrapper, ConstraintValidatorContext cvc) {

    boolean valid = true;
    
    try {
      if (facetWrapper.getSystemName() != null) {
        boolean isNewInput = true;
        if (facetWrapper.getFacetId() != null) {
          FacetWrapper currentFacet= facetService.loadEditFacetData(facetWrapper.getFacetId());
          if (!StringUtils.equals(currentFacet.getSystemName(), facetWrapper.getSystemName())) {
            isNewInput = true;
          } else {
            isNewInput = false;
          }

        }
        valid = !facetService.isExists(new KeyValueWrapper("systemName", facetWrapper.getSystemName()), isNewInput);
        processConstraintViolation("Bean.NotValidSystemName", "systemName", cvc);
      }

    } catch (Throwable t) {
      merchandisingBaseResponse.setErrorCode("Validation.Error");
      log.error("CustomValidator.validateFacet", t, APPLICATION, merchandisingBaseResponse.getMessage());
    }

    if (facetWrapper.getSystemName() != null) {

      if (facetWrapper.getSystemName().length() > 100) {
        processConstraintViolation("Bean.NotValidSize", "systemName", cvc);
        valid = false;
      }
    }

    if (facetWrapper.getDisplayName() != null) {

      if (facetWrapper.getDisplayName().length() > 100) {
        processConstraintViolation("Bean.NotValidSize", "displayName", cvc);
        valid = false;
      }
    }
    
    if (CollectionUtils.isNotEmpty(facetWrapper.getCategoryWrapper())){
        for (int i = 0; i < facetWrapper.getCategoryWrapper().size(); i++) {
        	
        	  if (facetWrapper.getCategoryWrapper().get(i).getCategoryId() == null) {
                  processConstraintViolation("Bean.NotValidCategoryId", "<context[" + i + "]>categoryId", cvc);
                  valid = false;
                }
        }
    }
    if (CollectionUtils.isNotEmpty(facetWrapper.getCategoryWrapper())){
        for (int i = 0; i < facetWrapper.getCategoryWrapper().size(); i++) {
	          if (facetWrapper.getCategoryWrapper().get(i).getDisplayDepFacet().equals("Y") && facetWrapper.getCategoryWrapper().get(i).getDepFacetId() == null) {
	            processConstraintViolation("Bean.NotValidDepFacets", "<context[" + i + "]>depFacetId", cvc);
	            valid = false;
	          }
        }
    }
	        
    if (! CollectionUtils.isNotEmpty(facetWrapper.getCategoryWrapper())){
    	 processConstraintViolation("Bean.NotValidCategoryWrapper","addPages", cvc);
         valid = false;
    }

    return valid;
  }

  public void processConstraintViolation(String message, String field, ConstraintValidatorContext cvc) {
    cvc.disableDefaultConstraintViolation();

    if (field == null) {
      cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
    else {
      cvc.buildConstraintViolationWithTemplate(message).addNode(field).addConstraintViolation();
    }
  }
}
