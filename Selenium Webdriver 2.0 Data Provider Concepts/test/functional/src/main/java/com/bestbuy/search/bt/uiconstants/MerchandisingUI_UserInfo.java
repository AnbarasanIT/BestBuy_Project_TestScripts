package com.bestbuy.search.bt.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class where constants of MerchandisingUI_UserInfo.xml are defined
 */

public class MerchandisingUI_UserInfo {
	
	private Log log = LogFactory.getLog("MerchandisingUI_UserInfo");

	private String userNameValue = "username_value";
	private String passwordValue = "password_value";

	/**
	 * Method to read and assign data to variables
	 */
	public MerchandisingUI_UserInfo() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMerchandisingUIUserInfoConstants();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in MerchandisingUI_UserInfo"+localException.getMessage());
		}
	}

	public String getUserNameValue() {
		return userNameValue;
	}

	public void setUserNameValue(String userNameValue) {
		this.userNameValue = userNameValue;
	}

	public String getPasswordValue() {
		return passwordValue;
	}

	public void setPasswordValue(String passwordValue) {
		this.passwordValue = passwordValue;
	}

}
