package firefoxFramework;

public class firefoxTest {
	
	public static void main(String[] args) {
	    
		/*String CHROMEDRIVER_PATH = "C://Users//Jiajun//workspace//liquor-QA-chrome//addons//chromedriver.exe";
		
		System.out.println("Beginning functional test on chrome");
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
	    ChromeBrowser chrome = new ChromeBrowser();
	    chrome.loginTest();
	    chrome.saveRecipeTest();
	    chrome.logoutTest();
	    chrome.quit();
	    chrome.summarise();
	    */
		System.out.println("Beginning functional test on Firefox");
	    FirefoxBrowser firefox = new FirefoxBrowser();
	    firefox.loginTest();
	    firefox.saveRecipeTest();
	    firefox.logoutTest();
	    firefox.quit();
	    firefox.summarise();
	    
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
