<html>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>

<script>

$(document).ready(function() {
		$('#popupContactClose').click(function(){
			disablePopup();
		});
	});	


</script>
<script><!--
$(document).ready(function() 
			{	

	
				var options = {	
						success: showResponse 
					};
				$("#submitBtn").click(function(){
						$("#WorkspaceApplicationDetailForm").ajaxSubmit(options);
					return false;			
				});
			});

function showResponse(responseText, statusText) 
{
	alert(responseText);
	var wsId = $("#workSpaceId").val();
	
	updateGried(wsId);
}


function removeWorkspaceDetail(applicationId,workSpaceId)
{	
	if(confirm('Are you sure?'))
	{
	$.ajax({			
		url:'RemoveWorkspaceApplication_ex.do?applicationId='+applicationId+'&workSpaceId='+workSpaceId,
		success: function(data)
  		{
  			alert(data);
  			updateGried(workSpaceId);
  		}
	  	});
	}
}

function updateDetails(applicationId)
{
	
	var sId = $('#subId'+applicationId).val();
	var seqNo=$('#seqNum'+applicationId).val();
	var appNo=$('#appNum'+applicationId).val();
	
	var wsId = $("#workSpaceId").val();		
		$.ajax({			
			url:'updateWorkspaceApplication_ex.do?applicationId='+applicationId+'&submissionId='+sId+'&sequenceNumber='+seqNo+'&applicationNumber='+appNo,
	  		success: function(data)
	  		{
	  			alert(data);
				  			
	  			updateGried(wsId);
	  		}
	  	});
}

</script>
</head>

<body>

<div style="height: 100%" align="center" id="publishedDtl">

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>
<div style="display: none;"></div>
<div align="center" 
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">Application 
Details</div>
<a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>

<hr color="#5A8AA9" size="3px"
	style="width: 350; border-bottom: 2px solid #CDDBE4;" align="left"></hr>

<br>

<s:form action="SaveWorkspaceApplication_ex" method="post" name="WorkspaceApplicationDetailForm"
	id="WorkspaceApplicationDetailForm">
	<div align="center">
	<table width="100%" align="center">
		
		
		<tr align="left">
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Submission Id</td>
			<td>
			<s:textfield  name="submissionId" size="9" id="submissionId"></s:textfield>
			</td>
		</tr>
		
		<tr>
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Sequence Number</td>
			<td align="left"><s:textfield name="sequenceNumber" id="sequenceNumber" size="20"></s:textfield>
			</td>
		</tr>
		
		<tr>
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Application Number</td>
			<td align="left"><s:textfield name="applicationNumber"
				id="applicationNumber" size="20"></s:textfield></td>
		</tr>
	
		
		<tr>
			<td></td>
			<td align="left"><input type="hidden" id="workSpaceId"
				name="workSpaceId" value="<s:property value='workSpaceId'/>">
				
				<input type="hidden" id="isMainApplication"
				name="isMainApplication" value="N">
				
				
			<s:hidden name="regionId"></s:hidden> <input type="button"
				name="submitBtn" id="submitBtn" value="Add" Class="button"
				align="center"></td>
		</tr>
		
		
	</table>
	</div>
</s:form>

<br />
	
<br>

<s:if test="workspaceApplicationDetailList.size() > 0">
	<div align="center"
		style="max-height: 175px; overflow-x: auto; border: 1px solid #0C3F62; width: 500px;">
	<table align="center" width="100%" class="datatable"
		style="border-bottom: 0px; border-top: 0px;">
		<thead>
			<tr>
				<th>#</th>
				<th>Submission Id</th>
				<th>Sequence Number</th>
				<th>Application Number</th>
				<th>Update</th>
				<th>Remove</th>


			</tr>
		</thead>
		<tbody>
			<s:iterator value="workspaceApplicationDetailList" status="status">
				<s:hidden value="statusIndi" id="statusIndi" />
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td>${status.count }</td>
					
					
					
					<td>
					<input type="text" name="submissionId" size="9"
						id="subId<s:property value="applicationId"/>"
						value="<s:property value="submissionId"/>">
					
					 </td>
				
					
					
					<td>
					<input type="text" name="submissionId" size="9"
						id="seqNum<s:property value="applicationId"/>"
						value="<s:property value="sequenceNumber"/>">
					
					 </td>
				
						<td>
					<input type="text" name="applicationNumber" size="9"
						id="appNum<s:property value="applicationId"/>"
						value="<s:property value="applicationNumber"/>">
					
					 </td>
					 
					
					
					<!-- <td><s:property value="isMainApplication" /></td> -->
					 
					
					<td>
					<div align="center"><a title="Edit"
						href="javascript:void(0);"
						onclick="updateDetails('<s:property value="applicationId"/>');">
					<img border="0px" alt="Update" src="images/Common/update.png"
						height="18px" width="18px"> </a></div>
					</td>
					
					
					<td><%-- <a href="RemoveCMS.do?workspaceCMSId=<s:property value="workspaceCMSId"/>&workSpaceId=${workSpaceId}&regionId=${regionId }" onclick="return confirm('Are you sure?');">
				       			Remove
				       		</a> --%>
					<div align="center"><a>
					<img border="0px" src="images/Common/remove.png" height="18px"
						width="18px" alt="Remove" title="Remove" 
						onclick="removeWorkspaceDetail('<s:property value="applicationId"/>','<s:property value="workSpaceId"/>');" />
					</a></div>
					</td>
				
				
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</s:if>
</div>

</body>

</html>