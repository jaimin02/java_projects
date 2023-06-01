<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script language="javascript">

  function validate()
  {
  	if(document.attributeForm.attributeValue.value=="")
  	{
  		alert("Please specify attribute value");
  		document.attributeForm.attributeValue.style.backgroundColor="#FFE6F7"; 
  		document.attributeForm.attributeValue.focus();
  		return false;
  	}
  	if(document.attributeForm.attributeValue.value.length>50)
  	{
  		alert("attribute value cannot be of more then 50 charactars");
  		document.attributeForm.attributeValue.style.backgroundColor="#FFE6F7"; 
  		document.attributeForm.attributeValue.focus();
  		return false;
  	}
  }	
 function refreshParent()
     {
     	opener.history.go(0);
		self.close();
     }
  
  function callonBlur(t)
  {
  	t.style.backgroundColor='white';
  }
     
</script>
<s:head />
</head>
<body onunload=" return refreshParent(); ">

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>


<div align="center"><br>
<s:form action="saveMoreAttribute" method="post" name="attributeForm">

	<%
		String attrId = request.getParameter("attrId");
	%>

	<table align="center" width="95%">
		<tr>
			<td class="title">Value</td>
			<td><s:textfield name="attributeValue"></s:textfield></td>
		</tr>
		<tr align="center">
			<td><s:submit onclick="return validate();" value="Save"
				cssClass="button" /></td>
		</tr>
	</table>
	<input type="hidden" name="attrId" value="<%=attrId%>">




</s:form></div>
</body>
</html>

