<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">

function validate()
	{
	
	
		if(document.attributeForm.attributeName.value=="") 
		{
			alert("Please select attribute Group Name..");
			document.attributeForm.attributeName.style.backgroundColor="#FFE6F7"; 
     		document.attributeForm.attributeName.focus();
     		return false;
     	}
     	
     	if(document.attributeForm.attributeName.value.length>50) 
		{
			alert("Group name cannot be of more then 50 charactars");
			document.attributeForm.attributeName.style.backgroundColor="#FFE6F7"; 
     		document.attributeForm.attributeName.focus();
     		return false;
     	}
     	if(document.attributeForm.projectCode.value=="-1") 
		{
			alert("Please select project type..");
			document.attributeForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.attributeForm.projectCode.focus();
     		return false;
     	}
		
		if(document.sopTrainingForm.userGroupName.value=="") 
		{
			alert("Please select User Group Name..");
			document.sopTrainingForm.userGroupName.style.backgroundColor="#FFE6F7"; 
     		document.sopTrainingForm.userGroupName.focus();
     		return false;
     	}
     	if(document.sopTrainingForm.locationCode.value=="-1")
		{
			alert("Please select Location Code..");
			document.sopTrainingForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.sopTrainingForm.locationCode.focus();
     		return false;
     	}
     	if(document.sopTrainingForm.deptCode.value=="-1")
		{
			alert("Please specify Department name..");
			document.sopTrainingForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.sopTrainingForm.deptCode.focus();
     		return false;
     	}     	
     	return true;
	}
	
	function authenticate(status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you really want to Activate selected Group ?");
		}else{
			var okCancel = confirm("Do you really want to Inactivate selected Group ?");
		}
		if(okCancel == true)
			return  true;
		else
			return false;
	}
	
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
  	
  	function EdittrainingGroup(trainingGroupId)
    {
    	var edittrainingWindow = "EditTrainingGroup.do?trainingGroupId="+trainingGroupId;    	
    	win3 = window.open(edittrainingWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=250,width=600,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(600/2),screen.availHeight/2-(250/2));
    	return true;
    }
  	function WriteToFile()
	{	
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		var exportPath = prompt("Enter full path to save report.","");

		if(exportPath!=null)
		{
			var newObject = fso.OpenTextFile(exportPath, 2, true,0);  // 2 For overwrite the file.
			newObject.write(document.getElementById("exportToExcel").innerHTML)
			alert("Training group detail report successfully exported at " + exportPath );
			newObject.close()
		}	
	}
	function ViewAttrValues(attrGroupId)
	{
	
		var viewAttrValues = "ViewAttrValues.do?attrGroupId="+attrGroupId;    	
		win3 = window.open(viewAttrValues,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=380,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(370/2));
    	return true;
	}
	function AddAttrToGroup(attrGroupId,attrGroupName)
	{
		
		var addAttrToGroup = "AddAttrToGroup.do?attrGroupId="+attrGroupId+"&attrGroupName="+attrGroupName;    	
    	win3 = window.open(addAttrToGroup,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=380,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(370/2));
    	return true;
	}
	function EditAttrGroup(attrGroupId,attrGroupName)
	{
	
		var editAttrGroup = "EditAttrGroup.do?attrGroupId="+attrGroupId+"&attrGroupName="+attrGroupName;    	
    	win3 = window.open(editAttrGroup,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=200,width=400,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(400/2),screen.availHeight/2-(200/2));
    	return true;
	}
	
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Master/Add_Attribute_Group.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveAttributeGroup"
	method="post" name="attributeForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Group Name</td>
			<td align="left"><s:textfield name="attributeName" size="50"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><s:select list="getProjectType"
				name="projectCode" headerKey="-1" headerValue="Select Project Type"
				listKey="projectCode" listValue="projectName">
			</s:select></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"></td>
			<td align="left"><s:submit value="Add Attribute Group"
				cssClass="button" onclick="return validate();" /></td>
		</tr>
	</table>
</s:form></div>

<%int srNo = 1; %> <br>
<table id="clientTable" align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>SrNo</th>
			<th>Attribute Group</th>
			<th>Project Type</th>
			<th>Status</th>
			<th>ModifyOn</th>
			<th>View</th>
			<th>Add</th>
			<th>Edit</th>
			<th>Revert</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="getAttrGroupDetail" id="getAttrGroupDetail"
			status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td><%=srNo++ %></td>
			<td><s:property value="attrGroupName" /></td>
			<td><s:property value="projectName" /></td>
			<td><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>

			<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
			<td>
			<div align="center"><a href="#" title="View"
				onclick="return ViewAttrValues('<s:property value="attrGroupId"/>');">
			<img border="0px" alt="View" src="images/Common/view.png"
				height="18px" width="18px"> </a></div>
			</td>
			<td>
			<div align="center"><a href="#" title="Add"
				onclick="return AddAttrToGroup('<s:property value="attrGroupId"/>','<s:property value="attrGroupName"/>');">
			<img border="0px" alt="Add" src="images/Common/add.png" height="18px"
				width="18px"> </a></div>
			</td>
			<td>
			<div align="center"><a href="#" title="Edit"
				onclick="return EditAttrGroup('<s:property value="attrGroupId"/>','<s:property value="attrGroupName"/>');">
			<img border="0px" alt="Edit" src="images/Common/edit.png"
				height="18px" width="18px"> </a></div>
			</td>
			<td>
			<div align="center"><s:if test="statusIndi == 'E'">
				<a title="InActivate"
					href="DeleteAttributeGroup.do?attrGroupId=<s:property value="attrGroupId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate"
					href="DeleteAttributeGroup.do?attrGroupId=<s:property value="attrGroupId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"
					href="DeleteAttributeGroup.do?attrGroupId=<s:property value="attrGroupId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="Activate" src="images/Common/active.png"
					height="18px" width="18px"> </a>
			</s:else></div>
			</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br></br>
</div>
</div>

</body>
</html>
