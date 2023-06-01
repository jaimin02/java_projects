<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head theme="ajax" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">
	$(document).ready(function() { 
		$("#dos").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
	});
	
</script>
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>



</head>
<body>
<div class="errordiv" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<SCRIPT type="text/javascript">

	
function detectReturnKey(evt) 
{ 
	if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
	{
		return document.saveLabel.submitBtn.onclick();
	} 
} 

document.onkeypress = detectReturnKey;

function hideDiv(divId)
{
	document.getElementById(divId).style.display = 'none';
	document.getElementById('msgDiv').style.display = '';
}
</SCRIPT>



<div align="center" class="maindiv" id="mainDiv">
<div class="titlediv">Recompile Submission</div>

<s:form action="RecompileActionForSubmission" method="post"
	name="RecompileAction">
	<div>
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>

		<tr>
			<td class="title"><b>New Date Of Submission :</b></td>
			<td><input type="text" name="dos" readonly="readonly" id="dos"
				value="${dos }"> (YYYY/MM/DD)</td>
		</tr>

		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>

	<center><s:submit name="submitBtn" value="Recompile"
		cssClass="button" theme="ajax" targets="msgDiv"
		onclick="hideDiv('mainDiv');" align="center" /></center>



	<s:hidden name="submissionPath"></s:hidden> <s:hidden
		name="workSpaceId">
	</s:hidden> <s:hidden name="submissionInfo__DtlId"></s:hidden></div>
</s:form></div>
<div id="msgDiv" class="bdycls" style="display: none;" align="center"></div>
</body>
</html>
