<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>

<style type="text/css">
#menu ul li:last-child {/* display: none !important */}

input[type=email] {
  width: 60%;
  padding: 7px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
}
</style>

<SCRIPT language="javascript">

function checkUserLoginName(){
	//debugger;
	var loginUserName = document.getElementById('email').value;
	if(loginUserName==""){
		alert("Please Enter Username");
		document.getElementById('email').value="";
		return false;
	}
	 var emailCheck = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  if(!loginUserName.match(emailCheck)) {
			alert("Please Enter Valid UserName");
			document.getElementById('email').value="";
			return false;
	  }
		
	$.ajax({		
		  //url: "UploadFileInSharepoint_ex.do?path="+path,
		  url: "ValidateUserEmail_ex.do?loginUserName="+loginUserName,
		  beforeSend: function()
		  { 
			 // debugger;
			// $('#rpButton').hide();
			//$('#chkUsername').css('background-color', '#E3EAF0');
		  },
		  success: function(data) 
		  {
			  //debugger;
			  	 	 if(data=="Password reset link has been sent to your registered Email Id"){
			 alert(data);
		
				 var url_string = window.location.href;
				 var url = new URL(url_string);
				 var str = url_string;
				 var word = '/';
				 var newWord = 'test';
				 var n = str.lastIndexOf(word);
				 str = str.slice(0, n);
				 location.replace(str);
			 }else{
				 alert(data);
				 document.getElementById('email').value="";
			 }
			
			 //$("#chkUsername").hide();
			 //$('#rpButton').show();
			 //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
			 /* var str = path.split("/");
			 var fileToUpload = str[str.length - 1];
			 window.open('https://sarjensys.sharepoint.com/sites/eCSVTest/Shared%20Documents/'+fileToUpload+'?WEB=1', '_newtab'); */
		  },
		  error: function(data) 
		  {
			  //debugger;
			  alert("Something went wrong while fetching data from server...");
		  },
			//async: false				
		});
}
	
</SCRIPT>
<s:head />
</head>
<body>
<br>
<s:actionerror /> <s:actionmessage /> <s:fielderror />
<br />
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder"><center><div class="all_title"><b style="float: left;">Forgot Password</b></div></center>
<!-- <div align="center"><img
	src="images/Header_Images/Utility/Change_Password.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-left: 3px; width: 100%; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="UpdatePassword"
	name="changepasswordform" onblur="getfocus();">
	<table width="100%">
		
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">
				<p class="title">Username</p>
				</td>
			<td align="left">
				 <input id="email" class="textbox" name="email" placeholder="email address" type="email" autocomplete="off"
				 pattern="/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/"
    				required></td>
		</tr>
		
		<tr>
		 <td colspan="2">
		</tr>
		<tr>
			<td colspan="2" align="center">
			 <div id="rpButton">
			<input id="resetPassword" name="recover-submit" value="Submit" type="button"
                          class="button" onclick="checkUserLoginName()" >
			</div>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<s:hidden id="minLength" name="minLength"></s:hidden>
	<s:hidden id="showReminder" name="showReminder"></s:hidden>
</s:form></div>
</div>
</div>
</div>
</div>
<s:if test="passExpired == true">
	<script type="text/javascript">
			<!--
			alert('Please change your Password. In case you are New User or If your password has got Expired');
			//-->
		</script>
</s:if>
</body>
</html>
