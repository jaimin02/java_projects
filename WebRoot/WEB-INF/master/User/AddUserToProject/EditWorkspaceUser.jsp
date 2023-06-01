<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">
	$(document).ready(function() { 
		
		$("#fromDt").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'd-M-yy'});
		$("#toDt").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'd-M-yy'});
		});
</script>
<script type="text/javascript">
	function validate()
	{
		if(document.getElementById('remark').value == "")
 	    {
 		    alert("Please specify Reason for Change...");
 		    document.editWorkspaceUserForm.remark.style.backgroundColor="#FFE6F7"; 
	     	document.editWorkspaceUserForm.remark.focus();
 		    return false;
 	    }    
		var stages = document.editWorkspaceUserForm.stageIds;
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
			return false;
		}

 		var fdate1 = document.getElementById('fromDt').value;
   		var tdate1 = document.getElementById('toDt').value;
   		var fdate = Date.parse(fdate1);
		var tdate = Date.parse(tdate1);
 		var alertMsg =  'To Date must be greater than or equal to From Date.'; 
     					
 		if(fdate != '' && tdate != '' && fdate > tdate)
 	    {
 		    alert(alertMsg);
 		    document.getElementById('toDt').value = "";
 		    document.editWorkspaceUserForm.toDt.style.backgroundColor="#FFE6F7"; 
	     	document.editWorkspaceUserForm.toDt.focus();
 		    return false;
 	    }         		
		return true;
	}
	function checkRights(ckType){
		//debugger;
		var checkboxes = [];
		$("input:checkbox[name=stageIds]:checked").each(function(){
			checkboxes.push($(this).val());
		});
		console.log(checkboxes);
		
		var userType = "${mst.userTypeName}";
	    var ckName = document.getElementsByName(ckType.name);
	    var checked = document.getElementById(ckType.id);
	    
		if(userType!="WA" && checkboxes.includes("10")){
			if (checked.checked) {
			      for(var i=0; i < ckName.length; i++){
			          if(ckName[i].value != "10")
			    	  	ckName[i].checked = false;				            				         
			      } 
			    }
		}
		if(userType!="WA" && checkboxes.includes("20")){
			if (checked.checked) {
			      for(var i=0; i < ckName.length; i++){
			          if(ckName[i].value != "20")
			    	  	ckName[i].checked = false;				            				         
			      } 
			    }
		}
		if(userType!="WA" && (ckType.value==10 || ckType.value==20)){
		
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

function checkRightsForCSV(){
		alert("User already performed activity on this project, you can't update access rights..!");
		return true;
	}
</script>
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>

</head>

<body>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Update User on Project</b></div>
<br>
<div id="imp" style="float: right; margin-right: 10px; margin-top: -4px;font-size: 14px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
<s:form action="UpdateWorkspaceUser" method="post"
	name="editWorkspaceUserForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td align="left">${mst.workspacedesc}</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">User Name</td>
			<td align="left">${mst.username}</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">User Group</td>
			<td align="left">${mst.userGroupName}</td>
		</tr>
		<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">User Role</td>
				<td align="left"><s:select list="roleDtl" name="roleCode" 
				listKey="roleCode" listValue="roleName" value="%{mst.roleCode}">
					</s:select>
				</td>
			</tr> 
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Reason for Change
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left">
				<input type="text" id="remark" name="remark" style=""/></td>
		</tr>
		<tr>
		<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Module Specific Rights</td>
		<td align="left"><s:if  test="%{RightsType!='modulewise'}">NO</s:if><s:else>YES</s:else></td>
		</tr>
	<s:if test="%{RightsType!='modulewise'}">
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Stages
				<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:iterator value="getStageDetail">
				<s:set name="outerStageId" value="stageId"></s:set>
				<s:set name="checkedValue" value=""></s:set>
				<s:iterator value="userStages">
					<s:if test="#outerStageId == StageId">
						<s:set name="checkedValue" value='checked="checked"'></s:set>
					</s:if>

				</s:iterator>

				<%-- <div><input type="checkbox" name="stageIds"
					id="stages_<s:property value = "stageId"/>"
					value="<s:property value="stageId"/>" <s:property value="#checkedValue"/> onclick="return checkRights(this)"> <LABEL
					for="stages_<s:property value = "stageId"/>"><s:property
					value="StageDesc" /></LABEL></div> --%>
				
			<%-- <div><input type="checkbox" name="stageIds"
					id="stages_<s:property value = "stageId"/>"
					value="<s:property value="stageId"/>" <s:property value="#checkedValue"/>> <LABEL
					for="stages_<s:property value = "stageId"/>"><s:property
					value="StageDesc" /></LABEL></div> --%>
					<div><input type="checkbox" name="stageIds"
					id="stages_<s:property value = "stageId"/>"
					value="<s:property value="stageId"/>"
					
					<s:iterator value="userStages">
					<s:if test="#outerStageId == StageId && userFlag=='Y' ">
						<s:set name="checkedValue" value='checked="checked"'></s:set>disabled="disabled" checked="true"
					</s:if>
					<s:else>
						<s:property value="#checkedValue"/>
					</s:else>
				</s:iterator>
					> <LABEL
					for="stages_<s:property value = "stageId"/>"><s:property
					value="StageDesc" /></LABEL></div>
			</s:iterator> 
			</td>
		</tr>
	</s:if>
		<tr>
			<!-- <td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">From Date</td>-->
			<td align="left" style="display:none"> 
			<input type="text" name="fromDt" style="" readonly="readonly" id="fromDt" 
				value="<s:date name="mst.fromDt" format="d-MMM-yyyy"/>"></td>
		</tr>

		<tr>
			<!-- <td class="title" align="right" width="45%"
				style="padding: 2px; padding-right: 8px;">To Date</td> -->
			<td align="left" style="display:none">
			<input type="text" name="toDt" readonly="readonly" id="toDt" style=""
				value="<s:date name="mst.toDt" format="d-MMM-yyyy"/>"></td>
		</tr>
		<tr><td></td></tr>
		<tr>
			<td align="left"><input type="hidden" name="userId"
				value="${mst.userCode}"> <input type="hidden"
				name="userGroupCode" value="${mst.userGroupCode}"> <s:hidden
				name="workSpaceId"></s:hidden>
				<s:hidden name="RightsType"/></td>
			<td align="left">
				<s:if test="RightsType=='projectwise' && userStages.size()==getStageDetail.size()">
				<input type="button" value="Update" class="button" onclick="return checkRightsForCSV();"/>
				</s:if>
				<s:else> 
				<s:submit value="Update" cssClass="button"
						onclick="return validate();">
				</s:submit>
				 </s:else>
			</td>
		</tr>

	</table>

</s:form></div>
</div>
</div>
</div>
</body>
</html>
