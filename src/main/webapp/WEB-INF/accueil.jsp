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
<h1>Stockage de fichiers dans le serveur</h1>
<div>
<%
List<String> listNames = (List<String>) request.getAttribute("filenames");
for (String name : listNames) { 
	%>
	<div class='texte'><%=name%></div>

	<%
		}
%>
</div>
<div style="padding:5px; color:red;font-style:italic;">
       ${errorMessage}
    </div>
    
    <h2>Upload Files</h2>
 
    <form method="post" action="${pageContext.request.contextPath}/uploadfile"
        enctype="multipart/form-data">
        
        Select file to upload:
        <br />
        <input type="file" name="file"  />
        <br />
       
        <br />
        <input type="submit" value="Upload" />
       	
    </form>
    <div>${message}</div>
</body>
</html>