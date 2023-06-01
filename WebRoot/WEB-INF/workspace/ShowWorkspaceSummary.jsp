<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>

<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
	
	<script src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"
	type="text/javascript"></script>

<style>

/* #confirmDialog {
	background: white;
	color: #5B89AA;
	position: fixed;
	left: 35%;
	margin-top: 20px;
	width: 30%;
	top: 100px;
	border: 3px solid white;
	font-size: 17px;
} */


</style>
<script language="javascript">
var allWorkspace = [];

var allFileApproved;
var wsid; 
var statusIndi;
 $(document).ready(function() { 
	 $('.toggleDiv').hide();
	 $('#jquery_datatable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"bDestroy": true,
			"iDisplayLength": -1,
		 });
	 
	$(".showData").click(function(e){
	
		var height = $('#projStatusDiv').height();
		var width = $('#projStatusDiv').width();
		//calculating offset for displaying popup message
		var leftVal=e.pageX-(width+10)+"px";
		//topVal=e.pageY-(height-5)+"px";
		var topVal=e.pageY+"px";
		var wsId = this.id.split('_')[1];
		
		 $.ajax({
			 
			 url : "ProjDocStatus_ex.do?workspaceId="+wsId,
			 beforeSend: function()
				{ 
					$('#projDocDiv').html('');								
				},
			 success:function(data)
			 {
					
				
				$('#projStatusDiv').css({left:leftVal,top:topVal,display:'block',visibility:'visible'}).show();
				var str="";
				var d = data.split(',');
				var leafCnt;
				var fileCnt;
				var cnt;
				
				for(var i=0;i<d.length;i++)
				{
					if(d[i].match("Per"))
					{
						str += d[i].replace("Per","");
						
						if(d[i].match("Leaf"))
						 {
							var re = /(Leaf)(Nodes)/g;
							var str2 = str.replace(re,'$1 $2');
							str=str2;
						 }
						str +="%"+"<br/>";
						}
					}
					$('#projDocDiv').html(str);
				 }
			 });
		});
		$('#close').click(function() {
			$('#projStatusDiv').hide();
		});	
	});
 
/*  function donotproceed()
	{
		$("#backgroundPopup").css('opacity','0');
		$("#backgroundPopup").hide(1000);	
		$("#confirmDialog").hide(1000);
	}
	function proceed()
	{
		//debugger;
		//alert(allFileApproved);
		var archive = true;
		
		if(allFileApproved == "true"){
			
			$.ajax({
				 
				 url : "DeleteWorkspace_ex.do?workspaceId="+wsid+"&archive="+archive+"&statusIndi="+statusIndi,
				 async: false,
				 beforeSend: function()
					{ 
					
						$('#statusDetails').html('');								
					},
				 success:function(data)
				 {
					 $("#workspaceSummary").html("");
					 alert("Project Archived Successfully.")
					 $("#backgroundPopup").css('opacity','0');
					 $("#backgroundPopup").hide(1000);	
					 $("#confirmDialog").hide(2000);	
				 }
				 });
		}
		else{
			alert("Please Approved All Node & Files.");
			$("#backgroundPopup").css('opacity','0');
			$("#backgroundPopup").hide(1000);	
			$("#confirmDialog").hide(2000);
		}
		
	} */
	
	function edit(){
		alert("You can not do any activity in locked project.");
	}
function checkLockSeq(workspaceId,wname,statusIndi){
	
	//debugger;
 	wsid = workspaceId;
 	statusIndi = statusIndi;
   	 $.ajax({
		 
		 url : "CheckLockSeq_ex.do?workspaceId="+wsid,
		 async: false,
		 beforeSend: function()
			{ 
			$('#statusDetails').html('');								
			},
		 success:function(data)
		 {
			 //alert(data);
			 //debugger;
			 var lockFlag=data;
			 if(lockFlag=="No")
			 {
				alert("Please Lock At Least One Sequence.");
			 }
			 else{
				 archive(workspaceId,wname,statusIndi);
			 }
		 }
		 });
}	
  function archive(workspaceId,wname,statusIndi){
	
	 	//debugger;
	 	wsid = workspaceId;
	 	statusIndi = statusIndi;
	 	var archive = true;
		
	 	/* var okCancel = confirm("Do you really want to Archive selected Project ?");
	    if(okCancel == true){ */
	    		
	        var remark = prompt("Please specify reason for change.");
	    		remark = remark.trim();
			if (remark != null && remark != ""){  
				 $.ajax({
						 
						 url : "ArchiveProject_ex.do?workspaceId="+wsid+"&archive="+archive+"&statusIndi="+statusIndi+"&remark="+remark,
						 async: false,
						 beforeSend: function()
							{ 
							
								$('#statusDetails').html('');								
							},
						 success:function(data)
						 {
							 alert(data);
							 var out="WorkspaceSummay.do";
							 location.href=out;
						 }
			 });
	      }
	    //}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	} 
 function unArchive(workspaceId, statusIndi)
	{		
	 	//debugger;
		wsid = workspaceId;
	 	statusIndi = statusIndi;
	 	var archive = true;
	 	/* var okCancel = confirm("Do you really want to Archive selected Project ?");
	    if(okCancel == true){ */
	 	
	    var remark = prompt("Please specify reason for change.");
	    remark = remark.trim();
		if (remark != null && remark != ""){ 
			 $.ajax({
		 
		 		url : "UnArchiveProject_ex.do?workspaceId="+wsid+"&archive="+archive+"&statusIndi="+statusIndi+"&remark="+remark,
				async: false,
		 		beforeSend: function()
				{ 
				    $('#statusDetails').html('');								
				},
		 		success:function(data)
				{
			 	alert(data);
				var out="WorkspaceSummay.do";
			 	location.href=out;
		 		}
		 	});
    	 }
		//}
	    else
	    	alert("Please specify reason for change.");
			return false;	
	}
 function projectDetailHistory(wsId)
	{
	 //debugger;
		if (wsId == null)
		{
			alert("Please Select Project");
			return false;
		}else{	
			
			str="ProjectDetailHistory_b.do?workspaceId="+wsId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1500,resizable=no,titlebar=no");
		 	win3.moveTo(screen.availWidth/2-(1100/2),screen.availHeight/2-(500/2));	
			return true;
		}
	}
 function voidProject(workspaceId,statusIndi){
		
	 	//debugger;
	 	wsid = workspaceId;
	 	statusIndi = statusIndi;
		
	 	/* var okCancel = confirm("Do you really want to Archive selected Project ?");
	    if(okCancel == true){ */
	    		
	        var remark = prompt("Please specify reason for change.");
	    		remark = remark.trim();
			if (remark != null && remark != ""){  
				 $.ajax({
						 
						 url : "VoidProject_ex.do?workspaceId="+wsid+"&statusIndi="+statusIndi+"&remark="+remark,
						 async: false,
						 beforeSend: function()
							{ 
							
								$('#statusDetails').html('');								
							},
						 success:function(data)
						 {
							 alert(data);
							 var out="WorkspaceSummay.do";
							 location.href=out;
						 }
			 });
	      }
	    //}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	} 
 function UnVoidProject(workspaceId,statusIndi){
		
	 	//debugger;
	 	wsid = workspaceId;
	 	statusIndi = statusIndi;
		
	 	/* var okCancel = confirm("Do you really want to Archive selected Project ?");
	    if(okCancel == true){ */
	    		
	        var remark = prompt("Please specify reason for change.");
	    		remark = remark.trim();
			if (remark != null && remark != ""){  
				 $.ajax({
						 
						 url : "UnVoidProject_ex.do?workspaceId="+wsid+"&statusIndi="+statusIndi+"&remark="+remark,
						 async: false,
						 beforeSend: function()
							{ 
							
								$('#statusDetails').html('');								
							},
						 success:function(data)
						 {
							 alert(data);
							 var out="WorkspaceSummay.do";
							 location.href=out;
						 }
			 });
	      }
	    //}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	} 
 function LockProject(workspaceId,statusIndi){
		
	 	//debugger;
	 	wsid = workspaceId;
	 	statusIndi = statusIndi;
		
	 	/* var okCancel = confirm("Do you really want to Archive selected Project ?");
	    if(okCancel == true){ */
	    		
	        var remark = prompt("Please specify reason for change.");
	    		remark = remark.trim();
			if (remark != null && remark != ""){  
				 $.ajax({
						 
						 url : "LockProject_ex.do?workspaceId="+wsid+"&statusIndi="+statusIndi+"&remark="+remark,
						 async: false,
						 beforeSend: function()
							{ 
							
								$('#statusDetails').html('');								
							},
						 success:function(data)
						 {
							 alert(data);
							 var out="WorkspaceSummay.do";
							 location.href=out;
						 }
			 });
	      }
	    //}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	}
 function UnLockProject(workspaceId,statusIndi){
		
	 	//debugger;
	 	wsid = workspaceId;
	 	statusIndi = statusIndi;
		
	 	/* var okCancel = confirm("Do you really want to Archive selected Project ?");
	    if(okCancel == true){ */
	    		
	        var remark = prompt("Please specify reason for change.");
	    		remark = remark.trim();
			if (remark != null && remark != ""){  
				 $.ajax({
						 
						 url : "UnLockProject_ex.do?workspaceId="+wsid+"&statusIndi="+statusIndi+"&remark="+remark,
						 async: false,
						 beforeSend: function()
							{ 
							
								$('#statusDetails').html('');								
							},
						 success:function(data)
						 {
							 alert(data);
							 var out="WorkspaceSummay.do";
							 location.href=out;
						 }
			 });
	      }
	    //}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	}
 function callForProjDocStatus(wsId)
 {
	 $('#toggleDiv_'+wsId).show();
	 var lbl_nodeName = '<s:property value="lbl_nodeName"/>';
	 //debugger;
 	 $.ajax({
 		// url : "ProjDocStatus_ex.do?workspaceId=<s:property value='#session.ws_id'/>",
 		   url : "ProjDocStatus_ex.do?workspaceId="+wsId+"&mode=1",
 		 beforeSend: function()
 			{ 
 				$('#statusDetails_'+wsId).html('');								
 			},
 		 success:function(data)
 		 {
 			 debugger;
				var str="<br/>";
				var leafCount="";
				var historyCount="";
				var d = data.split('#');
				//var projectname=wname;
				var tblstr="";
				tblstr="<table border=1 class=projectStatus cellspacing=2 width=100% style=\"margin-top:5px; margin-bottom: 5px;\">";
					
				for(var i=0;i<d.length;i++)
				{
					tblstr+="<tr>";		
					if (d[i].match("TotalNodes")) 
					{
						leafCount = d[i].split('=')[1]; 
					}
					if(d[i].match("PerLeafNodes"))
					{
						$('#labelDiv').html($('#labelDiv').html()+""+d[i].split('=')[2]+"%");
						historyCount = d[i].split(',')[0].split('=')[1];
					}
					else if(d[i].match("Per"))
					{
						var splstr = d[i].split(',');
						var finalStr = splstr[0].replace("Per","")+"%";
						var perval = splstr[0].split('=')[1];	
						finalStr+= " [ "+ splstr[1].split('=')[1] + "/"+leafCount + " ] <br/>" ;
						console.log(finalStr.split('=')[0]);
						console.log(finalStr.split('=')[1]);
						tblstr+="<td style=\"width: 60%;\">"+finalStr.split('=')[0]+"</td><td>"+finalStr.split('=')[1]+"</td>";
						str+=finalStr;
					}
					tblstr+="</tr>";
				}
				
				str+="&nbsp;&nbsp;&nbsp;Total "+lbl_nodeName+" with documents = "+ historyCount+"<br/>";
				str+="&nbsp;&nbsp;&nbsp;Total "+lbl_nodeName+" without documents = "+ (leafCount-historyCount)+"<br/>";
				tblstr+="<tr><td>Total "+lbl_nodeName+" with documents</td><td>"+historyCount+"</td></tr>";
				tblstr+="<tr><td>Total "+lbl_nodeName+" without documents</td><td>"+(leafCount-historyCount)+"</td></tr>";
				tblstr+="</table>";
 				$('#statusDetails_'+wsId).html(tblstr);				

 		 }
 		 });
 }
 function refreshParent(wsId) 
	{
	 //debugger;
	 document.getElementById("toggleDiv_"+wsId).style.display="none";
	}
</script>

</head>
<body>

<!-- <div id="backgroundPopup" onclick="proceed();"></div>
<div align="center"> popup for displaying project status
<div id="confirmDialog"
	style="position: fixed; top: 100px; z-index: 100000; display: none;">
<div id="statusDetails"></div>
<br>
<br>
	<form action="ExportToXls.do" method="post" id="myform" style="margin-left: 5px;margin-top: -24px">
	<input
			type="hidden" name="fileName" value="Project_Completion_Report.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableTextArea"
			style="visibility: hidden;"></textarea> <input type="button" value="OK" class="button"
			onclick="donotproceed();">
			<input type="button" value="Proceed" class="button" id="btnProceed" style="margin-right: 50px;"
	onclick="proceed();" />
		</form>
	
	
	</div> 
</div> -->

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<s:if test="workspaceSummaryDetails.size == 0  ">
	<div align="center" style="width: 100%;"><br>
	<table style="border: 1 solid black" width="100%" bgcolor="silver" id="jquery_datatable"
		align="center">
		<tr>
			<td align="center" width="90%"><font size="5" color="#c00000"><b><br>
			No details available.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	
	</div>
</s:if>

<s:else>
	<%int srNo = 1; %>
	<div style='text-align: right; padding-right: 41px; margin-bottom: 2px;'>
	<label
		style="background-color: #b7d6d6 ; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Voided
		<label
		style="background-color: #9fbde0; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Locked</div>
	<!-- <div style='text-align: right; padding-right: 25px;margin-bottom: 2px;'>
	<label
		style="background-color: #6e8db3; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Locked</div> -->
	<div align="center">
	<div style="float: right; cursor: pointer; margin-right: 5px; display:none" id="labelDiv" title="Project Status"></div>
	
</div>
	<table class="datatable remarktbl" width="100%" cellspacing="1" id="jquery_datatable">
		<thead>
			<tr>
				<th style="border: 1px solid black; height:10px;">#</th>
				<th style="border: 1px solid black; height:10px;">Project Type</th>
				<th style="border: 1px solid black; height:10px;">Project</th>
				<th style="border: 1px solid black; height:10px;">Software Application Group</th>
				<s:if test="dossStatPresent == true">
					<th style="border: 1px solid black; height:10px;">Dossier Status</th>
				</s:if>
				<th style="border: 1px solid black; height:10px;">Client</th>
				<!-- <th>Region</th> -->
				<th style="border: 1px solid black; height:10px;">Creation Date</th>
			<s:if test ="#session.countryCode != 'IND'">
				<th style="border: 1px solid black; height:10px;">Eastern Standard Time</th>
			</s:if>

				<!--  <th>Users</th> -->
				<!--                 <th>Details</th>  -->
				<!--  <th>Set Attribute</th> -->
				<th style="border: 1px solid black; height:10px;">Reason for Change</th>
			<s:if test ="eTMFCustomization == 'yes'">
			<s:if test="#session.usertypename == 'WA'">
				<th colspan="4" style="border: 1px solid black; height:10px;" class="datatableedit">Actions</th>
			</s:if>
			</s:if>
				<!-- <th>Delete</th> -->
				<!--    <th>Attach Attribute</th> -->
			<%-- <s:if test="#session.usertypename == 'WA'">
				<th style="border: 1px solid black; height:10px;" class="datatableedit">Void</th>
				<th style="border: 1px solid black; height:10px;" class="datatableedit">Lock</th>
			</s:if> --%>
			<s:if test ="eTMFCustomization == 'yes'">
			<s:if test="#session.usertypename == 'Archivist'">
				<th style="border: 1px solid black; height:10px;">Archive</th>
			</s:if>
				<th style="border: 1px solid black; height:10px;"></th>
			</s:if>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="workspaceSummaryDetails"
				id="workspaceSummaryDetails" status="status">
				<s:if test="statusIndi == 'D'">
					<tr class="matchFound">
				</s:if>
				<s:elseif test="statusIndi=='V'">
					<tr class="voidFound">
				</s:elseif>
				<s:elseif test="statusIndi =='L'">
					<tr class="lockFound">
				</s:elseif>
				<s:else> 
					<tr	class="<s:if test="#status.even">two</s:if><s:else>one</s:else>">
				</s:else>
					<td style="border: 1px solid black;"><%=srNo++ %></td>
					<td style="border: 1px solid black;" title='<s:property value="projectName"/>'><s:if
						test="projectName.length() > 10">
						<s:property value="projectName.substring(0,10)" />...
			    </s:if> <s:else>
						<s:property value="projectName" />

						<script>
							//alert("<s:property value='projectName'/>");
						</script>
					</s:else></td>
					<td style="border: 1px solid black;" title='<s:property value="workSpaceDesc"/>'>
					<s:if test="projectCodeName != null">
					[<s:property value="projectCodeName"/>]
					</s:if>
					<s:if test ="(statusIndi == 'L' || statusIndi == 'V') && #session.usertypename != 'WA'">
						  <%-- <script>
				  			allWorkspace.push({
				  			"value":"<s:property value='workSpaceDesc'/>",
				  			"data":"<s:property value='workSpaceId'/>"
				  		});
				  		</script>  --%>
				  		    <s:if test="workSpaceDesc.length() > 40">
								<s:property value="workSpaceDesc.substring(0,40)" />...
				     		</s:if> 
				     		<s:else>
								<s:property value="workSpaceDesc" />
							</s:else>
					</s:if>
					<s:else>
						<!-- <a href="WorkspaceAdminOpen.do?ws_id=<s:property value="workSpaceId"/>" style="this.style.zoom='200%'"><s:property value="workSpaceDesc"/></a> 
				  				This Link was used earlier for Editing Project Structure
				  		--> <script>
					  		allWorkspace.push({
					  			"value":"<s:property value='workSpaceDesc'/>",
					  			"data":"<s:property value='workSpaceId'/>"
					  		});
				  		</script> 
				  		<a href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>" style=""> 
				  		   <s:if test="workSpaceDesc.length() > 40">
							  <s:property value="workSpaceDesc.substring(0,40)" />...
				     	   </s:if>
				     	   <s:else>
							  <s:property value="workSpaceDesc" />
						   </s:else> 
						   </a>
					</s:else>
					</td>
					<td style="border: 1px solid black;"><s:property value="applicationName" default="-"/></td>
					<s:if test="dossStatPresent == true">
						<td style="border: 1px solid black;"><s:property value="dossierStatus" /></td>
					</s:if>
					<td style="border: 1px solid black;" title='<s:property value="clientName"/>'><s:property
						value="clientName" /></td>
					<%-- <td title='<s:property value="locationName"/>'><s:if
						test="locationName.length() > 10">
						<s:property value="locationName.substring(0,10)" />...
			     </s:if> <s:else>
						<s:property value="locationName" />
					</s:else></td> --%>
					<!-- <td title='<s:date name="createdOn" format="dd-MMM-yyyy HH"/>'><s:date
						name="createdOn" format="dd-MMM-yyyy HH:mm" /></td> -->
					<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
				<s:if test ="#session.countryCode != 'IND'">
					<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
				</s:if>
					<!-- <td>
			    	<div align="center" >
			    	<a  href="AddUserToProjectForm.do?workspaceid=<s:property value="workSpaceId"/>" title="Add">
					<img border="0px" alt="Add" src="images/Common/add.png" height="18px" width="18px">
					</a>
					</div>
			    </td>
			  	 -->
					<!-- 	<td>
			  	<a href="wsEctdAttr.do?ws_id=<s:property value="workSpaceId"/>">
			  	 Set</a>
			  
			  	 </td>
			  -->
			  <td style="border: 1px solid black;" title='<s:property value="remark"/>'><s:if
						test="remark.length() > 35">
						<s:property value="remark.substring(0,35)" />...
			    </s:if> <s:else>
						<s:property value="remark" />
					</s:else></td>
			  <s:if test ="eTMFCustomization == 'yes'">
			<s:if test="#session.usertypename == 'WA'">
					<td style="border: 1px solid black;">
					<s:if test ="statusIndi == 'L' || statusIndi == 'V' || isEditProject=='false'">-</s:if>
					<s:else>
						<div><a href="javascript:void(0);"
							onclick="openlinkEdit('<s:property value="workSpaceId"/>');"
							title="Edit"> <img border="0px" alt="Edit" src="images/Common/edit.svg" height="25px" width="25px"> </a></div>
					</s:else>
					</td>
			</s:if>
			</s:if>
			<s:else>
					<td style="border: 1px solid black;">
						<div><a href="javascript:void(0);"
							onclick="openlinkEdit('<s:property value="workSpaceId"/>');"
							title="Edit"> <img border="0px" alt="Edit"
							src="images/Common/edit.svg" height="25px" width="25px"> </a></div>
					</td>
			</s:else>
					<%--<td>
				 	 <div align="center"><img border="0px" alt="Delete"
						src="images/Common/delete.png" height="18px" width="18px"
						onclick="return authenticate('<s:property value="workSpaceDesc"/>','<s:property value="workSpaceId" />');">
					</div> 
					</td> --%>
					<s:if test="#session.usertypename == 'WA'">
					<td style="border: 1px solid black;">
					<s:if test="statusIndi == 'L'">-</s:if>
					 <s:else>
					<s:if test="statusIndi == 'V'">
				 	  <a title ="UnVoid" href="#" onclick="return UnVoidProject('<s:property value="workSpaceId"/>','<s:property value="statusIndi"/>');">
			  	     	 <img border="0px" alt="InActivate" src="images/Common/active.svg" height="25px" width="25px"></a>	
			  	     </s:if>
			  	     <s:else>
			  	     	<a title ="Void" href="#" onclick="return voidProject('<s:property value="workSpaceId"/>','<s:property value="statusIndi"/>');">
			  	     		<img border="0px" alt="Activate" src="images/Common/inactive.svg" height="25px" width="25px"></a>
			  	     </s:else>	 
			  	    </s:else>
					</td>
					<td style="border: 1px solid black;">
					<s:if test="statusIndi == 'V'">-</s:if>
					<s:else>
					<s:if test="statusIndi == 'L'">
						 <a title ="Lock" href="#" onclick="return UnLockProject('<s:property value="workSpaceId"/>','<s:property value="statusIndi"/>');">
			  	     		<img border="0px" alt="unlock" title="UnLock" src="images/Common/lock.svg" height="25px" width="25px"></a>
					</s:if>
					<s:else>
			  	     	 <a title ="Lock" href="#" onclick="return LockProject('<s:property value="workSpaceId"/>','<s:property value="statusIndi"/>');">
			  	     		<img border="0px" alt="lock" title="Lock" src="images/Common/unlock.svg" height="25px" width="25px"></a>
					</s:else>
					</s:else>
					</td>
					 <td style="border: 1px solid black;">
			        <img border="0px" alt="Project History"src="images/Common/project_status.svg" title="Project Status"
			        onclick="return callForProjDocStatus('<s:property value="workSpaceId"/>');" height="25px" width="25px">
			         <div id="toggleDiv_<s:property value="workSpaceId"/>" class="toggleDiv" style="max-height: 300px; overflow:auto; width: 325px;
 								 position:absolute; right: 104px; background: #fff; border: 3px solid #b1d2fb; z-index: 1;">
 								<img border="0px" style="float:right;" alt="Project History"src="images/Common/Close.svg" height="25px" width="25px"
 								 onclick="refreshParent('<s:property value="workSpaceId"/>');">
						<div id="statusDetails_<s:property value="workSpaceId"/>" style="padding-left: 4px; color: #fff;
    								font: calibri; font-weight: bold;"></div>
					</div>
			   </td>
					</s:if>
					<!--<td>
			   		<div align="center" >
				  	<a href="SetAttributesOnNode.do?ws_id=<s:property value="workSpaceId"/>" title="Attach">
					<img border="0px" alt="Attach" src="images/Common/attach.png" height="18px" width="18px">
					</a>
					</div>
			  	</td>-->
			  	<s:if test ="eTMFCustomization == 'yes'">
			  	<s:if test="#session.usertypename == 'Archivist'">
			  	<td style="border: 1px solid black;">
			  		<s:if test="statusIndi == 'A' && LockSeq =='L'">
			  	     	<a title ="UnArchive" href='#'
			  	 		  onclick="return unArchive('<s:property value="workSpaceId"/>','<s:property value="statusIndi"/>');">
			   			   <img border="0px" alt="InActivate" src="images/Common/active.svg" height="25px" width="25px"> </a>
			  		</s:if>
			  		<s:elseif test="LockSeq =='L'">
			  		   <a title ="Archive" href="#" onclick="checkLockSeq('<s:property value="workSpaceId"/>','<s:property value ="workSpaceDesc"/>','<s:property value ="statusIndi"/>');">
			  	           <img border="0px" alt="Activate" src="images/Common/inactive.svg" height="25px" width="25px"> </a>
			  		</s:elseif>
			  		<s:else>-</s:else>
			  	</td>
			   </s:if>
			   <td style="border: 1px solid black;">
			   <a title ="Project History" href="#" onclick="return projectDetailHistory('<s:property value="workSpaceId"/>');">
			  	      <img border="0px" alt="Project History"src="images/Common/details.svg" height="25px" width="25px"></a>
			   </td>
			  	  
			   </s:if>
				</tr>
			</s:iterator>
		</tbody>

	</table>
	<div id="projStatusDiv"
		style="display: none; position: absolute; max-height: 160px; width: 300px; margin: 5px; margin-top: 0px; visibility: hidden; background: #5B89AA;">
	<div style="float: right;" align="right"><img alt="Close"
		title="Close" src="images/Common/Close.svg" id="close" /></div>
	<div align="center" style="color: white; font-weight: bold;">Project
	Status</div>
	<div id="projDocDiv"
		style="max-height: 110px; width: 250px; text-align: left; padding-left: 5px; overflow: auto; border: 1px solid #0C3F62; margin: 5px; margin-top: 0px; background: white;"></div>
	</div>
</s:else>


</body>
</html>