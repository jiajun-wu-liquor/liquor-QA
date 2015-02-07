package firefoxFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BAT_LDC_posts {
	
	
	@Test
	public void BAT_LDC_all_posts() {
		FirefoxDriver ff = new FirefoxDriver();
		BAT_LDC_article(ff);
	}
	
	public void BAT_LDC_article(WebDriver driver) {
		log_entering("article");
		
		driver.get("http://liquor.com/articles/cocktails-are-ruining-your-diet/");
		
		String title = driver.findElement(By.className("entry-title")).getText();
		String content = driver.findElement(By.className("entry-content")).getText();
		WebElement img = driver.findElement(By.className("featured-image-slug"));
		
		Assert.assertEquals(title, "Cocktails Are Ruining Your Diet");
		Assert.assertTrue(content.contains("We have some sad"));
		Assert.assertTrue(img.isDisplayed());
	}
	
	public void log_entering(String post_type) {
		System.out.println("Beginning BAT test: " + post_type);
	}
}
