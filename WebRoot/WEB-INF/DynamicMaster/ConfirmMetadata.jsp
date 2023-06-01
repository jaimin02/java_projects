<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>Upload Excel File</title>
<style type="text/css">
.header {
	text-align: center;
	font-weight: bold;
}
</style>

</head>
<body>
<div
	style="width: 800px; height: 250px; border: 1px solid; vertical-align: middle;"
	id="confirmMetadataDiv">
<div style="text-align: right;"><img src="images/Common/Close.png"
	alt="Close" title="Close" style="cursor: pointer;" onclick="func1();" /></div>
<form action="DBImport_ex.do" method="post" name="frm" id="frm">
<table class="datatable">
	<tr>
		<th colspan="<s:property value='noOfColumns'/>" align="center"
			style="text-align: center;"><span class='header'>Excel
		Data Preview</span></th>
	</tr>
	<tr class="even">
		<td colspan="<s:property value='noOfColumns'/>"><label
			for="tableName">Table Name</label> <input name="tableName"
			id="tableName" value="<s:property value='tableName'/>"
			onchange="checkTableName();" autocomplete="off" /> <!-- input type="button" value="C" title="Check Table Name" onclick='checkTableName();' class="button"/> -->
		<img src=images/loading.gif " alt="loading ..." style="display: none;"
			id="img" />
		<div id='resDiv'><br>
		</div>
		</td>
	</tr>
</table>
<br>
<br>
<table class="datatable">
	<tr class='none even'>
		<s:iterator value="columnNamesVect">
			<td><input name="columnNames" value='<s:property value="top"/>'></td>
		</s:iterator>
	</tr>
	<s:iterator status="status" value="tableData">
		<tr class=odd>
			<s:iterator value="top">
				<td><s:property value="top" /></td>
			</s:iterator>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="<s:property value='noOfColumns'/>" align="center"
			style="text-align: center"><input type="button" value="Confirm"
			id="Confirm" onclick="return validate();" class="button">&nbsp;&nbsp;
		<input type="button" class="button" value="Cancel" onclick="func1();">
		</td>
	</tr>
</table>
<input type="hidden" name="uploadedFilePath"
	value="<s:property value='uploadedFilePath'/>" /></form>
</div>
</body>
</html>