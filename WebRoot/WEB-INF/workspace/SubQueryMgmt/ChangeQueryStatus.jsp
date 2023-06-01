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
</STYLE>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
		function treeLoad()
		{
			$.ajax({			
 				url: 'GetWorkspaceTree_ex.do?workSpaceId=' + '<s:property value="workSpaceId"/>'+'&mode=ChangeStatus&queryId=<s:property value="queryId"/>',
 				success: function(data) 
 		  		{
 					$('#nodeTreeDiv').html(data);
 					
 					showBranch('branch1',1);
 					swapFolder('folder1');
 				}							 
 			});
		}
 		$(document).ready(function() {
 			$('#popupContactClose').click(function(){
				disablePopup();
			});
 			treeLoad();
 			var options = 
			{
				success: showResponse 
			};
			$('#frmAddQuery').submit(function()
			{	
				if($("#status_sel").val()=="--Select--")
				{				
					alert("Please Select Status");
					$("#status_sel").animate({
						'color':'red'
					});
					
		
					return false;
				}
				if(checkAtleastOneNodes())
				{
					$(this).ajaxSubmit(options);
				}
				return false;									
			});
		});
		$("#status_sel").change(function(){
			$("#status_sel").animate({
				'color':'black'
			});
		});
		
 		function showResponse(responseText, statusText) 
		{			
 			alert(responseText);
			treeLoad();
			document.getElementById("selectAll").checked=false;
			uncheckSelectedNodes()
		}

		function chekcSelectedValue()
		{
			var selectAll= document.getElementById("selectAll").checked;
			if(selectAll)
			{
				checkSelectedNodes();
			}
			else
			{ 
				uncheckSelectedNodes();
			}
		}

 		function checkSelectedNodes()
		{
			 var obj=document.getElementById("queryIdString");
			 var queryIdString=obj.innerHTML;
			 var queryIdArray=queryIdString.split("_");
			 var i;
			 for(i=0;i<(queryIdArray.length-1);i++)
			 {
					obj=document.getElementById("CHK_"+queryIdArray[i]);
					obj.checked=true;
			 }
		}
 		function uncheckSelectedNodes()
		{
			 var obj=document.getElementById("queryIdString");
			 var queryIdString=obj.innerHTML;
			 var queryIdArray=queryIdString.split("_");
			 var i;
			 for(i=0;i<(queryIdArray.length-1);i++)
			 {
					obj=document.getElementById("CHK_"+queryIdArray[i]);
					obj.checked=false;
			 }
		}
 		function checkAtleastOneNodes()
		{
			 var obj=document.getElementById("queryIdString");
			 var chkbox=true;
			 var queryIdString=obj.innerHTML;
			 var queryIdArray=queryIdString.split("_");
			 var i;
			 for(i=0;i<(queryIdArray.length-1);i++)
			 {
					obj=document.getElementById("CHK_"+queryIdArray[i]);					
					if(obj.checked)
						return chkbox;
			 }
			 alert("Please check atleast one Query Node to change the status");
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
Change Query Status</div>
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
	id="frmAddQuery">
<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Project</td>
		<td align="left" class="title" style="color: black;" colspan="3">
		<s:hidden name="workSpaceId"></s:hidden> <s:hidden name="mode"></s:hidden>
		<s:hidden name="queryId"></s:hidden> <s:hidden name="seqNo"></s:hidden>
		<s:property value="workspaceDesc" /></td>
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
		<td align="left" colspan="3"><s:property
			value="dtoSubQueryMst.queryTitle" /></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Date</td>
		<td align="left" width="15%"><s:date
			name="dtoSubQueryMst.startDate" format="MMM-dd-yyyy" /></td>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Resolve Up To</td>
		<td align="left"><s:date name="dtoSubQueryMst.endDate"
			format="MMM-dd-yyyy" /></td>
	</tr>
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Reference Document</td>
		<td align="left" colspan="3"><s:if
			test="dtoSubQueryMst.refDoc != null && dtoSubQueryMst.refDoc != ''">
			<a title="<s:property value="dtoSubQueryMst.refDoc"/>"
				href="openfile.do?fileWithPath=<s:property value="refDocFullPath"/>"
				target="_blank"><img alt="Open File"
				src="<%=request.getContextPath()%>/images/file.png"
				style="border: none;"></a>
		</s:if> <s:else>No Reference Document.</s:else></td>
	</tr>
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Description</td>
		<td align="left" colspan="3"><textarea id="description"
			name="queryDesc" cols="35" readonly="readonly" rows="4"><s:property
			value="dtoSubQueryMst.queryDesc" /></textarea></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Status</td>
		<td align="left" colspan="3"><s:select list="statusValues"
			id="status_sel" name="queryStatus" listKey="top" listValue="top">
		</s:select></td>
	</tr>
	<tr>
		<td valign="top" class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Nodes Affected</td>
		<td align="left" colspan="3"><input type="checkbox"
			id="selectAll" onclick="chekcSelectedValue()"><label
			for="selectAll">Select All</label>

		<div id='nodeTreeDiv' class="bdycls"
			style="border: 1px solid; height: 130px;; width: 400px; overflow: auto;">
		</div>
		</td>
	</tr>
	<tr>
		<td align="right" style="padding-right: 10px; padding-top: 8px;">
		<input type="submit" class="button" value="Save" id="saveQueryBtn">
		</td>
		<td colspan="3"></td>
	</tr>
</table>
</form>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
