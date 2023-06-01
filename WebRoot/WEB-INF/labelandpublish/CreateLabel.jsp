<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
</head>
<body>
<div class="errordiv" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<SCRIPT type="text/javascript">

	
function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
function check(strString)
{
     strString=trim(strString);
   	 if(strString.indexOf("\\")!=-1){alert("Invalid Label Id." + " " + "\\ is not allowed");return false;}
	    
	    var strInvalidChars = "/\^$#:~%@&*`!;'\" ";
    	var strChar;
   	  	var blnResult = true;
   	  	 
	if(strString.length > 5)
	{
		alert("Label Id must not be greater then 5 chars.");
		document.saveWorkspaceForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     	document.saveWorkspaceForm.workSpaceDesc.focus();
		return false;
	}	
   	if (strString.length== 0) {alert ("Please specify Label Id.."); return false;}
      	for (i = 0; i < strString.length && blnResult == true; i++)
     	 {
 			strChar = strString.charAt(i);
			 if (strInvalidChars.indexOf(strChar)!= -1)
			 {
      			blnResult = false;
      			if(strChar == ' ')
      			{
      				alert("Invalid Label Id." + " 'Space' is not allowed" );
      			}
      			else
      			{
       				alert("Invalid Label Id." + " " + strChar + "  is not allowed" );
       			}
 			}
      	}
		return blnResult;
 }
	function validate()
	{
		var labelid = document.saveLabel.labelId.value;
		
		if(check(labelid) == false)
		{
			document.saveLabel.labelId.style.backgroundColor="#FFE6F7"; 
	     	document.saveLabel.labelId.focus();
	     	return false;
	   }
		
		return true;
	}	

	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.saveLabel.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
</SCRIPT>


<div align="center" class="maindiv">
<div class="titlediv">Create New Label</div>

<s:form action="SaveLabel" method="post" name="saveLabel">

	<td valign="top" align="center">
	<table class="channelcontent">
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="title">Project Name:</td>
			<td>&nbsp;</td>
			<td class="title">${workSpaceDesc}</td>
			<s:hidden name="workSpaceDesc"></s:hidden>
		</tr>
		<tr>
			<td class="title" id="labelId">Label Id:</td>
			<td>&nbsp;</td>
			<td><s:textfield name="labelId" maxlength="5"></s:textfield></td>

		</tr>

		<tr>
			<td class="title" id="labelReamak">Label Remarks:</td>
			<td>&nbsp;</td>
			<td><s:textfield name="labelReamak"></s:textfield></td>
		</tr>

		<tr>
			<td>&nbsp;</td>
			<td><s:submit name="submitBtn" value="Create" cssClass="button"
				onclick="return validate();" /></td>
		</tr>

		<s:hidden name="workSpaceId">
		</s:hidden>
	</table>
	</td>

</s:form></div>
</body>
</html>
