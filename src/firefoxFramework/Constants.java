package firefoxFramework;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
	
		// Wordpress 
		public String TEST_DOMAIN = "dev.";
		/* Ensure:
		 * - Ghost users are created
		 * - Post are created BY ghost for each editor's template
		 */
		
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
				}};
				
		public static Map<String, String> liquor2015SelectorNames = new HashMap<String, String>()
				{{	
					put("login_button", "nav-user-login");
					put("article_header", "//div[@class='row head-row text-center']//div//h1");
					put("article_content", "//div[@class='article-content']//p");
					put("signup_button", "nav-user-join"); //class
					put("save_recipe_button", "//button[@class='btn btn-default btn-save']");
					put("unsave_recipe_button", "//span[@class='message']");
				}};
		
		public String TEST_HOMEPAGE = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com";
		public String TEST_SAVERECIPE_RECIPEURL = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/recipes/scotch-and-soda/";
		public String TEST_SAVERECIPE_SAVEDPAGE = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/user-profile/?tab=recipes";
		public String TEST_PUBLISH_POSTURL_1 = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/wp-admin/post.php?post=";
		public String TEST_PUBLISH_POSTURL_2 = "&action=edit";
		
		
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
