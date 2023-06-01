<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript">
				$('#popupContactClose').click(function(){
					disablePopup();
				});

				var options = {success: function(data)	{
					alert(data);
					subinfo();
				}};
				$('#editTADtlForm').submit(function(){
					document.getElementById("presentEmp").value =$("input[name=isPresent]:checked").map(function () {return this.value;}).get().join(",");
					document.getElementById("absentEmp").value =$("input[name=isPresent]:not(:checked)").map(function () {return this.value;}).get().join(",");
					
					$(this).ajaxSubmit(options);
					return false;
				});

				$('#allEmp').click(function(){
					    $('#attRpt input:checkbox').attr('checked', $(this).attr('checked')); 
				});
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;"
	id="editMessage"></div>
<div><a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">Edit
Attendance Detail</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 500px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:form name="editTADtlForm" id="editTADtlForm"
	method="post" action="SaveTADtl_ex">
	<input type="hidden" name="presentEmp" id="presentEmp" value=""></input>
	<input type="hidden" name="absentEmp" id="absentEmp" value=""></input>
	<input type="hidden" name="trainingScheduleNo" id="trainingScheduleNo"
		value="<s:property value='tAMstList.get(0).trainingScheduleNo'/>"></input>
	<table class="datatable" width="95%" cellspacing="1" align="center">
		<thead>
			<tr>
				<th align="center" style="text-align: center"><input
					type="checkbox" name="allEmp" checked="checked" id="allEmp"></input></th>
				<th>As a</th>
				<th>Employee Code</th>
				<th>Employee Name</th>
				<th>Dept.</th>
			</tr>
		</thead>
		<tbody id="attRpt">
			<s:iterator value="tAMstList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td align="center" style="text-align: center"><input
						type="checkbox" name="isPresent"
						<s:if test="isPresent == 'Y'">
							        		checked="checked"
							        	</s:if>
						value="<s:property value="trAttNo"/>"><s:property
						value="trAttNo" /></td>
					<td><s:if test="isTraner == 'Y'">Trainer</s:if> <s:else>Trainee</s:else>
					</td>
					<td><s:property value="dtoEmpMst.empCode" /></td>
					<td><s:property value="dtoEmpMst.empName" /></td>
					<td><s:property value="dtoEmpMst.deptName" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br>
	<input type="submit" value="Save Attendance" class="button">
</s:form></div>
<br>
</div>
</body>
</html>