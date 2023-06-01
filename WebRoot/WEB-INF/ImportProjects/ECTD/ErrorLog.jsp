<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	response.addHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	response.addHeader("Cache-Control", "pre-check=0,post-check=0");
	response.setDateHeader("Expires", 0);
%>

<s:if test="extraHtmlCode != null && extraHtmlCode != ''">
	${extraHtmlCode}
</s:if>
<center><s:actionerror /></center>

<s:if test="ectdErrorList.size > 0">
	<div align="right"><s:form action="ExportToXls" method="post"
		cssStyle="width: 200px;padding-bottom: 5px;height: 20px;">
		<input type="hidden" name="fileName" value="ECTD_Error_Log.xls"
			style="">
		<textarea rows="1" cols="1" name="tabText" id="tableText"
			style="display: none;"></textarea>
		<input type="submit" class="button"
			onclick="document.getElementById('tableText').value=document.getElementById('divTabText').innerHTML;"
			value="Export To Excel">
	</s:form></div>
	<script type="text/javascript">
			alert('Validator shows list of errors and warnings according to validation criteria set for KnowledgeNET by KnowledgeNET development team. The Validation criteria may defer if compared that to any agency\'s validation criteria.'+
				  '\n\nThus it is recommended that, any errors/warnings/comments found by KnowledgeNET Validator, should be taken as an error.');
		</script>
	<div id="divTabText">
	<table class="datatable">
		<thead style="background-color: activeborder;">
			<tr>
				<th>#</th>
				<th></th>
				<th>Description</th>
				<th>Destination</th>
			</tr>
		</thead>
		<tbody>

			<s:iterator value="ectdErrorList" status="stat">
				<tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#stat.count" /></td>
					<td><s:if
						test="Type == @com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType@ECTD_ERROR">
						<img src="images/ectd/error.gif" alt="ECTD Error...">
					</s:if> <s:elseif
						test="Type == @com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType@ECTD_WARNING">
						<img src="images/ectd/warning.gif" alt="ECTD Warning...">
					</s:elseif> <s:elseif
						test="Type == @com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType@ECTD_INFO">
						<img src="images/ectd/info.gif" alt="ECTD Information...">
					</s:elseif></td>
					<td><s:property value="Msg" /></td>
					<td><s:property value="XmlFileName" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</s:if>
<s:else>
	No Errors Found
</s:else>