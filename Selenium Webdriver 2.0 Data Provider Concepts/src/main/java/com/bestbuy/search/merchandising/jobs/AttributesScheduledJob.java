package com.bestbuy.search.merchandising.jobs;

import static com.bestbuy.search.merchandising.common.ErrorType.ATTRIBUTES_JOB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.exception.ServiceException;

/**
 * Scheduled attributes job
 * 
 * @author a948063
 *
 */
public class AttributesScheduledJob extends BaseJob {
	private final static BTLogger logger = BTLogger.getBTLogger(AttributesJob.class.getName());

	/**
	 * Execute method
	 * 
	 */
	@Override
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		try {
			logger.info("** Attributes Job Started****");
			populateBeans(jobContext);
			this.getAttributeService().importSolrData();
			this.insertJobStatus("SUCCESS");
			logger.info("**  Attributes Job Completed****");
		} catch (ServiceException e) {
			this.insertJobStatus("FAILED");
			logger.error("AttributesJob", e, ATTRIBUTES_JOB, "Error while executing the attributes job");
		}
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
