<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

	/* function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	}
 */
</script>
</head>
<body>
<br><br>


<form id="myform" action="ExportToXls.do" method="post" >
				<input type="hidden" name="fileName" value="User_On_Project_History_Report">
				<textarea rows="1"  cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
				<img align="right" alt="Export To Excel" title = "Export To Excel" src="images/Common/Excel.png"  onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()"> &nbsp;
				<img alt="Print"  align="right" title="Print" src="images/Common/Print.png" style="padding-right: 5px;" onclick="return printPage();" >
				<br>
</form>

	<!-- <div class="headercls" align="center" style="width: 100%">Workspace User Detail History</div> -->
<br>
<div id="divTabText" style="width:100%; overflow:auto;">
<div align="center">

<TABLE  width="100%" cellspacing="0">
<tr class="none">
	<Td class="title" width="40%" align='right'><font size="4"> Project Name : </Td>
	<Td align='left' style="padding-left:8px" colspan="6"><font size="4">${workSpaceDesc}</font>
	</Td>
</tr>
</TABLE>

<table id = "workspaceUserHistory" class="datatable" border="1px" cellspacing="0" width="100%" align="center" style="border:0px solid black">


<!-- <table id = "workspaceUserHistory" class="datatable paddingtable" width="100%" align="center"> -->
	<thead>
		<tr>
			<th>#</th>
			<th>User Group </th>
			<th>User</th>
			<th>From Date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>From Date Eastern Standard Time</th>
		</s:if>
			<th>To Date</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>To Date Eastern Standard Time</th>
		</s:if>
			<th>ModifyOn</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Rights Given By</th>
			<th>Rights Type</th>
			<th>Stages</th>
			<th>Reason for Change</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="workspaceUserHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="userGroupName" /></td> 
				<td><s:property value="username" /></td>
				<td><s:property value="FromISTDate" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="FromESTDate" /></td>
			</s:if>
				<td><s:property value="ToISTDate"/></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ToESTDate" /></td>
			</s:if>
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="rightsGivenBy"/></td>
				<td><s:property value="RightsType"/></td>
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
</div>
<br>
<!-- <div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div> -->
<br>
</body>
</html>