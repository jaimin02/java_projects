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
	<!-- <div class="headercls" align="center" style="width: 100%">Client Detail History</div> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">Client Detail History</b></div>
<br>
<div class="grid_clr">
<table id = "clientDetailHistory" class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Exclude date</th>
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			
		</tr>
	</thead>
	<tbody>
		<s:iterator value="excludedDateHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td style="border: 1px solid black;"><s:date name="excludedDate" format="dd-MMM-yyyy" /><%-- <s:property value="excludedDate" /> --%></td>
				<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td>-->
				<td style="border: 1px solid black;"><s:property value="userName" /></td>
				<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
			</s:if>
			<td style="border: 1px solid black;"><s:property value="remark" default="-" /></td>
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