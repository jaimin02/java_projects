<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

	function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	}

</script>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float: left;">User Audit Trail-Project Specific</b></div><br>
	
	<div class="grid_clr" style="overflow: auto;">
<table id = "workspaceUserHistory" class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<th>Project</th>
			<th>User Profile </th>
			<th>Username</th>
			<th>User Role</th>
			<%-- <th>From Date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>From Date Eastern Standard Time</th>
		</s:if>
			<th>To Date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>To Date Eastern Standard Time</th>
		</s:if> --%>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Rights Given by</th>
			<th>Rights Type</th>
			<th>Stages</th>
			<th>Reason for Change</th>
			<th>Current Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="workspaceUserHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="workspacedesc" /></td>
				<td><s:property value="userGroupName" /></td> 
				<td><s:property value="username" /></td>
				<td><s:property value="roleName" /></td>
				<%-- <td><s:property value="FromISTDate" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="FromESTDate" /></td>
			</s:if>
				<td><s:property value="ToISTDate"/></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ToESTDate" /></td>
			</s:if> --%>
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="rightsGivenBy"/></td>
				<%-- <td><s:property value="RightsType"/></td> --%>
				<td><s:if test="%{RightsType=='modulewise'}">Section Wise</s:if><s:else>Project Wise </s:else></td>
				<td><s:property value="stages"/></td>
				<td><s:property value="remark"/></td>
			<s:if test="statusIndi=='N'">
				<td>New</td>
			</s:if>
			<s:elseif test="statusIndi=='E'">
				<td>Edited</td>
			</s:elseif>
			<s:elseif test="statusIndi=='D'">
				<td>Deleted</td>
			</s:elseif>
			<s:else>
				<td>-</td>
			</s:else>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>	
<br>
<div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
<br>
</div>
</div>
</div>
</div>
</body>
</html>