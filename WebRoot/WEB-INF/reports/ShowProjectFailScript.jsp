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

<s:if test="getFailScriptDeatil.size>0">
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
<s:if test="getFailScriptDeatil.size>0">
<div id="divTabText" style="width:100%">
<div class="grid_clr">
<table id = "ScriptTable" class="datatable paddingtable audittrailtable" width="100%" align="center">
	<thead>
		<tr>
			<th style="border: 1px solid; border-color:black;">#</th>
			<th style="border: 1px solid; border-color:black;">Project name</th>
			<th style="border: 1px solid; border-color:black;"><s:property value="lbl_nodeDisplayName"/></th>
			<th style="border: 1px solid; border-color:black;"><s:property value="lbl_folderName"/></th>
			<!-- <th style="border: 1px solid; border-color:black;">Attribute name</th> -->
			<th style="border: 1px solid; border-color:black;">Status</th>
			<th style="border: 1px solid; border-color:black;">Modified by</th>
			<th style="border: 1px solid; border-color:black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid; border-color:black;">Eastern Standard Time</th>
		</s:if>			
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getFailScriptDeatil" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td style="border: 1px solid; border-color:black;">${status.count }</td>
				<td style="border: 1px solid; border-color:black;"><s:property value="workSpaceDesc" /></td>
				<td style="border: 1px solid; border-color:black;"><s:property value="nodeDisplayName" /></td>
				<td style="border: 1px solid; border-color:black;">
				<s:if test = "#session.usertypename == 'WA'">
					<s:if test="fileExt=='PDF' || fileExt=='pdf'">
					<a href="javascript:void(0);"
						onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workspaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="validValues"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
					<s:property value="fileName" /> </a>
					</s:if>
					<s:else>
						<a href="javascript:void(0);"
						onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workspaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="validValues"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
					<s:property value="fileName" /> </a>
					</s:else>
				</s:if></td>
				<%-- <td style="border: 1px solid; border-color:black;"><s:property value="attrName" /></td> --%>
				<td style="border: 1px solid; border-color:black;"><s:property value="attrValue" /></td>
				<td style="border: 1px solid; border-color:black;"><s:property value="userName" /></td>
				<td style="border: 1px solid; border-color:black;"><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid; border-color:black;"><s:property value="ESTDateTime" /></td>
			</s:if>
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