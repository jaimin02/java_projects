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
/*
function copyAndPasteWorkspace(){	
	
		
		var srcNodeId = document.copyPasteWorkspaceForm.sourceNodeId.value;
		var destNodeId = document.copyPasteWorkspaceForm.destNodeId.value;
		var sourceWorkspaceId = document.copyPasteWorkspaceForm.sourceWorkspaceId.value;
		var destWorkspaceId = document.copyPasteWorkspaceForm.destWorkspaceId.value;
		str="CopyFile.do?srcNodeId="+srclastcheckedbox+"&destNodeId="+destlastcheckedbox+"&srcWsId="+sourceWorkspaceId+"&destWsId="+destWorkspaceId;
		window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=120,width=500,resizable=no,titlebar=no")	
		return true;
}	
*/
function Copy()
	{
	   if(document.getElementById("sourceWorkspaceId").value == "-1")
		{
			alert("Please Select Source Project Name");
			document.getElementById("sourceWorkspaceId").style.backgroundColor="#FFE6F7"; 
     		document.getElementById("sourceWorkspaceId").focus();
			return false;
		}		
		if(document.getElementById("destWorkspaceId").value == "-1")
		{
			alert("Please Select Destination Project Name");
			document.getElementById("destWorkspaceId").style.backgroundColor="#FFE6F7"; 
     		document.getElementById("destWorkspaceId").focus();
			return false;
		}
		
		var srcNodeId = document.copyPasteWorkspaceForm.sourceNodeId.value;
		var destNodeId = document.copyPasteWorkspaceForm.destNodeId.value;
		var sourceWorkspaceId = document.copyPasteWorkspaceForm.sourceWorkspaceId.value;
		var destWorkspaceId = document.copyPasteWorkspaceForm.destWorkspaceId.value;
		str="RepositoryWorkspaceNodeAttribute.do?srcNodeId="+srclastcheckedbox+"&destNodeId="+destlastcheckedbox+"&srcWsId="+sourceWorkspaceId+"&destWsId="+destWorkspaceId;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");
   		win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
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
<div class="titlediv">Repository</div>

<div align="center">

<form name="copyPasteWorkspaceForm" method="post"><br>
<table>
	<tr>
		<td style="border-left: 1px thin solid"></td>
		<td class="title" width="50%">&nbsp;&nbsp; Source Workspace : <s:select
			list="sourceWorkspaceDetails" name="sourceWorkspaceId" headerKey="-1"
			headerValue="Select Source Project " listKey="workSpaceId"
			listValue="workSpaceDesc">
		</s:select> <ajax:event
			ajaxRef="RepositoryCopyPasteSourceWorkspace/RepositoryGetWorkspaceSourceTree" />
		</td>

		<td class="title" width="50%"
			style="border-left: 1px thin solid; border-color: navy;">
		&nbsp;&nbsp; Destination Workspace : <s:select
			list="destWorkspaceDetails" name="destWorkspaceId" headerKey="-1"
			headerValue="Select Destination Project " listKey="workSpaceId"
			listValue="workSpaceDesc">
		</s:select> <ajax:event
			ajaxRef="RepositoryCopyPasteDescWorkspace/RepositoryGetWorkspaceDestinTree" />
		</td>
	</tr>
</table>

<table width="100%">
	<tr>
		<td align="center" width="100%"><input type="button" value="Copy"
			class="button" onclick="return Copy();" /></td>
	</tr>
</table>

<table width="100%">
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
</body>
</html>
