package com.bestbuy.search.bt.uiconstants;

import java.io.File;
import java.io.FilenameFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.bestbuy.search.bt.Screens.BannersScreen;
import com.bestbuy.search.bt.Screens.BoostAndBlockScreen;
import com.bestbuy.search.bt.Screens.FacetsScreen;
import com.bestbuy.search.bt.Screens.KeywordRedirectScreen;
import com.bestbuy.search.bt.Screens.PromoScreen;
import com.bestbuy.search.bt.Screens.SynonymsScreen;
import com.bestbuy.search.bt.Screens.UserAdminScreen;
import com.bestbuy.search.bt.model.Banners;
import com.bestbuy.search.bt.model.BoostAndBlock;
import com.bestbuy.search.bt.model.Facets;
import com.bestbuy.search.bt.model.KeywordRedirect;
import com.bestbuy.search.bt.model.Promo;
import com.bestbuy.search.bt.model.Synonyms;
import com.bestbuy.search.bt.model.UserAdmin;
import com.bestbuy.search.bt.selenium.util.ScreenException;

/**
 * The Class where the data is loaded from multiple xml's for create & update
 * functionality
 */

public class ReadCreateAndUpdateData {

	private static Element eElement;
	private Log log = LogFactory.getLog("ReadCreateData");
	private SynonymsScreen synonymsScreen;
	private KeywordRedirectScreen keywordRedirectScreen;
	private BannersScreen bannersScreen;
	private PromoScreen promoScreen;
	private FacetsScreen facetsScreen;
	private BoostAndBlockScreen boostAndBlockScreen;
	private UserAdminScreen userAdminScreen;
	private ModifyDateTime modifydatetime;
	private String textContent;
	private static final String SYNONYMSCREATEDATA = "./src/main/resources/synonyms/create";
	private static final String KEYWORDREDIRECTCREATEDATA = "./src/main/resources/keywordredirects/create";
	private static final String BANNERSCREATEDATA = "./src/main/resources/banners/create";
	private static final String PROMOCREATEDATA = "./src/main/resources/promo/create";
	private static final String FACETSCREATEDATA = "./src/main/resources/facets/create";
	private static final String BOOSTANDBLOCKCREATEDATA = "./src/main/resources/boostandblock/create";
	private static final String USERADMINCREATEDATA = "./src/main/resources/useradmin/create";

	private static final String SYNONYMSUPDATEDATA = "./src/main/resources/synonyms/update";
	private static final String KEYWORDREDIRECTUPDATEDATA = "./src/main/resources/keywordredirects/update";
	private static final String BANNERSUPDATEDATA = "./src/main/resources/banners/update";
	private static final String PROMOUPDATEDATA = "./src/main/resources/promo/update";
	private static final String FACETSUPDATEDATA = "./src/main/resources/facets/update";
	private static final String BOOSTANDBLOCKUPDATEDATA = "./src/main/resources/boostandblock/update";
	private static final String USERADMINUPDATEDATA = "./src/main/resources/useradmin/update";

	private static final String SYNONYMS = "synonyms";
	private static final String KEYWORDREDIRECT = "keywordredirects";
	private static final String BANNERS = "banners";
	private static final String PROMO = "promo";
	private static final String FACETS = "facets";
	private static final String BOOSTANDBLOCK = "boostandblock";
	private static final String USERADMIN = "useradmin";

	private static final String PRIMARYTERMS_DATA = "primaryTermData";
	private static final String TERMS_DATA = "termsData";
	private static final String RD_TERMVALUE = "rd_redirectTerm_value";
	private static final String RD_URL = "rd_RedirectUrl_value";
	private static final String BANNERS_NAME = "banners_Banner_Name";
	private static final String BANNERS_CONTEXT_KEYWORD = "banners_Context_KeyWord";
	private static final String BANNERS_ASSETID = "banners_Theme_AssetID";
	private static final String BNB_CREATE_SEARCH_DATA = "bnb_Search_Data";
	private static final String FACETS_CREATE_SYSNAME_DATA = "facets_SysName_Data";
	private static final String FACETS_CREATE_DISPNAME_DATA = "facets_DispName_Data";
	private static final String FACETS_CREATE_ASSET_DATA = "facets_AssetData";

	private static final String PROMO_CREATE_NAME = "promo_PromoName_Data";
	private static final String PROMO_CREATE_SKUID = "promo_PromoSKUID_Data";
	private static final String USERADMIN_FNAME = "user_FirstName_Data";
	private static final String USERADMIN_LNAME = "user_LastName_Data";
	private static final String USERADMIN_ID = "user_ID";

	private static final String START_DATE_MONTH = "Start_Date_Month";
	private static final String START_DATE_DAY = "Start_Date_Day";
	private static final String START_DATE_YEAR = "Start_Date_Year";
	private static final String START_DATE_HOUR = "Start_Date_Hour";
	private static final String START_DATE_MINUTE = "Start_Date_Minute";

	private static final String END_DATE_MONTH = "End_Date_Month";
	private static final String END_DATE_DAY = "End_Date_Day";
	private static final String END_DATE_YEAR = "End_Date_Year";
	private static final String END_DATE_HOUR = "End_Date_Hour";
	private static final String END_DATE_MINUTE = "End_Date_Minute";

	/**
	 * Method to read the xml data
	 */
	public ReadCreateAndUpdateData() {
		synonymsScreen = new SynonymsScreen();
		keywordRedirectScreen = new KeywordRedirectScreen();
		bannersScreen = new BannersScreen();
		promoScreen = new PromoScreen();
		facetsScreen = new FacetsScreen();
		boostAndBlockScreen = new BoostAndBlockScreen();
		userAdminScreen = new UserAdminScreen();
		modifydatetime = new ModifyDateTime();

	}

	/**
	 * Method to load the BestBuy constants
	 * 
	 * @param properties
	 *            properties is the variable that stores the xml file
	 * 
	 * @param tagname
	 *            tagname is the name of the root tag read from the xml
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */

	public void loadBTConstansts(String properties, String tagname)
			throws ScreenException {

		try {
			File fXmlFile = new File(properties);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nList = doc.getElementsByTagName(tagname);

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					eElement = (Element) nNode;

				}
			}
		} catch (Exception e) {
			log.info("Exception in loadBTConstansts" + e.getMessage());
		}
	}

	/**
	 * Method to read the tag value by passing the tag name
	 * 
	 * @param tagname
	 *            tagname is the name of the root tag read from the xml
	 */
	private void getElementsByTagName(String tagname) {

		try {
			textContent = eElement.getElementsByTagName(tagname).item(0)
					.getTextContent();
		} catch (DOMException e) {
			log.info("Exception in getElementsByTagName" + e.getMessage());
		}
	}

	/**
	 * Method to load create & update data from xml for synonyms tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadSynonymsCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(SYNONYMSCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				loadBTConstansts(SYNONYMSCREATEDATA + "/" + f.getName(),
						SYNONYMS);
				Synonyms synonyms = setSynonymsCreateData();
				synonymsScreen.selectSynonymsTab(screenshotName);
				synonymsScreen.addSynonym(screenshotName, synonyms);
				loadBTConstansts(SYNONYMSUPDATEDATA + "/" + f.getName(),
						SYNONYMS);
				Synonyms synonymsupdate = setSynonymsCreateData();				
				synonymsScreen.updateSynonym(screenshotName, synonymsupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadSynonymsCreateData" + e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the synonym class
	 */
	private Synonyms setSynonymsCreateData() {
		Synonyms synonym = new Synonyms();
		getElementsByTagName(PRIMARYTERMS_DATA);
		synonym.setSynPrimaryTermData(textContent);
		getElementsByTagName(TERMS_DATA);
		synonym.setSynTermsData(textContent);
		return synonym;
	}

	/**
	 * Method to load create & update data from xml for Keyword Redirects tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadKeywordRedirectCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(KEYWORDREDIRECTCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				modifydatetime.updateDateAndTime(KEYWORDREDIRECTCREATEDATA+ "/" + f.getName(), KEYWORDREDIRECT);
				loadBTConstansts(KEYWORDREDIRECTCREATEDATA + "/" + f.getName(),
						KEYWORDREDIRECT);
				KeywordRedirect keywordredirect = setXmlkeyworddata();
				keywordRedirectScreen.addkeywordRedirect(screenshotName,
						keywordredirect);
				modifydatetime.updateDateAndTime(KEYWORDREDIRECTUPDATEDATA+ "/" + f.getName(), KEYWORDREDIRECT);
				loadBTConstansts(KEYWORDREDIRECTUPDATEDATA + "/" + f.getName(),
						KEYWORDREDIRECT);
				KeywordRedirect keywordredirectupdate = setXmlkeyworddata();
				keywordRedirectScreen.updatekeywordRedirect(screenshotName,
						keywordredirectupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadKeywordRedirectCreateData"
					+ e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the Keyword redirect class
	 */
	private KeywordRedirect setXmlkeyworddata() {
		KeywordRedirect keywordredirect = new KeywordRedirect();
		getElementsByTagName(RD_TERMVALUE);
		keywordredirect.setKrdRedirectTermData(textContent);
		getElementsByTagName(RD_URL);
		keywordredirect.setKrdRiderectUrlValue(textContent);
		getElementsByTagName(START_DATE_MONTH);
		keywordredirect.setKrdStartDateMonth(textContent);
		getElementsByTagName(START_DATE_DAY);
		keywordredirect.setKrdStartDateDay(textContent);
		getElementsByTagName(START_DATE_YEAR);
		keywordredirect.setKrdStartDateYear(textContent);
		getElementsByTagName(START_DATE_HOUR);
		keywordredirect.setKrdStartDateHoure(textContent);
		getElementsByTagName(START_DATE_MINUTE);
		keywordredirect.setKrdStartDateMinute(textContent);
		getElementsByTagName(END_DATE_MONTH);
		keywordredirect.setKrdEndDateMonth(textContent);
		getElementsByTagName(END_DATE_DAY);
		keywordredirect.setKrdEndDateDay(textContent);
		getElementsByTagName(END_DATE_YEAR);
		keywordredirect.setKrdEndDateYear(textContent);
		getElementsByTagName(END_DATE_HOUR);
		keywordredirect.setKrdEndDateHoure(textContent);
		getElementsByTagName(END_DATE_MINUTE);
		keywordredirect.setKrdEndDateMinute(textContent);

		return keywordredirect;
	}

	/**
	 * Method to load create & update data from xml for Banners tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadBannersCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(BANNERSCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				modifydatetime.updateDateAndTime(BANNERSCREATEDATA + "/" + f.getName(), BANNERS);
				loadBTConstansts(BANNERSCREATEDATA + "/" + f.getName(), BANNERS);
				Banners banners = setBannersCreateData();
				bannersScreen.addBanners(screenshotName, banners);
				modifydatetime.updateDateAndTime(BANNERSUPDATEDATA+ "/" + f.getName(), BANNERS);
				loadBTConstansts(BANNERSUPDATEDATA + "/" + f.getName(), BANNERS);
				Banners bannersupdate = setBannersCreateData();
				bannersScreen.updateBanners(screenshotName, bannersupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadSynonymsCreateData" + e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the Banners class
	 */
	private Banners setBannersCreateData() {
		Banners banners = new Banners();
		getElementsByTagName(BANNERS_NAME);
		banners.setBannerName(textContent);
		getElementsByTagName(BANNERS_CONTEXT_KEYWORD);
		banners.setBannerContextKeyword(textContent);
		getElementsByTagName(BANNERS_ASSETID);
		banners.setBannerThemeAssetId(textContent);
		getElementsByTagName(START_DATE_MONTH);
		banners.setBannerStartDateMonth(textContent);
		getElementsByTagName(START_DATE_DAY);
		banners.setBannerStartDateDay(textContent);
		getElementsByTagName(START_DATE_YEAR);
		banners.setBannerStartDateYear(textContent);
		getElementsByTagName(START_DATE_HOUR);
		banners.setBannerStartDateHoure(textContent);
		getElementsByTagName(START_DATE_MINUTE);
		banners.setBannerStartDateMinute(textContent);
		getElementsByTagName(END_DATE_MONTH);
		banners.setBannerEndDateMonth(textContent);
		getElementsByTagName(END_DATE_DAY);
		banners.setBannerEndDateDay(textContent);
		getElementsByTagName(END_DATE_YEAR);
		banners.setBannerEndDateYear(textContent);
		getElementsByTagName(END_DATE_HOUR);
		banners.setBannerEndDateHoure(textContent);
		getElementsByTagName(END_DATE_MINUTE);
		banners.setBannerEndDateMinute(textContent);

		return banners;
	}

	/**
	 * Method to load create & update data from xml for BoostandBlock tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadBoostBlockCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(BOOSTANDBLOCKCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				loadBTConstansts(BOOSTANDBLOCKCREATEDATA + "/" + f.getName(),
						BOOSTANDBLOCK);
				BoostAndBlock boostandblock = setBoostAndBlockData();
				boostAndBlockScreen.addBoostAndBlock(screenshotName,
						boostandblock);
				loadBTConstansts(BOOSTANDBLOCKUPDATEDATA + "/" + f.getName(),
						BOOSTANDBLOCK);
				BoostAndBlock boostandblockupdate = setBoostAndBlockData();
				boostAndBlockScreen.updateBoostAndBlock(screenshotName,
						boostandblockupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadBoostAndBlockCreateData"
					+ e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the BoostandBlock class
	 */
	private BoostAndBlock setBoostAndBlockData() {
		BoostAndBlock boostandblock = new BoostAndBlock();
		getElementsByTagName(BNB_CREATE_SEARCH_DATA);
		boostandblock.setBbCreateSearchData(textContent);
		return boostandblock;
	}

	/**
	 * Method to load create & update data from xml for Facets tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadFacetsCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(FACETSCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				modifydatetime.updateDateAndTime(FACETSCREATEDATA+ "/" + f.getName(), FACETS);
				loadBTConstansts(FACETSCREATEDATA + "/" + f.getName(), FACETS);
				Facets facets = setFacetsData();
				facetsScreen.addFacets(screenshotName, facets);
				modifydatetime.updateDateAndTime(FACETSUPDATEDATA+ "/" + f.getName(), FACETS);
				loadBTConstansts(FACETSUPDATEDATA + "/" + f.getName(), FACETS);
				Facets facetsupdate = setFacetsData();
				facetsScreen.facetsValidUpdate(screenshotName, facetsupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadFacetsCreateData" + e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the Facets class
	 */
	private Facets setFacetsData() {
		Facets facets = new Facets();
		getElementsByTagName(FACETS_CREATE_SYSNAME_DATA);
		facets.setFacetsSystemNameData(textContent);
		getElementsByTagName(FACETS_CREATE_DISPNAME_DATA);
		facets.setFacetsDisplayNameData(textContent);
		getElementsByTagName(FACETS_CREATE_ASSET_DATA);
		facets.setFacetsAssetData(textContent);
		getElementsByTagName(START_DATE_MONTH);
		facets.setFacetsStartDateMonth(textContent);
		getElementsByTagName(START_DATE_DAY);
		facets.setFacetsStartDateDay(textContent);
		getElementsByTagName(START_DATE_YEAR);
		facets.setFacetsStartDateYear(textContent);
		getElementsByTagName(START_DATE_HOUR);
		facets.setFacetsStartDateHoure(textContent);
		getElementsByTagName(START_DATE_MINUTE);
		facets.setFacetsStartDateMinute(textContent);
		getElementsByTagName(END_DATE_MONTH);
		facets.setFacetsEndDateMonth(textContent);
		getElementsByTagName(END_DATE_DAY);
		facets.setFacetsEndDateDay(textContent);
		getElementsByTagName(END_DATE_YEAR);
		facets.setFacetsEndDateYear(textContent);
		getElementsByTagName(END_DATE_HOUR);
		facets.setFacetsEndDateHoure(textContent);
		getElementsByTagName(END_DATE_MINUTE);
		facets.setFacetsStartDateMinute(textContent);

		return facets;
	}

	/**
	 * Method to load create & update data from xml for Promo tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadPromoCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(PROMOCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				modifydatetime.updateDateAndTime(PROMOCREATEDATA+ "/" + f.getName(), PROMO);
				loadBTConstansts(PROMOCREATEDATA + "/" + f.getName(), PROMO);
				Promo promo = setPromoData();
				promoScreen.addPromoPage(screenshotName, promo);
				modifydatetime.updateDateAndTime(PROMOUPDATEDATA+ "/" + f.getName(), PROMO);
				loadBTConstansts(PROMOUPDATEDATA + "/" + f.getName(), PROMO);
				Promo promoupdate = setPromoData();
				promoScreen.updatePromoPage(screenshotName, promoupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadPromoCreateData" + e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the Promo class
	 */
	private Promo setPromoData() {
		Promo promo = new Promo();
		getElementsByTagName(PROMO_CREATE_NAME);
		promo.setPromoCreateName(textContent);
		getElementsByTagName(PROMO_CREATE_SKUID);
		promo.setPromoCreateSkuidData(textContent);
		getElementsByTagName(START_DATE_MONTH);
		promo.setPromoStartDateMonth(textContent);
		getElementsByTagName(START_DATE_DAY);
		promo.setPromoStartDateDay(textContent);
		getElementsByTagName(START_DATE_YEAR);
		promo.setPromoStartDateYear(textContent);
		getElementsByTagName(START_DATE_HOUR);
		promo.setPromoStartDateHoure(textContent);
		getElementsByTagName(START_DATE_MINUTE);
		promo.setPromoStartDateMinute(textContent);
		getElementsByTagName(END_DATE_MONTH);
		promo.setPromoEndDateMonth(textContent);
		getElementsByTagName(END_DATE_DAY);
		promo.setPromoEndDateDay(textContent);
		getElementsByTagName(END_DATE_YEAR);
		promo.setPromoEndDateYear(textContent);
		getElementsByTagName(END_DATE_HOUR);
		promo.setPromoEndDateHoure(textContent);
		getElementsByTagName(END_DATE_MINUTE);
		promo.setPromoEndDateMinute(textContent);

		return promo;
	}

	/**
	 * Method to load create & update data from xml for UserAdmin tab
	 * 
	 * @param screenshotName
	 *            The name in which the Screen shot file is saved
	 * 
	 * @throws ScreenException
	 *             ScreenException is an exception to suppress warnings
	 */
	public void loadUserAdminCreateAndUpdateData(String screenshotName)
			throws ScreenException {
		try {

			File directory = new File(USERADMINCREATEDATA);
			File[] listFiles = directory.listFiles(new AllTestFilter());
			for (File f : listFiles) {
				loadBTConstansts(USERADMINUPDATEDATA + "/" + f.getName(),
						USERADMIN);
				UserAdmin useradmin = setUserAdminData();
				userAdminScreen.addUserAdmin(screenshotName, useradmin);
				loadBTConstansts(USERADMINUPDATEDATA + "/" + f.getName(),
						USERADMIN);
				UserAdmin userupdate = setUserAdminData();
				userAdminScreen.editUserAdmin(screenshotName, userupdate);
			}
		} catch (Exception e) {
			log.info("Exception in loadUserAdminCreateData" + e.getMessage());
		}
	}

	/**
	 * Method to set and return the create data value
	 * 
	 * @return returns the object of the UserAdmin class
	 */
	private UserAdmin setUserAdminData() {
		UserAdmin useradmin = new UserAdmin();
		getElementsByTagName(USERADMIN_FNAME);
		useradmin.setUserFirstNameData(textContent);
		getElementsByTagName(USERADMIN_LNAME);
		useradmin.setUserLastNameData(textContent);
		getElementsByTagName(USERADMIN_ID);
		useradmin.setUserIDData(textContent);
		return useradmin;
	}
}

/**
 * Class to filter the .xml files
 */
class AllTestFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return name.endsWith(".xml");
	}
}
