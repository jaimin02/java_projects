<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>


<script type="text/javascript">
   function checkValue()
   {   
   	var paswd = document.getElementById('inputpswd').value;
   
	if(paswd.length == 0)	
	{	
		alert("Please Enter the Password!");
			return false;				
	}	
	return true;			
   }       
      </script>
<s:head />
</head>

<body>
<center><s:actionmessage /></center>
<center><s:actionerror /></center>


<br />
<div align="center"><img
	src="images/Header_Images/Utility/Password_Details.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="EncryptDecrypt" method="post">

	<table width="100%" align="center">
		<tr>
			<td colspan="2"></td>
		</tr>


		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Password</td>
			<td><s:textfield id="inputpswd" name="inputpswd"></s:textfield>
			</td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Encryped or Decrypted
			Value</td>
			<td><s:textfield name="outputpswd"></s:textfield></td>

		</tr>

		<tr>
			<td colspan="2"></td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><s:submit
				name="submitBtn" value="Encrypt" cssClass="button"
				onclick="return checkValue();"></s:submit> &nbsp;</td>
			<td>&nbsp;<s:submit name="submitBtn" value="Decrypt"
				cssClass="button" onclick="return checkValue();"></s:submit></td>
		</tr>

		<tr align="center">
			<td colspan="2">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr align="left">

			<td align="right"><s:submit name="submitBtn" cssClass="button"
				value="Encrypt All"></s:submit> &nbsp;</td>
			<td>&nbsp;<s:submit name="submitBtn" cssClass="button"
				value="Decrypt All"></s:submit></td>
		</tr>
	</table>

</s:form> <br />
<br />
</div>
</div>
</div>
</body>
</html>
