<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />


</head>
<body>

<s:if test="getDetails.size==0">
	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No Match Found In Your Template<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>

</s:if>
<s:else>

	<br>
	<hr>

	<div class="titlediv"><u>Set Default Attribute Value</u></div>
	<br>
	<table align="center" width="40%">

		<tr align="left">

			<td class="title" width="40%">Set Default Value As &nbsp;</td>
			<td width="40%"><s:subset count="1" source="getDetails">
				<s:iterator>
					<s:if test="attrForIndiId == 'Text'">
						<input type="text" name="attributeValue">
					</s:if>
					<s:if test="attrForIndiId == 'Combo'">
						<s:select list="getAttrValueFromMatrix" name="attributeValue"
							listKey="attrValue" listValue="attrValue">
						</s:select>
					</s:if>
				</s:iterator>
			</s:subset></td>

			<td width="20%"><s:hidden name="Flag" value="false"></s:hidden>
			<s:submit cssClass="button" value="SaveToAll"></s:submit></td>
		</tr>
	</table>
	<hr>
	<div class="titlediv"><u>Details</u></div>
	<br>
	<table align="center" width="95%" class="datatable">
		<thead>
			<tr>
				<th>#</th>

				<th>Node Name(Node Display Name)</th>
				<th>Existing Attr value</th>
				<th>Attribute Value</th>
				<th>Save</th>

			</tr>
		</thead>
		<tbody>
			<s:iterator value="getDetails" id="getDetails" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

					<td>${status.count}</td>
					<td><s:property value="remark" /></td>
					<td><s:property value="attrValue" /></td>
					<td><s:if test="attrForIndiId == 'Text'">
						<input type="text" name="<s:property value="nodeId"/>"
							value="<s:property value="attrValue"/>">
					</s:if> <s:if test="attrForIndiId == 'Combo'">
						<s:select list="getAttrValueFromMatrix" name="%{nodeId}"
							listKey="attrValue" listValue="attrValue">
						</s:select>
					</s:if></td>
					<td><input type="button" class="button" value="Save"
						onclick="return SaveValue('<s:property value="nodeId"/>','<s:property value="attrId"/>','<s:property value="templateId"/>');">
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>


</s:else>

</body>
</html>
