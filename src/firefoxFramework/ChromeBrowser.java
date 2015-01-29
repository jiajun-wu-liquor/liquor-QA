package firefoxFramework;

import java.lang.System;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser extends ChromeDriver implements configConstants {
	
	int LOAD_WAIT_TIME = 1000;
	long startTime;
	long[] loadTimes = new long[10];
	int arrayCount = 0;
	
	public void loginTest() {
		
	    //Launch the Liquor.com Website
	    goToLink("http://liquor.com");
	    
	    // Go through log in step 
	    waitOut();
	    clickByClass("ldc-user-login");
	    findElement(By.id("login_username")).sendKeys(LOGIN_USER_CH); 
	    findElement(By.id("login_password")).sendKeys(LOGIN_PASS_CH);
	    
	    startLoadTimer();
	    clickByID("ldc-user-signin-button");
	    logLoadTime();
	    
	    if (pageDoesContainClass("ldc-user-log-out")) {
	    	System.out.println("• Chrome" + LOG_TEST_LOGIN_PASS);
	    }
	
	}
	
	public void saveRecipeTest() {
		goToLink("http://liquor.com/recipes/bitters-sweet-barrel/");
		waitOut();
		clickByID("save-bookmark-button");
		
		goToLink("http://liquor.com/user-profile/?tab=recipes");
		if (pageDoesContainText("Bitters Sweet Barrel")) {
			System.out.println("• Chrome" + LOG_TEST_SAVERECIPE_PASS);
		}
		
		goToLink("https://liquor.com/recipes/bitters-sweet-barrel/");
		clickByID("bookmark-saved-button");
	}
	
	public void logoutTest() {
		
		try {
			startLoadTimer();
			clickByClass("ldc-user-log-out");
		} catch (Exception e) {
			System.out.println("There is no logout button on the current page.");
		}
		
		if (pageDoesContainClass("ldc-user-login")){
			System.out.println("• Chrome" + LOG_TEST_LOGOUT_PASS);
		}
	}
	
	public void summarise() {
		System.out.println("Homepage load time: " + calcHomepageLoadTime() + " milli sec");
		System.out.println("Average load time: " + calcAvgLoadTime() + " milli sec");
		System.out.println(arrayCount + LOG_TEST_SUMMARY_PAGES);
	}
	
	// Clicking functions
	public void clickByID(String id) {
		this.findElement(By.id(id)).click();
		this.waitOut();	
	}
	public void clickByClass(String className) {
		this.findElement(By.className(className)).click();
		this.waitOut();	
	}
	public void clickByHref(String href) {
		//String xpath = String.format(".//a[contains(@href, '%s')]", href);
	    //this.findElements(By.xpath(xpath)).get(0).click();
	    
	    this.findElement(By.cssSelector("a[href*='href']")).click();
	    this.waitOut();
	}
	
	
	// Checking functions
	public boolean pageDoesContainText(String text) {
		return this.getPageSource().contains(text);
	}
	public boolean pageDoesContainClass(String className) {
		return this.findElements(By.className(className)).size() > 0;
	}
	
	
	// Waiting function
	public void waitOut() {
		// Put a Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
		this.manage().timeouts().implicitlyWait(LOAD_WAIT_TIME, TimeUnit.SECONDS);
	}
	
	// Load page functions
	public void startLoadTimer() {
		startTime = System.currentTimeMillis();
	}
	public void logLoadTime() {
		long currentTime = System.currentTimeMillis();
		this.loadTimes[this.arrayCount] = currentTime - this.startTime;
		this.arrayCount++;
	}
	public long calcHomepageLoadTime() {
		return this.loadTimes[0];
	}
	public long calcAvgLoadTime() {
		long sum = 0;
		int count = 0;
		while (count <= this.arrayCount) {
			sum = sum + this.loadTimes[count];
			count++;
		}
		return sum/this.arrayCount;
	}
	
	// Navigating functions
	public void goToLink(String url) {
		this.startLoadTimer();
		this.get(url);
		this.logLoadTime();
	}
}
