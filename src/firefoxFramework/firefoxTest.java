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

/* Temp dump

package firefoxFramework;

public interface configConstants {
	public String CHROMEDRIVER_PATH = "C:////Users//david//workspace//LDC//addons//chromedriver.exe";
	
	
	public String TEST_SAVERECIPE_RECIPEURL = "http://liquor.com/recipes/bitters-sweet-barrel/";
	
	
	public String LOGIN_USER_FF = "ghost5";
	public String LOGIN_USER_CH = "ghost6";
	public String LOGIN_USER_SA = "ghost7";
	public String LOGIN_PASS_FF = "ghostghost";
	public String LOGIN_PASS_CH = "ghostghost";
	public String LOGIN_PASS_SA = "ghostghost";
	
	
	public String LOG_TEST_BROWSER_START = "Beginning functional test on ";
	
	public String LOG_TEST_LOGIN_PASS = " login - Successful";
	public String LOG_TEST_SAVERECIPE_PASS = " save recipe - Successful";
	public String LOG_TEST_LOGOUT_PASS =  "logout - Successful";
	
	public String LOG_TEST_SUMMARY_PAGES = "pages were loaded\n";
}

*/
