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

	var cname = document.masterAdminForm.clientName.value;
	cname=trim(cname);

	if(cname=="")
	{
		alert("Please specify client name.");
		document.masterAdminForm.clientName.style.backgroundColor="#FFE6F7"; 
    		document.masterAdminForm.clientName.focus();
    		return false;
  	 }
  	 	
 	 if(document.masterAdminForm.clientName.value.length>50)
	 {
		alert("Client name cannot ne of more then 50 charactars");
		document.masterAdminForm.clientName.style.backgroundColor="#FFE6F7"; 
   		document.masterAdminForm.clientName.focus();
   		return false;
 	 }
 	 if(document.masterAdminForm.remark.value == "")
 	 {
 		alert("Please specify Reason for Change.");
 		document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
 		document.masterAdminForm.remark.focus();
      	return false;
      }
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
<div class="headercls">Edit Client</div>

<s:form action="UpdateClient" method="post" name="masterAdminForm">
	<br>
	<table class="channelcontent" width="100%">
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">Old Client Name</td>
			<td class="title" align="left">${clientName}</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;" id="clientName">New
			Client Name</td>
			<td><s:textfield name="clientName" value=""></s:textfield></td>

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

		<s:hidden name="clientCode">
		</s:hidden>
	</table>
	</td>

</s:form></div>
</body>
</html>
