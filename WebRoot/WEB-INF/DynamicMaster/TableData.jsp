<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
</head>
<body>
<div align="center"><br>
<br>

<s:if test="tableDetails.getTableData().size()>0">
	<table class="datatable" id="tableHeader"
		width='<s:property value="(tableDetails.getNoOfColumns())*110"/>px'
		align="center" style="text-align: center; table-layout: fixed">
		<tr>
			<th style='text-align: center;'>Table Data(<s:property
				value="tableDetails.getTableName()" />)<span
				style="text-align: right;" id='signDiv2'
				onclick="tglDiv('tbData','signDiv2');">[=]</span> &nbsp;Page <select
				id='pgSel' onchange="pgChng();"></select> of <span id='totPgs'></span></th>
		</tr>
	</table>
	<div id="tbData"
		style="max-height:150px ;overflow: auto;width: <s:property value='(tableDetails.getNoOfColumns())*110'/>px">
	<table class="datatable" id="tableGrid"
		width='<s:property value="(tableDetails.getNoOfColumns())*110"/>px'
		align="center" style="text-align: center; table-layout: fixed;">

		<tr id='headerRow'>
			<s:iterator value="tableDetails.getColumnNames()" status="stat">
				<s:if test='#stat.count>1'>
					<th><s:property value="top" /></th>
				</s:if>
			</s:iterator>
			<!-- <th>Action</th> -->
		</tr>
		<s:iterator value="tableDetails.getTableData()" status="stat1">
			<s:if test="#stat1.count<=5">
				<tr id="dataRow<s:property value="#stat1.count"/>"
					class="Page-<s:property value="((#stat1.index)/5)+1"/>">
					<s:iterator value="top" status="stat2">
						<s:if test="#stat2.count>1">
							<td title='<s:property value="top" />' class="data"><s:if
								test="top.length()>10">
								<s:property value="top.substring(0,10)" />...
									</s:if> <s:else>
								<s:property value="top" />
							</s:else></td>
						</s:if>
					</s:iterator>
					<!-- <td>
							<img src="images/Common/edit.png" alt="Edit" onclick="showRow(<s:property value='#stat1.count'/>);">
							<input type="button" class="button" value="Edit" onclick="showRow(<s:property value='#stat1.count'/>);" style='display:none;'/>
						</td> -->
				</tr>
				<tr style="display: none;"
					id="editRow<s:property value="#stat1.count"/>">
					<s:iterator value="top" status="stat4">
						<s:if test="#stat4.count>1">
							<td><input type="text" name="newValues"
								value="<s:property value='top' />" style="width: 90px;"
								title="<s:property value='top' />" /></td>
						</s:if>
					</s:iterator>
					<td><img src="images/Common/edit.png" alt="Save"
						onclick="hideRow(<s:property value='#stat1.count'/>);"> <img
						src="images/Common/edit.png" alt="Cancel"
						onclick="hideRowWithoutSave(<s:property value='#stat1.count'/>);">
					<input type="button" class="button" value="Save"
						onclick="hideRow(<s:property value='#stat1.count'/>);" /> <input
						type="button" class="button" value="Cancel"
						onclick="hideRowWithoutSave(<s:property value='#stat1.count'/>);" />
					<img src=images/loading.gif " alt="loading ..."
						style="display: none;" id="img<s:property value='#stat1.count'/>" /></td>
				</tr>
			</s:if>
			<s:else>
				<tr style="display: none;"
					id="dataRow<s:property value="#stat1.count"/>"
					class="Page-<s:property value="((#stat1.index)/5)+1"/>">
					<s:iterator value="top" status="stat2">
						<s:if test="#stat2.count>1">
							<td title='<s:property value="top" />' class="data"><s:if
								test="top.length()>10">
								<s:property value="top.substring(0,10)" />...
									</s:if> <s:else>
								<s:property value="top" />
							</s:else></td>
						</s:if>
						<s:else>
							//TODO
							</s:else>
					</s:iterator>
					<td><img src="images/Common/edit.png" alt="Edit"
						onclick="showRow(<s:property value='#stat1.count'/>);"> <input
						type="button" class="button" value="Edit"
						onclick="showRow(<s:property value='#stat1.count'/>);"
						style='display: none;' /></td>
				</tr>
				<tr style="display: none;"
					id="editRow<s:property value="#stat1.count"/>">
					<s:iterator value="top" status="stat4">
						<s:if test="#stat4.count>1">
							<td><input type="text" name="newValues"
								value="<s:property value='top' />" style="width: 90px;"
								title="<s:property value='top' />" /></td>
						</s:if>
					</s:iterator>
					<td><img src="images/Common/edit.png" alt="Save"
						onclick="hideRow(<s:property value='#stat1.count'/>);"> <img
						src="images/Common/edit.png" alt="Cancel"
						onclick="hideRowWithoutSave(<s:property value='#stat1.count'/>);">
					<!-- 
						<input type="button" class="button" value="Save" onclick="hideRow(<s:property value='#stat1.count'/>);"/>
						<input type="button" class="button" value="Cancel" onclick="hideRowWithoutSave(<s:property value='#stat1.count'/>);"/>
						 --> <img src=images/loading.gif " alt="loading ..."
						style="display: none;" id="img<s:property value='#stat1.count'/>" /></td>
				</tr>
			</s:else>
		</s:iterator>

	</table>
	</div>
</s:if> <s:else>
	<table class="datatable" id="tableGrid"
		width='<s:property value="(tableDetails.getNoOfColumns())*110"/>px'
		align="center" style="text-align: center; table-layout: fixed;">
		<tr id='headerRow'>
			<s:iterator value="tableDetails.getColumnNames()" status="stat">
				<s:if test='#stat.count>1'>
					<th><s:property value="top" /></th>
				</s:if>
			</s:iterator>
			<!-- <th>Action</th> -->
		</tr>
	</table>
	<font color="red">There are no records in the table !!!</font>
</s:else>
<table class="datatable"
	width='<s:property value="(tableDetails.getNoOfColumns())*110"/>px'
	align="center" style="text-align: center; table-layout: fixed;">
	<tr id="addRow" class="odd none">
		<s:iterator value="tableDetails.getColumnNames()" status="stat3">
			<s:if test="#stat3.count>1">
				<td><input type="text" style="width: 95px;" /></td>
			</s:if>
		</s:iterator>
		<td id='addNewButton'><img src="images/Common/add.png" alt="Add"
			onclick="addRecord();"></img> <input style="display: none;"
			type="button" class="button" value="Add" onclick="addRecord();">
		<img src=images/loading.gif " alt="loading ..." style="display: none;"
			id="addimg" /></td>
	</tr>
</table>
</div>
</body>
</html>