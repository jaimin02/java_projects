<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<script language="javascript">

function validate(){

	   if(document.createDeleteStructureForm.templateDesc.value=="")
		{
	    	alert("Please specify Template name..");
	    	document.createDeleteStructureForm.templateDesc.style.backgroundColor="#FFE6F7"; 
			document.createDeleteStructureForm.templateDesc.focus();
			return false;
	    }
	    			
	    if(document.createDeleteStructureForm.templateDesc.value.length>250)
	    {
	    	alert("template name cannot be of more than 250 charactars..");
	    	document.createDeleteStructureForm.templateDesc.style.backgroundColor="#FFE6F7"; 
			document.createDeleteStructureForm.templateDesc.focus();
			return false;
	    }
	    return true;
	    		
	}
	
function authenticate(status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you really want to Activate selected Template ?");
		}else{
			var okCancel = confirm("Do you really want to InActivate selected Template ?");
		}
		if(okCancel == true)
			return  true;
		else
			return false;
	}
	
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Structure/Create_Template.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveTemplate" method="post"
	name="createDeleteStructureForm" enctype="multipart/form-data">
</s:form></div>



<br>
<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>Template Name</th>
			<th>Status</th>
			<th>ModifyOn</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>View</th>
			
		</tr>
	</thead>
	<tbody>
		<s:iterator value="templateDetail" id="templateDetail" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td>${status.count}</td>
			<td><s:property value="templateDesc" /></td>
			<td><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>
			<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> -->
			<td><s:property value="ISTDateTime" /></td> 
		<s:if test ="#session.countryCode != 'IND'">
			<td><s:property value="ESTDateTime" /></td>
		</s:if>
			<td><s:if test="statusIndi != 'D'">
				<div align="center"><a title="Edit"
					href="OpenStructure.do?templateId=<s:property value="templateId" />">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>
			</s:if> <s:else>
				<div align="center"><a title="Edit"> <img border="0px"
					alt="Edit" src="images/Common/edit.png" height="18px" width="18px">
				</a></div>
			</s:else></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<br>
</div>
</div>
</body>
</html>
