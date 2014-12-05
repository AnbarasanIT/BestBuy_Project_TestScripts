package com.bestbuy.search.merchandising.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

@Aspect
@Configurable
public class ModelValidatorAspect {
  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(ModelValidatorAspect.class.getName());

  @Autowired
  private ResourceBundleHandler resourceBundleHandler;
  @Autowired
  private Validator validator;
  @Autowired
  private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

  public void setResourceBundleHandler(ResourceBundleHandler resourceBundleHandler) {
    this.resourceBundleHandler = resourceBundleHandler;
  }

  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  public void setMerchandisingBaseResponse(MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse) {
    this.merchandisingBaseResponse = merchandisingBaseResponse;
  }

  /**
   * Method for validating the controller create methods input before the method execution
   * 
   * @param pjp
   *          The joinpoint information
   * @return An Object with a MerchandisingUIBaseResponse
   * @author Ramiro.Serrato, Chanchal.Kumari
   */
  @SuppressWarnings("unchecked")
  @Around("execution(* com.bestbuy.search.merchandising.web.*Controller.create(..)) || execution(* com.bestbuy.search.merchandising.web.*Controller.update(..))")
  public Object validate(ProceedingJoinPoint pjp) {
    try {
      Collection<ConstraintViolation<IWrapper>> violations = new HashSet<ConstraintViolation<IWrapper>>();

      IWrapper wrapper = (pjp.getArgs() != null && pjp.getArgs().length > 0) ? (IWrapper) pjp.getArgs()[0] : null;
      List<String> errorMessages = null;
      String beanViolationMessage = null;

      if (wrapper != null) {
        violations.addAll(validator.validate(wrapper));

        if (!violations.isEmpty()) {
          errorMessages = new ArrayList<String>();
          String message = null;

          for (ConstraintViolation<IWrapper> violation : violations) {
            String violationMessage = resourceBundleHandler.getErrorString(violation.getMessage()); // Trying to
                                                                                                    // retrieve the
                                                                                                    // error message
                                                                                                    // from the
                                                                                                    // properties file,
                                                                                                    // as in the most of
                                                                                                    // the cases it will
                                                                                                    // come from there,
                                                                                                    // it is not costly
            violationMessage = (violationMessage == null) ? violation.getMessage() : violationMessage;
            if (violation.getPropertyPath() != null && !violation.getPropertyPath().toString().equalsIgnoreCase("")) {
              message = violation.getPropertyPath() + ":" + violationMessage;
              errorMessages.add(message); // the contract is to send an array of strings with this structure
                                          // <fieldName>,<message>
            } else if (violation.getPropertyPath().toString().equalsIgnoreCase("")) {
              beanViolationMessage = violationMessage;
            } else {
              message = violationMessage;
              errorMessages.add(message);
            }

          }

          wrapper = null;
        }
      }

      merchandisingBaseResponse = (MerchandisingBaseResponse<IWrapper>) pjp.proceed(new Object[] {wrapper});
      merchandisingBaseResponse.setGeneralPurposeMessage(errorMessages);

      if (beanViolationMessage != null) {
        merchandisingBaseResponse.setMessage(beanViolationMessage);
      }
    }

    catch (Throwable t) { // not sending any exception or error to the UI
      merchandisingBaseResponse.setErrorCode("Validation.Error");
      log.error("ModelValidatorAspect", t, ErrorType.APPLICATION, merchandisingBaseResponse.getMessage());
    }

    return merchandisingBaseResponse;
  }
}
