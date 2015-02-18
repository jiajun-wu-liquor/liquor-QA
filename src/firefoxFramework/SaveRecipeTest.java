package firefoxFramework;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.openqa.selenium.*;

import firefoxFramework.FirefoxBrowser.TestType;

public class SaveRecipeTest extends FirefoxBrowser {
	@Test(groups = {"saveRecipeTest"} )
	public void saveRecipeTest() {
		
		//this.setWindowNo(1);
		goToLink(TEST_HOMEPAGE);
		
		summaryLog[0] = "Save Recipe Test";
		
		if(loginTest(TestType.SAVE_RECIPE)){		
			saveRecipe();	
			logoutTest();
		} else {
			summaryLog("• Error: Cannot log in");
		}
	}
	
	public void saveRecipe() {
		
		// Save recipe
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		
		waitOut();
		
		if (findElement(By.id("save-bookmark-button")).getAttribute("style").contains("display: none;")) {
			summaryLog("• Warning: The recipe was not un-saved previously!");
		} else {
			clickByID("save-bookmark-button");
		}
		
		goToLink(TEST_SAVERECIPE_SAVEDPAGE);
		if (pageDoesContainText("Scotch &amp; Soda")) {
			summaryLog[0] = summaryLog[0] + " (Success)";
		} else {
			summaryLog[0] = summaryLog[0] + " (Unsuccessful)";
		}
		
		// Return to page and un-save recipe
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		try {
			clickByID("bookmark-saved-button");
		} catch (Exception e) {
			summaryLog("• Could not un-save bookmark. Check that no one else logged in to the same account and un-toggled the saved recipe.");
		}
	}
}
