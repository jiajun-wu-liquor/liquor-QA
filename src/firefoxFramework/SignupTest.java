package firefoxFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class SignupTest extends FirefoxBrowser {
	@Test(groups = {"signupTest"} )
	public void signupTest() {
		//this.setWindowNo(0);
		goToLink(TEST_HOMEPAGE);
		
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
		
		summaryLog[0] = summaryLog[0] + " (Success)";
	}
	
	// Generates a new username and attempts to sign-up. If username is taken, will generate another username
	protected void generateNewSignIn() {
				
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
}
