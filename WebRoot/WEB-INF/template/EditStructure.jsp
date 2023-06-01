<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<SCRIPT type="text/javascript">
  function validate()
  {
  
  	 if(document.templateForm.templateId.value==-1)
		{
			alert("Please select Template name..");
			document.templateForm.templateId.style.backgroundColor="#FFE6F7"; 
     		document.templateForm.templateId.focus();
     		return false;
     	}
  	return true;
  }
  </SCRIPT>
</head>

<body>
<div class="titlediv">Edit Template</div>
<div align="center"><s:form action="OpenStructure" method="get"
	name="templateForm">
	<table width="40%">

		<tr>
			<td class="title" align="left">Select Template &nbsp;</td>
			<td align="left"><s:select list="TemplateDetails"
				name="templateId" headerKey="-1" headerValue="Please Select"
				listKey="templateId" listValue="templateDesc">
			</s:select></td>
			<td align="left"><s:submit value="select" cssClass="button"
				onclick="return validate();"></s:submit></td>
		</tr>


	</table>


</s:form></div>
</body>
</html>
