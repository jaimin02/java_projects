<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<script type="text/javascript">
function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
$(document).ready(function()
{
	$('#selColumns').html('No Columns to display!!!');
	$('#selColumns').hide();
	$('#attrType').change(function()
	{
		var attrType = $('#attrType').val();
		if (attrType == 'Dynamic')
		{
			$('#dynamicDiv').slideDown('normal');
			$('#selColumns').show();
		}
		else
		{
			$('#dynamicDiv').slideUp('normal');
			$('#selColumns').hide();
 		}	
	});

	$('#tableName').change(function()
	{
		$('#selColumns').html('No Columns to display!!!');
		var tbName=document.getElementById('tableName').value;
		if (tbName==null) 
			return;
		if (tbName=='-1')
		{
			return;
		}
		tbName=trim(tbName);
		$.ajax({			
			url: 'GetTableDetails_ex.do?tableName=' + tbName + '&addAttr=yes',
			beforeSend: function()
			{ 
												
			},
	  		success: function(data) 
	  		{
				$('#selColumns').html(data);
				$('#selColumns').show();												
			}										
		});
	});
});
</script>
</head>
<body>

<div class="errordiv" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Master/Add_Attributes.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveAttribute"
	id="attributeForm" method="post">

	<table width="100%">

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="attributeName">Attribute
			Name</td>
			<td align="left"><s:textfield name="attributeName"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="attributeValue">Default
			Attribute Value</td>
			<td align="left"><s:textfield name="attributeValue"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="attributeType">Input
			Type</td>
			<td align="left"><select name="attributeType" id="attrType">
				<option value="">Select Input Type</option>
				<option value="Combo">Combo Box</option>
				<option value="MultiSelectionCombo">Multi Selection Combo Box</option>
				<option value="Date">Date Picker</option>
				<option value="Text">Text Box</option>
				<option value="TextArea">Text Area</option>
				<option value="Check">Check Box</option>
				<!-- <option value="Radio">Radio Button</option>
				<option value="File">File</option> -->
				<option value="AutoCompleter">Auto Completer</option>
				<option value="Dynamic">Dynamic</option>
			</select></td>
			<td>
			<div id="dynamicDiv" style="display: none;"><select
				name="tableName" onchange="selTable();" id="tableName">
				<option value="-1">Please Select Table</option>
				<s:iterator value="xlsTableList" status="status">
					<option value="<s:property value="vTableName"/>"><s:property
						value="vTableName" /></option>
				</s:iterator>
			</select></div>
			<div id="selColumns"
				style="border: 1px solid; padding-left: 10px; width: 150px; overflow: auto;">
			</div>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="attrForIndiId">
			Attribute Type</td>
			<td align="left"><s:select list="attrForIndi"
				name="attrForIndiId" headerKey="" headerValue="Select Attribute For"
				listKey="attrForIndiId" listValue="attrForIndiName">

			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="userTypeCode">User
			Type</td>
			<td align="left"><s:select list="usertypelist"
				name="userTypeCode" headerKey="" headerValue="Select User Type"
				listKey="userTypeCode" listValue="userTypeName">

			</s:select></td>
		</tr>

		<tr valign="middle">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="userInput">User
			Input Required</td>
			<td align="left" valign="middle"><input type="radio"
				name="userInput" id="userInputY" value="Y"><label
				for="userInputY"> Yes</label> &nbsp;&nbsp;&nbsp; <input type="radio"
				name="userInput" id="userInputN" value="N"><label
				for="userInputN"> No</label></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="attributeRemark">
			Remarks</td>
			<td align="left"><s:textfield name="attributeRemark"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"></td>
			<td align="left"><s:submit value="Save" cssClass="button" /></td>
		</tr>

	</table>

</s:form> <br />
</div>

<%int srNo = 1; %>
<table id="attribtable" width="95%" align="center" class="datatable">
	<thead>
		<tr>
			<th>#</th>
			<th>AttrName</th>
			<th>Default Value</th>
			<th>Input Type</th>
			<th>Attribute Type</th>
			<th>User Type</th>
			<th>Input Required</th>
			<th>Remarks</th>
			<th>Optional Values</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>


		<s:iterator value="attributelist" id="attriblist" status="status">

			<s:hidden value="statusIndi" id="statusIndi" />

			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>

			<td><%=srNo++ %></td>
			<td><s:property value="attrName" /></td>
			<td><s:property value="attrValue" /></td>
			<td><s:property value="attrType" /></td>
			<td><s:property value="attrForIndiName" /></td>
			<td><s:property value="userTypeName" /></td>
			<td><s:property value="userInput" /></td>
			<td><s:property value="remark" /></td>
			<td><s:iterator value="attrMatrixValueVect">
				<s:property />
				<br>
			</s:iterator></td>
			<td><s:if
				test="attrType == 'Combo'  || attrType == 'MultiSelectionCombo' || attrType == 'AutoCompleter'">
				<div align="center"><a title="Edit"
					href="AddAttributeMatrixValue.do?iattrid=<s:property value="attrId"/>">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>
			</s:if></td>
			</tr>


		</s:iterator>
	</tbody>

</table>

</div>
</div>


</body>
</html>
