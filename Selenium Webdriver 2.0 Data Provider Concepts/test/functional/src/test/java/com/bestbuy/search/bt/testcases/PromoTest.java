package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.Screens.PromoScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.ReadCreateAndUpdateData;

/**
 * This class is to Test the major functionalities of the Promo Pages Tab
 */

public class PromoTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static PromoScreen promoBaseScreen;
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
		promoBaseScreen = new PromoScreen();
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
	 *        the Application and Tests the Add & update functionality of the Promo Pages
	 *        Tab
	 */

	@Test
	public void testAddAndUpdatePromoPage() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testAddAndUpdatePromoPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			readcreatedata.loadPromoCreateAndUpdateData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the approve functionality of the Promo
	 *        Pages Tab
	 */

	@Test
	public void testApprovePromoPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testApprovePromoPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.approvePromoPage(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the reject functionality of the Promo
	 *        Pages Tab
	 */

	@Test
	public void testRejectPromoPage() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testRejectPromoPage testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.rejectPromoPage(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the Promo
	 *        Pages Tab
	 */

	@Test
	public void testInvalidSearchPromoPage() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testInvalidSearchPromoPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.invalidSearchPromoName(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the Promo
	 *        Pages Tab
	 */

	@Test
	public void testSearchPromoName() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testSearchPromoName Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.searchPromoName(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	
	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the sort and refresh functionality of
	 *        the Promo Pages Tab
	 */

	@Test
	public void testSortnRefreshPromoPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println(" Executing testSortnRefreshPromoPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.sortnRefreshPromoPage(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the pagination functionality of the
	 *        Promo Pages Tab
	 */

	@Test
	public void testPromoPagination() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testPromoPagination Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.paginationForPromoPage(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the delete functionality of the Promo
	 *        Pages Tab
	 */

	@Test
	public void testDeletePromo() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testDeletePromo testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.deletePromo(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testVerifyPromoUpdatePopUpTextFieldsValidation() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testVerifyPromoUpdatePopUpTextFieldsValidation testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.VerifyUpdatePopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testVerifyPromoCreatePopUpTextFieldsValidation() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testVerifyPromoCreatePopUpTextFieldsValidation testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			promoBaseScreen.VerifyCreatePopUpTextFieldsValidation(methodName);

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
