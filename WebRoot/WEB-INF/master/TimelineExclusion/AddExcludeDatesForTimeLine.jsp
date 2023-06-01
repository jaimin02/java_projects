<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style type="text/css">
a {
	color: #000 !important;
	text-decoration: none;
}

</style>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
	
<s:head />

<script type="text/javascript">

$(document).ready(function() { 
	 $('#clientTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
	 $(".attrValueIdDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
} );

function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
	

	function validate()
	{
		var cname = document.masterAdminForm.date.value;
		var remark = document.masterAdminForm.remark.value;
		cname=trim(cname);
	
		if(cname=="")
		{
			alert("Please add Date.");
			document.masterAdminForm.date.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.date.focus();
     		return false;
     	}
		
		if (remark == "" || remark == null ){
	     	alert("Please specify Reason.");
	     	document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
			document.masterAdminForm.remark.focus();
	 		return false;
	     }
		
     	return true;
	}
	
	function openlinkEdit(clientCode,clientName)
    {
    	var editLocationWindow = "EditClient.do?clientCode="+clientCode+"&clientName="+clientName;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=150,width=400,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(150/2));
    	return true;
    }
    
	
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
	
	function dateHistory(clientCode)
	{
		str="showdateHistory_b.do?date=" + clientCode;
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
	<div class="boxborder"><div class="all_title"><b style="float:left">Timeline Date Master</b></div>

<div class="datatablePadding" style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">
<div id="imp" style="float: right; margin-right: 10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>

	<s:form action="SaveExcludedDate" method="post" name="masterAdminForm">
		<table width="100%">
			<tr>
				<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="clientName">
					Date
				</td>
				<td  align="left">
					<input type="text" name="date" class="attrValueIdDate" id="date">
				</td>
			</tr>
			<tr>
			<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Reason<span style="font-size:20px;color:red">*</span></td>
				<td align="left"><s:textfield name="remark" size="30" value="" /></td>
			</tr>
			<tr>
				<td colspan="2" align ="center">
			   	<s:submit name="submitBtn" value="Add" cssClass="button" onclick="return validate();" />
				</td>
			</tr>
		</table>
	</s:form>
</div>

<div></div>

<%int srNo = 1; %> <br>
<table id="clientTable" width="100%" align="center" class="datatable">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Excluded date</th>
			<th style="border: 1px solid black;">Reason</th>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>	
			 <th style="border: 1px solid black;"></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="excludedDateDetail" id="clientDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td style="border: 1px solid black;"><%=srNo++ %></td>
				<td style="border: 1px solid black;"><s:date name="excludedDate" format="dd-MMM-yyyy" /><%-- <s:property value="excludedDate" /> --%></td>
				<td style="border: 1px solid black;"><s:property value="remark" default="-"/></td>
				<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
			</s:if>
				<td style="border: 1px solid black;">
					<div><a title="Client History" onclick="dateHistory('<s:date name="excludedDate" format="dd-MMM-yyyy" />');"href="#">
							<img border="0px" alt="Audit Trail" src="images/Common/details.svg"height="25px" width="25px"> 
						</a>
					</div>
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
