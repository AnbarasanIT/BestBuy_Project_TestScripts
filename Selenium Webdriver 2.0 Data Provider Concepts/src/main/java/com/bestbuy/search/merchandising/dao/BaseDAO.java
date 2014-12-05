/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bestbuy.search.merchandising.common.DynamicQueryHelper;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.SortOrder;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * CRUD Operataions of the Data Base
 * 
 * @author Lakshmi Valluripalli
 * @Modified by chanchal kumari added the deleteAllById method.
 */

public class BaseDAO<K, E> {

  protected Class<E> entityClass;

  @PersistenceContext
  protected EntityManager entityManager;

  @SuppressWarnings("unchecked")
  public BaseDAO() {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  
  public void setEntityClass(Class<E> entityClass) {
	  this.entityClass = entityClass;
  }

  /**
   * Method to retrieve data based on primaryKey
   * 
   * @param K
   *          primaryKeyId
   * @return E entity
   * @throws DataAcessException
   */
  public E retrieveById(K primaryKeyId) throws DataAcessException {
    try {
      return entityManager.find(entityClass, primaryKeyId);
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Method to retrieve all the Data available in the DB
   * 
   * @return Collection<E> entities
   * @throws DataAcessException
   */
  @SuppressWarnings("unchecked")
  public Collection<E> retrieveAll() throws DataAcessException {
    try {
      Query query = entityManager.createQuery("SELECT obj FROM " + entityClass.getSimpleName() + " obj");
      return query.getResultList();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Method to Save a Single Entity
   * 
   * @param entity
   * @return
   * @throws DataAcessException
   */
  public E save(E entity) throws DataAcessException {
    try {
      entityManager.persist(entity);
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
    return entity;
  }

  /**
   * Method to save the entity Collection
   * 
   * @param entities
   * @throws DataAcessException
   */
  public void save(Collection<E> entities) throws DataAcessException {
    try {
      for (E entity : entities) {
        entityManager.persist(entity);
      }
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Method to update the single Entity
   * 
   * @param entity
   * @return E entity persisted to DB
   * @throws DataAcessException
   */
  public E update(E entity) throws DataAcessException {
    try {
      entity = entityManager.merge(entity);
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
    return entity;
  }

  /**
   * Method to update the Entity Collection
   * 
   * @param entity
   * @return E
   * @throws DataAcessException
   */
  public void update(Collection<E> entities) throws DataAcessException {
    try {
      for (E entity : entities) {
        entityManager.merge(entity);
      }
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Method to delete the Entity
   * 
   * @param primaryKeyId
   * @param entity
   * @return E entity
   * @throws DataAcessException
   */
  public E delete(E entity) throws DataAcessException {
    try {
      entityManager.remove(entity);
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
    return entity;
  }

  /**
   * Method to delete the Entity
   * 
   * @param primaryKeyId
   * @param entity
   * @return E entity
   * @throws DataAcessException
   */
  public E delete(K primaryKeyId, E entity) throws DataAcessException {
    try {
      entityManager.remove(entityManager.getReference(entityClass, primaryKeyId));
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
    return entity;
  }

  /**
   * Delete all the synonym terms for the input synonym id.
   * 
   * @param entities
   * @throws DataAcessException
   */
  public void deleteAllById(Long id) throws DataAcessException {
    try {
      Query query = entityManager.createQuery("delete from " + entityClass.getSimpleName() + " obj " + "where " + "obj.id.synonymGroupId = " + id);
      query.executeUpdate();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  public void updateInvalidCategory(String categoryNodeIds) throws DataAcessException {
    try {
      Query query = entityManager.createQuery("UPDATE " + entityClass.getSimpleName() + " obj SET obj.isActive='N' where obj.categoryNodeId in ('" + categoryNodeIds + "')");
      query.executeUpdate();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Update the context
   * 
   * @param contextIds
   * @throws DataAcessException
   */
  public void updateContext(String contextIds) throws DataAcessException {
    try {
      Query query = entityManager.createQuery("UPDATE " + entityClass.getSimpleName() + " obj SET obj.isActive ='N' where obj.categoryNode.categoryNodeId in ('" + contextIds + "')");
      query.executeUpdate();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Method to set the status (Banners/Facets) to invalid when all the context related to it are invalid
   * 
   * @param contextIds
   * @throws DataAcessException
   */
  public void invalidateUpdate(String invalidateSQL) throws DataAcessException {
    try {
      Query query = entityManager.createNativeQuery(invalidateSQL);
      query.executeUpdate();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Method to retrieve the data based on the search criteria
   * 
   * @param criteria
   * @throws DataAcessException
   */
  @SuppressWarnings("unchecked")
  public Collection<E> executeQuery(SearchCriteria criteria) throws DataAcessException {
    try {
      DynamicQueryHelper queryHelper = new DynamicQueryHelper(entityClass);
      queryHelper.prepareQuery(criteria);
      String queryString = queryHelper.getQueryString();
      Query query = entityManager.createQuery(queryString);
      query = setQueryParam(criteria, query);
      return query.getResultList();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
  }

  /**
   * Executes an update or delete query
   * 
   * @param jpql
   *          The query as a JPQL String
   * @return The number of altered records
   * @author Ramiro.Serrato
   */
  public Integer executeUpdate(String jpql) throws DataAcessException {
    try {
      Query query = entityManager.createQuery(jpql);
      return query.executeUpdate();
    }

    catch (RuntimeException e) {
      throw new DataAcessException("Error while executing the query[" + jpql + "]", e);
    }
  }

  /**
   * Method that gives the count of objects in DB
   * 
   * @return
   * @throws DataAcessException
   */
  public Long getCount(SearchCriteria criteria) throws DataAcessException {
    String queryString = null;
    try {
      DynamicQueryHelper queryHelper = new DynamicQueryHelper(entityClass, true);
      if (criteria != null) {
        queryHelper.prepareQuery(criteria);
      }
      queryString = queryHelper.getQueryString();
      Query query = entityManager.createQuery(queryString);
      if (criteria != null) {
        query = setQueryParam(criteria, query);
      }
      return (Long) query.getSingleResult();
    } catch (RuntimeException e) {
      throw new DataAcessException("Error while executing the query[" + queryString + "]", e);
    }
  }
  
  /**
   * Method to perform health check on db
   * 
   * @return boolean
   * @throws DataAcessException
   */
  public boolean databaseHealthCheck() throws DataAcessException {
	  boolean result = false;
	  String query = "select 1 from dual";
	  Query q = null;
	  try {
		  q = entityManager.createNativeQuery(query);
		  int i = ((BigDecimal) q.getSingleResult()).intValue();
		  if (i == 1) {
			  result = true;
		  }
	  } catch (RuntimeException e) {
		  throw new DataAcessException(e);
	  }
	  return result;
  }

  @SuppressWarnings("unchecked")
  private Query setQueryParam(final SearchCriteria criteria, final Query query) {

    // set the parameters
    int paramIndex = 0;
    // set parameters to the Where Clause

    if (criteria.getSearchTermsIgnoreCase() != null && !criteria.getSearchTermsIgnoreCase().isEmpty()) {
      final Map<String, Object> whereCriteria = criteria.getSearchTermsIgnoreCase();
      for (String key : whereCriteria.keySet()) {
        Object fieldValue = whereCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }
    if (criteria.getSearchTerms() != null && !criteria.getSearchTerms().isEmpty()) {
      final Map<String, Object> whereCriteria = criteria.getSearchTerms();
      for (String key : whereCriteria.keySet()) {
        Object fieldValue = whereCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }
    // set parameters to lessThan Clause
    if (criteria.getLesserCriteria() != null && !criteria.getLesserCriteria().isEmpty()) {
      final Map<String, Object> lesserCriteria = criteria.getLesserCriteria();
      for (String key : lesserCriteria.keySet()) {
        Object fieldValue = lesserCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }
    // set parameters to lessThanEq Clause
    if (criteria.getLesserEqCriteria() != null && !criteria.getLesserEqCriteria().isEmpty()) {
      final Map<String, Object> lesserEqCriteria = criteria.getLesserEqCriteria();
      for (String key : lesserEqCriteria.keySet()) {
        Object fieldValue = lesserEqCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }
    // set parameters to GreateThan Clause
    if (criteria.getGreaterCriteria() != null && !criteria.getGreaterCriteria().isEmpty()) {
      final Map<String, Object> greaterCriteria = criteria.getGreaterCriteria();
      for (String key : greaterCriteria.keySet()) {
        Object fieldValue = greaterCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }
    // set parameters to GreateThanEq Clause
    if (criteria.getGreaterEqCriteria() != null && !criteria.getGreaterEqCriteria().isEmpty()) {
      final Map<String, Object> greaterEqCriteria = criteria.getGreaterEqCriteria();
      for (String key : greaterEqCriteria.keySet()) {
        Object fieldValue = greaterEqCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }
    // set parameters to NotEq Clause
    if (criteria.getNotEqCriteria() != null && !criteria.getNotEqCriteria().isEmpty()) {
      final Map<String, Object> notEqCriteria = criteria.getNotEqCriteria();
      for (String key : notEqCriteria.keySet()) {
        Object fieldValue = notEqCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue);
        }
      }
    }

    // set parameters to Like Clause
    if (criteria.getLikeCriteria() != null && !criteria.getLikeCriteria().isEmpty()) {
      final Map<String, Object> likeCriteria = criteria.getLikeCriteria();
      for (String key : likeCriteria.keySet()) {
        Object fieldValue = likeCriteria.get(key);
        if (fieldValue != null) {
          query.setParameter(++paramIndex, fieldValue + "%");
        }
      }
    }
    // set parameters to IN Clause
    if (criteria.getInCriteria() != null && !criteria.getInCriteria().isEmpty()) {
      final Map<String, Object> inCriteria = criteria.getInCriteria();
      for (String key : inCriteria.keySet()) {
        List<Object> fieldValues = (List<Object>) inCriteria.get(key);
        if (fieldValues != null) {
          for (Object object : fieldValues) {
            query.setParameter(++paramIndex, object);
          }
        }
      }

    }

    // set parameters to Logical AND Clause
    if (!criteria.getAndCriteriaColumnValues().isEmpty()) {

      for (KeyValueWrapper keyValueWrapper : criteria.getAndCriteriaColumnValues()) {
        Object fieldValue = keyValueWrapper.getValue();
        if (fieldValue != null) {
          query.setParameter(++paramIndex, "%" + fieldValue + "%");
        }
      }
    }

    // set parameters to Logical AND Clause
    if (!criteria.getOrCriteriaColumnValues().isEmpty()) {

      for (KeyValueWrapper keyValueWrapper : criteria.getOrCriteriaColumnValues()) {
        Object fieldValue = keyValueWrapper.getValue();
        if (fieldValue != null) {
          query.setParameter(++paramIndex, "%" + fieldValue + "%");
        }
      }
    }

    // set parameters to NOT IN Clause
    if (criteria.getNotInCriteria() != null && !criteria.getNotInCriteria().isEmpty()) {
      final Map<String, Object> notInCriteria = criteria.getNotInCriteria();
      for (String key : notInCriteria.keySet()) {
        List<Object> fieldValues = (List<Object>) notInCriteria.get(key);
        if (fieldValues != null) {
          for (Object object : fieldValues) {
            query.setParameter(++paramIndex, object);
          }
        }
      }
    }

    return query;
  }

  /**
   * Method to retrieve the data based on the search criteria, using JPA 2.0 Predicates.
   * 
   * @param criteria
   * @throws DataAcessException
   */
  public Collection<E> executeQueryWithCriteria(final SearchCriteria searchCriteria) throws DataAcessException {
    Collection<E> relultSet = null;
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<E> criteriaQuery = builder.createQuery(entityClass);
    Root<E> root = criteriaQuery.from(entityClass);
    criteriaQuery.select(root);
    try {
      Predicate[] predicates = buildPredicates(root, builder, searchCriteria);
      criteriaQuery.where(predicates);
      criteriaQuery.orderBy(getOrderByList(root, builder, searchCriteria));
      TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);
      if (searchCriteria.isPaginationRequired()) {
        setPaginationData(typedQuery, searchCriteria);
      }
      setTotalCount(searchCriteria.getPaginationWrapper());
      relultSet = typedQuery.getResultList();
    } catch (RuntimeException rte) {
      throw new DataAcessException(rte);
    }
    return relultSet;
  }

  private Long getSearchCountForPagination(final SearchCriteria searchCriteria) throws DataAcessException {

    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
    Root<E> root = criteriaQuery.from(entityClass);
    criteriaQuery.select(builder.count(root));
    Predicate[] predicates = buildPredicates(root, builder, searchCriteria);
    criteriaQuery.where(predicates);
    TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
    return typedQuery.getSingleResult();

  }

  private void setTotalCount(PaginationWrapper paginationWrapper) throws DataAcessException {
    paginationWrapper.setTotalCount(paginationWrapper.getSearchCount());

    if (CollectionUtils.isNotEmpty(paginationWrapper.getSearchColumnValues()) && !paginationWrapper.getSearchColumnValues().get(0).getKey().equals("attributeId")) {
      SearchCriteria searchCriteria = new SearchCriteria();
      Map<String, Object> notEqCriteria = searchCriteria.getNotEqCriteria();
      notEqCriteria.put("status.statusId", DELETE_STATUS);
      paginationWrapper.setTotalCount(getSearchCountForPagination(searchCriteria));
    }

  }

  private void setPaginationData(final TypedQuery<E> typedQuery, final SearchCriteria searchCriteria) throws DataAcessException {

    PaginationWrapper paginationWrapper = searchCriteria.getPaginationWrapper();

    Long searchCount = getSearchCountForPagination(searchCriteria);
    paginationWrapper.setSearchCount(searchCount);

    Long totalPages = searchCount / paginationWrapper.getRowsPerPage();
    if (searchCount % paginationWrapper.getRowsPerPage() > 0) {
      totalPages++;
    }

    paginationWrapper.setTotalCountOfPages(totalPages);

    typedQuery.setMaxResults(paginationWrapper.getRowsPerPage());
    int firstResult = (paginationWrapper.getPageIndex().intValue() - 1) * paginationWrapper.getRowsPerPage();
    typedQuery.setFirstResult(firstResult);

  }

  private Predicate[] buildPredicates(final Root<E> root, final CriteriaBuilder builder, final SearchCriteria searchCriteria) throws DataAcessException {

    List<Predicate> predicateList = new ArrayList<Predicate>();

    predicateList.addAll(getPredicateListForEquals(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForEqualsIgnoreCase(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForGreaterThan(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForGreaterThanOrEqualTo(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForLessThan(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForLessThanOrEqualTo(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForIn(root, searchCriteria));
    predicateList.addAll(getPredicateListForLike(root, builder, searchCriteria));
    predicateList.addAll(getPredicateListForNotEquals(root, builder, searchCriteria));
    Predicate andPredicate = getPredicateForAnd(root, builder, searchCriteria);
    if (andPredicate != null) {
      predicateList.add(andPredicate);
    }
    Predicate orPredicate = getPredicateForOr(root, builder, searchCriteria);
    if (orPredicate != null) {
      predicateList.add(orPredicate);
    }

    Predicate[] predicates = new Predicate[predicateList.size()];
    predicateList.toArray(predicates);

    return predicates;

  }

  private Predicate getPredicateForOr(Root<E> root, CriteriaBuilder builder, SearchCriteria searchCriteria) {
    Predicate predicate = null;

    Predicate[] predicateListForLogicalOperator = getPredicateListForLogicalOperator(root, builder, searchCriteria.getOrCriteriaColumnValues());
    if (predicateListForLogicalOperator.length > 0) {
      predicate = builder.or(predicateListForLogicalOperator);
    }
    return predicate;
  }

  private Predicate getPredicateForAnd(Root<E> root, CriteriaBuilder builder, SearchCriteria searchCriteria) {
    Predicate predicate = null;
    Predicate[] predicateListForLogicalOperator = getPredicateListForLogicalOperator(root, builder, searchCriteria.getAndCriteriaColumnValues());
    if (predicateListForLogicalOperator.length > 0) {
      predicate = builder.and(predicateListForLogicalOperator);
    }
    return predicate;
  }

  private Predicate[] getPredicateListForLogicalOperator(Root<E> root, CriteriaBuilder builder, List<KeyValueWrapper> criteriaColumnValues) {

    List<Predicate> predicateList = new ArrayList<Predicate>();
    for (KeyValueWrapper keyValueWrapper : criteriaColumnValues) {
      predicateList.add(builder.like(builder.upper(getPathForTypeString(root, keyValueWrapper.getKey())), "%" + keyValueWrapper.getValue().toUpperCase() + "%"));
    }
    return predicateList.toArray(new Predicate[predicateList.size()]);

  }

  private Order[] getOrderByList(final Root<? extends Object> root, final CriteriaBuilder builder, final SearchCriteria searchCriteria) {
    Map<String, SortOrder> terms = searchCriteria.getOrderCriteria();
    Set<String> keys = terms.keySet();
    List<Order> orders = new ArrayList<Order>();
    for (String key : keys) {
      String values[] = StringUtils.tokenizeToStringArray(key, ",");
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          if (terms.get(key).equals(SortOrder.ASC)) {
            if (org.apache.commons.lang.StringUtils.contains(getClassTypeForSorting(values[i]).getName(), "Date")) {
              orders.add(builder.asc(getPathForTypeDate(root, values[i])));
            } else {
              orders.add(builder.asc(builder.upper(getPathForTypeString(root, values[i]))));
            }
          } else {
            if (org.apache.commons.lang.StringUtils.contains(getClassTypeForSorting(values[i]).getName(), "Date")) {
              orders.add(builder.desc(getPathForTypeDate(root, values[i])));
            } else {
              orders.add(builder.desc(builder.upper(getPathForTypeString(root, values[i]))));
            }

          }
        }
      } else {

        if (terms.get(key).equals(SortOrder.ASC)) {
          if (org.apache.commons.lang.StringUtils.contains(getClassTypeForSorting(key).getName(), "Date")) {
            orders.add(builder.asc(getPathForTypeDate(root, key)));
          } else {
            orders.add(builder.asc(builder.upper(getPathForTypeString(root, key))));
          }
        } else {
          if (org.apache.commons.lang.StringUtils.contains(getClassTypeForSorting(key).getName(), "Date")) {
            orders.add(builder.desc(getPathForTypeDate(root, key)));
          } else {
            orders.add(builder.desc(builder.upper(getPathForTypeString(root, key))));
          }
        }
      }
    }
    return orders.toArray(new Order[orders.size()]);

  }
  
  private Class getClassTypeForSorting(String fieldName) {
    Class returnClazz = String.class;
    if (fieldName.indexOf(MerchandisingConstants.STR_DOT) == -1) {
      Field field = null;
      try {
        field = entityClass.getDeclaredField(fieldName);
      } catch (SecurityException e) {
        // do nothing , default the field type as String
      } catch (NoSuchFieldException e) {
        try {
          field = entityClass.getSuperclass().getDeclaredField(fieldName);
        } catch (SecurityException e1) {
          // do nothing , default the field type as String
        } catch (NoSuchFieldException e1) {
          // do nothing , default the field type as String
        }

      }
      if(field != null){
        returnClazz = field.getType();
      }
    }
    return returnClazz;
  }

  private Collection<? extends Predicate> getPredicateListForGreaterThan(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) throws DataAcessException {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getGreaterCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      if (terms.get(key) instanceof Date) {

        predicateList.add(builder.greaterThan(getPathForTypeDate(root, key), (Date) terms.get(key)));

      } else {
        throw new DataAcessException("Not supported the Critera GreaterThan for a data type " + terms.get(key).getClass());

      }

    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForGreaterThanOrEqualTo(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) throws DataAcessException {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getGreaterEqCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      if (terms.get(key) instanceof Date) {

        predicateList.add(builder.greaterThanOrEqualTo(getPathForTypeDate(root, key), (Date) terms.get(key)));
      } else {
        throw new DataAcessException("Not supported the Critera GreaterThanOrEqual for a data type " + terms.get(key).getClass());

      }

    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForLessThan(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) throws DataAcessException {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getLesserCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      if (terms.get(key) instanceof Date) {
        predicateList.add(builder.lessThan(getPathForTypeDate(root, key), (Date) terms.get(key)));
      } else {
        throw new DataAcessException("Not supported the Critera LessThan for a data type " + terms.get(key).getClass());

      }

    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForLessThanOrEqualTo(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) throws DataAcessException {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getLesserCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      if (terms.get(key) instanceof Date) {
        predicateList.add(builder.lessThanOrEqualTo(getPathForTypeDate(root, key), (Date) terms.get(key)));
      } else {
        throw new DataAcessException("Not supported the Critera LessThan for a data type " + terms.get(key).getClass());

      }

    }
    return predicateList;
  }

  @SuppressWarnings("unchecked")
  private Collection<? extends Predicate> getPredicateListForIn(Root<? extends Object> root, SearchCriteria searchCriteria) {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getInCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      List<Object> values = (List<Object>) searchCriteria.getInCriteria().get(key);
      predicateList.add(getPath(root, key).in(values));

    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForLike(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) throws DataAcessException {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getLikeCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      if (terms.get(key) instanceof String) {
        predicateList.add(builder.like(getPathForTypeString(root, key), (String) terms.get(key)));
      } else {
        throw new DataAcessException("Not supported the Critera lessthan for a data type " + terms.get(key).getClass());

      }

    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForNotEquals(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getNotEqCriteria();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      predicateList.add(builder.notEqual(getPath(root, key), terms.get(key)));
    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForEquals(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getSearchTerms();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      predicateList.add(builder.equal(getPath(root, key), terms.get(key)));

    }
    return predicateList;
  }

  private Collection<? extends Predicate> getPredicateListForEqualsIgnoreCase(Root<? extends Object> root, CriteriaBuilder builder, SearchCriteria searchCriteria) throws DataAcessException {
    List<Predicate> predicateList = new ArrayList<Predicate>();

    Map<String, Object> terms = searchCriteria.getSearchTermsIgnoreCase();
    Set<String> keys = terms.keySet();

    for (String key : keys) {
      if (terms.get(key) instanceof String) {
        predicateList.add(builder.equal(builder.upper(getPathForTypeString(root, key)), terms.get(key).toString().toUpperCase()));
      } else {
        throw new DataAcessException("IgnoreCase criteria supports for only the String type " + terms.get(key).getClass());

      }

    }
    return predicateList;
  }

  private Path<String> getPathForTypeString(Root<? extends Object> root, String key) {
    Path<String> path = null;
    if (key.indexOf(MerchandisingConstants.STR_DOT) != -1) {

      String[] keysArray = StringUtils.tokenizeToStringArray(key, MerchandisingConstants.STR_DOT);
      Join<Object, Object> join = getJoinForPath(root, keysArray);
      // Below condition and split is for the EmbededKey
      if (keysArray[keysArray.length - 1].contains(MerchandisingConstants.STR_COLON)) {
        String[] atts = StringUtils.split(keysArray[keysArray.length - 1], MerchandisingConstants.STR_COLON);
        path = join.get(atts[0]).get(atts[1]);
      } else {
        path = join.get(keysArray[keysArray.length - 1]);
      }
    } else {
      path = root.get(key);
    }
    return path;
  }

  private Join<Object, Object> getJoinForPath(Root<? extends Object> root, String[] columns) {
    Join<Object, Object> join = root.join(columns[0]);
    int iterLength = columns.length - 1;
    for (int i = 1; i < iterLength; i++) {
      join = join.join(columns[i]);
    }

    return join;
  }

  private Path<Date> getPathForTypeDate(Root<? extends Object> root, String key) {
    Path<Date> path = null;
    if (key.indexOf(MerchandisingConstants.STR_DOT) != -1) {

      String[] keysArray = StringUtils.split(key, MerchandisingConstants.STR_DOT);
      Join<Object, Object> join = getJoinForPath(root, keysArray);

      // Below condition and split is for the EmbededKey
      if (keysArray[keysArray.length - 1].contains(MerchandisingConstants.STR_COLON)) {
        String[] atts = StringUtils.split(keysArray[keysArray.length - 1], MerchandisingConstants.STR_COLON);
        path = join.get(atts[0]).get(atts[1]);
      } else {
        path = join.get(keysArray[keysArray.length - 1]);
      }
    } else {
      path = root.get(key);
    }
    return path;
  }

  private Path<Object> getPath(Root<? extends Object> root, String key) {
    Path<Object> path = null;
    if (key.indexOf(MerchandisingConstants.STR_DOT) != -1) {

      String[] keysArray = StringUtils.split(key, MerchandisingConstants.STR_DOT);
      Join<Object, Object> join = getJoinForPath(root, keysArray);

      // Below condition and split is for the EmbededKey
      if (keysArray[keysArray.length - 1].contains(MerchandisingConstants.STR_COLON)) {
        String[] atts = StringUtils.split(keysArray[keysArray.length - 1], MerchandisingConstants.STR_COLON);
        path = join.get(atts[0]).get(atts[1]);
      } else {
        path = join.get(keysArray[keysArray.length - 1]);
      }
    } else {
      path = root.get(key);
    }
    return path;
  }

}
