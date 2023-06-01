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

<div id="showNodeAndUserDetail" style="width: 950px; position: static;">

<br>

<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Activity Name</td>
		<td align="left"><s:select list="getNodeDetail" name="nodeId"
			headerKey="-1" headerValue="Select Activity Project "
			listKey="nodeId" listValue="nodeDisplayName">
		</s:select></td>
	</tr>

	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">User Name</td>
		<td align="left"><s:select list="getUserDtl" headerKey="-1"
			headerValue="Select User Project " name="userId" listKey="userCode"
			listValue="loginName">
		</s:select></td>
	</tr>

	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Sort On</td>
		<td align="left"><select name="sortOn">
			<option value="WorkspaceId">ProjectName</option>
			<option value="NodeId">NodeDisplayName</option>
			<option value="UserName">UserName</option>

		</select></td>
	</tr>

	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Sort By</td>
		<td align="left"><select name="sortBy">
			<option value="ASC">Ascending</option>
			<option value="DESC">Descending</option>
		</select></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td align="left"><input type="button" value="Show Report"
			class="button" /> <ajax:event
			ajaxRef="showUserRightsReport/getUserRightsReport" /></td>
	</tr>

</table>
<div id="showUserRightsReportDiv"
	style="width: 100%; padding-top: 0px; margin-top: 0px;" align="center">

</div>
</div>

</body>
</html>