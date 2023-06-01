<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head theme="ajax" />
<script>
	function trim(str)
	{
		str = str.replace( /^\s+/g, "" );// strip leading
		return str.replace( /\s+$/g, "" );// strip trailing
	}
	function check(strString)
	{
		 strString=trim(strString);
	   	 if(strString.indexOf("\\")!=-1){alert("Invalid Project name." + " " + "\\ is not allowed");return false;}
		    
		    var strInvalidChars = "/\^$#:~%@&*`!;'\"\+\=({}<>?)[]";
	    	var strChar;
	   	  	var blnResult = true;
	   	  	 
		if(strString.length > 60)
		{
			alert("Project Name must not be greater then 60 char");
			document.importProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
	     	document.importProjectForm.workSpaceDesc.focus();
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
	 			}
	      	}
			return blnResult;
	 }
	   	function validateFiles(){
		
		var fileelement = document.getElementById('uploadFile');
				var err = false;
				if(fileelement.value == ''){
					return false;
				}else{
					var fileName = fileelement.value.substring(fileelement.value.lastIndexOf('\\')+1)
					var fileExt = fileName.substring(fileName.lastIndexOf('.')+1);
					if(fileExt != 'zip'){
						err = true;
					}
				}
				if(err){
					
					alert('Please Upload a Zip file..');
					fileelement.style.backgroundColor="#FFE6F7"; 
					document.importProjectForm.reset();
					return false; 
				}
		return true;
			}

	 function validate()
	{
     	if(!validateFiles())
		{
			alert("Please Upload the Project File");
			document.getElementById('uploadFile').style.backgroundColor="#FFE6F7"; 
     		document.getElementById('uploadFile').focus();
     		return false;
     	}    	
		else if(document.importProjectForm.workSpaceDesc.value=="")
		{
			alert("Please Specify Project name");
			document.importProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.workSpaceDesc.focus();
     		return false;
     	}
     	
     	else if(document.importProjectForm.workSpaceDesc.value.length>255)
		{
			alert("Project Name cannot be of more then 255 charactars");
			document.importProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.workSpaceDesc.focus();
     		return false;
     	}
     	else if(check(document.importProjectForm.workSpaceDesc.value)==false) {
	     	document.importProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.workSpaceDesc.focus();
			return false;
		}
		else if(document.importProjectForm.locationCode.value==-1)
		{
			alert("Please Select Region Name");
			document.importProjectForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.locationCode.focus();
     		return false;
     	}
     	else if(document.importProjectForm.deptCode.value==-1)
		{
			alert("Please Select Department Name");
			document.importProjectForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.deptCode.focus();
     		return false;
     	}
     	else if(document.importProjectForm.clientCode.value==-1)
		{
			alert("Please Select Client Name");
			document.importProjectForm.clientCode.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.clientCode.focus();
     		return false;
     	}
     	else if(document.importProjectForm.projectCode.value==-1)
		{
			alert("Please Select Project Type Name");
			document.importProjectForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.projectCode.focus();
     		return false;
     	}
     	else if(document.importProjectForm.userCode.value==-1)
		{
			alert("Please Select Default Admin");
			document.importProjectForm.userCode.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.userCode.focus();
     		return false;
     	}
     	
     	else if(document.importProjectForm.remark.value.length>250)
		{
			alert("Remarks cannot be of more then 250 charactars");
			document.importProjectForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.importProjectForm.remark.focus();
     		return false;
     	}
     	return true;
	}
	
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
		
  	
  	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.importProjectForm.submitBtn.onclick();
		}
		else if(event.srcElement.type=="file"){
  			return false;
  		}  		 
	} 
	function detectBackSpace(e) { 
    	if ((event.keyCode == 8 || event.keyCode == 46) && (event.srcElement.type=="file"))  
  		{//8 is for backspace and 46 is for delete key
  			return false;
  		} 
    }
	document.onkeydown = detectBackSpace;
	document.onkeypress = detectReturnKey;
	function clearDiv()
	{		
		document.getElementById('message').innerHTML='';		
	}

	$(document).ready(function()
			{
				
					$('.target').change(function(){
					
					var correct=true;
					if(check(document.importProjectForm.workSpaceDesc.value)==false) 
					{
				     	document.importProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
			     		document.importProjectForm.workSpaceDesc.focus();
						correct=false;
					}
					
			     	else if(document.importProjectForm.workSpaceDesc.value.length>255)
					{
						alert("Project name cannot be of more than 255 charactars..");
						document.importProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
			     		document.importProjectForm.workSpaceDesc.focus();
			     		correct=false;
			     	}	
					var workspace=document.getElementById('workSpaceDesc').value;
					document.getElementById('workSpaceDesc').value =trim(workspace);
					workspace=document.getElementById('workSpaceDesc').value; 
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
							success: function(response){
								$('#message').html(response);
								if(response.indexOf('Available') == -1){							
									document.getElementById('saveProjectbtn').disabled='disabled';
									return false;
								}
								else if(response.indexOf('Available') != -1)
								{
									document.getElementById('saveProjectbtn').disabled='';
									return true;
								}
							}
						});
					}
				});
			});
			
  	
</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red" id="message">
<b> <s:fielderror></s:fielderror> <s:actionerror /> </b></div>
<div align="center" style="color: green"><b><s:actionmessage /></b><br>
</div>
<br />
<div align="center"><img
	src="images/Header_Images/Project/Import_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div id="importProjectFormDiv" align="left"><br>
<s:form action="CreateImportProject" name="importProjectForm"
	method="post" enctype="multipart/form-data">

	<table width="100%">

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Import File</td>
			<td align="left"><s:file name="uploadFile" size="50%"
				onchange="return validateFiles();"></s:file></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td align="left"><s:textfield name="workSpaceDesc"
				id="workSpaceDesc" cssClass="target" onkeypress="clearDiv();"></s:textfield>
			<s:label name="LabeId1">
				<font color="#c00000">&nbsp;&nbsp;Special characters like / \
				^ $ # : ~ % @ & * ` ! ; ' " are not allowed</font>
			</s:label></td>

		</tr>
		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><select name="locationCode">
				<option value="-1">Select Region</option>
				<s:iterator value="getLocationDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="locationCode"/>"><s:property
							value="locationName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Department</td>
			<td align="left"><select name="deptCode">
				<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
				<option value="-1">Select Department</option>
				<s:iterator value="getDeptDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="deptCode"/>"><s:property
							value="deptName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left"><select name="clientCode">
				<option value="-1">Select Client</option>
				<s:iterator value="getClientDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="clientCode"/>"><s:property
							value="clientName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><select name="projectCode">
				<option value="-1">Select Project Type</option>
				<s:iterator value="getProjectDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="projectCode"/>"><s:property
							value="projectName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Default Admin</td>
			<td align="left"><select name="userCode">
				<option value="-1">Select Default Admin</option>
				<s:iterator value="getUserDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="userCode"/>"
							<s:if test="#session.username == userName">selected="selected"</s:if>><s:property
							value="userName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="remark">Remarks</td>
			<td align="left"><s:textfield name="remark" size="30%"></s:textfield>
			<br>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><s:submit name="submitBtn"
				value="Create Project" onclick="return validate();"
				cssClass="button" id="saveProjectbtn"></s:submit></td>
		</tr>
	</table>
</s:form></div>
</div>

</div>
</body>
</html>