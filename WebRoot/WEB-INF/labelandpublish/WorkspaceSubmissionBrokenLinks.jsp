<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>

<head>
<SCRIPT type="text/javascript">
	function showLinks(filePath)
	{
		$.ajax
		({			
				url: 'ShowLinks.do?fileWithPath='+filePath+'&getBrokenLinks=true',
				success: function(data) 
		  		{
					$("#BrokenLinksDiv").html(data);
				}	  		
		});
	}


	function ignoreBrokenLinks(workSpaceId,subInfoDtlId)
	{
		$.ajax
		({			
				url: 'ConfirmSubmission.do?workSpaceId='+workSpaceId+'&dontChkBrknLinks=true&submissionInfo__DtlId='+subInfoDtlId,
				success: function(data) 
		  		{
					$("#ShowSubInfoDiv").html(data);
				}	  		
		});
	}
</SCRIPT>
</head>

<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /> <s:actionmessage /></div>

<br>
<br>

<s:if test="filesContainingBrokenLinks.size != 0">
	<div class="titlediv" style="width: 95%" align="center">Broken
	Links Found in Following File(s) <br>
	<div align="center">
	<div style="width: 50%; max-height: 300px; overflow-y: auto;">
	<table class="datatable" align="center" width="100%">
		<thead>
			<tr>
				<th width="5%">#</th>
				<th width="55%">File Name</th>
				<th width="40%">View</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="filesContainingBrokenLinks" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

					<td width="5%">${status.count}</td>
					<td width="55%"><a href="#"
						onclick="openFileWithPath('<s:property value="top[2]"/>');"
						title="<s:property value="top[1]"/>"> <s:property
						value="top[0]" /> </a></td>
					<td width="40%"><img border="0px" alt="View"
						src="images/Common/view.png" height="18px" width="18px"
						onclick="showLinks('<s:property value="top[2]"/>')"></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
	</div>
	</div>
	<br />
	<div id="BrokenLinksDiv" style="width: 75%;"></div>
</s:if>
<br>
<s:if test="isConfirmLink == true">
	<input type="button" class="button" value="Ignore And Confirm"
		onclick="return ignoreBrokenLinks('<s:property value="workSpaceId"/>','<s:property value="submissionInfo__DtlId"/>');">
</s:if>
<input type="button" class="button" value="Back" onclick="subinfo();">

</body>
</html>
