package com.bestbuy.search.bt.Screens;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestbuy.search.bt.selenium.util.MagicNumbers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIData;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;

/**
 * Class that contains the implementation of the methods that are called in the
 * Test methods of the Class FacetOrderingTest
 */

public class FacetOrderingScreen extends BaseScreen {

	private Log log = LogFactory.getLog("LoginScreen");

	private MerchandisingUIIdentifiers merchUI;
	private MerchandisingUIData merchData;
	private String screenName;
	private String methodName;

	public FacetOrderingScreen() {

		merchUI = new MerchandisingUIIdentifiers();
		merchData = new MerchandisingUIData();
	}

	/**
	 * Implementation of the Method to Add FacetOrdering
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void facetOrderingPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing FacetOrderingPage scenario");
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			if (getXpathWebElement(merchUI.getFacetsOrderingTab())
					.isDisplayed()) {
				waitForElementPresent(merchUI.getFacetsOrderingTab(),
						methodName);
				getXpathWebElement(merchUI.getFacetsOrderingTab());
				click();
			} else {

				waitForElementPresent(merchUI.getIpadViewMenuTab(), methodName);
				getXpathWebElement(merchUI.getIpadViewMenuTab());
				click();
				waitForElementPresent(merchUI.getIpadViewFacetsOrdering(),
						methodName);
				getXpathWebElement(merchUI.getIpadViewFacetsOrdering());
				click();
			}
			waitForElementPresent(merchUI.getSelectCreateTree(), methodName);
			getXpathWebElement(merchUI.getSelectCreateTree());
			click();
			waitForElementPresent(merchUI.getCreateTree(), methodName);
			getXpathWebElement(merchUI.getCreateTree());
			click();
		} catch (Exception e) {
			log.info("Exception in facetOrderingPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Edit FacetOrdering
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void editFacetOrdering(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing editFacetOrdering scenario");
			waitForElementPresent(merchUI.getFacetOrderingEditcategory(),
					methodName);
			getXpathWebElement(merchUI.getFacetOrderingEditcategory());
			click();
			waitForElementPresent(merchUI.getFacetOrderingBestbuy(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingBestbuy());
			click();
			waitForElementPresent(merchUI.getFacetOrderingCar(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingCar());
			click();
			waitForElementPresent(merchUI.getFacetOrderingEditBtn(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingEditBtn());
			click();
			Thread.sleep(MagicNumbers.FOURTY_SECONDS);
			waitForElementPresent(merchUI.getFacetOrderingDrag(), methodName);
			dragAndDrop(merchUI.getFacetOrderingDrag());
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			waitForElementPresent(merchUI.getFacetOrderingSaveBtn(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingSaveBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);

		} catch (Exception e) {
			log.info("Exception in editFacetOrdering" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to lock FacetOrdering
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void lockFacetOrdering(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing lockFacetOrdering scenario");
		} catch (Exception e) {
			log.info("Exception in lockFacetOrdering" + e.getMessage());
		}

	}

	/**
	 * Implementation of the Method to Expand tree of the FacetOrdering
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void editFacetDisplayOrder(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing editFacetDisplayOrder scenario");
			waitForElementPresent(merchUI.getFacetOrderingEditcategory(),
					methodName);
			getXpathWebElement(merchUI.getFacetOrderingEditcategory());
			click();
			waitForElementPresent(merchUI.getFacetOrderingBestbuy(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingBestbuy());
			click();
			waitForElementPresent(merchUI.getFacetOrderingCar(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingCar());
			click();
			waitForElementPresent(merchUI.getFacetOrderingEditBtn(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingEditBtn());
			click();
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			if(getXpathWebElement(merchUI.getFacetOrderingTextfield()).isDisplayed()) {	
				waitForElementPresent(merchUI.getFacetOrderingTextfield(), methodName);
				getXpathWebElement(merchUI.getFacetOrderingTextfield());
				click();
				sendKeys(merchData.getFacetOrderingTextFieldData()); 
				Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
				if (getXpathWebElement(merchUI.getFacetOrderingTextfield1()).isDisplayed()) {					
					waitForElementPresent(merchUI.getFacetOrderingTextfield1(), methodName);
					enterKeys(merchUI.getFacetOrderingTextfield1(),merchData.getFacetOrderingTextFieldData1());
				}			
			}		
			else{
				log.info("Text Filed not available" );
			}
			waitForElementPresent(merchUI.getFacetOrderingSaveBtn(), methodName);
			getXpathWebElement(merchUI.getFacetOrderingSaveBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);

		} catch (Exception e) {
			log.info("Exception in editFacetDisplayOrder" + e.getMessage());
		}

	}

}
