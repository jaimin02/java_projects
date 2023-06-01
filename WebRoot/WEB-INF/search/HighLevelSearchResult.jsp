<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.docmgmt.dto.DTOHotsearchDetail"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<html>
<head>
<s:head />

<script language="javascript">
	function back()
	{
		location.replace("ShowSavedSearch.do");
	}
	function hide(str)
	{
		if(document.getElementById(str).style.display=="inline")
					document.getElementById(str).style.display = 'none'
		else
			document.getElementById(str).style.display = 'inline'
	}
	
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div id="ShowHighLevelSearchResult" align="center">
<% int srNo = 0; %> <% int divId = 0; %> <%
String oldws = "-1";
String oldproject = "-1";
String oldlocation = "-1";
String oldclient = "-1";
String rowClass = "-1";

%> <%Vector getSearchResult = (Vector)request.getAttribute("getSearchResult"); %>

<%if(getSearchResult.size() == 0){ %> <br>
<br>
<br>
<center>
<table style="border: 1 solid black" width="100%" bgcolor="silver">
	<tr>
		<td width="10%" align="right"><img
			src="<%=request.getContextPath()%>/images/stop_round.gif"> <br>
		</td>
		<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
		No records found for selected search critiria<br>
		&nbsp;</b></font> <br>
		</td>
	</tr>
</table>
</center>
<%}else{ %> <br>


<table width="95%">

	<tr>
		<td width="95%" align="center"><font face="verdana" size="2px"
			color="navy"><b><u>Search Result</u></b></font> <br>
		</td>
	</tr>
</table>

<br>
<%
		for(int i=0;i<getSearchResult.size();i++)
		{
			DTOHotsearchDetail dto = (DTOHotsearchDetail)getSearchResult.get(i);
	 		if(!oldws.equals(dto.getWorkspaceDesc()) || !oldproject.equals(dto.getProjectName()) || !oldlocation.equals(dto.getLocationName()) || !oldclient.equals(dto.getClientName()))  
	 		{
			 	if(srNo!=0)  {
				 	 divId++;  %>
</table>
</div>
<br>
<br>
<%} %>


<table align="center" width="100%" cellspacing="1" cellpadding="1"
	style="border-style: solid; border-width: 1; color: navy">

	<tr>
		<td width="10%" class="title" align="left">Location <br>
		</td>
		<td width="87%" class="title" align="left"><%=dto.getLocationName() %>
		</td>
		<td width="3%" align="right"><img
			src="<%=request.getContextPath()%>/images/open.gif"
			onclick="hide(<%=divId%>);"><br>
		</td>
	</tr>
	<tr>
		<td width="10%" class="title" align="left">Client <br>
		</td>
		<td width="87%" class="title" align="left"><%=dto.getClientName() %></td>
		<td width="3%" align="right">&nbsp;<br>
		</td>
	</tr>
	<tr>
		<td width="10%" class="title" align="left">Project Type <br>
		</td>
		<td width="87%" class="title" align="left"><%=dto.getProjectName() %></td>
		<td width="3%" align="right">&nbsp;<br>
		</td>
	</tr>
	<tr>
		<td width="10%" class="title" align="left">Project Name <br>
		</td>
		<td class="title" width="87%" align="left"><%=dto.getWorkspaceDesc() %></td>
		<td width="3%" align="right">&nbsp;<br>
		</td>
	</tr>

</table>

<div id="<%=divId%>" style="">
<table class="datatable" align="center" width="100%">
	<thead>
		<tr>

			<th>SrNo</th>
			<th>File Name</th>
			<th>Last Access By</th>
			<th>Last Access On</th>

		</tr>
	</thead>
	<tbody>
		<%	srNo = 1; } %>
		<% if (srNo%2==0) { %>

		<tr class="even">

			<td width="5%" valign="top"><%=srNo%></td>
			<td width="65%" valign="top"><%=dto.getFileName() %></td>
			<td width="15%" valign="top"><%=dto.getLoginName() %></td>
			<td width="15%" valign="top">
			<%
				    	SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
			         	java.util.Date Date = dto.getModifyOn();
			            String modifiedOn = sdf.format(Date);
			         %> <%=modifiedOn %></td>

		</tr>

		<% }  else { %>

		<tr class="even">

			<td width="5%" valign="top"><%=srNo%></td>
			<td width="65%" valign="top"><%=dto.getFileName() %></td>
			<td width="15%" valign="top"><%=dto.getLoginName() %></td>
			<td width="15%" valign="top">
			<%
				    	SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
			         	java.util.Date Date = dto.getModifyOn();
			            String modifiedOn = sdf.format(Date);
			       %> <%=modifiedOn %> <br>
			</td>

		</tr>

		<%}%>
		<% 
				oldws = dto.getWorkspaceDesc();
				oldproject =dto.getProjectName();
				oldlocation = dto.getLocationName();
				oldclient = dto.getClientName();
			%>

		<% srNo++;%>
	</tbody>


	<%}}%>
</table>


</div>
</body>
</html>
