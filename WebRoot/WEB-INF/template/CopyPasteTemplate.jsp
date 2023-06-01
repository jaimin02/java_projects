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
			if(eval(document.createDeleteStructureForm.<%=checkBoxName%>.checked)==true){				
					checkedBox = checkedBox+<%=nodeIdVal%>+",";
					flag = true;
					fieldName = document.createDeleteStructureForm.<%=checkBoxName%>.value;

				if(checkedBox!=""){
					document.createDeleteStructureForm.checkedBoxName.value = checkedBox;
				}
			} 
		<%
		}	
		%>
		if(flag==false)
				alert("Please Select any checkBox");		
}

function filledHiddenValue(srcnodeid){	
	boxx = eval("document.createDeleteStructureForm.CHK_" + srcnodeid);
	box = eval("document.createDeleteStructureForm.CHK_" + srclastcheckedbox); 
	box.checked = false;
	
	if(boxx.checked ==true){		
		document.createDeleteStructureForm.sourceNodeId.value = srcnodeid;
	}
	else
		document.createDeleteStructureForm.sourceNodeId.value = 0;
	srclastcheckedbox=srcnodeid;	
}

function filledHiddenValueT(destnodeid){
	boxx = eval("document.createDeleteStructureForm.TCHK_" + destnodeid); 
	box = eval("document.createDeleteStructureForm.TCHK_" + destlastcheckedbox); 
	box.checked = false;

	if(boxx.checked ==true){		
		document.createDeleteStructureForm.destNodeId.value = destnodeid;			
	}
	else
		document.createDeleteStructureForm.destNodeId.value = 0;
	destlastcheckedbox=destnodeid;		
}

function copyAndPasteStructure(){	
	
	var srcNodeId = document.createDeleteStructureForm.sourceNodeId.value;
	var destNodeId = document.createDeleteStructureForm.destNodeId.value;
	var sorcTemplateId = document.createDeleteStructureForm.sorcTemplateId.value;
	var destTemplateId = document.createDeleteStructureForm.destTemplateId.value;
	
	if(document.createDeleteStructureForm.sourceNodeId.value == 0)
	{
		alert("Please Select Source Node..");
		return false;
	}
	if(document.createDeleteStructureForm.destNodeId.value == 0)
	{
		alert("Please Select Destination Node..");
		return false;
	}
	
	
	
	str="CopyStructureNodeAttrAction.do?srcnodeId="+srcNodeId+"&destnodeId="+destNodeId+"&srctempId="+sorcTemplateId+"&desttempId="+destTemplateId;
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
	src="images/Header_Images/Structure/Copypaste_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left">
<form name="createDeleteStructureForm" method="post"><br>
<table width="100%">
	<tr>

		<td class="title" width="50%" align="right"
			style="padding-right: 20px;">Source Template&nbsp;&nbsp; <s:select
			list="sourceTemplateDetails" name="sorcTemplateId" headerKey="-1"
			headerValue="Select Source Template" listKey="templateId"
			listValue="templateDesc">

		</s:select> <ajax:event
			ajaxRef="createDeleteSourceStructure/getTemplateSourceTree" /></td>


		<td class="title" width="50%"
			style="border-left: 1px solid; border-color: navy;">
		&nbsp;&nbsp; Destination Template &nbsp;&nbsp; <s:select
			list="destTemplateDetails" name="destTemplateId" headerKey="-1"
			headerValue="Select Destination Template" listKey="templateId"
			listValue="templateDesc">
		</s:select> <ajax:event ajaxRef="createDeleteDestStructure/getTemplateDestinTree" />
		</td>
	</tr>
	<tr>

		<td align="center" colspan="2"><input type="button"
			class="button" value="Copy-paste Structure"
			onclick="copyAndPasteStructure();" /></td>

	</tr>
</table>



<table width="100%">
	<tr align="left">
		<td valign="top" width="50%">
		<div id="showSourceTreeDtl" style="overflow: auto;"></div>
		</td>

		<td valign="top" width="50%"
			style="border-left: 1px solid; border-color: navy; padding-left: 5%">
		<div id="showDestinationTreeDtl" style="overflow: auto;"></div>

		</td>
	</tr>
</table>


<ajax:enable /></form>
</div>
</div>
</div>
</body>
</html>

