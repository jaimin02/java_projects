<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<html>
<head>


<script type="text/javascript">
 
 function printPage()
			{
			
				document.getElementById('dontprint').style.display='none';
				window.print();
			}
	
	function back()
	{
		location.replace("ShowVersions.do");
	}
</script>

<s:head />


</head>



<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:if test="workspaceNodeVersionDetail.size == 0">


	<table align="right">
		<tr>
			<td><input type="button" class="button" value="Back"
				onclick="return back();" /></td>
		</tr>
	</table>
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
			No Match Found In Your WorkSpace.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>

</s:if>
<s:else>
	<div id="dontprint" style="display: block;">
	<table width="100%">
		<tr>

			<td align="right" width="100%"><input type="button"
				class="button" value="print" onclick="return printPage();" />
			<td align="left"><input type="button" class="button"
				value="Back" onclick="return back();" />
		</tr>
	</table>
	</div>
	<%int srNo = 1; %>
	<br>

	<div class="titlediv"><u>Version Maintenance Report</u></div>
	<br>
	<table width="95%" align="center" class="datatable">
		<thead>
			<tr>

				<th>#</th>
				<th>Version</th>
				<th>Modified By</th>
				<th>Modified On</th>
				<th>File Name</th>
				<th>Published</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="workspaceNodeVersionDetail"
				id="workspaceNodeVersionDetail" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><%=srNo++%></td>
					<td><s:property value="userDefineVersionId" /></td>
					<td><s:property value="userName" /></td>
					<td><s:date name="executedOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="fileName" /></td>
					<td><s:property value="Published" /></td>
				</tr>
			</s:iterator>


		</tbody>
	</table>
</s:else>

</body>
</html>
