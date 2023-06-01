<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<s:head />
<script language="javascript">

$(document).ready(function() { 
	 $('#templateTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
} );

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
<div class="container-fluid">
<div class="col-md-12">
<div align="center">
<!-- <img src="images/Header_Images/Structure/Create_Template.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

<div class="boxborder"><div class="all_title"><b style="float:left">Create Documents</b></div>

<div class="datatablePadding" style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<%-- <div align="left"><s:form action="SaveTemplate" method="post"
	name="createDeleteStructureForm" enctype="multipart/form-data">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Import File</td>
			<td align="left"><s:file name="uploadFile" size="50%"></s:file></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="templateName">Template
			Name</td>
			<td><s:textfield name="templateName"></s:textfield>&nbsp;&nbsp;
			<s:submit value="Save" cssClass="button" /></td>
		</tr>
	</table>
</s:form></div> --%>



<br>
<table id="templateTable"  align="center" width="100%" class="datatable">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Template Name</th>
			<th style="border: 1px solid black;">Status</th>
			<th style="border: 1px solid black;">Modify on</th>
			<th style="border: 1px solid black;">Edit</th>
			<!-- <th>Revert</th> -->

		</tr>
	</thead>
	<tbody>
		<s:iterator value="templateDetail" id="templateDetail" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr	class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td style="border: 1px solid black;">${status.count}</td>
			<td style="border: 1px solid black;"><s:property value="templateDesc" /></td>
			<td style="border: 1px solid black;"><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>
			<td style="border: 1px solid black;"><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td>
			<td style="border: 1px solid black;"><s:if test="statusIndi != 'D'">
				<div align="center"><a title="Edit"
					href="OpenStructureDocuments.do?templateId=<s:property value="templateId" />">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="25px" width="25px"> </a></div>
			</s:if> <s:else>
				<div align="center"><a title="Edit"> <img border="0px"
					alt="Edit" src="images/Common/edit.png" height="25px" width="25px">
				</a></div>
			</s:else></td>
			<%-- <td>
			<div align="center"><s:if test="statusIndi == 'E'">
				<a title="InActivate"
					href="DeleteTemplate.do?templateId=<s:property value="templateId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate"
					href="DeleteTemplate.do?templateId=<s:property value="templateId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.png"
					height="18px" width="18px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"
					href="DeleteTemplate.do?templateId=<s:property value="templateId" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">
				<img border="0px" alt="Activate" src="images/Common/active.png"
					height="18px" width="18px"> </a>
			</s:else></div>
			</td> --%>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
