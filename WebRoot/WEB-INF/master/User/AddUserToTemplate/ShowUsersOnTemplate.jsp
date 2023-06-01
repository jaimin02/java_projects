<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<body>
<s:if test="templateUserDetailList.size == 0">
	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No Match Found In Your Template.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
</s:if>
<s:else>
	<table width="95%" align="center" class="datatable">
		<thead>
			<tr>
				<th>#</th>
				<th>Template Name</th>
				<th>User Group</th>
				<th>User</th>
				<th>From date</th>
				<th>To Date</th>
				<th>Modify On</th>
				<th>view</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="templateUserDetailList" status="status">

				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

					<td>${status.count}</td>
					<td><s:property value="templateDesc" /></td>
					<td><s:property value="userGroupName" /></td>
					<td><s:property value="username" /></td>
					<td><s:date name="fromDt" format="MMM-dd-yyyy" /></td>
					<td><s:date name="toDt" format="MMM-dd-yyyy" /></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><a href="#"
						onclick="openlinkView('<s:property value="templateId"/>','<s:property value="userCode"/>')">View</a></td>
					<td><a href="#"
						onclick="openlinkEdit('<s:property value="templateId"/>',<s:property value="userCode"/>,<s:property value="userGroupCode"/>)">Edit</a></td>
					<td><a
						href="deleteTemplateUser.do?templateId=<s:property value="templateId"/>&userId=<s:property value="userCode"/>&userGroupId=<s:property value="userGroupCode"/>">Delete</a></td>


				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else>
</body>
</html>