<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<script src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"
	type="text/javascript"></script><base>

<%-- <s:head theme="ajax" /> --%>

<script type="text/javascript">
$(document).ready(function() { 
		var options = {target: '#userforprojectlist',
			beforeSubmit: showRequest,
			success: showResponse };
			$('#userDtlsforProject').submit(function()
			{
				$(this).ajaxSubmit(options);
				return false;
			});
});
	
function showRequest(formData, jqForm, options)
{
	$(options.target).html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
	return true;
}
function showResponse(responseText, statusText) 
{
			
}
	
function selAll()
{		
	for (var i=0;document.forms['multipleProjects'].project.length!=null && i<document.forms['multipleProjects'].project.length;i++)
	{		
		document.forms['multipleProjects'].project[i].checked=document.forms['multipleProjects'].sAll.checked;
	}
	if (document.forms['multipleProjects'].project.length==null)
	{
		document.forms['multipleProjects'].project.checked=document.forms['multipleProjects'].sAll.checked;		
	}
}
function chkPrjChks()
{	
	var sel=false;
	var str='';
	for (var i=0;document.forms['multipleProjects'].project.length!=null && i<document.forms['multipleProjects'].project.length;i++)
	{		
		if (document.forms['multipleProjects'].project[i].checked==true)
		{
			sel=true;
			if (str=='')
				str=document.getElementById('wsdesc'+i).value;
			else
				str=str + "<br>" + document.getElementById('wsdesc'+i).value;
		}
	}
	if (document.forms['multipleProjects'].project.length==null)
	{
		if (document.forms['multipleProjects'].project.checked==true)
		{
			sel=true;			
			str=document.getElementById('wsdesc0').value;			
		}
	}
	if (sel==true)
	{		
		document.getElementById('prjList').innerHTML=str;		
		tgldiv1();
		window.scroll(0,340);
	}
	else
		alert("Please select at least one project!!!");
}
function tgldiv1()
{
	document.getElementById('grid').style.display='';
	document.getElementById('list').style.display='none';
	document.getElementById('mainTab').style.display='';
}
function tgldiv2()
{
	document.getElementById('grid').style.display='none';
	document.getElementById('list').style.display='';
	document.getElementById('mainTab').style.display='none';
}
function getUsrs()
{	
	$.ajax(
	{			
		url: 'GetGroupUsersAction_ex.do?usrType=' + document.getElementById('usrType').value + '&no=' + document.getElementById('no').value,
		beforeSend: function()
		{ 
			if (document.getElementById('usrType').value=='none')
			{
				$('#usrGrpList').html("<b>Please select User Type</b>");
				return false;
			}
			document.getElementById("imgLoading").style.display='';			
		},
  		success: function(data) 
  		{
    		$('#usrGrpList').html(data);
    		document.getElementById("imgLoading").style.display='none';     		
		}	  		
	});
}
function attachUsers()
{	
	if (!confirm("You are about to attach multiple users to multiple projects\nwhich would overwrite their existing rights, with the new rights provided.\nDo you want to continue?"))
		return;
	//alert('done');
	var str='';
	for (var i=0;document.forms['frmUsrSelect'].usrSelect.length!=null && i<document.forms['frmUsrSelect'].usrSelect.length;i++)
	{
		if (document.forms['frmUsrSelect'].usrSelect[i].checked==true)
		{
			if (str=='')
				str=document.forms['frmUsrSelect'].usrSelect[i].value;
			else
				str+=","+document.forms['frmUsrSelect'].usrSelect[i].value;
		}
	}
	if (document.forms['frmUsrSelect'].usrSelect.length==null)
	{
		if (document.forms['frmUsrSelect'].usrSelect.checked==true)
		{
			str=document.forms['frmUsrSelect'].usrSelect.value;			
		}
	}
	if (str=='')
	{
		alert('Please select users ...');
		return;
	}
	//alert(str);
	/*var rite;
	if (document.forms['frmUsrSelect'].rites[0].checked==true)
		rite=document.forms['frmUsrSelect'].rites[0].value;
	else
		rite=document.forms['frmUsrSelect'].rites[1].value;
	*/
	//alert(rite);
	var stages = document.forms['frmUsrSelect'].stageIds;
	var stg="";
	for(i=0; i < stages.length; i++){
		if(stages[i].checked){
			if(stg==""){
				stg=stages[i].value;
			}
			else{
				stg+=","+stages[i].value;
			}
		}
	}
	if(stg == ''){
		alert("Please select stage(s)...");
		return;
	}
	var fdate = document.getElementById('frmDate').value;
	var tdate = document.getElementById('toDate').value;
	var alertMsg = 'To Date must be greater than or equal to From Date.';

	if(fdate!= '' && tdate != '' && fdate > tdate)
	{
		alert(alertMsg);
		document.getElementById('toDate').value="";
		document.getElementById('toDate').style.backgroundColor="#FFE6F7";
		document.getElementById('toDate').focus();
		return false;
	}
	//alert(stg);
	var str1='';
	for (var i=0;document.forms['multipleProjects'].project.length!=null && i<document.forms['multipleProjects'].project.length;i++)
	{
		if (document.forms['multipleProjects'].project[i].checked==true)
		{
			if (str1=='')
				str1=document.forms['multipleProjects'].project[i].value;
			else
				str1+=','+document.forms['multipleProjects'].project[i].value;
		}
	}
	if (document.forms['multipleProjects'].project.length==null)
	{
		if (document.forms['multipleProjects'].project.checked==true)
		{				
			str1=document.forms['multipleProjects'].project.value;			
		}
	}
	//alert(str1);	
	var fDate=document.forms['frmUsrSelect'].frmDate.value;
	var tDate=document.forms['frmUsrSelect'].toDate.value;
	var args="usrs=" + str + "&stageIds=" + stg + "&prj=" + str1 + "&frmDate=" + fDate + "&toDate=" + tDate;
	//alert(args);	
	$.ajax(
	{			
		url: 'AttachMultipleUsers_b.do?' + args,
		beforeSend: function()
		{ 
			document.getElementById("imgLoading").style.display='';
		},		
  		success: function(data) 
  		{
    		//$('#usrGrpList').html(data);     		
    		document.getElementById("imgLoading").style.display='none';
    		alert('Users Attached Successfully!!!');
		}	  		
	});
	
}
function selAlUsr(str)
{
	var cnt=document.getElementById(str).value;
	var sel=document.getElementById(str).checked;
	for (var i=1;i<=cnt;i++)
	{
		document.getElementById(str + '_' + i).checked=sel;
	}
}
function selUsrColumn(str,colNo,noOfCols)
{
	//alert(colNo);
	//alert(noOfCols);
	var cnt=document.getElementById(str).value;
	var sel=document.getElementById(str + "-" + colNo).checked;
	for (var i=0;i<cnt;i++)
	{		
		if (i%noOfCols==colNo)
		{
			//alert(i);
			//alert(i%noOfCols);			
			document.getElementById(str + '_' + (i+1)).checked=sel;
		}
	}
}
</script>

</head>
<body>

<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<br />
<!-- <img
	src="images/Header_Images/User/Attach_User_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder"><div class="all_title"><b style="float:left">User Allocation</b></div>
<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div align="center"><br><div align="left"><s:form action="userDtlsforProject" id="userDtlsforProject"
	method="post">
	<table style="width: 100%">

		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><select name="projectCode">
				<option value="-1">Select Project Type</option>
				<s:iterator value="projectTypes">
					<option value="<s:property value="projectCode"/>"><s:property
						value="projectName" /></option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left"><select name="clientCode">
				 <option value="-1">Select client</option>
				<s:if test="FosunChanges=='yes'">
					<s:iterator value="clients">
					<s:if test="clientCode == clientCodeGroup">
						<option value="<s:property value="clientCode"/>"><s:property
						value="clientName" /></option>
					</s:if>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator value="clients">
					 	<option value="<s:property value="clientCode"/>"><s:property
						value="clientName" /></option>
				</s:iterator>
				</s:else>
				
			</select></td>
		</tr>
		<%-- <tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><select name="locationCode">
				<option value="-1">Select Region</option>
				<s:iterator value="locations">
					<option value="<s:property value="locationCode"/>"><s:property
						value="locationName" /></option>
				</s:iterator>
			</select></td>
		</tr> --%>
	</table>
	<br />
	<%-- <div align="center"><s:submit value="Search" cssClass="button"
		theme="ajax" targets="userforprojectlist" align="center" /></div> --%>
		<div align="center"><s:submit value="Search" cssClass="button" align="center" /></div>
</s:form> <br />

<div align="center" id="userforprojectlist"></div>
</div>
</div>
</div>

</div>
</div>
</div>

</body>
</html>
