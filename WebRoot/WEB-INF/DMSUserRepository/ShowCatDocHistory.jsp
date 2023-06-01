<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<STYLE type="text/css">
.tabHdrActive {
	background: #5A8AA9;
	color: white;
	margin: 0px;
	padding: 0px;
}

.tabHdrInActive {
	background: white;
	color: #5A8AA9;
	margin: 0px;
	padding: 0px;
}
</STYLE>
<script type="text/javascript">
			
	 		$(document).ready(function() {
				$('#popupContactClose').click(function(){
					disablePopup();
				});
	 		});
		</script>

<script type="text/javascript">
 		$(document).ready(function() {
 			$('#refHistory').hide();
 			$('#localHistory').hide();
			$('#localHisTab').click(function(){
				$('#refHistory').hide();
				$('#localHisTab').attr('class','title tabHdrActive');
				$('#refHisTab').attr('class','title tabHdrInActive');
				$('#localHistory').fadeIn('slow');
				
				
			});
			$('#refHisTab').click(function(){
				$('#localHistory').hide();
				$('#refHisTab').attr('class','title tabHdrActive');
				$('#localHisTab').attr('class','title tabHdrInActive');
				$('#refHistory').fadeIn('slow');
			});
			$('#localHisTab').click();
 		});
		</script>
</head>
<body>
<a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">
<table align="left" width="30%">
	<tr>
		<td title="Document History" id="localHisTab" class="title tabTitle"
			style="border: 1px solid;">&nbsp;&nbsp;Doc. History&nbsp;&nbsp;</td>
		<td></td>
		<td title="Reference History" id="refHisTab" class="title tabTitle"
			style="border: 1px solid;">&nbsp;&nbsp;Ref. History&nbsp;&nbsp;</td>

	</tr>
</table>
</div>
<div id="localHistory"
	style="width: 100%; height: 410px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:if test="nodeVersionHistory.size > 0">
	<table class="datatable" width="100%" align="center">
		<thead>
			<tr>
				<th>#</th>
				<th>Document</th>
				<th>Document Name</th>
				<th>Modify On</th>
				<th>Modify By</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="nodeVersionHistory" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td align="center" valign="middle" style="text-align: center">
					<s:if
						test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
						<a title="<s:property value="fileName"/>"
							href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/>/<s:property value="workSpaceId"/>/<s:property value="nodeId"/>/<s:property value="tranNo"/>/<s:property value="fileName"/>"
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
					<td><s:if test="fileName != null && fileName !=''">
						<s:property value="fileName" />
					</s:if></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="userName" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:else>
	<label class="title"> History not Found. </label>
</s:else></div>
<div id="refHistory"
	style="width: 100%; height: 410px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:if test="wsNodeRefDtlList.size > 0">
	<table class="datatable" width="100%" align="center">
		<thead>
			<tr>
				<th>#<s:property value="wsNodeRefDtlList.size" /></th>
				<th title="Reference Project">Ref. Project</th>
				<th title="Reference Node Name">Ref. Node</th>
				<th>Created On</th>
				<th>Created By</th>
				<th>Modify On</th>
				<th>Modify By</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="wsNodeRefDtlList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="refWorkspaceDesc" /></td>
					<td><s:property value="refNodeName" /></td>
					<td><s:date name="createdOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="createdByUser" /></td>
					<td><s:if test="statusIndi != 'N'">
						<s:date name="modifyOn" format="MMM-dd-yyyy" />
					</s:if> <s:else>-</s:else></td>
					<td><s:if test="statusIndi != 'N'">
						<s:property value="modifyByUser" />
					</s:if> <s:else>-</s:else></td>
					<td align="center" style="text-align: center"><s:if
						test="statusIndi == 'D'">Deleted</s:if> <s:elseif
						test="statusIndi == 'E'">Edited</s:elseif> <s:else>New</s:else></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:else>
	<label class="title"> History not Found. </label>
</s:else></div>
</body>
</html>