<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<script type="text/javascript">
	function riteclick(divid)
	{		
		var str=document.getElementById(divid).style.display;
		if (str=='block')
			document.getElementById(divid).style.display='none';
		else
			document.getElementById(divid).style.display='block';
	}
</script>
<s:head />
</head>
<body>
<%int srno=1; %>

<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Attribute Value</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="attribComboValue">
			<tr>
				<td><%=srno++ %></td>
				<td><s:property value="attrValue" /></td>
				<td><a href="#"
					onclick="riteclick('<s:property value="attrValueId"/><s:property value="attrValue"/>');">Edit</a>
				<span
					id="<s:property value="attrValueId"/><s:property value="attrValue"/>"
					style="display: none"> <s:form
					action="EditAttributeMatrixValue" method="post">
					<input type="hidden" name="iattrid"
						value="<s:property value="attrId"/>">
					<input type="hidden" name="vattrvalue"
						value="<s:property value="attrValue"/>">					
					New Value: <input type="text" name="newValue">
					&nbsp;&nbsp;<input type="submit" value="Save">
				</s:form> </span></td>
			</tr>
		</s:iterator>
	</tbody>

</table>
</body>
</html>