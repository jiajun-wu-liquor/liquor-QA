package firefoxFramework;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class firefoxTest implements Constants {
	
	FirefoxProfile profile = new FirefoxProfile();
	FunctionalTest test = null;
	
	public static void main(String[] args) {
	    
		FirefoxProfile profilez = new FirefoxProfile(new File("C:////Users//david//AppData//Roaming//Mozilla//Firefox//Profiles//31j2jky2.liquor"));
		profilez.setPreference("webdriver.load.strategy", "unstable");
		
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
	    
	    //SignupTest s = new SignupTest(profilez);
		//s.begin();
		//s.tearDown();
		
		//SaveRecipeTest r = new SaveRecipeTest(profilez);
		//r.begin();
		//r.tearDown();
		
		PublishTest p = new PublishTest(profilez);
		p.begin(PostType.RECIPE);
		p.tearDown();
		
		//LoginTest l = new LoginTest(profilez);
		//l.begin();
		//l.tearDown();
	    
		//BATtest b = new BATtest();
		//b.begin();
	}
	
	@BeforeTest
	public void setProfilePreference() {
	    profile.setPreference("webdriver.load.strategy", "unstable");
	}
	
	@Test
	public void signupTest() {
		if (DO_SIGNUP_TEST) {		
			test = new SignupTest(profile);
			//this.resizeToWindow(1);
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
		this.resizeToWindow(8);
		System.out.println("Save Recipe test launched");
		test.begin();
		test.tearDown();
	}
	
	@Test
	public void loginTest() {
		test = new LoginTest(profile);
		this.resizeToWindow(7);
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
	    
	    return profile;
	}
}