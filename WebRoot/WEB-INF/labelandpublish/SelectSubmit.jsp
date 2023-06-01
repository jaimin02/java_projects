<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
<div class="titlediv">Submission</div>

<div align="center"><s:form method="post" action="SubmissionList">
	<br>
	<table>
		<tr>
			<td class="title">&nbsp;&nbsp; Project Name :</td>
			<td><s:select list="getWorkspaceDetail" name="workSpaceId"
				listKey="workSpaceId" listValue="workSpaceDesc" headerKey="-1"
				headerValue="Select Project">
			</s:select></td>

			<td>&nbsp;&nbsp; <s:submit value="GO" cssClass="button"></s:submit>
			</td>

		</tr>
	</table>



</s:form></div>
</body>
</html>

