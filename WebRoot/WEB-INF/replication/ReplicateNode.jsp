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
			document.copyPasteWorkspaceForm.destNodeId.value = destNodeId;	
	}
	else
		
		document.copyPasteWorkspaceForm.destNodeId.value = 0;
	destlastcheckedbox=destnodeid;
}

function copyAndPasteWorkspace(status){	
	
	
		if(document.getElementById("sourceWorkspaceId").value == "-1")
		{
			alert("Please Select Project Name");
			return false;
		}
		var srcNodeId = document.copyPasteWorkspaceForm.sourceNodeId.value;
		var sourceWorkspaceId = document.copyPasteWorkspaceForm.sourceWorkspaceId.value;
		var destWorkspaceId  = document.copyPasteWorkspaceForm.sourceWorkspaceId.value;
		var	destnodeId = document.copyPasteWorkspaceForm.sourceNodeId.value;
			  
	   // str="CopyWorkspaceNodeAction.do?srcnodeId="+srcNodeId+"&destnodeId="+destnodeId+"&srcWsId="+sourceWorkspaceId+"&destWsId="+destWorkspaceId+"&status="+status;
	    str="CopyWorkspaceNodeAction.do?srcnodeId="+srclastcheckedbox+"&destnodeId="+srclastcheckedbox+"&srcWsId="+sourceWorkspaceId+"&destWsId="+destWorkspaceId+"&status="+status;
		
		window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=120,width=500,resizable=no,titlebar=no")	
	
		
		return true;
}	

function popUp()
	{
		if(document.getElementById("sourceWorkspaceId").value == "-1")
		{
			alert("Please Select Project Name");
			return false;
		}
		var ddl = document.getElementsByName("sourceWorkspaceId");
		var wsid = document.getElementById("sourceWorkspaceId").value;
		var srcNodeId = document.copyPasteWorkspaceForm.sourceNodeId.value;
		var wsdesc = ddl[0].options[ddl[0].selectedIndex].text;
		strAction ="ReplicateWorkspaceNodeAttribute.do?workspaceID=" + wsid +"&nodeId=" +srclastcheckedbox;
   		window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=200,width=500,resizable=no,titlebar=no");
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
<div class="titlediv">Replicate Node</div>

<div align="center">
<form name="copyPasteWorkspaceForm" method="post"><br>
<table>
	<tr>
		<td class="title">&nbsp;&nbsp;Project Name : <s:select
			list="getWorkspaceDetail" name="sourceWorkspaceId" headerKey="-1"
			headerValue="Select Project Name" listKey="workSpaceId"
			listValue="workSpaceDesc">

		</s:select> <ajax:event ajaxRef="CopyPasteSourceWorkspace/getWorkspaceSourceTree" />
		</td>
	</tr>
</table>
<br>
<table width="95%">

	<tr>
		<td width="100%" align="center"><input type="button"
			value="Extend" onclick="return popUp();" class="button" /></td>
	</tr>

</table>
<table width="95%" align="center">
	<tr>
		<td valign="top" width="50%">
		<div id="showSourceTreeDtl"></div>
		</td>
	</tr>
</table>

<ajax:enable /> <input type="hidden" name="sourceNodeId" value="">
<input type="hidden" name="destNodeId" value=""></form>
</div>
</body>
</html>

