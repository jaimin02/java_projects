<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
			$(document).ready(function() { 
				$(".attrValue").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			});
		</script>
</head>
<body>
<div align="center"
	style="margin-bottom: 10px; margin-top: 10px; border: 1px solid #5A8AA9;">
<table border="1px" bordercolor="#5A8AA9" width="100%" align="center"
	class="datatable" cellpadding="5px" cellspacing="2px;">
	<tr>
		<th>Attribute Name</th>
		<th>Criteria</th>
		<th>Attribute Value</th>
	</tr>
	<tbody>
		<s:iterator value="dtoAttributeMstList">
			<tr class="none">
				<td><input type="radio"
					onclick="selectedAttr('<s:property value="attrName"/>','<s:property value="attrId"/>','<s:property value="attrType"/>');"
					name="attrList" id="<s:property value="attrId"/>" /> <label
					for="<s:property value="attrId"/>"> <s:property
					value="attrName" /></label></td>
				<s:if test="attrType == 'Text'">
					<td align="center"><SELECT name="oper"
						id="Text<s:property value="attrId"/>"
						onchange="selectedOper('<s:property value="attrId"/>','Text');">
						<option value="Like" selected="selected">Like</option>
						<option value="=">=</option>
					</SELECT></td>
					<td><input type="text" name="textboxtype"
						value="<s:property value="attrValue"/>"
						id="attrSetValue<s:property value="attrId"/>"
						onkeyup="setingValue('attrSetValue<s:property value="attrId"/>','<s:property value="attrId"/>')" />
					</td>
				</s:if>
				<s:elseif test="attrType == 'Combo'">
					<td align="center"><SELECT name="oper"
						id="Combo<s:property value="attrId"/>"
						onchange="selectedOper('<s:property value="attrId"/>','Combo');">
						<option value="=" selected="selected">=</option>
					</SELECT></td>
					<td><s:select list="attrMatrixValueVect" listKey="top"
						listValue="top" headerKey="0" headerValue="Select Value"
						id="attrSetValue%{attrId}"
						onchange="selectValue('attrSetValue%{attrId}','%{attrId}');"></s:select>
					</td>
				</s:elseif>
				<s:elseif test="attrType == 'Date'">
					<td align="center"><SELECT name="oper"
						id="Date<s:property value="attrId"/>"
						onchange="selectedOper('<s:property value="attrId"/>','Date');">
						<option value="=" selected="selected">=</option>
					</SELECT></td>
					<td><input type="text" name="attrVal" class="attrValue"
						id="attrSetValue<s:property value="attrId"/>"
						onchange="setingValue('attrSetValue<s:property value="attrId"/>','<s:property value="attrId"/>')">
					(YYYY/MM/DD) &nbsp;<IMG
						onclick="
												if(document.getElementById('attrSetValue'+<s:property value="attrId"/>).value != '' && confirm('Are you sure?'))
					   								document.getElementById('attrSetValue'+<s:property value="attrId"/>).value = '';"
						src="<%=request.getContextPath() %>/images/clear.jpg"
						align="middle" title="Clear Date"></td>
				</s:elseif>
				<s:elseif test="attrType == 'TextArea'">
					<td align="center"><SELECT name="oper"
						id="TextArea<s:property value="attrId"/>"
						onchange="selectedOper('<s:property value="attrId"/>','TextArea');">
						<option value="Like" selected="selected">Like</option>
						<option value="=">=</option>
					</SELECT></td>
					<td><textarea rows="2" cols="20"
						id="attrSetValue<s:property value="attrId"/>"
						onkeyup="setingValue('attrSetValue<s:property value="attrId"/>','<s:property value="attrId"/>')"><s:property
						value="attrValue" /></textarea></td>
				</s:elseif>
				<s:elseif test="attrType == 'AutoCompleter'">
					<td align="center"><SELECT name="oper"
						id="AutoCompleter<s:property value="attrId"/>"
						onchange="selectedOper('<s:property value="attrId"/>','TextArea');">
						<option value="=">=</option>
					</SELECT></td>
					<td><s:select list="attrMatrixValueVect" listKey="top"
						listValue="top" headerKey="0" headerValue="Select Value"
						id="attrSetValue%{attrId}"
						onchange="selectValue('attrSetValue%{attrId}','%{attrId}');"></s:select>
					</td>
				</s:elseif>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
<br>
<div id="query" align="center">
<form action="#"><input class="button" type="button"
	value="Block Start" onclick="btnBlockStart();" /> <input
	class="button" type="button" value="Block End" onclick="btnBlockEnd();" />
<input class="button" type="button" value="And" onclick="btnAnd();" />
<input class="button" type="button" value="Or" onclick="btnOr();" /> <input
	class="button" type="button" value="New Line" onclick="btnNewLine();" />
<input class="button" type="button" value="End Query"
	onclick="btnEndQuery();" /> <input class="button" type="button"
	value="Cancel" onclick="cancelString()" /> <input class="button"
	type="button" value="Submit Query" onclick="btnSubmitQuery();" /> <input
	class="button" type="hidden" id="queryStringForSend"
	name="queryStringForSend" /> <br>
<br>
<hr color="#5A8AA9" size="3px"
	style="width: 85%; border-bottom: 2px solid #CDDBE4;">
<table width="85%" align="center">
	<tr>
		<th width="15%" align="right">
		<h3>Query :&nbsp;&nbsp;&nbsp;</h3>
		</th>
		<td align="left" width="80%">
		<div id="queryStringDiv" style="text-align: left"></div>
		</td>
	</tr>
</table>
<hr color="#5A8AA9" size="3px"
	style="width: 85%; border-bottom: 2px solid #CDDBE4;">
<br>
</form>
</div>
<br>
<div id="resultDiv"></div>
</body>
</html>