<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">

$(document).ready(function() { 
	 $('#userGroupTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
} );
 
 function validate()
	{
	 	if(document.masterAdminForm.userGroupName.value=="")
		{
			alert("Please specify User Group Name.");
			document.masterAdminForm.userGroupName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userGroupName.focus();
     		return false;
     	} 
     	if(document.masterAdminForm.userGroupName.value.length>50)
		{
			alert("User Group Name cannot be of more then 50 charactars.");
			document.masterAdminForm.userGroupName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userGroupName.focus();
     		return false;
     	} 
     	if(document.masterAdminForm.locationCode.value=="-1")
		{
			alert("Please specify Region.");
			document.masterAdminForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.deptCode.value=="-1")
		{
			alert("Please specify department.");
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
			alert("Please specify userType.");
			document.masterAdminForm.userTypeCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userTypeCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.projectCode.value=="-1")
		{
			alert("Please specify project type.");
			document.masterAdminForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.projectCode.focus();
     		return false;
     	} 
     	
     	if(document.masterAdminForm.remark.value.length>250)
		{
			alert("Remarks cannot be more than 250 characters.);
			document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.remark.focus();
     		return false;
     	} 
     	return true;
     }    	
 
 function openlinkEdit(userGroupCode)
 {
		var editUserWindow = "EditUserGroup.do?userGroupCode="+userGroupCode;
    	win3=window.open(editUserWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=330,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
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
</script>

<s:head />
</head>
<body>

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /> <s:property value="errorMsg" /></div>


<br />
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder"><div class="all_title"><b style="float:left;">Add User Group</b></div>

<div class="datatablePadding" style="border: 1px; border-radius: 0px 0px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveUserGroup" method="post"
	name="masterAdminForm">
	<table style="width: 100%">
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">User Group Name</td>
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
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left"><s:select list="clientDtl" headerKey="-1"
				headerValue="Select client" name="clientCode" listKey="clientCode"
				listValue="clientName">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Type</td>
			<td align="left"><s:select list="getAllUserType" headerKey="-1"
				headerValue="Select Type" name="userTypeCode" listKey="userTypeCode"
				listValue="userTypeName">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><s:select list="projectTypeDtl" headerKey="-1"
				headerValue="Select Projects" name="projectCode"
				listKey="projectCode" listValue="projectName">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Remarks</td>
			<td align="left"><s:textfield name="remark" size="30"></s:textfield></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><s:submit name="submitBtn" value="Add"
				cssClass="button" onclick="return validate();" /></td>

		</tr>
	</table>


</s:form> <br />

</div>
<%int srNo = 1; %>
<table width="100%" align="center" id="userGroupTable" class="datatable paddingtable" >
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">User Group Name</th>
			<th style="border: 1px solid black;">Region</th>
			<th style="border: 1px solid black;">Department</th>
			<th style="border: 1px solid black;">Client On</th>
			<th style="border: 1px solid black;">Project</th>
			<th style="border: 1px solid black;">User Type</th>
			<th style="border: 1px solid black;">Status</th>
			<th style="border: 1px solid black;">Edit</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="userGroupDtl" status="status">

			<s:hidden value="statusIndi" id="statusIndi" />

			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td style="border: 1px solid black;"><%=srNo++ %></td>
			<td style="border: 1px solid black;"><s:property value="userGroupName" /></td>
			<td style="border: 1px solid black;"><s:property value="locationName" /></td>
			<td style="border: 1px solid black;"><s:property value="deptName" /></td>
			<td style="border: 1px solid black;"><s:property value="clientName" /></td>
			<td style="border: 1px solid black;"><s:property value="projectName" /></td>
			<td style="border: 1px solid black;"><s:property value="userTypeName" /></td>
			<td style="border: 1px solid black;"><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>


			<td style="border: 1px solid black;"><s:if test="statusIndi != 'D'">
				<div align="center"><a title="Edit" href="javascript:void(0);"
					onclick="openlinkEdit('<s:property value="userGroupCode" />','<s:property value="userGroupCode" />');">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>
			</s:if> <s:else>
				<div align="center"><a title="Edit"> <img border="0px"
					alt="Edit" src="images/Common/edit.png" height="18px" width="18px">
				</a></div>
			</s:else></td>
		</s:iterator>
	</tbody>
</table>
</div>
</div>
</div>
</div>
</body>
</html>
