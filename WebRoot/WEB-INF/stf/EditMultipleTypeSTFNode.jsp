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
   	 if(strString.indexOf("\\")!=-1){alert("Invalid Site Identifier. \\ is not allowed");return false;}
	    
	    var strInvalidChars = "/\^$#:~%@&*`!;'\" ";
    	var strChar;
   	  	var blnResult = true;
   	  	 
	if (strString.length== 0) {alert ("Please specify Site Identifier.."); return false;}
      	for (i = 0; i < strString.length && blnResult == true; i++)
     	 {
 			 strChar = strString.charAt(i);
			 if (strInvalidChars.indexOf(strChar)!= -1)
			 {
      			blnResult = false;
      			if(strChar == ' ')
      			{
      				alert("Invalid Site Identifier." + " 'Space' is not allowed" );
      			}
      			else
      			{
       				alert("Invalid Site Identifier." + " " + strChar + "  is not allowed" );
       			}
 			}
      	}
		return blnResult;
 }
	function validate()
	{
		
		if(document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeName.value == "")
		{
			alert('Please Specify Node Name..');
			document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeName.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeName.focus();
	     	return false;
	    }
		else if(document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeDisplayName.value == "")
		{
			alert('Please Specify Node Display Name..');
			document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeDisplayName.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeDisplayName.focus();
	     	return false;
	    }
		else if(!/^[a-z0-9-]+$/.test(document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeName.value)) 
		{
			alert("Invalid Folder Name... \n\nOnly Alphabets(small letter), Digits and '-' are allowed.");
			document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeName.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editMultipleTypeSTFNode'].multipleTypeSTFNodeName.focus();
	     	return false;
	    }
	    
		if(check(document.forms['editMultipleTypeSTFNode'].siteId.value) == false)
		{
			document.forms['editMultipleTypeSTFNode'].siteId.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editMultipleTypeSTFNode'].siteId.focus();
	     	return false;
	    }
		
		return true;
	}	

	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return false;
  		} 
	} 

	document.onkeypress = detectReturnKey;
</SCRIPT>


<div align="center" class="maindiv">
<div class="titlediv">Edit STF Node</div>

<s:form action="UpdateMultipleTypeSTFNode" method="post"
	name="editMultipleTypeSTFNode">

	<table class="channelcontent">
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="title">Node Display Name:</td>
			<td class="title"><s:textfield name="multipleTypeSTFNodeDisplayName" size="30px"></s:textfield>
			</td>
		</tr>
		<tr>
			<td class="title">Folder Name:</td>
			<td class="title"><s:textfield name="multipleTypeSTFNodeName" size="30px"></s:textfield>
			</td>
		</tr>
		<tr>
			<td class="title">Site Identifier:</td>
			<td><s:textfield name="siteId" size="30px"></s:textfield></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><s:submit name="submitBtn" value="Save" cssClass="button"
				onclick="return validate();" /></td>
		</tr>
	</table>
	<s:hidden name="editNodeId">
	</s:hidden>
</s:form></div>
</body>
</html>
