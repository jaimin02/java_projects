<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
</head>
<body>

<%
 ActionContext.getContext().getSession().put("ws_id",request.getParameter("ws_id").toString());
 %>

<iframe width="40%" height="450px" name="nodeFrame" scrolling="auto"
	frameborder="0" src="WorkspaceEctdAttrNodes.do"> </iframe>
<iframe width="60%" height="450px" name="attributeFrame"
	scrolling="auto" frameborder="0" src="WorkspaceEctdSetAttribute.do">
</iframe>

</body>
</html>