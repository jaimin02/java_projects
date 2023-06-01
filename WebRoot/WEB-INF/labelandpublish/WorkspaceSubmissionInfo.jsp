<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<SCRIPT type="text/javascript">
var tbodyAAN="0";
var tbodyProduct="0";
var tbodyINN="0";
var tbodyProduct_th="0";
var tbodyProprietary_Name="0";
var tbodyDosageForm_ZA="0";
var tbodyInn_ZA="0";
var tbodyApplicationNumber="0";

var countryName="";
	$(document).ready(function() 
	{
		
		if(document.getElementById("countryId") != "-1")
		{ 	
			getAgencies();
		}

		tbodyAAN = $('#tblMultipleAAN >tbody >tr').length;
		tbodyProduct =$('#tblMultipleProduct >tbody >tr').length;
		
		tbodyINN = $('#tblMultipleINN >tbody >tr').length;
		tbodyProduct_th =$('#tblMultipleProduct_th >tbody >tr').length;

		tbodyProprietary_Name=$('#tblMultipleProprietary_Name >tbody >tr').length;
		tbodyDosageForm_ZA=$('#tblMultipleDosageForm_ZA >tbody >tr').length;
		tbodyInn_ZA=$('#tblMultipleInn_ZA >tbody >tr').length;	
		tbodyApplicationNumber=$('#tblMultipleApplicationNumber >tbody >tr').length;
		
	});
	
	var totalMultipleANN=0;
	function addMultipleAAN()
	{

		if($("#aan").val()=="")
		{
			return false;
		}

		totalMultipleANN++;
		var clValue="";
		if(totalMultipleANN%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

	
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleAAN' value='"+$("#aan").val()+"'/></td><td>"+$("#aan").val()+"</td></tr>";
		
		$("#tblMultipleAAN tbody").append(row);
		$("#aan").val("");
		tbodyAAN++;
	}
	var totalMultipleINN=0;
	function addMultipleINN()
	{

		if($("#inn_th").val()=="")
		{
			return false;
		}

		totalMultipleINN++;
		var clValue="";
		if(totalMultipleINN%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

	
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleINN' value='"+$("#inn_th").val()+"'/></td><td>"+$("#inn_th").val()+"</td></tr>";
		
		$("#tblMultipleINN tbody").append(row);
		$("#inn_th").val("");
		tbodyINN++;
	}
	var totalMultipleProduct=0;
	function addMultipleProduce()
	{

		if($("#product_au").val()=="")
		{
			return false;
		}
	
		totalMultipleProduct++;
		var clValue="";
		if(totalMultipleProduct%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

		
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleProduct' value='"+$("#product_au").val()+"'/></td><td>"+$("#product_au").val()+"</td></tr>";
	
		$("#tblMultipleProduct tbody").append(row);
		$("#product_au").val("");
		tbodyProduct++;
	
	}

	var totalMultipleProduct_th=0;
	function addMultipleProduce_th()
	{

		if($("#product_th").val()=="")
		{
			return false;
		}
	
		totalMultipleProduct_th++;
		var clValue="";
		if(totalMultipleProduct_th%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

		
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleProduct_th' value='"+$("#product_th").val()+"'/></td><td>"+$("#product_th").val()+"</td></tr>";
	
		$("#tblMultipleProduct_th tbody").append(row);
		$("#product_th").val("");
		tbodyProduct_th++;
	
	}
	var totalMultipleProprietary_Name=0;
	function addMultipleProprietary_Name()
	{
		

		if($("#proprietary_za").val()=="")
		{
			return false;
		}
	
		totalMultipleProprietary_Name++;
		var clValue="";
		if(totalMultipleProprietary_Name%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

		
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleProprietary_Name' value='"+$("#proprietary_za").val()+"'/></td><td>"+$("#proprietary_za").val()+"</td></tr>";
	
		$("#tblMultipleProprietary_Name tbody").append(row);
		$("#proprietary_za").val("");
		tbodyProprietary_Name++;
	
	}


	var totalMultipleDosageForm_ZA=0;
	function addMultipleDosageForm_ZA()
	{
		

		if($("#dosageForm_za").val()=="")
		{
			return false;
		}
	
		totalMultipleDosageForm_ZA++;
		var clValue="";
		if(totalMultipleDosageForm_ZA%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

		
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleDosageForm_ZA' value='"+$("#dosageForm_za").val()+"'/></td><td>"+$("#dosageForm_za").val()+"</td></tr>";
	
		$("#tblMultipleDosageForm_ZA tbody").append(row);
		$("#dosageForm_za").val("");
		tbodyDosageForm_ZA++;
	
	}


	var totalMultipleInn_ZA=0;
	function addMultipleInn_ZA()
	{

		if($("#inn_za").val()=="")
		{
			return false;
		}

		totalMultipleInn_ZA++;
		var clValue="";
		if(totalMultipleInn_ZA%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

	
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleInn_ZA' value='"+$("#inn_za").val()+"'/></td><td>"+$("#inn_za").val()+"</td></tr>";
		
		$("#tblMultipleInn_ZA tbody").append(row);
		$("#inn_za").val("");
		tbodyInn_ZA++;
	}
	var totalMultipleApplicationNumber=0;
	function addMultipleApplicationNumber()
	{
		

		if($("#applicationNumber").val()=="")
		{
			return false;
		}
	
		totalMultipleApplicationNumber++;
		var clValue="";
		if(totalMultipleApplicationNumber%2==0)
		{
				clValue="odd";
		}
		else
		{
			clValue="even";
		}

		
		var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleApplicationNumber' value='"+$("#applicationNumber").val()+"'/></td><td>"+$("#applicationNumber").val()+"</td></tr>";
	
		$("#tblMultipleApplicationNumber tbody").append(row);
		$("#applicationNumber").val("");
		tbodyApplicationNumber++;
	
	}
	function getAgencies()				
	{			
			
			var countryId = document.getElementById("countryId").value;
			var countryIdArr = countryId.split(',');
			var dtdVersion=document.getElementById("regionalDTDVersion").value;
			if(countryIdArr[1]=="za" ||countryIdArr[1]=="us" || countryIdArr[1]=="gcc" || countryIdArr[1]=="ca" || countryIdArr[1]=="ch" || countryIdArr[1]=="au" || countryIdArr[1]=="th")
			
			{
				dtdVersion="";
			}

			/*if(countryIdArr[1]=="eu")
			{
				dtdVersion="20";	
			}*/
						

					
			$.ajax(
			{			
				url: 'GetCountryAgencies_ex.do?dtdVersion='+dtdVersion+'&selectedCountry=' + countryIdArr[0],
		  		success: function(data) 
		  		{
					
					var opt = "  ";			  		
		    		if(data.length > 0)
			    	{
		    			var agencyArr = data.split(',');
		    			var aName = document.getElementById('agencyName').value;
		    			for(i=0; i<agencyArr.length;i++)
			    		{
		    				var keyValuePair = agencyArr[i];
		    				var keyValuePairArr = keyValuePair.split('::');
		    				var key = keyValuePairArr[0];
		    				var value = keyValuePairArr[1];

		    				if(aName.length > 0 && aName == value)
		    					opt += "<option  selected='selected' value='"+value+"'>"+value+"</option>";
		    				else
		    					opt += "<option value='"+value+"'>"+value+"</option>";				    				
		    			}	
		    			opt = '<select name="agencyName">'+
		    					opt +
	  						  '</select>';
		    			document.getElementById('agencyNameList').innerHTML = opt;


		    			if(countryIdArr[0]!=-1)			
		    				showRegionDiv();
		    		    			
		    		}				    		
		    	}	  		
			});	
			return true;
		}
</SCRIPT>
</head>

<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- Country Region contain value like EU,GCC,US,CANADA etc added for putting condition for different region -->
<!-- <s:property value="CountryRegion"/> -->
<s:if test="workSpaceId == -1">
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
			Please Select Project Name...<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>
</s:if>

<s:else>
	<div id="submissionInfoFormDiv" align="center"><s:form
		action="AddSubmissionInfo" method="post" name="submissionInfoForm"
		id="submissionInfoForm">

		<table border="0" cellspacing="0" width="95%" bordercolor="#EBEBEB"
			class="paddingtable">

			<tr class="headercls" onclick="hide('ProjectDetailBox')">
				<td width="97%">Project Detail</td>
				<td width="3%"><img
					src="<%=request.getContextPath()%>/images/Darrow.gif"></td>
			</tr>

		</table>

		<div id="ProjectDetailBox">
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td width="30%" class="title"><b>Project name</b></td>
				<td width="20%"><font color="#c00000"><b><s:property
					value="project_name" /></b></font></td>
				<td colspan="2"></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Project Type</b></td>
				<td width="20%"><font color="#c00000"><b><s:property
					value="project_type" /></b></font></td>
				<td width="30%" class="title" align="right"><s:if
					test="userName != null">
					Details 
					<s:if test="statusIndi == 'N'">Added By</s:if>
					<s:elseif test="statusIndi == 'E'">Edited By</s:elseif>
					&nbsp;&nbsp;&nbsp;
				</s:if></td>
				<td width="20%"><font color="#c00000"><b> <s:property
					value="userName" /></b></font></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Client Name</b></td>
				<td width="20%"><font color="#c00000"><b><s:property
					value="client_name" /></b></font></td>
				<td width="30%" class="title" align="right"><s:if
					test="userName != null">
					Details 
					<s:if test="statusIndi == 'N'">Added On</s:if>
					<s:elseif test="statusIndi == 'E'">Edited On</s:elseif>
					&nbsp;&nbsp;&nbsp;
				</s:if></td>
				<td width="20%"><font color="#c00000"><b> <s:date
					name="modifyDate" format="MMM-dd-yyyy" /> </b></font></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Region Name</b></td>
				<td width="20%"><font color="#c00000"><b><s:property
					value="location_name" /> </b></font></td>
				<td width="30%" class="title"><b>Template Name</b></td>
				<td width="20%"><font color="#c00000"><b><s:property
					value="template_name" /> </b></font></td>
			</tr>

		</table>
		</div>

		<br>

		<div id="GenericdetailBox">
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td class="title" width="30%"><b>Country </b></td>
				<td width="70%"><s:select list="getCountryDetail"
					name="countryId" id="countryId" headerKey="-1"
					headerValue="Select Country Name" listKey="countryId+','+regionId"
					listValue="countryName" onchange="getAgencies();showRegionDiv();"
					ondblclick="showRegionDiv();">
				</s:select></td>
			</tr>

			<!-- --------------------------------------------------------- -->
			<tr id="euRegionalRow"
				<s:if test="location_name != 'EU'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'EU'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="getAgencies();showRegionDiv();">
						<s:if test="regionalDTDVersion == '13'">
							<option value="13" selected="selected">1.3</option>
							<option value="301">3.01</option>
							<option value="20">2.0</option>
							<option value="14">1.4</option>
						</s:if>
						<s:elseif test="regionalDTDVersion == '14'">
							<option value="14" selected="selected">1.4</option>
							<option value="301">3.01</option>
							<option value="20">2.0</option>
							<option value="13">1.3</option>
						</s:elseif>
						<s:elseif test="regionalDTDVersion == '20'">
							<option value="20" selected="selected">2.0</option>
							<option value="301">3.01</option>
							<option value="14">1.4</option>
							<option value="13">1.3</option>
						</s:elseif>
						<s:elseif test="regionalDTDVersion == '301'">
							<option value="301" selected="selected">3.01</option>
							<option value="20">2.0</option>
							<option value="14">1.4</option>
							<option value="13">1.3</option>
						</s:elseif>
						<s:else>
							<option value="301">3.01</option>
							<option value="20">2.0</option>
							<option value="14">1.4</option>
							<option value="13">1.3</option>
						</s:else>
						
						<!--<s:if test="regionalDTDVersion == '14'">
							<option value="14">2.0</option>
						</s:if>
						<s:elseif test="regionalDTDVersion == '20'">
							<option value="20">2.0</option>
						</s:elseif>
						<s:elseif test="regionalDTDVersion == '301'">
							<option value="301">3.01</option>
						</s:elseif>
						<s:else>
							<option value="301">3.01</option>
							<option value="20">2.0</option>
							<option value="14">1.4</option>
							<option value="">1.3</option>
						</s:else>-->


					</select>
				</s:if></td>
			</tr>
			<!-- below lines for gcc -->

			<tr id="gccRegionalRow"
				<s:if test="location_name != 'GCC'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'GCC'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						<s:if test="regionalDTDVersion == '12'">
							<option value="12">1.2</option>
						</s:if>
						<s:elseif test="regionalDTDVersion == '15'">
							<option value="15">1.5</option>
						</s:elseif>
						<s:else>
							<option value="12">1.2</option>
							<option value="15">1.5</option>
						</s:else>
					</select>
				</s:if></td>
			</tr>

			<tr id="zaRegionalRow"
				<s:if test="location_name != 'ZA'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'ZA'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						
						<s:if test="regionalDTDVersion == '10'">
							<option value="10" selected="selected">1.0</option>
							<option value="20">2.0</option>
						</s:if>
						<s:elseif test="regionalDTDVersion == '20'">
							<option value="10">1.0</option>
							<option value="20" selected="selected">2.0</option>
						</s:elseif>
						<s:else>
							<option value="10">1.0</option>
							<option value="20">2.0</option>
						</s:else>
					</select>
				</s:if></td>
			</tr>
			<tr id="chRegionalRow"
				<s:if test="location_name != 'CH'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'CH'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						<option value="13">1.3</option>
					</select>
				</s:if></td>
			</tr>
			<tr id="caRegionalRow"
				<s:if test="location_name != 'CA'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'CA'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						<option value="22">2.2</option>
					</select>
				</s:if></td>
			</tr>
			<tr id="auRegionalRow"
				<s:if test="location_name != 'AU'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'AU'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						<option value="30">3.0</option>
					</select>
				</s:if></td>
			</tr>
			<tr id="thRegionalRow"
				<s:if test="location_name != 'TH'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'TH'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						<option value="10">1.0</option>
					</select>
				</s:if></td>
			</tr>
			<tr id="usRegionalRow"
				<s:if test="location_name != 'US'">style="display: none;"</s:if>>
				<td class="title" width="30%">Regional Version</td>
				<td><s:if test="location_name == 'US'">
					<select name="regionalDTDVersion" id="regionalDTDVersion"
						onchange="showRegionDiv();">
						<s:if test="regionalDTDVersion == ''">
							<option value="21">2.01</option>
						</s:if>
						<s:elseif test="regionalDTDVersion == '23'">
							<option value="23">2.3</option>
						</s:elseif>
						<s:else>
							<option value="21">2.01</option>
							<option value="23">2.3(New)</option>
						</s:else>
						
					</select>
				</s:if></td>
			</tr>


			<!-- gcc completed -->
			<!-- --------------------------------------------------------- -->
			<tr>
				<td width="30%" class="title"><b>Agency Name</b></td>
				<td width="70%">	
				<div id="agencyNameList"><SELECT name="agencyName">
					<option value="-1">Select Agency Name</option>
				</SELECT></div>
				<s:hidden value="%{agencyName}" id="agencyName"></s:hidden></td>

			</tr>

			<tr>
				<td width="30%" class="title" id="appNoTd"><s:if
					test="location_name == 'EU' && (regionalDTDVersion == '14' || regionalDTDVersion == '20' || regionalDTDVersion == '301' )">
					Tracking Number
				</s:if> <s:elseif test="location_name == 'GCC'">
					Tracking Number
				</s:elseif>
				<s:elseif test="location_name == 'AU' || location_name == 'TH' ">
					eSubmissionId
				</s:elseif> 
				<s:elseif test="location_name == 'CA'">
					Dossier identifier
				</s:elseif> 
				 <s:else>
					Application Number
				</s:else></td>
				
				<s:if test="location_name == 'ZA'">
					<td width="70%"><input type="text" name="applicationNumber" id="applicationNumber" />&nbsp;<input type="button" value="Add" id="addApplicationNumber" class="button"
						onclick="addMultipleApplicationNumber();" )/></td>
				</s:if>
				<s:else>
					<td width="70%"><s:textfield name="applicationNumber"></s:textfield></td>
				</s:else>
					
			</tr>
			<s:if test="location_name == 'ZA'">
				<tr>
					<td></td>
					<td>
						<table width="30%" id="tblMultipleApplicationNumber" class="datatable">
							<thead>
								<tr>
									<th>#</th>
									<th>Application Number</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="ApplicationNumber_List" >
									<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleApplicationNumber' value='<s:property />'/></td><td><s:property /></td></tr>
								</s:iterator>
							</tbody>
	
						</table>
					</td>
							
				</tr>
		  </s:if>
		</table>
		</div>
		<br>

		<div id="detailBox_eu"
			<s:if test="location_name != 'EU'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000">

				<li>Specify following details for EU-Regional</li>
				</font> </u> </b></td>
			</tr>
			<tr id="UUID"
				<s:if test="regionalDTDVersion != '14' && regionalDTDVersion != '20' && regionalDTDVersion != '301'" >style="display: none;"</s:if>>
				<td width="30%" class="title"><b>UUID</b></td>
				<td width="70%"><s:textfield name="uuid"></s:textfield></td>
			</tr>
			<tr id="highLvlNo"
				<s:if test="regionalDTDVersion != '14' && regionalDTDVersion != '20' && regionalDTDVersion != '301' ">style="display: none;"</s:if>>
				<td width="30%" class="title"><b>High Level Number</b></td>
				<td width="70%"><s:textfield name="highLvlNo"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Applicant</b></td>
				<td width="70%"><s:textfield name="applicant"></s:textfield></td>
			</tr>

			<tr id="atcRow"
				<s:if test="regionalDTDVersion == '14' || regionalDTDVersion == '20'">style="display: none;"</s:if>>
				<td width="30%" class="title"><b>ATC</b></td>
				<td width="70%"><s:textfield name="atc"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Procedure Type</b></td>
				<td width="70%"><SELECT name="procedureType"
					onchange="showHideCMSLink();">
					<s:if test="procedureType == '' && procedureType == null">
						<option value="-1">Select Procedure Type</option>
					</s:if>

					<option value="centralised"
						<s:if test="procedureType != '' && procedureType != null &&  procedureType == 'centralised'"> selected="selected"</s:if>>centralised</option>
					<option value="national"
						<s:if test="procedureType != '' && procedureType != null &&  procedureType == 'national'"> selected="selected"</s:if>>national</option>
					<option value="mutual-recognition"
						<s:if test="procedureType != '' && procedureType != null &&  procedureType == 'mutual-recognition'"> selected="selected"</s:if>>mutual-recognition</option>
					<option value="decentralised"
						<s:if test="procedureType != '' && procedureType != null &&  procedureType == 'decentralised'"> selected="selected"</s:if>>decentralised</option>
				</SELECT> <s:if
					test="procedureType == 'mutual-recognition' || procedureType == 'decentralised'">
					&nbsp;<a href="javascript:void(0);" id="CMSLinkId"
						onclick="openCMSWindow('${workSpaceId}');">CMS Details</a>
				</s:if> <s:else>
					&nbsp;<a href="javascript:void(0);" id="CMSLinkId"
						onclick="openCMSWindow('${workSpaceId}');"
						style="visibility: hidden;">CMS Details</a>
				</s:else></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Invented Name</b></td>
				<td width="70%"><s:textfield name="inventedName"></s:textfield>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>INN</b></td>
				<td width="70%"><s:textfield name="inn"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Submission Description</b></td>
				<td width="70%"><s:textfield name="submissionDescription"></s:textfield>
				</td>
			</tr>

		</table>
		</div>

		<div id="detailBox_za"
			<s:if test="location_name != 'ZA'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000">

				<li>Specify following details for ZA-Regional</li>
				</font> </u> </b></td>
			</tr>


			<tr>
				<td width="30%" class="title"><b>Applicant</b></td>
				<td width="70%"><s:textfield name="applicant_za"></s:textfield></td>
			</tr>
			
			<tr>
				<td width="30%" class="title"><b>Proprietary Name</b></td>
				<td width="70%"><input type="text" name="proprietary_za" id="proprietary_za" />&nbsp;<input type="button" value="Add" id="addProprietary_Name" class="button"
						onclick="addMultipleProprietary_Name();" )/></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table width="30%" id="tblMultipleProprietary_Name" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<th>Proprietary Name</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="Proprietary_Name_List" >
								<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleProprietary_Name' value='<s:property />'/></td><td><s:property /></td></tr>
							</s:iterator>
						</tbody>

					</table>
				</td>
						
			</tr>
			
			
			<tr>
				<td width="30%" class="title"><b>Dosage Form</b></td>
				<td width="70%"><input type="text" name="dosageForm_za" id="dosageForm_za" />&nbsp;<input type="button" value="Add" id="addDosageForm_ZA" class="button"
						onclick="addMultipleDosageForm_ZA();" )/></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table width="30%" id="tblMultipleDosageForm_ZA" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<th>Dosage Name</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="DosageForm_ZA_List" >
								<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleDosageForm_ZA' value='<s:property />'/></td><td><s:property /></td></tr>
							</s:iterator>
						</tbody>

					</table>
				</td>
						
			</tr>
			
			
			<tr>
				<td width="30%" class="title"><b>INN</b></td>
				<td width="70%"><input type="text" name="inn_za" id="inn_za" />&nbsp;<input type="button" value="Add" id="addInn_ZA" class="button"
						onclick="addMultipleInn_ZA();" )/></td>	
			</tr>
			
			<tr>
				<td></td>
				
				
				<td>
					<table width="30%" id="tblMultipleInn_ZA" class="datatable" >
						<thead>
							<tr>
								<th>#</th>
								<th>INN</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="Inn_ZA_List" >
    							
    							<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleInn_ZA' value='<s:property />'/></td><td><s:property /></td></tr>
   							</s:iterator>
						</tbody>

					</table>
				</td>	
			</tr>
			
			<tr>
				<td width="30%" class="title"><b>Submission Description</b></td>
				<td width="70%"><s:textfield name="submissionDescription_za"></s:textfield>
				</td>
			</tr>

		</table>
		</div>
		
		<div id="detailBox_au"
			<s:if test="location_name != 'AU'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000">

				<li>Specify following details for AU-Regional</li>
				</font> </u> </b></td>
			</tr>


			<tr>
				<td width="30%" class="title"><b>Applicant</b></td>
				<td width="70%"><s:textfield name="applicant_au"></s:textfield></td>
			</tr>
			<tr>
				
				<td class="title" width="30%"><b>Regulatory Activity Lead</b></td>
					<td width="70%"><s:select list="getRegulatoryLead"
						name="regactlead" id="regactlead" headerKey="-1"
						headerValue="Select Regulatory activity Lead" listKey="regActLeadCode"
						listValue="regActLeadDescription">
				</s:select></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>AAN</b></td>
				<td width="70%"><input type="text" name="aan" id="aan" />&nbsp;<input type="button" value="Add" id="addAAN" class="button"
						onclick="addMultipleAAN();" )/></td>	
			</tr>
			
			<tr>
				<td></td>
				
				
				<td>
					<table width="30%" id="tblMultipleAAN" class="datatable" >
						<thead>
							<tr>
								<th>#</th>
								<th>AAN</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="AAN_List" >
    							
    							<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleAAN' value='<s:property />'/></td><td><s:property /></td></tr>
   							</s:iterator>
						</tbody>

					</table>
				</td>	
			</tr>
			
			
			<tr>
				<td width="30%" class="title"><b>Product Name</b></td>
				<td width="70%"><input type="text" name="product_au" id="product_au" />&nbsp;<input type="button" value="Add" id="addProduct" class="button"
						onclick="addMultipleProduce();" )/></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table width="30%" id="tblMultipleProduct" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<th>Product Name</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="Product_List" >
								<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleProduct' value='<s:property />'/></td><td><s:property /></td></tr>
							</s:iterator>
						</tbody>

					</table>
				</td>
						
			</tr>
		</table>
		</div>
		
		
		<div id="detailBox_th"
			<s:if test="location_name != 'TH'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000">

				<li>Specify following details for TH-Regional</li>
				</font> </u> </b></td>
			</tr>


			<tr>
				<td width="30%" class="title"><b>Licensee</b></td>
				<td width="70%"><s:textfield name="licensee" id="licensee"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Licensee Name</b></td>
				<td width="70%"><s:textfield name="licensee_Name" id="licensee_Name"></s:textfield></td>
			</tr>
			<tr>
				
				<td class="title" width="30%"><b>Licensee Type</b></td>
				<td width="70%">
				<select name="licensee_Type" id="licensee_Type">

						<s:if test="licensee_Type == 'Importer'">
							<option value="Importer">Importer</option>
						</s:if>
						<s:elseif test="licensee_Type == 'Manufacturer'">
							<option value="Manufacturer">Manufacturer</option>
						</s:elseif>
						<s:else>
							<option value="-1">Select Licensee Type</option>
							<option value="Importer">Importer</option>
							<option value="Manufacturer">Manufacturer</option>
							
						</s:else>


					</select>
				
				
				</td>	
			</tr>
			<tr>
				
				<td class="title" width="30%"><b>Regulatory Activity Lead</b></td>
				<td width="70%">
				<select name="regactlead_th" id="regactlead_th">

						<s:if test="regactlead_th == 'Biologicals'">
							<option value="Biologicals">Biologicals Product Review</option>
						</s:if>
						<s:elseif test="regactlead_th == 'Pharmaceuticals'">
							<option value="Pharmaceuticals">Pharmaceuticals Product Review</option>
						</s:elseif>
						<s:elseif test="regactlead_th == 'Pharmacovigilance'">
							<option value="Pharmacovigilance">Pharmacovigilance Review</option>
						</s:elseif>
						<s:elseif test="regactlead_th == 'Cosmetic'">
							<option value="Cosmetic">Cosmetic Review</option>
						</s:elseif>
						<s:elseif test="regactlead_th == 'Medical-Devices'">
							<option value="Medical-Devices">Medical Device Review</option>
						</s:elseif>
						<s:else>
							<option value="-1">Select Regulatory Activity Lead</option>
							<option value="Biologicals">Biologicals Product Review</option>
							<option value="Pharmaceuticals">Pharmaceuticals Product Review</option>
							<option value="Pharmacovigilance">Pharmacovigilance Review</option>
							<option value="Cosmetic">Cosmetic Review</option>
							<option value="Medical-Devices">Medical Device Review</option>
						</s:else>


					</select>
				
				
				</td>	
			</tr>
			<tr>
				<td width="30%" class="title"><b>INN</b></td>
				<td width="70%"><input type="text" name="inn_th" id="inn_th" />&nbsp;<input type="button" value="Add" id="addINN" class="button"
						onclick="addMultipleINN();" )/></td>	
			</tr>
			
			<tr>
				<td></td>
				
				
				<td>
					<table width="30%" id="tblMultipleINN" class="datatable" >
						<thead>
							<tr>
								<th>#</th>
								<th>INN</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="INN_List" >
    							
    							<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleINN' value='<s:property />'/></td><td><s:property /></td></tr>
   							</s:iterator>
						</tbody>

					</table>
				</td>	
			</tr>
			
			
			<tr>
				<td width="30%" class="title"><b>Product Name</b></td>
				<td width="70%"><input type="text" name="product_th" id="product_th" />&nbsp;<input type="button" value="Add" id="addProduct_th" class="button"
						onclick="addMultipleProduce_th();" )/></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table width="30%" id="tblMultipleProduct_th" class="datatable">
						<thead>
							<tr>
								<th>#</th>
								<th>Product Name</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="Product_List_th" >
								<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleProduct_th' value='<s:property />'/></td><td><s:property /></td></tr>
							</s:iterator>
						</tbody>

					</table>
				</td>
						
			</tr>
		</table>
		</div>
		
		<div id="detailBox_ch"
			<s:if test="location_name != 'CH'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000">

				<li>Specify following details for CH-Regional</li>
				</font> </u> </b></td>
			</tr>


			<tr>
				<td width="20%" class="title"><b>Applicant</b></td>
				<td width="80%"><s:textfield name="applicant_ch"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>DMF Number</b></td>
				<td width="70%"><s:textfield name="dmfNumber_ch"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>DMF Holder</b></td>
				<td width="70%"><s:textfield name="dmfHolder_ch"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>PMF Number</b></td>
				<td width="70%"><s:textfield name="pmfNumber_ch"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>PMF Holder</b></td>
				<td width="70%"><s:textfield name="pmfHolder_ch"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Invented Name</b></td>
				<td width="70%"><s:textfield name="inventedName_ch"></s:textfield>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>INN</b></td>
				<td width="70%"><s:textfield name="inn_ch"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Submission Description</b></td>
				<td width="70%"><s:textfield name="submissionDescription_ch"></s:textfield>
				</td>
			</tr>

		</table>
		</div>
		<div id="detailBox_gcc"
			<s:if test="CountryRegion != 'gcc'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000">

				<li>Specify following details for GCC-Regional</li>
				</font> </u> </b></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>High Level Number</b></td>
				<td width="70%"><s:textfield name="highLvlNo_gcc"></s:textfield>
				</td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>ATC</b></td>
				<td width="70%"><s:textfield name="atc_gcc"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Applicant</b></td>
				<td width="70%"><s:textfield name="applicant_gcc"></s:textfield>
				</td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Procedure Type</b></td>
				<td width="70%"><SELECT name="procedureType_gcc"
					onchange="showHideCMSLink();">
					<s:if test="procedureType == '' && procedureType == null">
						<option value="-1">Select Procedure Type</option>
					</s:if>


					<option value="national"
						<s:if test="procedureType_gcc != '' && procedureType_gcc != null &&  procedureType_gcc == 'national'"> selected="selected"</s:if>>national</option>

					<option value="gcc"
						<s:if test="procedureType_gcc != '' && procedureType_gcc != null &&  procedureType_gcc == 'gcc'"> selected="selected"</s:if>>gcc</option>
				</SELECT> <s:if test="procedureType_gcc == 'gcc'">
					&nbsp;<a href="javascript:void(0);" id="CMSLinkId_gcc"
						onclick="openCMSWindow('${workSpaceId}');">CMS Details</a>
				</s:if> <s:else>
					&nbsp;<a href="javascript:void(0);" id="CMSLinkId_gcc"
						onclick="openCMSWindow('${workSpaceId}');"
						style="visibility: hidden;">CMS Details</a>
				</s:else></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Invented Name</b></td>
				<td width="70%"><s:textfield name="inventedName_gcc"></s:textfield>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>INN</b></td>
				<td width="70%"><s:textfield name="inn_gcc"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Submission Description</b></td>
				<td width="70%"><s:textfield name="submissionDescription_gcc"></s:textfield>
				</td>
			</tr>



		</table>
		</div>
		<div id="detailBox_us"
			<s:if test="CountryRegion != 'us'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">

			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000"> Specify following details for US-Regional </font>
				</u> </b></td>
			</tr>

			<tr id="tr_applicationId">
				<td width="30%" class="title"><b>Application Id</b></td>
				<td width="70%"><s:textfield name="applicationId" id="applicationId"
					value="%{applicationId}"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Company Name</b></td>
				<td width="70%"><s:textfield name="companyName"
					value="%{companyName}"></s:textfield></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Product Name</b></td>
				<td width="70%"><s:textfield name="prodName"
					value="%{prodName}"></s:textfield></td>
			</tr>
			
			<tr>
				<td width="30%" class="title"><b>Product Type</b></td>
				<td width="70%"><select name="productType">
					<s:if test="productType != null && productType != ''">
						<option value="-1">Select Product Type</option>
					</s:if>

					<option value="established"
						<s:if test="productType != '' && productType != null &&  productType == 'established'"> selected="selected"</s:if>>established</option>
					<option value="proprietary"
						<s:if test="productType != '' && productType != null &&  productType == 'proprietary'"> selected="selected"</s:if>>proprietary</option>
					<option value="chemical"
						<s:if test="productType != '' && productType != null &&  productType == 'chemical'"> selected="selected"</s:if>>chemical</option>
					<option value="code"
						<s:if test="productType != '' && productType != null &&  productType == 'code'"> selected="selected"</s:if>>code</option>
				</select></td>
				
				
				
			</tr>
			<s:if test="getApplictionTypes != null">
				<tr id="tr_applicationType_23">
					<td class="title" width="30%"><b>Application Type </b></td>
					<td width="70%"><s:select list="getApplictionTypes"
						name="applicationType_23" id="applicationType_23" headerKey="-1"
						headerValue="Select Application Type" listKey="applicationTypeCode"
						listValue="applicationTypeDisplay">
					</s:select></td>
				</tr>
			</s:if>
			
			<tr id="tr_submissionId_US">
				<td width="30%" class="title"><b>Submission Id</b></td>
				<td width="70%"><s:textfield name="submissionId"
					value="%{submissionId}"></s:textfield></td>
			</tr>
			
			<tr id="tr_groupSubmission_US">
				<td width="30%" class="title"><b>Is Group Submission</b></td>
				<td width="70%">
				<SELECT name="isGroupSub"
					onchange="showHideWorkspaceApplicationDetailLink();">
					<option value="No"
						<s:if test="isGroupSub != '' && isGroupSub != null &&  isGroupSub == 'No'"> selected="selected"</s:if>>No</option>
					<option value="Yes"
						<s:if test="isGroupSub != '' && isGroupSub != null &&  isGroupSub == 'Yes'"> selected="selected"</s:if>>Yes</option>
					
				</SELECT> 
				
				<s:if
					test="isGroupSub == 'Yes' ">
					&nbsp;<a href="javascript:void(0);" id="LinkId"
						onclick="openWorkspaceApplicationDetail('${workSpaceId}');">Add Application Details</a>
				</s:if>
				 <s:else>
					&nbsp;<a href="javascript:void(0);" id="LinkId"
						onclick="openWorkspaceApplicationDetail('${workSpaceId}');"
						style="visibility: hidden;">Add Application Details</a>
				</s:else>
				</td>
			</tr>
			

			<s:if test="applicationType_23==null">
			<tr id="tr_applicationType_21">
				<td width="30%" class="title"><b>Application Type</b></td>
				<td width="70%"><select name="applicationType">
					<s:if test="applicationType != null && applicationType != ''">
						<option value="-1">Select Application Type</option>
					</s:if>

					<option value="nda"
						<s:if test="applicationType != '' && applicationType != null &&  applicationType == 'nda'"> selected="selected"</s:if>>nda</option>
					<option value="anda"
						<s:if test="applicationType != '' && applicationType != null &&  applicationType == 'anda'"> selected="selected"</s:if>>anda</option>
					<option value="bla"
						<s:if test="applicationType != '' && applicationType != null &&  applicationType == 'bla'"> selected="selected"</s:if>>bla</option>
					<option value="dmf"
						<s:if test="applicationType != '' && applicationType != null &&  applicationType == 'dmf'"> selected="selected"</s:if>>dmf</option>
					<option value="ind"
						<s:if test="applicationType != '' && applicationType != null &&  applicationType == 'ind'"> selected="selected"</s:if>>ind</option>
					<option value="master-file"
						<s:if test="applicationType != '' && applicationType != null &&  applicationType == 'master-file'"> selected="selected"</s:if>>master-file</option>
				</select></td>
			</tr>
			
			</s:if>
			
			
			<tr id="tr_submissionDescription_US">
				<td width="30%" class="title"><b>Submission Description</b></td>
				<td width="70%"><s:textfield name="submissionDescription_US" id="submissionDescription_US"></s:textfield></td>
			</tr>
		</table>
		</div>

		<div id="detailBox_ca"
			<s:if test="CountryRegion != 'ca'">style="display: none;"</s:if>>
		<table cellpadding="2" class="paddingtable" width="95%"
			style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">

			<tr>
				<td align="center" colspan="2"><b> <u> <font
					color="#c00000"> Specify following details for CA-Regional </font>
				</u> </b></td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Product Name</b></td>
				<td width="70%"><s:textfield name="prodName_ca"></s:textfield>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Applicant</b></td>
				<td width="70%"><s:textfield name="applicant_ca"></s:textfield>
				</td>
			</tr>
			
			<tr>
				
				<td class="title" width="30%"><b>Dossier Type</b></td>
				<td width="70%">
				<select name="dossier_type" id="dossier_type">

						<s:if test="dossier_type == 'Biologic Dossier'">
							<option value="Biologic Dossier">Biologic Dossier</option>
						</s:if>
						<s:elseif test="dossier_type == 'Pharmaceutical Dossier'">
							<option value="Pharmaceutical Dossier">Pharmaceutical Dossier</option>
						</s:elseif>
						<s:elseif test="dossier_type == 'Drug Master File Dossier'">
							<option value="Drug Master File Dossier">Drug Master File Dossier</option>
						</s:elseif>
						
						<s:else>
							<option value="-1">Select dossier type</option>
							<option value="Biologic Dossier">Biologic Dossier</option>
							<option value="Pharmaceutical Dossier">Pharmaceutical Dossier</option>
							<option value="Drug Master File Dossier">Drug Master File Dossier</option>
							
						</s:else>


					</select>
				
				
				</td>	
			</tr>
			
			<tr>
				
				<td class="title" width="30%"><b>Regulatory Activity Lead</b></td>
				<td width="70%">
				<select name="regactlead_ca" id="regactlead_ca">

						<s:if test="regactlead_ca == 'Pharmaceutical'">
							<option value="Pharmaceutical">Pharmaceutical</option>
						</s:if>
						<s:elseif test="regactlead_ca == 'Biological'">
							<option value="Biological">Biological</option>
						</s:elseif>
						<s:elseif test="regactlead_ca == 'Post-Market Pharmacovigilance'">
							<option value="Post-Market Pharmacovigilance">Post-Market Pharmacovigilance</option>
						</s:elseif>
						<s:elseif test="regactlead_ca == 'Drug Master File'">
							<option value="Drug Master File">Drug Master File</option>
						</s:elseif>
						<s:else>
							<option value="-1">Select Regulatory Activity Lead</option>
							<option value="Pharmaceutical">Pharmaceutical</option>
							<option value="Biological">Biological</option>
							<option value="Post-Market Pharmacovigilance">Post-Market Pharmacovigilance</option>
							<option value="Drug Master File">Drug Master File</option>
						</s:else>


					</select>
				
				
				</td>	
			</tr>

		</table>
		</div>

		<br>

		<input type="submit" name="submitBtn" class="button"
			value="Save Submission Details" onclick="return validate();">


		<s:hidden name="workSpaceId">
		</s:hidden>
		<s:hidden name="CountryRegion" value=""></s:hidden>
	</s:form></div>

	<div id="msgDiv" style="display: none;"></div>

</s:else>

</body>
</html>
