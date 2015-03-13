/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Documentation. Open up to read.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

/* =========================
 * Overview of Structure
 * =========================
 * 1. testing.xml: describes which functions are called in parallel. Calls firefox class methods
 * 2. firefoxTest class: the "main" class.
 * 		Where the windows-specific configurations are set.
 * 		eg. browser profile is set here before initialising test.
 * 3. the various Test procedure classes: SignupTest, LoginTest, PublishTest, SaveRecipeTest
 * 		Note: 	logging-in in PublishTest and SaveRecipeTest are from back-end (/wp-admin). 
 * 				Not same as front-end login in LoginTest (sign-in button in home page).
 * 4. FunctionalTest class: Parent class all tests inherit from. Contains essential common methods.
 * 5. Constants interface: Contains all (most) constants used. 
 * 		Used as a one-stop place to easily find and configure settings on what site domain to test etc.
 */

/* =========================
 * Setting up
 * =========================
 * Frameworks needed
 * - Selenium (for functional testings. Other software include phantomJS, a headless testing framework based on javascript)
 * - Git to retrieve pre-existing code from JJ<jj@liquor.com>
 * - Java Developer's Kit (JDK) + IDE (Eclipse recommended for easy downloading of other frameworks)
 * - TestNG (for parallel running of several tests)
 * - Maybe selenium grid
 * - If using ChromeDriver (a separate chrome driver .exe that connects selenium to chrome. NOT same as org.selenium.ChromeDriver class)
 * 
 * Preps Needed - on testing machine
 * - built path set up to include Java JRE libraries
 * - built path set up to include external libraries like selenium JARs
 * - If using ChromeDriver, set up a configConstant file to locate chrome driver .exe
 * 
 * Preps Needed - on Wordpress
 * - Ensure Ghost users are created
 * - Post are created BY ghost for each editor's template
 */

/* =========================
 * To test
 * =========================
 * 1. Set Constant class constants
 * - TEST_DOMAIN. Options available: 
 * 		stg => "stg.", 
 * 		dev => "dev.",
 * 		liquor.com => ""
 * - TEST_THEME. Just check that the themes fit their respective domains.
 * - AUTH_USER & AUTH_PASS. For "dev." and "stg.", this is used to set the authentication username and password.
 * 2. Right-click testing.xml, run-as..., 1 TestNG Suite
 */

package firefoxFramework;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
	
		// Domain and theme settings
		public String TEST_DOMAIN = "stg."; // "dev." or "stg." or ""
		public String TEST_THEME = /*TEST_DOMAIN == "" ?*/ "liquor2015" ;//: "liquor";
		
		// • set false if don't want to waste Ghost Inspector credits
		public boolean DO_BAT_TEST = false;
		// • set false if dont want to create unnecessary new dummy users
		public boolean DO_SIGNUP_TEST = true;
		
		public String AUTH_USER = "liquor";
		public String AUTH_PASS = "negroni";
		
		public String TEST_HOMEPAGE = "http://" + AUTH_USER + ":" + AUTH_PASS + "@" + TEST_DOMAIN + "liquor.com/";
		public String TEST_SAVERECIPE_RECIPEURL = "http://" + AUTH_USER + ":" + AUTH_PASS + "@" + TEST_DOMAIN + "liquor.com/recipes/scotch-and-soda/";
		public String TEST_SAVERECIPE_SAVEDPAGE = "http://" + AUTH_USER + ":" + AUTH_PASS + "@" + TEST_DOMAIN + "liquor.com/user-profile/?tab=recipes/";
		public String TEST_PUBLISH_POSTURL_1 = "http://" + AUTH_USER + ":" + AUTH_PASS + "@" + TEST_DOMAIN + "liquor.com/wp-admin/post.php?post=";
		public String TEST_PUBLISH_POSTURL_2 = "&action=edit";
		public String BAT_SUITE_USER_URL = TEST_THEME == "liquor2015" ? "https://app.ghostinspector.com/suites/54dd3853c918b9a14332f45c" : "https://app.ghostinspector.com/suites/54b9665f3c3c5403686aa177";
		
		// Wordpress - Page template's element identifiers.
			// • Old liquor theme
			public static Map<String, String> liqourOldSelectorNames = new HashMap<String, String>()
				{{
					
					put("article_header", "//h1[@class='entry-title']");
					put("article_content", "//div[@class='entry-content']/p");
					
					put("save_recipe_button", "//div[@id='save-bookmark-button']");
					put("unsave_recipe_button", "//div[@id='bookmark-saved-button']");
					
					put("signup_button", "ldc-user-join"); //class
					put("login_button", "ldc-user-login");
					put("logout_button", "//div[@class='ldc-user-log-out']");
				}};
		
				// • New liquor 2015 theme
				public static Map<String, String> liquor2015SelectorNames = new HashMap<String, String>()
				{{	
					
					put("article_header", "//div[@class='row head-row text-center']//div//h1");
					put("article_content", "//div[@class='article-content']//p");
					
					put("recipe_header", "//div[@class='row head-row text-center']/div/h1");
					put("recipe_img", "//div[@id='img-hero']");  
					put("recipe_about", "//div[@class='col-xs-12 fake-row x-recipe-about']/span/p");
					put("recipe_ingredient", "//div[@class='col-xs-9 x-recipe-ingredient']");
					put("recipe_unit", "//div[@class='row x-recipe-unit']/div");
					put("recipe_glasstype", "//div[@class='col-xs-9 x-recipe-glasstype']");
					put("recipe_prep", "//div[@class='row x-recipe-prep']/p");
					put("recipe_img", "//div[@id='img-hero']/img");
					
					put("save_recipe_button", "//button[@class='btn btn-default btn-save']");
					put("unsave_recipe_button", "//span[@class='message']");
					
					put("signup_button", "nav-user-join"); //class
					put("login_button", "nav-user-login");
					put("logout_button", "//ul[@id='wp-admin-bar-user-actions']/li[@id='wp-admin-bar-logout']");
				}};
		
		// Database - Posts IDs, Login usernames
		public enum PostType {
			ARTICLE,RECIPE,BRAND,BAR,EXPRESSION,CELEBRATE,GUIDE,BRANDCHILD,HUB,MOSAIC,SERIES,SLIDESHOW,SPIRIT;
		}
		
			// • post_ids for each domain
			int[] stgPostID = new int[]{42008,42010,42013,42014,42016,42017,42026,42029,42031,42032,42033,42034};
			int[] devPostID = new int[]{42090,42093};
			int[] ldcPostID = new int[]{1,2};
			
			// • username and password
			public String LOGIN_USER_GENERAL = "ghost5";
			public String LOGIN_USER_SAVERECIPE = "ghost6";
			public String LOGIN_USER_PUBLISH = "ldc.ghost-inspector";
			
			public String LOGIN_PASS = "ghostghost";
		
		// System settings - window resizing
		int windowWidth = (int)(1920/3/1.3);
		int windowHeight = (int)(900/3);
		
		int WAIT_TIME_LOAD_MILLISEC = 2500;
		int WAIT_TIME_SIGNUP_MILLISEC = 2000;
}
