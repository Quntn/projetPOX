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
<h1>Fichiers</h1>
<div>
<%
List<String> listNames = (List<String>) request.getAttribute("filenames");
for (String name : listNames) { 
	%>
	<div class='texte'><%=name%></div>
	<form method="post" action="${pageContext.request.contextPath}/renamefile">
	<input type="hidden" name="filename" value='<%=name%>' />
	<input type="submit" value="Renommer"/>
	</form>
	<form method="post" action="${pageContext.request.contextPath}/deletefile">
	<input type="hidden" name="filename" value='<%=name%>' />
	<input type="submit" value="Supprimer"/>
	</form>
	<%
		}
%>
</div>
<div style="padding:5px; color:red;font-style:italic;">
       ${errorMessage}
    </div>

    <form method="post" action="${pageContext.request.contextPath}/uploadfile"
        enctype="multipart/form-data">

        <br />
        <input type="file" name="file" multiple />
        <br />
       
        <br />
        <input type="submit" value="Upload" />
       	
    </form>
    <div>${message}</div>
</body>
</html>