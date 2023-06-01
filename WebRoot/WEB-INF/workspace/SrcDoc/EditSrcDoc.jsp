<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
</head>
<body>
<s:if test="nodeDisplayName != null">
	<div class="headercls" style="width: 100%"><font color="white"
		size="2"> <b>${nodeDisplayName}</b> </font></div>
</s:if>
<div class="errordiv" align="center"
	style="color: green; font-size: x-small;"><s:actionmessage /></div>

<s:if test="tempFilePath == 'conflict'">
	<br>
	<br>
	<br>
	<div align="center"><input type="button" class="button"
		value="Back" onclick="javascript:history.go(-1);"></div>

</s:if>
<s:else>
	<s:form action="saveSrcDoc" name="EditSrcDocForm" method="post">
		<div>
		<table width="100%">
			<tr>
				<td style="padding-left: 10px;" width="50%"><s:submit
					name="btnSub" cssClass="button" value="Done"></s:submit></td>
				<td style="padding-right: 10px;" align="right"><s:submit
					name="btnSub" cssClass="button" value="Undo"></s:submit></td>
			</tr>
		</table>

		<s:hidden name="tempFilePath"></s:hidden> <s:hidden name="fullDocPath"></s:hidden>
		<s:hidden name="workspaceId"></s:hidden> <s:hidden name="nodeId"></s:hidden>
		</div>
		<iframe src="file:<s:property value="tempFilePath"/>" width="100%"
			height="90%"></iframe>

	</s:form>
</s:else>


</body>
</html>