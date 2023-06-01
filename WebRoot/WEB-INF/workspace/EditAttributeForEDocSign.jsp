<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

function validate(){
	//debugger;
	var txtAttr = document.getElementsByClassName("txt");
	var dateAttr = document.getElementsByClassName("date");
	if(txtAttr.lengh != 0){
	for (var i = 0; i < txtAttr.length; i++) {
		 var attrVal = txtAttr[i].value;
		 attrVal= attrVal.trim();
		 if(attrVal==""){
			 alert("Please specify empty value.");
			 return false;
		 }
	 }
	if(dateAttr.lengh != 0){
		for (var i = 0; i < dateAttr.length; i++) {
			 var attrVal = dateAttr[i].value;
			 attrVal= attrVal.trim();
			 if(attrVal==""){
				 alert("Please specify empty value.");
				 return false;
			 }
		 }
	 }
	}
	var remark = document.getElementById("remark").value;
	remark = remark.trim();
	if (remark == ""){
		alert("Please specify Reason for Change.");
		document.saveAttribute.remark.style.backgroundColor="#FFE6F7"; 
 		document.saveAttribute.remark.focus();
 		return false;
 	}
 }
function nodeAttrDetailHistory(wsId,nodeId)
{
	//debugger;
	str="ChangeRequstDetailHistory_b.do?docId="+wsId+"&recordId="+nodeId;
	win3=window.open(str,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}
</script>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float:left;">Edit ${docTypeName}</b></div>
<s:form action="SaveAttributeForDocSign" method="post" enctype="multipart/form-data" name="saveAttribute" 
onsubmit="return validate();">
<div align="center">
<div id="imp" style="float: right; margin-right: 10px; margin-top: 5px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div>
<table id = "AttributeDetail" style="width:98%; align:center;">
	<s:hidden name="recordId" />
	<s:hidden name="docId" />
	<s:hidden name="docType" />
	<s:if test="attrDtl.size == 0">
				<tr>
					<td style='color: navy; font-size: 12px;'>No details available.</td>
								</tr>
							</s:if>
							<s:else>

								<s:set name="elementid" value="1"></s:set>

								<s:set name="prevattrid" value="-1" /> 

								<!-- <tr><td></td></tr>
								<tr>
								<td class="title">Document Name </td>
								<td>
								<input type="text"	name="docName" />
								</td>
									</tr>					 -->	
								<s:iterator value="attrDtl">
						
									<s:if test="attrType == 'Text'">

										<tr class="none">
										
											<td align="right" align="right" width="50%" class="title">${attrName } 
											<span style="font-size:20px;color:red">*</span>&nbsp;</td>
											<td align="left"><input class="txt" type="text"
												name="attrValueId<s:property value="#elementid"/>"
												value="<s:property value="attrValue"/>"> <input
												type="hidden"
												name="attrType<s:property value="#elementid"/>" value="Text">
											<input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"></td>
										</tr>

										<s:set name="elementid" value="#elementid + 1"></s:set>
									</s:if>
									<s:elseif test="attrType == 'TextArea'">

										<tr class="none">
											<td align="right" width="50%" class="title">${attrName }
											<span style="font-size:20px;color:red">*</span> &nbsp;</td>
											<td align="left"><textarea rows="3" cols="30" style="width:280px;"
												name="attrValueId<s:property value="#elementid"/>"><s:property
												value="attrValue" /></textarea> <input type="hidden"
												name="attrType<s:property value="#elementid"/>"
												value="TextArea"> <input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"></td>
										</tr>
										<s:set name="elementid" value="#elementid + 1"></s:set>
									</s:elseif>
									<s:elseif test="attrType == 'Date'">

										<tr class="none">
											<td align="right" width="50%" class="title">${attrName }
											<span style="font-size:20px;color:red">*</span> &nbsp;</td>
											<td align="left"><input class="date" type="hidden"
												name="attrType<s:property value="#elementid"/>" value="Date">
											<input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"> <input type="text"
												name="attrValueId<s:property value="#elementid"/>"
												class="attrValueIdDate" readonly="readonly"
												id="attrValueId<s:property value="#elementid"/>"
												value="<s:property value="attrValue"/>">
											(YYYY/MM/DD) &nbsp;<IMG
												onclick="
																						if(document.getElementById('attrValueId<s:property value="#elementid"/>').value != '' 
																								&& confirm('Are you sure?'))
																							document.getElementById('attrValueId<s:property value="#elementid"/>').value = '';
																					"
												src="<%=request.getContextPath() %>/images/clear.jpg"
												align="middle" title="Clear Date"></td>
										</tr>
										<s:set name="elementid" value="#elementid + 1"></s:set>
									</s:elseif>
									<s:elseif test="attrType == 'Combo'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
											<tr class="none">
												<td align="right" width="50%"class="title">${attrName }
												<span style="font-size:20px;color:red">*</span> &nbsp;</td>
												<td align="left"><select
													name="attrValueId<s:property value="#elementid"/>">
													<s:set name="outterAttrId" value="attrId" />
													<s:set name="outterAttrValue" value="attrMatrixValue" />
													<s:if test="attrValue==''">
														<OPTION value="">Please select value</OPTION>
													</s:if>
													<s:iterator value="attrDtl">
														<s:if test="#outterAttrId == attrId">
															<OPTION value="<s:property value="attrMatrixValue" />"
																<s:if test="attrMatrixValue == attrValue">selected="selected"</s:if>>
															<s:property value="attrMatrixValue" /></OPTION>
														</s:if>
													</s:iterator>
												</select> <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="Combo"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
									<s:elseif test="attrType == 'MultiSelectionCombo'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
										<s:if test="#session.usertypename == 'WA' && attrId==32">
											<tr class="none">
												<td align="right" width="50%" class="title">${attrName } 
												<span style="font-size:20px;color:red">*</span>&nbsp;</td>
											
												<td align="left">
												<select multiple="multiple" class="commentUsers" id="milestoneId" 
													name="attrValueId<s:property value="#elementid"/>">
													<s:set name="outterAttrId" value="attrId" />
													<s:set name="outterAttrValue" value="attrMatrixValue" />
													<s:iterator value="attrDtl">
														<s:if test="#outterAttrId == attrId">
															<OPTION value="<s:property value="attrMatrixValue" />"
																<s:if test="%{attrValue.contains(attrMatrixValue)}">selected="selected"</s:if>>
															<s:property value="attrMatrixValue" /></OPTION>
														</s:if>
													</s:iterator>
												</select>
											
												 <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="MultiSelectionCombo"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											</s:if>
											<s:else>
												<tr class="none">
											<td align="right" width="50%" class="title">${attrName } &nbsp;</td>
											<td style="float: left; margin-left: 50px; width: 36%;"><span><s:property value="attrValue"/></span>
											<input type="hidden"
												name="attrName<s:property value="#elementid"/>"
												value="${attrName }"> <input type="hidden"
												name="attrId<s:property value="#elementid"/>"
												value="${attrId }"></td>
										</tr>
										
											</s:else>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
									<s:elseif test="attrType == 'AutoCompleter'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
											<tr class="none">
												<td align="right" width="50%" class="title">${attrName }
												<span style="font-size:20px;color:red">*</span> &nbsp;</td>
												<td align="left"><s:iterator value="filterAutoCompleterList">
													<s:if test="top[0] == attrId">
														<s:autocompleter name="attrValueId%{#elementid}"
															list="top[1]" listKey="top" listValue="top"
															autoComplete="false"
															cssStyle="margin: 0; padding: 2px; width:400px;"
															dropdownHeight="145" loadOnTextChange="false"
															resultsLimit="all" forceValidOption="true"
															value="%{attrValue}">
														</s:autocompleter>
													</s:if>
												</s:iterator> <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="AutoCompleter"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
									<s:elseif test="attrType == 'Dynamic'">
										<s:if test="#prevattrid == -1 || #prevattrid != attrId">
											<tr class="none">
												<td align="right" width="50%" class="title">${attrName } 
												<span style="font-size:20px;color:red">*</span>&nbsp;</td>
												<td align="left"><s:iterator value="filterDynamicList">
													<s:if test="top[0] == attrId">
														<s:select list="top[1]" headerKey=""
															headerValue="Please Select Value" value="%{attrValue}"
															name="attrValueId%{#elementid}">
														</s:select>
													</s:if>
												</s:iterator> <input type="hidden"
													name="attrType<s:property value="#elementid"/>"
													value="Dynamic"> <input type="hidden"
													name="attrName<s:property value="#elementid"/>"
													value="${attrName }"> <input type="hidden"
													name="attrId<s:property value="#elementid"/>"
													value="${attrId }"></td>
											</tr>
											<s:set name="elementid" value="#elementid + 1"></s:set>
										</s:if>
										<s:set name="prevattrid" value="attrId" />
									</s:elseif>
								</s:iterator>								
							</s:else>
				<tr>
					<td align="right" width="50%;" class="title">Reason For Change
					<span style="font-size:20px;color:red">*</span> &nbsp; </td>
					<td align="left"><input type="text" id ="remark" name="remark" /></td>
					</tr>	
</table>
</div>
<br>
<div align ="center">
 <s:if test=" #session.usertypename == 'WA' || isFileUplodRights==true">
	<s:submit cssClass="button"  value="Update" id="savebtn" name="buttonName"/>
 </s:if>
	<input type="button" value="Close" style="margin-left: 7px;" class="button" onclick="window.location='ChangeRequest.do?docId=<s:property value="WorkspaceId"/>&docType=<s:property value="docType"/>';">
	<input type="button" value="Audit Trail" style="margin-left: 7px;" class="button" onclick="return nodeAttrDetailHistory('<s:property value="WorkspaceId"/>','<s:property value="recordId"/>');">
</div>

</s:form>
</div>
</div>
</div>
</div>
<br>
</body>
</html>