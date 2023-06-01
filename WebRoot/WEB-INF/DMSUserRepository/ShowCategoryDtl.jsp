<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>

<script type="text/javascript">
			$(document).ready(function() 
			{
				$(document).ready(function() {
					$(".attrValueIdDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
					});
				var options = {	
						success: showResponse 
					};
				$("#saveFormButton").click(function(){
					if(checkData())	
						$("#saveDocForm").ajaxSubmit(options);
					return false;			
				});
			});
			function trim(str)
			{
			   	str = str.replace( /^\s+/g, "" );// strip leading
				return str.replace( /\s+$/g, "" );// strip trailing
			}
			function checkData()
			{
				var nodevalue = $("#nodeName").val();
				var ret=true;
				nodevalue=trim(nodevalue);
				if(nodevalue == "" )
				{
					alert("You must have to enter Document Title..");
					ret=false;
					return false;
				}
				if(!nodevalue.match(/^([a-zA-Z0-9\/\u002D\u002C-_\s])*$/))
				{
					alert("Only digits, Alphabets, '-' , '_', '/' and ',' are allowed.");
					ret=false;
				   	return false;
				}
				var comment = $('#userComments').val();
				comment=trim(comment);
				if (comment.length <= 0) {
					alert("Please Enter The Comments.");
					ret=false;
				   	return false;
				}
				var doc =$("#attachment").val();
				doc = trim(doc);
				if (!doc.length > 0) 
				{
					alert("Please Attach The Document.");
					ret=false;
				   	return false;
				}
				return ret;
			}
			function showResponse(responseText, statusText) 
			{
				alert(responseText);
				getDocDtl();
			}
		</script>
</head>
<body>
<div id="showDocDtlDiv"><s:form name="saveDocForm" method="post"
	id="saveDocForm" action="SaveDoc_ex" enctype="multipart/form-data">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;" id="catDoc">Category
			Document&nbsp;</td>
			<td><s:if
				test="dtoWsNodeHis.fileName != null && dtoWsNodeHis.fileName !='' && !dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">
				<a title="<s:property value="dtoWsNodeHis.fileName"/>"
					href="openfile.do?fileWithPath=<s:property value="dtoWsNodeHis.baseWorkFolder"/><s:property value="dtoWsNodeHis.folderName"/>/<s:property value="dtoWsNodeHis.fileName"/>"
					target="_blank"> <s:if
					test="dtoWsNodeHis.fileName.contains(\".\")">
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
				</s:if> <s:else>
					<img alt="Open File"
						src="<%=request.getContextPath()%>/images/file.png"
						style="border: none;">
				</s:else> </a>
			</s:if> <s:else>
				<label><b>No Document Attached..</b></label>
			</s:else></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Document ID&nbsp;</td>
			<td align="left"><s:property value="folderName" /> <input
				type="hidden" name="folderName"
				value="<s:property value="folderName"/>"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Document Title&nbsp;</td>
			<td align="left"><s:textfield name="nodeName" id="nodeName"
				size="25" value="" cssClass="target"></s:textfield> <s:hidden
				id="documentSrc" name="documentSrc" value="New"></s:hidden></td>
		</tr>
		<tr id="slidingDiv">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Upload Document&nbsp;</td>
			<td><input type="file" name="attachment" id="attachment">
			</td>
		</tr>
		<s:if test="attrList.size() > 0">
			<tr align="center">
				<td colspan="2" align="center">
				<hr color="#5A8AA9" size="3px"
					style="width: 85%; border-bottom: 2px solid #CDDBE4;">
				</td>
			</tr>
			<s:set name="elementid" value="1"></s:set>
			<s:set name="prevattrid" value="-1" />
			<s:iterator value="attrList">
				<s:if test="attrType == 'Text'">
					<tr>
						<td class="title" align="right" width="25%"
							style="padding: 2px; padding-right: 8px;">${attrName}</td>
						<td align="left"><input type="hidden"
							name="attrForIndiId<s:property value="#elementid"/>"
							value="<s:property value="attrForIndiId"/>"> <input
							type="text" name="attrValueId<s:property value="#elementid"/>"
							value="<s:property value="attrValue"/>"> <input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="Text"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="${attrName }"> <input type="hidden"
							name="attrId<s:property value="#elementid"/>" value="${attrId }">
						</td>
					</tr>
					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
				<s:elseif test="attrType == 'TextArea'">
					<tr>
						<td class="title" align="right" width="25%"
							style="padding: 2px; padding-right: 8px;">${attrName }</td>
						<td align="left"><input type="hidden"
							name="attrForIndiId<s:property value="#elementid"/>"
							value="<s:property value="attrForIndiId"/>"> <textarea
							rows="3" cols="30"
							name="attrValueId<s:property value="#elementid"/>"><s:property
							value="attrValue" /></textarea> <input type="hidden"
							name="attrType<s:property value="#elementid"/>" value="TextArea">
						<input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="${attrName }"> <input type="hidden"
							name="attrId<s:property value="#elementid"/>" value="${attrId }">
						</td>
					</tr>
					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:elseif>
				<s:elseif test="attrType == 'Date'">
					<tr>
						<td class="title" align="right" width="25%"
							style="padding: 2px; padding-right: 8px;">${attrName }</td>
						<td align="left"><input type="hidden"
							name="attrForIndiId<s:property value="#elementid"/>"
							value="<s:property value="attrForIndiId"/>"> <input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="Date"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="${attrName }"> <input type="hidden"
							name="attrId<s:property value="#elementid"/>" value="${attrId }">
						<input type="text"
							name="attrValueId<s:property value="#elementid"/>"
							class="attrValueIdDate" readonly="readonly"
							id="attrValueId<s:property value="#elementid"/>"
							value="<s:property value="attrValue"/>"> (YYYY/MM/DD)
						&nbsp;<IMG
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
						<tr>
							<td class="title" align="right" width="25%"
								style="padding: 2px; padding-right: 8px;">${attrName}</td>
							<td align="left"><select
								name="attrValueId<s:property value="#elementid"/>">
								<s:set name="outterAttrId" value="attrId" />
								<s:iterator value="attrList">
									<s:if test="#outterAttrId == attrId">
										<OPTION value="<s:property value="attrMatrixValue" />">
										<s:property value="attrMatrixValue" /></OPTION>
									</s:if>
								</s:iterator>
							</select> <input type="hidden"
								name="attrForIndiId<s:property value="#elementid"/>"
								value="<s:property value="attrForIndiId"/>"> <input
								type="hidden" name="attrType<s:property value="#elementid"/>"
								value="Combo"> <input type="hidden"
								name="attrName<s:property value="#elementid"/>"
								value="${attrName }"> <input type="hidden"
								name="attrId<s:property value="#elementid"/>" value="${attrId }">
							</td>
						</tr>
						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:elseif>
				<s:elseif test="attrType == 'AutoCompleter'">
					<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<tr>
							<td class="title" align="right" width="25%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><s:iterator value="filterAutoCompleterList">
								<s:if test="top[0] == attrId">
									<s:autocompleter name="attrValueId%{#elementid}" list="top[1]"
										listKey="top" listValue="top" autoComplete="false"
										cssStyle="margin: 0; padding: 2px; width:400px;"
										dropdownHeight="145" loadOnTextChange="false"
										resultsLimit="all" forceValidOption="true"
										value="%{attrValue}">
									</s:autocompleter>
								</s:if>
							</s:iterator> <input type="hidden"
								name="attrForIndiId<s:property value="#elementid"/>"
								value="<s:property value="attrForIndiId"/>"> <input
								type="hidden" name="attrType<s:property value="#elementid"/>"
								value="AutoCompleter"> <input type="hidden"
								name="attrName<s:property value="#elementid"/>"
								value="${attrName }"> <input type="hidden"
								name="attrId<s:property value="#elementid"/>" value="${attrId }">
							</td>
						</tr>
						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:elseif>
				<s:elseif test="attrType == 'Dynamic'">
					<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<tr>
							<td class="title" align="right" width="25%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><s:iterator value="filterDynamicList">
								<s:if test="top[0] == attrId">
									<s:select list="top[1]" headerKey=""
										headerValue="Please Select Value" value="%{attrValue}"
										name="attrValueId%{#elementid}">
									</s:select>
								</s:if>
							</s:iterator> <input type="hidden"
								name="attrForIndiId<s:property value="#elementid"/>"
								value="<s:property value="attrForIndiId"/>"> <input
								type="hidden" name="attrType<s:property value="#elementid"/>"
								value="Dynamic"> <input type="hidden"
								name="attrName<s:property value="#elementid"/>"
								value="${attrName }"> <input type="hidden"
								name="attrId<s:property value="#elementid"/>" value="${attrId }">
							</td>
						</tr>
						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:elseif>
			</s:iterator>
			<input type="hidden" name="attrCount"
				value="<s:property value="#elementid"/>">
			<tr align="center">
				<td colspan="2" align="center">
				<hr color="#5A8AA9" size="3px"
					style="width: 85%; border-bottom: 2px solid #CDDBE4;">
				</td>
			</tr>
		</s:if>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Comments&nbsp;</td>
			<td><input type="text" name="userComments" value=""
				id="userComments"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="hidden" name="mtype"
				value="<s:property value="mtype"/>"> <input type="hidden"
				name="workSpaceId" value="<s:property value="workSpaceId"/>">
			<input type="hidden" name="nodeId"
				value="<s:property value="nodeId"/>"> <s:hidden name="mode"
				id="mode" value="add"></s:hidden> <input type="button"
				id="saveFormButton" name="saveFormButton" class="button"
				value="Attach Document" /></td>
		</tr>
	</table>
</s:form></div>
</body>
</html>
