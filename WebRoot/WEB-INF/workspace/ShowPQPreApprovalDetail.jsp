<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

 <script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<%-- <script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
 --%>

<%-- <style>
#preApprovaletail_paginate{
 float:right;
}

.dataTables_info{
 float:left;
}

.dataTables_length{
 float:left;
}

#preApprovaletail_filter{
 float:right;
}

</style>  --%>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

/* $(document).ready(function() { 
	 $('#preApprovaletail').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
} ); */
function showPreApprovalPopup(filePath){
	//debugger;
	var nodeId = '<s:property value="nodeId"/>';
	var wsId = '<s:property value="workspaceID"/>';
	var ClientId='<s:property value="clientCode"/>';
	 $.ajax({
		  	
		 url: "savePQPreApproval_ex.do?workspaceID="+wsId+"&nodeId="+nodeId+"&clientCode="+ClientId+"&Path="+filePath,

		 	success: function(data) 
		  {
			   debugger;
			/* if(data=="true"){	
				 showDeviationPopup();
			 }
			 else{
			 	 alert("Something went wrong while data saved.");
			 	 window.opener.parent.history.go(0);
			 	 self.close();
			 } */
			 	 alert("PQ-PreApproval data saved successfully.");
			 	 self.close();
			 	 //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
			 	
		  },
		  error: function(data) 
		  {
			alert("Something went wrong while data saved.");
			window.opener.parent.history.go(0);
			self.close();
		  },
			//async: false
		});
	
	
}
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
	<div class="all_title"><b style="float: left;">Pre-Approval</b></div>
<br>
<div class="grid_clr">
<s:if test="showPQSTableHeaderMst.size>0">
<table class="report paddingtable audittrailtable" style="width:99%; align:center;">
<tbody>
<tr>
	<div style="float: right; margin-top: -15px; " align="right"> 
		<form id="myform" action="ExportToXls.do" method="post">
			<input type="hidden" name="fileName" value="PreApproval-Script.xls">
			<textarea rows="1" cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
			<img alt="Export To Excel" title=" export to excel" style="cursor: pointer;cursor: pointer;width: 40%;margin-bottom: 5%;" src="images/Common/Excel.png" 
				onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()">&nbsp;
		</form>
	</div>


<td style="Width:49%"><label class="title">Total Rows:</label> <s:property value="totalScript"/></td>
<td><label class="title">Section:</label> <s:property value="successScript"/></td>
</tr>
<tr>
<td><label class="title">Total Confidence:</label> <s:property value="confidence"/>% </td>
<td><label class="title">Steps:</label> <s:property value="successSection"/></td>
</tr>
</tbody>
</table>
</s:if>
<br>
<div align ="center">
<s:if test="showPQSTableHeaderMst.size>0">
<s:if test="IsConfirmBtn==true && Confirmflag=='true' && iscreatedRights==true && #session.usertypename == 'WU'">
	<input type="button" value="Confirm" class="button" style="margin-right:5px; margin-bottom:5px;" onclick="showPreApprovalPopup('<s:property value="Path"/>');">
</s:if></s:if>
		<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
<br>
<s:if test="showPQSTableHeaderMst.size>0">
<div id="divTabText" style="width:100%">

<!-- <TABLE  style="display:none" >
<tr class="none">
	<Td class="title" style="border:1px;"> --> 
		<h3 style="display:none">Project Name :  ${wsDesc}</h3>
<!-- 	</Td>
</tr>
</table>
 -->
<table id = "preApprovaletail" class="report paddingtable audittrailtable" style="width:99%; align:center;">
	<thead>
		<tr>
			<th style="border: 1px solid; border-color:black;">#</th>
		 <s:iterator value="getClientDetail" status="stat"> 
			<th style="border: 1px solid; border-color:black;"><s:property value="stepNo"/></th>
		   </s:iterator> 
		</tr>
	</thead>
	<tbody>
		<s:iterator value="showPQSTableHeaderMst" status="status">
			<s:if test="isActive == 'N'">
				<tr style="background:yellow">
			</s:if>
			<s:elseif test="#status.odd">
				<tr class="odd">
			</s:elseif>
			<s:else>
				<tr class="even">
			</s:else>
				<td style="border: 1px solid; border-color:black;">${status.count }</td>  
				<td style="border: 1px solid; border-color:black;"><s:property value="stepNo" /></td>				
				<td style="border: 1px solid; border-color:black;"><s:property value="stepName" /></td>
				<td style="border: 1px solid; border-color:black;"><s:property value="expectedResult"/></td>
			</tr>
		</s:iterator>
	</tbody>
</table>	
</div>
</s:if>
<s:else><span style="font-size:15px; font-weight: 600;">${htmlContent}</span></s:else>
</div>
</div>
<br>
<%-- <div align ="center">
<s:if test="IsConfirmBtn==true && Confirmflag=='true'">
	<input type="button" value="Confirm" class="button" style="margin-right:5px; margin-bottom:5px;" onclick="showPreApprovalPopup();">
</s:if>
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div> --%>
</div>
</div>
<br>
</div>
</body>
</html>