<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<SCRIPT type="text/javascript">

function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
	
	function validate()
	{

		var pname = document.masterAdminForm.projectName.value;
		pname=trim(pname);
	
		if(pname=="")
		{
			alert("Please specify Project Name.");
			document.masterAdminForm.projectName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.projectName.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.projectName.value.length>50)
		{
			alert("Project Name cannot be of more then 50 charactars.");
			document.masterAdminForm.projectName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.projectName.focus();
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
<div class="headercls">Edit Project Type</div>
<s:form action="UpdateProject" method="post" name="masterAdminForm">
	<br>
	<table class="channelcontent" width="90%">

		<tr align="left">
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">Old Project Type</td>

			<td class="title" align="left">${projectName}</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;" id="projectName"
				align="left">New Project Type</td>

			<td align="left"><s:textfield name="projectName"></s:textfield></td>

		</tr>

		<tr align="left">
			<td>&nbsp;</td>
			<td><s:submit name="submitBtn" value="Update" cssClass="button"
				onclick="return validate();" /></td>
		</tr>

		<s:hidden name="projectCode">
		</s:hidden>
	</table>


</s:form></div>
</body>
</html>
