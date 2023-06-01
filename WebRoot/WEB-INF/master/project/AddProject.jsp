<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">


function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}


	function validate()
	{
		
		var pname = document.masterAdminForm.projectName.value;
		pname=trim(pname);
		if(pname=="")
		{
			alert("Please specify Project Name.");
			document.masterAdminForm.projectName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.projectName.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.projectName.value.length>50)
		{
			alert("Project Name cannot be of more then 50 charactars.");
			document.masterAdminForm.projectName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.projectName.focus();
     		return false;
     	}
     	return true;
	}
	
	function openlinkEdit(projectCode,projectName)
    {
    	
    	var editProjectWindow = "EditProject.do?projectCode="+projectCode+"&projectName="+projectName;
    	win3=window.open(editProjectWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=150,width=400,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(150/2));
    	return true;
    }
    
    function authenticate(status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you really want to Activate selected ProjectType ?");
		}else{
			var okCancel = confirm("Do you really want to InActivate selected ProjectType ?");
		}
		if(okCancel == true)
			return  true;
		else
			return false;
	}
	
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Master/Project_Type_Master.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveProjectType" method="post"
	name="masterAdminForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="projectName">Project
			Type</td>
			<td><s:textfield name="projectName" value=""></s:textfield>&nbsp;
			<s:submit name="submitBtn" value="Add" cssClass="button"
				onclick="return validate();" /></td>
		</tr>
	</table>
</s:form></div>

<%int srNo = 1; %> <br>
<table id="clientTable" align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Project Name</th>
			<th>Status</th>
			<th>ModifyOn</th>
			<th>Edit</th>
			<th>Revert</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="projectDetail" id="projectDetail" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td><%=srNo++ %></td>
			<td><s:property value="projectName" /></td>
			<td><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>
			<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
			<td><s:if test="statusIndi != 'D'">
				<div align="center"><a href="#" title="Edit"
					onclick="openlinkEdit('<s:property value="projectCode"/>','<s:property value="projectName"/>');">
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
					href="DeleteProject.do?projectCode=<s:property value="projectCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate"
					href="DeleteProject.do?projectCode=<s:property value="projectCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"
					href="DeleteProject.do?projectCode=<s:property value="projectCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="Activate" src="images/Common/active.png"
					height="18px" width="18px"> </a>
			</s:else></div>
			</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
</div>


</body>
</html>
