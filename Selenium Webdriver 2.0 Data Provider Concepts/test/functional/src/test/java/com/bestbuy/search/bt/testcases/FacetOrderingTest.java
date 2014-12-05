package com.bestbuy.search.bt.testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bestbuy.search.bt.Screens.BaseScreen;
import com.bestbuy.search.bt.Screens.FacetOrderingScreen;
import com.bestbuy.search.bt.Screens.LoginScreen;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.Env_Config;

/**
 * This class is to Test the major functionalities of the Facet Ordering Tab
 */

public class FacetOrderingTest {

	private static String selectedBrowser;
	private static MerchandisingUI_UserInfo merchUserInfo;
	private static MerchandisingUIIdentifiers merchUI;
	private static Env_Config merchEnv;
	private static FacetOrderingScreen facetOrderingBaseScreen;
	private String methodName;
	private static BaseScreen baseScreen;
	private static LoginScreen loginBaseScreen;

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
		facetOrderingBaseScreen = new FacetOrderingScreen();
		loginBaseScreen = new LoginScreen();

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
	 *        the Application and Tests the availability of Facet Ordering Page
	 */

	@Test
	public void testFacetOrdering() throws InterruptedException, IOException,
			Exception {
		try {

			System.out.println("Executing testFacetOrdering Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			loginBaseScreen.validLogin(methodName);
			facetOrderingBaseScreen.facetOrderingPage(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the edit functionality of the Facet
	 *        Ordering Tab
	 */

	@Test
	public void testEditFacetOrdering() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testEditFacetOrdering Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			facetOrderingBaseScreen.editFacetOrdering(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Lock functionality of the Facet
	 *        Ordering Tab
	 */

	@Test
	public void testLockFacetOrdering() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testLockFacetOrdering Testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			facetOrderingBaseScreen.lockFacetOrdering(methodName);

		} catch (Exception t) {
			t.printStackTrace();

		}
	}

	/**
	 * @Test: Makes a class or a method as part of the test This method Log into
	 *        the Application and Tests the Tree expantion functionality of the
	 *        Facet Ordering Tab
	 */

	@Test
	public void testeditFacetDisplayOrder() throws InterruptedException,
			IOException, Exception {
		try {

			System.out.println("Executing testeditFacetDisplayOrder testcases");
			methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();

			facetOrderingBaseScreen.editFacetDisplayOrder(methodName);

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
