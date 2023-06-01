<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<STYLE type="text/css">
.tabHdrActive {
	background: #5A8AA9;
	color: white;
	margin: 0px;
	padding: 0px;
}

.tabHdrInActive {
	background: white;
	color: #5A8AA9;
	margin: 0px;
	padding: 0px;
}
</STYLE>
<script type="text/javascript">
 		$(document).ready(function() {
 	 		
			
			$('#authorsTab').click(function(){
				$('#userDataOuter').fadeOut();
				$('#authorsTab').attr('class','title tabHdrActive');
				$('#reviewersTab').attr('class','title tabHdrInActive');
				$('#approversTab').attr('class','title tabHdrInActive');
				$('#userDataOuter').fadeIn();
				var modeUsers="Authors";
				var divId = "userTabData";
				getTabedData(modeUsers,divId);
			});
			$('#reviewersTab').click(function(){
				$('#userDataOuter').fadeOut();
				$('#reviewersTab').attr('class','title tabHdrActive');
				$('#authorsTab').attr('class','title tabHdrInActive');
				$('#approversTab').attr('class','title tabHdrInActive');
				$('#userDataOuter').fadeIn();
				var modeUsers="Reviewers";
				var divId = "userTabData";
				getTabedData(modeUsers,divId);
			});
			$('#approversTab').click(function(){
				$('#userDataOuter').fadeOut();
				$('#approversTab').attr('class','title tabHdrActive');
				$('#authorsTab').attr('class','title tabHdrInActive');
				$('#reviewersTab').attr('class','title tabHdrInActive');
				$('#userDataOuter').fadeIn();
				var modeUsers="Approvers";
				var divId = "userTabData";
				getTabedData(modeUsers,divId);
			});
 		});

		function getTabedData(modeUsers,divId)
		{
			var datamode = '<s:property value="datamode"/>';
 	 		var ndId ='<s:property value="nodeId"/>'
 			var wsId = '<s:property value="workSpaceId"/>' 
			$.ajax
			({
				url : 'ShowAuthors_ex.do?mode='+ modeUsers+"&workSpaceId="+wsId+"&nodeId="+ndId+"&datamode="+datamode,
				beforeSend: function()
				{
					$('#'+divId).html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
				},
		  		success: function(data) 
		  		{				
					$('#'+divId).html(data);
				}	  	
			});
		}
 		
		</script>
</head>
<body>
<div align="center" style="width: 95%">
<div align="right"><img title="Back" alt="Back"
	src="images/Common/Back.png" onclick="getDetails()"></div>
<div align="center" style="width: 100%; border: 1px solid #5A8AA9;">
<div align="left" style="width: 100%;">
<table width="100%" align="center">
	<tr>
		<td width="25%" class="title" align="right" style="padding: 2px;">Document
		Title&nbsp;&nbsp;</td>
		<td><s:property value="nodeName" /></td>
	</tr>
	<tr>
		<td class="title" align="right" style="padding: 2px;">Document
		ID&nbsp;&nbsp;</td>
		<td><s:property value="folderName" /></td>
	</tr>

</table>
</div>
<br>
<s:if test="userMode == 'true'">
	<div style="width: 95%; height: 350px;" align="left">
	<table align="left" width="10%">
		<tr>
			<td title="Authors" id="authorsTab" class="title tabTitle"
				style="border: 1px solid;">&nbsp;&nbsp;Authors&nbsp;&nbsp;</td>
			<td></td>
			<td title="Reviewers" id="reviewersTab" class="title tabTitle"
				style="border: 1px solid;">&nbsp;&nbsp;Reviewers&nbsp;&nbsp;</td>
			<td></td>
			<td title="Approvers" id="approversTab" class="title tabTitle"
				style="border: 1px solid;">&nbsp;&nbsp;Approvers&nbsp;&nbsp;</td>
		</tr>
	</table>
	<div id="userDataOuter"
		style="border: 1px solid #5A8AA9; max-height: 320px; overflow: auto; float: left; width: 100%; margin-top: -3px;">
	<div align="center" id="userTabData"
		style="max-height: 300px; overflow: auto; width: 100%"></div>
	<br />
	</div>
	</div>
</s:if></div>
<s:if test="userMode == 'true'">
	<script type="text/javascript">
					$('#authorsTab').click();
				</script>
</s:if></div>
</body>
</html>