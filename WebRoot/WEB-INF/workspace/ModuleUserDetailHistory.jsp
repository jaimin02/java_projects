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
	<div class="boxborder"><div class="all_title"><b style="float:left">User Audit Trail-Module Specific</b></div>
<br>
<div class="grid_clr" align="center">
<table id = "moduleUserDetailHistory" class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<!-- <th>Project</th> -->
			<th><s:property value="lbl_nodeName"/> [<s:property value="lbl_folderName"/>]</th>
			<!-- <th>Folder Name</th>
			<th>Node Display Name </th> -->
			<th>User Profile </th>
			<th>Username</th>
			<th>User Role</th>
			<th>Modified by</th>
			<th>Stages</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for change</th>
			<th>Current Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="moduleUserDetailHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<%-- <td><s:property value="workSpaceDesc" /></td> --%>
				<td><s:property value="nodeName" /> [<s:property value="folderName" />]</td> 
				<%-- <td><s:property value="folderName" /></td>
				<td><s:property value="nodeDisplayName" /></td> --%>
				<td><s:property value="userGroupName" /></td>
				<td><s:property value="userName"/></td>
				<td><s:property value="roleName"/></td>
				<td><s:property value="modifyByName" /></td>
				<td><s:property value="stages"/></td>
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="Remark"/></td>
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
</div>
</div>
</div>
</div>
<br>
</body>
</html>