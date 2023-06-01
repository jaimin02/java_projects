<%@ taglib prefix="s" uri="/struts-tags"%>

<s:form action="saveImportedECTD" name="">
	<s:push value="workspace">
		<table width="95%">
			<tr>
				<td width="25%" class="title">Project Name</td>
				<td align="left" width="75%"><s:textfield name="workSpaceDesc"></s:textfield>
				</td>
			</tr>
			<tr>
				<td class="title" width="15%" align="left">Region</td>
				<td align="left" width="35%"><select name="locationCode">
					<option value="-1">Select Region</option>
					<s:iterator value="getLocationDtl">
						<s:if test="statusIndi != 'D'">
							<option value="<s:property value="locationCode"/>"><s:property
								value="locationName" /></option>
						</s:if>
					</s:iterator>
				</select></td>
			</tr>
			<tr>
				<td class="title" width="10%" align="left">Department</td>
				<td align="left" width="40%"><select name="deptCode">
					<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
					<option value="-1">Select Department</option>
					<s:iterator value="getDeptDtl">
						<s:if test="statusIndi != 'D'">
							<option value="<s:property value="deptCode"/>"><s:property
								value="deptName" /></option>
						</s:if>
					</s:iterator>
				</select></td>
			</tr>

			<tr>
				<td class="title" width="15%" align="left">Client</td>
				<td align="left" width="35%"><select name="clientCode">
					<option value="-1">Select Client</option>
					<s:iterator value="getClientDtl">
						<s:if test="statusIndi != 'D'">
							<option value="<s:property value="clientCode"/>"><s:property
								value="clientName" /></option>
						</s:if>
					</s:iterator>
				</select></td>
			</tr>
			<tr>
				<td class="title" width="15%" align="left">Project Type</td>
				<td align="left" width="35%"><select name="projectCode">
					<option value="-1">Select Project Type</option>
					<s:iterator value="getProjectDtl">
						<s:if test="statusIndi != 'D'">
							<option value="<s:property value="projectCode"/>"><s:property
								value="projectName" /></option>
						</s:if>
					</s:iterator>
				</select></td>
			</tr>
			<tr>
				<td class="title" width="15%" align="left">Default Admin</td>
				<td align="left" width="35%"><select name="userCode">
					<option value="-1">Select Default Admin</option>
					<s:iterator value="getUserDtl">
						<s:if test="statusIndi != 'D'">
							<option value="<s:property value="userCode"/>"><s:property
								value="userName" /></option>
						</s:if>
					</s:iterator>
				</select></td>
			</tr>

			<tr>
				<td class="title" align="left" width="15%" id="remark">Remarks
				</td>
				<td align="left" width="35%"><s:textfield name="remark"
					size="30%"></s:textfield> <br>
				</td>
			</tr>


		</table>
		<s:submit />
	</s:push>
</s:form>