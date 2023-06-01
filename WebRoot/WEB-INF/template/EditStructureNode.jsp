<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:form action="SaveStructureNodes" method="post" name="structureForm">
	<!--<table width="95%" border="0" cellpadding="2" >
	  			<tr><td align="center" colspan="2"><b><font color="#c00000">Change Node Details</font></b></td></tr>
	  		</table>
		
			<div class="headercls" style="width: 95%" align="center">
				Node Details
			</div>
			-->
	<br />
<s:if test="userTypeName=='SU'">
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
				<td><input type='checkbox' name='publishFlag' value="Y"
					<s:if test="publishFlag == 'Y'">checked='checked'</s:if>></input>Publishable</td>
				<td><input type="checkbox" name="isOnlyFolder" value="Y"
					<s:if test="nodeTypeIndi=='F'">checked='checked'</s:if> /> <font
					color="navy">Skip XML Node</font></td>
			</tr>
		</s:iterator>

		<tr align="left">
			<td>&nbsp;</td>
			<td><s:submit cssClass="button" value="Save" />
			&nbsp;&nbsp;&nbsp; <input type="button" value="Delete"
				onclick="return DeleteNode(${nodeId });" class="button"></td>
		</tr>

	</table>
	</div>
</s:if>
	<s:hidden name="nodeId"></s:hidden>
</s:form>
<br>
<s:if test="userTypeName=='SU'">
  <div style="display: none">
	<table width="95%" border="0" cellpadding="2">
	   <tr>
		<td align="center" colspan="2"><b><font color="#c00000">Attribute
		Details</font></b></td>
	   </tr>
     </table>

<%int srNo = 1; %> <br>

<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Attribute Name</th>
			<th>Attribute Value</th>
			<th>ModifyOn</th>
		</tr>

	</thead>
	<tbody>
		<s:iterator value="getNodeAttrDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><%=srNo++ %></td>
				<td><s:property value="attrName" /></td>
				<td><s:hidden name="attrValue" id="attrValue"></s:hidden> <s:if
					test="attrValue == ''">
					<font color="red">NULL</font>
				</s:if> <s:else>
					<s:property value="attrValue" />
				</s:else></td>
				<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>

			</tr>
		</s:iterator>
	</tbody>
</table>
  </div>
</s:if>
