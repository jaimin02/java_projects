<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Location</title>
<STYLE type="text/css">
.location {
	margin-top: 13px;
	padding: 10px;
	color: white;
	background-color: #015C7A;
	font-size: small;
	border: 1px solid;
	width: 100%;
}

.btnSubmit {
	padding: 5px;
	background-color: #102066;
	color: white;
	border: 1px solid;
	margin-top: 20px;
}
</STYLE>
</head>
<body>
<form action="EditLocationTestSave.do" method="post"><s:hidden
	name="LocationCode" />
<table class="location">
	<tr>
		<td>Old Location Name</td>
		<td>${LocationName}</td>

	</tr>
	<tr>

		<td>New Location Name</td>
		<td><s:textfield name="LocationName"></s:textfield></td>

	</tr>
	<tr>
		<th colspan="2"><s:submit value="Update" cssClass="btnSubmit"></s:submit></th>
	</tr>
</table>
</form>
</body>
</html>