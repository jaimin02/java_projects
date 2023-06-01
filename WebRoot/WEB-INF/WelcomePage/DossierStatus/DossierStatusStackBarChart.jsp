<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery/charts/highcharts.src.js"></script>
</head>
<body>
<s:if test="NoAttr.equalsIgnoreCase('No')">
	<table width="100%">
		<tr>
			<td style="color: #3E576F">&nbsp;Region</td>
			<td style="color: #3E576F">&nbsp;Product</td>
			<td style="color: #3E576F">&nbsp;Dossier Status</td>
		</tr>
		<tr>
			<td><SELECT id="locationCode" name="locationCode"
				onchange="changeGraph()" style="width: 150px;">
				<option value="-1">All</option>
				<s:iterator value="getAllRegion">
					<option value="<s:property value="locationCode"/>"
						title="<s:property value="locationName"/>"
						id="<s:property value="locationCode"/>"><s:property
						value="locationName" /></option>
				</s:iterator>
			</SELECT></td>
			<td><SELECT id="ProductattrValue" name="ProductattrValue"
				onchange="changeGraph()" style="width: 150px;">
				<option value="-1">All</option>
				<s:iterator value="getAllProduct">
					<option value="<s:property value="attrValueId"/>"
						title="<s:property value="attrMatrixValue"/>"
						id="<s:property value="attrValueId"/>"><s:property
						value="attrMatrixValue" /></option>
				</s:iterator>
			</SELECT></td>
			<td><SELECT id="DossierStatusattrValue"
				name="DossierStatusattrValue" onchange="changeGraph()"
				style="width: 150px;">
				<option value="-1">All</option>
				<s:iterator value="getAllDossierStatus">
					<option value="<s:property value="attrValueId"/>"
						title="<s:property value="attrMatrixValue"/>"
						id="<s:property value="attrValueId"/>"><s:property
						value="attrMatrixValue" /></option>
				</s:iterator>
			</SELECT></td>
		</tr>
		<tr align="right">
			<td></td>
			<td></td>
			<td>
			<table>
				<tr>
					<td style="padding-right: 5px; color: #3E576F;">Top</td>
					<td style="padding-right: 5px;"><input type="text"
						id="topMostValue" size="1" disabled></td>
					<td style="padding-right: 30px;"><img title="Refresh"
						alt="Refresh" src="images/Common/refresh.png"
						onclick="changeGraph()"></td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td colspan="3"><s:url action="DossierStatusTab_ex"
				id="subqueryUrl">
				<s:param name="allSelected" value="'yes'"></s:param>
			</s:url> <s:div id="stackbarcontainer" autoStart="true" theme="ajax"
				executeScripts="true" href="%{subqueryUrl}" separateScripts="true"
				cssStyle="align:center;widht:550px;margin:0 auto"></s:div>
			<div id="piechartdatacontainer" align="center"
				style="width: 550px; height: 400px; margin: 0 auto; display: none;"></div>
			<div id="imageloader" align="center"
				style="width: 550px; height: 400px; margin: 0 auto; display: none;"></div>
			</td>
		</tr>
	</table>
</s:if>
<s:else>
	<div align="center"
		style="width: 550px; height: 300px; margin: 0 auto;">
	<table width="100%" height="100%">
		<tr>
			<td width="40%" align="right"><img alt="No Data Found"
				src="images/ectd/warning.gif" /></td>
			<td width="60%" align="left" style="color: #3E576F">&nbsp;&nbsp;No
			Data Found</td>
		</tr>
	</table>
	</div>
</s:else>
</body>
</html>
