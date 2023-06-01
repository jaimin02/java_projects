<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.2/css/bootstrap.min.css"/>
<style type="text/css">

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

input[type='checkbox']:checked:before {
  content: '';
  color: grey;
}

.hoverWrapper:hover #hoverShow1 {
 display: block;
}

.hoverWrapper #hoverShow1 {
 display: none;
 position: relative;
 width: 119px;
 height: 19px;
 float: right;
}

</style>

<SCRIPT language="javascript">

function validate()
	{
		debugger;
		var minlen = document.getElementById('minLength').value;
		var specialCharactersChkbx=document.getElementById('specialCharacters');
		var LowerCaseChkBx=document.getElementById('lowercaseChkBox');
		var UpperCaseChkBx=document.getElementById('capitalChkBox');
		var NumberChkBx=document.getElementById('numberChkBox');
		var MinLengthChkBx=document.getElementById('length');
		var PassMatchChkBx=document.getElementById('retypeNewPassword');
		var flag=false;
		var ConfirmBtn=document.getElementById('Confirm');
		
		if(specialCharactersChkbx.checked==true){
			if(LowerCaseChkBx.checked==true){
				if(UpperCaseChkBx.checked==true){
					if(NumberChkBx.checked==true){
						if(MinLengthChkBx.checked==true){
							if(PassMatchChkBx.checked==true){
								//document.getElementById('Confirm').style.visibility = 'hidden';
								flag=true;
							}
							else{
								alert('New password does not match with the retype password' );
								document.changepasswordform.newpassword.focus();
								document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
								//document.getElementById('Confirm').style.visibility = 'hidden';
								flag=false;
								return false;
							}
						}
						else{
							alert("Minimum "+minlen+" characters required For password..");
							document.changepasswordform.newpassword.focus();
							document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
							//document.getElementById('Confirm').style.visibility = 'hidden';
							flag=false;
							return false;
						}
					}
					else{
						alert("Password must contains at least one number");
						document.changepasswordform.newpassword.focus();
						document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
						//document.getElementById('Confirm').style.visibility = 'hidden';
						flag=false;
						return false;
					}
				}
				else{
					alert("Password must contains at least one upperCase character");
					document.changepasswordform.newpassword.focus();
					document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
					//document.getElementById('Confirm').style.visibility = 'hidden';
					flag=false;
					return false;
				}
			}
			else{
				alert("Password must contains at least one LowerCase character");
				document.changepasswordform.newpassword.focus();
				document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
				//document.getElementById('Confirm').style.visibility = 'hidden';
				flag=false;
				return false;
			}
		}else{
			alert("Password must contains at least one special characters");
			document.changepasswordform.newpassword.focus();
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
			//document.getElementById('Confirm').style.visibility = 'hidden';
			flag=false;
			return false;
		}
		
		
		if(document.changepasswordform.newpassword.value =="")
		{
			alert('Please enter new password');
			document.changepasswordform.newpassword.focus();
			document.changepasswordform.newpassword.style.backgroundColor="#FFE6F7";
			//document.getElementById('Confirm').style.visibility = 'hidden';
			flag=false;
			return false;
		}
	    if(document.changepasswordform.RetypeNewPassword.value =="")
		{
			alert('Please enter Retype password');
			document.changepasswordform.RetypeNewPassword.focus();
			document.changepasswordform.RetypeNewPassword.style.backgroundColor="#FFE6F7";
			flag=false;
			return false;
		}
	    if(flag!=false){
	    	 var url_string = window.location.href;
			var url = new URL(url_string);
			var loginUserName = url.searchParams.get("loginUserName"); 
			//var a= location.search.split('=');
			//var loginUserName =a[1];
	    	var dbpassword=document.getElementById('dbpassword').value;
	    	var newpassword=document.changepasswordform.newpassword.value;
	    	$.ajax({		
	  		  //url: "UploadFileInSharepoint_ex.do?path="+path,
	  		  url: "UpdateForgotPassword_ex.do?dbpassword="+dbpassword+"&loginUserName="+loginUserName+"&flag="+flag+"&newpassword="+newpassword,
	  		  beforeSend: function()
	  		  { 
	  			 // debugger;
	  			 //alert(this.url);
	  			// $('#rpButton').hide();
	  			//$('#chkUsername').css('background-color', '#E3EAF0');
	  			 //document.getElementById('rpButton').innerHTML="-"
	  		  },
	  		  success: function(data) 
	  		  {
	  			  //debugger;
	  			 if(data=="true"){
	  				alert("Password changed successfully.");
	  				var str = url_string;
	  				var word = '/';
	  				var newWord = 'test';
	  				var n = str.lastIndexOf(word);
	  				str = str.slice(0, n);
	  				location.replace(str);
	  			 }else{
	  				alert(data);
	  				document.getElementById('newpassword').value="";
	  				document.getElementById('RetypeNewPassword').value="";
	  				document.getElementById('Confirm').disabled  = true;
	  				document.getElementById('Confirm').style.background = 'grey';
	  				
	  				var specialCharactersChkbx=document.getElementById('specialCharacters');
	  				var LowerCaseChkBx=document.getElementById('lowercaseChkBox');
	  				var UpperCaseChkBx=document.getElementById('capitalChkBox');
	  				var NumberChkBx=document.getElementById('numberChkBox');
	  				var MinLengthChkBx=document.getElementById('length');
	  				var PassMatchChkBx=document.getElementById('retypeNewPassword');
	  				
	  				specialCharactersChkbx.checked=false;
	  				LowerCaseChkBx.checked=false;
	  				UpperCaseChkBx.checked=false;
	  				NumberChkBx.checked=false;
	  				MinLengthChkBx.checked=false;
	  				PassMatchChkBx.checked=false;
	  				
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
		
	}
		
	/* function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.changepasswordform.submitBtn.onclick();
  		} 
	}  */

</SCRIPT>
<s:head />
</head>
<body>
<br>
<s:actionerror /> <s:actionmessage /> <s:fielderror />
<br />
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder"><center><div class="all_title"><b style="float: left;">Change Password</b></div></center>
<!-- <div align="center"><img
	src="images/Header_Images/Utility/Change_Password.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div style="border: 1px; height: 180px; border-color: #5A8AA9; border-style: solid; padding-left: 3px; width: 100%; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
<div align="left">
			<s:form action="UpdatePassword" name="changepasswordform" onblur="getfocus();">
				<table width="100%">
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">
					<label for="exampleInputEmail1">Enter new password</label>
					<span style="font-size:20px;color:red">*</span>
			</td>
			<td align="left">
				<input id="newpassword" type="password" class="textbox" autocomplete="off">
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">
					<label for="exampleInputEmail1">Re-type password</label>
					<span style="font-size:20px;color:red">*</span>		
			</td>
			<td align="left">
				<input id="RetypeNewPassword" type="password" class="textbox" autocomplete="off">
			</td>
		</tr>
		<tr>
		 <td colspan="2">
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="button" id="Confirm" class="button" onclick="return validate()">Change</button>
			</td>
		</tr>
		<div id="message" class="checkboxes" style="position: absolute;display: block;top: 35px;margin-left: 950px;"><br/>
				  <%-- <label><input type="checkbox" id="specialCharacters" onclick="" disabled="disabled"><span> Special Characters (-+=_!@#$%^&*)</span></label><br/> --%>
				  <input type="checkbox" id="specialCharacters" onclick="" disabled="disabled">&nbsp;<div class="hoverWrapper" style="display: inline-block;">Special Characters &nbsp;<div id="hoverShow1"> (-+=_!@#$%^&*)</div></div>
				  <br/>
				  <input type="checkbox" id="lowercaseChkBox" onclick="" disabled="disabled"><span> Lowercase (Minumum 1)</span><br/>
				  <input type="checkbox" id="capitalChkBox" onclick="" disabled="disabled"><span> Uppercase (Minumum 1)</span><br/>
				  <input type="checkbox" id="numberChkBox" onclick="" disabled="disabled"><span> Number (Minumum 1)</span><br/>
				  <input type="checkbox" id="length" onclick="" disabled="disabled"><span> At least ${minLength} characters</span><br/>
				  <input type="checkbox" id="retypeNewPassword" onclick="" disabled="disabled"><span> Passwords Match</span><br/>
				   <input type="hidden" id="passLength" value="${minLength}"/>
		</div>
	<s:hidden id="minLength" name="minLength"></s:hidden>
	<s:hidden id="showReminder" name="showReminder"></s:hidden>
	<s:hidden id="dbpassword" name="dbpassword"></s:hidden></table>
</s:form></div>
</div>
</div>
</div>
</div>

<script type="text/javascript">
	
function hideButton() {
	//debugger;
	//alert("test");
	$('#newpassword').bind('copy paste', function (e) {
	       e.preventDefault();
	    });
	   
	   $('#RetypeNewPassword').bind('copy paste', function (e) {
	       e.preventDefault();
	    });
	   
	document.getElementById('Confirm').disabled  = true;
	if(document.getElementById('Confirm').disabled  == true){
		document.getElementById('Confirm').style.background = 'grey';
	}
}

window.onload = hideButton;
 
var myInput = document.getElementById("newpassword");
var RetypePassword = document.getElementById("RetypeNewPassword");
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
	//debugger;
	 var specialCharactersChkbx=document.getElementById('specialCharacters');
		var LowerCaseChkBx=document.getElementById('lowercaseChkBox');
		var UpperCaseChkBx=document.getElementById('capitalChkBox');
		var NumberChkBx=document.getElementById('numberChkBox');
		var MinLengthChkBx=document.getElementById('length');
		var PassMatchChkBx=document.getElementById('retypeNewPassword');
		if($("#RetypeNewPassword").val()!=""){
			if($("#newpassword").val() == $("#RetypeNewPassword").val()){
				  document.getElementById("retypeNewPassword").checked = true;
					if(specialCharactersChkbx.checked==true){
						if(LowerCaseChkBx.checked==true){
							if(UpperCaseChkBx.checked==true){
								if(NumberChkBx.checked==true){
									if(MinLengthChkBx.checked==true){
										if(PassMatchChkBx.checked==true){
											document.getElementById('Confirm').disabled  = false;
											document.getElementById('Confirm').style.background = '#2e7eb9';
										}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
									}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
								}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
							}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
						}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
					}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
				}else{
					document.getElementById("retypeNewPassword").checked = false;
					if(specialCharactersChkbx.checked==true){
						if(LowerCaseChkBx.checked==true){
							if(UpperCaseChkBx.checked==true){
								if(NumberChkBx.checked==true){
									if(MinLengthChkBx.checked==true){
										if(PassMatchChkBx.checked==true){
											document.getElementById('Confirm').disabled  = false;
											document.getElementById('Confirm').style.background = '#2e7eb9';
										}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
									}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
								}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
							}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
						}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
					}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
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
							document.getElementById('Confirm').disabled  = false;
							document.getElementById('Confirm').style.background = '#2e7eb9';
						}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
					}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
				}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
			}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
		}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
	}else{document.getElementById('Confirm').disabled  = true;document.getElementById('Confirm').style.background = 'grey';}
  // Validate lowercase letters
  //debugger;
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

</body>
</html>
