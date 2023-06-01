<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>




<%int srNo = 1; %>
<br>
<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Operation Name</th>
			<th>Operation Path</th>
			<th>Active Flag</th>
			<th>ModifyOn</th>


		</tr>
	</thead>
	<tbody>
		<s:iterator value="getOpeartons" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><%=srNo++ %></td>
				<td><s:property value="operationName" /></td>
				<td><s:property value="operationPath" /></td>
				<td><s:property value="activeFlag" /></td>

				<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>


			</tr>
		</s:iterator>
	</tbody>
</table>

