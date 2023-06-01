
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page"
	prefix="pages"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<%@ taglib prefix="knet" uri="/knet-tags"%>

<html>
<head>
<link href="<%=request.getContextPath()%>/decorators/maincss/main.css"
	rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="images/Common/favicon.ico" />
<title><knet:title /></title>
<script type="text/javascript">

       function PageLoad(){
       	history.go(1);       	
       	if (window.PageOnLoad)
           	PageOnLoad();
       }
         window.onbeforeunload = function() {
            if ((window.event.clientX >65 && window.event.clientY < 0) || (event.altKey == true && event.keyCode == 0)) {
                parwin = window.opener;
                if (parwin == null && typeof (parwin) == 'undefined') {
                    HandleLogOutEvent();
                }
            }
        };
        function HandleLogOutEvent() {
            var link = document.getElementById('logoutLink');
            $(location).attr('href',String(link));
        }       
 </script>

<!-- jQuery Menu -->
<link type="text/css" href="js/jquery/menu/menu.css" rel="stylesheet" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript" src="js/jquery/menu/menu.js"></script>

<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
 
<decorator:head />
<script type="text/javascript">
$(document).ready(function(){
	debugger;
	var date = (new Date().getFullYear()).toString();
	$("#copyright").text("\u00A9"+date +" Sarjen Systems Pvt. Ltd.");
});
var userCode = "<s:property value='#session.userid'/>";
var logOutFlag=false;
var inputtime=null;
var Endtime = new Date((new Date()).getTime() + ("<s:property value='#session.MaxLogintime' />" * 60 * 1000));
var tempMinutes;

	setInterval(function() { 
		if(logOutFlag==false){
		/* if(logOutFlag==false){
			Endtime = tempMinutes;
			logOutFlag=true;
		} */
	var currenttime = new Date().getTime(); 
	//debugger;
	inputtime = Endtime - currenttime; 
	var hours = ('0' + Math.floor((inputtime%(1000 * 60 * 60 * 24))/(1000 * 60 * 60))).slice(-2); 
	var minutes = ('0' + Math.floor((inputtime % (1000 * 60 * 60)) / (1000 * 60))).slice(-2); 
	var seconds = ('0' + Math.floor((inputtime % (1000 * 60)) / 1000)).slice(-2); 
	//document.getElementById("sessiontime").innerHTML = hours + ":" + minutes + ":" + seconds + "";
	var temp = document.getElementById('sessiontime');
	if (temp != null)
	document.getElementById("sessiontime").innerHTML =minutes + ":" + seconds + "";
	   if (inputtime < 1*60*1000) { 
		   if (temp != null)
	    	document.getElementById("sessiontime").innerHTML = "<span style='color: red;'>"+ minutes + ":" + seconds + ""+"</span>";  
	    	//console.log(hours + ":" + minutes + ":" + seconds);
	    	
	    		// debugger;
	    		
	    		 
	    		 	if(inputtime<1){ 
	    		 	    		 	
	    		 	if(logOutFlag==false){
	    		 	    		 		
	    		 	 	 $.ajax({
	    		 	 		   url : "CheckLoginTime_ex.do?userCode="+userCode,
	    		 	 		 success:function(data)
	    		 	 		 {
	    		 	 			 //debugger;
	    		 	 			/* alert(data) */;
	    		 	 			if(data==0 && inputtime<1){
	    		 	 				document.getElementById("sessiontime").innerHTML = "<span style='color: red;'> Expired </span>"; 
	    		 	 				var out="Logout.do";
	    		 	    			location.href=out;
	    		 	    			logOutFlag=true;
	    		 	 			}
	    		 	 			else{
	    		 	 				tempMinutes = new Date((new Date()).getTime() + (data * 60 * 1000)) ;
	    		 	 				Endtime = tempMinutes;
	    		 	 				logOutFlag=false;
	    		 	 		 	}
	    		 	 		 },
	    		 	 		 async: false,
	    		 	 	 });   
	    		 	//debugger;
	    		 	/* var out="Logout.do";
	    			location.href=out; */
	    		 	}
	    		 	}
	    	 
	    	}
		}
	}, 0); 

</script>
</head>

<body onload="PageLoad();" link="#0E4569" alink="#0E4569"
	vlink="#0E4569">

<div align="center" style="min-height: calc(100vh - 37px);">
<div id="wrap" style="width: 100%" align="center">
<div><img style="float: left; padding:8px 30px; height: 73px;"" src="decorators/maincss/ecsvlogo.png"/></div><div id="header"></div>
<div id="menuHeader" style="text-align: left;" align="center"><!-- This is for menu [start here] -->
<div id="menu"
	style="float: left; width: 100%; position: relative; z-index: 5000">
<ul class="menu">
	<s:if test="#session == null || #session.Menu == null">
		<li><a></a></li>
	</s:if>
	<s:else>
		<s:property value="#session.Menu" escape="false" />
	</s:else>
</ul>
</div>
<!-- This is for menu [end here] --> <!-- This is for display the current user name [start here] -->
<s:if test="#session.username != null">
<div id="hi" class="hi" style="width: 375px;">
<div><b>
<div style="float: left; width: 100%; margin-top:0px;">
<label style="color: #fff;font: bold 15px Calibri; float:left; margin-left:5px;">Welcome</label> 
<s:if test="#session.username == null">Guest</s:if> <s:else>
	<%-- <s:if test="#session.username.length() < 20"> --%>
		<label style="color: #043454; font: bold 15px Calibri;float:left; margin-left:5px;'">
		<s:property value='#session.username' /></label>
		<s:if test="#session.usertypename=='Inspector'">
			<label style="color: #043454; font: bold 15px Calibri;float:left; margin-left:5px;'">
			[<s:property value='#session.usertypename' /> View]</label>
		</s:if>
		<br />
	<%-- </s:if>
	<s:else>]
		<label style="color: #043454;font: bold 15px Calibri; float:left; margin-left:5px;"
			title="<s:property value='#session.username' />"><s:property
			value="#session.username.substring(0,17)+'...'" /></label>
	</s:else> --%>
</s:else></div> </b>
<diV><label style="color: #fff;font: bold 15px Calibri; float:left; margin-left: 5px;">Session</label>
<label id ="sessiontime" style="color: #043454;font: bold 15px Calibri; margin-left: 5px; float:left;"></label>

<div style="float:right; margin-top: 0px; margin-right: 25px;">
<label style="color: #fff;font: bold 15px Calibri; float:left;">Last Login</label>
<label style="color: #043454;font: bold 15px Calibri; float:left;margin-left: 5px;"> <s:property value='#session.lastLoginTime' /></label>
</div>
</div>
<br /></div>
</div>
</s:if>
</div>
<!-- This is for display the current user name [end here] --> <!-- content-wrap starts here -->
<div id="content-wrap">
<div id="main" style="margin-top: 10px;"><decorator:body /></div>
<!-- content-wrap ends here --></div>

</div>
<!-- wrap ends here --></div>

<s:url action="Logout" id="logoutUrl">
</s:url> <s:a href="%{logoutUrl}" id="logoutLink"></s:a>
<div style="visibility: hidden;"><a href="http://apycom.com/">s</a></div>



<div style="background:#64ace0; width:100%; float:left;">
<span id="copyright" style="float:left;padding-left:10px; font-family: calibri; font-size: 18px; color: #0D4264;font-weight: bold;"></span>
	<span class="plink" style="margin-top:4px; float:right; padding-right:10px; font-family: calibri; font-size: 14px; color: #0D4264;font-weight: bold;">DoQStack v1.05.00</span>
	</div>
<script>
	
</script>
</body>
</html>