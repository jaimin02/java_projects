<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="showAttributes">
<table align="center" width="100%" cellspacing="2" cellpadding="2">
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Attributes</td>
		<td align="left" width="75%"><s:select list="getAttrDetail"
			name="multiCheckAttr" multiple="true" size="10" listKey="attrId"
			listValue="attrName">
		</s:select></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><s:submit value="Add Attributes"
			cssClass="button"></s:submit>&nbsp; <input type="button"
			value="Check All" class="button" onclick="return selectAll();">&nbsp;
		<input type="button" value="Uncheck All"
			onclick="return deselectAll();" class="button"></td>
	</tr>
</table>
</div>