<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>

<style type="text/css">
#menu ul li:last-child {/* display: none !important */}

html,body { height: 100%; }

input[type='checkbox']{
  width: 14px !important;
  height: 14px !important;
  border-radius: 2px;
  -webkit-appearance: none;
  -moz-appearance: none;
  -o-appearance: none;
  appearance: none;
  box-shadow: none;
 
  text-align: center;
  line-height: 1em;
 background-color: #eee;
}

input[type='checkbox']:checked:after {
  content: '✔';
  background-color: #2196F3;
  color: white;
}

.hoverWrapper:hover #hoverShow1 {
 display: block;
}

.hoverWrapper #hoverShow1 {
 display: none;
 position: relative;
 /* width: 119px; */
 height: 19px;
 float: right;
}

@font-face {
	    font-family: 'password';
	    font-style: normal;
	    font-weight: 400;
	   /*  src: url('images/password.ttf'); */
		src: url(data:font/woff;charset:utf-8;base64,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) format("woff");

	   
	}          
	#oldpassword {
        font-family: 'password';
    }
        
    #newpassword {
        font-family: 'password';
    }
    #retype {
        font-family: 'password';
     }

</style>
<SCRIPT language="javascript">

	function validate()
	{
		var minlen = document.getElementById('minLength').value;
		if(document.changepasswordform.newpassword.value =="")
		{
			alert('Please enter new password');
			document.changepasswordform.newpassword.focus();
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
			return false;
		}
	    if(document.changepasswordform.retype.value =="")
		{
			alert('Please enter retype password');
			document.changepasswordform.retype.focus();
			document.changepasswordform.retype.style.backgroundColor="#FFE6F7";
			return false;
		}
		
		if(document.changepasswordform.newpassword.value != document.changepasswordform.retype.value)
		{
			alert('New password does not match with the retype password' );
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
			document.changepasswordform.retype.style.backgroundColor="#FFE6F7";
			document.changepasswordform.newpassword.focus();
			return false;
		}
		else if(document.changepasswordform.newpassword.value.length>20)
		{
			alert("Password cannot be of more then 20 characters.");
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7"; 
     		document.changepasswordform.newpassword.focus();
     		return false;
     	}
     	else if(document.changepasswordform.newpassword.value.length < minlen)
     	{
     		alert("Minimum "+minlen+" characters required for Password.");
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7"; 
     		document.changepasswordform.newpassword.focus();
     		return false;
     	}
     	
		var alphaExp = /[0-9]/;
    	var charExp = /[a-zA-Z]/;
    	var newPassword = document.changepasswordform.newpassword.value;
    	
		if(newPassword.match(alphaExp) && newPassword.match(charExp)){
			return true;
		}
		else
		{
			alert("Password must be alpha numeric");
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7"; 
     		document.changepasswordform.newpassword.focus();
     		return false;
	   }
	}
		
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.changepasswordform.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
	
</SCRIPT>
<s:head />
</head>
<body>
<br>
<br>
<br>
<s:actionerror /> <s:actionmessage /> <s:fielderror />
<br />
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder"><center><div class="all_title"><b style="float: left;">Change Password</b></div></center>
<!-- <div align="center"><img
	src="images/Header_Images/Utility/Change_Password.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-left: 3px; width: 100%; padding-bottom: 15px; border-top: none;"
	align="center"><br>

<s:if test='isAdUser == "Y"'>
<h2> You are logged in with AD User, so no need to change password. </h2>
</s:if>

<s:else>
<div align="left"><s:form action="UpdatePassword"
	name="changepasswordform" onblur="getfocus();">
	<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
		Fields with (<span style="color: red;" >*</span>) are mandatory.
	</div>
	<table width="100%">
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Current Password
				 <span style="font-size:20px;color:red">*</span></td>
			<td align="left">
			<%-- <s:password name="dbpassword" id="oldpassword" cssClass="textbox" /> --%>
			<input type="text" name="dbpassword"id="oldpassword" autocomplete="off"></input>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">New Password
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left">
			<%-- <s:password name="newpassword" showPassword="true"id="newpassword" cssClass="textbox" /> --%>
			<input type="text" name="newpassword"id="newpassword" autocomplete="off" showPassword="true"></input>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Retype New Password
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left">
			<%-- <s:password name="retype" showPassword="true" id="retype"cssClass="textbox" /> --%>
			<input type="text" name="retype"id="retype" autocomplete="off" showPassword="true"></input>
			
			</td>
		</tr>
		<tr>
		 <td colspan="2">
		</tr>
		<tr>
			<td colspan="2" align="center"><s:submit name="submitBtn" id="changeButton" value="Change"
				cssClass="button" indicator="indicator" onclick="return validate();" />

			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		
		<div id="message" class="checkboxes" style="position: absolute;display: block;top: 35px;margin-left: 950px;"><br/>
				  <%-- <label><input type="checkbox" id="specialCharacters" onclick="" disabled="disabled"><span> Special Characters (-+=_!@#$%^&*)</span></label><br/> --%>
				  <input type="checkbox" id="specialCharacters" onclick="" disabled="disabled">&nbsp;<div class="hoverWrapper" style="display: inline-block;">Special Characters &nbsp;<div id="hoverShow1"> (- + = _ ! @ # $ % ^ & *)</div></div>
				  <br/>
				  <input type="checkbox" id="lowercaseChkBox" onclick="" disabled="disabled"><span> Lowercase (Minumum 1)</span><br/>
				  <input type="checkbox" id="capitalChkBox" onclick="" disabled="disabled"><span> Uppercase (Minumum 1)</span><br/>
				  <input type="checkbox" id="numberChkBox" onclick="" disabled="disabled"><span> Number (Minumum 1)</span><br/>
				  <input type="checkbox" id="length" onclick="" disabled="disabled"><span> At least ${minLength} characters</span><br/>
				  <input type="checkbox" id="retypeNewPassword" onclick="" disabled="disabled"><span> Passwords Match</span><br/>
				  <input type="hidden" id="passLength" value="${minLength}"/>
		</div>
				
	</table>
	<s:hidden id="minLength" name="minLength"></s:hidden>
	<s:hidden id="showReminder" name="showReminder"></s:hidden>
</s:form></div>

</s:else>

</div>
</div>
</div>
</div>


<script type="text/javascript">
	
function hideButton() {
	
	//debugger;
	//alert("test");
	
	 $('#oldpassword').bind('copy paste', function (e) {
	       e.preventDefault();
	    });
	   
	   $('#newpassword').bind('copy paste', function (e) {
	       e.preventDefault();
	    });
	   
	   $('#retype').bind('copy paste', function (e) {
	       e.preventDefault();
	    });
	   
	    document.getElementById('oldpassword').value  = "";
		document.getElementById('newpassword').value  = "";
		document.getElementById('retype').value  = "";
	
		document.getElementById('changeButton').disabled  = true;
		if(document.getElementById('changeButton').disabled  == true){
			document.getElementById('changeButton').style.background = 'grey';
		}
	
}

window.onload = hideButton;
 
var myInput = document.getElementById("newpassword");
var RetypePassword = document.getElementById("retype");
var SC = document.getElementById("SC");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");
var retypeNewPassword = document.getElementById("retypeNewPassword");
document.getElementById("message").style.display = "block";

// When the user clicks on the password field, show the message box
myInput.onfocus = function() {
  //document.getElementById("message").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
myInput.onblur = function() {
  //document.getElementById("message").style.display = "none";
}
RetypePassword.onkeyup = function() {
	debugger;
	 var specialCharactersChkbx=document.getElementById('specialCharacters');
		var LowerCaseChkBx=document.getElementById('lowercaseChkBox');
		var UpperCaseChkBx=document.getElementById('capitalChkBox');
		var NumberChkBx=document.getElementById('numberChkBox');
		var MinLengthChkBx=document.getElementById('length');
		var PassMatchChkBx=document.getElementById('retypeNewPassword');
		if($("#retype").val()!=""){
			 if($("#newpassword").val() == $("#retype").val()){
				  document.getElementById("retypeNewPassword").checked = true;
					if(specialCharactersChkbx.checked==true){
						if(LowerCaseChkBx.checked==true){
							if(UpperCaseChkBx.checked==true){
								if(NumberChkBx.checked==true){
									if(MinLengthChkBx.checked==true){
										if(PassMatchChkBx.checked==true){
											document.getElementById('changeButton').disabled  = false;
											document.getElementById('changeButton').style.background = '#2e7eb9';
										}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
									}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
								}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
							}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
						}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
					}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
				}else{
					document.getElementById("retypeNewPassword").checked = false;
					if(specialCharactersChkbx.checked==true){
						if(LowerCaseChkBx.checked==true){
							if(UpperCaseChkBx.checked==true){
								if(NumberChkBx.checked==true){
									if(MinLengthChkBx.checked==true){
										if(PassMatchChkBx.checked==true){
											document.getElementById('changeButton').disabled  = false;
											document.getElementById('changeButton').style.background = '#2e7eb9';
										}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
									}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
								}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
							}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
						}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
					}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
				}
		}
	 
}
// When the user starts to type something inside the password field
myInput.onkeyup = function() {
	var specialCharactersChkbx=document.getElementById('specialCharacters');
	var LowerCaseChkBx=document.getElementById('lowercaseChkBox');
	var UpperCaseChkBx=document.getElementById('capitalChkBox');
	var NumberChkBx=document.getElementById('numberChkBox');
	var MinLengthChkBx=document.getElementById('length');
	var PassMatchChkBx=document.getElementById('retypeNewPassword');
	if(specialCharactersChkbx.checked==true){
		if(LowerCaseChkBx.checked==true){
			if(UpperCaseChkBx.checked==true){
				if(NumberChkBx.checked==true){
					if(MinLengthChkBx.checked==true){
						if(PassMatchChkBx.checked==true){
							document.getElementById('changeButton').disabled  = false;
							document.getElementById('changeButton').style.background = '#2e7eb9';
						}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
					}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
				}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
			}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
		}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
	}else{document.getElementById('changeButton').disabled  = true;document.getElementById('changeButton').style.background = 'grey';}
  // Validate lowercase letters
  debugger;
  var specialCharacters = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})";
  var regex = /^[A-Za-z0-9 ]+$/
  var isValid = regex.test(document.getElementById("newpassword").value);
  if(document.getElementById("newpassword").value!=""){
  if(!isValid) {  
    //SC.classList.remove("invalid");
    //SC.classList.add("valid");
	document.getElementById("specialCharacters").checked = true;
  } else {
    //SC.classList.remove("valid");
  //  SC.classList.add("invalid");
	document.getElementById("specialCharacters").checked = false;
  }
  }else{
	  document.getElementById("specialCharacters").checked = false;
  }
  
  
  var lowerCaseLetters = /[a-z]/g;
  if(myInput.value.match(lowerCaseLetters)) {  
    //letter.classList.remove("invalid");
    //letter.classList.add("valid");
	document.getElementById("lowercaseChkBox").checked = true;
  } else {
    //letter.classList.remove("valid");
    //letter.classList.add("invalid");
	document.getElementById("lowercaseChkBox").checked = false;
  }
  
  // Validate capital letters
  var upperCaseLetters = /[A-Z]/g;
  if(myInput.value.match(upperCaseLetters)) {  
    //capital.classList.remove("invalid");
    //capital.classList.add("valid");
	document.getElementById("capitalChkBox").checked = true;
  } else {
    //capital.classList.remove("valid");
    //capital.classList.add("invalid");
	document.getElementById("capitalChkBox").checked = false;
  }

  // Validate numbers
  var numbers = /[0-9]/g;
  var passLength=document.getElementById("passLength").value;
  
  if(myInput.value.match(numbers)) {  
   // number.classList.remove("invalid");
    //number.classList.add("valid");
	document.getElementById("numberChkBox").checked = true;
  } else {
    //number.classList.remove("valid");
    //number.classList.add("invalid");
	document.getElementById("numberChkBox").checked = false;
  }
  if(myInput.value.length >= passLength) {
	  document.getElementById("length").checked = true;
  }
  else{
	  document.getElementById("length").checked = false;
  }
  /* 
  // Validate length
  if(myInput.value.length >= 8) {
  //  length.classList.remove("invalid");
    //length.classList.add("valid");
	document.getElementById("MinimumChkBox").checked = true;
  } else {
    //length.classList.remove("valid");
    //length.classList.add("invalid");
	document.getElementById("MinimumChkBox").checked = false;
	
  } */
}
		</script>

<s:if test="passExpired == true">
	<script type="text/javascript">
			<!--
			alert('Please change your Password. In case you are New User or If your password has got Expired');
			//-->
		</script>
</s:if>
</body>
</html>
