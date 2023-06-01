<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() { 
		$("#frmDate").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		$("#toDate").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		});
	
</script>

</head>
<body>
<s:if test="usrGrps.size()>0">
	<form name="frmUsrSelect"><s:set name="no" value="no"></s:set>
	<div align="center"
		style="overflow: auto; width: 680px; height: 200px; border: 1px solid;">
	<table class="datatable" width="100%">
		<tr>
			<th width="20%">Group Name</th>
			<th></th>
			<s:iterator status="stat" value="{1,2,3,4,5,6,7,8,9,10}">
				<s:if test="top <= #no">
					<th>User Name</th>
					<th></th>
				</s:if>
			</s:iterator>
		</tr>
		<s:iterator value="usrGrps" status="status1">
			<s:set name="i" value="0"></s:set>
			<s:if test="users.size()>0">
				<tr>
					<s:if test="users.size()%#no!=0">
						<s:set name="rwsp" value="(users.size()/#no)+2" />
					</s:if>
					<s:else>
						<s:set name="rwsp" value="(users.size()/#no)+1" />
					</s:else>
					<td style="background-color: #FFFFFF; color: #000000;"
						rowspan="<s:property value="#rwsp"/>"><s:property
						value="userGroupName" /></td>
					<td style="background-color: #E3EAF0;"
						rowspan="<s:property value="#rwsp"/>"><input
						onclick="selAlUsr('<s:property value="userGroupCode"/>');"
						type="checkbox" value="<s:property value="users.size()"/>"
						id="<s:property value="userGroupCode"/>" /></td>
			</s:if>
			<s:else>
				<td><s:property value="userGroupName" /></td>
				<td></td>
				<td>None</td>
			</s:else>
			<s:iterator value="users" status="stat">
				<s:if test="#stat.count!=1">
					<s:if test="#i % #no==0">
						<tr>
					</s:if>
				</s:if>
				<td width=""><s:property value="userName" /></td>
				<td><input
					id="<s:property value="userGroupCode"/>_<s:property value="#stat.count"/>"
					name="usrSelect" type="checkbox"
					value="<s:property value="userGroupCode"/>_<s:property value="userCode"/>"></td>
				<s:if test="(#i % #no == (#no-1))">
					</tr>
				</s:if>
				<s:set name="i" value="#i+1" />
			</s:iterator>
			</tr>
			<tr style="background-color: #E3EAF0;">
				<s:iterator status="stat" value="{1,2,3,4,5,6,7,8,9,10}">
					<s:if
						test="(users.size() / #no) > 1 && top <= #no && users.size() >= top">
						<td>&nbsp;</td>
						<td><input
							onclick="selUsrColumn('<s:property value="userGroupCode"/>',<s:property value="#stat.index"/>,<s:property value="#no"/>);"
							type="checkbox" value="<s:property value="users.size()"/>"
							id="<s:property value="userGroupCode"/>-<s:property value="#stat.index"/>" /></td>
					</s:if>
				</s:iterator>
			</tr>
		</s:iterator>
	</table>
	</div>
	<br>
	<div id="usrrites">
	<table style="border: 1px solid;" width="400px">
		<!--<tr>
					<td style="text-align: right">Select User Rights</td>
					<td style="padding-left: 8px;"><input type="radio" name="rites" value="canread"  id="rites_canread" checked="checked"><label for="rites_canread">Can Read</label>
					&nbsp;<input type="radio" name="rites" value="canedit" id="rites_canedit"><label for="rites_canedit">Can Edit</label></td>					
				</tr>
				-->
		<tr>
			<td align="right" style="text-align: right; width: 30%"><label
				class="title">Select Stage</label></td>
			<td style="padding-left: 8px;" align="left"><s:iterator
				value="stages">
				<div><input type="checkbox" name="stageIds"
					id="stages_<s:property value = "stageId"/>"
					value="<s:property value = "stageId"/>"> <LABEL
					for="stages_<s:property value = "stageId"/>"><s:property
					value="StageDesc" /></LABEL></div>
			</s:iterator></td>
		</tr>

		<tr height="2px" align="center"
			style="<s:if test="usrType=='0002'">display: none;</s:if>">
			<td colspan="2" height="1">
			<hr color="#000000" size="1px"
				style="width: 100%; border-bottom: 1px solid #CDDBE4;">
			</td>
		</tr>
		<tr style="<s:if test="usrType=='0002'">display: none;</s:if>">
			<td colspan="2" style="text-align: center;"><label class="title">User
			Rights Duration</label></td>
		</tr>
		<tr style="<s:if test="usrType=='0002'">display: none;</s:if>">
			<td style="text-align: right"><label class="title">From
			Date</label></td>
			<td style="padding-left: 8px;"><input type="text" name="frmDate"
				readonly="readonly" id="frmDate"
				value="<s:property value="frmDate"/>"> (YYYY/MM/DD)</td>
		</tr>
		<tr style="<s:if test="usrType=='0002'">display: none;</s:if>">
			<td style="text-align: right"><label class="title">To
			Date</label></td>
			<td style="padding-left: 8px;"><input type="text" name="toDate"
				readonly="readonly" id="toDate" value="<s:property value="toDate"/>">
			(YYYY/MM/DD)</td>
		</tr>
	</table>
	</div>
	<br>
	<input type="button" value="Attach" onclick="attachUsers();"
		class="button"></form>
	<br>
</s:if>
<s:else>
		No Users Found
	</s:else>
</body>
</html>