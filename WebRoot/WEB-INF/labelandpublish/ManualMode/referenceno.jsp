<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="ReferenceNo"><s:property value="ReferenceNo" /></div>
<div class="displayReferenceNodeId"
	style="position: fixed; top: 5px; left: 40%"><s:if
	test="nodelist!=null || nodelist.size>=0">
	<s:select list="nodelist" name="seqnodelist" headerKey=""
		headerValue="Select Reference File" listKey="referenceNodeId"
		listValue="href" value="" id="nlist"
		onchange="changenodereference(this.value);">
	</s:select>

</s:if></div>

<!-- 
This File call from  ManualModeTree.jsp By AJAX call
Created on : 14/06/2013 for file reference Number  
 -->