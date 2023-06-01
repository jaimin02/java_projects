<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>

<script type="text/javascript">
			function  openInEditMode(workSpaceId,locationName,remark)
			{
				var args = '';
				args = "workSpaceId="+workSpaceId+"&locationName="+locationName+"&remark="+remark;
				$.ajax(
				{			
					url: 'EditProject.do?' + args,
					success: function(data) 
			  		{
						
						$('#popupContact').html(data);
			  		}		
	 			});				 
				//centering with css
				centerPopup();
				//load popup
				loadPopup();
			}
	 	</script>

<script type="text/javascript">
		function deleteWorkspace(wsId,wsName)
		{
			var okCancel = confirm("Do you really want to delete '" + wsName + "' ?");
			if(okCancel == true)
			{
				$.ajax(
				{			
					url: 'DeleteWorkspace_ex.do?workspaceId=' + wsId,
			  		success: function(data) 
			  		{
				  		alert(data);
						location.reload();
					}										
				});
			}
			else
				return false;
		}
		
		function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}
	
		function validate()
		{ 
			if(check(document.saveProjectForm.nodeName.value)==false) 
			{
		     	document.saveProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
	     		document.saveProjectForm.nodeName.focus();
				return false;
			}
			else if(checkFolderName(document.saveProjectForm.folderName.value)==false) 
			{
		     	document.saveProjectForm.folderName.style.backgroundColor="#FFE6F7"; 
	     		document.saveProjectForm.folderName.focus();
				return false;
			}
			return true;
		}
		
		function checkFolderName(strString)
		{
		     strString=trim(strString);
		   	 if(strString.indexOf("\\")!=-1){alert("Invalid Project Document Prefix." + " " + "\\ is not allowed");return false;}
			    
			    var strInvalidChars = "/\^$#:~%@&*`!;'\"\+\=({}<>?)[]-";
		    	var strChar;
		   	  	var blnResult = true;
		   	  	 
			if(strString.length < 2)
			{
				alert("Project Documemt Prefix must be greater then 2 characters..");
				document.saveProjectForm.folderName.style.backgroundColor="#FFE6F7"; 
				document.saveProjectForm.folderName.focus();
				return false;
			}	
			if(strString.length > 10)
			{
				alert("Project Documemt Prefix must be less then 10 characters..");
				document.saveProjectForm.folderName.style.backgroundColor="#FFE6F7"; 
				document.saveProjectForm.folderName.focus();
				return false;
			}	
		   	if (strString.length== 0) {alert ("Please Specify Project Document Prefix"); return false;}
		      	for (i = 0; i < strString.length && blnResult == true; i++)
		     	 {
		 			strChar = strString.charAt(i);
					 if (strInvalidChars.indexOf(strChar)!= -1)
					 {
		      			blnResult = false;
		       			if(strChar == ' ')
	      				{
	      					alert("Invalid Project Document Prefix " + " 'Space' is not allowed.." );
	      				}
	      				else
	      				{
	       					alert("Invalid Project Document Prefix" + " " + strChar + "  is not allowed.." );
	       				}
	       				break;
		 			}
		      	}
				return blnResult;
		 }
		
		function check(strString)
		{
		     strString=trim(strString);
		   	 if(strString.indexOf("\\")!=-1){alert("Invalid Project name." + " " + "\\ is not allowed");return false;}
			    
			    var strInvalidChars = "/\^$#:~%@&*`!;'\"\+\=({}<>?)[].|";
		    	var strChar;
		   	  	var blnResult = true;
		   	  	 
			if(strString.length < 5)
			{
				alert("Project Name must be greater then 5 characters..");
				document.saveProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
				document.saveProjectForm.nodeName.focus();
				return false;
			}	
			else if(strString.length > 60)
			{
				alert("Project Name must be less then 60 characters..");
				document.saveProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
				document.saveProjectForm.nodeName.focus();
				return false;
			}
				
		   	if (strString.length== 0)
			{
				alert ("Please Specify Project Name..");
				return false;
			}
		   	
		      	for (i = 0; i < strString.length && blnResult == true; i++)
		     	 {
		 			strChar = strString.charAt(i);
					 if (strInvalidChars.indexOf(strChar)!= -1)
					 {
		      			blnResult = false;
		       			if(strChar == ' ')
	      				{
	      					alert("Invalid Project Name" + " 'Space' is not allowed.." );
	      				}
	      				else
	      				{
	       					alert("Invalid Project Name" + " " + strChar + "  is not allowed.." );
	       				}
	       				break;
		 			}
		      	}
		      	if(!strString.match(/^([a-zA-Z0-9])/) || !strString.match(/([a-zA-Z0-9])$/))
       			{			       			
					alert("First or Last character can not be a special character..");
					blnResult = false;
	       		}
				return blnResult;
				
		 }

		function detectReturnKey(evt) 
		{ 
	 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
	  		{
	  			return document.saveProjectForm.createProjectBtn.onclick();
	  		} 
		} 

		function cls()
		{		
			document.getElementById('message').innerHTML='';		
		}
		document.onkeypress = detectReturnKey;

		$(document).ready(function()
		{
			$('.target').change(function()
			{
				var correct=true;
					if(check(document.saveProjectForm.nodeName.value)==false) 
				{
					document.saveProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
					document.saveProjectForm.nodeName.focus();
					correct=false;
				}											     	
				var workspace=document.getElementById('nodeName').value;
				document.getElementById('nodeName').value =trim(workspace);
				workspace=document.getElementById('nodeName').value; 
				var urlOfAction="WorkSpaceExistsForUpdate.do";
				var dataofAction="workSpaceDesc="+workspace;
				if (correct==true)
				{
					$.ajax({
					type: "GET", 
					url: urlOfAction, 
					data: dataofAction, 
					cache: false,
					dataType:'text',
					success: function(response)
					{				
						$('#message').html(response);
						if(response.indexOf('Available') == -1)
						{
							document.getElementById('createProjectBtn').disabled='disabled';
							return false;
						}
						else if(response.indexOf('Available') != -1)
						{
							document.getElementById('createProjectBtn').disabled='';
							return true;
						}						
					}
				});
			}
		});	
	});

</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
</div>
<div align="center"><img
	src="images/Header_Images/DMSUserRepository/Manage_Projects.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="saveProjectForm"
	id="saveProjectForm" method="post" action="SaveProject_ex">
	<table align="center" width="100%" cellspacing="0">

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Project Name&nbsp;</td>
			<td align="left"><s:textfield name="nodeName" id="nodeName"
				size="25" cssClass="target" onkeypress="cls();"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Project Document
			Prefix&nbsp;</td>
			<td align="left"><s:textfield name="folderName" id="folderName"
				size="25" value="" cssClass="target"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Reference
			Template&nbsp;</td>
			<td align="left"><s:select list="templateList" id="templateDtl"
				name="templateDtl" headerKey="-999##No Template"
				headerValue="No Template" listKey="templateId+'##'+templateDesc"
				listValue="templateDesc">
			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><s:textfield name="remark" id="remark"
				size="25" value="" cssClass="target"></s:textfield></td>

		</tr>

		<tr>
			<td></td>
			<td align="left"><s:submit name="createProjectBtn"
				id="createProjectBtn" value="Create Project" cssClass="button"
				onclick="return validate();"></s:submit></td>
		</tr>

	</table>
	<br>
</s:form></div>
<div>
<table class="datatable" width="95%" cellspacing="1">

	<thead>
		<tr>
			<th>#</th>
			<th>Project Name</th>
			<th>Prefix</th>
			<th>Reference Template</th>
			<th>Remarks</th>
			<th>Created On</th>
			<th>Created By</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>

	<tbody>
		<s:iterator value="workspaceMstList" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="#status.count" /></td>
				<td><s:property value="rootNodeDtl.nodeName" /></td>
				<td><s:property value="rootNodeDtl.folderName" /></td>
				<td><s:property value="templateDesc" /></td>
				<td><s:property value="rootNodeDtl.remark" /></td>
				<td><s:date name="createdOn" format="dd-MMM-yyyy HH:MM" /></td>
				<td><s:property value="lastaccessedUserName" /></td>
				<td>
				<div align="center"><a href="javascript:void(0);"
					onclick="openInEditMode('<s:property value="workSpaceId"/>','<s:property value="workSpaceName"/>','<s:property value="remark"/>');"
					title="Edit"> <img border="0px" alt="Edit"
					src="images/Common/edit.png" height="18px" width="18px"> </a></div>
				</td>
				<td>
				<div align="center"><img border="0px" alt="Delete"
					src="images/Common/delete.png" height="18px" width="18px"
					onclick="deleteWorkspace('<s:property value="workSpaceId"/>','<s:property value="rootNodeDtl.nodeName"/>')">
				</div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br />
</div>
</div>
</div>
<div id="backgroundPopup"></div>
<div id="popupContact" style="width: 400px; height: 190px;"></div>
</body>
</html>