package com.flipkart.utils;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipKartUtils {

	public static int flipKartMaxElementWait = 3;
	
	/**
     * To wait for the specific element on the page
     * 
     * @param driver -
     * @param element - webelement to wait for to appear
     * @return boolean - return true if element is present else return false
     */
    public static boolean waitForElement(WebDriver driver, WebElement element) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, flipKartMaxElementWait);
        try {
            WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
            if (waitElement.isDisplayed() && waitElement.isEnabled()) {
                statusOfElementToBeReturned = true;
            }
        } catch (Exception ex) {
        	System.out.println("Unable to find a element" + ex.getMessage());
    }
		return statusOfElementToBeReturned;
    }
    
    /**
     * To wait for the specific list elements
     * 
     * @param driver
     * @param elements - List elements to wait for to appear
     * @param maxWait - how long to wait for
     * @return boolean - return true if element is present else return false
     */
    public static boolean waitForListElement(WebDriver driver, List<WebElement> elements, int maxWait) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, maxWait);
        try {
			 wait.until(ExpectedConditions.visibilityOfAllElements(elements));
                statusOfElementToBeReturned = true;
        } catch (Exception ex) {
            statusOfElementToBeReturned = false;
        }
        return statusOfElementToBeReturned;
    }
	
	
}
