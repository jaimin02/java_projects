<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<s:head />


<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/CalendarPopup.js"></SCRIPT>
<SCRIPT type="text/javascript">

	function validate()
	{
		if(document.reportForm.workSpaceId.value==-1)
		{
		    alert("Please Select Project Name");
			document.reportForm.workSpaceId.style.backgroundColor="#FFE6F7"; 
     		document.reportForm.workSpaceId.focus();
			return false;
		}
		
		return true;
	}	

</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>


<div class="titlediv">Document Stage Report</div>
<div align="center"><s:form action="ShowDocStageReport"
	method="post" name="reportForm">
	<br>

	<table>
		<tr>
			<td class="title">&nbsp;&nbsp; Project Name:</td>
			<td><select name="workSpaceId">
				<option value="-1">Select Project Name</option>
				<s:iterator value="getAllWorkspace">
					<option value="<s:property value="workSpaceId"/>"><s:property
						value="workSpaceDesc" /></option>
				</s:iterator>
			</select><ajax:event ajaxRef="showUserDtl/getUserDtl" />

			<tr>
				<td class="title">&nbsp;&nbsp; User Name:</td>
				<td>
				<div id="ShowUserDetail"></div>
				</td>
			</tr>

			<!--<ajax:event ajaxRef="showProjectDtl/getProjectDtl"/> -->



			<tr>
				<td class="title">&nbsp;&nbsp; Stage Name:</td>
				<td><select name="stageId">
					<option value="-1">Select Stage</option>
					<s:iterator value="getAllStages">
						<option value="<s:property value="stageId"/>"><s:property
							value="stageDesc" /></option>
					</s:iterator>
				</select></td>
			</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td><s:submit value="Show" cssClass="button"
				onclick="return validate();"></s:submit></td>
		</tr>


	</table>

	<ajax:enable />

</s:form></div>
</body>
</html>

