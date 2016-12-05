package com.poc.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AccessToken {

	public AccessToken(String code){
		StringBuffer buffer = null;
		try {
			String accessTokenUrl =OauthUtil.OAUTH_ACCESS_TOKEN_URL+ OauthUtil.APP_ID+ "&redirect_uri="+URLEncoder.encode(OauthUtil.REDIRECT_URI_FROM_FACEBOOK,"UTF-8")
					+ "&client_secret="+ OauthUtil.APP_SECRET_KEY + "&code=" + code;

			URLConnection urlConnection = OauthUtil.getUrlConnection(accessTokenUrl);
			BufferedReader inpBuffer;
			inpBuffer = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String inputLine;
			buffer = new StringBuffer();
			while ((inputLine = inpBuffer.readLine()) != null)
				buffer.append(inputLine + "\n");
			inpBuffer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to connect with Facebook " + e);
		}
		accessToken = buffer.toString();
		if (accessToken.startsWith("{")) {
			throw new RuntimeException("invalid access token : "
					+ accessToken);
		}
	}
	
	private String accessToken = null;

	public String getAccessToken() {
		if(accessToken == null ){
			throw new RuntimeException("access token is null ");
		}
		return accessToken;
	}

}
