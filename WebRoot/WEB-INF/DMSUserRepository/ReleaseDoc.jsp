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

		 $(document).ready(function() {

			 $("#repeatNo").blur( function(e){
				 var repetno =$("#repeatNo").val(); 
				  var patt=/\D/g;
				  if (repetno.length == 0 || repetno == "0") 
					  $("#repeatNo").val("1");
				  if ((patt.test(repetno)))
					  $("#repeatNo").val(repetno.replace(/\D/g, ''));
				});
			 
			 $("#repeatNo").keypress(function(event) {
				  // Backspace, tab, enter, end, home, left, right
				  // We don't support the del key in Opera because del == . == 46.
				  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
				  // IE doesn't support indexOf
				  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
				  // Some browsers just don't raise events for control keys. Easy.
				  // e.g. Safari backspace.
				  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
				      (49 <= event.which && event.which <= 57) || // Always 1 through 9
				      (48 == event.which && $(this).attr("value")) || // No 0 first digit
				      isControlKey) { // Opera assigns values for control keys.
					  return;
				  } else {
					 //alert(event.which+"--else");
				    event.preventDefault();
				  }
				});
		    });

		
			$(document).ready(function() 
			{
				$(document).ready(function() {
					$(".txtDateValid").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
					});
				var options = {	
						beforeSubmit: showRequest,
						success: showResponse 
					};
				$("#saveFormButton").click(function(){
					if(checkData())		
					{			
						var comment = $('#userComments').val();
						comment = trim(comment);
						if (comment.length > 0) {
							$("#saveDocForm").ajaxSubmit(options);	
						}
						else
							alert("Please Enter The Release Comments.");
					}
					else
						alert("Attribute Value can not be blank.");
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
				var ret=true;
				var repeat = parseInt($("#repeatNo").val());
				if (!repeat) 
					repeat = 1;
					
				if (repeat < 0 || repeat >=10000 ) {
					alert("Value Can not be greater then 9999.");
					ret=false;
					return;
				}
				if (isNaN(repeat)) {
					alert("Please Enter The Valid Number.");
					ret=false;
					return;
					
				}
				var nodevalue = $("#nodeName").val();
				
				jQuery.exists = function(selector) {return ($(selector).length > 0);}
				if ($.exists(".dynamicValid"))
				{ 
					$('.dynamicValid').each(function(){
						var txtVal = $(this).val();
						if ((!txtVal.length > 0) || (txtVal == '-1')) {
							ret=false;
							return;
						}
					});
											
				}
				
				if ($.exists(".txtBoxValid")) 
				{ 
					$('.txtBoxValid').each(function(){
						var txtVal = $(this).val();
						txtVal = trim(txtVal);
						if (! txtVal.length > 0) {
							ret=false;
							return;
						}
					});
					/*
					if (! txtVal.length > 0) {
						ret=false;
						return false;
					}
					*/
				}
				
				if ($.exists(".txtAreaValid")) 
				{ 
					$('.txtAreaValid').each(function(){
						var txtVal = $(this).val();
						txtVal = trim(txtVal);
						if (! txtVal.length > 0) {
							ret=false;
							return;
						}
					});
				
				}
				
				if ($.exists(".txtDateValid")) 
				{ 
					$('.txtDateValid').each(function(){
						var txtVal = $(this).val();
						if (! txtVal.length > 0) {
							ret=false;
							return;
						}
					});
				}
				
				if ($.exists(".selectValid")) 
				{ 
					$('.selectValid').each(function(){
						var txtVal = $(this).val();
						if ((!txtVal.length > 0) || (txtVal == '-1')) {
							ret=false;
							return;
						}
						
					});
					
				}
				
				if ($.exists(".autoCmpltrValid")) 
				{ 
					$('.autoCmpltrValid').each(function(){
						var txtVal = $(this).val();
						if (! txtVal.length > 0) {
							ret=false;
							return;
						}
					});
				}
				/*
				if(!nodevalue.match(/^([a-zA-Z0-9\/\u002D\u002C-_])*$/))
				{
					alert("Only digits, Alphabets, '-' , '_', '/' and ',' are allowed.");
					ret=false;
				   	return false;
				}*/
				return ret;
			}
			function showRequest(formData, jqForm, options) {
				$('#showDocDtlDiv').html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
	 			return true;
	 		}
			function showResponse(responseText, statusText) 
			{
				if(responseText.match("#")?1:0)
				{
					var data=responseText.split('#');
					alert(data[0]);
					//if(confirm("Show Released Document?"))
						//openFile(data[1]);
				}
				else
					alert(responseText);
				getDocDtl();
			}
		</script>
</head>
<body>
<div id="showDocDtlDiv" align="center"><s:if
	test="dtoWsNodeHis.fileName != null && dtoWsNodeHis.fileName.substring(dtoWsNodeHis.fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
	<s:form name="saveDocForm" method="post" id="saveDocForm"
		action="SaveDoc_ex">
		<input type="hidden" name="folderName"
			value="<s:property value="folderName"/>">
		<table align="center" width="100%" cellspacing="0">
			<tr align="left">
				<td class="title" align="right" width="25%"
					style="padding: 2px; padding-right: 6px;" id="catDoc">Document
				ID&nbsp;</td>
				<td><s:property value="folderName" />&nbsp;&nbsp; <s:if
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
				</s:else> <s:if
					test="(!mtype.equalsIgnoreCase('n')) && (dtoWsNodeHis.fileName.substring(dtoWsNodeHis.fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf'))">
					<input type="hidden" name="printDocId" id="printDocId" value="Y">
				</s:if> <input type="hidden" name="nodeName" id="nodeName"
					value="<s:property value="dtoWsNodeHis.fileName.substring(0,dtoWsNodeHis.fileName.indexOf(\".\"))"/>">
				<input type="hidden" id="documentSrc" name="documentSrc"
					value="Inherit"></td>
			</tr>
			<tr align="left">
				<td class="title" align="right" width="25%"
					style="padding: 2px; padding-right: 6px;" id="catDoc">No. Of
				Release&nbsp;</td>
				<td><s:if
					test="dtoWsNodeHis.fileName != null && dtoWsNodeHis.fileName !='' && !dtoWsNodeHis.fileName.equalsIgnoreCase('No File')">
					<input type="text" style="width: 40px;" name="repeatNo" value="1"
						id="repeatNo" size="3px;" />
									&nbsp;&nbsp;<input type="checkbox" name="printing" value="yes"
						id="printing">
					<label for="printing">&nbsp;Printing</label>
				</s:if></td>
			</tr>
			<s:if test="attrList.size() > 0 ">
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
						<tr align="left">
							<td class="title" align="right" width="25%"
								style="padding: 2px; padding-right: 8px;">${attrName}</td>
							<td align="left"><input type="hidden"
								name="attrForIndiId<s:property value="#elementid"/>"
								value="<s:property value="attrForIndiId"/>"> <input
								type="text" class="txtBoxValid"
								name="attrValueId<s:property value="#elementid"/>"
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
								rows="3" cols="30" class="txtAreaValid"
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
							<input type="text" class="txtDateValid"
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
									name="attrValueId<s:property value="#elementid"/>"
									class="selectValid">
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
									name="attrId<s:property value="#elementid"/>"
									value="${attrId }"></td>
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
								<td align="left"><s:iterator
									value="filterAutoCompleterList">
									<s:if test="top[0] == attrId">
										<s:autocompleter name="attrValueId%{#elementid}"
											cssClass="autoCmpltrValid" list="top[1]" listKey="top"
											listValue="top" autoComplete="false"
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
									name="attrId<s:property value="#elementid"/>"
									value="${attrId }"></td>
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
										<s:select list="top[1]" cssClass="dynamicValid" headerKey="-1"
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
									name="attrId<s:property value="#elementid"/>"
									value="${attrId }"></td>
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
			<tr align="left">
				<td class="title" align="right" width="25%"
					style="padding: 2px; padding-right: 6px;">Comments&nbsp;</td>
				<td><input type="text" name="userComments" value=""
					id="userComments"></td>
			</tr>
			<tr align="left">
				<td></td>
				<td><input type="button" id="saveFormButton"
					name="saveFormButton" class="button" value="Release Document" /></td>
			</tr>
		</table>
		<input type="hidden" name="mtype" value="<s:property value="mtype"/>">
		<input type="hidden" name="workSpaceId"
			value="<s:property value="workSpaceId"/>">
		<input type="hidden" name="nodeId"
			value="<s:property value="nodeId"/>">
		<s:hidden name="mode" id="mode" value="add"></s:hidden>
	</s:form>

</s:if> <s:else>
	<div align="center" class="title">Upload PDF File On Category.</div>
</s:else></div>
</body>
</html>
