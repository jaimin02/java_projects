<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<s:head />
<SCRIPT type="text/javascript">
	   
	   function callonsubmit(applicationNo,labelno,WorkspaceId,SubmissionId,labelId,lastPublishedVersion)
	   {
	  	 	var str="UpdateSubmission.do?workspaceId="+WorkspaceId+"&applicationNo="+applicationNo+"&labelno="+labelno+"&SubmissionId="+SubmissionId+"&labelId="+labelId+"&lastPublishedVersion="+lastPublishedVersion;
	   		window.location.href=str;
	   }
	   
	   function openlinkEdit(submissionId)
	   {
	    	var editLocationWindow = "SubmissionDetails.do?submissionId="+submissionId;
	    	win3=window.open(editLocationWindow,"","toolbar=no,directories=no,menubar=no,scrollbars=no,height=300,width=400,resizable=no,titlebar=no")	
	    	win3.moveTo(screen.availWidth/2-(400/2),screen.availHeight/2-(300/2));
	    	return true;
	   }
   
   </SCRIPT>

</head>

<body>
<div class="titlediv">
<center><font size="2"><u><b> Submission List </b></u></font></center>
</div>
<br>
<br>
<s:if test="SubmissionDetail.size==0">
	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			<s:if test="workSpaceId == '-1'"> No project was selected.. </s:if><s:else> No submission details found.. </s:else><br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
	<br>
	<br>
	<div align="center"><input type="button" value="Back"
		class="button" onclick="history.go(-1);"></div>

</s:if>
<s:else>

	<table width="95%" class="datatable" align="center">
		<thead>
			<tr>
				<th>Project Name</th>
				<th>Application No.</th>
				<th>Agency Name</th>
				<th>Submission Country</th>
				<th>Submission Version</th>
				<th>LabelId</th>
				<th>Related Seq. Number</th>
				<th>View Sub. Details</th>
				<th>View XML</th>
				<th>Submission Path</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="SubmissionDetail" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<s:hidden value="reletedSeqNo" id="reletedSeqNo" />
					<s:hidden value="confirm" id="confirm" />
					<td><s:property value="workspaceDesc" /></td>
					<td><s:property value="applicationNo" /></td>
					<td><s:property value="agencyName" /></td>
					<td><s:property value="countryName" /></td>
					<td><s:property value="lastPublishedVersion" /></td>
					<!-- Next Submission No -->
					<td><s:property value="labelId" /></td>
					<td><s:if test="reletedSeqNo=='-999'">
						N.A.
				</s:if> <s:else>
						<s:property value="reletedSeqNo" />
					</s:else></td>
					<td><a href="#"
						onclick="openlinkEdit('<s:property value="submissionId"/>');">Details</a>
					</td>
					<td><!--  <a href="<s:property value="submissionpath"/>\<s:property value="labelId"/>\<s:property value="applicationNo"/>\0000\index.xml" target="_blank">View</a> </td>
			--> <a
						href="<s:property value="submissionpath"/>/<s:property value="lastPublishedVersion"/>/index.xml"
						target="_blank">View</a></td>

					<td><s:if test="confirm == 'Y'">
						<a href="<s:property value="submissionpath"/>"> <s:property
							value="submissionpath" /> </a>
					</s:if></td>
					<s:if test="confirm == 'N'">
						<td><input type="button" class="button" value="Submit"
							onclick="callonsubmit('<s:property value="applicationNo"/>','<s:property value="labelNo"/>','<s:property value="WorkspaceId"/>','<s:property value="submissionId"/>','<s:property value="labelId"/>','<s:property value="lastPublishedVersion"/>')">
						</td>
					</s:if>
					<s:else>
						<td><input type="button" class="button" value="Submit"
							disabled="disabled"></td>
					</s:else>
				</tr>
			</s:iterator>
		</tbody>

	</table>
</s:else>
</body>
</html>
