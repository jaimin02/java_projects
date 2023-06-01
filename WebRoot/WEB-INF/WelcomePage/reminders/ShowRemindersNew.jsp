<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.docmgmt.dto.DTOReminder"%>
<html>
<head>
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#igUpto").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});	
});
</script>
</head>
<body>
<div style="width: 100%; padding-top: 5px; padding-bottom: 5px;">
<div align="left" style="padding-bottom: 2px;">
<table width="100%" align="left" style="padding-bottom: 2px;"
	border="0px;">
	<tr>
		<td align="left" width="30%" style="padding-left: 10px;"><s:if
			test='showWhat.equals("2") || showWhat==2'>
			<img border="0px" title="Active Reminders" alt="Active Reminders"
				src="images/Common/activeRem.png" height="18px" width="18px"
				onclick="showReminders('rDiv',2);" class='tActive'
				style="cursor: pointer;">
			<img border="0px" title="Ignored Reminders" alt="Ignored Reminders"
				src="images/Common/ignore.png" height="18px" width="18px"
				onclick="showReminders('rDiv',3);" class='tIgnore'
				style="opacity: 0.4; filter: alpha(opacity =             40); cursor: pointer;">
			<img border="0px" title="Done Reminders" alt="Done Reminders"
				src="images/Common/done.png" height="18px" width="18px"
				onclick="showReminders('rDiv',4);" class='tDone'
				style="opacity: 0.4; filter: alpha(opacity =             40); cursor: pointer;">
		</s:if> <s:elseif test="showWhat.equals('3') || showWhat==3">
			<img border="0px" title="Active Reminders" alt="Active Reminders"
				src="images/Common/activeRem.png" height="18px" width="18px"
				onclick="showReminders('rDiv',2);" class='tActive'
				style="opacity: 0.4; filter: alpha(opacity =             40); cursor: pointer;">
			<img border="0px" title="Ignored Reminders" alt="Ignored Reminders"
				src="images/Common/ignore.png" height="18px" width="18px"
				onclick="showReminders('rDiv',3);" class='tIgnore'
				style="cursor: pointer;">
			<img border="0px" title="Done Reminders" alt="Done Reminders"
				src="images/Common/done.png" height="18px" width="18px"
				onclick="showReminders('rDiv',4);" class='tDone'
				style="opacity: 0.4; filter: alpha(opacity =             40); cursor: pointer;">
		</s:elseif> <s:else>
			<img border="0px" title="Active Reminders" alt="Active Reminders"
				src="images/Common/activeRem.png" height="18px" width="18px"
				onclick="showReminders('rDiv',2);" class='tActive'
				style="opacity: 0.4; filter: alpha(opacity =             40); cursor: pointer;">
			<img border="0px" title="Ignored Reminders" alt="Ignored Reminders"
				src="images/Common/ignore.png" height="18px" width="18px"
				onclick="showReminders('rDiv',3);" class='tIgnore'
				style="opacity: 0.4; filter: alpha(opacity =             40); cursor: pointer;">
			<img border="0px" title="Done Reminders" alt="Done Reminders"
				src="images/Common/done.png" height="18px" width="18px"
				onclick="showReminders('rDiv',4);" class='tDone'
				style="cursor: pointer;">
		</s:else></td>
		<td align="center" width="25%"
			style="font-family: vardana; font-size: 12px; font-weight: bold; color: #5B89AA;"
			valign="middle"><s:if test='showWhat.equals("2")'>Active Reminders<input
				type='hidden' name='sWat' id='sWat' value='2' />
		</s:if> <s:elseif test='showWhat.equals("3")'>Ignored Reminders<input
				type='hidden' name='sWat' id='sWat' value='3' />
		</s:elseif> <s:elseif test='showWhat.equals("4")'>Done Reminders<input
				type='hidden' name='sWat' id='sWat' value='4' />
		</s:elseif></td>
		<%
				ArrayList<DTOReminder> remMsg= (ArrayList<DTOReminder>)request.getAttribute("remMsg");
				if (remMsg!=null && remMsg.size()>0){
				%>
		<s:if test='showWhat.equals("2")'>
			<td align="right" style="padding-right: 10px; color: #5B89AA">
			Ignore Upto <input id="igUpto" type="text"></td>
		</s:if>
		<s:else>
			<td align="right" style="padding-right: 10px;">&nbsp;</td>
		</s:else>
		<%
				}
				else
				{
				%>
		<td align="right" style="padding-right: 10px;">&nbsp;</td>
		<%
				}
				%>
	</tr>
</table>
</div>
<%
	if (remMsg!=null && remMsg.size()>0) {
	int i=1;
	%>
<div
	style="width: 99.70%; border: 1px solid #175279; max-height: 230px; overflow: auto; padding: 0px;"
	align="center" id="disDataRem">
<table class="datatable" align="left" width="100%" id="contactArea"
	style="border: 0px; font-size: 5px vardana;">
	<tr>
		<th>#</th>
		<th>Event</th>
		<th>Project</th>
		<th>Node</th>
		<th>Date</th>
		<s:if test='showWhat.equals("2")'>
			<th>Done</th>
			<th>Ignore</th>
		</s:if>
		<s:elseif test='showWhat.equals("3")'>
			<th>Unignore</th>
			<th>Status</th>
		</s:elseif>
		<s:elseif test='showWhat.equals("4")'>
			<th>Status</th>
		</s:elseif>


	</tr>
	<s:iterator value="remMsg">
		<tr id="tr<%=i%>">
			<td><%=i%> <input type="hidden"
				value="<s:property value='workspaceId'/>" id="ws<%=i%>" /> <input
				type="hidden" value="<s:property value='nodeId'/>" id="nd<%=i%>" />
			<input type="hidden" value="<s:property value='attrId'/>"
				id="at<%=i%>" /> <input type="hidden"
				value="<s:property value='userCode'/>" id="us<%=i%>" /> <input
				type="hidden" value="<s:property value='attrName' escape="yes"/>"
				id="atnm<%=i%>" /> <input type="hidden"
				value="<s:property value='workspaceDesc' escape="yes"/>"
				id="wsnm<%=i%>" /> <input type="hidden"
				value="<s:property value='nodeDisplayName' escape="yes"/>"
				id="ndnm<%=i%>" /> <input type="hidden"
				value="<s:property value='attrValue' escape="yes"/>" id="atvl<%=i%>" />
			</td>
			<td title="<s:property value="attrName" />"><s:if
				test="attrName.length()>15">
				<s:property value="attrName.substring(0,12)" />...</s:if><s:else>
				<s:property value="attrName" />
			</s:else></td>
			<td title="<s:property value="workspaceDesc"/>"><s:if
				test="workspaceDesc.length()>18">
				<s:property value="workspaceDesc.substring(0,15)" />...</s:if><s:else>
				<s:property value="workspaceDesc" />
			</s:else></td>
			<td title="<s:property value="nodeDisplayName"/>"><s:if
				test="nodeDisplayName.length()>12">
				<s:property value="nodeDisplayName.substring(0,9)" />...</s:if><s:else>
				<s:property value="nodeDisplayName" />
			</s:else></td>
			<td title="<s:property value="attrValue"/>"><s:property
				value="attrValue"></s:property></td>
			<s:if test='showWhat.equals("2")'>
				<td title="Mark As Done" id="tdD<%=i%>" align="center"
					style="text-align: center;"><img height='15px' width='15px'
					src="images/loading.gif" id="imgD<%=i%>" style="display: none;" />
				<img alt="Mark As Done" title="Mark As Done"
					src="images/Common/done.png" id="butD<%=i%>"
					onclick='markReminderAsDone(<%=i%>);'>
				<div id="rsD<%=i%>"></div>
				</td>
				<td title="Mark As Ignore" id="tdI<%=i%>" align="center"
					style="text-align: center;"><img height='15px' width='15px'
					src="images/loading.gif" id="imgI<%=i%>" style="display: none;" />
				<img alt="Mark As Ignore" title="Mark As Ignore"
					src="images/Common/ignore.png" id="butI<%=i%>"
					onclick='markReminderAsIgnore(<%=i%>);'>
				<div id="rsI<%=i%>"></div>
				</td>
			</s:if>
			<s:elseif test='showWhat.equals("3")'>
				<td title="Mark As UnIgnore" id="tdI<%=i%>" align="center"
					style="text-align: center;"><img height='15px' width='15px'
					src="images/loading.gif" id="imgI<%=i%>" style="display: none;" />
				<img alt="Mark As Unignore" title="Mark As Unignore"
					src="images/Common/unignore.png" id="butI<%=i%>"
					onclick='markReminderAsUnIgnore(<%=i%>);'>
				<div id="rsI<%=i%>"></div>
				</td>
				<td align="center" style="text-align: center;"><s:if
					test="Done">
					<b>Done</b>
				</s:if> <s:else>
					<b>Ignored</b>
				</s:else></td>
			</s:elseif>
			<s:elseif test='showWhat.equals("4")'>
				<td align="center" style="text-align: center;"><b>Done</b></td>
			</s:elseif>
		</tr>
		<%i++;%>
	</s:iterator>
</table>
</div>
<%}else{%>
<div style="width: 100%; padding: 0px; overflow: hidden;" align="center">
<table
	style="border: 1px solid #175279; margin-top: 50px; overflow: hidden;"
	width="100%">
	<tr align="center">
		<td width="100%" align="center"><img src='images/ectd/info.gif'></img>
		<s:if test='showWhat.equals("2")'>&nbsp;No 'Active' Reminders to Display</s:if>
		<s:elseif test='showWhat.equals("3")'>&nbsp;No 'Ignored' Reminders to Display</s:elseif>
		<s:elseif test='showWhat.equals("4")'>&nbsp;No 'Done' Reminders to Display</s:elseif>
		<s:else>&nbsp;No Reminders to Display</s:else></td>
	</tr>
</table>
</div>
<%}%>
</div>
New
</body>
</html>