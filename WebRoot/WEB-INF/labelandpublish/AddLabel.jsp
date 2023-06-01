<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>


<html>
<head>
<s:head />

<SCRIPT type="text/javascript">
	
	function createNewLabel()
	{
		
		if(document.getElementById("workSpaceId").value == "-1")
		{
			alert("Please Select Project Name");
			return false;
		}
		var ddl = document.getElementsByName("workSpaceId");
		var wsid = document.getElementById("workSpaceId").value;
		//alert(ddl[0].options[ddl[0].selectedIndex].text);
		var wsdesc = ddl[0].options[ddl[0].selectedIndex].text;
		strAction = "CreateLabel.do?workSpaceId=" + wsid + "&workSpaceDesc=" +wsdesc;
   		win3=window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=200,width=500,resizable=yes,titlebar=no");
   		win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(200/2));
   		return true;
   	}
   	
   	function view(wsid,lblno)
   	{
   		strAction = "ViewLabelNodeDetail.do?workSpaceId=" + wsid + "&labelNo=" +lblno;
   		win3=window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=700,width=900,resizable=no,titlebar=no");
   		win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(700/2));
   		return true;
   	}
   	
</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->
<div class="titlediv">Add Label</div>

<div align="center"><s:form name="labelForm" method="post">
	<br>
	<table>
		<tr>
			<td class="title">&nbsp;&nbsp; Project Name :</td>
			<td><s:select list="getWorkspaceDetail" name="workSpaceId"
				listKey="workSpaceId" listValue="workSpaceDesc" headerKey="-1"
				headerValue="Select Project">
			</s:select> <ajax:event ajaxRef="Viewlabel/getViewlabel" /></td>
		</tr>

		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="button" value="Create Label"
				onclick="return createNewLabel();" class="button" /></td>
		</tr>
	</table>

	<table align="center" width="100%">
		<tr>
			<td>
			<div id="ViewLabelDiv"></div>
			</td>
		</tr>
	</table>

	<ajax:enable />
</s:form></div>
</body>
</html>

