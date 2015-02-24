package firefoxFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.Test;

public class SaveRecipeTest extends FunctionalTest implements Constants {
	public SaveRecipeTest(FirefoxProfile profile) {
		super(profile);
	}
	
	@Test(groups = {"saveRecipeTest"} )
	public void begin() {
		
		goToLink(TEST_HOMEPAGE);
		
		summaryLog[0] = "Save Recipe Test";
		
		if(login(TestType.SAVE_RECIPE)){		
			saveRecipe();
		} else {
			summaryLog("• Error: Cannot log in");
		}
	}
	
	public void saveRecipe() {
		
		// Save recipe
		goToLink(TEST_SAVERECIPE_RECIPEURL);
		
		waitOut();
		
		
		if (findElement(By.xpath(getSelectorName("save_recipe_button"))).getAttribute("style").contains("display: none;")) {
			summaryLog("• Warning: The recipe was not un-saved previously!");
		} else {
			clickByXpath(getSelectorName("save_recipe_button"));
			summaryLog("• Bookmark saved");
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
			clickByXpath(getSelectorName("unsave_recipe_button"));
			summaryLog("• Bookmark unsaved");
		} catch (Exception e) {
			summaryLog("• Could not un-save bookmark. Check that no one else logged in to the same account and un-toggled the saved recipe. Else see Exception error message below: ");
			summaryLog(e.getMessage());		}
	}
}
