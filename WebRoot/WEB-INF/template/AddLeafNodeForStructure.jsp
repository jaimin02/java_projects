<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>
<div class="titlediv" style="color: red">Add Leaf Node</div>

<div align="center"><br>
<s:form action="SaveLeafNodesStructure" method="post"
	name="createDeleteStructureForm">
	<div align="center" class="headercls"><b>Node Details</b></div>

	<div align="center" class="bdycls"
		style="padding-left: 30px; width: 100%;">
	<table align="left" width="90%" cellspacing="2" cellpadding="2">

		<s:iterator value="getNodeDetail">
			<tr align="left">
				<td class="title" width="20%" align="right"
					style="padding-right: 8px;">Node Name</td>

				<td width="80%"><s:textfield name="nodeName" size="60%"></s:textfield>
				</td>
			</tr>

			<tr align="left">
				<td class="title" align="right" style="padding-right: 8px;">Display
				Name</td>

				<td><s:textfield name="nodeDisplayName" size="60%"></s:textfield>
				</td>
			</tr>

			<tr align="left">
				<td class="title" align="right" style="padding-right: 8px;">Folder
				Name</td>
				<td><s:textfield name="folderName" size="60%"></s:textfield></td>
			</tr>
			<tr align="left">
				<td class="title" align="right" valign="top"
					style="padding-right: 8px;">Remarks</td>
				<td><TEXTAREA rows="4" cols="59" name="remark">${remark}</TEXTAREA>
				</td>
			</tr>

			<tr align="left">
				<td>&nbsp;</td>
				<td><input type='checkbox' name='publishFlag' value="Y"
					<s:if test="publishFlag == 'Y'">checked='checked'</s:if>></input>&nbsp;Publishable</td>
			</tr>

		</s:iterator>

		<!--<tr>
							<td colspan="2" align="center">
							<input type="checkbox" name="publishFlag" value="Y" id="publishFlag" checked="checked">&nbsp;
							<label for="publishFlag"><font color="navy">Publishable</font></label>
							</td>
						</tr>	-->

		<tr>
			<td colspan="2"></td>
		</tr>
		<tr align="left">
			<td align="center" colspan="2">
			<table width="100%">
				<tr align="left">
					<td width="18%">&nbsp;&nbsp;<input type="checkbox"
						name="sftnodes" value="S" id="sftnodes">&nbsp; <label
						for="sftnodes"><font color="navy">STF Node</font></label></td>
					<td width="5%"><s:submit onclick="return validation(1);"
						cssClass="button" value="Add Node Last"></s:submit></td>
					<td width="45%"><s:if test="nodeId == 1">
						<s:submit onclick="return validation(2);" cssClass="button"
							value="Add Node Before" disabled="true"></s:submit>
										&nbsp;
										<s:submit onclick="return validation(3);" cssClass="button"
							value="Add Node After" disabled="true"></s:submit>
					</s:if> <s:else>
						<s:submit onclick="return validation(2);" cssClass="button"
							value="Add Node Before"></s:submit>
										&nbsp;
										<s:submit onclick="return validation(3);" cssClass="button"
							value="Add Node After"></s:submit>
					</s:else></td>

				</tr>
			</table>
			</td>
		</tr>

	</table>
	</div>
	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="operation" value="-"></s:hidden>
	<s:hidden name="STFNode" value="N"></s:hidden>

</s:form></div>

