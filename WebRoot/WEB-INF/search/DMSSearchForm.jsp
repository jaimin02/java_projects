<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<s:head theme="ajax" />
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

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/popcalendar.js"></SCRIPT>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /> <s:actionmessage /></div>

<div class="titlediv">Search</div>
<div align="center"><s:form action="ShowDMSSearchResult"
	name="DMSSearchForm" method="post">
	<br>

	<table align="center">

		<!-- <tr>
  			<td  class="title">Project:</td>
	  			<td>
	  				<s:autocompleter
	  	 			list="userWorkspace"
	  				name="workSpace"
	  				listKey="workSpaceId"
	  				listValue="workSpaceDesc"
	  				dropdownWidth="270"
	  				cssStyle="width: 250px;"
	  				dropdownHeight="150">
	  				</s:autocompleter>		  						
	  			</td>
  		</tr> -->

		<tr>
			<td class="title">Procedure Type:</td>
			<td><s:select list="procedureTypes" name="procedureType"
				listKey="attrMatrixValue" listValue="attrMatrixValue" headerKey=""
				headerValue="Select Type">
			</s:select></td>
		</tr>

		<tr>
			<td class="title">Product Name:</td>
			<td><s:textfield name="productName"
				cssStyle="padding-left: 3px;"></s:textfield></td>
		</tr>
		<tr>
			<td class="title">Drug Name:</td>
			<td><s:textfield name="drugName" cssStyle="padding-left: 3px;"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title">Region:</td>
			<td><s:select list="allDMSRegions" name="region" headerKey=""
				headerValue="Select Region" listKey="countryCode"
				listValue="countryName+'('+countryCode+')'">
			</s:select></td>
		</tr>


		<tr>
			<td class="title">License No:</td>
			<td><s:textfield name="licenseNo" cssStyle="padding-left: 3px;"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title">MA No:</td>
			<td><s:textfield name="MA_No" cssStyle="padding-left: 3px;"></s:textfield>
			</td>
		</tr>

		<tr>
			<td class="title">Procedure No:</td>
			<td><s:textfield name="procedureNo"
				cssStyle="padding-left: 3px;"></s:textfield></td>
		</tr>

		<tr>
			<td class="title">Date of Renewal:&nbsp;</td>
			<td><select name="afterOrBefore">
				<option value="After">After</option>
				<option value="Before">Before</option>
			</select> &nbsp;<input type="text" name="renewalDate" id="renewalDate"
				ReadOnly="readonly" size="12" value="" style="padding-left: 3px;">
			&nbsp;<IMG onclick="popUpCalendar(this,renewalDate,'yyyy/mm/dd');"
				src="<%=request.getContextPath() %>/images/Calendar.png"
				align="middle"></td>
		</tr>
		<tr>
			<td></td>
		</tr>

	</table>

	<s:submit value=" Search " cssClass="button" theme="ajax"
		targets="DMSSearchResultDiv" align="center">
	</s:submit>
	<br>
	<div id="DMSSearchResultDiv"></div>

</s:form></div>
</body>
</html>
