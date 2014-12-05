/**
 * 
 */
package com.bestbuy.search.merchandising.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Class used to build the native Quries
 * 
 * @author Lakshmi.Valluripalli
 */
public class SearchCriteria {

  private boolean isPaginationRequired = true;

  private Map<String, Object> searchTermsIgnoreCase;
  private Map<String, Object> searchTerms;
  private Map<String, Object> lesserCriteria;
  private Map<String, Object> lesserEqCriteria;
  private Map<String, Object> greaterCriteria;
  private Map<String, Object> greaterEqCriteria;
  private Map<String, Object> notEqCriteria;
  private Map<String, Object> likeCriteria;
  private Map<String, Object> inCriteria;
  private Map<String, Object> notInCriteria;
  private Map<String, SortOrder> orderCriteria;
  private List<KeyValueWrapper> orCriteriaColumnValues;
  private List<KeyValueWrapper> andCriteriaColumnValues;
  private String orderByCriteria;

  private PaginationWrapper paginationWrapper;

  private int totalRecords;
  private int totalRecordsRequested;

  public SearchCriteria() {
    searchTermsIgnoreCase = new HashMap<String, Object>();
    searchTerms = new HashMap<String, Object>();
    lesserCriteria = new HashMap<String, Object>();
    lesserEqCriteria = new HashMap<String, Object>();
    greaterCriteria = new HashMap<String, Object>();
    greaterEqCriteria = new HashMap<String, Object>();
    likeCriteria = new HashMap<String, Object>();
    inCriteria = new HashMap<String, Object>();
    notInCriteria = new HashMap<String, Object>();
    notEqCriteria = new HashMap<String, Object>();
    orderByCriteria = new String();
    orderCriteria = new HashMap<String, SortOrder>();
    paginationWrapper = new PaginationWrapper();
    orCriteriaColumnValues = new ArrayList<KeyValueWrapper>();
    andCriteriaColumnValues = new ArrayList<KeyValueWrapper>();
    totalRecords = 0;
    totalRecordsRequested = 0;
  }

  /**
   * @return the isPaginationRequired
   */
  public boolean isPaginationRequired() {
    return isPaginationRequired;
  }

  /**
   * @param isPaginationRequired
   *          the isPaginationRequired to set
   */
  public void setPaginationRequired(boolean isPaginationRequired) {
    this.isPaginationRequired = isPaginationRequired;
  }

  public Map<String, Object> getSearchTermsIgnoreCase() {
    return searchTermsIgnoreCase;
  }

  public void setSearchTermsIgnoreCase(Map<String, Object> primaryTerms) {
    this.searchTermsIgnoreCase = primaryTerms;
  }

  public Map<String, Object> getSearchTerms() {
    return searchTerms;
  }

  /**
   * sets the list of Search Terms for Where Clause
   * 
   * @param searchTerms
   */
  public void setSearchTerms(Map<String, Object> searchTerms) {
    this.searchTerms = searchTerms;
  }

  public int getTotalRecords() {
    return totalRecords;
  }

  /**
   * Sets the total number of records
   * 
   * @param totalRecords
   */
  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getTotalRecordsRequested() {
    return totalRecordsRequested;
  }

  /**
   * Used for the display count
   * 
   * @param totalRecordsRequested
   */
  public void setTotalRecordsRequested(int totalRecordsRequested) {
    this.totalRecordsRequested = totalRecordsRequested;
  }

  /**
   * gets the lesser criteria
   * 
   * @return
   */
  public Map<String, Object> getLesserCriteria() {
    return lesserCriteria;
  }

  /**
   * sets the lesser criteria
   * 
   * @param lesserCriteria
   */
  public void setLesserCriteria(Map<String, Object> lesserCriteria) {
    this.lesserCriteria = lesserCriteria;
  }

  /**
   * gets the lesser Equal to criteria
   * 
   * @return
   */
  public Map<String, Object> getLesserEqCriteria() {
    return lesserEqCriteria;
  }

  /**
   * sets the lesser Equal to criteria
   * 
   * @param lesserEqCriteria
   */
  public void setLesserEqCriteria(Map<String, Object> lesserEqCriteria) {
    this.lesserEqCriteria = lesserEqCriteria;
  }

  /**
   * gets the greater to criteria
   * 
   * @return
   */
  public Map<String, Object> getGreaterCriteria() {
    return greaterCriteria;
  }

  /**
   * sets the greater Criteria
   * 
   * @param greaterCriteria
   */
  public void setGreaterCriteria(Map<String, Object> greaterCriteria) {
    this.greaterCriteria = greaterCriteria;
  }

  /**
   * gets the greater Equal to Criteria
   * 
   * @return
   */
  public Map<String, Object> getGreaterEqCriteria() {
    return greaterEqCriteria;
  }

  /**
   * sets the greater Equal to Criteria
   * 
   * @param greaterEqCriteria
   */
  public void setGreaterEqCriteria(Map<String, Object> greaterEqCriteria) {
    this.greaterEqCriteria = greaterEqCriteria;
  }

  /**
   * gets the InCriteria
   * 
   * @return
   */
  public Map<String, Object> getInCriteria() {
    return inCriteria;
  }

  /**
   * sets the In Criteria
   * 
   * @param inCriteria
   */
  public void setInCriteria(Map<String, Object> inCriteria) {
    this.inCriteria = inCriteria;
  }

  /**
   * gets the Not Equal to Criteria
   * 
   * @return
   */
  public Map<String, Object> getNotEqCriteria() {
    return notEqCriteria;
  }

  /**
   * sets the Not Equal to Criteria
   * 
   * @param notEqCriteria
   */
  public void setNotEqCriteria(Map<String, Object> notEqCriteria) {
    this.notEqCriteria = notEqCriteria;
  }

  /**
   * sets the like Criteria
   * 
   * @return
   */
  public Map<String, Object> getLikeCriteria() {
    return likeCriteria;
  }

  /**
   * returns the like Criteria
   * 
   * @param likeCriteria
   */
  public void setLikeCriteria(Map<String, Object> likeCriteria) {
    this.likeCriteria = likeCriteria;
  }

  /**
   * @return the orderByCriteria
   */
  public String getOrderByCriteria() {
    return orderByCriteria;
  }

  /**
   * @param to
   *          set the orderByCriteria
   */
  public void setOrderByCriteria(String orderByCriteria) {
    this.orderByCriteria = orderByCriteria;
  }

  /**
   * @return the paginationWrapper
   */
  public PaginationWrapper getPaginationWrapper() {
    return paginationWrapper;
  }

  /**
   * @param paginationWrapper
   *          the paginationWrapper to set
   */
  public void setPaginationWrapper(PaginationWrapper paginationWrapper) {
    this.paginationWrapper = paginationWrapper;
  }

  /**
   * @return the orderCriteria
   */
  public Map<String, SortOrder> getOrderCriteria() {
    return orderCriteria;
  }

  /**
   * @param orderCriteria
   *          the orderCriteria to set
   */
  public void setOrderCriteria(Map<String, SortOrder> orderCriteria) {
    orderCriteria = orderCriteria;
  }

  /**
   * @return the orCriteriaColumnValues
   */
  public List<KeyValueWrapper> getOrCriteriaColumnValues() {
    return orCriteriaColumnValues;
  }

  /**
   * @param orCriteriaColumnValues
   *          the orCriteriaColumnValues to set
   */
  public void setOrCriteriaColumnValues(List<KeyValueWrapper> orCriteriaColumnValues) {
    this.orCriteriaColumnValues = orCriteriaColumnValues;
  }

  /**
   * @return the andCriteriaColumnValues
   */
  public List<KeyValueWrapper> getAndCriteriaColumnValues() {
    return andCriteriaColumnValues;
  }

  /**
   * @param andCriteriaColumnValues
   *          the andCriteriaColumnValues to set
   */
  public void setAndCriteriaColumnValues(List<KeyValueWrapper> andCriteriaColumnValues) {
    this.andCriteriaColumnValues = andCriteriaColumnValues;
  }

  public Map<String, Object> getNotInCriteria() {
    return notInCriteria;
  }

  public void setNotInCriteria(Map<String, Object> notInCriteria) {
    this.notInCriteria = notInCriteria;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SearchCriteria other = (SearchCriteria) obj;
    if (greaterCriteria == null) {
      if (other.greaterCriteria != null)
        return false;
    } else if (!greaterCriteria.equals(other.greaterCriteria))
      return false;
    if (greaterEqCriteria == null) {
      if (other.greaterEqCriteria != null)
        return false;
    } else if (!greaterEqCriteria.equals(other.greaterEqCriteria))
      return false;
    if (inCriteria == null) {
      if (other.inCriteria != null)
        return false;
    } else if (!inCriteria.equals(other.inCriteria))
      return false;
    if (lesserCriteria == null) {
      if (other.lesserCriteria != null)
        return false;
    } else if (!lesserCriteria.equals(other.lesserCriteria))
      return false;
    if (lesserEqCriteria == null) {
      if (other.lesserEqCriteria != null)
        return false;
    } else if (!lesserEqCriteria.equals(other.lesserEqCriteria))
      return false;
    if (likeCriteria == null) {
      if (other.likeCriteria != null)
        return false;
    } else if (!likeCriteria.equals(other.likeCriteria))
      return false;
    if (notEqCriteria == null) {
      if (other.notEqCriteria != null)
        return false;
    } else if (!notEqCriteria.equals(other.notEqCriteria))
      return false;
    if (orderByCriteria == null) {
      if (other.orderByCriteria != null)
        return false;
    } else if (!orderByCriteria.equals(other.orderByCriteria))
      return false;
    if (searchTermsIgnoreCase == null) {
      if (other.searchTermsIgnoreCase != null)
        return false;
    } else if (!searchTermsIgnoreCase.equals(other.searchTermsIgnoreCase))
      return false;
    if (searchTerms == null) {
      if (other.searchTerms != null)
        return false;
    } else if (!searchTerms.equals(other.searchTerms))
      return false;
    if (totalRecords != other.totalRecords)
      return false;
    if (totalRecordsRequested != other.totalRecordsRequested)
      return false;
    return true;
  }

}
