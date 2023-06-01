<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<s:head />
<script type="text/javascript">
	$(document).ready(function() { 
		$("#fromDt").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		$("#toDt").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		});
	
</script>

<script type="text/javascript">

		function validate()
		{
			if(document.addUsertoTemplateForm.templateId.value == -1){
			
				alert("Please select Template");
				document.addUsertoTemplateForm.templateId.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoTemplateForm.templateId.focus();
	     		return false;
     		}
     		
			else if(document.addUsertoTemplateForm.userGroupYN.value == -1){
			
				alert("Please select User/UserGroupName");
				document.addUsertoTemplateForm.userGroupYN.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoTemplateForm.userGroupYN.focus();
	     		return false;
     		}else if(document.addUsertoTemplateForm.accessrights.value == -1){
     			
     			alert("Please select UserRights");
				document.addUsertoTemplateForm.accessrights.style.backgroundColor="#FFE6F7"; 
	     		document.addUsertoTemplateForm.accessrights.focus();
	     		return false;
     		}
     		else if (document.addUsertoTemplateForm.userGroupYN.value == 'user')
     		{
     			if(document.addUsertoTemplateForm.userWiseGroupCode.value==-1)
     			{
     				alert("Please select UserGroupName(userWise)");
					document.addUsertoTemplateForm.userWiseGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoTemplateForm.userWiseGroupCode.focus();
     				return false;
     			}
     			if(document.addUsertoTemplateForm.userCode.value=='')
     			{
     				alert("Please select UserName");
					document.addUsertoTemplateForm.userCode.focus();
     				return false;
     			
     			}
     			if(document.addUsertoTemplateForm.userCode.value==-1)
     			{
     				alert("Please select UserName");
					document.addUsertoTemplateForm.userCode.focus();
     				return false;
     			
     			}
     		
     		}
     		else if (document.addUsertoTemplateForm.userGroupYN.value == 'usergroup')
     		{
     			if(document.addUsertoTemplateForm.userGroupCode.value==-1)
     			{
     				alert("Please select UserGroupName");
					document.addUsertoTemplateForm.userGroupCode.style.backgroundColor="#FFE6F7"; 
		     		document.addUsertoTemplateForm.userGroupCode.focus();
     				return false;
     			}
     		
     		}
     		if(document.addUsertoTemplateForm.stageId.value==-1)
     		{
     			alert("Please select Stages");
     			document.addUsertoTemplateForm.stageId.style.backgroundColor="#FFE6F7"; 
		     	document.addUsertoTemplateForm.stageId.focus();
     			return false;
     		}
     	
			return true;
		}
		
 		function showcombobox()
	    {
	    	
	    	var selectcombo=document.addUsertoTemplateForm.userGroupYN.value;
	    	var usertab=document.getElementById('showUserDtl').innerHTML='';
	    	
	    	
	    	if(selectcombo=="user")
		    {
		    	
		    	document.getElementById('usergroupcombobox').style.display='none';
	    		document.getElementById('usercombobox').style.display='block';
	    		document.getElementById('unametd').style.display='block';
	    	}
	    	else if(selectcombo=="usergroup")
	    	{
	    		
	    		document.getElementById('usergroupcombobox').style.display='block';
	    		document.getElementById('usercombobox').style.display='none';
	    		document.getElementById('unametd').style.display='none';
	    	}
	    	
	    }
	    
	    function openlinkEdit(templateId,userCode,userGroupCode)
	    {
	    	 	str="EditTemplateUser.do?templateId="+templateId+"&userId="+userCode+"&userGroupId="+userGroupCode;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");	
				win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
				return true;
		}
		
		function openlinkView(templateId,userId)
		{
			
			 	str="ViewTemplateUserRights.do?templateId="+templateId+"&userCode="+userId;
				win3 =window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=700,width=1000,resizable=no,titlebar=no top=100");
				win3.moveTo(screen.width/2-(1000/2),screen.height/2-(750/2));
				return true;
		}

</script>
</head>

<body>

<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<div align="center" style="color: green;"><s:actionmessage /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Structure/Add_User_Structure.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="AddUserToTemplate" method="post"
	name="addUsertoTemplateForm">

	<table width="100%">

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Template Name</td>
			<td><s:select list="getAllTemplates" name="templateId"
				headerKey="-1" headerValue="Select Template Name"
				listKey="templateId" listValue="templateDesc" theme="simple">
			</s:select> <ajax:event ajaxRef="templateRightsDtl/getTemplateRightsDtl" /></td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">User / Group</td>
			<td><select name="userGroupYN" onchange="return showcombobox();">
				<option value="-1">Select User or UserGroup</option>
				<option value="user">User</option>
				<option value="usergroup">User Group</option>
			</select></td>
		</tr>

		<tr id="usergroupcombobox" style="display: none;">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">User Group Name</td>
			<td align="left"><s:select list="templateUserGroupsDetails"
				name="userGroupCode" headerKey="-1"
				headerValue="Select User Group Name" listKey="userGroupCode"
				listValue="userGroupName" theme="simple">
			</s:select></td>
		</tr>
		<tr id="usercombobox" style="display: none;">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">User Group
			Name(UserWise)</td>
			<td align="left"><s:select list="templateUserGroupsDetails"
				name="userWiseGroupCode" headerKey="-1"
				headerValue="Select User Group Name" listKey="userGroupCode"
				listValue="userGroupName" theme="simple">
			</s:select> <ajax:event ajaxRef="addUsertoProject/getUserDtl" /></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="unametd"
				style="display: none;">User Name</td>
			<td align="left">
			<div id="showUserDtl"></div>
			</td>
		</tr>
		
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">From Date</td>
			<td><input type="text" name="fromDt" readonly="readonly"
				id="fromDt" value="<s:date name="now" format="yyyy/MM/dd"/>">
			(YYYY/MM/DD)</td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">To Date</td>
			<td><input type="text" name="toDt" readonly="readonly" id="toDt"
				value="2051/04/01"> (YYYY/MM/DD)</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Rights</td>
			<td><select name="accessrights">
				<option value="-1">Select User Rights</option>
				<option value="read">Read Only</option>
				<option value="edit">Can Edit</option>
			</select></td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Stages</td>
			<td><s:select list="getStageDetail" name="stageId"
				headerKey="-1" headerValue="Select Stages" listKey="stageId"
				listValue="stageDesc">
			</s:select></td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Remarks</td>
			<td><s:textfield name="remark" size="40"></s:textfield></td>
		</tr>
		<tr align="left">
			<td>&nbsp;</td>
			<td><s:submit value="Attach User" cssClass="button"
				onclick="return validate();" /></td>
		</tr>
	</table>

	<ajax:enable />
</s:form> <br>
<div id="userForTemplateList" align="center"></div>
</div>
</div>
</div>
</body>
</html>
