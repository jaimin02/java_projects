<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>
<html>
<head>
<s:head />
<script type="text/javascript">
	$(document).ready(function() {
		$('#jquery_datatable').dataTable( {
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		} );
	});
</script>

</head>

<body>

<div style="width: 100%">
<table id="jquery_datatable" class="display" cellspacing="0"
	cellpadding="0" border="0">
	<thead>
		<tr>
			<th align="left">Project Name</th>
			<th align="left">Node Name</th>
			<th align="left">Attribute Name</th>
			<th align="left">Attribute Value</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="fullSearchData" status="stat">

			<tr class="gradeA" align="left">

				<td><span style="display: none"><s:property
					value="workspaceDesc" /></span><a
					href="WorkspaceOpen.do?ws_id=<s:property value="workspaceId"/>">
				<s:property value="workspaceDesc" /></a></td>
				<td><s:if
					test="fileName == null || fileName == '' || fileName == 'No File' ">
					<label title="No File Found"><s:property value="nodeName" /></label>
				</s:if> <s:else>
					<a
						href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="fileFolderName"/>/<s:property value="fileName"/>"
						target="_blank" title="<s:property value="fileName"/>"><s:property
						value="nodeName" /></a>
				</s:else></td>
				<td><s:property value="attrName" /></td>
				<td><s:property value="attrValue" /></td>
			</tr>
		</s:iterator>

	</tbody>
</table>
</div>

</body>
</html>