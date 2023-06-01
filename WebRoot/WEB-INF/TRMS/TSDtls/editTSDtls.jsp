<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
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
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/datepair.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/jquery.timepicker.js"></script>
<script type="text/javascript">
			$("#eTtrainingStartDate").val("<s:date name='tSDtlList.get(0).trainingStartDate' format='yyyy/MM/dd'/>");
			$("#eTrainingEndDate").val("<s:date name='tSDtlList.get(0).trainingEndDate' format='yyyy/MM/dd'/>");
				$('#popupContactClose').click(function(){
					disablePopup();
				});

				var options = {success: function(data)	{
					alert(data);
					subinfo();
				}};
				$('#editTSDtlForm').submit(function(){
					document.getElementById("trainerE").value = $("#trainerListE option").map(function () {return this.value;}).get().join(",");
					document.getElementById("traineeE").value = $("#traineeListE option").map(function () {return this.value;}).get().join(",");
					$(this).ajaxSubmit(options);
					return false;
				});

				function selectTrainerFun()
				{
					$('#selectEmpE :selected').each(function(index) {     
						var select = document.getElementById("trainerListE");
						select.options[select.options.length] = new Option($(this).text(), $(this).val());
						$('#selectEmpE :selected').remove();
					});  
				}
				function removeTrainerFun()
				{
					$('#trainerListE :selected').each(function(index) {     
						var select = document.getElementById("selectEmpE");
						select.options[select.options.length] = new Option($(this).text(), $(this).val());
						$('#trainerListE :selected').remove();
						index++;
					});  
				}


				function selectTraineeFun()
				{
					$('#selectEmpE :selected').each(function(index) {     
						var select = document.getElementById("traineeListE");
						select.options[select.options.length] = new Option($(this).text(), $(this).val());
						$('#selectEmpE :selected').remove();
					});  
				}
				function removeTraineeFun()
				{
					$('#traineeListE :selected').each(function(index) {     
						var select = document.getElementById("selectEmpE");
						select.options[select.options.length] = new Option($(this).text(), $(this).val());
						$('#traineeListE :selected').remove();
						index++;
					});  
				}
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;"
	id="editMessage"></div>
<div><a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">Edit
Training Schedule Detail</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 500px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:form name="editTSDtlForm" id="editTSDtlForm"
	method="post" action="SaveTSDtl_ex">
	<input type="hidden" name="trainingRecordNo" id="trainingRecordNo"
		value="<s:property value='tSDtlList.get(0).trainingRecordNo'/>" />
	<input type="hidden" name="trainingScheduleNo" id="trainingScheduleNo"
		value="<s:property value='tSDtlList.get(0).trainingScheduleNo'/>"></input>
	<input type="hidden" name="status" id="status" value="E" />


	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">TrainingScheduleDesc&nbsp;</td>
			<td align="left"><textarea name="trainingScheduleDesc"
				id="trainingScheduleDesc" cols="26" rows="5"><s:property
				value='tSDtlList.get(0).trainingScheduleDesc' /></textarea></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Date&nbsp;</td>
			<td align="left">
			<p class="datepair"><input type="text" id="eTtrainingStartDate"
				name="trainingStartDate" class="date start" /> <label class="title">to</label>
			<input type="text" id="eTrainingEndDate" name="trainingEndDate"
				class="date end" /> <label class="title">(YYYY/MM/DD)</label></p>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Time&nbsp;</td>
			<td align="left">
			<p class="datepair"><input type="text" id="trainingStartTime"
				name="trainingStartTime" class="time start"
				value="<s:property value='tSDtlList.get(0).trainingStartTime'/>" />
			<label class="title">to</label> <input type="text"
				id="trainingEndTime" name="trainingEndTime" class="time end"
				value="<s:property value='tSDtlList.get(0).trainingEndTime'/>" /></p>
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
					<td rowspan="2" width="30%"><select class="emp"
						id="selectEmpE" multiple="multiple" name="selectEmpE">
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
					<td width="30%"><select class="emp trainer" id="trainerListE"
						multiple="multiple" name="trainerListE">
						<s:iterator value="tAMstList">
							<s:if test="isTraner == 'Y'">
								<option class="option1" value="<s:property value="empNo"/>"><s:property
									value="dtoEmpMst.empName" />&nbsp;(<s:property
									value="dtoEmpMst.empCode" />)</option>
							</s:if>
						</s:iterator>
					</select></td>
					<td><input align="top" type="button" class="button"
						id="RemoveTrainer1" value=" Remove " onclick="removeTrainerFun();">
					<input type="hidden" id="trainerE" name="trainer" value="">
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
					<td width="30%"><select class="emp" id="traineeListE"
						multiple="multiple" name="traineeListE">
						<s:iterator value="tAMstList">
							<s:if test="isTraner == 'N'">
								<option class="option1" value="<s:property value="empNo"/>"><s:property
									value="dtoEmpMst.empName" />&nbsp;(<s:property
									value="dtoEmpMst.empCode" />)</option>
							</s:if>
						</s:iterator>
					</select></td>
					<td><input align="top" type="button" class="button"
						id="RemoveTrainee1" value=" Remove " onclick="removeTraineeFun()">
					<input type="hidden" id="traineeE" name="trainee" value="">
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><textarea rows="5" cols="26" name="remark"
				id="remark"><s:property value='tSDtlList.get(0).remark' /></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit"
				value="Update Schedule Record" class="button"></td>
		</tr>
	</table>
	<br>
</s:form></div>
<br>
</div>
</body>
</html>