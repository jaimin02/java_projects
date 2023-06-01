<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
</head>
<body>
<div align="center" style="width: 900px;"><s:if
	test="dtoContentSearch.size > 0">
	<h3>Advanced Search Result</h3>
	<table align="center" style="overflow: auto;" width="100%"
		border="1px solid" class="datatable">
		<thead>
			<tr align="left">
				<th>No.</th>
				<th>Project Name</th>
				<th>Node Name</th>
				<th>Node Display Name</th>
				<th>Attribute Name</th>
				<th>Attribute Value</th>
			</tr>
		</thead>
		<tbody>
			<%int srNo = 1; %>
			<s:iterator value="dtoContentSearch" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><%=srNo++ %></td>
					<td><s:property value="workspaceDesc" /></td>
					<td><s:property value="nodeName" /></td>
					<td><s:property value="nodeDisplayName" /></td>
					<td><s:property value="attrName" /></td>
					<td><s:property value="attrValue" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:else>
	<h3 style="text-decoration: blink; color: red;">No Data Found OR
	Invalid Query Submitted.</h3>
</s:else></div>
</body>
</html>