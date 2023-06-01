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
		var lname =document.masterAdminForm.locationName.value; 
		lname=trim(lname);


		if(lname=="")
		{
			alert("Please specify Region Name..");
			document.masterAdminForm.locationName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationName.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.locationName.value.length>50)
		{
			alert("Region Name cannot be of more then 50 charactars..");
			document.masterAdminForm.locationName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationName.focus();
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
<div class="headercls">Edit Region</div>
<br>
<s:form action="UpdateLocation" method="post" name="masterAdminForm">
	<table class="channelcontent" width="80%">

		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">Old Region Name</td>

			<td class="title" align="left">${locationName}</td>
		</tr>
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;" id="locationName">
			New Region Name</td>

			<td align="left"><s:textfield name="locationName"></s:textfield></td>

		</tr>

		<tr>
			<td>&nbsp;</td>
			<td align="left"><s:submit name="submitBtn" value="Update"
				cssClass="button" onclick="return validate();" /></td>
		</tr>

		<s:hidden name="locationCode">
		</s:hidden>
	</table>
</s:form></div>
</body>
</html>
