<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">

	function validate()
	{
		if(document.masterAdminForm.stageDesc.value=="")
		{
			alert("Please specify Stage name");
			document.masterAdminForm.stageDesc.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.stageDesc.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.stageDesc.value.length>50)
		{
			alert("Stage name cannot be of more then 50 charactars");
			document.masterAdminForm.stageDesc.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.stageDesc.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.inclusive.value == -1)
     	{
     		alert("Please Selecte Inclusive");
     		document.masterAdminForm.inclusive.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.inclusive.focus();
     		return false;
     	}
     	
     	
     	return true;
	}
	
	function openlinkEdit(stageId,stageDesc)
    {
    	var editLocationWindow = "EditStage.do?stageId="+stageId+"&stageDesc="+stageDesc;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=215,width=500,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(200/2));
    	return true;
    }
    
  
    function authenticate(status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you really want to Activate selected Stage ?");
		}else{
			var okCancel = confirm("Do you really want to InActivate selected Stage ?");
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

<div class="titlediv">Add Stage</div>
<div align="center"><br>
<s:form action="SaveStage" method="post" name="masterAdminForm">
	<table width="25%">
		<tr>
			<td align="left" width="50%" class="title">Stage Name</td>
			<td align="left" width="50%"><s:textfield name="stageDesc"
				value=""></s:textfield></td>

		</tr>
		<tr>
			<td class="title" align="left">Inclusive</td>
			<td align="left"><select name="inclusive">
				<option value="-1">Select Yes/No</option>
				<option value="Y">Yes</option>
				<option value="N">No</option>
			</select></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>

		<tr>
			<td></td>
			<td align="left"><s:submit name="submitBtn" value="Add"
				cssClass="button" onclick="return validate();" /></td>
		</tr>

	</table>
</s:form></div>



<br>
<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Stage Name</th>
			<th>Inclusive</th>
			<th>Status</th>
			<th>ModifyOn</th>
			<th>Edit</th>
			<th>Revert</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="stageDetail" id="stageDetail" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:hidden value="inclusive" id="inclusive" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td>${status.count}</td>
			<td><s:property value="stageDesc" /></td>

			<td><s:if test="inclusive == 'Y'">Yes</s:if> <s:else>No</s:else>
			</td>

			<td><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>
			<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>

			<td><s:if test="statusIndi != 'D'">
				<div align="center"><a href="#" title="Edit"
					onclick="openlinkEdit('<s:property value="stageId"/>','<s:property value="stageDesc"/>');">
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
					href="DeleteStage.do?stageId=<s:property value="stageId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate"
					href="DeleteStage.do?stageId=<s:property value="stageId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"
					href="DeleteStage.do?stageId=<s:property value="stageId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="Activate" src="images/Common/active.png"
					height="18px" width="18px"> </a>
			</s:else></div>
			</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</body>
</html>
