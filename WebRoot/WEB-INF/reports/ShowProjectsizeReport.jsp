<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style>
#ScriptTable tr td:nth-last-child(2) {      
      width:100px;
    }
</style>

<%-- <script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script> --%>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script type="text/javascript">

$(document).ready(function() { 
	/*  $('#ScriptTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } ); */
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

<s:if test="getLeafNode.size>0">
	<form id="myform" action="ExportToXls.do" method="post" style="width: 25px; float: right; margin: -14px 14px 14px 0; cursor: pointer;">
		<input type="hidden" name="fileName" value="Attribute Detail.xls">					
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
<s:if test="getLeafNode.size>0">
<div id="divTabText" style="width:100%">
<div class="grid_clr">
<table id = "ScriptTable" class="datatable paddingtable audittrailtable" width="100%" align="center">
	<thead>
		<tr>
			<!-- <th style="border: 1px solid; border-color:black;">#</th> -->
			<th style="border: 1px solid; border-color:black; width: 15%;">Project name</th>
			<th style="border: 1px solid; border-color:black;">${ lbl_nodeName }/${ lbl_folderName }</th>
			<th style="border: 1px solid; border-color:black;"><s:property value=""/>Source Document Size</th>
			<th style="border: 1px solid; border-color:black;">Sign Document Size</th>
			<th style="border: 1px solid; border-color:black;">Total Size</th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getLeafNode" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<%-- <td style="border: 1px solid; border-color:black;">${status.count }</td> --%>
				<td style="border: 1px solid; border-color:black;"><s:property value="workSpaceDesc" /></td>
				<td style="border: 1px solid; border-color:black;"><s:property value="nodeName" /> [<s:property value="folderName" />]</td>
				<td style="border: 1px solid; border-color:black;"><%-- 	<s:property value="nSize" /> MB </td> --%>
				<s:if test="nSize == 0.0">
					-
				</s:if>
				<s:else> 
					<s:property value="nSize" /> MB
				</s:else>
				</td> 
				<td style="border: 1px solid; border-color:black;">
				<s:if test="eSignfileSize == 0.0">
					-
				</s:if>
				<s:else> 
					<s:property value="eSignfileSize" /> MB
				</s:else>
				</td> 
				
				<td style="border: 1px solid; border-color:black;"><s:property value="totalFileSize" /> MB</td>
				
			</tr>
			
		</s:iterator>
		
		
	</tbody>
</table>	
<table class="datatable paddingtable " width="100%" align="center" >
<tr style="background-color: darkseagreen";
>
<td style=" border-collapse: collapse; border: 1px solid; border-color:black;" colspan = "4" width="92.6%">Total Size</td>
<td style="float:left;" ><s:property value="sum" /> MB</td>
</tr>
<tr style="border: 1px solid; border-color:black;background-color : antiquewhite">
<td style=" border-collapse: collapse; border: 1px solid; border-color:black;" colspan = "4" width="92.6%">PDF Publish Size</td>
<td style=" float:left;" ><s:property value="getPdfpublishSize" /> MB</td>
</tr>
</table>
</div>
</div>


</s:if>
		<s:else>No details available.</s:else>
<br>

</body>
</html>