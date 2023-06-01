<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<style type="text/css">
.disabled {
    pointer-events: none;
    cursor: not-allowed;
}
</style>
<SCRIPT type="text/javascript">
	
	
function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
function check(strString)
{
     strString=trim(strString);
   	 if(strString.indexOf("\\")!=-1){alert("Invalid project name." + " " + "\\ is not allowed");return false;}
	    
	    var strInvalidChars = "/\^$#:~%@&*`!;'\"+=,| ><?";
    	var strChar;
   	  	var blnResult = true;
   	  	 
	if(strString.length < 5)
	{
		alert("Project name must be greater than 5 char.");
		document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     	document.saveWorkspaceForm.workSpaceDesc.focus();
		return false;
	}
	if(strString.length > 60)
	{
		alert("Project name must be less then 60 char.");
		document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     	document.saveWorkspaceForm.workSpaceDesc.focus();
		return false;
	}		
   	if (strString.length== 0) {alert ("Please specify project name."); return false;}
      	for (i = 0; i < strString.length && blnResult == true; i++)
     	 {
 			strChar = strString.charAt(i);
			 if (strInvalidChars.indexOf(strChar)!= -1)
			 {
      			blnResult = false;
      			if(strChar == ' ')
      			{
      				alert("Invalid project." + " 'space' is not allowed" );
      				return false;
      			}
      			else
      			{
       				alert("Invalid project." + " " + strChar + "  is not allowed" );
       				return false;
       			}
 			}
      	}
      	if(!strString.match(/^([a-zA-Z0-9])/) || !strString.match(/([a-zA-Z0-9])$/))
			{			       			
			alert("First or last character can not be a special character.");
			blnResult = false;
   		}	
		return blnResult;
 }
	function validate()
	{ 
		debugger;
		if(check(document.saveWorkspaceForm.workSpaceDesc.value)==false) 
		{
	     	document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.workSpaceDesc.focus();
			return false;
		}
		
		else if(document.saveWorkspaceForm.workSpaceDesc.value.length>255)
		{
			alert("Project cannot be of more than 255 charactars.");
			document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.workSpaceDesc.focus();
     		return false;
     	}
		else if(document.saveWorkspaceForm.templateId.value==-1)
		{
			alert("Please select Document Structure.");
			document.saveWorkspaceForm.templateId.style.backgroundColor="#FFE6F7"; 
	     	document.saveWorkspaceForm.templateId.focus();
	     	return false;
	     }
		 else if(document.saveWorkspaceForm.projectFor.value == -1)
		{
			alert("Please select Publish Type.");
			document.saveWorkspaceForm.projectFor.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.projectFor.focus();
     		return false;
     	}
		 else if(document.saveWorkspaceForm.clientCode.value==-1)
			{
				alert("Please select Client.");
				document.saveWorkspaceForm.clientCode.style.backgroundColor="#FFE6F7"; 
	     		document.saveWorkspaceForm.clientCode.focus();
	     		return false;
	     	}
		else if(document.saveWorkspaceForm.locationCode.value==-1)
		{
			alert("Please select Location.");
			document.saveWorkspaceForm.locationCode.style.backgroundColor="#FFE6F7"; 
	     	document.saveWorkspaceForm.locationCode.focus();
	     	return false;
	     }
		else if(document.saveWorkspaceForm.deptCode.value==-1)
		{
			alert("Please select Department.");
			document.saveWorkspaceForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.deptCode.focus();
     		return false;
     	}
		else if(document.saveWorkspaceForm.projectCode.value==-1)
		{
			alert("Please select Project Type.");
			document.saveWorkspaceForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.projectCode.focus();
     		return false;
     	}
		 
//     	else if(document.saveWorkspaceForm.docTypeCode.value==-1)
//		{
//			alert("Please select document type..");
//			document.saveWorkspaceForm.docTypeCode.style.backgroundColor="#FFE6F7"; 
 //    		document.saveWorkspaceForm.docTypeCode.focus();
 //    		return false;
 //    	}
     	/* else if(document.saveWorkspaceForm.userCode.value==-1)
		{
			alert("Please select default Admin ..");
			document.saveWorkspaceForm.userCode.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.userCode.focus();
     		return false;
     	} */
     	
     		
		else if(document.saveWorkspaceForm.remark.value.length>250)
		{
			alert("Reason for change cannot be of more than 250 charactars.");
			document.saveWorkspaceForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.saveWorkspaceForm.remark.focus();
     		return false;
     	}
     	
		
     	return true;
	}
	
	function advanceSearch()
    {
    	document.saveWorkspaceForm.action = "EDocSignWorkspaceSummay.do";
		document.saveWorkspaceForm.submit();
    }

	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.saveWorkspaceForm.submitBtn.onclick();
  		} 
	} 

	function cls()
	{		
		document.getElementById('message').innerHTML='';		
	}

	document.onkeypress = detectReturnKey;
	
	$(document).ready(function()
	{
			$('.target').change(function(){
			var correct=true;
			if(check(document.saveWorkspaceForm.workSpaceDesc.value)==false) 
			{
		     	document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
	     		document.saveWorkspaceForm.workSpaceDesc.focus();
				correct=false;
			}
			
	     	else if(document.saveWorkspaceForm.workSpaceDesc.value.length>255)
			{
				alert("Project name cannot be of more than 255 charactars.");
				document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
	     		document.saveWorkspaceForm.workSpaceDesc.focus();
	     		correct=false;
	     	}	
			debugger;
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
						if(response.indexOf('available') == -1){
							document.getElementById('saveProjectbtn').disabled='disabled';
							return false;
						}
						else if(response.indexOf('available') != -1)
						{
							document.getElementById('saveProjectbtn').disabled='';
							return true;
						}
						//alert($('#htmlRole').html(response));
						//addMessage($('#htmlRole').html(response));
					}
				});
			}
		});
	});

</SCRIPT>

</head>
<body>
<div class="errordiv" align="center"  style="font-size: 14px; color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>

<div align="center" style="color: green; size: 50px; width: 100%">
<B><s:actionmessage /></B></div>
<br />
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Create Project</b></div>
<!-- <img
	src="images/Header_Images/Project/Create_Workspace.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div
	style="padding-left: 3px; width: 100%; align:center; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><!-- <div style="text-align: left; background-color: #5A8AA9; width: 955px;color: white;font-size: small; font: bolder;font-weight: bold;padding-bottom: 6px;padding-top: 6px;" align="center">&nbsp;&nbsp;<img src="images\Save As project.gif">&nbsp;&nbsp;Create Project</div> -->
<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
<table align="center" width="100%" cellspacing="0">
	<s:form action="SaveEDocSignWorkspace" name="saveWorkspaceForm" method="post"
		cssStyle="text-align: center;width: 90%;">
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 6px;">Project
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:if test="maxLimitExceeded=='yes'">
				<input size="35" disabled="disabled">
			</s:if> <s:else>
				<s:textfield name="workSpaceDesc" size="35" value=""
					id="workSpaceDesc" cssClass="target" onkeypress="cls();"></s:textfield>
			</s:else></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Document Structure
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><select name="templateId">
				<!-- <option value="-1">Select Scope</option> -->
				<s:iterator value="getTemplateDtl">
					<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="templateId"/>"><s:property
							value="templateDesc" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Publish Type
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><select name="projectFor">
				<!-- <option value="-1">Select Type</option> -->
				<s:if test="maxLimitExceeded=='no'">
					<!-- <option value="P">eCTD</option>
					<option value="N">NeeS</option>
					<option value="V">vNeeS</option>
					<option value="E">Document Management</option> -->
					<option value="D">E-Doc Sign</option>
				</s:if>
				<!-- <option value="M">eCTD (Manual Mode)</option> -->
			</select></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Client
			<span style="font-size:20px;color:red">*</span></td>
			<td align="left">
			
				<select name="clientCode">
					<!-- <option value="-1">Select Client</option> -->
					<s:if test="FosunChanges=='yes'">
						<s:iterator value="getClientDtl">
							<s:if test="statusIndi != 'D' && clientCode == clientCodeGroup">
							<option value="<s:property value="clientCode"/>"><s:property
								value="clientName" /></option>
						</s:if>
						</s:iterator>
					</s:if>
					<s:else>
						<s:iterator value="getClientDtl">
							<s:if test="statusIndi != 'D'">
							<option value="<s:property value="clientCode"/>"><s:property
								value="clientName" /></option>
							</s:if>
						</s:iterator>
					</s:else>
				</select>
					
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 6px;">Location 
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><select name="locationCode">
				<!-- <option value="-1">Select Location</option> -->
				<s:iterator value="getLocationDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="locationCode"/>"><s:property
							value="locationName" /></option>
					</s:if>
				</s:iterator>
			</select> <br>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Department
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><select name="deptCode">
				<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
				<!-- <option value="-1">Select Department</option> -->
				<s:iterator value="getDeptDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="deptCode"/>"><s:property
							value="deptName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Project Type
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><select name="projectCode">
				<!-- <option value="-1">Select Project Type</option> -->
				<s:iterator value="getProjectDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="projectCode"/>"><s:property
							value="projectName" /></option>
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
			<td class="title" align="right" id="remark" width="50%"
				style="padding: 2px; padding-right: 8px;">Remarks&nbsp;</td>
			<td align="left"><s:if test="maxLimitExceeded=='yes'">
				<input size="35" disabled="disabled" />
			</s:if> <s:else>
				<%-- <s:textfield name="remark" size="35" value=""></s:textfield> --%>
				<TEXTAREA rows="3" style="width: 200px;" name="remark"></TEXTAREA>
			</s:else> <br>
			</td>
		</tr>

			<!-- <tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Default Admin&nbsp;</td>
			<td align="left">
				<s:if test="FosunChanges =='yes'">
					<select name="userCode" class="disabled">
						<option value="-1">Select Default Admin</option>
						<s:iterator value="getUserDtl">
							<s:if test="statusIndi != 'D'">
								<option value="<s:property value="userCode"/>"
									<s:if test="#session.username == userName">selected="selected"</s:if>>
								<s:property value="userName" /></option>
							</s:if>
						</s:iterator>
					</select>
				</s:if>
				<s:else>
					<select name="userCode">
						<option value="-1">Select Default Admin</option>
						<s:iterator value="getUserDtl">
							<s:if test="statusIndi != 'D'">
								<option value="<s:property value="userCode"/>"
									<s:if test="#session.username == userName">selected="selected"</s:if>>
								<s:property value="userName" /></option>
							</s:if>
						</s:iterator>
					</select>
				</s:else>
				
			</td>
		</tr>
	<tr>
			<td class="title" align="right"
				style="padding: 2px; padding-right: 8px;">Copy User
			Rights&nbsp;</td>
			<td align="left" style="color: black;"><input type="radio"
				id="copyRightsY" name="copyRights" value="Y" checked="checked"
				<s:if test="maxLimitExceeded=='yes'">disabled="disabled"</s:if> />
			<label for="copyRightsY">Yes</label>&nbsp; <input type="radio"
				id="copyRightsN" name="copyRights" value="N"
				<s:if test="maxLimitExceeded=='yes'">disabled="disabled"</s:if> />
			<label for="copyRightsN">No</label></td>
		</tr> -->
		<tr><td></td></tr>
		<tr>
			<td colspan="2" align="center"><s:if
				test="maxLimitExceeded=='yes'">
				<input type="button" value="Create Project" class="button"
					align="right" disabled="disabled">&nbsp;&nbsp;
		  	 		</s:if> <s:else>
				<s:submit name="submitBtn" value="Create Project" cssClass="button"
					align="right" onclick="return validate();" id="saveProjectbtn"></s:submit>&nbsp;&nbsp;
		  	 		</s:else><!--  <input type="button" value="Advanced Search" class="button"
				align="right" onclick="return advanceSearch();" /> --></td>
		</tr>
	</s:form>
</table>
</div>





</div>
</div>

</div>
</div>
</div>
</body>
</html>