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
<div align="left" style="float: left"><iframe width="394px"
	height="450px" name="nodeFrame" ID="nodeFrame" scrolling="auto"
	frameborder="0" src="WorkspaceAdminNodes.do"
	style="border: 1px solid #669;"> </iframe></div>
<div align="left" style="float: right; padding-left: 2px;"><iframe
	width="600px" height="450px" name="attributeFrame" ID="attributeFrame"
	scrolling="auto" frameborder="0" src="WorkspaceNodeRights.do"
	style="border: 1px solid #669;"> </iframe></div>

</body>
</html>