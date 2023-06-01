<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
</head>
<body>
<div style="height: autp; padding-bottom: 8px; overflow: auto;">
<div class="titlediv">Submission Details</div>

<div align="center" style="padding-top: 8px; overflow: auto">

<table width="95%" align="center" class="datatable" height="100%">

	<s:if test="submissionDetail.workspaceDesc!= ''">
		<tr>
			<td class="odd" width="50%">Project Name</td>
			<td class="even" width="50%">${submissionDetail.workspaceDesc}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.countryName== 'Australia' || submissionDetail.countryName== 'Thailand' ">
		<tr>
			<td class="odd" width="50%">eSubmissionId</td>
			<td class="even" width="50%">${submissionDetail.eSubmissionId}</td>
		</tr>
	</s:if>
	<s:elseif test="submissionDetail.countryName== 'Canada'">
		<tr>
			<td class="odd" width="50%">Dossier Identifier</td>
			<td class="even" width="50%">${submissionDetail.dossierIdentifier}</td>
		</tr>
	</s:elseif>
	<s:elseif test="submissionDetail.applicationNo!= ''">
		<tr>
			<td class="odd" width="50%">Application No</td>
			<td class="even" width="50%">${submissionDetail.applicationNo}</td>
		</tr>
	</s:elseif>
	<s:if test="submissionDetail.companyName!= ''">
		<tr>
			<td class="odd" width="50%">Company Name</td>
			<td class="even" width="50%">${submissionDetail.companyName}</td>
		</tr>
	</s:if>
	<s:if test="submissionDetail.countryName !='Australia'">
		<s:if test="submissionDetail.productName!= ''">
			<tr>
				<td class="odd" width="50%">Product Name</td>
				<td class="even" width="50%">${submissionDetail.productName}</td>
			</tr>
		</s:if>
	</s:if>

	<s:if test="submissionDetail.productType!= ''">
		<tr>
			<td class="odd" width="50%">Product Type</td>
			<td class="even" width="50%">${submissionDetail.productType}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.countryName!= ''">
		<tr>
			<td class="odd" width="50%">Submission Country</td>
			<td class="even" width="50%">${submissionDetail.countryName}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.agencyName!= ''">
		<tr>
			<td class="odd" width="50%">Agency</td>
			<td class="even" width="50%">${submissionDetail.agencyName}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.countryName !='Australia' && submissionDetail.countryName !='Thailand' submissionDetail.countryName !='Canada'">
		<s:if test="submissionDetail.submissionType!= ''">
			<tr>
				<td class="odd" width="50%">Submission Type</td>
				<td class="even" width="50%">${submissionDetail.submissionType}</td>
			</tr>
		</s:if>
	</s:if>

	<s:if test="submissionDetail.currentSequenceNumber!= ''">
		<tr>
			<td class="odd" width="50%">Submission Sequence</td>
			<td class="even" width="50%">${submissionDetail.currentSequenceNumber}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.reletedSeqNo!= ''">
		<tr>
			<td class="odd" width="50%">Related Sequences</td>
			<td class="even" width="50%">${submissionDetail.reletedSeqNo}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.dateOfSubmission!= null">
		<tr>
			<td class="odd" width="50%">Date Of Submission</td>
			<td class="even" width="50%"><s:date
				name="submissionDetail.dateOfSubmission" format="MMM-dd-yyyy" /></td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.applicationType!= ''">
		<tr>
			<td class="odd" width="50%">Application Type</td>
			<td class="even" width="50%">${submissionDetail.applicationType}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.applicant!= ''">
		<tr>
			<td class="odd" width="50%">Applicant</td>
			<td class="even" width="50%">${submissionDetail.applicant}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.atc!= ''">
		<tr>
			<td class="odd" width="50%">Atc</td>
			<td class="even" width="50%">${submissionDetail.atc}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.procedureType!= ''">
		<tr>
			<td class="odd" width="50%">Procedure Type</td>
			<td class="even" width="50%">${submissionDetail.procedureType}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.inventedName!= ''">
		<tr>
			<td class="odd" width="50%">Invented Name</td>
			<td class="even" width="50%">${submissionDetail.inventedName}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.inn!= ''">
		<tr>
			<td class="odd" width="50%">Inn</td>
			<td class="even" width="50%">${submissionDetail.inn}</td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.submissionDescription!= ''">
		<tr>
			<td class="odd" width="50%">Submission Description</td>
			<td class="even" width="50%">${submissionDetail.submissionDescription}</td>
		</tr>
	</s:if>



	<s:if test="submissionDetail.submittedOn!= null">
		<tr>
			<td class="odd" width="50%">Submitted On</td>
			<td class="even" width="50%"><s:date
				name="submissionDetail.submittedOn"
				format="MMM-dd-yyyy,HH 'hrs': mm'min'" /></td>
		</tr>
	</s:if>

	<s:if test="submissionDetail.confirm!= ''">
		<tr>
			<td class="odd" width="50%">Submission Status</td>
			<td class="even" width="50%"><s:if
				test="submissionDetail.confirm == 'Y'">Confirmed</s:if> <s:else>-</s:else>
			</td>
		</tr>
	</s:if>
	<s:if test="submissionDetail.countryName !='Thailand' && submissionDetail.countryName !='Canada'">
		<s:if test="submissionDetail.submissionMode!= ''">
			<tr>
				<td class="odd" width="50%">Submission Mode</td>
				<td class="even" width="50%">${submissionDetail.submissionMode}</td>
			</tr>
		</s:if>
	</s:if>

</table>



</div>
</div>
</body>
</html>

