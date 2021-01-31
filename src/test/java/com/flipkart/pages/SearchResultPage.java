package com.flipkart.pages;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.flipkart.utils.FlipKartUtils;


public class SearchResultPage {
	
	private final WebDriver driver;
	private final String SORT_BY_PRICE_LOW_TO_HIGH = "Price -- Low to High";
	
	private final String textIphoneItemLoacator = ".//*[contains(text(),'iPhone')]";
	private final String textproductRatingLocator = ".//span[contains(@id,'product')]/following-sibling::span";
	private final String textproductRatingLocatorForExtraWidth = "//span[contains(@id,'product')]/following-sibling::span/span/span[1]";
	private final String textpriceLocator = ".//div[contains(text(),'₹')]";
	
	
	@FindBy(xpath = "//div[contains(text(),'"+SORT_BY_PRICE_LOW_TO_HIGH+"')]")
	WebElement lnkSortPriceLowToHigh;
	
//	@FindBy(xpath = "//div[@data-id]")
//	List<WebElement> lstDataId;
	
	@FindBy(css = "div[data-id]")
	List<WebElement> lstdataId;
	
	
	 /**
     * 
     * Constructor class for Search page here we initializing the driver for page
     * factory objects.
     * 
     * @param driver
     * @param url
     */
    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        FlipKartUtils.waitForElement(driver, lnkSortPriceLowToHigh);
    }
    
    
    /**
  	 * Click Price -- Low to High link
  	 * 
  	 */
  	public void clickSortPriceLowToHighLink() {
  		FlipKartUtils.waitForElement(driver, lnkSortPriceLowToHigh);
  		JavascriptExecutor executor = (JavascriptExecutor)driver;
  		executor.executeScript("arguments[0].click();", lnkSortPriceLowToHigh);
  	}
  	
  	public String getItem(WebElement element){
		String rating;
		try {
			rating = element.findElement(By.xpath(textIphoneItemLoacator)).getText();		
		} catch (Exception e) {
			rating = "";
		}
		return rating;
  	}
  	
  	public String getProductRating(WebElement element){
		String rating;
		try {
			if(element.getAttribute("style").contains("100%")) {
				rating = element.findElement(By.xpath(textproductRatingLocatorForExtraWidth))
						.getText();	
			} else {
				rating = element.findElement(By.xpath(textproductRatingLocator))
						.getText();	
			}
		} catch (Exception e) {
			rating = "";
		}
		return rating;
  	}
  	
  	public String getPrize(WebElement element){
		String price;
		try {
			price = element.findElement(By.xpath(textpriceLocator))
					.getText();			
		} catch (NoSuchElementException e) {
			price = "";
		}
		return price;
  	}
  	
  
  	
  	public Map<String,String> getItemDetails(int maxPrice, String storage) {
  		String price;
  		String nameWithStorageDetails = "";
  		String ratings = "Zero Ratings";
  		
  		Map<String,String> map = new LinkedHashMap<String,String>();  
  		FlipKartUtils.waitForListElement(driver, lstdataId, FlipKartUtils.flipKartMaxElementWait);
  		for(int i = 0; i < lstdataId.size(); i++){
  			nameWithStorageDetails = getItem(lstdataId.get(i));
  			nameWithStorageDetails = nameWithStorageDetails.substring(0, nameWithStorageDetails.lastIndexOf(')')+1);
  			ratings = getProductRating(lstdataId.get(i)).replaceAll("^([-+] ?)?[0-9]+(,[0-9]+)?$", "");
  			price = getPrize(lstdataId.get(i));
  			if(Integer.parseInt(price.replaceAll("[^\\d.]", "")) <= maxPrice && nameWithStorageDetails.contains(storage))
  			map.put(nameWithStorageDetails, price+";"+ratings); 
  			else if(Integer.parseInt(price.replaceAll("[^\\d.]", "")) > maxPrice)
  			break;  			
  			// toDo click on nextpage and call recursively
  			//else if(Integer.parseInt(price.replaceAll("[^\\d.]", "")) < maxPrice)
  			//clickNextPage();
  			//getItemDetails();
  		}
		return map;
  	}
}
