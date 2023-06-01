<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if
	test="getProjectLevelSearchResult == null || getProjectLevelSearchResult.size == 0">
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="46%" align="right" style="padding-right: 20px;"
				valign="middle"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td style="vertical-align: middle;" align="left"><br>

			<font size="2" color="#c00000"> <b>No Result Found. </b> </font> <br>
			&nbsp;</td>
		</tr>
	</table>
	</center>
</s:if>

<s:else>
	<div align="right" style="padding-right: 25px;"><s:form
		action="ExportToXls" method="post">
		<input type="hidden" name="fileName" value="Dossier_Status.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableText"
			style="visibility: hidden;"></textarea>
		<input type="submit" class="button"
			onclick="document.getElementById('tableText').value=document.getElementById('divTabText').innerHTML;"
			value="Export">
	</s:form></div>
	<br>

	<!-- <div style="width: 100%; height: 265px; overflow-y: scroll;"> -->
	<div id="divTabText"
		style="padding: 5px; padding-bottom: 20px; overflow: auto; border: 1px solid; max-width: 900px; height: 300px;"
		align="center"><s:set name="isBold" value=""></s:set>
	<table class="datatable doubleheight" border="1px" bordercolor="#669"
		width="98%" height="auto;">
		<thead>
			<s:subset source="getProjectLevelSearchResult" count="1" start="0">
				<s:iterator value="%{top}">
					<tr>
						<s:iterator status="status">
							<s:if test="#status.count != 1 && #status.count != 2">
								<th><s:property value="top" /> <s:if test="top == 'Field'">
									<s:set name="isBold" value="true"></s:set>
								</s:if></th>
							</s:if>
						</s:iterator>
					</tr>
				</s:iterator>
			</s:subset>
		</thead>
		<tbody>

			<s:set name="workspaceId" value=""></s:set>
			<s:subset source="getProjectLevelSearchResult" start="1">
				<s:iterator value="%{top}" status="outerStatus">
					<tr class="even" onclick="ChangeRowColor(this);">
						<s:iterator status="status">
							<s:if test="#status.count == 1">
								<s:set name="workspaceId" value="top"></s:set>
							</s:if>
							<s:if test="#status.count == 2">
								<s:set name="columnNo" value="top"></s:set>
							</s:if>
							<s:if test="#status.count != 1 && #status.count != 2">
								<s:if test="#isBold == true && #status.count == 3">
									<td style="font-weight: bold;"><s:property value="top" />
									</td>
								</s:if>
								<s:elseif
									test="!#workspaceId.equals('') && #columnNo==#status.count-2">
									<td><a
										href="WorkspaceOpen.do?ws_id=<s:property value="#workspaceId"/>">
									<s:property value="top" /></a></td>
								</s:elseif>


								<s:else>
									<td><s:property value="top" />&nbsp;</td>
								</s:else>
							</s:if>
						</s:iterator>
					</tr>
				</s:iterator>
			</s:subset>

		</tbody>
	</table>
	</div>
	<br>
	<div align="right" style="padding-right: 20px;"><s:form
		action="ExportToXls" method="post">
		<input type="hidden" name="fileName" value="Dossier_Status.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableText1"
			style="visibility: hidden;"></textarea>
		<input type="submit" class="button"
			onclick="document.getElementById('tableText1').value=document.getElementById('divTabText').innerHTML;"
			value="Export">
	</s:form></div>


</s:else>