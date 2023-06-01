<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">

	function validate()
		{
	     	if(document.operationform.operationName.value=="")
			{
				alert("Please select Operation Name..");
				document.operationform.operationName.style.backgroundColor="#FFE6F7"; 
	     		document.operationform.operationName.focus();
	     		return false;
	     	} 
	     	if(document.operationform.operationPath.value=="")
			{
				alert("Please Operation Path Code..");
				document.operationform.operationPath.style.backgroundColor="#FFE6F7"; 
	     		document.operationform.operationPath.focus();
	     		return false;
	     	} 
	     	if(document.operationform.operationCode.value=="-1")
			{
				alert("Please Operation Code..");
				document.operationform.operationCode.style.backgroundColor="#FFE6F7"; 
	     		document.operationform.operationCode.focus();
	     		return false;
	     	} 
	
	 	return true;
     }    	
	
	function authenticate(status){
	
		if(status == 'I'){
			var okCancel = confirm("Do you really want to Activate selected Operation ?");
		}else{
			var okCancel = confirm("Do you really want to InActivate selected Operation ?");
		}
		if(okCancel == true)
			return  true;
		else
			return false;
	}
	
	function openlinkEdit(operationCode,operationName,operationPath,parentOperationCode)
    {
    	var editLocationWindow = "EditOperations.do?operationCode="+operationCode+"&operationName="+operationName+"&operationPath="+operationPath+"&parentOperationCode="+parentOperationCode;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=210,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(200/2));
    	return true;
    }
    
	
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<br />
<div align="center"><img
	src="images/Header_Images/Master/Set_Rol_Operations.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveOperation" method="post"
	name="operationform">
	<table width="100%">

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Operation Name</td>
			<td align="left"><s:textfield name="operationName"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Operation Path</td>
			<td align="left"><s:textfield name="operationPath"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Parent Menu</td>
			<td align="left"><s:select list="roleoperation"
				name="operationCode" headerKey="-1" headerValue="Select Parent Menu"
				listKey="operationCode" listValue="operationName">
			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Operation flag</td>
			<td class="title" align="left"><input type="radio" value="N"
				name="statusIndi" id="statusIndiA" checked="checked" /> <label
				for="statusIndiA">Active</label> &nbsp; <input type="radio"
				value="I" name="statusIndi" id="statusIndiI" /> <label
				for="statusIndiI">Inactive</label></td>
		</tr>

		<tr>
			<td></td>
			<td align="left"><s:submit onclick="return validate();"
				value="Add Operation" cssClass="button" />
		</tr>

	</table>
</s:form></div>
<%int srNo = 1; %> <br>
<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>srNo</th>
			<th>Operation Name</th>
			<th>Operation Path</th>
			<th>Status</th>
			<th>Edit</th>
			<th>Revert</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="alloperationvector" id="alloperationvector"
			status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'I'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td><%=srNo++ %></td>
			<td><s:property value="operationName" /></td>
			<td><s:property value="operationPath" /></td>
			<td><s:if test="statusIndi == 'N'">New</s:if> <s:elseif
				test="statusIndi == 'E'">Edited</s:elseif> <s:else>Deleted</s:else>
			</td>

			<td><s:if test="statusIndi =='N' || statusIndi =='E' ">
				<div align="center"><a href="#" title="Edit"
					onclick="openlinkEdit('<s:property value="operationCode"/>','<s:property value="operationName"/>','<s:property value="operationPath"/>','<s:property value="parentOperationCode"/>');">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>
			</s:if> <s:else>
				<div align="center"><a title="Edit"> <img border="0px"
					alt="Edit" src="images/Common/edit.png" height="18px" width="18px">
				</a></div>
			</s:else></td>

			<td>
			<div align="center"><s:if test="statusIndi == 'E'">
				<a title="InActivate"
					href="DeleteOperation.do?operationCode=<s:property value="operationCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate"
					href="DeleteOperation.do?operationCode=<s:property value="operationCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"
					href="DeleteOperation.do?operationCode=<s:property value="operationCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="Activate" src="images/Common/active.png"
					height="18px" width="18px"> </a>
			</s:else></div>
			</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br>
</div>
</div>

</body>
</html>
