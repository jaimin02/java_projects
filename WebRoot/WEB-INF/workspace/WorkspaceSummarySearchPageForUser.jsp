<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head theme="ajax" />

<script type="text/javascript">
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.UserWorkspaceSummaryForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
</script>
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Project/Manage_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="ShowUserWorkspaceSummay"
	method="post" theme="ajax" name="UserWorkspaceSummaryForm">

	<table width="100%">
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Type&nbsp;</td>
			<td align="left"><select name="projectCode">
				<option value="-1">Select Project Type</option>
				<s:iterator value="projectTypes">
					<option value="<s:property value="projectCode"/>"><s:property
						value="projectName" /></option>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Client&nbsp;</td>
			<td align="left"><select name="clientCode">
				<option value="-1">Select Client Name</option>
				<s:iterator value="clients">
					<option value="<s:property value="clientCode"/>"><s:property
						value="clientName" /></option>
				</s:iterator>
			</select></td>
		</tr>


		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region&nbsp;</td>
			<td align="left"><select name="locationCode">
				<option value="-1">Select Region Name</option>
				<s:iterator value="locations">
					<option value="<s:property value="locationCode"/>"><s:property
						value="locationName" /></option>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="workSpaceDesc">Project
			Name&nbsp;</td>
			<td align="left"><input type="text" name="workSpaceDesc"
				size="30"></td>
		</tr>
	</table>

	<br>
	<div align="left" style="padding-left: 240px;"><s:submit
		name="submitBtn"  align="left" value="Search Project" theme="ajax"
		cssClass="button" targets="workspaceSummary">
	</s:submit>
	</div>

</s:form> <s:url id="DisplaySearchPageURL" action="ShowUserWorkspaceSummay">
	<s:param name="defaultWorkSpace" value="true"></s:param>
	<s:param name="projectCode" value="-1"></s:param>
	<s:param name="clientCode" value="-1"></s:param>
	<s:param name="locationCode" value="-1"></s:param>
	<s:param name="workSpaceDesc" value=""></s:param>
</s:url>
<div style="width: 950px;" align="center"><s:div
	id="workspaceSummary" theme="ajax" autoStart="true"
	href="%{DisplaySearchPageURL}">
</s:div></div>
</div>
</div>
</div>

</body>
</html>
