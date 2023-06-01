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

	<div class="headercls" align="center" style="width: 100%">Publish Detail History</div>
<br>
<div align="center">
<table id = "publishDetailHistory" class="datatable paddingtable" width="100%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<!-- <th>Project Name </th> -->
			<th>Sequence No.</th>
			<th>Submission Mode</th>
			<th>Submission Info.</th>
			<th>Confirm</th>
			<th>Confirmed By</th>
			<th>Modified By</th>
			<th>Date Of Submission</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="publishDetailHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<%-- <td><s:property value="workspaceDesc" /></td> --%>
				<td><s:property value="currentSeqNumber" /></td>
				<td><s:property value="submissionMode"/></td>
				<td><s:property value="SubmissionDesc"/></td>
			<s:if test="Confirm=='Y'">
				<td>Confirmed</td>
			</s:if>
			<s:else>
				<td>-</td>
			</s:else>
				<%-- <td><s:property value ="Confirm"/></td> --%>
				<td><s:property value="ConfirmedBy" default="-" /></td>
				<td><s:property value="ModifiedBy" default="-" /></td>
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
			<s:if test="Remark == null|| Remark == '' ">
				<td>-</td>
			</s:if>
			<s:else>
			   <td><s:property value="Remark"/></td>
			</s:else>
			<s:if test="StatusIndi=='N'">
				<td>New</td>
			</s:if>
			<s:elseif test="StatusIndi=='E'">
				<td>Edited</td>
			</s:elseif>
			<s:elseif test="StatusIndi=='D'">
				<td>Deleted</td>
			</s:elseif>
			<s:elseif test="StatusIndi=='L'">
				<td>Locked</td>
			</s:elseif>
			<s:elseif test="StatusIndi=='U'">
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
<br>
<div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
<br>
</body>
</html>