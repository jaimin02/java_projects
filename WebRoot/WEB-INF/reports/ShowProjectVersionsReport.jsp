<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>




<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->
<div id="showActivityVersionReportDetail"><br>
<table>
	<tr>
		<td class="title">&nbsp;&nbsp; Activity Name&nbsp;</td>
		<td><s:select list="getUserDtl" name="nodeId" headerKey="-1"
			headerValue="Select Activity Name" listKey="nodeId"
			listValue="nodeDisplayName">

		</s:select></td>
	</tr>

	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td align="left"><s:submit value="View" cssClass="button"></s:submit>
		</td>
	</tr>
</table>
</div>



