<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	$(document).ready(function() { 
		var options = 
		{
			target: '#messageDiv',
			url: 'SetLink.do'
		};
		$('#SetLinkForm').submit(function()
		{
			$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');	
			$(this).ajaxSubmit(options);
			setLinks();
			return false;									
		});
	});

</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>


<div align="left"><s:if test="finalDtlOfFiles.size == 0  ">
	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000">
			<b> <br>
			<s:if test="workspaceid == '-1' ">
							Please Select Project Name.
						</s:if> <s:else>
							No files containing links were found in the project.
						</s:else> <br>
			&nbsp; </b> </font></td>
		</tr>
	</table>
	</center>
</s:if> <s:else>
	<!-- <div class="titlediv" align="left">Files Containing Links</div> -->

	<form name="SetLinkForm" id="SetLinkForm">

	<table width="100%">
		<tr>
			<td class="title" align="right" width="15%"
				style="padding: 2px; padding-right: 8px;">Selected File</td>
			<td align="left" width="55%"><s:textfield name="selectedfile"
				value=" Please Select File" size="55%" readonly="true"
				theme="simple"></s:textfield></td>
			<td class="title" align="right" width="11%"
				style="padding: 2px; padding-right: 8px;">Selected Link</td>
			<td align="left"><s:textfield name="selectedlinkno"
				value=" Please Select Link" readonly="true" theme="simple"></s:textfield>
			</td>
			<td>
			<div align="center"><s:hidden name="sourceNodeId" value=""></s:hidden>
			<s:hidden name="selectedLink" value=""></s:hidden> <s:hidden
				name="selectedfilepath" value=""></s:hidden> <s:hidden
				name="selectedFileDepth" value=""></s:hidden> <s:hidden
				name="selectedImagepathId" value="">
			</s:hidden> <s:hidden name="workspaceid" value=""></s:hidden></div>
			</td>
		</tr>
	</table>

	<br>
	<!-- <div style="width: 100%; height: 265px; overflow-y: scroll;"> -->

	<div align="left">
	<table cellspacing="3" border="1" width="900px">
		<tr>
			<td width="300px" valign="top">
			<div class="titlediv">Files</div>
			<br>
			<div
				style="border: medium; width: 300px; height: 230px; overflow-y: auto">
			<table class="datatable" width="100%">
				<thead>
					<tr>
						<th>#</th>
						<th>Parent Folder</th>
						<th>File/Leaf-Node Name</th>
						<th>View Links</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="finalDtlOfFiles" id="fileList" status="status">
						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
							<td>${status.count}</td>
							<td><s:property value="top[1]" /></td>

							<td><a
								onclick="openFileWithPath('<s:property value="top[2]"/>');"
								title="<s:property value="top[2]"/>"> <s:property
								value="top[0]" /> </a></td>

							<td>
							<div align="center"><img
								id="viewlinks_<s:property value="top[4]"/>" border="0px"
								alt="View" src="images/Common/view.png" height="18px"
								width="18px"
								onclick="javascript:setSelectedFile('<s:property value="top[0]"/>','<s:property value="top[2]"/>','<s:property value="top[3]"/>',this);return false;">
							</div>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			</div>
			</td>

			<td width="300px" align="center" valign="top">
			<div id="LinksDiv" style="width: 300px; overflow: auto"></div>
			</td>
			<td width="300px" valign="top">

			<div class="titlediv">Select Reference File</div>
			<br>
			<div class="bdycls"
				style="width: 100%; height: 230px; overflow-y: auto">
			${htmlCode}</div>

			</td>
		</tr>
	</table>
	<div style="width: 900px;" align="center"><br />
	<input type="submit" value="Edit Link" class="button" /></div>
	</div>
	</form>
</s:else></div>
</body>
</html>

