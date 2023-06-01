<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

<SCRIPT type="text/javascript">
	/* window.onunload = refreshParent;
	function refreshParent() {
    		window.opener.location.reload();
	} */
	 function refreshParent() 
	{
		window.opener.parent.history.go(0);
		self.close();               
	} 
	function closeWindow()
	{
		window.opener.location.reload();
		window.close();
	}
	function deleteNode(nodeId)
	{
		var remark=document.forms["frmNodeDetail"].remark.value;
		if(remark==null || remark==""){
			alert("Please specify reason for change.");
			document.forms["frmNodeDetail"].remark.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].remark.focus();
			return false;
		}
		window.location.href = "DeleteProjectNode.do?nodeId="+nodeId+"&remark="+remark;
		//refreshParent();
	}
	function validate(){
		//debugger;
		var remark=document.forms["frmNodeDetail"].remark.value;
		var nodeName=document.forms["frmNodeDetail"].nodeName.value;
		var folderName=document.forms["frmNodeDetail"].folderName.value;
		var lbl_nodeName = '<s:property value="lbl_nodeName"/>';
		var lbl_folderName = '<s:property value="lbl_folderName"/>';
		var lbl_nodeDisplayName = '<s:property value="lbl_nodeDisplayName"/>';
		var fileExt = folderName.substring(folderName.lastIndexOf(".")+1);
		fileExt = fileExt.toLowerCase();
		if(nodeName==null || nodeName==""){
			alert("Please specify "+lbl_nodeName+".");
			document.forms["frmNodeDetail"].nodeName.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].nodeName.focus();
			return false;
		}
		if(folderName==null || folderName==""){
			alert("Please specify "+lbl_folderName+".");
			document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].folderName.focus();
			return false;
		}
		
		if(document.forms["frmNodeDetail"].nodeDisplayName.value == ''){
			alert("Please specify "+lbl_nodeDisplayName+".");
			document.forms["frmNodeDetail"].nodeDisplayName.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].nodeDisplayName.focus();
			return false;
		}
		/* if(document.forms["frmNodeDetail"].nodeDisplayName.value.includes('-')){
			alert(" - (e.g. Dash) is not allowed in "+lbl_nodeDisplayName);
			document.forms["frmNodeDetail"].nodeDisplayName.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].nodeDisplayName.focus();
			return false;
		} */
		
		if(fileExt != "pdf" && fileExt != "doc" && fileExt != "docx"){
			alert("Please specify valid extension(e.g. .pdf, .doc & .docx) "+lbl_folderName+".");
			document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].folderName.focus();
			return false;
		}
		if(remark==null || remark==""){
			alert("Please specify Reason for Change.");
			document.forms["frmNodeDetail"].remark.style.backgroundColor="#FFE6F7"; 
     		document.forms["frmNodeDetail"].remark.focus();
			return false;
		}
		var isMultiple=document.getElementById("isMultiple").value;
		uploadFile= document.forms["frmNodeDetail"].folderName.value;
		var index=uploadFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
    	var fileext = uploadFile.substring(index+1).toLowerCase();
    	var strChar;
		if(isMultiple=='yes')
			return true;
		/* if(index<0)
			{
			alert("File name should be valid and end with .pdf extesion.");
			document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7";
			document.forms["frmNodeDetail"].folderName.focus();
			return false;
			} */
		if(index>0)
		{
			/* if(fileext!="pdf")
			{	
				alert("File name should be valid and end with .pdf extesion.");
				document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7";
				document.forms["frmNodeDetail"].folderName.focus();
			    return false;
			}	 */
		}
     	for (i = 0; i < uploadFile.length; i++)
    	{
 			strChar = uploadFile.charAt(i);
		 	if (strInvalidChars.indexOf(strChar)!= -1)
				{
		 		alert("Invalid document name. \nOnly alphabets, digits, dot, underscore and dash are allowed.");
		 		document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7";
				document.forms["frmNodeDetail"].folderName.focus();
	      			return false;  			
 				}
 			/* if(strChar==' ')
 			{
 				alert("Invalid File Name. \n\nOnly Alphabets, Digits,'_' and '-' are allowed.");
 				document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7";
 				document.forms["frmNodeDetail"].folderName.focus();
 				return false;
 			} */	
 				
	   	}
	/* 	if(!/^[A-Za-z0-9-.\\(\\)]+$/.test(document.forms["frmNodeDetail"].folderName.value)) 
		{	
			alert("Invalid Folder Name. \n\nOnly Alphabets, Digits,(,' and '-' are allowed.");
			document.forms["frmNodeDetail"].folderName.style.backgroundColor="#FFE6F7";
			document.forms["frmNodeDetail"].folderName.focus();
			return false;			
		} */
		//refreshParent();
		return true;	
	}
</SCRIPT>
<!-- error  -->
<STYLE type="text/css">
<s:iterator value="fieldErrors">#<s:property value="key"/>,
</s:iterator>
{
/*border:1px solid #FFE6F7;*/
color:red;
}
</STYLE>
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div class="container-fluid">
<div class="col-md-12">
<br>
<div class="boxborder">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div align="left"><s:form action="SaveProjectNodes" method="post" name="frmNodeDetail">
	<table class="datatable paddingtable" style="width:100%; border:0; cellpadding:2;">
	<tr>
		<div class="all_title"><b style="float: left;">Change Details</b></div><!-- 
		<td class="boxborder" align="center" colspan="2"><b><font color="#c00000">Change
			Node Details</font></b></td> -->
		</tr>
	</table>
	<br>
	<div class="boxborder">
	<div class="all_title" style="margin-left: 10px;margin-right: 10px;"><center><b>${ lbl_nodeName } Details</b></center></div>
	<!-- 		<div class="boxborder" align="center" style="width: 100%">Node	Details</div> -->
	<br>
	<div id="imp" style="float: right; margin-right: 10px; margin-top: -15px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
	<div align="center" class="bdycls" style="margin-left: 10px;margin-right: 10px;">
	<table style=" border:none; align:center; cellspacing:2; cellpadding:2;">
		<s:iterator value="getNodeDetail">
		<s:if test ="eTMFCustomization == 'yes'">
		

		<%-- <s:if test="isLeafNode==1 && isRepeatFlag=='Y'">
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">${ lbl_nodeName }: 
					<span style="font-size:20px;color:red">*</span></td>
					<td align="left"><s:textfield name="nodeDisplayName" size="80%"></s:textfield>				
				</td>
			</tr>
	</s:if>
	<s:else> --%>
	<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">${ lbl_nodeName }: </td>
				<td align="left"><s:textfield name="nodeDisplayName" size="80%" readonly="true"></s:textfield></td>
			</tr>
	<%-- </s:else> --%>
	<tr>
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">${ lbl_nodeDisplayName }: 
					<span style="font-size:20px;color:red">*</span></td>
				<td  align="left">
				<%  if(request.getParameter("isSTF")==null || !request.getParameter("isSTF").equals("true")){
						
					%>
					<s:textfield id="nodeName" name="nodeName" size="80%"></s:textfield>
				<% }
				else{%>
					<s:textfield id="nodeName" name="nodeName" size="80%" readonly="true"></s:textfield>
					<s:hidden name="nodeName" ></s:hidden>
				<%}%>	
				</td>
			</tr>
 	
 	</s:if>
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">${ lbl_folderName }:
					<span style="font-size:20px;color:red">*</span></td>
				<td align="left">
				<s:if test="folderName.equals('crf') || folderName.equals('subject-profiles') || folderName.equals('datasets') || folderName.equals('image')">
					<s:textfield id="folderName" name="folderName" size="80%" readonly="true"></s:textfield>
					<s:hidden name="folderName"></s:hidden>
					<s:hidden name="isMultiple" id="isMultiple" value="yes"></s:hidden>
					
				</s:if>
				<s:else>
					<s:textfield id="folderName" name="folderName" size="68%"></s:textfield>
					<s:hidden name="isMultiple" id="isMultiple" value="no"></s:hidden>
				</s:else>
				
				</td>
			</tr>
			<tr align="left">
				<td class="title" align="right" width="50%"
					style="padding: 2px; padding-right: 8px;">Reason for Change
					<span style="font-size:20px;color:red">*</span></td>
				<%-- <td align="left"><TEXTAREA rows="4" cols="69" name="remark">${remark }</TEXTAREA> --%>
				<td align="left"><TEXTAREA rows="3" style="width: 200px;" name="remark"></TEXTAREA>
				</td>
			</tr>
			<tr><td colspan="2"></td></tr>
			<tr>
			
				<td align="center" colspan="2">
				<center>
				<%  if(request.getParameter("isSTF")==null || !request.getParameter("isSTF").equals("true")){
						
					%>
				    <input type="checkbox" style="display:none;" onchange="this.checked = !this.checked" name="isPublishable" value="Y"
					<s:if test="publishedFlag=='Y'"> checked='checked'</s:if> />&nbsp;
				<!-- <font color="navy">Is Publishable</font> -->
				 
				 <s:if test="appType.equalsIgnoreCase('0001')">
					<input type="checkbox" name="isOnlyFolder" value="Y"
						<s:if test="nodeTypeIndi=='F'"> checked='checked'</s:if> />&nbsp;
									<font color="navy">Skip XML Node</font>
									&nbsp;&nbsp;&nbsp;
				</s:if>
								
				<%} %>				
				<s:submit cssClass="button" value="Save" id="Save" name="Save" onclick="return validate();" /> &nbsp;
				 
				 <%  if(request.getParameter("isSTF")==null || !request.getParameter("isSTF").equals("true")){
						
					%><s:if test="usertypename == 'WA'">
						<%-- <s:if test="isLeafNode !=0 && !(workSpaceNodeDtl.requiredFlag == 'S' && workSpaceNodeDtl.cloneFlag == 'N')"> --%>
						   <s:if test = "isLeafNode!=1 && WorkspaceUserHistory == false">						
							 <input type="button" value="Remove" onclick="return deleteNode(${nodeId});" class="button" />
						  <%-- </s:if> --%>
						</s:if> 
					</s:if>&nbsp;
				<%} %>	
				<input type="button" value="close" onclick="return closeWindow();" class="button" />
				</center>
				</td>
				
			</tr>
			
		</s:iterator>
	</table>
	</div>
	</div>
	<s:hidden name="nodeId"></s:hidden>
</s:form> <br>

<%--<%  if(request.getParameter("isSTF")==null || !request.getParameter("isSTF").equals("true")){
						
					%>
 <table style="width:100%; border:0; cellpadding:2; align:center;">
	<tr>
		<td align="center" colspan="2"><b><font color="#c00000">Attribute
		Details</font></b></td>
	</tr>
</table>

<%int srNo = 1; %> <br>
<div align="center">
<table style="width:95%; align:center;" class="datatable">
	<thead>
		<tr>
			<th>srNo</th>
			<th>Attribute Name</th>
			<th>Attribute Value</th>
			<th>ModifyOn</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
		</tr>

	</thead>
	<tbody>
		<s:iterator value="getNodeAttrDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><%=srNo++ %></td>
				<td><s:property value="attrName" /></td>
				<td><s:hidden name="attrValue" id="attrValue"></s:hidden> <s:if
					test="attrValue == ''">
					<font color="red">NULL</font>
				</s:if> <s:else>
					<s:property value="attrValue" />
				</s:else></td>
				<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /></td> -->
				<td><s:property value="ISTDateTime" /></td> 
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>

			</tr>
		</s:iterator>
	</tbody>
</table>
	<%} %>	
</div> --%>
</div>
</div>

</div>
</div>
</div>
</body>
</html>
