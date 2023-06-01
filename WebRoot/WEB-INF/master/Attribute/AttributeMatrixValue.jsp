<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	function riteclick(divid)
	{		
		var str=document.getElementById(divid).style.display;
		if (str=='block')
			document.getElementById(divid).style.display='none';
		else
			document.getElementById(divid).style.display='block';
	}
</script>

<s:head theme="ajax" />
</head>

<body>
<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>
<br />
<div align="center"><img
	src="images/Header_Images/Master/Add_Attributes.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveAttributeMatrixValue"
	method="post" name="AttrMatrixValue">


	<table width="100%">
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attribute Name</td>
			<td>${attribdetail.attrName}</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attribute Type</td>
			<td>${attribdetail.attrType}</td>

		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attribute User</td>
			<td>${attribdetail.userTypeName}</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attribute Remarks</td>
			<td>${attribdetail.remark }</td>

		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attribute value</td>
			<td><!-- this two field are passed for form submit --> <input
				type="hidden" name="attribid" value="${attribdetail.attrId}">
			<input type="text" name="attribvalue"></td>
		</tr>
	</table>
	<div align="center" style="width: 100%;"><input type="button"
		value="Save"
		onclick="document.forms['AttrMatrixValue'].savebutton.click();"
		class="button">&nbsp;&nbsp;&nbsp; <input type="button"
		class="button" value="Exit"
		onclick="window.location.href='AddAttribute.do';">

	<div style="visibility: hidden;"><s:submit value="Save"
		cssClass="button" cssStyle="visibility: hidden;" theme="ajax"
		targets="valuelist" name="savebutton"></s:submit></div>
	</div>
</s:form> <br />
<div id="valuelist" align="center"><!--<jsp:include page="AttributeMatrixValueList.jsp"></jsp:include>-->

<%int srno=1;%>
<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th width="1%">#</th>
			<th width="40%">Attribute Value</th>
			<th width="50%">Edit</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="attribComboValue">
			<tr>
				<td><%=srno++ %></td>
				<td><s:property value="attrValue" /></td>
				<td>
				<div align="center"><a title="Edit" href="javascript:void(0);"
					onclick="riteclick('<s:property value="attrValueId"/><s:property value="attrValue"/>');">
				<img border="0px" alt="Edit" src="images/Common/edit.png"
					height="18px" width="18px"> </a></div>

				<span
					id="<s:property value="attrValueId"/><s:property value="attrValue"/>"
					style="display: none; float: right;"> <s:form
					action="EditAttributeMatrixValue" method="post">
					<input type="hidden" name="iattrid"
						value="<s:property value="attrId"/>">
					<input type="hidden" name="vattrvalue"
						value="<s:property value="attrValue"/>">					
						New Value: <input type="text" name="newValue"
						value="<s:property value="attrValue"/>">
						&nbsp;&nbsp;<input type="submit" value="Save" class="button">
				</s:form> </span></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
<br>
</div>

</div>
</div>


</body>
</html>
