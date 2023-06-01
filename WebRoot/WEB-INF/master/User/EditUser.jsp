<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<style type="text/css">
#clientTable_filter input{
background-color: #fff;
color:#000;
}
#clientTable_length select {
background-color: #fff;
color:#000;
}
.dataTables_wrapper{
width:99%;
}
</style>
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script> --%>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
 <link
	href="<%=request.getContextPath()%>/css/fSelect.css"
	rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<SCRIPT type="text/javascript">


		function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}
		
		function validate(buttonName)
		{

			var userName = document.masterAdminForm.userName.value;
			userName = trim(userName);
			var loginName = document.masterAdminForm.loginName.value;
			loginName = trim(loginName);
			var chkboxValue=document.getElementById('isAdUserChecked');
		if(userName == "")
		{
			alert("Please specify user name.");
			document.masterAdminForm.userName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userName.focus();
     		return false;
     	}
     	else if(loginName == "")
		{
			alert("Please specify login name.");
			document.masterAdminForm.loginName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginName.focus();
     		return false;
     	}
     	 else if(document.masterAdminForm.loginName.value.length>150)
		{
			alert("Login Name cannot be of more then 150 charactars.");
			document.masterAdminForm.loginName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginName.focus();
     		return false;
     	}
		if(buttonName!="Update"){
		if(chkboxValue.checked){
			if(document.masterAdminForm.adUserName.value==""){
				alert("Please specify AD Username.");
				document.masterAdminForm.adUserName.style.backgroundColor="#FFE6F7"; 
	     		document.masterAdminForm.adUserName.focus();
	     		return false;
			}
			if(document.masterAdminForm.adUserName.value.includes(" ")){
				alert("Space is not allowed in AD Username.");
				document.masterAdminForm.adUserName.style.backgroundColor="#FFE6F7"; 
	     		document.masterAdminForm.adUserName.focus();
	     		return false;
			}
		}
		
		if(buttonName=="Add"){
		var chkLoginName = checkLoginName(loginName);
     	if(chkLoginName==true){
     		alert("Login Name already exists.");
     		document.masterAdminForm.loginName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.loginName.focus();
     		return false;
     	}
		}
     /*	else if(document.masterAdminForm.emailAddress.value=="")
		{
			alert("Please specify email address..");
			document.masterAdminForm.emailAddress.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.emailAddress.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.emailAddress.value.length>50)
		{
			alert("Email address cannot be more then 50 charactars..");
			document.masterAdminForm.emailAddress.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.emailAddress.focus();
     		return false;
     	}
     */
     	/* else if(document.masterAdminForm.userTypeCode.value == "-1")
		{
			alert("Please Select User Type.");
			document.masterAdminForm.userTypeCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.userTypeCode.focus();
     		return false;
     	} */
     	else if(document.masterAdminForm.usergrouplist.value == "")
		{
			alert("Please Select User Group.");
			document.masterAdminForm.usergrouplist.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.usergrouplist.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.locationCode.value=="-1")
		{
			alert("Please select Location.");
			document.masterAdminForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationCode.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.roleCode.value=="-1")
		{
			alert("Please select User Role.");
			document.masterAdminForm.roleCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.roleCode.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.deptCode.value=="-1")
		{
			alert("Please select User Department.");
			document.masterAdminForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.deptCode.focus();
     		return false;
     	}
     	else if(document.masterAdminForm.remark.value == "")
		{
			alert("Please specify Reason for Change.");
			document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.remark.focus();
     		return false;
     	} 
     	else if(document.masterAdminForm.remark.value.length>250)
		{
			alert("Reason for Change cannot be more then 250 charactars.");
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


	$(document).ready(function() {	
		
		 $('#clientTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } ); 
		
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
		if(document.getElementById('isAdUserChecked').checked==false)
			$("#adUser").hide();
		
				var options = {	
						success: showResponse 
					};
				
				 /* $("#saveFormButton").click(function(){
					 //document.getElementsByName("saveFormButton").click(function(){
					 debugger;
					if(validate())	
					{				
						$("#masterAdminForm").ajaxSubmit(options);
					}
					return false;			
				});  */
				 $("#isAdUserChecked").click(function () {
				        if ($(this).is(":checked")) {
				            $("#adUser").show();
				        } else {
				            $("#adUser").hide();
				        }
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
			function openlinkEdit(userCode,userGroupCode,loginName)
			 {
					var editUserWindow = "ChangeUserDetail_b.do?userCodeForEdit="+userCode+"&userGroupCodeForEdit="+userGroupCode+"&loginName="+loginName;
					//var editUserWindow = "EditUser.do?loginName="+loginName;
			    	win3=window.open(editUserWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=650,width=1300,resizable=no,titlebar=no")	
			    	win3.moveTo(screen.availWidth/2-(1300/2),screen.availHeight/2-(650/2));
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
			 function resetpassword(userCode,userGroupCode,loginName)
				{
					var okCancel = confirm("Do you really want to reset the password to selected user?");
					if(okCancel == true)
					{
						var remark = prompt("Please specify reason for change.");
						remark = remark.trim();
						if (remark != null && remark != ""){
							alert("Default password will be same as your login name");
							var resetPasswordWindow = "ResetUserPassword.do?userCode="+userCode+"&remark="+remark+"&loginName="+loginName;
					   		window.location.href=resetPasswordWindow;
					   		//window.location.reload();
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

				function revert(userCode,statusindi,loginName)
				{
					debugger;
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
							var revertWindow = "DeleteUser.do?userCode="+userCode+"&statusIndi="+statusindi+"&remark="+remark+"&loginName="+loginName;
					   		window.location.href=revertWindow;
					   		//window.location.reload();
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
				
				function unblockUser(userName,userCode)
				{
					//alert("userCode : "+userCode); 
					if (confirm("Do you really want to unblock " + userName + " ?")==false)
						return;
					//var URL = 'UnblockUser_ex.do?userName=' + userName;
					var URL = 'UnblockUser_ex.do?userName=' + userName+'&userCodeToUnblock='+userCode;
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
				    		//window.location.href='AddUser.do';
				    		window.location.reload();
						}	  		
					});
				}
			function showUsers()
			{	
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
				  		$('#ShowAllUserGroupByType').html(data);
					}
				});
				return true;
			}	
			function userHistory(userCode,userGroupCode)
			{
				//debugger;
				str="showUserDetailHistory_b.do?userCode=" + userCode + "&userGroupCode=" + userGroupCode;
				win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1100,resizable=no,titlebar=no");
			 	win3.moveTo(screen.availWidth/2-(1100/2),screen.availHeight/2-(500/2));	
				return true;
			}
			function resendVerificationMail(userCode,loginName,userName){
				//debugger;
				
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
	function refreshParent() 
	{
		window.opener.parent.history.go(0);
		self.close();
	}
</SCRIPT>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div align="center">
<div class="boxborder" style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; ">
<div style="width:100%;" class="all_title"><b style="float: left;">Update User</b></div>
<br>
<s:form action="UpdateUser_ex" method="post" name="masterAdminForm"
	id="masterAdminForm" enctype="multipart/form-data">
	<table width="80%">
		<s:iterator value="user">
			<s:hidden name="userCode" />
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">User Name
					<span style="font-size:20px;color:red">*</span></td>
<%-- 				<s:if test="isAdUser=='Y' "> --%>
					<%-- <td><s:textfield name="adUserName" size="30" readonly="true"> 
					<td><s:textfield name="adUserName" size="30">
					</s:textfield></td>
				<%-- </s:if>
				<s:else> --%>
					<td><s:textfield name="userName" id="userName" size="30">
					</s:textfield></td>
				<%-- </s:else> --%>
			</tr>
			<%-- <tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Login Name</td>
				<td><s:textfield name="loginId" size="30" readonly="true"></s:textfield></td>
			</tr> --%>
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Login Name
					<span style="font-size:20px;color:red">*</span></td>
				<td><s:textfield name="loginName" id="loginName" size="30" readonly="true"></s:textfield></td>
			</tr>
			
			<%-- <s:if test="isAdUser!='Y' ">  --%>
				<tr align="left">
					<td  align="right" width="50%"style="padding: 2px; padding-right: 8px;">
						<!--  <input type="text" id="adUser" name="adUserName" autocomplete="off" size="30" value=""/>  -->
						<!-- <input type="checkbox" name="adCheckBox" id="adCheckBox" value="Y"/> -->
						<s:if test="isAdUser=='Y' ">
						 	<input type="checkbox" name="isAdUserChecked" id="isAdUserChecked" checked=checked value="Y">
						 </s:if>
						 <s:else>
						 	 <input type="checkbox" name="isAdUserChecked" id="isAdUserChecked" value="Y">
						 </s:else> 
					</td>
						
					<td class="title">
						<%-- <s:textfield name="adUserName" size="30" readonly="true"></s:textfield> --%>
						Allow Login With AD User?						
					</td>
					
				</tr>
			<%--  </s:if>
			 <s:if test="isAdUser=='Y' "> --%> 
				<tr id="adUser"align="left">
					<td class="title" align="right" width="50%"style="padding: 2px; padding-right: 8px;">
						AD Username <%-- <span style="font-size:20px;color:red">*</span>  --%>
						<%-- <s:if test="isAdUser=='Y' ">
						 	<input type="checkbox" name="isAdUserChecked" id="isAdUserChecked" checked=checked value="Y">
						 </s:if>
						 <s:else>
						 	 <input type="checkbox" name="isAdUserChecked" id="isAdUserChecked" value="Y">
						 </s:else> --%>
					</td>	
					<td>
					<s:if test="isAdUser=='Y' ">
						<s:textfield name="adUserName" size="30" id="adUserName" readonly="true"></s:textfield> 
					</s:if>
					<s:else>
						<s:textfield name="adUserName" id="adUserName" size="30"></s:textfield>
					</s:else>
					</td>
					
				</tr>
			<%-- </s:if>
			
			
			
			<!--  				<tr>
  					<td class="title">Password:</td>
  					<td><s:password name="loginPass" size="30" showPassword="true"></s:password></td>
  				</tr>
  				<tr>
  					<td class="title">Retype Password:</td>
  					<td><s:password name="retypePass" size="30" value="%{loginPass}" showPassword="true"></s:password></td>
  				</tr>
  				
  			    <tr>
  					<td class="title">Email:</td>
  					<td><s:textfield name="emailAddress" size="30"></s:textfield></td>
  				</tr>
  		-->

			<%-- <tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">User Type</td>
				<td>
				<s:select id="userTypeCodeList" list="usertypelist"
					name="userTypeCode" listKey="userTypeCode" listValue="userTypeName"
					value="%{userType}" onchange="showUsers()">
				</s:select>
				</td>
			</tr> --%>
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Profile Name
					<span style="font-size:20px;color:red">*</span></td>
				<td>
				<!--  <div id="ShowAllUserGroupByType"> -->
				<s:select list="usergrplist" id="usergrouplist"
					name="usergrouplist" listKey="userGroupCode"
					listValue="userGroupName" value="%{userGroupCode}">
				</s:select>
			<!-- 	</div> -->
				<%-- <select name="usergrouplist" id="usergrouplist" size="6" Style="width:75%;" multiple="multiple" class="assignUsers">
					        <s:iterator value="usergrplist">
					            <option value="<s:property value="userGroupCode"/>" title="<s:property value="userGroupName"/>">
					                <s:property value="userGroupName"/>
					            </option>
					        </s:iterator>
				</select> --%> 
				</td>
			</tr>
			 <tr>
				<td class="title" align="right" width="50"
					style="padding: 2px; padding-right: 8px;">User Role
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left"><s:select list="roleDtl" name="roleCode" id="roleCode"
				listKey="roleCode" listValue="roleName" value="%{roleCode}">
					</s:select>
				</td>
			</tr> 
			<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Department
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><%-- <select name="deptCode">
				<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
				<option value="-1">Select Department</option>
				<s:iterator value="getDeptDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="deptCode"/>"><s:property
							value="deptName" /></option>
					</s:if>
				</s:iterator>
			</select> --%>
					<s:select list="getDeptDtl" name="deptCode" id="deptCode"
							listKey="deptCode" listValue="deptName" value="%{deptCode}">
					</s:select>
			</td>
		</tr>
			 <tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Location Name
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left"><s:select list="locationDtl" name="locationCode" id="locationCode"
				listKey="locationCode" listValue="locationName" value="%{locationCode}">
					</s:select>
				</td>
			</tr> 
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Reason for Change
					<span style="font-size:20px;color:red">*</span></td>
				<td><s:textfield name="remark" id="remark" value="" size="30" /></td>
			</tr>
			<tr align="left">
				<td></td>
				<td></td>
			</tr>
			<tr>
			
				<td align="center" colspan="2" style="width:50%;">
					<s:submit type="button" id="Add" name="saveFormButton" cssClass="button" value="Add" onclick="return validate(this.id);"></s:submit>
				<s:if test="showUpdateBtn==true">
					<s:submit type="button" id="saveFormButton" cssStyle="margin: 0px 5px 0px 5px;" name="saveFormButton" cssClass="button" value="Update" onclick="return validate(this.id);" ></s:submit>
				</s:if>
					<input type="button" id="close" name="close" class="button" value="Close" onclick="refreshParent();"/>
					</td>
			</tr>
			<tr><td></td></tr>
		</s:iterator>
	</table>

</s:form>
<%int i = 1; %> <s:if test="blockFlag==true">
	<div style='text-align: right; padding-right: 25px;'><label
		style="background-color: #FFEDCC; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Blocked
	Users</div>
</s:if> <s:if test="delFlag==true">
	<div style='text-align: right; padding-right: 25px;'><label
		style="background-color: #FFDDDD; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Deleted
	Users</div>
</s:if> <br>
<table id="clientTable" style="margin-bottom: 5px;width:100%;" align="center" class="datatable">
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
			<!-- <th style="border: 1px solid black;">User Type</th> -->
			<th style="border: 1px solid black;">Department</th>
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
					class="<s:if test="statusIndi == 'D'"> if matchFound</s:if>
	    					<s:elseif test="blockedFlag==true">elseif blockedFound</s:elseif>
	    					<s:else>
	    					  	<s:if test="#status.even">even fff</s:if>
	    					  	<s:else>odd dd</s:else>
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
					<%-- <td style="border: 1px solid black;"><s:property value="userType" /></td> --%>
					<td style="border: 1px solid black;"><s:property value="deptName" /></td>
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
					<td style="border: 1px solid black;">
					 <s:if test="statusIndi != 'D' && blockedFlag!=true && userGroupName != 'IT' ">
						<s:if test="userGroupCode == '0001' &&  currentUserType == 'IT'">-</s:if>
						<s:else> 
						<div align="center"><a title="Edit"
							href="javascript:void(0);"
							onclick="openlinkEdit('<s:property value="userCode" />','<s:property value="userGroupCode" />','<s:property value="loginName" />');">
						<img border="0px" alt="Edit" src="images/Common/edit.svg"
							height="25px" width="25px"> </a></div>
							 </s:else>
					</s:if> <s:else>-</s:else> 
					</td>
					 <td style="border: 1px solid black;"><s:if test="statusIndi != 'D' && blockedFlag!=true">
						<div align="center"><a title="Reset"
							href="javascript:void(0);"
							onclick="resetpassword('<s:property value="userCode" />','<s:property value="userGroupCode" />','<s:property value="loginName" />');">
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
								onclick="revert('<s:property value="userCode" />','<s:property value="statusIndi"/>','<s:property value="loginName" />');">
							<img border="0px" alt="InActivate"
								src="images/Common/inactive.svg" height="25px" width="25px">
							</a>
						</s:if>
						<s:elseif test="statusIndi == 'N'">
							<a title="InActivate" href="javascript:void(0);"
								onclick="revert('<s:property value="userCode" />','<s:property value="statusIndi"/>','<s:property value="loginName" />');">
							<img border="0px" alt="InActivate"
								src="images/Common/inactive.svg" height="25px" width="25px">
							</a>
						</s:elseif>
						<s:else>
							<a title="Activate" href="javascript:void(0);"
								onclick="revert('<s:property value="userCode" />','<s:property value="statusIndi"/>','<s:property value="loginName" />');">
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
								onclick="unblockUser('<s:property value="loginName"/>','<s:property value="userCode"/>');">Unblock</a>
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
						<s:if test="isVerified !='Y' "> 
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
</div>
</div>
</body>
</html>