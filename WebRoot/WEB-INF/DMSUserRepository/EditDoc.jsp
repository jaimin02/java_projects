<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<STYLE type="text/css">
.tabHdrActive {
	background: #5A8AA9;
	color: white;
	margin: 0px;
	padding: 0px;
}

.tabHdrInActive {
	background: white;
	color: #5A8AA9;
	margin: 0px;
	padding: 0px;
}
</STYLE>
<script type="text/javascript">
 		$(document).ready(function() {
			$('#popupContactClose').click(function(){
				disablePopup();
			});
			var options = {	
					success: showResponse 
				};
			$("#saveDocDtl").click(function(){
				if(chkData())					
					$("#editDocForm").ajaxSubmit(options);
				return false;			
			});

			$('#attrTab').click(function(){
				$('#attrDataOuter').fadeOut();
				$('#attrTab').attr('class','tabHdrActive');
				$('#attrDataOuter').fadeIn();
				var wsId = document.getElementById("workSpaceId").value;
				var ndId = document.getElementById("nodeId").value;
				var divId = "attrTabData";
	  			$.ajax
				({
					url : 'EditDocAttr_ex.do?workSpaceId='+wsId+"&nodeId="+ndId,
					beforeSend: function()
					{
						$('#'+divId).html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
					},
			  		success: function(data) 
			  		{														  					  		
						$('#'+divId).html(data);							
					}	  	
				});
			});
			
 		});


		function showResponse(responseText, statusText) 
		{
 			alert(responseText);
 			BackToMain();
		}
 		</script>
<script type="text/javascript">
		function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}
		
		function chkData()
		{
			var userComments = $('#userComments').val();
			userComments = trim(userComments);
			if(userComments.length == 0)
			{
				alert("Please Enter The Comments.");
				return false;
			}
			else
			{				
				return true;
			}			
		}

	/*	
		function showTabs(num)
		{
			var wsId = document.getElementById("workSpaceId").value;
			var ndId = document.getElementById("nodeId").value;
			var modeUsers="";
			var divId='#showTabDiv'+num;				
	  		if(num == 0){
	  			modeUsers=document.getElementById("authorMode").value;
	  		}
	  		else if(num == 1){
	  			modeUsers=document.getElementById("reviewerMode").value;
	  		}
	  		else if(num == 2){
	  			modeUsers=document.getElementById("approverMode").value;
	  		}
	  		else if(num == 3)
	  		{
	  			$.ajax
				({
					url : 'EditDocAttr_ex.do?workSpaceId='+wsId+"&nodeId="+ndId,
					beforeSend: function()
					{
						$("#showDocumentAttrtabDiv").html('Loading...');
					},
			  		success: function(data) 
			  		{							
				  		$('#showDocumentAttrtabDiv').html(data);							
					}
				});	
				return true;	
	  		}

	  		if(num != 3)
	  		{
				$.ajax
				({
					url : 'ShowAuthors_ex.do?mode='+ modeUsers+"&workSpaceId="+wsId+"&nodeId="+ndId,
					beforeSend: function()
					{
							$(divId).html('Loading....');
							$('.userDtlData').html('');
					},
			  		success: function(data) 
			  		{														  					  		
							$(divId).html(data);							
					}	  	
				});
	  		}
			
		} 
*/
		function BackToMain()
		{
			getDetails();
		}


		/*
		function dispMsg()
		{
			var stgId = '';
			for (var i=0; i < document.editDocForm.stageId.length; i++)
			{
			   if (document.editDocForm.stageId[i].checked)
			   {
			      var stg_id = document.editDocForm.stageId[i].value;
			   }
			}

			$.ajax
			({
				url : 'ChangeStage_ex.do?stageId='+ stg_id,
		  		success: function(data) 
		  		{
					//$('#showAuthorstabhDiv').html(data);
				}	  	
			});
		}	
		*/	
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center" style="width: 95%"><br>
<div align="right"><img title="Back" alt="Back"
	src="images/Common/Back.png" onclick="BackToMain();"></div>
<div align="center"><s:form name="editDocForm" method="post"
	id="editDocForm" action="SaveDoc_ex">
	<input type="hidden" id="workSpaceId" name="workSpaceId"
		value="<s:property value='workSpaceId'/>" />
	<input type="hidden" id="nodeId" name="nodeId"
		value="<s:property value='nodeId'/>" />
	<input type="hidden" id="userStageId" name="userStageId"
		value="<s:property value='userStageId'/>" />
	<input type="hidden" id="folderName" name="folderName"
		value="<s:property value='folderName'/>" />
	<input type="hidden" id="mode" name="mode" value="edit" />
	<s:if test="userMode == 'false'">
		<div align="left"><label title="Edit Document details"
			class="tabHdrActive"
			style="width: 30%; padding: 5px; margin-left: 2px;">&nbsp;&nbsp;Edit
		Document Details&nbsp;&nbsp;</label>
		<table width="100%"
			style="border: 1px solid #5A8AA9; margin-top: 5px;">
			<tr>
				<td class="title" align="right" width="25%" style="padding: 2px;">Document
				ID&nbsp;</td>
				<td align="left" width="75%"><s:property value="dto.folderName" />
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="padding: 2px;">Document
				Title&nbsp;</td>
				<td align="left"><s:property value="dto.nodeName" /> <input
					type="hidden" name="nodeName"
					value="<s:property value='dto.nodeName'/>"></td>
			</tr>
			<tr>
				<td valign="top" class="title" align="right" width="25%"
					style="padding: 2px; padding-right: 8px;">Document</td>
				<td align="left">
				<table style="font-size: x-small">
					<tr>
						<td valign="top"><input
							<s:if test=" dtoWsNodeHis.fileName == null || dtoWsNodeHis.fileName =='' || dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">disabled="disabled"</s:if>
							id="current" type="radio" name="fileUploading" value="Current"
							checked="checked"><label style="margin-bottom: 2px;"
							for="current">Keep Current</label>&nbsp;&nbsp; <a
							title="<s:property value="dtoWsNodeHis.fileName"/>"
							href="openfile.do?fileWithPath=<s:property value="dtoWsNodeHis.baseWorkFolder"/><s:property value="dtoWsNodeHis.folderName"/>/<s:property value="dtoWsNodeHis.fileName"/>"
							target="_blank"> <s:if
							test="dtoWsNodeHis.fileName != null && dtoWsNodeHis.fileName !='' && !dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">
							<s:if test="dtoWsNodeHis.fileName.contains(\".\")">
								<s:if
									test="dtoWsNodeHis.fileName.substring(dtoWsNodeHis.fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
									<img alt="Open File"
										src="<%=request.getContextPath()%>/js/jquery/tree/skin/pdf_icon_14x14.gif"
										style="border: none;">
								</s:if>
								<s:elseif
									test="dtoWsNodeHis.fileName.substring(dtoWsNodeHis.fileName.indexOf(\".\")+1).equalsIgnoreCase('doc') || dtoWsNodeHis.fileName.substring(dtoWsNodeHis.fileName.indexOf(\".\")+1).equalsIgnoreCase('docx') ">
									<img alt="Open File"
										src="<%=request.getContextPath()%>/js/jquery/tree/skin/icon-doc-14x14.gif"
										style="border: none;">
								</s:elseif>
								<s:else>
									<img alt="Open File"
										src="<%=request.getContextPath()%>/images/file.png"
										style="border: none;">
								</s:else>
							</s:if>
							<s:else>
								<img alt="Open File"
									src="<%=request.getContextPath()%>/images/file.png"
									style="border: none;">
							</s:else>
						</s:if> </a></td>
					</tr>
					<tr>
						<td valign="top"><input
							<s:if test="dtoWsNodeHis.fileName == null || dtoWsNodeHis.fileName =='' || dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">
															checked="checked"
														</s:if>
							id="newFile" type="radio" name="fileUploading" value="New"><label
							for="newFile">New&nbsp;&nbsp;<input type="file"
							id="attachment" name="attachment"></input></label></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="padding: 2px;">Comments&nbsp;</td>
				<td align="left"><input type="text" id="userComments"
					name="userComments" /></td>
			</tr>
			<tr>
				<td class="title" align="right" style="padding: 2px;">Stage&nbsp;</td>
				<td align="left"><input type="checkbox" name="stageDesc"
					id="stageDesc" value="Created" /><label for="stageDesc">&nbsp;&nbsp;Created</label>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="left"><input type="button" class="button"
					value="Save Document" id="saveDocDtl" /></td>
			</tr>
		</table>
		</div>
	</s:if>
</s:form> <br />
<br />
<s:form name="tabForm">
	<s:if test="userMode != 'true'">
		<br />

		<div style="width: 100%; height: 350px;">
		<table align="left" width="10%">
			<tr>
				<td title="Attributes" id="attrTab" class="title"
					style="border: 1px solid;">&nbsp;&nbsp;Attributes&nbsp;&nbsp;</td>
				<td></td>
			</tr>
		</table>
		<div id="attrDataOuter"
			style="border: 1px solid #5A8AA9; float: left; width: 100%; margin-top: -3px;">
		<div align="center" id="attrTabData"
			style="overflow: auto; width: 100%"></div>
		<br />
		</div>
		</div>
	</s:if>
</s:form> <br />
</div>
</div>

<s:if test="userMode != 'true'">
	<script type="text/javascript">
			$('#attrTab').click();
		</script>
</s:if>
</body>
</html>