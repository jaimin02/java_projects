<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
<SCRIPT type="text/javascript">
			$(document).ready(function() { 
				$(".dateValue").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
				var options = {success: function(data)	{
					alert(data);
					location.reload();
				}};
				$('#saveEmployeeForm').submit(function(){
					$(this).ajaxSubmit(options);
					return false;
				});
			});
			function deleteEmp(empNo)
			{
				$.ajax({			
					url: 'deleteEmpDtl_ex.do?empNo=' + empNo,
			  		success: function(data) 
			  		{
				  		alert(data);
						location.reload();
					}										
				});
			}
			function isNumberKey(evt)
		    {
		         var charCode = (evt.which) ? evt.which : event.keyCode;
		         if (charCode > 31 && ((charCode < 48) || (charCode > 57)))
		            return false;
		         return true;
		    }
			function editEmp(empNo)
			{
				$.ajax({			
					url: 'editEmpDtl_ex.do?empNo=' + empNo,
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
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
</div>
<div align="center"><img
	src="images/Header_Images/Master/Employee_Details.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="saveEmployeeForm"
	id="saveEmployeeForm" method="post" action="SaveEmpDtl_ex">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Employee Code&nbsp;</td>
			<td align="left"><s:textfield name="empCode" id="empCode"
				size="25"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Employee Name&nbsp;</td>
			<td align="left"><s:textfield name="empName" id="empName"
				size="25"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Department&nbsp;</td>
			<td align="left"><s:select list="deptMstList" id="deptCode"
				name="deptCode" headerKey="-999" headerValue="Select Department"
				listKey="deptCode" listValue="deptName"></s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Date of Joining&nbsp;</td>
			<td align="left"><input type="text" readonly="readonly"
				class="dateValue" name="jod" id="jod"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Mobile No.&nbsp;</td>
			<td align="left"><s:textfield name="mobileNo"
				onkeypress="return isNumberKey(event)" id="mobileNo" size="25"
				maxLength="12"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Email Id.&nbsp;</td>
			<td align="left"><s:textfield name="emailId" id="emailId"
				size="25"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><s:textarea name="remark" id="remark" cols="26"
				rows="5"></s:textarea></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit" value="Save Details"
				class="button"></td>
		</tr>
	</table>
	<br>
</s:form></div>
<s:if test="employeeList.size() > 0 ">
	<div>
	<table class="datatable" width="95%" cellspacing="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Emp Code</th>
				<th>Employee Name</th>
				<th>Department</th>
				<th>Date of Joining</th>
				<th>Email Id.</th>
				<th>Mobile No.</th>
				<th>Remarks</th>
				<th>Modify On</th>
				<th>Modify By</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="employeeList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="empCode" /></td>
					<td><s:property value="empName" /></td>
					<td><s:property value="deptName" /></td>
					<td><s:date name="jOD" format="MMM-dd-yyyy" /></td>
					<td><s:property value="emailId" /></td>
					<td><s:property value="mobileNo" /></td>
					<td><s:property value="remark" /></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="modifyByUser" /></td>
					<td>
					<div align="center"><img border="0px" alt="Edit"
						src="images/Common/edit.png" height="18px" width="18px"
						onclick="editEmp('<s:property value="empNo"/>')"></div>
					</td>
					<td>
					<div align="center"><img border="0px" alt="Delete"
						src="images/Common/delete.png" height="18px" width="18px"
						onclick="deleteEmp('<s:property value="empNo"/>')"></div>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br />
	</div>
</s:if></div>
</div>
<div id="backgroundPopup"></div>
<div id="popupContact" style="width: 550px; height: 350px;"></div>
</body>
</html>