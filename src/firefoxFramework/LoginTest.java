package firefoxFramework;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.internal.Locatable;

public class LoginTest extends FunctionalTest implements Constants {
	public LoginTest(FirefoxProfile profile){
		super(profile);
	}
	
	public LoginTest(URL url, Capabilities caps) {
		super(url, caps);
	}
	
	public void begin() {
		summaryLog[0] = "Login Test";
		
		this.goToLink(TEST_HOMEPAGE);
		login();
		logout();
	}
	
	public void login() {
	    clickByClass(getSelectorName("login_button"));
	    
	    // Fill in log in fields
	    findElement(By.id("login_username")).sendKeys(LOGIN_USER_GENERAL); 
	    findElement(By.id("login_password")).sendKeys(LOGIN_PASS);
	    
	    clickByID("ldc-user-signin-button");
	    
	    if (pageDoesContainClass("ldc-user-log-out")) {
	    	summaryLog("• Logged in");
	    	summaryLog[0] = summaryLog[0] + " (Success)";
	    }
	}
	
	public void logout() {
		goToLink(TEST_HOMEPAGE);
		try {
			if (TEST_THEME == "liquor") {
				clickByXpath(this.getSelectorName("logout_button"));
				summaryLog("• Logged out");
			} else if (TEST_THEME == "liquor2015"){
				summaryLog("• There is no logout button in the theme except WP built-in logout");
			}
		} catch (Exception e) {
			summaryLog( "• Logout - There is no logout button on the current page.");
		}
	}
}
