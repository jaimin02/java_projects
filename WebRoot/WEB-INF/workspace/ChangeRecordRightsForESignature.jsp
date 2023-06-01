<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

function openlinkEdit(buttonName,stageIds,userCode,nodeId){
	//debugger;
	var wsId= '<s:property value="WorkspaceId"/>';
	var count=0;
	var button = buttonName;
	var modulewiserights="modulewiserights";		
	var uCode=document.getElementById("userCode").value;
	var remark=document.getElementById("remark").value;
	
	var url_string = window.location.href;
	var url = new URL(url_string);
	var stageIds = url.searchParams.get("stageIds")
	//alert(stageIds);
	
	if(uCode=="-1"){
		alert("Please Select User.");
		document.getElementById("userCode").style.backgroundColor="#FFE6F7"; 
		$("#userCode").focus();
	}
	else if(remark==""){
		alert("Please specify Reason for Change.");
		document.getElementById("remark").style.backgroundColor="#FFE6F7"; 
		$("#remark").focus();
	}
	else{
	$.ajax( {
		url:'RemoveAndUpdateRecordRightsForESignature_ex.do?WorkspaceId='+wsId+'&stageIdForChange='+stageIds+'&userCodeForChange='+userCode+'&nodeIdForChange='+nodeId+'&remarkForChange='+remark+'&RightsType='+modulewiserights+'&uCodeByForChange='+uCode,
		beforeSend : function() {
			
		},
		success : function(data) {
			window.opener.parent.history.go(0);
			self.close();
			},
		error: function(data) 
		  {
			alert("Something went wrong while Removing rights.");
		  },
				async: false
	});
	
	return true;
	} 
}

</script>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float:left">Change <s:property value="lbl_folderName"/> Rights</b></div>
<br>
<div align="center">
<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
<table id = "moduleUserDetailHistory" style="width:98%; align:center;">
	
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Select User
				<span style="font-size:20px;color:red">*</span></td>
	 		<td>
	 			<s:select list="assignWorkspaceRightsDetails"
					name="userCode" id ="userCode" headerKey="-1" headerValue="Select User" listKey="userCode"
					listValue="userName" theme="simple">
				</s:select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%"style="padding: 2px; padding-right: 8px;"><b>Reason for Change</b>
			<span style="font-size:20px;color:red">*</span></td>
			<td align="left"><s:textfield name="remark" size="30" value="" /></td>
			</tr> 
</table>	
</div>
<br>
<div align ="center">
	<!-- <input type="submit" value="Change Rights" class="button"> -->
	<input type="submit" value="Update Rights" class="button" name="buttonName"
			onclick="return openlinkEdit('Change Rights','<s:property value="stageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />');"  />
</div>
</div>
</div>
</div>
</div>
<br>
</body>
</html>