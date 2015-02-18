package firefoxFramework;

import static org.testng.AssertJUnit.assertEquals;

import java.awt.Toolkit;
import java.lang.System;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/* Documentation
 * Class consist of 3 sections
 * - Class variables
 * - Class public methods
 * 	 These are the main methods we will call. Consist of 3 segments for TestNG.
 * 	 > @Before/AfterTest: Executed before the other functions are called WHEN RUNNING FROM TESTNG ONLY.
 * 		• setUp()
 * 		• tearDown()
 * 	 > @Test: The functions called by TestNG. Also the main functional tests.
 * 		• saveRecipeTest()
 * 		• signupTest()
 * 	 > Other @Tests: public functions to be tested but not called, because they are called in other testing functions anyway.
 * 		• loginTest()
 * 		• logoutTest()
 * - Class protected methods
 */

public class FirefoxBrowser extends FirefoxDriver implements configConstants{
	
	int WAIT_TIME_LOAD_MILLISEC = 2500;
	int WAIT_TIME_SIGNUP_MILLISEC = 2000;
	
	Random generator = new Random();
	String[] summaryLog = new String[20];
	long[] loadTimes = new long[10];
	int summaryCount = 1;
	int loadTimeCount = 0;
	long startTime;
	
	public enum TestType { NONE, SAVE_RECIPE, PUBLISH }
	
	public boolean loginTest(TestType testType) {
		
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
		
	    clickByClass("ldc-user-login");
	    
	    // Fill in log in fields
	    findElement(By.id("login_username")).sendKeys(username); 
	    findElement(By.id("login_password")).sendKeys(LOGIN_PASS);
	    
	    clickByID("ldc-user-signin-button");
	    
	    if (pageDoesContainClass("ldc-user-log-out")) {
	    	summaryLog( LOG_TEST_LOGIN_PASS);
	    	return true;
	    }
	    return false;
	
	}
	public void loginTest() {
		loginTest(TestType.NONE);
	}
	
	public void logoutTest() {
		
		try {	
			clickByClass("ldc-user-log-out");
			if (pageDoesContainClass("ldc-user-login")){
				summaryLog(  LOG_TEST_LOGOUT_PASS );
			}
		} catch (Exception e) {
			summaryLog( "Logout - There is no logout button on the current page.");
		}
	}
	
	@BeforeTest
	public void setUp() {
		//System.out.println(LOG_TEST_BROWSER_START + "Firefox...");
		
		// Launch the Liquor.com Website
	    //goToLink(TEST_HOMEPAGE);
	}
	@AfterTest
	public void tearDown() {
		this.summarise();
		this.quit();
	}
	
	// Set windowPosition
	public void setWindowNo(int i) {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = (int)(screenSize.getWidth()/3/1.3);
		int windowHeight = (int)(screenSize.getHeight()/3);
		Dimension windowSize = new Dimension(windowWidth, windowHeight);
		
		this.manage().window().setSize(windowSize);
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
	protected void clickByLinkText(String text) {
		this.findElement(By.partialLinkText(text)).click();
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
	protected String getFieldAttributeById(String attribute, String id) {
		return this.findElement(By.id(id)).getAttribute(attribute);
	}
	protected String getFieldAttributeByClass(String attribute, String className) {
		return this.findElement(By.className(className)).getAttribute(attribute);
	}
	protected String getFieldAttributeByXpath(String attribute, String xpath) {
		return this.findElement(By.xpath(xpath)).getAttribute(attribute);
	}
	
	// Waiting function
	protected void waitOut() {
		try	{
	    	Thread.sleep(WAIT_TIME_LOAD_MILLISEC);
	    } catch (Exception e) {
	    }
		
		// TODO try this wait method
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		//WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ID")));
		// TODO try this wait method
		// Put a Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
		//this.manage().timeouts().implicitlyWait(LOAD_WAIT_TIME, TimeUnit.SECONDS);
	}
	
	protected void waitOut(int customWaitTime) {
		try	{
	    	Thread.sleep(customWaitTime);
	    } catch (Exception e) {
	    }
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
