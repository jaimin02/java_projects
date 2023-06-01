<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
<div id="showDocDtlDiv">
<div><s:if test="releaseDocMgmtList.size!=0">
	<div align="right" style="width: 99%"><s:form
		action="ExportToXls" id="myform" method="post"
		cssStyle="width: 200px;padding-bottom: 5px;height: 20px;">
		<input type="hidden" name="fileName" value="DocActionDtl(1).xls"
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
			<tr>
				<th>#</th>
				<th>Title</th>
				<th>ID</th>
				<th>Action</th>
				<th>Comments</th>
				<th>Doc. Version</th>
				<th>Doc. Stage</th>
				<th>Performed By</th>
				<th>Performed On</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="releaseDocMgmtList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="nodeName" /></td>
					<td><s:property value="folderName" /></td>
					<td><s:if test="statusIndi == 'P'">
								    			Re-Printing
								    		</s:if> <s:elseif test="statusIndi == 'R'">
								    			Returned
								    		</s:elseif></td>
					<td><s:property value="comments" /></td>
					<td><s:property value="docVersion" /></td>
					<td><s:property value="stage" /></td>
					<td><s:property value="modifyByUser" /></td>
					<td><s:date name="modifyOn" format="MMM dd, yyyy. hh:mm a" />
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</s:if> <s:else>
	<label class="title">No Document Action Performed.</label>
</s:else></div>
</div>
</body>
</html>
