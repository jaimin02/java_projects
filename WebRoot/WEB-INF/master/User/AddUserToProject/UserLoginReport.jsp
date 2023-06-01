<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<script src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"
	type="text/javascript"></script>

<%-- <s:head theme="ajax" /> --%>
<style>
a {
	color: #000 !important;
	text-decoration: none;
}
</style>
<script type="text/javascript">

function PageLoad(){
	   
	   var fDate = document.getElementById("FromDate");
	var tDate = document.getElementById("ToDate");
	//var subDate = document.getElementById("fromDate");
	//var subList = document.getElementById("toDate");
   
   
   /* fDate.style.display="none";
		tDate.style.display="none";
		subDate.style.display="none";
		subList.style.display="none"; */
	fDate.style.display="inline-block";
	tDate.style.display="inline-block";
	//subDate.style.display="table-cell";
	//subList.style.display="table-cell";			
		
	history.go(1);       	
	if (window.PageOnLoad)
   	PageOnLoad();
	history.go(1);       	
	if (window.PageOnLoad)
    	PageOnLoad();
}

$(document).ready(function() { 
	
	var options = {target: '#userforprojectlist',
			beforeSubmit: showRequest,
			success: showResponse };
			$('#userDtlsforProject').submit(function()
			{
				$(this).ajaxSubmit(options);
				return false;
			});
			
	$("#fromSuibmissionDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
	$("#toSuibmissionDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});

	
	var date = (new Date().getFullYear()).toString();
	
	var fDate = document.getElementById("FromDate");
	var tDate = document.getElementById("ToDate");
	var subDate = document.getElementById("fromDate");
	var subList = document.getElementById("toDate");

	fDate.style.display="none";
	tDate.style.display="none";
	//subDate.style.display="none";		
});

function showcombobox()
{
	debugger;
	 var selectcombo=document.userDtlsforProject.searchMode.value;
	 //alert(""+selectcombo);
	
	 
	 	var fDate = document.getElementById("FromDate");
		var tDate = document.getElementById("ToDate");
		var subDate = document.getElementById("fromDate");
		var subList = document.getElementById("toDate");

		if (selectcombo== "Logout" || selectcombo== "Login"){
			fDate.style.display="inline-block";
			tDate.style.display="inline-block";
			//subDate.style.display="table-cell";
			//subList.style.display="table-cell";
			}
		else{

			fDate.style.display="none";
			tDate.style.display="none";
			//subDate.style.display="none";
			//subList.style.display="none";
		}
	 
	 
}

	
function printPage()
{
	getTimeInfo();
	window.print();
}
function showRequest(formData, jqForm, options)
{
	$(options.target).html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
	return true;
}
function showResponse(responseText, statusText) 
{
			
}

function getTimeInfo(){
	debugger;	
	var currentdate = new Date();
	var datetime = currentdate.getFullYear() + "-"
    + (currentdate.getMonth()+1)  + "-" 
    +  currentdate.getDate()+ " "  
    + currentdate.getHours() + ":"  
    + currentdate.getMinutes() + ":" 
    + currentdate.getSeconds() + ":"
	+ currentdate.getMilliseconds();
	document.getElementById("tm").innerHTML = datetime;
	
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
<div id="content-wrap">
<div id="main" style="margin-top: 10px;">
<div class="container-fluid">
<div class="col-md-12">

<div class="boxborder"><div class="all_title"><b style="float:left">User Login Detail</b></div></div><!-- <img
	src="images/Header_Images/Report/Project_Node_History_Snapshot.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="center"><br><s:form action="UserLoginReport_ex" id="userDtlsforProject"
	method="post" name="userDtlsforProject">
	<table style="width: 100%">
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">Search On</td>
			<td align="left"><select id="reportType" name="searchMode" onchange="return showcombobox();">
					<!-- <option value="All_User">All User</option>
					<option value="Active">Active User</option>
					<option value="Deactive">Inactive User</option>
					<option value="Active_Session">Active Session</option> -->
					<option value="Login">Login & Logout History</option>
					
					
					</select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">Profile Name</td>
			<td align="left"><select name="userTypeCode">
				<option value="-1">All</option>
				<s:iterator value="allUserType">
					<option value="<s:property value="userTypeCode"/>">
						<s:if test="userTypeName=='WA'">Admin User</s:if>
						<s:elseif test="userTypeName=='WU'">Project User</s:elseif>
						<s:else>
							<s:property	value="userTypeName" />
						</s:else>
					</option>
				</s:iterator>
			</select></td>
		</tr>	
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
				<div align="left" class="displayDuration" id="FromDate" style="display: inline-block;margin-left: -7.5%;">From &nbsp;&nbsp;&nbsp;
				<input type="text" name="fromSubmissionDate" class="attrValueIdDate" readonly="readonly"id="fromSuibmissionDate"> 
				(YYYY/MM/DD) &nbsp;<IMG
																		onclick="if(document.getElementById('fromSuibmissionDate').value != '' 
														&& confirm('Are you sure?'))
														document.getElementById('fromSuibmissionDate').value = '';
																					"
																		src="<%=request.getContextPath()%>/images/clear.svg"
																		height="25px" width="25px" align="middle"
																		title="Clear Date">
				</div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
				<div align="left" class="displayDuration" id="ToDate" style="display: inline-block;margin-left: -5.3%;">To &nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" name="toSubmissionDate"class="attrValueIdDate" readonly="readonly" id="toSuibmissionDate"> 
				(YYYY/MM/DD) &nbsp;<IMG
																		onclick="if(document.getElementById('toSuibmissionDate').value != '' 
														&& confirm('Are you sure?'))
														document.getElementById('toSuibmissionDate').value = '';
																					"
																		src="<%=request.getContextPath()%>/images/clear.svg"
																		height="25px" width="25px" align="middle"
																		title="Clear Date">
				</div>
			</td>
		</tr>
		
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
