<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$('#popupContactClose').click(function(){
				disablePopup();
			});
			$(".dateValue1").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});

			var options = {success: function(data)	{
				alert(data);
				location.reload();
			}};
			$('#editEmployeeForm').submit(function(){
				$(this).ajaxSubmit(options);
				return false;
			});
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
Project Detail</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 300px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:form action="SaveEmpDtl_ex" method="post"
	name="editEmployeeForm" id="editEmployeeForm">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Employee Code&nbsp;</td>
			<td align="left"><input type="text" name="empCode" id="empCode1"
				size="25" value="<s:property value='dtoEmployeeMst.empCode'/>" /></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Employee Name&nbsp;</td>
			<td align="left"><input type="text" name="empName" id="empName1"
				size="25" value="<s:property value='dtoEmployeeMst.empName'/>" /></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Department&nbsp;</td>
			<td align="left"><select id="deptCode" name="deptCode">
				<s:iterator value="deptMstList">
					<option
						<s:if test="dtoEmployeeMst.deptCode == deptCode">
												selected="selected"
											</s:if>
						value="<s:property value="deptCode"/>"><s:property
						value="deptName" /></option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Date of Joining&nbsp;</td>
			<td align="left"><input type="text" readonly="readonly"
				class="dateValue1" name="jod" id="jod1"
				value="<s:date name='dtoEmployeeMst.jOD' format='yyyy/MM/dd' />"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Mobile No.&nbsp;</td>
			<td align="left"><input type="number"
				onkeypress="return isNumberKey(event)" name="mobileNo"
				id="mobileNo1" size="25" maxLength="12"
				value="<s:property value='dtoEmployeeMst.mobileNo'/>" /></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Email Id.&nbsp;</td>
			<td align="left"><input type="text" name="emailId" id="emailId1"
				size="25" value="<s:property value='dtoEmployeeMst.emailId'/>" /></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><textarea name="remark" id="remark1" cols="26"
				rows="5"><s:property value="dtoEmployeeMst.remark" /></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit" value="Update Details"
				class="button"></td>
		</tr>
	</table>
	<s:hidden name="empNo"></s:hidden>
	<s:hidden name="update" value="true"></s:hidden>
</s:form></div>
<br>
</div>
</body>
</html>