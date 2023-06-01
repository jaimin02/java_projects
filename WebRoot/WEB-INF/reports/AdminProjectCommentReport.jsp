<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<s:head />

<script type="text/javascript">

	function validate()
	{
		
	}
</script>
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->



<div class="titlediv">Project Trace Report</div>
<div align="left"><br>
<s:form action="GenerateActivityCommentReport" method="get">
	<br>
	<table>
		<tr>
			<td class="title">&nbsp;&nbsp;&nbsp; Project Name&nbsp;&nbsp;</td>
			<td><s:select list="getWorkSpace" name="workSpaceId"
				headerKey="-1" headerValue="Select Project Name"
				listKey="workSpaceId" listValue="workSpaceDesc">

			</s:select> <ajax:event ajaxRef="showActivityDtl/getActivityDtl" /></td>
		</tr>
	</table>
	<table>

		<tr>
			<td colspan="2">
			<div id="showActivityDetail"></div>
			</td>
		</tr>

	</table>

	<ajax:enable />
</s:form></div>
</body>
</html>

