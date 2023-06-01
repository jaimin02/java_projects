<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<base target="attributeFrame">
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<STYLE>
BODY {
	FONT: 10pt Verdana, sans-serif;
	COLOR: olive;
	scrollbar-arrow-color: blue;
	scrollbar-face-color: #e7e7e7;
	scrollbar-3dlight-color: #a0a0a0;
	scrollbar-darkshadow-color: #888888;
}

.trigger {
	CURSOR: hand
}

.branch {
	DISPLAY: none;
	MARGIN-LEFT: 16px
}
</STYLE>

<SCRIPT language=JavaScript>
var openImg = new Image();
openImg.src = "images/open.gif";
var closedImg = new Image();
closedImg.src = "images/closed.gif";

function showBranch(branch,nodeId){
	var objBranch = document.getElementById(branch).style;
	if(objBranch.display=="block")
		objBranch.display="none";
	else
		objBranch.display="block";

	document.workspacenodes.nodeId.value = 	nodeId;
	document.workspacenodes.submit();
}

function swapFolder(img){
	objImg = document.getElementById(img);
	if(objImg.src.indexOf('closed.gif')>-1)
		objImg.src = openImg.src;
	else
		objImg.src = closedImg.src;
}

function getCheckedNode(){
	var flag=false;
    var checkedBox = ""; 
	var chklength=document.workspacenodes.CHK.length;
	
	for(var iCount=0;iCount<chklength;iCount++){
		
		if(eval(document.workspacenodes.CHK[iCount].checked)==true){
			var arr=document.workspacenodes.CHK[iCount].value;
			var nodeid=arr.split('_');
			checkedBox = checkedBox + nodeid[1] + ",";
			flag = true;
			if(checkedBox!=""){
					document.features.checkedBoxName.value = checkedBox;
			}
		}	
	}	
	if(flag==false){
		alert("Please Select any checkBox");
		return false;
	}else{
		return true;
	}
}

function getCheckedNodeForAttach(wsId){
	var flag="false";
    var checkedBox = ""; 
	
	if(getCheckedNode()==true){
		var id = document.features.hworkspaceId.value;
		checkedBox=	document.features.checkedBoxName.value;	
		//strAction = "showTemplateForAttach.do?wsId="+id+"&nodeId="+checkedBox;
		//window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=400,width=900,resizable=no,titlebar=no");	
	}	
	return false;	
}

</SCRIPT>


</head>
<body>

<div class="headercls">WorkSpace Nodes</div>
<div class="bdycls"><br>
<font class="title">Client:&nbsp;</font>${client_name} <br>
<font class="title">Project Type:&nbsp;</font>${project_type} <br>
<font class="title">Project:&nbsp;</font>${project_name} <br>
<br>
<br>

${htmlCode}</div>
<input type="hidden" name="nodeId" value="">
<input type="hidden" name="checkBoxName">


</body>
</html>
