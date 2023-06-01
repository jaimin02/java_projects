<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<%-- <script type="text/javascript"
	src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<style>
#Nodedetailhistory_paginate{
 float:right;
}

.dataTables_info{
 float:left;
}

.dataTables_length{
 float:left;
}

#Nodedetailhistory_filter{
 float:right;
}

</style> --%>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

/* $(document).ready(function() {

	$('#Nodedetailhistory').dataTable( {
	"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
	"bJQueryUI": true,
    "sPaginationType": "full_numbers"
	 } );
}); */

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
<div class="boxborder">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="all_title"><b style="float: left;">Deleted Document Detail History</b></div>
	
<br>
<div class="grid_clr">
<table id = "Nodedetailhistory" class="datatable paddingtable audittrailtable" style="width:98%; align:center;">
	<thead>
		<tr>
			<th>#</th>
			<th><s:property value="lbl_nodeName"/>/<s:property value="lbl_folderName"/></th>
			<!-- <th>Node Display Name </th> -->
			<%-- <th><s:property value="lbl_folderName"/></th> --%>
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getDeletedNodeDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="nodeName" /> [<s:property value="folderName" />]</td>
				<%-- <td><s:property value="nodeDisplayName" /></td>  --%>
				<%-- <td><s:property value="folderName" /></td> --%>
				<td><s:property value="userName" /></td>
				<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> -->
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="remark"/></td>
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
<br>
</div>
</body>
</html>