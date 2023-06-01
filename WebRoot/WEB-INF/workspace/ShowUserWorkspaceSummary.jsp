<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />


</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<s:if test="workspaceSummaryDetails.size==0 ">
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No details available.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
</s:if>

<s:else>
	<%int srNo = 1; %>
	<br>
	<table class="datatable" width="90%">
		<thead>
			<tr>
				<th>#</th>
				<th>Project Type</th>
				<th>Project Name</th>
				<th>Remark</th>
				<s:if test="dossStatPresent == true">
					<th>Dossier Status</th>
				</s:if>
				<th>Client</th>
				<th>Region</th>
				<th>From Date</th>
				<th>To Date</th>


			</tr>
		</thead>
		<tbody>
			<s:iterator value="workspaceSummaryDetails"
				id="workspaceSummaryDetails" status="status">
				<s:if test="statusIndi !='D'">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td><%=srNo++ %></td>
						<td title='<s:property value="projectName"/>'><s:if
							test="projectName.length() > 10">
							<s:property value="projectName.substring(0,10)" />...
				    </s:if> <s:else>
							<s:property value="projectName" />
						</s:else></td>
						<td title='<s:property value="workSpaceDesc"/>'><a
							href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>"
							style=""> <s:if test="workSpaceDesc.length() > 40">
							<s:property value="workSpaceDesc.substring(0,40)" />...
						</s:if> <s:else>
							<s:property value="workSpaceDesc" />
						</s:else> </a></td>
						<td title='<s:property value="remark"/>'><s:if
							test="remark.length() > 40">
							<s:property value="remark.substring(0,40)" />...
				    </s:if> <s:else>
							<s:property value="remark" />
						</s:else></td>
						<s:if test="dossStatPresent == true">
							<td><s:property value="dossierStatus" /></td>
						</s:if>
						<td><s:property value="clientName" /></td>
						<td title='<s:property value="locationName"/>'><s:if
							test="locationName.length() > 10">
							<s:property value="locationName.substring(0,10)" />...
				     </s:if> <s:else>
							<s:property value="locationName" />
						</s:else></td>

						<td><s:date name="fromDate" format="MMM-dd-yyyy" /></td>
						<td><s:date name="toDate" format="MMM-dd-yyyy" /></td>
					</tr>
				</s:if>

			</s:iterator>
		</tbody>
	</table>
</s:else>


</body>
</html>
