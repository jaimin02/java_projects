<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<script language="javascript">
	function selectAll()
	{
		var select=document.attributeForm.multiCheckAttr.options;
		
		for(i=0;i<select.length;i++) {
			select[i].setAttribute('selected','1');
		}
		return true;
	}
	function deselectAll()
	{
		var select=document.attributeForm.multiCheckAttr.options;
		
		for(i=0;i<select.length;i++) {
			select[i].setAttribute('selected','');
		}
		return true;
	}
</script>

</head>


<body bgcolor="#f2f2f2">
<div align="center" class="maindiv"><br>
<div class="headercls">Add Attributes To <%=request.getAttribute("attrGroupName")%></div>


<s:form action="InsertAttrIntoGroup" name="attributeForm">

	<table align="center" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attribute Type</td>
			<td width=75%"><s:select list="attrForIndi" name="attrForIndiId"
				headerKey="-1" headerValue="Select Attribute Type"
				listKey="attrForIndiId" listValue="attrForIndiName"
				onchange="callonchange()">

			</s:select> <ajax:event ajaxRef="showAttributes/getAttributes" /></td>
		</tr>
		<tr>
			<td colspan="2">
			<div id="showAttributes"></div>
			</td>

		</tr>
	</table>
	<s:hidden name="attrGroupId">
	</s:hidden>
	<ajax:enable />
</s:form></div>

</body>
</html>
