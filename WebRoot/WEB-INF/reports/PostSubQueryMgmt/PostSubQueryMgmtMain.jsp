<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head theme="ajax" />
<STYLE type="text/css">
.projectNames {
	display: none;
}

.statusNames {
	display: none;
}

.timeBase {
	display: none;
}

.submitBtnDiv {
	display: none;
}
</STYLE>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<SCRIPT type="text/javascript">
			$(document).ready(function() { 
				$(".dateValue").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
				//Setting for Current quater value set in datepicker control.. --Start Here
				var d = new Date();
				var quarter = Math.floor((d.getMonth() / 3));
		      	var firstDate = new Date(d.getFullYear(), quarter * 3, 1);
		      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
		      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 3, 0));
				//--End Here
			
				//For Report Type
				$('#reportType').change(function(){
					$("#SubQueryReport").html("");
					var reportType =$(this).val();
					displayNone();
					if(reportType == '-1')
					{
						displayNone();
					}
					if(reportType == 'StatusWR')
					{
						$('.timeBase').slideUp('fast');
						$('.projectNames').slideDown('slow');
						$('.statusNames').slideDown('slow');
						$('.submitBtnDiv').slideDown('slow');
					}
					if(reportType == 'TimeWR')
					{
						$('.statusNames').slideUp('fast');
						$('.projectNames').slideDown('slow');
						$('.timeBase').slideDown('slow');
						$('.submitBtnDiv').slideDown('slow');
					}
				});
				//For Time selection
				$('#durationType').change(function() {
					var id = this.value;
					//alert(id);
					d = new Date();
					//d = new Date('2010','00','03');
					//alert(d);
				 	switch (id) 
				 	{
					    case "CQ":
				    		var quarter = Math.floor((d.getMonth() / 3));
					      	var firstDate = new Date(d.getFullYear(), quarter * 3, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 3, 0));
					    break;
					    case "CH":
					    	var half = Math.floor((d.getMonth() / 6));
					      	var firstDate = new Date(d.getFullYear(), half * 6, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 6, 0));
					    break;
					    case "CY":
					    	var year = Math.floor((d.getMonth() / 12));
					      	var firstDate = new Date(d.getFullYear(), year * 12, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 12, 0));
					    break;
					    case "LQ":
					    	var quarter = Math.floor((d.getMonth() / 3));
					      	var firstDate = new Date(d.getFullYear(), quarter * 3 - 3, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
						  	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 3, 0));
					    break;
					    case "LH":
					    	var half = Math.floor((d.getMonth() / 6));
					      	var firstDate = new Date(d.getFullYear(), half * 6 - 6, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 6, 0));
					    break;
					    case "LY":
					    	var year = Math.floor((d.getMonth() / 12));
					      	var firstDate = new Date(d.getFullYear(), year * 12 - 12, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 12, 0));
					    break;
				    }
				});
				//For Click the Search button
				$('#subBtn').click(function(){
					var reportType =$('#reportType').val();
					var workspaceId =$('#workspaceId').val();
					var queryString="?workSpaceId="+workspaceId;
					if(reportType == 'StatusWR')
					{
						var statusVal =$('#statusDDL').val();
						queryString+="&status="+statusVal;
						getData(queryString);
					} 

					if(reportType == 'TimeWR'  )
					{
						var chkBox = document.getElementsByName('statusCB');
						var status="";
						var isStatus = false;
						var frmDt =$('#fromSuibmissionDate').val();
						var toDt =$('#toSuibmissionDate').val();
						var queryOn = $('#queryOn').val();
						for (i=0; i<chkBox.length; i++)
						{
							if (chkBox[i].checked==true)
							{
								status+=chkBox[i].value+"@@";
								isStatus=true;
							}
						}
						
						if(isStatus)
						{
							status=status.substring(0,status.length-2);
							queryString+="&status="+status+"&fromDate="+frmDt+"&toDate="+toDt+"&queryOn="+queryOn;
							getData(queryString);
						}
						else
						{
							alert("Please select any one status.");
							return false;	
						}
					}
				});
			});
			function displayNone()
			{
				$('.projectNames').slideUp('fast');
				$('.statusNames').slideUp('fast');
				$('.timeBase').slideUp('fast');
				$('.submitBtnDiv').slideUp('fast');
			}
			function getData(queryString)
			{
				var commURL = "PostSubQueryReport_ex.do"+queryString;
				$.ajax
				({			
						url: commURL,
						beforeSend: function()
						{ 
							$("#SubQueryReport").html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
						},
						success: function(data) 
				  		{
							$("#SubQueryReport").html(data);	    		
						}	  		
				});
			} 
		</script>
</head>
<body>
<br>
<div align="center"><img
	src="images/Header_Images/Report/post_submission_query_report.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div style="width: 95%"><s:form
	action="SubmissionTrackingSearch_ex" method="post" id="myForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Report Type</td>
			<td align="left" width="85%"><select id='reportType'
				name="reportType">
				<option value="-1">Select Report Type</option>
				<option value="StatusWR">Status Wise Report</option>
				<option value="TimeWR">Time Wise Report</option>
			</select></td>
		</tr>
	</table>
	<div class="projectNames" style="width: 100%">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td align="left" width="75%"><s:select id="workspaceId"
				list="wsMstList" headerKey="All" headerValue="All Project"
				listKey="workSpaceId" listValue="workSpaceDesc"></s:select></td>
		</tr>
	</table>
	</div>
	<div class="statusNames" style="width: 100%">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Status</td>
			<td align="left" width="75%"><s:select list="statusValues"
				id="statusDDL" headerKey="All" headerValue="All Status"
				listKey="top" listValue="top"></s:select></td>
		</tr>
	</table>
	</div>
	<div class="timeBase" style="width: 100%">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Status</td>
			<td align="left" width="75%"><s:iterator value="statusValues">
				<input type="checkbox" id="<s:property value="top"/>"
					name="statusCB" value="<s:property value="top"/>">
				<label for="<s:property value="top"/>"><s:property
					value="top" /></label> &nbsp;&nbsp;&nbsp;	
										</s:iterator></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Query Date</td>
			<td align="left" width="85%" class="title"><SELECT
				name="durationType" id="durationType">
				<option value="CQ">Current Quarter</option>
				<option value="CH">Current Half Year</option>
				<option value="CY">Current Year</option>
				<option value="LQ">Last Quarter</option>
				<option value="LH">Last Half Year</option>
				<option value="LY">Last Year</option>
			</SELECT> <input type="text" value="<s:property value="currentDate"/>"
				id="currDateFromServer" style="display: none;"></td>
		</tr>
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
			<div align="left" class="displayDuration">From <input
				type="text" readonly="readonly" name="fromSubmissionDate"
				class="dateValue" id="fromSuibmissionDate"> (YYYY/MM/DD)</div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
			<div align="left" class="displayDuration">
			&nbsp;&nbsp;&nbsp;&nbsp;To <input type="text" readonly="readonly"
				name="toSubmissionDate" class="dateValue" id="toSuibmissionDate">
			(YYYY/MM/DD)</div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
			<div align="left" class="displayDuration">Match with <SELECT
				name="queryOn" id="queryOn">
				<option value="startOn">Query Start Date</option>
				<option value="endOn">Query End Date</option>
				<option value="modifyOn">Query Modify Date</option>
			</SELECT></div>
			</td>
		</tr>
	</table>
	</div>
	<div class="submitBtnDiv" style="width: 100%">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"></td>
			<td align="left" width="75%"><input type="button" name="subBtn"
				id="subBtn" value="Search" class="button"></td>
		</tr>
	</table>
	</div>
</s:form> <br />
<br />
<div id="SubQueryReport" style="width: 100%"></div>
<br />
</div>
</div>
</div>
</body>
</html>