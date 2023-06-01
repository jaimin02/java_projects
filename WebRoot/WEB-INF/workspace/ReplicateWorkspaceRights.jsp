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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />

<STYLE>
BODY {
	FONT: 10pt Verdana, sans-serif;
	scrollbar-arrow-color: blue;
	scrollbar-face-color: #e7e7e7;
	scrollbar-3dlight-color: #a0a0a0;
	scrollbar-darkshadow-color: #888888;
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
	/* padding-top: 35px !important; */
}
.position-right{
	position: fixed;
    right: 0px;
    padding-right: 110px;
    background: #fff;
    padding-top: 3px;
    left: 180px;}


.position-left{
    padding-top: 3px;
    position: fixed;
    left: 162px;
    background: #fff;}
    
    #treeLeft .dynatree-container{height:295px; overflow:auto;}
    #treeRight .dynatree-container{height:255px; overflow:auto;}
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


#treeLeft,#treeRight{
	margin-top: 10px;
	margin-bottom : 10px;
	border: 1px; 
	border-color: #5A8AA9;
	border-style: solid;
	/*height: 432px;
	 width: 49%; */
	overflow: auto;
}

/* #DeletedNodeDetail{
	padding-right: 6px !important;
    margin-left: 63px !important;
 } */
</style><link
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
<script src="<%=request.getContextPath()%>/js/newtree/jquery.cookie.js"
	type="text/javascript"></script>
	
	
<script
	src='<%=request.getContextPath()%>/js/newtree/jquery.dynatree.js'
	type='text/javascript'></script>
	
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<link
	href='<%=request.getContextPath() %>/js/jquery/jquery-ui-1.8.0.min.css'
	rel='stylesheet' type='text/css' />

<script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>

<link href="<%=request.getContextPath()%>/css/fSelect.css"
	rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>	
	
	
<script type='text/javascript'>

var opr="new";
var rplc='Y';
var crctpdf='Y'; 
var remark='';
var fNameToDisplay='';



function tmpdialog(evt,nodeId,FileObj,foldername,isFileHistory,fileName,filePath) {
	/* var opr="new";
	var rplc='Y';
	var crctpdf='Y'; */ 
	debugger;
	 document.getElementById("remark").value='';
	 $("#replcfilechkbox").prop("checked", false);
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
        		  alert("Please specify Reason for Change..");
        		  document.getElementById("remark").style.backgroundColor="#FFE6F7"; 
           		  document.getElementById("remark").focus();
        		  return false;
        		}       	
        	dropFile(evt,FileObj,nodeId,opr,rplc,crctpdf,foldername,remark,fileName,filePath);
        			        	
          $( this ).dialog( "close" );
        }
      }
    });
}

function dropFile(evt,FileObj,nodeId,opr,rplc,crctpdf,foldername,remark,fileName,filePath){
	debugger;
	$("#"+nodeId).removeClass("draghover");
	$("#" + nodeId).addClass("loadingImage");
	
		var dragFlag=true; //to check whether file upload through dnd or locknode(dnd:true)
		//var files = FileObj; // FileList object.
		//var fileName=files[0].name;
	    var ext=fileName.indexOf(".");//to check if folder or file
	    
		if(ext>0) {

	    var formData_FileUploading=new FormData();
	    formData_FileUploading.append('uploadFile', '');
	    formData_FileUploading.append('repositoryFilePath', 'true');
	    formData_FileUploading.append('filePath', filePath);
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
				  debugger;
				$("#"+nodeId).html(foldername);
 			$("#"+nodeId).removeClass("draghover");
 	  		ext=fileName.substr(fileName.lastIndexOf(".") + 1);
 	  		$("#dynatree-id-"+nodeId + " span").addClass(ext);
 	  		if(rplc=='Y'){
 	  			$("#"+nodeId)[0].innerHTML = fileName.substring(0,fileName.lastIndexOf('_'))+"."+ext;
 	  			//$("#"+nodeId)[0].innerHTML=fileName;
 	  		}
 	  		//parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
 	  		//parent.document.getElementById("nodeFrame").src;
	 	  	/*  var treeiframe = parent.document.getElementById("nodeFrame");
	         treeiframe.src = treeiframe.src; */
	         /* var Flag=true;
	 	  	 var des; */
		 	 var treeiframe = parent.document.getElementById("nodeFrame");
		 	 /* des="";
		 	 des='?Flag=' +Flag+'&nodeID='+nodeId;
		 	 treeiframe.src=treeiframe.src.split('?')[0];
		     treeiframe.src += des; */
 	 		} 
	  			else {
		    		  alert('An error occurred!');
	    	    	  $("#"+nodeId).html(foldername);
	    	    } 
	  		$("#" + nodeId).removeClass("loadingImage");
		};
		xhr.send(formData_FileUploading);
		 }
		else
 		alert("Can't upload a folder,It must be a file");
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
function SyncTree(){
	//debugger;
	$("#treeLeft").dynatree("destroy");
	//$('#tree').html($("#tree").text());
	//$("#tree").dynatree("destroy");
	//$("#tree").html($("#tree").text());
	var workspaceId="<s:property value='#session.ws_id'/>";
	var dragdrop="<s:property value='allowDragandDrop'/>";
	var lastOpendWsId=readCookie("KNETOpendWSID");
	var persist=false;
	
	//if(workspaceId==lastOpendWsId)
	//{
		persist=false;	
	//}
	$(function(){
		$("#treeLeft").dynatree(
			{
				persist: persist,
		    	checkbox: false,
		      	selectMode: 3,
			  	clickFolderMode: 3,
			  	minExpandLevel: 1,
			  	generateIds: true, // Generate id attributes like <span id='dynatree-id-KEY'>
			    idPrefix: "dynatree-id-",
			    onActivate: function(dtnode) 
		     	{
		     		if( dtnode.data.url )
					{
						//window.parent.frames['attributeFrame'].location=dtnode.data.url;
						//$("#echoActive").text(dtnode.data.title);
					}
					dtnode.toggleSelect();
				},
				onCreate: function(dtnode, nodeSpan)
				{
					//debugger;
					//this will fire when node is render in tree.
					//$("#" + dtnode.data.key).addClass("dynatree-node dynatree-folder dynatree-expanded dynatree-lastsib dynatree-selected dynatree-exp-el dynatree-ico-ef");
					//$("#dynatree-id-"+dtnode.data.key + " span").addClass("dynatree-node dynatree-folder dynatree-has-children dynatree-lastsib dynatree-ico-cf");
					nodeDropZone=document.getElementById(dtnode.li.lastChild.lastChild.lastChild.id);
					
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
					//debugger;
		           
		         if ($(e.target).hasClass("dynatree-edit-icon")) {
		            // $("#info").text("You clicked " + node + ",  url=" + node.url);
		         }
		         
		         nodeDropZone=document.getElementById(node.data.key);
		          var workSpaceId="<s:property value='#session.ws_id'/>";
		         var nodeId=node.data.key;
		         if(node.span.attributes[0].nodeValue == 
		        	    "dynatree-node dynatree-expanded dynatree-lastsib dynatree-selected dynatree-active dynatree-exp-el dynatree-ico-e" ||
		        	 node.span.attributes[0].nodeValue ==
		        	    "dynatree-node dynatree-expanded dynatree-lastsib dynatree-selected dynatree-active dynatree-exp-el dynatree-ico-e dynatree-focused" ||
		        	 node.span.attributes[0].nodeValue ==
		        		"dynatree-node dynatree-lastsib dynatree-selected dynatree-exp-cl dynatree-ico-c" ||
		        	 node.span.attributes[0].nodeValue ==
		        		"dynatree-node dynatree-selected dynatree-exp-c dynatree-ico-c" ||
		        	node.span.attributes[0].nodeValue ==
				 		"dynatree-node dynatree-expanded dynatree-lastsib dynatree-active dynatree-exp-el dynatree-ico-e dynatree-focused" ||
				 	node.span.attributes[0].nodeValue ==
				 		"dynatree-node dynatree-expanded dynatree-lastsib dynatree-active dynatree-exp-el dynatree-ico-e" ||
				 	node.span.attributes[0].nodeValue ==
			     	 	"dynatree-node dynatree-lastsib dynatree-active dynatree-exp-cl dynatree-ico-c" ||
			     	node.span.attributes[0].nodeValue ==
			     		"dynatree-node dynatree-expanded dynatree-selected dynatree-exp-e dynatree-ico-e" ||
			     	node.span.attributes[0].nodeValue ==
			     		"dynatree-node dynatree-lastsib dynatree-selected dynatree-active dynatree-exp-cl dynatree-ico-c dynatree-focused" ||
			     	node.span.attributes[0].nodeValue ==
						"dynatree-node dynatree-expanded dynatree-lastsib dynatree-exp-el dynatree-ico-e dynatree-active dynatree-selected" ||
					node.span.attributes[0].nodeValue ==
						"dynatree-node dynatree-exp-c dynatree-ico-c" ||
					node.span.attributes[0].nodeValue ==
						"dynatree-node dynatree-lastsib dynatree-exp-cl dynatree-ico-c dynatree-active dynatree-selected dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-exp-e dynatree-ico-e dynatree-active dynatree-selected dynatree-focused" ||
				    node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-selected dynatree-active dynatree-exp-e dynatree-ico-e dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-exp-e dynatree-ico-e dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-lastsib dynatree-exp-el dynatree-ico-e" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-exp-e dynatree-ico-e" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-lastsib dynatree-exp-el dynatree-ico-e dynatree-active dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
				        "dynatree-node dynatree-expanded dynatree-exp-e dynatree-ico-e dynatree-selected" ||
				    node.span.attributes[0].nodeValue ==
				        "dynatree-node dynatree-expanded dynatree-exp-e dynatree-ico-e dynatree-active dynatree-focused" ||
				    node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-exp-e dynatree-ico-e dynatree-active dynatree-selected dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-active dynatree-exp-c dynatree-ico-c dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-lastsib dynatree-selected dynatree-exp-el dynatree-ico-e" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-lastsib dynatree-exp-cl dynatree-ico-c" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-selected dynatree-active dynatree-exp-c dynatree-ico-c dynatree-focused" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-selected dynatree-active dynatree-exp-c dynatree-ico-c" ||
					node.span.attributes[0].nodeValue ==
					    "dynatree-node dynatree-expanded dynatree-active dynatree-exp-e dynatree-ico-e dynatree-focused")
		        	{
		          var attrFlag;
		         $.ajax(
		      			{			
		      				url: 'CheckAttribute_ex.do?workSpaceId=' + workSpaceId+'&nodeId='+nodeId,
		      				beforeSend: function()
		      				{
		      					
		      					//alert(" "+this.url);
		      						//$('#tree').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");	
		      						
		      				},
		      				success: function(data) 
		      		  		{
		      					//debugger;
		      					//alert(data);
		      					//$("#tree").dynatree("destroy");
		      					attrFlag=data;
		      					//$('#AutoSyncTree').html(data);
		      					//SyncTree();
		      				},
		      				async: false,
		      				error: function(data){
		      			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
		      			    }
		      			}); 
		         //debugger;
		         if(attrFlag=="True"){ 
		        	 //debugger;
		          $.ajax(
		        			{			
		        				url: 'GetAllChilds_ex.do?workSpaceId=' + workSpaceId+'&nodeId='+nodeId,
		        				beforeSend: function()
		        				{
		        					//alert(" "+this.url);
		        					//$("#tree").dynatree("destroy");
		        					//$("#AutoSyncTree").dynatree("destroy");
		      						//$("#tree").text("");
		      						debugger;
		        					$("#treeLeft").html("<center style='margin-left:20px; margin-top:160px;'><img src=\"images/loading.gif\" alt=\"loading ...\" /><center>");								
		        				},
		        				success: function(data) 
		        		  		{
		        					//debugger;
		        					console.log(data);
		        					//$("tree").empty();
		        					//$('#tree').remove('#togglediv')
		        					//$('#AutoSyncTree').html(data);
		        					$("#treeLeft").html(data);
		        					SyncTree();
		        				},
		        				error: function(data){
		        			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
		        			    }
		        			});
		         }
				}
		          
		         
		         
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
						debugger;alert("Enterting drag enter");
						if(node.parent !== sourceNode.parent)
							return false;
						
						return ["before", "after"];
					},
					onDragOver: function(node, sourceNode, hitMode) {
						debugger;
						logMsg("tree.onDragOver(%o, %o, %o)", node, sourceNode, hitMode);
						if(node.isDescendantOf(sourceNode)){
							return false;
						}
						
						
					},
					onDrop: function(node, sourceNode, hitMode, ui, draggable) {
						debugger;
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
						debugger;
						logMsg("tree.onDragLeave(%o, %o)", node, sourceNode);
					}
				}
		        
		    });
	});
	 function dragAndDrop(evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
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
		//debugger;
		 nodeId=evt.target.id; 
		 $("#"+nodeId).removeClass("draghover");
	}
	
	function handleDragOver(evt) {
		//debugger;
		nodeId=evt.target.id;  
	    evt.stopPropagation();
	    evt.preventDefault();
		$("#" + nodeId).addClass("draghover");
		
		
	  //  evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
	  }
	
function handleFileSelect(evt) {
		
		debugger;
	    evt.stopPropagation();
	    evt.preventDefault();	    
	    var operation="new";//operation (new,append,replace)
	    //var lastPublishversion = "<s:property value='lastPublishedVersion'/>";
	    var lockSeqFlag = "<s:property value='lockSeqFlag'/>";
	    var userType = "<s:property value='#session.usertypename'/>";
	    var StageId;
	    var isNodeLockedFlag;
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
	    //var fileName = evt.dataTransfer.files[0].name;
	    var lngth=evt.dataTransfer.getData('URL').split("/").length;
	    var fileName = evt.dataTransfer.getData('URL').split("/")[lngth-1];
	    var filePath="//";
	    
	    for(var i=2;i<lngth;i++){
	    	if(i==lngth-1)
	    		filePath+=evt.dataTransfer.getData('URL').split("/")[i];
	    	else
	    		filePath+=evt.dataTransfer.getData('URL').split("/")[i]+"/";
	    }
	    
	    var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
	    
	    if(lockSeqFlag=="true"){
	    	alert("You can not do any activity in locked project...");
	    	$("#"+nodeId).removeClass("draghover");
	    }
	    else{
	    	debugger;
	    	for (i = 0; i < fileName.length; i++)
	    	{
	 			strChar = fileName.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid File Name.. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
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
	     $.ajax
			({			
				url: "checkLockNode_ex.do?ndId="+nodeId,
				success: function(data) 
		  		{
			  		//debugger;
			  		//alert(data);
			  		isNodeLockedFlag = data;
				},
				async: false	  		
			});
	  
	  
	     for(var i=0;i<spltdata.length-1;i++){
	    	var snodedata = spltdata[i].split(":");
	    	for(var j=0; j<snodedata.length;j++){	    	
	    		key = spltdata[i].split(":")[0];
		    	value=spltdata[i].split(":")[1];
	    	}
	    	debugger;
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
	    	debugger;
	     }
	     debugger;
	    	if(nodetypeindi=="S" && ((foldername.startsWith("stf") && foldername.endsWith(".xml")) || (foldername.startsWith("STF") && foldername.endsWith(".xml")) )){
	    		
	    		alert("Drag & Drop Not allowed on STF node...!");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}   			
	    	else if(requiredflag=="S"){
	    		
	    		alert("Drag & Drop Not allowed in Country specific node please repeat node and try again...!");
		    	$("#"+nodeId).removeClass("draghover");
		    	return true;
	    	}	
	    	/* else if(userType!="WA" && userType!="Initiator"){
	    		alert("You don't have file uploading rights...!");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	} */
	    	else if((userType=="WA" || userType=="WU") && isCreate=="false"){
    			alert("You don't have file uploading rights...!");
    	    	$("#"+nodeId).removeClass("draghover");
    	   		return true;
    		}
	    	else if(isNodeLockedFlag=="Y"){
	    		if(StageId == "20" || StageId == "30" ){
		    		alert("Drag & Drop not allowed, because file is on another stage...!");
		    	    $("#"+nodeId).removeClass("draghover");
		    	   	return true;
		    	}
	    		else{
					alert("Node is already locked by user, please unlock the same and try again....!");	    
					$("#"+nodeId).removeClass("draghover");
					return true;
	    		}
 			}else if(StageId == "20" || StageId == "30" ){
	    		alert("Drag & Drop not allowed, because file is on another stage...!");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}
 			 else if(StageId == "100" && CurrentSeq == ""){
 				alert("Drag & Drop not allowed, because file is on another stage...!");
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
		        alert("Please upload valid extension file..!");
		    	$("#"+nodeId).removeClass("draghover");
		    	return true;
		    }
	    	else
 			{
	    		debugger;
	    		 tmpdialog(evt,nodeId,evt.dataTransfer.files,foldername,isFileHistory,fileName,filePath);
	  		}
	    }       
	  }
}
var currSearchSpan;

$(document).ready(function() {
	
	
	//console.log(<s:property value="dTreeSecond" />);
	
	$('.userList').fSelect({
		    	    placeholder: 'Select User',
		    	    numDisplayed: 3,
		    	    overflowText: '{n} selected',
		    	    noResultsText: 'No results found',
		    	    searchText: 'Search',
		    	    showSearch: true,
		    	    
		    	});
	
	//$('#treeRight').html($("#treeRight").text());
	var workspaceId="<s:property value='#session.ws_id'/>";
	var dragdrop="<s:property value='allowDragandDrop'/>";
	var lastOpendWsId=readCookie("KNETOpendWSID");
	var persist=false;
	
	//if(workspaceId==lastOpendWsId)
	//{
		persist=false;	
//	}
	//else
	//{
		createCookie("KNETOpendWSID",workspaceId,330);
	
	//}

	var new_childIDs; //  after drag drop
	var old_childIDs; // before drag
	//debugger;
	var treeLeft=$("#treeLeft").dynatree(
	{
		persist: persist,
    	checkbox: true,
      	selectMode: 3,
	  	clickFolderMode: 3,
	  	minExpandLevel: 1,
	  	generateIds: true, // Generate id attributes like <span id='dynatree-id-KEY'>
	    idPrefix: "dynatree-id-",
	    onActivate: function(dtnode) 
     	{
     		if( dtnode.data.url )
			{
				//window.parent.frames['attributeFrame'].location=dtnode.data.url;
				//$("#echoActive").text(dtnode.data.title);
			}
			dtnode.toggleSelect();
		},
		onCreate: function(dtnode, nodeSpan)
		{
			//this will fire when node is render in tree.
			nodeDropZone=document.getElementById(dtnode.li.lastChild.lastChild.lastChild.id);
			
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
				debugger;//alert("On dragEnter");
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
	$("#destinationProject").dynatree( {
		title : "Select Reference File",
		rootVisible : true,
		checkbox: true,
      	selectMode: 1,

		onActivate : function(dtnode) {
			/* debugger;
			var str = dtnode.data.href.replace("file:", "");
			//var str = content.str(/\n/g, ' ');
			//str="file:"+str;
			var url='openfile.do?fileWithPath='+str;
			//alert(url);
			 $.ajax
				({			
					url: "checkFileExists_ex.do?fileCheckPath="+dtnode.data.href,
					success: function(data) 
	  				{
		  				//debugger;
		  				//alert(data);
	  					if(data=="true")
	  						window.open(url,"_blank");
	  					else
	  						alert("File does not exist..!!");
					},
					async: false	  		
		});  */
			
			
		},
		
		onDeactivate : function(dtnode) {

		},

		onRender : function(node, nodeSpan) {
		//var str = node.data.href.replace("file:", "localexplorer:");
			if ($(nodeSpan).find("a").attr('href')) {
			var newpath = $(nodeSpan).find("a").attr('href');
			setTimeout(function() {

				$(nodeSpan).find("a").attr('href', newpath);

			},1500);

			}
		 
			
		},

		onClick : function(node, event) {
			if(!node.data.isFolder)
			{
				$("#hiddenAddress").val(node.data.href);
				if($("#hiddenAddress").val(node.data.href).find('LocalExplorer:'))
						$("#hiddenAddress").val($("input").val().replace('LocalExplorer:',''));
				setTimeout(function() {
					$("#hiddenAddress").select();
					$("#hiddenAddress").focus();
					$("#openFile").attr('href',"LocalExplorer:"+node.data.href);
					
				},500);
			}
		},
		onFocus : function(dtnode) {

		},
		onBlur : function(dtnode) {

		},
		onSelect : function(flag, dtnode){
			var selKeys =[]
			selKeys= $.map($("#destinationProject").dynatree("getSelectedNodes"), function(node) {
			        return node.data.key;
			    });
			if(selKeys.length==0){
				document.getElementById('edtBtn').style.display="none";
				fileNameToChange="";
			}
			if(selKeys.length>0){
				if(dtnode.li.lastChild.lastChild.innerHTML.endsWith('.pdf' || '.PDF' || '.docx' || '.DOCX' || '.DOC' || '.DOC'))
					{
					document.getElementById('edtBtn').style.display="block";
					fileNameToChange=dtnode.li.lastChild.lastChild.href.split('/')[dtnode.li.lastChild.lastChild.href.split('/').length-1];
					fileNameToDisplay=dtnode.li.lastChild.lastChild.innerHTML;
					}
			}
		}
		
	});
	 function dragAndDrop(evt) {
	        evt.stopPropagation();
	        evt.preventDefault();
	    }        

	function handleFileSelect(evt) {
		
		debugger;
	    evt.stopPropagation();
	    evt.preventDefault();	    
	    var operation="new";//operation (new,append,replace)
	    //var lastPublishversion = "<s:property value='lastPublishedVersion'/>";
	    var lockSeqFlag = "<s:property value='lockSeqFlag'/>";
	    var userType = "<s:property value='#session.usertypename'/>";
	    var StageId;
	    var isNodeLockedFlag;
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
	    //var spltdata=nodedata.split(";");
	    //var fileName = evt.dataTransfer.files[0].name;
		var lngth=evt.dataTransfer.getData('URL').split("/").length;
	    var fileName = evt.dataTransfer.getData('URL').split("/")[lngth-1];
	    
 		var filePath="//";
	    
	    for(var i=2;i<lngth;i++){
	    	if(i==lngth-1)
	    		filePath+=evt.dataTransfer.getData('URL').split("/")[i];
	    	else
	    		filePath+=evt.dataTransfer.getData('URL').split("/")[i];
	    }
	    
	    
	    var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
	    
	    if(lockSeqFlag=="true"){
	    	alert("You can not do any activity in locked project...");
	    	$("#"+nodeId).removeClass("draghover");
	    }
	    else{
	    	
	    	for (i = 0; i < fileName.length; i++)
	    	{
	 			strChar = fileName.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
			 		alert("Invalid File Name.. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
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
	     $.ajax
			({			
				url: "checkLockNode_ex.do?ndId="+nodeId,
				success: function(data) 
		  		{
			  		//debugger;
			  		//alert(data);
			  		isNodeLockedFlag = data;
				},
				async: false	  		
			});
	  
	  
	     for(var i=0;i<spltdata.length-1;i++){
	    	var snodedata = spltdata[i].split(":");
	    	for(var j=0; j<snodedata.length;j++){	    	
	    		key = spltdata[i].split(":")[0];
		    	value=spltdata[i].split(":")[1];
		    	
	    	}
	    	debugger;
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
	     debugger;
	    	if(nodetypeindi=="S" && ((foldername.startsWith("stf") && foldername.endsWith(".xml")) || (foldername.startsWith("STF") && foldername.endsWith(".xml")) )){
	    		
	    		alert("Drag & Drop Not allowed on STF node...!");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}   			
	    	else if(requiredflag=="S"){
	    		
	    		alert("Drag & Drop Not allowed in Country specific node please repeat node and try again...!");
		    	$("#"+nodeId).removeClass("draghover");
		    	return true;
	    	}	
	    	/* else if(userType!="WA" && userType!="Initiator"){
	    		alert("You don't have file uploading rights...!");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	} */
	    	else if((userType=="WA" || userType=="WU") && isCreate=="false"){
    			alert("You don't have file uploading rights...!");
    	    	$("#"+nodeId).removeClass("draghover");
    	   		return true;
    		}
	    	else if(isNodeLockedFlag=="Y"){
	    		if(StageId == "20" || StageId == "30" ){
		    		alert("Drag & Drop not allowed, because file is on another stage...!");
		    	    $("#"+nodeId).removeClass("draghover");
		    	   	return true;
		    	}
	    		else{
					alert("Node is already locked by user, please unlock the same and try again....!");	    
					$("#"+nodeId).removeClass("draghover");
					return true;
	    		}
 			}else if(StageId == "20" || StageId == "30" ){
	    		alert("Drag & Drop not allowed, because file is on another stage...!");
	    	    $("#"+nodeId).removeClass("draghover");
	    	   	return true;
	    	}
 			 else if(StageId == "100" && CurrentSeq == ""){
 				alert("Drag & Drop not allowed, because file is on another stage...!");
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
		        alert("Please upload valid extension file..!");
		    	$("#"+nodeId).removeClass("draghover");
		    	return true;
		    }
	    	else
 			{
	    		debugger;
	    		 tmpdialog(evt,nodeId,evt.dataTransfer.files,foldername,isFileHistory,fileName,filePath);
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

	  
	
	});


function openPopupForName(){
	var urlParams = new URLSearchParams(window.location.search);
	var workspaceId = urlParams.get('workSpaceId');
	str="ChangeRespsitoryFileName_b.do?wsId="+workspaceId+"&fileNameToChange="+fileNameToChange+"&fileNameToDisplay="+fileNameToDisplay;
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=350,width=600,resizable=yes,titlebar=no");
	win3.moveTo((screen.availWidth/2)-(600/2),screen.availHeight/2-(350/2));
	return true;
}

function getDestinationProject(id){
	//debugger;
	if(id.selectedIndex==0)
	{
		alert('Please Select Destination Project');		
	}
	else{	
		var wsId = id.value;
		debugger;
			$.ajax({
				url : 'showDestinationProject_ex.do?workSpaceId='+ wsId,				
				beforeSend: function()
				{
					$('#destinationProject').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
				},
				success: function(data) 
		  		{
					//debugger;
					//$('#showUserDtl').html("");
					var opt;		
					
		    		if(data.length > 0)
			    	{
		    			//debugger;
		    			prepareDestinationTree(data);
		    			//document.getElementById('destinationProject').innerHTML = data; 
		    				    				
					}	
		    		else{
		    			opt = 'No Data found';
		    			document.getElementById('destinationProject').innerHTML = opt;
		    		}
		  		}	 
			});		 
		}
}

function ReplicateRights(){
	var url_string = window.location.href;
	var url = new URL(url_string);
	var workspaceId = url.searchParams.get("workSpaceId");
	var selKeys =[]
	selKeys= $.map($("#treeLeft").dynatree("getSelectedNodes"), function(node) {
	        return node.data.key;
	    });
	if(selKeys.length==0){
		alert("Please select document from source project...");
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
	debugger;
	/* $.ajax(
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
 					} 
 					
 				},
 				async: false,
 				error: function(data){
 			        alert('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + data.responseText);
 			       mdl.style.display = "none";
 			    }
 			}); */
	
	if(flag==false){
	//if (confirm("Are you sure you want to inactive selected section.?")){
		//var remark=prompt("Please specify Reason for Change..");
	/* 	
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
	} */
	var htmlObj 
	var mdl 
	var destWorkSpaceId = document.getElementById('wsList').value;
	if(document.getElementById('wsList').value ==-1){
		alert("Please Destination Project");
		return false;
	}
	   $.ajax(
     			{			
     				url: 'ReplicateRights_ex.do?workSpaceId='+workspaceId+'&nodeIds='+strSelect+'&destWorkSpaceId='+destWorkSpaceId,
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
     					var info=data.split("_")[0];
     					if(info=="noRights"){
     						alert("Cannot copy rights on selected node...");
     						mdl.style.display = "none";
     					}
     					
     					else if(info=="noDiv"){
     						alert("Rights copied successfully...");
     						mdl.style.display = "none";
     					}
     					else if(info=="true"){
     						/* alert("Section deactivated successfully...");
     						mdl.style.display = "none"; */
     						var content=data.split("_")[1];
     						var htmlObject = document.createElement('div');
						 	htmlObject.innerHTML = content;
						 	var modal = document.getElementById("myModal");
					 		$('#viewSubDtl').html(htmlObject);
					 		window.parent.$('#myModal');
					 		modal.style.display = "block";
					 		var out="WorkspaceOpen.do?ws_id="+workspaceId;
					 		mdl.style.display = "none";
     						
     						//window.location.reload();
     					}
     					else if(info=="false"){
     						var info=data.split(":")[1];
     						alert(info);
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
		//}else{mdl.style.display = "none";return false;}
	}
}


function dvCloseButton(){
	document.getElementById("myModal").style.display = "none";;
}

 </script>


</head>

<body>

<%-- <div style=" margin-left: 20px; margin-right: 20px;">
		 --%>
		
<br />
<div class="container-fluid">
<div class="col-md-12">
<div align="center">
<div class="boxborder"><div class="all_title"><b style="float:left">Project Detail</b></div>
		<!-- <table class="paddingtable" cellspacing="0" width="100%;"
			bordercolor="#EBEBEB">

			<tr class="headercls" onclick="hide('ProjectDetailBox')">
				<td width="95%">Project Detail</td>
				
			</tr>
		</table> -->
	<s:form cssStyle="text-align: left;">
		<div id="ProjectDetailBox" style="border: 1px solid #669;">
		<table class="paddingtable" width="100%;">
			<tr>
				<td width="25%" class="title"><b>Project Name</b></td>
				<td width=""><font color="#c00000"><b>${project_name}</b></font>
				</td>
			</tr>

			<tr>
				<td width="25%" class="title"><b>Project Type</b></td>
				<td width=""><font color="#c00000"><b>${project_type}</b></font>
				</td>
				
			</tr>

			<tr>
				<td width="25%" class="title"><b>Client Name</b></td>
				<td width=""><font color="#c00000"><b>${client_name}</b></font>
				</td>
			</tr>

		</table>
		</div>
		</s:form>
		
		

<div style="">
	<label for="sourceTree" class="title" style="float:left;margin-top:10px; margin-left: 20%;">Source Project </label> 
	<label for="Select Repository File" class="title" style="float: right; margin-top:10px; margin-right: 18%;">Destination Project </label>
</div>
<input type="button" id="edtBtn" value="Edit" name="editButton" style="display: none;margin-left: 94%;
	   position: absolute;margin-top: 4%;" class="button" onclick="openPopupForName();">
<table align="center" width="100%" style="">
<tr>
	<td id="treeLeft" style="width:48%; vertical-align: top;">
			${htmlCode}
	</td>
	
	<td style="text-align: center; width:4%">
		<input type="button" class="button" value=">>" name="buttonName" onclick="ReplicateRights();"></input>
	</td> 
		
	<td id="treeRight" style="width:48%; vertical-align: top;">
		<div>
			<table style="width:100%;">
				<tr>
					<td style="padding-left:15px">
						<s:select id="wsList" name="wsList" list="getWorkspaceDetail" listKey="workSpaceId"
									 cssClass="userList" listValue="workSpaceDesc" onchange="getDestinationProject(this)" headerKey="-1" headerValue="Select Destination Project">
						</s:select>
					</td>
				</tr>
				<tr>
					<td id="destinationProject">
					</td>
				</tr>
			</table>
		</div>
	</td>	
	<!-- <td id="treeWsSelection" rowspan="2">
	
	</td> -->
</tr>

</table>


<input type="hidden" name="nodeId" value="">
<input type="hidden" name="checkBoxName">



<div id="myModal" class="modal" style="font-size: 12px;padding-top:50px;">
   
  <div class="modal-content" style="width: 95%; max-height: 450px; overflow-y: scroll;margin-top: 150px;">
  <span style="color: #fff; font-size: 20px; background: #2e7eb9; font-family:Calibri; display: inline-block; width: 100%;text-align: left; padding-left: 20px; padding-bottom: 2px;"><b>Information</b>
  	<img alt="Close" title="Close" src="images/Common/Close.svg" onclick='dvCloseButton()' class='popupCloseButton' style="margin-top: 5px;float:right;"/>
  </span>
  
  	<div id="viewSubDtl"></div><br>	
  	<table>
  		<tr>
  			<td colspan="2"style="padding-left: 20px; font-size: 14px; font-weight: bold;">Rights on above node(s) could not be copied due to following reason : 
  			<br> 1) <!-- because --> Some operation has been performed or 
  			<br> 2) Node(s) are not available or 
  			<br> 3) Rights on source project has not allocated.</td>
  		</tr>
  	</table>
  </div>
</div>

<div id="loadingDisplay" class="modal" style="font-size: 12px;padding-top:50px;background: transparent;">
	<center style='margin-left:20px; margin-top:160px;'>
		<img src="images/loading.gif" alt="loading ..." />
	<center>
</div>
		</div>
		</div>
		</div>
		
		</div>
		
<script>

function prepareDestinationTree(data){
	$('#destinationProject').html(data);
	$("#destinationProject").dynatree("destroy");
	
	$("#destinationProject").dynatree( {
		title : "Select Reference File",
		rootVisible : true,
      	selectMode: 1,

		onActivate : function(dtnode) {
			/* debugger;
			var str = dtnode.data.href.replace("file:", "");
			//var str = content.str(/\n/g, ' ');
			//str="file:"+str;
			var url='openfile.do?fileWithPath='+str;
			//alert(url);
			 $.ajax
				({			
					url: "checkFileExists_ex.do?fileCheckPath="+dtnode.data.href,
					success: function(data) 
	  				{
		  				//debugger;
		  				//alert(data);
	  					if(data=="true")
	  						window.open(url,"_blank");
	  					else
	  						alert("File does not exist..!!");
					},
					async: false	  		
		});  */
			
			
		},
		
		onDeactivate : function(dtnode) {

		},

		onRender : function(node, nodeSpan) {
		//var str = node.data.href.replace("file:", "localexplorer:");
			if ($(nodeSpan).find("a").attr('href')) {
			var newpath = $(nodeSpan).find("a").attr('href');
			setTimeout(function() {

				$(nodeSpan).find("a").attr('href', newpath);

			},1500);

			}
		 
			
		},

		onClick : function(node, event) {
			if(!node.data.isFolder)
			{
				$("#hiddenAddress").val(node.data.href);
				if($("#hiddenAddress").val(node.data.href).find('LocalExplorer:'))
						$("#hiddenAddress").val($("input").val().replace('LocalExplorer:',''));
				setTimeout(function() {
					$("#hiddenAddress").select();
					$("#hiddenAddress").focus();
					$("#openFile").attr('href',"LocalExplorer:"+node.data.href);
					
				},500);
			}
		},
		onFocus : function(dtnode) {

		},
		onBlur : function(dtnode) {

		},
		onSelect : function(flag, dtnode){
			var selKeys =[]
			selKeys= $.map($("#destinationProject").dynatree("getSelectedNodes"), function(node) {
			        return node.data.key;
			    });
			if(selKeys.length==0){
				document.getElementById('edtBtn').style.display="none";
				fileNameToChange="";
			}
			if(selKeys.length>0){
				if(dtnode.li.lastChild.lastChild.innerHTML.endsWith('.pdf' || '.PDF' || '.docx' || '.DOCX' || '.DOC' || '.DOC'))
					{
					document.getElementById('edtBtn').style.display="block";
					fileNameToChange=dtnode.li.lastChild.lastChild.href.split('/')[dtnode.li.lastChild.lastChild.href.split('/').length-1];
					fileNameToDisplay=dtnode.li.lastChild.lastChild.innerHTML;
					}
			}
		}
		
	});
}

</script>
</body>
</html>