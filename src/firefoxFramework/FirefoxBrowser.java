package firefoxFramework;

import static org.testng.AssertJUnit.assertEquals;
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
 * - Class private methods
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
	
	@Test(groups = {"publish"} )
	public void publishTest() {
		summaryLog[0] = "Publish Test";
		loginTest(TestType.PUBLISH);
		
goToLink(generateEditPageLink(PostType.ARTICLE));
		
		String[] expected = new String[20];
		expected[0] = getFieldAttributeById("value", "title");
		expected[1] = getFieldAttributeById("innerHTML", "content");
		expected[2] = getFieldAttributeByClass("src", "attachment-266x266");
		
		expected[0].replace('-', '–');
		expected[1] = expected[1].replaceAll("&lt;p&gt;", "").replaceAll("&lt;/p&gt;\n", "");
		expected[2] = expected[2].replaceAll("300x93", "");
		
		clickByID("publish"); 
		summaryLog("• Article is published.");
		clickByLinkText("View post");
		
		String[] actual = new String[20];
		actual[0] = getFieldAttributeByClass("innerHTML", "entry-title");
		actual[1] = getFieldAttributeByXpath("innerHTML", "//div[@class='entry-content']/p");
		actual[2] = getFieldAttributeByClass("src", "wp-post-image");
		
		actual[2] = actual[2].replaceAll("290x155", "");
		
		for(int i = 0; i < 3; i++) {
			assertEquals(actual[i], expected[i]);
		}
		summaryLog("• All content assertions are correct");
		
		
		goToLink(generateEditPageLink(PostType.ARTICLE));
		clickByClass("edit-post-status");
		new Select(findElement(By.id("post_status"))).selectByValue("draft");
		clickByID("publish");
		summaryLog("• Article is drafted")
	}
	
	@Test(groups = {"saveRecipe"} )
	public void saveRecipeTest() {
		
		summaryLog[0] = "Save Recipe Test";
		
		loginTest(TestType.SAVE_RECIPE);
		
		// Save recipe
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		waitOut();
		
		if (findElement(By.id("save-bookmark-button")).getAttribute("style") == "display: none;") {
			summaryLog("• Warning: The recipe was not un-saved previously!");
		} else {
			clickByID("save-bookmark-button");
		}
		
		goToLink(TEST_SAVERECIPE_SAVEDPAGE);
		if (pageDoesContainText("Scotch &amp; Soda")) {
			summaryLog(  LOG_TEST_SAVERECIPE_PASS );
		} else {
			summaryLog ("• Save recipe - Unsuccessful");
		}
		
		// Return to page and un-save recipe
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		clickByID("bookmark-saved-button");
		
		logoutTest();
	}
	
	@Test(groups = {"signup"} )
	public void signupTest() {
		
		summaryLog[0] = "Sign up Test";
		
		try {
			clickByClass("ldc-user-join");
		} catch (Exception e) {
			summaryLog("Error: There is no Sign-up button on the current (home) page. Test terminated.");
			return;
		}
		
		// Finish up first sign up page
		generateNewSignIn();
		
		// Finish up second sign up page
		clickByID("ldc-user-signup2-button");
		
		summaryLog( LOG_TEST_SIGNUP_PASS );
	}
	
	public void loginTest(TestType testType) {
		
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
	    }
	
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
	    goToLink(TEST_HOMEPAGE);
	}
	@AfterTest
	public void tearDown() {
		this.summarise();
		this.quit();
	}
	
	// Generates a new username and attempts to sign-up. If username is taken, will generate another username
	private void generateNewSignIn() {
				
		int randNo = generator.nextInt(1000);
		String username = "ghost" + randNo;
		String email = username + "@liquor.com";
		
		findElement(By.id("signup_username")).clear(); 
		findElement(By.id("signup_email")).clear(); 
	    findElement(By.id("signup_password_1")).clear();
	    findElement(By.id("signup_password_2")).clear();
		
		findElement(By.id("signup_username")).sendKeys(username); 
	    findElement(By.id("signup_email")).sendKeys(email);
	    findElement(By.id("signup_password_1")).sendKeys(LOGIN_PASS);
	    findElement(By.id("signup_password_2")).sendKeys(LOGIN_PASS);
	    
	    new Select(findElement(By.id("signup_birthdate_month"))).selectByValue("10");
	    new Select(findElement(By.id("signup_birthdate_day"))).selectByValue("29");
	    new Select(findElement(By.id("signup_birthdate_year"))).selectByValue("1991");
	    
	    // If "Above 21 year old" check-box is not checked
	    if(!findElement(By.id("signup_age_tos")).isSelected()){
	    	clickByID("signup_age_tos");
	    }
	    clickByID("ldc-user-signup-button");
	    
	    waitOut(2000);
	    if (pageDoesContainID("ldc-user-signup-error")) {
    		generateNewSignIn();
    		return;
	    }
	    summaryLog("• New user created: " + username);
	}
	
	// Generate post URL. Temporary implementation while figuring out arrays in Interface.
	private String generateEditPageLink(PostType type) {
		int[] postIDs;
		
		if(TEST_DOMAIN == "stg.") {
			postIDs = stgPostID;
		} else if (TEST_DOMAIN == "dev.") {
			postIDs = devPostID;
		} else {
			postIDs = ldcPostID;
		}

		return TEST_PUBLISH_POSTURL_1 + postIDs[type.ordinal()] + TEST_PUBLISH_POSTURL_2;
	}
	
	
	// Summarizes the test details. Prints out all test pass/fail messages. 
	// Console messages should be stored in summaryLog[] array and print out here instead of sysout direct in main codes.
	private void summarise() {
		for(int i = 0; i < summaryCount; i++) {
			System.out.println(summaryLog[i]);
		}
		
		// Load time 
		//System.out.println("Homepage load time: " + calcHomepageLoadTime() + " milli sec");
		//System.out.println("Average load time: " + calcAvgLoadTime() + " milli sec");
		//System.out.println(loadTimeCount + LOG_TEST_SUMMARY_PAGES);
	}
	private void summaryLog(String logMsg) {
		this.summaryLog[this.summaryCount] = logMsg;
		this.summaryCount++;
	}
	
	// Clicking functions
	// Re-factored to simplify code reading in main functions
	private void clickByID(String id) {
		this.findElement(By.id(id)).click();
		this.waitOut();	
	}
	private void clickByClass(String className) {
		this.findElement(By.className(className)).click();
		this.waitOut();	
	}
	private void clickByLinkText(String text) {
		this.findElement(By.partialLinkText(text)).click();
	}
	private void clickByHref(String href) {
			    
	    this.findElement(By.cssSelector("a[href*='"+ href + "']")).click();
	    this.waitOut();
	    
		// Try with Xpath
		//String xpath = String.format(".//a[contains(@href, '%s')]", href);
	    //this.findElements(By.xpath(xpath)).get(0).click();
	}
	
	// Checking functions
	private boolean pageDoesContainText(String text) {
		return this.getPageSource().contains(text);
	}
	private boolean pageDoesContainClass(String className) {
		return this.findElements(By.className(className)).size() > 0;
	}
	private boolean pageDoesContainID(String id) {
		return this.findElements(By.id(id)).size() > 0;
	}
	private String getFieldAttributeById(String attribute, String id) {
		return this.findElement(By.id(id)).getAttribute(attribute);
	}
	private String getFieldAttributeByClass(String attribute, String className) {
		return this.findElement(By.className(className)).getAttribute(attribute);
	}
	protected String getFieldAttributeByXpath(String attribute, String xpath) {
		return this.findElement(By.xpath(xpath)).getAttribute(attribute);
	}
	
	// Waiting function
	private void waitOut() {
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
	
	private void waitOut(int customWaitTime) {
		try	{
	    	Thread.sleep(customWaitTime);
	    } catch (Exception e) {
	    }
	}
	
	// Page loading time functions
	// Starts the timer. Called before executing other codes that are to be time logged.
	private void startLoadTimer() {
		startTime = System.currentTimeMillis();
	}
	// Calculates time of previous code execute and logs it. Has to call startLoadTimer() before it.
	private void logLoadTime() {
		long currentTime = System.currentTimeMillis();
		this.loadTimes[this.loadTimeCount] = currentTime - this.startTime;
		this.loadTimeCount++;
	}
	private long calcHomepageLoadTime() {
		return this.loadTimes[0];
	}
	private long calcAvgLoadTime() {
		long sum = 0;
		int count = 0;
		while (count <= this.loadTimeCount) {
			sum = sum + this.loadTimes[count];
			count++;
		}
		return sum/this.loadTimeCount;
	}
	
	// Navigating functions
	private void goToLink(String url) {
		//this.startLoadTimer();
		this.get(url);
		//this.logLoadTime();
	}
}
