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
	<!-- <div class="headercls" align="center" style="width: 100%">Project Details</div> -->
	<div class="boxborder"><div class="all_title"><center><b style="float: left;">Project Details</b></center></div>
<br>
<div class="grid_clr">
<table id = "getProjectDetailHistory" class="report paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<th>Project</th>
			<th>Software Application Group</th>
			<th>Project Type </th>
			<th>Client</th>
			<th>Location</th>
			<th>Department</th>
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getProjectDetailHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td>
					<s:if test="projectCodeName != null">
					[<s:property value="projectCodeName"/>]
					</s:if>
					<s:property value="workSpaceDesc" /></td>
					<td><s:property value="applicationName" default="-"/></td>
				<td><s:property value="projectName" /></td> 
				<td><s:property value="clientName" /></td>
				<td><s:property value="locationName" /></td>
				<td><s:property value="deptName"/></td>
				<td><s:property value="loginName"/></td>
				<!--<td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> -->
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
				<s:elseif test="statusIndi=='A'">
					<td>Archived</td>
				</s:elseif>
				<s:elseif test="statusIndi=='D'">
					<td>Deleted</td>
				</s:elseif>
				<s:elseif test="StatusIndi=='U'">
					<td>UnArchived</td>
				</s:elseif>
				<s:elseif test="StatusIndi=='V'">
					<td>Void</td>
				</s:elseif>
				<s:elseif test="StatusIndi=='W'">
					<td>UnVoid</td>
				</s:elseif>
				<s:elseif test="StatusIndi=='L'">
					<td>Locked</td>
				</s:elseif>
				<s:elseif test="StatusIndi=='X'">
					<td>UnLocked</td>
				</s:elseif>
				<s:else>
					<td>-</td>
				</s:else>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>	
</div>
<br>
<div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
</div>
</div>
</div>
</body>
</html>