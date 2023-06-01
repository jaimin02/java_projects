<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<s:head />
<script language="javascript">

	function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none'
			else
				document.getElementById(str).style.display = 'inline'
		}	
		
</script>
<script language="javascript">
	function validate()
	{
		var desc = document.hotSearchResultsForm.hotSearchDesc.value;
	 	if(desc=="")
		{
			alert("Please specify search name.." );
			document.hotSearchResultsForm.hotSearchDesc.style.backgroundColor="#FFE6F7"; 
     		document.hotSearchResultsForm.hotSearchDesc.focus();
     		return false;
     	}
     	if(desc.length>255)
		{
			alert("Save search cannot be of more then 255 charactars" );
			document.hotSearchResultsForm.hotSearchDesc.style.backgroundColor="#FFE6F7"; 
     		document.hotSearchResultsForm.hotSearchDesc.focus();
     		return false;
     	}
     	
     	
     	return true;
     }
     
     function viewSavedSearch()
     {
     	    var viewSavedSearch = "ShowSavedSearch.do";
			document.hotSearchResultsForm.action=viewSavedSearch;
			document.hotSearchResultsForm.submit();
			return true;
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
	src="images/Header_Images/Search/Criteria_Search.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="SaveHighLevelSearch"
	name="hotSearchResultsForm" method="post">
	<br>

	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project</td>
			<td align="left" width="85%"><s:select list="userWorkspace"
				name="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="ALL" headerValue="----ALL-----">
			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><s:select list="locationForSearch"
				name="locationCode" listKey="locationId" listValue="locationName">
			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left" width="85%"><s:select list="clientForSearch"
				name="clientCode" listKey="clientId" listValue="clientName">
			</s:select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left" width="85%"><s:select list="projectForSearch"
				name="projectCode" listKey="projectId" listValue="projectName">
			</s:select></td>
		</tr>


		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project User</td>
			<td align="left" width="85%"><s:select
				list="userDetailsByUserGrp" name="userCode" listKey="userCode"
				listValue="userName">
			</s:select></td>
		</tr>

		<tr>
			<td></td>
			<td align="left"><input type="button" value="Perform Search"
				class="button" /> <ajax:event
				ajaxRef="showHighLevelResult/getHighLevelResult" /></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1px">
			<hr color="#5A8AA9" size="3px" noshade="noshade"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Save Search</td>
			<td align="left"><s:textfield name="hotSearchDesc"></s:textfield>&nbsp;
			<s:submit onclick="return validate();" value="Save Search"
				cssClass="button">
			</s:submit>&nbsp;&nbsp; <input type="button" value="View Saved Search"
				onclick="return viewSavedSearch();" class="button" /></td>
		</tr>





	</table>
	<br>
	<div id="ShowHighLevelSearchResult"></div>
	<ajax:enable />
</s:form></div>
</div>
</div>

</body>
</html>
