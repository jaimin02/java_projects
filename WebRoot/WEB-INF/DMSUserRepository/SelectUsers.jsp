<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<input type="hidden" id="authUsers" name="authUsers"
	value="<s:property value='authUsers'/>" />
<input type="hidden" id="revUsers" name="revUsers"
	value="<s:property value='revUsers'/>" />
<input type="hidden" id="approvUsers" name="approvUsers"
	value="<s:property value='approvUsers'/>" />
<input type="hidden" id="disableUserList" name="disableUserList"
	value="<s:property value="disableUserList"/>" />
<div align="center"><s:if test="usrGrps.size()>0">
	<form name="frmUsrSelectForTab" id="frmUsrSelectForTab"
		action="ShowUsers_ex"><s:set name="no" value="3"></s:set>
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
				<s:set name="outerUserCode" value="userCode"></s:set>
				<s:set name="checkedValue" value=""></s:set>
				<s:set name="disable" value=""></s:set>
				<s:if test="#outerUserCode == disableUser">

					<s:set name="disable" value='disabled="disabled"'></s:set>
				</s:if>
				<s:iterator value="selUsers">
					<s:if test="#outerUserCode == top">
						<s:iterator value="disableUser">
							<s:if test="#outerUserCode == top">
								<s:set name="disable" value='disabled="disabled"'></s:set>
							</s:if>
						</s:iterator>

						<s:set name="checkedValue" value='checked="checked"'></s:set>
					</s:if>
				</s:iterator>
				<s:if test="#stat.count!=1">
					<s:if test="#i % #no==0">
						<tr>
					</s:if>
				</s:if>
				<td width=""><s:property value="userName" /></td>
				<td><input
					id="<s:property value="userGroupCode"/>_<s:property value="#stat.count"/>"
					name="usrSelect" type="checkbox"
					value="<s:property value="userGroupCode"/>_<s:property value="userCode"/>"
					<s:property value="#disable"/> <s:property value="#checkedValue"/> /></td>
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
	</form>
</s:if></div>
<div class="submitBtnDiv" style="width: 680px;">
<table width="100%">
	<tr>
		<td align="right"><input type="button" name="addUsersBtn"
			id="addUsersBtn" value="Add Users" class="button"
			onclick="return checkData('<s:property value="mode"/>');"></td>
	</tr>
</table>
</div>
