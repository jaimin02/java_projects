<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">
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
   	  	 
	if(strString.length > 60)
	{
		alert("Project must not be greater then 60 char.");
		document.editWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     	document.editWorkspaceForm.workSpaceDesc.focus();
		return false;
	}	
   	if (strString.length== 0) {alert ("Please enter Project."); return false;}
      	for (i = 0; i < strString.length && blnResult == true; i++)
     	 {
 			strChar = strString.charAt(i);
			 if (strInvalidChars.indexOf(strChar)!= -1)
			 {
      			blnResult = false;
      			if(strChar == ' ')
      			{
      				alert("Invalid Project." + " 'space' is not allowed" );
      			}
      			else
      			{
       				alert("Invalid Project." + " " + strChar + "  is not allowed" );
       			}
 			}
      	}
		return blnResult;
 }
	function validate()
	{
		if(check(document.editWorkspaceForm.workSpaceDesc.value)==false) 
		{
	     	document.editWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.workSpaceDesc.focus();
			return false;
		}
		
		else if(document.editWorkspaceForm.remark.value=="") 
		{
			alert("Please specify Reason for Change.");
	     	document.editWorkspaceForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.remark.focus();
			return false;
		}
	 
		else if(document.editWorkspaceForm.projectCode.value==-1)
		{
			alert("Please select Project Type.");
			document.editWorkspaceForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.projectCode.focus();
     		return false;
     	}
     	else if(document.editWorkspaceForm.clientCode.value==-1)
		{
			alert("Please select Client.");
			document.editWorkspaceForm.clientCode.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.clientCode.focus();
     		return false;
     	}
     	/* else if(document.editWorkspaceForm.locationCode.value==-1)
		{
			alert("Please select region.");
			document.editWorkspaceForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.locationCode.focus();
     		return false;
     	} */
		else if(document.editWorkspaceForm.deptCode.value==-1)
		{
			alert("Please select Department.");
			document.editWorkspaceForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.deptCode.focus();
     		return false;
     	}
     	else if(document.editWorkspaceForm.remark.value.length>250)
		{
			alert("Reason for Change cannot be of more than 250 charactars.");
			document.editWorkspaceForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.editWorkspaceForm.remark.focus();
     		return false;
     	}
     	return true;
	}
	
	$(document).ready(function()
	{	
	// pre-submit callback
		$('.target').change(function(){
			var workspace=document.getElementById('workSpaceDesc').value;
			document.getElementById('workSpaceDesc').value =trim(workspace);
			workspace=document.getElementById('workSpaceDesc').value;
			var originalNodeName = "<s:property value='workSpaceDesc'/>";
			var urlOfAction="WorkSpaceExistsForUpdate.do";
			var dataofAction="workSpaceDesc="+workspace;
			if(originalNodeName != workspace)
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
							document.getElementById('updateProject').disabled='disabled';
							return false;
						}
						else if(response.indexOf('available') != -1)
						{
							document.getElementById('updateProject').disabled='';
							return true;
						}
					}
				});
			}
			else
			{
				$('#message').html("<font color='green'>"+ originalNodeName +" is available. </font>");
			}
		});
	});

	
</script>
</head>
<body>
<div id="message" align="center"
	style="font: bold; color: red; font-size: 13px;"></div>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Edit Project Detail</b></div>
<!-- <div class="headercls">Edit Project Detail</div> -->
<br>
<s:form action="UpdateWorkspace" method="post" name="editWorkspaceForm">
<div id="imp" style="float: right; margin-right: 10px; margin-top: -4px;font-size: 14px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
	<table width="100%">

		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;" id="workSpaceDesc1">Project
				 <span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:textfield name="workSpaceDesc"
				cssClass="target" id="workSpaceDesc"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Project Type<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:select list="projectTypes"
				name="projectCode" headerKey="-1" headerValue="Select Project Type"
				listKey="projectCode" listValue="projectName"
				value="%{dto.projectCode}">

			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Client<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:select list="clients" name="clientCode" listKey="clientCode"
				listValue="clientName" value="%{dto.clientCode}">

			</s:select></td>
		</tr>
		<%-- <tr>
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><s:select list="locations" name="locationCode"
				headerKey="-1" headerValue="Select Region Name"
				listKey="locationCode" listValue="locationName"
				value="%{dto.locationCode}">
			</s:select></td>
		</tr> --%>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Department<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:select list="department" name="deptCode"
				listKey="deptCode" listValue="deptName" value="%{dto.deptCode}">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;" id="remark">Reason for Change 
				<span style="font-size:20px;color:red">*</span>&nbsp;
			</td>
			<%-- <td align="left"><input name="remark" style="width: 200px;"
				value="<s:property value='dto.getRemark()'/>"></td> --%>
				<td align="left"><input name="remark" style="width: 200px;"></td>
		</tr>
		<tr><td></td></tr>
		<tr>
			<td colspan="2" align="center"><s:submit value="Update Project"
				id="updateProject" cssClass="button" onclick="return validate();"></s:submit></td>
		</tr>
	</table>
	<s:hidden name="workSpaceId"></s:hidden>
</s:form></div>

</div>
</div>
</body>
</html>
