<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<s:head />
</head>

<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>
<div class="titlediv"><u>Document Property Details</u></div>
<br>
<div class="headercls" align="center" style="width: 100%;">
Project Details</div>

<table style="border-style: solid; border-width: 1" width="100%">


	<s:if test="projectDetail.size == 0">
		<tr>
			<td colspan="2" align="center"><font size="2" color="#c00000"><b><br>
			Details are Not Available<br>
			&nbsp;</b></font></td>
		</tr>

	</s:if>
	<s:else>
		<s:iterator value="projectDetail">
			<tr class="even">
				<td width="30%" style="color: navy;"><s:property
					value="attributeName" /></td>
				<td width="30%"><s:property value="attrValue" /></td>
			</tr>
		</s:iterator>


	</s:else>

</table>



<div class="headercls" align="center" style="width: 100%;">
Document System Properties</div>


<table style="border-style: solid; border-width: 1" width="100%">

	<s:if test="documentSYSProperties.size == 0">
		<tr>
			<td colspan="2" align="center"><font size="2" color="#c00000"><b><br>
			Details are Not Available<br>
			&nbsp;</b></font></td>
		</tr>
	</s:if>
	<s:else>
		<s:iterator value="documentSYSProperties">
			<s:hidden value="attrValue" id="attrValue" />
			<s:if test="attrValue != ''">
				<tr class="even">
					<td width="30%" style="color: navy;"><s:property
						value="attributeName" /></td>
					<td width="30%"><s:property value="attrValue" /></td>
				</tr>
			</s:if>
		</s:iterator>

	</s:else>
</table>


<div class="headercls" align="center" style="width: 100%;">
Document user defined properties</div>
<table style="border-style: solid; border-width: 1" width="100%">
	<s:if test="docUserDefinedProp.size == 0">
		<tr>
			<td colspan="2" align="center"><font size="2" color="#c00000"><b><br>
			Details are Not Available<br>
			&nbsp;</b></font></td>
		</tr>
	</s:if>
	<s:else>
		<s:iterator value="docUserDefinedProp">
			<tr class="even">
				<td width="30%" style="color: navy;"><s:property
					value="attributeName" /></td>
				<td width="30%"><s:property
					value="workSpaceNodeAttrHistoryAttributeValue" /></td>
			</tr>
		</s:iterator>
	</s:else>


</table>

<div class="headercls" align="center" style="width: 100%;">
Document user defined properties</div>
<table style="border-style: solid; border-width: 1" width="100%">
	<tr style="width: 100%;">
		<td colspan="2" height="3" width="100%">&nbsp;</td>
	</tr>
	<tr style="width: 100%;" class="even" style="color: navy;">
		<td width="35%"><u><b>Created By</b></u></td>
		<td width="35%"><u><b>Reviewed By</b></u></td>
		<td width="35%"><u><b>Approved By</b></u></td>
	</tr>
	<tr style="width: 100%;" class="even">
		<td width="35%">${created}</td>
		<td width="35%">${reviewed}</td>
		<td width="35%">${approved}</td>
	</tr>
	<tr style="width: 100%;">
		<td colspan="2" height="3" width="100%">&nbsp;</td>
	</tr>
</table>



</body>
</html>

