<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>

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


<%!
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

function showBranch(branch,nodeId){
	var objBranch = document.getElementById(branch).style;
	if(objBranch.display=="block")
		objBranch.display="none";
	else
		objBranch.display="block";

	document.workspacenodes.nodeId.value = 	nodeId;
//	document.workspacenodes.submit();
}

function swapFolder(img){
	objImg = document.getElementById(img);
	if(objImg.src.indexOf('closed.gif')>-1)
		objImg.src = openImg.src;
	else
		objImg.src = closedImg.src;
}
function expandall(){
	alert(<%=nodeSize%>);
<%
	for(int i=0;i<nodeSize;i++){
	
%>
	var str="branch"+<%=i+1%>
	if(document.getElementById(str).style.display="none")
		document.getElementById(str).style.display="block";
<%}%>	
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

	

</SCRIPT>

</head>

<body>
<div class="headercls">Project Structure</div>
<div class="bdycls"><br>
<font class="title">Client:&nbsp;</font>${client_name} <br>
<font class="title">Project Type:&nbsp;</font>${project_type} <br>
<font class="title">Project:&nbsp;</font>${project_name} <br>
<br>
<br>

<s:form name="workspacenodes" method="post"
	action="workspaceNodeAttrAction" target="attributeFrame">
 			${htmlCode} 	
 			<input type="hidden" name="nodeId" value="">
	<input type="hidden" name="checkBoxName">
</s:form></div>



</body>
</html>
