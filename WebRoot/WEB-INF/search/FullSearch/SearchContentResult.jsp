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

<div style="width: 100%"><input type="hidden" name="output">
<s:if test="output == 'P'">
	<table id="jquery_datatable" class="display" cellspacing="0"
		cellpadding="0" border="0">
		<thead>
			<tr>
				<th align="left">Project Name</th>
				<th align="left">Project DetailReport</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="contentData" status="stat">
				<tr class="gradeA" align="left">

					<td><span style="display: none"><s:property
						value="workspaceDesc" /></span><a
						href="WorkspaceOpen.do?ws_id=<s:property value="workspaceid"/>">
					<s:property value="workspaceDesc" /></a></td>
					<td>Click to View Full Report</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:elseif test="output == 'N'">
	<table id="jquery_datatable" class="display" cellspacing="0"
		cellpadding="0" border="0">
		<thead>
			<tr>
				<th align="left">Project Name</th>
				<th align="left">Node Name</th>
				<th align="left">File Name</th>
				<th align="left">Modify By</th>
				<th align="left">Modify On</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="contentData" status="stat">
				<tr class="gradeA" align="left">
					<td><a
						href="WorkspaceOpen.do?ws_id=<s:property value="workspaceid"/>">
					<s:property value="workspaceDesc" /></a></td>
					<td><s:property value="nodeName" /></td>
					<td><a
						href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="fileFolderName"/>/<s:property value="fileName"/>"
						target="_blank" title="<s:property value="fileName"/>"><s:property
						value="fileName" /></a></td>
					<td><s:property value="modifyBy" /></td>
					<td><s:property value="modifyOn" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:elseif> <s:elseif test="output == 'C'">
	<table id="jquery_datatable" class="display" cellspacing="0"
		cellpadding="0" border="0">
		<thead>
			<tr>
				<th align="left">Project Name</th>
				<th align="left">Node Name</th>
				<th align="left">Comment Desc</th>
				<th align="left">Sender</th>
				<th align="left">Receiver</th>
				<th align="left">Modify On</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="contentData" status="stat">
				<tr class="gradeA" align="left">
					<td><a
						href="WorkspaceOpen.do?ws_id=<s:property value="workspaceid"/>">
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
					<td><s:property value="subjectDesc" /></td>
					<td><s:property value="senderUserName" /></td>
					<td><s:property value="receiverUserName" /></td>
					<td><s:property value="modifyOn" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:elseif> <s:elseif test="output == 'A'">
	
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
			<s:iterator value="contentData" status="stat">
				<tr class="gradeA" align="left">
					<td><a
						href="WorkspaceOpen.do?ws_id=<s:property value="workspaceid"/>">
					<s:property value="workspaceDesc" /></a></td>
					<td>
					
					<s:if
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
</s:elseif> <s:elseif test="output == 'F'">
	<table id="jquery_datatable" class="display" cellspacing="0"
		cellpadding="0" border="0">
		<thead>
			<tr>
				<th align="left">Project Name</th>
				<th align="left">Node Name</th>
				<th align="left">PDF Filename</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="contentData" status="stat">
				<tr class="gradeA" align="left">
					<td><a
						href="WorkspaceOpen.do?ws_id=<s:property value="workspaceid"/>">
					<s:property value="workspaceDesc" /></a></td>
					<td><s:property value="nodeName" /></td>
					<td><a
						href="openfile.do?fileWithPath=<s:property value="pdfFilePath"/>"
						target="_blank" title="<s:property value="pdfFileName"/>"> <s:property
						value="pdfFileName" /></a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:elseif></div>

</body>
</html>