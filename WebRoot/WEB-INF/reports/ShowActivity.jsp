<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<s:head />

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<div id="showActivityDetail"><br>
<table>
	<tr>
		<td class="title">&nbsp;&nbsp; Activity Name&nbsp;&nbsp;</td>
		<td><s:select list="getActivityName" headerKey="-1"
			headerValue="Select Activity Project" listKey="nodeId"
			listValue="nodeDisplayName" name="nodeId" size="1">
		</s:select></td>
	</tr>

	<tr>
		<td class="title">&nbsp;&nbsp; User Name</td>
		<td><s:select list="getUserDtl" name="userId" headerKey="-1"
			headerValue="Select User Project" listKey="userId"
			listValue="loginName">
		</s:select></td>
	</tr>

	<tr>
		<td class="title">&nbsp;&nbsp; Report</td>
		<td><select name="reportId">
			<option value="-1">Select Report Title...</option>
			<option value="1">Comments on e-mail</option>
			<option value="2">File Version History</option>
			<option value="3">Audit Trail</option>
		</select></td>
	</tr>
</table>
</div>

<br>
<table align="center" width="100%">
	<tr>
		<td width="113px"></td>
		<td><s:submit value="View" cssClass="button"></s:submit></td>
	</tr>
</table>

</body>
</html>

