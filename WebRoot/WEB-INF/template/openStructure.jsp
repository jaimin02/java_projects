<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html>
<head>
<s:head />
</head>
<body>

<%
 	ActionContext.getContext().getSession().put("templateId",request.getParameter("templateId").toString());
 %>
<%--  <s:if test = "userTypeName='SU'"> --%>
<div align="left" style="float: left;"><iframe width="400px"
	height="450px" name="nodeFrame" scrolling="auto" frameborder="0"
	style="margin-left: 1%; border: 1px solid #669;" src="StructureOpenNodes.do"> </iframe>
</div>
<%-- </s:if>
<s:else>
<div align="left" style="float: left;"><iframe width="250px"
	height="450px" name="nodeFrame" scrolling="auto" frameborder="0"
	style="border: 1px solid #669;" src="PreviewStructureOpenNodes.do"> </iframe>
</div>
</s:else> --%>
<div style="float: right;"><iframe width="950px" height="450px" 
	name="attributeFrame" scrolling="auto" frameborder="0"
	style="margin-left: -1%; border: 1px solid #669; border-right: 1px solid #669;">
</iframe></div>


</body>
</html>
