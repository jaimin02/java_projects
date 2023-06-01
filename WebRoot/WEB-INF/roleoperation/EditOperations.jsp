<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">

	function validate()
		{
	     	if(document.operationform.operationName.value=="")
			{
				alert("Please select Operation Name..");
				document.operationform.operationName.style.backgroundColor="#FFE6F7"; 
	     		document.operationform.operationName.focus();
	     		return false;
	     	} 
	     	if(document.operationform.operationPath.value=="")
			{
				alert("Please Operation Path Code..");
				document.operationform.operationPath.style.backgroundColor="#FFE6F7"; 
	     		document.operationform.operationPath.focus();
	     		return false;
	     	} 
	     	if(document.operationform.operationCode.value=="-1")
			{
				alert("Please Operation Code..");
				document.operationform.operationCode.style.backgroundColor="#FFE6F7"; 
	     		document.operationform.operationCode.focus();
	     		return false;
	     	} 
	
	 	return true;
     }    	
	
	function authenticate(status){
	
		if(status == 'I'){
			var okCancel = confirm("Do you really want to Activate selected Operation ?");
		}else{
			var okCancel = confirm("Do you really want to InActivate selected Operation ?");
		}
		if(okCancel == true)
			return  true;
		else
			return false;
	}
	
	function openlinkEdit(operationCode,operationName,operationPath)
    {
    	var editLocationWindow = "EditOperations.do?operationCode="+operationCode+"&operationName="+operationName+"&operationPath="+operationPath;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=150,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(150/2));
    	return true;
    }
    
	
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div align="center" class="maindiv"><br>
<div class="headercls">Edit Role Operations</div>

<s:form action="UpdateOperation" method="post" name="operationform">

	<table width="100%">

		<tr>
			<td class="title" align="right" width="35%"
				style="padding: 2px; padding-right: 8px;">Operation Name</td>
			<td align="left"><s:textfield name="operationName"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="35%"
				style="padding: 2px; padding-right: 8px;">Operation Path</td>
			<td align="left"><s:textfield name="operationPath"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="35%"
				style="padding: 2px; padding-right: 8px;">Parent Menu</td>
			<td align="left"><s:select list="roleoperation"
				name="parentOperationCode" headerKey="-1"
				headerValue="Select Parent Menu" listKey="operationCode"
				listValue="operationName" value="%{parentOperationCode}">
			</s:select></td>
		</tr>

		<tr>
			<td></td>
			<td align="left"><s:submit onclick="return validate();"
				value="Update" cssClass="button" />
		</tr>

	</table>
	<s:hidden name="operationCode"></s:hidden>
</s:form></div>

</body>
</html>
