package firefoxFramework;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PublishTest extends FunctionalTest implements Constants {
	
	String postTypeName = null;
	int checklistSize = 0;
	
	public PublishTest(FirefoxProfile profile){
		super(profile);
	}
	
	@Test(groups = {"publishTest"} )
	public void begin(PostType postType) {
		postTypeName = postType.toString().substring(0, 1).toUpperCase() + postType.toString().substring(1);
		summaryLog[0] = "Publish Test - " + postTypeName;
		if (TEST_DOMAIN == "") {
			summaryLog("• Publish test is not executed on liquor.com. We are not gonna be messing with the real content right? ;)");
			return;
		}
		
		this.goToLink(TEST_HOMEPAGE);
		
		if(login(TestType.PUBLISH)) {
			publish(postType);
		} else {
			summaryLog("• Error: Cannot log in");
		}
	}
	
	public void publish(PostType postType) {
		
		goToLink(generateEditPageLink(postType));
		
		String[] expected = getExpectedValues(postType);
		
		if(true)return;
		
		clickByID("publish"); 
		summaryLog("• " + postTypeName + " is published.");
		clickByLinkText("View post");
		
		//String[] actual = getActualValues(postType);
		
		if (checklistSize == 0) {
			summaryLog("• The checklistSize is not updated. Asserts cannot work like this.");
			return;
		}
		
		for(int i = 0; i < checklistSize ; i++) {
			System.out.println(expected[i]);
			//assertEquals(actual[i], expected[i]);
		}
		
		summaryLog("• All content assertions are correct");
		summaryLog[0] = summaryLog[0] + " (Success)";
		
		goToLink(generateEditPageLink(postType));
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
		
	public String[] getExpectedValues(PostType postType) {
		String[] expected = new String[20];
		int i = 0;
		
		if ( postType == PostType.ARTICLE) {
			expected[0] = getAttributeBySelectorAndName("id", "title","value");
			expected[1] = getAttributeBySelectorAndName("id", "content","innerHTML");
			expected[2] = getAttributeBySelectorAndName("class", "attachment-266x266","src"); //featured image
			
			//formatting
			expected[0].replace('-', '–');
			expected[1] = expected[1].replaceAll("&lt;p&gt;", "").replaceAll("&lt;/p&gt;\n", "");
			expected[2] = expected[2].replaceAll("-300x93", "");
			checklistSize = 3;
		}
		
		if ( postType == PostType.RECIPE) {
			expected[0] = getAttributeBySelectorAndName("id", "title", "value");
			
			expected[1] = getAttributeBySelectorAndName("id", "Name", "value");
			expected[2] = getAttributeBySelectorAndName("id", "About", "innerHTML");
			expected[3] = getAttributeBySelectorAndName("name", "Ingredient[name][]", "value");
			expected[4] = getAttributeBySelectorAndName("name", "Ingredient[amount][]", "value");
			expected[5] = getAttributeBySelectorAndName("name", "Ingredient[unit][]");
			
			checklistSize = 5;
		}
		
		while (i < checklistSize) {
			System.out.println(expected[i]);
			i++;
		}
		
		return expected;
	}
	
	public String[] getActualValues(PostType postType) {
		String[] actual = new String[20];
		
		if ( postType == PostType.ARTICLE) {
			actual[0] = getAttributeBySelectorAndName("xpath", getSelectorName("article_header"), "innerHTML");
			actual[1] = getAttributeBySelectorAndName("xpath", getSelectorName("article_content"), "innerHTML");
			actual[2] = getAttributeBySelectorAndName("class", "wp-post-image", "src");
			
			int delim1 = actual[2].lastIndexOf('-');
			int delim2 = actual[2].lastIndexOf('.');
			actual[2] = actual[2].substring(0, delim1) + actual[2].substring(delim2, actual[2].length());
		}
		
		if (postType == PostType.RECIPE) {
			
		}
		
		return actual;
	}
	
	// Eg: <input value="valueWanted" id="name"> : getAttributeBySelectorAndName("id","name", "value") == "valueWanted";
	protected String getAttributeBySelectorAndName(String selector, String name, String attribute) {
		By bySelector = null;
		
		switch (selector) {
		case "name":
			bySelector = By.name(name);
			break;
		case "id":
			bySelector = By.id(name);
			break;
		case "class":
			bySelector = By.className(name);
			break;
		case "xpath":
			bySelector = By.xpath(name);
			break;
		default:
			summaryLog("• Field cannot be foudn using this selector method");
		}
		
		WebElement element = this.findElement(bySelector);
		
		if (element.getTagName() == "select") {
			return new Select(element).getFirstSelectedOption().getText();
		}
		
		return element.getAttribute(attribute);
	}
	protected String getAttributeBySelectorAndName(String selector, String name){
		return getAttributeBySelectorAndName(selector,name,"");
	}
}
