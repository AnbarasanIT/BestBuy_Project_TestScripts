package com.bestbuy.search.merchandising.common;


/**
 * @author Kalaiselvi.Jaganathan
 *
 */
public class MerchandisingConstants {
	public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm:ss";
	public static final String DATE_FORMAT = "MM-dd-yyyy HH:mm";
	public static final String SYNONYM_TYPE_ONEWAY="One-way";
	public static final String SYNONYM_TYPE_TWOWAY="Two-way";
	public static final String USER_ID = "1";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final Long SYNONYM_ASSET_TYPE=3L;
	public static final Long PROMO_ASSET_TYPE=4L;
	public static final Long REDIRECT_ASSET_TYPE=11L;
	public static final Long BANNER_ASSET_TYPE=5L;
	public static final Long FACET_ASSET_TYPE=6L;
	public static final Long DRAFT_STATUS=2L; 
	public static final Long APPROVE_STATUS=3L; 
	public static final Long REJECT_STATUS=6L; 
	public static final Long AF_PUBLISH_STATUS = 10L;
	public static final Long EXPIRED_STATUS=4L;
	public static final Long DELETE_STATUS=7L; 
	public static final Long CF_PUBLISH_STATUS=8L;
	public static final Long NOT_VALID_STATUS=9L;
	public static final Long VALID_STATUS=1L;
	public static final String BANNER_TEMPLATE_HTML = "HTML_ONLY";
	public static final String BANNER_TEMPLATE_1HEADER="1HEADER_3SKU";
	public static final String BANNER_TEMPLATE_3HEADER="3HEADER_3SKU";
	public static final Long BANNER_TEMPLATE_HTML_VAL = 1L;
	public static final Long BANNER_TEMPLATE_1HEADER_VAL=2L;
	public static final Long BANNER_TEMPLATE_3HEADER_VAL=3L;
	public static final String STR_DOT = ".";
	public static final String STR_COLON = ":";
	public static final String STR_PIPE = "|";
	public static final String BESTBUY_CATEGORY_ID="0|cat00000";
	public static final int MAX_EXPECTED_VARIANCE = 10 ;

	/*** Job SQLs **/

	public static final String JOB_INSERT_SQL = "INSERT INTO BATCH_JOB_EXECUTION(JOB_EXECUTION_ID,JOB_INSTANCE_ID,START_TIME,STATUS)" +
			"VALUES (BATCH_JOB_EXECUTION_SEQ.nextval,?,?,?)";
	public static final String UPDATE_JOB_STATUS_Sql = "UPDATE BATCH_JOB_EXECUTION SET END_TIME=?,STATUS=?,EXIT_MESSAGE=?,LAST_UPDATED=? WHERE JOB_EXECUTION_ID=?";
	public static final String ATTRIBTUES_NAMES_SQL = "SELECT ATTRIBUTE_NAME FROM ATTRIBUTES WHERE IS_ACTIVE='Y'";
	public static final String ATTRIBUTES_ID_SQL = "SELECT ATTRIBUTE_ID FROM ATTRIBUTES WHERE ATTRIBUTE_NAME = ? AND IS_ACTIVE='Y'";
	public static final String ATTRIBTUES_VALUES_SQL = "SELECT ATTRIBUTE_VALUE FROM ATTRIBUTE_VALUES WHERE ATTRIBUTE_ID = ? AND IS_ACTIVE='Y'";
	public static final String INVALIDATE_DEP_FACET_ATTR_SQL = "UPDATE CATEGORY_FACET SET IS_ACTIVE = 'N' WHERE DEP_FACET_ATTR_VALUE_ID IN " +
			"( SELECT ATTRIBUTE_VALUE_ID FROM ATTRIBUTE_VALUES WHERE IS_ACTIVE = 'N' )";
	public static final String INVALIDATE_BANNER_FACET_ATTR_SQL  = "UPDATE CONTEXT_FACETS SET IS_ACTIVE = 'N' WHERE ATTRIBUTE_VALUE_ID IN " +
			"( SELECT ATTRIBUTE_VALUE_ID FROM ATTRIBUTE_VALUES WHERE IS_ACTIVE = 'N' )";
	public static final String INVALIDATE_FACET_ATTR_SQL = "UPDATE FACETS SET STATUS_ID = 9 WHERE FACET_ID IN ( SELECT FACET_ID FROM CATEGORY_FACET WHERE IS_ACTIVE = 'N' AND FACET_ID " +
			"NOT IN (SELECT FACET_ID FROM CATEGORY_FACET WHERE IS_ACTIVE = 'Y' ) )";
	public static final String INVALIDATE_BANNER_ATTR_SQL = "UPDATE BANNERS SET STATUS_ID = 9 WHERE BANNER_ID IN (SELECT BANNER_ID FROM BANNER_CONTEXTS WHERE " +
			"CONTEXT_ID IN ( SELECT CONTEXT_ID FROM CONTEXT_FACETS WHERE IS_ACTIVE = 'N' AND CONTEXT_ID " +
			"NOT IN (SELECT CONTEXT_ID FROM CONTEXT_FACETS WHERE IS_ACTIVE = 'Y' ) ) )";
	public static final String UPDATE_BANNER_STATUS_SQL="UPDATE BANNERS SET STATUS_ID=9 WHERE BANNER_ID NOT IN (SELECT BANNER_ID FROM " +
			"BANNER_CONTEXTS WHERE CONTEXT_ID IN (SELECT CONTEXT_ID FROM CONTEXTS WHERE IS_ACTIVE='Y'))";
	public static final String UPDATE_FACET_STATUS_SQL ="UPDATE FACETS SET STATUS_ID=9 WHERE FACET_ID NOT IN (SELECT FACET_ID FROM CATEGORY_FACET WHERE IS_ACTIVE='Y')";
	public static final String AF_JOB_INSERT_SQL = "INSERT INTO AF_BATCH_JOB_EXECUTION(JOB_EXECUTION_ID,JOB_INSTANCE_ID,START_TIME,STATUS)" +
			"VALUES (BATCH_JOB_EXECUTION_SEQ.nextval,?,?,?)";
	public static final String AF_UPDATE_JOB_STATUS_Sql = "UPDATE AF_BATCH_JOB_EXECUTION SET END_TIME=?,STATUS=?,EXIT_MESSAGE=?,LAST_UPDATED=? WHERE JOB_EXECUTION_ID=?";
	public static final String AF_JOB_SUCCESS_TIME_SQL = "SELECT MAX(START_TIME) FROM AF_BATCH_JOB_EXECUTION WHERE STATUS='SUCCESS' AND JOB_INSTANCE_ID=? ";
	public static final String CF_JOB_SUCCESS_TIME_SQL = "SELECT MAX(START_TIME) FROM BATCH_JOB_EXECUTION WHERE STATUS='SUCCESS' AND JOB_INSTANCE_ID=? ";
	public static final String INSERT_FACET_VALUE = "INSERT INTO ATTRIBUTE_VALUES (ATTRIBUTE_VALUE_ID,ATTRIBUTE_VALUE,IS_ACTIVE,ATTRIBUTE_ID) " +
			"VALUES (HIBERNATE_SEQUENCE.nextval,?,'Y',?)";
	
	public static final String VALID_ATTRIBUTES_COUNT = "SELECT COUNT(*) AS CNT FROM ATTRIBUTES WHERE IS_ACTIVE='Y'";
	public static final String VALID_ATTRIBUTE_VALUES_COUNT = "SELECT COUNT(*) AS CNT FROM ATTRIBUTE_VALUES WHERE IS_ACTIVE='Y'";
	public static final String ATTRIBUTES_JOB_STATUS = "select status from batch_job_execution where job_execution_id = (select max(job_execution_id) from batch_job_execution where job_instance_id = 'Attributes')";
}
