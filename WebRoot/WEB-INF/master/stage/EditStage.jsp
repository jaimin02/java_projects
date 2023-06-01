<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<SCRIPT type="text/javascript">
function validate()
{

		if(document.masterAdminForm.stageDesc.value=="")
		{
			alert("Please specify Stage name");
			document.masterAdminForm.stageDesc.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.stageDesc.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.stageDesc.value.length>50)
		{
			alert("Stage name cannot be of more then 50 charactars");
			document.masterAdminForm.stageDesc.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.stageDesc.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.inclusive.value == -1)
     	{
     		alert("Please Select Inclusive");
     		document.masterAdminForm.inclusive.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.inclusive.focus();
     		return false;
     	}
     	
     	
     	return true;
   }
	
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;

</SCRIPT>


</head>
<body>
<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>


<div align="center" class="maindiv"><br>
<div class="titlediv">Edit Stage</div>
<div align="center"><s:form action="UpdateStage" method="post"
	name="masterAdminForm">
	<table width="60%">
		<tr>
			<td width="50%"></td>
			<td width="50%"></td>
		</tr>
		<tr>
			<td align="left" class="title">Old Stage Name</td>
			<td class="title" align="left">${stageDesc}</td>
		</tr>
		<tr>
			<td align="left" class="title">New Stage Name</td>
			<td align="left"><s:textfield name="stageDesc"></s:textfield></td>
		</tr>

		<tr>
			<td align="left" class="title">Inclusive</td>
			<td align="left"><select name="inclusive">
				<option value="-1">Select Yes/No</option>
				<option value="Y">Yes</option>
				<option value="N">No</option>
			</select></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>

		<tr>
			<td></td>
			<td align="left"><s:submit name="submitBtn" value="Update"
				cssClass="button" onclick="return validate();" /></td>
		</tr>

	</table>

	<s:hidden name="stageId">
	</s:hidden>
</s:form></div>
</div>
</body>
</html>
