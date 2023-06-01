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
		var lname = document.masterAdminForm.locationName.value;
		lname=trim(lname);
		if(lname=="")
		{
			alert("Please specify Region Name..");
			document.masterAdminForm.locationName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationName.focus();
     		return false;
     	}
		
     	
     	if(document.masterAdminForm.locationName.value.length>50)
		{
			alert("Region Name cannot be of more then 50 charactars..");
			document.masterAdminForm.locationName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.locationName.focus();
     		return false;
     	}
     	return true;
	}
	
	function openlinkEdit(locationCode,locationName)
    {
    	
    	var editLocation = "EditLocation.do?locationCode="+locationCode+"&locationName="+locationName;
   		win3=window.open(editLocation,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=150px;,width=400px;,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(150/2));	
    	return true;
    }
    
    function authenticate(status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you really want to Activate selected Region ?");
		}else{
			var okCancel = confirm("Do you really want to Inactivate selected Region ?");
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
	src="images/Header_Images/Master/Location_Master.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveLocation" method="post"
	name="masterAdminForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="locationName">Region
			Name</td>
			<td><s:textfield name="locationName" value=""></s:textfield>&nbsp;
			<s:submit name="submitBtn" value="Add" cssClass="button"
				onclick="return validate();" /></td>
		</tr>
	</table>
</s:form></div>


<br>
<div align="center">
<table id="clientTable" width="95%" class="datatable" cellspacing="2">

	<tr>
		<th>#</th>
		<th>Region Name</th>
		<th>Status</th>
		<th>ModifyOn</th>
		<th>Edit</th>
		<th>Revert</th>

	</tr>

	<s:iterator value="locationDetail" id="locationDetail" status="status">
		<s:hidden value="statusIndi" id="statusIndi" />

		<tr
			class="<s:if test="statusIndi == 'D'">matchFound</s:if><s:elseif test="#status.even">even</s:elseif><s:else>odd</s:else>">

			<td>${status.count}</td>
			<td><s:property value="locationName" /></td>
			<td><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>
			<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
			<td><s:if test="statusIndi != 'D'">
				<div align="center"><a href="#" title="Edit"
					onclick="openlinkEdit('<s:property value="locationCode"/>','<s:property value="locationName"/>');">
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
					href="DeleteLocation.do?locationCode=<s:property value="locationCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi"/>');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate"
					href="DeleteLocation.do?locationCode=<s:property value="locationCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi"/>');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"
					href="DeleteLocation.do?locationCode=<s:property value="locationCode" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi"/>');">
				<img border="0px" alt="Activate" src="images/Common/active.png"
					height="18px" width="18px"> </a>
			</s:else></div>
			</td>
		</tr>
	</s:iterator>

</table>
</div>

</div>
</div>

</body>
</html>
