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
			<div align="left" style="width: 100%;overflow:auto;" id="divTabText" >
			<table width="100%" align="center" style=""
				class="striped card" border="">
				<tr>
					<th>Project</th>
					<th>Region</th>
					<th>Sequence(Confirmed)</th>
					<th>Submitted On</th>
					<th>Submission Type</th>
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
							<b> <s:if test="workSpaceDesc.length() > 35">
								<s:property value="workSpaceDesc" />
									    </s:if> <s:else>
								<s:property value="workSpaceDesc" />
							</s:else> <br />
							<!--								<s:if test="statusIndi=='D'">--> <!--								<font color="red">(Deleted)</font> -->
							<!--								</s:if>--> <!--								<s:property value="statusIndi"/> --></td>
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
								value="submissionMst.CountryRegion.toUpperCase()" /></td>
							<s:if
								test="submissionMst.CountryRegion.equalsIgnoreCase('us') && submissionMst.submissionInfoUSDtls.size() != 0">
								<s:iterator value="submissionMst.submissionInfoUSDtls"
									status="status123">
									<s:if test="#status123.count > 1">
										<tr class="none">
									</s:if>
									<td><s:property value="currentSeqNumber" /> (<s:property
										value="confirm" />)</td>
									<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
									<td><s:property value="submissionType" /></td>

									<td>-</td>
						</tr>
				</s:iterator>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('us') && submissionMst.submissionInfoUSDtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
					 
				</s:if>

				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ca') && submissionMst.submissionInfoCADtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoCADtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						<td><s:property value="currentSeqNumber" /> (<s:property
							value="confirm" />)</td>
						<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
						<td>-</td>
						<td>-</td>
						
						</tr>
					</s:iterator>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ca') && submissionMst.submissionInfoCADtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
				</s:if>
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('ch') && submissionMst.submissionInfoCHDtls.size() != 0">
					<s:iterator value="submissionMst.submissionInfoCHDtls"
						status="status123">
						<s:if test="#status123.count > 1">
							<tr class="none">
						</s:if>
						
						<td><s:property value="currentSeqNumber" /> (<s:property
							value="confirm" />)</td>
						<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
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
						
						<td><s:property value="currentSeqNumber" /> (<s:property
							value="confirm" />)</td>
						<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td><s:property value="submissionMst.procedureType" /></td>
						
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
						
						<td><s:property value="currentSeqNumber" /> (<s:property
							value="confirm" />)</td>
						<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
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
						
						<td><s:property value="currentSeqNumber" /> (<s:property
							value="confirm" />)</td>
						<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
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
						
						<td><s:property value="currentSeqNumber" /> (<s:property
							value="confirm" />)</td>
						<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
						<td><s:property value="submissionType" /></td>
						<td>-</td>
						</tr>
					</s:iterator>
				</s:if>
				
				<s:if
					test="submissionMst.CountryRegion.equalsIgnoreCase('za') && submissionMst.submissionInfoZADtls.size() == 0">
					<th colspan="5" class="headercls">Data Not Found.</th>
				</s:if>
				
				<s:if test="submissionMst.CountryRegion.equalsIgnoreCase('eu')">
					<s:if
						test="submissionMst.submissionInfoEU20Dtls.size() != 0">
						<s:iterator value="submissionMst.submissionInfoEU20Dtls"
							status="status123">
							<s:if test="#status123.count > 1">
								<tr class="none">
							</s:if>
							<td><s:property value="currentSeqNumber" /> (<s:property
								value="confirm" />)</td>
							<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
							<td><s:property value="submissionType" /></td>

							<td><s:property value="submissionMst.procedureType" /></td>
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
							<td><s:property value="currentSeqNumber" /> (<s:property
								value="confirm" />)</td>
							<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
							<td><s:property value="submissionType" /></td>

							<td><s:property value="submissionMst.procedureType" /></td>
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
							<td><s:property value="currentSeqNumber" />(<s:property
								value="confirm" />)</td>
							<td><s:date name="submitedOn" format="MMM-dd-yyyy" /></td>
							<td><s:property value="submissionType" /></td>

							<td><s:property value="submissionMst.procedureType" /></td>
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