package com.poc.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OauthServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		
		if (code == null || code.equals("")) {
			throw new RuntimeException("unable to retrieve access token");
		}
		AccessToken accessToken = new AccessToken(code);
		String token = accessToken.getAccessToken();
		String userProfileJson = OauthUtil.getUserProfile(token);
		Map<String, String> userData = OauthUtil.getUserData(userProfileJson);	
		request.setAttribute("userData", userData);
		RequestDispatcher rd = request.getRequestDispatcher("authorized.jsp");
        rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,  response);
	}
}
