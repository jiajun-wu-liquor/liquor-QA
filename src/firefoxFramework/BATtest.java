package firefoxFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class BATtest extends FunctionalTest implements Constants {
	public void begin() {
		summaryLog[0] = "BAT test";
		if (TEST_DOMAIN == "stg.") {
			summaryLog("• BAT test was not set up for 'stg.' in Ghost Inspector. Test not executed.");
			return;
		}
		
		goToLink("https://app.ghostinspector.com/");
		
		login();
		
		goToLink(BAT_SUITE_USER_URL); // new theme suite
		
		try {
			clickByID("btn-execute");
			summaryLog[0] = summaryLog[0] + "(Started)";
		} catch (Exception e) {
			summaryLog("• Suite's execution button could not be found. It could already be running from recently.");
		}
	}
	
	public void login() {
		this.findElement(By.id("input-email")).sendKeys("jj@liquor.com");
		this.findElement(By.id("input-password")).sendKeys("jjthejetplane");
		this.findElement(By.id("btn-login")).click();
		
		waitOut();
	}
	
}
