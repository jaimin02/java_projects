<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<style type="text/css">
.smalltd {
	height: 12px;
	font-size: 12px;
	color: navy;
}

body {
	scrollbar-arrow-color: blue;
	scrollbar-face-color: #e7e7e7;
	scrollbar-3dlight-color: #a0a0a0;
	scrollbar-darkshadow-color: #888888;
}
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
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
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$(".attrValueIdDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		});
	
</script>
<script language="javascript">

	var OperationId = null;
	function callAddMore(attrId)
	{
   		strAction = "<%=request.getContextPath()%>/addMoreAttribute.do?attrId="+attrId;
   		win3=window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=130,width=500,resizable=no,titlebar=no");	
   		win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(130/2));
   		return true;
	}
	
	
	
	function setDescription()
	{
		var keyword = document.workspaceNodeAttrForm.keyWord.value;
		document.workspaceNodeAttrForm.description.value = keyword; 
		var newstring = keyword.split("/");
		document.workspaceNodeAttrForm.versionPrefix.value = newstring[0];
		return true;		
	}

	function changePdfIcon()
	{
		//debugger;
		var CurrentNodeId=document.workspaceNodeAttrForm.nodeId.value;
  		var htmlDocObj = parent.document.getElementsByTagName("iframe");
		var selectedHtmlDoc = htmlDocObj[0].contentDocument || htmlDocObj[0].contentWindow.document;
		var htmlLabelElementObj = selectedHtmlDoc.getElementById(CurrentNodeId);
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
		var deltefilevalue = $('input[name="deleteFile"]:checked').length
		var sp = uploadFile.split('\\');
		var fname1=sp[sp.length-1];

		var fileExtension ="";
		var index=fname1.lastIndexOf('.');
		
		if(index==-1){
			htmlLabelElementObj.innerText = htmlLabelElementObj.innerText;
			if(deltefilevalue != 0){
				htmlLabelElementObj.parentNode.parentNode.className =' dynatree-node dynatree-lastsib dynatree-selected dynatree-exp-cl dynatree-ico-c';
			}
		}
		if(index>0)
		{
			//debugger;
			fileExtension=fname1.substring(index+1);
			var x = htmlLabelElementObj.parentNode.parentNode.classList[1];
			if(x == 'dynatree-exp-c'){
				if(fileExtension == "docx"){
					htmlLabelElementObj.parentNode.parentNode.className+=' doc';
				}
				else{
					htmlLabelElementObj.parentNode.parentNode.className+=' '+fileExtension;
				}
			}else{
				if(x == 'dynatree-lastsib' && fileExtension=='pdf'){
					htmlLabelElementObj.parentNode.parentNode.className='dynatree-node dynatree-lastsib pdf dynatree-exp-cl dynatree-ico-c dynatree-active dynatree-selected'
				}
				else{
					htmlLabelElementObj.parentNode.parentNode.className='dynatree-node dynatree-lastsib docx dynatree-exp-cl dynatree-ico-c dynatree-active dynatree-selected'
				}
				/* var y = htmlLabelElementObj.parentNode.parentNode.className;
				y = y.replace(x,fileExtension);
				htmlLabelElementObj.parentNode.parentNode.className = y; */
			}			
			
			var temp = htmlLabelElementObj.innerText;
			var tempFileName = temp.lastIndexOf('.');
			var newFileName = temp.substring(0, tempFileName);
			htmlLabelElementObj.innerText = newFileName +"."+ fileExtension;
		}
		return true;
	}
	function validate()
	{
		//debugger;
		var ParentFolderName = document.workspaceNodeAttrForm.fullPathName.value;
		var takeVersion = document.workspaceNodeAttrForm.takeVersion.value;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
	
		var sp = uploadFile.split('\\');
		var fname = sp[sp.length - 1].indexOf(".");
		var fname1=sp[sp.length-1];
		var filenamepath1 = ParentFolderName+fname1;
		var fname2 = 239 - ParentFolderName.length ;
		var strInvalidChars = "()/\^$#:~%@&;,_'";
    	var strChar;
    	
    	var digitExp = /^[0-9]+$/;
    	var charExp = /^[a-zA-Z0-9]+$/;
    	
    	var chkUseSrcDoc = document.workspaceNodeAttrForm.useSourceFile;
    	var index=fname1.lastIndexOf('.');
    	
    	if(index>0)
		{
			if(fname1.substring(index+1)!="pdf")
			{	
				if(!confirm("You have selected other than pdf file. Do you want to upload?"))
					return false;
			}	
		}
		if(document.workspaceNodeAttrForm.isReplaceFileName.checked == false || (chkUseSrcDoc != null && chkUseSrcDoc.checked == false))
		{
	    	if(fname1.length>64)
	    	{
	    		alert("Uploaded File Name must be less than 64 characters");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
		     	document.workspaceNodeAttrForm.uploadFile.focus();
		     	return false;
	    	}
	    
	    	if(filenamepath1.length>239)
			{
			    alert("Uploaded File Name must be less than" + " " + fname2 + " characters");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
		     	document.workspaceNodeAttrForm.uploadFile.focus();
		     	return false;
			}
	   		
	       	for (i = 0; i < fname1.length; i++)
	    	{
	 			strChar = fname1.charAt(i);
			 	if (strInvalidChars.indexOf(strChar)!= -1)
					{
		      			alert("Invalid Document name." + " " + strChar + "  is not allowed" );
		      			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
		      			return false;  			
	 				}
	 			if(strChar==' ')
	 			{
	 				alert('\'Space\' Is Not Allowed In The Uploaded Document Name!\n\nPlease Rename The Document And Upload It Again.')
	 				return false;
	 			}	
	 				
		   	}
		}
		
		if(uploadFile=="" && OperationId!="delete" )
		{
			//debugger;
			if(chkUseSrcDoc == null || (chkUseSrcDoc != null && chkUseSrcDoc.checked == false))
			{
				alert("Please Upload Document");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.uploadFile.focus();
				return false;
			}
		 		
		}
		else if(takeVersion=="true")
		{
			var versionPrefix = document.workspaceNodeAttrForm.versionPrefix.value;
			var versionSuffix = document.workspaceNodeAttrForm.versionSuffix.value;
			if(versionPrefix=="")
			{
				alert("Please enter sop number prefix.");
				document.workspaceNodeAttrForm.versionPrefix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionPrefix.focus();
				return false;
			}
			else if(versionPrefix.length > 3)
			{
				alert("Sop number prefix cannot be more than 3 characters.");
				document.workspaceNodeAttrForm.versionPrefix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionPrefix.focus();
				return false;
			}
			else if(!versionPrefix.match(charExp))
			{
				alert("Only alphabets and digits are allowed for sop number prefix.");
				document.workspaceNodeAttrForm.versionPrefix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionPrefix.focus();
				return false;
			}
			else if(versionSuffix=="")
			{
				alert("Please enter sop number suffix(initial version).");
				document.workspaceNodeAttrForm.versionSuffix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionSuffix.focus();
				return false;
			}
			else if(versionSuffix.length > 3)
			{
				alert("Sop number suffix cannot be more than 3 characters.");
				document.workspaceNodeAttrForm.versionSuffix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionSuffix.focus();
				return false;
			}
			else if(!versionSuffix.match(digitExp))
			{
				alert("Only digits are allowed for sop number suffix.");
				document.workspaceNodeAttrForm.versionSuffix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionSuffix.focus();
				return false;
			}
		}
		changePdfIcon();
		return true;
	}
	
	function DMSvalidate()
	{
		debugger;
		var docType = "<s:property value='appType'/>";
		var extension = "<s:property value='fileExt'/>";
		let extFile = extension.split(",");
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
		var remarkvalue = document.workspaceNodeAttrForm.remark.value;
    	var hiddenFieldvalue = document.workspaceNodeAttrForm.secret.value;
    	var deltefilevalue = $('input[name="deleteFile"]:checked').length
    	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
    	var index=uploadFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^'$#:~%@&;,!*<>?";
    	var extFlag="false";
    	
    	/* var formate = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/; */
    	var fileext = uploadFile.substring(index+1).toLowerCase();
    	var strChar;
    	if(uploadFile=="" && deltefilevalue==0)
		{
				alert("Please Select Document");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.uploadFile.focus();
				return false;
		}
  		
    	if(docType=='0003'){
    		if((fileext !="pdf") && deltefilevalue==0){
    			alert("Please upload only pdf Document.")
    			return false;
    		}
    	}
    	else{
    	/* if((fileext !="pdf" && fileext!="doc" && fileext!="docx") && deltefilevalue==0){
			alert("Please upload valid extension(e.g. .pdf, .doc & .docx) Document.")
			return false;
		} */
    		if(deltefilevalue==0){
    			for (let i = 0; i < extFile.length; i++) {
    		    	if (fileext.toLowerCase()==extFile[i]){
    		    		extFlag = "true";
    		    		break;  			
    				}
    		    }
    			if(extFlag=="false"){
		    		alert("Please upload valid extension Document.\n ."+fileext+" Document not allowed.")
			    	return false;
    			}
    			
    		}
    	} 
    	
    	/*if(index>0)
		{
			if(fileext!="pdf" && deltefilevalue==0)
			{	
				alert("You have Selected other than PDF file.Please upload PDF File.");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7";
			    return false;
			}	
		} */
     	for (i = 0; i < uploadFile.length; i++)
    	{
 			strChar = uploadFile.charAt(i);
		 	if (strInvalidChars.indexOf(strChar)!= -1 && deltefilevalue==0)
				{
		 		alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
	      			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	      			return false;  			
 				}
 				
	   	}	
		if(remarkvalue=="" && hiddenFieldvalue!=0)
			{
			alert("Please specify Reason for Change.");
			document.workspaceNodeAttrForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.remark.focus();
			return false;
			}
    		
    	
    	/* var elecSig='Yes';
		document.getElementById('reConfPass').value = '';
		if (elecSig=='Yes')
			{
			centerPopup();				
			loadPopup();
			return false;
			}	 */
			changePdfIcon();
		return true;
	}
	function chgStatus()
	{
         //debugger;
		//var sessPass=document.getElementById('sessPass').value;
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		
		if (!pass || pass == '' )
		{						
			alert("Please Enter Password !!!");
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
			changePdfIcon();
		}
	}
	
	function callAddMore(attrId)
	{
   		strAction = "addMoreAttribute.do?attrId="+attrId;
   		window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=130,width=500,resizable=no,titlebar=no");	
   		return true;
	}
	
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
 	
 	function fileOpen(actionName)
 	{
    		win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
    		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
    }
    function operationDelete(deleteOperation)
    {
    	 OperationId = document.getElementById(deleteOperation).value;
    
    	 if(OperationId == 'delete'){
    		document.getElementById("fileTr").style.visibility='hidden';
    		document.getElementById("renameOrDeleteFileTr").style.visibility='hidden';
    		document.getElementById("autoCorrectPdfPropTr").style.visibility='hidden';
    	 }  
		 else {
			document.getElementById("fileTr").style.visibility='visible';   
			document.getElementById("renameOrDeleteFileTr").style.visibility='visible';
			document.getElementById("autoCorrectPdfPropTr").style.visibility='visible';
		 }
    }
    
    function cls()
	{
		document.getElementById('reConfPass').value = '';
		disablePopup();
	}
    function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.workspaceNodeAttrForm.submitBtn.onclick();
  		} 
	} 
    function loader(){
    	//debugger; 
    	document.getElementById("loadingpopup").style.display='block'; 
     }
	document.onkeypress = detectReturnKey;
    
</script>

<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>

</head>
<body class="bdycls">

<%
	String takeVersion = request.getAttribute("takeVersion").toString();
%>

<s:actionerror />

<s:form name="workspaceNodeAttrForm" method="post"
	action="SaveNodeAttrAction" enctype="multipart/form-data" onsubmit="return loader();">

<div id="loadingpopup" style="width: 50px;z-index: 1;position: absolute;top: 180.5px;left: 392.5px;
			padding-top: 0px;display:none">
   <center><img style="margin-top:100px" src="images/loading.gif" alt="loading ..." /></center>
		</div>

	<div class="headercls">Edit ${ lbl_nodeName }: <s:property value="finalString" /></div>
	<div class="bdycls">

	<table width="100%" align="center">


		<%
			int elementid = 1;
		%>

		<s:iterator value="attrDtl">
			<s:if test="userInput == 'Y'">
				<s:if test="attrType == 'Text'">

					<tr>
						<td class="title">${attrName }</td>
						<td><input type="text" name="<%="attrValueId" + elementid%>"
							value="${attrValue }"> <input type="hidden"
							name="<%="attrType" + elementid%>" value="Text"> <input
							type="hidden" name="<%="attrName" + elementid%>"
							value="${attrName }"> <input type="hidden"
							name="<%="attrId" + elementid%>" value="${attrId }"></td>
					</tr>
					<%
						elementid++;
					%>
				</s:if>
			</s:if>
		</s:iterator>


		<s:iterator value="attrDtl">
			<s:if test="userInput == 'Y'">
				<s:if test="attrType == 'TextArea'">

					<tr>
						<td class="title">${attrName }</td>
						<td><textarea rows="3" cols="30"
							name="<%="attrValueId" + elementid%>">${attrValue }</textarea> <input
							type="hidden" name="<%="attrType" + elementid%>" value="TextArea">
						<input type="hidden" name="<%="attrName" + elementid%>"
							value="${attrName }"> <input type="hidden"
							name="<%="attrId" + elementid%>" value="${attrId }"></td>
					</tr>
					<%
						elementid++;
					%>
				</s:if>
			</s:if>
		</s:iterator>


		<s:iterator value="attrDtl">

			<s:if test="attrType == 'Date'">
				<tr>
					<td class="title">${attrName }</td>
					<td><input type="text" name="<%="attrValueId" + elementid%>"
						class="attrValueIdDate" readonly="readonly"
						id="<%="attrValueId" + elementid%>"
						value="<s:property value="attrValue"/>"> (YYYY/MM/DD)
					&nbsp;<IMG
						onclick="if(document.getElementById('<%="attrValueId" + elementid%>').value != '' 
												&& confirm('Are you sure?'))
											document.getElementById('<%="attrValueId" + elementid%>').value = '';"
						src="<%=request.getContextPath()%>/images/clear.jpg"
						align="middle" title="Clear Date"> <input type="hidden"
						name="<%="attrType" + elementid%>" value="Date"> <input
						type="hidden" name="<%="attrName" + elementid%>"
						value="${attrName }"> <input type="hidden"
						name="<%="attrId" + elementid%>" value="${attrId }"></td>
				</tr>
				<%
					elementid++;
				%>
			</s:if>

		</s:iterator>

		<s:iterator value="attrDtl">
			<s:if test="userInput == 'Y'">
				<s:if test="attrType == 'Dynamic'">

					<tr>
						<td class="title">${attrName }</td>
						<td><s:property value="attrValueId%{#elementid}" /> <s:property
							value="attrValueId%{elementid}" /> <s:property
							value="attrValueId#elementid" /> <s:iterator
							value="filterDynamicList">
							<s:if test="top[0] == attrId">
								<select name='<%="attrValueId" + elementid%>'>
									<option value="">Please Select Value</option>
									<s:iterator value="top[1]">
										<option value='<s:property value="top"/>'
											<s:if test='attrValue.equals(top)'>selected = "selected"</s:if>><s:property
											value="top" /></option>
									</s:iterator>
								</select>
							</s:if>
						</s:iterator> <input type="hidden" name="<%="attrType" + elementid%>"
							value="TextArea"> <input type="hidden"
							name="<%="attrName" + elementid%>" value="${attrName }">
						<input type="hidden" name="<%="attrId" + elementid%>"
							value="${attrId }"></td>
					</tr>
					<%
						elementid++;
					%>
				</s:if>
			</s:if>
		</s:iterator>

		<s:set name="prevattrid" value="-1" />
		<s:iterator value="attrDtl">
			<s:if test="userInput == 'Y'">
				<s:if test="attrType == 'Combo'">
					<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<tr>
							<td class="title">${attrName}</td>
							<td><select name="<%="attrValueId" + (elementid)%>"
								id="<%="attrValueId" + (elementid)%>"
								onchange="operationDelete('<%="attrValueId" + (elementid)%>')">
								<s:set name="outterAttrId" value="attrId" />

								<%
									int op = 1;
								%>

								<s:iterator value="attrDtl">
									<s:if test="#outterAttrId == attrId">
										<s:if test="attrName =='operation'">
											<%
												if (op == 1) {
											%>
											<option value="new">new</option>
											<%
												op = 2;
																				}
											%>

											<s:if test="lastPublishedVersion == '-999'">
												<s:if test="attrMatrixValue == 'new'">


												</s:if>
											</s:if>
											<s:else>
												<s:if test="submittedNodeHistory == false">
													<s:if test="attrMatrixValue == 'new'">

													</s:if>
												</s:if>
												<s:elseif test="checkDelete ==  'delete'">
													<s:if test="attrMatrixValue == 'new'">
														<option value="<s:property value="attrMatrixValue"/>"><s:property
															value="attrMatrixValue" /></option>

													</s:if>
												</s:elseif>
												<s:elseif test="attrMatrixValue != 'new'">

													<option value="<s:property value="attrMatrixValue"/>"><s:property
														value="attrMatrixValue" /></option>
												</s:elseif>
											</s:else>
										</s:if>
										<s:else>
											<s:if test="attrValue == attrMatrixValue">
												<option value="<s:property value="attrMatrixValue"/>"
													selected="selected"><s:property
													value="attrMatrixValue" /></option>
											</s:if>
											<s:else>
												<option value="<s:property value="attrMatrixValue"/>"><s:property
													value="attrMatrixValue" /></option>
											</s:else>

										</s:else>
									</s:if>
								</s:iterator>
							</select> <input type="hidden" name="<%="attrType" + elementid%>"
								value="Combo"> <input type="hidden"
								name="<%="attrName" + elementid%>" value="${attrName }">
							<input type="hidden" name="<%="attrId" + elementid%>"
								value="${attrId }"></td>
						</tr>
						<%
							elementid++;
						%>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:if>
		</s:iterator>

		<input type="hidden" name="attrCount" value="<%=(elementid - 1)%>">
		<tr id="fileTr">
			<td class="title" width="18%;" style="padding-left:12px;">New Document</td>
			<td align="left"><s:file name="uploadFile" required="true"></s:file>
			</td>
		</tr>
		<tr id="renameOrDeleteFileTr">
			<td></td>
			<td class="title" style="color: black; font-weight: lighter">
			<s:if
				test="appType == '0001'">
				<input type="checkbox" name="isReplaceFileName" value="Y"
					checked="checked">
					
				Replace eCTD Specific FileName
				
			</s:if> 
			
			<s:elseif test="appType=='NeeS' || appType=='vNeeS'">
				<input type="checkbox" name="isReplaceFileName" value="Y"
					checked="checked">
					
				Replace NeeS Specific FileName
			
			</s:elseif>
			
			<s:elseif test="appType == '0002' || appType == '0003'">
				<input type="checkbox" name="deleteFile" value="Y" 
					<s:if test="nodeHistory.size == 0 || nodeHistory.get(0).fileName.equalsIgnoreCase('no file') || stageId==100">disabled="disabled"</s:if>>
					Delete Document
			</s:elseif></td>
		</tr>
	
	<s:if test="(appType=='0002' && alloweTMFCustomization=='yes')">
		<!-- <tr>
		<td></td>
		<td class="title" style="color: black; font-weight: lighter">
				<input type="checkbox" name="isReplaceFolderName" value="Y">
					checked="checked"
					
				Replace eTMF Specific FolderName
		</td></tr> -->	
	</s:if> 
		
		<s:if test="(appType == '0001' || appType=='NeeS' || appType=='vNeeS' || appType=='0002') && allowAutoCorrectionPDFProp=='yes'">
			<!-- <tr id='autoCorrectPdfPropTr'>
				<td></td>
				<td class="title" style="color: black; font-weight: lighter"><input
					type="checkbox" name="autoCorrectPdfProp" value="Y"
					checked="checked"> Auto Correct PDF Properties</td>
			</tr> -->
		</s:if>
		<%-- <s:if test="nodeDocHistory.size() > 0">
			<tr>
				<td></td>
				<td class="title" style="color: black; font-weight: lighter"
					title="Check if you want to convert Source Document into PDF and upload it as the node file.">
				<input type="checkbox" name="useSourceFile" value="Y"
					checked="checked"> Use Source Document</td>
			</tr>
		</s:if> --%>

		<input type="hidden" name="takeVersion" value="<%=takeVersion%>">

		<%
			if (takeVersion.equals("true")) {
		%>
		<!-- <tr>
			<td class="title">File Version</td>
			<td><input type="text" size="7" name="versionPrefix"
				onblur="callonBlur(this);" value="A"></td>
		</tr>
		<tr>
			<td class="title"></td>
			<td><SELECT NAME="VersionSeperator" class="comboFontSize"
				title="Seperator">
				<option value="-">-</option>
				<option value="/">/</option>
				<option value=":">:</option>
			</Select> <font class="title">Start With&nbsp;&nbsp;</font><input type="text"
				name="versionSuffix" value="1" onblur="callonBlur(this);"></td>

		</tr> -->
		<%
			}
		%>
		<s:if test="appType == '0002' || appType == '0003'">
	<%-- 	<s:property value="stageCode"/> --%>
			<%-- <s:if test="stageCode != 0"> --%>
				<tr>
					<td class="title" width="18%;" style="padding-left:12px;">Reason for Change</td>
					<td align="left"><textarea rows="3" cols="30" name="remark"></textarea></td>
					
					<s:hidden name="secret" value="%{historysize}" />
					<td></td>
				</tr>
	</s:if>

	<%-- 	</s:if> --%>
		<tr>
			<%-- <td><s:if test="appType == 'eCSV'">

				<s:submit name="submitBtn" value="Save & Send File Comments"
					cssClass="button" onclick="return changePdfIcon();" />
			</s:if> <s:elseif test="appType == 'eCTD' || appType=='NeeS' || appType=='vNeeS'">
				<s:submit name="submitBtn" onclick="return validate();"
					value="Save & Send File Comments" cssClass="button" />
			</s:elseif></td> --%>
			
			<td colspan="2" style="padding-left: 8px;"><s:if test="appType == '0002' || appType == '0003'">

				<s:submit name="submitBtn" value="Save Document" id="savesendbtn"
					cssClass="button" onclick="return DMSvalidate();" />
			</s:if> <s:elseif test="appType == '0001' || appType=='NeeS' || appType=='vNeeS'">
				<s:submit name="submitBtn" onclick="return validate();"
					value="Save & Send File Comments" cssClass="button" />
			</s:elseif></td>
			<td></td>
		</tr>

	</table>
	<br>
	<!-- <div id="popupContact" style="width: 350px; height: 120px;"><img
			alt="Close" title="Close" src="images/Common/Close.png"
			onclick='cls();' class='popupCloseButton' />
		<h3>Electronic Signature</h3>
		<div style="width: 100%; height: 90px; overflow: auto;">Enter
		password:&nbsp;<input type="password" name="reConfPass"
			id="reConfPass"></input> <br>
		<br>
		<p align="center">
			<input type="submit" class="button"
				value="Verify" id="Verify" name="buttonName" onclick="return chgStatus();"></input>
		</p>
		</div>
		</div>
		<div id="backgroundPopup"></div> -->
	<br>
	<table width="95%" class="datatable audittrailtable">
		<thead>
			<tr>
				<th>Last Modified Date</th>
		    <s:if test ="#session.countryCode != 'IND'">
				<th>Eastern Standard Time</th>
			</s:if>
				<th>Description</th>
				<th>Value</th>
				<th>Username</th>

			</tr>
		</thead>
		<tbody>
			<tr class="odd">

				<td>&nbsp; <s:subset source="nodeHistory" count="1" start="0">
					<s:iterator>
						<!-- <s:date name="modifyOn" /> -->
						<s:property value="ISTDateTime" />
					</s:iterator>
				</s:subset></td>
			<s:if test ="#session.countryCode != 'IND'">
					<td><s:subset source="nodeHistory" count="1" start="0">
						<s:iterator>
							<s:property value="ESTDateTime" />
						</s:iterator>
					</s:subset></td>
			</s:if>
				<td>Document</td>
				<td>&nbsp; <s:subset source="nodeHistory" count="1" start="0">
					<s:iterator>
						<s:if test="fileName == 'No File' || fileName == 'no File'">
							<s:property value="fileName" />
						</s:if>
						<s:else>
							<a href="#"
								onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
							<s:property value="fileName" /> </a>
						</s:else>
					</s:iterator>
				</s:subset></td>
				<td>&nbsp; <s:subset source="nodeHistory" count="1" start="0">
					<s:iterator>
						<s:property value="userName" />
					</s:iterator>
				</s:subset></td>

			</tr>


		</tbody>
	</table>
	<br />

	<s:if test="nodeHistory.size ==0">
	</s:if> <s:else>
		<div class="headercls">User Defined Attributes</div>
		<div class="bdycls" style="padding-left:5px;">
		<table>
			<s:iterator value="attrHistory">
				<tr>
					<td class="smalltd"><b><s:property value="attrName" /></b></td>
					<td class="smalltd">&nbsp;&nbsp;<s:property
						value="attrValue.replaceAll('\n','<br>&nbsp;&nbsp;')"
						escape="false" /></td>
				</tr>
			</s:iterator>
		</table>
		</div>
	</s:else></div>

	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="fullPathName"></s:hidden>
	<s:hidden name="nodeFolderName"></s:hidden>

</s:form>


</body>
</html>
