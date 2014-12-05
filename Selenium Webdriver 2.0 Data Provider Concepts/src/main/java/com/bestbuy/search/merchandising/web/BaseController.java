/**
 * 
 */
package com.bestbuy.search.merchandising.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.service.IBaseService;
import com.bestbuy.search.merchandising.wrapper.DuplicateCheckWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * @author Sreedhar Patlola
 */
public class BaseController {

  private final static BTLogger log = BTLogger.getBTLogger(BaseController.class.getName());

  @Autowired
  protected MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

  public void setMerchandisingBaseResponse(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
    this.merchandisingBaseResponse = merchandisingBaseResponse;
  }

  protected MerchandisingBaseResponse<IWrapper> checkIfExists(DuplicateCheckWrapper duplicateCheckWrapper, IBaseService baseService) {
    try {
      if (duplicateCheckWrapper != null) {
        List<String> messages = new ArrayList<String>();
        boolean isNewInput = true;
        if (!StringUtils.isBlank(duplicateCheckWrapper.getCurrentValue())) {
          if (!StringUtils.equals(duplicateCheckWrapper.getInputFieldValue(), duplicateCheckWrapper.getCurrentValue())) {
            isNewInput = true;
          } else {
            isNewInput = false;
          }
        }

        KeyValueWrapper keyValueWrapper = new KeyValueWrapper(duplicateCheckWrapper.getColumnName(), duplicateCheckWrapper.getInputFieldValue());
        if (baseService.isExists(keyValueWrapper, isNewInput)) {
          messages.add(duplicateCheckWrapper.getColumnName() + ": " + duplicateCheckWrapper.getColumnName() + " is a duplicate and cannot be used");
          merchandisingBaseResponse.setGeneralPurposeMessage(messages);
          merchandisingBaseResponse.setErrorCode("isExist.true", duplicateCheckWrapper.getColumnName());
        } else {
          merchandisingBaseResponse.setSuccessCode("isExist.false", duplicateCheckWrapper.getColumnName());
        }
      } else {
        merchandisingBaseResponse.setErrorCode("Request.NoData");
      }
    } catch (Exception e) {
      merchandisingBaseResponse.setErrorCode("isExists.Error", duplicateCheckWrapper.getColumnName());
      log.error("BaseController", e, ErrorType.APPLICATION, merchandisingBaseResponse.getMessage());
    }
    return merchandisingBaseResponse;
  }
}
