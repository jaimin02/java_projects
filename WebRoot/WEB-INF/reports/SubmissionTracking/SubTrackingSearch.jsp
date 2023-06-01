<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="wsMstList.size() > 0 ">
	<!-- check that is there any data to display -->
	<s:iterator value="wsMstList">
		<s:if test="submissionMst != null">
			<s:set name="isValid" value="1"></s:set>
		</s:if>
	</s:iterator>
	<s:if test="#isValid > 0">
		<s:if test="reportType.equalsIgnoreCase('fullReport')">
			<s:iterator value="wsMstList">
				<s:if test="submissionMst != null">
					<table width="100%" align="center"
						style="border: 1px solid #5A8AA9;">
						<tr align="left">
							<th colspan="5">Project : <s:property value="workSpaceDesc" /></th>
						</tr>
						<tr align="center">
							<td colspan="3">
							<hr color="#5A8AA9" size="3px"
								style="width: 85%; border-bottom: 2px solid #CDDBE4;">
							</td>
						</tr>
						<tr align="left">
							<td><b>Region :</b> <s:property value="locationName" /></td>
							<td><b>Template :</b> <s:property value="templateDesc" /></td>
							<td><b>Client :</b> <s:property value="clientName" /></td>
						</tr>
						<s:if test="submissionMst != null">
							<tr align="center">
								<td colspan="3">
								<hr color="#5A8AA9" size="3px"
									style="width: 85%; border-bottom: 2px solid #CDDBE4;">
								</td>
							</tr>
							<tr align="left">
								<td><b>Country :</b> <s:property
									value="submissionMst.countryName" />&nbsp;(<s:property
									value="submissionMst.countryCodeName.toUpperCase()" />)</td>
								<td><b>Agency :</b> <s:property
									value="submissionMst.agencyName" /></td>
								<td></td>
							</tr>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('us')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.applicationNo" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Company :</b> <s:property
										value="submissionMst.companyName" /></td>
								</tr>
								<tr align="left">
									<td><b>Product Type :</b> <s:property
										value="submissionMst.productType" /></td>
									<td><b>Application Type :</b> <s:property
										value="submissionMst.applicationType" /></td>
									<td>&nbsp;</td>
								</tr>
								<s:if test="submissionMst.submissionInfoUSDtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoUSDtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('ca')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.DossierIdentifier" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Applicant :</b> <s:property
										value="submissionMst.applicant" /></td>
								</tr>
								<s:if test="submissionMst.submissionInfoCADtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoCADtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('th')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.eSubmissionId" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Company :</b> <s:property
										value="submissionMst.companyName" /></td>
								</tr>
								<tr align="left">
									<td><b>Product Type :</b> <s:property
										value="submissionMst.productType" /></td>
									<td><b>Application Type :</b> <s:property
										value="submissionMst.applicationType" /></td>
									<td>&nbsp;</td>
								</tr>
								<s:if test="submissionMst.submissionInfoTHDtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoTHDtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('ch')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.applicationNo" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Company :</b> <s:property
										value="submissionMst.companyName" /></td>
								</tr>
								<tr align="left">
									<td><b>Product Type :</b> <s:property
										value="submissionMst.productType" /></td>
									<td><b>Application Type :</b> <s:property
										value="submissionMst.applicationType" /></td>
									<td>&nbsp;</td>
								</tr>
								<s:if test="submissionMst.submissionInfoCHDtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoCHDtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('za')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.applicationNo" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Company :</b> <s:property
										value="submissionMst.companyName" /></td>
								</tr>
								<tr align="left">
									<td><b>Product Type :</b> <s:property
										value="submissionMst.productType" /></td>
									<td><b>Application Type :</b> <s:property
										value="submissionMst.applicationType" /></td>
									<td>&nbsp;</td>
								</tr>
								<s:if test="submissionMst.submissionInfoZADtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoZADtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('au')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.eSubmissionId" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Company :</b> <s:property
										value="submissionMst.companyName" /></td>
								</tr>
								<tr align="left">
									<td><b>Product Type :</b> <s:property
										value="submissionMst.productType" /></td>
									<td><b>Application Type :</b> <s:property
										value="submissionMst.applicationType" /></td>
									<td>&nbsp;</td>
								</tr>
								<s:if test="submissionMst.submissionInfoAUDtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoAUDtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('gcc')">
								<tr align="left">
									<td><b>Application Number :</b> <s:property
										value="submissionMst.trackingNo" /></td>
									<td><b>Product :</b> <s:property
										value="submissionMst.productName" /></td>
									<td><b>Company :</b> <s:property
										value="submissionMst.companyName" /></td>
								</tr>
								<tr align="left">
									<td><b>Product Type :</b> <s:property
										value="submissionMst.productType" /></td>
									<td><b>Application Type :</b> <s:property
										value="submissionMst.applicationType" /></td>
									<td>&nbsp;</td>
								</tr>
								<s:if test="submissionMst.submissionInfoGCCDtls.size() != 0">
									<tr align="center">
										<td colspan="3">
										<hr color="#5A8AA9" size="3px"
											style="width: 85%; border-bottom: 2px solid #CDDBE4;">
										</td>
									</tr>
									<tr align="center">
										<td colspan="3">
										<table class="datatable" width="100%">
											<tr>
												<th>Sequence</th>
												<th>Related Sequences</th>
												<th>Submission Type</th>
												<th>Date Of Submission</th>
											</tr>
											<s:iterator value="submissionMst.submissionInfoGCCDtls"
												status="status">
												<tr
													class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
													<td><s:property value="currentSeqNumber" /></td>
													<td><s:property value="relatedSeqNo" /></td>
													<td><s:property value="submissionType" /></td>
													<td><s:date name="dateOfSubmission"
														format="MMM-dd-yyyy" /></td>
												</tr>
											</s:iterator>
										</table>
										</td>
									</tr>
								</s:if>
							</s:if>
							<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('eu')">
								<s:if test="submissionMst.regionalDTDVersion.equalsIgnoreCase('20')">
									<tr align="left">
										<td><b>Tracking Number :</b> <s:property
											value="submissionMst.trackingNo" /></td>
										<td><b>Applicant :</b> <s:property
											value="submissionMst.applicant" /></td>
										<td><!-- <b>High Level Number :</b> <s:property value="submissionMst.highLvlNo"/> --></td>
									</tr>
									<tr align="left">
										<td><b>Procedure Type :</b> <s:property
											value="submissionMst.procedureType" /></td>
										<td><b>Invented Name :</b> <s:property
											value="submissionMst.inventedName" /></td>
										<td><b>INN :</b> <s:property value="submissionMst.inn" /></td>
									</tr>
									<s:if test="submissionMst.submissionInfoEU20Dtls.size() != 0">
										<tr align="center">
											<td colspan="3">
											<hr color="#5A8AA9" size="3px"
												style="width: 85%; border-bottom: 2px solid #CDDBE4;">
											</td>
										</tr>
										<tr align="center">
											<td colspan="3">
											<table class="datatable" width="100%">
												<tr>
													<th>Sequence</th>
													<th>Related Sequences</th>
													<th>Submission Type</th>
													<th>Submission Variation Mode</th>
													<th>Date Of Submission</th>
													<th>Submission Description</th>
												</tr>
												<s:iterator value="submissionMst.submissionInfoEU20Dtls"
													status="status">
													<tr
														class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
														<td><s:property value="currentSeqNumber" /></td>
														<td><s:property value="relatedSeqNo" /></td>
														<td><s:property value="submissionType" /></td>
														<td><s:property value="subVariationMode" /></td>
														<td><s:date name="dateOfSubmission"
															format="MMM-dd-yyyy" /></td>
														<td><s:property
															value="CmsDtls.get(0).submissionDescription" /></td>
													</tr>
												</s:iterator>
											</table>
											</td>
										</tr>
									</s:if>
								</s:if>
								<s:elseif
									test="submissionMst.regionalDTDVersion.equalsIgnoreCase('14')">
									<tr align="left">
										<td><b>Tracking Number :</b> <s:property
											value="submissionMst.trackingNo" /></td>
										<td><b>Applicant :</b> <s:property
											value="submissionMst.applicant" /></td>
										<td><!-- <b>High Level Number :</b> <s:property value="submissionMst.highLvlNo"/> --></td>
									</tr>
									<tr align="left">
										<td><b>Procedure Type :</b> <s:property
											value="submissionMst.procedureType" /></td>
										<td><b>Invented Name :</b> <s:property
											value="submissionMst.inventedName" /></td>
										<td><b>INN :</b> <s:property value="submissionMst.inn" /></td>
									</tr>
									<s:if test="submissionMst.submissionInfoEU14Dtls.size() != 0">
										<tr align="center">
											<td colspan="3">
											<hr color="#5A8AA9" size="3px"
												style="width: 85%; border-bottom: 2px solid #CDDBE4;">
											</td>
										</tr>
										<tr align="center">
											<td colspan="3">
											<table class="datatable" width="100%">
												<tr>
													<th>Sequence</th>
													<th>Related Sequences</th>
													<th>Submission Type</th>
													<th>Submission Variation Mode</th>
													<th>Date Of Submission</th>
													<th>Submission Description</th>
												</tr>
												<s:iterator value="submissionMst.submissionInfoEU14Dtls"
													status="status">
													<tr
														class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
														<td><s:property value="currentSeqNumber" /></td>
														<td><s:property value="relatedSeqNo" /></td>
														<td><s:property value="submissionType" /></td>
														<td><s:property value="subVariationMode" /></td>
														<td><s:date name="dateOfSubmission"
															format="MMM-dd-yyyy" /></td>
														<td><s:property
															value="CmsDtls.get(0).submissionDescription" /></td>
													</tr>
												</s:iterator>
											</table>
											</td>
										</tr>
									</s:if>
								</s:elseif>
								<s:else>
									<tr align="left">
										<td><b>Application Number :</b> <s:property
											value="submissionMst.applicationNo" /></td>
										<td><b>Applicant :</b> <s:property
											value="submissionMst.applicant" /></td>
										<td><b>ATC :</b> <s:property value="submissionMst.atc" /></td>
									</tr>
									<tr align="left">
										<td><b>Procedure Type :</b> <s:property
											value="submissionMst.procedureType" /></td>
										<td><b>Invented Name :</b> <s:property
											value="submissionMst.inventedName" /></td>
										<td><b>INN :</b> <s:property value="submissionMst.inn" /></td>
									</tr>
									<s:if test="submissionMst.submissionInfoEUDtls.size() != 0">
										<tr align="center">
											<td colspan="3">
											<hr color="#5A8AA9" size="3px"
												style="width: 85%; border-bottom: 2px solid #CDDBE4;">
											</td>
										</tr>
										<tr align="center">
											<td colspan="3">
											<table class="datatable" width="100%">
												<tr>
													<th>Sequence</th>
													<th>Related Sequences</th>
													<th>Submission Type</th>
													<th>Date Of Submission</th>
													<th>Submission Description</th>
												</tr>
												<s:iterator value="submissionMst.submissionInfoEUDtls"
													status="status">
													<tr
														class="<s:if test="#status.even">odd</s:if><s:else>even</s:else>">
														<td><s:property value="currentSeqNumber" /></td>
														<td><s:property value="relatedSeqNo" /></td>
														<td><s:property value="submissionType" /></td>
														<td><s:date name="dateOfSubmission"
															format="MMM-dd-yyyy" /></td>
														<td><s:property
															value="CmsDtls.get(0).submissionDescription" /></td>
													</tr>
												</s:iterator>
											</table>
											</td>
										</tr>
									</s:if>
								</s:else>
							</s:if>
						</s:if>
					</table>
					<br />
					<br />
				</s:if>
			</s:iterator>
		</s:if>
		<s:if test="reportType.equalsIgnoreCase('trackReport')">
			<div align="right">
			<form action="ExportToXls.do" method="post" id="myform"><input
				type="hidden" name="fileName" value="Project_Tracking_Report.xls">
			<textarea rows="1" cols="5" name="tabText" id="tableTextArea"
				style="visibility: hidden;"></textarea> <img alt="Export To Excel"
				title="Export To Excel" src="images/Common/Excel.png"
				onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()">
			</form>
			</div>
			<div align="left" style="width: 100%;" id="divTabText">
			<table width="100%" align="center" style="border: 1px solid #5A8AA9;"
				class="datatable" border="1px;">
				<tr>
					<th>Project</th>
					<th>Region</th>
					<th>Sequence</th>
					<th>Submission Date</th>
					<th>Submission Type</th>
					<th>Submission Description</th>
					<th>Procedure Type</th>
				</tr>
				<s:iterator value="wsMstList" status="status">
					<s:if test="submissionMst != null">
						<tr class="none">
							<td title="<s:property value="workSpaceDesc"/>"
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('us') && submissionMst.submissionInfoUSDtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoUSDtls.size()"/>"
									</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('ca') && submissionMst.submissionInfoCADtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoCADtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('ch') && submissionMst.submissionInfoCHDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoCHDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('gcc') && submissionMst.submissionInfoGCCDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoGCCDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('th') && submissionMst.submissionInfoTHDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoTHDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('au') && submissionMst.submissionInfoAUDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoAUDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('za') && submissionMst.submissionInfoZADtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoZADtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('eu')  && submissionMst.submissionInfoEU20Dtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoEU20Dtls.size()"/>"
										
									</s:if>
								<s:elseif test="submissionMst.CountryRegion.equalsIgnoreCase('eu')  && submissionMst.submissionInfoEU14Dtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoEU14Dtls.size()"/>"
									</s:elseif>
								<s:elseif test="submissionMst.CountryRegion.equalsIgnoreCase('eu')  && submissionMst.submissionInfoEUDtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoEUDtls.size()"/>"
										
									</s:elseif>>
							<b> <s:if test="workSpaceDesc.length() > 15">
								<s:property value="workSpaceDesc.substring(0,15)" />...
									    </s:if> <s:else>
								<s:property value="workSpaceDesc" /> 
							</s:else> <br />
							</b> ( <s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('eu') && submissionMst.regionalDTDVersion.equalsIgnoreCase('14') || submissionMst.regionalDTDVersion.equalsIgnoreCase('20') || submissionMst.regionalDTDVersion.equalsIgnoreCase('301')">
								<s:property value="submissionMst.TrackingNo" /> 
							</s:if> 
							<s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('gcc') ">
								<s:property value="submissionMst.TrackingNo" /> 
							</s:if> 
							<s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('ca') ">
								 
								<s:property value="submissionMst.dossierIdentifier"/>
							</s:if> 
							<s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('th') ">
								<s:property value="submissionMst.eSubmissionId" /> 
							</s:if>
							<s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('au') ">
								<s:property value="submissionMst.eSubmissionId" /> 
							</s:if>
							
							<s:else>
							
								<s:property value="submissionMst.ApplicationNo" />
							</s:else> )</td>
							<td title="<s:property value="workSpaceDesc"/>"
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('us') && submissionMst.submissionInfoUSDtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoUSDtls.size()"/>"
									</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('ca') && submissionMst.submissionInfoCADtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoCADtls.size()"/>"
									</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('ch') && submissionMst.submissionInfoCHDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoCHDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('gcc') && submissionMst.submissionInfoGCCDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoGCCDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('th') && submissionMst.submissionInfoTHDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoTHDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('au') && submissionMst.submissionInfoAUDtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoAUDtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('za') && submissionMst.submissionInfoZADtls.size() != 0" >
										rowspan="<s:property value="submissionMst.submissionInfoZADtls.size()"/>"
								</s:if>
								<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('eu') && submissionMst.submissionInfoEU20Dtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoEU20Dtls.size()"/>"
								</s:if>
								<s:elseif test="submissionMst.CountryRegion.equalsIgnoreCase('eu')  && submissionMst.submissionInfoEU14Dtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoEU14Dtls.size()"/>"
									</s:elseif>
								<s:elseif test="submissionMst.CountryRegion.equalsIgnoreCase('eu') && submissionMst.submissionInfoEUDtls.size() != 0">
										rowspan="<s:property value="submissionMst.submissionInfoEUDtls.size()"/>"
										
									</s:elseif>>
							<!--<s:property value="templateDesc.toUpperCase()"/>--> <s:property
								value="submissionMst.CountryRegion.toUpperCase()" /> </td>
							<s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('us') && submissionMst.submissionInfoUSDtls.size() != 0">
								<s:iterator value="submissionMst.submissionInfoUSDtls"
									status="status123">
									<s:if test="#status123.count > 1">
										<tr class="none">
									</s:if>
									<td><s:property value="currentSeqNumber" /></td>
									<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
									<td><s:property value="submissionType" /></td>
									<td>-</td>
									<td>-</td>
						</tr>
				</s:iterator>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('us') && submissionMst.submissionInfoUSDtls.size() == 0">
					<td colspan="5">Data Not Found.</td>
				</s:if>

				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ca') && submissionMst.submissionInfoCADtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoCADtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ca') && submissionMst.submissionInfoCADtls.size() == 0">
					<td colspan="5">Data Not Found.</td>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ch') && submissionMst.submissionInfoCHDtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoCHDtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
						
						</tr>
					</s:iterator>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ch') && submissionMst.submissionInfoCHDtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('gcc') && submissionMst.submissionInfoGCCDtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoGCCDtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
						</tr>
					</s:iterator>
				</s:if>
				
				
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('gcc') && submissionMst.submissionInfoGCCDtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('th') && submissionMst.submissionInfoTHDtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoTHDtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
						</tr>
					</s:iterator>
				</s:if>
				
				
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('th') && submissionMst.submissionInfoTHDtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('au') && submissionMst.submissionInfoAUDtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoAUDtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
						</tr>
					</s:iterator>
				</s:if>
				
				
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('au') && submissionMst.submissionInfoAUDtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('za') && submissionMst.submissionInfoZADtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoZADtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('eu')">
					<s:if
						test="submissionMst.submissionInfoEU20Dtls.size() != 0">
						<s:iterator value="submissionMst.submissionInfoEU20Dtls"
							status="status123">
							<s:if test="#status123.count > 1">
								<tr class="none">
							</s:if>
							<td><s:property value="currentSeqNumber" /></td>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
							</tr>
						</s:iterator>
					</s:if>
					<s:if
						test="submissionMst.submissionInfoEU20Dtls.size() == 0">
						<th colspan="5" class="headercls">Data Not Found.</th>
					</s:if>
					<s:if
						test="submissionMst.submissionInfoEU14Dtls.size() != 0">
						
						<s:iterator value="submissionMst.submissionInfoEU14Dtls"
							status="status123">
							<s:if test="#status123.count > 1">
								<tr class="none">
							</s:if>
							<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
							</tr>
						</s:iterator>
					</s:if>
					
					<s:if
						test="submissionMst.submissionInfoEU14Dtls.size() == 0">
						<th colspan="5" class="headercls">Data Not Found.</th>
					</s:if>
					<s:if
						test="submissionMst.submissionInfoEUDtls.size() != 0">
						<s:iterator value="submissionMst.submissionInfoEUDtls"
							status="status123">
							<s:if test="#status123.count > 1">
								<tr class="none">
							</s:if>
							<td><s:property value="submissionType" /></td>
						<td>-</td>
						<td>-</td>
							</tr>
						</s:iterator>
					</s:if>
					<s:if
						test="submissionMst.submissionInfoEUDtls.size() == 0">
						<th colspan="5" class="headercls">Data Not Found.</th>
					</s:if>
				</s:if>
				</tr>
				</s:if>
				</s:iterator>
			</table>
			</div>
		</s:if>
	</s:if>
	<s:else>
		<h4>Data Not Found.</h4>
	</s:else>
</s:if>
<s:else>
	<h4>Data Not Found.</h4>
</s:else>