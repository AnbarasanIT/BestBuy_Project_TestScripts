package com.bestbuy.search.bt.Screens;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bestbuy.search.bt.model.UserAdmin;
import com.bestbuy.search.bt.selenium.util.MagicNumbers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIData;

/**
 * Class that contains the implementation of the methods that are called in the
 * Test methods of the Class UserAdminTest
 */

public class UserAdminScreen extends BaseScreen {

	private Log log = LogFactory.getLog("UserAdminScreen");
	private MerchandisingUIIdentifiers merchUI;
	private MerchandisingUIData merchData;
	private String screenName;
	private String methodName;
	
	public UserAdminScreen() {

		merchUI = new MerchandisingUIIdentifiers();
		merchData = new MerchandisingUIData();

	}

	/**
	 * Implementation of the Method to Add UserAdmin
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param useradmin
	 *            Object of UserAmin class
	 */

	public void addUserAdmin(String screenshotName, UserAdmin useradmin) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing addUserAdmin scenario");
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			if (getXpathWebElement(merchUI.getUserAdminTab()).isDisplayed()) {
				waitForElementPresent(merchUI.getUserAdminTab(), methodName);
				getXpathWebElement(merchUI.getUserAdminTab());
				click();
			} else {

				waitForElementPresent(merchUI.getIpadViewMenuTab(), methodName);
				getXpathWebElement(merchUI.getIpadViewMenuTab());
				click();
				waitForElementPresent(merchUI.getIpadViewUserAdmin(),
						methodName);
				getXpathWebElement(merchUI.getIpadViewUserAdmin());
				click();
			}		
			waitForElementPresent(merchUI.getUserAddIcon(), methodName);
			getXpathWebElement(merchUI.getUserAddIcon());
			click();
			waitForElementPresent(merchUI.getUserFirstName(), methodName);
			getXpathWebElement(merchUI.getUserFirstName());
			click();
			sendKeys(useradmin.getUserFirstNameData());
			waitForElementPresent(merchUI.getUserLastName(), methodName);
			getXpathWebElement(merchUI.getUserLastName());
			click();
			sendKeys(useradmin.getUserLastNameData());
			waitForElementPresent(merchUI.getUserId(), methodName);
			getXpathWebElement(merchUI.getUserId());
			click();
			sendKeys(useradmin.getUserIDData());
			waitForElementPresent(merchUI.getUserRole(), methodName);
			getXpathWebElement(merchUI.getUserRole());
			click();
			waitForElementPresent(merchUI.getUserCreateBtn(), methodName);
			getXpathWebElement(merchUI.getUserCreateBtn());
			click();
			isTextPresent(useradmin.getUserFirstNameData());
		} catch (Exception e) {
			log.info("Exception in addUserAdmin" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to edit UserAdmin
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param useradmin
	 *            Object of the UserAdmin Class 
	 */

	public void editUserAdmin(String screenshotName, UserAdmin useradmin) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing editUserAdmin scenario");
			waitForElementPresent(merchUI.getUserEditIcon(), methodName);
			getXpathWebElement(merchUI.getUserEditIcon());
			click();
			waitForElementPresent(merchUI.getUserLastName(), methodName);
			getXpathWebElement(merchUI.getUserLastName());
			click();
			clear();
			sendKeys(merchData.getUserLastNameUpdatedData());
			waitForElementPresent(merchUI.getUserId(), methodName);
			getXpathWebElement(merchUI.getUserId());
			click();
			clear();
			sendKeys(useradmin.getUserIDData());
			waitForElementPresent(merchUI.getUserRole(), methodName);
			getXpathWebElement(merchUI.getUserRole());
			click();
			waitForElementPresent(merchUI.getUserUpdateIcon(), methodName);
			getXpathWebElement(merchUI.getUserUpdateIcon());
			click();
			isTextPresent(merchData.getUserLastNameUpdatedData());
			
		} catch (Exception e) {
			log.info("Exception in editUserAdmin" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Sort and Refresh UserAdmin
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void sortnRefreshUserAdmin(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing sortnRefreshUserAdmin scenario");
			waitForElementPresent(merchUI.getUserSorting(), methodName);
			getXpathWebElement(merchUI.getUserSorting());
			click();
			waitForElementPresent(merchUI.getUserSorting(), methodName);
			getXpathWebElement(merchUI.getUserSorting());
			click();
			waitForElementPresent(merchUI.getUserRefershIcon(), methodName);
			getXpathWebElement(merchUI.getUserRefershIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in sortnRefreshUserAdmin" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search UserAdmin
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void searchUsers(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing searchUsers scenario");
			waitForElementPresent(merchUI.getUserNameSearchField(), methodName);
			enterKeys(merchUI.getUserNameSearchField(),
					merchData.getUserSearchData());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in searchUsers" + e.getMessage());
		}
	}
	
	
	public void searchInValidUsers(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing searchUsers scenario");
			waitForElementPresent(merchUI.getUserNameSearchField(), methodName);
			enterKeys(merchUI.getUserNameSearchField(),
					merchData.getUserInvalidSearchData());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in searchUsers" + e.getMessage());
		}
	}
	
	public void VerifyCreatePopUpTextFieldsValidation(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("ExecutingVerifyCreatePopUpTextFieldsValidation scenario");
			waitForElementPresent(merchUI.getUserAddIcon(), methodName);
			getXpathWebElement(merchUI.getUserAddIcon());
			click();			
			waitForElementPresent(merchUI.getUserCreateBtn(), methodName);
			getXpathWebElement(merchUI.getUserCreateBtn());
			click();
			waitForElementPresent(merchUI.getUserAddUpdatePopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getUserAddUpdatePopUpCloseBtn());
			click();
			
		} catch (Exception e) {
			log.info("Exception in VerifyCreatePopUpTextFieldsValidation" + e.getMessage());
		}
	}

	public void VerifyUpdatePopUpTextFieldsValidation(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing VerifyUpdatePopUpTextFieldsValidation scenario");
			waitForElementPresent(merchUI.getUserEditIcon(), methodName);
			getXpathWebElement(merchUI.getUserEditIcon());
			click();
			waitForElementPresent(merchUI.getUserFirstName(), methodName);
			getXpathWebElement(merchUI.getUserFirstName());
			click();
			clear();
			waitForElementPresent(merchUI.getUserLastName(), methodName);
			getXpathWebElement(merchUI.getUserLastName());
			click();
			clear();
			waitForElementPresent(merchUI.getUserId(), methodName);
			getXpathWebElement(merchUI.getUserId());
			click();
			clear();
			waitForElementPresent(merchUI.getUserUpdateIcon(), methodName);
			getXpathWebElement(merchUI.getUserUpdateIcon());
			click();
			waitForElementPresent(merchUI.getUserAddUpdatePopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getUserAddUpdatePopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in VerifyUpdatePopUpTextFieldsValidation" + e.getMessage());
		}
	}

}
