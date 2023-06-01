<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />

<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/CalendarPopup.js"></SCRIPT>


</head>

<body>

<div class="titlediv">Edit User To Template</div>
<div align="center"><br>


<s:form action="UpdateTemplateUser" method="post">
	<table>
		<tr>
			<td class="title">Template Name :</td>
			<td class="title">${getTemplateUserDetail.templateDesc}</td>
		</tr>
		<tr>
			<td class="title">User Name :</td>
			<td class="title">${getTemplateUserDetail.username}</td>
		</tr>
		<tr>
			<td class="title">User Group Name :</td>
			<td class="title">${getTemplateUserDetail.userGroupName}</td>
		</tr>


		<tr>
			<td class="title">Rights :</td>
			<td><select name="accessrights">
				<option value="-1">Select User Rights</option>
				<option value="read">Read Only</option>
				<option value="edit">Can Edit</option>
			</select></td>
		</tr>

		<tr>
			<td class="title">From Date :</td>
			<td><input type="text" name="fromDt" id="fromDt"
				ReadOnly="readonly" size="12"
				value="<s:date name="getTemplateUserDetail.fromDt" format="yyyy/M/dd"/>">
			&nbsp;<IMG onclick="popUpCalendar(this,fromDt,'yyyy/mm/dd');"
				src="<%=request.getContextPath() %>/images/Calendar.png"
				align="middle"></td>
		</tr>

		<tr>
			<td class="title">To Date :</td>
			<td><input type="text" name="toDt" id="toDt" ReadOnly="readonly"
				size="12"
				value="<s:date name="getTemplateUserDetail.toDt" format="yyyy/M/dd"/>">
			&nbsp;<IMG onclick="popUpCalendar(this,toDt,'yyyy/mm/dd');"
				src="<%=request.getContextPath() %>/images/Calendar.png"
				align="middle"></td>
		</tr>

		<tr>
			<td><input type="hidden" name="userId"
				value="${getTemplateUserDetail.userCode}"> <input
				type="hidden" name="userGroupCode"
				value="${getTemplateUserDetail.userGroupCode}"> <s:hidden
				name="templateId"></s:hidden></td>
			<td><s:submit value="Update" cssClass="button"></s:submit></td>
		</tr>

	</table>

</s:form></div>

</body>
</html>
