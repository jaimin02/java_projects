<%@page import="com.docmgmt.dto.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>


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
<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/CalendarPopup.js"></SCRIPT>
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
	
	function validate()
	{
	
	
		var ParentFolderName = document.workspaceNodeAttrForm.fullPathName.value;
		var takeVersion = document.workspaceNodeAttrForm.takeVersion.value;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
	
		var takeVersion = document.workspaceNodeAttrForm.takeVersion.value;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
		
		
		var sp = uploadFile.split('\\');
		var fname = sp[sp.length - 1].indexOf(".");
		var fname1=sp[sp.length-1];
		var filenamepath1 = ParentFolderName+fname1;
		var fname2 = 239 - ParentFolderName.length ;
		var strInvalidChars = "()/\^$#:~%@&;,_";
    	var strChar;
    	
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
	      			alert("Invalid File name." + " " + strChar + "  is not allowed" );
	      			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	      			return false;  			
 				}
 			if(strChar==' ')
 			{
 				alert('Space Is Not Allowed!')
 				return false;
 			}	
 				
	   	}
		if(uploadFile=="" && OperationId!="delete" )
		{
			alert("Please upload File");
			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     	document.workspaceNodeAttrForm.uploadFile.focus();
			return false;
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
			else if(versionSuffix=="")
			{
				alert("Please enter sop number suffix(initial version).");
				document.workspaceNodeAttrForm.versionSuffix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionSuffix.focus();
				return false;
			}
		}
		return true;
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
    }
    
    	
</script>

</head>

<body>

<%String takeVersion=request.getAttribute("takeVersion").toString(); %>

<s:actionerror />



<s:form name="workspaceNodeAttrForm" method="post"
	action="SaveNodeAttrAction" enctype="multipart/form-data">
	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="fullPathName"></s:hidden>

	<div class="headercls">Edit Node Detail</div>
	<div class="bdycls">

	<table width="100%" align="center">




		<!-- ------------dynamic display of attribute started here ----------------->
		<%
	int elementid=1;
	String fileName="";
 	%>
		<!-- Text Box -->


		<s:iterator value="attrDtl">
			<s:if test="userInput == 'Y'">
				<s:if test="attrType == 'Text'">

					<tr>
						<td class="title">${attrName }</td>
						<td><input type="text" name="<%="attrValueId"+elementid%>"
							value="${attrValue }"> <input type="hidden"
							name="<%="attrType"+elementid %>" value="Text"> <input
							type="hidden" name="<%="attrName"+elementid %>"
							value="${attrName }"> <input type="hidden"
							name="<%="attrId"+elementid %>" value="${attrId }"></td>
					</tr>
					<%elementid++;%>
				</s:if>
			</s:if>
		</s:iterator>

		<!-- combo  -->


		<s:set name="prevattrid" value="-1" />
		<s:set name="lastPublishedVersion" id="lastPublishedVersion"></s:set>
		<s:iterator value="attrDtl">
			<s:if test="userInput == 'Y'">
				<s:if test="attrType == 'Combo'">
					<s:if test="lastPublishedVersion == '-999'">

						<s:if test="#prevattrid == -1 || #prevattrid != attrId">
							<tr>
								<td class="title">${attrName }</td>

								<td><select name="<%="attrValueId"+(elementid)%>">
									<s:set name="outterAttrId" value="attrId" />
									<s:iterator value="attrDtl">
										<s:if test="#outterAttrId == attrId">
											<s:if test="attrName =='operation'">
												<s:if test="attrMatrixValue == 'new'">
													<option value="<s:property value="attrMatrixValue"/>"><s:property
														value="attrMatrixValue" /></option>
												</s:if>
											</s:if>
											<s:else>
												<option value="<s:property value="attrMatrixValue"/>"><s:property
													value="attrMatrixValue" /></option>
											</s:else>
										</s:if>
									</s:iterator>
								</select> <input type="hidden" name="<%="attrType"+elementid %>"
									value="Combo"> <input type="hidden"
									name="<%="attrName"+elementid %>" value="${attrName }">
								<input type="hidden" name="<%="attrId"+elementid %>"
									value="${attrId}"></td>
							</tr>
							<%elementid++;%>
						</s:if>
					</s:if>

					<s:else>

						<s:if test="#prevattrid == -1 || #prevattrid != attrId">
							<tr>
								<td class="title">${attrName }</td>
								<td><s:if test="attrName =='operation'">
									<select name="<%="attrValueId"+(elementid)%>"
										onchange="operationDelete('<%="attrValueId"+(elementid)%>')">
								</s:if> <s:else>
									<select name="<%="attrValueId"+(elementid)%>">
								</s:else> <s:set name="outterAttrId" value="attrId" /> <s:iterator
									value="attrDtl">
									<s:if test="#outterAttrId == attrId">
										<!-- revised submission -->

										<s:if test="nodeHistory.size ==0"
											|| test="nodeHistory.size ==0">
											<s:if test="attrName =='operation'">
												<s:if test="attrMatrixValue == 'new'">
													<option value="<s:property value="attrMatrixValue"/>"><s:property
														value="attrMatrixValue" /></option>
												</s:if>
											</s:if>
											<s:else>
												<option value="<s:property value="attrMatrixValue"/>"><s:property
													value="attrMatrixValue" /></option>
											</s:else>
										</s:if>
										<s:else>
											<s:if test="attrMatrixValue != 'new'">
												<option value="<s:property value="attrMatrixValue"/>"><s:property
													value="attrMatrixValue" /></option>
											</s:if>
										</s:else>
									</s:if>
								</s:iterator> </select> <input type="hidden" name="<%="attrType"+elementid %>"
									value="Combo"> <input type="hidden"
									name="<%="attrName"+elementid %>" value="${attrName }">
								<input type="hidden" name="<%="attrId"+elementid %>"
									value="${attrId }"></td>
							</tr>
							<%elementid++;%>
						</s:if>
					</s:else>

					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:if>
		</s:iterator>

		<input type="hidden" name="attrCount" value="<%=(elementid-1)%>">

		<!--------------- dynamic display of attribute ended here ------------------->

		<tr>
			<td class="title">New Document</td>
			<td align="left"><s:file name="uploadFile" required="true"></s:file>
			</td>

		</tr>
		<tr>
			<td class="title">Select Users</td>
			<td><s:select list="users" name="userCodedtl" headerKey="-1"
				headerValue="Select User" listKey="userCode" listValue="loginName"
				multiple="true">

			</s:select></td>
		</tr>


		<input type="hidden" name="takeVersion" value="<%=takeVersion%>">
		<% if(takeVersion.equals("true")) { %>
		<!-- Check user already entered user defined version or not -->
		<tr>
			<td class="title">File Version</td>
			<td><input type="text" size="7" name="versionPrefix"
				onblur="callonBlur(this);" value="A"></td>
		</tr>
		<tr>
			<td class="title"></td>
			<td><SELECT NAME="VersionSeperator" class="comboFontSize"
				title="Seperator">
				<option value="none">none</option>
				<option value="/">/</option>
				<option value="-">-</option>
				<option value=":">:</option>
			</Select> <font class="title">Start With&nbsp;&nbsp;</font><input type="text"
				name="versionSuffix" value="1" onblur="callonBlur(this);"></td>

		</tr>
		<% } %>

		<tr>
			<td><s:submit onclick="return validate();"
				value="Save & Send File Comments" cssClass="button" /></td>
			<td></td>
		</tr>

	</table>

	<!-- -------- ----------------node history -------------------- -->

	<table width="95%" class="datatable">
		<thead>
			<tr>
				<th>Last Modified Date</th>
				<th>Description</th>
				<th>Value</th>
				<th>User Name</th>

			</tr>
		</thead>
		<tbody>
			<tr class="odd">

				<td>&nbsp; <s:subset source="nodeHistory" count="1" start="1">
					<s:iterator>
						<s:date name="modifyOn" />
					</s:iterator>
				</s:subset></td>
				<td>Document</td>
				<td>&nbsp; <s:subset source="nodeHistory" count="1" start="1">
					<s:iterator>
						<s:if test="fileName == 'No File'">
							<s:property value="fileName" default="-" />
						</s:if>
						<s:else>
							<a href="#"
								onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
							<s:property value="fileName" default="-" /> </a>
						</s:else>
					</s:iterator>
				</s:subset></td>
				<td>&nbsp; <s:subset source="nodeHistory" count="1" start="1">
					<s:iterator>
						<s:property value="userName" default="-" />
					</s:iterator>
				</s:subset></td>

			</tr>


		</tbody>
	</table>
	<br />

	<s:if test="nodeHistory.size ==0">

	</s:if> <s:else>
		<div class="headercls">User Defined Attributes</div>
		<div class="bdycls">
		<table>

			<s:if test="attrHistory.size == 0">
				No Attributes Found
				
				</s:if>

			<s:iterator value="attrHistory">

				<tr>
					<td class="smalltd"><b> <s:property value="attrName" /></b></td>
					<td class="smalltd">&nbsp;&nbsp;<s:set name="attrValue"
						id="attrValue" /> <s:if test="attrValue == ''">
						<font color="red"> </font>
					</s:if> <s:else>
						<s:property value="attrValue" />
					</s:else></td>
				</tr>

			</s:iterator>
		</table>
		</div>
	</s:else> <!-- -------- ----------------node history -------------------       - -->

	</div>

</s:form>


</body>
</html>
