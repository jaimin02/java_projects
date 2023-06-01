<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<html>
<head>
<s:head />


</head>


<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:if test="getViewDetail.size==0">
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
			No labels found for selected project.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>

</s:if>
<s:else>

	<br>
	<table width="95%" align="center" class="datatable">
		<thead>
			<tr>

				<th>Label No</th>
				<th>Label Id</th>
				<th>Remarks</th>
				<th>Modified By</th>
				<th>Modified On</th>
				<th>Approved Files</th>
				<th>Publish</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="getViewDetail" id="getViewDetail" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="labelNo" /></td>
					<td><s:property value="labelId" /></td>
					<td><s:property value="remark" /></td>
					<td><s:property value="userName" /></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><a href="#"
						onclick="view('<s:property value="workSpaceId"/>','<s:property value="labelNo"/>');">View</a>

					</td>
					<td><a
						href="WorkspaceSubmission.do?workSpaceId=<s:property value="workSpaceId" />&labelNo=<s:property value="labelNo" />&labelId=<s:property value="labelId" />">Publish</a>

					</td>
				</tr>
			</s:iterator>


		</tbody>
	</table>
</s:else>
</body>
</html>
