package com.poc.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class OauthUtil {
	// tester account
	// facebook login : svmc26@gmail.com
	// password : oauthtester1
	public static final String APP_ID = "976458805746172";
	public static final String APP_SECRET_KEY = "11be7f2bfaee519cefdc39790759add1";
	public static final String OAUTH_USER_REDIRECT_URL = "http://www.facebook.com/dialog/oauth?client_id=";
	public static final String REDIRECT_URI_FROM_FACEBOOK = "http://localhost:9082/oauth-facebook-war/facebook-login";
	public static final String OAUTH_ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token?client_id=";
	public static final String OAUTH_ACCESS_DETAILS_URL = "https://graph.facebook.com/me";
	private static boolean useProxy = true;
	
	static String accessToken = "";
	public static String getOuthUrl(){
		String oauthUrl = "";
		try {
			oauthUrl = OAUTH_USER_REDIRECT_URL+ APP_ID + "&redirect_uri="+ URLEncoder.encode(REDIRECT_URI_FROM_FACEBOOK, "UTF-8")+ "&scope=email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return oauthUrl;
	}
	
	public static String getUserProfile(String accessToken){
		String profile = null;
		try {

			String facebookProfileUrl = OAUTH_ACCESS_DETAILS_URL+"?" + accessToken;
			URLConnection connection = getUrlConnection(facebookProfileUrl);
			BufferedReader inpBufReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = inpBufReader.readLine()) != null)
				b.append(inputLine + "\n");
			inpBufReader.close();
			profile = b.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		System.out.println(profile);
		return profile;
	}

	public static Map<String, String> getUserData(String profile) {
		Map<String, String> userData = new HashMap<String, String>();
		try {
			JSONObject json = new JSONObject(profile);
			userData.put("id", json.getString("id"));
			userData.put("name", json.getString("name"));
			userData.put("profilePic","https://graph.facebook.com/"+json.getString("id")+"/picture");
			if (json.has("email"))
				userData.put("email", json.getString("email"));
			if (json.has("gender"))
				userData.put("gender", json.getString("gender"));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return userData;
	}
	
	public static URLConnection getUrlConnection(String url) throws Exception{
		URL urlConnection = new URL(url);
		if(useProxy){
			return urlConnection.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("nyproxy", 80)));
		}
		else{
			return urlConnection.openConnection();
		}
		
	}

}
