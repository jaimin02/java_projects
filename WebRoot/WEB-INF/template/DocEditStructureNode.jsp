<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
	
	
	<%-- <script>
	function DMSvalidate()
	{
		debugger;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
    	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
    	var index=uploadFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^'$#:~%@&;,!*<>?";
    	
    	/* var formate = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/; */
    	var fileext = uploadFile.substring(index+1).toLowerCase();
    	var strChar;
    	if(uploadFile=="" && deltefilevalue==0)
		{
				alert("Please Select Structure Document.");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.uploadFile.focus();
				return false;
		}
    	if((fileext!="doc" && fileext!="docx") && deltefilevalue==0){
			alert("Please upload valid extension(e.g. .pdf, .doc & .docx) Document.")
			return false;
		}
	}
	</script> --%>
	
	
<s:if test="actionMessages!=null && actionMessages.size > 0">
    <script>
        var actionMessages;
        <s:iterator value="actionMessages" >
            // Iterate the messages, and build the JS String
            actionMessages =  '<s:property />';
            actionMessages=actionMessages.substring(1, actionMessages.length-1);
        </s:iterator>   
        if(actionMessages=="pdfUpload"){
        	alert("Access denied !, File upload unsuccessful. Server refuses the active connection");
        }else if(actionMessages=="docUpload"){
        	alert("Access denied !, File upload unsuccessful. Server refuses the active connection");
        }
        else if(actionMessages=="EncryptedFile"){
        	alert("You are try to upload password protected file. \nPlease remove password protection security and re-upload the same.");
        }
        else if(actionMessages=="FileUploadingSize"){
        	alert("You can't upload file size greate than 100 MB.");
        }
        
    </script>
</s:if>
</head>
<body class="bdycls">
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:form name="structureForm" method="post" action="SaveTemplateFile" enctype="multipart/form-data">
	<br />
	<div class="bdycls" style="padding-left: 5px; width: 100%;">
	<s:if test="isLeafNode==1">
	<table width="80%" cellspacing="0">
			<tr>
				<td class="title" width="32%" style="padding: 2px; padding-right: 8px;">Select Structure Document</td>
				<td width="10%"><s:file name="uploadFile" required="true" />
				<td><s:submit cssClass="button" name="saveSend" value="Save Document" onclick="return DMSvalidate();"/></td>
			</tr>
	</table>
	</s:if>
	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="templateId"></s:hidden>
	</div>
	<br>
	<s:if test="fullNodeHistory.size() > 0">
<div style="border: 1px; margin-top:65px; overflow: auto; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px;
 border-top: none; width: 99%; margin-left: 5px;" align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Document History</b></div>
<br>
<div style="max-height: 342px; overflow: auto;">
<table class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<th>Document</th>
			<th>Size</th>
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="fullNodeHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="#status.count" /></td>
				<td>
				<s:if test="fileName == ''">&nbsp;-</s:if>
				
				 <s:else>
				 <s:if test="fileExt=='PDF' || fileExt=='pdf'">
				 <a href="javascript:void(0);"
						onclick="return fileOpen('openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>');">
					<s:property value="fileName" /> </a>
				 </s:if>
				 <s:else>
					<a href="javascript:void(0);"
						onclick="return docFileOpen('openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>');">
					<s:property value="fileName" /> </a>
				</s:else>
				</s:else></td>
				<td><s:if test="historyDocumentSize.sizeMBytes>0">
					<s:property value="historyDocumentSize.sizeMBytes" default="--" /> MB
								</s:if> <s:else>
					<s:property value="historyDocumentSize.sizeKBytes" default="--" /> KB
								</s:else></td>
				<td><s:property value="userName" /></td>
				<td><s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="remark" /></td>
			</tr>
		</s:iterator>

	</tbody>
</table>
</div>
</div>
</div>
</s:if>
</s:form>
</body>
</html>