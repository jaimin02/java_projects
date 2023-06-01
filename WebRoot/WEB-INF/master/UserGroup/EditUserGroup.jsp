<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>

<script type="text/javascript">
 
 function validate()
	{
	 	if(document.masterAdminForm.userGroupName.value=="")
		{
			alert("Please specify User Group Name..");
			document.masterAdminForm.userGroupName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userGroupName.focus();
     		return false;
     	} 
     	if(document.masterAdminForm.userGroupName.value.length>50)
		{
			alert("User Group Name cannot be of more then 50 charactars..");
			document.masterAdminForm.userGroupName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userGroupName.focus();
     		return false;
     	} 
     	if(document.masterAdminForm.locationCode.value=="-1")
		{
			alert("Please specify region..");
			document.masterAdminForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.deptCode.value=="-1")
		{
			alert("Please specify department..");
			document.masterAdminForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.deptCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.clientCode.value=="-1")
		{
			alert("Please specify Client..");
			document.masterAdminForm.clientCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.clientCode.focus();
     		return false;
     	} 
     	if(document.masterAdminForm.userTypeCode.value=="-1")
		{
			alert("Please specify userType..");
			document.masterAdminForm.userTypeCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userTypeCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.projectCode.value=="-1")
		{
			alert("Please specify project type..");
			document.masterAdminForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.projectCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.remark.value.length>250)
		{
			alert("Remarks cannot be more than 250 characters..");
			document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.remark.focus();
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


			$(document).ready(function() 
			{	
				var options = {	
						success: showResponse 
					};
				$("#saveFormButton").click(function(){
					if(validate())					
						$("#masterAdminForm").ajaxSubmit(options);
					return false;			
				});
			});
			function showResponse(responseText, statusText) 
			{
				alert(responseText);
				opener.history.go(0);
				self.close();
				window.location.reload();
			//	 validate();
			}
	
</script>

</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div align="center">
	<div class="boxborder" style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; ">
		<div class="all_title">
			<b style="float:left">Edit User Group</b>
		</div>
<br>
<div align="center"><s:form action="UpdateUserGroup_ex"
	method="post" name="masterAdminForm" id="masterAdminForm"
	enctype="multipart/form-data">
	<table width="100%">
		<s:iterator value="usergroupdata">
			<s:hidden name="userGroupCode"></s:hidden>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">User Group
				Name&nbsp;&nbsp;</td>
				<td align="left"><s:textfield name="userGroupName" size="30"></s:textfield></td>
			</tr>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Region</td>
				<td align="left"><s:select list="locationDtl" headerKey="-1"
					headerValue="Select Region" name="locationCode"
					listKey="locationCode" listValue="locationName">
				</s:select></td>
			</tr>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Department</td>
				<td align="left"><s:select list="deptDtl" headerKey="-1"
					headerValue="Select Department" name="deptCode" listKey="deptCode"
					listValue="deptName">
				</s:select></td>
			</tr>
			<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Client</td>
				<td align="left"><s:select list="clientDtl" headerKey="-1"
					headerValue="Select client" name="clientCode" listKey="clientCode"
					listValue="clientName">
				</s:select></td>
			</tr>
			<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Type</td>
				<td align="left"><s:select list="getAllUserType" headerKey="-1"
					headerValue="Select Type" name="userTypeCode"
					listKey="userTypeCode" listValue="userTypeName">
				</s:select></td>
			</tr>
			<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Project Type</td>
				<td align="left"><s:select list="projectTypeDtl" headerKey="-1"
					headerValue="Select Projects" name="projectCode"
					listKey="projectCode" listValue="projectName">
				</s:select></td>
			</tr>
			<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Remarks</td>
				<td align="left"><s:textfield name="remark" size="30"></s:textfield></td>
			</tr>
			<tr><td></td></tr>
			<tr>
				<td></td>
				<td align="left"><input type="submit" id="saveFormButton"
					name="saveFormButton" class="button" value="Update" /></td>
			</tr>
		</s:iterator>
	</table>


</s:form> <br />

</div>
</div>
</div>
</div>
</div>
</body>
</html>
