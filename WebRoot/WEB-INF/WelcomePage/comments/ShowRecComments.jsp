<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<script type="text/javascript">

function fileOpen(actionName)
{
	win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
}

</script>
</head>
<body>
<div align="right" style="margin-top: 5px;">Show <s:select
	list="{3,4,5,6,7,8,9,10}" name="noOfRecords"
	onchange="getRecComments('1','%{receiverUserCode}','tabDiv1','noOfRecords');return false;">
</s:select> Records&nbsp;&nbsp;&nbsp;</div>
<s:if test="allComments.size()>0">
	<div align="center" style="border: 1px solid #175279;">
	<div align="center" style="max-height: 250px; overflow: auto;">
	<table class="datatable" style="width: 100%; border: 0px;"
		align="center">
		<tr>
			<th>#</th>
			<th>Sender</th>
			<th>Project</th>
			<th>Node</th>
			<th>File</th>
			<th>Sent Date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Show</th>
			<!-- <th>Reply</th> -->
			<!-- <th>Delete</th> -->
		</tr>
		<s:iterator value="allComments" status="status">
			<tr id="row<s:property value='#status.count'/>"
				<s:if test="readFlag=='Y' && LockSeq=='L'">
				 			class="lockFound"
						</s:if>
						<s:elseif test ="readFlag=='N' && LockSeq=='L'">
							class="lockFound"
						</s:elseif>
						<s:elseif test ="readFlag=='Y' && LockSeq=='E'">
				 			class="even"
						</s:elseif>
						<s:else>
							class="odd"
						</s:else>>
				<td><s:property
					value="((pageNo-1)*noOfRecords) + #status.count" /></td>
				<td><s:property value="senderName" /></td>
				<td><s:property value="workSpaceDesc" /></td>
				<td><s:property value="nodeName" /></td>

				<td align="center" style="text-align: center;"><s:if
					test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
					<a title="<s:property value="fileName"/>"
						href="openfile.do?fileWithPath=<s:property value="showCommentDocPath"/>/<s:property value="workspaceId"/>/<s:property value="nodeId"/>/<s:property value="subjectId"/>/<s:property value="fileName"/>"
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

				<!-- <td><s:date name="createdOn" format="dd-MMM-yyyy HH:mm" /></td> -->
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td>
				<s:if test="LockSeq=='L'">
					<img align="right" border="0px" title="Lock Project"
						  alt="Show Comments" src="images/Common/show_comment.png" height="18px" width="18px" onclick="">
				</s:if>
				<s:else>
				   <img align="right" border="0px" title="Show Comments" alt="Show Comments" src="images/Common/show_comment.png" height="18px" width="18px" 
					    onclick="showComment(<s:property value='#status.count'/>,<s:property value='allComments.size()'/>,'<s:property value='subjectId'/>');getStructure('<s:property value='workspaceId'/>','<s:property value='nodeId'/>','Structure<s:property value='#status.count'/>');">
				</s:else>
				</td>
				<!-- <td><img border="0px" title="Reply Commnets" alt="Reply Comments" src="images/Common/reply_comment.png" height="18px" width="18px" onclick="showReply(<s:property value='#status.count'/>,<s:property value='allComments.size()'/>);return false;"></td> -->
				<!-- <td><input type='button' class='button' value='Delete' onclick="delCom('<s:property value='subjectId'/>');"></td> -->
			</tr>
			<tr id="rowShow<s:property value='#status.count'/>"
				style="display: none;" class="even none">
				<td colspan="7" style="padding-bottom: 3px;">
				<hr color="#5A8AA9" size="2px"
					style="width: 100%; border-bottom: 1px solid #CDDBE4; margin-bottom: 5px; margin-top: 5px;">
				<div style="width: 100%; padding-top: 2px;" align="right"><img
					border="0px" title="Reply" alt="Reply"
					src="images/Common/reply_comment.png" height="18px" width="18px"
					onclick="showReply(<s:property value='#status.count'/>,<s:property value='allComments.size()'/>);return false;">
				&nbsp;&nbsp; <img border="0px" title="Back" alt="Back"
					src="images/Common/Back.png" height="18px" width="18px"
					onclick="hideComment(<s:property value='allComments.size()'/>);">
				</div>
				<div style="width: 85%" align="left"><b><u>Comment :</u>
				&nbsp;</b>
				<div
					style="max-height: 85px; width: 100%; overflow: auto; border: 1px thin #175279">
				<s:property value="subjectDesc" /></div>
				</div>
				<br />
				<div style="width: 95%; padding-bottom: 2px;" align="left"><b>
				<u>Node Hierarchy :</u> </b> <br>
				<div id="Structure<s:property value='#status.count'/>"
					style="width: 100%;"></div>
				</div>
				</td>
			</tr>
			<tr id="rowReply<s:property value='#status.count'/>"
				style="display: none;" class="none">
				<td colspan="7"
					style="padding-bottom: 3px; border: 1px solid #175279;">
				<div style="width: 100%; padding-top: 2px;" align="right"><img
					title="Send" src="images/Common/send_comment.png"
					onclick="reply('<s:property value='workspaceId'/>','<s:property value='nodeId'/>','<s:property value='senderUserCode'/>','reply<s:property value='#status.count'/>','<s:property value='subjectId'/>','rplRes<s:property value='#status.count'/>');" />
				&nbsp;&nbsp; <img border="0px" title="Back" alt="Back"
					src="images/Common/Back.png" height="18px" width="18px"
					onclick="hideReply(<s:property value='#status.count'/>,<s:property value='allComments.size()'/>);">
				</div>
				<div style="width: 90%" align="left">

				<table width="100%" class="none">
					<tr class="none">
						<td valign="top" width="120px;"><b><u>Enter Comment :</u></b>
						</td>
						<td><textarea id="reply<s:property value='#status.count'/>"
							rows="5" cols="45"></textarea></td>
					</tr>
				</table>
				</div>
				<div id="rplRes<s:property value='#status.count'/>"></div>
				</td>
			</tr>
		</s:iterator>

	</table>
	</div>
	</div>
	<table width="100%">
		<tr>
			<td width="50%" align="left" style="padding-left: 10px;">Page
			No: <s:property value='pageNo' />/<s:property value="noOfPages" />&nbsp;
			</td>
			<td align="right" style="padding-right: 10px;">Goto Page:&nbsp;
			<s:iterator value="{-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6}" status="status">
				<s:if test="(pageNo+top)>0 && (pageNo+top) <= noOfPages">
					<s:if test="(pageNo+top) != pageNo">
						<a href=#
							onclick='getRecComments("<s:property value='pageNo+top'/>","<s:property value='receiverUserCode'/>","tabDiv1","noOfRecords");return false;'><s:property
							value='pageNo+top' /></a>
					</s:if>
					<s:else>
						<s:property value='pageNo' />
					</s:else>
				</s:if>
			</s:iterator></td>
		</tr>
	</table>
</s:if>
<s:else>Nothing to Display!!!</s:else>

</body>
</html>