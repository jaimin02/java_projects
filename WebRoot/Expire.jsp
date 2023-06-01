<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
<SCRIPT type="text/javascript">

if(window.parent){
	window.parent.location="Login_input.do";
}else{

window.location="Login_input.do";
}


</SCRIPT>

</head>
<body>

<div id="msgDiv" align="center" style="color: green; font-weight: bold;">
<s:actionmessage /></div>

<div id="errDiv" align="center" style="color: red; font-weight: bold;">
<s:actionerror /> <s:fielderror></s:fielderror></div>
<br>
<br>
<div id="extraHtmlDiv"><a href="Login_input.do" target="">Click
Here To Login.</a></div>
</body>
</html>