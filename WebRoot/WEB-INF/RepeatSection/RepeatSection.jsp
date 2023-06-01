<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>
<s:head theme="ajax" />
<script type="text/javascript">
	
	
	
	function editLeafNode(editNodeId,folderName)
	{
		var str=folderName;		
		folderName=str.substring(0,str.lastIndexOf('.'));	
		strAction = "EditSection.do?editNodeId=" + editNodeId + "&fixedPart=" + folderName;
   		win=window.open(strAction,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=no,height=330,width=500,resizable=no,titlebar=no");
   		win.moveTo(screen.availWidth/2-(400/2),screen.availHeight/2-(250/2));
   		return true;
   	}
	
	function removeLeafNode(removeNodeId,repeatNodeId){
		//debugger;
		var remark = prompt("Please specify reason for change.");
		remark = remark.trim();
		if (remark != null && remark != ""){
		 	var url = "RepeatSection.do?removeNodeId=" + removeNodeId + "&repeatNodeId=" + repeatNodeId + "&remark=" + remark;
			location.href = url;	
		}
		else if(remark==""){
			alert("Please specify reason for change.");
			return false;
		}
	}
   	
   	function validate()
   	{
   		if(document.forms["RepeatSectionForm"].numberOfRepetitions.value=="")
		{
			alert("Please enter Number of Repetitions.");
			document.forms["RepeatSectionForm"].numberOfRepetitions.style.backgroundColor="#FFE6F7";
			document.forms["RepeatSectionForm"].numberOfRepetitions.focus();
			return false;
		}
		else if(!document.forms["RepeatSectionForm"].numberOfRepetitions.value.match(/^([1-9])([0-9])*$/) && parseInt(document.forms["RepeatSectionForm"].numberOfRepetitions.value) < 1)
		{
			alert("Number of Repetitions should be a number greater than zero.");
			document.forms["RepeatSectionForm"].numberOfRepetitions.style.backgroundColor="#FFE6F7";
			document.forms["RepeatSectionForm"].numberOfRepetitions.focus();
			return false;
		}
		else if(document.forms["RepeatSectionForm"].suffixStart.value=="")
		{
			alert("Please enter Suffix Start.");
			document.forms["RepeatSectionForm"].suffixStart.style.backgroundColor="#FFE6F7";
			document.forms["RepeatSectionForm"].suffixStart.focus();
			return false;
		}
		else if(!document.forms["RepeatSectionForm"].suffixStart.value.match(/^([1-9])([0-9])*$/) && parseInt(document.forms["RepeatSectionForm"].suffixStart.value) < 1)
		{
			alert("Suffix Start should be a number greater than zero.");
			document.forms["RepeatSectionForm"].suffixStart.style.backgroundColor="#FFE6F7";
			document.forms["RepeatSectionForm"].suffixStart.focus();
			return false;
		}
		else if(document.forms["RepeatSectionForm"].remark.value==""){
			debugger;
			alert('Please specify Reason for Change.');
			document.forms["RepeatSectionForm"].remark.style.backgroundColor="#FFE6F7"; 
			document.forms["RepeatSectionForm"].remark.focus();
			return false;
		}
	
		return true;
   	}
	function refreshParent() 
	{
		window.opener.parent.history.go(0);
		self.close();
	}
	
	
</script>
<style type="text/css">
#err {
	color: red;
	font-size: 12px;
	text-align: center;
}
</style>
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div class="container-fluid">
<br>
<div class="col-md-12">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder">
<s:form action="SaveSection" name="RepeatSectionForm" method="post">
	
	<s:if test="isSection == true">
		
		<div class="all_title"><b><center>Repeat Section</b><center></div>
		<!-- <div align="center" class="headercls"><b> Repeat Section </b></div> -->
		<div id="err"><s:if test="errorMsg==null || errorMsg == ''">&nbsp;</s:if>
		<s:else>
			<s:property value="errorMsg" />
		</s:else></div>
		<table style="align:center; width:95%;">
			<tr>
				<td colspan="2">
				<table style="align:center;width:95%;">
					<tr>
						<td class="title" align="right" width="45%"
							style="padding: 2px; padding-right: 8px;">Source Section
						Name</td>
						<td class="title" align="left"
							style="font-weight: lighter; font: x-small; color: black">
						${sourceNodeDetail.nodeDisplayName }</td>
					</tr>
				</table>
			<s:if test="repeatNodeAttrDtl.size == 0">
				<tr>
					<td class="title" colspan="2">
					<center><b> No details available.</b></center>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td width="100%" align="center"><s:submit value="Repeat"
						cssClass="button">
					</s:submit> &nbsp;&nbsp; <input type="button" class="button" value="Close"
						onclick="refreshParent();" /></td>
				</tr>
			</s:if>

			<s:else>
				<%int elementid=1;%>
				<!-- TextArea -->

				<s:iterator value="repeatNodeAttrDtl">

					<s:if test="attrType == 'TextArea'">

						<tr>
							<td class="title" align="right" width="45%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><TEXTAREA
								NAME="<%="attrValueId"+elementid%>" COLS=30 ROWS=3>${attrValue }</TEXTAREA>

							<input type="hidden" name="<%="attrType"+elementid%>"
								value="TextArea"> <input type="hidden"
								name="<%="attrName"+elementid %>" value="${attrName }">
							<input type="hidden" name="<%="attrId"+elementid %>"
								value="${attrId }"></td>
						</tr>
						<%elementid++;%>
					</s:if>
				</s:iterator>

				<!-- Text -->

				<s:iterator value="repeatNodeAttrDtl">

					<s:if test="attrType == 'Text'">

						<tr>
							<td class="title" align="right" width="45%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><input type="text"
								name="<%="attrValueId"+elementid%>"
								value="<s:property value="attrValue"/>"> <input
								type="hidden" name="<%="attrType"+elementid %>" value="Text">
							<input type="hidden" name="<%="attrName"+elementid %>"
								value="${attrName }"> <input type="hidden"
								name="<%="attrId"+elementid %>" value="${attrId }"></td>
						</tr>
						<%elementid++;%>
					</s:if>

				</s:iterator>

				<!-- /////date -->
				<s:iterator value="repeatNodeAttrDtl">

					<s:if test="attrType == 'Date'">

						<tr>
							<td class="title" align="right" width="45%"
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
				<s:iterator value="repeatNodeAttrDtl">

					<s:if test="attrType == 'File'">

						<tr>
							<td class="title" align="right" width="45%"
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

				<s:iterator value="repeatNodeAttrDtl">

					<s:if test="attrType == 'Combo'">

						<s:if test="#prevattrid == -1 || #prevattrid != attrId">
							<tr>
								<td class="title" align="right" width="45%"
									style="padding: 2px; padding-right: 8px;">${attrName }</td>
								<td align="left"><select
									name="<%="attrValueId"+(elementid)%>">
									<s:set name="outterattrid" value="attrId" />
									<s:set name="outterattrValue" value="attrValue" />
									<s:iterator value="repeatNodeAttrDtl">
										<s:if test="#location_name!=GCC">
											<s:if test="#outterattrid == attrId">


												<OPTION
													value="<s:property value="attrMatrixValue"/>&&&&<s:property value="attrMatrixDisplayValue"/>"
													<s:if test="#outterattrValue == attrMatrixValue">selected="selected"</s:if>>
												<s:property value="attrMatrixDisplayValue" /></OPTION>
											</s:if>
										</s:if>
										<s:else>
											<!-- for gcc region -->
											<s:if test="#outterattrid == attrId">


												<OPTION
													value="<s:property value="attrMatrixValue"/>&&&&<s:property value="attrMatrixDisplayValue"/>"
													<s:if test="#outterattrValue == attrMatrixValue">selected="selected"</s:if>>
												<s:property value="attrMatrixDisplayValue" /></OPTION>
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


				<tr>
					<td colspan="2" align="center"><s:submit value="Repeat"
						cssClass="button"></s:submit>&nbsp;&nbsp; <input type="button"
						class="button" value="Close" onclick="refreshParent();" /></td>
				</tr>


				<input type="hidden" name="attrCount" value="<%=elementid%>">


			</s:else>
		</table>
	</s:if>
	<!-- 'if isSection == true' ended here -->
	<s:else>
		<div class="all_title"><b><center>Repeat <s:property value="lbl_folderName"/></b><center></div>
		<!-- <div align="center" class="headercls"><b> Repeat Leaf Node</b></div> -->
		<div id="err"><s:if test="errorMsg==null || errorMsg == ''">&nbsp;</s:if>
		<s:else>
			<s:property value="errorMsg" />
		</s:else></div>
		<br>
		<table style="align:center; width:95%;">
			<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;"> <s:property value="lbl_nodeName"/></td>
				<td class="title" align="left"
					style="font-weight: lighter; font: x-small; color: black">
				${finalString}</td>
			</tr>
	<%-- <s:property value="nodeHistorySize"/>
	<s:property value="nId"/>  --%>
	

			<tr>
				<td class="title" align="right" width="45%"
					style="padding: 2px; padding-right: 8px;">Number of
				Repetitions</td>
				<td><s:textfield id="numberOfRepetitions"
					name="numberOfRepetitions" size="2" maxlength="2"
					cssStyle="padding-left: 3px;width: 60px;" value="1"></s:textfield></td>
			</tr>
			<tr>
				<td class="title" align="right">Suffix &nbsp;&nbsp;&nbsp;</td>
				<td align="left"><s:textfield name="suffixStart" size="2"
					maxlength="2" cssStyle="padding-left: 3px; width: 60px;" value="1"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td class="title" align="right">Reason For Change &nbsp;&nbsp;&nbsp;</td>
				<td align="left">
				<input type="text" name="remark" id="remark"></input>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center"><br>
				<s:submit value="Repeat" cssClass="button"
					onclick="return validate();"></s:submit>&nbsp;&nbsp; <input
					type="button" class="button" value="Close"
					onclick="refreshParent();" /></td>
			</tr>

		</table>

	</s:else>
<%-- <s:set name="userCode" value="usertypecode"/> --%>
<%-- <s:property value="#userCode"/> --%>
	<s:if test="originalNodeWithAllclones.size > 0">
		<br>
		<div align="center">
		<div
			style="height: 134px; width: 95%; overflow-x: auto; overflow-y: auto;">
		<table class="datatable paddingtable audittrailtable"style="width:100%;">
			<tr>
				<th>#</th>
			<!-- 	<th>Node Id</th> -->
				<th><s:property value="lbl_nodeName"/> Name</th>
				<s:if test="isSection == false">
					<th>Edit</th>
				</s:if>
				<th>Remove</th>
				<!-- <th>Clone</th> -->
			</tr>

			<s:iterator value="originalNodeWithAllclones" status="status">
				<!-- ******Set Fixed part of the file******************** -->
				<s:set name="originalFileName" value=""></s:set>
				<s:iterator value="originalNodeWithAllclones" status="status">
					<s:if test="cloneFlag == 'N'">
						<s:set name="originalFileName" value="%{folderName}"></s:set>
					</s:if>
				</s:iterator>
				<!-- ******************************************************** -->
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td>${status.count }</td>
					<%-- <td><s:property value="nodeId"/></td> --%>
					<td title='<s:property value="folderName"/>'><s:property
						value="nodeDisplayName" /></td>
					<s:if test="isSection == false">
						<td><s:if test="cloneFlag == 'Y'">
							<a href="#"	onclick="editLeafNode('<s:property value="nodeId"/>','<s:property value="originalFileName"/>');">
							<img border="0px" alt="Edit" title="Edit" src="images/Common/edit.svg" height="25px" width="25px">
							</a></s:if> 
							<s:else><img border="0px" alt="Edit" title="Edit" src="images/Common/edit.svg" height="25px" width="25px">
						</s:else></td>
					</s:if>
					
					<td>
					<s:if test="alloweTMFCustomization=='yes'">
						<s:if test="usertypename=='WA'">
						<s:if test="%{(cloneFlag == 'Y' && nId == nodeId)}">
							<s:if test="senfForReview == '' ">
								<a href="#"	onclick="removeLeafNode('<s:property value="nodeId"/>','${repeatNodeId }');">
								<img border="0px" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px"></a>
							</s:if>
							<s:else>
								<img border="0px" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px">
							</s:else>
						</s:if> 
						<s:else>
							<img border="0px" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px">
						</s:else>
						</s:if>
						<%-- <s:elseif test=" usertypename=='Initiator' && nodeHistory.size == 0">
							<s:if test="%{(cloneFlag == 'Y' && nId == nodeId)}">
								<a href="#"
									onclick="removeLeafNode('<s:property value="nodeId"/>','${repeatNodeId }');">Remove</a>
							</s:if> 
							<s:else>-</s:else>
						</s:elseif> --%>
						<s:else>-</s:else>
					</s:if>
					<s:else>
					<s:if test="cloneFlag == 'Y'">
						<a href="RepeatSection.do?removeNodeId=<s:property value="nodeId"/>&repeatNodeId=${repeatNodeId }">
							<img border="0px" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px"></a>
						</s:if> 
						<s:else>
							<img border="0px" alt="Remove Document" title="Remove Document" src="images/Common/delete.svg" height="25px" width="25px">
						</s:else>
					</s:else>
					</td>
			</tr>
			</s:iterator>
		</table>
		</div>
		</div>

	</s:if>


	<s:hidden name="repeatNodeId"></s:hidden>
	<s:hidden name="isSection"></s:hidden>

</s:form>

</div>
</div>
</div>
</div>
</body>
</html>
