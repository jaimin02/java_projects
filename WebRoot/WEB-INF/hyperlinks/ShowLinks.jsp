<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>

<SCRIPT LANGUAGE="JavaScript">


</SCRIPT>

</head>
<body>


<!-- <div style="width: 100%; height: 265px; overflow-y: scroll;"> -->
<div class="titlediv" align="center">List of <s:if
	test="getBrokenLinks == true">Broken </s:if>Links</div>
<br>
<div align="center"
	style="width: 100%; max-height: 230px; overflow-y: auto;">

<table class="datatable" width="100%">
	<thead>
		<tr>
			<th>#</th>
			<th>Page#</th>
			<th>Link#</th>
			<th>Link Reference Path</th>
		</tr>
	</thead>
	<tbody>
		<%int linkno = 0; %>
		<s:set name="pageno" value="" />
		<s:iterator value="links" id="linksList" status="status">
			<s:if test="#pageno != <s:property value='top[0]'/>">
				<s:set name="pageno" value="<s:property value='top[0]'/>" />
				<% linkno = 1; %>
			</s:if>
			<s:else>
				<% linkno++; %>
			</s:else>
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

				<td>${status.count}</td>
				<td>Page:<s:property value="top[0]" /></td>


				<td><a
					<s:if test="getBrokenLinks == false">onclick="setSelectedLink('<s:property value="top[0]"/>','<s:property value="top[4]"/>','Link <%= linkno %>');"</s:if>
					title="<s:property value="top[1]"/>"> Link:<%= linkno %> </a></td>
				<td><s:property value="top[1].replaceAll('/','/ ')" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

</div>



</body>
</html>

