<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<s:head />
</head>
<body>
<table class="datatable" style="background: transparent;" width="100%">
	<tr>
		<th>#</th>
		<th>Node Name</th>
		<th>Node Title</th>
		<th>Site Identifier</th>
		<th>Remove</th>
	</tr>
	<s:iterator value="NodeDetail" status="status">
		<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			<td>${status.count}</td>
			<td><s:property value="nodeName" /></td>
			<td><s:property value="nodeDisplayName" /></td>
			<td><s:property value="remark" /></td>
			<td><s:a href="#" onclick="">Remove</s:a></td>
		</tr>
	</s:iterator>
</table>


</body>
</html>