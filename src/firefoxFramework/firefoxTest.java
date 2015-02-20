package firefoxFramework;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class firefoxTest implements Constants {
	
	FirefoxProfile profile = new FirefoxProfile();
	FirefoxBrowser test = null;
	
	public static void main(String[] args) {
	    
		/*
		System.out.println(LOG_TEST_BROWSER_START + "Chrome...");
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
	    ChromeBrowser chrome = new ChromeBrowser();
	    */
		
		//System.out.println(LOG_TEST_BROWSER_START + "Firefox...");
	    //FirefoxBrowser firefox = new FirefoxBrowser();
	    //firefox.setUp();
	    //firefox.publishTest();
	    //firefox.tearDown();
	    
	    //SignupTest s = new SignupTest(profile);
		//s.signupTest();
		//s.tearDown();
		
		//SaveRecipeTest r = new SaveRecipeTest();
		//r.saveRecipeTest();
		//r.tearDown();
	    
	}
	
	@BeforeTest
	public void setProfilePreference() {
	    profile.setPreference("webdriver.load.strategy", "unstable");
	}
	
	@Test
	public void publishTest() {
	    test = new PublishTest(profile);
	   // this.resizeToWindow(0);
	    test.begin();
	    test.tearDown();
	}
	
	@Test
	public void signupTest() {
		test = new SignupTest(profile);
		//this.resizeToWindow(1);
		test.begin();
		test.tearDown();
	}
	
	@Test
	public void saveRecipeTest() {
		test = new SaveRecipeTest(profile);
		//this.resizeToWindow(2);
		test.begin();
		test.tearDown();
	}
	
	public void resizeToWindow(int i) {
		test.manage().window().setSize(new Dimension(windowWidth, windowHeight));
		test.manage().window().setPosition(new Point(windowWidth*(i%3),windowHeight*(i/3)));
	}
	
	public FirefoxProfile getProfile() {
		// Attaching blockSite extension to Firefox
	    //String blockSiteExtensionPath = "C:\\Users\\Jiajun\\workspace\\LDC\\extensions\\blocksite-1.1.8-fx.xpi";
	    FirefoxProfile profile = new FirefoxProfile();
	    
	    /*
	    try {
	        profile.addExtension(new File(blockSiteExtensionPath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }*/
	    
	    return profile;
	}
}