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
		window.opener.parent.history.go(0);
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
	<div class="all_title"><b style="float: left;">Tracebility Matrix</b></div>
<br>
<div class="grid_clr">
<s:if test="getTracebilityMatrixDetail.size>0">
<table class="report paddingtable audittrailtable" style="width:99%; align:center;">
<tbody>
<tr>
	<div style="float: right; margin-top: -15px; " align="right"> 
		<form id="myform" action="ExportToXls.do" method="post">
			<input type="hidden" name="fileName" value="Tracebility-Matrix.xls">
			<textarea rows="1" cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
			<img alt="Export To Excel" title=" export to excel" style="cursor: pointer;cursor: pointer;width: 40%;margin-bottom: 5%;" src="images/Common/Excel.png" 
				onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()">&nbsp;
		</form>
	</div>

</tr>

</tbody>
</table>
</s:if>
<br>
<div align ="center">
		<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
<br>
<s:if test="getTracebilityMatrixDetail.size>0">
<div id="divTabText" style="width:100%">

<!-- <TABLE  style="display:none" >
<tr class="none">
	<Td class="title" style="border:1px;"> --> 
		<h3 style="display:none">Project Name :  ${wsDesc}</h3>
<!-- 	</Td>
</tr>
</table>
 --> 
<table id = "trDetail" class="report paddingtable audittrailtable" style="width:99%; align:center;">
	<thead>
		<tr>
			<th style="border: 1px solid; border-color:black;">#</th>
		 <s:iterator value="getTracebilityMatrixHeader" status="stat"> 
			<th style="border: 1px solid; border-color:black;"><s:property value="stepNo"/></th>
		   </s:iterator>
		   <th style="border: 1px solid; border-color:black;">PQ No.</th> 
		</tr>
	</thead>
	<tbody>
	<s:iterator value="getTracebilityMatrixDetail" status="status">
		<tr class="even">
			<td style="border: 1px solid; border-color:black;">${status.count }</td>
			<s:if test="fileName == 'URS' ">
				<td style="border: 1px solid; border-color:black;"><s:property value="URSNo" default="-"/></td>
			</s:if>
			<td style="border: 1px solid; border-color:black;"><s:property value="FRSNo" default="-"/></td>
			 
			<s:if test="fileName == 'URS' ">				
				<td style="border: 1px solid; border-color:black;"><s:property value="URSDescription" /></td>
			</s:if>
			<s:if test="fileName == 'FS' ">				
				<td style="border: 1px solid; border-color:black;"><s:property value="FSDescription"/></td> 
			</s:if>
			<%-- <td style="border: 1px solid; border-color:black;">
				<s:property value="attrValue" default="-"/>
			</td> --%>
			<td style="border: 1px solid; border-color:black;">
			<s:set name="srcDoc" value="getTracebilityMatrixDetail.get(0).getIDNo()"></s:set>
			
				<s:iterator value="sorted" status="status">
					
					<s:set name="itrId" value="IDNo"/>
					 <s:if test="key==#itrId">
						<s:property value="value" />
					 </s:if> 
					
				</s:iterator>
			</td>
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
</div>
</div>
<br>
</div>
</body>
</html>