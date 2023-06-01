<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<body>
Excel File Imported ...!
<br>
&nbsp;&nbsp;&nbsp;&nbsp;Master '
<s:property value="tableName" />
' created.
<br>
&nbsp;&nbsp;&nbsp;&nbsp;
<s:property value="tableData.size()" />
records inserted.
<br>
<br>
<table class="datatable" id="xlsTableList">
	<tr>
		<th>Table Name</th>
		<th>File Name</th>
		<th>Creation Date</th>

	</tr>
	<s:iterator value="xlsTableList" status="status">
		<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			<td><s:property value="vTableName" /></td>
			<td><s:property value="vFileName" /></td>
			<td><s:date name="dModifyOn" format="MMM-dd-yyyy" /></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>