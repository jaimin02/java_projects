<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="getDMSSearchResult.size == 0  ">
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
			No Result Found.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
</s:if>

<s:else>

	<br>
	<!-- <div style="width: 100%; height: 265px; overflow-y: scroll;"> -->
	<div>
	<table class="datatable" width="95%">
		<thead>
			<tr>
				<th>#</th>
				<th>Attribute Name</th>
				<th>Attribute Value</th>
				<th>Project Name</th>
				<th>Node Name</th>
				<th>Region</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="getDMSSearchResult" id="workspaceSummaryDetails"
				status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td>${status.count }</td>
					<td><s:property value="attrName" /></td>
					<td><s:property value="attrValue" /></td>
					<td><a
						href="WorkspaceOpen.do?ws_id=<s:property value="workspaceId"/>"
						target="_blank" style=""> <s:property value="workspaceDesc" />
					</a></td>
					<td><s:property value="nodeDisplayName" /></td>
					<td></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</s:else>