package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.KeywordRedirectScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.ReadCreateAndUpdateData;

/**
 * This class is to Test the major functionalities of the Keyword Redirects Tab
 */

public class KeywordRedirectTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static KeywordRedirectScreen keywordRedirectBaseScreen;
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
		keywordRedirectBaseScreen = new KeywordRedirectScreen();
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
	 *        Keyword Redirects Tab
	 */

	@Test
	public void testAddAndUpdatekeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testAddAndUpdatekeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			readcreatedata.loadKeywordRedirectCreateAndUpdateData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	
	@Test
	public void testVerifyKeywordCreatePopUpTextFieldsValidation() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testVerifyKeywordCreatePopUpTextFieldsValidation Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			
			keywordRedirectBaseScreen.VerifyCreatePopUpTextFieldsValidation(methodName);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testVerifyKeywordEditPopUpTextFieldsValidation() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testVerifyKeywordEditPopUpTextFieldsValidation Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			keywordRedirectBaseScreen.VerifyEditPopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Add functionality of the Invalid
	 *        Keyword Redirects Tab
	 */

	@Test
	public void testInvalidAddkeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testInvalidAddkeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			keywordRedirectBaseScreen.invalidAddkeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Invalid update functionality of the
	 *        Keyword Redirects Tab
	 */

	@Test
	public void testInvalidUpdatekeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testInvalidUpdatekeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.invalidUpdatekeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the approve functionality of the Keyword
	 *        Redirects Tab
	 */

	@Test
	public void testApprovekeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testApprovekeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.approvekeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the reject functionality of the Keyword
	 *        Redirects Tab
	 */

	@Test
	public void testRejectkeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testRejectkeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.rejectkeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the sort and refresh functionality of
	 *        the Keyword Redirects Tab
	 */

	@Test
	public void testSortnRefreshkeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testSortnRefreshkeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.sortnRefreshkeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the invalid search functionality of the
	 *        Keyword Redirects Tab
	 */

	@Test
	public void testInvalidSearchkeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testInvalidSearchkeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.invalidSearchkeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the Keyword
	 *        Redirects Tab
	 */

	@Test
	public void testSearchKeywordTerms() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testSearchKeywordTerms Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.searchKeywordTerms(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the delete functionality of the Keyword
	 *        Redirects Tab
	 */

	@Test
	public void testDeletekeywordRedirect() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testDeletekeywordRedirect Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen.deleteKeywordRedirect(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Pagination functionality of the
	 *        Keyword Redirects Tab
	 */

	@Test
	public void testPaginationForKeywordRedirectPage()
			throws InterruptedException, IOException, Exception {
		try {

			System.out.println("Executing testPaginationForKeywordRedirectPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			keywordRedirectBaseScreen
					.pageNationForKeywordRedirectPage(methodName);

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
