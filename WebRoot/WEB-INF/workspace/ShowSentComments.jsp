<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<s:head />
<script type="text/javascript">

function fileOpen(actionName)
{
	win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
}

</script>
</head>
<body>

<s:set name="sentComments" id="sentComments"></s:set>
<s:if test="sentComments==null || sentComments.size()==0">
  		No Comments Here!
  	</s:if>
<s:else>
	<div style="width: 100%" align="center">
	<table width="95%" style="padding-bottom: 4px" align="center">
		<tr>
			<td align="center" class="title label"><font
				style="font-style: normal; font-weight: bold; font-size: 14px;">Sent
			Comments</font></td>
		</tr>
	</table>
	<div
		style="width: 95%; overflow: auto; max-height: 185px; border: 1px solid #0C3F62;">
	<table width="100%" class="datatable" align="center">
		<thead>
			<tr>
				<th>#</th>
				<th>Comments</th>
				<th>Node</th>
				<th>File</th>
				<th>Send To</th>
				<th>Sent Date</th>
			<s:if test ="#session.countryCode != 'IND'">
				<th>Eastern Standard Time</th>
			</s:if>
			</tr>
		</thead>
		<tbody>

			<s:iterator value="sentComments" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="subjectDesc" /></td>
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
					<td><s:property value="receiverName" /></td>
					<!-- <td><s:date name="createdOn" format="dd-MMM-yyyy HH:mm" /></td> -->
					<td><s:property value="ISTDateTime" /></td> 
				<s:if test ="#session.countryCode != 'IND'">
					<td><s:property value="ESTDateTime" /></td>
				</s:if>
				</tr>

			</s:iterator>

		</tbody>

	</table>
	</div>
	</div>
</s:else>

<center style="padding-top: 4px"><input type="button"
	value="Close" class="button" onclick="javascript:self.close();">
</center>
</body>
</html>