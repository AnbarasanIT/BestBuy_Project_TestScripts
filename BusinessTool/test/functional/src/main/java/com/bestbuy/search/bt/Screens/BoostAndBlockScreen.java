package com.bestbuy.search.bt.Screens;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestbuy.search.bt.model.BoostAndBlock;
import com.bestbuy.search.bt.selenium.util.MagicNumbers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIData;

/**
 * Class that contains the implementation of the methods that are called in the
 * Test methods of the Class BoostBlockTest
 */

public class BoostAndBlockScreen extends BaseScreen {

	private Log log = LogFactory.getLog("BoostAndBlockScreen");
	private MerchandisingUIIdentifiers merchUI;
	private MerchandisingUIData merchData;
	private String screenName;
	private String methodName;

	/**
	 * Constructor of the class which initializes other constructors
	 */
	public BoostAndBlockScreen() {
		merchUI = new MerchandisingUIIdentifiers();
		merchData = new MerchandisingUIData();
	}

	public void selectBoostAndBlockTab(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing selectFacetsTab scenario");
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			if (getXpathWebElement(merchUI.getBoostnBlockTab()).isDisplayed()) {
				waitForElementPresent(merchUI.getBoostnBlockTab(), methodName);
				getXpathWebElement(merchUI.getBoostnBlockTab());
				click();
			} else {
				waitForElementPresent(merchUI.getIpadViewMenuTab(), methodName);
				getXpathWebElement(merchUI.getIpadViewMenuTab());
				click();
				waitForElementPresent(merchUI.getIpadViewBoostAndBlock(),
						methodName);
				getXpathWebElement(merchUI.getIpadViewBoostAndBlock());
				click();
			}
		} catch (Exception e) {
			log.info("Exception in addFacets" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Add Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param boostandblock
	 *            Object of BoostAndBlock class
	 */

	public void addBoostAndBlock(String screenshotName,
			BoostAndBlock boostandblock) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing addBoostAndBlock scenario");
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

			waitForElementPresent(merchUI.getUserAddIcon(), methodName);
			getXpathWebElement(merchUI.getUserAddIcon());
			click();
			waitForElementPresent(merchUI.getBbCreateSearchTerm(), methodName);
			getXpathWebElement(merchUI.getBbCreateSearchTerm());
			click();
			clear();
			sendKeys(boostandblock.getBbCreateSearchData());
			waitForElementPresent(merchUI.getCreateTreeView(), methodName);
			getXpathWebElement(merchUI.getCreateTreeView());
			click();
			waitForElementPresent(merchUI.getSelectCreateTree(), methodName);
			getXpathWebElement(merchUI.getSelectCreateTree());
			click();
			waitForElementPresent(merchUI.getBbSelectedCategory(), methodName);
			getXpathWebElement(merchUI.getBbSelectedCategory());
			click();
			waitForElementPresent(merchUI.getBbCreateNxtBtn(), methodName);
			getXpathWebElement(merchUI.getBbCreateNxtBtn());
			click();
			waitForElementPresent(merchUI.getBbCreateBoostBtn(), methodName);
			getXpathWebElement(merchUI.getBbCreateBoostBtn());
			click();
			waitForElementPresent(merchUI.getBbCreateBoostBtn2(), methodName);
			getXpathWebElement(merchUI.getBbCreateBoostBtn2());
			click();
			waitForElementPresent(merchUI.getBbCreatebtn(), methodName);
			getXpathWebElement(merchUI.getBbCreatebtn());
			click();
			isTextPresent(merchData.getBnbValidationMsgCreate());

		} catch (Exception e) {
			log.info("Exception in addBoostAndBlock" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Boost and Block Empty Search Term
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void boostAndBlockEmptySearchTerm(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing boostAndBlockEmptySearchTerm scenario");
			waitForElementPresent(merchUI.getAddIcon(), methodName);
			getXpathWebElement(merchUI.getAddIcon());
			click();
			waitForElementPresent(merchUI.getBbCreateSearchTerm(), methodName);
			getXpathWebElement(merchUI.getBbCreateSearchTerm());
			click();
			clear();
			waitForElementPresent(merchUI.getCreateTreeView(), methodName);
			getXpathWebElement(merchUI.getCreateTreeView());
			click();
			waitForElementPresent(merchUI.getSelectCreateTree(), methodName);
			getXpathWebElement(merchUI.getSelectCreateTree());
			click();
			waitForElementPresent(merchUI.getCreateTree(), methodName);
			getXpathWebElement(merchUI.getCreateTree());
			click();
			waitForElementPresent(merchUI.getBbCreateNxtBtn(), methodName);
			getXpathWebElement(merchUI.getBbCreateNxtBtn());
			click();
			waitForElementPresent(merchUI.getBbPopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getBbPopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in boostAndBlockEmptySearchTerm" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Update Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param upboostblock
	 *            Object of the BoostAndBlock Class
	 */

	public void updateBoostAndBlock(String screenshotName,
			BoostAndBlock upboostblock) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing updateBoostAndBlock scenario");
			waitForElementPresent(merchUI.getEditBtn(), methodName);
			getXpathWebElement(merchUI.getEditBtn());
			click();
			waitForElementPresent(merchUI.getBbCreateBoostBtn(), methodName);
			getXpathWebElement(merchUI.getBbCreateBoostBtn());
			click();
			waitForElementPresent(merchUI.getBbRemoveBoostBtn(), methodName);
			getXpathWebElement(merchUI.getBbRemoveBoostBtn());
			click();
			waitForElementPresent(merchUI.getUpdateBtn(), methodName);
			getXpathWebElement(merchUI.getUpdateBtn());
			click();
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
			isTextPresent(merchData.getBnbValidationMsgUpdate());
		} catch (Exception e) {
			log.info("Exception in updateBoostAndBlock" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Approve Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void approveBoostAndBlock(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing approveBoostAndBlock scenario");
			waitForElementPresent(merchUI.getApproveTab(), methodName);
			getXpathWebElement(merchUI.getApproveTab());
			click();
			isTextPresent(merchData.getBnbValidationMsgApprove());
		} catch (Exception e) {
			log.info("Exception in approveBoostAndBlock" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to delete Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void deleteBoostAndBlock(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing deleteBoostAndBlock scenario");
			for (int temp = 0; temp < 2; temp++) {
				Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
				if (getXpathWebElement(merchUI.getDeleteTab()).isDisplayed()) {
					waitForElementPresent(merchUI.getDeleteTab(), methodName);
					getXpathWebElement(merchUI.getDeleteTab());
					click();
					waitForElementPresent(merchUI.getDeletePopUPOKBtn(),
							methodName);
					getXpathWebElement(merchUI.getDeletePopUPOKBtn());
					click();
					isTextPresent(merchData.getBnbValidationMsgDelete());
				} else {
					log.info("BoostAndBlock Delete Icon Not Displayed");
				}
			}
		} catch (Exception e) {
			log.info("Exception in approveBoostAndBlock" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Reject Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void rejectBoostAndBlock(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing rejectBoostAndBlock scenario");
			waitForElementPresent(merchUI.getRejectTab(), methodName);
			getXpathWebElement(merchUI.getRejectTab());
			click();
			isTextPresent(merchData.getBnbValidationMsgReject());
		} catch (Exception e) {
			log.info("Exception in rejectBoostAndBlock" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search Boost and Block Not AvailableData
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void searchBoostAndBlockNotAvailableData(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing searchBoostAndBlock scenario");
			waitForElementPresent(merchUI.getBbSearchTermsSearchField(),
					methodName);
			enterKeys(merchUI.getBbSearchTermsSearchField(),
					merchData.getBbSearchInvalidTerms());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
			isTextPresent(merchData.getValidationMsgInvalidSearch());
		} catch (Exception e) {
			log.info("Exception in searchBoostAndBlock" + e.getMessage());
		}
	}

	
	/**
	 * Implementation of the Method to Sort and Refresh Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void sortnRefreshBoostAndBlock(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing sortnRefreshBoostAndBlock scenario");
			waitForElementPresent(merchUI.getBbSortBySearchTerm(), methodName);
			getXpathWebElement(merchUI.getBbSortBySearchTerm());
			click();
			waitForElementPresent(merchUI.getBbSortBySearchTerm(), methodName);
			getXpathWebElement(merchUI.getBbSortBySearchTerm());
			click();
			waitForElementPresent(merchUI.getBbSortBySearchTerm(), methodName);
			getXpathWebElement(merchUI.getBbSortBySearchTerm());
			click();
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in sortnRefreshBoostAndBlock" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to verify the Pagination of Boost and Block
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void pagiNationForBoostAndBlockPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing pagiNationForBoostAndBlockPage scenario");
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			if (getXpathWebElement(merchUI.getNextBtn()).isDisplayed()) {
				log.info("Pagination Not Available");
			} else {
				waitForElementPresent(merchUI.getNextBtn(), methodName);
				getXpathWebElement(merchUI.getNextBtn());
				click();
				waitForElementPresent(merchUI.getPrevBtn(), methodName);
				getXpathWebElement(merchUI.getPrevBtn());
				click();
				waitForElementPresent(merchUI.getRefreshIcon(), methodName);
				getXpathWebElement(merchUI.getRefreshIcon());
				click();
			}
		} catch (Exception e) {
			log.info("Exception in pagiNationForBoostAndBlockPage"
					+ e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search BoostAndBlock
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void searchBnBSearchTerm(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
						.getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing searchBnBSearchTerm scenario");
			waitForElementPresent(merchUI.getBbSearchTermsSearchField(),
					methodName);
			enterKeys(merchUI.getBbSearchTermsSearchField(),
					merchData.getBbSearchTermsSearchFieldData());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in searchBnBSearchTerm" + e.getMessage());
		}
	}

}
