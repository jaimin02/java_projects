<%@ taglib prefix="s" uri="/struts-tags"%>


<style>
.pagingSettings {
	position: relative;
}

#pdfSettings {
	display: none;
	width: 60%;
	padding: 10px;
}

#headerSettings {
	border-bottom: 1px solid black;
}

#footerSettings {
	border-bottom: 1px solid black;
	margin: 5px;
	padding: 10px;
}

#propertyWindow {
	position: relative;
}
</style>

<script type="text/javascript" src="js/jquery/jquery.form.js"></script>

<script type="text/javascript">



function Validation()
{
	//debugger;
	var subInfo = document.getElementById('submissionMode').value;
	var subDesc = document.getElementById('submissionDesc').value;
	var dateOfSub = document.getElementById('dateOfSubmission').value;
	
	var chkPDFList = document.forms['PDFDocList'].documents;
	var chkNodeList = document.forms['PDFDocList'].nodes;
	
		/* var docCount=0;
		for(i=0;i<chkPDFList.length;i++)
		{
			if(chkPDFList[i].checked ==true)
			{
				
					docCount=docCount+1;
			}
		}
		if(docCount<1)
		{
			alert('Please select atleast one file.');
			return false;
		} */
		
	 if($('[name="documents"]:checked').length<1){
			alert('Please select at least one file.');
			return false;
			} 
		var addLogo = document.forms['PDFDocList'].headerSettings;
		if(addLogo[1].checked)
		{
				var logoFileName=document.forms['PDFDocList'].logoFileName;
				if(logoFileName.value==-1)
				{
					logoFileName.style.background='rgb(255, 230, 247)';
					alert('Please select logo file.');
					return false;
				}
			
		}	

		 if (subInfo == -1) {
			alert("Please select the Submission Mode");
			return false;
		}  

		 if (subDesc.length > 0 && dateOfSub.length > 0 )
			return true;
		 else {
			 	//alert(subDesc.length);
				alert("Please enter the Submission Info or Date Of Submission.");
				return false;
			}
		 
			 
	return true;
}

	
	function paggingSetting(e)
	{
			if(e.checked)
			{
				$(".pagingSettings").show();
			}
			else
			{
				$(".pagingSettings").hide();
			}
	}

	function hideshow(e,id,hfID)
	{
		
		
		if(e.checked)
		{
			$("#"+hfID).show();
			$("#"+id).show();
		}
		else
		{
			$("#"+hfID).hide();	
			$("#"+id).hide();
		}
		
	}
	function hideshowtable(id)
	{
		
		$("#"+id).toggle(500);
	}
	
	function changeLogoMethod()
	{		
						
		if(document.forms['PDFDocList'].logoSelection[0].checked)
		{
			document.getElementById('logoImg').style.display='none';
			document.getElementById('logoSelectionTR').style.display = 'none';
			document.getElementById('UploadLogoTR').style.display = 'block';				
		}
		else
		{
			document.getElementById('logoImg').style.display='';
			document.getElementById('UploadLogoTR').style.display = 'none';
			document.getElementById('logoSelectionTR').style.display = 'block';								
		}
			
	}
	$(document).ready(function() {

		$("#dateOfSubmission").datepicker({
			minDate : '',
			maxDate : '',
			showAnim : 'slideDown',
			dateFormat : 'yy/mm/dd'
		});

	/* 	$('#projectDtl').click(function() {
			$('#submissionDtlmain').slideToggle('slow', function() {
				// Animation complete.
			});
		});  */
		
		/* $('#submissionDtl').click(function() {
			$('#projectDtlDiv').slideToggle('slow', function() {
				// Animation complete.
			});
		});  */
		

		/* 	var options = 
			{
				success: showResponse 
			}; */
		$('#frmAddPubDtl').submit(function() {
			if (validateSubDtlForm()) {
				$(this).ajaxSubmit(options);
			}
			return false;
		});

		$(".showTree")
				.click(
						function(e) {
							//debugger;
							var subId = $(this).attr("name");
							//getting height and width of the message box
							var height = $('#treeView')
									.height();
							var width = $('#treeView').width();
							//calculating offset for displaying popup message
							leftVal = e.pageX - (width + 10)
									+ "px";
							//topVal=e.pageY-(height-5)+"px";
							topVal = e.pageY + "px";
							$
									.ajax({
										url : 'getConfirmedNodeTree_ex.do?workSpaceId='
												+ '<s:property value="workSpaceId"/>'
												+ '&submissionId='
												+ subId,
										success : function(data) {
											if (data != "0") {
												//show the popup message and hide with fading effect
												$('#treeView')
														.css(
																{
																	left : leftVal,
																	top : topVal,
																	display : 'block',
																	visibility : 'visible'
																})
														.show();
												$(
														'#treeDetails')
														.html(
																data);
												showBranch(
														'branch1',
														1);
												swapFolder('folder1');
											} else
												alert("No nodes found.");
										}
									});
						});

		$('#close').click(function() {
			$('#treeView').hide();
		});		
		
		
		var options = {	target: '#linkDiv', 
			beforeSubmit: showRequest
	    };
		$('#PDFDocList').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});

		// Used For Logo change Event
		
		document.getElementById('LoadImgLogo').style.display = 'none';
		//document.getElementById('UploadLogoTR').style.display = 'none';
		
		$('.target').change(function(){
			document.getElementById('LoadImgLogo').style.display = '';
		
			var LogoName=document.getElementById('logoFileName').value;				
			var urlOfAction="ShowLogo_b.do";
			var dataofAction="logoFileName="+LogoName;
		
			if($("#logoFileName").val() == "-1")
			{
				document.getElementById('LoadImgLogo').style.display = 'none';
				return true;
			}
		
			document.getElementById('logoImg').src='ShowLogo_b.do?logoFileName='+LogoName;				
		});

		$("#isTitle").click(function(){
			if(!$(this).is(':checked'))
	        {
			 	$('#addToc').attr('checked',false);
	            
	        }
		});
		$("#addToc").click(function(){
			if($(this).is(':checked'))
	        {
				if(!$('#isTitle').is(':checked'))
			 		$('#addToc').attr('checked',false);
	            
	        }
		});

		
	
	});
	function selectnode(id)
	{
		if($("#"+id).is(':checked')){
			$('#nod'+id).attr('checked',true);
			$('#disp'+id).attr('checked',true);
			$('#headers'+id).attr('checked',true);
			$('#footers'+id).attr('checked',true);	
		}
		else{
			$('#nod'+id).attr('checked',false);
			$('#disp'+id).attr('checked',false);
			$('#headers'+id).attr('checked',false);
			$('#footers'+id).attr('checked',false);
			$('#shrink'+id).attr('checked',false);
		}	
	}
	
	function updateGried() {
		$.ajax({
			url : 'PublishAndSubmitDMS_ex.do?workSpaceId='
					+ '<s:property value="workSpaceId"/>',
			success : function(data) {
				//alert(data);
				$('#publishedDtl').html(data);
			}
		});
	}

	
	function confirmseq(subDtlId, currSeqNo) {
		//debugger;
		if(confirm('Are you sure to Confirm Sequence?')) 
		{
	    	$.ajax({
			url : 'confirmDMSPubSeq_ex.do?workSpaceId='
					+ '<s:property value="workSpaceId"/>' + '&subDtlId='
					+ subDtlId + '&currSeqNo=' + currSeqNo,
			success : function(data) {
				alert(data);
				subinfo();
				//updateGried();
				//$('#publishedDtl').html(data);
			}
		  });
		}
	}
	
	function isValidPositions()
	{
		var logoPosition=document.forms['PDFDocList'].header_logoPosition;
		var headerTextPosition=document.forms['PDFDocList'].headerTextPosition;

		//debugger;
		var footerPageNumberPosition=document.forms['PDFDocList'].footer_pageNumberPosition;
		var footerTextPosition=document.forms['PDFDocList'].footerContentPosition;
		

		var headerSettings=document.forms['PDFDocList'].headerSettings;
		var footerSettings=document.forms['PDFDocList'].footerSettings;

		if(headerSettings[0].checked && headerSettings[1].checked && logoPosition.value==headerTextPosition.value)
		{
				logoPosition.style.background='rgb(255, 230, 247)';
				headerTextPosition.style.background='rgb(255, 230, 247)';
				alert("You can not use same position for Logo and Header Text");
				return false;
		}
		else if(footerSettings[0].checked && footerSettings[1].checked && footerTextPosition.value==footerPageNumberPosition.value)
		{
			footerTextPosition.style.background='rgb(255, 230, 247)';
			footerPageNumberPosition.style.background='rgb(255, 230, 247)';
			alert("You can not use same position for footer text and page number");
			return false;	
		}
		return true;
	}
	function showRequest(formData, jqForm, options) {
		if(!isValidPositions())
		{
			return false;
		}
		
		if(validate())
		{
			$(options.target).html('Loading...');
			$("#PDFDocList").hide();
				return true;
		}
		else 
			return false;
	}
	
	function Lock(subDtlId, currSeqNo){
		//alert("Test"+subDtlId + " "+ currSeqNo);
		//debugger;
		 var remark = prompt("Please specify Reason for Change.");
		 remark = remark.trim();
		if (remark != null && remark != ""){ 
			$.ajax({
				url : 'lockSequence_ex.do?workSpaceId='
						+ '<s:property value="workSpaceId"/>' + '&subDtlId='
						+ subDtlId + '&currSeqNo=' + currSeqNo + "&remark=" + remark,
				success : function(data) {
					//alert(data);
				 	//var out="PublishProject.do";
				 	//location.href=out;
					subinfo();
				}
			});
		}
		else if(remark==""){
			alert("Please specify Reason for Change.");
			return false;
		}
	}
	
	function UnLock(subDtlId, currSeqNo){
		//debugger;
		 var remark = prompt("Please specify Reason for Change.");
		 remark = remark.trim();
		if (remark != null && remark != ""){ 
			$.ajax({
				url : 'unlockSequence_ex.do?workSpaceId='
						+ '<s:property value="workSpaceId"/>' + '&subDtlId='
						+ subDtlId + '&currSeqNo=' + currSeqNo  + "&remark=" + remark,
				success : function(data) {
					 //alert(data);
					// var out="PublishProject.do";
					 //location.href=out;
					 subinfo();
				}
			});
		}
		else{
			alert("Please specify Reason for Change.");
			return false;
		}
	}
	
	function publishHistory(subDtlId, currSeqNo)
	{
		var wsId = '<s:property value="workSpaceId"/>';
		str="showPublishDetailHistory_b.do?workSpaceId="+ wsId  + "&subDtlId="	+ subDtlId + "&currentSeqNumber=" + currSeqNo;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
		win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
		return true;
		
	}

</script>

<div id="statusDetails"></div>
<div id="linkDiv" align="center"></div>
<s:form action="MergePDFs" method="post" name="PDFDocList"
	id="PDFDocList">
	<%--  <s:if test="docDetails.size==0 "> --%>
	<s:if test="docDetails.size==0 "> 
 <%-- <s:if test="flag==false ">   --%>
	<br>
		<center>
		<%--<table style="border: 1 solid black" width="100%" bgcolor="silver">
			<tr>
				<td width="10%" align="right"><img
					src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
				<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
				No Files Found.<br>
				&nbsp;</b></font></td>
			</tr>
		</table>--%>
		<br><br><br>
		<div align="center" style="border: 1px solid #669;">
		<lable class="title">There are no files for Publishing.</lable>
		</div>
		<br><br>
		<hr color="#5A8AA9" size="3px" style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="center">
		<br>
		</center>
	</s:if>
	<%-- <s:else> --%>
	<s:if test="docDetails.size!=0 ">
		<br>
		<div id="showPDFDetail" align="center"
			style="border: 1 solid; padding: 10px;">

		<div style="font-size: 12px; color: black;"><span
			style="font-size: 14px; font-weight: bold;">PDF Documents List
		For : </span> <s:property value="docDetails.get(0).workSpaceDesc" /></div>
		<br>
		<table class="datatable">
			<thead>
				<tr>
					<th style="padding-left: 10px; padding-right: 10px;"><input
						type="checkbox" name="chk" onclick="return chkAll();"
						title="Select All Files"></th>
					<th align="center" style="text-align: center">Header <input
						type="checkbox" name="chkAllHeaderFilesList"
						onclick="return chkAllFiles('header');"
						title="Add Headers in All Files" /></th>
					<th style="text-align: center">Footer <input type="checkbox"
						name="chkAllFooterFilesList"
						onclick="return chkAllFiles('footer');"
						title="Add Footer in All Files" /></th>
					<th style="text-align: center">Shrink <input type="checkbox"
						name="chkAllShrinkFilesList"
						onclick="return chkAllFiles('shrink');" title="Shrink All Files" />
					</th>
					<th>#</th>
					<th>Node Name</th>
					<th>File Name</th>
					<th>User Detail</th>
					<th> Uploading Version</th>
					<th>ModifyOn</th>
				<s:if test ="#session.countryCode != 'IND'">
					<th>Eastern Standard Time</th>
				</s:if>

				</tr>
			</thead>
			<tbody>

				<s:iterator value="docDetails" status="stat">

					<s:iterator value="attrHistory" status="stat">

						<!-- <s:property value="attrName"/> - <s:property value="attrValue"/>
				
				<s:if test="#stat.count!=attrHistory.size">
				,
				<br>&nbsp; 
				</s:if>
	    		-->
					</s:iterator>


				<%-- 	<s:if test="attrNodeName!='no' && attrNodeName!='yes' ">

						<s:if test="#PrevAtrrNodeName=='yes'">
							<s:set name="PrevAtrrNodeName" value="'no'" />
							<tr style="background-color: #702020; color: black; height: 2px;">
								<td style="height: 2px;"></td>
								<td colspan="10" style="height: 2px;"></td>
							</tr>

						</s:if>
						<tr style="background-color: #ABB0B6; color: white;">

							<th colspan="10" style="text-align: left;"><s:property
								value="attrNodeName" /> <s:if test="attrHistory.size>=1">
								<br>
 
								<s:set name="PrevAtrrNodeName" value="'yes'" />
								 <span style="color: white;"> <s:iterator
									value="attrHistory" status="stat">
									<s:property value="attrName" /> - <s:property
										value="attrValue" />
									<s:if test="#stat.count!=attrHistory.size">
										<br>
									</s:if>

								</s:iterator> </span>
 
						 </s:if></th>
						</tr> 
					</s:if>
					 <s:if test="attrNodeName=='no' && #PrevAtrrNodeName=='yes'">
						<s:set name="PrevAtrrNodeName" value="'no'" />
						<tr style="background-color: #702020; color: black; height: 2px;">
							<td colspan="10" style="height: 2px;"></td>
						</tr>
					</s:if> --%>
					<tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>"
						id="tr_<s:property value="nodeId"/>">
						<td style="padding-left: 10px; padding-right: 10px;"><input
							id="<s:property value="nodeId"/>" type="checkbox"
							name="documents"
							value="<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>"
							onclick="selectnode(this.id)"> <input
							id="nod<s:property value="nodeId"/>" type="checkbox" name="nodes"
							value="<s:property value="nodeId"/>" style="display: none" /> <s:if
							test="attrNodeName!='no' && attrNodeName!='yes' ">
							<input id="disp<s:property value="nodeId"/>" type="checkbox"
								name="nodeDisplayName" value="<s:property value="nodeName"/>###"
								style="display: none;" />
						</s:if> <s:else>
							<input id="disp<s:property value="nodeId"/>" type="checkbox"
								name="nodeDisplayName" value="<s:property value="nodeName"/>"
								style="display: none;" />
						</s:else></td>

						<td style="text-align: center"><input
							id="headers<s:property value="nodeId"/>" type="checkbox"
							name="allowHeaderPdfs" value="<s:property value="nodeId"/>" /></td>
						<td style="text-align: center"><input
							id="footers<s:property value="nodeId"/>" type="checkbox"
							name="allowFooterPdfs" value="<s:property value="nodeId"/>" /></td>
						<td style="text-align: center"><input
							id="shrink<s:property value="nodeId"/>" type="checkbox"
							name="allowShrinkPdfs" value="<s:property value="nodeId"/>" /></td>


						<td><s:property value="#stat.count" /></td>
						<td><s:property value="nodeName" /></td>
						<td><a
							href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>"
							target="_blank"><s:property value="fileName" /></a></td>
						<td><s:property value="userName" /></td>
						<td><s:property value="userDefineVersionId" /></td>
						<!-- 
						
						<td><s:property value="stageDesc" /></td>
						 -->
						<!-- <td><s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" />
						</td> -->
						<td><s:property value="ISTDateTime" /></td> 
					<s:if test ="#session.countryCode != 'IND'">
						<td><s:property value="ESTDateTime" /></td>
					</s:if>

					</tr>

				</s:iterator>
			</tbody>
		</table>
		<br></br>
		<span style="display: none;"><input type="checkbox"
			name="isTitle" id="isTitle" value="yes" checked="checked" />Add Node
		Wise Title Page </span><input type="checkbox" name="addToc" id="addToc"
			value="yes" checked="checked" />Generate TOC <input type="checkbox"
			name="addBookmark" value="yes" checked="checked" />Generate Bookmark
		<input type="checkbox" name="addHeader" id="addHeader" value="yes"
			checked="checked"
			onclick="hideshow(this,'headerSettings','tblHeader')" />Add Page
		Header <input type="checkbox" name="addFooter" id="addFooter"
			value="yes" checked="checked"
			onclick="hideshow(this,'footerSettings','tblFooter')" />Add Page
		Footer 		
		<input type="checkbox" name="addCoverPages" id="addCoverPages"
			value="yes" checked="checked"
			onclick="hideshow(this,'tblCoverPage','tblCoverPage')" />Add Cover
		Pages &nbsp;<!-- <input type="Submit" value="Generate" class="button" /> -->
		
		
		<br></br>
		<br />
	<!-- 	<div id="linkDiv" align="center"></div> -->
		<table width="99%" style="cursor: pointer;"
			align="left" onclick="hideshowtable('tblHeader')">
			<tr>
				<td colspan="5"
					style="padding-left: 10px; color: white; border-bottom: 1px solid black; font-weight: bold; background: #5B89AA; height: 20px; margin-top: 10px;">
				Header Settings</td>
			</tr>
		</table>

		<table border="0" width="99%" align="left" id="tblHeader">
			
			<tr>
				<td width="20%"><input type="checkbox" name="headerSettings"
					value="h_addProductName" checked="checked" /> Input Header Text</td>
				<td width="20%"><input type="text" size="22" name="headerText1"
					placeholder="Header Text Line 1"
					value="<s:property value='productName'/>"
					title="Display on generated header" /></td>
				<td width="23%"><input type="text"
					placeholder="Header Text Line 2" size="22" name="headerText2"
					value="<s:property value='productName2'/>"
					title="Display on generated header" /></td>
				<td><select name="headerTextPosition" title="Position">
					<option value="1">---Center---</option>
					<option value="2" selected="selected">Left------</option>
					<option value="3">------Right</option>

				</select> <input type="color" id="headerTextColor" name="headerTextColor"
					style="display: none;" /></td>
			</tr>
			<tr style="height: 52px;">
				<td><input type="checkbox" name="headerSettings"
					value="h_addLogo"/> Add Logo</td>

				<td>
				<div id="logoSelectionTR"><s:select list="logoNameList"
					name="logoFileName" listKey="top" listValue="top" headerKey="-1"
					headerValue="Select Logo" theme="simple" cssClass="target"
					id="logoFileName" cssStyle="padding: 3px;">
				</s:select></div>
				</td>
				<td><select name="header_logoPosition" title="Position"
					style="margin-top: 5px;">
					<option value="1">---Center---</option>
					<option value="2">Left------</option>
					<option value="3" selected="selected">------Right</option>

				</select></td>

				<td>
				<div id="LoadImgLogo"><img src="" id="logoImg"
					alt="No Logo Selected" height="45px" width="100px"
					title="Select Logo Image" style="padding: 3px;"></div>
				</td>
			</tr>
			<tr>

			</tr>
			<tr>
				<td><input type="checkbox" name="headerSettings"
					value="h_addNodeName" checked="checked"/ > Add Node Title
				</td>
				<td><select name="nodeTitlePosition" title="Position"
					style="margin-top: 5px;">
					<option value="1">---Center---</option>
					<option value="2">Left------</option>
					<option value="3" selected="selected">------Right</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="headerSettings"
					value="h_addLine" checked="checked"/ > Add Line</td>
				<td><select style="display: none;">
					<option value="1">Solid</option>
					<option value="2">Dashed</option>
				</select></td>
			</tr>
		</table>
		<table width="99%" style="cursor: pointer;"
			align="left" onclick="hideshowtable('tblFooter')">
			<tr>
				<td colspan="3"
					style="padding-left: 10px; color: white; border-bottom: 1px solid black; font-weight: bold; background: #5B89AA; height: 20px; margin-top: 10px;">
				Footer Settings</td>
			</tr>
		</table>
		<table id="tblFooter"  width="99%" align="left">
			<tr>
				<td width="20%"><input type="checkbox" name="footerSettings"
					value="f_addPageNumber" checked="checked" /> Add Page Number</td>
				<td width="20%"><select name="footer_pageNumberStyle"
					title="Number Style">
					<option value="1" selected="selected">1,2,3,...</option>
					<option value="2">1 of N</option>
				</select></td>
				<td><select name="footer_pageNumberPosition" title="Position">
					<option value="1" selected="selected">---Center---</option>
					<option value="2">Left------</option>
					<option value="3">------Right</option>

				</select></td>

			</tr>
			<tr>
				<td><input type="checkbox" name="footerSettings"
					value="f_addVersion" checked="checked" /> Input Footer Text</td>
				<td><input type="text" size="22" name="footer_version"
					value="<s:property value='version'/>" title="Version" /></td>

				<td><select name="footerContentPosition" title="Position">
					<option value="2" selected="selected">Left------</option>
					<option value="1">---Center---</option>
					<option value="3">------Right</option>

				</select></td>
			</tr>
			<tr>
				<td><input title="Add Line" type="checkbox"
					name="footerSettings" value="f_addLine" checked="checked"/ >
				Add Line</td>
			</tr>
		</table>
		<table width="99%" style="cursor: pointer;"
			align="left" onclick="hideshowtable('tblCoverPage')">
			<tr>
				<td colspan="4"
					style="padding-left: 10px; color: white; border-bottom: 1px solid black; font-weight: bold; background: #5B89AA; height: 20px; margin-top: 10px;">
				Cover Page Settings</td>
			</tr>
		</table>
		<table id="tblCoverPage" width="99%"
			align="left">
			<tr>
				<td width="20%">Product Name :</td>
				<td width="20%"><textarea rows="4" cols="25"
					name="coverpage_productname"><s:property value='coverpage_productname'/></textarea></td>
				<td><select name="coverpage_fontsize" title="Position">
					<option value="26" selected="selected">---Font Size---</option>
					<option value="8">08</option>
					<option value="10">10</option>
					<option value="12">12</option>
					<option value="14">14</option>
					<option value="16">16</option>
					<option value="18">18</option>
					<option value="20">20</option>
					<option value="22">22</option>
					<option value="24">24</option>
					<option value="26">26</option>
					<option value="28">28</option>
					<option value="30">30</option>
					<option value="32">32</option>
					<option value="34">34</option>
					<option value="36">36</option>
					<option value="38">38</option>
					<option value="40">40</option>
					<option value="42">42</option>
					<option value="44">44</option>
					<option value="46">46</option>
					<option value="48">48</option>
				</select>
				</td><td>
				<select name="coverpage_fontstyle" title="Font Style" >
					<option value="0" selected="selected">---Font Style---</option>
					<option value="0">Normal</option>
					<option value="1">Bold</option>
					<option value="2">Italic</option>
					<option value="3">Bold/Italic</option>

				</select>
				</td>
				<td>
				<select name="coverpage_fontname" title="Font Name">
					<option value="Times" selected="selected">---Font Name---</option>
					<option value="arial.ttf">Arial</option>
					<option value="arialblack.ttf">Arial Black</option>
					<option value="Times">Times New Roman</option>
					<option value="BOOKOS.TTF">Bookman Old Style</option>
					<option value="CALIFR.TTF">Californian FB</option>
					<option value="calibri.ttf">Calibri (Body)</option>
					<option value="cambriab.ttf">Cambria</option>
					<option value="COOPBL.TTF">Cooper Black</option>
					<option value="verdana.ttf">Verdana</option>
										
				</select>
				
				</td>
			</tr>
			<tr>
				<td width="20%">Submitted By :</td>
				<td width="20%"><textarea rows="4" cols="25"
					name="coverpage_submittedby"><s:property value='coverpage_submittedby'/></textarea></td>
				<td><select name="coverpage_fontsizeby" title="Position">
					<option value="16" selected="selected">---Font Size---</option>
					<option value="8">08</option>
					<option value="10">10</option>
					<option value="12">12</option>
					<option value="14">14</option>
					<option value="16">16</option>
					<option value="18">18</option>
					<option value="20">20</option>
					<option value="22">22</option>
					<option value="24">24</option>
					<option value="26">26</option>
					<option value="28">28</option>
					<option value="30">30</option>
					<option value="32">32</option>
					<option value="34">34</option>
					<option value="36">36</option>
					<option value="38">38</option>
					<option value="40">40</option>
					<option value="42">42</option>
					<option value="44">44</option>
					<option value="46">46</option>
					<option value="48">48</option>
				</select></td>
				<td>
				<select name="coverpage_subbyfontstyle" title="Font Style" >
					<option value="0" selected="selected">---Font Style---</option>
					<option value="0">Normal</option>
					<option value="1">Bold</option>
					<option value="2">Italic</option>
					<option value="3">Bold/Italic</option>
				</select>
				</td>
				<td>
				<select name="coverpage_subbyfontname" title="Font Name">
					<option value="Times" selected="selected">---Font Name---</option>
					<option value="arial.ttf">Arial</option>
					<option value="arialblack.ttf">Arial Black</option>
					<option value="Times">Times New Roman</option>
					<option value="BOOKOS.TTF">Bookman Old Style</option>
					<option value="CALIFR.TTF">Californian FB</option>
					<option value="calibri.ttf">Calibri (Body)</option>
					<option value="cambriab.ttf">Cambria</option>
					<option value="COOPBL.TTF">Cooper Black</option>
					<option value="verdana.ttf">Verdana</option>
				</select>
				
			</tr>
			<tr>
				<td width="20%">Submitted To :</td>
				<td width="20%"><textarea rows="4" cols="25"
					name="coverpage_submittedto"><s:property value='coverpage_submittedto'/></textarea></td>
				<td><select name="coverpage_fontsizeto" title="Position">
					<option value="16" selected="selected">---Font Size---</option>
					<option value="8">08</option>
					<option value="10">10</option>
					<option value="12">12</option>
					<option value="14">14</option>
					<option value="16">16</option>
					<option value="18">18</option>
					<option value="20">20</option>
					<option value="22">22</option>
					<option value="24">24</option>
					<option value="26">26</option>
					<option value="28">28</option>
					<option value="30">30</option>
					<option value="32">32</option>
					<option value="34">34</option>
					<option value="36">36</option>
					<option value="38">38</option>
					<option value="40">40</option>
					<option value="42">42</option>
					<option value="44">44</option>
					<option value="46">46</option>
					<option value="48">48</option>
				</select></td>
				<td>
				<select name="coverpage_subtofontstyle" title="Font Style" >
					<option value="0" selected="selected">---Font Style---</option>
					<option value="0">Normal</option>
					<option value="1">Bold</option>
					<option value="2">Italic</option>
					<option value="3">Bold/Italic</option>
				</select>
				</td>
				<td>
				<select name="coverpage_subtofontname" title="Font Name">
					<option value="Times" selected="selected">---Font Name---</option>
					<option value="arial.ttf">Arial</option>
					<option value="arialblack.ttf">Arial Black</option>
					<option value="Times">Times New Roman</option>
					<option value="BOOKOS.TTF">Bookman Old Style</option>
					<option value="CALIFR.TTF">Californian FB</option>
					<option value="calibri.ttf">Calibri (Body)</option>
					<option value="cambriab.ttf">Cambria</option>
					<option value="COOPBL.TTF">Cooper Black</option>
					<option value="verdana.ttf">Verdana</option>
				</select>
				
			</tr>
		</table>
		
		<table width="99%" style="cursor: pointer;"
			align="left" onclick="hideshowtable('tblTOC')">
			<tr>
				<td colspan="3"
					style="padding-left: 10px; color: white; border-bottom: 1px solid black; font-weight: bold; background: #5B89AA; height: 20px; margin-top: 10px;">
				TOC Settings</td>
			</tr>
		</table>
		<table id="tblTOC" id="tocSettings" width="99%" align="left">
			<tr>
				<td width="20%"><input type="checkbox" name="tocSettings"
					value="toc_modulewisetoc" checked="checked" /> Module Wise TOC</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><input type="checkbox" name="tocSettings"
					value="toc_modulewisenumber" checked="checked" /> Module Wise Page Numbering</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="20%"><input type="checkbox" name="tocSettings"
					value="toc_addPageNumber" checked="checked" /> Add Page Number</td>
				<td width="20%"><select name="tocPageNumberStyle"
					title="TOC Number Style">
					<option value="1" selected="selected">1,2,3,...</option>
					<option value="2">1 of N</option>
				</select></td>
			</tr>
			<tr>
				<td width="20%">Formatting</td>
				<td width="20%"><select name="tocFontSize"
					title="TOC Font Size">
					<option value="12" selected="selected">---Font Size---</option>
					<option value="8">08</option>
					<option value="10">10</option>
					<option value="12">12</option>
					<option value="14">14</option>
					<option value="16">16</option>
					<option value="18">18</option>
					<option value="20">20</option>
					<option value="22">22</option>
					<option value="24">24</option>
					<option value="26">26</option>
					<option value="28">28</option>
					<option value="30">30</option>
					<option value="32">32</option>
					<option value="34">34</option>
					<option value="36">36</option>
					<option value="38">38</option>
					<option value="40">40</option>
					<option value="42">42</option>
					<option value="44">44</option>
					<option value="46">46</option>
					<option value="48">48</option>
				</select>
				</td>
			</tr>
			
		</table>
		
		<br>
		</div>
		
		<div id="publish&submitId">
		<table width="99%" style="cursor: pointer;" align="left"
				onclick="hideshowtable('tblTOC')">
				<tr>
					<td colspan="2"
						style="padding-left: 10px; color: white; border-bottom: 1px solid black; font-weight: bold; background: #5B89AA; height: 20px; margin-top: 10px; margin-right: 40px">
						Publish And Submission Detail</td>
				</tr>
			</table>
	</div>

		<div style="width: 900px; height: 100%" align="center"
			id="submissionDtl">
			<div align="left" id="submissionDtlmain"
				style="width: 900px; padding-bottom: 20px; padding-left: 3px; padding-right: 3px; padding-top: 3px;">
				<table width="100%" align="left" style="padding-bottom: 10px;">
					<tr>
						<td class="title" align="right" width="25%"
							style="padding: 2px; widht: 25%;">Sequence No.&nbsp;</td>
						<td align="left"><input type="text" size="6"
							name="currentSeqNumber" id="currentSeqNumber" readonly="readonly"
							value="<s:property value="currentSeqNumber"/>"></td>
						<s:set name="workspaceCurrentSeqNumber" value="currentSeqNumber"></s:set>
					</tr>


					<tr>
						<td class="title" align="right" width="25%" style="padding: 2px;">Submission
							Mode&nbsp;</td>
						<td align="left"><select id="submissionMode"
							name="submissionMode">

								<option value="-1">--Select--</option>
								<option value="CTD">CTD</option>
								<option value="ACTD">ACTD</option>
								<option value="Paper">Paper</option>
								<option value="Nees">NeeS</option>

						</select></td>
					</tr>

					<tr>
						<td class="title" align="right" width="25%" style="padding: 2px;">Submission
							Info.&nbsp;</td>
						<td align="left"><input type="text" name="submissionDesc"
							id="submissionDesc"></td>
					</tr>

					<tr>
						<td class="title" align="right" width="25%" style="padding: 2px;">Date
							Of Submission &nbsp;</td>
						<td align="left">
							<!-- <s:date name="modifyOn" format="MMM-dd-yyyy"/> --> <input
							id="dateOfSubmission" name="dateOfSubmission" type="text"
							size="13" value="<s:property value="dateOfSubmission"/>"
							readonly="readonly"> (YYYY/MM/DD)
						</td>
					</tr>
					<tr>
						<td></td>
						
					</tr>
				</table>
			</div>
			</s:if>
			<table class="paddingtable" cellspacing="0" width="100%;"
				bordercolor="#EBEBEB">
				<tr class="headercls" id="projectDtl">
					<td width="100%" style="background: #5B89AA;">Project Detail</td>
					
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
			<s:if test="docDetails.size!=0 && allFileApproved==true">
			<br>
			<input type="submit" value="Publish And Submit"
							class="button" align="right" onclick="return Validation()"/><br></s:if>

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
							<s:if test ="#session.countryCode != 'IND'">
								<th>Eastern Standard Time</th>
							</s:if>
								<th>Submission Path</th>
								<!-- <th></th> -->
								<s:if test ="eTMFCustomization == 'yes'">
								  <s:if test="userTypeName == 'WA'">
								<th>Lock</th>
								<th>Reason for Change</th>
								  </s:if>
								 <th></th>
								</s:if>
							</tr>
						</thead>
						<tbody style="overflow: auto;">
							<s:iterator value="subInfoDMSDtlList" status="status">
							
								<%-- <tr id="row<s:property value="#status.count"/>"
									class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>"> --%>
									
							<tr id="row<s:property value="#status.count"/>"
								class="<s:if test="StatusIndi == 'L'">
				    					lockFound
				    				</s:if>
				    				<s:else> even </s:else>">
				    				
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
												onclick="confirmseq('<s:property value="subInfoDMSDtlId" />','<s:property value="currentSeqNumber" />');">Confirm</a>
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
									<!--  <td><s:date name="dateOfSubmission" format="dd-MMM-yyyy HH:mm" /></td> -->
									
									<td><s:property value="ISTDateTime" /></td> 
								<s:if test ="#session.countryCode != 'IND'">
									<td><s:property value="ESTDateTime" /></td>
								</s:if>
									
									
							<s:if test="SubmissionPath == '' || SubmissionPath == null">
								<td align="center" style="text-align: center">-</td>
								
							</s:if> 
							<s:else>
									<td><div align="center"><a
									href="file:<s:property value="SubmissionPath"/>"
									title="<s:property value="SubmissionPath"/>"> <img
									border="0px" alt="Open" src="images/Common/open.png"
									height="18px" width="18px"> </a></div></td>
							</s:else>
							<%-- <td><img name="<s:property value="subInfoDMSDtlId" />"
										class="showTree" alt="Tree View" title="Tree View"
										src="images/Common/tree_view.png">
							</td> --%>
								<s:if test ="eTMFCustomization == 'yes'">
								  <s:if test="userTypeName == 'WA'">
									<td>
									<s:if test="confirm == 'Y' && StatusIndi !='L'">
									    <a onclick="Lock('<s:property value="subInfoDMSDtlId" />','<s:property value="currentSeqNumber" />');">Lock</a>
									</s:if>
									<s:elseif test="StatusIndi=='L'">
									    <a onclick="UnLock('<s:property value="subInfoDMSDtlId" />','<s:property value="currentSeqNumber" />');">UnLock</a>
									</s:elseif>
									<s:else>-</s:else>
									</td>
									   <s:if test="remark == '' || remark == null">
									      <td align="center" style="text-align: center">-</td>
										</s:if>
										<s:else>
										   <td  align="center" style="text-align: center"><s:property value="remark"/></td>
									   </s:else>
								   </s:if>
								   <s:if test="confirm == 'Y'">
								 	<td>
								 		<div align="center"><a title="Publish History" href="#"
								 		onclick="publishHistory('<s:property value="subInfoDMSDtlId" />','<s:property value="currentSeqNumber" />')">
										<img border="0px" alt="Audit Trail" src="images/Common/details.png" height="20px" width="20px"> </a></div>
								 	</td>
								 </s:if>
								 <s:else> 
								 	 <td align="center" style="text-align: center">-</td>
								 </s:else>
								</s:if>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<!-- KnowledgeNET's POPUP -->
					<div id="treeView"
						style="display: none; position: absolute; max-height: 160px; width: 300px; top: 0; left: 0; visibility: hidden; background: #EEEEEE;">
						<div style="float: right; width: 100%" align="right">
							<img alt="Close" title="Close" src="images/Common/Close.png"
								id="close" class='popupCloseButton' />
						</div>
						<div align="center" class="title">Confirmed Node List</div>
						<div id="treeDetails"
							style="max-height: 110px; width: 280px; overflow: auto; border: 1px solid #0C3F62; margin: 10px; margin-top: 0px; background: white;"></div>
					</div>
				</div>
			</s:if>
		</div>
		
		<s:hidden name="workSpaceId"></s:hidden>


	<%-- </s:else> --%>

</s:form>