package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.Screens.SynonymsScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.ReadCreateAndUpdateData;

/**
 * This class is to Test the major functionalities of the Synonyms Tab
 */

public class SynonymsTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static SynonymsScreen synonymsBaseScreen;
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
		synonymsBaseScreen = new SynonymsScreen();
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
	 *        Synonyms Tab
	 */

	@Test
	public void testAddAndUpdateSynonym() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testAddAndUpdateSynonym testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			readcreatedata.loadSynonymsCreateAndUpdateData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testVerifySynonymsCreatePopUpTextFieldsValidation() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testVerifySynonymsCreatePopUpTextFieldsValidation testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			synonymsBaseScreen.verifyCreatePopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testVerifySynonymsEditPopUpTextFieldsValidation() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testVerifySynonymsEditPopUpTextFieldsValidation testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			synonymsBaseScreen.verifyEditPopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testInvalidUpdateSynonyms() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testInvalidUpdateSynonyms testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			synonymsBaseScreen.invalidUpdateSynonyms(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Add functionality of the Synonyms
	 *        Tab
	 */

	@Test
	public void testInValidAddSynonym() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testInValidAddSynonym Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			synonymsBaseScreen.inValidAddSynonym(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the approve functionality of the Synonyms Tab
	 */

	@Test
	public void testApproveSynonym() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testApproveSynonym Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.approveSynonym(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the reject functionality of the Synonyms Tab
	 */

	@Test
	public void testRejectSynonym() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testRejectSynonym testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.rejectSynonym(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the sort and refresh functionality of the Synonyms Tab
	 */

	@Test
	public void testSortnRefreshSynonyms() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testSortnRefreshSynonyms Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.sortnRefreshSynonyms(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the search functionality of the Synonyms Tab
	 */

	@Test
	public void testSearchSynonymsPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testSearchSynonymsPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.searchSynonyms(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testColumnSearchSynonymsPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testColumnSearchSynonymsPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.searchSynonymsPrimaryTerm(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the invalid search functionality of the Synonyms Tab
	 */

	@Test
	public void testInvalidSearchSynonymsPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testInvalidSearchSynonymsPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.inValidSearchSynonyms(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the delete functionality of the Synonyms Tab
	 */

	@Test
	public void testdeleteSynonymsPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testdeleteSynonymsPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.deleteSynonym(methodName);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the Pagination functionality of the Synonyms Tab
	 */

	@Test
	public void testPagiNationForSynonymsPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testPagiNationForSynonymsPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			synonymsBaseScreen.pageNationForSynonymPage(methodName);
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
