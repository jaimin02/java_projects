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
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->



<div class="titlediv">Project Versions Report</div>
<div align="left"><br>
<s:form action="ShowversionMaintenance" method="get">
	<br>
	<table>
		<tr>
			<td class="title">&nbsp;&nbsp; Project Name&nbsp;&nbsp;</td>
			<td><s:select list="getWorkSpace" name="workSpaceId"
				headerKey="-1" headerValue="Select Project Name"
				listKey="workSpaceId" listValue="workSpaceDesc">

			</s:select> <ajax:event ajaxRef="showActivityNameDtl/getActivityNameDtl" /></td>
		</tr>
	</table>
	<table>
		<tr>
			<td colspan="2">
			<div id="showActivityVersionReportDetail"></div>
			</td>
		</tr>

	</table>

	<ajax:enable />
</s:form></div>
</body>
</html>

