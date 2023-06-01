<%@ taglib prefix="s" uri="/struts-tags"%>
<%	
	response.addHeader("Pragma","no-cache"); 
	response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
	response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
	response.setDateHeader("Expires",0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" media="screen" />
<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css"
	rel="stylesheet" type="text/css" media="screen" />
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"
	type="text/javascript"></script>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />

<STYLE>
BODY {
	FONT: 12px calibri;
	scrollbar-arrow-color: blue;
	scrollbar-face-color: #e7e7e7;
	scrollbar-3dlight-color: #a0a0a0;
	scrollbar-darkshadow-color: #888888;
}
span{
	color: #0D4264;
    font: calibri;
    font-size: 16px;
    font-weight: 600;
}

.bg-position {
	width: 96%;
    background: #fff;
    height: 30px;
    position: fixed;
}

.trigger {
	CURSOR: hand
}

.branch {
	MARGIN-LEFT: 16px
}

#openAllProjects {
	cursor: pointer;
}

#openAllProjects label {
	cursor: pointer;
}

.filehistory {
	cursor: help;
	width: 16px;
	border: none;
	background: url("images/info.png") no-repeat;
	height: 16px;
}

.fileInfoDialog {
	position: fixed;
	background-color: #103050;
	color: white;
	display: none;
}

.fileInfoDialog table {
	border: 1px solid blue;
}
.loadingImage{
	font-weight: bolder;
	font-style: italic;
	font-size: 14pt;
	color:#1A661A;
	position:relative;
	width: 80px;
	padding:12px;
	padding-right: 44px;
	background: url("images/loading2.gif") no-repeat;
}
.draghover {
	
	font-weight: bolder;
	font-style: italic;
	font-size: 14pt;
	color:#1A661A;
	position:relative;
	width: 80px;
	padding:12px;
	padding-right: 44px;
	border:3px dashed red;
	background-color: rgba(255,255,255, 0.9);
	
}

.closebutton {
	padding: 1px;
	position: absolute;
	right: 0px;
	top: -15px;
	cursor: pointer;
	color: white;
	text-decoration: underline;
	background-color: black;
}
.ui-dialog .ui-dialog-titlebar{
	background: #54ACD2;
	color: #FFF;
	font-family: verdana;
}
.ui-dialog .ui-dialog-buttonpane button{
    margin-right: 35px !important;
	background: #54ACD2;
} 
.ui-button-text{
    background: #54ACD2;
    border: black;
    height: 10px;
    font-size: 12px;
    font-weight: bold;
    color: #FFF;
    font-family: verdana;
    text-align: center;
    cursor: pointer;
    padding: 0 3px 3px 0;
}

	SPAN.searchword {
		background-color: #5B89AA;
		color: white;
}

	SPAN.currsearchword {
		background-color: green;
		color: white;
}
.dynatree-container {
	padding-top: 35px !important;
}
.position-right{
	position: fixed;
    right: 0px;
    /*padding-right: 110px;*/
    background: #fff;
    padding-top: 3px;
    left: 180px;
    cursor: pointer;
    }


.position-left{
    padding-top: 3px;
    position: fixed;
    left: 162px;
    background: #fff;
    }
</STYLE>


<style>
SPAN.searchword {
	background-color: #5B89AA;
	color: white;
}

SPAN.currsearchword {
	background-color: green;
	color: white;
}
.Progress {
  width: 100%;
  background-color: #d2abab;
  height: 30px;
  margin-bottom: 10px;
}
.bg-approved
{
background-color: #4fa4ce;
}

/* #DeletedNodeDetail{
	padding-right: 6px !important;
    margin-left: 63px !important;
 } */
</style>
<link
	href='<%=request.getContextPath() %>/js/newtree/skin/ui.dynatree.css'
	rel='stylesheet' type='text/css' />
<link href='<%=request.getContextPath() %>/js/progressbar/style.css'
	rel='stylesheet' type='text/css' />
<script src="js/find/searchhi.js" type="text/javascript"
	language="JavaScript"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/progressbar/progressbar.js"></script>
<!-- 
<script type="text/javascript" src="<%=request.getContextPath() %>/js/progressbar/js/prototype/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/progressbar/js/bramus/jsProgressBarHandler.js"></script>
 -->
<script src='<%=request.getContextPath()%>/js/newtree/jquery.js'
	type='text/javascript'></script>
<script
	src='<%=request.getContextPath()%>/js/newtree/jquery-ui.custom.js'
	type='text/javascript'></script>
<script src='<%=request.getContextPath()%>/js/jquery.cookie.js'
	type='text/javascript'></script>
<script
	src='<%=request.getContextPath()%>/js/newtree/jquery.dynatree.js'
	type='text/javascript'></script>

<link
	href='<%=request.getContextPath() %>/js/jquery/jquery-ui-1.8.0.min.css'
	rel='stylesheet' type='text/css' />

<script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>

<script type='text/javascript'>

var opr="new";
var rplc='Y';
var crctpdf='Y'; 
var remark='';

function tmpdialog(evt,nodeId,FileObj,foldername,isFileHistory) {
	/* var opr="new";
	var rplc='Y';
	var crctpdf='Y'; */ 
	//debugger;
	 $( "#dialog-confirm" ).dialog({
      resizable: false,
      height: "auto",
      width: 300,
      modal: true,
      buttons:{
        Cancel: function() {
          document.getElementById("remark").value='';
          $( this ).dialog( "close" );
          $("#"+nodeId).removeClass("draghover");
        },
        OK: function() {
        
        	
        	if($('#replcfilechkbox').prop('checked')){ //checkbox checked or not
        		rplc='Y';	
        		/* ('#replcfilechkbox').prop('checked', this.checked); */
        	}
        	else {
        		rplc='N';
        	}	
        	if($('#pdfchkbox').is (':checked')){      //checkbox checked or not
        		crctpdf='Y';	
        	}
        	else{
        		crctpdf='N';
        	}
        	
        		//opr=document.getElementById("oprtn").value;
        	 	//debugger;
        		remark=document.getElementById("remark").value;
           		if(isFileHistory=="true" && remark=="")
        		{
        		  alert("Please specify Reason for Change.");
        		  document.getElementById("remark").style.backgroundColor="#FFE6F7"; 
           		  document.getElementById("remark").focus();
        		  return false;
        		}       	
        	dropFile(evt,FileObj,nodeId,opr,rplc,crctpdf,foldername,remark);
        	        	 
          $( this ).dialog( "close" );
        }
      }
    });
}

function dropFile(evt,FileObj,nodeId,opr,rplc,crctpdf,foldername,remark){
	//debugger;
	$("#"+nodeId).removeClass("draghover");
	$("#" + nodeId).addClass("loadingImage");
	
		var dragFlag=true; //to check whether file upload through dnd or locknode(dnd:true)
		var files = FileObj; // FileList object.
	    var fileName=files[0].name;
	    var ext=fileName.indexOf(".");//to check if folder or file
	    
		if(ext>0) {

	    var formData_FileUploading=new FormData();
	    formData_FileUploading.append('uploadFile', files[0]);
	    formData_FileUploading.append('nodeId',evt.target.id);
	    formData_FileUploading.append('operation',opr);
	    formData_FileUploading.append('versionPrefix',"A");
	    formData_FileUploading.append('versionSeperator',"-");
		formData_FileUploading.append('versionSuffix',"1");
		formData_FileUploading.append('remark',remark);
		formData_FileUploading.append('useSourceFile',"N");
		formData_FileUploading.append('isReplaceFolderName',rplc);
		formData_FileUploading.append('autoCorrectPdfProp',crctpdf);
		formData_FileUploading.append('nodeFolderName',foldername);
	    
		var xhr = new XMLHttpRequest();
		
	    xhr.open('POST', 'SaveNodeAttrAction.do?dndflag='+ dragFlag, true);

		 xhr.onload = function () {
 
	  			if (xhr.status == 200) {
				  
			$("#"+nodeId).html(foldername);
 			$("#"+nodeId).removeClass("draghover");
 	  		ext=fileName.substr(fileName.lastindexOf(".") + 1);
 	  		$("#dynatree-id-"+nodeId + " span").addClass(ext);
 	  		parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
 	  		//parent.document.getElementById("nodeFrame").src;
	 	  	 var treeiframe = parent.document.getElementById("nodeFrame");
	         treeiframe.src = treeiframe.src;
 	 		} 
	  		else {
		    	alert('An error occurred!');
	    	    $("#"+nodeId).html(foldername);
	    	 } 
		};
		xhr.send(formData_FileUploading);
		 }
		else
 		alert("Can't upload a folder,It must be a file");
}
function lockSeq(){
	alert("You can not do any activity in locked project.");
}
function hideFileInfoDialog()
{
	//onmouseout
	//debugger;
	$(".fileInfoDialog").hide(10);
	
}
var lastid="";
var cflag=1;
function getFileInfo(id)
{
	//debugger;
	var nodeid=id.substring(7,id.length);

	if(lastid==nodeid && cflag==1)
	{
		$('.fileInfoDialog').show();
		return false;
	}
	lastid=nodeid;
	//debugger;
	$.ajax
	({			
		url: "WorkspaceOpenNodeInfo_b.do?nodeId="+nodeid,
		beforeSend: function()
		{											
				//						
		},
		success: function(data) 
  		{
			cflag=1;
			$('.fileInfo').html(data);
	  		
			$('.fileInfoDialog').css("top",$("#"+nodeid).position().top);
			$('.fileInfoDialog').css("left",$("#"+nodeid).offset().left);
			if($('.fileInfo').find("span").html()=="")
			{
				$('.fileInfoDialog').show();
			}
			else{
				cflag=0;	
				$('.fileInfoDialog').hide();
			}
				//alert($('.fileInfo', window.parent.document).find("span").html());
	  		//debugger;
	  		//alert(data);
		},
		error:function(x,y,z)
		{
			//debugger;
		}	  		
	});
	
	
}

function dvCloseButton(){
	document.getElementById("myModal").style.display = "none";;
}
function ActiveSectionFromChkBox(){
	//debugger; 
	var url_string = window.location.href;
	var url = new URL(url_string);
	var workspaceId = url.searchParams.get("wsid");
	var selKeys =[]
	selKeys= $.map($("#tree").dynatree("getSelectedNodes"), function(node) {
	        return node.data.key;
	    });
	if(selKeys.length==0){
		alert("Please select section or node to active");
		return false;
	}	
	var flag=false;
	var strSelect = "";
	if (selKeys.length != null){
		
		for(i=0; i<selKeys.length; i++){
			var str = selKeys[i];
			if(strSelect == "")
			{
				strSelect = str ;
			}
			else
			{
				strSelect = strSelect + '_' + selKeys[i];
			}
		}
	}
	$.ajax(
 			{			
 				url: 'CheckActiveSectionFromChkBox_ex.do?workspaceId=' +workspaceId+'&nodeIds='+strSelect,
 				beforeSend:function(){
						//$("#tree").html("<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>");
 					htmlObj = document.createElement('div');
 					htmlObj.innerHTML = "<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>";
				 	mdl = document.getElementById("loadingDisplay");
			 		$('#viewSubDtl').html(htmlObj);
			 		window.parent.$('#loadingDisplay');
			 		mdl.style.display = "block";
 				},
 				success: function(data) 
 		  		{
 					
 					//debugger;
 					//alert(data);
 					/* if(data=="true"){
 						alert("Section activated successfully...");
 						window.location.reload();
 					} */
 					if(data=="activatedFlagFalse"){
 						alert("Section already active...");
 						mdl.style.display = "none";
 						flag=true;
 					}
 					/* else{
 						var temp = parent.document.getElementsByTagName("iframe");
 						var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;
 						
 						var htmlObject = document.createElement('div');
					 	htmlObject.innerHTML = data;
					 	var modal = document.getElementById("myModal");
				 		$('#viewSubDtl').html(htmlObject);
				 		window.parent.$('#myModal');
				 		modal.style.display = "block";
				 		var out="WorkspaceOpen.do?ws_id="+workspaceId;	
 					} */
 					
 				},
 				async: false,
 				error: function(data){
 			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
 			       mdl.style.display = "none";
 			    }
 			});
	
	if(flag==false){
	if (confirm("Are you sure you want to active selected section.?")){
		var remark=prompt("Please specify Reason for Change..");
		var regex = /^[a-zA-Z0-9\s]+$/;
		if (!regex.test(remark)) {
		  alert("Special characters are not allowed.");
		  mdl.style.display = "none";
			return false;
		}
		if(remark==null || remark==""){
			//debugger;
			//alert("Please specify reason for change.");
			mdl.style.display = "none";
			return false;
		}
	//}
//		return false;
	//}
	//debugger;
	var htmlObj 
	var mdl 
	   $.ajax(
     			{			
     				url: 'ActiveSectionFromChkBox_ex.do?workspaceId=' +workspaceId+'&remark='+remark+'&nodeIds='+strSelect,
     				beforeSend:function(){
 						//$("#tree").html("<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>");
     					htmlObj = document.createElement('div');
     					htmlObj.innerHTML = "<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>";
					 	mdl = document.getElementById("loadingDisplay");
				 		$('#viewSubDtl').html(htmlObj);
				 		window.parent.$('#loadingDisplay');
				 		mdl.style.display = "block";
     				},
     				success: function(data) 
     		  		{
     					
     					//debugger;
     					//alert(data);
     					if(data=="true"){
     						alert("Section activated successfully...");
     						window.location.reload();
     					}
     					else if(data=="activatedFlagFalse"){
     						alert("Section already active...");
     						mdl.style.display = "none";
     					}
     					else{
     						var temp = parent.document.getElementsByTagName("iframe");
     						var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;
     						
     						var htmlObject = document.createElement('div');
						 	htmlObject.innerHTML = data;
						 	var modal = document.getElementById("myModal");
					 		$('#viewSubDtl').html(htmlObject);
					 		window.parent.$('#myModal');
					 		modal.style.display = "block";
					 		var out="WorkspaceOpen.do?ws_id="+workspaceId;	
     					}
     					
     				},
     				async: false,
     				error: function(data){
     			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
     			    }
     			});
		}else{
			mdl.style.display = "none";
			return false;
		}
	}
}

function DeleteSectionFromChkBox(){
	//debugger; 
	var url_string = window.location.href;
	var url = new URL(url_string);
	var workspaceId = url.searchParams.get("wsid");
	var selKeys =[]
	selKeys= $.map($("#tree").dynatree("getSelectedNodes"), function(node) {
	        return node.data.key;
	    });
	if(selKeys.length==0){
		alert("Please select section or node to delete");
		return false;
	}
	
	var flag=false;
	var strSelect = "";
	if (selKeys.length != null){
		
		for(i=0; i<selKeys.length; i++){
			var str = selKeys[i];
			if(strSelect == "")
			{
				strSelect = str ;
			}
			else
			{
				strSelect = strSelect + '_' + selKeys[i];
			}
		}
	}
	//debugger;
	$.ajax(
 			{			
 				url: 'CheckDeleteSectionFromChkBox_ex.do?workspaceId=' +workspaceId+'&nodeIds='+strSelect,
 				beforeSend:function(){
						//$("#tree").html("<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>");
 					htmlObj = document.createElement('div');
 					htmlObj.innerHTML = "<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>";
				 	mdl = document.getElementById("loadingDisplay");
			 		$('#viewSubDtl').html(htmlObj);
			 		window.parent.$('#loadingDisplay');
			 		mdl.style.display = "block";
 				},
 				success: function(data) 
 		  		{
 					
 					//debugger;
 					//alert(data);
 					/* if(data=="true"){
 						alert("Section activated successfully...");
 						window.location.reload();
 					} */
 					if(data=="deletedFlagFalse"){
 						alert("Section already inactive...");
 						mdl.style.display = "none";
 						flag=true;
 					}
 					/* else{
 						var temp = parent.document.getElementsByTagName("iframe");
 						var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;
 						
 						var htmlObject = document.createElement('div');
					 	htmlObject.innerHTML = data;
					 	var modal = document.getElementById("myModal");
				 		$('#viewSubDtl').html(htmlObject);
				 		window.parent.$('#myModal');
				 		modal.style.display = "block";
				 		var out="WorkspaceOpen.do?ws_id="+workspaceId;	
 					} */
 					
 				},
 				async: false,
 				error: function(data){
 			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
 			       mdl.style.display = "none";
 			    }
 			});
	
	if(flag==false){
	if (confirm("Are you sure you want to inactive selected section.?")){
		var remark=prompt("Please specify Reason for Change..");
		var regex = /^[a-zA-Z0-9\s]+$/;
		if (!regex.test(remark)) {
		  alert("Special characters are not allowed.");
		  mdl.style.display = "none";
			return false;
		}
		if(remark==null || remark==""){
			//debugger;
			//alert("Please specify reason for change.");
			mdl.style.display = "none";
			return false;
		}
		//return false;
	//}
	//debugger;
	var strSelect = "";
	if (selKeys.length != null){
		
		for(i=0; i<selKeys.length; i++){
			var str = selKeys[i];
			if(strSelect == "")
			{
				strSelect = str ;
			}
			else
			{
				strSelect = strSelect + '_' + selKeys[i];
			}
		}
	}
	var htmlObj 
	var mdl 
	   $.ajax(
     			{			
     				url: 'DeleteSectionFromChkBox_ex.do?workspaceId='+workspaceId+'&remark='+remark+'&nodeIds='+strSelect,
     				beforeSend:function(){
     					htmlObj = document.createElement('div');
     					htmlObj.innerHTML = "<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>";
					 	mdl = document.getElementById("loadingDisplay");
				 		$('#viewSubDtl').html(htmlObj);
				 		window.parent.$('#loadingDisplay');
				 		mdl.style.display = "block";
 					},
     				success: function(data) 
     		  		{
     					//debugger;
     					//alert(data);
     					if(data=="true"){
     						alert("Section deactivated successfully...");
     						window.location.reload();
     					}
     					else if(data=="deletedFlagFalse"){
     						alert("Section already inactive...");
     						mdl.style.display = "none";
     					}
     					
     					else{
     						var temp = parent.document.getElementsByTagName("iframe");
     						var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;
     						
     						var htmlObject = document.createElement('div');
						 	htmlObject.innerHTML = data;
						 	var modal = document.getElementById("myModal");
					 		$('#viewSubDtl').html(htmlObject);
					 		window.parent.$('#myModal');
					 		modal.style.display = "block";
					 		var out="WorkspaceOpen.do?ws_id="+workspaceId;
					 		mdl.style.display = "none";
     					}
     					
     				},
     				async: false,
     				error: function(data){
     			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
     			    }
     			});
		}else{mdl.style.display = "none";return false;}
	}
}


function DeletedNodeDetail()
{
	//debugger;
	var wsid="<s:property value='wsid'/>";
	str="DeletedNodeDetail_b.do?workspaceId="+wsid;
	//str="DeletedNodeDetail_b.do";
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1000,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}

function showSendBackFiles()
{
	//debugger;
	var wsid="<s:property value='wsid'/>";
	str="ShowSendBackFiles_b.do?workspaceId="+wsid;
	//str="ShowSendBackFiles_b.do";
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}
function showDeviationFiles()
{
	//debugger;
	var wsid="<s:property value='wsid'/>";
	str="ShowDeviationFiles_b.do?workspaceId="+wsid;
	//str="ShowDeviationFiles_b.do";
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}
function Bulkadduser(){
	//debugger;
	var wsid="<s:property value='wsid'/>";
	str="BulkUserAllocation.do?workSpaceId="+wsid;
	newtab = window.open(str, "_blank");	
}

function ReplicateWorkspaceRights(){
	//debugger;
	var wsid="<s:property value='wsid'/>";
	str="ReplicateWorkspaceRights.do?workSpaceId="+wsid;
	newtab = window.open(str, "_blank");	
}

function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else var expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}

function checkArrays( arrA, arrB ){

    //check if lengths are different
    if(arrA.length !== arrB.length) return false;

    //slice so we do not effect the orginal
    //sort makes sure they are in order
    var cA = arrA; 
    var cB = arrB;
    var isDrop=false;

    for(var i=0;i<cA.length;i++){
         if(cA[i]!==cB[i]) return false;
    }

    return true;

}
var currSearchSpan;

$(document).ready(function() {

	//var workspaceId="<s:property value='#session.ws_id'/>";
	var workspaceId="<s:property value='wsid'/>";
	var dragdrop="<s:property value='allowDragandDrop'/>";
	var lastOpendWsId=readCookie("KNETOpendWSID");
	var persist=false;
	
	if(workspaceId==lastOpendWsId)
	{
		persist=true;	
	}
	else
	{
		createCookie("KNETOpendWSID",workspaceId,330);
	
	}

	var new_childIDs; //  after drag drop
	var old_childIDs; // before drag
	 var checkbox;
	 var userTypeForTree = "<s:property value='#session.usertypename'/>";
	 if(userTypeForTree=='WA'){
		 checkbox=true;
		 persist=false;
	 }
	 else{
		 checkbox=false;
		 persist=true;
	 }
	//debugger;
	var tree=$("#tree").dynatree(
	{
		persist: persist,
    	checkbox: checkbox,
      	selectMode: 3,
	  	//clickFolderMode: 3,
	  	//minExpandLevel: 1,
	  	generateIds: true, // Generate id attributes like <span id='dynatree-id-KEY'>
	    idPrefix: "dynatree-id-",
	    onActivate: function(dtnode) 
     	{
     		if( dtnode.data.url )
			{
				window.parent.frames['attributeFrame'].location=dtnode.data.url;
				$("#echoActive").text(dtnode.data.title);
			}
			//dtnode.toggleSelect();
		},
		onCreate: function(dtnode, nodeSpan)
		{
			//this will fire when node is render in tree.
			//debugger;
			nodeDropZone=document.getElementById(dtnode.li.lastChild.lastChild.lastChild.id);
			var status=dtnode.data.title.split(';')[8].substr(0,12).split(":")[1];
			if(status=='D'){
				console.log('Deleted Node For Admin');
				dtnode.span.style.backgroundColor="#e34949";
				//dtnode.span.lastChild.addClass("d-active");
			}
    		if(dtnode.data.isFolder) {
    			nodeDropZone.addEventListener('dragover', dragAndDrop, false);
    			nodeDropZone.addEventListener('drop', dragAndDrop, false);
    		}
    		else {
    			 if(dragdrop=="Yes"){
    				nodeDropZone.addEventListener('dragover', handleDragOver, false);
	        		nodeDropZone.addEventListener( 'dragleave', handleDragLeave, false );
    				nodeDropZone.addEventListener('drop', handleFileSelect, false);
    			 }
    		}
			
			//debugger;
				
		}, 		
		onPostInit: function(isReloading, isError) {

		//	debugger;
			 
		//	this.activateKey("ui-dynatree-id-72");
			//this.getRoot().activate();
		},
		onClick : function(node, e) {
           
         if ($(e.target).hasClass("dynatree-edit-icon")) {
            // $("#info").text("You clicked " + node + ",  url=" + node.url);
         }
         nodeDropZone=document.getElementById(node.data.key);
			if(node.data.isFolder) {
			nodeDropZone.addEventListener('dragover', dragAndDrop, false);
			nodeDropZone.addEventListener('drop', dragAndDrop, false);
		}
		else
		{
			 if(dragdrop=="Yes"){
				nodeDropZone.addEventListener('dragover', handleDragOver, false);
	        	nodeDropZone.addEventListener( 'dragleave', handleDragLeave, false );
				nodeDropZone.addEventListener('drop', handleFileSelect, false);
			 }
		}
     	},
     	dnd: {
     		revert: false,
     		preventVoidMoves: true, // Prevent dropping nodes 'before self', etc.
    		onDragStart: function(node) {
    			
    			return true;
    		},
			onDragStop: function(node) {
    			//debugger;
				if(node.childList!=null)
				{
					return false;
				}
    					
    			//if isDrop is true then location of node is changed,so need to change order in database
    			if(!isDrop)
    			{
        			return false;
    			}
				
					
    			var parent=node.parent;
    			
    			var childList=parent.childList;
				new_childIDs=new Array();
    			for(var i=0;i<childList.length;i++)
    			{
    				var a=childList[i].span;
    				var b=a.getElementsByTagName("label");
    				var c=b.item(0);
    				var id=c.getAttribute("id");
    				new_childIDs[new_childIDs.length]=id;
    			}

    			//here update database(iNodeNo)
    			
    			var temp=new_childIDs.toString();
    			$.ajax
				({			
					url: "WorkspaceOpenNodes_b.do?nodeIds="+temp,
					beforeSend: function()
					{											
							//						
					},
					success: function(data) 
			  		{
				  		//debugger;
				  		//alert(data);
					},
					error:function(x,y,z)
					{
						//debugger;
					}	  		
				});
    			
    			if(checkArrays(new_childIDs,old_childIDs))
				{
						//alert("same");
				}
				else
				{
						//alert("Not Same");
				}
				isDrop=false;
    			//childIds contains all ids that need to rearrang as per sequence in childList
				logMsg("new-tree.onDragStop(%o)", new_childIDs);
			},
			autoExpandMS: 1000,
			preventVoidMoves: true, // Prevent dropping nodes 'before self', etc.
			onDragEnter: function(node, sourceNode) {
				//debugger;
				if(node.parent !== sourceNode.parent)
					return false;
				
				return ["before", "after"];
			},
			onDragOver: function(node, sourceNode, hitMode) {
				logMsg("tree.onDragOver(%o, %o, %o)", node, sourceNode, hitMode);
				if(node.isDescendantOf(sourceNode)){
					return false;
				}
				
				
			},
			onDrop: function(node, sourceNode, hitMode, ui, draggable) {
				if(node.parent !== sourceNode.parent)
					return false;
				isDrop=true;
				var parent=node.parent;
    			var childList=parent.childList;
				old_childIDs=new Array();
    			for(var i=0;i<childList.length;i++)
    			{
    				var a=childList[i].span;
    				var b=a.getElementsByTagName("label");
    				var c=b.item(0);
    				var id=c.getAttribute("id");
    				old_childIDs[old_childIDs.length]=id;
    			}			
				
				//debugger;
				//$("span > a").attr('id')
				var a=node.parent.span;
				var b=a.getElementsByTagName("label");
				var c=b.item(0);
				var parentId=c.getAttribute("id");
				//alert(parentId);
				logMsg("OldOds-",old_childIDs);
				
				sourceNode.move(node, hitMode);
			},
			onDragLeave: function(node, sourceNode) {
				
				logMsg("tree.onDragLeave(%o, %o)", node, sourceNode);
			}
		}
        
    });	
	 function dragAndDrop(evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
	    }        

	function handleFileSelect(evt) {
		
		//debugger;
	    evt.stopPropagation();
	    evt.preventDefault();	    
	    var operation="new";//operation (new,append,replace)
	    //var lastPublishversion = "<s:property value='lastPublishedVersion'/>";
	    var lockSeqFlag = "<s:property value='lockSeqFlag'/>";
	    var userType = "<s:property value='#session.usertypename'/>";
	    var StageId;
	    var CurrentSeq;
	    var isFileHistory;
	    var isCreate;
	    var nodeName=this.innerHTML;//to get the nodename of particular node
	    var nodeId=evt.target.id;
	    var node = $.ui.dynatree.getNode( $('#'+evt.target.id));
	    var key="";
	    var value="";
	    var requiredflag;
	    var LockedNodeFlag;
	    var publishFlag;
	    //var foldername;
	    var nodetypeindi;
	    var pjcttype="${project_type}";
	    var nodedata = node.span.getElementsByTagName("a")[0].childNodes[0].nodeValue;
	    var spltdata=nodedata.split(";");
	    var fileName = evt.dataTransfer.files[0].name;
	    var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
	    var lbl_nodeName = '<s:property value="lbl_nodeName"/>';
	    if(lockSeqFlag=="true"){
	    	alert("You can not do any activity in locked project.");
	    	$("#"+nodeId).removeClass("draghover");
	    }
	    else{
	    	
	    	for (i = 0; i < fileName.length; i++)
	    	{
	 			strChar = fileName.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid document name. \n\nOnly alphabets, digits,dot,underscore and dash are allowed.");
			 		$("#"+nodeId).removeClass("draghover");
			    	return true; 			
	 				}	
		   	}
	    	
	     $.ajax
		({			
			url: "checkNodeRights_ex.do?ndId="+nodeId,
			success: function(data) 
	  		{
		  		//debugger;
		  		//alert(data);
	  			isCreate=data;
			},
			async: false	  		
		}); 
	     
	     $.ajax
			({			
				url: "checkNodeHistory_ex.do?ndId="+nodeId,
				success: function(data) 
		  		{
			  		//debugger;
			  		//alert(data);
		  			isFileHistory=data;
				},
				async: false	  		
			});
	     
	     $.ajax
			({			
				url: "checkStages_ex.do?ndId="+nodeId,
				success: function(data) 
		  		{
			  		//debugger;
			  		//alert(data);
			  		var temp = data.split(":");
			  		StageId = temp[0];
			  		CurrentSeq = temp[1];
			  		//alert(CurrentSeq);
				},
				async: false	  		
			});
	  
	  
	     for(var i=0;i<spltdata.length-1;i++){
	    	var snodedata = spltdata[i].split(":");
	    	for(var j=0; j<snodedata.length;j++){	    	
	    		key = spltdata[i].split(":")[0];
		    	value=spltdata[i].split(":")[1];
		    	
	    	}
	    	//debugger;
	    	if(key == "nodeTypeIndi"){
	    		nodetypeindi = value;
	    	}
	    	if (key == "folderName"){
	    		foldername = value;
 	    }
	    	if (key == "requiredFlag"){
	    		requiredflag = value;
 		}
	    	
	    	if (key == "LockedNodeFlag"){
	    		LockedNodeFlag = value;
 		}
	    	if(key == "publishFlag"){
	    		publishFlag = value;
	    	}
	    	//debugger;
	     }
	     //debugger;
	    	if(nodetypeindi=="S" && ((foldername.startsWith("stf") && foldername.endsWith(".xml")) || (foldername.startsWith("STF") && foldername.endsWith(".xml")) )){
	    		
	    		alert("Drag & drop not allowed on STF "+lbl_nodeName+".");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}   			
	    	else if(requiredflag=="S"){
	    		
	    		alert("Drag & drop not allowed in country specific node please repeat "+lbl_nodeName+ "and try again.");
		    	$("#"+nodeId).removeClass("draghover");
		    	return true;
	    	}	
	    	else if(userType!="WA" && userType!="Initiator"){
	    		alert("You don't have file uploading rights.");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}
	    	else if((userType=="WA" || userType=="Initiator") && isCreate=="false"){
    			alert("You don't have file uploading rights.");
    	    	$("#"+nodeId).removeClass("draghover");
    	   		return true;
    		}
	    	else if(LockedNodeFlag=="true"){
	    		if(StageId == "20" || StageId == "30" ){
		    		alert("Drag & drop not allowed, because document is on another stage.");
		    	    $("#"+nodeId).removeClass("draghover");
		    	   	return true;
		    	}
	    		else{
					alert(lbl_nodeName+" is already locked by user, please unlock the same and try again.");	    
					$("#"+nodeId).removeClass("draghover");
					return true;
	    		}
 			}else if(StageId == "20" || StageId == "30" ){
	    		alert("Drag & drop not allowed, because document is on another stage.");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}
 			 else if(StageId == "100" && CurrentSeq == ""){
 				alert("Drag & drop not allowed, because document is on another stage.");
		    	    $("#"+nodeId).removeClass("draghover");
		    	   	return true;
		    }
 			/*else if(!/^[A-Za-z0-9-.\\(\\)]+$/.test(fileName))
	    	{
				alert("Invalid File Name.. \n\nOnly Alphabets, Digits,(,) and '-' are allowed.");
				$("#"+nodeId).removeClass("draghover");
		    	return true;
	    	} */
	    	 else if(!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx") && !fileName.endsWith(".xls") 
	    			  && !fileName.endsWith(".xlsx") && !fileName.endsWith(".xsl") && !fileName.endsWith(".xslx") && !fileName.endsWith(".xpt")
	    			  && !fileName.endsWith(".zip") && !fileName.endsWith(".xml") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") 
	    			  && !fileName.endsWith(".msg") && !fileName.endsWith(".png") && !fileName.endsWith(".odt") && !fileName.endsWith(".html")
	    			  && !fileName.endsWith(".bmp") && !fileName.endsWith(".eml") && !fileName.endsWith(".ppt") && !fileName.endsWith(".pptx"))
	    	 {	 
		        alert("Please upload valid extension document.");
		    	$("#"+nodeId).removeClass("draghover");
		    	return true;
		    }
	    	else
 			{
	    		 tmpdialog(evt,nodeId,evt.dataTransfer.files,foldername,isFileHistory);
	  		}
	    }       
	  }

	window.addEventListener("dragover",function(e){
		  e = e || event;
		  e.preventDefault();
		},false);
	
	window.addEventListener("drop",function(e){
		  e = e || event;
		  e.preventDefault();
		},false);
	
	function handleDragLeave(evt){
		 nodeId=evt.target.id; 
		 $("#"+nodeId).removeClass("draghover");
	}
	
	function handleDragOver(evt) {
		nodeId=evt.target.id;  
	    evt.stopPropagation();
	    evt.preventDefault();
		$("#" + nodeId).addClass("draghover");
		
		
	  //  evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
	  }

	  function filedetail(nodeID) {
		  
		 // var arr_ext=['pdf','xml','doc','jpg','jpeg','bmp','zip','xls','rtf','ppt','png','xpt','odt','ods','txt','gif','au','mp2','mp3','mp4','wav','wma','avi','flv','mpeg','swf','wmv','html','f4v','fla','flv','swf','css','data','svg','xsd','xsl'];
		 var arr_ext=['pdf','doc','docx','xls','xlsx','xsl','xslx','xpt','zip','xml','jpg','jpeg','msg','png','odt','html','eml','ppt']; 
		 var extFlag=false;
		  for(var index=0;index<arr_ext.length;index++) {
			  extFlag=$("#dynatree-id-"+ nodeID +" span").hasClass(arr_ext[index]);
				if(extFlag)
					break;
		  }
		  
		  return extFlag;
	  }
	 $( "#searchField" ).keyup(function() {
		 
			//debugger;
			$("#tree").dynatree("getRoot").search($(this).val());
			console.log( "Handler for .keypress() called." );
	});
	
	$("#openAllProjects").click(function(){
		
		$('#allWorkspace', window.parent.document).toggle();
	
	});
	
    
	callForProjDocStatus();
	$('#toggleDiv').hide();
	$('#labelDiv').click(function() {
		  $('#toggleDiv').slideToggle('slow', function() {
		  });
		});
	});	



//$("#tree").dynatree("getTree").getNodeByKey("ui-dynatree-id-72").select();


//ui-dynatree-id-72

/* function sendMail(wsId){
	 $.ajax({
			
			 url : "ProjectNodeWiseMail_ex.do?workspaceId="+wsId,
			 success:function(data)
			 {
				 alert(data);
			 }
			 });
	
} */

function callForProjDocStatus()
{
	var lbl_nodeName = '<s:property value="lbl_nodeName"/>';
	 $.ajax({
		// url : "ProjDocStatus_ex.do?workspaceId=<s:property value='#session.ws_id'/>",
		   url : "ProjDocStatus_ex.do?workspaceId=<s:property value='wsid'/>&mode=1",
		 beforeSend: function()
			{ 
				$('#statusDetails').html('');								
			},
		 success:function(data)
		 {
				var str="";
				var leafCount="";
				var historyCount="";
				//debugger;	
				data = data.replaceAll('=',': ');
				var d = data.split('#');			
				for(var i=0;i<d.length;i++)
				{
					if (d[i].match("TotalNodes")) 
					{
						leafCount = d[i].split(': ')[1]; 
					}
					if(d[i].match("PerLeafNodes"))
					{
						//$('#labelDiv').html("<span>test</span>");
						$('#labelDiv').html($('#labelDiv').html()+"Approved: "+d[i].split(': ')[2]+"%");
						historyCount = d[i].split(',')[0].split(': ')[1];
					}
					else if(d[i].match("Per"))
					{
						var splstr = d[i].split(',');
						var finalStr ="<span>"+splstr[0].replace("Per","")+"%</span>";
						var perval = splstr[0].split(': ')[1];		
						//finalStr+= "<span> [ "+ splstr[1].split(': ')[1] + "/"+leafCount + " ]</span> <br/>" + "<div id=\"progress_container\"><div id=\"progress\" style=\"width:"+perval+"%\">"+perval+"</div></div>";
						finalStr+= "<span> [ "+ splstr[1].split(': ')[1] + "/"+leafCount + " ]</span> <br/>" + "<div class=\"progress\"><div class=\"progress-bar progress-bar-striped bg-approved\" role=\"progressbar\" style=\"width:"+perval+"%\" aria-valuenow=\"10\" aria-valuemin=\"0\" aria-valuemax=\"100\">"+perval+"</div></div>";
						str+=finalStr;
					}
				}
				str+="<br/><span>Total "+lbl_nodeName+ " with Documents: "+ historyCount+"<br/></span>";
				$('#statusDetails').html(str);				

		 }
		 });
}

function expand(){

	$("#tree").dynatree("getRoot").visit(function(node){
	      node.expand(true);
  });
}
function colaps(){
	
	 $("#tree").dynatree("getRoot").visit(function(node){
	      node.expand(false);
 });
}
function up(){
	$('#tree').animate({scrollTop:0 }, 100);
}
function down(){
	$('#tree').animate({scrollTop:$('#tree')[0].scrollHeight }, 100);

}

/*function callSearch()
{
	 //unhighlight(document.getElementById('tree'));
	 var color = '';
	 unhighliteAllTreeNode(document.getElementById('tree'),color); 
	//New Search
	localSearchHighlight('?h=' + document.searchhi.h.value,document.getElementById('tree'));
	var matchFound=false; 
	var check;
	//Scroll To First Search Term
	$('.searchword').each(function(){
		currSearchSpan = this;
		matchFound = true;

		$(this).parents('div').map(function (){ 
			if(this.firstChild.lastChild){
				
				var childNodeList = this.firstChild.lastChild.childNodes;
				for(i = 0 ; i < childNodeList.length; i++){
					var ele = childNodeList[i];
					if(ele.tagName == 'LABEL'){
						//alert(ele.innerHTML.charAt(0));
						$(ele).css('background-color', '#DBEEFF' );
						//if(ele.innerHTML.charAt(0) != '*' )
						//ele.innerHTML = "#"+ele.innerHTML;
					}
			}
			}
          return ''; 
       });

	
		var $elem = $('.searchword').parent();
		//	 alert($('.searchword').length);
			if( $elem.size() > 0 ){
	 			 var length=$('.searchword').length;
	 	 		 while(length > 0)
	 	 		 {
	 	 	 		 $elem=$elem.parent();
	 	 	 	 	 length--;
	 	 	 	 	 $elem.css('background-color', 'white' );
	 	 	 	 } 
			 }  
			return '';
	 		
		});
		if(!matchFound){
			document.searchhi.h.style.color='red';
		}
	}	*/
 </script>


</head>

<body>

<div class="fileInfoDialog"><span class="closebutton"
	onclick="hideFileInfoDialog();">close</span>
<div class="fileInfo"></div>

<div id="dialog-confirm" title="Drag & Drop">
<table>
	<%-- <tr>
	  <s:if test="lastPublishedVersion =='-999'">
		 <td colspan="2"> Operation : <select id = "oprtn">
	       <option value = "new">new</option>
	         </select>
	       </td>
	</tr> --%>
	<tr>
		<td colspan="2"><input type="checkbox" id="replcfilechkbox" name="replcfilechkbox"/> Replace eTMF Specific FolderName</td>
	</tr>
	<tr>
		<td colspan="2"><input type="checkbox" id="pdfchkbox" name="pdfchkbox" checked="checked" /> Auto Correct PDF Properties</td>
	</tr>
	<tr>
	<!-- 	<td>Remark: <input type="text" id="remark" name="remark" /></td> -->
		<td>Reason for Change </td>
		<td><textarea rows="4" cols="19" id="remark" name="remark"></textarea></td>
	</tr>
	
</table>
</div>

</div>


<div class="headercls">Project:  
	<%-- <s:if  test="project_name.length() > 17 ">
		<label style="font-size: 15px;" id="openAllProjects" title="<s:property value="project_name"/>">
			<s:property value="project_name.substring(0,15)" />...</label>
	</s:if> 
	<s:else> --%>
		<label style="font-size: 14px;" id="openAllProjects" title="<s:property value="project_name"/>">
		<s:property value="project_name" /></label>
	<%-- </s:else> --%>

<div style="float: right; cursor: pointer; margin-right: 5px;" id="labelDiv"
	title="Project Status"></div>
</div>

<div style="padding:0 0 10px 3px;"><font class="title">Client: &nbsp;</font>
<tr><td><label class="bdycls">${client_name}</label> <br /></td>
  <td><font class="title">Project Type: &nbsp;</font><label class="bdycls">${project_type}</label>
<br /></td>
<!--<td><font class="title">Location Name:&nbsp;</font><label class="bdycls">${location_name}</label></td> --%>

<!-- <td><input type="button" class="button" Value="Up" onclick="up()" style="margin-left: 33px;
		   padding-left: 9px; padding-right: 8px; position: fixed;"/></td>
<td><input type="button" class="button" Value="Down" onclick="down()"  style="margin-left: 69px;
		   padding-left: 9px; padding-right: 8px; position: fixed;"/></td> -->
</tr>
</div>
<div id="tree" class="fixed_possition"
	style="height: 450px; width: 100%; overflow: auto; border-top: 1px solid #669; float:left;">
<!-- <div id="tree" class="fixed_possition"
	style="height: 34.8em; width: 100%; overflow: auto; border-top: 1px solid #669; float:left;"> -->


<tr><td>
	<input id="searchField" placeholder="Search <s:property value="lbl_folderName"/>" type="text" style="margin-left: 2px; position: fixed; width: 154px;
	 z-index: 1; height: 27px;">${htmlCode}</td>
<div class="bg-position">
<td><img src="images/Down1.svg"  title="Expand" onclick="expand()" height="25px" width="25px" class="position-left" /></td>
<td><img src="images/uparr1.svg" title="Collapse" onclick="colaps()" height="25px" width="25px" class="position-right" style="left: 191px;"/></td> 
<td>
	<!-- <input type ="button" onclick = "DeletedNodeDetail();" style="position: fixed; margin-left: 200px;" value="Deleted File" class="button" id="DeletedNodeDetail"> -->
	<img src="images/Common/AuditTrials.svg" title="Active/Inactive ${ lbl_nodeName }" id="DeletedNodeDetail" onclick = "DeletedNodeDetail();" style="height: 28px;top: 15.9%;position: fixed;margin-left: 9%;width: 25px;" class="position-right" >
</td>
<td><img src="images/Common/send_back.svg" title="Send Back" onclick="showSendBackFiles()" height="25px" width="25px" style="position: fixed;  margin-left: 69px;" class="position-right" ></td>
<td><img src="images/Common/deviation.svg" title="Deviation Details" onclick="showDeviationFiles()" height="25px" width="25px" style="position: fixed; margin-left: 98px;"  class="position-right" ></td>
<s:if test ="showTrackingReport == true">
<td>
<a title="Show Report" style="float: left;margin-left: 14px;" href="ProjectTimelineTrackingById.do?workSpaceId=<s:property value='wsid'/>" target="_blank">
   <img src="images/Common/Project-tracking.svg" title="Show Project Tracking Report" height="25px" width="25px" style="position: fixed; margin-left: 127px;" class="position-right" ></a>
</td>
</s:if>
 <s:if test="#session.usertypename== 'WA' ">
<td><s:if test="lockSeqFlag==true">
	<img title="Active Section" style="height: 26px;top: 15.9%;position: fixed;margin-left: 33%;width: 25px;" src="images/Common/activate.svg" onclick="lockSeq()" height="18px" class="position-left"/>
</s:if>
<s:else>
		<!-- <input type ="button" onclick = "ActiveSectionFromChkBox();" style="top: 5%;position: fixed;margin-left: 64%;" value="Active" class="button" id="Active"> -->
		<img title="Active Section" style="height: 26px;top: 15.9%;position: fixed;margin-left: 33%;width: 25px;" src="images/Common/activate.svg" onclick="ActiveSectionFromChkBox()" height="18px" class="position-left"/>
</s:else>
</td>
<td>
<s:if test="lockSeqFlag==true">
	<img title="Inactive Section"style="height: 26px; top: 15.9%;position: fixed;margin-left: 40%;width: 25px;" src="images/Common/cancel.svg" onclick="lockSeq()" height="18px" class="position-left"/>
</s:if>
<s:else>
	<!-- <input type ="button" onclick = "DeleteSectionFromChkBox();" style="position: fixed;margin-left: 79%;top: 5%;" value="Inactive" class="button" id="Inactive"> -->
	<img title="Inactive Section"style="height: 26px; top: 15.9%;position: fixed;margin-left: 40%;width: 25px;" src="images/Common/cancel.svg" onclick="DeleteSectionFromChkBox()" height="18px" class="position-left"/>
</s:else>
</td>
<td>
<s:if test="lockSeqFlag==true">
	<img title="Bulk User Allocation" style="height: 26px; top: 15.9%;position: fixed;margin-left: 47%;width: 25px;" src="images/add-user-svgrepo-com.svg" onclick="lockSeq()" height="18px" class="position-left"/>
</s:if>
<s:else>
	<!-- <input type ="button" onclick = "DeleteSectionFromChkBox();" style="position: fixed;margin-left: 79%;top: 5%;" value="Inactive" class="button" id="Inactive"> -->
	<img title="Bulk User Allocation" style="height: 26px; top: 15.9%;position: fixed;margin-left: 47%;width: 25px;" src="images/add-user-svgrepo-com.svg" onclick="Bulkadduser()" height="18px" class="position-left"/>
</s:else>
</td>

<td>
<s:if test="lockSeqFlag==true">
	<img title="Replicate Rights" style="height: 28px;top: 15.9%;position: fixed;margin-left: 54%;width: 29px;" src="images/DnDRights.svg" onclick="lockSeq()" height="18px" class="position-left">
</s:if>
<s:else>
	<!-- <input type ="button" onclick = "DeleteSectionFromChkBox();" style="position: fixed;margin-left: 79%;top: 5%;" value="Inactive" class="button" id="Inactive"> -->
	<img title="Replicate Rights" style="height: 28px;top: 15.9%;position: fixed;margin-left: 54%;width: 29px;" src="images/DnDRights.svg" onclick="ReplicateWorkspaceRights()" height="18px" class="position-left">
</s:else>
</td>

</s:if>
 <%-- <s:if test ="#session.usertypename == 'WA'">
<td>
 <img src="images/Common/Project-tracking.png" title="Send Mail" onclick="return sendMail('<s:property value='wsid'/>');"
  height="" style="position: fixed; margin-left: 158px;"  class="position-right" >
</td>
</s:if>  --%>
</div>
<div id="myModal" class="modal" style="font-size: 12px;padding-top:50px;">
   
  <div class="modal-content" style="width: 95%; max-height: 450px; overflow-y: scroll;">
  <span style="color: #fff; font-size: 20px; background: #2e7eb9; font-family:Calibri; display: inline-block; width: 100%;text-align: left; padding-left: 20px; padding-bottom: 2px;"><b>Information</b>
  	<img alt="Close" title="Close" src="images/Common/Close.svg" onclick='dvCloseButton()' class='popupCloseButton' style="float:right"/>
  </span>
  
  	<div id="viewSubDtl"></div><br>	
  	<table>
  		<tr>
  			<td colspan="2"style="padding-left: 20px; font-size: 14px; font-weight: bold;">Selected section could not be deleted because some operation has been performed in the section.</td>
  		</tr>
  	</table>
  </div>
</div>

<div id="loadingDisplay" class="modal" style="font-size: 12px;padding-top:50px;background: transparent;">
	<center style='margin-left:20px; margin-top:160px;'>
		<img src="images/loading.gif" alt="loading ..." />
	<center>
</div>

</tr></div>
<div id="toggleDiv"
	style="max-height: 300px; width: 250px; border: 2px solid #0C3F62; font-family: calibri; 
	background:#e8f2ff; z-index:1; position: absolute; float: left; top: 24px; right:0px; ">
<div id="statusDetails" style="padding: 15px"></div>
</div>

<input type="hidden" name="nodeId" value="">
<input type="hidden" name="checkBoxName">

</body>

</html>