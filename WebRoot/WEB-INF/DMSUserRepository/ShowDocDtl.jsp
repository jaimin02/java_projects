<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
<div id="showDocDtlDiv">
<div><s:if test="wsNodeHistory.size!=0">
	<table class="datatable" width="99%" cellspacing="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Title</th>
				<th>ID</th>
				<th>Doc.</th>
				<th>Stage Changed On</th>
				<th>Stage Changed By</th>
				<th>Curr. Stage</th>
				<th>Doc. Version</th>
				<th>Edit</th>
				<th>Review</th>
				<th>Approve</th>
				<th>Users</th>
				<th>Comments</th>
				<th>Doc. Action</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="wsNodeHistory" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="nodeName" /> <s:set name="docStage"
						value="%{stageId}"></s:set></td>
					<td><s:property value="nodeFolderName" /></td>
					<td align="center" style="text-align: center;"><s:if
						test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
						<a title="<s:property value="fileName"/>"
							href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>"
							target="_blank"> <s:if test="fileName.contains(\".\")">
							<s:if
								test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
								<img alt="Open File"
									src="<%=request.getContextPath()%>/js/jquery/tree/skin/pdf_icon_14x14.gif"
									style="border: none;">
							</s:if>
							<s:elseif
								test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('doc') || fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('docx') ">
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
					<td><s:date name="modifyOn" format="MMM dd, yyyy" /></td>
					<td><s:property value="UserName" /></td>

					<td><s:property value="stageDesc" /></td>
					<td><s:property value="userDefineVersionId" /></td>
					<td><s:iterator value="wsUsrRightsList">
						<s:if test="stageId == 10">
							<div align="center"><a title="Edit Doc"
								href="javascript:void(0);"
								onclick="openInEditMode('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','<s:property value="nodeFolderName"/>','<s:property value="#docStage"/>');">
							<img border="0px" alt="Edit" src="images/Common/edit.png"
								height="18px" width="18px"> </a></div>
						</s:if>
					</s:iterator></td>
					<td><s:iterator value="wsUsrRightsList">
						<s:if test="stageId == 20">
							<s:if test="#docStage == 10">
								<div align="center"><a title="Reviewer Comments"
									href="javascript:void(0);"
									onclick="openInEditModeForComments('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','<s:property value="nodeFolderName"/>','<s:property value="#docStage"/>');">
								<img title="Reviewer Comments" border="0px" alt="Edit"
									src="images/Common/send_comment.png" height="18px" width="18px">
								</a></div>
							</s:if>
						</s:if>
					</s:iterator></td>
					<td><s:iterator value="wsUsrRightsList">
						<s:if test="stageId == 100">
							<s:if test="#docStage == 20">
								<div align="center"><a title="Approver Comments"
									href="javascript:void(0);"
									onclick="openInEditModeForComments('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','<s:property value="nodeFolderName"/>','<s:property value="#docStage"/>');">
								<img title="Approver Comments" border="0px" alt="Edit"
									src="images/Common/send_comment.png" height="18px" width="18px">
								</a></div>
							</s:if>
						</s:if>
					</s:iterator></td>
					<td style="text-align: center"><s:iterator
						value="wsUsrRightsList">
						<s:if test="stageId == 10">
							<a title="Attach User" href="javascript:void(0);"
								onclick="openInUserMode('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','<s:property value="nodeName"/>','<s:property value="nodeFolderName"/>');">
							<img title="Attach User" border="0px" alt="Users"
								src="images/Common/user.png" height="18px" width="18px"> </a>
						</s:if>
					</s:iterator></td>
					<td style="text-align: center"><a title="Show Comments"
						href="javascript:void(0);"
						onclick="openInUserCommentMode('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','<s:property value="nodeName"/>','<s:property value="nodeFolderName"/>');">
					<img title="Show Comments" border="0px" alt="Comments"
						src="images/Common/show_comment.png" height="18px" width="18px">
					</a></td>
					<td style="text-align: center"><a title="Document Action"
						href="javascript:void(0);"
						onclick="docAction('<s:property value="workSpaceId"/>','<s:property value="nodeId"/>','<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>','<s:property value="nodeFolderName"/>','<s:property value="stageDesc"/>','<s:property value="userDefineVersionId"/>');">
					<img title="Document Action" border="0px" alt="Comments"
						src="images/Common/DocAction.png" height="18px" width="18px">
					</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:else>
	<label class="title">No Document Released.</label>
</s:else></div>
</div>
</body>
</html>
