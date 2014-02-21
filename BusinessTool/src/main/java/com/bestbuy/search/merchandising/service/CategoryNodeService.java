package com.bestbuy.search.merchandising.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bestbuy.search.foundation.common.XmlPropertyPlaceholderConfigurer;
import com.bestbuy.search.merchandising.category.Categories;
import com.bestbuy.search.merchandising.category.CategoryParser;
import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.CategoryNodeIdPathMap;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.ResponseUtility;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryNodeDAO;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.google.common.base.Joiner;

/**
 * Service class to save/load the category tree
 * 
 * @author Lakshmi.Valluripalli
 */
public class CategoryNodeService extends BaseService<String, Category> implements ICategoryNodeService {

  private final static BTLogger log = BTLogger.getBTLogger(CategoryNodeService.class.getName());

  @Autowired
  private CategoryNodeDAO categoryNodeDAO;

  @Autowired
  public void setDao(CategoryNodeDAO dao) {
    this.baseDAO = dao;
  }

  @Autowired
  private IContextService contextService;

  @Autowired
  private ICategoryFacetService categoryFacetService;

  @Autowired
  private CategoryParser categoryParser;

  @Autowired
  private ICategoryService categoryService;

  @Autowired
  RestTemplate restTemplate;

  /**
   * @param to
   *          set the categoryFacetService
   */
  public void setCategoryFacetService(ICategoryFacetService categoryFacetService) {
    this.categoryFacetService = categoryFacetService;
  }

  public void setContextService(IContextService contextService) {
    this.contextService = contextService;
  }

  /**
   * Method to invoke service to get category tree
   */
  public void invokeCategoryTreeService() throws ServiceException {
    log.info("Call DAAS to fetch category tree");
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

    // Read AF DAAS Fetch category tree
    String ipAdderss = XmlPropertyPlaceholderConfigurer.getProperty("CategoryTreeAF.host");
    String protocol = XmlPropertyPlaceholderConfigurer.getProperty("CategoryTreeAF.protocol");
    String port = XmlPropertyPlaceholderConfigurer.getProperty("CategoryTreeAF.port");
    String requestorId = XmlPropertyPlaceholderConfigurer.getProperty("CategoryTreeAF.daas.requestor.id");
    String categoryTreeURI = XmlPropertyPlaceholderConfigurer.getProperty("CategoryTreeAF.category.tree.query.uri");

    // Build AF/CF Urls
    String url = protocol + "://" + ipAdderss + ":" + port + categoryTreeURI;

    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, ResponseUtility.getRequestEntity(requestorId, null, "application/text"), String.class);
    if (response != null && StringUtils.isNotBlank(response.getBody())) {
      postCategoryTree(response.getBody());
    } else {
      log.error("CategoryNodeService", "errorType=" + ErrorType.CATEGORY_TREE + "\",message=\"Category tree returned empty\"");
    }
    log.info(">>>>> Response From The Daas's Load Categpry Service <<<<<<<<  " + response.getBody().toString());
  }

  /**
   * Method to post category tree
   * 
   * @param categoryXml
   * @throws ServiceException
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public synchronized void postCategoryTree(String categoryXml) throws ServiceException {
    try {
      if (categoryXml != null && !categoryXml.isEmpty()) {
        String xslTreePath = XmlPropertyPlaceholderConfigurer.getProperty("CategoryXSL.category.tree.xsl.path");
        String parsedTree = categoryParser.parseCategoryTree(xslTreePath, categoryXml);
        if (parsedTree != null) {
          categoryService.saveCategory(parsedTree);
        }
        String xslNodePath = XmlPropertyPlaceholderConfigurer.getProperty("CategoryXSL.category.nodes.xsl.path");
        Categories categories = categoryParser.parseCategoryNodes(xslNodePath, categoryXml);
        if (categories != null) {
          log.info("Parsed Nodes Size:.." + categories.getNodes().size());
          List<Category> categoryNodes = categories.getNodes();
          if (categoryNodes != null && categoryNodes.size() > 0) {
            saveCategoryNodes(categoryNodes);
          }
        }
      }
      // what are we trying to accomplish here?
      // wouldn't this be the same as just catching exception?
      //
    } catch (ServiceException e) {
      throw new ServiceException(e);
    } catch (WorkflowException e) {
      throw new ServiceException(e);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * Method compares the nodes in the
   * Saves the received category tree paths to DB
   * 
   * @param - String categoryTree XML
   * @throws WorkflowException
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public List<Long> saveCategoryNodes(List<Category> categoryNodes) throws ServiceException, WorkflowException {
    List<Long> bannerIds = new ArrayList<Long>();
    List<String> categoryNodeIds = new ArrayList<String>();
    Map<String, String> tempCategoryNodeIdPathMap = new HashMap<String, String>();
    try {
      List<String> validNodes = new ArrayList<String>();
      List<Category> nodes = (List<Category>) ((CategoryNodeDAO) baseDAO).retrieveAll();
      // check if you have any nodes on the Db If not save them else compare for invalid and new nodes
      if (nodes != null && nodes.size() > 0) {
        // compare the new nodes with the old nodes before updating to DB
        // for(CategoryNode categoryNode:categoryNodes){
        for (Iterator<Category> iterator = categoryNodes.iterator(); iterator.hasNext();) {
          Category categoryNode = iterator.next();
          tempCategoryNodeIdPathMap.put(categoryNode.getCategoryPath(), categoryNode.getCategoryNodeId());
          for (Category node : nodes) {
            if (categoryNode.getCategoryNodeId().equals(node.getCategoryNodeId())) {
              if (!categoryNode.getCategoryPath().replace("\n", "").equals(node.getCategoryPath().replace("\n", ""))) {
                node.setCategoryPath(categoryNode.getCategoryPath());
                node.setAlsoFoundIn(categoryNode.getAlsoFoundIn());
                ((CategoryNodeDAO) baseDAO).update(node);
              } else if (categoryNode.getCategoryPath().replace("\n", "").equals(node.getCategoryPath().replace("\n", "")) && (!categoryNode.getAlsoFoundIn().equals(node.getAlsoFoundIn()))) {
                node.setAlsoFoundIn(categoryNode.getAlsoFoundIn());
                ((CategoryNodeDAO) baseDAO).update(node);
              }
              validNodes.add(node.getCategoryNodeId());
              iterator.remove();
              break;
            }
          }
        }

        // Invalidate all the old nodes which are no longer available in tree and set the status isActive to N
        if (nodes.size() > 0) {
          for (Category node : nodes) {
            String categNodeId = node.getCategoryNodeId();
            categoryNodeIds.add(categNodeId);
          }
          if (validNodes != null && validNodes.size() > 0) {
            categoryNodeIds.removeAll(validNodes);
          }
        }

        if (categoryNodeIds.size() > 0) {
          if (categoryNodeIds.size() > 1000) {
            int iterations = new Double(Math.ceil(new Double(categoryNodeIds.size()) / new Double(1000))).intValue();
            for (int i = 0; i < iterations; i++) {
              int start = i * 1000;
              int end = start + 1000;
              if (end > categoryNodeIds.size()) {
                end = categoryNodeIds.size();
              }
              List<String> subListCategoryNodeIds = categoryNodeIds.subList(start, end);
              String categoryNodeList = Joiner.on("','").join(subListCategoryNodeIds);
              baseDAO.updateInvalidCategory(categoryNodeList);
              // Invalidate context
              invalidateContext(subListCategoryNodeIds);
              invalidateCategoryFacet(subListCategoryNodeIds); // Invalidate Category Facet
            }
          } else {
            String categoryNodeList = Joiner.on("','").join(categoryNodeIds);
            baseDAO.updateInvalidCategory(categoryNodeList);
            // Invalidate context
            invalidateContext(categoryNodeIds);
            invalidateCategoryFacet(categoryNodeIds); // Invalidate Category Facet
          }
          invalidateBanners();// Invalidate Banners
          invalidateFacet(); // Invalidate Facets
        }

        // if we have any new paths added add it to the DB
        if (categoryNodes.size() > 0) {
          ((CategoryNodeDAO) baseDAO).save(categoryNodes);
        }
      } else {
        for (Iterator<Category> iterator = categoryNodes.iterator(); iterator.hasNext();) {
          Category categoryNode = iterator.next();
          tempCategoryNodeIdPathMap.put(categoryNode.getCategoryPath(), categoryNode.getCategoryNodeId());
        }
        ((CategoryNodeDAO) baseDAO).save(categoryNodes);
      }

      if (tempCategoryNodeIdPathMap.entrySet().size() > 0) {
        if (CategoryNodeIdPathMap.categoryNodeIdPathMap != null) {
          CategoryNodeIdPathMap.categoryNodeIdPathMap.clear();
        }
        CategoryNodeIdPathMap.setCategoryNodeIdPathMap(tempCategoryNodeIdPathMap);
      }

    } catch (DataAcessException e) {
      log.error("CategoryNodeService", e, ErrorType.CATEGORY_TREE, "Error while saving the categoryNode");
      throw new ServiceException("Error while Saving categoryNodes:.", e);
    }
    return bannerIds;
  }

  /**
   * Method that invalidates the contexts
   * 
   * @param invalidNodes
   * @throws WorkflowException
   * @throws DataAcessException
   * @throws ServiceException
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  private void invalidateContext(List<String> invalidNodes) throws ServiceException {
    List<String> contextList = new ArrayList<String>();
    SearchCriteria criteria = new SearchCriteria();
    Map<String, Object> inCriteria = criteria.getInCriteria();
    inCriteria.put("obj.categoryNode.categoryNodeId", invalidNodes);
    List<Context> contexts = (List<Context>) contextService.executeQuery(criteria);
    for (Context context : contexts) {
      String contextId = context.getCategoryNode().getCategoryNodeId();
      contextList.add(contextId);
    }
    String contextIds = Joiner.on("','").join(contextList);
    contextService.updateContextStatus(contextIds);
  }

  /**
   * Update the banner status as Invalid if all the contexts related to that banner are Invalid
   * 
   * @throws DataAcessException
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public void invalidateBanners() throws DataAcessException {
    baseDAO.invalidateUpdate(MerchandisingConstants.UPDATE_BANNER_STATUS_SQL);
  }

  /**
   * Method that invalidates the contexts
   * 
   * @param invalidNodes
   * @throws WorkflowException
   * @throws DataAcessException
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  private void invalidateCategoryFacet(List<String> invalidNodes) throws ServiceException {
    List<String> categoryFacetList = new ArrayList<String>();
    SearchCriteria criteria = new SearchCriteria();
    Map<String, Object> inCriteria = criteria.getInCriteria();
    inCriteria.put("obj.categoryNode.categoryNodeId", invalidNodes);
    List<CategoryFacet> categoryFacets = (List<CategoryFacet>) categoryFacetService.executeQuery(criteria);
    for (CategoryFacet categoryFacet : categoryFacets) {
      String categoryFacetId = categoryFacet.getCategoryNode().getCategoryNodeId();
      categoryFacetList.add(categoryFacetId);
    }
    String categoryFacetIds = Joiner.on("','").join(categoryFacetList);
    categoryFacetService.updateCategoryFacet(categoryFacetIds);
  }

  /**
   * Update the facet status as Invalid if all the contexts related to that facet are Invalid
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public void invalidateFacet() throws DataAcessException {
    baseDAO.invalidateUpdate(MerchandisingConstants.UPDATE_FACET_STATUS_SQL);
  }

  @Override
  public String getCategoryPath(String categoryId) throws ServiceException {
    String categoryPath = null;
    try {
      categoryPath = ((Category) baseDAO.retrieveById(categoryId)).getCategoryPath();
    } catch (DataAcessException e) {
      throw new ServiceException("The service was not able to retrieve the categoryPath for the categoryId " + categoryId, e);

    }
    return categoryPath;
  }

  /**
   * Get the categoryNodeId joined by | for the categoryPath provided.
   * 
   * @parem categoryPath
   * @throws ServiceException
   * @author Chanchal.Kumari
   */

  public String getCategoryNodeId(String categoryPath) throws ServiceException {
    List<String> categoryPaths = new ArrayList<String>();
    int lastIndex = 0;
    int startIndex = 0;
    categoryPaths.add(categoryPath);
    while (lastIndex >= 0 && categoryPath != null) {
      lastIndex = categoryPath.lastIndexOf("|");
      if (lastIndex >= 0) {
        categoryPath = categoryPath.substring(startIndex, lastIndex);
        categoryPaths.add(categoryPath);
      }
    }
    String categoryNodeIds = new String();
    if (CategoryNodeIdPathMap.categoryNodeIdPathMap != null && CategoryNodeIdPathMap.categoryNodeIdPathMap.entrySet().size() > 0) {
      for (int iterator = categoryPaths.size() - 1; iterator >= 0; iterator--) {
        categoryNodeIds += CategoryNodeIdPathMap.categoryNodeIdPathMap.get(categoryPaths.get(iterator)) + "|";
      }
    } else {
      try {
        List<Category> categories = categoryNodeDAO.retrieveByPath(categoryPaths);
        Map<String, String> categoryNodeIdPathMapFromDB = new HashMap<String, String>();
        for (Category category : categories) {
          categoryNodeIdPathMapFromDB.put(category.getCategoryPath(), category.getCategoryNodeId());
        }
        for (int iterator = categoryPaths.size() - 1; iterator >= 0; iterator--) {
          categoryNodeIds += categoryNodeIdPathMapFromDB.get(categoryPaths.get(iterator)) + "|";
        }
      } catch (DataAcessException e) {
        throw new ServiceException(e);
      }
    }
    if (categoryNodeIds.length() > 0) {
      return categoryNodeIds.substring(0, categoryNodeIds.length() - 1);
    }
    return categoryNodeIds;
  }
}
