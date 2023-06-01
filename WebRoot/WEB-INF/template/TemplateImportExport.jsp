<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<SCRIPT type="text/javascript">
  function validate(action)
  {
  
  if(action==1)
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
  if(action==2)
  {
  	
  		if(document.templateForm.XmlData.value=='')
		{
			alert("Please select XML File");
			
			document.templateForm.XmlData.style.backgroundColor="#FFE6F7"; 
     		document.templateForm.XmlData.focus();
     		return false;
     	}
  	
  		
		if(document.templateForm.templatedesc.value=='')
		{
			alert("Please Enter Template Desc");
			
			document.templateForm.templatedesc.style.backgroundColor="#FFE6F7"; 
     		document.templateForm.templatedesc.focus();
     		return false;
     	}
		  		
		  		
		var str=document.templateForm.XmlData.value;  		 
		var strary=str.split(".");  		
		 if(strary[strary.length-1] != "xml")
		 {
			alert("Only XML Files Are Allowed!"); 
		 	return false;
		 } 		
		  		
		  		
		  		
  		
    	return true;
  	
  
  }
  
  
    }
  </SCRIPT>
</head>

<body>
<div align="center" style="color: green; size: 50px"><s:actionmessage />

</div>

<br />
<div align="center"><img
	src="images/Header_Images/Structure/ImportExport_Structure.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<br>
<div class="titlediv">Export Template</div>
<br>
<div align="center"><s:form action="Generatexml" method="post"
	name="templateForm" enctype="multipart/form-data">
	<table width="45%">

		<tr align="left">
			<td class="title" width="30%">Select Template</td>
			<td width="60%"><s:select list="TemplateDetails"
				name="templateId" headerKey="-1" headerValue="Please Select"
				listKey="templateId" listValue="templateDesc" value="-1">
			</s:select></td>
			<td width="0%"><s:submit value="Export" cssClass="button"
				onclick="return validate(1);" name="buttonName"></s:submit></td>

		</tr>

	</table>

	<br>
	<br>
	<br>
	<br>

	<hr color="#5A8AA9" size="3px"
		style="width: 85%; border-bottom: 2px solid #CDDBE4;">

	<br>
	<br>
	<div class="titlediv">Import Template</div>
	<br>
	<table width="45%">
		<tr align="left">
			<td class="title" width="30%">Template Name</td>
			<td width="60%"><s:textfield name="templatedesc" value=""></s:textfield>
			</td>
			<td width="10%">&nbsp;</td>
		</tr>
		<tr align="left">
			<td class="title">Select XML File</td>
			<td><s:file name="XmlData" accept="text/xml"></s:file>
			&nbsp;&nbsp;</td>
			<td><s:submit value="Import" cssClass="button"
				onclick="return validate(2);" name="buttonName"></s:submit></td>
		</tr>

	</table>
</s:form></div>
<br>
<br>
<br>
<br>
</div>
</div>

</body>
</html>
