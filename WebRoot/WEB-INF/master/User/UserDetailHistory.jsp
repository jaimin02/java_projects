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
	<div class="boxborder" style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; ">
	<div class="all_title"><center><b style="float: left;">User Detail History</b></center></div>
<br>
<div style="border: 1px; border-radius: 0px 0px 0px 0px; border-color: #5A8AA9; padding-bottom: 15px; border-top: none;"
	align="center">
<table id = "userDetailHistory" class="datatable paddingtable audittrailtable"style="width:98%; border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;" align="center">
	<thead>
		<tr>
			<th>#</th>
			<th>Username</th>
			<!-- <th>Login Name</th> -->
			<th>Login Name</th>
			<th>Group</th>
			<th>User Role</th>
			<th>Location</th>
			<th>User Type</th>
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			<th>Current Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="userDetailHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="userName" /></td>
				<%-- <td><s:property value="loginId" /></td> --%>
				<td><s:property value="loginName" /></td>
				<td><s:property value="userGroupName" /></td>
				<td><s:property value="roleName" /></td>
				<td><s:property value="locationName" /></td>
				<td><s:property value="userType" /></td>
				<td><s:property value="modifyByName" /></td>
				<td><s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="remark"/></td> 
			<s:if test="statusIndi=='N'">
				<td>New</td>
			</s:if>
			<s:elseif test="statusIndi=='E'">
				<td>Edited</td>
			</s:elseif>
			<s:elseif test="statusIndi == 'A'">
				<td>Active</td>
			</s:elseif>
			<s:elseif test="statusIndi=='D'">
				<td>Inactive</td>
			</s:elseif>
			<s:elseif test="statusIndi == 'B'">
				<td>Block</td>
			</s:elseif>
			<s:elseif test="statusIndi == 'U'">
				<td>UnBlock</td>
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
</body>
</html>