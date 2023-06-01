<%@page import="java.util.ArrayList"%>
<%@page import="com.docmgmt.dto.DTOAttributeMst"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/js/jquery/cal/master.css"
	type="text/css" media="screen" charset="utf-8" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery/cal/coda.js"
	type="text/javascript"> </script>
</head>
<body>
<div align="center" style="width: 285px; padding: 1px; margin: 1px;">
<table cellspacing="1px;" width="281px;" height="25px;" border="0px;">
	<tr>
		<td width="40px" height="25px" valign="middle" align="center"
			style="padding-top: 6px; background: #9DACBE; text-align: center;"><label
			style="cursor: pointer;"
			onclick="return getMonth(<s:property  value="pmonth" />,<s:property value="pyear" />);">&lt;&lt;</label></td>
		<td colspan="5" valign="middle" width="200px" height="25px"
			align="center"
			style="padding-top: 6px; background: #9DACBE; text-align: center;">
		<select name="selMonth" id="selMonth"
			onchange="return handleSelectClick();" style="min-width: 50px;">
			<option value="0"
				<s:if test="selMonth == 0">selected="selected"</s:if>>Jan</option>
			<option value="1"
				<s:if test="selMonth == 1">selected="selected"</s:if>>Feb</option>
			<option value="2"
				<s:if test="selMonth == 2">selected="selected"</s:if>>Mar</option>
			<option value="3"
				<s:if test="selMonth == 3">selected="selected"</s:if>>Apr</option>
			<option value="4"
				<s:if test="selMonth == 4">selected="selected"</s:if>>May</option>
			<option value="5"
				<s:if test="selMonth == 5">selected="selected"</s:if>>Jun</option>
			<option value="6"
				<s:if test="selMonth == 6">selected="selected"</s:if>>Jul</option>
			<option value="7"
				<s:if test="selMonth == 7">selected="selected"</s:if>>Aug</option>
			<option value="8"
				<s:if test="selMonth == 8">selected="selected"</s:if>>Sep</option>
			<option value="9"
				<s:if test="selMonth == 9">selected="selected"</s:if>>Oct</option>
			<option value="10"
				<s:if test="selMonth == 10">selected="selected"</s:if>>Nov</option>
			<option value="11"
				<s:if test="selMonth == 11">selected="selected"</s:if>>Dec</option>
		</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select name="selYear"
			id="selYear" onchange="return handleSelectClick();"
			style="min-width: 60px;" value="<s:property value="selYear"/>">
			<%
				int nextNoOfYears=((Integer)request.getAttribute("nextNoOfYears")).intValue();
				int prevNoOfYears=((Integer)request.getAttribute("prevNoOfYears")).intValue();
				int selYear=((Integer)request.getAttribute("selYear")).intValue();
				int currYear=new GregorianCalendar().get(Calendar.YEAR);
				for (int i=currYear-prevNoOfYears;i<=(currYear+nextNoOfYears);i++){
				%>
			<option value=<%=i%> <%if(selYear==i){%> selected='selected' <%}%>><%=i%></option>
			<%	
				}
				%>
		</select></td>
		<td width="40px" height="25px" align="center" valign="middle"
			style="padding-top: 6px; background: #9DACBE; text-align: center;"><label
			style="cursor: pointer;"
			onclick="return getMonth(<s:property value="nmonth" />,<s:property  value="nyear" />);">&gt;&gt;</label></td>
	</tr>
</table>
<%
	String attrChoices=(String)request.getAttribute("attrChoices");
	String remnChoices=(String)request.getAttribute("remnChoices");	
	if (attrChoices==null)
		attrChoices="";
	if (remnChoices==null)
		remnChoices="";
	ArrayList<DTOAttributeMst> list =(ArrayList<DTOAttributeMst>)request.getAttribute("evtAttrs");
	if (list==null)
		list=new ArrayList<DTOAttributeMst>();
	int i=0;
	%> <s:property escape="false" value="calendarHTML" />
<div id="container-1" style="background: #9DACBE; width: 281px;">
Select Calendar Events <a href=# id="link1"
	onclick="toggle('detail1','link1','detail2','link2');return false;"
	style="text-decoration: none;">( + )</a> <s:form
	action="calsetevtattr_b" id="calsetevtattr_b">
	<div id="detail1" style="display: none;" align="left"><a href=#
		onclick="selectall();return false;" style="text-decoration: none;">Select
	All</a> <a href=# onclick="unselectall();return false;"
		style="text-decoration: none;">Unselect All</a><br>
	<div style="height: 45px; overflow: auto;"><s:iterator
		value="evtAttrs">
		<input type="checkbox" name="evtattr"
			title="Select Calendar Attribute"
			value="<s:property value="attrId" ></s:property>"
			<%
			if (attrChoices.contains(String.valueOf(list.get(i).getAttrId()))){
				%>
			checked="checked" <%
			}
			%> />
		<s:property value="attrName"></s:property>
		<br>
		<%i++; %>
	</s:iterator></div>
	<input type="hidden" name="attrlist" id="attrlist"></input>
	<center><input type="submit" value="Set" class="button"
		onclick="collect();"></center>
	</div>
</s:form></div>
<hr color="white" style="width: 270px;" align="center">
<%i=0; %>
<div id="container-2" style="background: #9DACBE; width: 281px;">
Select Reminder Events <a href=# id="link2"
	onclick="toggle('detail2','link2','detail1','link1');return false;"
	style="text-decoration: none;">( + )</a> <s:form
	action="calsetremattr_b" id="calsetremattr_b">
	<div id="detail2" style="display: none;" align="left"><a href=#
		onclick="selectall1();return false;" style="text-decoration: none;">Select
	All</a> <a href=# onclick="unselectall1();return false;"
		style="text-decoration: none;">Unselect All</a><br>
	<div style="height: 45px; overflow: auto;"><s:iterator
		value="evtAttrs">
		<input type="checkbox" id="remattr" name="remattr"
			title="Select Reminder Attribute"
			value="<s:property value="attrId" ></s:property>"
			<%
			if (remnChoices.contains(String.valueOf(list.get(i).getAttrId()))){
				%>
			checked="checked" <%
			}
			%> />
		<s:property value="attrName"></s:property>
		<br>
		<%i++; %>
	</s:iterator></div>
	<input type="hidden" name="attrRemlist" id="attrRemlist"></input>
	<center><input type="submit" value="Set" class="button"
		onclick="collect1();"></center>
	</div>
</s:form></div>
</div>
</body>
</html>