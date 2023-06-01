<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<link
	href="<%=request.getContextPath()%>/js/jquery/timepicker/jquery.timepicker.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
input.time,input.date {
	font-size: 13px;
}

input.time {
	width: 80px;
}

input.date {
	width: 90px;
}

.footer {
	text-align: center;
	padding-top: 30px;
	font-style: italic;
}

div.ui-datepicker {
	font-size: 11px;
}

.emp {
	min-width: 250px;
	width: : 250px;
	height: 150px;
	max-width: 250px;
	overflow: auto;
}

.trainer {
	height: 100px;
}
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/datepair.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/jquery.timepicker.js"></script>
<SCRIPT type="text/javascript">
			$(document).ready(function() { 
				var options = {success: function(data)	{
					alert(data);
					subinfo();
				}};
				$('#saveTSDtlForm').submit(function(){
					document.getElementById("trainer").value = $("#trainerList option").map(function () {return this.value;}).get().join(",");
					document.getElementById("trainee").value = $("#traineeList option").map(function () {return this.value;}).get().join(",");
					$(this).ajaxSubmit(options);
					return false;
				});
			});
			function deleteTSDtl(trainingScheduleNo)
			{
				$.ajax({			
					url: 'deleteTSDtl_ex.do?trainingScheduleNo=' + trainingScheduleNo,
			  		success: function(data) 
			  		{
				  		alert(data);
				  		subinfo();
					}										
				});
			}
			function editTSDtl(trainingScheduleNo)
			{
				$.ajax({			
					url: 'editTSDtl_ex.do?trainingScheduleNo=' + trainingScheduleNo,
			  		success: function(data) 
			  		{
						$('#popupContact').html(data);
			  		}										
				});
				//centering with css
				centerPopup();
				//load popup
				loadPopup();
			}

			function editAttendanceDtl(trainingScheduleNo)
			{
				$.ajax({			
					url: 'editTADtl_ex.do?trainingScheduleNo=' + trainingScheduleNo,
			  		success: function(data) 
			  		{
						$('#popupContact').html(data);
			  		}										
				});
				//centering with css
				centerPopup();
				//load popup
				loadPopup();
			}
		</script>

<script type="text/javascript">
			function selectTrainerFun()
			{
				$('#selectEmp :selected').each(function(index) {     
					var select = document.getElementById("trainerList");
					select.options[select.options.length] = new Option($(this).text(), $(this).val());
					$('#selectEmp :selected').remove();
				});  
			}
			function removeTrainerFun()
			{
				$('#trainerList :selected').each(function(index) {     
					var select = document.getElementById("selectEmp");
					select.options[select.options.length] = new Option($(this).text(), $(this).val());
					$('#trainerList :selected').remove();
					index++;
				});  
			}


			function selectTraineeFun()
			{
				$('#selectEmp :selected').each(function(index) {     
					var select = document.getElementById("traineeList");
					select.options[select.options.length] = new Option($(this).text(), $(this).val());
					$('#selectEmp :selected').remove();
				});  
			}
			function removeTraineeFun()
			{
				$('#traineeList :selected').each(function(index) {     
					var select = document.getElementById("selectEmp");
					select.options[select.options.length] = new Option($(this).text(), $(this).val());
					$('#traineeList :selected').remove();
					index++;
				});  
			}
			
	</script>


</head>
<body>
<div align="center"><br>
<div align="left"><s:form name="saveTSDtlForm" id="saveTSDtlForm"
	method="post" action="SaveTSDtl_ex">
	<input type="hidden" name="trainingRecordNo" id="trainingRecordNo"
		value="<s:property value='trainingRecordNo'/>" />
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">TrainingScheduleDesc&nbsp;</td>
			<td align="left"><s:textarea name="trainingScheduleDesc"
				id="trainingScheduleDesc" cols="26" rows="5"></s:textarea></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Date&nbsp;</td>
			<td align="left">
			<p class="datepair"><input type="text" id="trainingStartDate"
				name="trainingStartDate" class="date start" /> <label class="title">to</label>
			<input type="text" id="trainingEndDate" name="trainingEndDate"
				class="date end" /> <label class="title">(YYYY/MM/DD)</label></p>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Time&nbsp;</td>
			<td align="left">
			<p class="datepair"><input type="text" id="trainingStartTime"
				name="trainingStartTime" class="time start" /> <label class="title">to</label>
			<input type="text" id="trainingEndTime" name="trainingEndTime"
				class="time end" /></p>
			</td>
		</tr>
		<!--
						<tr>
							<td class="title" align="right" width="25%" style="padding: 2px; padding-right: 6px;">TrainingRefDocPath&nbsp;</td>
							<td align="left">
								<input type="text" id="trainingRefDocPath" name="trainingRefDocPath">
							</td>
						</tr>
						-->
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Employee :&nbsp;</td>
			<td valign="top">
			<table align="left" width="100%">
				<tr>
					<td rowspan="2" width="30%"><select class="emp" id="selectEmp"
						multiple="multiple" name="selectEmp">
						<s:iterator value="empList">
							<option class="option1" value="<s:property value="empNo"/>"><s:property
								value="empName" />&nbsp;(<s:property value="empCode" />)</option>
						</s:iterator>
					</select></td>
					<td><input type="button" class="button" id="selectTrainer1"
						value=" As A Trainer " onclick="selectTrainerFun();"></td>
				</tr>
				<tr>
					<td><input type="button" class="button" id="selectTrainee1"
						value=" As A Trainee " onclick="selectTraineeFun();"></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Trainer :&nbsp;</td>
			<td valign="top">
			<table align="left" width="100%">
				<tr>
					<td width="30%"><select class="emp trainer" id="trainerList"
						multiple="multiple" name="trainerList"></select></td>
					<td><input align="top" type="button" class="button"
						id="RemoveTrainer1" value=" Remove " onclick="removeTrainerFun();">
					<input type="hidden" id="trainer" name="trainer" value="">
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Trainee :&nbsp;</td>
			<td valign="top">
			<table align="left" width="100%">
				<tr>
					<td width="30%"><select class="emp" id="traineeList"
						multiple="multiple" name="traineeList"></select></td>
					<td><input align="top" type="button" class="button"
						id="RemoveTrainee1" value=" Remove " onclick="removeTraineeFun()">
					<input type="hidden" id="trainee" name="trainee" value="">
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><s:textarea name="remark" id="remark" cols="26"
				rows="5"></s:textarea></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit"
				value="Generate Schedule Record" class="button"></td>
		</tr>
	</table>
	<br>
</s:form></div>
<s:if test="tSDtlList.size() > 0 ">
	<div>
	<table width="95%" cellspacing="1" align="center"
		style="text-align: left">
		<tr align="left" style="text-align: left" class="blank">
			<td width="70%"><label class="title"> Topic&nbsp;:</label>&nbsp;<s:property
				value="tSDtlList.get(0).trainingHdr" /></td>
			<td align="right"><label class="title"> Exp.
			Duration&nbsp;:</label>&nbsp;<s:property
				value="tSDtlList.get(0).totalTrainingDuration" /> &nbsp;Min.</td>
		</tr>
		<tr>
			<td colspan="2"><label class="title"> Details&nbsp;:</label>&nbsp;<s:property
				value="tSDtlList.get(0).trainingDtl" /></td>
		</tr>
	</table>
	<table class="datatable" width="95%" cellspacing="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Scheduliing Details</th>
				<th>Training Date</th>
				<th>Training Timing&nbsp;[Duration (Hours:Minutes)]</th>
				<!--<th>Ref. Project</th>
								<th>Ref. Doc.</th>-->
				<th>Remarks</th>
				<th>Modify On</th>
				<th>Modify By</th>
				<th>Edit</th>
				<th>Attendance</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="tSDtlList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="trainingScheduleDesc" /></td>
					<td style="text-align: center"><s:date
						name="trainingStartDate" format="MMM-dd-yyyy" /> To <s:date
						name="trainingEndDate" format="MMM-dd-yyyy" /></td>
					<td style="text-align: center"><s:date
						name="trainingStartTime" format="HH:mm " /> To <s:date
						name="trainingEndTime" format="HH:mm " /> &nbsp;&nbsp; [<s:date
						name="diffTime" format="HH:mm" /> ]</td>
					<!--<td><s:property value="refWorkspaceDesc"/></td>
							    	<td><s:property value="trainingRefDocPath"/></td>
							    	-->
					<td><s:property value="remark" /></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="modifyByUser" /></td>
					<td>
					<div align="center"><img border="0px" alt="Edit"
						src="images/Common/edit.png" height="18px" width="18px"
						onclick="editTSDtl('<s:property value="trainingScheduleNo"/>')">
					</div>
					</td>
					<td>
					<div align="center"><img border="0px" alt="Edit"
						src="images/Common/attendance.png" height="18px" width="18px"
						onclick="editAttendanceDtl('<s:property value="trainingScheduleNo"/>')">
					</div>
					</td>
					<td>
					<div align="center"><img border="0px" alt="Delete"
						src="images/Common/delete.png" height="18px" width="18px"
						onclick="deleteTSDtl('<s:property value="trainingScheduleNo"/>')">
					</div>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br />
	</div>
</s:if></div>
<div id="backgroundPopup"></div>
<div id="popupContact" style="width: 550px; height: 550px;"></div>
</body>
</html>