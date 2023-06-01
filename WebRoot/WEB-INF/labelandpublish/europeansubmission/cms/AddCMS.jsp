<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script language="javascript">
	$(document).ready(function() {
		$('#popupContactClose').click(function(){
			disablePopup();
		});
	});	
	function validateCMSPage()
	{
		var trackNum = document.AddCMSForm.cmsTrackNum.value;
		if(document.AddCMSForm.selectedCountry.value=="-1")
		{
			alert("Please select a Country..");
			document.AddCMSForm.selectedCountry.style.backgroundColor="#FFE6F7"; 
     		document.AddCMSForm.selectedCountry.focus();
     		return false;
     	}
     	else if(document.AddCMSForm.selectedAgency.value=="-1")
		{
			alert("Please select an Agency..");
			document.AddCMSForm.selectedAgency.style.backgroundColor="#FFE6F7"; 
     		document.AddCMSForm.selectedAgency.focus();
     		return false;
     	}
     	else if(!document.AddCMSForm.waveNo.value.match(/^([1-9])$/))
		{
			alert("Please enter valid Wave No..");
			document.AddCMSForm.waveNo.style.backgroundColor="#FFE6F7"; 
     		document.AddCMSForm.waveNo.focus();
     		return false;
     	}
     	else if(!trackNum.match(/^([a-zA-Z0-9\/\u002D\u002C])*$/))
     	{
     		alert("Only digits, Alphabets, '-' , '/' and ',' are allowed.");
			document.AddCMSForm.cmsTrackNum.style.backgroundColor="#FFE6F7"; 
     		document.AddCMSForm.cmsTrackNum.focus();
     		return false;
     	}
     	return true;
	}
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 
	document.onkeypress = detectReturnKey;
	function getAgencies()
	{		

		var dtdVersion=document.getElementById("dtdVersion").value;
		$.ajax(
		{			
			url: 'GetCountryAgencies_ex.do?dtdVersion='+dtdVersion+'&selectedCountry=' + $('#selectedCountry').val(),
	  		success: function(data) 
	  		{
	  			var agencySelect = document.getElementById('selectedAgency');
	    		if(data.length > 0){
	    			var agencyArr = data.split(',');
	    			var options = '';
	    			for(i=0; i<agencyArr.length;i++){
	    				var keyValuePair = agencyArr[i];
	    				var keyValuePairArr = keyValuePair.split('::');
	    				var key = keyValuePairArr[0];
	    				var value = keyValuePairArr[1];
	    				options += '<option value="'+key+'">'+value+'</option>';
	    			}
	    			options = '<select name="selectedAgency">'+
	    						options +
  							  '</select>';
	    			document.getElementById('selectedAgency').innerHTML = options;
	    		}
	    	}	  		
		});		
		return false;
	}
	
	
	$(document).ready(function() 
			{	
				var options = {	
						success: showResponse 
					};
				$("#submitBtn").click(function(){
					if(validateCMSPage())					
						$("#AddCMSForm").ajaxSubmit(options);
					return false;			
				});
			});
			function showResponse(responseText, statusText) 
			{
				alert(responseText);
				var wsId = $("#workSpaceId").val();
				addCmsDtl(wsId);
			}
		
			function removeCMS(workspaceCMSId,workSpaceId,regionId)
			{
				if(confirm('Are you sure?'))
				{
					
					$.ajax({			
							url:'RemoveCMS_ex.do?workspaceCMSId='+workspaceCMSId+'&workSpaceId='+workSpaceId+'&regionId='+regionId,
					  		success: function(data)
					  		{
					  			alert(data);
					  			addCmsDtl(workSpaceId);
					  		},
					  		error: function(data)
					  		{
					  			alert("Error in data:::"+data);
					  			
					  		}
					  	});
				}
				
			}
			function updateCMSTrackNo(workspaceCMSId)
			{
			
				var trackNo = $('#trackCMS'+workspaceCMSId).val();
				//var inventName=$('#invented_Name'+workspaceCMSId).val();
				var inventName="";
				if(!trackNo.match(/^([a-zA-Z0-9\/\u002D\u002C])*$/))
		     	{
		     		alert("Only digits, Alphabets, '-' , '/' and ',' are allowed.");
		     		$('#trackCMS'+workspaceCMSId).css('backgroundColor','#FFE6F7'); 
		     		$('#trackCMS'+workspaceCMSId).focus();
		     	
		     		return false;
		     	}
				else
				{
					$.ajax({			
						url:'updateCMSTrackNo_ex.do?workspaceCMSId='+workspaceCMSId+'&cmsTrackNum='+trackNo+'&inventedName='+inventName,
				  		success: function(data)
				  		{
				  			alert(data);
				  			//addCmsDtl(workSpaceId);
				  		}
				  	});
				}
			}
			
</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>
<div style="display: none;"></div>
<div align="center"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">CMS
Details</div>
<a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>


<hr color="#5A8AA9" size="3px"
	style="width: 350; border-bottom: 2px solid #CDDBE4;" align="left"></hr>

<br>

<s:form action="SaveCMS_ex" method="post" name="AddCMSForm"
	id="AddCMSForm">
	<div align="center">
	<table width="100%" align="center">
		<tr align="left">
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Wave</td>
			<td><s:textfield name="waveNo" size="2" value="1" maxlength="1"></s:textfield></td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Country Name</td>
			<td><s:select list="countries" id="selectedCountry"
				name="selectedCountry" listKey="countryId" listValue="countryName"
				headerKey="-1" headerValue="Select Country"
				onchange="getAgencies();">
			</s:select></td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Agency Name</td>
			<td><SPAN id="selectedAgency"> <select
				name="selectedAgency">
				<option value="-1">Select Agency</option>
			</select> </SPAN></td>
		</tr>
		<tr>
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Tracking Number</td>
			<td align="left"><s:textfield name="cmsTrackNum"
				id="cmsTrackNum" size="20" id="trackNumId"></s:textfield></td>
		</tr>
		
		<!-- 
		<tr>
			<td class="title" align="right" width="30%"
				style="padding: 2px; padding-right: 8px;">Invented Name</td>
			<td align="left"><s:textfield name="inventedName"
				id="inventedName" size="20"></s:textfield></td>
		</tr> -->
		
		<tr>
			<td></td>
			<td align="left"><input type="hidden" id="workSpaceId"
				name="workSpaceId" value="<s:property value='workSpaceId'/>">
			<s:hidden name="regionId"></s:hidden> <input type="button"
				name="submitBtn" id="submitBtn" value="Add CMS" Class="button"
				align="center"></td>
		</tr>
	</table>
	</div>
</s:form>

<br>
<s:if test="lstWorkspaceCMS.size > 0">
	<div align="center"
		style="max-height: 160px; overflow-x: auto; border: 1px solid #0C3F62; width: 500px;">
	<table align="center" width="100%" class="datatable"
		style="border-bottom: 0px; border-top: 0px;">
		<thead>
			<tr>
				<th>#</th>
				<th>Wave</th>
				<th>CMS Name</th>
				<th>Agency</th>
				<th>Tracking Number</th>
				<!--<th>Invented Name</th> -->
				<th>Update</th>
				<th>Remove</th>


			</tr>
		</thead>
		<tbody>
			<s:iterator value="lstWorkspaceCMS" status="status">
				<s:hidden value="statusIndi" id="statusIndi" />
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td>${status.count }</td>
					<td><s:property value="waveNo" /></td>
					<td><s:property value="countryName" /></td>
					<td><s:property value="agencyName" /></td>
					<td><input type="text" name="trackCMS" size="9"
						id="trackCMS<s:property value="workspaceCMSId"/>"
						value="<s:property value="cmsTrackNum"/>"></td>
						
					<!-- <td><input type="text" name="invented_Name" size="25"
						id="invented_Name<s:property value="workspaceCMSId"/>"
						value="<s:property value="inventedName"/>"></td> -->
						
					<td>
					
					<div align="center"><a title="Edit"
						href="javascript:void(0);"
						onclick="updateCMSTrackNo('<s:property value="workspaceCMSId"/>');">
					<img border="0px" alt="Update" src="images/Common/update.png"
						height="18px" width="18px"> </a></div>
					</td>
					<td><%-- <a href="RemoveCMS.do?workspaceCMSId=<s:property value="workspaceCMSId"/>&workSpaceId=${workSpaceId}&regionId=${regionId }" onclick="return confirm('Are you sure?');">
				       			Remove
				       		</a> --%>
					<div align="center"><a title="Remove" id="removeCMSCountry">
					<img border="0px" src="images/Common/remove.png" height="18px"
						width="18px" alt="Remove" title="Remove"
						onclick="removeCMS('<s:property value="workspaceCMSId"/>','<s:property value="workSpaceId"/>','<s:property value="regionId"/>');">
					</a></div>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</s:if>

<input type="hidden" id="dtdVersion" name="dtdVersion"
	value="<s:property value="dtdVersion"/>"></input>
</body>
</html>