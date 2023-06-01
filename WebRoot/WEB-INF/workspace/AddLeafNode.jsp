<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<SCRIPT type="text/javascript">

	function closeWindow()
	{
		window.close();
	}
	
	function validation(operation)
	{
		if(document.workspaceNodeAttrForm.nodeName.value=="")
		{
			alert("Enter Node Name.");
   			document.workspaceNodeAttrForm.nodeName.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.nodeName.focus();
    		return false;
		}
		if(document.workspaceNodeAttrForm.nodeName.value.length>100)
		{
			alert("Node Name cannot be of more then 100 charactars.");
   			document.workspaceNodeAttrForm.nodeName.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.nodeName.focus();
    		return false;
		}
		
		if(document.workspaceNodeAttrForm.nodeDisplayName.value=="")
		{
			alert("Enter Node Display Name.");
			document.workspaceNodeAttrForm.nodeDisplayName.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.nodeDisplayName.focus();
    		return false;
		}
		if(document.workspaceNodeAttrForm.nodeDisplayName.length>100)
		{
			alert("Node Display Name cannot be of more then 100 charactars.");
			document.workspaceNodeAttrForm.nodeDisplayName.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.nodeDisplayName.focus();
    		return false;
		}
		if(document.workspaceNodeAttrForm.folderName.value=="")
		{
			alert("Enter Folder Name.");
			document.workspaceNodeAttrForm.folderName.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.folderName.focus();
    		return false;		
		}
		if(document.workspaceNodeAttrForm.remark.value=="")
		{
			alert("Please specify Reason for Change.");
			document.workspaceNodeAttrForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.remark.focus();
    		return false;		
		}

		document.workspaceNodeAttrForm.operation.value = operation;
		
		
		if(document.getElementById("sftnodes").checked==true)
			document.getElementById("STFNode").value=document.getElementById("sftnodes").value;
		else
			document.getElementById("STFNode").value="N";
		
		return true;
	}

</SCRIPT>


<s:head />
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 300px; border-top: none;"
	align="center">
<div class="boxborder"><div class="all_title"><b style="float:left">Add Leaf Node</b><br/>

<div align="center"><br>
<s:form action="SaveLeafNodes" method="post"
	name="workspaceNodeAttrForm">
	<div class="boxborder"><div class="all_title"><b>Node Details</b></div>
	<!-- <div align="center" class="headercls"><b>Node Details</b></div> -->
	<br>
	<table align="center" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Node Name</td>
			<td><s:textfield name="nodeName" size="80%"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Node Display Name</td>
			<td><s:textfield name="nodeDisplayName" size="80%"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Folder Name</td>
			<td><s:textfield name="folderName" size="80%"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Reason for Change</td>
			<td><TEXTAREA rows="4" style="width: 280px;" name="remark"></TEXTAREA></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
			
			<s:if test ="eTMFCustomization!='yes'">
			 <input type="checkbox" name="sftnodes" value="S">&nbsp; <font color="navy">Is
			STF</font> &nbsp;&nbsp;&nbsp;</s:if>
			
			<input type="checkbox" name="isPublishable" onchange="this.checked = !this.checked" value="Y" checked="checked">&nbsp; <font color="navy">Is
			Publishable</font> &nbsp;&nbsp;&nbsp; 
			
			<s:if test="appType.equalsIgnoreCase('0001')">
				<input type="checkbox" name="isOnlyFolder" value="Y"
					<s:if test="nodeTypeIndi=='F'"> checked="checked"</s:if> />&nbsp;
									<font color="navy">Skip XML Node</font>
									&nbsp;&nbsp;&nbsp;
								</s:if></td>
		</tr>
		<tr>
			<td align="center" colspan="2"><s:submit
				onclick="return validation(1);" cssClass="button"
				value="Add Node Last"></s:submit> &nbsp;&nbsp;&nbsp; <s:if
				test="nodeId == 1">
				<s:submit onclick="return validation(2);" cssClass="button"
					value="Add Node Before" disabled="true"></s:submit>
										&nbsp;&nbsp;&nbsp;
				<s:submit onclick="return validation(3);" cssClass="button"
					value="Add Node After" disabled="true"></s:submit>
			</s:if> <s:else>
				<s:submit onclick="return validation(2);" cssClass="button"
					value="Add Node Before"></s:submit>
										&nbsp;&nbsp;&nbsp;
										<s:submit onclick="return validation(3);" cssClass="button"
					value="Add Node After"></s:submit>
			</s:else> &nbsp;&nbsp;&nbsp; <s:submit onclick="return closeWindow();"
				cssClass="button" value="Close"></s:submit></td>
		</tr>

	</table>

	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="operation" value="-"></s:hidden>
	<s:hidden name="STFNode" value="N"></s:hidden>
</div>
</s:form></div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>

