<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<script type="text/javascript">

	function selectAll()
		{
		
			var select=document.templateAssignRightsForm.multiCheckUser.options;
			for(i=0;i<select.length;i++) {
			select[i].setAttribute('selected','1');
			
		}
		}
		function deselectAll()
		{
			var select=document.templateAssignRightsForm.multiCheckUser.options;
			for(i=0;i<select.length;i++) {
			select[i].setAttribute('selected','');
		}
		}
		
	function validate()
	{
		if(document.templateAssignRightsForm.stageId.value == -1)
		{
			alert("Please select Stages");
			document.templateAssignRightsForm.stageId.style.backgroundColor="#FFE6F7"; 
		   	document.templateAssignRightsForm.stageId.focus();
			return false;
		}
		else
		window.close();
	
		return true;
	}
		

		
</script>

</head>

<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div class="titlediv"></div>

<div align="center"><br>
<s:form action="TemplateRights" method="post"
	name="templateAssignRightsForm">
	<table width="95%" align="center">
		<table align="center" width="95%" style="border: 1 solid black">
			<tr>
				<td>
				<div class="headercls" align="center">Select User For Assign
				Rights</div>
				</td>
			</tr>

			<tr>
				<td><s:select list="assignTemplateRightsDetails"
					multiple="true" name="multiCheckUser" cssStyle="width: 95%"
					size="5%" listKey="userCode" listValue="loginName">

				</s:select></td>
			</tr>

			<tr>
				<td align="center"><input type="button" value="Check All"
					class="button" name="Check All" onclick="selectAll();" />
				&nbsp;&nbsp; <input type="button" name="UnCheck All" class="button"
					onclick="deselectAll();" value="UnCheck All" /></td>
			</tr>



			<tr>
				<td>
				<div class="headercls" align="center">Select Rights</div>
				</td>
			</tr>

			<tr align="left">
				<td class="title" style="padding-left: 40px;">Stages
				&nbsp;&nbsp;&nbsp; <s:select list="getStageDetail" name="stageId"
					headerKey="-1" headerValue="Select Stages" listKey="stageId"
					listValue="stageDesc">
				</s:select></td>
			</tr>


			<tr>
				<td class="title" align="left" style="padding-left: 60px;"><input
					type="radio" id="cr" name="rights" value="readonly"
					onclick="return checkBlank()" /> <label for="cr">Can Read</label>
				<font color="red">( Only document download )</font></td>
			</tr>

			<tr>
				<td class="title" align="left" style="padding-left: 60px;"><input
					type="radio" id="ce" name="rights" value="edit"
					onclick="return checkBlank()" /> <label for="ce">Can Edit
				</label><font color="red">( Document upload and download )</font></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
			</tr>

			<tr>
				<td>
				<center><!-- 	<s:submit value="Update Rights" cssClass="button" onclick="javascript:window.close();"></s:submit> -->
				<s:submit value="Update Rights" cssClass="button"
					onclick="return validate();"></s:submit></center>
				</td>
			</tr>

		</table>
	</table>
	<s:hidden name="nodeName"></s:hidden>
	<s:hidden name="nodeId"></s:hidden>

</s:form> <br>
<table align="center" width="95%" class="datatable">
	<thead>
		<tr>
			<th>SrNo</th>
			<th>User Name</th>
			<th>Documents Rights</th>
			<th>Stage Rights</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="getUserRightsDetail" id="getUserRightsDetail"
			status="status">
			<s:hidden value="canEditFlag" id="canEditFlag"></s:hidden>
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

				<td>${status.count}</td>
				<td><s:property value="userName" /></td>
				<td><s:if test="canEditFlag == 'Y'">Edit Rights</s:if> <s:else>Only Read Rights</s:else>
				</td>
				<td><s:property value="stageDesc" /></td>

			</tr>
		</s:iterator>
	</tbody>


</table>



</div>
</body>
</html>
