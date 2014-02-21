package com.bestbuy.search.bt.Screens;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bestbuy.search.bt.model.Promo;
import com.bestbuy.search.bt.selenium.util.MagicNumbers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIData;

/**
 * Class that contains the implementation of the methods that are called in the
 * Test methods of the Class PromoTest
 */

public class PromoScreen extends BaseScreen {

	private Log log = LogFactory.getLog("PromoScreen");
	private MerchandisingUIIdentifiers merchUI;
	private MerchandisingUIData merchData;
	private String screenName;
	private String methodName;

	public PromoScreen() {

		merchUI = new MerchandisingUIIdentifiers();
		merchData = new MerchandisingUIData();

	}

	/**
	 * Implementation of the Method to Add Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param promo
	 *            Object of Promo Class
	 */

	public void addPromoPage(String screenshotName, Promo promo) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing addPromoPage scenario");
			Thread.sleep(MagicNumbers.FOUR_THOUSAND_SECONDS);
			if (getXpathWebElement(merchUI.getPromoPagesTab()).isDisplayed()) {
				waitForElementPresent(merchUI.getPromoPagesTab(), methodName);
				getXpathWebElement(merchUI.getPromoPagesTab());
				click();
			} else {
				waitForElementPresent(merchUI.getIpadViewMenuTab(), methodName);
				getXpathWebElement(merchUI.getIpadViewMenuTab());
				click();
				waitForElementPresent(merchUI.getIpadViewPromoTab(), methodName);
				getXpathWebElement(merchUI.getIpadViewPromoTab());
				click();
			}
			waitForElementPresent(merchUI.getAddIcon(), methodName);
			getXpathWebElement(merchUI.getAddIcon());
			click();
			waitForElementPresent(merchUI.getPromoCreateName(), methodName);
			getXpathWebElement(merchUI.getPromoCreateName());
			click();
			sendKeys(promo.getPromoCreateName());
			waitForElementPresent(merchUI.getPromoCreateSkuid(), methodName);
			getXpathWebElement(merchUI.getPromoCreateSkuid());
			click();
			sendKeys(promo.getPromoCreateSkuidData());
			waitForElementPresent(merchUI.getPromoAddTerms(), methodName);
			getXpathWebElement(merchUI.getPromoAddTerms());
			click();
			waitForElementPresent(merchUI.getStartDate(), methodName);
			getXpathWebElement(merchUI.getStartDate());
			click();
			waitForElementPresent(merchUI.getCalMonthFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalMonthFieldXpath());
			click();
			selectText(element, promo.getPromoStartDateMonth());
			waitForElementPresent(merchUI.getCalYearFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalYearFieldXpath());
			click();
			selectText(element, promo.getPromoStartDateYear());
			waitForElementPresent(merchUI.getCalHourFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalHourFieldXpath());
			click();
			sendKeys(promo.getPromoStartDateHoure());
			waitForElementPresent(merchUI.getCalMinuteFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalMinuteFieldXpath());
			click();
			sendKeys(promo.getPromoStartDateMinute());
			waitForElementPresent(merchUI.getSelectedStartDate(), methodName);
			getXpathWebElement(merchUI.getSelectedStartDate());
			click();
			waitForElementPresent(merchUI.getEndDate(), methodName);
			getXpathWebElement(merchUI.getEndDate());
			click();
			waitForElementPresent(merchUI.getCalMonthFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalMonthFieldXpath());
			click();
			selectText(element, promo.getPromoEndDateMonth());
			waitForElementPresent(merchUI.getCalYearFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalYearFieldXpath());
			click();
			selectText(element, promo.getPromoEndDateYear());
			waitForElementPresent(merchUI.getCalHourFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalHourFieldXpath());
			click();
			sendKeys(promo.getPromoEndDateHoure());
			waitForElementPresent(merchUI.getCalMinuteFieldXpath(), methodName);
			getXpathWebElement(merchUI.getCalMinuteFieldXpath());
			click();
			sendKeys(promo.getPromoEndDateMinute());
			getXpathWebElement(merchUI.getSelectedEndDate());
			click();
			waitForElementPresent(merchUI.getPromoCreateBtn(), methodName);
			getXpathWebElement(merchUI.getPromoCreateBtn());
			click();
			isTextPresent(promo.getPromoCreateName());

		} catch (Exception e) {
			log.info("Exception in addPromoPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Update Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param uppromo
	 *            Object of the Promo Class
	 */

	public void updatePromoPage(String screenshotName, Promo uppromo) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing updatePromoPage scenario");
			waitForElementPresent(merchUI.getEditBtn(), methodName);
			getXpathWebElement(merchUI.getEditBtn());
			click();
			waitForElementPresent(merchUI.getPromoCreateName(), methodName);
			getXpathWebElement(merchUI.getPromoCreateName());
			click();
			clear();
			sendKeys(uppromo.getPromoCreateName());
			waitForElementPresent(merchUI.getPromoUpdateBtn(), methodName);
			getXpathWebElement(merchUI.getPromoUpdateBtn());
			click();
			isTextPresent(uppromo.getPromoCreateName());
		} catch (Exception e) {
			log.info("Exception in updatePromoPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Approve Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void approvePromoPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing approvePromoPage scenario");
			waitForElementPresent(merchUI.getApproveTab(), methodName);
			getXpathWebElement(merchUI.getApproveTab());
			click();
		} catch (Exception e) {
			log.info("Exception in approvePromoPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Reject Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void rejectPromoPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing rejectPromoPage scenario");
			waitForElementPresent(merchUI.getRejectTab(), methodName);
			getXpathWebElement(merchUI.getRejectTab());
			click();
		} catch (Exception e) {
			log.info("Exception in rejectPromoPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */
	public void invalidSearchPromoName(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing invalidSearchPromoName scenario");
			waitForElementPresent(merchUI.getPromoNameSearchField(), methodName);
			enterKeys(merchUI.getPromoNameSearchField(),
					merchData.getPromoNameInvalidSearch());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in invalidSearchPromoName" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void sortnRefreshPromoPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing sortnRefreshPromoPage scenario");
			waitForElementPresent(merchUI.getPromoSortByName(), methodName);
			getXpathWebElement(merchUI.getPromoSortByName());
			click();
			waitForElementPresent(merchUI.getPromoSortByName(), methodName);
			getXpathWebElement(merchUI.getPromoSortByName());
			click();
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in sortnRefreshPromoPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Paginate Promo Pages
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void paginationForPromoPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing pageNationForPromoPage scenario");
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
			log.info("Exception in pageNationForPromoPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Delete Promo
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */
	public void deletePromo(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing deletePromo scenario");
			for (int temp = 0; temp <10; temp++) {
				Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
				if (getXpathWebElement(merchUI.getDeleteTab()).isDisplayed()) {
					waitForElementPresent(merchUI.getDeleteTab(), methodName);
					getXpathWebElement(merchUI.getDeleteTab());
					click();
					waitForElementPresent(merchUI.getDeletePopUPOKBtn(),
							methodName);
					getXpathWebElement(merchUI.getDeletePopUPOKBtn());
					click();

				} else {
					log.info("Promo Delete Icon Not Displayed");
				}
			}
		} catch (Exception e) {
			log.info("Exception in deletePromo" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search Promo
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void searchPromoName(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing searchPromo scenario");
			waitForElementPresent(merchUI.getPromoNameSearchField(), methodName);
			enterKeys(merchUI.getPromoNameSearchField(),
					merchData.getPromoNameSearchFieldData());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in searchPromo" + e.getMessage());
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
			log.info("Executing VerifyCreatePopUpTextFieldsValidation scenario");
			waitForElementPresent(merchUI.getAddIcon(), methodName);
			getXpathWebElement(merchUI.getAddIcon());
			click();
			waitForElementPresent(merchUI.getPromoCreateBtn(), methodName);
			getXpathWebElement(merchUI.getPromoCreateBtn());
			click();
			waitForElementPresent(merchUI.getPromoAddUpdatePopUpCloseBtn(),
					methodName);
			getXpathWebElement(merchUI.getPromoAddUpdatePopUpCloseBtn());
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
			waitForElementPresent(merchUI.getEditBtn(), methodName);
			getXpathWebElement(merchUI.getEditBtn());
			click();
			waitForElementPresent(merchUI.getPromoCreateName(), methodName);
			getXpathWebElement(merchUI.getPromoCreateName());
			click();
			clear();
			waitForElementPresent(merchUI.getPromoSKURemove(), methodName);
			getXpathWebElement(merchUI.getPromoSKURemove());
			click();
			clear();
			waitForElementPresent(merchUI.getStartDate(), methodName);
			getXpathWebElement(merchUI.getStartDate());
			click();
			clear();
			waitForElementPresent(merchUI.getEndDate(), methodName);
			getXpathWebElement(merchUI.getEndDate());
			click();
			clear();
			waitForElementPresent(merchUI.getPromoUpdateBtn(), methodName);
			getXpathWebElement(merchUI.getPromoUpdateBtn());
			click();
			waitForElementPresent(merchUI.getPromoAddUpdatePopUpCloseBtn(),
					methodName);
			getXpathWebElement(merchUI.getPromoAddUpdatePopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in VerifyUpdatePopUpTextFieldsValidation" + e.getMessage());
		}
	}

}
