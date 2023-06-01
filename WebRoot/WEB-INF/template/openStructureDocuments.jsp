<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html>
<head>
<s:head />
<style>

#nodeFrame {
	width: 100%;
	height: 100%;
	border: none;
}

#framBodyDiv {
	/*width: 1000px;*/
	height: 100%;
	border: 0px;
}

#framBodyDiv>div {
	display: inline-block;
}

#resizable {
	/*width: 325px;*/
	height: 543px;
	border: 1px solid #ccc;
}

#resizable:hover {
	border-right-color: #304040;
}

#resizable2 {
	/*width: 658px;*/
	height: 543px;
	border-radius: 1px;
	border: 1px solid #ccc;
}

#resizable2:hover {
	
}
#attributeFrame1 {
	width: 100%;
	height: 100%;
	border: none;
}

#slider {
	cursor: pointer;
	border: none;
	height: 1px;
	color: gray;
	background-color: gray;
}
</style>
</head>
<body>

<%
 	ActionContext.getContext().getSession().put("templateId",request.getParameter("templateId").toString());
 %>
<%--  <s:if test = "userTypeName='SU'"> --%>
 <div class="container-fluid">
          
<div id="framBodyDiv">
<div class="col-md-4" id="resizable" style="padding:0px;">
<iframe align="left" id="nodeFrame" name="nodeFrame" scrolling="auto" frameborder="0"
	style="border: 1px solid #669;" src="DocStructureOpenNodes_b.do"> </iframe>
</div>
<%-- </s:if>
<s:else>
<div align="left" style="float: left;"><iframe width="250px"
	height="450px" name="nodeFrame" scrolling="auto" frameborder="0"
	style="border: 1px solid #669;" src="PreviewStructureOpenNodes.do"> </iframe>
</div>
</s:else> --%>
<div class="col-md-8" id="resizable2" style="padding:0px;">
<iframe align="left" id="attributeFrame1" name="attributeFrame1" scrolling="auto" frameborder="0"
	style="border: 1px solid #669; border-right: 1px solid #669;">
</iframe></div>
</div>
</div>


</body>
</html>
