<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
			/*
				var emailRegex = /^[a-zA-Z][\w\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$/;
				var email = document.masterAdminForm.emailAddress.value;
				if((!emailRegex.test(email)) && (email.length > 0))
					return false;
			*/
			$(document).ready(function(){
				$('#editButton').click(function(){
					$('.txtBoxProfileValue').css('border','1px solid');
					$('.txtBoxProfileValue').removeAttr("readonly");
					$('.txtBoxProfileValue:first').focus();
					$('.checkBoxAlert').removeAttr("disabled");
					$('#saveButton').css('display','inline');
					$('#cancelButton').css('display','inline');
					$('#editButton').css('display','none');
				});
				$('#cancelButton').click(function(){
					//window.location.href = window.location.href;
					window.location.reload(true);
				});
				var options = {beforeSubmit: validateTextBox,success: showResponse};
				$('#profileDetails').submit(function(){
					$(this).ajaxSubmit(options);
					return false;
				});
			});
			function validateTextBox()
			{
				
				var emailRegex = /^[a-zA-Z][\w\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$/;
				var phonenumberRegex = /^[0-9][0-9]*$/;
				var txtNameMobile = /^M_[a-zA-Z].*$/;
				var txtNameEmail = /^E_[a-zA-Z].*$/;
				var returnVal = true;
				var itr=1;
				$('.txtBoxProfileValue').each(function(){
					var txtValue = $(this).val();
					//alert(txtValue);
					var txtName = this.name; 
					//alert(txtName + " == " +txtValue + "--" + txtValue.length);
					if(txtNameMobile.test(txtName))
					{
						//alert("mobile");
						if(txtValue.length != 0) 
						{
							//alert(phonenumberRegex.test(txtValue));
							if(!phonenumberRegex.test(txtValue))
							{
								alert("Please enter valid contact number.");
								$(this).focus();
								returnVal = false;
								return returnVal;
							}
						}
					}
					else if (txtNameEmail.test(txtName))
					{
						//alert("email");
						if(txtValue.length != 0) 
						{
							//alert(emailRegex.test(txtValue));
							if(!emailRegex.test(txtValue))
							{
								alert("Please enter valid email id.");
								$(this).focus();
								returnVal= false;
								return returnVal;
							}
						}
					}
				});
				return returnVal;
			}

			function showResponse(responseText, statusText) {
				//document.getElementById("AttrDisplayValueMain").style.display = 'block';
				$('.txtBoxProfileValue').css('border','0px');
				$('.txtBoxProfileValue').attr("readonly","readonly");
				$('#saveButton').css('display','none');
				$('#cancelButton').css('display','none');
				$('#editButton').css('display','inline');
				$('.checkBoxAlert').attr("disabled","disabled");
				alert(responseText);
				//alert(statusText);
			}
		</script>
<s:head />
</head>
<body>
<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />

<div class="container-fluid">
<div class="col-md-12">
<!-- <div align="center"><img
	src="images/Header_Images/Utility/My_Profile.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
	<div class="boxborder"><center><div class="all_title"><b>My Profile</b></div></center>
<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-left: 3px; width: 100%; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">
<form action="SaveProfile_ex.do" method="post" id="profileDetails">
<table style="width: 100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">User Name</td>
		<td align="left" colspan="2"><s:property
			value="dtoUserMSt.userName" /></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Login ID</td>
		<td align="left" colspan="2"><s:property
			value="dtoUserMSt.loginName" /></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Group Name</td>
		<td align="left" colspan="2"><s:property
			value="dtoUserMSt.userGroupName" /></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;"></td>
		<td align="left" colspan="2"><label
			style="font-size: small; color: green;">(Contact numbers
		should include country code)</label></td>
	</tr>
	<s:iterator value="listProfileType">
		<s:set name="profileTypeSet" value="top" />
		<s:iterator value="listProfileSubType">
			<s:set name="profileSubTypeSet" value="top" />
			<tr>
				<td class="title" align="right" width="25%"
					style="padding: 2px; padding-right: 8px;"><s:if
					test="#profileTypeSet.charAt(0) == 'M'">Mobile</s:if> <s:if
					test="#profileTypeSet.charAt(0) == 'E'">Email</s:if> (<s:property
					value='#profileSubTypeSet' />)</td>
				<td align="left" style="width: 20px;"><input type="checkbox"
					name="listAlertOnValue" class="checkBoxAlert" disabled="disabled"
					value='<s:property value="#profileTypeSet + '_' + #profileSubTypeSet"/>'
					<s:iterator value="listUserProfile">
								  					<s:if test="#profileTypeSet == profileType.toString() && #profileSubTypeSet == profileSubType && profilevalue != null && profilevalue != '' && alertOn == 'Y' ">
								  						checked="checked"
								  					</s:if>
							  					</s:iterator>>
				</td>
				<td align="left"><input type="text" class="txtBoxProfileValue"
					name='<s:property value="#profileTypeSet + '_' + #profileSubTypeSet"/>'
					<s:if test="#profileTypeSet.charAt(0) == 'M'">size="15"</s:if>
					<s:if test="#profileTypeSet.charAt(0) == 'E'">size="25"</s:if>
					<s:iterator value="listUserProfile">
								  					<s:if test="#profileTypeSet == profileType.toString() && #profileSubTypeSet == profileSubType">
								  						 value='<s:property value="profilevalue"/>' 
									  				</s:if>
						  						</s:iterator>
					style="border: none;" readonly="readonly"></td>
			</tr>
		</s:iterator>
	</s:iterator>
	<tr>
		<td align="right" style="padding-right: 3px;"><input
			type="button" id="editButton" value="Edit" class="button"
			style="display: inline;"> <input type="button"
			id="cancelButton" value="Cancel" class="button"
			style="display: none;"></td>
		<td align="left" style="padding-left: 3px;" colspan="2"><input
			type="submit" id="saveButton" value="Save" class="button"
			style="display: none;"></td>
	</tr>
</table>
</form>
</div>
</div>
</div>

</div>
</div>

</body>
</html>
