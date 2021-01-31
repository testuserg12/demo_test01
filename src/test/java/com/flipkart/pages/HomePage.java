package com.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.flipkart.utils.FlipKartUtils;

public class HomePage {
	
	private final WebDriver driver;
    
	
	@FindBy(xpath = "//button[contains(text(),'✕')]")
	WebElement btnClose;
	
	@FindBy(css = "input[title*='Search']")
	WebElement txtSearchProduct;
	
	@FindBy(css = "button[type='submit']")
	WebElement btnSubmit;
	
	 /**
     * 
     * Constructor class for Home page Here we initializing the driver for page
     * factory objects.
     * 
     * @param driver
     * @param url
     */
    public HomePage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
    
    /**
	 * Click Close button to close LoginPopup
	 * 
	 */
	public void clickCloseButton() {
		FlipKartUtils.waitForElement(driver, btnClose);
		btnClose.click();
	}
	
	/**
	 * Enter product or brand and search
	 * 
	 */
	public SearchResultPage searchProductOrBrand(String product) {
		FlipKartUtils.waitForElement(driver, txtSearchProduct);
		txtSearchProduct.sendKeys(product);
		btnSubmit.submit();
		return new SearchResultPage(driver);
	}

}
