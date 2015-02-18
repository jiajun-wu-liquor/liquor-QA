package firefoxFramework;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import firefoxFramework.FirefoxBrowser.TestType;
import firefoxFramework.configConstants.PostType;

public class PublishTest extends FirefoxBrowser {
	@Test(groups = {"publishTest"} )
	public void publishTest() {
		
		//this.setWindowNo(2);
		this.goToLink(TEST_HOMEPAGE);
		
		summaryLog[0] = "Publish Test";
		if(loginTest(TestType.PUBLISH)) {
				
			publish(PostType.ARTICLE);
			
			clickByClass("edit-post-status");
			new Select(findElement(By.id("post_status"))).selectByValue("draft");
			clickByID("publish");
			summaryLog("• Article is drafted");
		} else {
			summaryLog("• Error: Cannot log in");
		}
	}
	
	@Test(groups = {"publish"} )
	public void publish(PostType type) {
		
		goToLink(generateEditPageLink(type));
		
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
		summaryLog[0] = summaryLog[0] + " (Success)";
		
		goToLink(generateEditPageLink(type));
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
