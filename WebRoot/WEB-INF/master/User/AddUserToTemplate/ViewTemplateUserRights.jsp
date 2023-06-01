<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style type="text/css">
.smalltd {
	height: 10px;
	font-size: 10px;
	color: navy;
}
</style>


<script type="text/javascript">
 
 function printPage()
{

	document.getElementById('dontprint').style.display='none';
	window.print();
}
</script>

<s:head />
</head>


<body>

<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<s:if test="getUserRightsReportDtl.size == 0">

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

	<div id="dontprint" style="display: block;">
	<table width="100%">
		<tr>

			<td align="right" width="100%"><input type="button"
				class="button" value="print" onclick="return printPage();" /></td>
		</tr>
	</table>
	</div>


	<div class="titlediv">User Rights Report</div>

	<hr>

	<table class="datatable" width="95%" align="center">
		<thead>
			<tr>
				<th>#</th>
				<th>Template Name</th>
				<th>NodeDisplay Name</th>
				<th>UserName</th>
				<th>Can Read</th>
				<th>Can Edit</th>
				<th>Adv. Rights</th>

			</tr>

		</thead>
		<tbody>
			<s:iterator value="getUserRightsReportDtl"
				id="getUserRightsReportDtl" status="status">

				<s:hidden value="AdvancedRights" id="AdvancedRights" />
				<s:hidden value="editRights" id="editRights" />
				<s:hidden value="readRights" id="readRights" />

				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td>${status.count}</td>
					<td><s:property value="templateDesc" /></td>
					<td><s:property value="nodeDisplayName" /></td>
					<td><s:property value="userName" /></td>


					<td><s:if test="readRights.charAt(0) == 'Y' ">
						<b><font color="green" face="verdana">Yes</font></b>
					</s:if> <s:else>
						<b><font color="red" face="verdana">No</font></b>
					</s:else></td>

					<td><s:if test="editRights.charAt(0) == 'Y'">
						<b><font color="green" face="verdana">Yes</font></b>
					</s:if> <s:else>
						<b><font color="red" face="verdana">No</font></b>
					</s:else></td>

					<td><s:if test="AdvancedRights.charAt(0) == 'Y'">
						<b><font color="green" face="verdana">Approved</font></b>
					</s:if> <s:elseif test="AdvancedRights == 'R'">
						<b><font color="blue" face="verdana">Reviewed</font></b>
					</s:elseif> <s:else>
						<b><font color="red" face="verdana">No</font></b>
					</s:else></td>
				</tr>

			</s:iterator>
		</tbody>
	</table>
</s:else>

</body>
</html>
