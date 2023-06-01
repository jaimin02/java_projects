<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<SCRIPT type="text/javascript">
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center" style="width: 900px;"><s:if
	test="empList.size != 0">
	<div align="right" style="width: 99%"><s:form
		action="ExportToXls" id="myform" method="post"
		cssStyle="width: 200px;padding-bottom: 5px;height: 20px;">
		<input type="hidden" name="fileName"
			value="EmployeeTrainingDetails(1).xls" style="">
		<textarea rows="1" cols="1" name="tabText" id="tableText"
			style="display: none;"></textarea>
		<img alt="Export To Excel" title="Export To Excel"
			src="images/Common/Excel.png"
			onclick="document.getElementById('tableText').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit();">
	</s:form></div>
	<div id="divTabText" style="width: 100%" align="left"><s:iterator
		value="empList">
		<table align="left" width="100%" class="datatable" id="tableText">
			<tr class="none">
				<td colspan="2"><label class="title">Employee Code : </label><s:property
					value="empCode" /></td>
			</tr>
			<tr class="none">
				<td colspan="2"><label class="title">Employee Name : </label><s:property
					value="empName" /></td>
			</tr>
			<tr class="none">
				<td colspan="2"><label class="title">Dept. Name : </label><s:property
					value="deptName" /></td>
			</tr>
			<tr class="none">
				<td colspan="2"></td>
			</tr>
			<s:iterator value="tRDtl">
				<tr class="none">
					<td colspan="2">
					<table align="right" width="90%">
						<tr class="none">
							<td></td>
							<td><label class="title">Training Id : </label><s:property
								value="trainingId" /></td>
							<td style="text-align: right;" align="right"><label
								class="title">Exp. Duration : </label><s:property
								value="totalTrainingDuration" /></td>
						</tr>
						<tr class="none">
							<td></td>
							<td colspan="2"><label class="title">Training Topic
							: </label><s:property value="trainingHdr" />&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr class="none">
							<td></td>
							<td colspan="2"><label class="title">Training
							Details : </label><s:property value="trainingDtl" />&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr class="none">
							<td colspan="3"></td>
						</tr>
						<s:iterator value="trainingScheduleingDetails">
							<tr class="none">
								<td></td>
								<td colspan="2">
								<table align="right" width="90%"
									<s:if test="trainingAttendanceMst.get(0).isPresent != 'Y'">style="color: red;"</s:if>>

									<tr class="none">
										<td></td>
										<td colspan="2"><label class="title">Training
										Schedule Desc : </label><s:property value="trainingScheduleDesc" /></td>
									</tr>
									<tr class="none">
										<td></td>
										<td><label class="title">Training Date : </label><s:date
											name="trainingStartDate" format="MMM-dd-yyyy" /><label
											class="title"> To </label><s:date name="trainingEndDate"
											format="MMM-dd-yyyy" /></td>
										<td><label class="title">Training Time : </label><s:date
											name="trainingStartTime" format="HH:mm " /><label
											class="title"> To </label><s:date name="trainingEndTime"
											format="HH:mm " />&nbsp;&nbsp; <label class="title">[</label><s:date
											name="diffTime" format="HH:mm" /> <label class="title">]
										(</label>Hour:Min.<label class="title">)</label></td>
									</tr>
									<tr class="none">
										<td></td>
										<td colspan="2"><label class="title">As A : </label><s:if
											test="trainingAttendanceMst.get(0).isTraner == 'Y'"> Trainer</s:if><s:else>Trainee</s:else></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr class="none">
								<td></td>
								<td colspan="2"></td>
							</tr>
						</s:iterator>
					</table>
					</td>
				</tr>
			</s:iterator>
		</table>
		<table align="left" width="100%">
			<tr>
				<td></td>
			</tr>
		</table>
	</s:iterator></div>
</s:if> <s:else>
	<div style="width: 95%; text-align: center" align="center"><label
		class="title">No Training Details Found For Selected Criteria.</label>
	</div>
</s:else> &nbsp;</div>
</body>
</html>