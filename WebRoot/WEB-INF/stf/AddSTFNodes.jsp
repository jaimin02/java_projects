<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">

	function validate()
	{
		if (document.STFForm.nodename.value=="")
		{
			alert("Please Node Name..")
			document.STFForm.nodename.style.backgroundColor="#FFE6F7"; 
     		document.STFForm.nodename.focus();
			return false;
		}
		
		if (document.STFForm.nodename.value.length>50)
		{
			alert("Node name cannot be of more then 50 charactars")
			document.STFForm.nodename.style.backgroundColor="#FFE6F7"; 
     		document.STFForm.nodename.focus();
			return false;
		}
		
		if (document.STFForm.remark.value.length>250)
		{
			alert("Remark cannot be of more then 250 charactars")
			document.STFForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.STFForm.remark.focus();
			return false;
		}
		if(document.STFForm.category.value=="-1")
		{
			alert("Please select Category Type..");
			document.STFForm.category.style.backgroundColor="#FFE6F7"; 
     		document.STFForm.category.focus();
     		return false;
     	}     	
	     	return true;
	}	
	
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
  	function WriteToFile()
	{	
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		var exportPath = prompt("Enter full path to save report...","");

		if(exportPath!=null)
		{
			var newObject = fso.OpenTextFile(exportPath, 2, true,0);  // 2 For overwrite the file.
			newObject.write(document.getElementById("exportToExcel").innerHTML)
			alert("STF Nodes Details report successfully exported at " + exportPath );
			newObject.close()
		}	
	}
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<div class="titlediv">Add STF Nodes</div>
<div align="center"><br>
<s:form action="SaveSTFNodes" method="post" name="STFForm">
	<table width="25%">
		<tr>
			<td align="left" width="40%" class="title">Node Name</td>
			<td align="left" width="60%"><s:textfield name="nodename"></s:textfield>
			</td>
		</tr>
		<tr>
			<td class="title" align="left">Category</td>
			<td align="left"><select name="category">
				<option value="-1">select Category</option>
				<option value="ich">ICH</option>
				<option value="jp">JP</option>
				<option value="us">US</option>
			</select></td>
			<tr>

				<tr>
					<td class="title" align="left">Remarks</td>
					<td align="left"><s:textfield name="remark"></s:textfield></td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><s:submit value="Add STF Node"
						onclick="return validate();" cssClass="button"></s:submit></td>
				</tr>
	</table>
</s:form></div>

<%int srNo = 1; %>
<br>

<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Node Name</th>
			<th>Category</th>
			<th>Remarks</th>
			<th>ModifyOn</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="stfnode" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><%=srNo++ %></td>
				<td><s:property value="nodeName" /></td>
				<td><s:property value="nodeCategory" /></td>
				<td><s:property value="remark" /></td>
				<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</body>
</html>
