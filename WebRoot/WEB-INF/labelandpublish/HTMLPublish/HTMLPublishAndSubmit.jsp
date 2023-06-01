<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<style>
/* #submissionDtlTbl_filter input{
background-color: #2e7eb9;
color:#ffffff;
}
#submissionDtlTbl_length select {
background-color: #2e7eb9;
color:#ffffff;
} */
</style>
<s:if test="workSpaceId == -1">
	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black ;bgcolor:silver" width="100%" >
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			Please Select Project Name...<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
</s:if>
<s:else>



	<script type="text/javascript">
	//debugger;
	function openwindow()
	{
		
		window.open("","MsgWindow","width=200,height=100");
	}
		
	// prepare the form when the DOM is ready
	$(document).ready(function() {
		//debugger;
		$('#NoFiles').hide();
		$(".downloading").hide();
		 $('#submissionDtlTbl').dataTable( {
				"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
				"bJQueryUI": true,
				"sPaginationType": "full_numbers"
			 } );
		//alert("<s:property value="docDetails.size"/>");
		var flag="<s:property value="docDetails.size"/>"
		if(flag==0){
			//$('#PublishAndSubmitFormDiv').hide();
			$('#PublishAndSubmitFormDiv').hide();
			$('#NoFiles').show();
			$('#submissionDtlTblDiv').hide();
		}else
			{
			//$('#PublishAndSubmitFormDiv').hide();
			$('#PublishAndSubmitFormDiv').show();
			$('#submissionDtlTblDiv').show();
			}
		document.getElementById('LoadImgLogo').style.display = 'none';
		document.getElementById('logoSelectionTR').style.display = 'none';
		var options = {	target: '#submissionDtlTblDiv', 
						beforeSubmit: showRequest,
						success: showResponse 
					  };
						
		// bind to the form's submit event
		$('.HtmlPublishFormId').submit(function() {
			$(this).ajaxSubmit(options);
		
			// !!! Important !!!
			// always return false to prevent standard browser submit and page navigation
			return false;
		});
});

	// pre-submit callback
	function showRequest(formData, jqForm, options) {
		$('#submissionDtlTblDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
		return true;
	}
	
	// post-submit callback
	function showResponse(responseText, statusText) {
	}
</script>
<div id="NoFiles">

	<%-- <table style="border: 1 solid black" width="100%" bgcolor="silver">
			<tr>
				<td width="10%" align="right"><img
					src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
				<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
				No Files Found.<br>
				&nbsp;</b></font></td>
			</tr>
		</table> --%>
		<br><br>
		<div align="center" style="border: 1px solid #669; margin-right: 5px;">
		<%-- <lable class="title" style="font-size: 16px;">There are no files for publishing for this <s:property	value="dtows.workSpaceDesc" /> project.</lable> --%>
		<lable class="title" style="font-size: 16px;">No details available.</lable>
		</div>
		<!-- <br><br>
		<hr color="#5A8AA9" size="3px" style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center"> -->
</div>
<div class="container-fluid">
<div class="col-md-12">
<div style="border-radius: 10px 10px 0px 0px; padding-bottom: 15px; border-top: none;"
	align="center">

	<div id="PublishAndSubmitFormDiv" align="center"
		style="padding-left: 10px; padding-right: 10px;">
	<form id="HtmlPublishFormId" class="HtmlPublishFormId"
		action="SaveHTMLPublishAndSubmitForm.do" method="post"
		name="HTMLPublishAndSubmitForm" enctype="multipart/form-data">
	<div>
	

	<%-- <table class="paddingtable" border="0" cellpadding="" 
		bordercolor="#EBEBEB" cellspacing="0" style="width:100%;">

		<tr onclick="hide('ProjectDetailBox')">
		<td style="padding-left: 14px;" width="100%"><div class="boxborder"><div class="all_title"><b>Project Detail</b></div></div></td>
			<!-- <td width="95%">Project Detail</td> -->
			<td style="padding-right: 9px;" width="5%" align="center"><img style="margin-left: -40px;"
				src="<%=request.getContextPath()%>/images/Darrow.gif"></td>
		</tr>


	</table> --%>

<!-- <div onclick="hide('ProjectDetailBox')" class="all_title"> -->
<div class="all_title">
			<div style="width:100%">
				<div class="boxborder">
					<div><b>Project Details</b>
					</div>
				</div>
			</div>
			<!-- <td width="95%">Project Detail</td> -->
			<%-- <div style="padding-right: 9px; float:right"><img 	src="<%=request.getContextPath()%>/images/Darrow.gif"> --%>
			</div>
		</div>


	<div id="ProjectDetailBox" style="border: 1px solid #669; width:100%">
	<table class="paddingtable" style="cellpadding:2; width:100%; cellspacing:0">
	<div id="imp" style="float: right; margin-right: 10px; margin-top: 10px; font-size: 16px;">
			Fields with (<span style="color: red;" >*</span>) are mandatory.
	</div>
		<tr>
			<td width="20%" class="title"><b>Project</b></td>
			<td><font color="#c00000"><b><s:property
				value="dtows.workSpaceDesc" /></b></font></td>
		</tr>

		<tr>
			<td class="title"><b>Project Type</b></td>
			<td><font color="#c00000"><b><s:property
				value="dtows.ProjectName" /></b></font></td>
		</tr>

		<tr>
			<td class="title"><b>Client</b></td>
			<td><font color="#c00000"><b><s:property
				value="dtows.clientName" /></b></font></td>

		</tr>
		<tr>
			<td class="title"><b>Publish Description</b>
			<span style="font-size:20px;color:red">*</span></td>
			<td><input type="text" name="subDesc"></td>
		</tr>
		<tr>
		<td class="title" colspan="2" style="padding-left: 5px;"><input
				type="checkbox" checked="true" name="Publish" onclick="test();"
				value="Y"> Publish with sign-off</td>
		</tr>
		<tr>
			<!-- <td><input type="radio" name="logoSelection" value="Y"
				checked="checked" id="Upload" onclick="return fun();"><label
				for="Upload">Upload Logo</label></td>
			<td><input type="radio" name="logoSelection" value="N"
				id="Available" onclick="return fun();"><label
				for="Available" id="logoTest">Available Logo(s)</label></td> -->
		</tr>
		<!-- <tr id="UploadLogoTR">
			<td class="title"><b>Upload Logo</b></td>
			<td><input type="file" id="uploadLogo" name="uploadLogo"
				size="30" onchange="return validateFiles();"></td>
		</tr> -->
		<tr id="logoSelectionTR">
			<td class="title"><b>Select Publish Logo</b></td>
			<td><s:select list="logoNameList" name="logoFileName"
				listKey="top" listValue="top" headerKey="-1"
				headerValue="Use Default Logo" theme="simple" cssClass="target"
				id="logoFileName" cssStyle="padding: 10px;">
			</s:select></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<div id="LoadImgLogo" align="center"><img src="" id="logoImg"
				alt="No Logo Selected" height="65px" width="150px"
				title="Select Logo Image" style="padding: 10px;"></div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<div id="uploadLogoDiv" align="center"
				style="padding: 5px; border: 1px; border-bottom-color: black;"></div>
			</td>
		</tr>
		<tr>
		
				
			<!-- <td class="title" colspan="2" style="padding-left: 20px;"><input
				type="checkbox" name="nonPublishableNode" onclick="test();"
				value="Y"> Include Non Publishable Nodes</td> -->
		</tr>
	</table>

	</div>
	<br>

	<!-- <s:submit name="submitBtn" value="Save" cssClass="button" onclick="return validate();"/> -->
	<!-- 	<s:submit id="PublishBtn" name="PublishBtn" value="Publish" cssClass="button"  align="center" cssStyle="visibility : hidden;" theme="ajax" targets="submissionDtlTblDiv" /> -->
	<input type="submit" class="button" value="Publish" name="displayBtn"
		onclick="return validate();"> <!-- <input type="button" class="button" value="Cancel" > -->
	<s:hidden name="workSpaceId">
	</s:hidden></form>
	</div>

	<br>

	<s:if test="htmlSubDtl.size != 0">
		<!-- <br>
		<hr>
		<br> -->

		<br>
		<div class="datatablePadding" id="submissionDtlTblDiv" align="center"
			style="width: 100%; overflow-x: auto; border: 1 solid; padding-bottom: 20px;padding-top: 3px;">
		<table id="submissionDtlTbl" align="center" style="width:100%;"class="datatable doubleheight">
			<thead>
				<tr>
					<th style="border: 1px solid black;">#</th>
					<th style="border: 1px solid black;">Publish Description</th>
					<!-- <th>View HTML</th> -->
					<th style="border: 1px solid black;">Published by</th>
					<th style="border: 1px solid black;">Published on</th>
					<!-- <th>User Name</th> -->
				 <s:if test ="#session.countryCode != 'IND'">
					<th style="border: 1px solid black;">Eastern Standard Time</th>
				</s:if>
					<th style="border: 1px solid black;">Published Document</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="htmlSubDtl" status="stat">

					<tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>">
						<td style="border: 1px solid black;"><s:property value="tranNo" /></td>
						<td style="border: 1px solid black;"><s:property value="description" /></td>
						<%-- <td>
						<div align="center"> <s:if
							test="publishPath.startsWith('//')">
							<a title="View HTML"
								href="file:<s:property value="publishPath"/>/index.html"
								target="_blank"> <img border="0px" alt="View"
								src="images/Common/view.png" height="18px" width="18px"> </a>

						</s:if> <s:elseif test="publishPath.charAt(1) == ':'">
							<a title="View HTML"
								href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/docmgmtandpub/<s:property value="publishPath.replaceAll('[^/]:/[^/]+/','')"/>/index.html"
								target="_blank"> <img border="0px" alt="View"
								src="images/Common/view.png" height="18px" width="18px"> </a>
						</s:elseif></div>
						</td> --%>
						<td style="border: 1px solid black;"><s:property value="username" /></td>
						<%-- <td><s:date name="modifyon" format="MMM dd,yyyy hh:mm:ss a" /></td> --%>
						<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
					 <s:if test ="#session.countryCode != 'IND'">
						<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
					</s:if>
						<td style="border: 1px solid black;">
						<div><s:if
							test="publishPath.startsWith('//')">
							<a href="file:<s:property value="publishPath"/>" target="_blank"
								title="file:<s:property value="publishPath"/>">  <!-- <img
								border="0px" alt="Open" src="images/Common/open.svg"
								height="25px" width="25px">  -->
						<div id="downloading<s:property value="tranNo" />" class="downloading" style="height:30px; width:40px;float: left; left:590px; top:490px;">
								<img src="images/loading.gif" alt="loading ..." />
							</div>
							<img id="imgPdf<s:property value="tranNo" />" class="imgPdf" height="25px" width="25px" src="images/Common/open.svg" title="Publish" 
							onclick="downloadPdf('<s:property value="publishPath"/>','<s:property value="tranNo" />');"/> </a>
						</s:if> <s:elseif test="publishPath.charAt(1) == ':'">
							<%-- <a
								href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/docmgmtandpub/<s:property value="publishPath.replaceAll('[^/]:/[^/]+/','')"/>"
								target="_blank" title="file:<s:property value="publishPath"/>">
							<img border="0px" alt="Open" src="images/Common/open.svg"
								height="25px" width="25px"> </a> --%>
								<a><img id="imgPdf<s:property value="tranNo" />" class="imgPdf" height="25px" width="25px" src="images/Common/open.svg" title="Publish" 
							onclick="downloadPdf('<s:property value="publishPath"/>','<s:property value="tranNo" />');"/> </a>
								</s:elseif>
						</div>
						</td>
					</tr>
				</s:iterator>

			</tbody>
		</table>

		</div>
		<br>
		<br>
	</s:if> <s:else>
		<div id="submissionDtlTblDiv" align="center"
			style="width: 100%; overflow-x: auto; border: 1 solid; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px;">
		<div style="color: #0D4264; font-size:16px;"><br>
		<%-- <b>There are no submission information found for this <s:property	value="dtows.workSpaceDesc" /> project.</b></div> --%>
		<b>No details available.</b></div>
		</div>
	</s:else></div>
	
</div>
</div>
</div>
	
</s:else>