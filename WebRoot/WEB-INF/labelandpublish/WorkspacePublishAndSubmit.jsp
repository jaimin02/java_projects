<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
$(document).ready(function(){
	
	$(".closeButtton").click(function(){
	    $(".popUpcontactDownload").hide();
	});
});
	var totalNumber=0;
	var totalApplicant=0;
	
	 $("#addTeleNumber").click(function(){

		 if($("#telephoneNumberType").val()=="" || $("#telephoneNumber").val()=="")
			{
				return false;
			}
			totalNumber++;
			var clValue="";
			if(totalNumber%2==0)
			{
					clValue="odd";
			}
			else
			{
				clValue="even";
			}

			
			var row="<tr class="+clValue+"><td><input type='checkbox' class='contactNumbers' checked='checked' name='contactNumbers' value='"+$("#telephoneNumberType").val()+":"+$("#telephoneNumber").val()+"'/></td><td>"+$("#telephoneNumberType option:selected").text()+"</td><td>"+$("#telephoneNumber").val()+"</td></tr>";
			
			$("#tblTeleNumber tbody").append(row);
			$("#telephoneNumber").val("");
			
				  
	 });

	$("#addContactDetails").click(function(){

		    if($("#applicantName").val()=="" || $("#applicantContactType").val()=="")
			{
				return false;
			}
			
			
		    totalApplicant++;
			var clValue="";
			if(totalApplicant%2==0)
			{
					clValue="odd";
			}
			else
			{
				clValue="even";
			}
			
			
		
		var checkedValue = null; 
		var inputElements = document.getElementsByClassName('contactNumbers');
		var contacts="";
		var contactDetails="";
		for(var i=0; inputElements[i]; ++i){
		      if(inputElements[i].checked){
		           checkedValue = inputElements[i].value;
		           if(contacts==''){
		        	   contacts=checkedValue;
		        	   contactDetails=checkedValue;
			         }	
		           else
		           {
						contacts=contacts+"</br>"+checkedValue;
						 contactDetails=contactDetails+";"+checkedValue;
		           }
		       }
		}


		var applicantDetails=$("#applicantContactType").val()+"#"+$("#applicantName").val() +"#"+$("#emails").val()+"#"+contactDetails;

		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='applicantContactDetails' value='"+applicantDetails+"'/></td>"
		+"<td>"+$("#applicantName").val()+"<br>("+$("#applicantContactType option:selected").text()+")</td><td>"+ $("#emails").val() +"</td><td>"+contacts+"</td></tr>";
		
		$("#tblApplicantContact tbody").append(row);
		tbodyApplicantContact++;
		$("#applicantName").val("");
		$("#emails").val("")
		$('#tblTeleNumber tbody').empty();
			
				  
	 });
	 
	
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
				//alert(data);
		  		
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

	function getSeqDescription()	
	{				
			
			var seqDesc = document.getElementById("seqDesc").value;
			
			$.ajax(
			{			
				url: 'GetDescriptionData_ex.do?seqDesCode='+seqDesc,
		  		success: function(data) 
		  		{
					if(data=="::"){
						document.getElementById('DescValue').innerHTML = "<input type='text' disabled='disabled' value='Not Applicable' />";
					}
					else{
						var opt = "  ";			  		
			    		if(data.length > 0)
				    	{
			    			var descriptionArr = data.split(',');
			    			//var aName = document.getElementById('agencyName').value;
			    			for(i=0; i<descriptionArr.length;i++)
				    		{
			    				var keyValuePair = descriptionArr[i];
			    				var keyValuePairArr = keyValuePair.split('::');
			    				var key = keyValuePairArr[0];
			    				var value = keyValuePairArr[1];
	
			    				if(key.length > 0 && value.length > 0){
	
			    					if(value=="date" && key=="singledate") {
			    						opt+="<span><b>"+ "Select Date " +"</b></span>  <input type='text' name="+key+ " id='singledate'/>";
			    					}
			    					else {
			    						
	
				    					if(value=="date") {
				    						opt+="<span>"+ key +"</span>  <input type='text' name="+key+ " id='dos"+(i+1)+"'/>";
				    					}
				    					
			    						else{
				    						opt+="<span>"+ key +"</span>  <input type='text' name="+key+ " id="+key+"/>";
				    					}
				    				}
				    			}
	
			    				
			    				
		    					
			    								    				
			    			}	
			    			document.getElementById('DescValue').innerHTML = opt;
			    			
			    			$("#dos1").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			    			$("#dos2").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			    			$("#singledate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			    			
	
			    			/*if(countryIdArr[0]!=-1)			
			    				showRegionDiv();
			    		    	*/		
			    		}
					}				    		
		    	}	  		
			});	
			return true;
		}


	function getSeqDescription_CA()	
	{				
			
			var seqDescofCA = document.getElementById("seqDesc_CA").value;
			var seqDescofArr = seqDescofCA.split('#');
			$.ajax(
			{			
				url: 'GetDescriptionDataCA_ex.do?seqDesCodeCA='+seqDescofArr[0],
		  		success: function(data) 
		  		{
			  		
					if(data=="::"){
						document.getElementById('DescValue_CA').innerHTML = "<input type='text' disabled='disabled' value='Not Applicable' />";
					}
					else{
						var opt = "  ";			  		
			    		if(data.length > 0)
				    	{
			    			var descriptionArr = data.split(',');
			    			//var aName = document.getElementById('agencyName').value;
			    			for(i=0; i<descriptionArr.length;i++)
				    		{
			    				var keyValuePair = descriptionArr[i];
			    				var keyValuePairArr = keyValuePair.split('::');
			    				var key = keyValuePairArr[0];
			    				var value = keyValuePairArr[1];
	
			    				if(key.length > 0 && value.length > 0){
	
			    					if(value=="date" && key=="singledate") {
			    						opt+="<span><b>"+ "Select Date " +"</b></span>  <input type='text' name="+key+ " id='singledate'/>";
			    					}
			    					else {
			    						
	
				    					if(value=="date") {
				    						opt+="<span>"+ key +"</span>  <input type='text' name="+key+ " id='dos"+(i+1)+"'/>";
				    					}
				    					
			    						else{
				    						opt+="<span>"+ key +"</span>  <input type='text' name="+key+ " id="+key+"/>";
				    					}
				    				}
				    			}
	
			    				
			    				
		    					
			    								    				
			    			}	
			    			document.getElementById('DescValue_CA').innerHTML = opt;
			    			
			    			$("#dos1").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			    			$("#dos2").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			    			$("#singledate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			    			
	
			    			/*if(countryIdArr[0]!=-1)			
			    				showRegionDiv();
			    		    	*/		
			    		}
					}
		    				    		
		    	}	  		
			});	
			return true;
		}
	function showRequest(formData, jqForm, options) {
		var queryString = $.param(formData);
		var formElement = jqForm[0];
		$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');
		/*if(publishSequence())
		{
			showMsg();
			return true;
		}
		else
			return false;
		*/
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
	<div><s:form action="SavePublishAndSubmitForm" method="post"
		name="PublishAndSubmitForm" id="PublishAndSubmitForm"
		cssStyle="text-align: left;">
		<s:if test="projectPublishType == 'M' ">
			<div class="" align="center">
				<span style="color:#c00000">Make sure you have uploaded previous sequence inside <b><s:property value="manualPrevSeq" /></b></span>
			</div>
		</s:if> 

		
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

		</table>
		</div>

		<br />

		<div id="PreValidationDiv" style="border: 1px solid #669;">PreValidation
		Data</div>

		<br />
		<div id="GenericdetailBox">
		<table width="100%" style="border: 1px solid #669;"
			class="paddingtable">
			<s:if test="countryRegion == 'au' ">
				
				<tr>
					<td class="title" width="22%"><b>Sequence Type</b></td>
					<td width="20%">
						 <s:select list="getSequenceTypes"
						name="seqTypes" id="seqTypes" headerKey="-1"
						headerValue="Select Sequence Type" listKey="sequenceTypeCode"
						listValue="sequenceTypeDescription">
					</s:select>
				</tr>
				<tr>
					<td class="title" width="22%"><b>Sequence Description</b></td>
					<td width="20%">
						<s:select list="getSequenceDescriptions"
						name="seqDesc" id="seqDesc" headerKey="-1" headerValue="Select Sequence Description"
						listKey="seqDescCode"
						listValue="sequenceDescription" onchange="getSeqDescription();">
						</s:select>
					</td>
				</tr>
				<tr>
					<td class="title" width="22%"><b>Sequence Description Value</b></td>
					<td width="20%">
						<div id="DescValue" class="title">
							
						</div>
					</td>
				</tr>
			</s:if>
			<s:if test="countryRegion == 'th' ">
				
				<tr>
					<td class="title" width="22%"><b>Sequence Type</b></td>
					<td width="20%">
						 <s:select list="getSequenceTypes_Thai"
						name="seqTypes_th" id="seqTypes_th" headerKey="-1"
						headerValue="Select Sequence Type" listKey="sequenceTypeCode"
						listValue="sequenceTypeDescription">
					</s:select>
					</td>
				</tr>
				<tr>
					<td class="title" width="22%"><b>Sequence Description</b></td>
					<td width="20%"><s:textfield name="seqDesc_th"
					id="seqDesc_th" size="25"></s:textfield></td>
				</tr>
				<tr>
					<td class="title" width="22%"><b>Email</b></td>
					<td width="20%"><s:textfield name="email"
					id="email" size="25"></s:textfield></td>
				</tr>
				
			</s:if>
			<s:if test="countryRegion == 'ca' ">
				
				<tr>
					<td class="title" width="22%"><b>Regulatory Activity Type</b></td>
					<td width="20%">
						 <s:select list="getRegulatoryActivityType"
						name="regActType" id="regActType" headerKey="-1"
						headerValue="Select Regulatory Activity Type" listKey="regActTypeCode"
						listValue="regActTypeDescription">
					</s:select>
					</td>
				</tr>
				<tr>
					<td class="title" width="22%"><b>Sequence Description</b></td>
					<td width="20%">
						<s:select list="getSequenceDescriptions_CA"
						name="seqDesc_CA" id="seqDesc_CA" headerKey="-1" headerValue="Select Sequence Description"
						listKey="seqDescCode+'#'+sequenceDescription"
						listValue="sequenceDescription" onchange="getSeqDescription_CA();">
						</s:select>
					</td>
				</tr>
				<tr>
					<td class="title" width="22%"><b>Sequence Description Value</b></td>
					<td width="20%">
						<div id="DescValue_CA" class="title">
							
						</div>
					</td>
				</tr>
			</s:if>
				<s:if test="countryRegion != 'au' && countryRegion != 'th' && countryRegion != 'ca'">
				<tr>
					<td class="title" width="22%"><b>Submission Type</b></td>
					<td width="20%">
					
						<s:if test="countryRegion == 'us' && regionalDTDVersion == '23'"> 
						 <s:select list="getSubmissionTypes"
							name="subType" headerKey="-1" headerValue="Select Submission Type"
							listKey="submissionTypeCode+','+submissionTypeIndi"
							listValue="submissionType">
						</s:select>
						</s:if>
						<s:elseif test="countryRegion == 'ch'"> 
							 <s:select list="getSubmissionTypes" id="subType_ch"
								name="subType_ch" headerKey="-1" headerValue="Select Submission Type"
								listKey="submissionTypeCode"
								listValue="submissionType">
							 </s:select>
							  <input type="button" value="Add" id="addMultiple" class="button"
						onclick="addMultipleSubmissionType();" )/>
						</s:elseif>
						<s:elseif test="countryRegion == 'za' && regionalDTDVersion == '20'">
							<s:select list="getSubmissionTypes" id="subType_za20"
								name="subType_za20" headerKey="-1" headerValue="Select Submission Type"
								listKey="submissionType+','+submissionTypeIndi"
								listValue="submissionTypeValue">
							</s:select>
							 <!-- <input type="button" value="Add" id="addMultipleZA20" class="button"
							onclick="addMultipleSubmissionTypeZa20();" )/>
							-->
						</s:elseif>
						<s:else>
						 <s:select list="getSubmissionTypes"
							name="subType" headerKey="-1" headerValue="Select Submission Type"
							listKey="submissionType+','+submissionTypeIndi"
							listValue="submissionTypeValue">
						</s:select>
						
						</s:else>
						<!--<s:if test="countryRegion == 'za' && regionalDTDVersion == '20'">
				
							<td></td>
							<td>
							<table width="100%" id="tblMultipleSubmissonTypeZa20" class="datatable" style="margin-bottom:6px;margin-top:16px;">
								<thead>
									<tr>
										<th>#</th>
										<th>Submission Types</th>
									</tr>
								</thead>
								<tbody>
		
								</tbody>
		
							</table>
							</td>
					</s:if>-->
					</td>
					
				
					<s:if test="(countryRegion == 'eu' && ( regionalDTDVersion == '13' || regionalDTDVersion == '14' ||  regionalDTDVersion == '20')) || countryRegion=='gcc' ">
						<td class="title" width="25%"><b>Submission Description</b></td>
						<td colspan="2" width="25%"><s:textfield name="subDesc"
							size="30" maxlength="800"></s:textfield></td>
					</s:if>
					<s:else>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</s:else>
				</tr>
				</s:if>
				
				<!-- --------------------------------ZA Code Efficacy---------------------------- -->
			<s:if test="countryRegion == 'za' ">
				<tr>	
				<td></td>
					<td>
					<div style="width:384px;" id="subEfficacyValue" class="testing">
					<table width="100%" align="center" class="datatable" id="subEfficacy">
						<thead>
							<tr>
								<th align="left" colspan="2" style="padding-left: 5px;"><input
									type="checkbox" id="chkAllEfficacy"
									onclick="selectAllDataType();" /> &nbsp;Submission/Efficacy</th>

							</tr>
						</thead>

						<tbody>
							<tr>
								<td><input type="checkbox" value="non-cl" name="datatype"
									class="selDatatypeCls" />&nbsp; Non-clinical</td>
								<td width="55%"><input type="checkbox" value="cl"
									name="datatype" class="selDatatypeCls" />&nbsp;
								Clinical&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td><input type="checkbox" value="be" name="datatype"
									class="selDatatypeCls" />&nbsp; Bioequivalence study &nbsp;&nbsp;</td>
								<td><input type="checkbox" value="na" name="datatype"
									class="selDatatypeCls" /> &nbsp;Not applicable&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr style="padding: 2px 0px 2px 8px; height: 26px;">
								<td><input type="checkbox" value="other" name="datatype"
									class="selDatatypeCls" onClick="hideShowdatatypeDesciption();" />
								&nbsp;Other &nbsp;&nbsp;</td>
								<td><span id="efficacyDescription" style="display: none;"><input
									type="text" size="20" name="efficacyDescription"
									id="efficacyDesc" /></span></td>
							</tr>


						</tbody>
					</table>
					</div>

					</td>
					<s:if test="regionalDTDVersion == '20'">
					<td><input type="button" value="Add" id="addMultipleZA20" class="button"
							onclick="addMultipleSubmissionTypeZa20();" )/></td>
							</s:if>
					</tr>
				</s:if>
			<s:if test="countryRegion == 'za' && regionalDTDVersion == '20'">
				
							<td></td>
							<td>
							<table width="100%" id="tblMultipleSubmissonTypeZa20" class="datatable" style="margin-bottom:6px;margin-top:16px;">
								<thead>
									<tr>
										<th>#</th>
										<th>Submission Types</th>
										<th>Efficacy</th>
									</tr>
								</thead>
								<tbody>
		
								</tbody>
		
							</table>
							</td>
					</s:if>
			
			<!-- ------------------------------------------------------------------- -->
				
			<s:if test="countryRegion == 'ch'">
				<tr>
					<td></td>
					<td>
					<table width="48%" id="tblMultipleSubmissonType" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<th>Submission Types</th>
							</tr>
						</thead>
						<tbody>

						</tbody>

					</table>
					</td>


				</tr>
			</s:if>
			
			
			<s:if test="countryRegion == 'eu' && regionalDTDVersion == '301'">
				<tr>
					<td class="title" width="22%"><b>Submission Description</b></td>
					<td width="20%"><s:textfield name="subDesc" size="30" maxlength="800"></s:textfield></td>
					
					
				</tr>
			</s:if>
			<s:if test="countryRegion == 'us' && regionalDTDVersion == '23'">
			<tr>
				<td class="title" width="22%"><b>Submission Sub Type</b></td>
				<td width="20%"><s:select list="getSubmissonSubTypes"
					name="subSubType" headerKey="-1" headerValue="Select Submission Sub Type"
					listKey="submissionSubTypeCode"
					listValue="submissionSubTypeDisplay">
				</s:select></td>
				
				
			</tr>
			
			</s:if>
			<s:if test="countryRegion=='eu' && regionalDTDVersion == '301' ">
			
			<tr>
					<td class="title"><b>Submission Unit Type</b></td>
					<td> <SELECT
						name="submissionUnitType_eu_301">

						<option value="-1">Select Submission Unit Type</option>
						<option value="initial">Initial submission to start any regulatory activity</option>
						<option value="validation-response">For rectifying business validation issues</option>
						<option value="response">Response to any kind of question</option>
						<option value="corrigendum">Corrigendum</option>
						<option value="additional-info">Other additional Information</option>
						<option value="closing">Final documents in the centralised procedure</option>
						<option value="consolidating">Consolidates the application after several information in the MRP or DCP handled outside the eCTD application</option>
						<option value="reformat">Reformatting of an existing submission application</option>

					</SELECT></td>
			</tr>
			</s:if>
			<s:if test="countryRegion=='gcc' && regionalDTDVersion == '15' ">
			
			<tr>
					<td class="title"><b>Submission Unit Type</b></td>
					<td> <SELECT
						name="submissionUnitType">

						<option value="-1">Select Submission Unit Type</option>
						<option value="initial">initial</option>
						<option value="response">response</option>
						<option value="additional-info">additional-info</option>
						<option value="closing">closing</option>
						<option value="correction">correction</option>
						<option value="reformat">reformat</option>

					</SELECT></td>
			</tr>
			</s:if>
			<s:if test="countryRegion=='eu' && regionalDTDVersion == '301' ">
				<tr>
						<td class="title"><b>Submission Mode</b></td>
						<td><!-- disabled for EU v1.3 --> <SELECT
							name="subVariationMode">
	
							<option value="-1">Select Mode</option>
							<option value="single">Single</option>
							<option value="grouping">Grouping</option>
							<option value="worksharing">Worksharing</option>
	
						</SELECT></td>
				</tr>
				<tr>
					<s:if test="workspaceRMS != null">
							<td class="title" width="25%">Include RMS(${workspaceRMS })</td>
							<td style="color: black;" colspan="2" width="25%"><input
								type="radio" name="isRMSSelected" class="RMSSel" value="Y"
								id="isRMSSelectedY" checked="checked"> <label
								for="isRMSSelectedY">Yes</label> <input type="radio"
								name="isRMSSelected"
								<s:if test="lstWorkspaceCMS.size() == 0"> 
									disabled="disabled"
								</s:if>
								class="RMSSel" value="N" id="isRMSSelectedN"> <label
								for="isRMSSelectedN">No</label></td>
						</s:if>
	
				</tr>
			</s:if>
			<s:if test="(countryRegion == 'eu' && regionalDTDVersion != '301') || countryRegion=='gcc' || countryRegion == 'au' ">
				<tr>
					<td class="title"><b>Submission Mode</b></td>
					<td><!-- disabled for EU v1.3 --> <SELECT
						name="subVariationMode">

						<option value="-1">Select Mode</option>
						<option value="single">Single</option>
						<option value="grouping">Grouping</option>
						<option value="worksharing">Worksharing</option>

					</SELECT></td>

					<s:if test="workspaceRMS != null">
						<td class="title" width="25%">Include RMS(${workspaceRMS })</td>
						<td style="color: black;" colspan="2" width="25%"><input
							type="radio" name="isRMSSelected" class="RMSSel" value="Y"
							id="isRMSSelectedY" checked="checked"> <label
							for="isRMSSelectedY">Yes</label> <input type="radio"
							name="isRMSSelected"
							<s:if test="lstWorkspaceCMS.size() == 0"> 
								disabled="disabled"
							</s:if>
							class="RMSSel" value="N" id="isRMSSelectedN"> <label
							for="isRMSSelectedN">No</label></td>
					</s:if>

				</tr>
			</s:if>
			
			
			<s:elseif test="countryRegion == 'ch'">
				<tr>
					<td class="title">Paragraph-13-tpa</td>
					<td><select name="paragraph13">
						<option value="no">No</option>
						<option value="yes">Yes</option>

					</select></td>
				</tr>
			</s:elseif>
			<s:else>

			</s:else>

			<tr>
				<td class="title"><s:if
					test="countryRegion == 'eu' && (regionalDTDVersion == '13' || regionalDTDVersion == '14' || regionalDTDVersion == '20' || regionalDTDVersion == '301')">
							Tracking Number
						</s:if> <s:elseif test="countryRegion == 'gcc'">
							Application Reference Number
						</s:elseif><s:elseif test="countryRegion == 'au' || countryRegion == 'th' ">
							eSubmissionId
						</s:elseif> 
						<s:elseif test="countryRegion == 'ca' ">
							Dossier Identifier
						</s:elseif>
						<s:else>
							Application Number
						</s:else></td>
				<td><s:textfield name="applicationNumber"
					id="applicationNumber" size="25"></s:textfield></td>
				<s:if test="(countryRegion == 'eu' && regionalDTDVersion != '301') ||  countryRegion == 'gcc'">
						<s:if test="workspaceRMS != null">
							<td class="title">Select CMS&nbsp;</td>
							<td>
							<div
								style="width: 100%; max-height: 100px; overflow-y: auto; border: 1px solid #666699">
							<input type="hidden" id="cmsSize"
								value="<s:property value="lstWorkspaceCMS.size()"/>"> <s:if
								test="lstWorkspaceCMS.size() == 0">
								<p style="color: navy;">No CMS country Added for this Project</p>
							</s:if> <s:else>
								<table width="100%" align="center" class="datatable">
									<thead>
										<tr>
											<s:if test="lstWorkspaceCMS.size() > 1">
												<th align="center" style="text-align: center;"><input
													type="checkbox" id="chkAllCMS" onclick="selectAllCMS();" /></th>
											</s:if>
											<s:else>
												<th></th>
											</s:else>
											<th>CMS Country</th>
											<th>Tracking Number</th>
										</tr>
									</thead>
		
									<tbody>
										<s:iterator value="lstWorkspaceCMS" id="lstWorkspaceCMS"
											status="status">
											<tr
												class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
												<td style="text-align: center;"><s:property
													value="lstWorkspaceCMS.count" /> <input type="checkbox"
													value="<s:property value="workspaceCMSId"/>"
													name="selectedCMS" class="selCMSCls"
													id="selectedCMS<s:property value="workspaceCMSId"/>"
													onclick="toggleTxtTrackNo('<s:property value="workspaceCMSId"/>')" />
												</td>
												<td><label
													for="selectedCMS<s:property value="workspaceCMSId"/>"><s:property
													value="countryName" /></label></td>
												<td><input type="text" name="trackCMS"
													id="trackCMS<s:property value="workspaceCMSId"/>" size="9"
													disabled="disabled"
													<s:if test="cmsTrackNum == null || cmsTrackNum == ''">value="<s:property value="applicationNumber"/>"</s:if>
													<s:else>value="<s:property value="cmsTrackNum"/>"</s:else> />
												</td>
		
		
		
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</s:else></div>
		
		
							</td>
						</s:if>
				</s:if>
		<!-- 	<s:if test="countryRegion == 'za' ">
					<td>&nbsp;</td>
					<td>
					<div>
					<table width="100%" align="center" class="datatable">
						<thead>
							<tr>
								<th align="left" colspan="2" style="padding-left: 5px;"><input
									type="checkbox" id="chkAllEfficacy"
									onclick="selectAllDataType();" /> &nbsp;Submission/Efficacy</th>

							</tr>
						</thead>

						<tbody>
							<tr>
								<td><input type="checkbox" value="non-cl" name="datatype"
									class="selDatatypeCls" />&nbsp; Non-clinical&nbsp;&nbsp;</td>
								<td width="55%"><input type="checkbox" value="cl"
									name="datatype" class="selDatatypeCls" />&nbsp;
								Clinical&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td><input type="checkbox" value="be" name="datatype"
									class="selDatatypeCls" />&nbsp; Bioequivalence study
								&nbsp;&nbsp;</td>
								<td><input type="checkbox" value="na" name="datatype"
									class="selDatatypeCls" /> &nbsp;Not
								applicable&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr style="padding: 2px 0px 2px 8px; height: 26px;">
								<td><input type="checkbox" value="other" name="datatype"
									class="selDatatypeCls" onClick="hideShowdatatypeDesciption();" />
								&nbsp;Other &nbsp;&nbsp;</td>
								<td><span id="efficacyDescription" style="display: none;"><input
									type="text" size="20" name="efficacyDescription"
									id="efficacyDesc" /></span></td>
							</tr>


						</tbody>
					</table>
					</div>

					</td>
				</s:if>
				

	-->

			</tr>
			
			<s:if test="(countryRegion == 'eu' && regionalDTDVersion == '301')">
						<s:if test="workspaceRMS != null">
						<tr>
							<td class="title">Select CMS&nbsp;</td>
							<td>
							<div
								style="width: 100%; max-height: 100px; overflow-y: auto; border: 1px solid #666699">
							<input type="hidden" id="cmsSize"
								value="<s:property value="lstWorkspaceCMS.size()"/>"> <s:if
								test="lstWorkspaceCMS.size() == 0">
								<p style="color: navy;">No CMS country Added for this Project</p>
							</s:if> <s:else>
								<table width="100%" align="center" class="datatable">
									<thead>
										<tr>
											<s:if test="lstWorkspaceCMS.size() > 1">
												<th align="center" style="text-align: center;"><input
													type="checkbox" id="chkAllCMS" onclick="selectAllCMS();" /></th>
											</s:if>
											<s:else>
												<th></th>
											</s:else>
											<th>CMS Country</th>
											<th>Tracking Number</th>
										</tr>
									</thead>
		
									<tbody>
										<s:iterator value="lstWorkspaceCMS" id="lstWorkspaceCMS"
											status="status">
											<tr
												class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
												<td style="text-align: center;"><s:property
													value="lstWorkspaceCMS.count" /> <input type="checkbox"
													value="<s:property value="workspaceCMSId"/>"
													name="selectedCMS" class="selCMSCls"
													id="selectedCMS<s:property value="workspaceCMSId"/>"
													onclick="toggleTxtTrackNo('<s:property value="workspaceCMSId"/>')" />
												</td>
												<td><label
													for="selectedCMS<s:property value="workspaceCMSId"/>"><s:property
													value="countryName" /></label></td>
												<td><input type="text" name="trackCMS"
													id="trackCMS<s:property value="workspaceCMSId"/>" size="9"
													disabled="disabled"
													<s:if test="cmsTrackNum == null || cmsTrackNum == ''">value="<s:property value="applicationNumber"/>"</s:if>
													<s:else>value="<s:property value="cmsTrackNum"/>"</s:else> />
												</td>
		
		
		
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</s:else></div>
		
		
							</td>
						</tr>
						</s:if>
				</s:if>
			<s:if test="countryRegion == 'us' && regionalDTDVersion == '23'">
			<tr>
				<td class="title">Cross Ref. Application Number & Application Type</td>
				<td colspan="2">
					<s:textfield name="crossReferenceNumber"></s:textfield>
				
				 <s:select list="getApplicationTypes"
						name="applicationTypeCode" headerKey="-1" headerValue="Select Application Type"
						listKey="applicationTypeCode"
						listValue="applicationTypeDisplay">
					</s:select>
				</td>
			</tr>
			<tr>
				<td class="title">Supplement Effective Date Type</td>
				<td colspan="2">
							
				 <s:select list="getSupplimentEffectiveDateType"
						name="supplementEffectiveDateTypeCode" headerKey="-1" headerValue="Select Supplement Effective Date Type"
						listKey="supplementEffectiveDateTypeCode"
						listValue="supplementEffectiveDateTypeDisplay">
					</s:select>
				</td>
			</tr>
			
			
			
			</s:if>
			<s:if test="workspaceRMS != null && countryRegion != 'gcc' && (countryRegion == 'eu' && regionalDTDVersion == '301')">
				<tr>
					<td></td>
					<td><input type="checkbox" name="addTT" id="addTT" value="Y">
						<label for="addTT" class="title">Add Tracking Table In PDF</label>
						<br />
					</td>
				</tr>
			</s:if>
			<tr>
				<td class="title">Current Sequence No.</td>
				<td>
					<s:if test="editSequence=='Yes'">
						<s:textfield name="currentSeqNumber" 
							size="4" ></s:textfield>
					</s:if>
					<s:else>
							<s:if test="lastPublishedVersion=='-999' && countryRegion == 'us'">
								<s:textfield name="currentSeqNumber" 
								size="4" ></s:textfield>
							</s:if>
							<s:else>
								<s:textfield name="currentSeqNumber" readonly="true"
									size="4"></s:textfield>
							</s:else>
							
					</s:else>
				</td>
				<s:if test="workspaceRMS != null && countryRegion != 'gcc' && (countryRegion == 'eu' && regionalDTDVersion != '301')">
					<td></td>
					<td><input type="checkbox" name="addTT" id="addTT" value="Y">
					<label for="addTT" class="title">Add Tracking Table In PDF</label>
					<br />
					<!-- 
							<input type="checkbox" name="addTTinPDF" id="addTTinPDF" value="Y">
							<label for="addTTinPDF" class="title">Add Tracking Table In PDF</label>
							 --></td>
				</s:if>
			</tr>
			
			<tr id="relatedSequenceNoTr">
				<td class="title">Related Sequence No.</td>
				<td>
				<s:if test="projectPublishType=='M'">
					<s:textfield name="relatedSeqNo" title="Enter comma-separated values.(e.g. 0000,0001)"></s:textfield>
					<tr>
						<td></td>
						<td><input type="checkbox" name="copyRelatedSeq" value="Y" id="copyRelatedSeq"/>	
								<label for="cpyrelated" class="title">Copy all related sequence</label>
						 </td>
					</tr>
				</s:if>
				<s:else>
				<div
					style="width: 200px; min-height: 20px; border: 1px solid #669; color: black; padding: 5px;">
					
					<s:if test="getAllWorkspaceSequences.size > 0">
						<s:checkboxlist list="getAllWorkspaceSequences" name="relatedSeqNo"
							listKey="top" listValue="top">
						</s:checkboxlist>
					</s:if> 
					<s:else>No Related Sequences Found</s:else>
						
				</div>
				</s:else>
				</td>
			</tr>
			
			<s:if test="countryRegion == 'au'">
			<tr>
					<td class="title" width="22%"><b>ARTG Number</b></td>
					
				
					<td width="20%"><b> <input type="text"
						name="artgNumber" id="artgNumber" /></b><input type="button" value="Add" id="addMultiple" class="button"
						onclick="addMultipleARTG();" )/></td>
		

				</tr>
				<tr>
					<td></td>
					<td>
					<table width="30%" id="tblMultipleARTG" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<th>ARTG Number</th>
							</tr>
						</thead>
						<tbody>

						</tbody>

					</table>
					</td>


				</tr>
			</s:if>
			<s:if test="countryRegion != 'za' && countryRegion != 'au' && countryRegion != 'ca'" >
				<tr>
					<td class="title"><b>Date Of Submission</b></td>
					<td><input type="text" name="dos" readonly="readonly" id="dos"
						value="${dos }"> (YYYY/MM/DD)</td>
				</tr>
			</s:if>
			
			
			<s:if test="countryRegion != 'za' && countryRegion != 'au'">
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
				</tr>
			</s:if>
			<s:elseif test="countryRegion == 'za'">
				<tr>
					<td class="title"><b><u>Multiple Application :</u> </b></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<s:if test="regionalDTDVersion == '10'">
						<td class="title"><b> Propriatery Name</b></td>
						<td class="title"><b> Date of Application</b></td>
					
					</s:if>
					<s:elseif test="regionalDTDVersion == '20'">
						<td class="title"><b> Multiple/Duplicate Applications</b></td>
						<td class="title"><b> Application Numbers</b></td>	
					
					</s:elseif>
					<s:else>
						<td class="title"><b> Propriatery Name</b></td>
						<td class="title"><b> Date of Application</b></td>
					
					</s:else>
				</tr>
				<tr>
					<td class="title">
						<b> <input type="text" name="propriateryName" id="propriateryName" /></b>
					</td>
					
					<td>
					<s:if test="regionalDTDVersion == '10' || regionalDTDVersion == null">
						<input type="text" name="dos" readonly="readonly" id="dos" value="${dos }">
					</s:if>
					
					<s:if test="regionalDTDVersion == '20'">
						<input type="text" name="propriateryAppNo" id="propriateryAppNo">
					</s:if>
					
					<input type="button" value="Add" id="addMultiple" class="button" onclick="addMultipleApplication();" )/>
					</td>

				</tr>
				<tr>
					<td colspan="2">
					<table width="95%" id="tblMultipleApplication" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<s:if test="regionalDTDVersion == '10' || regionalDTDVersion == null">
									<th>Propriatery Name</th>
									<th>Date of Application</th>
								</s:if>
								<s:else>
									<th>Multiple/Duplicate Applications</th>
									<th>Application Number</th>
								</s:else>
							</tr>
						</thead>
						<tbody>

						</tbody>

					</table>
					</td>


				</tr>

			</s:elseif>
			
			<s:if test="countryRegion == 'us' && regionalDTDVersion == '23'">
			
			<tr>
				<td width="100%" colspan="2">
					<hr/>
				</td>
			</tr>
			<tr>
				<td class="title"><b><u>Applicant Contact Details :</u> </b></td>
			</tr>
			<tr>
				<td class="title"> Applicant Contact Type</td>
				<td>
					
					<s:select list="getApplicantContactTypes" id="applicantContactType"
					name="applicantContactType" headerKey="-1" headerValue="Select Applicant Contact Type"
					listKey="applicantContactTypeCode"
					listValue="applicantContactTypeDisplay">
				</s:select>
					
					
				</td>
				
			</tr>	
			<tr>
				<td class="title"><b> Applicant Name</b></td>
				
				<td class="title">
										
				 <input type="text"
					name="applicantName" id="applicantName" /></b></td>
				

			</tr>
			<tr>	
				
				<td class="title"><b> Emails</b></td>
				<td colspan="2">
				 <input type="text"
					name="emails" id="emails"/>
					
					Use semicolon<b>( ; )</b> for multiple emails.
					
				</td>	
				
			</tr>
			<tr>
				<td class="title"><b> Telephone Details</b></td>
				<td>
				
					<s:select list="getTelephoneNumberType" id="telephoneNumberType"
					name="telephoneNumberType" headerKey="-1" headerValue="Select Telephone Number Type"
					listKey="telephoneNumberTypeCode"
					listValue="telephoneNumberTypeDisplay">
				</s:select>
				
					
					<input type="text" name="telephoneNumber" id="telephoneNumber"/>
				
				<input type="button" value=" + " id="addTeleNumber" class="button"  /></td>
					
					<td>
					<table width="83%" id="tblTeleNumber" class="datatable">
						<thead>
							<tr>
								<th width="3%">#</th>
								<th width="20%">Number Type</th>
								<th width="20%">Number</th>

							</tr>
						</thead>
						<tbody>
						</tbody>

					</table>
			
				</td>
			</tr>
			
			<tr>
				<td><input type="button" value="Add Contact Details" id="addContactDetails" class="button"  /></td>
			</tr>
			<tr>
				<td colspan="3">
				<table width="95%" id="tblApplicantContact" class="datatable">
					<thead>
						<tr>
							<th>#</th>
							<th>Applicant Name</th>
							<th width="20%">Emails</th>
							<th>Telephone Number(s) </th>
							
						</tr>
					</thead>
					<tbody>

					</tbody>

				</table>
				</td>


			</tr>

			</s:if>
				
			
			<s:if test="countryRegion == 'ch'">
				<tr align="left">
					<td class="title" colspan="5"><b><u>Galenic Form
					Details:</u> </b>
					<table>
						<tr>
							<td class="title" width="3%">&nbsp;</td>
							<td class="title"><b> Galenic Form</b></td>
							<td class="title"><b> Galenic Number</b></td>
							<td class="title"><b> Language</b></td>
							<td class="title"><b> Galenic Name</b></td>
							<td class="title"><b> &nbsp;</b></td>

						</tr>
						<tr>
							<td class="title">&nbsp;</td>
							<td class="title"><b> <input type="text"
								name="galenicForm" id="galenicForm" /></b></td>
							<td class="title"><b> <input type="text"
								name="galenicNum" id="galenicNum" /></b></td>
							<td class="title"><select name="galenicLang"
								id="galenicLang" style="width: 170px; padding-left: 5px;">
								<option value="de">German</option>
								<option value="fr">French</option>
								<option value="it">italian</option>
							</select></td>
							<td><input type="text" name="galenicName" id="galenicName" />
							</td>
							<td><input type="button" value="Add" id="addMultiple"
								class="button" onclick="addGanelic();" )/></td>

						</tr>

					</table>

					</td>
				</tr>

				<tr>
					<td colspan="5">
					<table width="83%" id="tblGalenic" class="datatable">
						<thead>
							<tr>
								<th width="3%">#</th>
								<th width="20%">Galenic Form</th>
								<th width="20%">Galenic Number</th>
								<th width="20%">Language</th>
								<th width="20%">Galenic Name</th>

							</tr>
						</thead>
						<tbody>
						</tbody>

					</table>
					</td>
				</tr>

			</s:if>

		</table>
		<div align="center" style="width: 900px; border: 0px;"><!-- <s:submit name="submitBtn" value="Save" cssClass="button" onclick="return validate();"/> -->
		<!--<s:submit name="submitBtn" id="submitBtn"  value="Publish And Submit" cssClass="button" align="center"/>
		
			<input type="submit" name="submitBtn" id="submitBtn"
			value="Publish And Submit" class="button"/>-->
			<input type="button" name="submitBtn" id="submitBtn"
			value="Publish And Submit" class="button" onclick="publishSequence()"/>
			</div>
		<!-- <input type="button" class="button" value="Cancel" > --> <s:hidden
			name="workSpaceId">
		</s:hidden> <s:hidden name="lastPublishedVersion">
		</s:hidden> <s:hidden name="lastConfirmedSubmissionPath">
		</s:hidden> <s:hidden name="countryRegion"></s:hidden>
		<s:hidden name="projectPublishType"></s:hidden>
		<s:hidden name="regionalDTDVersion"></s:hidden></div>
	</s:form></div>
	<br />
	<s:if test="getWorkspacePublishInfo.size != 0">
		<div align="left"><br />
		<hr color="#5A8AA9" size="3px"
			style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center">
		<br />
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
							<td><s:if
								test="countryRegion == 'eu' && ( prevEUDtdVersion == '14' || prevEUDtdVersion=='20')">
								<span class="title">Tracking No </span>
								<s:property
									value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).trackingNo" />
							</s:if> 
							<s:elseif test="countryRegion == 'eu' && prevEUDtdVersion == '13'">
								<span class="title">Tracking No </span>
								<s:property
									value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).applicationNo" />
							</s:elseif>
							<s:elseif test="countryRegion == 'au' || countryRegion == 'th'">
								<span class="title">eSubmissionId </span>
								<s:property
									value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).eSubmissionId" />
							</s:elseif>
							<s:elseif test="countryRegion == 'ca'">
								<span class="title">Dossier Identifier </span>
								<s:property
									value="getWorkspacePublishInfo.get(getWorkspacePublishInfo.size()-1).dossierIdentifier" />
							</s:elseif>
							<s:else>
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
		<br />

		<hr color="#5A8AA9" size="3px"
			style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center">

		<br />
		<div id="submissionDtlTblDiv" align="left"
			style="width: 900px; border: 1px solid #5A8AA9; overflow: auto; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px; min-height: 200px;">

		<table id="submissionDtlTbl" align="left"
			class="datatable doubleheight">
			<thead>
				<tr>
					<th>#</th>
					<th>Id</th>
					<th>Sequence</th>
					<th>Related Sequences</th>
					<s:if test="countryRegion != 'au' && countryRegion != 'th' && countryRegion != 'ca'">
						<th>Submission Type</th>
					</s:if>	
					<th>Details</th>
					<th>View XML</th>
					<th>Broken Links</th>
					<th>Validate</th>
					<th>Add Link</th>
					<th>Recompile</th>
					<th>Confirm</th>
					<th>Publish Path</th>
					<s:if test="downloadZipSig == 'yes'">
						<th>Download</th>
					</s:if>
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
						<s:if test="countryRegion == 'us'">
							<td>
							<s:if
								test="regionalDTDVersion == '23'">
								<s:property value="submissionInfoUS23DtlId" />
								<s:set name="subId" value="%{submissionInfoUS23DtlId}"></s:set>
								</s:if>
								<s:else>
									<s:property value="submissionInfoUSDtlId" />
									 <s:set	name="subId" value="%{submissionInfoUSDtlId}"></s:set>
								</s:else>
									
								
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
						<s:elseif test="countryRegion == 'au'">
							<td><s:property value="submissionInfoAUDtlId" /> <s:set
								name="subId" value="%{submissionInfoAUDtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'th'">
							<td><s:property value="submissionInfoTHDtlId" /> <s:set
								name="subId" value="%{submissionInfoTHDtlId}"></s:set></td>
						</s:elseif>
						<s:elseif test="countryRegion == 'ch'">
							<td><s:property value="submissionInfoCHDtlId" /> <s:set
								name="subId" value="%{submissionInfoCHDtlId}"></s:set></td>
						</s:elseif>
						<td><s:property value="currentSeqNumber" /></td>
						<td><s:property value="relatedSeqNo" /></td>
						<s:if test="countryRegion != 'au' && countryRegion != 'th' && countryRegion != 'ca'">
						<td><s:property value="submissionType" /></td>
					</s:if>	
						<td style="text-align: center;"><a title="View Details"
							id="Detail<s:property value="subId"/>"
							onclick="javascript:viewDetails('<s:property value="workspaceId"/>','<s:property value="subId"/>',this.id);return false;">
						<img border="0px" alt="View Details"
							src="images/Common/details.png" height="18px" width="18px">
						</a></td>

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


						<td style="text-align: center;"><s:if
							test="deleted == 'false'">
							<a title="Check Broken Links"
								onclick="javascript:checkBrokenLinks('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="subId"/>');return false;">
							<img border="0px" alt="Check Broken Links"
								src="images/Common/check.png" height="18px" width="18px">
							</a>
						</s:if></td>
						<td><s:if test="deleted == 'false'">
							<div align="center"><a title="Validate"
								onclick="javascript:validate('<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="countryRegion"/>');return false;">
							<img border="0px" alt="Validate" src="images/Common/validate.png"
								height="18px" width="18px"> </a></div>
						</s:if></td>
						
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
						
						<s:if
							test="deleted == 'false' && currentSeqNumber == #workspaceCurrentSeqNumber">
							<td style="text-align: center;"><a title="Recompile"
								href="javascript:void(0);"
								onclick="recompileSubmission('<s:property value="submissionPath"/>'+'/'+'<s:property value="currentSeqNumber"/>','<s:property value="workspaceId"/>','${subId }');">
							<img border="0px" alt="Recompile"
								src="images/Common/recompile.png" height="18px" width="18px">
							</a> <!-- recompileSubmission('submissionPath/currentSeqNumber','workspaceId','submissionId') -->
							</td>

							<td><a title="Confirm"
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
							<s:if test="deleted == 'false' && submissionPath.startsWith('//')">
								<div align="center"><a
									href="file:<s:property value="submissionPath"/>"
									title="<s:property value="submissionPath"/>"> <img
									border="0px" alt="Open" src="images/Common/open.png"
									height="18px" width="18px"> </a></div>
							</s:if> 
							<s:elseif test="deleted == 'false' && submissionPath.charAt(1) == ':'">
									<s:property value="submissionPath" />
							</s:elseif>
						</td>
						
							<s:if test="deleted == 'true' && downloadZipSig == 'yes'">
								<td style="text-align: center;">-</td>
							</s:if>
							<s:else>
						
								<s:if test="downloadZipSig == 'yes'">
								<td>
									<a title="Download"
											onclick="javascript:DownloadZip('<s:property value="workspaceId"/>','<s:property value="submissionPath"/>','<s:property value="currentSeqNumber"/>','<s:property value="subId"/>');">
										Download </a>
								</td>
								</s:if>
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
	
	<div id="popupContact" class="popUpcontactDownload"
		style="width: 300px; height: 120px; margin-bottom: 50px; padding-bottom: 40px;">
	<img alt="Close" title="Close" src="images/Common/Close.png"
		onclick='clsdownload();' class='popupCloseButton closeButtton' />
	<h3>Download Sequence</h3>
		<table align="center">
		
		<tr>
			<td>
				<input type="radio" value="current" name="rd"/>Current Sequence
			</td>
			
		</tr>
		<tr>
			<td>
				 <input type="radio" value="related" name="rd"/>Related Sequence
			</td>
		</tr>
		<tr>
			<td style="padding-top: 20px;">
				<input type="button" class="button" value="Submit" id="Submitbtn" onclick="return CreateZip();"/>
				<input type="button" class="button" value="Cancel" id="Cancelbtn" class='popupCloseButton closeButtton' onclick="clsdownload();" style="margin-left:35px;"/>
			</td>
			
		</tr>
	</table> 
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



