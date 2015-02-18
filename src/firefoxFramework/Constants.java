package firefoxFramework;

public interface Constants {
	
	// Wordpress 
	public String TEST_DOMAIN = "stg.";
	
	// Wordpress post_id
	public enum PostType {
		ARTICLE,RECIPE,BRAND,BAR,EXPRESSION,CELEBRATE,GUIDE,BRANDCHILD,HUB,MOSAIC,SERIES,SLIDESHOW,SPIRIT;
	}
	
	int[] stgPostID = new int[]{42008,42010,42013,42014,42016,42017,42026,42029,42031,42032,42033,42034};
	int[] devPostID = new int[]{1,2};
	int[] ldcPostID = new int[]{1,2};
	
	public String TEST_HOMEPAGE = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com";
	public String TEST_SAVERECIPE_RECIPEURL = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/recipes/scotch-and-soda/";
	public String TEST_SAVERECIPE_SAVEDPAGE = "http://liquor:negroni@" + TEST_DOMAIN + "liquor.com/user-profile/?tab=recipes";
	public String TEST_PUBLISH_POSTURL_1 = "http://" + TEST_DOMAIN + "liquor.com/wp-admin/post.php?post=";
	public String TEST_PUBLISH_POSTURL_2 = "&action=edit";
	
	
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
	public String LOG_TEST_SAVERECIPE_PASS = "• Save recipe - Successful";
	public String LOG_TEST_LOGOUT_PASS =  "• Logout - Successful";
	public String LOG_TEST_SIGNUP_PASS = "• Signup - Successful";
	
	public String LOG_TEST_SUMMARY_PAGES = " pages were loaded\n";
}
