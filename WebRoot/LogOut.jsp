<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.docmgmt.dto.DTOCheckedoutFileDetail"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>

<html>
<head>

<script language="javascript">

	/* function selectAll(){

		var chkBox = document.unlockFileForm.selectedTranNo;
			if ($(".checkAll").html() == 'Check All'){
				if($('[name="selectedTranNo"]').length==1){
					 chkBox.checked = 'checked';
				   } else{
						
					   for(i=0; i<chkBox.length; i++)
						{
							chkBox[i].checked = 'checked';
						}
					}
				$(".checkAll").val('Uncheck All');
			 }else
			  {
				 if($('[name="selectedTranNo"]').length==1){
					 chkBox.checked = '';
				   } else{
						
					   for(i=0; i<chkBox.length; i++)
						{
							chkBox[i].checked = '';
						}
					}
				 $(".checkAll").val('Check All');
			 }	
		
	} */ 	
	
	function selectAll(){
		var chkBox = document.unlockFileForm.selectedTranNo;
			if ($(".checkAll").val() == 'Select All'){
				for(i=0; i<chkBox.length; i++)
				{
					chkBox[i].checked = 'checked';
			 	}
				$(".checkAll").val('Unselect All');
			 }else
			  {
				 for(i=0; i<chkBox.length; i++)
				 {
					 chkBox[i].checked = '';
				 }
				 $(".checkAll").val('Select All');
			 }	
		
	} 	
	function checkChkbox(){
		//debugger;
		var len = $(".chkbox:checked").length;
		if(len==0){
			alert("Please select at least one file.");
			return false;
		}
	}
	
	function logout()
	{
		var out="Logout.do";
		location.href=out;
	}
	
	function fileOpen(actionName)
    {
    	win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
    	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
    }
	function docFileOpen(actionName)
    {
		window.open(actionName, '_newtab');
    }
	</script>
</head>
<%Vector lockedFileDetails = (Vector)request.getAttribute("lockedFileDetails"); %>
<body>

<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>


<br />

<!-- <div align="center"><img
	src="images/Header_Images/Exit/Logout_Window.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div class="container-fluid">
<div class="col-md-12">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float: left;">Logout Window</b></div>
	<br>
<div align="left"><s:form action="UnLockFileForLogout"
	name="unlockFileForm" method="post" onsubmit="return checkChkbox();">

	<s:if test="lockedFileDetails.size == 0">
		<table width="100%" align="center">
			<tr align="center">
				<td><b><font color="navy">You are currently Not
				Working on Any Files.</font> </b><font color="navy">[You can view files
				in this space only if you have locked them for current use]</font></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="center"><input type="button" class="button"
					value="Logout" onclick="logout();" /></td>
			</tr>

		</table>

	</s:if>
	<s:else>

		<s:if test="lockedFileDetails.size > 15">
			<table align="center" width="100%">
				<tr align="center">
					<td align="center"><input type="button" name="selectAllButton"
						class="button checkAll" value="Select All"
						onclick="return selectAll();" /> &nbsp;&nbsp; <s:submit
						name="btnUnlock" cssClass="button" value="Check-In" />
					&nbsp;&nbsp; <!--	<s:submit name="btnUnlock" cssClass="button" value ="Unlock Without Saving" />
				&nbsp;&nbsp;
		--> <input type="button" class="button" value="Logout"
						onclick="logout();" /></td>
				</tr>
			</table>

		</s:if>
		<br>
		<%int srNo = 1; %>
		<div align="center">
		<table class="datatable datatable paddingtable audittrailtable" align="center" width="98%">
			<thead>
				<tr>
					<th>#</th>
					<th>Select</th>
					<th>Project Type</th>
					<th>Project</th>
					<th>Client</th>
					<th>Location</th>
					<th>Document</th>
					<th>Last Accessed</th>
				<s:if test ="#session.countryCode != 'IND'">
					<th>Eastern Standard Time</th>
				</s:if>
					<th>Modified by</th>


				</tr>
			</thead>
			<tbody>
				<s:iterator value="lockedFileDetails" status="status">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td><%=srNo++%></td>
						<td><input type="checkbox"
							value="<s:property value="workSpaceId"/>_<s:property value="nodeId"/>_<s:property value="tranNo"/>" name="selectedTranNo" class="chkbox" /></td>
						<td><s:property value="projectName" /></td>
						<td><s:property value="workSpaceDesc" /></td>
						<td><s:property value="clientName" /></td>
						<td><s:property value="locationName" /></td>
						<td><s:if test="fileName.equalsIgnoreCase('No File')">
							<s:property value="fileName" />
						</s:if> <s:else>
							<s:if test="fileExt=='PDF' || fileExt=='pdf'">
							<a href="#"
								onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
							<s:property value="fileName" /> </a>
							</s:if>
							<s:else>
								<a href="#"
								onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
							<s:property value="fileName" /> </a>
							</s:else>
						</s:else></td>
						<!--  <td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>-->
						 <td><s:property value="ISTDateTime" /></td>  
					<s:if test ="#session.countryCode != 'IND'">
						<td><s:property value="ESTDateTime" /></td>
					</s:if>
						<td><s:property value="userName" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</div>
		<br>
		<input type="hidden" value="<%=srNo - 1%>" name="check"></input>

		<table align="center" width="100%">
			<tr align="center">
				<td><input type="button" name="selectAllButton"
					class="button checkAll" value="Select All"
					onclick="return selectAll();" /> &nbsp;&nbsp; <s:submit
					name="btnUnlock" cssClass="button" value="Check-In" />
				&nbsp;&nbsp; <!--	<s:submit name="btnUnlock" cssClass="button" value ="Unlock Without Saving" />
				&nbsp;&nbsp;
		--> <input type="button" class="button" value="Logout"
					onclick="logout();" /></td>
			</tr>
		</table>

	</s:else>
</s:form></div>
</div>
</div>

</div>
</div>
</div>

</body>
</html>
