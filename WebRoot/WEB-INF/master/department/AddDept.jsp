<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<style type="text/css">
/* .datatable_filter input{
background-color: #fff;
color:#000;
}
.datatable_length select {
background-color: #fff;
color:#000;
} */
</style>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<s:head />

<script type="text/javascript">

$(document).ready(function() { 
	 $('#clientTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
} );

function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
	
	function validate()
	{
		var dname = document.masterAdminForm.deptName.value;
		dname=trim(dname);
		if(dname=="")
		{
			alert("Please add Department.");
			document.masterAdminForm.deptName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.deptName.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.deptName.value.length>50)
		{
			alert("Department name cannot be of more then 50 charactars.");
			document.masterAdminForm.deptName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.deptName.focus();
     		return false;
     	}
     	return true;
	}
	function openlinkEdit(deptCode,deptName)
    {
    	
    	var editDept = "EditDept.do?deptCode="+deptCode+"&deptName="+deptName;
    	win3=window.open(editDept,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=150,width=400,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(150/2));
    	return true;
    }
    
   function authenticate(deptCode,status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you want to activate selected Department.");
		}else{
			var okCancel = confirm("Do you want to inactive selected Department.");
		}
		if(okCancel == true)
		{
			var remark = prompt("Please specify reason for change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
				var revertWindow = "DeleteDept.do?deptCode="+deptCode+"&statusIndi="+status+"&remark="+remark;
		   		window.location.href=revertWindow;
				return true;	
			}
			else if(remark==""){
				//debugger;
				alert("Please specify reason for change.");
				return false;
			}
		}	
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
	
	function deptHistory(deptCode)
	{
		str="showDeptDetailHistory_b.do?deptCode=" + deptCode;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
	 	win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
		return true;
	}
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div class="container-fluid">
<div class="col-md-12">

<!-- <div align="center"><img
	src="images/Header_Images/Master/Department_Master.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div class="boxborder"><div class="all_title"><b style="float:left">Department Master</b></div>
<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div class="datatablePadding" style="width: 100%; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveDept" method="post"
	name="masterAdminForm">
	<table width="100%" align="center">
		<tr>
			<td id="deptName" class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Department</td>
			<td><s:textfield name="deptName" value=""></s:textfield></td>
		</tr>
		<tr><td colspan="2" align ="center">
		    <s:submit name="submitBtn" value="Add" cssClass="button" onclick="return validate();" />
		</td></tr>
	</table>
</s:form></div>

<br>
<table id="clientTable" width="100%" align="center" class="datatable paddingtable" >
	<thead>
		<tr>
			<th style="border: 1px solid black;" class="datatableSrNo">#</th>
			<th style="border: 1px solid black;">Department</th>
			<th style="border: 1px solid black;">Current Status</th>
			<th style="border: 1px solid black;">Modified on</th>
			<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
			</s:if>
			<!-- <th>Edit</th> -->
			<th style="border: 1px solid black;">Change Status to</th>
			<th style="border: 1px solid black;"></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="deptDetail" id="deptDetail" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td style="border: 1px solid black;">${status.count}</td>
			<td style="border: 1px solid black;"><s:property value="deptName" /></td>
			<td style="border: 1px solid black;"><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Inactive</s:elseif>
				<s:elseif test="statusIndi == 'A'">Active</s:elseif>
				 <s:else>New</s:else></td>
			<!-- <td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td> -->
			<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
			</s:if>
			<%-- <td><s:if test="statusIndi != 'D'">
				<div align="center"><a href="#" title="Edit"
					onclick="openlinkEdit('<s:property value="deptCode"/>','<s:property value="deptName"/>');">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>
			</s:if> <s:else>
				<div align="center"><a title="Edit"> <img border="0px"
					alt="Edit" src="images/Common/edit.png" height="18px" width="18px">
				</a></div>
			</s:else></td> --%>
			<td style="border: 1px solid black;">
			<div align=""><s:if test="statusIndi == 'E' || statusIndi == 'A'">
				<a title="InActivate" href="javascript:void(0);"
					onclick="authenticate('<s:property value="deptCode" />','<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.svg"
					height="25px" width="25px"> </a>
			</s:if> <s:elseif test="statusIndi == 'N'">
				<a title="InActivate" href="javascript:void(0);"
					onclick="authenticate('<s:property value="deptCode" />','<s:property value="statusIndi" />');">
				<img border="0px" alt="InActivate" src="images/Common/inactive.svg"
					height="25px" width="25px"> </a>
			</s:elseif> <s:else>
				<a title="Activate"	href="javascript:void(0);"
					onclick="authenticate('<s:property value="deptCode" />','<s:property value="statusIndi" />');">
				<img border="0px" alt="Activate" src="images/Common/active.svg"
					height="25px" width="25px"> </a>
			</s:else></div>
			</td>
			<td style="border: 1px solid black;">
				<div><a title="Department History" onclick="deptHistory('<s:property value="deptCode"/>');"
							href="#">
						<img border="0px" alt="Audit Trail" src="images/Common/details.svg"
							height="25px" width="25px"> </a></div>
			</td>
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
