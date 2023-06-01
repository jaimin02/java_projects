<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<s:head />
</head>
<body>

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:if test="getUserRightsReportDtl.size == 0 ">
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
	<br>
	<div style="width: 950px; padding-top: 0px; margin-top: 0px;"
		align="center">

	<div class="titlediv">User Rights Report</div>
	<br>
	<hr width="95%">
	<br>

	<div style="width: 100%;" align="right"><img alt="Print"
		title="Print" id="dontprint" src="images/Common/Print.png"
		onclick="printPage();">&nbsp; <img alt="Back" title="Back"
		src="images/Common/Back.png" onclick="temp();">&nbsp;&nbsp;</div>
	<br>
	<div align="center" style="width: 95%; border: 1px solid #5A8AA9;">
	<%int srNo = 1; %>
	<table class="datatable" width="100%" align="center">
		<thead>
			<tr>
				<th>#</th>
				<th>Project Name</th>
				<th>NodeDisplay Name</th>
				<th>UserName</th>
				<th>Can Read</th>
				<th>Can Edit</th>
				<th>Adv Rights</th>

			</tr>

			<s:iterator value="getUserRightsReportDtl" status="status">

				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><%=srNo++ %></td>
					<td><s:property value="workSpaceDesc" /></td>
					<td><s:property value="nodeDisplayName" /></td>
					<td><s:property value="userName" /></td>
					<td><s:hidden name="readRights" id="read"></s:hidden> <s:if
						test="read == Y ">
						<b><font color="green" size="1" face="verdana">Yes</font></b>
					</s:if> <s:else>
						<b><font color="red" size="1" face="verdana">No</font></b>
					</s:else></td>

					<td><s:hidden name="editRights" id="edit"></s:hidden> <s:if
						test="edit == Y ">
						<b><font color="green" size="1" face="verdana">Yes</font></b>
					</s:if> <s:else>
						<b><font color="red" size="1" face="verdana">No</font></b>
					</s:else></td>
					<td><s:if test="advancedRights == A">
						<b><font color="green" size="1" face="verdana">Approved</font></b>
					</s:if> <s:elseif test="advancedRights == R">
						<b><font color="blue" size="1" face="verdana">Reviewed</font></b>
					</s:elseif> <s:else>
						<b><font color="red" size="1" face="verdana">No</font></b>
					</s:else></td>

				</tr>
			</s:iterator>
	</table>
	</div>
	<br>
	<br>
	</div>
</s:else>
</body>
</html>
