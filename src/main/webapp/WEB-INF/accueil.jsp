<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stockage fichier</title>


</head>
<body>
	<h1>Files</h1>


	<form method="post"
		action="${pageContext.request.contextPath}/uploadfileserver"
		enctype="multipart/form-data">
		<br />
		<h3>Upload to Server</h3>
		<input type="file" name="file" multiple /> <br /> <br /> <input
			type="submit" value="Upload" />

	</form>

	<form method="post"
		action="${pageContext.request.contextPath}/uploadfiledatabase"
		enctype="multipart/form-data">
		<br />
		<h3>Upload to Database</h3>
		<input type="file" name="file" multiple /> <br /> <br /> <input
			type="submit" value="Upload" />

	</form>
	
	<form method="post"
		action="${pageContext.request.contextPath}/uploadfiledropbox"
		enctype="multipart/form-data">
		<br />	
		<h3>Upload to Dropbox</h3>
		<input type="file" name="file" multiple /> <br /> <br /> <input
			type="submit" value="Upload" />
		
	</form>

	<div>${message}</div>
</body>
</html>