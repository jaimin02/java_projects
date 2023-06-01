<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<%
	String key = UUID.randomUUID().toString();
	response.addHeader("X-CSRF-Token", key);
	response.addHeader("X-Frame-Options","SAMEORIGIN");
	response.addHeader("X-Content-Type-Options", "nosniff");
	response.addHeader("X-Xss-Protection", "1; mode=block");
	response.addHeader("Cache-Control", "no-cache, no-store");
	response.addHeader("Strict-Transport-Security", "max-age=31536000");
	
%>

<html>
<head>
<style>

/* #popupContact {
top:65px !important;
} */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgb(0 0 0 / 73%); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  /*padding: 20px;*/
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

	@font-face {
	    font-family: 'password';
	    font-style: normal;
	    font-weight: 400;
	   /*  src: url('images/password.ttf'); */
		src: url(data:font/woff;charset:utf-8;base64,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) format("woff");

	   
	}        
	#password1 {
            font-family: 'password';
        }
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-3.6.0.js"></script>
<!-- <SCRIPT type="text/javascript"> -->
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script> --%>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<SCRIPT type="text/javascript">
$(document).ready(function(){
	var date = (new Date().getFullYear()).toString();
	$("#copyright").text("\u00A9"+date +" Sarjen Systems Pvt. Ltd.");
	$("#copyright1").text("DoQStack v1.05.00");
	    /* $('#password1').bind('copy paste', function (e) {
	       e.preventDefault();
	    });
	   
	   $('#password').bind('copy paste', function (e) {
	       e.preventDefault();
	    }); */ 
	   
	   document.getElementById("popupContact").style.display='none'; 
	   document.getElementById("loadingpopup").style.display='block';
	   
});

	function getfocus()
	{
		var uName=document.getElementById('username');
		document.getElementById('username1').focus();
	}
	
	function clearText()
	{
		document.getElementById("username1").value="";
		document.getElementById("password1").value="";
	}
	
	function clsPass()
	{		
		document.getElementById("password1").value;
	}
	function CheckUserNameText(){
		var tempusername = document.getElementById("username1").value
		//same change in vb page on click btnMediator_Click
		//document.getElementById("username1").value=tempusername.replaceAll('\'','').replaceAll('\"','').replaceAll('<','').replaceAll('>','').
		//replaceAll('=','').replaceAll('/','').trim();
		var regEx = /^[0-9a-zA-Z@.-\s]+$/;
		   if(document.getElementById("username1").value.match(regEx))
		     {
		      return true;
		     }
		   else
		     {
				 //spclChr = true;
			     //alert("Please enter letters and numbers only.");
			     document.getElementById("username1").value = document.getElementById("username1").value.substring(0, document.getElementById("username1").value.length - 1);
			     document.getElementById("username1").value=document.getElementById("username1").value.replace(/[^a-zA-Z@.\s]/g, "");
			     
			     return false;
		     }
	}
	function dataSubmit()
	{		
		var uname=document.getElementById("username1").value;
		var upass=document.getElementById("password1").value;
		
		document.getElementById("username").value=uname;
		//document.getElementById("password").value=upass;
		document.getElementById("password").value=encyptPass;
		document.getElementById("password1").value=encyptPass;
		document.getElementById("userGroupCode").value=document.getElementById("profileData").value;
		
		document.getElementById("loginform").submit();
	
	}
	function validate()
	{	
		debugger;
		if(document.getElementById("username1").value=="" || document.getElementById("username1").value==null){
			alert("Please enter Username.");
			return false;
		}
		
		var drpDownVal=document.getElementById('profileData').value
		if(drpDownVal == -1){
			alert("Please select Profile");
			document.getElementById('loginId').value="";
			return false;
		}
		
		if(document.getElementById("password1").value=="" || document.getElementById("password1").value==null){
			alert("Please enter Password.");
			return false;
		}
		
		if(document.getElementById("username1").value!=null && document.getElementById("password1").value!=null)
		{
			dataSubmit();
			return false;
		}
		else
		{
				return true;
		}
		
	}
	
	/* function UserProfileFilter(){
		debugger;
		var UserName = document.getElementById("username1").value;
		if(UserName != ""){
			var Value="Select Profile";
			
			//alert("You selected: " + UserName);
			//document.getElementById("siteDataByCountryTD").style.display = "block";
			$.ajax({			
				url: 'GetProfileNames_ex.do?loginUserName=' + UserName,
						
				beforeSend: function()
				{
					$('#showProfile').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
				},
				success: function(data) 
		  		{
					//debugger;
					//$('#showUserDtl').html("");
					var opt;		
					if (data=="false"){
						alert("You don't have AD User rights. Please login with correct Username and Password");
						document.getElementById("profileTD").style.display = "block";
		    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
		    			document.getElementById('showProfile').innerHTML = opt;
					}
					else if (data=="true"){
						alert("You have AD User rights. Please login with correct AD Username and Password");
						document.getElementById("profileTD").style.display = "block";
		    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
		    			document.getElementById('showProfile').innerHTML = opt;
					}
					else if (data=="NotFound"){
						alert("Invalid Username. Please login with correct Username");
						document.getElementById("profileTD").style.display = "block";
		    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
		    			document.getElementById('showProfile').innerHTML = opt;
					}
					else if(data.length > 0)
			    	{
		    			debugger;
		    			document.getElementById("profileTD").style.display = "block";
		    			
		    			var usersArr = data.split('&');
		    			opt = "<option value='-1'>"+Value+"</option>";	
		    			
		    			for(i=0; i<usersArr.length;i++)
			    		{
		    				var keyValuePair = usersArr[i];
		    				var keyValuePairArr = keyValuePair.split('::');
		    				var key = keyValuePairArr[0];
		    				var value = keyValuePairArr[1];
		    				opt += "<option value='"+key+"'>"+value+"</option>";				    				
		    			}	
		    			 opt = '<select name="profileData" id = "profileData" style="width:100%; padding: 1px 22px 1px 1px; font-size: 16px;" >'+
		    					opt +
								  '</select>'; 
		    			document.getElementById('showProfile').innerHTML = opt;
		    			document.getElementById("profileTD").innerHTML = "Select Profile"; 
		    				    				
					}	
		    		else{
		    			document.getElementById("profileTD").style.display = "block";
		    			opt = "<select name='profileData' id = 'profileData' style='width:165px;'><option value=-1>No Profile Found</option></select>";	
		    			//opt = 'No Profile found';
		    			document.getElementById('showProfile').innerHTML = opt;
		    			//document.getElementById("showProfile").style.marginLeft = "74px";
		    		}
		  		}
			});
			
		}
		else{
			return false;
		}
		
	} */
	
	function openForgotPassword(){
		centerPopup();				
		loadPopup();
	}
	
	function checkUserLoginName(){
		debugger;
		var loginUserName = document.getElementById('loginId').value;
		if(loginUserName==""){
			alert("Please enter Username");
			document.getElementById('loginId').value="";
			return false;
		}
		
	
		
		$.ajax({		
			  url: "ValidateUserEmail_ex.do?loginUserName="+loginUserName,
			  success: function(data) 
			  {
				  //debugger;
				 if(data=="true"){
				 	alert("Password reset link has been sent to your registered Email Id");
				 	document.getElementById('loginId').value="";
				 	disablePopup();
				 }else{
					 alert(data);
					 document.getElementById('loginId').value="";
				 }
			  },
			  error: function(data) 
			  {
				  //debugger;
				  alert("Something went wrong while fetching data from server...");
			  },
				//async: false				
			});
	}
	function cls()
	{
		document.getElementById('loginId').value = '';
		disablePopup();
	}
	
</SCRIPT>

<s:head />

</head>
<%	
	String ipaddress="";
	/* String Verified=""; */
	if (request.getHeader("HTTP_X_FORWARDED_FOR") == null) {
		ipaddress = request.getRemoteAddr();
		/* Verified  = request.getQueryString(); */
		System.out.println(ipaddress);
		/* System.out.println(request.getQueryString());
		System.out.println(Verified); */
	} else {
		ipaddress = request.getHeader("HTTP_X_FORWARDED_FOR");
	}
	%>
<body>
<div align="center">
<div class="mainLoginPage">

<div>
<table align="center" style="float: none;" width="100%">
	<tr>
		<td>
		<div align="center" style="width: 32%; margin-top: 100px; float:right;"><s:form
			action="Login" name="loginform">
			<div><img src="images/login/ecsvlogo.png"></div>
			
			<div>
			<div align="center"
				style="height: 55px;"><s:actionmessage />
			<s:actionerror /> <s:fielderror /> <font color="red"><b>${session.Expire}</b>
			</font></div>
			</div>
			<table style="align:center;">
				<tr align="left">
					<td class="title">Username</td>
				</tr>
				<tr align="left">
					<td>
					<%--  <s:textfield name="username" id="username1" cssStyle="width:240px;"/> --%>
					<!-- <input type="text" name="username" id="username1"  style="width:240px;" onblur="UserProfileFilter();" autocomplete="off"/> -->
					<input type="text" name="username" id="username1" onkeyup="CheckUserNameText();" style="width:240px;" autocomplete="off"/> 
					</td>
				</tr>
				<%-- <tr align="left">
					<td class="title" id="profileTD">Profile</td>
				</tr>
				<tr align="left">
					<td>
					<div id="showProfile">
						<select name="profileData" id="profileData" style="width:100%; padding: 1px 22px 1px 1px; font-size: 16px;">
  							<option value="-1">Select Profile</option>
						</select>
					</div> 
					</td>
				</tr> --%>
				<tr align="left">
					<td class="title">Password</td>
				</tr>
				<tr align="left">
					<td><%-- <s:password name="password" showPassword="true"
						id="password1" onfocus="clsPass();" /> --%>
						<%-- <s:textfield name="password" id="password1" onfocus="clsPass();" cssStyle="width:240px;"/> --%>
						<input type="text" name="password" id="password1"  style="width:240px;" autocomplete="off"/>  						
						 <s:hidden	name="postLoginURL" value="%{postLoginURL}"></s:hidden> <input
						type="hidden" value="<%=ipaddress%>" name="clientIp"></td>
				</tr>
				<tr align="left" style="cursor: pointer; height: 30px;">
					<td style="text-align:center"><!--<s:submit value="Login" cssClass="button"/>--> 
					<input type="submit" value="  Login  " class="button" onclick="return validate();"> 
					<input type="button" value="  Clear  " class="button" onclick="clearText();"></td>
				</tr>
			</table>
			<a href="#" style="font-size: 18px; font-weight: bold;" onclick="openForgotPassword()">Forgot Password</a>
			<script type="text/javascript">
					<!--
						getfocus();
					//-->
				</script>
		</s:form></div>
		</td>
	</tr>
</table>
	<form action="Login.do" name="loginform" method="post" id="loginform" style="display:none;" >
		<input type="text" name="username" id="username"/>
		<input type="text" name="password" id="password"  readonly onfocus="$(this).removeAttr('readonly');"/>
		<input type="text" name="userGroupCode" id="userGroupCode"/>
		<input type="hidden" value="<%=ipaddress%>" name="clientIp" id = "clientIp">
		<%-- <input type="hidden" value="<%=Verified%>" name="Verified" id = "Verified"> --%>
	</form>
</div>
<div style="margin-top: 19px; width: 32%; float:right;" align="center">
<SPAN id="copyright1"
	style="font-family: calibri; font-size: 14px; color: #0D4264;font-weight: bold;"></SPAN>
<br/><SPAN id="copyright"
	style="font-family: calibri; font-size: 14px; color: #0D4264;font-weight: bold;"></SPAN>
	<br/>
	<%-- <span class="plink" style="font-family: calibri; font-size: 14px; color: #0D4264;font-weight: bold;"><a href="https://knowledgenet.sarjen.com" target="_blank">371: <span class="plink" style="font-family: calibri; font-size: 14px; color: #0D4264;font-weight: bold;"><a href="https://knowledgenet.sarjen.com" target="_blank">DocStack v1.03.00</a></span>  </a></span> --%>
	</div>
	<div id="popupContact" class="popcuswidth" style="height: 170px;">
		<img
			alt="Close" title="Close" src="images/Common/Close.svg" style="height:25px; width:25px;"
			onclick='cls();' class='popupCloseButton' />
		<h3 style="margin-top: 2px;">Forgot Password</h3>
		<!-- <div style="width: 100%; height: 90px; overflow: auto;"> -->
		<table id="tble" style="width:100%">
		<tr><td style="color:#000000; text-align: right; font-size: 15px;">
				<b>Username: &nbsp;</b>
			</td>
			<td>
				<input id="loginId" class="textbox" name="loginId" type="text"  
				 	style="width: 220px;"  autocomplete="off">
    		</td>
    	</tr>
			<tr><td colspan="2"></td></tr>
		<tr>
			<td colspan="2" align="center">
				<input id="resetPassword" name="recover-submit" value="Submit" type="button"
                    	class="button" onclick="checkUserLoginName()" >
			</td>
		</tr>
		</table><br>
		
		<!-- </div> -->
		</div>
	<div id="backgroundPopup"></div>
	<div id="loadingpopup" style="width: 50px;z-index: 1;position: absolute;top: 180.5px;left: 392.5px;
			padding-top: 0px;display:none">

</div>
</div>
</body>
</html>