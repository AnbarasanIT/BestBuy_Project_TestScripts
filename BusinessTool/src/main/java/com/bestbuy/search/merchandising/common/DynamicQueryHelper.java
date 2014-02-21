package com.bestbuy.search.merchandising.common;

import java.util.List;

import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;

/**
 * Class that Constructs the Queries based on Criteria
 * 
 * @author Lakshmi.Valluripalli
 */
public final class DynamicQueryHelper {

  private StringBuilder queryString;
  private final static String OBJECT_NAME = "obj";
  private boolean hasWhere = false;

  /**
   * Enum class that defines Operator Type
   */
  public enum Operator {
    IN(" IN "), BETWEEN(" BETWEEN "), NE(" != "), GT(" > "), LT(" < "), GE(" >= "), LE(" <= "), EQ(" = "), LIKE(" LIKE "), NOTIN(" NOT IN "),;

    private String opertorType;

    private Operator(String opertorType) {
      this.opertorType = opertorType;
    }

    public String getOpertorType() {
      return opertorType;
    }
  }

  public DynamicQueryHelper(final Class className) {
    queryString = new StringBuilder("SELECT " + OBJECT_NAME + " FROM " + className.getSimpleName() + " " + OBJECT_NAME);
  }

  public DynamicQueryHelper(final Class className, boolean isCount) {
    queryString = new StringBuilder("SELECT count(*) FROM " + className.getSimpleName() + " " + OBJECT_NAME);
  }

  public String getQueryString() {
    return queryString.toString();
  }

  public void prepareQuery(final SearchCriteria criteria) {
    int i = 0;
    // append the Where Clause

    if (criteria.getSearchTermsIgnoreCase() != null && !criteria.getSearchTermsIgnoreCase().isEmpty()) {
      for (String key : criteria.getSearchTermsIgnoreCase().keySet()) {
        CheckForUniqueTerm(key, i++, Operator.EQ);
      }
    }
    if (criteria.getSearchTerms() != null && !criteria.getSearchTerms().isEmpty()) {
      for (String key : criteria.getSearchTerms().keySet()) {
        appendWhereClause(key, i++, Operator.EQ);
      }
    }
    // append lessThan Clause
    if (criteria.getLesserCriteria() != null && !criteria.getLesserCriteria().isEmpty()) {
      for (String key : criteria.getLesserCriteria().keySet()) {
        appendWhereClause(key, i++, Operator.LT);
      }
    }
    // append lessThanEq Clause
    if (criteria.getLesserEqCriteria() != null && !criteria.getLesserEqCriteria().isEmpty()) {
      for (String key : criteria.getLesserEqCriteria().keySet()) {
        appendWhereClause(key, i++, Operator.LE);
      }
    }
    // append GreateThan Clause
    if (criteria.getGreaterCriteria() != null && !criteria.getGreaterCriteria().isEmpty()) {
      for (String key : criteria.getGreaterCriteria().keySet()) {
        appendWhereClause(key, i++, Operator.GT);
      }
    }
    // append GreateThanEq Clause
    if (criteria.getGreaterEqCriteria() != null && !criteria.getGreaterEqCriteria().isEmpty()) {
      for (String key : criteria.getGreaterEqCriteria().keySet()) {
        appendWhereClause(key, i++, Operator.GE);
      }
    }

    // append GreateThanEq Clause
    if (criteria.getNotEqCriteria() != null && !criteria.getNotEqCriteria().isEmpty()) {
      for (String key : criteria.getNotEqCriteria().keySet()) {
        appendWhereClause(key, i++, Operator.NE);
      }
    }

    // append Like Clause
    if (criteria.getLikeCriteria() != null && !criteria.getLikeCriteria().isEmpty()) {
      for (String key : criteria.getLikeCriteria().keySet()) {
        appendWhereClause(key, i++, Operator.LIKE);
      }
    }
    // append IN Clause
    if (criteria.getInCriteria() != null && !criteria.getInCriteria().isEmpty()) {
      for (String key : criteria.getInCriteria().keySet()) {
        List<Object> values = (List<Object>) criteria.getInCriteria().get(key);
        appendInClause(key, i++, Operator.IN, values.size());
      }
    }
    
   //append NOT IN Clause
	if(criteria.getNotInCriteria() != null && !criteria.getNotInCriteria().isEmpty()){
		for(String key : criteria.getNotInCriteria().keySet()){
			List<Object> values = (List<Object>)criteria.getNotInCriteria().get(key);
			appendInClause(key,i++,Operator.NOTIN,values.size());
		}
	}



    // append order by Clause
    if (criteria.getOrderByCriteria() != null && !criteria.getOrderByCriteria().isEmpty()) {
      appendOrderByClause(criteria.getOrderByCriteria());
    }

    if (!criteria.getAndCriteriaColumnValues().isEmpty()) {
      appendLogicalClause(criteria.getAndCriteriaColumnValues(), i++, MerchandisingConstants.AND);
    }

    if (!criteria.getOrCriteriaColumnValues().isEmpty()) {
      appendLogicalClause(criteria.getOrCriteriaColumnValues(), i++, MerchandisingConstants.OR);
    }

  }

  public void CheckForUniqueTerm(final String field, final int i, final Operator operatorType) {
    queryString.append(hasWhere ? " AND " : " WHERE ");
    queryString.append("UPPER(");
    queryString.append(field);
    queryString.append(")");
    queryString.append(operatorType.getOpertorType() + "?");
    queryString.append(i + 1);
    hasWhere = true;
  }

  /**
   * @param field
   * @param i
   */
  public void appendWhereClause(final String field, final int i, final Operator operatorType) {
    queryString.append(hasWhere ? " AND " : " WHERE ");
    queryString.append(field);
    queryString.append(operatorType.getOpertorType() + "?");
    queryString.append(i + 1);
    hasWhere = true;
  }

  /**
   * This works for only LIKE operator for AND or OR .
   * For eg: ColumnName1 like %<value>% AND ColumnName2 like %<value>%
   * but not ColumnName1=<value> AND ColumnName2=<value>
   * 
   * @param list
   * @param i
   */
  public void appendLogicalClause(final List<KeyValueWrapper> list, final int i, final String logicalOperator) {
    queryString.append(hasWhere ? " AND " : " WHERE ");
    int count = 0;
    int paraCount = i + 1;
    for (KeyValueWrapper keyValueWrapper : list) {
      count++;
      queryString.append(keyValueWrapper.getKey());
      queryString.append(" ");
      queryString.append(Operator.LIKE.opertorType + "?");
      queryString.append(paraCount++);
      if (count < list.size()){
        queryString.append(" ");
        queryString.append(logicalOperator);
        queryString.append(" ");
      }
    }
    hasWhere = true;
  }

  /**
   * @param field
   * @param i
   */
  public void appendInClause(final String field, int i, final Operator operatorType, int totalParams) {
    queryString.append(hasWhere ? " AND " : " WHERE ");
    queryString.append(field);
    queryString.append(operatorType.getOpertorType() + "( ");
    while (totalParams > 0) {
      queryString.append("?");
      queryString.append(++i);
      totalParams--;
      if (totalParams > 0)
        queryString.append(", ");
    }
    queryString.append(" )");
    hasWhere = true;
  }

  public void appendOrderByClause(final String field) {
    queryString.append("ORDER BY ");
    queryString.append(field);
    hasWhere = true;
  }
}
