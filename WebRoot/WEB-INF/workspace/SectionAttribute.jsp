
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
window.onload = function exampleFunction() {
	$("td#dynaTD").each(function() {
		//debugger;
		 var spans=3;
	 	 var size="<s:property value='getProjectLevelSearchResult.size()'/>";
	 	 var spanSize=spans+size;
	    var id = $(this).attr("colspan",spanSize);
	});
}
	

var url_string = window.location.href;
var url = new URL(url_string);
var nodeIdFroRedirection = url.searchParams.get("nodeId");
	function refreshParent() 
	{
		self.close();
		 window.opener.parent.history.go(0);	
	}
	
	function RepeatNodeSection(nodeId){
			//alert("test");
			//debugger;
			var nodeHistorySize = <s:property value="nodeHistorysize" />;
			var url_string = window.location.href;
			var url = new URL(url_string);
			var workspaceID = url.searchParams.get("workspaceID");
	  		//str="RepeatNodeSection.do?workspaceID="+workspaceID+"&repeatNodeId="+nodeId;
			//win3=window.open(str,'ThisWindow','height=380,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
			//win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(350/2));
			//return true;
			
			$.ajax( {
				url:"RepeatNodeSection.do?workspaceID="+workspaceID+"&repeatNodeId="+nodeId,
				success : function(data) {
					location.reload();
					},
				error: function(data) 
				  {
					alert("Something went wrong while Removing rights.");
				  },
						async: false
			});
	}
	 
	function RepeatParentSection(nodeId){
			//debugger;
			var url_string = window.location.href;
			var url = new URL(url_string);
			var workspaceID = url.searchParams.get("workspaceID");
			str="SaveParentSectionAttributes_b.do?workspaceID="+workspaceID+"&nodeId="+nodeId;
			win3=window.open(str,"ThisWindow2","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=400,width=800,resizable=no,titlebar=no");	
			win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(500/2));
			return true;
		}
	
	function removeLeafNode(removeNodeId,repeatNodeId){
		
		//debugger;
		var url_string = window.location.href;
		var url = new URL(url_string);
		var workspaceID = url.searchParams.get("workspaceID");
		var nodeId = url.searchParams.get("nodeId");
		var remark = prompt("Please specify reason for change.");
		remark = remark.trim();
		if (remark != null && remark != ""){
		$.ajax(
				{	
					url: "RemoveNodeSection_ex.do?workspaceID="+workspaceID+"&removeNodeId=" + removeNodeId+"&repeatNodeId="+nodeId+"&remark=" + remark,
							
					success: function(data) 
			  		{
						 
						if(data=="true")
						{
						 alert("Document Removed Successfully.");
					 	}
						window.location.href = "getAttributeDataForCSV_b.do?workspaceID="+workspaceID+"&nodeId=" + nodeId;
						//window.location.href = "ShowPendingWorks.do?workSpaceId=" + workSpaceId;
					},
		            error: function (data) {
		            	alert("Something went wrong");
		            },
		            async: false
				});
		}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
		
	}
	function EditSectionAttr(nodeId){
		//debugger;
		var url_string = window.location.href;
		var url = new URL(url_string);
		var workspaceID = url.searchParams.get("workspaceID");
		str="EditSectionAttr_b.do?workspaceID="+workspaceID+"&nodeId="+nodeId;
		win3=window.open(str,"ThisWindow2","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=450,width=700,resizable=no,titlebar=no");	
		win3.moveTo(screen.availWidth/2-(700/2),screen.availHeight/2-(300/2));
		return true;
	}
	
	function DeleteSectionForScripts(nodeId){
		//debugger;
		var url_string = window.location.href;
		var url = new URL(url_string);
		var workspaceID = url.searchParams.get("workspaceID");
		//var nodeId = url.searchParams.get("nodeId");
		var remark = prompt("Please specify reason for change.");
		remark = remark.trim();
		if (remark != null && remark != ""){
		$.ajax(
				{	
					url: "DeleteSectionForScripts_ex.do?workspaceID="+workspaceID+"&removeNodeId="+nodeId+"&remark=" + remark,
							
					success: function(data) 
			  		{
						 
						if(data=="true")
						{
						 alert("Section removed successfully.");
						window.location.href = "getAttributeDataForCSV_b.do?workspaceID="+workspaceID+"&nodeId=" + nodeIdFroRedirection;
						}
						else{
							alert("Could not delete section because a file is uploaded");
						}
						//window.location.href = "ShowPendingWorks.do?workSpaceId=" + workSpaceId;
					},
		            error: function (data) {
		            	alert("Something went wrong");
		            },
		            async: false
				});
		}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	}
	
	
	
	/* function RepeatParentSection(){
		//alert("test");
		debugger;
		var nodeHistorySize = <s:property value="nodeHistorysize" />;
		var url_string = window.location.href;
		var url = new URL(url_string);
		var workspaceID = url.searchParams.get("workspaceID")
  		str="RepeatParentSection.do?workspaceID="+workspaceID+"&&repeatNodeId="+<s:property value="nodeId"/>;
		win3=window.open(str,'ThisWindow','height=380,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
		win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(350/2));
		return true; 
} */
</script>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<!-- <div class="headercls" align="center" style="width: 100%">Client Detail History</div> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">Tabular View</b></div>
<br>

<s:if test="getAttributeDetails.size>0">
	<form id="myform" action="ExportToXls.do" method="post" style="width: 25px; float: right; margin: -14px 14px 14px 0; cursor: pointer;">
		<input type="hidden" name="fileName" value="Attribute Detail.xls">					
			<textarea rows="1" cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
				<td style="width:25%;">
				<img alt="Export To Excel" title ="Export To Excel" src="images/Common/Excel.png" 
				onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()"/>
				</td>
	</form>
</s:if>

<div id="divTabText" style="width:98%">
<table class="datatable paddingtable" style="border: 1px solid;width:100%;" align="center"> 
	<tr style="border: 1px solid;"class="odd">
		<td id="dynaTD" style="border: 1px solid;"align='left' style="padding-left:8px;background-color: white;font-size:16px;">
		<b>Project:</b> ${ ProjectName } 
		<input type="button"  value="Add" class="button" style="font-size: 15px; float: right;"
		onclick="RepeatParentSection(<s:property value="originalNodeWithAllclones.lastElement().getNodeId()"/>);">
		</td>
	</tr>
</table>
<table id="table" class="datatable paddingtable audittrailtable" style="border: 1px solid;width:100%;" align="center">
<s:iterator value="wsNodeDtlToShow" status="status">

<s:if test="parentFlag == 'Y' ">
<thead>
	<tr style="border: 1px solid; background: #b1b1b1;"class="odd">
	  <s:set name="temp" value="  <s:property value='getProjectLevelSearchResult.size()'/> "></s:set>
	  <td id="dynaTD"  style="border: 1px solid; padding-left:8px; font-size:16px;"align="left">
		<b><s:property value="lbl_nodeName"/>: </b> <s:property	value="folderName" />
		&nbsp;<input type="button"  value="Edit" class="button" style="font-size: 15px; float: right;"
		onclick="EditSectionAttr(<s:property value="nodeId"/>);">
		<s:if test="cloneFlag=='Y' && requiredFlag == 'D' ">
			<input type="button"  value="Delete Section" class="button" style="font-size: 15px; float: right;"
			onclick="DeleteSectionForScripts(<s:property value="nodeId"/>);">
		</s:if>
		</td></tr>
		<tr>
		<th><s:property value="lbl_folderName"/></th>
		<th style="border: 1px solid; border-color:black;">Is Document Uploaded</th>
		<s:iterator value="getProjectLevelSearchResult" status="status">
				<th style="border: 1px solid; border-color:black;">
					<s:property value="attrName" />
				</th>
			</s:iterator>
		<th class="rmvCol" style="border: 1px solid; border-color:black;">Remove</th>
		<th class="rmvCol" style="border: 1px solid; border-color:black;">Action</th>
	</tr>		
</thead>	
</s:if>	
<s:else>
<tbody>
		<s:if test="#status.even">
		<s:if test="publishFlag==true"><tr class="even"></s:if>
		<s:else><tr class="even rmvCol"></s:else>
		</s:if>
		<s:else>
		<s:if test="publishFlag==true"><tr class="odd"></s:if>
		<s:else><tr class="odd rmvCol"></s:else>
		</s:else>
		<%-- <tr	class="<s:if test="#status.even">even <s:if test="publishFlag==true">rmvCol</s:if></s:if>
						<s:else>odd <s:if test="publishFlag==true">rmvCol</s:if></s:else>"> --%>
					<td style="border: 1px solid; border-color:black; width:18.5%" title='<s:property value="folderName"/>'>
					<s:property	value="folderName" />
					<s:if test="attrValue !=null ">
						[<s:property value="attrValue" />]
					</s:if></td>
					<td style="border: 1px solid; border-color:black; width:15.4%">
						<s:if test="uploadFlag=='Yes' ">
							<s:property value="uploadFlag"/>
						</s:if> 
						<s:else>No</s:else>
					</td>
					
					<%-- <s:if test="Automated_TM_Required=='Yes'">
						<s:iterator value="URSTracebelityMatrixDtl">
							<s:if test="fileName == 'FS' ">
								<td style="border: 1px solid; border-color:black; width:14.99%" >
									<s:property value="IDNo"/>
								</td>
							</s:if>
							
						</s:iterator>
					</s:if>
					<s:else> --%>
	 					<s:iterator value="sortedMap" status="stat">
	                        <s:set name="elementid" value="parentNodeId"></s:set>
	                        	<s:if test="key == #elementid ">
	                        		<s:if test="key != '' "> 
	                        			
	                        			<s:iterator value="value[0]" status="valItr">
	                        			   <s:set name="index" value="#valItr.count-1"></s:set>
	                        			  	<td style="border: 1px solid; border-color:black; width:14.99%" >
													<s:property value="value[0].toArray()[#index]"/>
		                        				</td>
	                        			</s:iterator>
		                        	</s:if>
		                        	<s:else>
		                        	<td style="border: 1px solid; border-color:black; width:14.99%" >-</td></s:else>
	                       		</s:if> 
	                	</s:iterator>
                	<%-- </s:else> --%>
					<td class="rmvCol" style="border: 1px solid; border-color:black; width:5%;">
					<s:if test="cloneFlag == 'Y' && requiredFlag != 'N' ">
					<a href="#"
						onclick="removeLeafNode('<s:property value="nodeId"/>','<s:property value="nodeId"/>');">
						 <img border="0px" class="temp" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px">
						</a>
						</s:if>
						 <s:elseif test="requiredFlag == 'B' ">-
						<%-- <a href="#"
						onclick="removeLeafNode('<s:property value="nodeId"/>','<s:property value="nodeId"/>');">
						 <img border="0px" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px"> 
						</a> --%>
						</s:elseif><s:else>-</s:else>
					</td>
					<td class="rmvCol" style="border: 1px solid; border-color:black; border: 1px solid; width: 10%;">
						<s:if test="sortOrder!='true' "> 
						<input type="button" style="font-size: 12px;" value="Add" class="button" onclick="RepeatNodeSection(<s:property value="nodeId"/>);" >
						</s:if><s:else>-</s:else>
					</td>
			</tr>
			</tbody>
			</s:else>
</s:iterator>	
</table>
<s:set name="isBold" value=""></s:set>

</div>
</div>
<br>
<div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
	
</div>




</div>
</div>
</div>
</body>
</html>