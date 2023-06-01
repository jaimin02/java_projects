<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="refresh" content="5;url=<s:url includeParams="all"/>" />
<s:head />

<script language="JavaScript" type="text/javascript">

</script>

</head>

<body class="yui-skin-sam">
<br>


<div id="dialog" align="center">

<div class="hd"><font color="red">Please wait.</font></div>
<div class="bd"><img
	src="<%=request.getContextPath() %>/decorators/maincss/loading.gif">
</div>

</div>




</body>
</html>
