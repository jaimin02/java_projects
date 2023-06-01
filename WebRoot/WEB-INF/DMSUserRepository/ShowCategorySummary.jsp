<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
</head>
<body>

<div id="showCategoryDiv" style="width: 900px;"><br>
<a class="button" style="text-decoration: none;"
	href="javascript:void(0);"
	onclick="openInEditMode('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','add');"
	title="Add Category Details">Add New Category</a> <br>
<br>
<div align="center" style="width: 900px; overflow: auto;"><s:if
	test="workspaceChildNodeDtls.size > 0">
	<table class="datatable" width="900px" align="center">
		<thead>
			<tr>
				<th>#</th>
				<th>Category Name</th>
				<th>Prefix</th>
				<th>Document</th>
				<th>Modify On</th>
				<th>Modify By</th>
				<th>Remarks</th>
				<th style="text-align: center">Edit</th>
				<th>History</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="workspaceChildNodeDtls" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="nodeName" /></td>
					<td><s:property value="folderName" /></td>
					<td align="center" valign="middle" style="text-align: center">
					<s:if
						test="latestNodeHistory.fileName != null && latestNodeHistory.fileName !='' && !latestNodeHistory.fileName.equalsIgnoreCase('No File')">
						<a title="<s:property value="latestNodeHistory.fileName"/>"
							href="openfile.do?fileWithPath=<s:property value="latestNodeHistory.baseWorkFolder"/><s:property value="latestNodeHistory.folderName"/>/<s:property value="latestNodeHistory.fileName"/>"
							target="_blank"> <s:if
							test="latestNodeHistory.fileName.contains(\".\")">
							<s:if
								test="latestNodeHistory.fileName.substring(latestNodeHistory.fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
								<img alt="Open File"
									src="<%=request.getContextPath()%>/js/jquery/tree/skin/pdf_icon_14x14.gif"
									style="border: none;">
							</s:if>
							<s:elseif
								test="latestNodeHistory.fileName.substring(latestNodeHistory.fileName.indexOf(\".\")+1).equalsIgnoreCase('doc') || latestNodeHistory.fileName.substring(latestNodeHistory.fileName.indexOf(\".\")+1).equalsIgnoreCase('docx') ">
								<img alt="Open File"
									src="<%=request.getContextPath()%>/js/jquery/tree/skin/icon-doc-14x14.gif"
									style="border: none;">
							</s:elseif>
							<s:else>
								<img alt="Open File"
									src="<%=request.getContextPath()%>/images/file.png"
									style="border: none;">
							</s:else>
						</s:if> <s:else>
							<img alt="Open File"
								src="<%=request.getContextPath()%>/images/file.png"
								style="border: none;">
						</s:else> </a>
					</s:if> <s:else>-</s:else></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="userName" /></td>
					<td><s:property value="remark" /></td>
					<td>
					<div align="center"><a href="javascript:void(0);"
						onclick="openInEditMode('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','edit');"
						title="Edit Category Details"> <img border="0px" alt="Edit"
						src="images/Common/edit.png" height="18px" width="18px"> </a></div>
					</td>
					<td>
					<div align="center"><a href="javascript:void(0);"
						onclick="showHistory('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>');"
						title="Show History"> <img border="0px" alt="Show History"
						src="images/Common/history.png" height="18px" width="18px">
					</a></div>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if></div>
</div>
</body>
</html>