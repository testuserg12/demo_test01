package com.flipkart.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

public class WebDriverSetup {

private static WebDriver driver;
	
	private static final String LIB_DIR = System.getProperty("user.dir") + "/lib/webdrivers/";
	
	public WebDriver getWebDriver(String browserName) throws Exception {
		int retryCntr = 0;
			try {
				if ("FF".equals(browserName)) {
					driver = getFirefoxDriver();

				} else if ("chrome".equals(browserName)) {
					driver = getChromeDriver();

				} else {
					throw new Exception("invalid browser: " + browserName);
				}

				driver.manage().window().maximize();

				setWebDriverTimeouts();

			} catch (Exception e) {
					throw new Exception("failed to fetch web driver", e);
			}
		return driver;
	}
	
	private static WebDriver getFirefoxDriver() {
		
		System.setProperty("webdriver.gecko.driver", LIB_DIR + "geckodriver.exe");

		return new FirefoxDriver();
	}

	private static WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", LIB_DIR + "chromedriver.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions co = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		//prefs.put("download.prompt_for_download", true);
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("--ignore-certificate-errors", true);
		co.setExperimentalOption("prefs", prefs);
		co.addArguments("disable-extensions");
        co.addArguments("--start-maximized");
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		
		
		capabilities.setCapability(ChromeOptions.CAPABILITY, co);
		return new ChromeDriver(co);
	}


	
	private static void setWebDriverTimeouts() throws Exception {
		if (driver != null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else {
			throw new Exception("Driver is null. Cannot set driver timeouts.");
		}
	}


	public static WebDriver getDriver(ITestContext ctx) throws Exception {
		String browserName = ctx.getCurrentXmlTest().getParameter("browser");
		WebDriver driver = new WebDriverSetup().getWebDriver(browserName);
		return driver;
	}

}
