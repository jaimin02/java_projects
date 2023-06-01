<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<SCRIPT type="text/javascript">

function validate()
	{
	
		if(document.attributeForm.projectCode.value==-1)
		{
			alert("Please select project type ");
			document.attributeForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.attributeForm.projectCode.focus();
     		return false;
     	}
     	
		else if(document.attributeForm.atttrGroupName.value=="")
		{
			alert("Please specify group name..");
			document.attributeForm.atttrGroupName.style.backgroundColor="#FFE6F7"; 
     		document.attributeForm.atttrGroupName.focus();
     		return false;
     	}
     	
     	return true;
	}
	


</SCRIPT>


</head>
<body>
<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<div align="center" class="maindiv"><br>
<div class="headercls">Edit Attribute Group</div>

<s:form action="UpdateAttributeGroup" method="post" name="attributeForm">


	<td valign="top" align="center">
	<table class="channelcontent" width="100%" align="center">
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="title" align="right" width="35%"
				style="padding: 2px; padding-right: 8px;">Group Name</td>
			<td align="left"><s:textfield name="attrGroupName" size="22"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="35%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><s:select list="getProjectType" headerKey="-1"
				headerValue="Select Project Type" listKey="projectCode"
				listValue="projectName" name="projectCode">

			</s:select></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr align="center">
			<td></td>
			<td align="left"><s:submit value="Update" cssClass="button"
				onclick="return validate();" /></td>
		</tr>

		<s:hidden name="attrGroupId">
		</s:hidden>
	</table>
	</td>

</s:form></div>

</body>
</html>
