<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<s:select list="getParentOpDetail" name="code" listKey="operationCode"
	listValue="operationName" multiple="true">
</s:select>


