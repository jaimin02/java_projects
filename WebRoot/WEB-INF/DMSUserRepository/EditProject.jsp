<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>


<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$('#popupContactClose').click(function(){
				disablePopup();
			});
				
		});

		function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}

		
		function validate()
		{ 
			if(check(document.editProjectForm.nodeName.value)==false) 
			{
		     	document.editProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
	     		document.editProjectForm.nodeName.focus();
				return false;
			}
			else if(checkFolderName(document.editProjectForm.folderName.value)==false) 
			{
		     	document.editProjectForm.folderName.style.backgroundColor="#FFE6F7"; 
	     		document.editProjectForm.folderName.focus();
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
			    
			    var strInvalidChars = "/\^$#:~%@&*`!;'\"\+\=({}<>?)[].";
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

		
		function checkEdit(strString)
		{
		     strString=trim(strString);
		   	 if(strString.indexOf("\\")!=-1){alert("Invalid Project name." + " " + "\\ is not allowed");return false;}
			    
			    var strInvalidChars = "/\^$#:~%@&*`!;'\"\+\=({}<>?)[]|";
		    	var strChar;
		   	  	var blnResult = true;
		   	  	 
			if(strString.length < 5)
			{
				alert("Project Name must be greater then 5 characters");
				document.editProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
				document.editProjectForm.nodeName.focus();
				return false;
			}
			if(strString.length > 60)
			{
				alert("Project Name must be less then 5 characters");
				document.editProjectForm.nodeName.style.backgroundColor="#FFE6F7"; 
				document.editProjectForm.nodeName.focus();
				return false;
			}	
		   	if (strString.length== 0) {alert ("Please Specify Project Name"); return false;}
		      	for (i = 0; i < strString.length && blnResult == true; i++)
		     	 {
		 			strChar = strString.charAt(i);
					 if (strInvalidChars.indexOf(strChar)!= -1)
					 {
		      			blnResult = false;
		       			if(strChar == ' ')
	      				{
	      					alert("Invalid Project Name" + " 'Space' is not allowed" );
	      				}
	      				else
	      				{
	       					alert("Invalid Project Name" + " " + strChar + "  is not allowed" );
	       				}
	       				break;
		 			}
		      	}
				return blnResult;
		 }

		function detectReturnKey(evt) 
		{ 
	 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
	  		{
	  			return document.editProjectForm.updateProject.onclick();
	  		} 
		} 

		function cls()
		{		
			document.getElementById('editMessage').innerHTML='';		
		}
		document.onkeypress = detectReturnKey;

		$(document).ready(function()
				{	
					$('.editNodeName').change(function()
					{
						var correct=true;
						if(checkEdit(document.editProjectForm.editNodeNameId.value)==false) 
						{
							document.editProjectForm.editNodeNameId.style.backgroundColor="#FFE6F7"; 
							document.editProjectForm.editNodeNameId.focus();
							correct=false;
						}	
						
						var workspace=document.getElementById('editNodeNameId').value;
						document.getElementById('editNodeNameId').value =trim(workspace);
						workspace=document.getElementById('editNodeNameId').value;
						
						var urlOfAction="WorkSpaceExistsForUpdate.do";
						var dataofAction="workSpaceDesc="+workspace;
						var originalNodeName = "<s:property value='dtoNodeDetail.nodeName'/>";
						cls();
						if(correct==true)
						{
							if(originalNodeName != workspace)
							{
								$.ajax({
									type: "GET", 
					   				url: urlOfAction, 
					   				data: dataofAction, 
					   				cache: false,
					   				dataType:'text',
									success: function(response){
										$('#editMessage').html(response);
										if(response.indexOf('Available') != -1)
										{
											document.getElementById('updateProject').disabled='';
											return false;
										}
										
										else if(response.indexOf('Available') == -1)
										{
											document.getElementById('updateProject').disabled='disabled';
											return false;
										}
										
									}
								});
							}
							else
								$('#editMessage').html("<font color='green'>"+ originalNodeName +" is available. </font>");
						}
					});
				});
				
	</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;"
	id="editMessage"></div>
<div><a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">Edit
Project Detail</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 150px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:form action="UpdateProject" method="post"
	name="editProjectForm">


	<table width="100%">

		<tr>
			<td class="title" align="right" width="40%"
				style="padding: 2px; padding-right: 8px;" id="workSpaceDesc">Project
			Name</td>
			<td align="left"><input name="nodeName" id="editNodeNameId"
				class="editNodeName" onkeypress="cls();"
				value="<s:property value='dtoNodeDetail.nodeName'/>"></td>
		</tr>

		<tr>
			<td class="title" align="right" width="40%"
				style="padding: 2px; padding-right: 8px;">Project Document
			Prefix</td>
			<td align="left"><input name="folderName"
				value="<s:property value='dtoNodeDetail.folderName'/>"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Reference
			Template&nbsp;</td>
			<td align="left"><select id="templateDtl" name="templateDtl">
				<option value="-999##No Template">No Template</option>
				<s:iterator value="templateList">
					<option
						<s:if test="selectedeTemplate == templateId+'##'+templateDesc">
												selected="selected"
											</s:if>
						value="<s:property value="templateId"/>##<s:property value="templateDesc"/>"><s:property
						value="templateDesc" /></option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="40%"
				style="padding: 2px; padding-right: 8px;">Remarks</td>
			<td align="left"><input name="remark"
				value="<s:property value='dtoNodeDetail.remark'/>"></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><s:submit value="Update Project"
				id="updateProject" cssClass="button" onclick="return validate();"></s:submit></td>
		</tr>
	</table>
	<s:hidden name="workSpaceId"></s:hidden>
</s:form></div>
<br>
</div>
</body>
</html>