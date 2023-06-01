<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<s:head theme="ajax" />
<script language="javascript">
		function authenticate(searchName)
		{	
			
			var okCancel = confirm("Do you really want to delete '" + searchName + "' ?");
			if(okCancel == true)
				return  true;
			else
				return false;
		}
</script>
<STYLE>
.cstyle {
	cursor: hand;
}

.initial {
	background-color: #DDDDDD;
	color: #000000
}

.normal {
	background-color: #F2F2F2
}

.highlight {
	background-color: #CBCBE4
}
</style>
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<br />
<div align="center"><img
	src="images/Header_Images/Search/Saved_Search.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="hotSearchResultsForm"
	method="post">
	<s:if test="getSavedSearch.size == 0">
		<br>
		<br>
		<br>
		<center>
		<table style="border: 1 solid black" width="100%" bgcolor="silver">
			<tr>
				<td width="10%" align="right"><img
					src="<%=request.getContextPath()%>/images/stop_round.gif"> <br>
				</td>
				<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
				No records found<br>
				&nbsp;</b></font> <br>
				</td>
			</tr>
		</table>
		</center>

	</s:if>
	<s:else>

		<br>
		<div align="center">
		<div
			style="height: 187px; overflow: auto; border: 1px solid #5A8AA9; width: 96%;">
		<table align="center" width="100%" class="datatable">
			<thead>
				<tr>
					<th>#</th>
					<th>Search Name</th>
					<th>Location</th>
					<th>Client</th>
					<th>Project Type</th>
					<th>Project</th>
					<th>User</th>
					<th>View</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="getSavedSearch" status="status">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td>${status.count}</td>
						<td><s:property value="hotSearchDesc" /></td>

						<td><s:property value="locationName" /></td>
						<td><s:property value="clientName" /></td>
						<td><s:property value="projectName" /></td>
						<td><s:property value="workspaceDesc" /></td>
						<td><s:property value="userName" /></td>

						<td>
						<div align="center"><s:url action="HighLeverSearchResult"
							id="SearchResultURL">
							<s:param name="locationCode" value="%{locationId}"></s:param>
							<s:param name="clientCode" value="%{clientId}"></s:param>
							<s:param name="projectCode" value="%{projectId}"></s:param>
							<s:param name="workSpaceId" value="%{workspaceId}"></s:param>
							<s:param name="userCode" value="%{lastModifiedBy}"></s:param>
						</s:url> <s:a title="View" href="%{SearchResultURL}" theme="ajax"
							targets="ShowHighLevelSearchResult">
							<img border="0px" alt="View" src="images/Common/view.png"
								height="18px" width="18px">
						</s:a></div>
						</td>


						<td>
						<div align="center"><a title="Delete"
							href="DeleteHotSearch.do?hotSearchId=<s:property value="hotSearchId" />"
							onclick="return authenticate('<s:property value="hotSearchDesc"/>');">
						<img border="0px" alt="Delete" src="images/Common/delete.png"
							height="18px" width="18px"> </a></div>
						</td>


					</tr>



				</s:iterator>

			</tbody>
		</table>
		</div>

		<div align="center" id="ShowHighLevelSearchResult"
			style="margin-top: 10px;"></div>
		<br>
		</div>
	</s:else>
</s:form></div>
</div>
</div>
</body>
</html>
