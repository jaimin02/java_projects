<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<%@ page import="java.util.*"%>
<html>
<head>
<s:head />


<STYLE>
BODY {
	FONT: 10pt Verdana, sans-serif;
	COLOR: olive;
}

.trigger {
	CURSOR: hand
}

.branch {
	DISPLAY: none;
	MARGIN-LEFT: 16px
}
</STYLE>

<%
	int nodeSize = 0;
	String newCheck = new String();
	Vector checkedBoxIds = new Vector();
	Vector nodeIdRec = new Vector();
%>

<SCRIPT language=JavaScript>
var openImg = new Image();
openImg.src = "images/open.gif";
var closedImg = new Image();
closedImg.src = "images/closed.gif";
var SourceNodeId;
var DestinationNodeId;
var srclastcheckedbox=1;
var destlastcheckedbox=1;

function showBranch(branch,nodeId){
	var objBranch = document.getElementById(branch).style;
	if(objBranch.display=="block")
		objBranch.display="none";
	else
		objBranch.display="block";
}

function swapFolder(img){
	objImg = document.getElementById(img);
	if(objImg.src.indexOf('closed.gif')>-1)
		objImg.src = openImg.src;
	else
		objImg.src = closedImg.src;
}
function showBranch1(branch1,nodeId){
	var objBranch = document.getElementById(branch1).style;
	if(objBranch.display=="block")
		objBranch.display="none";
	else
		objBranch.display="block";
}

function swapFolder1(img){
	objImg = document.getElementById(img);
	if(objImg.src.indexOf('closed.gif')>-1)
		objImg.src = openImg.src;
	else
		objImg.src = closedImg.src;
}
function tempgetCheckedNode(){
	var fieldName;
	var flag=false;
    var checkedBox = ""; 
	
	<%
		int nodeId=0;
		int leaf=0;
		String checkBoxName = new String();
		String newCh = new String();
		int nodeIdVal;
		for(int i=0;i<nodeSize;i++){
			nodeIdVal = ((Integer) nodeIdRec.elementAt(i)).intValue();
 			checkBoxName = "CHK_"+ nodeIdVal;
 			application.log("nodeIdVal: " + nodeIdVal);
 	%>		
			if(eval(document.copyPasteWorkspaceForm.<%=checkBoxName%>.checked)==true){				
					checkedBox = checkedBox+<%=nodeIdVal%>+",";
					flag = true;
					fieldName = document.copyPasteWorkspaceForm.<%=checkBoxName%>.value;

				if(checkedBox!=""){
					document.copyPasteWorkspaceForm.checkedBoxName.value = checkedBox;
				}
			} 
		<%
		}	
		%>
		if(flag==false)
				alert("Please Select any checkBox");		
}

function filledHiddenValue(srcnodeid){
	boxx = eval("document.copyPasteWorkspaceForm.CHK_" + srcnodeid);
	box = eval("document.copyPasteWorkspaceForm.CHK_" + srclastcheckedbox); 
	box.checked = false;
	if(boxx.checked ==true){		
		document.copyPasteWorkspaceForm.sourceNodeId.value = srcnodeid;
	}
	else
		document.copyPasteWorkspaceForm.sourceNodeId.value = 0;
	srclastcheckedbox=srcnodeid;
}

function filledHiddenValueT(destnodeid){
	boxx = eval("document.copyPasteWorkspaceForm.TCHK_" + destnodeid); 
	box = eval("document.copyPasteWorkspaceForm.TCHK_" + destlastcheckedbox); 
	box.checked = false;
	
	if(boxx.checked ==true){		
		document.copyPasteWorkspaceForm.destNodeId.value = destnodeid;	
	}
	else
		document.copyPasteWorkspaceForm.destNodeId.value = 0;
	destlastcheckedbox=destnodeid;
}

function copyAndPasteWorkspace(status){	
	
		
		var srcNodeId = document.copyPasteWorkspaceForm.sourceNodeId.value;
		var destNodeId = document.copyPasteWorkspaceForm.destNodeId.value;
		var sourceWorkspaceId = document.copyPasteWorkspaceForm.sourceWorkspaceId.value;
		var destWorkspaceId = document.copyPasteWorkspaceForm.destWorkspaceId.value;
	
		str="CopyWorkspaceNodeAction.do?srcnodeId="+srclastcheckedbox+"&destnodeId="+destlastcheckedbox+"&srcWsId="+sourceWorkspaceId+"&destWsId="+destWorkspaceId+"&status="+status;
		window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=120,width=500,resizable=no,titlebar=no")	
		return true;
}	

</SCRIPT>
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->
<br />
<div align="center"><img
	src="images/Header_Images/Project/Copypaste_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left">

<form name="copyPasteWorkspaceForm" method="post"><br>
<table width="100%">
	<tr align="left">

		<td class="title">Source Workspace</td>
		<td><s:select list="sourceWorkspaceDetails"
			name="sourceWorkspaceId" headerKey="-1"
			headerValue="Select Source Project " listKey="workSpaceId"
			listValue="workSpaceDesc">

		</s:select> <ajax:event ajaxRef="CopyPasteSourceWorkspace/getWorkspaceSourceTree" />
		</td>


		<td class="title"
			style="border-left: 1px thin solid; border-color: navy;">
		&nbsp;&nbsp;Destination Workspace</td>
		<td><s:select list="destWorkspaceDetails" name="destWorkspaceId"
			headerKey="-1" headerValue="Select Destination Project "
			listKey="workSpaceId" listValue="workSpaceDesc">

		</s:select> <ajax:event ajaxRef="CopyPasteDescWorkspace/getWorkspaceDestinTree" />
		</td>
	</tr>

</table>


<table width="95%">
	<tr>
		<td width="33%" align="center"><input type="button"
			class="button" value="Add Node As Child Node"
			onclick="copyAndPasteWorkspace('1');" /></td>

		<td width="33%" align="center"><input type="button"
			class="button" value="Add Node After"
			onclick="copyAndPasteWorkspace('2');" /></td>
		<td width="33%" align="center"><input type="button"
			class="button" value="Add Node Before"
			onclick="copyAndPasteWorkspace('3');" /></td>
	</tr>
</table>

<table width="95%">
	<tr>
		<td valign="top" width="50%">
		<div id="showSourceTreeDtl"></div>
		</td>

		<td valign="top" width="50%"
			style="border-left: 1px thin solid; border-color: navy;">
		<div id="showDestTreeDtl"></div>
		</td>
	</tr>
</table>


<input type="hidden" name="sourceNodeId" value=""> <input
	type="hidden" name="destNodeId" value=""> <ajax:enable /></form>
</div>
</div>
</div>
</body>
</html>

