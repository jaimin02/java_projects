<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title></title>
</head>
<body>
<table class="datatable" width="100%" style="border: 0px">
	<thead>
		<tr>
			<th>No.</th>
			<th>Projects</th>
			<s:if
				test="gridThirdColumn.equalsIgnoreCase('') || gridThirdColumn==''">
			</s:if>
			<s:else>
				<th><s:property value="gridThirdColumn" /></th>
			</s:else>
		</tr>
	</thead>
	<s:iterator value="workSpaceDetail" status="status">
		<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			<td>${status.count}</td>
			<td><label title="<s:property value="workSpaceDesc"/>">
			<s:if test="workSpaceDesc.length()>20">
				<a href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>"><s:property
					value="workSpaceDesc.substring(0,20)" />...</a>
			</s:if> <s:else>
				<a href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>"><s:property
					value="workSpaceDesc" /></a>
			</s:else> </label></td>
			<s:if test="gridThirdColumn.equalsIgnoreCase('')">
			</s:if>
			<s:else>
				<td><s:property value="attrValue" /></td>
			</s:else>

		</tr>
	</s:iterator>
</table>
</body>
</html>
<!--
	<label title="<s:property value="workSpaceDesc"/>">
							<s:if test="workSpaceDesc.length()>20">
								<s:property value="workSpaceDesc.substring(0,20)" />...
							</s:if>
							<s:else>
								<s:property value="workSpaceDesc" />
							</s:else>
						</label> 
 -->