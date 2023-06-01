<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>

<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".attrValueIdDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		});
	
</script>

<script type="text/javascript">
	$(document).ready(function(){
		var options = {success: function(data)	{
							alert("Attribute Value Changed Successfully.");
						}	
					};
		$('#editDocAttrForm').submit(function(){
			$(this).ajaxSubmit(options);
			return false;
		});
	});

	
 	</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
</div>
<div><br />

<div style="width: 100%; margin-top: 10px;" align="center"><s:if
	test="attrDtl.size() > 0">
	<s:form name="editDocAttrForm" method="post" action="SaveDocAttr_ex"
		id="editDocAttrForm">
		<input type="hidden" id="workSpaceId" name="workSpaceId"
			value="<s:property value='workSpaceId'/>" />
		<input type="hidden" id="nodeId" name="nodeId"
			value="<s:property value='nodeId'/>" />
		<div style="max-height: 300px; overflow: auto;">
		<table width="100%">
			<s:set name="elementid" value="1"></s:set>

			<s:set name="prevattrid" value="-1" />

			<s:iterator value="attrDtl">

				<s:if test="attrType == 'Text'">
					<tr>
						<td class="title" align="right" width="30%"
							style="padding: 2px; padding-right: 8px;">${attrName}</td>
						<td align="left"><input type="text"
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
						<td class="title" align="right" width="30%"
							style="padding: 2px; padding-right: 8px;">${attrName }</td>
						<td align="left"><textarea rows="3" cols="30"
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
						<td class="title" align="right" width="30%"
							style="padding: 2px; padding-right: 8px;">${attrName }</td>
						<td align="left"><input type="hidden"
							name="attrType<s:property value="#elementid"/>" value="Date">
						<input type="hidden"
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
							<td class="title" align="right" width="30%"
								style="padding: 2px; padding-right: 8px;">${attrName}</td>
							<td align="left"><select
								name="attrValueId<s:property value="#elementid"/>">

								<s:set name="outterAttrId" value="attrId" />
								<s:set name="outterAttrValue" value="attrMatrixValue" />
								<s:iterator value="attrDtl">

									<s:if test="#outterAttrId == attrId">

										<OPTION value="<s:property value="attrMatrixValue" />"
											<s:if test="attrMatrixValue == attrValue">selected="selected"</s:if>>
										<s:property value="attrMatrixValue" /></OPTION>

									</s:if>
								</s:iterator>

							</select> <input type="hidden"
								name="attrType<s:property value="#elementid"/>" value="Combo">
							<input type="hidden"
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
							<td class="title" align="right" width="30%"
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
								name="attrType<s:property value="#elementid"/>"
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
							<td class="title" align="right" width="30%"
								style="padding: 2px; padding-right: 8px;">${attrName }</td>
							<td align="left"><s:iterator value="filterDynamicList">
								<s:if test="top[0] == attrId">
									<s:select list="top[1]" headerKey=""
										headerValue="Please Select Value" value="%{attrValue}"
										name="attrValueId%{#elementid}">
									</s:select>
								</s:if>

							</s:iterator> <input type="hidden"
								name="attrType<s:property value="#elementid"/>" value="Dynamic">
							<input type="hidden"
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

			<tr>
				<td></td>
				<td align="left"><input type="hidden" name="attrCount"
					value="<s:property value="#elementid"/>"> <input
					type="submit" id="saveAttribBtn" value="Save Attribute"
					class="button"></td>
			</tr>

		</table>
		</div>
	</s:form>
</s:if> <s:else>
	<label class="title">No Attributes Attached.</label>
</s:else></div>
</div>
</body>
</html>