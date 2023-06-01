<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<s:head theme="ajax" />
<script language="javascript">

	function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none';
			else
				document.getElementById(str).style.display = 'inline';
		}	
		
	function selectAttributes()
	{
		var mode = document.forms['ProjectLevelSearchForm'].searchMode.value;
		if(mode == 'General_Search'){
			
			document.getElementById('trDossierStatus').style.display = '';
			document.getElementById('trRegion').style.display = '';
			document.getElementById('trGenericName').style.display = '';
			document.getElementById('trSerialNo').style.display = 'none';
		
			var dossierStatusVal = document.forms['ProjectLevelSearchForm'].dossierStatus.value;
			
			if(dossierStatusVal == 'Products Registered')
			{
				var attrArr = ['Agent','Applicant','Brand Name','Date of Expiry','Date of Registration',
								'Date of Submission','ETD of Registration','Manufacturer','Pack Size',
								'Registration No','Renewal Expiry Date','Renewal Registration Date',
								'Renewal Registration No','Re-Registration Decision','Site','Serial NO',
								'Type of Registration','Total Cost','Customer'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Dossiers Submitted')
			{
				var attrArr = ['Agent','Applicant','Brand Name','Date of NOD','Date of Submission','Dossier Submission Status',
								'Dossier Status','ETD of Registration','ETD of Submission','Manufacturer','Pack Size','Site',
								'Date of NOD-1','Deadline of NOD-1','Compliance of NOD-1','Date of NOD-2','Deadline of NOD-2',
								'Compliance of NOD-2','Date of NOD-3','Deadline of NOD-3','Compliance of NOD-3',
								'Type of Registration','Re-Registration Status','Total Cost'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Dossier Under Evaluation')
			{
				var attrArr = ['Brand Name',',ManufacturerApplicant','Agent','Pack Size','Site','Type of Registration',
								'Dossier Status','Date of sent for Evaluation','ETD of Evaluation','ETD of Submission',
								'Re-Registration Status','Total Cost'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Dossiers Under Preparation')
			{
				var attrArr = ['Brand Name','Manufacturer','Applicant','Agent','Pack Size','Site','Type of Registration',
								'Dossier Status','Dossier Intimation Date','Dossier Acceptance Date','Dossier Preparation Status',
								'Date of sent for Evaluation'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Pipeline Products')
			{
				var attrArr = ['Brand Name','Manufacturer','Applicant','Agent','Pack Size','Site','Type of Registration',
								'Pipeline Intimation Date','Pipeline Acceptance Date','Pipeline Status'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Products Registration Expired')
			{
				var attrArr = ['Serial NO','Brand Name','Manufacturer','Applicant','Agent','Pack Size','Site',
								'Type of Registration','Date of Registration','Registration No','Date of Expiry',
								'Re-Registration Decision','Re-Registration Status'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Products Under Renewal')
			{
				var attrArr = ['Serial NO','Brand Name','Manufacturer','Applicant','Agent','Pack Size',
								'Site','Type of Registration','Dossier Status','Dossier Intimation Date',
								'Dossier Acceptance Date','Dossier Preparation Status','Date of sent for Evaluation',
								'Date of Submission','Dossier Submission Status','ETD of Registration',
								'Re-Registration Status'];
				updateCheckBoxes(attrArr);
			
			}
			else if(dossierStatusVal == 'Status to be Assigned')
			{
				var attrArr = ['Dossier Status'];
				updateCheckBoxes(attrArr);
			
			}
		}
		else if(mode == 'Serial_Num_Wise_Search')
		{
			document.getElementById('trDossierStatus').style.display = 'none';
			document.getElementById('trRegion').style.display = 'none';
			document.getElementById('trGenericName').style.display = 'none';
			document.getElementById('trSerialNo').style.display = '';
		
			var attrArr = ['Serial NO','Brand Name','Manufacturer','Applicant','Agent','Pack Size',
							'Site','Type of Registration','Dossier Status','Date of Registration','Registration No','Date of Expiry',
							'Re-Registration Status','Renewal Registration Date','Renewal Registration No','Renewal Expiry Date',
							'Total Cost','Customer'];
			updateCheckBoxes(attrArr);
		}
		
		else
		{
			document.getElementById('trDossierStatus').style.display = 'none';
			document.getElementById('trRegion').style.display = 'none';
			document.getElementById('trGenericName').style.display = 'none';
			document.getElementById('trSerialNo').style.display = 'none';
		
		}
		
	}	
	
	function updateCheckBoxes(chkArr)
	{
		var chkboxes = document.forms['ProjectLevelSearchForm'].selectedOutputFields;
			for(i= 0; i < chkboxes.length ; i++)
			{
				for(j= 0; j < chkArr.length ; j++)
				{
					if(chkboxes[i].value == chkArr[j])
					{
						chkboxes[i].checked = 'checked';
						break;
					}
					else
					{
						chkboxes[i].checked = '';
					}
				}
				
			}
	}
	
	function checkAll()
	{
		var chkBox = document.forms['ProjectLevelSearchForm'].selectedOutputFields;
		
		if(document.forms['ProjectLevelSearchForm'].chkBtn.value == 'Check All')
		{
			for(i = 0; i < chkBox.length; i++)
			{
				chkBox[i].checked = 'checked';
			}
			
			document.forms['ProjectLevelSearchForm'].chkBtn.value = 'Uncheck All';
		}
		else
		{
			for(i = 0; i < chkBox.length; i++)
			{
				chkBox[i].checked = '';
			}
			
			document.forms['ProjectLevelSearchForm'].chkBtn.value = 'Check All';
		}
	}
	
	function selectRecord(trObj)
	{
		document.getElementById('');
	}
	
	var PreviousRowColor = '';
	var PreviousRow = '';
	function ChangeRowColor(row) {
		if (PreviousRow != '') {
			PreviousRow.style.backgroundColor = PreviousRowColor;
		}
		PreviousRowColor = row.style.backgroundColor;
		row.style.backgroundColor = '#FFDBA5';
		PreviousRow = row;
    }
   
</script>

<STYLE>
</style>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /> <s:actionmessage /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Search/Project_Level_Search.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="ShowProjectLevelSearchResult"
	name="ProjectLevelSearchForm" method="post">
	<br>

	<table width="100%">
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Search Mode</td>
			<td><select name="searchMode" onchange="selectAttributes();">

				<option value="General_Search">General Search</option>
				<option value="Serial_Num_Wise_Search">Serial No Search</option>
			</select></td>
		</tr>

		<tr id="trDossierStatus" align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Dossier Status</td>
			<td><s:select list="lstDossierStatusValues" name="dossierStatus"
				listKey="attrMatrixValue" listValue="attrMatrixValue" headerKey=""
				headerValue="All Status" onchange="javascript:selectAttributes();">
			</s:select></td>
		</tr>


		<tr id="trRegion" align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Country/Region</td>
			<td><s:select list="lstCountry" name="location" headerKey=""
				headerValue="All Countries" listKey="locationName"
				listValue="locationName">
			</s:select></td>
		</tr>


		<tr id="trGenericName" align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Generic Name</td>

			<td><s:autocompleter list="lstProduct" name="genericName"
				listKey="top" listValue="top" autoComplete="false"
				cssStyle="margin: 0; padding: 2px; width:400px;"
				dropdownHeight="145" loadOnTextChange="false" resultsLimit="all"
				forceValidOption="true">
			</s:autocompleter></td>


		</tr>
		<tr id="trSerialNo" style="display: none;" align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Serial No</td>

			<td><!-- <s:select 
				  					list="lstSerialNo"
				  					name="serialNo"
				  					headerKey=""
				  					headerValue="Select Serial No"
				  					listKey="attrValue"
				  					listValue="attrValue">
				  				</s:select> --> <!--<s:textfield name="serialNo" cssStyle="padding-left: 3px;"></s:textfield>-->

			<s:autocompleter list="lstSerialNo" name="serialNo"
				listKey="attrValue" listValue="attrValue" autoComplete="false"
				cssStyle="margin: 0; padding: 2px; width:200px;"
				dropdownHeight="145" loadOnTextChange="false" resultsLimit="all"
				forceValidOption="true">
			</s:autocompleter></td>


		</tr>

		<tr align="left">
			<td>&nbsp;</td>
		</tr>
		<tr id="trOutputFields" align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Output Fields</td>
			<td colspan="2"><input type="button" name="chkBtn"
				value="Check All" class="button" onclick="checkAll();"></td>
		</tr>
		<tr id="trAttributes" align="center" valign="middle">
			<td colspan="3" valign="middle">
			<div align="center"
				style="height: 153px; width: 630px; overflow-y: auto; border: solid 1px; padding-left: 2px;">
			<table border="1px;" style="color: black;" width="100%">
				<s:iterator value="projectLevelAttributes" status="status">
					<s:if test="#status.count % 3 == 1">
						<tr align="left" valign="middle">
					</s:if>
					<td style="padding-left: 6px;" valign="middle"><input
						type="checkbox" name="selectedOutputFields"
						value="<s:property value="attrName"/>"> <s:property
						value="attrName" /></td>
					<s:if test="#status.count % 3 == 0">
						</tr>
					</s:if>
				</s:iterator>
			</table>
			</div>
			</td>
		</tr>

	</table>
	<br>
	<s:submit value=" Search " cssClass="button" theme="ajax"
		targets="ProjectLevelSearchResultDiv" align="center">
	</s:submit>
</s:form> <br>
<div id="ProjectLevelSearchResultDiv" align="center"></div>

</div>
</div>



</div>
</body>
</html>
