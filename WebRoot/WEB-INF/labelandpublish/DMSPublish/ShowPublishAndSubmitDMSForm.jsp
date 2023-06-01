<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	$(document).ready(function() { 
		$("#dateOfSubmission").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});

		$('#projectDtl').click(function() {
		  $('#projectDtlDiv').slideToggle('slow', function() {
		    // Animation complete.
		  });
		});

		var options = 
		{
			success: showResponse 
		};
		$('#frmAddPubDtl').submit(function()
		{	
			if(validateSubDtlForm())
			{
				$(this).ajaxSubmit(options);
			}
			return false;
		});

		$(".showTree").click(function(e){
			var subId = $(this).attr("name");
			//getting height and width of the message box
			var height = $('#treeView').height();
			var width = $('#treeView').width();
			//calculating offset for displaying popup message
			leftVal=e.pageX-(width+10)+"px";
			//topVal=e.pageY-(height-5)+"px";
			topVal=e.pageY+"px";
			$.ajax({			
				url: 'getConfirmedNodeTree_ex.do?WsId='+'<s:property value="WsId"/>'+'&submissionId='+subId,
				success: function(data) 
		  		{
			  		if(data != "0")
			  		{
						//show the popup message and hide with fading effect
						$('#treeView').css({left:leftVal,top:topVal,display:'block',visibility:'visible'}).show();
						$('#treeDetails').html(data);
						showBranch('branch1',1);
	 					swapFolder('folder1');
			  		}
			  		else
				  		alert("No nodes found.");
				}							 
			});
		});

		$('#close').click(function() {
			$('#treeView').hide();
		});
	});
	function showResponse(responseText, statusText) 
	{
		alert(responseText);
		updateGried();
	}
	function updateGried()
	{
		$.ajax({			
			url: 'PublishAndSubmitDMS_ex.do?WsId='+'<s:property value="WsId"/>',
			success: function(data) 
	  		{
		  		//alert(data);
				$('#publishedDtl').html(data);
			}							 
		});
	}
	function validateSubDtlForm()
	{
		var subInfo = document.getElementById('submissionMode').value;
		var subDesc = document.getElementById('submissionDesc').value;
		var dateOfSub = document.getElementById('dateOfSubmission').value;
		if(subInfo=="-1")
		{
			alert("Please select the Submission Mode");
			return false;
		}
		
		if(subDesc.length > 0 && dateOfSub.length > 0)
			return true;
		else
		{
			alert("Please enter the Submission Info or Date Of Submission.");
			return false;
		}
	}
	function confirm(subDtlId,currSeqNo)
	{
		$.ajax({			
			url:'confirmDMSPubSeq_ex.do?WsId='+'<s:property value="WsId"/>'+'&subDtlId='+subDtlId+'&currSeqNo='+currSeqNo,
			success: function(data) 
	  		{
		  		alert(data);
		  		updateGried();
				//$('#publishedDtl').html(data);
			}							 
		});
	}

	var openImg = new Image();
	openImg.src = "images/open.gif";
	var closedImg = new Image();
	closedImg.src = "images/closed.gif";
	
	function showBranch(branch,nodeId)
	{
		var objBranch = document.getElementById(branch).style;
		if(objBranch.display=="block")
			objBranch.display="none";
		else
			objBranch.display="block";
	}

	function swapFolder(img)
	{
		objImg = document.getElementById(img);
		if(objImg.src.indexOf('closed.gif')>-1)
			objImg.src = openImg.src;
		else
			objImg.src = closedImg.src;
	}

</script>
<div style="width: 900px; height: 100%" align="center" id="publishedDtl">
<s:if test="dtoWsMstInfo.lastPublishedVersion != null ">
	<div align="left"
		style="width: 900px; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px;">
	<form action="SaveDMSSubmissionDtl_ex.do" id="frmAddPubDtl"
		method="post" name="frmAddPubDtl"><s:hidden name="WsId" />
	<table width="100%" align="left" style="padding-bottom: 10px;">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; widht: 25%; padding-right: 6px;">Sequence
			No.&nbsp;</td>
			<td align="left"><input type="text" size="6"
				name="currentSeqNumber" id="currentSeqNumber" readonly="readonly"
				value="<s:property value="currentSeqNumber"/>"></td>
			<s:set name="workspaceCurrentSeqNumber" value="currentSeqNumber"></s:set>
		</tr>

		<!-- 		<tr>
						<td class="title" align="right" width="25%" style="padding: 2px; padding-right: 6px;">Submission Type &nbsp;</td>
						<td align="left"><select>
							<s:iterator value="submissionType">
								<option value="<s:property value="subType"/>"><s:property value="subType" /></option>
							</s:iterator>
						</select></td>
					</tr>
				 	-->
	<!--  changing input text to dropdown -->
		
	<!--<input type="text" name="submissionMode" id="submissionMode">
			-->
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Submission Mode&nbsp;</td>
			<td align="left">
			
			<select id="submissionMode" name="submissionMode" >
				
				<option value="-1">--Select--</option>
				<option value="CTD">CTD</option>
				<option value="ACTD">ACTD</option>
				<option value="Paper">Paper</option>
				<option value="Nees">NeeS</option>
				
			</select>
		</td>
		</tr>
		
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Submission
			Info.&nbsp;</td>
			<td align="left"><input type="text" name="submissionDesc"
				id="submissionDesc"></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Date Of Submission
			&nbsp;</td>
			<td align="left"><!-- <s:date name="modifyOn" format="MMM-dd-yyyy"/> -->
			<input id="dateOfSubmission" name="dateOfSubmission" type="text"
				size="13" value="<s:property value="dateOfSubmission"/>"
				readonly="readonly"> (YYYY/MM/DD)</td>	
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Publish And Submit"
				class="button" align="right"  /></td>
		</tr>
	</table>
	</form>
	</div>

	<table class="paddingtable" cellspacing="0" width="100%;"
		bordercolor="#EBEBEB">
		<tr class="headercls" id="projectDtl">
			<td width="95%">Project Detail</td>
			<td width="5%" align="center"><img
				src="<%=request.getContextPath()%>/images/Darrow.gif"></td>
		</tr>
	</table>

	<div id="projectDtlDiv" style="border: 1px solid #669; height: auto;">
	<table class="paddingtable" width="100%;">
		<tr>
			<td width="25%" class="title"><b>Project Name</b></td>
			<td><font color="#c00000"><b><s:property
				value="dtoWsMstInfo.workSpaceDesc" /></b></font></td>
		</tr>
		<tr>
			<td width="25%" class="title"><b>Project Type</b></td>
			<td><font color="#c00000"><b><s:property
				value="dtoWsMstInfo.projectName" /></b></font></td>
		</tr>
		<tr>
			<td width="25%" class="title"><b>Client Name</b></td>
			<td><font color="#c00000"><b><s:property
				value="dtoWsMstInfo.clientName" /></b></font></td>
		</tr>
	</table>
	</div>

	<s:if test="subInfoDMSDtlList.size > 0">
		<br />
		<div align="left"
			style="width: 900px; border: 1px solid #5A8AA9; overflow: auto; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px; min-height: 200px;">
		<table align="center" class="datatable doubleheight" width="100%">
			<thead>
				<tr>
					<th>Id</th>
					<th>Sequence No.</th>
					<!-- <th>Submission Type</th> -->
					<th>Submission Mode</th>
					<th>Submission Info.</th>
					<th>Confirm</th>
					<th>Confirmed By</th>
					<th>Date Of Submission</th>
					<th></th>
				</tr>
			</thead>
			<tbody style="overflow: auto;">
				<s:iterator value="subInfoDMSDtlList" status="status">
					<tr id="row<s:property value="#status.count"/>"
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td><s:property value="subInfoDMSDtlId" /></td>
						<td><s:property value="currentSeqNumber" /></td>
						<!-- <td><s:property value="submissionType" /></td> -->
						<td title="<s:property value="submissionMode"/>"><s:if
							test="submissionMode.length() > 22">
							<s:property value="submissionMode.substring(0,20)" />...
								    </s:if> <s:else>
							<s:property value="submissionMode" />
						</s:else></td>
						<td title="<s:property value="SubmissionDesc"/>"><s:if
							test="SubmissionDesc.length() > 42">
							<s:property value="SubmissionDesc.substring(0,40)" />...
								    </s:if> <s:else>
							<s:property value="SubmissionDesc" />
						</s:else></td>
						<td><s:if
							test="currentSeqNumber == #workspaceCurrentSeqNumber">
							<a
								onclick="confirm('<s:property value="subInfoDMSDtlId" />','<s:property value="currentSeqNumber" />');">Confirm</a>
						</s:if> <s:else>
							<s:if test="confirm == 'Y'">Confirmed</s:if>
							<s:else>-</s:else>
						</s:else></td>
						<s:if test="confirmedBy == '' || confirmedBy == null">
							<td align="center" style="text-align: center">-</td>
						</s:if>
						<s:else>
							<td><s:property value="confirmedBy" /></td>
						</s:else>
						<td><s:date name="dateOfSubmission" format="MMM-dd-yyyy" /></td>
						<td><img name="<s:property value="subInfoDMSDtlId" />"
							class="showTree" alt="Tree View" title="Tree View"
							src="images/Common/tree_view.png"></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<!-- KnowledgeNET's POPUP -->
		<div id="treeView"
			style="display: none; position: absolute; max-height: 160px; width: 300px; top: 0; left: 0; visibility: hidden; background: #EEEEEE;">
		<div style="float: right; width: 100%" align="right"><img
			alt="Close" title="Close" src="images/Common/Close.png" id="close"
			class='popupCloseButton' /></div>
		<div align="center" class="title">Confirmed Node List</div>
		<div id="treeDetails"
			style="max-height: 110px; width: 280px; overflow: auto; border: 1px solid #0C3F62; margin: 10px; margin-top: 0px; background: white;"></div>
		</div>
		</div>
	</s:if>
</s:if> <s:else>
	<br />
	<label class="title" style="font-size: 12px; color: red;">Please
	Select DMS Project.</label>
</s:else></div>

