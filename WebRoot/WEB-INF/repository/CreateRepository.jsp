<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript">
		function subinfo()
		{
			var srcAEle = document.getElementById('srcA');
			var srcProjectId= document.forms["copyPasteWorkspaceForm"].sorceWorkspaceId.value;
			var srcProjectIdb= document.forms["copyPasteWorkspaceForm"].destWorkspaceId.value;
			srcAEle.setAttribute('href','RepositoryGetWorkspaceSourceTree.do?sorceWorkspaceId='+srcProjectId+'&destWorkspaceId='+srcProjectIdb);
		}
	</script>
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> 
-->

<br />
<div align="center"><img
	src="images/Header_Images/Project/Repository.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="copyPasteWorkspaceForm"
	method="post">
	<br>
	<table width="100%">
		<tr>
			<td class="title" align="left" width="50%"
				style="padding: 2px; padding-left: 8px;">Source Project</td>
			<td></td>
			<td></td>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Destination Project</td>

		</tr>

		<tr>

			<td align="left" width="50%" colspan="2" valign="top"
				style="padding-left: 8px;"><s:select
				list="sourceWorkspaceDetails" name="sorceWorkspaceId" headerKey="-1"
				headerValue="Select Source Project " listKey="workSpaceId"
				listValue="workSpaceDesc" onchange="subinfo();"
				ondblclick="subinfo();">
			</s:select></td>
			<td></td>
			<td align="right" width="50%" colspan="2" valign="top"
				style="padding-right: 8px;"><s:select
				list="destWorkspaceDetails" name="destWorkspaceId" headerKey="-1"
				headerValue="Select Destination Project " listKey="workSpaceId"
				listValue="workSpaceDesc" onchange="subinfo();"
				ondblclick="subinfo();">
			</s:select></td>
		</tr>
		<tr align="center">
			<td colspan="4"><a class="button" name="srcA" id="srcA"
				target="srcFrame" style="text-decoration: none;"> Show </a></td>
		</tr>
	</table>
</s:form> <br />
<table width="100%" align="center">
	<tr>
		<td valign="top" width="100%" align="center"><iframe
			id="srcFrame" name="srcFrame" width="100%" height="430px"
			align="left" frameborder='0' scrolling="no"></iframe></td>

	</tr>
</table>
</div>
</div>
</div>
</body>
</html>
