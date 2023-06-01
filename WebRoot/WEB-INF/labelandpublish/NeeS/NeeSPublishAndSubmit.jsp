<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<script type="text/javascript">


	$(document).ready(function()
	{ 

		
		//callForProjDocStatus('<s:property value="workSpaceId"/>');
		
		$("#dos").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
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
			target: '#msgDiv',
			url: 'SaveNeeSPublishAndSubmitForm.do',
			beforeSubmit: showRequest,
			success: showResponse
		};
		$('#PublishAndSubmitForm').submit(function()
		{	
			debugger;
			$(this).ajaxSubmit(options);

			
			return false;									
		});
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
	
	<s:form action="SaveNeeSPublishAndSubmitForm" method="post"
		name="PublishAndSubmitForm" id="PublishAndSubmitForm"
		cssStyle="text-align: left;">
		<div id="PreValidationDiv" style="border: 1px solid #669;">PreValidation
		Data</div>
		<div id="GenericdetailBox">
		<table class="paddingtable" cellspacing="0" width="100%;"
			bordercolor="#EBEBEB">

			<tr class="headercls" onclick="hide('ProjectDetailBox')">
				<td width="95%">Project Detail</td>
				<td width="5%" align="center"><img
					src="<%=request.getContextPath()%>/images/Darrow.gif"></td>
			</tr>
		</table>

		<div id="ProjectDetailBox" style="border: 1px solid #669;">
		<table class="paddingtable" width="100%;">
			<tr>
				<td width="30%" class="title"><b>Project Name</b></td>
				<td width="70%"><font color="#c00000"><b>${project_name}</b></font>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Project Type</b></td>
				<td width="70%"><font color="#c00000"><b>${project_type}</b></font>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Client Name</b></td>
				<td width="70%"><font color="#c00000"><b>${client_name}</b></font>
				</td>
			</tr>
			
			<tr>
				<td class="title"><b>Date Of Submission</b></td>
				<td><input type="text" name="dos" readonly="readonly" id="dos"
					value="${dos }"> (YYYY/MM/DD)</td>
			</tr>
			<tr>
				<td class="title">Current Sequence No.</td>
				<s:if test="editSequence=='Yes'">
						<td><s:textfield name="currentSeqNumber" readonly="false"
						size="4"></s:textfield></td>
				
				</s:if>
				<s:else>
					<td><s:textfield name="currentSeqNumber" readonly="true"
						size="4"></s:textfield></td>
				</s:else>
					
			</tr>
					

		</table>
		</div>

		<br />


	
		<div align="center" style="width: 900px; border: 0px;"><!-- <s:submit name="submitBtn" value="Save" cssClass="button" onclick="return validate();"/> -->
		<!--<s:submit name="submitBtn" id="submitBtn"  value="Publish And Submit" cssClass="button" align="center"/>-->
		<input type="submit" name="submitBtn" id="submitBtn"
			value="Publish And Submit" class="button" /></div>
		<!-- <input type="button" class="button" value="Cancel" > --> <s:hidden
			name="workSpaceId">
		</s:hidden> <s:hidden name="lastPublishedVersion">
		</s:hidden> <s:hidden name="lastConfirmedSubmissionPath">
		</s:hidden> <s:hidden name="countryRegion"></s:hidden></div>
		
	</s:form>
	<br />
	
	
	<s:if test="getWorkspacePublishInfo.size != 0">
		<div align="left"><br />
<!--		<hr color="#5A8AA9" size="3px"-->
<!--			style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center">-->
		
		<table width="100%" style="display:none">
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
							
						</tr>

						<tr class="even">
							<td><span class="title">Submission Country </span> <s:property
								value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).countryName" />
							</td>
							
						</tr>

					</tbody>
				</table>
				</td>
			</tr>
		</table>

		</div>
		<br />

		<hr color="#5A8AA9" size="3px"
			style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center">

		<br />
		<div id="submissionDtlTblDiv" align="center"
			style="width: 900px; border: 1px solid #5A8AA9; overflow: auto; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px; min-height: 200px;">

		<table id="submissionDtlTbl" align="center" width="90%"
			class="datatable doubleheight">
			<thead>
				<tr>
					<th>#</th>
					<th>Id</th>
					<th>Sequence</th>
					<th>Confirm</th>
					<th>Publish Path</th>
					<th>TOC Path</th>	
					<th>Add Link</th>			
					<th>Size</th>
					<th>Delete</th>
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
						
							<td><s:property value="submissionInfoNeeSDtlId" /> <s:set
								name="subId" value="%{submissionInfoNeeSDtlId}"></s:set></td>
					
						
						<td><s:property value="currentSeqNumber" /></td>
						
						<s:if test="deleted == 'false' && confirm == 'Y'" >
								
								<td>Confirmed</td>
						</s:if>
							
						
						<s:else>
					
						<s:if
							test="deleted == 'false' && editSequence=='Yes'">
							

							<td><a title="Confirm"
								onclick="javascript:confirmSubmission('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="subId"/>','<s:property value='elecSig'/>');return false;">
							Confirm </a></td>

						</s:if>
						
						<s:if
							test="deleted == 'false' && editSequence=='No'" >
							<s:if test="currentSeqNumber == #workspaceCurrentSeqNumber">
							

							<td><a title="Confirm"
								onclick="javascript:confirmSubmission('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="subId"/>','<s:property value='elecSig'/>');return false;">
							confirm</a></td>

						</s:if>
						<s:else>
							
								<td style="text-align: center;">-</td>
								
							</s:else>
						</s:if>
<!--						 && currentSeqNumber == #workspaceCurrentSeqNumber"-->
						
							
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
						
						
						<td>
						
							<s:if test="deleted == 'false' && submissionPath.startsWith('//')">
								<div align="center">	
								<a target="_blank" 
								href="file:<s:property value='submissionPath'/>/<s:property value='currentSeqNumber'/>/ctd-toc.pdf"
								 title="file:<s:property value='submissionPath'/>/<s:property value='currentSeqNumber'/>/ctd-toc.pdf">
								 <img
								border="0px" alt="Open" src="images/Common/tocicon.PNG"
								height="18px" width="18px"/>
								 
								 </a></div>
							</s:if> 
							<s:elseif test="deleted == 'false' && submissionPath.charAt(1) == ':'">
								<a target="_blank" href="file:<s:property value='submissionPath'/>/<s:property value='currentSeqNumber'/>/ctd-toc.pdf" title="General TOC File">TOC</a>
							</s:elseif></td>
						
						<s:if test="deleted == 'false' && confirm == 'Y'" >
								
								<td style="text-align: center;"><label>-</label></td>
						</s:if>
							
						
						<s:else>
					
						<s:if
							test="deleted == 'false' && editSequence=='Yes'">
							

							<td><div align="center">
									<a target="_blank" href="AddHyperLinkMain.do?subId=<s:property value="subId"/>&workSpaceId=<s:property value="workSpaceId"/>&sequenceNumber=<s:property value="currentSeqNumber"/>&region=<s:property value="countryRegion"/> ">
<img border="0px" alt="Add Link" src="images/Common/add_hyperlink.png" height="18px" width="18px">
</a>
								</div></td>

						</s:if>
						
						<s:if
							test="deleted == 'false' && editSequence=='No'" >
							<s:if test="currentSeqNumber == #workspaceCurrentSeqNumber">
							

							<td><div align="center">
									<a target="_blank" href="AddHyperLinkMain.do?subId=<s:property value="subId"/>&workSpaceId=<s:property value="workSpaceId"/>&sequenceNumber=<s:property value="currentSeqNumber"/>&region=<s:property value="countryRegion"/> ">
<img border="0px" alt="Add Link" src="images/Common/add_hyperlink.png" height="18px" width="18px">
</a>
								</div></td>

						</s:if>
						<s:else>
							
								<td style="text-align: center;">-</td>
								
							</s:else>
						</s:if>
<!--						 && currentSeqNumber == #workspaceCurrentSeqNumber"-->
						
							
						</s:else>
						
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
							test="deleted == 'true'">
							Deleted
						</s:if> <s:else>
							<s:if
								test="currentSeqNumber < #workspaceCurrentSeqNumber && confirm == 'N'">
								<a id='dlink<s:property value="#status.count"/>' href=#
									onclick="delFolder('<s:property value="submissionPath"/>','del<s:property value="#status.count"/>','dimgLoading<s:property value="#status.count"/>','dlink<s:property value="#status.count"/>','row<s:property value="#status.count"/>');return false;">Delete</a>
								<img src="images/loading.gif"
									id="dimgLoading<s:property value="#status.count"/>"
									alt="loading ..." style="display: none;">
							</s:if>
							<s:else>
								-
							</s:else>
						</s:else></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</div>
		<br />
		<br />
	</s:if> <input type="hidden" id="workspaceIdH" value=""> <input
		type="hidden" id="submissionPathH" value=""> <input
		type="hidden" id="currentSeqNumberH" value=""> <input
		type="hidden" id="subIdH" value="">
		
</div>

	
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
	
	<input type="hidden" value="<s:property value='elecSig'/>"
		name="elecSig" id="elecSig" style="display: none;">
	<input type="hidden" value="<s:property value='#session.password'/>"
		name="sessPass" id="sessPass" style="display: none;">

</s:else>
<div id="msgDiv" style="display: none;"></div>




