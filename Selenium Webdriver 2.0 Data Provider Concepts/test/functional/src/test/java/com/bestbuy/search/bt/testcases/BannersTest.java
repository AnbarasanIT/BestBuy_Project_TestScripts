package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.BannersScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.ReadCreateAndUpdateData;

/**
 * This class is to Test the major functionalities of the Banners Tab
 * 
 */

public class BannersTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static BannersScreen bannersBaseScreen;
	private String methodName;
	private static BaseScreen baseScreen;
	private static LoginScreen loginBaseScreen;
	private static ReadCreateAndUpdateData readcreatedata;

	/**
	 * @BeforeTest:The annotated method will be run before any test method
	 *                 belonging to the classes inside the <test> tag is run
	 *                 This method is to instantiate the browser session
	 */

	@BeforeTest
	public static void setUp() throws Exception {

		merchEnv = new Env_Config();
		merchUserInfo = new MerchandisingUI_UserInfo();
		merchUI = new MerchandisingUIIdentifiers();
		bannersBaseScreen = new BannersScreen();
		loginBaseScreen = new LoginScreen();
		readcreatedata = new ReadCreateAndUpdateData();

		try {
			launchingBrowser();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * This method is called in the setUp method which instantiates the browser
	 * session
	 */

	public static void launchingBrowser() throws Exception {

		String applicationURL = merchEnv.getProtocol() + "://"
				+ merchEnv.getHost() + ":" + merchEnv.getPort() + "/";
		selectedBrowser = merchEnv.getBrowser();
		try {
			baseScreen = new BaseScreen(selectedBrowser, applicationURL,
					merchEnv.getContext(), merchEnv, merchUserInfo, merchUI);
		} catch (ScreenException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Add & update functionality of the
	 *        Banners Tab
	 */

	@Test
	public void testAddAndUpdateBanners() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testAddAndUpdateBanners Testcase");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			readcreatedata.loadBannersCreateAndUpdateData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the approve functionality of the Banners
	 *        Tab
	 */

	@Test
	public void testApproveBanners() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testApproveBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			
			bannersBaseScreen.approveBanners(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the reject functionality of the Banners
	 *        Tab
	 */

	@Test
	public void testRejectBanners() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testRejectBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.rejectBanners(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the Banners
	 *        Tab
	 */

	@Test
	public void testInvalidSearchBanners() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testInvalidSearchBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.invalidSearchBanners(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the Banners
	 *        Tab
	 */

	@Test
	public void testSearchBannersName() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testSearchBannersName Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.searchBannersName(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the show and Hide All functionality of
	 *        the Banners Tab
	 */

	@Test
	public void testShowOrHideAllBanners() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testShowOrHideAllBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.showOrHideAllBanners(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the sort and refresh functionality of
	 *        the Banners Tab
	 */

	@Test
	public void testSortnRefreshBanners() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testSortnRefreshBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.sortnRefreshBanners(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the delete functionality of the Banners
	 *        Tab
	 */

	@Test
	public void testDeleteBanners() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testDeleteBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.deleteBanner(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the pagination functionality of the
	 *        Banners Tab
	 */

	@Test
	public void testPaginationForBanners() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testPaginationForBanners Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.pagiNationForBannersPage(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testVerifyBannersCreatePopUpTextFieldsValidation() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testVerifyBannersCreatePopUpTextFieldsValidation Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			
			bannersBaseScreen.VerifyCreatePopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testVerifyBannersUpdatePopUpTextFieldsValidation() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testVerifyBannersUpdatePopUpTextFieldsValidation Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.VerifyUpdatePopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testKeyWordSearch() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testKeyWordSearch Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			bannersBaseScreen.bannersKeywordSearch(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @AfterTest: The annotated method will be run after all the test methods
	 *             belonging to the classes inside the <test> tag have run This
	 *             method is to terminate the browser session
	 */

	@AfterTest
	public static void tearDown() {
		baseScreen.closeBrowser();
	}

}
