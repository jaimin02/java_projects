<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<STYLE>
.trigger {
	CURSOR: hand
}

.branch {
	DISPLAY: none;
	MARGIN-LEFT: 16px
}
</STYLE>

<script type="text/javascript">
		
 		$(document).ready(function() {
			$('#popupContactClose').click(function(){
				disablePopup();
			});
			var options = {	 
					beforeSubmit: showRequest,
					success: showResponse
				  };
			$('#createCategoryBtn').click(function() {
					if(validate())					
	 					$("#saveCategoryForm").ajaxSubmit(options);
	 				return false;
	 			});
			getUsrs();

			$('#reference').hide();
			$('#newFile').hide();
			$('#docType').change(function() {
				var docType = $('#docType').val();
				if (docType == "NewDoc") 
				{
					$('#newFile').show();
					$('#reference').hide();
				}
				else if (docType == "NewRef")
				{
					$('#reference').show();
					$('#newFile').hide();
				}
				else
				{
					$('#reference').hide();
					$('#newFile').hide();
				}
 			});

 			$('#workspaceDtl').change(function() {
				var workspaceId = $('#workspaceDtl').val();
				var commURL = "GetWSTree_ex.do?workSpaceId="+workspaceId;
				$.ajax
				({			
						url: commURL,
						beforeSend: function()
						{
							$("#treeDetails").html("");
							$("#treeDetails").hide();
							$('#loadImgDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
															
						},
						success: function(data) 
				  		{
							$('#loadImgDiv').html("");
							$("#treeDetails").show();
							$("#treeDetails").html(data);	
							showBranch('branch1',1);
		 					swapFolder('folder1');
						}	  		
				});
 			});
 		});
 		function showRequest(formData, jqForm, options) {
 			return true;
 		}
 		
 		// post-submit callback
 		function showResponse(responseText, statusText) {
 			alert(responseText);
 			disablePopup();
 			showData();
 		}

 		function showBranch(branch,nodeId)
		{
			if(document.getElementById(branch))
			{
				var objBranch = document.getElementById(branch).style;
			
				if(objBranch.display=="block")
					objBranch.display="none";
				else
					objBranch.display="block";
			}
		}

		function swapFolder(img)
		{
		  if(document.getElementById(img))
		  {	
			  var objImg = document.getElementById(img);
			  if(objImg)
			  {
				if(objImg.src.indexOf('closed.gif')>-1)
					objImg.src = openImg.src;
				else
					objImg.src = closedImg.src;
			  }
		  }
		}
 		</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
</div>
<div><a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">
Edit Category Details</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 410px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:form name="saveCategoryForm"
	id="saveCategoryForm" method="post" action="SaveCategory_ex"
	enctype="multipart/form-data">
	<br />

	<input type="hidden" id="workSpaceId" name="workSpaceId"
		value="<s:property value="workSpaceId"/>"></input>
	<input type="hidden" id="nodeId" name="nodeId"
		value="<s:property value='dto.nodeId'/>" />
	<input type="hidden" id="selAttributes" name="selAttributes" value="" />
	<input type="hidden" id="userDetails" name="userDetails" value="" />
	<input type="hidden" id="userCodeList" name="userCodeList" value="" />

	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Category Name&nbsp;</td>
			<td align="left"><input type="text" name="nodeName"
				id="nodeNameId" value="<s:property value='dto.nodeName'/>">
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Document Category
			Prefix&nbsp;</td>
			<td align="left"><input type="text" name="folderName"
				value="<s:property value='dto.folderName'/>"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Select Attributes</td>
			<td valign="middle" align="left" style="padding: 2px;">
			<div align="right" style="padding-bottom: 2px; width: 430px;">
			&nbsp;<label for="selectAll">Select All</label>&nbsp;<input
				type="checkbox" id="selectAll" name="selectAll" onclick="chkAll();"
				align="right"></div>
			<div align="left"
				style="width: 430px; height: 85px; overflow-y: auto; border: solid 1px; text-align: left;">
			<table border="0" style="color: black;">
				<s:iterator value="getAttributeDetail" status="status">
					<s:set name="outerAttrId" value="attrId"></s:set>
					<s:set name="checkedValue" value=""></s:set>
					<s:if test="nodeId != 0">
						<s:iterator value="workspaceNodeAttrList">
							<s:if test="#outerAttrId == attrId">
								<s:set name="checkedValue" value='checked="checked"'></s:set>
							</s:if>
						</s:iterator>
					</s:if>
					<s:if test="#status.count % 3 == 1">
						<tr align="left" valign="middle">
					</s:if>
					<td style="padding-left: 20px; padding-right: 10px;"
						valign="middle"><input type="checkbox"
						name="selectedAttributes" id="<s:property value="attrId"/>"
						value="<s:property value="attrId"/>"
						<s:property value="#checkedValue"/> />&nbsp<label
						for="<s:property value="attrId"/>"><s:property
						value="attrName" /></label></td>
					<s:if test="#status.count % 3 == 0">
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</div>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remark&nbsp;</td>
			<td align="left"><input type="text" name="remark" id="remark"
				value="<s:property value='dto.remark'/>"></td>
		</tr>
		<tr>
			<td valign="top" class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Document</td>
			<td align="left"><SELECT id="docType" name="docType">
				<option value="KeepCurrent">Keep Current <s:if
					test="catStatus == 1">Reference</s:if><s:elseif
					test="catStatus == 2">Document</s:elseif></option>
				<option value="NewDoc">New Document</option>
				<option value="NewRef">Update Reference</option>
				<s:if test="catStatus != 0">
					<option value="RemoveAll">Remove All (Document and
					Reference)</option>
				</s:if>
			</SELECT> <!--<table style="font-size: x-small">
									<tr>
										<td valign="top">
												<input  
												<s:if test=" dtoWsNodeHis.fileName == null || dtoWsNodeHis.fileName =='' || dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">disabled="disabled"</s:if>	
												 id="current" type="radio" name="fileUploading" value="Current" checked="checked"><label style="margin-bottom: 2px;" for="current">Keep Current</label>&nbsp;&nbsp;
												<a title="<s:property value="dtoWsNodeHis.fileName"/>" href="openfile.do?fileWithPath=<s:property value="dtoWsNodeHis.baseWorkFolder"/><s:property value="dtoWsNodeHis.folderName"/>/<s:property value="dtoWsNodeHis.fileName"/>" target="_blank">
													<s:if test="dtoWsNodeHis.fileName != null && dtoWsNodeHis.fileName !='' && !dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">
														<img alt="Open File" src="<%=request.getContextPath()%>/images/file.png" style="border: none;">
													</s:if>
												</a>
											
										</td>
									</tr>
									<tr>
										<td valign="top">
											<input 
												<s:if test="dtoWsNodeHis.fileName == null || dtoWsNodeHis.fileName =='' || dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">
													checked="checked"
												</s:if>
											 id="newFile" type="radio" name="fileUploading" value="New"><label for="newFile">New&nbsp;&nbsp;<input type="file" id="refDocUpload" name="refDocUpload"></input></label>	
										</td>
									</tr>
									<tr>
										<td valign="top">
											<input
											<s:if test="dtoWsNodeHis.fileName == null || dtoWsNodeHis.fileName =='' || dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">disabled="disabled"</s:if>
											 id="remove" type="radio" name="fileUploading" value="Remove"><label for="remove">Remove</label>
										</td>
									</tr>
								</table>
							--></td>
		</tr>
		<tr id="newFile">
			<td valign="top" class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Document</td>
			<td align="left"><input type="file" id="refDocUpload"
				name="refDocUpload"></input></td>
		</tr>
		<tr id="reference">
			<td valign="top" class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"></td>
			<td align="left"><s:select id="workspaceDtl" name="workspaceDtl"
				list="projectList" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="0" headerValue="Select Project For Reference">
			</s:select>
			<div id="loadImgDiv" style="width: 100%"></div>
			<div id="treeDetails" style="width: 80%; border: 1px solid;"></div>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Select User
			Type&nbsp;</td>
			<td align="left"><select name="usrTyp" id="usrTyp"
				style="min-width: 0px;" onchange="getUsrs();">
				<s:iterator value="userTypes">
					<option value="<s:property value="userTypeCode"/>"
						<s:if test="userTypeCode.equalsIgnoreCase('0003')">selected="selected"</s:if>>
					<s:if test="userTypeName == 'WA'">Workspace Admin</s:if> <s:if
						test="userTypeName == 'WU'">Workspace User</s:if></option>
				</s:iterator>

			</select></td>
		</tr>
		<tr>
			<td colspan="2">
			<div id="usrGrpList" align="center"></div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><input type="button"
				id="createCategoryBtn" name="createCategoryBtn"
				value="Save Category" Class="button" /></td>
		</tr>
	</table>
</s:form></div>
<br>
</div>
</body>
</html>