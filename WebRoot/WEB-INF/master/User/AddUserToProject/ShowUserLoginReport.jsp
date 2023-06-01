<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

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

<div align="center" id="list"><s:if
	test="UserRoleOperation.size == 0">
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
			No records found<br>
			&nbsp;</b></font> <br>
			</td>
		</tr>
	</table>
	</center>
</s:if> <s:else>
<table width="100%" border="0">
	<tr>
		<td align="right" width="100%">
		<form action="ExportToXls.do" method="post" id="myform"><input
			type="hidden" name="fileName" value="User_Login_Detail.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableTextArea"
			style="visibility: hidden;"></textarea> <img alt="Export To Excel"
			title="Export To Excel" src="images/Common/Excel.png"
			onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;;document.getElementById('myform').submit()">
		&nbsp;<!-- <img alt="Print" title="Print" src="images/Common/Print.png"
			onclick="return printPage();"> &nbsp; -->

		</form>
		</td>
	</tr>
</table>


	<form name="multipleProjects">
	<%int srno=1; %>
	<div id="divTabText" style="width: 98%;">
	
	 <table id="usertable" width="100%" align="center" class="report paddingtable audittrailtable">
	<thead>
		<tr>
			<!-- <th>#</th> -->
			<th style="border: 1px solid black;">User Name</th>
			<!-- <th style="border: 1px solid black;">GroupName</th> -->
			<th style="border: 1px solid black;">Profile</th>
			<th style="border: 1px solid black;">Login On</th>
		 <s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		 </s:if>
		 	<th style="border: 1px solid black;">LogOut On</th>
		 <s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		 </s:if>
		 	<!-- <th>Total Time</th> -->
			<th style="border: 1px solid black;">Login IP</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="userdtl" status="status">

			<s:if test="currentUserType != 'WA' || userType != 'SU'">
				<tr id='tr<s:property value="loginName"/>'
					class="<s:if test="statusIndi == 'D'">
	    						matchFound
	    					</s:if>
	    					<s:else>
	    						<s:if test="#status.even">even</s:if><s:else>odd</s:else>
		    					<s:if test="blockedFlag==true">blockedFound</s:if>		
	    					</s:else>">

					<%-- <td>${status.count }</td> --%>
					<td style="border: 1px solid black;"><s:property value="userName" /></td>
					<%-- <td style="border: 1px solid black;"><s:property value="userGroupName" /></td> --%>
					<td style="border: 1px solid black;"><s:property value="userType" /></td>
					<td style="border: 1px solid black;">
					 	<s:property value="ISTLogin" default="-"/>
					</td>
					<s:if test ="#session.countryCode != 'IND'">
						<td style="border: 1px solid black;"><s:property value="ESTLogin" default="-"/></td>
					</s:if>
						<td style="border: 1px solid black;"><s:property value="ISTLogOut" default="-"/></td>
					 <s:if test ="#session.countryCode != 'IND'">
						<td style="border: 1px solid black;"><s:property value="ESTLogOut" default="-"/></td>
					</s:if>
					<td style="border: 1px solid black;"><s:property value="loginIP" default="-"/></td>
				</tr>
			</s:if>
		</s:iterator>
	</tbody>
</table>
</div>
	<%-- <s:if test="alloweTMFCustomization!='yes'">
		<div align="center"
			style="position: fixed; right: 5%; bottom: 5px; width: 90%; height: 25px; background: #5B89AA; font-color: black; opacity: 0.3; filter: alpha(opacity =             30); z-index: 9999;">&nbsp;</div>
		
		<div
			style="position: fixed; right: 5%; bottom: 5px; height: 25px; background: transparent; color: black; z-index: 10000;">Click
		<input type="button" class="button" onclick="chkPrjChks();" value="Add">
		to attach user(s) on multiple projects.</div>
	</s:if> --%>


	</form>
</s:else></div>
</body>
</html>
