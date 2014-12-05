package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.Screens.UserAdminScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.ReadCreateAndUpdateData;

/**
 * This class is to Test the major functionalities of the User Admin Tab
 */

public class UserAdminTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static UserAdminScreen userAdminScreen;
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
		userAdminScreen = new UserAdminScreen();
		loginBaseScreen = new LoginScreen();
		readcreatedata = new ReadCreateAndUpdateData();

		try {
			launchingBrowser();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * This method is called in the setUp method which instantiates the
	 * browsersession
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
	 *        User Admin Tab
	 */

	@Test
	public void testAddAndUpdateUserAdmin() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testAddAndUpdateUserAdmin Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			readcreatedata.loadUserAdminCreateAndUpdateData(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the edit functionality of the User Admin
	 *        Tab
	 */

	@Test
	public void testEditUserAdminPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testEditUserAdminPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			userAdminScreen.editUserAdmin(methodName, null);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the sort and refresh functionality of
	 *        the User Admin Tab
	 */

	@Test
	public void testSortnRefreshUserAdminPage() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testSortnRefreshUserAdminPage Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			userAdminScreen.sortnRefreshUserAdmin(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the search functionality of the User
	 *        Admin Tab
	 */

	@Test
	public void testSearchValidUser() throws InterruptedException, IOException,
			Exception {
		try {

			System.out
					.println("Executing testSearchValidUser Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			userAdminScreen.searchUsers(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}
	
	@Test
	public void testSearchInValidUser() throws InterruptedException, IOException,
			Exception {
		try {

			System.out
					.println("Executing testSearchInValidUser Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			userAdminScreen.searchInValidUsers(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testVerifyUserCreatePopUpTextFieldsValidation() throws InterruptedException, IOException,
			Exception {
		try {

			System.out
					.println("Executing testVerifyUserCreatePopUpTextFieldsValidation Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			userAdminScreen.VerifyCreatePopUpTextFieldsValidation(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	@Test
	public void testVerifyUserUpdatePopUpTextFieldsValidation() throws InterruptedException, IOException,
			Exception {
		try {

			System.out
					.println("Executing testVerifyUserUpdatePopUpTextFieldsValidation Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			userAdminScreen.VerifyUpdatePopUpTextFieldsValidation(methodName);

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
