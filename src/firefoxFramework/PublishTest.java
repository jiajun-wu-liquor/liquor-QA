package firefoxFramework;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PublishTest extends FirefoxBrowser {
	
	public PublishTest(FirefoxProfile profile){
		super(profile);
	}
	
	@BeforeTest
	public void setBrowserPreference() {
		FirefoxProfile profile = new FirefoxProfile();
	    profile.setPreference("webdriver.load.strategy", "unstable");
	    
	    PublishTest p = new PublishTest(profile);
	}
	
	@Test(groups = {"publishTest"} )
	public void begin() {
		
		//this.setWindowNo(2);
		this.goToLink(TEST_HOMEPAGE);
		
		summaryLog[0] = "Publish Test";
		if(loginTest(TestType.PUBLISH)) {
			publish(PostType.ARTICLE);
		} else {
			summaryLog("• Error: Cannot log in");
		}
	}
	
	public void publish(PostType type) {
		
		//temporary fix for homepage login != wp-admin login in dev. new theme
		if (TEST_DOMAIN == "dev.") {
			this.waitOut(5000);
			//this.authenticate();
			goToLink(TEST_HOMEPAGE + "/wp-admin");
			this.findElement(By.id("user_login")).clear();
			this.findElement(By.id("user_login")).sendKeys("ldc.ghost-inspector");
			this.findElement(By.id("user_pass")).clear();
			this.findElement(By.id("user_pass")).sendKeys("ghostghost");
			this.findElement(By.id("wp-submit")).click();
		}
		
		goToLink(generateEditPageLink(type));
		
		String[] expected = new String[20];
		expected[0] = getFieldAttributeById("value", "title");
		expected[1] = getFieldAttributeById("innerHTML", "content");
		expected[2] = getFieldAttributeByClass("src", "attachment-266x266");
		
		expected[0].replace('-', '–');
		expected[1] = expected[1].replaceAll("&lt;p&gt;", "").replaceAll("&lt;/p&gt;\n", "");
		expected[2] = expected[2].replaceAll("-300x93", "");
		
		clickByID("publish"); 
		summaryLog("• Article is published.");
		clickByLinkText("View post");
		
		String[] actual = new String[20];
		actual[0] = getFieldAttributeByXpath("innerHTML", getSelectorName("article_header"));
		actual[1] = getFieldAttributeByXpath("innerHTML", getSelectorName("article_content"));
		actual[2] = getFieldAttributeByClass("src", "wp-post-image");
		
		int delim1 = actual[2].lastIndexOf('-');
		int delim2 = actual[2].lastIndexOf('.');
		actual[2] = actual[2].substring(0, delim1) + actual[2].substring(delim2, actual[2].length());
//		actual[2] = actual[2].replaceAll("290x155", "");
		
		for(int i = 0; i < 3; i++) {
			assertEquals(actual[i], expected[i]);
		}
		
		summaryLog("• All content assertions are correct");
		summaryLog[0] = summaryLog[0] + " (Success)";
		
		goToLink(generateEditPageLink(type));
		waitOut(1000);
		clickByClass("edit-post-status");
		new Select(findElement(By.id("post_status"))).selectByValue("draft");
		clickByID("publish");
		summaryLog("• Article is drafted");
	}
	
	// Generate post URL. Temporary implementation while figuring out arrays in Interface.
	protected String generateEditPageLink(PostType type) {
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
		
}
