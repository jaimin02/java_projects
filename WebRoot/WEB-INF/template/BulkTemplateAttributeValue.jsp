<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<s:head />
<SCRIPT type="text/javascript">
  
  	function validate()
  	{
  		if(document.templateForm.attrId.value==-1)
  		{
  			alert("Select Attribute Name");
  			document.templateForm.attrId.style.backgroundColor="#FFE6F7"; 
			document.templateForm.attrId.focus();
			return false;
  		}
  		return true;
  	}
  	
  	function SaveValue(nodeId,attrId,templateId)
  	{
  		var attributeValue = document.getElementById(nodeId).value;
  		window.location.href = "SaveBulkAttrValue.do?nodeId="+nodeId+"&attributeValue="+attributeValue+"&attrId="+attrId+"&templateId="+templateId+"&Flag=true";
  		return true;
  	}
  	
  </SCRIPT>

</head>

<body>
<div class="titlediv">Bulk Template Attribute Value</div>
<div align="center"><br>
<s:form action="SaveBulkAttrValue" method="post" name="templateForm">
	<table width="35%">

		<tr align="left">
			<td class="title">Select Template</td>
			<td><s:select list="getTemplateDetail" name="templateId"
				headerKey="-1" headerValue="Please Select Template"
				listKey="templateId" listValue="templateDesc">
			</s:select></td>

		</tr>

		<tr align="left">
			<td class="title">Select Attribute</td>

			<td><s:select list="getTempAllAttributes"
				onchange="return validate();" name="attrId" headerKey="-1"
				headerValue="Please Select Attribute" listKey="attrId"
				listValue="attrName">
			</s:select><ajax:event ajaxRef="bulkAttrValue/templateAttrValue" /></td>
		</tr>



	</table>

	<table align="center" width="100%">
		<tr>
			<td>
			<div id="BulkTemplate"></div>
			</td>
		</tr>
	</table>


	<ajax:enable />
</s:form></div>
</body>
</html>
