<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">

.paddingtable tr td:nth-last-child(2) {      
      width:200px;
    }
/* #usertable_filter input{
background-color: #2e7eb9;
color:#ffffff;
}
#usertable_length select {
background-color: #2e7eb9;
color:#ffffff;
} */

.remarktbl tr td:nth-last-child(2) {      
      width:40px;
    }
</style>


<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>


<script type="text/javascript">

$(document).ready(function() { 
		//debugger;
	    var date = (new Date().getFullYear()).toString();
		$("#copyright").text("\u00A9"+date +" Sarjen Systems Pvt. Ltd.");
		
	$(".downloading").hide();
	  $('#usertable').dataTable( {
		 "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
		 } ); 
	  
	 
});

function openAssignUser(nodeId){
	//debugger;
	var wsId= '<s:property value="WorkspaceId"/>';
	str="AttachUserOnRecord_b.do?docId="+wsId+"&recordId="+nodeId;
	win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=550,width=900,resizable=no,titlebar=no");	
	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}	

function deleteNode(wsId,nodeId)
{
	//debugger;
	var docType ='<s:property value="docType"/>';
	var remark = prompt("Please specify reason for change.");
	 remark = remark.trim();
	 if(remark == null || remark == ""){
		alert("Please specify reason for change.");
		return false;
	}
		window.location.href = "DeleteNodeForDocSing.do?WorkspaceId="+wsId+"&nodeId="+nodeId+"&remark="+remark+"&docType="+docType;
}
function deletedFileHistory(wsId,docType){
	str="DeletedNodeDetailForDocCR_b.do?WorkspaceId="+wsId+"&docType="+docType;
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}
function downloadPdf(wsId,nodeId){
	//debugger;
 	var URL='DownloadDocSing_ex.do';
 	var docType='<s:property value="docType"/>';
 	
 	$.ajax(
 	{			
 		url: 'ConvertAndDownloadDocSign_ex.do?WorkspaceId='+wsId+"&nodeId="+nodeId+"&docType="+docType,
 		 		
 		beforeSend: function()
 		{
 			$('#imgPdf'+nodeId).hide();
 			//$('#downloading'+nodeId).css('background-color', '#E3EAF0');
 			$('#downloading'+nodeId).show();
			
 		},
 		success: function(data) 
   		{
 			//url: 'Download_ex.do?DownloadFile='+data;
 			//debugger;
 			
 			//alert(data);
				if(data.length>0){
					if(data.includes("/")){
						window.location = URL+'?DownloadFile='+data; 
					}
					else{
						alert(data);
					}
				}
			$('#downloading'+nodeId).hide();
	 		$('#imgPdf'+nodeId).show(); 
 		},
 		 async: true,
         error: function (data) {
         	alert("Something went wrong");
             },		
 		
 	});
 	return true;
 }	
</script>

</head>
<body>
<div class="container-fluid">
<div class="col-md-12">


<div class="msgdiv" style="color: green;" align="center"><s:actionmessage /></div>
<br />
<div align="center"><!-- <img
	src="images/Header_Images/User/Add_User.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

<div class="boxborder"><div class="all_title"><b style="float:left;">${docTypeName}</b></div>

<div class="datatablePadding" style="border: 1px; border-radius: 0px 0px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>

<div>
<%-- <s:if test="#session.usertypename == 'WA'">
<a title="Add" style="float: left; margin-left: 10px;" href="OpenRecord.do?docId=<s:property value="WorkspaceId"/>&docType=<s:property value="docType"/>">
	<input type="button" value="New" class="button" name="buttonName"/></a>
</s:if> --%>
	<input style="float: Right; margin-right: 10px;" type="button" value="Deleted Document History" class="button" name="buttonName" onclick="deletedFileHistory('<s:property value="WorkspaceId"/>','<s:property value="docType"/>')"/>
<br/>
</div>
<br><br>
<div id="divTabText" style="width:98%">
<s:set name="isBold" value=""></s:set>
<table id = "usertable" class="datatable paddingtable remarktbl" style="width:100%;" align="center">
	<thead>
	
		<tr style="border: 1px solid;">
			<th style="border: 1px solid; border-color:black;">#</th>
			<th style="border: 1px solid; border-color:black; width: 200px;">Document</th>
			<s:iterator value="getProjectLevelSearchResult" status="status">
			<th style="border: 1px solid; border-color:black;"><s:property value="attrName" /></th>
			</s:iterator>
			<th style="border: 1px solid; border-color:black;">Current Stage</th>
		<%-- <s:if test="#session.usertypename == 'WA'">
			<th style="border: 1px solid; border-color:black;">Assign Rights</th>
		</s:if> 
			<th style="border: 1px solid; border-color:black;">Edit Attribute</th>
			<th style="border: 1px solid; border-color:black;">Upload File</th> --%>
			<th colspan="5" style="border: 1px solid; border-color:black; width:180px;">Actions</th>
		<%-- <s:if test="#session.usertypename == 'WU'">
			<th style="border: 1px solid; border-color:black;">Change Status</th>
		</s:if> --%>
			<!-- <th style="border: 1px solid; border-color:black; width: 60px;">Publish</th> -->

			<th style="border: 1px solid; border-color:black;">Publish</th>
		
		</tr>
		
	</thead>
	<tbody>
	 	<s:iterator value="getAttributeDetailsForDisplay" status="stat">
			<tr	class="<s:if test="#stat.even">two</s:if><s:else>one</s:else>">
			 <td style="border: 1px solid;">${stat.count }</td>
			<td style="border: 1px solid;">
			<s:if test="top[3] != ''">
			<s:if test="#session.usertypename == 'WA'">
				<a style="text-decoration: underline;color:blue; font-size: 16px;" 
				    href="openfile.do?fileWithPath=<s:property value="top[3]" />" target="_blank"><s:property value="top[2]" /></a>
			</s:if>
			<s:else>
				<s:if test="OpenFileAndSignOff=='Yes'">
				<a style="text-decoration: underline;color:blue; font-size: 16px;" 
				    href="SignOffOpenfilefromSingleDashboard.do?fileWithPath=<s:property value="top[3]" />" target="_blank"><s:property value="top[2]" /></a>
				</s:if>
				<s:else>
					<a style="text-decoration: underline;color:blue; font-size: 16px;" 
				    href="openfile.do?fileWithPath=<s:property value="top[3]" />" target="_blank"><s:property value="top[2]" /></a>
				</s:else>
			</s:else>
			</s:if>
			<s:else><s:property value="top[2]" /></s:else>
				</td>	
			<s:subset source="getAttributeValuesForDisplay" start="#stat.count-1">
		 	  <s:iterator value="%{top}" status="outerStatus">
		 		<s:if test="#outerStatus.first == true ">
		 		  <s:iterator status="status">
		 			<td style="border: 1px solid;"><s:property value="top" default="-"/></td>
		 		  </s:iterator>
		 		</s:if>
		 	  </s:iterator>
		 	</s:subset>		 	 
		 <%--	<td style="border: 1px solid;"><s:property value="top[8]" /></td>
			<td style="border: 1px solid;"><s:property value="top[9]" /></td>
			<td style="border: 1px solid;"><s:property value="top[10]" /></td>
			<td style="border: 1px solid;"><s:property value="top[11]" /></td>
			<td style="border: 1px solid;"><s:property value="top[12]" /></td> --%>  
			
			<td style="border: 1px solid;"><s:property	value="top[7]" /></td>
			
    		<td style="border: 1px solid;"><%-- <input style="margin-top: 2px; margin-bottom: 3px" type="submit" value="Change Rights" class="button" name="buttonName"
					onclick="return openAssignUser('<s:property	value="top[1]" />');"  /> --%>
				<a title="Assign Users" 
				 href="#" onclick="return openAssignUser('<s:property	value="top[1]" />');">
    				<img border="0px" title="Assign Users" alt="Assign Users" src="images/Common/assign_rights.svg" height="25px" width="25px"></a>
			 </td>
			
			<td style="border: 1px solid;">
				<a title="Edit <s:property value="docTypeName"/>"
				 href="EditAttribute.do?docId=<s:property value="top[0]"/>&recordId=<s:property value="top[1]"/>&docType=<s:property value="docType"/>" >
    				<img border="0px" alt="Edit <s:property value="docTypeName"/> " src="images/Common/edit.svg" height="25px" width="25px"></a>
    		 </td>
			<td style="border: 1px solid;"> 
			 <%-- <s:if test="#session.usertypename == 'WU' && top[4] == 'true'"> --%> 
				<%-- <s:if test= "(top[5] == '10' && top[6] !='SR') || top[5] == '-1' || top[5] == 0"> --%> 
				<a title="Upload Document"style="margin-top: 2px; margin-bottom: 3px" 
    		 		href="uploadFileForDocSign.do?docId=<s:property value="top[0]"/>&recordId=<s:property value="top[1]"/>&docType=<s:property value="docType"/>" >
    				<!-- <input type="button" value="Upload File" class="button" name="buttonName"/> -->
    				<img border="0px" alt="Upload Document" src="images/Common/upload_file.svg" height="25px" width="25px"></a>
    				<%--  </s:if>
    				<s:else><span>
    					<img border="0px" title ="Upload Document" alt="Upload Document" src="images/Common/upload_file.svg" height="25px" width="25px">
    				</span></s:else> --%>
    		 <%-- </s:if>
    		<s:else><span>
    			<img border="0px" title ="Upload Document" alt="Upload Document" src="images/Common/upload_file.svg" height="25px" width="25px">
			</span></s:else> --%> 
    		 </td>
    		
    		<td style="border: 1px solid;"> 
    		<%--  <s:if test="#session.usertypename == 'WU'">  --%>
    		 <s:if test="top[4] == 'false' &&  #session.usertypename == 'WU'"> 
    		
    			<a title="Change Stage" style="margin-top: 2px; margin-bottom: 3px" 
    		 		href="ChangeStatusDocSign.do?docId=<s:property value="top[0]"/>&recordId=<s:property value="top[1]"/>&docType=<s:property value="docType"/>" >
    				<!-- <input type="button" value="Change Status" class="button" name="buttonName"/> -->
    				<img border="0px" alt="eSign" title ="eSign" src="images/Common/change_stage.svg" height="25px" width="25px"></a>
    		 </s:if>
    		<s:else><span>
    			<img border="0px" title ="eSign" alt="eSign" src="images/Common/change_stage.svg" height="25px" width="25px">
			</span></s:else> 
			 <%--  </s:if> --%>
    		 </td>
    		
			<td style="border: 1px solid;">
					
				 <s:if test=" #session.usertypename == 'WA' && top[3] == ''">
				
					<%-- <input style="margin-top: 2px; margin-bottom: 3px" type="button" value="Delete Node" class="button" name="buttonName"
						   onclick="return deleteNode('<s:property value="top[0]"/>',<s:property value="top[1]"/>);"  /> --%>
					<a title="Delete Document" style="margin-bottom: 3px" 
    		 		href="#"  onclick="return deleteNode('<s:property value="top[0]"/>',<s:property value="top[1]"/>);" >
    				<img border="0px" alt="Delete Record" src="images/Common/delete.svg" height="25px" width="25px"></a>
				 </s:if>
				 <s:else><span style="">
				 	<img border="0px" title ="Delete Record" alt="Delete Record" src="images/Common/delete.svg" height="25px" width="25px">
				 </span></s:else>
			</td>
			<td style="border: 1px solid;">
				<s:if test= "(top[5] == '10' && top[6] !='SR') || top[5] == '-1' || top[5] == 0">
				 <img border="0px" title ="Publish" alt="Publish" height="25px" width="25px" src="images/Common/open.svg" height="25px" width="25px">
    			</s:if>
    			<%-- <s:else>
    				<div class ="downloading" id="downloading" style="width:10px; background-color: #E3EAF0;float: left;"><img src="images/loading.gif" alt="loading ..." /></div>
    				<a><img id="imgPdf" src="images/Common/open.png" title="Publish" style="margin-left: 10px;"
    						onclick="downloadPdf('<s:property value="top[0]"/>',<s:property value="top[1]"/>);"/></a>
    			</s:else> --%>
    			<s:else>
    				<div class ="downloading" id="downloading<s:property value="top[1]"/>" style="width:10px;"><img height="25px" width="25px" src="images/loading.gif" alt="loading ..." /></div>
    				<a><img id="imgPdf<s:property value="top[1]"/>" src="images/Common/open.svg" title="Publish" height="25px" width="25px"  style=""
    						onclick="downloadPdf('<s:property value="top[0]"/>',<s:property value="top[1]"/>);"/></a>
    			</s:else>
			</td>
				
			</tr>
		</s:iterator>		
	</tbody>
</table>	
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
