package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.BoostAndBlockScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.ReadCreateAndUpdateData;

/**
 * This class is to Test the major functionalities of the Boost and Block Tab
 */
public class BoostBlockTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static BoostAndBlockScreen boostAndBlocksScreen;
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
		boostAndBlocksScreen = new BoostAndBlockScreen();
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
	 *        Boost and Block Tab
	 */

	@Test
	public void testAddAndUpdateBoostBlock() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testAddAndUpdateBoostBlock Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			boostAndBlocksScreen.selectBoostAndBlockTab(methodName);
			readcreatedata.loadBoostBlockCreateAndUpdateData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the functionality of the Boost and Block
	 *        Tab Empty SearchTerm
	 */

	@Test
	public void testboostAndBlockEmptySearchTerm() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testboostAndBlockEmptySearchTerm Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			boostAndBlocksScreen.boostAndBlockEmptySearchTerm(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the approve functionality of the Boost
	 *        and Block Tab
	 */

	@Test
	public void testApproveBoostAndBlock() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testApproveBoostAndBlock Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen.approveBoostAndBlock(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the reject functionality of the Boost
	 *        and Block Tab
	 */

	@Test
	public void testRejectBoostAndBlock() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testRejectBoostAndBlock Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen.rejectBoostAndBlock(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Search Not-Available Data
	 *        functionality of the Boost and Block Tab
	 */

	@Test
	public void testSearchBoostAndBlockNotAvailableData()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("Executing testSearchBoostAndBlockNotAvailableData Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen
					.searchBoostAndBlockNotAvailableData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the delete functionality of the Boost
	 *        and Block Tab
	 */

	@Test
	public void testDeleteBoostAndBlock() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testDeleteBoostAndBlock Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen.deleteBoostAndBlock(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the Boost
	 *        and Block Tab
	 */

	@Test
	public void testSearchBoostAndBlockSearchTerm()
			throws InterruptedException, IOException, Exception {
		try {

			System.out.println("Executing testSearchBoostAndBlock Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen.searchBnBSearchTerm(methodName);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the sort and refresh functionality of
	 *        the Boost and Block Tab
	 */

	@Test
	public void testSortnRefreshBoostAndBlock() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testSortnRefreshBoostAndBlock Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen.sortnRefreshBoostAndBlock(methodName);
		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the pagination functionality of the
	 *        Boost and Block Tab
	 */

	@Test
	public void testPagiNationForBoostAndBlockPage()
			throws InterruptedException, IOException, Exception {
		try {

			System.out
					.println("Executing testPagiNationForBoostAndBlockPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			boostAndBlocksScreen.pagiNationForBoostAndBlockPage(methodName);

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
