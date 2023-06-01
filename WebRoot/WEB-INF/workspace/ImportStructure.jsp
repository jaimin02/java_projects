<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div class="titlediv">Import Structure Files</div>

<div align="center"><br>
<s:form action="CopyStructureFiles" method="post">


	<table>
		<tr>
			<td class="title" align="left" width="30%">Project Name&nbsp;:</td>
			<td align="left" width="35%"><s:textfield name="workSpaceDesc"
				readonly="true"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" width="30%">Structures&nbsp;:</td>
			<td align="left" width="70%"><select name="structNo">
				<option value="-1">Select Structure</option>
				<s:iterator value="getStructDtl">
					<option value="<s:property value="top"/>"><s:property
						value="top" /></option>
				</s:iterator>
			</select></td>
		</tr>





		<table align="center">
			<tr>
				<td colspan="2" align="center"><s:submit value="Import"
					cssClass="button" onclick="return validate();"></s:submit>&nbsp;</td>
				<br>
			</tr>

		</table>
	</table>
	<s:hidden name="workSpaceId"></s:hidden>
</s:form></div>
</body>
</html>
