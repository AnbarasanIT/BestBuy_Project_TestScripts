package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.ErrorType.ATTRIBUTES_JOB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.exception.ServiceException;

/**
 * Job to Move all the Assets that reached End date to Expired Time Interval for
 * this job is configured in the merc.job.properties
 * 
 * @author Lakshmi.Valluripalli
 * @Refactored By Chanchal/Raja
 */
@Component
public class AttributesJob extends BaseJob {
	private final static BTLogger logger = BTLogger.getBTLogger(AttributesJob.class.getName());

	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		try {
			logger.info("** Attributes Job Started****");
			populateBeans(jobContext);
			Integer attributeCount = getCount(MerchandisingConstants.VALID_ATTRIBUTES_COUNT);
			Integer attributeValueCount = getCount(MerchandisingConstants.VALID_ATTRIBUTE_VALUES_COUNT);
			if ((attributeCount != null && attributeCount == 0) ||
					(attributeValueCount != null && attributeValueCount == 0)) {
				this.getAttributeService().importSolrData();
				this.insertJobStatus("SUCCESS");
			} else {
				String status = this.getAttributeJobStatus();
				if (StringUtils.isNotBlank(status) && status.equalsIgnoreCase("FAILED")) {
					this.getAttributeService().importSolrData();
					this.insertJobStatus("SUCCESS");
				}
			}
			logger.info("**  Attributes Job Completed****");
		} catch (ServiceException e) {
			this.insertJobStatus("FAILED");
			logger.error("AttributesJob", e, ATTRIBUTES_JOB, "Error while executing the attributes job");
		}
	}
	
	/**
	 * Method to get facet fields from database
	 * 
	 * @return Integer
	 */
	private Integer getCount(final String query) {
		Integer cnt = getJdbcTemplate().query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
				PreparedStatement st = con.prepareStatement(query);
				return st;
			}
		}, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				Integer count = null;
				while (rs.next()) {
					count = rs.getInt("CNT");
				}
				return count;
			}
		});
		return cnt;
	}
	
	/**
	 * Method to get attribute job status
	 * 
	 * @return String
	 */
	private String getAttributeJobStatus() {
		String status = getJdbcTemplate().query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
				PreparedStatement st = con.prepareStatement(MerchandisingConstants.ATTRIBUTES_JOB_STATUS);
				return st;
			}
		}, new ResultSetExtractor<String>() {
			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String st = null;
				while (rs.next()) {
					st = rs.getString("STATUS");
				}
				return st;
			}
		});
		return status;
	}
	
	/**
	 * Method to insert job status
	 * 
	 * @param status
	 */
	private void insertJobStatus(final String status) {
		this.getJdbcTemplate().execute(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
				PreparedStatement st = con.prepareStatement(MerchandisingConstants.JOB_INSERT_SQL);
				st.setString(1, "Attributes");
				st.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
				st.setString(3, status);
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
}
