<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>

<head>
<s:head theme="ajax" />

<SCRIPT type="text/javascript">
  
  	function callonsubmit(applicationNo,labelno,WorkspaceId,SubmissionId)
	{
  	 	var str="UpdateSubmissionComplete.do?workspaceId="+WorkspaceId+"&applicationNo="+applicationNo+"&labelno="+labelno+"&SubmissionId="+SubmissionId;
   		window.location.href=str;
	}
   
	function openFileWithPath(filewithpath)
	{
		var fileWindow = "openfile.do?fileWithPath="+filewithpath;
    	win3=window.open(fileWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height="+ screen.availHeight + ",width=" + (screen.availWidth/2) + ",resizable=yes,titlebar=no")	
    	win3.moveTo(0,0);
    	
    	return true;
	}
  </SCRIPT>
</head>

<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /> <s:actionmessage /></div>

<table width="95%" class="datatable" align="center">
	<thead>
		<tr>
			<th>Project Name</th>
			<th>Application No.</th>
			<th>Submission Version</th>
			<th>Confirm</th>
		</tr>
	</thead>
	<tbody>
		<tr class="even">
			<td>${submision.workspaceDesc }</td>
			<td>${submision.applicationNo }</td>
			<td>${submision.lastPublishedVersion }</td>
			<!-- 	<td>
				<a href="<s:property value="submision.submissionpath"/>\<s:property value="submision.labelId"/>">
					<s:property value="submision.submissionpath"/>\<s:property value="submision.labelId"/>
				</a>
			 </td> -->

			<td><!-- /* Code Added By : Ashmak Shah
 					  * Added On : 12th may 2009
 					  */ --> <s:if test="filesContainingBrokenLinks.size == 0">
				<input type="button" class="button" value="Confirm"
					onclick="callonsubmit('${submision.applicationNo }','${submision.labelNo }','${submision.workspaceId }','${submision.submissionId }')">
			</s:if> <s:else>
				<input type="button" class="button" value="Confirm"
					disabled="disabled">
			</s:else> <!-- code end --></td>


		</tr>
	</tbody>
</table>

<br>
<br>
<!-- 
	/* 
		Code Added By : Ashmak Shah
 		  * Added On : 12th may 2009
 */ 
 
 -->
<s:if test="filesContainingBrokenLinks.size != 0">

	<table border="1" width="95%" align="center" cellspacing="1">
		<tr>
			<td width="60%" valign="top">
			<div class="titlediv">Broken Links Found in Following File(s)</div>
			<br>

			<table class="datatable" width="100%">
				<thead>
					<tr>
						<th width="5%">#</th>
						<th width="55%">File Name</th>
						<th width="40%">Show Broken Links</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="filesContainingBrokenLinks" status="status">

						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

							<td width="5%">${status.count}</td>
							<td width="55%"><a href="#"
								onclick="openFileWithPath('<s:property value="top[2]"/>');"
								title="<s:property value="top[1]"/>"> <s:property
								value="top[0]" /> </a></td>
							<td width="40%"><s:url id="aUrl" action="ShowLinks">
								<s:param name="fileWithPath" value="top[2]" />
								<s:param name="getBrokenLinks" value="true" />
							</s:url> <s:a href="%{aUrl}" targets="BrokenLinksDiv" theme="ajax">
							  		Show
							  	</s:a></td>
					</s:iterator>

				</tbody>
			</table>

			</td>
			<td width="40%"><s:div id="BrokenLinksDiv" theme="ajax">

			</s:div></td>
		</tr>
	</table>

</s:if>
<!-- code end -->


</body>
</html>
