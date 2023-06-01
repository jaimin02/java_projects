<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<%@ page import="java.util.*"%>
<html>
<head>

<script type="text/javascript">
 
 function printPage()
			{
			
				document.getElementById('dontprint').style.display='none';
				window.print();
			}
		
	function temp()
	{
		
		location.replace("ProjectCommentReport.do");
		
	}
</script>

<s:head />
</head>


<body>

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<s:if test="auditTrailDtl.size==0">
	<table align="right">
		<tr>
			<td><input type="button" class="button" value="Back"
				onclick="return temp();" /></td>
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
			No Transaction Found....<br>
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
		</tr>
	</table>
	</div>

	<br>
	<div class="titlediv">Audit Trail Report</div>
	<br>
	<hr>



	<table class="datatable" align="center" width="100%">
		<thead>
			<tr>

				<th>SrNo</th>
				<th>Project</th>
				<th>Activity</th>
				<th>User Name</th>
				<th>File Name</th>
				<th>File Version Id</th>
				<th>Attribute Name</th>
				<th>Attribute Value</th>
				<th>Modified On</th>


			</tr>
		</thead>
		<tbody>
			<s:iterator value="auditTrailDtl" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

					<td>${status.count }</td>
					<td><s:property value="workspaceDesc" /></td>
					<td><s:property value="nodeDisplayName" /></td>
					<td><s:property value="userName" /></td>
					<td><s:property value="fileName" /></td>

					<td><s:property value="fileVersionId" /></td>

					<td><s:property value="attrName" /></td>
					<td><s:set name="attrValue" id="attrValue"></s:set> <s:if
						test="attrValue == ''">
						<center>--</center>
					</s:if> <s:else>
						<s:property value="attrValue" />
					</s:else></td>

					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>

				</tr>
			</s:iterator>
		</tbody>


	</table>
</s:else>


</body>
</html>
