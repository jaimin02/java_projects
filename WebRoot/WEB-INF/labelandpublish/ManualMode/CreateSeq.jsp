<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() { 
		$("#dos").datepicker({minDate: '', maxDate: '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		var workSpaceId ='<s:property value="workSpaceId"/>';
		$.ajax
		({			
				url: "PreValidation_ex.do?workspaceId="+workSpaceId+"&checkOnlyFiles=false",
				beforeSend: function()
				{
					$('#PreValidationDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
				},
				success: function(data) 
		  		{
			  		data = data.replace(/^\s+/, '');
					$('#PreValidationDiv').show();
			  		if(data.match("^"+"no"))
			  		{
				  		var data1 = "<div align=\"center\">"+
									"<lable class=\"title\">"+
										"There are no files for Publishing."+
									"</lable>"+
									"</div>";
						$('#GenericdetailBox').hide();
						$('#PreValidationDiv').html(data1);
			  		}
			  		else if(data.match("^"+"remove"))
			  		{
				  		$('#GenericdetailBox').show();
				  		$('#PreValidationDiv').hide();
			  		}
			  		else
			  		{		  
			  			$('#GenericdetailBox').show();
						$('#PreValidationDiv').html(data);
			  		}
				}	  		
		});
		var options = 
		{
			target: '#treeDiv',
			url: 'ShowTreeForPublish.do',
			beforeSubmit: showRequest,
			success: showResponse
		};
		$('#PublishAndSubmitForm').submit(function()
		{	
			$(this).ajaxSubmit(options);
			return false;									
		});
	});
	
	function showRequest(formData, jqForm, options) {
		var queryString = $.param(formData);
		var formElement = jqForm[0];
		if(publishSequence())
		{
			showTree('treeDiv');
			$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');
			return true;
		}
		else
			return false;
	}
	
	function showResponse(responseText, statusText) 
	{
		showTree('treeDiv');
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
	<div><s:form action="ShowTreeForPublish" method="post"
		name="PublishAndSubmitForm" id="PublishAndSubmitForm"
		cssStyle="text-align: left;">

		<table class="paddingtable" cellspacing="0" width="100%;"
			bordercolor="#EBEBEB">

			<tr class="headercls" onclick="hide('ProjectDetailBox')">
				<td width="95%">Project Detail</td>
				<td width="5%" align="center"><img
					src="<%=request.getContextPath()%>/images/Darrow.gif"></td>
			</tr>
		</table>

		<div id="ProjectDetailBox" style="border: 1px solid #669;">
		<table class="paddingtable" width="100%">
			<tr>
				<td width="25%" class="title"><b>Project Name</b></td>
				<td width="75%" align="left"><font color="#c00000"><b>${project_name}</b></font>
				</td>

			</tr>

			<tr>
				<td width="25%" class="title"><b>Project Type</b></td>
				<td width="75%" align="left"><font color="#c00000"><b>${project_type}</b></font>
				</td>

			</tr>

			<tr>
				<td width="25%" class="title"><b>Client Name</b></td>
				<td width="75%" align="left"><font color="#c00000"><b>${client_name}</b></font>
				</td>

			</tr>

		</table>
		</div>

		<br />

		<div id="PreValidationDiv" style="border: 1px solid #669;">PreValidation
		Data</div>

		<br />
		<div id="GenericdetailBox">
		<table width="100%" style="border: 1px solid #669;"
			class="paddingtable">
			<tr>
				<td class="title" width="25%"><b>Submission Type</b></td>
				<td width="25%"><s:select list="getSubmissionTypes"
					name="subType" headerKey="-1" headerValue="Select Submission Type"
					listKey="submissionType+','+submissionTypeIndi"
					listValue="submissionType">
				</s:select></td>

				<s:if test="countryRegion == 'eu'">
					<td class="title" width="25%"><b>Submission Description</b></td>
					<td colspan="2" width="25%"><s:textfield name="subDesc"
						size="30" maxlength="100"></s:textfield></td>
				</s:if>
				<s:else>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</s:else>
			</tr>

			<s:if test="countryRegion == 'eu' ">
				<tr>
					<td class="title" width="25%"><b>Submission Mode</b></td>
					<td width="25%"><SELECT name="subVariationMode">
						<option value="-1">Select Mode</option>
						<option value="single">Single</option>
						<option value="grouping">Grouping</option>
						<option value="worksharing">Worksharing</option>
					</SELECT></td>

					<s:if test="workspaceRMS != null">
						<td class="title" width="25%">Include RMS(${workspaceRMS })</td>
						<td style="color: black;" width="25%"><input type="radio"
							name="isRMSSelected" value="Y" id="isRMSSelectedY"
							checked="checked"> <label for="isRMSSelectedY">Yes</label>
						<input type="radio" name="isRMSSelected" value="N"
							id="isRMSSelectedN"> <label for="isRMSSelectedN">No</label>
						</td>
					</s:if>
					<s:else>
						<td width="25%">&nbsp;</td>
						<td width="25%">&nbsp;</td>
					</s:else>

				</tr>
			</s:if>
			<s:else>

			</s:else>

			<tr>
				<td class="title"><s:if test="countryRegion == 'eu'">
							Tracking Number
						</s:if> <s:else>
							Application Number
						</s:else></td>
				<td><s:textfield name="applicationNumber"
					id="applicationNumber" size="20"></s:textfield></td>

				<s:if test="workspaceRMS != null">
					<td class="title" width="25%">Select CMS</td>
					<td width="25%"><s:select list="lstWorkspaceCMS"
						name="selectedCMS" listKey="workspaceCMSId"
						listValue="countryName" multiple="true"
						cssStyle="height:50px;overflow: auto;"
						title="Press 'Ctrl'+'click' to select/deselect CMS">
					</s:select> <a href="javascript:void(0);" onclick="selectAllCMS();">Select
					All</a> <br>
					<input type="checkbox" name="addTT" id="addTT" value="Y"
						<s:if test="lstWorkspaceCMS.size() == 0">
									disabled="disabled" 
								</s:if>
						<s:else>checked="checked"</s:else>> <label for="addTT"
						class="title">Add Tracking Table In PDF</label></td>

				</s:if>
				<s:else>
					<td width="25%">&nbsp;</td>
					<td width="25%">&nbsp;</td>
				</s:else>
			</tr>
			<tr>
				<td class="title"><b>Current Sequence No</b></td>
				<td><s:textfield name="currentSeqNumber"></s:textfield></td>

				<td width="25%">&nbsp;</td>
				<td width="25%">&nbsp;</td>

			</tr>
			<tr id="relatedSequenceNoTr">
				<td class="title"><b>Related Sequence No</b></td>
				<td><s:textfield name="relatedSeqNo"
					title="Enter comma-separated values.(e.g. 0000,0001)" size="35"></s:textfield>
				</td>
				<td width="25%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
			</tr>
			<tr>
				<td class="title"><b>Date Of Submission</b></td>
				<td><input type="text" name="dos" readonly="readonly" id="dos"
					value="${dos }"> (YYYY/MM/DD)</td>
				<td width="25%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
			</tr>
			<tr>
				<td class="title"><b>Transmission Media</b></td>
				<td><SELECT name="subMode">
					<option value="ESG">ESG</option>
					<option value="Mail">Mail</option>
					<option value="Post">Post</option>
					<option value="Email">Email</option>
					<option value="Courier">Courier</option>
					<option value="Web">Web</option>
					<option value="Others">Others</option>
				</SELECT></td>
				<td width="25%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
			</tr>
		</table>

		<div align="center" style="width: 900px; border: 0px;"><!-- <s:submit name="submitBtn" value="Save" cssClass="button" onclick="return validate();"/> -->
		<s:submit name="submitBtn" id="submitBtn" value="Publish And Submit"
			cssClass="button" align="center" /></div>

		<!-- <input type="button" class="button" value="Cancel" > --> <s:hidden
			name="workSpaceId">
		</s:hidden> <s:hidden name="lastPublishedVersion"></s:hidden> <s:hidden
			name="lastConfirmedSubmissionPath"></s:hidden> <s:hidden
			name="projectPublishType"></s:hidden> <s:hidden name="countryRegion"></s:hidden>
		</div>
	</s:form></div>

	<br>

	<s:if test="getWorkspacePublishInfo.size != 0">
		<br>
		<hr color="#5A8AA9" size="3px"
			style="width: 100%; border-bottom: 2px solid #CDDBE4;">
		<br />
		<div align="left">
		<table width="100%">
			<tr>
				<td>
				<table class="datatable doubleheight" width="600px">
					<thead>
						<tr>
							<th colspan="2">Project Submission Details</th>
						</tr>
					</thead>
					<tbody>
						<tr class="even">
							<td><span class="title">Project Name </span> <s:property
								value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).workspaceDesc" />
							</td>
							<td><s:if test="countryRegion == 'eu'">
								<span class="title">Tracking No </span>
								<s:property
									value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).trackingNo" />
							</s:if> <s:else>
								<span class="title">Application No </span>
								<s:property
									value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).applicationNo" />
							</s:else></td>
						</tr>
						<tr class="even">
							<td><span class="title">Submission Country </span> <s:property
								value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).countryName" />
							</td>
							<td><span class="title">Agency </span> <s:property
								value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).agencyName" />
							</td>
						</tr>
					</tbody>
				</table>
				</td>
			</tr>
		</table>

		</div>
		<br>
		<hr color="#5A8AA9" size="3px"
			style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center">
		<br>
		<div id="submissionDtlTblDiv" align="left"
			style="width: 900px; border: 1px solid #5A8AA9; overflow: auto; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px; min-height: 200px;">
		<table id="submissionDtlTbl" align="left"
			class="datatable doubleheight" width="100%">
			<thead>
				<tr>
					<th>#</th>
					<th>Id</th>
					<th>Sequence</th>
					<th>Related Sequences</th>
					<th>Submission Type</th>
					<th>Details</th>
					<th>View XML</th>
					<th>Broken Links</th>
					<th>Validate</th>
					<th>Recompile</th>
					<th>Confirm</th>
					<th>Extract</th>
					<th>Publish Path</th>
					<th>Size</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
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
							<td><s:property value="submissionInfoUSDtlId" /> <s:set
								name="subId" value="%{submissionInfoUSDtlId}"></s:set></td>
						</s:if>
						<s:elseif test="countryRegion == 'eu'">
							<td><!-- For EU v1.4 --> <s:if
								test="regionalDTDVersion == '14'">
								<s:property value="submissionInfoEU14DtlId" />
								<s:set name="subId" value="%{submissionInfoEU14DtlId}"></s:set>
							</s:if> <s:if test="regionalDTDVersion == '20'">
								<s:property value="submissionInfoEU20DtlId" />
								<s:set name="subId" value="%{submissionInfoEU20DtlId}"></s:set>
							</s:if> <!-- For EU v1.3 --> <s:elseif test="regionalDTDVersion == ''">
								<s:property value="submissionInfoEUDtlId" />
								<s:set name="subId" value="%{submissionInfoEUDtlId}"></s:set>
							</s:elseif></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'ca'">
							<td><s:property value="submissionInfoCADtlId" /> <s:set
								name="subId" value="%{submissionInfoCADtlId}"></s:set></td>
						</s:elseif>
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:property value="relatedSeqNo" /></td>
						<td><s:property value="submissionType" /></td>
						<td style="text-align: center;"><a
							onclick="javascript:viewDetails('<s:property value="workspaceId"/>','<s:property value="subId"/>');return false;">
						<img border="0px" alt="View Details"
							src="images/Common/details.png" height="18px" width="18px">
						</a></td>
						<td style="text-align: center;"><s:if
							test="deleted == 'false' && submissionPath.startsWith('//')">
							<a title="View XML"
								href="file:<s:property value="submissionPath"/>/<s:property value="currentSeqNumber"/>/index.xml"
								target="_blank"> <img border="0px" alt="View XML"
								src="images/Common/view.png" height="18px" width="18px"> </a>
						</s:if> <s:elseif
							test="deleted == 'false' && submissionPath.charAt(1) == ':'">
							<a title="View XML"
								href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/docmgmtandpub/<s:property value="submissionPath.replaceAll('[^/]:/[^/]+/','')"/>/<s:property value="currentSeqNumber"/>/index.xml"
								target="_blank"> <img border="0px" alt="View XML"
								src="images/Common/view.png" height="18px" width="18px"> </a>
						</s:elseif></td>
						<td style="text-align: center;"><s:if
							test="deleted == 'false'">
							<a
								onclick="javascript:checkBrokenLinks('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="subId"/>');return false;">
							<img border="0px" alt="Check Broken Links"
								src="images/Common/check.png" height="18px" width="18px">
							</a>
						</s:if></td>
						<td><s:if test="deleted == 'false'">
							<div align="center"><a
								onclick="javascript:validate('<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>');return false;">
							<img border="0px" alt="Validate" src="images/Common/validate.png"
								height="18px" width="18px"> </a></div>
						</s:if></td>
						<s:if
							test="deleted == 'false' && currentSeqNumber == #workspaceCurrentSeqNumber">
							<td style="text-align: center;"><a title="Recompile"
								href="javascript:void(0);"
								onclick="recompileSubmission('<s:property value="submissionPath"/>'+'/'+'<s:property value="currentSeqNumber"/>','<s:property value="workspaceId"/>','${subId }');">
							<img border="0px" alt="Recompile"
								src="images/Common/recompile.png" height="18px" width="18px">
							</a> <!-- recompileSubmission('submissionPath/currentSeqNumber','workspaceId','submissionId') -->
							</td>

							<td><a
								onclick="javascript:confirmSubmission('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="subId"/>','<s:property value='elecSig'/>');return false;">
							Confirm </a></td>
						</s:if>
						<s:else>
							<s:if test="deleted == 'false' && confirm == 'Y'">
								<td style="text-align: center;"><label>-</label></td>
								<td>Confirmed</td>
							</s:if>
							<s:else>
								<td style="text-align: center;">-</td>
								<td style="text-align: center;">-</td>
							</s:else>
						</s:else>
						<td>
						<div id="div_<s:property value='#subId'/>"><a
							onclick="javascript:extract('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','div_<s:property value="#subId"/>');return false;">
						Extract </a></div>
						</td>
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
						<td id='sub<s:property value="#status.count"/>'><s:if
							test="deleted == 'false'">
							<a id='link<s:property value="#status.count"/>' href=#
								onclick="getSize('<s:property value="submissionPath"/>','sub<s:property value="#status.count"/>','imgLoading<s:property value="#status.count"/>','link<s:property value="#status.count"/>');return false;">Get
							Size</a>
							<img src="images/loading.gif"
								id="imgLoading<s:property value="#status.count"/>"
								alt="loading ..." style="display: none;">
						</s:if></td>
						<td id='del<s:property value="#status.count"/>'><s:if
							test="deleted == 'false'">
							<a id='dlink<s:property value="#status.count"/>' href=#
								onclick="delFolder('<s:property value="submissionPath"/>','del<s:property value="#status.count"/>','dimgLoading<s:property value="#status.count"/>','dlink<s:property value="#status.count"/>','row<s:property value="#status.count"/>');return false;">Delete</a>
							<img src="images/loading.gif"
								id="dimgLoading<s:property value="#status.count"/>"
								alt="loading ..." style="display: none;">
						</s:if> <s:else>
			    				Deleted
			    			</s:else></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</div>
		<br>
	</s:if> <input type="hidden" id="workspaceIdH" value=""> <input
		type="hidden" id="submissionPathH" value=""> <input
		type="hidden" id="currentSeqNumberH" value=""> <input
		type="hidden" id="subIdH" value=""></div>
	<div id="treeDiv" style="display: none;" align="center"></div>
	<div id="msgDiv" style="display: none;" align="center"></div>

	<div id="popupContact" style="width: 400px; height: 120px;"><img
		alt="Close" title="Close" src="images/Common/Close.png"
		onclick='cls();' class='popupCloseButton' />
	<h3>Electronic Signature</h3>
	<div style="width: 100%; height: 90px; overflow: auto;">Enter
	confirm password:&nbsp;<input type="password" name="reConfPass"
		id="reConfPass"></input> <br>
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
