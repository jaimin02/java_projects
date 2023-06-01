<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
<div id="showDocDtlDiv"><s:if test="docReleaseTrackList.size!=0">
	<div align="right" style="width: 99%"><s:form
		action="ExportToXls" id="myform" method="post"
		cssStyle="width: 200px;padding-bottom: 5px;height: 20px;">
		<input type="hidden" name="fileName" value="DocReleaseTrail(1).xls"
			style="">
		<textarea rows="1" cols="1" name="tabText" id="tableText"
			style="display: none;"></textarea>
		<img alt="Export To Excel" title="Export To Excel"
			src="images/Common/Excel.png"
			onclick="document.getElementById('tableText').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit();">
	</s:form></div>
	<div id="divTabText" style="width: 100%" align="left">
	<table class="datatable" width="99%" cellspacing="1"
		border="1px solid #5A8AA9">
		<thead>
			<tr align="center">
				<th align="center" rowspan="2" valign="middle"
					style="text-align: center;">#</th>
				<th align="center" rowspan="2" valign="middle"
					style="text-align: center;">Quantity</th>
				<th align="center" colspan="2" valign="middle"
					style="text-align: center;">Form Nos.</th>
				<th align="center" rowspan="2" valign="middle"
					style="text-align: center;">Date</th>
				<th align="center" rowspan="2" valign="middle"
					style="text-align: center;">Released By</th>
				<th align="center" rowspan="2" valign="middle"
					style="text-align: center;">Comments</th>
			</tr>
			<tr align="center">
				<th align="center" style="text-align: center;">From</th>
				<th align="center" style="text-align: center;">To</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="docReleaseTrackList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>"
					align="left">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="qty" /></td>
					<td><s:property value="startId" /></td>
					<td><s:property value="endId" /></td>
					<td><s:date name="releaseDate" format="MMM dd, yyyy" /></td>
					<td><s:property value="releasedByUser" /></td>
					<td><s:property value="comments" /></td>

				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</s:if> <s:else>
	<label class="title">No Document Released.</label>
</s:else></div>
</body>
</html>
