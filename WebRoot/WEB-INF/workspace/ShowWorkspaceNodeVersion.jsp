<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<s:head />
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>

<div align="center" class="headercls">Version Maintenance</div>
<s:if test="workspaceNodeVersionDetail.size == 0">

	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No Match Found In Your WorkSpace.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>

</s:if>
<s:else>
	<div align="center"><br>
	<s:form action="SaveWorkspaceNodeVersion" method="post">

		<br>
		<table id="clientTable" align="center" width="95%" class="datatable">
			<thead>
				<tr>
					<th>#</th>
					<th>Version</th>
					<th>Modified On</th>
					<th>Modified By</th>
					<th>Version Status</th>

				</tr>
			</thead>
			<tbody>

				<s:hidden value="statusIndi" id="statusIndi" />
				<s:iterator value="workspaceNodeVersionDetail"
					id="workspaceNodeVersionDetail" status="status">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

						<td><input type="radio" name="selectedRadiobtnVersion"
							value="<s:property value="fileVersionId"/>" /></td>
						<td><s:property value="userDefineVersionId" /></td>

						<td><s:date name="executedOn" format="MMM-dd-yyyy" /></td>

						<td><s:property value="userName" /></td>

						<td><s:hidden value="published" id="published" /> <s:if
							test="statusIndi == 'Y'">Published</s:if> <s:else>
							<s:hidden value="lastClosed" id="lastClosed" />
							<s:if test="lastClosed == 'Y'">Last Closed</s:if>
							<s:if test="lastClosed == 'N'">Closed</s:if>
						</s:else></td>

					</tr>

				</s:iterator>
			</tbody>
		</table>
		<br>
		<table width="95%">

			<tr align="center">
				<td><s:submit value="Revert To Older Version" cssClass="button" />
				</td>
			</tr>

		</table>

		<s:hidden name="workspaceId"></s:hidden>
		<s:hidden name="nodeId"></s:hidden>
	</s:form></div>
</s:else>
</body>
</html>

