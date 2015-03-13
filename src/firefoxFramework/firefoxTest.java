package firefoxFramework;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class firefoxTest implements Constants {
	
	FirefoxProfile profile = new FirefoxProfile();
	FunctionalTest test = null;
	
	public static final String USERNAME = "liquorcom1";
	public static final String AUTOMATE_KEY = "rut8ZXz8rqZyjRkwqfsh";
	public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
	
	 public static void main(String[] args) throws Exception {
		 
		
		FirefoxProfile profilez = new FirefoxProfile(new File("C:////Users//david//AppData//Roaming//Mozilla//Firefox//Profiles//31j2jky2.liquor"));
		profilez.setPreference("webdriver.load.strategy", "unstable");
	    
	    //SignupTest s = new SignupTest(profilez);
		//s.begin();
		//s.tearDown();
		
		//SaveRecipeTest r = new SaveRecipeTest(profilez);
		//r.begin();
		//r.tearDown();
		
		PublishTest p = new PublishTest(profilez);
		p.begin(PostType.ARTICLE);
		p.tearDown();
		
		//LoginTest l = new LoginTest(profilez);
		//l.begin();
		//l.tearDown();
	    
		//BATtest b = new BATtest();
		//b.begin();
		
		
		/*
	    DesiredCapabilities caps = new DesiredCapabilities();
	    caps.setCapability("os", "Windows");
	    caps.setCapability("os_version", "7");
	    caps.setCapability("browser", "IE");
	    caps.setCapability("browser_version", "9.0");
	    caps.setCapability("resolution", "1024x768");
	    caps.setCapability("browserstack.debug", true);

	    LoginTest driver = new LoginTest(new URL(URL), caps);
	    
	    driver.goToLink(TEST_HOMEPAGE + "wp-admin/");
		driver.login();
		driver.logout();
	    
	    driver.quit();
	    */
	}
	
	@BeforeTest
	public void setProfilePreference() {
	    profile.setPreference("webdriver.load.strategy", "unstable");
	}
	
	@Test
	public void signupTest() {
		if (DO_SIGNUP_TEST) {		
			test = new SignupTest(profile);
			//this.resizeToWindow(0);
			System.out.println("Signup test launched");
			test.begin();
			test.tearDown();
		}
	}
	
	@Test
	public void publishTest() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
	    test = new PublishTest(profile);
	    this.resizeToWindow(2);
		System.out.println("Publish test launched");
	    test.begin(PostType.RECIPE);
	    test.tearDown();
	}
	
	@Test
	public void publishArticleTest() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
	    test = new PublishTest(profile);
	    this.resizeToWindow(5);
		System.out.println("Publish test launched");
	    test.begin(PostType.ARTICLE);
	    test.tearDown();
	}
	
	@Test
	public void saveRecipeTest() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		test = new SaveRecipeTest(profile);
		this.resizeToWindow(4);
		System.out.println("Save Recipe test launched");
		test.begin();
		test.tearDown();
	}
	
	@Test
	public void loginTest() {
		test = new LoginTest(profile);
		this.resizeToWindow(1);
		System.out.println("Login test launched");
		test.begin();
		test.tearDown();
	}
	
	@Test
	public void BATtest() {
		if (DO_BAT_TEST) {
			test = new BATtest();
			this.resizeToWindow(0);
			System.out.println("BAT test launched");
			test.begin();
			test.summarise();
		}
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
	    
		/*profilez.setPreference("network.negotiate-auth.trusteduris", "http://dev.liquor.com");
		try {
			profilez.addExtension(new File("C:////Users//david//workspace//addons//autoauth-2.1-fx+fn.xpi"));
		} catch (IOException e) {
			System.out.println("AutoAuth extension not found");
			e.printStackTrace();
		}*/
		
		/*
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
	    ChromeBrowser chrome = new ChromeBrowser();
	    */
	    
	    return profile;
	}
}