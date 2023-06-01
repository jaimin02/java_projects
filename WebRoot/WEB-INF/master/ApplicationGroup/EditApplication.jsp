<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head theme="ajax" />
 <script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>

<SCRIPT type="text/javascript">

$(document).ready(function() { 
	debugger;
	
	$('.target').change(function(){
		//debugger;	
		var correct=true;
		var docName=document.getElementById('fileName').value;
		docName = docName.trim();
		//docName=document.getElementById('docName').value; 
		//debugger;
		var urlOfAction
		var WorkspaceId = '<s:property value="WorkspaceId"/>';
		if(docName==""){
			$('#message').html("");
			alert("Please specify Attachment Title.")
			return false;
		}else{
			urlOfAction="AttachmentNameExists_ex.do";
		}
		var dataofAction="fileName="+docName+"&applicationCode="+applicationCode;
		if (correct==true)
		{
			$.ajax({
				type: "GET", 
   				url: urlOfAction, 
   				data: dataofAction, 
   				cache: false,
   				dataType:'text',
				success: function(response){
					$('#message').html(response);
					if(response.indexOf('available') == -1){
						document.getElementById('Save').disabled='disabled';
						return false;
					}
					else if(response.indexOf('available') != -1)
					{
						document.getElementById('Save').disabled='';
						return true;
					}
				}
			});
		}
	});
	
	
	
	

});


function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
	
	
function validate()
{
	debugger; 
	/*if(document.masterAdminForm.remark.value == "")
 	 {
 		alert("Please specify Reason for Change.");
 		document.masterAdminForm.remark.style.backgroundColor="#FFE6F7"; 
 		document.masterAdminForm.remark.focus();
      	return false;
      }*/
      
    var url_string = window.location.href;
  	var url = new URL(url_string);
  	var applicationCode = url.searchParams.get("applicationCode");
  	var applicationName = url.searchParams.get("applicationName");
	var hosting = document.getElementById('applicationHostDetail').value;
	var categroy= document.getElementById('applicationCategoryDetail').value;
	var remark = document.masterAdminForm.remark.value;
	var remarkFromDB = url.searchParams.get("remark");
	remark = remark.trim();
	if(remark=="" || remark==null){
		 /* alert("Please specify Reason for Change.");
		 document.getElementById("remark").style.backgroundColor="#FFE6F7"; 
		 document.getElementById("remark").focus();
		 return false; */	 
		 remark = remarkFromDB;
		 }
	var fileName = document.masterAdminForm.fileName.value;
	var uploadFile = document.masterAdminForm.uploadFile.value;
	
	if(fileName =="" && uploadFile != "" ){
		alert("Please enter attachment title.")
		document.getElementById("fileName").style.backgroundColor="#FFE6F7";
		 document.getElementById("fileName").focus();
		return false;
	}
	if(fileName !="" && uploadFile == "" ){
		alert("Please select file to upload.");
		document.getElementById("uploadFile").style.backgroundColor="#FFE6F7";
		 document.getElementById("uploadFile").focus();
		return false;
	}
	
	var index=uploadFile.lastIndexOf('.');
	var fileext = uploadFile.substring(index+1).toLowerCase();
	if(uploadFile != "" ){
		if((fileext !="pdf" && fileext!="doc" && fileext!="docx")){
			alert("Please upload valid extension(e.g. .pdf, .doc & .docx) Document.")
			return false;
		}
	}
	
	var fd = new FormData();
	//if($('#uploadFile')[0]!=undefined){
	if(fileName !="" && uploadFile != "" ){
    	var file = $('#uploadFile')[0].files[0];
    	fd.append('fileName',fileName);
    	fd.append('uploadingfileName',$('#uploadFile')[0].files[0].name);
    	
    	fd.append('uploadFile', file);
	}
    fd.append('applicationCode', applicationCode);
    fd.append('applicationName', applicationName);
    fd.append('hostingId',hosting);
    fd.append('catgoryId',categroy);
    fd.append('remark',remark);
    
    $.ajax({
        url: 'SaveFileForApplication_ex.do',
        data: fd,
        type: "POST",
        contentType: false,
        processData: false,
        success: function(data) {
          //alert(data);
          if(data=="true"){
        	  var out="EditApplication_b.do?applicationCode="+applicationCode+"&applicationName="+applicationName+"&hostingId="+hosting+
        			  "&catgoryId="+categroy+"&remark="+remark;
      		  location.href=out;
          }
          else{
        	  alert(data);
          }
        },	
		  error: function(data) 
		  {
			 alert("Something went wrong while fetching data from server.");
		  },
			async: false				
		});
   }

	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;

	function refreshParent() 
	{
		window.opener.parent.history.go(0);
		self.close();
	}
	
	function fileOpen(actionName)
	{
		//debugger;
		//win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
		win3=window.open(actionName,"_newtab","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2)); 
		
	}
	
	function deleteAttachment(tranNo)
	{
		debugger;
		var remark = prompt("Please specify reason for change.");
		remark = remark.trim();
		if(remark == null || remark == ""){
			alert("Please specify reason for change.");
			return false;
		}
		window.location.href = "DeleteApplicationAttachment.do?applicationCode="+applicationCode+"&applicationName="+applicationName+
				"&hostingId="+hosting+"&catgoryId="+categroy+"&remark="+remark+"&tranNo="+tranNo;
	}
	
</SCRIPT>


</head>
<body>
<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>


<br>
<div class="container-fluid">
<div class="col-md-12">
<div align="center">
<div class="boxborder" style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; ">
<div style="width:100%;" class="all_title"><b style="float: left;">Edit Software Application Group</b></div>
<br>
<s:form method="post" name="masterAdminForm" enctype="multipart/form-data">
	<br>
	<table class="channelcontent" width="100%">
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="applicationName">Software Application Name</td>
			<td  align="left"><input type="text" id="applicationName" value="${ applicationName}" disabled="disabled"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="hostingId">Hosting</td>
			<td  align="left">
				<%-- <select name="hostingId">
					<!-- <option value="-1">Select Tree Structure</option> -->
					 <s:iterator value="applicationHostDetail">
						<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
						<s:if test="statusIndi != 'D'"> 
						<option value="<s:property value="hostingCode"/>"><s:property value="hostingName" /></option>
						 </s:if>
					</s:iterator>
				</select> --%>
				<s:select list="applicationHostDetail" id="applicationHostDetail"
					name="applicationHostDetail" listKey="hostingCode"
					listValue="hostingName" value="%{hostingId}">
				</s:select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="catgory">Category</td>
			<td  align="left">
				<%-- <select name="catgoryId">
					<!-- <option value="-1">Select Tree Structure</option> -->
					<s:iterator value="applicationCategoryDetail">
						<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
						<s:if test="statusIndi != 'D'"> 
							<option value="<s:property value="categoryCode"/>"><s:property value="categoryName" /></option>
						 </s:if>
					</s:iterator> 
				</select> --%>
				<s:select list="applicationCategoryDetail" id="applicationCategoryDetail"
					name="applicationCategoryDetail" listKey="categoryCode"
					listValue="categoryName" value="%{catgoryId}">
				</s:select>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;" id="remark">Remark</td>
			<td  align="left"><s:textfield name="remark" value=""></s:textfield></td>
		</tr>
		
		<tr id="fileNameTr" align="left">
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;">Attachment Title</td>
			<td align="left"><input type="text" class="target" id="fileName"></td>
			<div class="errordiv" align="center" style="font-size: 14px; margin-bottom: 20px; color: red;" id="message"></div>
		</tr>
		
		<tr id="fileTr" align="left">
			<td class="title" align="right" width="50%" style="padding: 2px; padding-right: 8px;">Attachment</td>
			<td align="left"><input type="file" id="uploadFile"></td>
		</tr>
		<tr align="left">
			<td>&nbsp;</td>
			<td>
				<input type="button" name="submitBtn" id="Save" value="Update" class="button"onclick="return validate();" />
				<input type="button" value="Close" class="button" name="Close" onclick="return refreshParent();" />
			</td>
				
		</tr>
	</table>
</s:form>
	
	<%int srNo = 1; %> <br>
<table id="clientTable" width="98%" align="center" class="datatable">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Attachment</th>
			<th style="border: 1px solid black;">Modified by</th>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>	
			<!-- <th>Edit</th> -->
			<th style="border: 1px solid black;">Remark</th>
			<th style="border: 1px solid black;">Action</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="attachmentHistory" id="attachmentHistory" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td style="border: 1px solid black;"><%=srNo++ %></td>
			<td style="border: 1px solid black;">
					<a href="#" onclick="return fileOpen('openfileForApplicationAttachment.do?applicationCode=<s:property value="applicationCode"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
							<s:property value="attachmentTitle" default="-" />
					</a>
			</td>
			
			
			<td style="border: 1px solid black;"><s:property value="userName" /></td>
			<td style="border: 1px solid black;"><s:property value="ISTDateTime" /></td> 
		<s:if test ="#session.countryCode != 'IND'">
			<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
		</s:if>
			<td style="border: 1px solid black;">
				<s:property value="remark" />
			</td>
			<td style="border: 1px solid black;">
			<div align="">
				<a title="Delete Attachment" onclick="deleteAttachment('<s:property value="tranNo"/>');" href="#">
						<img border="0px" alt="Delete Attachment" src="images/Common/delete.svg"height="25px" width="25px"> 
				</a>			
			</div>
			</td>
		</s:iterator>
	</tbody>
</table>

<br>

</div>
</div>
</div>

</div>

<SCRIPT type="text/javascript">

	var url_string = window.location.href;
	var url = new URL(url_string);
	var applicationCode = url.searchParams.get("applicationCode");
	var applicationName = url.searchParams.get("applicationName");
	var hosting = document.getElementById('applicationHostDetail').value;
	var categroy= document.getElementById('applicationCategoryDetail').value;
	var remark = document.masterAdminForm.remark.value;
	var remarkFromDB = url.searchParams.get("remark");

</script>

</body>
</html>
