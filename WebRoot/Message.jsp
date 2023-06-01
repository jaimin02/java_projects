<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
</head>
<body>
<div id="msgDiv" align="center" style="color: green; font-weight: bold; font-size:16px;">
<s:actionmessage /></div>
<div id="errDiv" align="center" style="color: red; font-weight: bold; font-size:16px;">
<s:actionerror /> <s:fielderror></s:fielderror></div>
<br>
<br>

<div id="extraHtmlDiv">${extraHtmlCode}</div>

</body>
</html>