<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<STYLE>
.trigger {
	CURSOR: hand
}

.branch {
	DISPLAY: none;
	MARGIN-LEFT: 16px
}

#confirmDialog {
	border: 2px solid white;
	background-color: black;
	color: white;
	border-radius: 10px;
	font-weight: bolder;
}
</STYLE>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
 		$(document).ready(function() {
			$('#popupContactClose').click(function(){
				disablePopup();
			});
 			$.ajax({			
 				url: 'GetWorkspaceTree_ex.do?workSpaceId=' + '<s:property value="workSpaceId"/>',
 				success: function(data) 
 		  		{
 					$('#nodeTreeDiv').html(data);
 					showBranch('branch1',1);
 					swapFolder('folder1');
 					checkSelectedNodes();
 				}							 
 			});
 		});
 		
		function check()
		{
			var sUser = userCodedtl.value;
			
			if(sUser == "null" || sUser == "")
			{
				 $("#confirmDialog").fadeIn();
				
				
			}
			else
			{
				 $("#confirmDialog").fadeOut();
			
			}
		}
		
	    $(document).ready(function() {

		    $("#confirmDialog").show();
		   
			$(".queryDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
			/*
			$("#endDate").blur(function() {
				 var startDateValue = $("#startDate").val();
				 var endDateValue =  $("#endDate").val();
				 if(Date.parse(startDateValue) > Date.parse(endDateValue))
				 {
					alert("You can not select less then start date.");
				 }
			 });
			 */
				 			
			var options = 
			{
				success: showResponse 
			};
			$('#frmAddQuery').submit(function()
			{	
				//debugger;
				var sUser = userCodedtl.value;
				
				if(sUser == "null" || sUser == "")
				{
					 $("#confirmDialog").show();
					
					
				}
				
				if(validateQueryForm())
				{
					$(this).ajaxSubmit(options);
					 $("#confirmDialog").hide();
				}
				return false;
			});
			
		});
	    function showResponse(responseText, statusText) 
		{
			alert(responseText);
			disablePopup();
			$.ajax({			
				url: 'ShowQueryDetails_ex.do?workSpaceId='+ '<s:property value="workSpaceId"/>'+'&mi=<s:property value="mi"/>',
				success: function(data) 
		  		{
			  		//alert(data);
					$('#workspaceDetail').html(data);
					
					
				}							 
			});
		}
	    function checkSelectedNodes()
		{
			 var queryId=document.getElementById("queryIdString");
			 var queryIdString=queryId.innerHTML;
		
			 var queryIdArray=queryIdString.split("_");
			 var i;
			 for(i=0;i<(queryIdArray.length-1);i++)
			 {
					var objCheckBox=document.getElementById("CHK_"+queryIdArray[i]);										
					objCheckBox.checked=true;


					showAllNodeInTree(queryIdArray[i]);
						/*
					$(objCheckBox).parents('.trigger').map(function () { 
			               alert(this.tagName);
			        });
				        */
			 }
		}


		function showAllNodeInTree(queryIdArray)
		{
			var a =$("#CHK_"+queryIdArray)
            .parents(".trigger")
            .click();
           
            
		}
		
		
		function validateQueryForm()
		{
			var QueryTitle=document.getElementById("queryTitleId");
			var Description=document.getElementById("description");
			var str="";
			var elem = document.getElementById('frmAddQuery').elements;
			var StartDate=document.getElementById("startDateId");			
			var EndDate=document.getElementById("endDateId");
			
			var QueryTitleString=QueryTitle.value;
			var DescriptionString=Description.value;
			var ObjRegExp = /(^[\sa-zA-Z0-9_-]+)$/;
			var ObjRegExpDesc = /(^[\sa-zA-Z0-9_-]*)$/;

			if(QueryTitle.value=="")
			{
					alert("Please enter Query Title...");
					QueryTitle.focus();						
					return false;
			}
			if(ObjRegExp.test(QueryTitleString))
			{								
				if(StartDate.value>EndDate.value)
				{
					alert("Resolved UpTo Date cannot be less then Query Date...");
					return false;
				}
				else
				{
					if(ObjRegExpDesc.test(DescriptionString))
						{
							
							for(var i = 0; i < elem.length; i++)
					        {
					            if(elem[i].type=="checkbox")
					            {
						            
					            	if(elem[i].checked)
					            	{		            	
						            	return true;							
					            	}
					            }
					        }
						}				
					else
					{
						alert("Please do not use &,%,!,=,^,$,#,@ ( ) \+ in project description");
						Description.focus();
						return false;
					}
				}
			}
			else
			{
				 alert("Please do not use &,%,!,=,^,$,#,@ ( ) + in project Title");
				 QueryTitle.focus();
				 return false;
			}

			alert("Please select at least one node from the Structure...");			
			return false;									
		}
		
	</script>
</head>
<body>
<div style="display: none;" id="queryIdString"><s:property
	value="queryIdString" /></div>
<a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">
<s:property value="mode" /> Query Details</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 410px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center">
<div id="addQueryDtl" style="height: 100%; width: 100%;">
<div align="center">
<div style="padding-left: 3px; width: 650px;" align="center">
<div align="left">
<form action="SaveQueryDetails_ex.do" method="post" name="SubQueryMgmt"
	enctype="multipart/form-data" id="frmAddQuery">
<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Project</td>
		<td align="left" class="title" style="color: black;"><s:hidden
			name="workSpaceId"></s:hidden> <s:hidden name="mode"></s:hidden> <s:hidden
			name="queryId"></s:hidden> <s:hidden name="seqNo"></s:hidden> <s:property
			value="workspaceDesc" /></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Last Confirmed Seq.</td>
		<td align="left" class="title"
			style="color: black; font-weight: lighter"><s:property
			value="seqNo" /></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Title</td>
		<td align="left"><s:textfield name="queryTitle" size="35"
			id="queryTitleId"></s:textfield></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Date</td>
		<td align="left"><input type="text" name="startDate"
			readonly="readonly" id="startDateId" class="queryDate"
			value="<s:property value='startDate'/>"> (YYYY/MM/DD)</td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Resolve Up To</td>
		<td align="left"><input type="text" name="endDate"
			readonly="readonly" id="endDateId" class="queryDate"
			value="<s:property value='endDate'/>"> (YYYY/MM/DD)</td>
	</tr>
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Reference Document</td>
		<td align="left"><s:if test="mode == 'Add'">
			<input type="file" name="refDocUpload"></input>
		</s:if> <s:if test="mode == 'Edit'">
			<table style="font-size: x-small">
				<tr>
					<td valign="top"><input
						<s:if test="dtoSubQueryMst.refDoc == null || dtoSubQueryMst.refDoc ==''">disabled="disabled"</s:if>
						id="current" type="radio" name="fileUploading" value="Current"
						checked="checked"><label style="margin-bottom: 2px;"
						for="current">Keep Current</label>&nbsp;&nbsp; <a
						title="<s:property value="dtoSubQueryMst.refDoc"/>"
						href="openfile.do?fileWithPath=<s:property value="refDocFullPath"/>"
						target="_blank"> <s:if
						test="dtoSubQueryMst.refDoc != null && dtoSubQueryMst.refDoc !=''">
						<img alt="Open File"
							src="<%=request.getContextPath()%>/images/file.png"
							style="border: none;">
					</s:if> </a></td>
				</tr>
				<tr>
					<td valign="top"><input
						<s:if test="dtoSubQueryMst.refDoc == null || dtoSubQueryMst.refDoc ==''">
																	checked="checked"
																</s:if>
						id="newFile" type="radio" name="fileUploading" value="New"><label
						for="newFile">New&nbsp;&nbsp;<input type="file"
						name="refDocUpload"></input></label></td>
				</tr>
				<tr>
					<td valign="top"><input
						<s:if test="dtoSubQueryMst.refDoc == null || dtoSubQueryMst.refDoc ==''">disabled="disabled"</s:if>
						id="remove" type="radio" name="fileUploading" value="Remove"><label
						for="remove">Remove</label></td>
				</tr>
			</table>
		</s:if></td>
	</tr>
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Description</td>
		<td align="left"><textarea id="description" name="queryDesc"
			cols="35" rows="4"><s:if
			test="mode.equalsIgnoreCase('edit')">
			<s:property value="queryDesc" />
		</s:if></textarea></td>
		<td align="left" width="75%"
			style="padding-left: 7px; padding-bottom: 10px"><font
			style="font-style: normal; font-size: 12px; font-weight: bold;"
			class="title label">Select Users: </font> <s:select list="users"
			name="userCodedtl" size="6" cssStyle="width:75%;" multiple="true"
			listKey="userCode" listValue="loginName" onclick="check();">
		</s:select></td>


	</tr>
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Nodes Affected</td>
		<td align="left">
		<div id='nodeTreeDiv' class="bdycls"
			style="border: 1px solid; height: 100px;; width: 400px; overflow: auto;">
		</div>
		</td>
	</tr>
	<tr>
		<td align="right" style="padding-right: 10px; padding-top: 8px;">
		<input type="submit" class="button" value="Save" id="saveQueryBtn">
		</td>
	</tr>
</table>
</form>
</div>
</div>
</div>
</div>
</div>

<div id="confirmDialog"
	style="position: fixed; bottom: 5px; right: 15px; z-index: 2147483647; padding: 8px">
<span id="confirmmsg">You have not selected any user(s).</span></div>

</body>
</html>