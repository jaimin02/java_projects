<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<html>
<head>
<s:head />
<style>
Table.GridOne {
	padding: 3px;
	margin: 0;
	border: 1px solid blue;
}

Table.GridOne Td {
	padding: 2px;
	border: 2px solid #ff9900;
}
</style>
<script>
   
	function hideColumn(id)
	{
		$("#"+id).css('color','red');
		
		 $('.datatable td:nth-child('+(id)+')').toggle();
		 $('.datatable th:nth-child('+(id)+')').toggle();
	}
    </script>
</head>
<body>

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<div style="width: 950px; padding-top: 0px; margin-top: 0px;"
	align="center">

<div class="titlediv">QA Matrix</div>
<br>
<hr width="95%">
<br>

<div style="width: 100%;" align="right"><img alt="Print"
	title="Print" id="dontprint" src="images/Common/Print.png"
	onclick="printPage();">&nbsp; <img alt="Back" title="Back"
	src="images/Common/Back.png" onclick="temp();">&nbsp;&nbsp;</div>
<br>
<div align="center"
	style="width: 100%; border: 1px solid #5A8AA9; overflow-x: scroll; padding: 20px;">

<%int srNo = 1; %>
<table style="position: fixed;">
	<tr>
		<%
   		for(int i=2;i<=21;i++)
   		{
   			
   			%>
		<td style="padding-left: 30px;"><input id="<%=i %>"
			type="checkbox" onclick="hideColumn(this.id);" checked />Node-<%=i-1 %></td>
		<%
   			if((i-1)%7==0)
   			{
   				%>
	</tr>
	<tr>
		<%
   			}
   		}
   %>
	</tr>
</table>


<table class="datatable" style="margin-top: 100px;">
	<tr>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Project
		Name</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
		Node-1</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-2</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-3</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-4</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-5</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-6</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-7</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-8</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-9</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-10</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-11</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-12</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-13</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-14</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-15</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-16</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-17</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
		Node-18</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-19</th>
		<th
			style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Node-20</th>
	</tr>

	<tr style="text-align: center; padding-left: 15px;">
		<td>Project-1</td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>

		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>


	</tr>
	<tr style="text-align: center">
		<td>Project-2</td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>
		<td><img src="images/DeleteRed.png" width="20px" height="20px" /></td>
		<td><img src="images/fileapproved.png" width="20px" height="20px" /></td>


	</tr>

</table>

</div>
<br>
<br>
</div>

</body>
</html>
