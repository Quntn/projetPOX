<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Upload files</title>
</head>
<body>
 
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