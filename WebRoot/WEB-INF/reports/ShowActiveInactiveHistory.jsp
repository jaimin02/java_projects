<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>


<%-- <script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script> --%>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script type="text/javascript">

$(document).ready(function() { 
	 $('#ScriptTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
} );
	function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	}
	function fileOpen(actionName)
	{   
		win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
	}
	
	function docFileOpen(actionName)
	{   
		window.open(actionName, '_newtab');
	}
</script>
</head>
<body>
<br>

<s:if test="getDeletedNodeDetail.size>0">
	<form id="myform" action="ExportToXls.do" method="post" style="width: 25px; float: right; margin: -14px 14px 14px 0; cursor: pointer;">
		<input type="hidden" name="fileName" value="Active_Inactive_History.xls">					
			<textarea rows="1" cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
				<td style="width:25%;">
				<img alt="Export To Excel" title ="Export To Excel" src="images/Common/Excel.png" 
				onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()"/>
				</td>
	</form>
</s:if>
<!-- <div class="container-fluid">
<div class="col-md-12"> -->
<!-- 
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"> -->
	<!-- <div class="boxborder"><div class="all_title"><b style="float: left;">Client Detail History</b></div> -->
<br>
<s:if test="getDeletedNodeDetail.size>0">
<div id="divTabText" style="width:100%">
<div class="grid_clr">
<table border="1px" id = "ScriptTable" class="datatable paddingtable audittrailtable" width="100%" align="center" style="border:0px solid black; word-break: break-word;">
	<thead>
		<tr>
			<th>#</th>
			<th>${lbl_folderName }</th>
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getDeletedNodeDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="folderName" /></td>
				<td><s:property value="userName" /></td>
				<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> -->
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="remark"/></td>
				<td>
					<s:if test="statusIndi=='D'">
						InActivate
					</s:if>
					<s:if test="statusIndi=='E'">
						Activate
					</s:if>
				</td>
			</tr>
		</s:iterator>
		
	</tbody>
</table>	
</div>
</div>
</s:if>
		<s:else>No details available.</s:else>
<br>
<!-- <div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
</div>
</div>
</div> -->
</body>
</html>