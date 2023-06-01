<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<style type="text/css">
<!--
A:link {
	text-decoration: none
}

A:visited {
	text-decoration: none
}

A:hover {
	text-decoration: underline;
}
-->
</style>
<script language="javascript">
</script>
<base target="attributeFrame1">
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<STYLE>
BODY {
	FONT: 10pt Verdana, sans-serif;
	COLOR: olive;
}

.trigger {
	cursor: pointer; 
	font-size: 14px;
    font-family: calibri;
    /* white-space: nowrap; */   
}

.trigger img{
padding: 0px 5px 4px 5px;
}

.branch img {
padding: 0px 2px 5px 5px;
}

.branch {
	DISPLAY: none;
	MARGIN-LEFT: 30px;
	font-size: 14px;
    font-family: calibri;
   /*  white-space: nowrap; */
}
#CHK_1{
MARGIN-LEFT: 10px;
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
			if(eval(document.workspacenodes.<%=checkBoxName%>.checked)==true){				
					checkedBox = checkedBox+<%=nodeIdVal%>+",";
					flag = true;
					fieldName = document.workspacenodes.<%=checkBoxName%>.value;

				if(checkedBox!=""){
					document.features.checkedBoxName.value = checkedBox;
				}
			} 
		<%
		}	
		%>
		if(flag==false)
				alert("Please Select any checkBox");	
}


</SCRIPT>

</head>


<body>

<div class="headercls">Project Navigator</div>
<div class="bdycls">
<div class="fixed_possition" style="height: 500px; width: 100%; overflow: auto; border-top: 1px solid #669; float:left;">
<s:form name="workspacenodes" method="post"
	action="DocStructureNodeAttrAction_b" target="attributeFrame1">
	<br>	
 		
 		${htmlCode}
 		<input type="hidden" name="nodeId" value="">
	<input type="hidden" name="checkBoxName">
	<s:hidden name="templateId"></s:hidden>

</s:form></div>
</div>

</body>
</html>
