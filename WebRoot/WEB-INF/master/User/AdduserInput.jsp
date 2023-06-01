<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">
#usertable_filter input{
background-color: #fff;
color:#000;
}
#usertable_length select {
background-color: #fff;
color:#000;
}
</style>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
 <link
	href="<%=request.getContextPath()%>/css/fSelect.css"
	rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
$(function () {
    $("#adCheckBox").click(function () {
        if ($(this).is(":checked")) {
            $("#adUser").show();
            $("#adUserInfo").show();
        } else {
            $("#adUser").hide();
            $("#adUserInfo").hide();
        }
    });
});
$(document).ready(function() { 
	
	(function($) {
	    $(function() {
	    	$('.assignUsers').fSelect({
	    	    placeholder: 'Select User',
	    	    numDisplayed: 3,
	    	    overflowText: '{n} selected',
	    	    noResultsText: 'No results found',
	    	    searchText: 'Search',
	    	    showSearch: true,
	    	    
	    	});
	    	
	    });
	})(jQuery);
	
	$("#adUser").hide();
	$("#adUserInfo").hide();
	 $('#usertable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
} );

	function validate()
	{
		var userinput = document.masterAdminForm.loginName.value;
		var pattern = /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i
		var chkboxValue=document.getElementById('adCheckBox');
		debugger;	
		
		
		
		if(document.masterAdminForm.userName.value=="")
		{
			alert("Please specify Username.");
			document.masterAdminForm.userName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userName.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.userName.value.length>100)
		{
			alert("User Name cannot be of more then 100 characters.");
			document.masterAdminForm.userName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userName.focus();
     		return false;
     	}
     	/* else if(document.masterAdminForm.loginId.value=="")
		{
			alert("Please specify Login Name.");
			document.masterAdminForm.loginId.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginId.focus();
     		return false;
     	} */

      	else if(document.masterAdminForm.loginName.value=="")
		{
			alert("Please specify Login Name.");
			document.masterAdminForm.loginName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginName.focus();
     		return false;
     	}
		if(chkboxValue.checked){
			if(document.masterAdminForm.adUserName.value==""){
				alert("Please specify AD Username.");
				document.masterAdminForm.adUserName.style.backgroundColor="#FFE6F7"; 
	     		document.masterAdminForm.adUserName.focus();
	     		return false;
			}
		}
		if(!pattern.test(userinput))
		{
		  alert('Please enter valid Email Id.');
		  return false;
		} 
     	else if(document.masterAdminForm.loginName.value.length>150)
		{
			alert("Login Name cannot be of more then 150 characters.");
			document.masterAdminForm.loginName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginName.focus();
     		return false;
     	}
   /*  	else if(document.masterAdminForm.emailAddress.value=="")
		{
			alert("Please specify email address..");
			document.masterAdminForm.emailAddress.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.emailAddress.focus();
     		return false;
     	}
     	
     	else if(document.masterAdminForm.emailAddress.value.length>50)
		{
			alert("Email Address cannot be of more then 50 charactars..");
			document.masterAdminForm.emailAddress.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.emailAddress.focus();
     		return false;
     	}
     	*/
     	else if(document.masterAdminForm.remark.value.length>250)
		{
			alert("Reason for Change cannot be more then 250 characters.");
			document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.remark.focus();
     		return false;
     	}
     	/* else if(document.masterAdminForm.userTypeCode.value == "-1")
     	{
     		alert("Please select User Type..");
			document.masterAdminForm.userTypeCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userTypeCode.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.usergrouplist == null)
     	{
     		document.masterAdminForm.userTypeCode.value = "-1";
     		alert("User Group not found.\n\nPlease select User Type first.");
     		document.masterAdminForm.userTypeCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userTypeCode.focus();
     		return false;
     	} */
     	else if(document.masterAdminForm.usergrouplist.value == "")
     	{
     		alert("Please select Group Name.");
			document.masterAdminForm.usergrouplist.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.usergrouplist.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.roleCode.value=="-1")
		{
			alert("Please select User Role.");
			document.masterAdminForm.roleCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.roleCode.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.locationCode.value=="-1")
		{
			alert("Please select Location Name.");
			document.masterAdminForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationCode.focus();
     		return false;
     	}
		
		/* var chkLogiId = checkLoginId(document.masterAdminForm.loginId.value);
     	if(chkLogiId==true){
     		alert("Login Name already exists.");
     		document.masterAdminForm.loginId.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginId.focus();
     		return false;
     	}*/
     	
     	var chkLoginName = checkLoginName(document.masterAdminForm.loginName.value);
     	if(chkLoginName==true){
     		alert("Login Name Id already exists.");
     		document.masterAdminForm.loginName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginName.focus();
     		return false;
     	}  
		return true;
	}
	
function checkLoginId(loginId){
	var flag=false;
	var userGropCode = $('#usergrouplist').val();
	$.ajax({		
		  url: "checkLoginId_ex.do?loginId="+loginId+"&userGroupCode="+userGropCode,
		  success: function(data) 
		  {
			 // alert(data);
			 if(data=="true")
			  flag=true;
		  },
		  error: function(data) 
		  {
			alert("Something went wrong while fetching data from server.");
		  },
			async: false
		});
	return flag;
}

function checkLoginName(loginName){
	var flag=false;
	var userGropCode = $('#usergrouplist').val();
	$.ajax({		
		  url: "checkLoginName_ex.do?loginName="+loginName+"&userGroupCode="+userGropCode,
		  success: function(data) 
		  {
			 // alert(data);
			 if(data=="true")
			  flag=true;
		  },
		  error: function(data) 
		  {
			alert("Something went wrong while fetching data from server.");
		  },
			async: false
		});
	return flag;
}
 function openlinkEdit(userCode,userGroupCode)
 {
		var editUserWindow = "EditUser.do?userCodeForEdit="+userCode+"&userGroupCodeForEdit="+userGroupCode;
    	win3=window.open(editUserWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=300,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
    	return true;
 }

 function resetpassword(userCode,userGroupCode)
	{
		var okCancel = confirm("Do you really want to reset the password to selected user?");
		if(okCancel == true)
		{
			var remark = prompt("Please specify reason for change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
				alert("Default password will be same as your login name");
				var resetPasswordWindow = "ResetUserPassword.do?userCode="+userCode+"&remark="+remark;
		   		window.location.href=resetPasswordWindow;
				return true;	
			}
			else if(remark==""){
				//debugger;
				alert("Please specify reason for change.");
				return false;
			}
		}	
		else
			return false;
	}	

	function revert(userCode,statusindi)
	{
		if(statusindi == 'D'){
			var okCancel = confirm("Do you want to Activate selected User ?");
		}else{
			var okCancel = confirm("Do you want to Inactive selected User ?");
		}
		if(okCancel == true)
		{
			var remark = prompt("Please specify Reason for Change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
				var revertWindow = "DeleteUser.do?userCode="+userCode+"&statusIndi="+statusindi+"&remark="+remark;
		   		window.location.href=revertWindow;
				return true;	
			}
			else if(remark==""){
				//debugger;
				alert("Please Specify reason for change.");
				return false;
			}
		}	
		else
			return false;
	}	

	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;

	function trim(str)
	{
	   	str = str.replace( /^\s+/g, "" );// strip leading
		return str.replace( /\s+$/g, "" );// strip trailing
	}
	
	function unblockUser(userName)
	{
		if (confirm("Do you really want to unblock " + userName + " ?")==false)
			return;
		var URL = 'UnblockUser_ex.do?userName=' + userName;
		$.ajax(
		{			
			url: URL,
			beforeSend: function()
			{ 
				document.getElementById("img"+userName).style.display='';
				document.getElementById("link"+userName).style.display='none';			
			},
	  		success: function(data) 
	  		{
				document.getElementById("img"+userName).style.display='none';
	    		document.getElementById("td"+userName).innerHTML=data;     		
	    		var clsNm=document.getElementById("tr"+userName).className;
	    		clsNm=trim(clsNm);
	    		document.getElementById("tr"+userName).className=clsNm.split(" ")[0];
	    		window.location.href='AddUser.do';
			}	  		
		});
	}


	function showUsers()
	{	
		
		debugger;
		var userTypeCode = $('#userTypeCodeList').val();
		$.ajax(
		{			
			
			url: 'ShowAllUserGroupByType.do?userTypeCode=' + userTypeCode,
			beforeSend: function()
			{
					$('#ShowAllUserGroupByType').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
			},
			success: function(data) 
	  		{

		//		  		alert(data);
					$('#ShowAllUserGroupByType').html(data);
			}	  				    		
		});
		return true;
	}	
	
	function userHistory(userCode,userGroupCode)
	{
		//debugger;
		str="showUserDetailHistory_b.do?userCode=" + userCode + "&userGroupCode=" + userGroupCode;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1100,resizable=no,titlebar=no");
	 	win3.moveTo(screen.availWidth/2-(1100/2),screen.availHeight/2-(500/2));	
		return true;
	}
	
	function resendVerificationMail(userCode,loginName,userName){
		debugger;
		
		$.ajax({		
	  		  //url: "UploadFileInSharepoint_ex.do?path="+path,
	  		  url: "sendVerificationMailForADUser_ex.do?userCode="+userCode+"&loginName="+loginName+"&userName="+userName,
	  		  beforeSend: function()
	  		  { 
	  			// alert(this.url);
	  		  },
	  		  success: function(data) 
	  		  {
	  			  //debugger;
	  			 if(data=="true"){
	  				alert("Verification mail has been sent to "+userName+".");
	  				window.location.reload();
	  			 }else{
	  				alert(data);
	  			 }
	  		  },
	  		  error: function(data) 
	  		  {
	  			  //debugger;
	  			  alert("Something went wrong while fetching data from server...");
	  		  },
	  			//async: false				
	  		});
		return true;
	}
	
</script>

</head>
<body>
<div class="container-fluid">
<div class="col-md-12">

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /> <s:if test="activateOk!=true || limitOk!=true">
	<div align="center">Cannot Add/Activate User... Limit of maximum
	permissible active users exceeded !!!</div>
</s:if></div>
<div class="msgdiv" style="color: green;" align="center"><s:actionmessage /></div>
<br />
<div align="center"><!-- <img
	src="images/Header_Images/User/Add_User.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

<div class="boxborder"><div class="all_title"><b style="float:left;">Add User</b></div>

<div class="datatablePadding" style="border: 1px; border-radius: 0px 0px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:if test="limitOk==true">
	<s:form action="SaveUser" method="post" name="masterAdminForm">
		<table style="width: 100%">
			
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Username
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left">
				<%-- <s:textfield name="userName" size="30" value=""></s:textfield> --%>
				<input type="text" name="userName" autocomplete="off" size="30" value=""/>
				
				</td>
			</tr>
			<%-- <tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Login Name</td>
				<td align="left">
				<s:textfield name="loginId" size="30"value=""></s:textfield>
				<input type="text" name="loginId" autocomplete="off" size="30" value=""/>
				</td>
			</tr> --%>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Login Name / Email <span style="font-size:20px;color:red">*</span></td>
				<td align="left">
				<%-- <s:textfield name="loginName" size="30"	value=""></s:textfield> --%>
				<input type="text" name="loginName" autocomplete="off" size="30" value=""/>
				</td>
			</tr>
			<tr>
				<td class="title" align="right" width="50%"style="padding: 2px; padding-right: 8px;">
					Allow Login With AD User?   
				</td>
				<td>
					<input type="checkbox" name="isAdUserChecked"id="adCheckBox" value="Y"/>
				</td>
				
			</tr>
			<tr id="adUserInfo">
			<td class="title" align="right" width="50%"style="padding: 2px; padding-right: 8px;">
				AD Username <span style="font-size:20px;color:red">*</span>
				</td>
				<td>
				<input type="text"  width="50%"style="padding: 2px; padding-right: 8px;" id="adUser" name="adUserName" autocomplete="off" size="30" value=""/> 
				
				<span style="color: #2e7eb9;">Ex. Domain\AD ID</span>
				</td>
			</tr>
			
			
			<!--  			<tr>
  					<td class="title">Password:</td>
  					<td><s:password name="loginPass" size="30" value=""></s:password></td>
  				</tr>
  				<tr>
  					<td class="title">Retype Password:</td>
  					<td><s:password name="retypePass" size="30" value=""></s:password></td>
  				</tr>
-->
			<!-- <tr>
  					<td class="title">Email:</td>
  					<td><s:textfield name="emailAddress" size="30" value=""></s:textfield></td>
  				</tr>
  				-->
  				
			<%-- <tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">User Type</td>
				<td align="left">
				<s:select id="userTypeCodeList"
					list="usertypelist" headerKey="-1" headerValue="Select User Type"
					name="userTypeCode" listKey="userTypeCode" listValue="userTypeName"
					value="-1" onchange="showUsers()">
				</s:select> --%>
				<%-- <select id="userTypeCodeList" name="userTypeCode" onchange="showUsers()">
				<option value="Select User Type">Select User Type</option>
				<s:iterator value="usertypelist">
					<option value="<s:property value="userTypeCode"/>">
					<s:if test="userTypeName=='WA'">Project Admin</s:if>
					<s:elseif test="userTypeName=='WU'">Project User</s:elseif>
					<s:else>
					<s:property	value="userTypeName" /></s:else></option>
				</s:iterator>
			</select> --%>
				<!-- </td>
			</tr> -->
			<!-- <tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Group Name</td>
				<td align="left">
				<div id="ShowAllUserGroupByType"></div>
				</td>
			</tr> -->
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Group Name
					<span style="font-size:20px;color:red">*</span></td>
				<td>				
				  <select name="usergrouplist" id="usergrouplist" size="6" Style="width:75%;" multiple="multiple" class="assignUsers">
					        <s:iterator value="getUserGroupDtl">
					            <option value="<s:property value="userGroupCode"/>" title="<s:property value="userGroupName"/>">
					                <s:property value="userGroupName"/>
					            </option>
					        </s:iterator>
				</select> 
					   
				</td>
			</tr>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">User Role
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left"><s:select list="roleDtl" headerKey="-1"
					headerValue="Select Role" name="roleCode"
					listKey="roleCode" listValue="roleName">
					</s:select>
				</td>
			</tr>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Location Name
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left"><s:select list="locationDtl" headerKey="-1"
					headerValue="Select Location" name="locationCode"
					listKey="locationCode" listValue="locationName">
					</s:select>
				</td>
			</tr>
			<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Reason for Change
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left"><s:textfield name="remark" size="30" value="" /></td>
			</tr>
			<tr><td></td></tr>
			<tr>
				<td></td>
				<td align="left"><s:submit name="submitBtn" value="Add"
					cssClass="button" onclick="return validate();" /></td>
				<!-- 	<td><input type="button" class="button" disabled="disabled" value ="Add"></td>  // Restrict Client to Add Users(Only Add button Disable)-->
			</tr>
		</table>

	</s:form>
</s:if></div>
<%int i = 1; %> <s:if test="blockFlag==true">
	<div style='text-align: right; padding-right: 25px;'><label
		style="background-color: #12e2cf; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Blocked
	Users</div>
</s:if> <s:if test="delFlag==true">
	<div style='text-align: right; padding-right: 25px;'><label
		style="background-color: #FFDDDD; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Deleted
	Users</div>
</s:if> <br>
<table id="usertable" width="100%" align="center" class="datatable">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Username</th>
			<!-- <th style="border: 1px solid black;">Login Name</th> -->
			<th style="border: 1px solid black;">Login Name</th>
			<th style="border: 1px solid black;">AD Username</th>
			<!--                 <th>Password</th> -->
			<th style="border: 1px solid black;">Group</th>
			<th style="border: 1px solid black;">User Role</th>
			<th style="border: 1px solid black;">Location</th>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>
			<th style="border: 1px solid black;">User Type</th>
			<th style="border: 1px solid black;">Current Status</th>
			<th style="border: 1px solid black;">Edit</th>
			<th style="border: 1px solid black;">Reset Password</th>
			<th style="border: 1px solid black;">Change status to</th>
			<s:if
				test="(currentUserType == 'WA' || currentUserType == 'SU' || currentUserType == 'IT') && blockFlag == true">
				<th style="border: 1px solid black;">Unblock&nbsp;&nbsp;</th>
			</s:if>
			<th style="border: 1px solid black;"></th>
			<s:if test="currentUserType == 'IT'"><th style="border: 1px solid black;">Resend Verification Mail</th></s:if>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="userdata" status="status">

			<s:if test="currentUserType != 'WA' || userType != 'SU'">
				<tr id='tr<s:property value="loginName"/>'
					class="<s:if test="statusIndi == 'D'">matchFound</s:if>
	    					<s:elseif test="blockedFlag==true">blockedFound</s:elseif>
	    					<s:else>
	    					  	<s:if test="#status.even">even</s:if>
	    					  	<s:else>odd</s:else>
	    					</s:else>">

					<td style="border: 1px solid black;"><%=i++ %></td>
					<td style="border: 1px solid black;"><s:property value="userName" /></td>
					<%-- <td style="border: 1px solid black;"><s:property value="loginId" /></td> --%>
					<td style="border: 1px solid black;"><s:property value="loginName" /></td>
					<td style="border: 1px solid black;"><s:property value="adUserName" />
						<%-- <s:if test="adUserName.contains('90.0.0.38')">true<s:property value="adUserName.split('\\\\')[1]" /></s:if>
						<s:else><s:property value="adUserName" /></s:else> --%>
					</td>
					<!-- 			<td><s:property value="loginPass"/></td> -->
					<td style="border: 1px solid black;"><s:property value="userGroupName" /></td>
					<td style="border: 1px solid black;"><s:property value="roleName" /></td>
					<td style="border: 1px solid black;"><s:property value="locationName" /></td>
					<!-- <td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>-->
					<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td>
				<s:if test ="#session.countryCode != 'IND'">
					<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
				</s:if>
					<td style="border: 1px solid black;"><s:property value="userType" /></td>
					<td style="border: 1px solid black;"><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
						test="statusIndi == 'D'">Inactive</s:elseif> 
						<s:elseif test="statusIndi == 'A'">Active</s:elseif>
						<s:else>New</s:else></td>
					<%-- <td><s:if test="statusIndi != 'D' && blockedFlag!=true">
						<div align="center"><a title="Edit"
							href="javascript:void(0);"
							onclick="openlinkEdit('<s:property value="userCode" />','<s:property value="userGroupCode" />');">
						<img border="0px" alt="Edit" src="images/Common/edit.png"
							height="18px" width="18px"> </a></div>
					</s:if> <s:else>
						<div align="center"><a title="Edit"> <img border="0px"
							alt="Edit" src="images/Common/edit.png" height="18px"
							width="18px"> </a></div>
					</s:else></td>  --%>
					<td style="border: 1px solid black;"><s:if test="statusIndi != 'D' && blockedFlag!=true && userGroupName != 'IT' ">
						<s:if test="userGroupCode == '0001' &&  currentUserType == 'IT'">-</s:if>
						<s:else>
						<div align="center"><a title="Edit"
							href="javascript:void(0);"
							onclick="openlinkEdit('<s:property value="userCode" />','<s:property value="userGroupCode" />');">
						<img border="0px" alt="Edit" src="images/Common/edit.svg"
							height="25px" width="25px"> </a></div>
							</s:else>
					</s:if> <s:else>-</s:else></td>
					<td style="border: 1px solid black;"><s:if test="statusIndi != 'D' && blockedFlag!=true">
						<div align="center"><a title="Reset"
							href="javascript:void(0);"
							onclick="resetpassword('<s:property value="userCode" />','<s:property value="userGroupCode" />');">
						<img border="0px" alt="Reset" src="images/Common/reset.svg"
							height="25px" width="25px"> </a></div>
					</s:if> <s:else>
						<div align="center"><a title="Reset"> <img border="0px"
							alt="Reset" src="images/Common/reset.svg" height="25px"	width="25px"> </a></div>
					</s:else></td>

					<td style="border: 1px solid black;">
					<div align="center"><s:if test="userType != 'SU'">

						<s:if test="statusIndi == 'E' || statusIndi == 'A'">
							<a title="InActivate" href="javascript:void(0);"
								onclick="revert('<s:property value="userCode" />','<s:property value="statusIndi"/>');">
							<img border="0px" alt="InActivate"
								src="images/Common/inactive.svg" height="25px" width="25px">
							</a>
						</s:if>
						<s:elseif test="statusIndi == 'N'">
							<a title="InActivate" href="javascript:void(0);"
								onclick="revert('<s:property value="userCode" />','<s:property value="statusIndi"/>');">
							<img border="0px" alt="InActivate"
								src="images/Common/inactive.svg" height="25px" width="25px">
							</a>
						</s:elseif>
						<s:else>
							<a title="Activate" href="javascript:void(0);"
								onclick="revert('<s:property value="userCode" />','<s:property value="statusIndi"/>');">
							<img border="0px" alt="Activate" src="images/Common/active.svg"
								height="25px" width="25px"> </a>
						</s:else>
					</s:if></div>
					</td>
					<s:if
						test="(currentUserType == 'WA' || currentUserType == 'SU' || currentUserType == 'IT') && blockFlag==true">
						<td style="border: 1px solid black;" id='td<s:property value="loginName"/>' align="center"
							style="text-align: center;"><s:if test="blockedFlag==true">
							<a href='javascript:void(0);'
								id='link<s:property value="loginName"/>'
								onclick="unblockUser('<s:property value="loginName"/>');">Unblock</a>
							<img height='15px' width='15px'
								id='img<s:property value="loginName"/>' src="images/loading.gif"
								alt="loading ..." style="display: none;">
						</s:if></td>
					</s:if>
					<td style="border: 1px solid black;">
						<div align="center"><a title="User History" onclick="userHistory('<s:property value="userCode" />','<s:property value="userGroupCode" />');"
												href="#">
							<img border="0px" alt="Audit Trail" src="images/Common/details.svg"
								height="25px" width="25px"> </a></div>
					</td>
					<s:if test="currentUserType == 'IT' ">
						<s:if test="isAdUser=='Y' && isVerified !='Y' "> 
						<td style="border: 1px solid black;">
							<div align="center">
								<a title="Resend Mail" onclick="resendVerificationMail(
								'<s:property value="userCode" />','<s:property value="loginName" />','<s:property value="userName" />');"href="#">
									<img border="0px" alt="Audit Trail" src="images/Common/resendEmail.png"height="25px" width="25px"> 
								</a>
							</div>
						</td>
						</s:if><s:else><td style="border: 1px solid black;">-</td></s:else>
					</s:if>
				</tr>
			</s:if>
		</s:iterator>
	</tbody>
</table>
</div>
</div>
<s:if test="activateOk!=true || limitOk!=true">
	<!-- <div align="center" style="color: red;font-size: 20px;">Cannot Add/Activate User... Limit of maximum permissible active users exceeded !!!</div> -->
	<script>alert('Cannot Add/Activate User... Limit of maximum permissible active users exceeded !!!');</script>
</s:if>
</div>
</div>
</div>
</body>
</html>
