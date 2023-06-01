<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>

<div align="center"><img
	src="images/Header_Images/User/Add_User.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
	<%int srNo = 1; %>
<table id="userstatustable" width="95%" align="center" class="datatable">
	<thead>
			<tr>
				<th>#</th>
				<th>UserName</th>
				<th>Login On</th>
				<th>Logout On</th>
				<th>Status</th>
		  </tr>
		</thead>
		<tbody>
			<s:iterator value="userstatusdata" status="status">

				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><%=srNo++ %></td>
					<td><s:property value="userName" /></td>
					<td><s:date name="loginOn"  format="dd-MMM-yyyy HH:mm" />
				    <td><s:date name="loginOut"  format="dd-MMM-yyyy HH:mm" />
				    <td><s:if test="(loginOut==null|| loginOut=='' || loginOn=='' || loginOn==null)">DeActive</s:if><s:else>Active</s:else> </td>
				</tr>
			</s:iterator>
			</tbody>
</table>
</div>
</div>
</body>
</html>
