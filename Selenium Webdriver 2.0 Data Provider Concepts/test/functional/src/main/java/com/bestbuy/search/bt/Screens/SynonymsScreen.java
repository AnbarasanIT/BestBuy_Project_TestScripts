package com.bestbuy.search.bt.Screens;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestbuy.search.bt.model.Synonyms;
import com.bestbuy.search.bt.selenium.util.MagicNumbers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIData;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;

/**
 * Class that contains the implementation of the methods that are called in the
 * Test methods of the Class SynonymsTest
 */

public class SynonymsScreen extends BaseScreen {

	private Log log = LogFactory.getLog("LoginScreen");
	private MerchandisingUIIdentifiers merchUI;
	private MerchandisingUIData merchData;
	private String screenName;
	private String methodName;

	public SynonymsScreen() {

		merchUI = new MerchandisingUIIdentifiers();
		merchData = new MerchandisingUIData();

	}

	public void selectSynonymsTab(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing selectSynonymsTab scenario");
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);
			if (getXpathWebElement(merchUI.getSynonymsTab()).isDisplayed()) {
				waitForElementPresent(merchUI.getSynonymsTab(), methodName);
				getXpathWebElement(merchUI.getSynonymsTab());
				click();
			} else {
				waitForElementPresent(merchUI.getIpadViewMenuTab(), methodName);
				getXpathWebElement(merchUI.getIpadViewMenuTab());
				click();
				waitForElementPresent(merchUI.getIpadViewSynonymsTab(),
						methodName);
				getXpathWebElement(merchUI.getIpadViewSynonymsTab());
				click();
			}
		} catch (Exception e) {
			log.info("Exception in selectSynonymsTab" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Add Synonym
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param synonyms
	 *            Object of the Synonyms class
	 */
	public void addSynonym(String screenshotName, Synonyms synonyms) {
		try {
			synonyms.getSynPrimaryTermData();
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing addSynonym scenario");
			waitForElementPresent(merchUI.getAddIcon(), methodName);
			getXpathWebElement(merchUI.getAddIcon());
			click();
			waitForElementPresent(merchUI.getSynPrimaryTerm(), methodName);
			getXpathWebElement(merchUI.getSynPrimaryTerm());
			click();
			sendKeys(synonyms.getSynPrimaryTermData());
			waitForElementPresent(merchUI.getSynTermsTxtArea(), methodName);
			getXpathWebElement(merchUI.getSynTermsTxtArea());
			click();
			sendKeys(synonyms.getSynTermsData());
			waitForElementPresent(merchUI.getSynAddTermsLink(), methodName);
			getXpathWebElement(merchUI.getSynAddTermsLink());
			click();
			waitForElementPresent(merchUI.getSynCreateBtn(), methodName);
			getXpathWebElement(merchUI.getSynCreateBtn());
			click();
			Thread.sleep(MagicNumbers.TWO_THOUSAND_SECONDS);
			isTextPresent(synonyms.getSynPrimaryTermData());

		} catch (Exception e) {
			log.info("Exception in addSynonym()" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to InValid Add Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void inValidAddSynonym(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing inValidAddSynonym scenario");

			waitForElementPresent(merchUI.getAddIcon(), methodName);
			getXpathWebElement(merchUI.getAddIcon());
			click();
			waitForElementPresent(merchUI.getSynPrimaryTerm(), methodName);
			getXpathWebElement(merchUI.getSynPrimaryTerm());
			click();
			waitForElementPresent(merchUI.getSynTermsTxtArea(), methodName);
			getXpathWebElement(merchUI.getSynTermsTxtArea());
			click();
			sendKeys(merchData.getSynTermsData());
			waitForElementPresent(merchUI.getSynAddTermsLink(), methodName);
			getXpathWebElement(merchUI.getSynAddTermsLink());
			click();
			waitForElementPresent(merchUI.getSynCreateBtn(), methodName);
			getXpathWebElement(merchUI.getSynCreateBtn());
			click();
			waitForElementPresent(merchUI.getSynPopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getSynPopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in inValidAddSynonym" + e.getMessage());
		}
	}

	public void verifyCreatePopUpTextFieldsValidation(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing verifyCreatePopUpTextFieldsValidation scenario");

			waitForElementPresent(merchUI.getAddIcon(), methodName);
			getXpathWebElement(merchUI.getAddIcon());
			click();
			waitForElementPresent(merchUI.getSynCreateBtn(), methodName);
			getXpathWebElement(merchUI.getSynCreateBtn());
			click();
			waitForElementPresent(merchUI.getSynPopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getSynPopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in verifyCreatePopUpTextFieldsValidation" + e.getMessage());
		}
	}

	public void verifyEditPopUpTextFieldsValidation(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing verifyEditPopUpTextFieldsValidation scenario");

			waitForElementPresent(merchUI.getEditBtn(), methodName);
			getXpathWebElement(merchUI.getEditBtn());
			click();
			waitForElementPresent(merchUI.getSynPrimaryTerm(), methodName);
			getXpathWebElement(merchUI.getSynPrimaryTerm());
			clear();
			waitForElementPresent(merchUI.getSynEditPopUpTermCloseBtn(),
					methodName);
			getXpathWebElement(merchUI.getSynEditPopUpTermCloseBtn());
			click();
			waitForElementPresent(merchUI.getSynEditPopUpTermCloseBtn(),
					methodName);
			getXpathWebElement(merchUI.getSynEditPopUpTermCloseBtn());
			click();
			waitForElementPresent(merchUI.getSynUpdateBtn(), methodName);
			getXpathWebElement(merchUI.getSynUpdateBtn());
			click();
			waitForElementPresent(merchUI.getSynPopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getSynPopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in verifyEditPopUpTextFieldsValidation" + e.getMessage());
		}
	}

	public void invalidUpdateSynonyms(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing inValidAddSynonym scenario");

			waitForElementPresent(merchUI.getEditBtn(), methodName);
			getXpathWebElement(merchUI.getEditBtn());
			click();
			waitForElementPresent(merchUI.getSynEditPopUpTermCloseBtn(),
					methodName);
			getXpathWebElement(merchUI.getSynEditPopUpTermCloseBtn());
			click();
			waitForElementPresent(merchUI.getSynEditPopUpTermCloseBtn(),
					methodName);
			getXpathWebElement(merchUI.getSynEditPopUpTermCloseBtn());
			click();
			waitForElementPresent(merchUI.getSynUpdateBtn(), methodName);
			getXpathWebElement(merchUI.getSynUpdateBtn());
			click();
			waitForElementPresent(merchUI.getSynPopUpCloseBtn(), methodName);
			getXpathWebElement(merchUI.getSynPopUpCloseBtn());
			click();
		} catch (Exception e) {
			log.info("Exception in inValidAddSynonym" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Update Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @param updatesynonyms
	 *            Object of the Synonyms Class
	 */

	public void updateSynonym(String screenshotName, Synonyms upsynonyms) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing updateSynonym scenario");
			waitForElementPresent(merchUI.getEditBtn(), methodName);
			getXpathWebElement(merchUI.getEditBtn());
			click();
			waitForElementPresent(merchUI.getSynTermsTxtArea(), methodName);
			getXpathWebElement(merchUI.getSynTermsTxtArea());
			click();
			sendKeys(upsynonyms.getSynTermsData());
			waitForElementPresent(merchUI.getSynAddTermsLink(), methodName);
			getXpathWebElement(merchUI.getSynAddTermsLink());
			click();
			waitForElementPresent(merchUI.getSynUpdateBtn(), methodName);
			getXpathWebElement(merchUI.getSynUpdateBtn());
			click();
			isTextPresent(upsynonyms.getSynTermsData());
		} catch (Exception e) {
			log.info("Exception in updateSynonym" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Approve Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void approveSynonym(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing approveSynonym scenario");
			waitForElementPresent(merchUI.getApproveTab(), methodName);
			getXpathWebElement(merchUI.getApproveTab());
			click();
		} catch (Exception e) {
			log.info("Exception in approveSynonym" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Reject Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void rejectSynonym(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing rejectSynonym scenario");
			waitForElementPresent(merchUI.getRejectTab(), methodName);
			getXpathWebElement(merchUI.getRejectTab());
			click();
		} catch (Exception e) {
			log.info("Exception in rejectSynonym" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Sort and Refresh Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void sortnRefreshSynonyms(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing sortnRefreshSynonyms scenario");
			waitForElementPresent(merchUI.getSortingIcon(), methodName);
			getXpathWebElement(merchUI.getSortingIcon());
			click();
			waitForElementPresent(merchUI.getSortingIcon(), methodName);
			getXpathWebElement(merchUI.getSortingIcon());
			click();
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in sortnRefreshSynonyms" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void searchSynonyms(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}

			log.info("Executing searchSynonyms scenario");
			waitForElementPresent(merchUI.getSearchField(), methodName);
			enterKeys(merchUI.getSearchField(), merchData.getSynSearchValue());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in searchSynonyms" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to InValid Search Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void inValidSearchSynonyms(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing inValidSearchSynonyms scenario");
			waitForElementPresent(merchUI.getSearchField(), methodName);
			enterKeys(merchUI.getSearchField(),
					merchData.getSynInvalidSearchValue());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();		

		} catch (Exception e) {
			log.info("Exception in inValidSearchSynonyms" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Delete Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void deleteSynonym(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing deleteSynonym scenario");
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
					log.info("Synonyms Delete Icon Not Displayed");
				}
			}
		} catch (Exception e) {
			log.info("Exception in deleteSynonym" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to verify the Pagination of the Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void pageNationForSynonymPage(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing pageNationForSynonymPage scenario");
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
			log.info("Exception in pageNationForSynonymPage" + e.getMessage());
		}
	}

	/**
	 * Implementation of the Method to Search Synonyms
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 */

	public void searchSynonymsPrimaryTerm(String screenshotName) {
		try {
			if (StringUtils.isEmpty(screenshotName)) {
				screenName = Thread.currentThread().getStackTrace()[1]
				                                                    .getMethodName();
				methodName = screenName;
			} else {
				methodName = screenshotName;
			}
			log.info("Executing searchSynonymsPrimaryTerm scenario");
			waitForElementPresent(merchUI.getSynPrimaryTermSearchField(),
					methodName);
			enterKeys(merchUI.getSynPrimaryTermSearchField(),
					merchData.getSynPrimaryColumnSearchFieldData());
			waitForElementPresent(merchUI.getRefreshIcon(), methodName);
			getXpathWebElement(merchUI.getRefreshIcon());
			click();
		} catch (Exception e) {
			log.info("Exception in searchSynonymsPrimaryTerm" + e.getMessage());
		}
	}

}
