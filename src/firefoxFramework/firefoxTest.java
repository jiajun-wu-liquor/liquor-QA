package firefoxFramework;

public class firefoxTest implements configConstants {
	
	public static void main(String[] args) {
	    
		
		System.out.println(LOG_TEST_BROWSER_START + "Chrome...");
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
	    ChromeBrowser chrome = new ChromeBrowser();
	    chrome.loginTest();
	    chrome.saveRecipeTest();
	    chrome.logoutTest();
	    chrome.quit();
	    chrome.summarise();
	    
		System.out.println(LOG_TEST_BROWSER_START + "Firefox...");
	    FirefoxBrowser firefox = new FirefoxBrowser();
	    firefox.loginTest();
	    firefox.saveRecipeTest();
	    firefox.logoutTest();
	    firefox.quit();
	    firefox.summarise();
	    
	    System.out.println(LOG_TEST_BROWSER_START + "Safari...");
	    SafariBrowser safari = new SafariBrowser();
	    safari.loginTest();
	    safari.saveRecipeTest();
	    safari.logoutTest();
	    safari.quit();
	    safari.summarise();
	    
	}
	
	/*
	public static FirefoxProfile getProfile() {
		// Attaching blockSite extension to Firefox
	    String blockSiteExtensionPath = "C:\\Users\\Jiajun\\workspace\\LDC\\extensions\\blocksite-1.1.8-fx.xpi";
	    FirefoxProfile profile = new FirefoxProfile();
	    
	    try {
	        profile.addExtension(new File(blockSiteExtensionPath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return profile;
	}*/
}