<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head theme="ajax" />
<style type="text/css">
.disabled {
    pointer-events: none;
    cursor: not-allowed;
}
</style>
<SCRIPT type="text/javascript">
	function importManualproject()
	{	
		alert('test');
		var workSpaceId = $('#ws_id').val();
		alert(workSpaceId);	
		$.ajax(
		{			
			url: 'ImportManualProject.do?vWorkspaceId=' + workSpaceId,
			beforeSend: function()
			{
				$('#ShowSubInfoDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
			},
			success: function(data) 
	  		{
				alert("Process Comleted");
					$('#ShowSubInfoDiv').html("Completed");
			}	  				    		
		});
	
		return true;
	}	
	var showHideDivIdArr=new Array('CreateFrmDiv','fileUploadDiv','mDiv');
	
	function createProject(divId){
		if(validate()){
			showdiv(showHideDivIdArr,divId);
			document.forms['CrtManualPubProjectForm'].submitBtn.click();
		}else{
			return false;
		}return false;
	}
	function uploadSeq(divId){
		var ary = document.forms['SeqUploadForm'].elements;
		for(i=0;i<ary.length;i++){//alert('ary:'+ary[i].name);
		}
		var fileTags =document.getElementsByTagName('input');
		for(i=0;i<fileTags.length;i++){//alert(fileTags[i].name);
		}
		showdiv(showHideDivIdArr,divId);
		document.forms['SeqUploadForm'].submitBtn.onclick();
	}
	function showdiv(divIdArr,divIdToShow){

		//debugger;
		for(i = 0; i < divIdArr.length ; i++){
			if(document.getElementById(divIdArr[i]) != null){
				if(divIdToShow == divIdArr[i]){
					document.getElementById(divIdArr[i]).style.dplay='';
				}else{document.getElementById(divIdArr[i]).style.display='none';}
			}
		}
	}
$(document).ready(function(){

	//debugger;

		$("input[name=advanceManualProject]").change(function() {
			//debugger;

			if($(this).attr("checked") && $(this).attr("value")=="Y")
			{
			
				$("#dossierName").attr("disabled","");
			}
			else
			{
				$("#dossierName").attr("disabled","disabled");
			}
		});
			
   	
});
	
	
	function trim(str)
	{
	   	str = str.replace( /^\s+/g, "" );// strip leading
		return str.replace( /\s+$/g, "" );// strip trailing
	}
	function check(strString)
	{
	    strString=trim(strString);
	   	if(strString.indexOf("\\")!=-1){alert("Invalid project name." + " " + "\\ is not allowed");
	   	return false;
	   	}
		var strInvalidChars = "/\^$#:~%@&*`!;'\" ><?";
		var strChar;
		var blnResult = true;
		if(strString.length > 60){
			alert("Project name must not be greater then 60 char.");
			return false;
		}	
	   	if(strString.length== 0) {alert ("Please specify project name.."); return false;}
	    for (i = 0; i < strString.length && blnResult == true; i++){
	 		strChar = strString.charAt(i);
			if (strInvalidChars.indexOf(strChar)!= -1){
				blnResult = false;
				if(strChar == ' '){
      				alert("Invalid project name." + " 'Space' is not allowed" );
      			}else{
       				alert("Invalid project name." + " " + strChar + "  is not allowed" );
       			}
	 		}
	    }
			return blnResult;
	 }
	function validate()
	{
		if(check(document.CrtManualPubProjectForm.workSpaceDesc.value)==false) {
	     	document.CrtManualPubProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.workSpaceDesc.focus();
			return false;
		}
		/*else if(document.CrtManualPubProjectForm.workSpaceDesc.value=="")
		{
			alert("Please specify project name..");
			document.CrtManualPubProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.workSpaceDesc.focus();
     		return false;
     	}*/
     	
     	else if(document.CrtManualPubProjectForm.templateId.value=="-1")
		{
			alert("Please select Template..");
			document.CrtManualPubProjectForm.templateId.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.templateId.focus();
     		return false;
     	}
		else if(document.CrtManualPubProjectForm.locationCode.value==-1)
		{
			alert("Please select region name..");
			document.CrtManualPubProjectForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.locationCode.focus();
     		return false;
     	}
     	else if(document.CrtManualPubProjectForm.deptCode.value==-1)
		{
			alert("Please select department name..");
			document.CrtManualPubProjectForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.deptCode.focus();
     		return false;
     	}
     	else if(document.CrtManualPubProjectForm.clientCode.value==-1)
		{
			alert("Please select client name..");
			document.CrtManualPubProjectForm.clientCode.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.clientCode.focus();
     		return false;
     	}
     	else if(document.CrtManualPubProjectForm.projectCode.value==-1)
		{
			alert("Please select project type name..");
			document.CrtManualPubProjectForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.projectCode.focus();
     		return false;
     	}
     	else if(document.CrtManualPubProjectForm.userCode.value==-1)
		{
			alert("Please select default Admin ..");
			document.CrtManualPubProjectForm.userCode.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.userCode.focus();
     		return false;
     	}
     	
     	else if(document.CrtManualPubProjectForm.remark.value.length>250)
		{
			alert("Remarks cannot be of more then 250 charactars..");
			document.CrtManualPubProjectForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.CrtManualPubProjectForm.remark.focus();
     		return false;
     	}
     	return true;
	}
	var latestSeq=null;
	function createFileTag(id){	
		var uploadSeqNo;
		if(latestSeq==null){
			uploadSeqNo=document.getElementById('startSequence').value;
			latestSeq=uploadSeqNo;
		}
		else{
			uploadSeqNo=latestSeq;
		}
		
		var newSeq=incDecSeq(uploadSeqNo,1);
		var filename="upload_"+newSeq;
		
		//document.getElementById(id).innerHTML=document.getElementById(id).innerHTML+'<div><br/><span class="title">Upload Sequence '+newSeq+': </span><input type="file" name="'+filename+'" id="'+filename+'"></div>';
		
		var newDiv=document.createElement('div');
		newDiv.id="div_"+newSeq;
		var newBr=document.createElement('br');
		newDiv.appendChild(newBr);
		var newSpan=document.createElement('span');
		newSpan.innerHTML="Upload Sequence "+newSeq+": ";
		newDiv.appendChild(newSpan);
		var newSeqName=document.createElement('input');
		newSeqName.type='file';
		newSeqName.name="upload";//Don't change the name as it is used in the action java code.
		newSeqName.size="50";
		newSeqName.id=filename;
		newDiv.appendChild(newSeqName);
		
		document.getElementById(id).appendChild(newDiv);
		
		latestSeq=newSeq;
		updateAddRemoveLinks();
		document.getElementById(filename).focus();
	}
	function removeFileTag(id){
		document.getElementById(id).removeChild(document.getElementById('div_'+latestSeq));
		latestSeq=incDecSeq(latestSeq,-1);
		updateAddRemoveLinks();
	}
	function incDecSeq(seq,changeval){
		seq='000'+ (parseInt(seq,10)+changeval);
		if(seq.length > 4){
			seq = seq.substring(seq.length-4);
		}
		return seq;
	}
	function updateAddRemoveLinks(){
		var linkEle = document.getElementById('addSeqLink');
		var addSeq=incDecSeq(latestSeq,1);
		linkEle.innerHTML = "Add "+addSeq;
		
		var removeLinkEle = document.getElementById('removeSeqLink');
		removeLinkEle.innerHTML="Remove "+latestSeq;
		if(latestSeq == document.getElementById('startSequence').value){
			removeLinkEle.style.display = 'none';
		}else{
			removeLinkEle.style.display = '';
		}
	}
	function validateFiles(){
		var allElements = document.getElementsByTagName('input');
		for(var i = 0; i < allElements.length;i++){
			if(allElements[i].id.match(/^(upload_\d{4})$/)){
				var err = false;
				if(allElements[i].value == ''){
					err = true;
				}else{
					var fileName = allElements[i].value.substring(allElements[i].value.lastIndexOf('\\')+1)
					var fileExt = fileName.substring(fileName.lastIndexOf('.')+1);
					if(fileExt != 'zip'){
						err = true;
					}
				}
				if(err){
					alert('Please upload a zip file..');
					allElements[i].style.backgroundColor="#FFE6F7"; 
					allElements[i].focus();
					return false;
				}
			}
		}
		return true;
	}
	function exists(){
		var divId = 'CreateFrmDiv';
		showdiv(showHideDivIdArr,divId);
	}
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
  	
  	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.CrtManualPubProjectForm.submitBtnInput.onclick();
  		} 
  		else if(event.srcElement.type=="file"){
  			return false;
  		}
	} 
	document.onkeypress = detectReturnKey;
	function detectBackSpace(e) { 
    	if ((event.keyCode == 8 || event.keyCode == 46) && (event.srcElement.type=="file"))  
  		{//8 is for backspace and 46 is for delete key
  			return false;
  		} 
    }
	document.onkeydown = detectBackSpace;
	/*Added by Rahul for ajax call
 	*check the project name exist or not
 	*/
 	$(document).ready(function()
	{
		$('.target').change(function(){
			var correct=true;
				if(check(document.CrtManualPubProjectForm.workSpaceDesc.value)==false) 
				{
			     	document.CrtManualPubProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
		     		document.CrtManualPubProjectForm.workSpaceDesc.focus();
					correct=false;
				}
		     	else if(document.CrtManualPubProjectForm.workSpaceDesc.value.length>255)
				{
					alert("Project name cannot be of more than 255 charactars..");
					document.CrtManualPubProjectForm.workSpaceDesc.style.backgroundColor="#FFE6F7"; 
		     		document.CrtManualPubProjectForm.workSpaceDesc.focus();
		     		correct=false;
		     	}	
				var workspace=document.getElementById('workSpaceDesc').value;
				document.getElementById('workSpaceDesc').value =trim(workspace);
				workspace=document.getElementById('workSpaceDesc').value; 
				var urlOfAction="WorkSpaceExistsForUpdate.do";
				var dataofAction="workSpaceDesc="+workspace;
				if (correct==true)
				{
					$.ajax({
						type: "GET", 
		   				url: urlOfAction, 
		   				data: dataofAction, 
		   				cache: false,
		   				dataType:'text',
						success: function(response){
							$('#message').html(response);
							if(response.indexOf('Available') == -1)
							{
								document.getElementById('saveProjectbtn').disabled='disabled';
								return false;
							}
							else if(response.indexOf('Available') != -1)
							{
								document.getElementById('saveProjectbtn').disabled='';
								return true;
							}
							//alert($('#htmlRole').html(response));
							//addMessage($('#htmlRole').html(response));
						}
					});
				}
			});
		}); 
</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red" id="message">
<b> <s:fielderror></s:fielderror> <s:actionerror /> </b></div>
<div align="center" style="color: green; size: 50px"><b><s:actionmessage /></b>
</div>
<br />
<div align="center"><img
	src="images/Header_Images/Project/Create_Manual_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">
<div id="CreateFrmDiv" align="left"><s:form
	action="SaveManualProject" name="CrtManualPubProjectForm" method="post"
	enctype="multipart/form-data">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">New Project Name</td>
			<td align="left"><s:if test="maxLimitExceeded=='yes'">
				<input size="35" disabled="disabled">
			</s:if> <s:else>
				<s:textfield name="workSpaceDesc" size="35" value=""
					id="workSpaceDesc" cssClass="target" onkeypress="cls();"></s:textfield>
			</s:else></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Template Name</td>
			<td align="left"><select name="templateId">
				<option value="-1">Select Template</option>
				<s:iterator value="getTemplateDtl">
					<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="templateId"/>"><s:property
							value="templateDesc" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><select name="locationCode">
				<option value="-1">Select Region</option>
				<s:iterator value="getLocationDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="locationCode"/>"><s:property
							value="locationName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Department</td>
			<td align="left"><select name="deptCode">
				<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
				<option value="-1">Select Department</option>
				<s:iterator value="getDeptDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="deptCode"/>"><s:property
							value="deptName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left">
			<select name="clientCode">
					<option value="-1">Select Client</option>
					<s:if test="FosunChanges=='yes'">
						<s:iterator value="getClientDtl">
							<s:if test="statusIndi != 'D' && clientCode == clientCodeGroup">
							<option value="<s:property value="clientCode"/>"><s:property
								value="clientName" /></option>
						</s:if>
						</s:iterator>
					</s:if>
					<s:else>
						<s:iterator value="getClientDtl">
							<s:if test="statusIndi != 'D'">
							<option value="<s:property value="clientCode"/>"><s:property
								value="clientName" /></option>
						</s:if>
						</s:iterator>
					</s:else>
				</select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><select name="projectCode">
				<option value="-1">Select Project Type</option>
				<s:iterator value="getProjectDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="projectCode"/>"><s:property
							value="projectName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Remarks</td>
			<td align="left"><s:textfield name="remark" size="30"></s:textfield>
			<br>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Default Admin</td>
			<td align="left">
				<s:if test="FosunChanges == 'yes'">
					<select name="userCode" class="disabled">
						<option value="-1">Select Default Admin</option>
						<s:iterator value="getUserDtl">
							<s:if test="statusIndi != 'D'">
								<option value="<s:property value="userCode"/>"
									<s:if test="#session.username == userName">selected="selected"</s:if>>
								<s:property value="userName" /></option>
							</s:if>
						</s:iterator>
					</select>
				</s:if>
				<s:else>
					<select name="userCode">
						<option value="-1">Select Default Admin</option>
						<s:iterator value="getUserDtl">
							<s:if test="statusIndi != 'D'">
								<option value="<s:property value="userCode"/>"
									<s:if test="#session.username == userName">selected="selected"</s:if>>
								<s:property value="userName" /></option>
							</s:if>
						</s:iterator>
					</select>
				</s:else>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%" style="padding-right: "8px;">Copy
			User Rights</td>
			<td align="left" style="color: black;"><input type="radio"
				id="copyRightsY" name="copyRights" value="Y" checked="checked" /> <label
				for="copyRightsY">Yes</label>&nbsp; <input type="radio"
				id="copyRightsN" name="copyRights" value="N" /> <label
				for="copyRightsN">No</label></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

	
<!-- 
		<tr>
			<td class="title" align="right" width="25%"
				style="padding-right: 8px;">Advance Manual Project</td>
			<td align="left" style="color: black;"><input type="radio"
				id="advanceManualProjectYes" name="advanceManualProject" value="Y"
				checked="checked"
				<s:if test="allowAdvanceManualProject==false">  
				disabled="true" </s:if>></input>
			<label for="">Yes</label>&nbsp; 
			
			<input type="radio" 
				<s:if test="allowAdvanceManualProject==false"> 
				checked="checked"
				</s:if> 
				id="advanceManualProjectNo" name="advanceManualProject" value="N" />
			<label for="">No</label></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Dossier Name</td>
			<td align="left">
			
			<select name="dossierName" id="dossierName"
				<s:if test="allowAdvanceManualProject==false">  
				disabled="true" </s:if>>
				<option value="-1">Select Dossier</option>
				<s:iterator value="dossierDtl">
					<option value="<s:property/>"><s:property /></option>
				</s:iterator>
			</select></td>
		</tr>
 -->
		<tr>
			<td>&nbsp;</td>
			<td align="left"><input id="saveProjectbtn" type="button"
				name="submitBtnInput" class="button" value="Create Project"
				disabled="disabled" onclick="return createProject('fileUploadDiv');">
			</td>
		</tr>


	</table>
	<s:submit name="submitBtn" align="center" onclick="" value="Create"
		cssClass="button" theme="ajax" targets="fileUploadDiv"
		cssStyle="display: none;"></s:submit>
</s:form></div>
<div id="fileUploadDiv" align="center"></div>
<div id="mDiv" align="center"></div>

</div>
</div>
</div>

</body>

</html>

