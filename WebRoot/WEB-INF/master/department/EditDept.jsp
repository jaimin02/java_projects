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
			var dname = document.masterAdminForm.deptName.value;
			dname=trim(dname);
		if(dname=="")
		{
			alert("Please specify Department name.");
			document.masterAdminForm.deptName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.deptName.focus();
     		return false;
     	}
		else if(document.masterAdminForm.remark.value == "")
		{
			alert("Please specify Reason for Change.");
			document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
			document.masterAdminForm.remark.focus();
     		return false;
     	}
     	if(document.masterAdminForm.deptName.value.length>50)
		{
			alert("Department name cannot be of more then 50 charactars.");
			document.masterAdminForm.deptName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.deptName.focus();
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
<br>

<div align="center" class="maindiv">

<div class="headercls">Edit Department</div>
<s:form action="UpdateDept" method="post" name="masterAdminForm">
	<br>
	<table class="channelcontent" width="100%">
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">Old Department Name</td>

			<td class="title" align="left">${deptName}</td>
		</tr>
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;" id="deptName">New
			Department Name</td>

			<td align="left"><s:textfield name="deptName" value=""></s:textfield></td>

		</tr>
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;" id="comment">Reason for Change</td>

			<td align="left"><s:textfield name="remark" value="" size="30" /></td>

		</tr>

		<tr align="left">
			<td>&nbsp;</td>
			<td><s:submit name="submitBtn" value="Update" cssClass="button"
				onclick="return validate();" /></td>
		</tr>

		<s:hidden name="deptCode">
		</s:hidden>
	</table>


</s:form></div>
</body>
</html>
