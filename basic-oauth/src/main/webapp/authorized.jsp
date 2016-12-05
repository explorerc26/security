<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OAuth Facebook Login</title>
</head>
<body style="text-align: center; margin: 0 auto;">
	<h1>Welcome ${userData["name"]}</h1>
	<div><b> You have successfully logged in from facebook</b></div>
	<div>Here is your profile pic </div>
	<img src='${userData["profilePic"]}' alt='facebook profile pic' />
	
</body>
</html>