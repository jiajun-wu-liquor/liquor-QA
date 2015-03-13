package firefoxFramework;

import java.awt.Robot;
import java.awt.Toolkit;
import java.lang.System;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class FunctionalTest extends /*RemoteWebDriver*/ FirefoxDriver implements Constants{
	
	Random generator = new Random();
	String[] summaryLog = new String[20];
	long[] loadTimes = new long[10];
	int summaryCount = 1;
	int loadTimeCount = 0;
	long startTime;
	
	public enum TestType { NONE, SAVE_RECIPE, PUBLISH }
	
	public FunctionalTest() {
		super();
	}
	public FunctionalTest(FirefoxProfile profile) {
		super(profile);
	}
	public FunctionalTest(URL url, Capabilities caps) {
		//super(url, caps);
	}
	
	public void begin() {					// for error-free calling of child functions
	}
	public void begin(PostType postType){	// for publishTest
	}
	
	public boolean login(TestType testType) {
		
		String username;
		
		switch (testType) {
			case SAVE_RECIPE:
				username = LOGIN_USER_SAVERECIPE;
				break;
			case PUBLISH:
				username = LOGIN_USER_PUBLISH;
				break;
			default:
				username = LOGIN_USER_GENERAL;
		}
		
		
		this.goToLink(TEST_HOMEPAGE + "wp-admin/");
				
	    // Fill in log in fields
		waitOutFor("user_login", "id");
	    findElement(By.id("user_login")).sendKeys(username); 
	    findElement(By.id("user_pass")).sendKeys(LOGIN_PASS);
	    
	    clickByID("wp-submit");
	    
	    
	    //getFieldAttributeByXpath("innerHTML", "//div[@class='wrao']/h2") == "Dashboard"
	    if (this.pageDoesContainText("Dashboard")) {
	    	summaryLog("• Logged into backend");
	    	return true;
	    }
	    return false;
	}
	public void login() {
		login(TestType.NONE);
	}
	
	@BeforeTest
	public void setUp() {
	}
	
	@AfterTest
	public void tearDown() {
		this.summarise();
		this.quit();
	}
	
	// Authentication
	public void authenticate() {
		WebDriverWait wait = new WebDriverWait(this, 10);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.authenticateUsing(new UserAndPassword("liquor", "negroni"));
	}
	
	//Get CSS selectors
	public String getSelectorName(String selector) {
		Map<String,String> selectorNames = new HashMap<String, String>();
		
		if (TEST_THEME == "liquor") {
			selectorNames = liqourOldSelectorNames;
		} else if (TEST_THEME == "liquor2015") {
			selectorNames = liquor2015SelectorNames;
		}
		
		return (String) selectorNames.get(selector);
	}
	
	// Set windowPosition
	public void setWindowNo(int i) {
		//java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = (int)(1920/3/1.3);
		int windowHeight = (int)(1080/3);
		//Dimension windowSize = new Dimension(windowWidth, windowHeight);
		
		
		this.manage().window().setSize(new Dimension(windowWidth, windowHeight));
		this.manage().window().setPosition(new Point(windowWidth*(i%3),windowHeight*(i/3)));
	}
	
	// Summarizes the test details. Prints out all test pass/fail messages. 
	// Console messages should be stored in summaryLog[] array and print out here instead of sysout direct in main codes.
	protected void summarise() {
		for(int i = 0; i < summaryCount; i++) {
			System.out.println(summaryLog[i]);
		}
		System.out.println("");
		// Load time 
		//System.out.println("Homepage load time: " + calcHomepageLoadTime() + " milli sec");
		//System.out.println("Average load time: " + calcAvgLoadTime() + " milli sec");
		//System.out.println(loadTimeCount + LOG_TEST_SUMMARY_PAGES);
	}
	protected void summaryLog(String logMsg) {
		this.summaryLog[this.summaryCount] = logMsg;
		this.summaryCount++;
	}
	
	// Clicking functions
	// Re-factored to simplify code reading in main functions
	protected void clickByID(String id) {
		this.findElement(By.id(id)).click();
		this.waitOut();	
	}
	protected void clickByClass(String className) {
		this.findElement(By.className(className)).click();
		this.waitOut();	
	}
	protected void clickByXpath(String xpath) {
		this.findElement(By.xpath(xpath)).click();
		this.waitOut();
	}
	protected void clickByLinkText(String text) {
		this.findElement(By.partialLinkText(text)).click();
		this.waitOut();
	}
	protected void clickByHref(String href) {
			    
	    this.findElement(By.cssSelector("a[href*='"+ href + "']")).click();
	    this.waitOut();
	    
		// Try with Xpath
		//String xpath = String.format(".//a[contains(@href, '%s')]", href);
	    //this.findElements(By.xpath(xpath)).get(0).click();
	}
	
	// Checking functions
	protected boolean pageDoesContainText(String text) {
		return this.getPageSource().contains(text);
	}
	protected boolean pageDoesContainClass(String className) {
		return this.findElements(By.className(className)).size() > 0;
	}
	protected boolean pageDoesContainID(String id) {
		return this.findElements(By.id(id)).size() > 0;
	}
	
	// Waiting function
	protected void waitOutFor(String selector, String byMethod) {
		
		By bySelector = null;
		switch (byMethod) {
		case "name":
			bySelector = By.name(selector);
			break;
		case "id":
			bySelector = By.id(selector);
			break;
		case "class":
			bySelector = By.className(selector);
			break;
		case "xpath":
			bySelector = By.xpath(selector);
			break;
		default:
			summaryLog("• Field cannot be foudn using this selector method");
		}
		
		WebDriverWait wait = new WebDriverWait(this, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(bySelector));
				
		//ExpectedConditions.visibilityOfElementLocated(By.id("ID")));
	}
	
	protected void waitOut(int customWaitTime) {
		try	{
	    	Thread.sleep(customWaitTime);
	    } catch (Exception e) {
	    }
	}
	protected void waitOut() {
		waitOut(WAIT_TIME_LOAD_MILLISEC);
	}
	
	// Page loading time functions
	// Starts the timer. Called before executing other codes that are to be time logged.
	protected void startLoadTimer() {
		startTime = System.currentTimeMillis();
	}
	// Calculates time of previous code execute and logs it. Has to call startLoadTimer() before it.
	protected void logLoadTime() {
		long currentTime = System.currentTimeMillis();
		this.loadTimes[this.loadTimeCount] = currentTime - this.startTime;
		this.loadTimeCount++;
	}
	protected long calcHomepageLoadTime() {
		return this.loadTimes[0];
	}
	protected long calcAvgLoadTime() {
		long sum = 0;
		int count = 0;
		while (count <= this.loadTimeCount) {
			sum = sum + this.loadTimes[count];
			count++;
		}
		return sum/this.loadTimeCount;
	}
	
	// Navigating functions
	protected void goToLink(String url) {
		//this.startLoadTimer();
		this.get(url);
		//this.logLoadTime();
	}
}
