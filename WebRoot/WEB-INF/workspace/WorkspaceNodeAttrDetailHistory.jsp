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

/*  $(document).ready(function() {

	 /*	$('#Nodedetailhistory').dataTable( {
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

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<!-- <div class="headercls" align="center" style="width: 100%">Node Attribute Detail</div> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">Node Attribute Detail</b></div>
<br>
	
<br>
<div align="center">
<table id = "getNodeAttrDetailHistory" class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<!-- <th>AttributeId</th> -->
			<th>Attribute Name </th>
			<th>Attribute Value</th>
			<th>Modified By</th>
			<th>Modified On</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			 <th>Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getNodeAttrDetailHistory" status="status">
		<s:if test="attrName != 'ManualSignature' ">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<!--  <td><s:property value="attrId" /></td> -->
				<td><s:property value="attrName" /></td> 
				<td><s:property value="attrValue" /></td>
				<td><s:property value="userName" /></td>
				<!--  <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> -->
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
			     <td>
			    	<s:if test="remark==null || remark==''">-</s:if>
			    	<s:else>
			    		<s:property value="remark" />
			    	</s:else>
			    </td>
				<td>
					<s:if test ="StatusIndi!='N'">Edited
					</s:if>
					<s:else>New</s:else></td>
				
			</tr>
			</s:if>
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