<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Unexpected Error</title>
</head>

<body>

<div align="center" style="width: 950px; overflow: auto;"><font
	color="red" face="tahoma" size="5" style="text-decoration: blink;">Error
Message</font> <br />
<b>PLease contact or send this Error to your administrator</b><br />
<s:actionerror /> <s:property value="%{exception.message}" /> <br />

<div class="lablediv" align="center">Technical Details</div>
<br />
<div align="left"
	style="width: 920px; overflow: auto; border: 1px solid; border-color: #5A8AA9">
<textarea rows="50" cols="150" style="color: #0066FF; border: none;"
	readonly="readonly">
    <s:property value="%{exceptionStack}" />
</textarea></div>
</div>


</body>
</html>
