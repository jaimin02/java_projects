<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<style type="text/css">
/* #usertable_filter input{
background-color: #2e7eb9;
color:#ffffff;
}
#usertable_length select {
background-color: #2e7eb9;
color:#ffffff;
} */
</style>


<script type="text/javascript">
$(document).ready(function() { 
 $('#usertable').dataTable( {
		
		"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
		"bJQueryUI": true,
		"sPaginationType": "full_numbers"
		
	 }); 
});
</script>
<%-- <s:head /> --%>
</head>
<body>
<div class="container-fluid">
<div class="col-md-12">
<div class="datatablePadding" style="padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="center" id="list"><s:if
	test="workspacesummrydtl.size == 0">
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"> <br>
			</td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No details available.<br>
			&nbsp;</b></font> <br>
			</td>
		</tr>
	</table>
	</center>
</s:if> <s:else>
	<form name="multipleProjects">
	<%int srno=1; %>

	<table align="center" width="100%" class="datatable paddingtable" id="usertable">
		<thead>
			<tr>
				<th style="border: 1px solid black;">#</th>
				<th style="border: 1px solid black;">Project</th>
				<th style="border: 1px solid black;">Project Type</th>
				<!-- <th>Region</th> -->
				<th style="border: 1px solid black;">Department</th>
				<th style="border: 1px solid black;">Last Accessed</th>
			<s:if test ="#session.countryCode != 'IND'">
				<th style="border: 1px solid black;">Eastern Standard Time</th>
			</s:if>
				<th style="border: 1px solid black;">Action</th>
			<s:if test="alloweTMFCustomization!='yes'">
				<th style="border: 1px solid black;"><input type="checkbox" name="sAll" onclick="selAll();"></th>
			</s:if>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="workspacesummrydtl" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td style="border: 1px solid black;"><%=srno%></td>
					<td style="border: 1px solid black;"><s:property value="workSpaceDesc" /></td>
					<td style="border: 1px solid black;"><s:property value="projectName" /></td>
					<%-- <td><s:property value="locationName" /></td> --%>
					<td style="border: 1px solid black;"><s:property value="deptName" /></td>
					<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy  HH:MM" /></td> -->
					<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
				<s:if test ="#session.countryCode != 'IND'">
					<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
				</s:if>
					<td style="border: 1px solid black;"><s:if test="statusIndi != 'D'">
						<div align="center"><a title="Assign Users"
							href="AddUserToProjectForm.do?workspaceid=<s:property value="workSpaceId"/>">
						<img border="0px" alt="Assign Users" src="images/Common/assign_rights.png"
							height="25px" width="25px"> </a></div>
					</s:if> <s:else>
						<div align="center"><a title="Assign Users"> <img border="0px"
							alt="Assign Users" src="images/Common/assign_rights.png" height="25px" width="25px">
						</a></div>
					</s:else></td>
					<s:if test="alloweTMFCustomization!='yes'">
					<td style="border: 1px solid black;"><input type="checkbox" name="project"
						id="project<%=srno%>" value="<s:property value="workSpaceId"/>"></input>
					<input type="hidden" id="wsdesc<%=srno-1%>"
						value="<s:property value="workSpaceDesc"/>"></input></td>
						</s:if>
				</tr>
				<%srno++;%>
			</s:iterator>
		</tbody>

	</table>
	<s:if test="alloweTMFCustomization!='yes'">
		<div align="center"
			style="position: fixed; right: 5%; bottom: 5px; width: 90%; height: 25px; background: #5B89AA; font-color: black; opacity: 0.3; filter: alpha(opacity =             30); z-index: 9999;">&nbsp;</div>
		
		<div
			style="position: fixed; right: 5%; bottom: 5px; height: 25px; background: transparent; color: black; z-index: 10000;">Click
		<input type="button" class="button" onclick="chkPrjChks();" value="Add">
		to attach user(s) on multiple projects.</div>
	</s:if>


	</form>
</s:else></div>
<div align="center"><img src="images/loading.gif" id="imgLoading"
	alt="loading ..." style="display: none;" align="middle"></div>
<div align="center" id="mainTab" style="display: none;">
<table style="border: 1px solid;" width="95%" height="450px;">
	<tr>
		<th></th>
		<th><label class="title">User Type</label> <select name="usrType"
			onchange="getUsrs();" id="usrType" style="min-width: 0px;">
			<option value="none">Select Type</option>
			<s:iterator value="userTypes">
				<option value="<s:property value="userTypeCode"/>"><s:property
					value="userTypeName" /></option>
			</s:iterator>
		</select> <label class="title">Number Of Columns </label> <select id="no"
			onchange="getUsrs();" style="min-width: 0px; width: 50px;">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3" selected="selected">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
		</select></th>
		<th align="right"><input type="button" value="Go Back!"
			onclick="tgldiv2();" class="button"></th>
	</tr>
	<tr height="2px" align="center">
		<td colspan="3" height="1">
		<hr color="#5A8AA9" size="3px"
			style="width: 100%; border-bottom: 2px solid #CDDBE4;">
		</td>
	</tr>
	<tr>
		<td valign="top" width="30%" align="center"><label class="title">Selected
		Projects</label>
		<div id="prjList" align="left"
			style="height: 450px; overflow: auto; border: 1px solid black; width: 200px;"></div>
		</td>
		<td valign="top" width="70%" colspan="2">
		<div id="grid" align="center" style="overflow: auto;">
		<div id="usrGrpList"></div>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
</div>
</div>
</body>
</html>
