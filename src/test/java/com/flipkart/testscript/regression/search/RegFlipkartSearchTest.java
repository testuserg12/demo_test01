package com.flipkart.testscript.regression.search;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.flipkart.pages.*;
import com.flipkart.utils.BaseTest;
import com.flipkart.utils.CSVUtil;

public class RegFlipkartSearchTest extends BaseTest {
	
	HomePage homePage;
	SearchResultPage searchResultPage;
	private static final String CSV_FILE_PATH  = "./result.csv"; 
	int maxPrice = 40000;
  	String storage = "GB";
  	
	@BeforeMethod
	public void setUp() throws InterruptedException {
		driver.get(prop.getProperty("url"));
		homePage = new HomePage(driver);
	}
	
	@Test(priority=1)
	public void searchItem(){
		homePage.clickCloseButton();
		searchResultPage = homePage.searchProductOrBrand("iPhones");
		searchResultPage.clickSortPriceLowToHighLink();
		CSVUtil.writeDataLineByLine(CSV_FILE_PATH, searchResultPage.getItemDetails(maxPrice, storage));
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}

}
