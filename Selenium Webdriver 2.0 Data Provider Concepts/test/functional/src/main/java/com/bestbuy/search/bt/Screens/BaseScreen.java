package com.bestbuy.search.bt.Screens;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.iphone.IPhoneDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.openqa.selenium.Keys;

import com.bestbuy.search.bt.selenium.util.Constants;
import com.bestbuy.search.bt.selenium.util.GetCurrentDir;
import com.bestbuy.search.bt.selenium.util.MagicNumbers;
import com.bestbuy.search.bt.selenium.util.ScreenException;
import com.bestbuy.search.bt.uiconstants.Env_Config;
import com.bestbuy.search.bt.uiconstants.MerchandisingUIIdentifiers;
import com.bestbuy.search.bt.uiconstants.MerchandisingUI_UserInfo;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.google.common.base.Function;

/**
 * This class contains the pre-defined or user defined methods that are called
 * in the Test methods
 */

public class BaseScreen {

	private static WebDriver driver;
	private ChromeDriverService chromeService;
	private Log log = LogFactory.getLog("BaseScreen");
	protected static WebElement element;
	private static Env_Config merchEnv;
	private boolean value;

	private static HtmlUnitDriver htmlDriver;

	public BaseScreen() {

	}

	/**
	 * This method instantiates browser's driver
	 * 
	 * @param selectedBrowser
	 *            current selected Browser where the test cases will execute
	 * 
	 * @param applicationURL
	 *            Application URL
	 * 
	 * @param applicationContext
	 *            Application Context
	 * 
	 * @param merchEnv
	 *            Instance to the environment configurations class
	 * 
	 * @param merchUserInfo
	 *            Instance to the User Info class
	 * 
	 * @param merchUI
	 *            Instance to the UI Identifiers class
	 * 
	 */
	public BaseScreen(String selectedBrowser, String applicationURL,
			String applicatinContext, Env_Config merchEnv,
			MerchandisingUI_UserInfo merchUserInfo,
			MerchandisingUIIdentifiers merchUI) throws ScreenException {

		BaseScreen.merchEnv = merchEnv;

		instantiateBrowser(selectedBrowser, applicationURL, applicatinContext);

	}

	/**
	 * This method instantiates browser's driver
	 * 
	 * @param selectedBrowser
	 *            current selected Browser where the test cases will execute
	 * 
	 * @param applicationURL
	 *            Application URL
	 * 
	 * @param applicationContext
	 *            Application Context
	 */

	public void instantiateBrowser(String selectedBrowser,
			String applicationURL, String applicationContext)
			throws ScreenException {
/*
		if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_CHROME)) {
			try {
				chromeService = new ChromeDriverService.Builder()
						.usingDriverExecutable(new File(getChromeLocation()))
						.usingAnyFreePort().build();

				log.info("LAUNCHING GOOGLECHROME");
				driver = new ChromeDriver(chromeService);
				windowResize();
				driver.navigate().to(applicationURL + applicationContext);

			} catch (Exception e) {
				log.info("EXCEPTION IN LAUNCHING GOOGLECHROME" + e.getMessage());
			}

		} */
		 if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_IE)) {
			log.info("LAUNCHING INTERNET EXPLORE");
			driver = new InternetExplorerDriver();
			windowResize();
			driver.navigate().to(applicationURL + applicationContext);

		} else if (selectedBrowser.equalsIgnoreCase(Constants.IPHONE_HYBRID)) {
			log.info("LAUNCHING IPHONE SIMULATOR/DEVICE");
			try {
				driver = new IPhoneDriver();
			} catch (Exception e) {
				log.info("EXCEPTION IN LAUNCHING SIMULATOR/DEVICE"
						+ e.getMessage());
			}
			driver.navigate().to(applicationURL + applicationContext);

		} else if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
			log.info("LAUNCHING FIREFOX");
		  //  System.setProperty("webdriver.firefox.bin", "/usr/local/bin/firefox701_for_fta");  
			driver = new FirefoxDriver();
			windowResize();
			driver.navigate().to(applicationURL + applicationContext);

		} else if (selectedBrowser.equalsIgnoreCase(Constants.ANDROID_HYBRID)) {
			log.info("LAUNCHING ANDROID SIMULATOR/DEVICE");
			driver = new AndroidDriver();
			driver.navigate().to(applicationURL + applicationContext);

		} else if (selectedBrowser.equalsIgnoreCase(Constants.BROWSER_OPERA)) {
			log.info("LAUNCHING OPERA");
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_SPACE);
				robot.keyPress(KeyEvent.VK_X);
				robot.keyRelease(KeyEvent.VK_X);
			} catch (AWTException e) {

				log.info("EXCEPTION IN LAUNCHING OPERA" + e.getMessage());
			}

		} else if (selectedBrowser.equalsIgnoreCase(Constants.HTML_UNIT)) {
			log.info("LAUNCHING HTML_UNIT");
		    //System.setProperty("webdriver.firefox.bin", "/usr/local/bin/firefox701_for_fta");  
			htmlDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_10);
			htmlDriver.setJavascriptEnabled(true);
			java.util.logging.Logger.getLogger("com.gargoylesoftware")
					.setLevel(Level.OFF);
			htmlDriver.get(applicationURL + applicationContext);

		} else {
			throw new ScreenException(
					"Only FireFox,IExplore,Chrome,opera,android and iphone browsers works");
		}

	}

	/**
	 * This method sets different resolution for the window
	 */

	public static void windowResize() {

		String resolution = merchEnv.getResolution();
		if (resolution != null) {
			String[] tokens = resolution.split("x");
			String resolutionX = tokens[0];
			String resolutionY = tokens[1];
			int x = Integer.parseInt(resolutionX);
			int y = Integer.parseInt(resolutionY);
			Dimension screenResolution = new Dimension(x, y);
			driver.manage().window().setSize(screenResolution);
		} else {
			driver.manage().window().maximize();
		}
	}

	/**
	 * This method is called in the Test classes for termination of the Browser
	 * session
	 */

	public void closeBrowser() {
		log.info("BROWSER CLOSING");
		if (driver != null) {
			driver.quit();
		} else {
			log.info("DRIVER NOT EXIST TO QUIT ");
		}

	}

	/**
	 * This method is to get the location of the Chrome Driver
	 */
	/*public String getChromeLocation() {
		log.info("getChromeLocation:CHROME TARGET LOCATION FOUND");
		String directory = System.getProperty("user.dir");
		String targetDirectory = getChromeFile();
		String location = directory + targetDirectory;
		return location;
	}*/

	/**
	 * This method is to get the Chrome Driver based on the OS
	 */
	/*public String getChromeFile() {
		if (System.getProperty("os.name").startsWith(Constants.WINDOWS_OS)) {
			log.info("WINDOWS MACHINE FOUND");
			return Constants.CHROME_DIRECTORY + "/chromedriver.exe";
		} else if (System.getProperty("os.name").startsWith(Constants.LINUX_OS)) {
			log.info("LINUX MACHINE FOUND");
			return Constants.CHROME_DIRECTORY + "/chromedriver";
		} else if (System.getProperty("os.name").startsWith(Constants.MAC_OS)) {
			log.info("MAC MACHINE FOUND");
			return Constants.CHROME_DIRECTORY + "/chromedriver";
		} else {
			throw new NullPointerException("PLATFORM NOT FOUND");

		}

	}*/

	/**
	 * This method is to get the xpath of the Web element
	 * 
	 * @param xpath
	 *            It is the identifier of an UI object
	 */

	public WebElement getXpathWebElement(String xpath) {
		log.info("Entering getXpathWebElement");
		try {
			if (merchEnv.getBrowser().equalsIgnoreCase("htmlunit")) {
				element = htmlDriver.findElement(By.xpath(xpath));
			} else {
				element = driver.findElement(By.xpath(xpath));
			}
		} catch (Exception e) {
			log.info("Exception in getXpathWebElement" + e.getMessage());
		}
		return element;
	}

	/**
	 * This method is to get the Id of the Web element
	 * 
	 * @param id
	 *            It is the identifier of an UI object
	 */

	public void getIdWebElement(String id) throws ScreenException {
		log.info("Entering getIdWebElement");
		try {
			if (merchEnv.getBrowser().equalsIgnoreCase("htmlunit")) {
				element = htmlDriver.findElement(By.id(id));
			} else {
				element = driver.findElement(By.id(id));
			}
		} catch (Exception e) {
			log.info("Exception in getIdWebElement" + e.getMessage());
		}
	}

	/**
	 * This method is to get the css of the Web element
	 * 
	 * @param selector
	 *            It is the identifier of an UI object
	 */

	public void getcssWebElement(String selector) throws ScreenException {
		log.info("Entering getIdWebElement");
		try {
			if (merchEnv.getBrowser().equalsIgnoreCase("htmlunit")) {
				element = htmlDriver.findElement(By.cssSelector(selector));
			} else {
				element = driver.findElement(By.cssSelector(selector));
			}
		} catch (Exception e) {
			log.info("Exception in getIdWebElement" + e.getMessage());
		}
	}

	/**
	 * This method waits for presence of specific xpath or Id of the Web element
	 * and takes screen shot if it is not available
	 * 
	 * @param locator
	 *            It is the Identifier of the UI object
	 * @param methodName
	 *            It stores the method Name from which the call is triggered
	 */

	public void waitForElementPresent(String locator, String methodName) {
		try {
			if (merchEnv.getBrowser().equalsIgnoreCase("htmlunit")) {
				log.info("ENTERING IN HTML WAIT");
				Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);
			} else {
				By by = null;
				log.info("Entering waitForElementPresent");

				if (locator.startsWith("//")) {
					log.info("Entering Xpath checker");
					by = By.xpath(locator);
				} else {
					log.info("Entering Non-Xpath checker");
					by = By.id(locator);
				}
				WebDriverWait wait = new WebDriverWait(driver,
						MagicNumbers.FOURTY_SECONDS);
				wait.until(presenceOfElementLocated(by));
			}
		} catch (Exception e) {
			log.info("presenceOfElementLocated" + e.getMessage());
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile,
						new File(GetCurrentDir.getCurrentDirectory() + "\\"
								+ methodName + ".png"));
			} catch (Exception e1) {
				log.info("presenceOfElementLocated" + e1.getMessage());
			}
			Assert.assertNull(scrFile);
		}
	}

	Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
		log.info("Entering presenceOfElementLocated");
		return new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
					log.info("Waiting for Element to present");
				} catch (InterruptedException e) {
					log.info("Exception in presenceOfElementLocated"
							+ e.getMessage());
				}
				return driver.findElement(locator);
			}
		};
	}

	/**
	 * This method is to select a particular Text from the Application UI
	 * 
	 * @param element
	 *            Instance to the WebElement class
	 * @param textValue
	 *            Text to be selected in the UI
	 */

	public void selectText(WebElement element, String textValue)
			throws ScreenException {
		log.info("Entering get Select Text Webelement");

		Select selObj = new Select(element);
		selObj.selectByVisibleText(textValue);

	}

	/**
	 * This method is to click on a particular Web element
	 */

	public void click() throws ScreenException {
		log.info("Click operation start");
		try {
			element.click();
		} catch (Exception e) {
			log.info("Exception in click" + e.getMessage());
		}
		log.info("Click operation end");
	}

	/**
	 * This method is to clear a particular Text from the Application UI
	 */

	public void clear() throws ScreenException {
		log.info("Clear operation start");
		try {
			element.clear();
		} catch (Exception e) {
			log.info("Exception in clear" + e.getMessage());
		}
		log.info("Clear operation end");

	}

	/**
	 * This method is to type a particular Text its an alternate of the type
	 * method
	 * 
	 * @param text
	 *            The text to be passed as value for the Text field in the UI
	 */

	public void sendKeys(String text) throws ScreenException {
		log.info("EnterText operation start");
		try {
			element.sendKeys(text);
		} catch (Exception e) {
			log.info("Exception in sendKeys" + e.getMessage());
		}
		log.info("EnterText operation end");
	}

	public void enterKeys(String value, String value1) throws ScreenException {
		log.info("EnterText operation start");
		try {
			element = driver.findElement(By.xpath(value));
			element.clear();
			element.sendKeys(value1);
			Thread.sleep(MagicNumbers.ONE_THOUSAND_SECONDS);
			element.sendKeys(Keys.RETURN);
			Thread.sleep(MagicNumbers.THREE_THOUSAND_SECONDS);

		} catch (Exception e) {
			log.info("Exception in sendKeys" + e.getMessage());
		}
		log.info("EnterText operation end");
	}

	/**
	 * This method is to click on the submit button
	 */

	public void submit() throws ScreenException {
		log.info("Submit operation start");
		try {
			element.submit();
		} catch (Exception e) {
			log.info("Exception in submit" + e.getMessage());
		}
		log.info("Submit operation end");
	}

	/**
	 * This method is to verify the presence of particular Text
	 * 
	 * @param text
	 *            The text to be found in the UI
	 * @throws InterruptedException
	 */

	public boolean isTextPresent(String text) {
		try {
			Thread.sleep(MagicNumbers.FIVE_THOUSAND_SECONDS);

			if (merchEnv.getBrowser().equalsIgnoreCase("htmlunit")
					&& text != null) {
				value = htmlDriver.findElement(By.tagName("body")).getText()
						.contains(text);
			} else if (text != null) {
				value = driver.findElement(By.tagName("body")).getText()
						.contains(text);
			} else {
				throw new RuntimeException("Text not present");
			}
		} catch (InterruptedException e) {
			log.info("Text not present" + e.getMessage());
		}
		AssertJUnit.assertTrue(value);
		return value;

	}

	/**
	 * Method to perform Drag and drop functionality
	 * 
	 * @param dragelement
	 *            ElementID of the element to be dragged
	 */
	public void dragAndDrop(String dragelement) {

		WebElement dragElement = driver.findElement(By.xpath(dragelement));
		new Actions(driver)
				.dragAndDropBy(dragElement, MagicNumbers.X_POSITION,
						MagicNumbers.Y_POSITION).build().perform();

	}
}
