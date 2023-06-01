<%@ page language="java"%>
<%@ page contentType="text/html"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<html>
<head>
<script type='text/javascript'
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'></script>
<style>

#envelop{
	top: 50%;
    left: 50%;
    margin-top: -50px;
    margin-left: -50px;
    width:200px;
    height:100px;
    position:fixed;
	background-color:rgba(0,0,0,0.4);	
}
.ui-draggable{	
   /*  height: calc(100vh - 200px;) !important;
    top: 151px !important; */
    z-index: 10000 !important;
    height: 1000px !important;
    width: 1000px !important;
    top: -2808px;
    left: 179px !important;
}
 .fileInfoDialog {
	position: fixed;
	background-color: #103050;
	color: white;
	display: none;
	height: 420px;
	margin-top: 160px;
	width: 780px;
}

#fileDiv{
	margin-top: 15px;
    margin-bottom: 15px;
  	height: 925px;
   	width: 950px;
}
.ui-dialog-title {
float:left;
padding:10px;
}

.ui-icon{
float:right;
margin:10px;
}
</style>
<script type="text/javascript">
	function printPage()
	{
		window.print();
	}
		
	function back()
	{
		location.replace("GenerateDocSummaryReport.do");
	}
	function chkAll()
	{
		var chkBox = document.getElementsByName("selectedNodeId");
		
		 if (document.getElementById("chk").value == 'Check All'){
			for(i=0; i<chkBox.length; i++)
			{
				chkBox[i].checked = 'checked';
		 	}
			document.getElementById("chk").value = 'Uncheck All';
		 }else
		  {
			  
			 for(i=0; i<chkBox.length; i++)
			 {
				 chkBox[i].checked = '';
			 }
			 document.getElementById("chk").value = 'Check All';
		 }	 
	}
	function zip(){
		
		var btn = valButton(document.getElementsByName("selectedNodeId"));		
		if (btn == null) 
		{
			alert('Please Select File(s)');
			return false;
		}
		var nodeIds=[];
		if (document.getElementsByName("selectedNodeId").length != null){
			
			for(i=0; i<document.getElementsByName("selectedNodeId").length; i++){
				
				if(document.getElementsByName("selectedNodeId")[i].checked == true)
				{
					var str = document.getElementsByName("selectedNodeId")[i].value;
					nodeIds.push(str);			
				}	 				
			}				
		}
		
		var workSpaceId=document.getElementById("workspaceId").value;
		
		var queryString = "?workSpaceId="+workSpaceId+"&nodeIds="+nodeIds;

		$.ajax({			
			url: 'DownloadZipPendingWork_ex.do'+queryString,
			beforeSend: function()
	  		{
				$('#envelop').show();
	  		},
			success: function(data) 
	  		{		  	   
		  	 	var str="DownloadZipFolder_ex.do?DownloadFolderPath="+data;
		  	  	$('#envelop').hide(); 
		  		window.location.href=str;
			}		
		});
	}
	function valButton(btn) 
	{
		
	    var cnt = -1;
	    if(btn.length != null)
	    {
	    	//alert("if");
	    	for (var i=btn.length-1; i > -1; i--) 
	    	{
	        	if (btn[i].checked) 
	        	{
		    	    cnt = i; 
		  	      i = -1;
	        	}
	    	}
	    }
	    else
	    {
	    	//alert("else");
			if(btn.checked)
			{
				cnt = 0;
			}
	    }
	
	    if (cnt > -1)
	     return cnt;
	    else
	     return null;
	}
	
	function fileopen(actionName)
	{   
		//debugger;
		var url = actionName;
		var encodeurl = encodeURIComponent(url);
		$( "#dialog-confirm" ).dialog({
			  resizable: false,
		      height: 450,
		      width: "auto",
		      modal: true,
		       
		      open: function(ev, ui){
		             $('#fileDiv').attr('src','PdfDowloadRestrict_ex.do?file='+encodeurl);
		          } 
		      });
	}
	
</script>
<style type="text/css">
.color1 {
	color: navy;
	font-weight: bold;
}
</style>
</head>
<body>
<br/>
<div class="fileInfoDialog">
<div id="dialog-confirm" title="OpenFile" align="center">
	<iframe id="fileDiv" name="fileDiv" src=""></iframe>
</div>
</div>
<table width="100%" border="0">
	<tr>
		<td align="right" width="100%">
		<form action="ExportToXls.do" method="post" id="myform"><input
			type="hidden" name="fileName" value="Document_Summary_Report.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableTextArea"
			style="visibility: hidden;"></textarea> <img alt="Export To Excel"
			title="Export To Excel" src="images/Common/Excel.png"
			onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;;document.getElementById('myform').submit()">
		&nbsp;<img alt="Print" title="Print" src="images/Common/Print.png"
			onclick="return printPage();"> &nbsp;<img alt="Back"
			title="Back" src="images/Common/Back.png" onclick="return back();">&nbsp;

		</form>


		</td>
	</tr>

</table>


<br>

<s:if test="getDtl.size==0">

	<table bgcolor="#FCE0E3" border="0" width="100%"
		style="border: 1 solid #FF0000">
		<tr bgcolor="#FCE0E3" align="center">
			<td width="100%">
			<table bgcolor="#FCE0E3" width="100%">
				<tr>
					<td width="20%" align="left">&nbsp;&nbsp;<img
						src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
					<td width="75%" valign="center" align="left"><b><font
						face="System" color="red" size="2">&nbsp;&nbsp;Message : No
					Data Found</font></b></td>
					<td width="5%">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:if>
<s:else>

	<div id="divTabText">
	<table class="channel" cellspacing="2" cellpadding="2" width="100%">
		<tr>
			<td colspan="8">
			<center><b><font face="verdana" size="3px" color="navy">DocumentSummary
			Report</font> </b></center>
			<br>
			<b> <font size="2" face="Verdana" color="navy"> Project
			Name : ${workspaceDesc} </font> </b> <br>
			
			<!-- <b> <font size="2" face="Verdana" color="navy"> Search
			on&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:${fieldName} </font> <font
				size="2" face="Verdana" color="#C00000"> &nbsp;${fieldValue}
			</font> <s:if test="opvalue=='D'">
				<font size="2" face="Verdana" color="navy"> &nbsp;date </font>
			</s:if> </b> -->
			</td>
			<s:if test="userTypeName=='Initiator'">
			<td>
				<input float="right" margin-bottom="-25%" type="button" name="tst" class="button" value="Download" onclick="return zip();" />
			</td>
			</s:if>
		</tr>
	</table>
	
	<table class="channel" border="1" cellspacing="2" cellpadding="2"
		width="100%" style="border: 1 solid #C0C0C0">

		<tr width="100%" bgcolor="#CBCBE4">
			<!-- <td class="title" width="3%"></td> -->
			<!-- <td width="32%" style="color: navy;"><b>Activity Name</b></td> -->
			<td width="32%" style="color: navy;"><b>Section No. and Title</b></td>
			<td style="color: navy;" width="20%"><b>File Name</b></td>
			<td style="color: navy;" width="6%"><b>Uploading Version</b></td>
			<td style="color: navy;" width="10%"><b>Status</b></td>
			<td style="color: navy;" width="7%"><b>Modify By</b></td>
			<td style="color: navy;" width="15%"><b>Modify On</b></td>
		<s:if test ="#session.countryCode != 'IND'">
			<td style="color: navy;" width="10%"><b>Eastern Standard Time</b></td>
		</s:if>
		<s:if test="userTypeName=='Initiator'">
			<td style="color: navy;" width="15%" align="center"><input
						type="checkbox" name="chk" id="chk" value="Check All" onclick="return chkAll();"></td>
		</s:if>
			
		</tr>

		<s:iterator value="getDtl" status="stat">

			<s:if test="fieldValue == 'All'">
				<tr class="<s:if test="top[3]!= dash"> color1 </s:if>">
			</s:if>
			<s:else>
				<tr>
			</s:else>

			<%-- <td>${stat.count }</td> --%>
			<TD><s:property value="top[1]" /></TD>
			<td><b>
			 <s:if test="top[3]!= dash">
			   <s:if test="userTypeName=='Monitor' && (top[3].toUpperCase().endsWith('.PDF'))">
						<a href="javascript:void(0)" onclick="return fileopen('openfile.do?fileWithPath=<s:property value="top[10]" />');"
						target=""><s:property value="top[3]" />
			   </s:if>
			   <s:else>
			   	 <a href="openfile.do?fileWithPath=<s:property value="top[10]" />"
				target="_blank"><s:property value="top[3]" />	
			   </s:else>
			</s:if>		
			<s:else>
				<font color="red"> File Not Attached </font>
			</s:else> </b></td>
			<td><s:property value="top[4]" /></td>
			<td><s:if test="top[9] == Approved">
				<font color="green">
			</s:if> <s:else>
				<font color="red">
			</s:else> <s:property value="top[9]" /> </font></td>
			<td><s:property value="top[5]" /></td>
			<!--<td><s:property
				value="(new java.text.SimpleDateFormat('MMM-dd')).format((java.util.Date)top[6])" />
			 <s:property value="top[6]" /></td>-->
			<td><s:property value="top[13]" /></td>  
		<s:if test ="#session.countryCode != 'IND'">
			<td><s:property value="top[14]" /></td>
		</s:if>
			<s:if test="userTypeName=='Initiator'">
			<td align="center">
			<s:if test="top[3]!= dash">
			 <input type="checkbox" value="<s:property value="top[0]"/>" name="selectedNodeId" /></td>	
			</s:if>
			</s:if>
			<%-- <td><s:property value="top[0]"/></td> --%>
			</tr>

		</s:iterator>

	</table>
	
	</div>

</s:else>
<div id="envelop" style="display:none;">
	<div>
		<center><img src="<%=request.getContextPath()%>/images/loading.gif" alt="loading..." style="margin-top:32px;"></center>
	</div>
</div>
<input type="text" id="workspaceId" style="display:none;" value="<s:property value="workSpaceId"/>"></input>
</div>
</body>
</html>