<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:select list="STFCatValues" name="categoryValue" headerKey="-1"
	headerValue="Select Category Value"
	listKey="categoryValue +','+ attrName +','+ attrValue +','+categoryName"
	listValue="categoryValue">
</s:select>