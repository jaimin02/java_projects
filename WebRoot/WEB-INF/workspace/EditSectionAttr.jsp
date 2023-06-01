<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>

<style type="text/css">
#err {
	color: red;
	font-size: 14px;
	text-align: center;
}

input[type="checkbox"][readonly] {
  pointer-events: none;
 }
#usertable_filter input {
background-color: #2e7eb9;
color:#ffffff;
}
#usertable_length select {
background-color: #2e7eb9;
color:#ffffff;
}

.fs-dropdown {
    width: 45% !important;
}
</style>


<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>

<link href="<%=request.getContextPath()%>/css/fSelect.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>
	
<s:head />

<script type="text/javascript">
//var userType;

	$(document).ready(function() { 
		//debugger;
		//var scriptCodeId = document.getElementById("Script Code").id;
		//document.getElementById(scriptCodeId).readOnly = true;
		$("#remark").val("");
		 /* $('#usertable').dataTable( {
				
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
				
		}); */
		
		$('.assignUsers').fSelect({
    	    placeholder: 'Select value',
    	    numDisplayed: 3,
    	    overflowText: '{n} selected',
    	    noResultsText: 'No data found',
    	    searchText: 'Search',
    	    showSearch: true,
    	    title : 'test'
    	    
    	});
		
		$('.target').change(function(){
			//debugger;	
			var correct=true;
			var docName=document.getElementById('docName').value;
			docName = docName.trim();
			//docName=document.getElementById('docName').value; 
			var WorkspaceId = '<s:property value="WorkspaceId"/>';
			var urlOfAction="DocNameExistsForUpdate_ex.do";
			var dataofAction="docName="+docName+"&WorkspaceId="+WorkspaceId;
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
						if(response.indexOf('Available') == -1){
							document.getElementById('Save').disabled='disabled';
							return false;
						}
						else if(response.indexOf('Available') != -1)
						{
							document.getElementById('Save').disabled='';
							return true;
						}
					}
				});
			}
		});
	
	});
		function refreshParent() 
		{
			window.opener.parent.history.go(0);
			self.close();
		}
		function Filevalidate()
		{
			//debugger;
			var foldername = document.getElementById('folderName').value;
			var lbl_folderName = '<s:property value="lbl_folderName"/>';
			var txtAttr = document.getElementsByClassName("txt");
	    	var dateAttr = document.getElementsByClassName("date");
	    	var scriptCodeId = document.getElementById("Script Code").id;
	    	var ScriptCodeAutoGenrate = '<s:property value="isScriptCodeAutoGenrate"/>';
	    	
	    	if(foldername==null || foldername==""){
	    		alert("Please specify "+lbl_folderName+".");
	    		document.getElementById('folderName').focus();
	    		document.getElementById("folderName").style.backgroundColor="#FFE6F7";
	    		return false;
	    	}
	    	if(txtAttr.lengh != 0){
	    	for (var i = 0; i < txtAttr.length; i++) {
	    		 var attrVal = txtAttr[i].value;
	    		 attrVal= attrVal.trim();
	    		 if(attrVal=="" && scriptCodeId !="Script Code"){
	    			 alert("Please specify attribute value.");
	    			 return false;
	    		 }
	    	 }
	    	for (var i = 0; i < txtAttr.length; i++) {
	    		 var attrVal = txtAttr[i].value;
	    		 attrVal= attrVal.trim();
	    		 if(attrVal=="" && scriptCodeId =="Script Code" && ScriptCodeAutoGenrate=='No' ){
	    			 alert("Please specify attribute value.");
	    			 return false;
	    		 }
	    	 }
	    	if(dateAttr.lengh != 0){
	    		for (var i = 0; i < dateAttr.length; i++) {
	    			 var attrVal = dateAttr[i].value;
	    			 attrVal= attrVal.trim();
	    			 if(attrVal==""){
	    				 alert("Please specify attribute value.");
	    				 return false;
	    			 }
	    		 }
	    	 }
	    	var remark = document.getElementById("remark").value;
	    	if (remark == null || remark == ""){
	    		alert("Please specify reason for change.");
	    		document.getElementById('remark').focus();
	    		document.getElementById("remark").style.backgroundColor="#FFE6F7";
	    		return false;
	    		}
	    	}
			return true;
		}

</script>

</head>
<body>
<div class="errordiv"><s:fielderror></s:fielderror> <s:actionerror />
</div>

<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float:left;">Edit Attribute Detail</b></div>
<br>

</div>
<br>

<s:form action="EditSectionAttrs" method="post" id="addUsertoProjectForm" name="addUsertoProjectForm" >
<div align="center" style="color: green;"><s:actionmessage /></div>
<s:hidden name="workspaceID"></s:hidden>
<s:hidden name="nodeId"/>
<table style="align:center; width:95%;">
<div id="err"><s:if test="errorMsg==null || errorMsg == ''">&nbsp;</s:if>
		<s:else>
			<s:property value="errorMsg" />
		</s:else></div>
			<tr>
				<td colspan="2">
				
			<s:if test="attrDtl.size == 0">
				<tr>
					<td class="title" colspan="2">
					<center><b> No details available.</b></center>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td width="100%" align="center"><s:submit value="Update"
						cssClass="button">
					</s:submit> &nbsp;&nbsp; <input type="button" class="button" value="Close"
						onclick="refreshParent();" /></td>
				</tr>
			</s:if>

			<s:else>
				<%int elementid=1;%>
				<tr>
							<td class="title" align="right" width="45%"
								style="padding: 2px; padding-right: 8px;"><s:property value="lbl_nodeName"/></td>
							<td align="left">
							<input class="txt" type="text" id="folderName" name="folderName" value="${folderName}"></td>
				</tr>
				<!-- TextArea -->

				<s:iterator value="attrDtl">
					
					<s:if test="attrType == 'TextArea'">

						<tr>
							<td class="title" align="right" width="45%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><input class="txt" type="text"
								name="<%="attrValueId"+elementid%>" id="${attrName }"
								value=""> <input
								type="hidden" name="<%="attrType"+elementid %>" value="Text">
							<input type="hidden" name="<%="attrName"+elementid %>"
								value="${attrName }"> <input type="hidden"
								name="<%="attrId"+elementid %>" value="${attrId }"></td>
						</tr>
						<%elementid++;%>
					 </s:if> 

				</s:iterator>

				<!-- Text -->

				<s:iterator value="attrDtl">
					<s:if test="attrType == 'Text'">
					<s:if test="Automated_TM_Required=='Yes' && showAutomateButton==false 
								&& (attrName=='URS Reference Number' || attrName=='FS Reference Number') ">
					<%-- <s:set name="elementid" value="#elementid - 1"></s:set> --%>
					</s:if>
					<s:else><%-- <s:if test="attrType == 'Text'"> --%>
						<tr>
							<td class="title" align="right" width="45%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><input class="txt" type="text"
								name="<%="attrValueId"+elementid%>" id="${attrName }"
								value=""> <input
								type="hidden" name="<%="attrType"+elementid %>" value="Text">
							<input type="hidden" name="<%="attrName"+elementid %>"
								value="${attrName }"> <input type="hidden"
								name="<%="attrId"+elementid %>" value="${attrId }"></td>
						</tr>
						<%elementid++;%>
					<%-- </s:if> --%>
					</s:else>
					 </s:if> 

				</s:iterator>
				<!-- /////date -->
				<s:iterator value="attrDtl">

					<s:if test="attrType == 'Date'">

						<tr>
							<td class="title" align="right" width="50%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><input type="text"
								name="<%="attrValueId"+elementid %>"
								id="<%="attrValueId"+elementid %>" ReadOnly="readonly" size="12"
								value="<s:property value="attrValue"/>"> &nbsp;<IMG
								onclick="popUpCalendar(this,<%="attrValueId"+elementid %>,'yyyy/mm/dd');"
								src="${pageContext.request.contextPath}/images/Calendar.png"
								align="middle"> <input type="hidden"
								name="<%="attrType"+elementid %>" value="Date"> <input
								type="hidden" name="<%="attrName"+elementid %>"
								value="${attrName }"> <input type="hidden"
								name="<%="attrId"+elementid %>" value="${attrId }"></td>
						</tr>
						<%elementid++;%>
					</s:if>
				</s:iterator>


				<!-- ////file -->
				<s:iterator value="attrDtl">

					<s:if test="attrType == 'File'">

						<tr>
							<td class="title" align="right" width="50%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><input type="file"
								name="<%="attrValueId"+elementid %>"> <input
								type="hidden" name="<%="attrType"+elementid %>" value="File">
							<input type="hidden" name="<%="attrName"+elementid %>"
								value="${attrName }"> <input type="hidden"
								name="<%="attrId"+elementid %>" value="${attrId }"></td>
						</tr>

					</s:if>
				</s:iterator>


				<!-- ////combo -->

				<s:set name="prevattrid" value="-1" />

				<s:iterator value="attrDtl">

					<s:if test="attrType == 'Combo'">

						<s:if test="#prevattrid == -1 || #prevattrid != attrId">
							<tr>
								<td class="title" align="right" width="50%"
									style="padding: 2px; padding-right: 8px;">${attrName }</td>
								<td align="left"><select
									name="<%="attrValueId"+(elementid)%>">
									<s:set name="outterattrid" value="attrId" />
									<s:set name="outterattrValue" value="attrValue" />
									<s:iterator value="attrDtl">
										<s:if test="#location_name!=GCC">
											<s:if test="#outterattrid == attrId">


												<OPTION
													value="<s:property value="attrMatrixValue"/>&&&&<s:property value="attrMatrixDisplayValue"/>"
													<s:if test="#outterattrValue == attrMatrixValue">selected="selected"</s:if>>
												<s:property value="attrMatrixValue" /></OPTION>
											</s:if>
										</s:if>
										<s:else>
											<!-- for gcc region -->
											<s:if test="#outterattrid == attrId">


												<OPTION
													value="<s:property value="attrMatrixValue"/>&&&&<s:property value="attrMatrixDisplayValue"/>"
													<s:if test="#outterattrValue == attrMatrixValue">selected="selected"</s:if>>
												<s:property value="attrMatrixValue" /></OPTION>
											</s:if>
										</s:else>

									</s:iterator>

								</select> <input type="hidden" name="<%="attrType"+elementid %>"
									value="Combo"> <input type="hidden"
									name="<%="attrName"+elementid %>" value="${attrName}">
								<input type="hidden" name="<%="attrId"+elementid %>"
									value="${attrId}"></td>

							</tr>
							<%elementid++;%>
						</s:if>
						<s:set name="prevattrid" value="attrId" />
					</s:if>
					
				</s:iterator>
				
				
				<s:iterator value="attrDtl">

					<s:if test="attrType == 'MultiSelectionCombo' ">

						<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<s:if test="attrName == 'URS Reference No.' || attrName=='FS Reference No.' ">
							<s:if test="URSTracebelityMatrixDtl.size>0" >
								<s:if test="attrName == 'URS Reference No.' ">
									<tr class="none">
										<td class="title" align="right" width="50%"
											style="padding: 2px; padding-right: 8px;">${attrName }</td>
										<td><select multiple="multiCheckUser"class="assignUsers" id="userWiseGroupCode"
												name="<%="attrValueId"+(elementid)%>">
												<s:set name="outterAttrId" value="attrId" />
												<s:set name="outterAttrValue" value="attrMatrixValue" />
												<s:set name="outterValue" value="attrValue" />
												<s:iterator value="URSTracebelityMatrixDtl">
													<s:if test="#outterAttrId == attrId">
														<s:if test="fileName == 'URS' ">
															<option value="<s:property value="IDNo"/>"
															title="<s:property value="nodeName"/>"
																<s:if test="%{#outterValue.contains(IDNo)}">selected="selected"</s:if>>
								                					<s:property value="URSNo"/> <s:property value="URSDescription"/></option>
														</s:if>
													</s:if>
												</s:iterator>
											</select>
											<input type="hidden" name="<%="attrType"+elementid %>" value="MultiSelectionCombo"> 
											<input type="hidden" name="<%="attrName"+elementid %>" value="${attrName }"> 
											<input type="hidden" name="<%="attrId"+elementid %>" value="${attrId }">
										</td>
									</tr><%elementid++;%>
								</s:if>
								<s:if test="attrName == 'FS Reference No.' ">
				 				    <tr class="none">
				 				    	<td class="title" align="right" width="50%"
											style="padding: 2px; padding-right: 8px;">${attrName }</td>
				 				    	<td><select multiple="multiCheckUser"class="assignUsers" id="userWiseGroupCode"
											 name="<%="attrValueId"+(elementid)%>">
												<s:set name="outterAttrId" value="attrId" />
												<s:set name="outterAttrValue" value="attrMatrixValue" />
												<s:set name="outterValue" value="attrValue" />									
												<s:iterator value="URSTracebelityMatrixDtl">
													<s:if test="#outterAttrId == attrId">
														<s:if test="fileName == 'FS' ">
															<option value="<s:property value="IDNo"/>" title="<s:property value="nodeName"/>"
																<s:if test="%{#outterValue.contains(IDNo)}">selected="selected"</s:if>>
								                					<s:property value="FRSNo"/> <s:property value="FSDescription"/>
									            			</option>
														</s:if>
													</s:if>
												</s:iterator>
											</select> 
											<input type="hidden" name="<%="attrType"+elementid %>" value="MultiSelectionCombo"> 
											<input type="hidden" name="<%="attrName"+elementid %>" value="${attrName }"> 
											<input type="hidden" name="<%="attrId"+elementid %>" value="${attrId }">
										</td>
									</tr> <%elementid++;%> 
								</s:if>
							</s:if>
						 </s:if>
						 <s:else>
						 	<tr class="none">
						 		<td class="title">${attrName }</td>
									<td><select multiple="multiple" class="commentUsers" id="milestoneId" 
										name="<%="attrValueId"+(elementid)%>">
										<s:set name="outterAttrId" value="attrId" />
										<s:set name="outterAttrValue" value="attrMatrixValue" />
											<s:iterator value="attrDtl">
												<s:if test="#outterAttrId == attrId">
													<OPTION	value="<s:property value="attrMatrixValue" />"
														<s:if test="%{attrValue.contains(attrMatrixValue)}">selected="selected"</s:if>>
															<s:property value="attrMatrixValue" /></OPTION>
														</s:if>
											</s:iterator>
										</select>
											<input type="hidden" name="<%="attrType"+elementid %>" value="MultiSelectionCombo"> 
											<input type="hidden" name="<%="attrName"+elementid %>" value="${attrName }"> 
											<input type="hidden" name="<%="attrId"+elementid %>" value="${attrId }">
									</td>
							</tr><%elementid++;%>
						</s:else>
					</s:if>
						<s:set name="prevattrid" value="attrId" />
					</s:if>
				</s:iterator>
				</tr>
				<tr>
					<td class="title" align="right" width="50%"	style="padding: 2px; padding-right: 8px;">Reason for Change</td>
					<td><input type="text" name="remark" id="remark"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><s:submit value="Update"
						cssClass="button" onclick="return Filevalidate();"></s:submit>&nbsp;&nbsp; <input type="button"
						class="button" value="Close" onclick="refreshParent();" /></td>
				</tr>

				<input type="hidden" name="attrCount" value="<%=elementid%>">


			</s:else>
			
		</table>
<br/>

<s:hidden name="WorkspaceId"></s:hidden>
</s:form>
<br/>
</div>
</div>
</div>
</body>
</html>
