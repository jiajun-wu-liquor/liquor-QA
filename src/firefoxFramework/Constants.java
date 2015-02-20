/* Documentation
 * 1. testing.xml: describes which functions are called in parallel. Calls firefox class methods
 * 2. firefoxTest class: the "main" class.
 * 		Where the windows-specific configurations are set.
 * 		eg. browser profile is set here before initialising test.
 * 3. the various Test procedure classes: SignupTest, LoginTest, PublishTest, SaveRecipeTest
 * 		Note: 	logging-in in PublishTest and SaveRecipeTest are from back-end (/wp-admin). 
 * 				Not same as front-end login in LoginTest (sign-in button in home page).
 * 4. FirefoxBrowser class: an ill-named class due to legacy-burden. 
 * 		Parent class all tests inherit from, contains essential common methods.
 * 5. Constants interface: Contains all (most) constants used. 
 * 		Used as a one-stop place to easily find and configure settings on what site domain to test etc.
 */

/* Needed frameworks to setup
 * - Selenium (for functional testings. Other software include phantomJS, a headless testing framework based on javascript)
 * - Git to retrieve pre-existing code from JJ<jj@liquor.com>
 * - Java Developer's Kit (JDK) + IDE (Eclipse recommended for easy downloading of other frameworks)
 * - TestNG (for parallel running of several tests)
 * - AutoAuth
 * - Maybe selenium grid
 * - If using ChromeDriver (a separate chrome driver .exe that connects selenium to chrome. NOT same as org.selenium.ChromeDriver class)
 * 
 * Preps Needed
 * - built path set up to include Java JRE libraries
 * - built path set up to include external libraries like selenium JARs
 * - If using ChromeDriver, set up a configConstant file to locate chrome driver .exe
 * 
 * Wordpress preps
 * - Ensure Ghost users are created
 * - Post are created BY ghost for each editor's template
 */

/* To test,
 * 1. Set TEST_DOMAIN in Constants class. Options availablefor assigning that String: stg - "stg." / dev - "dev." / liquor.com - ""
 * 2. Right-click testing.xml, run-as..., 1 TestNG Suite
 */

package firefoxFramework;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
	
		// Wordpress 
		public String TEST_DOMAIN = "dev.";
		public String TEST_THEME = TEST_DOMAIN == "dev." ? "liquor2015" : "liquor";
		
		
		// Wordpress post_id
		public enum PostType {
			ARTICLE,RECIPE,BRAND,BAR,EXPRESSION,CELEBRATE,GUIDE,BRANDCHILD,HUB,MOSAIC,SERIES,SLIDESHOW,SPIRIT;
		}
		
		int[] stgPostID = new int[]{42008,42010,42013,42014,42016,42017,42026,42029,42031,42032,42033,42034};
		int[] devPostID = new int[]{42090,42093};
		int[] ldcPostID = new int[]{1,2};
		
		public static Map<String, String> liqourOldSelectorNames = new HashMap<String, String>()
				{{
					put("login_button", "ldc-user-login");
					put("article_header", "//h1[@class='entry-title']");
					put("article_content", "//div[@class='entry-content']/p");
					put("signup_button", "ldc-user-join button"); //class
					put("save_recipe_button", "//div[@id='save-bookmark-button']");
					put("unsave_recipe_button", "//div{@id='bookmark-saved-button']");
					put("logout_button", "//div[@class='ldc-user-log-out']");
				}};
				
		public static Map<String, String> liquor2015SelectorNames = new HashMap<String, String>()
				{{	
					put("login_button", "nav-user-login");
					put("article_header", "//div[@class='row head-row text-center']//div//h1");
					put("article_content", "//div[@class='article-content']//p");
					put("signup_button", "nav-user-join"); //class
					put("save_recipe_button", "//button[@class='btn btn-default btn-save']");
					put("unsave_recipe_button", "//span[@class='message']");
					put("logout_button", "//ul[@id='wp-admin-bar-user-actions']/li[@id='wp-admin-bar-logout']");
				}};
		
		public String TEST_HOMEPAGE = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/";
		public String TEST_SAVERECIPE_RECIPEURL = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/recipes/scotch-and-soda/";
		public String TEST_SAVERECIPE_SAVEDPAGE = "https://liquor:negroni@" + TEST_DOMAIN + "liquor.com/user-profile/?tab=recipes/";
		public String TEST_PUBLISH_POSTURL_1 = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/wp-admin/post.php?post=";
		public String TEST_PUBLISH_POSTURL_2 = "&action=edit/";
		
		
		int windowWidth = (int)(1920/3/1.3);
		int windowHeight = (int)(1080/3);
		
		// Wordpress username password
			// Deprecated
		public String LOGIN_USER_FF = "ghost5";
		public String LOGIN_USER_CH = "ghost6";
		public String LOGIN_USER_SA = "ghost7";
		
		public String LOGIN_USER_GENERAL = "ghost5";
		public String LOGIN_USER_SAVERECIPE = "ghost6";
		public String LOGIN_USER_PUBLISH = "ldc.ghost-inspector";
		
		public String LOGIN_PASS = "ghostghost";
		
			// Deprecated
		public String LOGIN_PASS_FF = "ghostghost";
		public String LOGIN_PASS_CH = "ghostghost";
		public String LOGIN_PASS_SA = "ghostghost";
		
		
		// Log messages
		public String LOG_TEST_BROWSER_START = "Beginning functional test on ";
		
		public String LOG_TEST_LOGIN_PASS = "• Login - Successful";
		public String LOG_TEST_LOGOUT_PASS =  "• Logout - Successful";
		
		public String LOG_TEST_SUMMARY_PAGES = " pages were loaded\n";
}
