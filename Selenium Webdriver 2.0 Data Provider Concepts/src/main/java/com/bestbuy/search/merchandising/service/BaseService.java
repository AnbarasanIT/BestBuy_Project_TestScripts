/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.AND;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.SortOrder;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Base Service Layer for the CRUD operations
 * 
 * @author Lakshmi Valluripalli
 */
public abstract class BaseService<K, E> {

  protected E entityClass;

  protected BaseDAO baseDAO;

  private Map<String, String> uiToEntityMapper;

  public BaseDAO getBaseDAO() {
    return baseDAO;
  }

  public void setBaseDAO(BaseDAO baseDAO) {
    this.baseDAO = baseDAO;
  }

  /**
   * Method to retrieve data based on primaryKey
   * 
   * @param K
   *          primaryKeyId
   * @return E entity
   * @throws ServiceException
   */
  public E retrieveById(K primaryKeyId) throws ServiceException {
    try {
      return (E) baseDAO.retrieveById(primaryKeyId);

    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Method to retrieve all the Data available in the DB
   * 
   * @return Collection<E> entities
   * @throws ServiceException
   */
  @SuppressWarnings("unchecked")
  public Collection<E> retrieveAll() throws ServiceException {
    try {
      return baseDAO.retrieveAll();

    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Method to Save a Single Entity
   * 
   * @param entity
   * @return
   * @throws ServiceException
   */
  public E save(E entity) throws ServiceException {
    try {
      entity = (E) baseDAO.save(entity);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
    return entity;
  }

  /**
   * Method to save the entity Collection
   * 
   * @param entities
   * @throws ServiceException
   */
  public void save(Collection<E> entities) throws ServiceException {
    try {
      baseDAO.save(entities);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Method to update the single Entity
   * 
   * @param entity
   * @return E entity persisted to DB
   * @throws ServiceException
   */
  public E update(E entity) throws ServiceException {
    try {
      entity = (E) baseDAO.update(entity);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
    return entity;
  }

  /**
   * Method to update the Entity collection
   * 
   * @param entity
   *          collection
   */
  public void update(Collection<E> entities) throws ServiceException {
    try {
      baseDAO.update(entities);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Method to delete the Entity
   * 
   * @param entity
   * @return E
   * @throws ServiceException
   */
  public E delete(E entity) throws ServiceException {
    try {
      baseDAO.delete(entity);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
    return entity;
  }

  /**
   * Method to delete the Entity
   * 
   * @param primaryKeyId
   * @param entity
   * @return E entity
   * @throws ServiceException
   */
  public E delete(K primaryKeyId, E entity) throws ServiceException {
    try {
      baseDAO.delete(primaryKeyId, entity);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
    return entity;
  }

  /**
   * Method to deletes all the synonym terms for input synonym group id
   * 
   * @param primaryKeyId
   * @param entity
   * @return E entity
   * @throws ServiceException
   */
  public void deleteAllById(Long id) throws ServiceException {
    try {
      baseDAO.deleteAllById(id);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Method to load the objects depending on the search Criteria
   * 
   * @param criteria
   * @return
   * @throws DataAcessException
   */
  public Collection<E> executeQuery(SearchCriteria criteria) throws ServiceException {
    try {
      return baseDAO.executeQuery(criteria);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Method to load the objects depending on the search Criteria
   * 
   * @param criteria
   * @return
   * @throws DataAcessException
   */
  public Long getCount(SearchCriteria criteria) throws ServiceException {
    try {
      return baseDAO.getCount(criteria);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * @return the uiToEntityMapper
   */
  public Map<String, String> getUiToEntityMapper() {
    return uiToEntityMapper;
  }

  /**
   * @param uiToEntityMapper
   *          the uiToEntityMapper to set
   */
  public void setUiToEntityMapper(Map<String, String> uiToEntityMapper) {
    this.uiToEntityMapper = uiToEntityMapper;
  }

  /**
   * This is used to set/add the entity field name and its value to the andCriteriaColumnValues or
   * andCriteriaColumnValues of SearcheCriteria object from the PaginationWrapper object based on its searchOperation
   * "AND" or "OR"
   * 
   * @param paginationWrapper
   *          the paginationWrapper object
   * @param criteria
   *          the criteria object
   */
  protected void setColumnValuesForSearch(PaginationWrapper paginationWrapper, SearchCriteria criteria) {

    if (CollectionUtils.isNotEmpty(paginationWrapper.getSearchColumnValues())) {

      List<KeyValueWrapper> uiAndColumnKeyValues = paginationWrapper.getSearchColumnValues();
      for (KeyValueWrapper uiKeyValue : uiAndColumnKeyValues) {
        if (StringUtils.isNotBlank(uiKeyValue.getValue()) && getUiToEntityMapper().get(uiKeyValue.getKey()) != null) {
          if (AND.equals(paginationWrapper.getSearchOper())) {
            String values[] = StringUtils.split(getUiToEntityMapper().get(uiKeyValue.getKey()), ",");
            if (values.length > 1) {
              for (int i = 0; i < values.length; i++) {
                List<KeyValueWrapper> orColumnKeyValues = criteria.getOrCriteriaColumnValues();
                orColumnKeyValues.add(new KeyValueWrapper(values[i], uiKeyValue.getValue()));
              }

            } else {
              List<KeyValueWrapper> andColumnKeyValues = criteria.getAndCriteriaColumnValues();
              andColumnKeyValues.add(new KeyValueWrapper(getUiToEntityMapper().get(uiKeyValue.getKey()), uiKeyValue.getValue()));
            }
          } else {
            List<KeyValueWrapper> orColumnKeyValues = criteria.getOrCriteriaColumnValues();
            orColumnKeyValues.add(new KeyValueWrapper(getUiToEntityMapper().get(uiKeyValue.getKey()), uiKeyValue.getValue()));
          }
        }
      }
    }

  }

  /**
   * This is used to set/add the entity field name and its sorting order to the orderCriteria map of the SearchCriteria
   * object from the paginationWrapper object based on its sortOrder
   * 
   * @param paginationWrapper
   *          the paginationWrapper object
   * @param criteria
   *          the criteria object
   */
  protected void setColumnValuesForOrderCriteria(PaginationWrapper paginationWrapper, SearchCriteria criteria) {
    Map<String, SortOrder> orderCriteria = criteria.getOrderCriteria();
    if (StringUtils.isNotBlank(paginationWrapper.getSortColumn()) && getUiToEntityMapper().get(paginationWrapper.getSortColumn()) != null) {
      orderCriteria.put(getUiToEntityMapper().get(paginationWrapper.getSortColumn()), SortOrder.fromValue(paginationWrapper.getSortOrder().toUpperCase()));
    }
  }

  /**
   * Method to load the entities
   * 
   * @throws DataAcessException
   */
  public List<E> loadEntitiesWithCriteria(SearchCriteria criteria) throws DataAcessException {
    List<E> entities = null;
    entities = (List<E>) baseDAO.executeQueryWithCriteria(criteria);

    return entities;
  }

  /**
   * Checks if any record exists with the Specified KeyValueWrapper in the database by calling the baseDao
   * 
   * @param KeyValueWrapper
   * @return
   * @throws ServiceException
   */
  @SuppressWarnings("unchecked")
  public boolean isExists(KeyValueWrapper KeyValueWrapper, boolean isCreate) throws ServiceException {
    boolean theReturn = false;
    if (StringUtils.isNotBlank(KeyValueWrapper.getKey()) && getUiToEntityMapper().get(KeyValueWrapper.getKey()) != null) {
      List<E> entities = null;
      SearchCriteria criteria = new SearchCriteria();
      criteria.setPaginationRequired(false);
      Map<String, Object> searchTerm = criteria.getSearchTermsIgnoreCase();
      Map<String, Object> notEqCriteria = criteria.getNotEqCriteria();
      notEqCriteria.put("status.statusId", DELETE_STATUS);
      searchTerm.put(getUiToEntityMapper().get(KeyValueWrapper.getKey()), KeyValueWrapper.getValue());
      try {
        entities = (List<E>) baseDAO.executeQueryWithCriteria(criteria);
      } catch (DataAcessException da) {
        throw new ServiceException("Error while checking the name from DB:.." + KeyValueWrapper.toString(), da);
      }
      if (!CollectionUtils.isEmpty(entities)) {
        if (isCreate) {
          theReturn = true;
        } else {
          theReturn = false;
        }
      }
    } else {
      throw new ServiceException("key not found for the field" + KeyValueWrapper.getKey() + " in the UiToEntity Map of the BaseService bean ");
    }
    return theReturn;

  }
}