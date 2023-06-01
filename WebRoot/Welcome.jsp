<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.docmgmt.dto.DTOReminder"%><html>

<head>
<s:head theme="ajax" />


<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>

<link href="<%=request.getContextPath()%>/js/jquery/jquery.alerts.css"
	rel="stylesheet" type="text/css" media="screen" />
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script src="<%=request.getContextPath()%>/js/jquery/jquery.alerts.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>


<style>
.ui-button {
	margin-left: -1px;
}

.ui-button-icon-only .ui-button-text {
	padding: 0.24em;
}

.ui-autocomplete-input {
	margin: 0;
	padding: 0.23em 0.23em 0.22em;
}
</style>
<STYLE type="text/css">
.headernotice {
	font-size: 13px;
	color: #5B89AA;
	font-family: verdana;
	padding-left: 5px;
	font-weight: bold;
}

.noticedesc {
	font-size: 12px;
	color: black;
	font-family: cursive;
	padding-left: 26px;
	text-align: justify;
	padding-right: 5px;
}
</STYLE>
<style type="text/css">
.toggler {
	width: 500px;
	height: 200px;
}

#setProjectBtn {
	text-decoration: none;
}

#setProject {
	width: 240px;
	height: 135px;
	padding: 0.4em;
	position: relative;
}

#setProject h3 {
	margin: 0;
	padding: 0.4em;
	text-align: center;
}

.ui-effects-transfer {
	border: 2px dotted gray;
}

.toggler {
	width: 500px;
	height: 200px;
}

#calenderBtn {
	text-decoration: none;
}

#calender {
	width: 240px;
	height: 135px;
	padding: 0.4em;
	position: relative;
}

#calender h3 {
	margin: 0;
	padding: 0.4em;
	text-align: center;
}

.ui-effects-transfer {
	border: 2px dotted gray;
}

.workspacedesc {
	/*padding-top: 3px;*/
	font-size: 18px;
	color: black;
	font-family: calibri;
	padding-left: 15px;
	text-align: justify;
	padding-right: 3px;
}

.hr1{
	margin: 0 5px 0 5px !important;
	border-top: 1px solid #c9bfbf !important;
}
#NoOfComments{
	
    border-radius: 10px;
    color: #fff;
    background: green;
    color: #fff;
    float: right;
    width: 31px;
    position: absolute;
    margin: -50px 0 0 0;
    font-size: 13px;
    right:6%;
    
}
#NoOfDocReminder{
    border-radius: 10px;
    color: #fff;
    background: goldenrod;
    color: #fff;
    float: right;
    width: 36px;
    position: absolute;
    margin: -52px 0 0 0;
    font-size: 13px;
    right:6%;
}
#NoOfRunningDoc{
	
    border-radius: 10px;
    color: #fff;
    background: red;
    color: #fff;
    float: right;
    width: 31px;
    position: absolute;
    margin: -50px 0 0 0;
    font-size: 13px;
    right:6%;
    
}
#popupContact {
width: 620px !important;
}
@media only screen and (min-width: 1600px) {
  #NoOfComments{
  margin: -56px 0 0 0;
  right: 18%;
  }
  #NoOfRunningDoc{
  margin: -50px 0 0 0;
  right: 18%;
  }
}


.dashboardreporticon{
	text-align:center;
    font-weight: 600;
    margin-bottom: 15px;
}

@font-face {
	    font-family: 'password';
	    font-style: normal;
	    font-weight: 400;
	   /*  src: url('images/password.ttf'); */
		src: url(data:font/woff;charset:utf-8;base64,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) format("woff");

	   
	}          
	#reConfPass {
            font-family: 'password';
        }

</style>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
	
<script type="text/javascript">
 	var locationCodeIndex,productattrValueIndex,dossierStatusattrValueIndex;
 	var projectStatusFlag = false;
 	var projectCompletionFlag = false;
 	var upcomingSignOffReportFlag=false;
 	var runningSignOffReportFlag=false;
 	var srcDocReminderFlag=false;
 	var eSignatureFlag=false;
 	var acknowledgeFlag=false;
 	
 	var logoname;
 	var signImgs;
 	function hideToolTip()
	{
		$('#wsdetailtooltip1').hide();
	}
 	
	function ChangeStatusQuery(queryId,wsid,wsdesc)
	{
		
		/*var ddl = document.getElementById("workspaceId");
		var wsid = ddl.value;
		var wsdesc = ddl.options[ddl.selectedIndex].text; */
		$.ajax({			
			url: 'PostSubQueryMgmt_b.do?workSpaceId='+wsid+"&workspaceDesc="+wsdesc+"&mode=ChangeStatus&queryId="+queryId+"&mi=c",
			success: function(data) 
	  		{
				//$('#addQueryDtl').html(data);
			
				
				$(".querypopup").show(300);

				var windowWidth = document.documentElement.clientWidth;
				var windowHeight = document.documentElement.clientHeight;
				var popupHeight = $("#popupContact").height();
				var popupWidth = $("#popupContact").width();
				//centering
				$("#popupContact").css({
					"position": "absolute",
					"top": windowHeight/2-popupHeight/2,
					"left": windowWidth/2-popupWidth/2
				});
				//only need force for IE6
				
				$("#backgroundPopup").css({
					"height": windowHeight
				});
					
				$('#popupContact').html(data);
			
	  			
			}		
			});					 
		//centering with css
		centerPopup();
		//load popup
		loadPopup();
			
	}

	 function showBranch(branch,nodeId)
		{
			
			if(document.getElementById(branch))
			{
				var objBranch = document.getElementById(branch).style;
			
				if(objBranch.display=="block")
					objBranch.display="none";
				else
					objBranch.display="block";
			}
		}

		function swapFolder(img)
		{
		  if(document.getElementById(img))
		  {	
			  var objImg = document.getElementById(img);
			  if(objImg)
			  {
				if(objImg.src.indexOf('closed.gif')>-1)
					objImg.src = openImg.src;
				else
					objImg.src = closedImg.src;
			  }
		  }
		}
	
	function changeGraph()
	{
		var locationCode=document.getElementById("locationCode");
		var productattrValue=document.getElementById("ProductattrValue");
		var dossierStatusattrValue=document.getElementById("DossierStatusattrValue");
		var topMostValue=document.getElementById("topMostValue");
		var re10digit=/^\d{1,2}$/ ;			
		document.getElementById("topMostValue").disabled = false;				
		if(topMostValue.value==""){
		}else if(topMostValue.value.search(re10digit)==-1){
			alert("Enter valid Input in top ten box");
			return false;
		}else if(parseInt(topMostValue.value)>15){
			alert("Value Should be less then 15");
			return false;
		}		
		var graphFlag=0;

		hideToolTip();
				
		if(locationCode.value!="-1" && productattrValue.value!="-1" && dossierStatusattrValue.value!="-1")
			alert("select Atleast one value as \"All\"");		
		else if(locationCode.value=="-1" && productattrValue.value=="-1" && dossierStatusattrValue.value=="-1")	
		{			
			/*
			locationCode.options[locationCodeIndex].selected=true;
			productattrValue.options[productattrValueIndex].selected=true;
			dossierStatusattrValue.options[dossierStatusattrValueIndex].selected=true;
			*/
			document.getElementById("topMostValue").value="";
			document.getElementById("topMostValue").disabled = true;		
			$.ajax({			
				url: 'DossierStatusTab_ex.do?allSelected=yes',
				beforeSend: function()
				{ 	
					$("#piechartdatacontainer").hide();																											
					$("#imageloader").show();						
					$("#imageloader").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');																				
				},
		  		success: function(data) 
		  		{	
			  		//alert(data);
					$("#imageloader").hide();											
					$("#stackbarcontainer").show();				
					$("#stackbarcontainer").html('');												
					$("#stackbarcontainer").html(data);
												
				},
				async: false							 
			  });							
		}
		else if(locationCode.value!="-1" && productattrValue.value!="-1" || productattrValue.value!="-1" && dossierStatusattrValue.value!="-1" || dossierStatusattrValue.value!="-1" && locationCode.value!="-1" || locationCode.value!="-1" || productattrValue.value!="-1")
		{	
			if(productattrValue.value!="-1" && dossierStatusattrValue.value!="-1"){
				graphFlag=2;//Region				
			}else if(locationCode.value!="-1" && dossierStatusattrValue.value!="-1"){
				graphFlag=3;//Product
			}	
			else if(locationCode.value!="-1" && productattrValue.value!="-1" || locationCode.value!="-1" || productattrValue.value!="-1"){
				graphFlag=1;//Dossier Status 
			}			
					$.ajax({			
						url: 'DossierStatusTabAction_ex.do?locationCode=' + locationCode.value+'&productattrValue='+productattrValue.value+'&dossierStatusattrValue='+dossierStatusattrValue.value+"&graphFlag="+graphFlag+"&topMostValue="+(topMostValue.value==""?"5":topMostValue.value),
						beforeSend: function()
						{ 														
							$("#imageloader").show();
							$("#stackbarcontainer").hide();															
							$("#imageloader").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');							
															
						},
				  		success: function(data) 
				  		{		
					  		alert(data);	
					  		console.log(data);			  		
							$("#imageloader").hide();
							$("#piechartdatacontainer").show();															
					  		$("#piechartdatacontainer").html(data);							
						},
						async: false							 
					  });
				
		}		
		locationCodeIndex=locationCode.selectedIndex;
		productattrValueIndex=productattrValue.selectedIndex;
		dossierStatusattrValueIndex=dossierStatusattrValue.selectedIndex;
	}							
	$(document).ready(function() {
		//centering with css
		
		////debugger;
		
		commentDispOption=1;
		//document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;		
		//showComments('#commentsDiv2','<s:property value="#session.userid"/>','commentParent',1,1,"3");
		centerPopup();
		//load popup
		loadPopup();
		

		//showComments('#commentsDiv','<s:property value="#session.userid"/>','commentParent',1,1,"5");


		
		$("#commentsDiv2").hide();

		function runSetProject(){
			
			$("#setProject").toggle("slide","",5000,callbackSetProject);
		};
		function runCalender(){
			$("#calender").toggle("slide","",5000,callbackCalender);
		};
		function runComment(){
			$("#commentParent").toggle("slide","",5000,callbackComment);
		};
		function runReminders(){
			$("#reminders").toggle("slide","",5000,callbackReminders);
		};
		function runSubquerymgmt(){
			$("#subquerymgmt").toggle("slide","",5000,callbackSubquerymgmt);
		};
		function runUpcomingReport(){
			$("#upcomingReport").toggle("slide","",5000,callbackUpcomingReport);
		};
		function runSourceDocReminder(){
			$("#SourceDocReminder").toggle("slide","",5000,callbackSourceDocReminder);
		};
		 function runProjectCompletionReport(){
			$("#ProjectCompletionReport").toggle("slide","",5000,callbackProjectCompletionReport);
		}; 
		
		function runAcknowledgementDiv(){
			$("#acknowledgementDiv").toggle("slide","",5000,callbackAcknowledgementDiv);
		};
		
		function runeSignatureDiv(){
			$("#eSignatureDiv").toggle("slide","",5000,callbackeSignatureDiv);
		};
		
		function runApplicationGraph(){
			$("#dossierstatustab").toggle("slide","",5000,callbackApplicationGraph);
		};


		function runProjectStatus(){
			$("#projectStatusDiv").toggle("slide","",5000,callbackProjectStatus);
		};
		
		
		//callback function to bring a hidden box back
		function callbackSetProject(){
			setTimeout(function(){
				$("#setProject:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackCalender(){
			setTimeout(function(){
				$("#calender:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackComment(){
			setTimeout(function(){
				$("#commentParent:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackReminders(){
			setTimeout(function(){
				$("#reminders:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackSubquerymgmt(){
			setTimeout(function(){
				$("#subquerymgmt:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackUpcomingReport(){
			setTimeout(function(){
				$("#upcomingReport:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		function callbackSourceDocReminder(){
			setTimeout(function(){
				$("#SourceDocReminder:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackAcknowledgementDiv(){
			setTimeout(function(){
				$("#acknowledgementDiv:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackeSignatureDiv(){
			setTimeout(function(){
				$("#eSignatureDiv:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		 function callbackProjectCompletionReport(){
			setTimeout(function(){
				$("#ProjectCompletionReport:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		}; 
		
		function callbackApplicationGraph(){
			setTimeout(function(){
				$("#dossierstatustab:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		function callbackProjectStatus(){
			setTimeout(function(){
				$("#projectStatusDiv:visible").removeAttr('style').hide().fadeOut();
			}, 10000);
		};
		
		//set effect from select menu value
		$("#setProjectBtn").click(function() {
			
			$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			runSetProject();
					
		$("#calender").hide();
		$("#commentParent").hide();	
		$("#reminders").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();

		$("#pleaseWait").html('');
		$("#calenderBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#upcomingReportBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");

			if($("#setProjectBtn").css("border")=="1px solid #175279")							
					$("#setProjectBtn").css("border","0px solid");
			else									
				$("#setProjectBtn").css("border","1px solid #175279");
			return false;
		});

		$("#calenderBtn").click(function() 
		{
			$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			runCalender();
			
		$("#setProject").hide();
		$("#commentParent").hide();
		$("#reminders").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();

		$("#pleaseWait").html('');

		  $("#setProjectBtn").css("border","0px solid");		  
		  $("#commentBtn").css("border","0px solid");
		  $("#remindersBtn").css("border","0px solid");
		  $("#subquerymgmtBtn").css("border","0px solid");
		  $("#ProjectCompletionBtn").css("border","0px solid");
		  $("#dossierstatustabBtn").css("border","0px solid");
		  $("#projectStatusBtn").css("border","0px solid");
		  $("#upcomingReportBtn").css("border","0px solid");
		  $("#docReminderBtn").css("border","0px solid");
		  $("#acknowledgementBtn").css("border","0px solid");
		  $("#eSignatureBtn").css("border","0px solid");

		 if($("#calenderBtn").css("border")=="1px solid #175279")							
				$("#calenderBtn").css("border","0px solid");
		 else									
			$("#calenderBtn").css("border","1px solid #175279");
			return false;
		});

		$("#commentBtn").click(function() {
			//alert("Comment Button Clicked..");
			$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			$("#lblcomment").show();
			runComment();
			
			
		$("#setProject").hide();
		$("#calender").hide();
		$("#reminders").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();

		$("#pleaseWait").html('');
		
		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#upcomingReportBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");
		
		document.getElementById("lblcomment").style.display="";
		if($("#commentBtn").css("border")=="1px solid #175279")							
			$("#commentBtn").css("border","0px solid");
	 	else									
			$("#commentBtn").css("border","1px solid #175279");
			return false;
		});

		$("#remindersBtn").click(function() {

			$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			
			runReminders();
						
		$("#setProject").hide();
		$("#calender").hide();
		$("#commentParent").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();

		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");	  
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#upcomingReportBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");
		

		  if($("#remindersBtn").css("border")=="1px solid #175279")							
				$("#remindersBtn").css("border","0px solid");
		 	else									
				$("#remindersBtn").css("border","1px solid #175279");
					
			return false;
		});

		$("#subquerymgmtBtn").click(function() {
			//debugger;
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			//runSubquerymgmt();
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(runningSignOffReportFlag)
			{
				runningSignOffReportFlag = false;
				$("#subquerymgmt").toggle("slide","",5000,callbackSubquerymgmt);
			}
			else
			{
				runningSignOffReportFlag = true;
			}			
			if(runningSignOffReportFlag)
			{
				runSubquerymgmt();
			}
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#commentParent").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#ProjectCompletionReport").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();
		
		if(runningSignOffReportFlag)
		{
			runningSignOffReport();
		}
		
		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#upcomingReportBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");

		  /* if($("#subquerymgmtBtn").css("border")=="1px solid #175279")							
				$("#subquerymgmtBtn").css("border","0px solid");
		 	else									
				$("#subquerymgmtBtn").css("border","1px solid #175279"); */
			return false;
		});
		
		$("#upcomingReportBtn").click(function() {
			//debugger;
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(upcomingSignOffReportFlag)
			{
				upcomingSignOffReportFlag = false;
				$("#upcomingReport").toggle("slide","",5000,callbackUpcomingReport);
			}
			else
			{
				upcomingSignOffReportFlag = true;
			}			
			if(upcomingSignOffReportFlag)
			{
				runUpcomingReport();
			}	
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#subquerymgmt").hide();
		$("#commentParent").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#ProjectCompletionReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();
		
		if(upcomingSignOffReportFlag)
		{
			upcommingSignOffReport();
		}
		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");
		
		 /*  if($("#upcomingReportBtn").css("border")=="1px solid #175279")							
				$("#upcomingReportBtn").css("border","0px solid");
		 	else									
				$("#upcomingReportBtn").css("border","1px solid #175279");*/
			return false; 
		});
		$("#docReminderBtn").click(function() {
			//debugger;
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(srcDocReminderFlag)
			{
				srcDocReminderFlag = false;
				$("#SourceDocReminder").toggle("slide","",5000,callbackSourceDocReminder);
			}
			else
			{
				srcDocReminderFlag = true;
			}			
			if(srcDocReminderFlag)
			{
				runSourceDocReminder();
			}	
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#subquerymgmt").hide();
		$("#commentParent").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#ProjectCompletionReport").hide();
		$("#upcomingReport").hide();
		$("#acknowledgementDiv").hide();
		
		if(srcDocReminderFlag)
		{
			docReminderReport();
		}
		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");

		 /*  if($("#upcomingReportBtn").css("border")=="1px solid #175279")							
				$("#upcomingReportBtn").css("border","0px solid");
		 	else									
				$("#upcomingReportBtn").css("border","1px solid #175279");*/
			return false; 
		});
		
		$("#acknowledgementBtn").click(function() {
			debugger;
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(acknowledgeFlag)
			{
				acknowledgeFlag = false;
				$("#acknowledgementDiv").toggle("slide","",5000,callbackAcknowledgementDiv);
			}
			else
			{
				acknowledgeFlag = true;
			}			
			if(acknowledgeFlag)
			{
				runAcknowledgementDiv();
			}	
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#subquerymgmt").hide();
		$("#commentParent").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#ProjectCompletionReport").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		if(acknowledgeFlag)
		{
			acknowledgementReminder();
		}
		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");
		 /*  if($("#upcomingReportBtn").css("border")=="1px solid #175279")							
				$("#upcomingReportBtn").css("border","0px solid");
		 	else									
				$("#upcomingReportBtn").css("border","1px solid #175279");*/
			return false; 
		});
		
		$("#pleaseWait").css("border","0px solid"); 
		
		$("#eSignatureBtn").click(function() {
			debugger;
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(eSignatureFlag)
			{
				eSignatureFlag = false;
				$("#eSignatureDiv").toggle("slide","",5000,callbackeSignatureDiv);
			}
			else
			{
				eSignatureFlag = true;
			}			
			if(eSignatureFlag)
			{
				runeSignatureDiv();
			}	
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#subquerymgmt").hide();
		$("#commentParent").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#ProjectCompletionReport").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		
		$("#acknowledgementDiv").hide();
		
		if(eSignatureFlag)
		{
			eSignatureReminder();
		}
		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");
		
		 /*  if($("#upcomingReportBtn").css("border")=="1px solid #175279")							
				$("#upcomingReportBtn").css("border","0px solid");
		 	else									
				$("#upcomingReportBtn").css("border","1px solid #175279");*/
			return false; 
		});
		 $("#ProjectCompletionBtn").click(function() {
			//debugger;
			//$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(projectCompletionFlag)
			{
				projectCompletionFlag = false;
				$("#ProjectCompletionReport").toggle("slide","",5000,callbackProjectCompletionReport)
			}
			else
			{
				projectCompletionFlag = true;
			}			
			if(projectCompletionFlag)
			{
				runProjectCompletionReport();
			}		
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#subquerymgmt").hide();
		$("#commentParent").hide();
		$("#dossierstatustab").hide();
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();
		
		if(projectCompletionFlag)
		{
			projectCompletion();
		}
		$("#pleaseWait").html('');

		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#upcomingReportBtn").css("border","0px solid");
		$("#docReminderBtn").css("border","0px solid");
		$("#eSignatureBtn").css("border","0px solid");
		$("#acknowledgementBtn").css("border","0px solid");

		 /*  if($("#ProjectCompletionBtn").css("border")=="1px solid #175279")							
				$("#ProjectCompletionBtn").css("border","0px solid");
		 	else									
				$("#ProjectCompletionBtn").css("border","1px solid #175279");*/
			return false; 
		}); 

		$("#dossierstatustabBtn").click(function() {

				
			$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
		
			runApplicationGraph();
			
			
		$("#reminders").hide();
		$("#setProject").hide();
		$("#calender").hide();
		$("#commentParent").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();
		
		$("#pleaseWait").html('');
		
		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#projectStatusBtn").css("border","0px solid");
		$("#upcomingReportBtn").css("border","0px solid");
		 $("#docReminderBtn").css("border","0px solid");
		 $("#eSignatureBtn").css("border","0px solid");
		 $("#acknowledgementBtn").css("border","0px solid");
		
		  if($("#dossierstatustabBtn").css("border")=="1px solid #175279")							
				$("#dossierstatustabBtn").css("border","0px solid");
		 	else									
				$("#dossierstatustabBtn").css("border","1px solid #175279");
					
			return false;
		});

		$("#projectStatusBtn").click(function() {
			//debugger;
			$("#pleaseWait").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');
			if(projectStatusFlag)
			{
				projectStatusFlag = false;
				$("#projectStatusDiv").toggle("slide","",5000,callbackProjectStatus);
			}
			else
			{
				projectStatusFlag = true;
			}

			
			
			if(projectStatusFlag)
			{
				runProjectStatus();
			}

		$("#setProject").hide();
		$("#calender").hide();
		$("#commentParent").hide();	
		$("#reminders").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#dossierstatustab").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();
		
		if(projectStatusFlag)
		{
			getProjectStatus();
		}
		$("#pleaseWait").html('');
		$("#setProjectBtn").css("border","0px solid");
		$("#calenderBtn").css("border","0px solid");
		$("#commentBtn").css("border","0px solid");
		$("#remindersBtn").css("border","0px solid");
		$("#subquerymgmtBtn").css("border","0px solid");
		$("#ProjectCompletionBtn").css("border","0px solid");
		$("#dossierstatustabBtn").css("border","0px solid");
		("#upcomingReportBtn").css("border","0px solid");
		 $("#docReminderBtn").css("border","0px solid");
		 $("#eSignatureBtn").css("border","0px solid");
		 $("#acknowledgementBtn").css("border","0px solid");

			if($("#projectStatusBtn").css("border")=="1px solid #175279")
			{							
					$("#projectStatusBtn").css("border","0px solid");
					if(projectStatusFlag)
					{
						getProjectStatus();
					}
			}
			else									
				$("#projectStatusBtn").css("border","1px solid #175279");
			
			return false;
		});
		
		
		$("#setProject").hide();
		$("#calender").hide();
		$("#commentParent").hide();
		$("#reminders").hide();
		$("#subquerymgmt").hide();
		$("#ProjectCompletionReport").hide();
		$("#dossierstatustab").hide();	
		$("#projectStatusDiv").hide();
		$("#upcomingReport").hide();
		$("#SourceDocReminder").hide();
		$("#eSignatureDiv").hide();
		$("#acknowledgementDiv").hide();
		
		//Done By Harsh Shah
		var userType = '<s:property value="userTypeName"/>';
		if(userType=="WU"){
		$.ajax({
				url: 'OngoingDocCount_ex.do',
		  		success: function(data) 
		  		{	
					 $('#NoOfRunningDoc').html(data);
				}
				});
		 $.ajax({
			url: 'UpCommingDocCount_ex.do',
	  		success: function(data) 
	  		{	
				 $('#NoOfComments').html(data);
			}		
			});	
		 $.ajax({
				url: 'SrcDocReminderCount_ex.do',
		  		success: function(data) 
		  		{	
					 $('#NoOfDocReminder').html(data);
				}		
				});
		}
		 	
	});

	function getProjectStatus()
	{
	
		$.ajax({			
			url: 'getWorkspaceDtl_ex.do',
			beforeSend: function()
			{ 	
				$("#imageloader").show();						
				$("#imageloader").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');																				
			},
	  		success: function(data) 
	  		{	
				$("#imageloader").hide();
				$("#workSpaceDtl").html(data);
				//proStatus();
			},
			async: false							 
		  });	
	}
	function proStatus()
	{	
		
		var workSpaceId=$('#workSpaceDtl').val();
		$.ajax(
		{			
			url: 'ProjDocStatus_ex.do?m=W&workspaceId=' + workSpaceId,
			beforeSend: function()
			{
			 $('#projectStatusInrDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
			},
			success: function(data) 
	  		{
		  		
			  $('#projectStatusInrDiv').html(data);
			}	  				    		
		});
		return true;
	}		

	function showCommentsonStartup()
	{
		document.getElementById('btn1').style.background="#B1C7D5";
		document.getElementById('btn2').style.background="white";
		document.getElementById("lblcomment").style.display='none';
		//$("#btn2").css('background-color':'black');
		$("#commentsDiv2").show();
		$("#dispReminder").hide();
		
	}

	function showReminderonStartup()
	{
		document.getElementById('btn1').style.background="white";
		document.getElementById('btn2').style.background="#B1C7D5";
		
		$("#commentsDiv2").hide();
		$("#dispReminder").show();
	}

	
</script>
<script type="text/javascript">

function jQueryOnchangeAutocompleter()
{
	proStatus();
}


(function( $ ) {
	var calltheData=false;
	$.widget( "ui.combobox", {
			_create: function() {
			var self = this,
				select = this.element.hide(),
				selected = select.children( ":selected" ),
				value = selected.val() ? selected.text() : "";
			var input = this.input = $( "<input>" )
				.insertAfter( select )
				.val( value )
				.autocomplete({
					delay: 0,
					minLength: 0,
					source: function( request, response ) {
						var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
						response( select.children( "option" ).map(function() {
							var text = $( this ).text();
							if ( this.value && ( !request.term || matcher.test(text) ) )
								return {
									label: text.replace(
										new RegExp(
											"(?![^&;]+;)(?!<[^<>]*)(" +
											$.ui.autocomplete.escapeRegex(request.term) +
											")(?![^<>]*>)(?![^&;]+;)", "gi"
										), "<strong>$1</strong>" ),
									value: text,
									option: this
								};
						}) );
					},
					select: function( event, ui ) {
						ui.item.option.selected = true;
						jQueryOnchangeAutocompleter();
						
						self._trigger( "selected", event, {
							item: ui.item.option
						});
					},
					change: function( event, ui ) {
						if ( !ui.item ) {
							var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
								valid = false;
							select.children( "option" ).each(function() {
								if ( $( this ).text().match( matcher ) ) {
									this.selected = valid = true;
									return false;
								}
							});
							if ( !valid ) {
								// remove invalid value, as it didn't match anything
								$( this ).val( "" );
								select.val( "" );
								input.data( "autocomplete" ).term = "";
								return false;
							}
						}
					}
					
				})
				
				.addClass( "ui-widget ui-widget-content ui-corner-left" );

			input.data( "autocomplete" )._renderItem = function( ul, item ) {
				return $( "<li></li>" )
					.data( "item.autocomplete", item )
					.append( "<a>" + item.label + "</a>" )
					.appendTo( ul );
			};

			this.button = $( "<button type='button'>&nbsp;</button>" )
				.attr( "tabIndex", -1 )
				.attr( "title", "Show All Items" )
				.insertAfter( input )
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "ui-corner-right ui-button-icon" )
				.click(function() {
					// close if already visible
					if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
						input.autocomplete( "close" );
						return;
					}

					// pass empty string as value to search for, displaying all results
					input.autocomplete( "search", "" );
					input.focus();
				});
		},

		destroy: function() {
			this.input.remove();
			this.button.remove();
			this.element.show();
			$.Widget.prototype.destroy.call( this );
		}
	});
})( jQuery );

$(function() {
	
	$( "#workSpaceDtl" ).combobox();
		});

function projectCompletion(){
	//debugger;
	$.ajax({			
	  url: "ProjectCompletionReport_b.do",
	  beforeSend: function()
		{
		  $("#ProjectCompletionReport").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');								
		},
	  success: function(data) 
	  {
		  setTimeout(function() {
			  $("#ProjectCompletionReport").html(data);
	       }, 500);
  },
  error: function(data) 
  {
	alert("Something went wrong while after stage change.");
 },
		async: false
		});
}
function runningSignOffReport(){
	//debugger;
	$.ajax({			
		url: "pendingSignOff_ex.do",
		beforeSend: function()
		{
			$("#subquerymgmt").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');								
		},
	  	success: function(data) 
	  	{
	  	  //setTimeout(function() {
			  $("#subquerymgmt").html(data);
	      //}, 2000);
  },
  error: function(data) 
  {
	alert("Something went wrong while after stage change.");
 },
		async: true
		});
}
function upcommingSignOffReport(){
	//debugger;
	
	$.ajax({			
		url: "upcomingSignOff_ex.do",
		beforeSend: function()
		{
			$("#upcomingReport").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');								
		},
	  	success: function(data) 
	  	{
	  		//setTimeout(function() {
				  $("#upcomingReport").html(data);
		     // }, 2000);
  },
  error: function(data) 
  {
	alert("Something went wrong while after stage change.");
 },
		async: true
		});
	
}
function docReminderReport(){
	//debugger;
	
	$.ajax({			
		url: "docReminderReport_ex.do",
		beforeSend: function()
		{
			$("#SourceDocReminder").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');								
		},
	  	success: function(data) 
	  	{
	  		//setTimeout(function() {
				  $("#SourceDocReminder").html(data);
		     // }, 2000);
  },
  error: function(data) 
  {
	alert("Something went wrong while after stage change.");
 },
		async: true
		});
	
}

function acknowledgementReminder(){
	//debugger;
	
	$.ajax({			
		url: "userAcknowledgeMent_ex.do",
		beforeSend: function()
		{
			$("#acknowledgementDiv").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');								
		},
	  	success: function(data) 
	  	{
	  		//setTimeout(function() {
				  $("#acknowledgementDiv").html(data);
		     // }, 2000);
  },
  error: function(data) 
  {
	alert("Something went wrong while after stage change.");
 },
		async: true
		});
	
}

function eSignatureReminder(){
	//debugger;
	
	$.ajax({			
		url: "eSignStatus_ex.do",
		beforeSend: function()
		{
			$("#eSignatureDiv").html('<img title="Loading" alt="Loading" src="images/loading.gif" style="margin-top:140px; "></img>');								
		},
	  	success: function(data) 
	  	{
	  		//setTimeout(function() {
				  $("#eSignatureDiv").html(data);
		     // }, 2000);
  },
  error: function(data) 
  {
	alert("Something went wrong while after stage change.");
 },
		async: true
		});
	
}

function verifyPass()
{
     	debugger;
		var WorkspaceId=$('#wsId').text();
		var NodeId=$('#nodeId').text();
		var UserId = "<s:property value='#session.userid'/>";
		var StageId= $('#nextStageId').text();
		
		var selectValues = NodeId+'_'+StageId;
		
		//alert(WorkspaceId+","+NodeId+","+UserId+","+StageId);		
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		if (!pass || pass == '')
		{						
			alert("Please enter Password.");
			$( '#reConfPass').focus(); 
			return false;
		}
		if(sessAdUser=='Y'){
			if(sessAdUserPass!=pass){
				alert("You have AD User rights. Please enter correct password.");
				document.getElementById('reConfPass').value = '';
				$( '#reConfPass').focus(); 
				return false;
			}
		}
	if(sessAdUser!='Y'){
			if(sessPass!=pass){
			alert("Incorrect Password !!!");
			document.getElementById('reConfPass').value = '';
			$( '#reConfPass').focus(); 
			return false;
			}
		}
		
	if(sessPass==pass || sessAdUserPass==pass){
		
		$.ajax({			
			url: "CompleteWork_ex.do?workSpaceId=" + WorkspaceId+"&temp="+selectValues
							+"&targetDivId="+null+"&selectedNodeId="+NodeId+"&eSign=Y",
			 beforeSend: function()
			{ 
				 $(".pendingSignOffpoupup").hide();
				 document.getElementById("popupContact1").style.display='block'; 
			}, 
			success: function(data) 
		      {
				
				  cls();
				  document.getElementById("popupContact1").style.display='none'; 
				 // alert(data);3
			   if(data=="true")
			   {
				   debugger;
				   alert("Stage changed Successfully.");
				   
				   $.ajax({			
						url: "pendingSignOff_ex.do",
						
			 	  success: function(data) 
		     	  {
			 		 debugger;
			 		 $("#subquerymgmt").html(data);
			 		 /* $("#subquerymgmt").hide(); */
			 		 $("#subquerymgmt").show();
			 			
			 		 $.ajax({
							url: 'OngoingDocCount_ex.do',
					  		success: function(onGoingCount) 
					  		{	
								 $('#NoOfRunningDoc').html(onGoingCount);
							}
					 });
			 		 $.ajax({
			 			url: 'UpCommingDocCount_ex.do',
			 	  		success: function(upComingData) 
			 	  		{	
			 				 $('#NoOfComments').html(upComingData);
			 			}		
			 		});
				  },
				  error: function(data) 
				  {
					alert("Something went wrong while after stage change.");
				 },
						async: false
		      		});
				 }
			   else if(data=="sendback"){
				   alert("Document is Send back by other user.");
			   }
		      },
			  error: function(data) 
			  {
				alert("Something went wrong while stage change.");
			  },
				async: false
			});
		}
}

function ChangeFileStatusForESignature(workSpaceId,nodeID,checkFileOpenSignOff){
	var temp="true";
	debugger;
	//alert("WsId="+workSpaceId+"&NodeId="+nodeID);
		if(checkFileOpenSignOff=="Yes"){
			 $.ajax({		
			  url: "checkfileopenforsign_ex.do?nodeId="+nodeID+"&workspaceID="+workSpaceId,
			  success: function(data) 
			  {
				 //debugger;
				 temp = data;
				 if(data=="false"){
				  	alert("Please review document before sign off.");
				  	 document.getElementById("popupContact1").style.display='none'; 
				  	return false;
				 }
			  },
			  error: function(data) 
			  {
				alert("Something went wrong while fetching data from server.");
			  },
				async: false
			});
		}

		if(temp!="false"){
			var docType = 0004;
			var signatureFlag = "<s:property value='signatureFlag'/>";
			if(signatureFlag=="false"){
		//alert("Please create signature before proceeding sign off.");
				var proceed = confirm("Please create signature before proceeding sign off.");
					if (proceed) {
		  					window.open('Signature.do');
					return false;
					} else {
					return false;
					}
			 }
				var win = window.open("ChangeStatusDocSignForESignature.do?docId="+workSpaceId+"&recordId="+nodeID+"&docType=0004");
				var timer = setInterval(function() {
				if (win.closed) {
				clearInterval(timer);
				location.reload();
				}
				}, 500);
		}
	}
	
function ChangeFileStatus(wsId,nodeId,userCode,nextStageId,nextStageDesc,userName,checkFileOpenSignOff,
							roleName,signId,signImg,signStyle,dateForPreview,manualSignatureConfig,applicationUrl){
		var temp="true";
		//debugger;
		//alert("WsId="+wsId+"&NodeId="+nodeId+"&userCode="+userCode+"&NextStageId="+nextStageId);
		if(checkFileOpenSignOff=="Yes"){
		 $.ajax({		
		  url: "checkfileopenforsign_ex.do?nodeId="+nodeId+"&workspaceID="+wsId,
		  success: function(data) 
		  {
			 //debugger;
			 temp = data;
			 if(data=="false"){
			  	alert("Please review document before sign off.");
			  	 document.getElementById("popupContact1").style.display='none'; 
			  	return false;
			 }
		  },
		  error: function(data) 
		  {
			alert("Something went wrong while fetching data from server.");
		  },
			async: false
		});
	}
		 if(temp!="false"){
		var signatureFlag = "<s:property value='signatureFlag'/>";
		if(signatureFlag=="false"){
			//alert("Please create signature before proceeding sign off.");
			var proceed = confirm("Please create signature before proceeding sign off.");
			if (proceed) {
  				window.open('Signature.do');
				return false;
			} else {
				return false;
			}
		}
		if(manualSignatureConfig=="Yes"){
			/* var signatureFlag = "<s:property value='signatureFlag'/>";
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			if(deviationRemark==undefined)
				deviationRemark="";
			 */
			var win = window.open(applicationUrl+"ManualSignature.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&selectedStage="+nextStageId, "_blank");
			var timer = setInterval(function() {
		        if (win.closed) {
		            clearInterval(timer);
		            location.reload();
		        }
		    }, 500);
			
		}
		else{
			$(".pendingSignOffpoupup").show();
			$( '#reConfPass').focus(); 
			var windowWidth = document.documentElement.clientWidth;
			var windowHeight = document.documentElement.clientHeight;
			var popupHeight = $("#popupContact").height();
			var popupWidth = $("#popupContact").width();
			//centering
			 $("#popupContact").css({
				"position": "absolute",
				"top": "65.5px",
				"left": "440.5px"
			}); 
			
			$("#backgroundPopup").css({
				"height": windowHeight
			});
			debugger;
			if(signStyle ==""){
				logoname=signImg;
				document.getElementById('imgTd').style.display = 'block';
				document.getElementById('styleTd').style.display = 'none';
				document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;
			}
			else{
				document.getElementById('imgTd').style.display = 'none';
				document.getElementById('styleTd').style.display = 'block';
				signImgs=signStyle; 
				document.getElementById('signStyle').innerHTML = userName;
				document.getElementById("signStyle").style.fontFamily = signStyle;
			}
			document.getElementById('wsId').innerHTML = wsId;
			document.getElementById('nodeId').innerHTML = nodeId;
			document.getElementById('nextStageId').innerHTML = nextStageId;
			document.getElementById('nextStageDesc').innerHTML = nextStageDesc;
			document.getElementById('userName').innerHTML = userName;
			document.getElementById('userNameForPreview').innerHTML = userName;
			document.getElementById('roleName').innerHTML = roleName;
			document.getElementById('dateForPreview').innerHTML = dateForPreview;
			document.getElementById('signId').innerHTML = signId;
			////debugger;
			var  months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
			var monthName; // "July" (or current month)

			var currentdate = new Date();
			var datetime =  currentdate.getDate()+ "-" 
		    +  months[currentdate.getMonth()] + "-" 
		    + currentdate.getFullYear() + " "
		    //+ currentdate.getHours() + ":"  
		    //+ currentdate.getMinutes();
			document.getElementById("date").innerHTML = datetime;
		}
		
	  }
	}
	
/* function detectReturnKey(evt) 
{ 
		if ((event.keyCode == 13))  
		{
			return false;
		} 
} */

function detectReturnKey(evt) 
{ 
	if (event.keyCode === 13) {
        event.preventDefault();
        document.getElementById("Verify").click();
    }
}

document.onkeypress = detectReturnKey;

function cls()
{
	document.getElementById('reConfPass').value = '';
	$(".pendingSignOffpoupup").hide();

}

function openlinkEdit(buttonName,workSpaceID,stageIds,userCode,nodeId){
	//debugger;
	var button = buttonName;
	var modulewiserights="modulewiserights";
	str="ChangeWSRightsFromHomePage_b.do?workSpaceID="+workSpaceID+"&stageIds="+stageIds+"&userCode="+userCode+"&nodeId="+nodeId+"&RightsType="+modulewiserights;
	win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");	
	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
	return true;
}

function approvalofacknowledgement(workSpaceID,userCode){
	$.ajax({			
		url: "acknowledgementUpdate_ex.do?workspaceID="+workSpaceID,
	  	success: function(data) 
	  	{
	  		if(data == "true"){
	  			alert("Acknowledgement Successful.");	
	  			$.ajax({			
	  				url: "userAcknowledgeMent_ex.do",
	  			  	success: function(data) 
	  			  	{
	  			  		//setTimeout(function() {
	  						  $("#acknowledgementDiv").html(data);
	  				     // }, 2000);
	  		  }});
	  		}
	  		if(data == "false"){
	  			alert("Acknowledgement failed.");	
	  		}
	  		
  		},
  		error: function(data) 
  		{
			alert("Something went wrong while after stage change.");
 		},
		async: true
		});
	//alert("workspace id = "+ workSpaceID + " usercode = " + userCode);
	return true;
	
}

function openlinkEditForCR(buttonName,workSpaceID,stageIds,userCode,nodeId){
	//debugger;
	var button = buttonName;
	var modulewiserights="modulewiserights";
	str="ChangeWSRightsForCRFromHomePage_b.do?workSpaceID="+workSpaceID+"&stageIds="+stageIds+"&userCode="+userCode+"&nodeId="+nodeId+"&RightsType="+modulewiserights;
	win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");	
	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
	return true;
}
</script>

<script src="<%=request.getContextPath()%>/js/welcome.js"
	type="text/javascript"></script>
</head>

<body>
<div id="popupContact1" style="width: 100px; z-index: 1;height: 197px;position: absolute;
						top: 200.5px;left: 867.5px;display: block;padding-top: 29px;display:none">
   <center><img style="margin-top:100px" src="images/loading.gif" alt="loading ..." /></center>
		</div>
<div class="pendingSignOffpoupup">
<div id="popupContact" style="height: 440px;" >

   <img	alt="Close" title="Close" src="images/Common/Close.svg"	style="height:25px; width:25px;" onclick='cls();' class='popupCloseButton'/>
		<h3 style="margin-top: 2px;">Electronic Signature</h3>
		<!-- <div style="width: 100%; height: 90px; overflow: auto;"> -->
		<table style="width:100%">
		<tr><td class="popUpTdHdr"><b>Username: &nbsp;</b></td>
		<td class="popUpTd"><span id="userName"></span></td>
		</tr>
		<tr><td class="popUpLbl"><b>Date: &nbsp;</b></td>
		<td class="popUpTd"><span id="date"></span></td>
		</tr>
		<tr><td class="popUpLbl"><b>Action: &nbsp;</b></td>
		<td class="popUpTd"><span id="nextStageDesc"></span></td>
		</tr>
		<tr><td class="popUpLbl"><b>Password: &nbsp;</b></td>
		<td class="popUpTd">
		<!-- <input type="password" name="reConfPass"id="reConfPass"></input> -->
		<input type="text" name="reConfPass"id="reConfPass" autocomplete="off"></input>
		</td>
		</tr>
		</table>
		<hr style="margin-top: 10px; margin-bottom: 10px;"><h3 style="margin-top: 2px;">Preview Signature</h3>
					<table id="tble" style="width: 100%">
					<tr>
						<td colspan="2" style="border: 1px solid #222; text-align: center; font-weight: bold; font-size: 18px; 
										background-color: gray;	color: black;">Signature</td>
					</tr>
					<tr style="border: 1px solid #222;">
					<td style="vertical-align: top; padding:10px  10px;">
					<table id="tble" style="width: 100%">
						<tr >
							<td id ="imgTd" style="display:none">
							<img src="" id="PreviewImg" height="90px" width="200px" name="PreviewImg" alt="Picture">
							</td>
							<td id="styleTd" style="display:none" >
							<label style="height: 90px;width: 200px; text-align: center; padding-top: 20px; font-size: 24px; color: black;">
							<span id="signStyle"></span></label>
							</td>
						</tr>
						</table>
					</td>
					
					<td>
					<table id="tble" style="width: 100%;align:left;">
						
						<tr>
							<td style="color: #000000; width: 55px; padding: 0 !important; text-align: left; vertical-align: top;"><b>Name:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important;  vertical-align: top;"><span id="userNameForPreview"></span></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important;padding: 0; text-align: left; vertical-align: top;"><b>Role:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important; vertical-align: top;"><span id="roleName"></span></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0!important; text-align: left; vertical-align: top;"><b>Date:&nbsp;</b></td>
							<td style="color: #000000; padding: 0!important; vertical-align: top;"><span id="dateForPreview"></span></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important; text-align: left; vertical-align: top;"><b>Sign Id:&nbsp;</b></td>
							<td style="color: #000000; padding: 0!important; vertical-align: top;"><span id="signId"></span></td>
						</tr>
					</table>
					</td>					
					</tr>					
					</table>	
		<br>
		<p align="center">
		<span id="wsId" style="display:none"></span>
		<span id="nodeId" style="display:none"></span>
		<span id="nextStageId" style="display:none"></span>
		<span><b>I hereby confirm signing of this document electronically.</b></span><br><br>
			<input type="submit" class="button"
				value="Sign" id="Verify" name="buttonName" onclick="return verifyPass();"></input>
		</p>
		<!-- </div> -->
		</div>
		<div id="backgroundPopup"></div>
		</div>
<div><s:if
	test='reminder.equals("Yes") && showReminder.equals("yes")'>
	<div id="popupContact" style="width: 750px; height: 310px; margin-top: -20px">

	<table style="width: 98%; margin-top: -8px;"cellspacing="0px">
		<tr>
			<th id="btn1"
				style="text-align: center; border: 1px solid #175279; border-bottom: 1px solid #175279; width: 49%;"
				onclick="showReminderonStartup()"><label
				style="cursor: pointer;">Reminder</label></th>
			<th id="btn2"
				style="text-align: center; border: 1px solid #175279; border-bottom: 1px solid #175279; background: #B1C7D5; width: 49%;"
				onclick="showCommentsonStartup()"><label
				style="cursor: pointer;">User Comments</label></th>
			<!-- <th id="th3" style="text-align:center;border: 1px solid black;border-bottom: 0px solid black;background:silver;" onclick="f(3,3);"><label style="cursor: pointer;">Trash</label></th> -->
		</tr>
	</table>
	<a id="popupContactClose"><img alt="Close" title="Close" src="images/Common/Close.png" /></a>
	<hr color="#5A8AA9" size="3px" style="width: 100%; border-bottom: 2px solid #CDDBE4" align="left">

	<div style="width: 100%; height: 240px;" align="center"	id="commentsDiv2"></div>

	<div style="width: 100%; height: 240px;" align="center" id="dispReminder"><s:url action="ShowReminders_ex" id="remUrl">
		<s:param name="showWhat" value="2" />
	</s:url> <s:div id="rDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{remUrl}" separateScripts="true" indicator="imgLoading"
		loadingText="&nbsp;">
	</s:div> <img id="imgLoading" name="imgLoading" border="0px" title="Loading"
		alt="Loading" src="images/loading.gif" height="40px" width="40px"
		style="display: none;"></div>

	</div>


	<div id="backgroundPopup"></div>
</s:if>
<div class="container-fluid">
          <div class="">
<div class="col-md-3" align="left"><s:if test="noticeboard == 'Yes'">
	<div style="border: 1px solid #175279; height: 350px;"
		align="left"><img src="images/WelCome_Page/notice_board.jpg" height="24px;" width="300px;">
	<div align="left" style="margin-top: 0px; height: 275px; overflow: auto; width: auto; width: 300px;">
	<s:iterator value="allNotices">
		<ul>
			<li class="headernotice"><s:property value="Subject" /></li>
			<li class="noticedesc"><s:property
				value="Description.replaceAll('\r\n','<br>')" escape="false" /></li>
			<s:if test="Attachment != \"\"">
				<li class="noticedesc"><a target="_blank"
					href="openfile.do?fileWithPath=<s:property value="attachmentsFolderPath"/>/<s:property value="Attachment"/>"><s:property
					value="Attachment" /></a></li>
			</s:if>
			<li style="text-align: right;" class="noticedesc"><s:property
				value="UserNameOfCreator" /></li>
			<li style="text-align: right;" class="noticedesc"><s:date
				name="ModifyOn" format="dd-MMM-yyyy HH:mm" /></li>
		</ul>
	</s:iterator></div>
	</div>
</s:if>
<s:if test="userTypeName=='WA' || userTypeName=='WU'">
<div class="boxborder" style="border: 1px solid #175279;" align="left">
<div class="all_title"><b>Recent Project/s</b>
</div>
<!--  <img src="images/WelCome_Page/latestwspace.png" height="24px;" width="300px;" />-->
	
	<s:if test="latestWspace.size==0">
	<br>
		<span style="margin-left:60px;"><label>No details available.</label></span>
	</s:if>
	<s:else>
	<ul style="padding-bottom:5px; padding-top:10px">
	<s:iterator value="latestWspace" status="status">
	
		<li style="padding-bottom:5px;" class="workspacedesc"><a
			href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>"><s:property
			value="workspacedesc"></s:property></a>
			</li>
			<s:if test="#status.last != true ">
			<hr class="hr1">
			</s:if>
	
</s:iterator>
</ul>
</s:else>

</div>
</s:if>
</div>
<div class="col-md-8" >
<s:if test="setproject.equalsIgnoreCase('YES')">
	<div align="center" style="z-index: 1000; border: none;">
	<div id="setProject" class="ui-widget-content ui-corner-all"
		style="padding-left: 0px; width: 90%; float: right; height: 200px; padding-top: 0px; padding-right: 10px; border: none;"
		align="center">
		<s:url action="DefaultWorkSpace_b" id="DefaultWorkSpaceURL">
			<s:param name="workSpaceId" value="%{workSpaceId}"></s:param>
	</s:url>
	<table style="cellpadding:2; cellspacing:2; width:100%;" align="left" >
		<tr>
			<td style="border: 1px solid #175279;" align="left">
			<div class="boxborder"
				style="height: 31px; border: 1px solid #175279; /* background: url('images/WelCome_Page/bg_project.jpg') */ repeat-x;"><!-- <img
				align="left" src="images/WelCome_Page/projects.jpg" height="24px;"
				width="300px;" style="padding-left: 5px;"> -->
				<div class="all_title"><b>Set Projects</b></div></div>

			<s:div id="DefaultWorkspace" autoStart="true" theme="ajax"
				cssStyle="text-align: center; padding-top: 5px;margin-left: 2px;margin-right: 2px; "
				executeScripts="true" href="%{DefaultWorkSpaceURL}"	separateScripts="true">
			</s:div></td>
		</tr>
	</table>
	<div style="margin-left: 2%; margin-right: 2%;"></div>

	</div>
	</div>
</s:if>

<s:if test="projectCompletionReport.equalsIgnoreCase('YES')">
	<%-- <div id="ProjectCompletionReport" class="ui-widget-content ui-corner-all"
		style="padding-left: 0px; width: 90%; float: right; height: 200px; padding-top: 0px; padding-right: 10px; border: none;"
		align="center"><s:url action="ProjectCompletionReport_b"
		id="ProjectCompletionReport">
		<s:param name="workSpaceId" value="%{workSpaceId}"></s:param>
	</s:url>
	<div style="margin-left: 2%; margin-right: 2%;"></div>

	</div> --%>
	<div align="center" style="z-index: 1000;">
	<div id="ProjectCompletionReport" align="center" style="float: right; width:100%; height: 280px;">
	<div style="width: 100%; height: 220px;"><%-- <s:url
		action="ProjectCompletionReport_b" id="projectCompletion"></s:url> <s:div
		id="ProjectCompletionReport" autoStart="true" theme="ajax" executeScripts="true"
		href="%{projectCompletion}" separateScripts="true"></s:div> --%></div>
	</div>
	</div>
</s:if>

<div align="center" style="z-index: 1000;">
<div id="commentParent" align="center"
	style="width: 560px; float: right; height: 250px; padding-right: 10px; display: none">
<div id="commentsDiv"></div>
</div>
</div>


<s:if test="calendar.equalsIgnoreCase('YES')">
	<div align="center" style="z-index: 1000;">
	<div id="calender"
		style="padding-left: 0px; padding-bottom: 0px; width: 400px; float: right; height: 300px; padding-right: 50px;"
		align="center"><s:url action="cal_b.do" id="calUrl"></s:url> <s:div
		id="calDiv" autoStart="true" theme="ajax" executeScripts="true"	href="%{calUrl}" separateScripts="true">
	</s:div></div>
	</div>
</s:if> <s:if test="reminder.equalsIgnoreCase('YES')">
	<div align="center" style="z-index: 1000;">
	<div id="reminders" align="center"
		style="width: 560px; float: right; height: 290px; padding-right: 10px;">
	<div
		style="width: 100%; height: 279px; width: 550px; border: 1px solid #175279">
	<s:url action="ShowReminders_ex" id="remUrl">
		<s:param name="showWhat" value="2" />
	</s:url>
	 <s:div id="rDiv" autoStart="true" theme="ajax" executeScripts="true" href="%{remUrl}" separateScripts="true">
		<img border="0px" title="Loading" alt="Loading"	src="images/loading.gif" height="40px" width="40px" align="middle">
	</s:div>
	</div>
	</div>
	</div>
</s:if> <s:if test="subquerymgmt.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div align="center" style="z-index: 1000;">
	<div id="subquerymgmt" align="center" style="float: right; width:100%; height: 280px;">
	<div style="width: 100%; height: 220px;"><%-- <s:url
		action="pendingSignOff_ex" id="subqueryUrl"></s:url> <s:div
		id="subqueryDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{subqueryUrl}" separateScripts="true"></s:div> --%></div>
	</div>
	</div>
</s:if> 
<s:if test="upcomingFilesReports.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div align="center" style="z-index: 1000;">
	<div id="upcomingReport" align="center"  style="float: right; width:100%; height: 280px;">
	<div style="width: 100%; height: 220px;"><%-- <s:url
		action="upcomingSignOff_ex" id="upcomingReportUrl"></s:url> <s:div
		id="upcomingReportDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{upcomingReportUrl}" separateScripts="true"></s:div> --%></div>
	</div>
	</div>
</s:if> 
<s:if test="srcDocReminder.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div align="center" style="z-index: 1000;">
	<div id="SourceDocReminder" align="center"  style="float: right; width:100%; height: 280px;">
	<div style="width: 100%; height: 220px;"><%-- <s:url
		action="upcomingSignOff_ex" id="upcomingReportUrl"></s:url> <s:div
		id="upcomingReportDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{upcomingReportUrl}" separateScripts="true"></s:div> --%></div>
	</div>
	</div>
</s:if> 
<%-- <s:if test="pendingReport.equalsIgnoreCase('YES')">
	<div align="center" style="z-index: 1000;">
	<div id="pendingReport" align="center"
		style="width: 560px; float: right; height: 270px; padding-right: 10px;">
	<div style="width: 100%; height: 220px;"><s:url
		action="pendingSignOff_ex" id="pendingUrl"></s:url> <s:div
		id="pendingDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{pendingUrl}" separateScripts="true"></s:div></div>
	</div>
	</div>
</s:if> --%>

<s:if test="acknowledgementReminder.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div align="center" style="z-index: 1000;">
	<div id="acknowledgementDiv" align="center"  style="float: right; width:100%; height: 280px;">
	<div style="width: 100%; height: 220px;"><%-- <s:url
		action="upcomingSignOff_ex" id="upcomingReportUrl"></s:url> <s:div
		id="upcomingReportDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{upcomingReportUrl}" separateScripts="true"></s:div> --%></div>
	</div>
	</div>
</s:if>

<%-- <s:if test="acknowledgementReminder.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')"> --%>
	<div align="center" style="z-index: 1000;">
	<div id="eSignatureDiv" align="center"  style="float: right; width:100%; height: 280px;">
	<div style="width: 100%; height: 220px;"><%-- <s:url
		action="upcomingSignOff_ex" id="upcomingReportUrl"></s:url> <s:div
		id="upcomingReportDiv" autoStart="true" theme="ajax" executeScripts="true"
		href="%{upcomingReportUrl}" separateScripts="true"></s:div> --%></div>
	</div>
	</div>
<%-- </s:if> --%>
<s:if test="dossierstatustab.equalsIgnoreCase('YES')">
	<div align="center" style="z-index: 1000;">
	<div id="dossierstatustab" align="center"
		style="width: 560px; float: right; min-height: 250px; border: 1px solid #5B89AA; margin-right: 10px">
	<div><s:url action="DossierStatusTab_ex"
		id="dossierstatustabhUrl"></s:url> 
		<s:div id="dossierstatustabhDiv" autoStart="true" theme="ajax" executeScripts="true"	
				href="%{dossierstatustabhUrl}" separateScripts="true" cssStyle="width:560px;">
		</s:div>
	</div>
	</div>
	<!-- tooltip div tag is start -->
	<div id="wsdetailtooltip1"
		style="display: none; position: absolute; max-height: 180px; width: 300px; top: 0; left: 0; visibility: hidden; background: #EEEEEE;">
	<div style="float: right; width: 100%" align="right"><img
		align="right" alt="Close" title="Close" src="images/Common/Close.svg"
		onclick="hideToolTip();"></div>
	<div align="center" class="title">Project Detail</div>
	<div id="wsdetailtooltip2"
		style="max-height: 130px; width: 275px; overflow: auto; border: 1px solid #0C3F62; margin: 10px; margin-top: 0px;"></div>
	</div>
	</div>
</s:if> <s:if test="projectdocstatus.equalsIgnoreCase('YES')">
	<div align="center" style="z-index: 1000; overflow: hidden;">
	<div id="projectStatusDiv" align="center"
		style="width: 90%; float: right; height: 398px; padding-right: 10px; display: none; border: 1px solid #5B89AA;">
	<div style="z-index: 2000; position: relative">
	<table width="100%" style="overflow: auto; z-index: 1;">
		<tr>
			<th class="title" colspan="2">Project Document Status</th>
		</tr>
		<tr align="left">
			<td class="title" align="right"
				style="padding: 2px; padding-right: 8px;">Project Name</td>

			<td><SELECT id="workSpaceDtl" name="workSpaceDtl"></SELECT></td>
		</tr>
	</table>
	</div>
	<div id="projectStatusInrDiv" style="z-index: 0;"></div>
	</div>
	</div>
</s:if>
</div>
<div class="col-md-1" >

<div style="margin-top: 10px; padding: 0px; white-space: nowrap;"
	align="right"><s:if test="setproject.equalsIgnoreCase('YES')">
	<img title="Set Project" src="images/WelCome_Page/set_project.jpg" id="setProjectBtn"
			style="cursor: pointer; border: 0px solid;"	height="45px" width="50px;" alt="Set Project" />
	<br /><br />
</s:if>
<s:if test="projectCompletionReport.equalsIgnoreCase('YES') && (userTypeName.equalsIgnoreCase('WU') || userTypeName.equalsIgnoreCase('WA'))">
	<div class="dashboardreporticon"><img title="Project Progress Status" src="images/WelCome_Page/project_Status.svg"
		id="ProjectCompletionBtn" style="cursor: pointer; border: 0px solid;"height="50px" width="50px;" alt="Project Completion Report" />
	<br /><span style="color: #2e7eb9; font: bold 13px Calibri;">Project Progress<br/> Status</span><br /></div>
</s:if>
 <s:if test="comments.equalsIgnoreCase('YES')">
	<img title="Comments" id="commentBtn"
		style="cursor: pointer; border: 0px solid;"
		src="images/WelCome_Page/show_comments.jpg" height="45px"
		width="50px;" alt="Calendar" />
	<br /><br />
</s:if> <s:if test="calendar.equalsIgnoreCase('YES')">
	<img title="Calender" id="calenderBtn" 
	style="cursor: pointer; border: 0px solid" src="images/WelCome_Page/calendar.jpg" height="45px" width="50px;" alt="Calendar" />
	<br /><br />
</s:if> <s:if test="reminder.equalsIgnoreCase('YES')">
	<img title="Reminder" id="remindersBtn"
		style="cursor: pointer; border: 0px solid;"	src="images/WelCome_Page/reminders.jpg" height="45px" width="50px;"	alt="Reminder" />
	<br /><br />
</s:if>

<s:if test="subquerymgmt.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div class="dashboardreporticon"><img title="Ongoing Task/s" id="subquerymgmtBtn"
		style="cursor: pointer; border: 0px solid;"	src="images/WelCome_Page/running_Task.svg" height="50px" width="50px;" alt="ubQueryMgmt" />
	<div id="NoOfRunningDoc"></div><br/><span style="color: #2e7eb9; font: bold 13px Calibri;">Ongoing Task/s</span><br /></div>
	

</s:if><%-- <s:if test="pendingReport.equalsIgnoreCase('YES')">
	<img title="Pending Sign Off Report" id="pendingReportBtn"
		style="cursor: pointer; padding-bottom: 10px; border: 0px solid;"
		src="images/WelCome_Page/subquery.jpg" height="45px" width="50px;"
		alt="pendingReport" />
	<br />
</s:if> --%>
<s:if test="upcomingFilesReports.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div class="dashboardreporticon"><img title="Upcoming Task/s" id="upcomingReportBtn"
		style="cursor: pointer; border: 0px solid;"	src="images/WelCome_Page/upcoming_Task.svg" height="50px" width="50px;"	alt="ubQueryMgmt" />
		<div id="NoOfComments"></div>
	<br /><span style="color: #2e7eb9; font: bold 13px Calibri;">Upcoming Task/s</span><br /></div>
</s:if>
 <s:if test="dossierstatustab.equalsIgnoreCase('YES')">
	<img title="Dossier Status" id="dossierstatustabBtn"
		style="cursor: pointer; " src="images/WelCome_Page/dossier_status.jpg" height="45px" width="50px;" alt="Dossier Status" />
	<br /><br />
</s:if> <s:if test="projectdocstatus.equalsIgnoreCase('YES')">
	<img title="Project Document Status" id="projectStatusBtn"
		style="cursor: pointer; " src="images/WelCome_Page/project_Status.jpg" height="45px" width="50px;" alt="Project Document Status" />
	<br /><br />
</s:if>
<s:if test="srcDocReminder.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	
	<div class="dashboardreporticon"><img title="Doc Reminder/s" id="docReminderBtn"
		style="cursor: pointer; border: 0px solid; margin-left: 7px;" src="images/WelCome_Page/reminders.svg" height="50px" width="50px;" alt="Doc Reminder" />
		<div id="NoOfDocReminder"></div>		
	<br /><span style="color: #2e7eb9; font: bold 13px Calibri;">Doc Reminder/s</span><br /></div>
	
</s:if>

<s:if test="acknowledgementReminder.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')">
	<div class="dashboardreporticon"><img title="Acknowledgement Status" id="acknowledgementBtn"
		style="cursor: pointer; border: 0px solid;"	src="images/WelCome_Page/running_Task.svg" height="50px" width="50px;" alt="Acknowledgement" />
		<br/><span style="color: #2e7eb9; font: bold 13px Calibri;">Acknowledgement <br/> Status</span><br/>
	</div>
</s:if>

<s:if test="eSignatureStatus.equalsIgnoreCase('YES') && userTypeName.equalsIgnoreCase('WU')"> 
	<div class="dashboardreporticon"><img title="Esign Reminder" id="eSignatureBtn"
		style="cursor: pointer; border: 0px solid;"	src="images/WelCome_Page/running_Task.svg" height="50px" width="50px;" alt="E Signature" />
		<div id="NoOfEsignDocReminder"></div>
		<span style="color: #2e7eb9; font: bold 13px Calibri;">E Signature <br/>Reminder</span><br/>
	</div>
</s:if>
</div>


<div id="pleaseWait" align="center"></div>
</div>
</div>

<div  class="panel-body" ng-bind-html="myvar" id="docStackGraphTemplate">

</div>

<s:if test="userTypeName=='Inspector'">

<div id="inspectorId" style="color:#337ab7; margin-top:170px;">
	<h1>Welcome, Inspector</h1>
</div>  
</s:if>

</div>
</div>

<div class="querypopup"><span id="backgroundPopup"></span> <span
	id="popupContact" style="width: 700px; height: 470px;"> </span></div>
	
<script>

$(".querypopup").hide();
$(".pendingSignOffpoupup").hide();
</script>
</body>
</html>