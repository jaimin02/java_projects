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
<table style="width: 100%; padding-bottom: 5px;" cellspacing="0px">
	<tr>
		<th id="th1"
			style="text-align: center; border: 1px solid #175279; border-bottom: 0px solid #175279;"
			onclick="f(2,1);showComments('#commentsDiv','<s:property value="#session.userid"/>','commentParent',1,2);return false;"><label
			style="cursor: pointer;">Inbox</label></th>
		<th id="th2"
			style="text-align: center; border: 1px solid #175279; border-bottom: 0px solid #175279; background: #B1C7D5;"
			onclick="f(2,2);showComments('#commentsDiv','<s:property value="#session.userid"/>','commentParent',2,2);return false;"><label
			style="cursor: pointer;">Sent</label></th>
		<!-- <th id="th3" style="text-align:center;border: 1px solid black;border-bottom: 0px solid black;background:silver;" onclick="f(3,3);"><label style="cursor: pointer;">Trash</label></th> -->
	</tr>
	<tr>
		<td colspan=5
			style="border: 1px solid #175279; border-top: 0px; background: white;">
		<div id="tabDiv1" style="width: 100%;" align="center">
		<div align="right" style="margin-top: 5px;">Show <s:select
			list="{3,4,5,6,7,8,9,10}" name="noOfRecords"
			onchange="getRecComments('1','%{receiverUserCode}','tabDiv1','noOfRecords');return false;">
		</s:select> Records&nbsp;&nbsp;&nbsp;</div>
		<s:if test="allComments.size()>0">
			<div align="center" style="border: 1px solid #175279;">
			<div align="center" style="max-height: 250px; overflow: auto;">
			<table class="datatable" align="center"
				style="width: 100%; border: 0px;">
				<tr>
					<th>#</th>
					<th>Sender</th>
					<th>Project</th>
					<th>Node</th>
					<th>File</th>
					<th>Sent Date</th>
					<th>Show</th>
					<!-- <th>Reply</th> -->
					<!-- <th>Delete</th> -->
				</tr>
				<s:iterator value="allComments" status="status">
					<tr id="row<s:property value='#status.count'/>"
						<s:if test="readFlag=='Y'">
									class="even"
								</s:if>
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

						<td><s:date name="createdOn" format="dd-MMM-yyyy HH:mm" /></td>
						<td>
						<img align="right" border="0px" title="Show Comments"
							alt="Show Comments" src="images/Common/show_comment.png"
							height="18px" width="18px"
							onclick="showComment(<s:property value='#status.count'/>,<s:property value='allComments.size()'/>,'<s:property value='subjectId'/>');getStructure('<s:property value='workspaceId'/>','<s:property value='nodeId'/>','Structure<s:property value='#status.count'/>');"></td>
						<!--<td><img border="0px" title="Reply Commnets" alt="Reply Comments" src="images/Common/reply_comment.png" height="18px" width="18px" onclick="showReply(<s:property value='#status.count'/>,<s:property value='allComments.size()'/>,'<s:property value='subjectId'/>');return false;"></td>-->
						<!--<td><input type='button' class='button' value='Delete' onclick="delCom('<s:property value='subjectId'/>');"></td> -->
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
						<div style="width: 95%; padding-bottom: 2px;" align="left">
						<b> <u>Node Hierarchy :</u> </b> <br>
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
						<div style="width: 90%; vertical-align: top" align="left">
						<table width="100%" class="none">
							<tr class="none">
								<td valign="top" width="120px;"><b><u>Enter Comment
								:</u></b></td>
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
					No: 1/<s:property value="noOfPages" /></td>
					<td align="right" style="padding-right: 10px;">Goto
					Page:&nbsp; <s:iterator value="{-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6}"
						status="status">
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
		</s:if> <s:else>Nothing to Display!!!</s:else></div>
		<div id="tabDiv2" style="display: none;" align="center">
		<div
			style="max-height: 293px; overflow: auto; margin-top: 5px; border: 1px solid #175279;"
			align="center"><s:if test="sentComments.size()>0">
			<table class="datatable" style="width: 100%; border: 0px;"
				height="auto">
				<tr>
					<th>#</th>
					<th>Sent To</th>
					<th>Project</th>
					<th>Node</th>
					<th>File</th>
					<th>Sent Date</th>
					<th>Show</th>
				</tr>
				<s:iterator value="sentComments" status="status">
					<tr id="srow<s:property value='#status.count'/>" class="even">
						<td><s:property value="#status.count" /></td>
						<td><s:property value="receiverName" /></td>
						<td><s:property value="workSpaceDesc" /></td>
						<td><s:property value="nodeName" /></td>

						<td align="center" style="text-align: center;"><s:if
							test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
							<a title="<s:property value="fileName"/>"
								href="openfile.do?fileWithPath=<s:property value="showSentCommentDocPath"/>/<s:property value="workspaceId"/>/<s:property value="nodeId"/>/<s:property value="subjectId"/>/<s:property value="fileName"/>"
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

						<td><s:date name="createdOn" format="dd-MMM-yyyy HH:mm" /></td>
						<td><img align="right" border="0px" title="Show Commnets"
							alt="Show Comments" src="images/Common/show_comment.png"
							height="18px" width="18px"
							onclick="showComment1(<s:property value='#status.count'/>,<s:property value='sentComments.size()'/>);getStructure('<s:property value='workspaceId'/>','<s:property value='nodeId'/>','SentStructure<s:property value='#status.count'/>');"></td>
					</tr>
					<tr id="srowShow<s:property value='#status.count'/>"
						style="display: none;" class="even none">
						<td colspan="7" style="padding-bottom: 3px;">
						<hr color="#5A8AA9" size="2px"
							style="width: 100%; border-bottom: 1px solid #CDDBE4; margin-bottom: 5px; margin-top: 5px;">
						<div style="width: 100%; padding-top: 2px;" align="right"><img
							border="0px" title="Back" alt="Back" src="images/Common/Back.png"
							height="18px" width="18px"
							onclick="hideComment1(<s:property value='sentComments.size()'/>);">
						</div>
						<div style="width: 95%" align="left"><b><u>Comment :</u>
						&nbsp;</b>
						<div
							style="max-height: 85px; width: 100%; overflow: auto; border: 1px thin #175279">
						<s:property value="subjectDesc" /></div>
						</div>
						<br />
						<div style="width: 95%; padding-bottom: 2px;" align="left">
						<b> <u>Node Hierarchy :</u> </b> <br>
						<div id="SentStructure<s:property value='#status.count'/>"
							style="width: 100%; overflow: auto;"></div>
						</div>
					</tr>
				</s:iterator>
			</table>
		</s:if> <s:else>Nothing to Display!!!</s:else></div>
		</div>
		<div id="tabDiv3" style="display: none; overflow: auto;"
			align="center"><s:if test="delComments.size()>0">
			<table class="datatable" style="width: 100%;">
				<tr>
					<th>#</th>
					<th>Sender</th>
					<th>Project</th>
					<th>Node</th>
					<th>Sent Date</th>
					<th>Show</th>
				</tr>
				<s:iterator value="delComments" status="status">
					<tr id="drow<s:property value='#status.count'/>">
						<td><s:property value="#status.count" /></td>
						<td><s:property value="senderName" /></td>
						<td><s:property value="workSpaceDesc" /></td>
						<td><s:property value="nodeName" /></td>
						<td><s:date name="createdOn" format="dd-MMM-yyyy HH:mm" /></td>
						<td><img align="right" border="0px" title="Show Commnets"
							alt="Show Comments" src="images/Common/show_comment.png"
							height="18px" width="18px"
							onclick="showComment2(<s:property value='#status.count'/>,<s:property value='delComments.size()'/>);"></td>
					</tr>
					<tr id="drowShow<s:property value='#status.count'/>"
						style="display: none;">
						<td colspan="7">Commented Node: <br>
						<s:property value='rootPath' escape="false" /> <br>
						Details: <br>
						<s:property value="subjectDesc" /> <br>
						<img border="0px" title="Back" alt="Back"
							src="images/Common/Back.png" height="18px" width="18px"
							onclick="hideComment2(<s:property value='delComments.size()'/>);">
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:if> <s:else>Nothing to Display!!!</s:else></div>
		</td>
	</tr>
</table>
<s:if test="showWhat==1">
	<script type="text/javascript">
		f(2,1);
	</script>
</s:if>
<s:else>
	<script type="text/javascript">
		f(2,2);
	</script>
</s:else>
</body>
</html>
