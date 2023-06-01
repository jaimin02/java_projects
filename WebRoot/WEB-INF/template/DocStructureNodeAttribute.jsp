<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<SCRIPT type="text/javascript">

function fileOpen(actionName)
{   
	//debugger;
	win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
}

function docFileOpen(actionName)
{   
	window.open(actionName, '_newtab');
}
	
 function DMSvalidate()
	{
		//debugger;
		var uploadFile = document.structureForm.uploadFile.value;
    	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
    	var index=uploadFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
    	var fileext = uploadFile.substring(index+1).toLowerCase();
    	var strChar;
    	if(uploadFile=="")
		{
				alert("Please Select Structure Document.");
				document.structureForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     		document.structureForm.uploadFile.focus();
				return false;
		}
    	
    	if(fileext!="doc" && fileext!="docx"){
			alert("Please upload valid extension(e.g..doc & .docx) Document.")
			return false;
		}
     	for (i = 0; i < uploadFile.length; i++)
    	{
 			strChar = uploadFile.charAt(i);
		 	if (strInvalidChars.indexOf(strChar)!= -1 && deltefilevalue==0)
				{
		 		alert("Invalid Document Name.. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
	      			document.structureForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	      			return false;  			
 				}
 				
	   	}
			//changePdfIcon();
		return true;
	} 
 /* function changePdfIcon()
 {
 	//debugger;
 	var CurrentNodeId=document.structureForm.nodeId.value;
 	var htmlDocObj = parent.document.getElementsByTagName("iframe");
 	var selectedHtmlDoc = htmlDocObj[0].contentDocument || htmlDocObj[0].contentWindow.document;
 	var htmlLabelElementObj = selectedHtmlDoc.getElementById(CurrentNodeId);
 	var uploadFile = document.structureForm.uploadFile.value;
 	var sp = uploadFile.split('\\');
 	var fname1=sp[sp.length-1];

 	var fileExtension ="";
 	var index=fname1.lastIndexOf('.');
 	
 	if(index==-1){
 		htmlLabelElementObj.innerText = htmlLabelElementObj.innerText;
 	}
 	if(index>0)
 	{
 		fileExtension=fname1.substring(index+1);
 		if(fileExtension == "docx"){
 			htmlLabelElementObj.parentNode.parentNode.className+=' doc';
 		}
 		else{
 			htmlLabelElementObj.parentNode.parentNode.className+=' '+fileExtension;
 		}
 		htmlLabelElementObj.innerText = fname1;
 	}
 	return true;
 } */

	function editNodeDetail(nodeId)
	{
		var templateId = document.StructureLinkForm.templateId.value;
		
		var nodeDisplayName = document.StructureLinkForm.displayName.value;
		
		var str="DocEditLeafNodesForStructure_b.do?templateId="+templateId + "&nodeId="+nodeId + "&nodeName="+nodeDisplayName;
		//win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no")	
		//win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));
	
		$.ajax({
			url: str,
							
			beforeSend: function()
			{ 
				$('#editNodeDiv').html('');								
			},
	  		success: function(data) 
	  		{			  		
				document.getElementById("editNodeDiv").innerHTML=data;
		  		//$('#flag').html(data);			
			}								
		});		
				
		return false;
	}

	/*function hide(str)
	{
		$('#'+str).slideToggle("slow");
	}*/	
	
	function show(RemarkDivId)
	{
		
		$('#RemarkDivId').show();
	} 	

	
	function addLeafNode(nodeId)
	{
		var templateId = document.StructureLinkForm.templateId.value;
		var nodeDisplayName = document.StructureLinkForm.displayName.value;
		var str = "AddLeafNodesForStructure.do?templateId="+templateId + "&nodeId="+nodeId + "&nodeName="+nodeDisplayName;
		//win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=800,resizable=no,titlebar=no")				
		//win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(300/2));
		
		$.ajax({
			url: str,
							
			beforeSend: function()
			{ 
				$('#targetDiv').html('');								
			},
	  		success: function(data) 
	  		{			  		
				document.getElementById("targetDiv").innerHTML=data;
		  		//$('#flag').html(data);			
			}								
		});		
		
		return false;
	}		
	
	function assignRights(nodeId)
		{
			var str="AssignTemplateRights.do?nodeId="+nodeId;
			//win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=420,resizable=no,titlebar=no")	
			//win3.moveTo(screen.availWidth/2-(420/2),screen.availHeight/2-(500/2));

			$.ajax({
				url: str,
								
				beforeSend: function()
				{ 
				document.getElementById("targetDiv").innerHTML="";				
				},
		  		success: function(data) 
		  		{			  		
					document.getElementById("targetDiv").innerHTML=data;
			  		//$('#flag').html(data);			
				}								
			});		
			
			return false;	
		}

	function callOpenTree(nodeId)
	 {
		
		var str = "templateNodeAttribute.do?nodeId="+nodeId;
		//document.structureForm.action = strAction;
		//document.structureForm.submit();
	 	
	 	$.ajax({
			url: str,
							
			beforeSend: function()
			{ 
	 			$('#targetDiv').html('');				
			},
	  		success: function(data) 
	  		{			  		
				document.getElementById("targetDiv").innerHTML=data;
		  					
			}								
		});		
		
		return false; 
	 }

	function nodeattr(requestId,nodeId,displayName)
	{			
		$.ajax({			
			url: 'StructureNodeAttributeAction.do?requestId='+requestId+'&nodeId='+nodeId+'&displayName='+displayName,
							
			beforeSend: function()
			{ 
				$('#targetDiv').html('');								
			},
	  		success: function(data) 
	  		{			  		
		  		$('#targetDiv').html(data);			
			}								
		});					
	}

	$(document).ready(function(){
		 editNodeDetail(${nodeId});
	});
</SCRIPT>

<script language="javascript" type="text/javascript">
	function setPosition(){
		window.moveTo(100,100);
		return true;
	}
	
	function validation(operation)
	{	
		if(document.createDeleteStructureForm.nodeName.value=="")
		{
			alert("Enter Node Name.");
   			document.createDeleteStructureForm.nodeName.style.backgroundColor="#FFE6F7"; 
     		document.createDeleteStructureForm.nodeName.focus();
    		return false;
		}
		if(document.createDeleteStructureForm.nodeName.value.length>250)
		{
			alert("Node Name cannot be of more then 250 charactars..");
   			document.createDeleteStructureForm.nodeName.style.backgroundColor="#FFE6F7"; 
     		document.createDeleteStructureForm.nodeName.focus();
    		return false;
		}
		if(document.createDeleteStructureForm.nodeDisplayName.value=="")
		{
			alert("Enter Node Display Name.");
			document.createDeleteStructureForm.nodeDisplayName.style.backgroundColor="#FFE6F7"; 
     		document.createDeleteStructureForm.nodeDisplayName.focus();
    		return false;
		}
		if(document.createDeleteStructureForm.nodeDisplayName.value.length>250)
		{
			alert("Node display Name cannot be of more then 250 charactars..");
			document.createDeleteStructureForm.nodeDisplayName.style.backgroundColor="#FFE6F7"; 
     		document.createDeleteStructureForm.nodeDisplayName.focus();
    		return false;
		}
		
		if(document.createDeleteStructureForm.folderName.value=="")
		{
			alert("Enter Folder Name.");
			document.createDeleteStructureForm.folderName.style.backgroundColor="#FFE6F7"; 
     		document.createDeleteStructureForm.folderName.focus();
    		return false;		
		}

		document.createDeleteStructureForm.operation.value = operation;
		//alert(operation);
		return true;
	}
	
	function closeWindow()
	{
		window.close();
	}
	
	function callonBlur(elementId)
  	{
  		elementId.style.backgroundColor='white';
  	}	
  	
  	 function refreshParent()
     {
     	opener.history.go(-1);
		self.close();
		return true;
     }
     
</script>

<SCRIPT type="text/javascript">
	
	function closeWindow()
	{
		window.close();
	}
	
	function DeleteNode()
	{
		var nodeId = document.structureForm.nodeId.value;
		var nodeName = document.structureForm.nodeName.value;
		var okCancel = confirm("Do you really want to Delete "+nodeName+" ?");
		if(okCancel == true)
		{
			window.location.href="DeleteStructureNode.do?nodeId="+nodeId;
			return  true;
		}
		else
			return false;
	}

</SCRIPT>

</head>


<body>
<!--<div class="headercls">
 		Structure Node Attributes Detail
 	</div> -->
<div class="headercls"><b><s:property value="lbl_nodeName"/> Name:&nbsp;<s:property value="getTemplateDetail.get(0).nodeDisplayName" /> </b></div>

<div id="RemarkDivId" class="bdycls" style="display: none"><s:if
	test="getTemplateDetail.get(0).remark.trim() !=''">
	<%-- <s:property value="getTemplateDetail.get(0).remark" escape="false" /> --%>
</s:if> <s:else>
			<!-- Guidelines not attached. -->
		</s:else></div>

<div id="editNodeDiv" style="width: 100%; float: left;"></div>
<div style="width: 100%; float: left;" align="center"><br />
<br />
</div>

<s:form method="post" name="StructureLinkForm">

	<s:hidden name="templateId"></s:hidden>
	<s:hidden name="displayName"></s:hidden>

</s:form>
<br />
<br />
<br />
<div id="targetDiv" style="width: 100%; float: left;" align="left"></div>

</body>
</html>
