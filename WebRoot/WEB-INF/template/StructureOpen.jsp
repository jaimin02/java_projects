<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
</head>
<body>

<%
 String templateId = request.getParameter("templateId");
ActionContext.getContext().getSession().put("templateId",request.getParameter("templateId").toString());
 %>

<div style="float: left;" align="left"><iframe width="394px"
	height="450px" name="nodeFrame" scrolling="auto" frameborder="0"
	src="templateTree.do"
	style="border-bottom: 1px solid #669; border: 1px solid #669;">
</iframe></div>
<div align="left" style="float: right;"><iframe width="600px"
	height="450px" name="attributeFrame" scrolling="auto" frameborder="0"
	src="templateNodeAttribute.do"
	style="border: 1px solid #669; border-top: 1px solid #669;"> </iframe>
</div>
</body>
</html>