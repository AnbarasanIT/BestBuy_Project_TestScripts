package com.bestbuy.search.bt.Screens;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;

/**
 * Class that contains the implementation of the methods that are called in the
 * Test methods of the Class LoginTest
 */

public class LoginScreen extends BaseScreen {

	private Log log = LogFactory.getLog("LoginScreen");
	private MerchandisingUI_UserInfo merchUserInfo;
	private MerchandisingUIIdentifiers merchUI;
	private String screenName;
	private String methodName;

	public LoginScreen() {
		merchUserInfo = new MerchandisingUI_UserInfo();
		merchUI = new MerchandisingUIIdentifiers();

	}

	/**
	 * Implementation of the Method to Login with empty credentials
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void emptyLogin(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing Emptylogin scenario");
			waitForElementPresent(merchUI.getUserName(), methodName);
			getXpathWebElement(merchUI.getUserName());
			click();
			clear();
			sendKeys("");
			waitForElementPresent(merchUI.getPassword(), methodName);
			getXpathWebElement(merchUI.getPassword());
			click();
			clear();
			sendKeys("");
			waitForElementPresent(merchUI.getLoginButton(), methodName);
			getXpathWebElement(merchUI.getLoginButton());
			click();
			
		} catch (Exception e) {
			log.info("Exception in emptyLogin" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Login without Username and with Password
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void withoutUserNameAndWithPassword(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing WithoutUserName_WithPassword scenario");
			waitForElementPresent(merchUI.getUserName(), methodName);
			getXpathWebElement(merchUI.getUserName());
			click();
			clear();
			sendKeys("");
			waitForElementPresent(merchUI.getPassword(), methodName);
			getXpathWebElement(merchUI.getPassword());
			click();
			clear();
			sendKeys(merchUserInfo.getPasswordValue());
			waitForElementPresent(merchUI.getLoginButton(), methodName);
			getXpathWebElement(merchUI.getLoginButton());
			click();
		} catch (Exception e) {
			log.info("Exception in withoutUserNameAndWithPassword"
					+ e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Login with Username and without Password
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void withoutPasswordAndWithUserName(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing WithoutPassword_WithUserName scenario");
			waitForElementPresent(merchUI.getUserName(), methodName);
			getXpathWebElement(merchUI.getUserName());
			click();
			clear();
			sendKeys(merchUserInfo.getUserNameValue());
			waitForElementPresent(merchUI.getPassword(), methodName);
			getXpathWebElement(merchUI.getPassword());
			click();
			clear();
			sendKeys("");
			waitForElementPresent(merchUI.getLoginButton(), methodName);
			getXpathWebElement(merchUI.getLoginButton());
			click();
		} catch (Exception e) {
			log.info("Exception in withoutPasswordAndWithUserName"
					+ e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Login with the invalid credentials
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void invalidLogin(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing Invalidlogin scenario");
			waitForElementPresent(merchUI.getUserName(), methodName);
			getXpathWebElement(merchUI.getUserName());
			click();
			sendKeys("Dummy1");
			waitForElementPresent(merchUI.getPassword(), methodName);
			getXpathWebElement(merchUI.getPassword());
			click();
			sendKeys("Dum");
			waitForElementPresent(merchUI.getLoginButton(), methodName);
			getXpathWebElement(merchUI.getLoginButton());
			click();
		} catch (Exception e) {
			log.info("Exception in invalidLogin" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Login with valid credentials
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void validLogin(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing Validlogin scenario");
			waitForElementPresent(merchUI.getUserName(), methodName);
			getXpathWebElement(merchUI.getUserName());
			click();
			clear();
			sendKeys(merchUserInfo.getUserNameValue());
			waitForElementPresent(merchUI.getPassword(), methodName);
			getXpathWebElement(merchUI.getPassword());
			click();
			clear();
			sendKeys(merchUserInfo.getPasswordValue());
			waitForElementPresent(merchUI.getLoginButton(), methodName);
			getXpathWebElement(merchUI.getLoginButton());
			click();
		} catch (Exception e) {
			log.info("Exception in validLogin" + e.getMessage());
		}
	}
}
