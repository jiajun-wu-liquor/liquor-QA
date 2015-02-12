package firefoxFramework;

import java.lang.System;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FirefoxBrowser extends FirefoxDriver implements configConstants{
	
	int LOAD_WAIT_TIME = 100;
	long startTime;
	long[] loadTimes = new long[10];
	int arrayCount = 0;
	
	@BeforeTest
	public void setUp() {
		System.out.println(LOG_TEST_BROWSER_START + "Firefox...");
		//Launch the Liquor.com Website
	    goToLink("http://liquor.com");
	}
	/*
	@Test
	public void loginTest() {
	    // Go through log in step 
	    clickByClass("ldc-user-login");
	    findElement(By.id("login_username")).sendKeys(LOGIN_USER_FF); 
	    findElement(By.id("login_password")).sendKeys(LOGIN_PASS_FF);
	    
	    startLoadTimer();
	    clickByID("ldc-user-signin-button");
	    logLoadTime();
	    
	    if (pageDoesContainClass("ldc-user-log-out")) {
	    	System.out.println("• Firefox" + LOG_TEST_LOGIN_PASS);
	    }
	
	}
	
	@Test
	public void saveRecipeTest() {
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		waitOut();
		clickByID("save-bookmark-button");
		
		goToLink("http://liquor.com/user-profile/?tab=recipes");
		if (pageDoesContainText("Bitters Sweet Barrel")) {
			System.out.println("• Firefox" + LOG_TEST_SAVERECIPE_PASS);
		}
		
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		clickByID("bookmark-saved-button");
	}
	
	@Test
	public void logoutTest() {
		startLoadTimer();
		
		try {	
			clickByClass("ldc-user-log-out");
		} catch (Exception e) {
			System.out.println("There is no logout button on the current page.");
		}
		
		if (pageDoesContainClass("ldc-user-login")){
			System.out.println("• Firefox" + LOG_TEST_LOGOUT_PASS);
		}
	}
	*/
	@Test
	public void signupTest() {
		
		try {
			clickByClass("ldc-user-join");
		} catch (Exception e) {
			System.out.println("There is no Sign-up button on the current page.");
			return;
		}
		
		// Finish up first sign up page
		generateNewSignIn(17);
		
		// Finish up second sign up page
		clickByID("ldc-user-signup2-button");
		
		
	}
	
	@AfterTest
	public void tearDown() {
		//this.quit();
	}
	
	public void generateNewSignIn(int i) {
		// TODO re-factor generator out
		Random generator = new Random();
		int randNo = generator.nextInt(100);
		
		findElement(By.id("signup_username")).clear(); 
		findElement(By.id("signup_email")).clear(); 
	    findElement(By.id("signup_password_1")).clear();
	    findElement(By.id("signup_password_2")).clear();
		
		String username = "ghost" + randNo;
		String email = username + "@liquor.com";
		
		findElement(By.id("signup_username")).sendKeys(username); 
	    findElement(By.id("signup_email")).sendKeys(email);
	    findElement(By.id("signup_password_1")).sendKeys(LOGIN_PASS);
	    findElement(By.id("signup_password_2")).sendKeys(LOGIN_PASS);
	    
	    new Select(findElement(By.id("signup_birthdate_month"))).selectByValue("10");
	    new Select(findElement(By.id("signup_birthdate_day"))).selectByValue("29");
	    new Select(findElement(By.id("signup_birthdate_year"))).selectByValue("1991");
	    
	    
	    if(!findElement(By.id("signup_age_tos")).isSelected()){
	    	clickByID("signup_age_tos");
	    }
	    clickByID("ldc-user-signup-button");
	    
	    
	   // WebElement e = findElement(By.id("ldc-user-signup-error"));
	   // System.out.println(e.());
	    /* Guard generated username is used before
	    if (pageDoesContainID("ldc-user-signup-error")) {
	    	generateNewSignIn(i+1);
	    }
	    */
	    System.out.println("New user created: " + username);
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
	public boolean pageDoesContainID(String id) {
		return this.findElements(By.id(id)).size() > 0;
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
