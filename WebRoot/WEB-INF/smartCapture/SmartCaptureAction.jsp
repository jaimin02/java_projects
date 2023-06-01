<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<%-- <script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script> --%>
	
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
$(document).ready(function() 
		{	
			//debugger;
			//$("#downloading").hide();
			$(".downloading").hide();
			$('#usertable').dataTable( {
				 "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
					"bJQueryUI": true,
					"sPaginationType": "full_numbers",
				 } ); 
		});
		
function downloadPdf(id){
 	debugger;
 	var path=id;
 	//var workSpaceId=$('#workSpaceId').val();
 	//var workspaceID =  '<s:property value="workspaceID"/>';
 	//var nodeId=document.workspaceNodeAttrForm.nodeId.value
 	//alert("Type is :"+type);
 	//var URL='Download_ex.do?workSpaceId='+workSpaceId+"&nodeId="+nodeId ;
 	//data=data.replace(/\\/g,"\\\\");
 	var URL='DownloadExeFile_ex.do';
 	
 	//$.ajax(
 	//{			
 		//url: 'convertAndDownload_ex.do?workspaceID='+workspaceID+"&nodeId="+nodeId,
 		  //url: 'Download_ex.do?DownloadFile='+data,
 		//beforeSend: function()
 		//{
 			$('#imgPdf'+id).hide();
 			//$('#downloading'+nodeId).css('background-color', '#E3EAF0');
 			$('#downloading'+id).show();
			
 		//},
 		//success: function(data) 
   		//{
 			//debugger;
			window.location = URL+'?FileId='+id;
			//alert("File downloaded successfully.");
			$('#downloading'+id).hide();
	 		$('#imgPdf'+id).show(); 
 		//},
 		/* complete: function(){
 			
 	      }, */
 		//async: false,
         /* error: function (data) {
         	alert("Something went wrong");
         	$('#downloading'+nodeId).hide();
	 		$('#imgPdf'+nodeId).show(); 
             }, */		
 		
 	//});
 	return true;
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
	<div class="boxborder"><div class="all_title"><b style="float: left;">Smart Capture</b></div>
<br>
<div class="grid_clr">
<table id = "clientDetailHistory" class="datatable paddingtable audittrailtable" width="98%" align="center">
<!-- <table id = "usertable" class="datatable paddingtable remarktbl" style="width:100%;" align="center"> -->
	<thead>
		<tr>
			<th>#</th>
			<th>Smart Capture Version</th>
			<th>Compatible DoQStack Version</th>
			<th>Build Notes</th>
		<%-- 	<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th> --%>
			<th>Download</th>
			
		</tr>
	</thead>
	<tbody>
		<s:iterator value="smartCaptureDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="srNo" /></td>
				<td><s:property value="smartCaptureVersion" /></td>
				<td><s:property value="docStackVersion" /></td>
				<td><s:property value="description" /></td>
				<%-- <td><s:property value="modifyBy" /></td>
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if> --%>
				 
			<%-- <s:if test="statusIndi=='N'">
				<td>New</td>
			</s:if>
			<s:elseif test="statusIndi=='E'">
				<td>Edited</td>
			</s:elseif>
			<s:elseif test="statusIndi == 'A'">
				<td>Active</td>
			</s:elseif>
			<s:elseif test="statusIndi=='D'">
				<td>Inactive</td>
			</s:elseif> --%>
<%-- 			<s:else>
				<td>-</td>
			</s:else> --%>
			<%-- <td><s:property value="remark" /></td> --%>
			
<%-- 			<div height=30px; width=40px; class="downloading" id="downloading<s:property value="srNo" />" style="width:10px;"><img src="images/loading.gif" alt="loading ..." /></div> --%>
			
			<td style="width: 10%;">
			<div class ="downloading" id="downloading<s:property value="srNo"/>" style="width:10px;margin-left: 45px;"><img height="25px" 
				width="25px" src="images/loading.gif" alt="loading ..." /></div>
				<center><a><%-- <img id="imgPdf<s:property value="srNo" />" height="25px" width="25px" src="images/Common/open.svg" 
				title="Download" 
				onclick="downloadPdf('<s:property value="appPath" />','<s:property value="srNo" />');"/> --%>
				<img id="imgPdf<s:property value="srNo" />" height="25px" width="25px" src="images/Common/download.svg" 
				title="Download" 
				onclick="downloadPdf('<s:property value="srNo" />');"/>
				</a></center>
			</td>
			</tr>
		</s:iterator>
	</tbody>
</table>	
</div>
</div>

</div>
</div>
</div>
</body>
</html>