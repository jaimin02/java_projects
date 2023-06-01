<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.docmgmt.dto.DTOForumDtl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<%@ page import="java.util.*"%>
<html>
<head>

<script type="text/javascript">
 
function printPage()
{
		
	document.getElementById('dontprint').style.display='none';
	window.print();
}
		
	function back()
	{
		location.replace("ProjectCommentReport.do");
	}
</script>

<s:head />
</head>

<%Vector commentsOnProject =(Vector)request.getAttribute("commentsOnProject"); %>
<body>

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<%if(commentsOnProject.size()==0){ %>
<table align="right">
	<tr>
		<td><input type="button" class="button" value="Back"
			onclick="return back();" /></td>
	</tr>
</table>

<br>
<br>
<br>
<br>
<br>
<center>
<table style="border: 1 solid black" width="100%" bgcolor="silver">
	<tr>
		<td width="10%" align="right"><img
			src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
		<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
		No comments attached on selected node...<br>
		&nbsp;</b></font></td>
	</tr>
</table>
</center>
<%}else{ %>
<div id="dontprint" style="display: block;">
<table width="100%">
	<tr>

		<td align="right" width="100%"><input type="button"
			class="button" value="print" onclick="return printPage();" />
		<td>&nbsp;<input type="button" class="button" value="Back"
			onclick="return back();" /></td>
	</tr>
</table>
</div>


<div class="titlediv">File Version History</div>
<br>
<hr>
<%int srNo = 0; %>
<% String oldWorkSpaceId = "0" ;%>
<% String oldNodeId = "0" ; %>


<%
		for(int i=0;i<commentsOnProject.size();i++)
		{
			DTOForumDtl dto = (DTOForumDtl)commentsOnProject.get(i);
	 		if(!oldWorkSpaceId.equals(dto.getWorkspaceId()) )
	 		{
			 	 if(srNo!=0)  {
				 	   %></table>
<br>

<% } 
			%>

<table class="datatable" align="center" width="100%">
	<thead>
		<tr>

			<th>#</th>
			<th>Comments</th>
			<th>Sender</th>
			<th>Receiver</th>
			<th>Send Date</th>


		</tr>
	</thead>
	<tbody>

		<%	srNo = 1; } %>

		<tr class="even">

			<td><%=srNo%></td>
			<td><%=dto.getSubjectDesc() %></td>
			<td><%=dto.getSenderName() %></td>
			<td><%=dto.getReceiverName() %></td>

			<td><%=dto.getModifyOn() %></td>

		</tr>

	</tbody>
	<% oldWorkSpaceId = dto.getWorkspaceId(); %>
	<% oldNodeId = Integer.toString(dto.getNodeId()); %>
	<% srNo++ ; %>

	<%}}%>
</table>

</body>
</html>
