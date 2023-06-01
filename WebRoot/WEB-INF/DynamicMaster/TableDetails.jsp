<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<script type="text/javascript">
			
		</script>
</head>
<body>
<div align="center"><s:if test="tableDetails !=null">
	<table class="datatable" width="500px">
		<tr>
			<th colspan="2" style="text-align: center;"
				onclick="tglDiv('tbDetails','signDiv1');">Table Details(<s:property
				value="tableDetails.getTableName()" />)<span
				style="text-align: right;" id='signDiv1'>[=]</span></th>
		</tr>
	</table>
	<div id='tbDetails'>
	<table class="datatable" width="500px">
		<tr>
			<td>No Of Records:</td>
			<td id="valueOfNoOfRecords"><s:property
				value="tableDetails.getNoOfRecords()" /></td>
		</tr>
		<tr>
			<td>No Of Columns:</td>
			<td id="valueOfNoOfColumns"><s:property
				value="tableDetails.getNoOfColumns()-1" /></td>
		</tr>
		<tr>
			<td colspan="2">Columns: <br>
			<ul id="columnList">
				<s:iterator value="tableDetails.getColumnNames()" status="stat">
					<s:if test="#stat.count>1">
						<li><s:property value="top" /></li>
					</s:if>
				</s:iterator>
			</ul>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;"><input type="button"
				class="button" value="Display" onclick="displayGrid();"></td>
		</tr>
	</table>
	</div>
</s:if> <s:else>
	<font color="red">Error: May be the table is not available !!!</font>
</s:else></div>
</body>
</html>