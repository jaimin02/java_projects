<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
	function test(id) {
		alert(id);
	}
	function openlinkEdit(id,name) {
		
		var editLocationWindow = "EditLocationTest.do?LocationCode="+id+"&LocationName="+name;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=0,menubar=no,scrollbars=no,height=150,width=400,resizable=no,titlebar=no");
    	win3.moveTo(screen.availWidth/2-(400/2),screen.availHeight/2-(300/2));
    	return true;
	}
	function openlinkRevert(id,name) {
		
		var editLocationWindow = "DeleteLocationTest.do?LocationCode="+id+"&LocationName="+name;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=0,menubar=no,scrollbars=no,height=150,width=400,resizable=no,titlebar=no");
    	win3.moveTo(screen.availWidth/2-(400/2),screen.availHeight/2-(300/2));
    	return true;
	}
	function authenticate()
	{
			if(confirm("Do you want to delete this country"))
			{
				return true;
			}
			return false;
	}
	function validate()
	{
		lname=document.getElementById("LocationName").value;
	
		if(lname=="")
		{
			alert("Please Insert Location Name...");
			document.getElementById("LocationName").style.backgroundColor="#FFE6F7"; 
			document.getElementById("LocationName").focus();
			return false;
		}
		return true;
	}
</SCRIPT>
<STYLE type="text/css">
.location {
	background-color: gray;
	font-size: small;
	border: 1px solid;
	width: 60%;
	text-align: center;
}

.location th {
	color: black;
}

.location td {
	
}

.location tr:nth-child(even) {
	background: #015C7A;
	color: white;
	border: 1px solid;
}

.location tr:nth-child(odd) {
	background: #FFF;
	color: black;
	border: 1px solid;
}

.location tbody tr:hover {
	background-color: #0F6F8F;
	color: white;
	border: 2px solid red;
}

.editLink {
	color: red;
}

.btnSubmit {
	padding: 5px;
	background-color: #102066;
	color: white;
	border: 1px solid;
}
</STYLE>
</head>
<body>

<div align="center"><img
	src="images/Header_Images/Master/Location_Master.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div align="left"><br></br>
<s:form action="AddLocationtTest.do" method="post">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="locationName">Location
			Name</td>
			<td><s:textfield name="LocationName" id="LocationName"></s:textfield>&nbsp;
			<s:submit name="submitBtn" value=" Add " cssClass="button"
				onclick="return validate();" /></td>
		</tr>
	</table>
</s:form> <br>

</br>
<center>
<table width="100%" class="location">
	<thead>
		<tr>
			<th>Sr. No.</th>
			<th>Location Code</th>
			<th>Location Name</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator status="vListStatus" value="vList">
			<tr>

				<td><s:property value="#vListStatus.count" /></td>
				<td><s:property value="CountryCode" /></td>
				<td><s:property value="CountryName" /></td>
				<td><a href="javascript:void(0);"
					onclick="openlinkEdit('<s:property value="CountryCode"/>','<s:property value="CountryName"/>');"
					title="Edit"> <img border="0px" alt="Edit"
					src="images/edit.gif" height="18px" width="18px"> </a></td>
				<td><a title="Delete"
					href="DeleteLocationTest.do?LocationCode=<s:property value="CountryCode" />&LocationName=<s:property value="CountryName" />"
					);" onclick="return authenticate();"> <img border="0px"
					alt="Activate" src="images/Common/delete.png" height="18px"
					width="18px"> </a></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</center>
</body>
</html>