<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<style>
.fs-dropdown{
z-index: 111111 !important;
}

a {
	color: #000 !important;
	text-decoration: none;
}

</style>

<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
 --%>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>


 <link href="<%=request.getContextPath()%>/css/fSelect.css" rel="stylesheet" type="text/css" media="screen" />
 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>
<s:head />



<script type="text/javascript">
$(document).ready(function() {
	$("#delete").show();
	$("#fromDt").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'd-M-yy'});
	$("#toDt").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'd-M-yy'});

	
	(function($) {
	    $(function() {
	    	$('.assignUsers').fSelect({
	    	    placeholder: 'Select User',
	    	    numDisplayed: 3,
	    	    overflowText: '{n} selected',
	    	    noResultsText: 'No results found',
	    	    searchText: 'Search',
	    	    showSearch: true,
	    	    
	    	});
	    	
	    });
	})(jQuery);
	
	 $("#duration").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	     return false;
	    }
	   });
	
	});
	function selectAll()
	{
			//alert("selectAll");
			//debugger;
			var select=document.workspaceAssignRightsForm.multiCheckUser.options;
			for(i=0;i<select.length;i++) {
			select[i].setAttribute('selected','1');
			}
	}
	
	function deselectAll()
	{
			//alert("de selectAll");
			//debugger;
			var select=document.workspaceAssignRightsForm.multiCheckUser.options;
			for(i=0;i<select.length;i++) {
			//select[i].setAttribute('selected','');
			select[i].removeAttribute('selected');
			}
	}
		
	function validate(buttonName)
	{
		var select=document.workspaceAssignRightsForm.multiCheckUser.options;
		var getStages;
		if(document.querySelector('.checkAll:checked')!=null)
			getStages = document.querySelector('.checkAll:checked').value;
		var count=0;
		var button = buttonName;
		var userType = "<s:property value='#session.usertypename'/>";
		var ProjectTimeLine = '<s:property value = "ProjectTimeLine"/>';
		var getAttrDtl = '<s:property value = "showHours"/>';
		var calculationBase = '<s:property value = "TimelineCalculationBase"/>';
		var timeDuration;
		 if(ProjectTimeLine=="Yes" && getAttrDtl=="true"){
			 if(calculationBase != 'Date')
			 	timeDuration = document.getElementById("duration").value;
		} 	
		 var selectUsers="";
		for(i=0;i<select.length;i++) {
			if(select[i].selected)
			{
				count++;
				selectUsers += select[i].value+",";
			}
			
		}
		if(count>1 && getStages=="10"){
			alert("You can't assign Created rights to more than one user.");
			return false;
		}
		if(count==0)
		{
			alert("Please select user(s).");
			return false;
		}
		else{
			//debugger;
			 selectUsers = selectUsers.substring(0, selectUsers.length - 1);
			 URLAction="CheckUserRights_ex.do?nodeId="+<s:property value="nodeId"/>+"&selectUsers="+selectUsers;
			 $.ajax( {
					url:URLAction,
					beforeSend : function() {
						
					},
					success : function(data) {
						if(data=="true"){
		  					//alert(data);
		  					if (!confirm("You are about to attach stage/s to user/s which would overwrite their rights.\nDo you want to continue?"))
		  						return false;
		  					}
						},
						async:false,
				});
		}
		
		var stages = document.workspaceAssignRightsForm.stageIds;
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
			alert("Please select stage(s).");
			return false;
		}
		
		if(calculationBase == 'Date'){
			if(document.getElementById("fromDt").value == ""){
				alert("Please select From Date");
				return false;
			}
			if(document.getElementById("toDt").value == ""){
				alert("Please select To Date");
				return false;
			}
			
			if(document.getElementById("fromDt").value != "" && document.getElementById("toDt").value != ""){
				 URLAction="CheckExcludedDate_ex.do?fromDate="+document.getElementById("fromDt").value
						 +"&toDate="+document.getElementById("toDt").value;
				 $.ajax( {
						url:URLAction,
						beforeSend : function() {
							
						},
						success : function(data) {
							if(data=="true"){
			  					//alert(data);
			  					if (!confirm("You are about to attach stage/s to user/s which would overwrite their rights.\nDo you want to continue?"))
			  						return false;
			  					}
							},
							async:false,
					});
			}
			
		}
		
		//debugger;
		if(userType=="WA" && ProjectTimeLine=="Yes" && getAttrDtl=="true"){
			/* if(timeDuration <=0 || timeDuration == ""){
				alert("Please enter valid hours.");
				return false;
			} */
			if(timeDuration == ""){
				document.getElementById("duration").value=0;
			}
		}
		
		/* if(buttonName=="Update Rights")
		{
			if (!confirm("You are about to attach stage/s to user/s which would overwrite their rights.\nDo you want to continue?"))
				return false;
		} */
		if(buttonName=="Remove Rights")
		{
			if (!confirm("Do you want to remove rights?"))
				return false;
		}
		return true;
	}
	
	function UserToAssignRights()
	  {
		var URLAction;
		if ($("#rights").is(':checked')) {
				$("#delete").show();
			URLAction="UsertoAssignRights_ex.do?nodeId="+<s:property value="nodeId"/>+"&RightsType=modulewise";
			 } else {
				// debugger;
				 $("#delete").hide();
			URLAction="UsertoAssignRights_ex.do?nodeId="+<s:property value="nodeId"/>;
		     }
		
		$.ajax( {
			url:URLAction,
			beforeSend : function() {
				
			},
			success : function(data) {
				if(!data==""){
  					//alert(data);
	  				$('#Multiuser').html("");
  					
  					$('#Multiuser').html(data); 
  					}		
				},
		});
	}
	function test(){
			
		strAction = "RemoveWSRights_b.do?nodeId="+<s:property value="nodeId"/>;;
   		win=window.open(strAction,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=no,height=330,width=500,resizable=no,titlebar=no");
   		win.moveTo(screen.availWidth/2-(400/2),screen.availHeight/2-(250/2));
   		return true;
	}
	function moduleUserHistory()
	{
			str="showModuleUserDetailHistory_b.do?nodeId="+<s:property value="nodeId"/>;
			win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1300,resizable=no,titlebar=no");
		 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(400/2));	
			return true;
		}
	function refreshParent() 
	{
		/* window.opener.parent.history.go(0); */
		self.close();
	}
	function checkRights(ckType){
		
		//debugger;
		var usergroupCode = document.getElementById("userWiseGroupCode").value;
		var userType;
		var projectCode = '<s:property value="projectCode"/>';
		 //debugger;
		 //if(usergroupCode!=""){
		/* $.ajax({		
			  url: "checkUseType_ex.do?usergroupId="+usergroupCode,
			  success: function(data) 
			  {
				  //alert(data);
				  userType = data;
			  },
			  async: false
		});  */
 if(projectCode!="0003"){
		var checkboxes = [];
		$("input:checkbox[name=stageIds]:checked").each(function(){
			checkboxes.push($(this).val());
		});
		//debugger;
	    var ckName = document.getElementsByName(ckType.name);
	    var checked = document.getElementById(ckType.id);
	    
		if(checkboxes.includes("10")){
			if (checked.checked) {
			      for(var i=0; i < ckName.length; i++){
			          if(ckName[i].value != "10")
			    	  	ckName[i].checked = false;				            				         
			      } 
			    }
		}
		if(checkboxes.includes("20")){
			if (checked.checked) {
			      for(var i=0; i < ckName.length; i++){
			          if(ckName[i].value != "20")
			    	  	ckName[i].checked = false;				            				         
			      } 
			    }
		}
		if(checkboxes.includes("40")){
			if (checked.checked) {
			      for(var i=0; i < ckName.length; i++){
			          if(ckName[i].value != "40")
			    	  	ckName[i].checked = false;				            				         
			      } 
			    }
		}
		if(checkboxes.includes("100")){
			if (checked.checked) {
			      for(var i=0; i < ckName.length; i++){
			          if(ckName[i].value != "100")
			    	  	ckName[i].checked = false;				            				         
			      } 
			    }
		}
	    
		if(ckType.value==10 || ckType.value==20 || ckType.value==40 || ckType.value==100){
		
	    if (checked.checked) {
	      for(var i=0; i < ckName.length; i++){
	          if(!ckName[i].checked){
	              ckName[i].disabled = true;
	          }else{
	              ckName[i].disabled = false;
	          }
	      } 
	    }
	    else {
	      for(var i=0; i < ckName.length; i++){
	        ckName[i].disabled = false;
	      } 
	    }
		}
	}
 else{
	 debugger;
	 var checkboxes = [];
		$("input:checkbox[name=stageIds]:checked").each(function(){
			checkboxes.push($(this).val());
		});
		//debugger;
	    var ckName = document.getElementsByName(ckType.name);
	    var checked = document.getElementById(ckType.id);
		 $(".checkAll").prop('checked', true);
 }
}

	function openlinkEdit(buttonName,stageIds,userCode,nodeId,roleCode){
		//debugger;
		var button = buttonName;
		var modulewiserights="modulewiserights";
		str="ChangeWSRights_b.do?stageIds="+stageIds+"&userCode="+userCode+"&nodeId="+nodeId+"&RightsType="+modulewiserights+"&roleCode="+roleCode;
		win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=300,width=500,resizable=no,titlebar=no");	
		win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(300/2));
		return true;
	}
	
	function deleteRights(buttonName,stageIds,userCode,nodeId){
		//debugger;
		
		var select=document.workspaceAssignRightsForm.multiCheckUser.options;
		var count=0;
		var button = buttonName;
		
		if(buttonName=="Remove Rights")
		{
			var ok=confirm("Do You want to remove rights?")
			if(ok==true){
				var remark = prompt("Please specify reason for change.");
				remark = remark.trim();
				 if (remark != null && remark != ""){ 
					 $('#remark').val(remark);
					 $.ajax( {
							url:'RemoveAssignedUserRights_ex.do?stageIds='+stageIds+'&userCode='+userCode+'&nodeId='+nodeId+'&remark='+remark,
							beforeSend : function() {
								
							},
							success : function(data) {
								window.location.reload();
								},
							error: function(data) 
							  {
								alert("Something went wrong while Removing rights.");
							  },
									async: false
						});
					 return true;
				}
				 else{
					 alert("Please specify reason for change.");
					 return false;
				 }
			}
			else{
				return false;
			}
			
		}
		
		return true;
		
	}		
</script>

</head>

<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div class="titlediv"></div>
<div class="container-fluid">
<div class="col-md-12">
<br>
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 10px;"
	align="center">
<div align="center"><s:form action="WorkspaceRights" method="post"
	name="workspaceAssignRightsForm">
	<table width="95%" align="center">
		<div id="imp" style="float: right; margin-right: 10px;">
				Fields with (<span style="color: red;" >*</span>) are mandatory.
		</div>
		<tr>
			<td>
			<table align="center" width="100%" style="border: 1 solid black">		
				<tr>		
					<td colspan="2">
					<div class="boxborder"><div class="all_title"><b style="float: left;">User Rights Allocation</b></div>
					<!-- <div class="headercls" align="center">Select User For Assign
					Rights</div> -->
					</td>
				</tr>
				
				<tr>				
					<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">	
						<b><font class="title">Document: </font></b> </td>
						<td align="left"><font size="3.5px" face="calibri"><s:property value="nodeName"/></font></td>
				</tr>	
				<tr style="display:none;">
				<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
				 	<label class="title"> Assign Module Specific Rights</label></td>
				<td align="left"><input type="checkbox" id="rights" name="RightsType" value="modulewiserights" onclick="this.checked=!this.checked;"
						onclick="return UserToAssignRights();" checked></td>
				</tr>
				<tr>
					<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
						<label class="title"> User </label>
						<span style="font-size:20px;color:red">*</span></td>
					<td align="left" id="Multiuser"><%-- <s:select list="assignWorkspaceRightsDetails"
						multiple="true" name="multiCheckUser"  cssStyle="width: 100%"
						size="5%" listKey="userCode" listValue="loginName">

					</s:select> --%>
					<%-- <select name="multiCheckUser" id="userWiseGroupCode" size="6" Style="width:75%;" multiple>
					        <s:iterator value="assignWorkspaceRightsDetails">
					            <option value="<s:property value="userCode"/>" title="<s:property value="userType"/>">
					                <s:property value="loginName"/>
					            </option>
					        </s:iterator>
					        </select> --%>
					  <select name="multiCheckUser" id="userWiseGroupCode" size="6" Style="width:75%;" multiple="multiple" class="assignUsers">
					        <s:iterator value="assignWorkspaceRightsDetails">
					            <option value="<s:property value="userCode"/>" title="<s:property value="userType"/>">
					                <s:property value="loginName"/>
					            </option>
					        </s:iterator>
					        </select></td>
				</tr>
					<!-- <td align="center"><input type="button" value="Check All"
						class="button" name="Check All" onclick="selectAll();" />
					&nbsp;&nbsp; <input type="button" name="UnCheck All" class="button"
						onclick="deselectAll();" value="UnCheck All" /></td> -->
			<!-- 	<tr>
					<td>
					<div class="boxborder"><div class="all_title"><b><center>Select User For Assign
					Rights</b><center></div>
					</td>
				</tr> -->

				<tr>
					 <td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
					 	<label class="title"> Stages</label>
					 	<span style="font-size:20px;color:red">*</span></td>
					 <td align ="left"> 
					 	<s:iterator value="getStageDetail">
						<input type="checkbox" name="stageIds" class="checkAll"
							id="stages_<s:property value = "stageId"/>"
							value="<s:property value="stageId"/>" onclick="return checkRights(this)">
						<LABEL class="even" for="stages_<s:property value = "stageId"/>"><s:property
							value="StageDesc" /></LABEL>
					</s:iterator></td> 
					<%-- <td><label class="title"> Stages :</label> <s:iterator
						value="getStageDetail">
						<input type="checkbox" name="stageIds"
							id="stages_<s:property value = "stageId"/>"
							value="<s:property value="stageId"/>">
						<LABEL class="even" for="stages_<s:property value = "stageId"/>"><s:property
							value="StageDesc" /></LABEL>
					</s:iterator></td> --%>
				</tr>
				
				<s:if test="#session.usertypename == 'WA' && ProjectTimeLine=='Yes' && showHours==true ">
						<s:if test="TimelineCalculationBase=='Date' ">
							<tr id="frmdate"> 
								<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
									<label class="title"> From Date</label>
								</td>
								<td align="left"><input type="text" name="fromDt" style="position: relative; z-index: 100000;" id="fromDt"></td>
							</tr> 
							<tr>
							<tr id="todate">
								<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
									<label class="title"> To Date</label>
								</td>
								<td align="left"><input type="text" name="toDt" style="position: relative; z-index: 100000;"  id="toDt"></td>							
							</tr>
						</s:if>
						<s:else>
						<tr>
							<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
							<label class="title"> Duration(Man Hours) </label></td>
							<td align="left"><input type="text" value="" name="duration" id="duration"/></td>
						</tr>	
						</s:else>
					
				
				</s:if>
				<tr>
					<td colspan="2" align="center">
					<%-- <s:submit value="Remove Rights" cssClass="button" name="buttonName" id="delete"
						onclick="return validate('Remove Rights');" /> --%> 
					<s:if test="#session.usertypename == 'WA'">
						 <s:submit value="Assign Rights" cssClass="button" name="buttonName"
						onclick="return validate('Update Rights');" />
					</s:if>
						<input type="button" value="Audit Trail"
						class="button" name="Audit Trail" onclick="return moduleUserHistory();" />
						<input type="button" value="Close"
						class="button" name="Close" onclick="return refreshParent();" />
					</td>
				</tr>

			</table>
			</td>
		</tr>
	</table>
	<s:hidden name="nodeName"></s:hidden>
	<s:hidden name="nodeId"></s:hidden>

</s:form> <br>
<div
	style="max-height: 378px; overflow: auto; border: 1px solid; width: 95%"
	align="center">
<table align="center" width="100%" class="datatable" border="1px solid">
	<thead>
		<tr>
			<th>#</th>
			<th>Username</th>
			<th>User Role</th>
			<th>Rights</th>
		<s:if test="#session.usertypename == 'WA'">
			<th>Remove Rights</th>
		</s:if>
			<th>Assign Hours</th>
			<th>Update User</th>
			

		</tr>
	</thead>
	<tbody>
		<s:set name="prevUserName" value=""></s:set>
		<s:set name="counter" value="0" />
		<s:iterator value="getUserRightsDetail" id="getUserRightsDetail"
			status="status">
			<s:if test="#prevUserName != userName">
				<s:set name="outerUserName" value="userName"></s:set>
				<s:set name="userCount" value="0"></s:set>
				<s:subset source="getUserRightsDetail" start="#status.index">
					<s:iterator status="innerStatus">
						<s:if test="#outerUserName == userName">
							<s:set name="userCount" value="#innerStatus.count"></s:set>
						</s:if>
					</s:iterator>
				</s:subset>
				<s:set name="counter" value="#counter+1"></s:set>
			</s:if>

			<tr class="even none">

				<s:if test="#prevUserName != userName">
					<td rowspan="<s:property value="#userCount"/>"><s:property
						value="#counter" /></td>
					<td rowspan="<s:property value="#userCount"/>"><s:property
						value="userName" /></td>
				</s:if>
				<td><s:property value="roleName"/></td>
				<td
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<s:property value="stageDesc" /></td>
			<s:if test="#session.usertypename == 'WA'">
				<td>
					<s:if test=" userFlag == 'N' && userTypeCode!='0002'">
					<a href="#" name="buttonName" id="delete"
							onclick="return deleteRights('Remove Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />');">
							<img border="0px" alt="Delete Record" src="images/Common/delete.png" height="20px" width="20px">
					</a>
					</s:if>
					<s:else>-</s:else>
				</td>
			</s:if>
			<td><s:if test = "hours==0">-</s:if>
			<s:else><s:property value="hours" default="-"/></s:else>
			</td>
			<td>	
			<s:if test="projectCode!='0003' ">
				<s:if test="#session.username == userName && userTypeCode=='0002'">
				-
				</s:if>
				<s:elseif test="userTypeCode=='0003' &&  userFlag == 'N'">
					<s:if test="#session.username == userName && #session.usertypename != 'WA'">
						<s:if test="stageId != 40">
							<input type="image" title="Update User" alt="Submit" src="images/Common/update_user.png" height="20px" width="20px"
							onclick="return openlinkEdit('Change Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />','<s:property value="roleName"/>');"  />
						</s:if>
						<s:else>-</s:else>
					</s:if>
					<s:elseif test="#session.usertypename == 'WA' && isVoidFlag==true">
						<s:if test="stageId != 40">
							<input type="image" title="Update User" alt="Submit" src="images/Common/update_user.png" height="20px" width="20px"
							onclick="return openlinkEdit('Change Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />','<s:property value="roleName"/>');"  />
						</s:if>
						<s:else>-</s:else>
					</s:elseif>
					<s:else>-</s:else>
				</s:elseif>
				
				<s:else>-</s:else>
				</s:if>
				<s:else>-</s:else>
				</td>
			</tr>

			<s:set name="prevUserName" value="userName"></s:set>
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
