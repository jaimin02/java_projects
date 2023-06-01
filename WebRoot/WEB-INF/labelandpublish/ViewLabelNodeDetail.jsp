<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.docmgmt.dto.DTOInternalLabelMst"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<html>
<head>
<s:head />


</head>


<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:if test="getViewNodeDetail.size==0">

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
			There are no APPROVED files in the project.<br>
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

				<th>SrNo</th>
				<th>Project Name</th>
				<th>Node Name</th>
				<th>Expected File Name</th>
				<th>File Version</th>
				<th>Actual File Name</th>

			</tr>
		</thead>
		<tbody>

			<s:iterator status="status" value="getViewNodeDetail">
				<tr class="even">
					<td>${status.count}</td>
					<td><s:property value="workspaceDesc" /></td>
					<td><s:property value="nodeDisplayName" /></td>
					<td><s:property value="folderName" /></td>
					<td><s:if test="fileVersion == null">
						<center>--</center>
					</s:if> <s:else>
						<s:property value="fileVersion" />
					</s:else></td>
					<td><s:if test="fileName == null">
						<center>No File</center>
					</s:if> <s:else>
						<a
							href="openfile.do?workSpaceId=<s:property value="workspaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>"
							target="_blank"> <s:property value="fileName" /> </a>
					</s:else></td>
				</tr>


			</s:iterator>

		</tbody>
	</table>
</s:else>

</body>
</html>
