/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bestbuy.search.foundation.common.XmlPropertyPlaceholderConfigurer;
import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.SortOrder;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.SkuHistoryDAO;
import com.bestbuy.search.merchandising.domain.SkuHistory;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.SkuHistoryWrapper;

/**
 * Service to Remove SKUs
 * 
 * @author Sreedhar Patlola
 */
@Service("skuHistoryService")
public class SkuHistoryService extends BaseService<String, SkuHistory> {

  private final static BTLogger log = (BTLogger) BTLogger.getBTLogger(SkuHistoryService.class.getName());

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  private SkuHistoryDAO skuHistoryDAO;

  @Autowired
  private UserUtil userUtil;

  /**
   * @param restTemplate
   *          the restTemplate to set
   */
  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Autowired
  public void setDao(SkuHistoryDAO dao) {
    this.baseDAO = dao;
  }

  /**
   * @param SkuHistoryDAO
   *          the SkuHistoryDAO to set
   */
  public void setSkuHistoryDAO(SkuHistoryDAO skuHistoryDAO) {
    this.skuHistoryDAO = skuHistoryDAO;
  }

  /**
   * @param userUtil
   *          the userUtil to set
   */
  public void setUserUtil(UserUtil userUtil) {
    this.userUtil = userUtil;
  }

  /**
   * Retrieves all users of the system.
   * 
   * @return the list of the UserWrapper objects
   * @throws ServiceException
   */
  public List<IWrapper> load() throws ServiceException {

    List<IWrapper> wrappers = null;
    SearchCriteria searchCriteria = new SearchCriteria();
    PaginationWrapper pw = searchCriteria.getPaginationWrapper();
    pw.setRowsPerPage(20);
    Map<String, SortOrder> orderCriteria = searchCriteria.getOrderCriteria();
    orderCriteria.put("time", SortOrder.DESC);

    try {
      List<SkuHistory> skus = (List<SkuHistory>) skuHistoryDAO.executeQueryWithCriteria(searchCriteria);
      wrappers = SkuHistoryWrapper.getSkus(skus);
    }

    catch (DataAcessException da) {
      throw new ServiceException("Error while retriving the skus from DB", da);
    }

    return wrappers;
  }

  /**
   * Creates the new User.
   * 
   * @param userWrapper
   * @return
   * @throws ServiceException
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public IWrapper addSku(String skuId, String action) throws ServiceException, DataAcessException {
	  SkuHistory savedSkuHistory = null;
      Users loggedUser = userUtil.getUser();
      SkuHistory skuHistory = new SkuHistory();
      skuHistory.setSkuId(skuId);
      skuHistory.setAddedBy(loggedUser);
      skuHistory.setAction(action);
      skuHistory.setTime(new Date());
      savedSkuHistory = save(skuHistory);
	  try {
		  invokeDeleteSkuService(skuId);
	  } catch (ServiceException e) {
		  log.error("SkuHistoryService", e, ErrorType.APPLICATION, "an error occured while invoking delete sku operation");
	  }
	  return SkuHistoryWrapper.getSkuHistory(savedSkuHistory);
  }

  /**
   * This method calls the DaaS WebService deleteSku.
   * 
   * @param queryParams
   * @return
   */
  @SuppressWarnings("unused")
  private void invokeDeleteSkuService(String skuId) throws ServiceException {
    String urlAF = null;
    String urlCF = null;
    try {
      log.info("calling delete Sku service with SkuId:.." + skuId);
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
      restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
      
      // Read AF DAAS Delete SKU properties
      String ipAdderssAF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUAF.host");
      String deleteSkuURIAF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUAF.removeSku.query.uri");
      String portAF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUAF.port");
      String requestorIdAF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUAF.daas.requestor.id");
      
      // Read CF DAAS Delete SKU properties
      String ipAdderssCF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUCF.host");
      String deleteSkuURICF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUCF.removeSku.query.uri");
      String portCF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUCF.port");
      String requestorIdCF = XmlPropertyPlaceholderConfigurer.getProperty("DeleteSKUCF.daas.requestor.id");
      
      // Build AF/CF Urls
      urlAF = "http://" + ipAdderssAF + ":" + portAF + deleteSkuURIAF + skuId;
      urlCF = "http://" + ipAdderssCF + ":" + portCF + deleteSkuURICF + skuId;
      
      // Execute URLs
     // String responseAF = restTemplate.getForObject(urlAF, String.class);
      ResponseEntity<String> responseAF = restTemplate.exchange(urlAF, HttpMethod.GET, ResponseUtility.getRequestEntity(requestorIdAF, null, "application/text"), String.class);
     // String responseCF = restTemplate.getForObject(urlCF, String.class);
      ResponseEntity<String> responseCF = restTemplate.exchange(urlCF, HttpMethod.GET, ResponseUtility.getRequestEntity(requestorIdCF, null, "application/text"), String.class);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
