<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<SCRIPT type="text/javascript">

function authenticate(status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you really want to Activate selected Group ?");
		}else{
			var okCancel = confirm("Do you really want to InActivate selected Group ?");
		}
		if(okCancel == true)
			return  true;
		else
			return false;
	}

</SCRIPT>
<s:head />
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div align="center" class="maindiv"><br>
<div class="headercls">Attribute Group Values</div>
<s:if test="getAttrValues.size == 0">

	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No Attributes Are Added To Selected Group.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>

</s:if> <s:else>



	<%int srNo = 1; %>
	<br>
	<table align="center" width="95%" class="datatable">
		<thead>
			<tr>
				<th>SrNo</th>
				<th>Attribute Name</th>
				<th>Status</th>
				<th>Revert</th>


			</tr>
		</thead>
		<tbody>
			<s:iterator value="getAttrValues" id="getAttrValues" status="status">
				<s:if test="statusIndi == 'D'">
					<tr class="matchFound">
				</s:if>
				<s:else>
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				</s:else>

				<td><%=srNo++ %></td>
				<td><s:property value="attrName" /></td>
				<td><s:if test="statusIndi == 'E'">Edited</s:if> <s:elseif
					test="statusIndi == 'D'">Deleted</s:elseif> <s:else>New</s:else></td>

				<td><a
					href="DeleteAttrValues.do?Id=<s:property value="id" />&statusIndi=<s:property value="statusIndi" />"
					onclick="return authenticate('<s:property value="statusIndi" />');">

				<s:if test="statusIndi == 'E'">InActivate</s:if> <s:elseif
					test="statusIndi == 'N'">InActivate</s:elseif> <s:else>Activate</s:else>
				</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:else></div>
</body>
</html>
