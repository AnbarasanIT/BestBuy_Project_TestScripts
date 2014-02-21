package com.bestbuy.search.merchandising.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.domain.Attribute;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class AttributeDAO extends BaseDAO<Long,Attribute> implements IAttributeDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Method to get facet fields from database
	 * 
	 * @return List<String>
	 */
	public List<String> getFacetFieldsFromDatabase() {
		List<String> attributesDB = jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
				PreparedStatement st = con.prepareStatement(MerchandisingConstants.ATTRIBTUES_NAMES_SQL);
				return st;
			}
		}, new ResultSetExtractor<List<String>>() {
			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> results = null;
				while (rs.next()) {
					if (results == null) {
						results = new ArrayList<String>();
					}
					results.add(rs.getString("ATTRIBUTE_NAME"));
				}
				return results;
			}
		});
		return attributesDB;
	}
	
	/**
	 * Method to get attribute id for facet field
	 * 
	 * @param facetFieldName
	 * @return String facetFieldName
	 */
	public Long getAttributeIdForFacetField(String facetFieldName) {
		return jdbcTemplate.queryForLong(MerchandisingConstants.ATTRIBUTES_ID_SQL, facetFieldName);	
	}
	
	/**
	 * Method to get facet field values by facet field
	 * 
	 * @param attributeId
	 * @return List<String>
	 */
	public List<String> getFacetFieldValuesByFacetField(final Long attributeId) {
		List<String> attributeValuesDB = jdbcTemplate.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
				PreparedStatement st = con.prepareStatement(MerchandisingConstants.ATTRIBTUES_VALUES_SQL);
				st.setLong(1, attributeId);
				return st;
			}
		}, new ResultSetExtractor<List<String>>() {
			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> results = null;
				while (rs.next()) {
					if (results == null) {
						results = new ArrayList<String>();
					}
					results.add(rs.getString("ATTRIBUTE_VALUE"));
				}
				return results;
			}});
		return attributeValuesDB;
	}
	
	/**
	 * Method to insert facet values
	 * 
	 * @param insertFacetFieldValues
	 * @param attributeId
	 */
	public void insertFacetValues(final List<String> insertFacetFieldValues,
			final Long attributeId) {
		if (insertFacetFieldValues != null && insertFacetFieldValues.size() > 0) {
			jdbcTemplate.execute(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
					PreparedStatement st = con.prepareStatement(MerchandisingConstants.INSERT_FACET_VALUE);
					for (String insertValue : insertFacetFieldValues) {
						if (insertValue.contains("'")) {
							insertValue = insertValue.replaceAll("'","''");
						}
						st.setString(1, insertValue);
						st.setLong(2, attributeId);
						st.addBatch();
					}
					return st;
				}
			}, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(
						PreparedStatement ps)
						throws SQLException,
						DataAccessException {
					Boolean executed = false;
					ps.executeBatch();
					executed = true;
					return executed;
				}
			});
		}
	}
	
	/**
	 * Method to update facet values
	 * 
	 * @param updateFacetFieldValues
	 * @param attributeId
	 */
	public void updateFacetValues(final List<String> updateFacetFieldValues, Long attributeId) {
		StringBuilder sql = new StringBuilder("UPDATE ATTRIBUTE_VALUES SET IS_ACTIVE = 'N' where ATTRIBUTE_ID = "
				+ attributeId + " AND ATTRIBUTE_VALUE IN (");
		String[] arrayAttributeValues = new String[updateFacetFieldValues.size()];
		int count = 0;
		for (String attributeValue : updateFacetFieldValues) {
			arrayAttributeValues[count] = attributeValue;
			if (attributeValue.contains("'")) {
				attributeValue = attributeValue.replaceAll("'", "''");
			}
			sql.append("?");
			count++;
			if (count != updateFacetFieldValues.size()) {
				sql.append(",");
			} else {
				sql.append(")");
			}
		}
		final String finSql = sql.toString();
		final String[] finalArrayAttributeValues = arrayAttributeValues;
		jdbcTemplate.execute(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
				PreparedStatement st = con.prepareStatement(finSql);
				int c = 1;
				for (String s : finalArrayAttributeValues) {
					st.setString(c, s);
					c++;
				}
				return st;
			}
		}, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(
					PreparedStatement ps)
					throws SQLException,
					DataAccessException {
				return ps.executeUpdate();
			}
		});
	}
	
	/**
	 * Method to invalidate attribute values
	 * 
	 * @param attributesDB
	 */
	public void invalidateAttributeValues(List<String> attributesDB) {
		if (attributesDB != null && attributesDB.size() > 0) {
			StringBuffer facetBuffer = new StringBuffer();
			for (String attributeName : attributesDB) {
				facetBuffer.append("'" + attributeName + "',");
			}
			facetBuffer.setCharAt(facetBuffer.lastIndexOf(","), ' ');
			String invalidAttrSql = "UPDATE ATTRIBUTES SET IS_ACTIVE = 'N' WHERE ATTRIBUTE_NAME IN ("
				+ facetBuffer.toString() + ")";
			String invalidAttrValSql = "UPDATE ATTRIBUTE_VALUES SET IS_ACTIVE = 'N' WHERE ATTRIBUTE_ID IN "
				+ "( SELECT ATTRIBUTE_ID FROM ATTRIBUTES WHERE ATTRIBUTE_NAME IN ("
				+ facetBuffer.toString() + "))";
			jdbcTemplate.batchUpdate(
				new String[] { invalidAttrValSql, invalidAttrSql });
		}
	}
	
	/**
	 * This Method will invalidate the Category Dependent Facet if its AttrValue is Invalid
	 * 
	 */
	public void invalidateDepCategoryFacets() {
		jdbcTemplate.update(MerchandisingConstants.INVALIDATE_DEP_FACET_ATTR_SQL);
	}
	
	/**
	 * This Method will invalidate the Facets if all the Dependent Facet Values are Invalid
	 * 
	 */
	public void invalidateFacets() {
		jdbcTemplate.update(MerchandisingConstants.INVALIDATE_FACET_ATTR_SQL);
	}
	
	/**
	 * This method will invalidated the Banner Dependent facet if its facet Value is Invalid
	 * 
	 */
	public void invalidBannerFacets() {
		jdbcTemplate.update(MerchandisingConstants.INVALIDATE_BANNER_FACET_ATTR_SQL);
	}
	
	/**
	 * This Method will invalidate the Banners if the Banner Dependent facet Values are invalid
	 * 
	 */
	public void invalidateBanners() {
		jdbcTemplate.update(MerchandisingConstants.INVALIDATE_BANNER_ATTR_SQL);
	}
}
