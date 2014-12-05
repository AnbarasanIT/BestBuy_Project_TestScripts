package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;

/**
 * This class is to Test the Login functionality
 */

public class LoginTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static LoginScreen loginBaseScreen;
	private String testMethodName;
	private static BaseScreen baseScreen;

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
		loginBaseScreen = new LoginScreen();
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
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the Login functionality with empty username and password
	 */

	@Test
	public void testWithout_UserNameAndPassword() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testWithout_UserNameAndPassword Testcases");
			testMethodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			loginBaseScreen.emptyLogin(testMethodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the Login functionality without username and with password
	 */

	@Test
	public void testWithoutUserName_WithPassword() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println(" Executing testWithoutUserName_WithPassword Testcases");
			testMethodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			loginBaseScreen.withoutUserNameAndWithPassword(testMethodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the Login functionality with username and without password
	 */

	@Test
	public void testWithoutPassword_WithUserName() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testWithoutPassword_WithUserName testcases");
			testMethodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			loginBaseScreen.withoutPasswordAndWithUserName(testMethodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Tests
	 *        the Login functionality with valid username and password
	 */

	@Test
	public void testValid_UserNameAndPassword() throws InterruptedException,
			IOException, Exception {
		try {

			System.out
					.println("Executing testValid_UserNameAndPassword Testcases");
			testMethodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			loginBaseScreen.validLogin(testMethodName);

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
