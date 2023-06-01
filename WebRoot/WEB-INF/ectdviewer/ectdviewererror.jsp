<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>

<style>
.error {
	color: red;
	text-align: center;
	font-size: 18px;
}

ul li {
	list-style: none;
}
</style>
</head>
<body>
<center><span class="error"> <s:actionerror /> <s:fielderror />
</span> <input type="button" onclick="window.location='ViewAllDossier.do'"
	value="BACK" /></center>
</body>

</html>