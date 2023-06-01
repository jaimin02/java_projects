<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>

<s:head />


<SCRIPT LANGUAGE="JavaScript">

function printPage()
{
		window.print();
}

function temp()
{
		window.location.href = "UserRightsReport.do";
		return true;
}



</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<br />
<div align="center"><img
	src="images/Header_Images/Report/User_Rights_Report.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="ShowUserRightsReport">
	<br>
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td width="70%"><s:select list="getAllWorkSpace"
				name="workSpaceId" headerKey="-1"
				headerValue="Select Source Project " listKey="workSpaceId"
				listValue="workSpaceDesc">
			</s:select> <ajax:event
				ajaxRef="showNodeDtlForUserRights/getNodeDtlForUserRights" /></td>
		</tr>
	</table>
	<table>

		<tr>
			<td>
			<div id="showNodeAndUserDetail" align="center"></div>
			</td>
		</tr>
	</table>

	<ajax:enable />
</s:form></div>
</div>
</div>
</body>
</html>

