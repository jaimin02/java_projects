<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style type="text/css">
/* #clientTable_filter input{
background-color: #2e7eb9;
color:#ffffff;
}
#clientTable_length select {
background-color: #2e7eb9;
color:#ffffff;
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
		debugger;
		var cname = document.masterAdminForm.applicationName.value;
		cname=trim(cname);
		var strChar;
		var strInvalidChars = "/\^$#:~%@&*`!;'\"+=,| ><?";
		
		if(cname=="")
		{
			alert("Please add Software Application Name.");
			document.masterAdminForm.applicationName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.applicationName.focus();
     		return false;
     	}
		
		for (i = 0; i < cname.length; i++)
    	 {
			strChar = cname.charAt(i);
			 if (strInvalidChars.indexOf(strChar)!= -1)
			 {
     			if(strChar == ' ')
     			{
     				alert("Invalid Software Application Name." + " 'space' is not allowed" );
     				return false;
     			}
     			else
     			{
      				alert("Invalid Project." + " " + strChar + "  is not allowed" );
      				return false;
      			}
			}
     	}
		
		if(document.masterAdminForm.remark.value=="")
		{
			alert("Please Specify Remark.");
			document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.remark.focus();
     		return false;
     	}
		
		if(document.masterAdminForm.fileName.value=="")
		{
			alert("Please Specify Software Application Title.");
			document.masterAdminForm.fileName.style.backgroundColor="#FFE6F7"; 
	    	document.masterAdminForm.fileName.focus();
	    	return false;
	    }
		
		if(document.masterAdminForm.uploadFile.value=="")
		{
			alert("Please Select File.");
			document.masterAdminForm.uploadFile.style.backgroundColor="#FFE6F7"; 
		   	document.masterAdminForm.uploadFile.focus();
		   	return false;
		}
	}
	
	function openlinkEdit(clientCode,clientName,hosting,category,remark)
    {
    	debugger;
    	var editLocationWindow = "EditApplication_b.do?applicationCode="+clientCode+"&applicationName="+clientName+
    			"&hostingId="+hosting+"&catgoryId="+category+"&remark="+remark;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=500,width=900,resizable=no,titlebar=no")
    	leftPosition = (window.screen.width / 2) - ((900 / 2));
    	topPosition = (window.screen.height / 2) - ((500 / 2) + 50);
    	win3.moveTo(leftPosition,topPosition);
    	return true;
    }
    
    
    function authenticate(clientCode,status)
	{
		debugger;
    	if(status == 'D'){
			var okCancel = confirm("Do you want to active selected Software Application .");
		}else{
			var okCancel = confirm("Do you want to inactive selected Software Application .");
		}
		if(okCancel == true)
		{
			var remark = prompt("Please specify reason for change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
				var revertWindow = "DeleteApplication.do?applicationCode="+clientCode+"&statusIndi="+status+"&remark="+remark;
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
	
	function clientHistory(clientCode)
	{
		str="showApplicationDetailHistory_b.do?applicationCode=" + clientCode;
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

<div align="center"><!-- <img
	src="images/Header_Images/Master/Client_Master.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
	<div class="boxborder"><div class="all_title"><b style="float:left">Software Application Group</b></div>

<div class="datatablePadding" style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveApplicationGroup" method="post"  enctype="multipart/form-data" name="masterAdminForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="applicationName">Software Application Group</td>
			<td  align="left"><s:textfield name="applicationName" value=""></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="hostingId">Hosting</td>
			<td  align="left">
				<select name="hostingId">
				<!-- <option value="-1">Select Tree Structure</option> -->
				 <s:iterator value="applicationHostDetail">
					<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
					<s:if test="statusIndi != 'D'"> 
					<option value="<s:property value="hostingCode"/>"><s:property value="hostingName" /></option>
					 </s:if>
				</s:iterator> 
			</select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="catgory">Category</td>
			<td  align="left">
				<select name="catgoryId">
				<!-- <option value="-1">Select Tree Structure</option> -->
				<s:iterator value="applicationCategoryDetail">
					<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
					<s:if test="statusIndi != 'D'"> 
						<option value="<s:property value="categoryCode"/>"><s:property value="categoryName" /></option>
					 </s:if>
				</s:iterator> 
			</select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="remark">Remark</td>
			<td  align="left"><s:textfield name="remark" value=""></s:textfield></td>
		</tr>
		<%-- <tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="fileName">Software/Application Title</td>
			<td  align="left">
				<s:textfield name="fileName" value=""></s:textfield>
				<s:file name="uploadFile" required="true"></s:file>(e.g. .pdf, .doc & .docx)
			</td>
		</tr> --%>
		<tr><td colspan="2" align ="center">
		   <s:submit name="submitBtn" value="Add" cssClass="button" onclick="return validate();" />
		</td></tr>
	</table>
</s:form></div>

<div></div>

<%int srNo = 1; %> <br>
<table id="clientTable" width="100%" align="center" class="datatable">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Software Application Group</th>
			<th style="border: 1px solid black;">Hosting</th>
			<th style="border: 1px solid black;">Category</th>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>	
			<!-- <th>Edit</th> -->
			<th style="border: 1px solid black;">Remark</th>
			<th  colspan="2" style="border: 1px solid black;">Action</th>
			<th style="border: 1px solid black;">History</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="applicationDetail" id="clientDetail" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td style="border: 1px solid black;"><%=srNo++ %></td>
			<td style="border: 1px solid black;"><s:property value="applicationName" /></td>
			<td style="border: 1px solid black;"><s:property value="hostingName" /></td>
			<td style="border: 1px solid black;"><s:property value="categoryName" /></td>
			<%-- <td style="border: 1px solid black;"><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
				test="statusIndi == 'D'">Inactive</s:elseif>
				<s:elseif test="statusIndi == 'A'">Active</s:elseif>
				 <s:else>New</s:else></td> --%>
			<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td>-->
			<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
		<s:if test ="#session.countryCode != 'IND'">
			<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
		</s:if>
			<%-- <td><s:if test="statusIndi != 'D'">
				<div align="center"><a href="#" title="Edit"
					onclick="openlinkEdit('<s:property value="clientCode"/>','<s:property value="clientName"/>');">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>
			</s:if> <s:else>
				<div align="center"><a title="Edit"> <img border="0px"
					alt="Edit" src="images/Common/edit.png" height="18px" width="18px">
				</a></div>
			</s:else></td> --%>
			<td style="border: 1px solid black;">
				<s:property value="remark" />
			</td>
			
			<td style="border: 1px solid black;">
				<div align="">
				<s:if test="statusIndi != 'D'">
						<a title="Edit" onclick="openlinkEdit('<s:property value="applicationCode"/>','<s:property value="applicationName"/>',
						'<s:property value="hostingCode"/>','<s:property value="categoryCode"/>','<s:property value="remark"/>');"
							href="#">
							<img border="0px" alt="Audit Trail" src="images/Common/edit.svg"height="25px" width="25px"> 
						</a>
				</s:if>
				<s:else>
					<img border="0px" alt="Audit Trail" src="images/Common/edit.svg"height="25px" width="25px">
				</s:else>
				</div>
			</td>
			
			<td style="border: 1px solid black;">
			<div align="">
				<s:if test="statusIndi == 'E' || statusIndi == 'A'">
					<a title="InActivate" href="javascript:void(0);"
						onclick="authenticate('<s:property value="applicationCode" />','<s:property value="statusIndi" />');">
					<img border="0px" alt="InActivate" src="images/Common/inactive.svg"
						height="25px" width="25px"> </a>
				</s:if> 
				<s:elseif test="statusIndi == 'N'"> 
					<a title="InActivate" href="javascript:void(0);"
						onclick="authenticate('<s:property value="applicationCode" />','<s:property value="statusIndi" />');">
					<img border="0px" alt="InActivate" src="images/Common/inactive.svg"
						height="25px" width="25px"> </a>
				</s:elseif> 
				<s:else>
					<a title="Activate"	href="javascript:void(0);"
						onclick="authenticate('<s:property value="applicationCode" />','<s:property value="statusIndi" />');">
					<img border="0px" alt="Activate" src="images/Common/active.svg"
						height="25px" width="25px"> </a>
				</s:else>
				</div>
				</td>
				
			
			<td style="border: 1px solid black;">
				<div align=""><a title="Software Application Group History" onclick="clientHistory('<s:property value="applicationCode"/>');"
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
