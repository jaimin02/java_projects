<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<script type="text/javascript">


	$(document).ready(function()
	{ 

		$("#dos").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		var workSpaceId ='<s:property value="workSpaceId"/>';
		

		var options = 
		{
			target: '#msgDiv',
			url: 'SavePublishAndSubmitForm.do',
			beforeSubmit: showRequest,
			success: showResponse
		};
		
	});

	function showRequest(formData, jqForm, options) {
		var queryString = $.param(formData);
		var formElement = jqForm[0];
		$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');
		if(publishSequence())
		{
			showMsg();
			return true;
		}
		else
			return false;
	}
	function showResponse(responseText, statusText) 
	{
		
	}
	
</script>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>


<s:if
	test="workSpaceId == null || workSpaceId.trim() == '' || workSpaceId == -1">
	<br />
	<br />
	<br />
	<br />
	<br />
	<center>
	<table style="border: 1 solid black" width="80%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br />
			Please Select Project Name...<br />
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
</s:if>
<s:else>
	<div id="PublishAndSubmitFormDiv" align="left" style="width: 900px;">

	<br />
	
	<s:if test="getWorkspacePublishInfo.size != 0">
		
		

		

		<center><table id="submissionDtlTbl" align="center"
			class="datatable doubleheight" style="margin-top:10px;">
			<thead>
				<tr>
					<th>#</th>
					<th>Id</th>
					<th>Sequence</th>
					<th>Related Sequences</th>
					<s:if test="countryRegion != 'au' && countryRegion != 'th' && countryRegion != 'ca'">
						<th>Submission Type</th>
					</s:if>	
					<th>View XML</th>
					<th>Recompile</th>
					<th>Publish Path</th>
					<th>AddLink</th>
				</tr>
			</thead>
			<tbody style="overflow: auto;">
				<s:set name="workspaceCurrentSeqNumber" value="currentSeqNumber"></s:set>
				<s:iterator value="getWorkspacePublishInfo" status="status">

					<tr id="row<s:property value="#status.count"/>"
						class="<s:if test="confirm == 'Y'">odd</s:if>
				    			<s:else>
				    				<s:if test="deleted == 'true'">
				    					matchFound
				    				</s:if>
				    				<s:else>
				    					even
				    				</s:else>
				    			</s:else>">
						<td>${status.count}</td>
						<s:if test="countryRegion == 'us'">
							
							<td>
								<s:if test="regionalDTDVersion == '23'">
								<s:property value="submissionInfoUS23DtlId" /> <s:set
								name="subId" value="%{submissionInfoUS23DtlId}"></s:set>
								</s:if>
								<s:else><s:property value="submissionInfoUSDtlId" /> <s:set
								name="subId" value="%{submissionInfoUSDtlId}"></s:set></s:else>
								
								</td>
						</s:if>
						<s:elseif test="countryRegion == 'eu'">
							<td><!-- For EU v1.4 --> <s:if
								test="prevEUDtdVersion == '14'">
							
								<s:property value="submissionInfoEU14DtlId" />
								<s:set name="subId" value="%{submissionInfoEU14DtlId}"></s:set>
							</s:if> <!-- For EU v1.3 --> <s:elseif test="prevEUDtdVersion == '13'">
							
								<s:property value="submissionInfoEUDtlId" />
								<s:set name="subId" value="%{submissionInfoEUDtlId}"></s:set>
							</s:elseif> <s:elseif test="prevEUDtdVersion == '20'">
							
								<s:property value="submissionInfoEU20DtlId" />
								<s:set name="subId" value="%{submissionInfoEU20DtlId}"></s:set>
							</s:elseif></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'ca'">
							<td><s:property value="submissionInfoCADtlId" /> <s:set
								name="subId" value="%{submissionInfoCADtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'gcc'">
							<td><s:property value="submissionInfoGCCDtlId" /> <s:set
								name="subId" value="%{submissionInfoGCCDtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'za'">
							<td><s:property value="submissionInfoZADtlId" /> <s:set
								name="subId" value="%{submissionInfoZADtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'ch'">
							<td><s:property value="submissionInfoCHDtlId" /> <s:set
								name="subId" value="%{submissionInfoCHDtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'th'">
							<td><s:property value="submissionInfoTHDtlId" /> <s:set
								name="subId" value="%{submissionInfoTHDtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'au'">
							<td><s:property value="submissionInfoAUDtlId" /> <s:set
								name="subId" value="%{submissionInfoAUDtlId}"></s:set></td>
						</s:elseif>
						
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:property value="relatedSeqNo" /></td>
						<s:if test="countryRegion != 'au' && countryRegion != 'th' && countryRegion != 'ca'">
						<td><s:property value="submissionType" /></td>
					</s:if>	
						

						<td style="text-align: center;"><s:if
							test="deleted == 'false' && submissionPath.startsWith('//')">



							<a title="View XML"
								href="file:<s:property value="submissionPath"/>/<s:property value="currentSeqNumber"/>/index.xml"
								target="_blank"> <img
								border="0px" alt="View XML" src="images/Common/view.png"
								height="18px" width="18px"> </a>
						</s:if> <s:elseif
							test="deleted == 'false' && submissionPath.charAt(1) == ':'">
							<a title="View XML"
								href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/docmgmtandpub/<s:property value="submissionPath.replaceAll('[^/]:/[^/]+/','')"/>/<s:property value="currentSeqNumber"/>/index.xml"
								target="_blank"> <img border="0px" alt="View XML"
								src="images/Common/view.png" height="18px" width="18px"> </a>
						</s:elseif></td>
						<s:if
							test="deleted == 'false' && currentSeqNumber == #workspaceCurrentSeqNumber">
							<td style="text-align: center;"><a title="Recompile"
								href="javascript:void(0);"
								onclick="recompileSubmission('<s:property value="submissionPath"/>'+'/'+'<s:property value="currentSeqNumber"/>','<s:property value="workspaceId"/>','${subId }');">
							<img border="0px" alt="Recompile"
								src="images/Common/recompile.png" height="18px" width="18px">
							</a> <!-- recompileSubmission('submissionPath/currentSeqNumber','workspaceId','submissionId') -->
							</td>

							

						</s:if>
						<s:else>
							<s:if test="deleted == 'false' && confirm == 'Y'">
								<td style="text-align: center;"><label>-</label></td>
								
							</s:if>
							<s:else>
								<td style="text-align: center;">-</td>
								<!--<td style="text-align: center;">-</td>
							--></s:else>
						</s:else>

						<td><s:if
							test="deleted == 'false' && submissionPath.startsWith('//')">
							<div align="center"><a
								href="file:<s:property value="submissionPath"/>"
								title="<s:property value="submissionPath"/>"> <img
								border="0px" alt="Open" src="images/Common/open.png"
								height="18px" width="18px"> </a></div>
						</s:if> <s:elseif
							test="deleted == 'false' && submissionPath.charAt(1) == ':'">
							<s:property value="submissionPath" />
						</s:elseif></td>
						<s:if
							test="deleted == 'false' && currentSeqNumber == #workspaceCurrentSeqNumber">
							<td>
								<div align="center">
									<a target="_blank" href="AddHyperLinkMain.do?subId=<s:property value="subId"/>&workSpaceId=<s:property value="workSpaceId"/>&sequenceNumber=<s:property value="currentSeqNumber"/>&region=<s:property value="countryRegion"/> ">
										<img border="0px" alt="Add Link" src="images/Common/add_hyperlink.png" height="18px" width="18px">
									</a>
								</div>
				
							</td>
						</s:if>
						<s:else>
							<s:if test="deleted == 'false' && confirm == 'Y'">
								<td style="text-align: center;"><label>-</label></td>
								
							</s:if>
							<s:else>
								<td style="text-align: center;">-</td>
								
								
								
							</s:else>
						</s:else>
						<!--

					 	<td>
							<div align="center">
							<a
								href="<s:property value="workSpaceId"/>">Edit Link</a></div>
						</td>	
					
					--></tr>
				</s:iterator>
			</tbody>
		</table></center>
		
		<br />
		<br />
	</s:if> <input type="hidden" id="workspaceIdH" value=""> <input
		type="hidden" id="submissionPathH" value=""> <input
		type="hidden" id="currentSeqNumberH" value=""> <input
		type="hidden" id="subIdH" value=""></div>

	<div id="msgDiv" style="display: none;"></div>
	<div id="popupContact"
		style="width: 400px; height: 120px; margin-bottom: 50px; padding-bottom: 50px;">
	<img alt="Close" title="Close" src="images/Common/Close.png"
		onclick='cls();' class='popupCloseButton' />
	<h3>Electronic Signature</h3>
	<div style="width: 100%; height: 90px; overflow: auto;">Enter
	password:&nbsp;<input type="password" name="reConfPass" id="reConfPass"></input>
	<br>
	<br>
	<p align="center"><input type="button" class="button" value="Ok"
		name="buttonName" onclick="return checkPasswords();"></input></p>
	</div>
	</div>
	<div id="backgroundPopup"></div>
	<input type="hidden" value="<s:property value='elecSig'/>"
		name="elecSig" id="elecSig" style="display: none;">
	<input type="hidden" value="<s:property value='#session.password'/>"
		name="sessPass" id="sessPass" style="display: none;">

</s:else>

<!-- 
<div id="confirmDialog"
	style="position: fixed; top: 100px; z-index: 100000;">


<div id="statusDetails"></div>
<br></br>
<input type="button" value="Proceed" class="button" id="btnProceed"
	onclick="proceed();" /> <input type="button" value="Do Not Proceed"
	class="button" id="btnDoNotProceed" onclick="donotproceed()" /></div>

 -->



