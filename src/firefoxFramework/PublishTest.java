package firefoxFramework;

import static org.testng.AssertJUnit.assertEquals;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PublishTest extends FunctionalTest implements Constants {
	
	String postTypeName = null;
	int expectedSize = 0;
	int actualSize = 0;
	String[] expected = new String[20];
	String[] actualResult = new String[20];
	
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
		
		clickByID("publish");
		waitOut(2000);
		summaryLog("• " + postTypeName + " is published.");
		clickByLinkText("View post");
		
		String[] actualResult = getActualValues(postType);
		
		try {
		for(int i = 0; i < expectedSize ; i++) {
			assertEquals(actualResult[i], expected[i]);
		}
		System.out.println("");
		} catch (Exception e) {
			summaryLog("• Actual result and expected did not match");
			summaryLog(e.getMessage());
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
		if ( postType == PostType.ARTICLE) {
			String title = getAttributeByMethodAndSelector("id", "title","value");
			String content = getAttributeByMethodAndSelector("id", "content","innerHTML");
			String featuredImg = getAttributeByMethodAndSelector("class", "attachment-266x266","src");
			
			// formatting
			title = title.replace('-', '–');
			content = content.replaceAll("&lt;p&gt;", "").replaceAll("&lt;/p&gt;\n", "");
			featuredImg = featuredImg.replaceAll("-300x93", "");
			
			addToExpected(title);
			addToExpected(content);
			addToExpected(featuredImg); //featured image
		}
		
		if ( postType == PostType.RECIPE) {
			String title = getAttributeByMethodAndSelector("id", "title", "value"); //Name of recipe follows post title, so I used this
			String about = getAttributeByMethodAndSelector("id", "About", "innerHTML");
			String ingredient = getAttributeByMethodAndSelector("name", "Ingredient[name][]", "value");
			String amount = getAttributeByMethodAndSelector("name", "Ingredient[amount][]", "value");
			String unit = getAttributeByMethodAndSelector("name", "Ingredient[unit][]");
			//String glasstype = getAttributeByMethodAndSelector("id", "GlassType");
			//String instructions = getAttributeByMethodAndSelector("id", "Instructions", "innerHTML");
			String prep = getAttributeByMethodAndSelector("name", "PreparationStep[]", "value");
			String featuredImg = getAttributeByMethodAndSelector("class", "attachment-266x266", "src"); // featureImg may become a legacy. prepare to remove
			
			// formatting
			title = title.replace("-", "–");
			int delimDecimal = amount.indexOf('.');
			amount = amount.substring(0, delimDecimal) + " " + unit;
			featuredImg = featuredImg.replaceAll("-300x93", "");
			
			addToExpected(title);
			addToExpected(about);
			addToExpected(ingredient);
			addToExpected(amount);
			//addToExpected(glasstype); // TODO currently not added because the xpath is very hard to identify. add an id for it?
			//addToExpected(instructions); // TODO instructions is not displayed in page
			//addToExpected(prep);
			addToExpected(featuredImg);
		}
		
		return expected;
	}
	
	public String[] getActualValues(PostType postType) {
		// TODO format before addtoactual
		if ( postType == PostType.ARTICLE) {
			addToActual(getAttributeByMethodAndSelector("xpath", getSelectorName("article_header"), "innerHTML"));
			addToActual(getAttributeByMethodAndSelector("xpath", getSelectorName("article_content"), "innerHTML"));
			addToActual(getAttributeByMethodAndSelector("class", "wp-post-image", "src"));
			
			int delim1 = actualResult[2].lastIndexOf('-');
			int delim2 = actualResult[2].lastIndexOf('.');
			actualResult[2] = actualResult[2].substring(0, delim1) + actualResult[2].substring(delim2, actualResult[2].length());
		}
		
		if (postType == PostType.RECIPE) {
			String title = getAttributeByMethodAndSelector("xpath", getSelectorName("recipe_header"), "innerHTML");
			String about = getAttributeByMethodAndSelector("xpath", getSelectorName("recipe_about"), "innerHTML");
			String ingredient = getAttributeByMethodAndSelector("xpath", getSelectorName("recipe_ingredient"), "innerHTML");
			String unit = getAttributeByMethodAndSelector("xpath", getSelectorName("recipe_unit"), "innerHTML");
			//String glasstype = getAttributeBySelectorAndName("xpath", getSelectorName("recipe_glasstype")); // xpath cannot be identified
			//String instrucitons = CREATE XPATH FOR INSTRUCTIONS
			// TODO change all these findelements with the new xpaths
			//String prep = findElements(By.xpath(getSelectorName("recipe_prep"))).get(1).getAttribute("innerHTML"); 
//					getSelectorName("recipe_prep"), "innerHTML");
			String featuredImg = this.getAttributeByMethodAndSelector("xpath", getSelectorName("recipe_img"), "src");
			
			// formatting
			title = title.replace("The ", "").replace(" Cocktail", "");
			ingredient = ingredient.replaceAll("	", "").replace("\n", "");
			unit = unit.replaceAll("	", "").replace("\n", "");
			featuredImg = featuredImg.replace("-300x93", "");
			
			addToActual(title);
			addToActual(about);
			addToActual(ingredient);
			addToActual(unit);
			//addToActual(glasstype);
			//addToActual(instructions);
			//addToActual(prep);
			addToActual(featuredImg);
		}
		
		return actualResult;
	}
	
	// Eg: <input value="valueWanted" id="name"> : getAttributeBySelectorAndName("id","name", "value") == "valueWanted";
	protected String getAttributeByMethodAndSelector(String method, String name, String attribute) {
		By bySelector = null;
		
		switch (method) {
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
		
		if (element.getTagName().contains("select")) {
			return new Select(element).getFirstSelectedOption().getText();
		}
		
		return element.getAttribute(attribute);
	}
	protected String getAttributeByMethodAndSelector(String selector, String name){
		return getAttributeByMethodAndSelector(selector,name,"");
	}
	
	public void addToExpected(String text) {
		expected[expectedSize] = text;
		expectedSize++;
	}
	public void addToActual(String text) {
		actualResult[actualSize] = text;
		actualSize++;
	}
}
