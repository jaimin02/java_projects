<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.docmgmt.dto.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>


<html>
<head>
<s:head />
<SCRIPT type="text/javascript">
	
	function validate()
	{

		var templateId = document.structureForm.nodeId.value;
		if(templateId == 0 || templateId == "")
			{
				alert("Please Select Template Node..");
				return false;	
			}
		return true;	
	}		
</SCRIPT>

<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/CalendarPopup.js"></SCRIPT>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>
<link
	href="<%=request.getContextPath()%>/css/fSelect.css"
	rel="stylesheet" type="text/css" media="screen" />
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		(function($) {
		    $(function() {
		        window.fs_test = $('.commentUsers').fSelect();
		        alert("test");
		    });
		})(jQuery);
		
</script>
</head>

<body>


<s:form name="structureForm" action="saveTemplateNodeAttribute">
	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="displayName"></s:hidden>

	<div class="headercls">Attach Attributes To Structure Node</div>
	<div class="bdycls" style="padding-left: 10px;">
	<s:if test="getTemplateDtl.size==0">
		<table border="0" width="95%" height="90%">
			<tr>
				<td valign="top"><b>Currently no attribute found.</b></td>
			</tr>
		</table>
	</s:if> <s:else>
		<!--<table  border="0" width="95%" align="center">
		<tr>
	   			<td>
					<b><font color="navy" size="2">Node Name &nbsp;</font></b>
					<font color="blue violet" size=2>
					<u><b>${displayName}</b></u>
					</font>
	   			</td>
	   		</tr>
		</table> -->

		<!-- <table width="100%" align="center"> -->
		<table align="center" width="95%" class="datatable">
		<th>Attribute Name</th>
		<th> Attribute Value</th>
		<th>Add Attribute</th>
		<th>Add In Cover Page</th>
		<!-- <div style="width: 5%;margin-left: 62.5%;">
		<h5>Cover Page Setting</h5>
		</div> -->
			
			<s:set name="elementid" value="1"></s:set>

			<!-- Text Box -->
			<s:iterator value="getTemplateDtl">
				<s:if test="attrType == 'Text'">

					<s:set name="val" value="" />
					<s:set name="flag" value="false" />
					<s:set name="validValue" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					
					<s:iterator value="templateAttrib">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="val" value="attrValue" />
							<s:set name="validValue" value="validValues" />

						</s:if>
					</s:iterator>

					<tr>
					<%-- <s:property value="#validValue"/> --%>
						<td class="title">${attrName }</td>
						<td><input type="text"
							name="attrValueId<s:property value="#elementid"/>"
							value="<s:property value="#val"/>"> 
						</td>
						<td><input type="checkbox" name="cattrId<s:property value="#elementid"/>"
							<s:if test="#flag == true">checked="checked"</s:if>>
						</td>
						<td>
						<input type="checkbox" name="coverPage<s:property value="#elementid"/>"
							<s:if test="#validValue == 'Cover Page'">checked="checked"</s:if>
						>
						 </td>
							<input type="hidden" name="attrType<s:property value="#elementid"/>"value="Text"> 
							<input type="hidden"name="attrName<s:property value="#elementid"/>"value="<s:property value="attrName"/>"> 
							<input type="hidden" name="attrId<s:property value="#elementid"/>"value="<s:property value="attrId"/>">
					</tr>


					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
			</s:iterator>

			<!-- Text Area-->
			<s:iterator value="getTemplateDtl">

				<s:if test="attrType == 'TextArea'">

					<s:set name="val" value="" />
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttrib">

						<s:if test="#outterAttrName == attrName">

							<s:set name="flag" value="true" />
							<s:set name="val" value="attrValue" />

						</s:if>
					</s:iterator>

					<tr>
						<td class="title">${attrName }</td>
						<td><textarea rows="3" cols="30"
							name="attrValueId<s:property value="#elementid"/>"><s:property
							value="#val" /></textarea> <input type="checkbox"
							name="cattrId<s:property value="#elementid"/>"
							<s:if test="#flag == true">checked="checked"</s:if>> <input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="TextArea"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="<s:property value="attrName"/>"> <input
							type="hidden" name="attrId<s:property value="#elementid"/>"
							value="<s:property value="attrId"/>"></td>
					</tr>


					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
			</s:iterator>

			<!-- Date -->
			<s:iterator value="getTemplateDtl">

				<s:if test="attrType == 'Date'">

					<s:set name="val" value="" />
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttrib">

						<s:if test="#outterAttrName == attrName">

							<s:set name="flag" value="true" />
							<s:set name="val" value="attrValue" />

						</s:if>
					</s:iterator>

					<tr>
						<td class="title">${attrName }</td>
						<td><input type="text"
							name="attrValueId<s:property value="#elementid"/>"
							id="attrValueId<s:property value="#elementid"/>"
							ReadOnly="readonly" size="12" value="<s:property value="#val"/>">
						&nbsp;<IMG
							onclick="popUpCalendar(this,'attrValueId<s:property value="#elementid"/>','yyyy/mm/dd');"
							src="<%=request.getContextPath() %>/images/Calendar.png"
							align="middle"> <input type="checkbox"
							name="cattrId<s:property value="#elementid"/>"
							<s:if test="#flag == true">checked="checked"</s:if>> <input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="Date"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="<s:property value="attrName"/>"> <input
							type="hidden" name="attrId<s:property value="#elementid"/>"
							value="<s:property value="attrId"/>"></td>
					</tr>


					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
			</s:iterator>

			<!-- combo box -->
			<s:set name="prevattrid" value="-1" />
			<s:iterator value="getTemplateDtl">

				<s:set name="combovalue" value="" />
				<s:if test="attrType == 'Combo'">
				
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttrib">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="combovalue" value="attrValue" />
						</s:if>

					</s:iterator>


					<s:if test="#prevattrid == '-1' || #prevattrid != attrId">

						<tr>
							<td class="title">${attrName }</td>
							<td><select
								name="attrValueId<s:property value="#elementid"/>">
								<s:set name="outterAttrId" value="attrId" />
							<s:iterator value="getTemplateDtl">

									<s:if test="#outterAttrId == attrId">

										<OPTION value="<s:property value="attrValue"/>"
											<s:if test="#combovalue == attrValue">selected="selected"</s:if>>
										<s:property value="attrValue" /></OPTION>
									</s:if>
								</s:iterator>
								</select> 
							</td>
							<input type="hidden" name="attrType" value="Combo"> 
							<td><input type="checkbox" name="cattrId<s:property value="#elementid"/>"
								<s:if test="#flag == true">checked="checked"</s:if>> </td>
							<td><input type="checkbox" name="coverPage" value=""/></td>
							<input type="hidden" name="attrName<s:property value="#elementid"/>"value="<s:property value="attrName"/>"> 
							<input type="hidden" name="attrId<s:property value="#elementid"/>" value="<s:property value="attrId"/>"></td>
						</tr>

						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>
            <!-- Multi Selection combo box -->
			
			<s:set name="prevattrid" value="-1" />
			<s:iterator value="getTemplateDtl">

				<s:set name="combovalue" value="" />
				<s:if test="attrType == 'MultiSelectionCombo'">
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttrib">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="combovalue" value="attrValue" />
						</s:if>

					</s:iterator>


					<s:if test="#prevattrid == '-1' || #prevattrid != attrId">

						<tr>
							<td class="title">${attrName }</td>
							<td>
							
							<select multiple="multiple" class="commentUsers"
								name="attrValueId<s:property value="#elementid"/>">
							
								<s:set name="outterAttrId" value="attrId" />
								<s:iterator value="getTemplateDtl">

									<s:if test="#outterAttrId == attrId">

										<OPTION value="<s:property value="attrValue"/>"
											<s:if test="#combovalue == attrValue">selected="selected"</s:if>>
										<s:property value="attrValue" /></OPTION>
									</s:if>
								</s:iterator>

							</select> 
							 <input type="hidden" name="attrType" value="MultiSelectionCombo"> <input
								type="checkbox" name="cattrId<s:property value="#elementid"/>"
								<s:if test="#flag == true">checked="checked"</s:if>> <input
								type="hidden" name="attrName<s:property value="#elementid"/>"
								value="<s:property value="attrName"/>"> <input
								type="hidden" name="attrId<s:property value="#elementid"/>"
								value="<s:property value="attrId"/>"></td>
						</tr>

						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>
			
			<!-- Dynamic -->
			<s:iterator value="getTemplateDtl">
				<s:set name="dynamicvalue" value="" />
				<s:if test="attrType == 'Dynamic'">
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttrib">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="dynamicvalue" value="attrValue" />
						</s:if>

					</s:iterator>
					<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<tr>
							<td class="title">${attrName }</td>
							<td><s:iterator value="filterDynamicList">
								<s:if test="top[0] == attrId">

									<s:select list="top[1]" headerKey=""
										headerValue="Please Select Value" value="%{attrValue}"
										name="attrValueId%{#elementid}">
									</s:select>
								</s:if>

							</s:iterator> <input type="hidden"
								name="attrType<s:property value="#elementid"/>" value="Dynamic">
							<input type="checkbox"
								name="cattrId<s:property value="#elementid"/>"
								<s:if test="#flag == true">checked="checked"</s:if>> <input
								type="hidden" name="attrName<s:property value="#elementid"/>"
								value="<s:property value="attrName"/>"> <input
								type="hidden" name="attrId<s:property value="#elementid"/>"
								value="<s:property value="attrId"/>"></td>

						</tr>

						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>

			<input type="hidden" name="attrCount"
				value="<s:property value="#elementid"/>">
			<tr align="left">
				<td></td>
				<td><s:submit onclick="return validate();" value="Save"
					cssClass="button" /></td>

			</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		
		</table>

	</s:else>
	</s:form>
	<br>
	<div class="headercls">Attach configurable attribute To Leaf Node</div>
	<s:form name="structureForm" action="saveTemplateLeafNodeAttribute">
	<s:hidden name="nodeId"></s:hidden>
	<s:hidden name="displayName"></s:hidden>
	
	<s:if test="getTemplateDtlForLeafNodes.size==0">
		<table border="0" width="95%" height="90%">
			<tr>
				<td valign="top"><b>Currently no attribute found.</b></td>
			</tr>
		</table>
	</s:if> <s:else>
		<!--<table  border="0" width="95%" align="center">
		<tr>
	   			<td>
					<b><font color="navy" size="2">Node Name &nbsp;</font></b>
					<font color="blue violet" size=2>
					<u><b>${displayName}</b></u>
					</font>
	   			</td>
	   		</tr>
		</table> -->

		<table align="center" width="95%" class="datatable">
		<th>Attribute Name</th>
		<th> Attribute Value</th>
		<th>Add Attribute</th>
	

			<s:set name="elementid" value="1"></s:set>

			<!-- Text Box -->
			<s:iterator value="getTemplateDtlForLeafNodes">

				<s:if test="attrType == 'Text'">

					<s:set name="val" value="" />
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttribForLeafNode">

						<s:if test="#outterAttrName == attrName">

							<s:set name="flag" value="true" />
							<s:set name="val" value="attrValue" />

						</s:if>
					</s:iterator>

					<tr>
						<td class="title" title="First, Last, Number">${attrName }  (First,Last,Number)</td>
						<td><input type="text"
							name="attrValueId<s:property value="#elementid"/>"
							value="<s:property value="#val"/>">
						</td>
						<td> 
							<input type="checkbox" name="cattrId<s:property value="#elementid"/>"
							<s:if test="#flag == true">checked="checked"</s:if>> 
						</td>
							<input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="Text"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="<s:property value="attrName"/>"> <input
							type="hidden" name="attrId<s:property value="#elementid"/>"
							value="<s:property value="attrId"/>"></td>
					</tr>


					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
			</s:iterator>

			<!-- Text Area-->
			<s:iterator value="getTemplateDtlForLeafNodes">

				<s:if test="attrType == 'TextArea'">

					<s:set name="val" value="" />
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttribForLeafNode">

						<s:if test="#outterAttrName == attrName">

							<s:set name="flag" value="true" />
							<s:set name="val" value="attrValue" />

						</s:if>
					</s:iterator>

					<tr>
						<td class="title">${attrName }</td>
						<td><textarea rows="3" cols="30"
							name="attrValueId<s:property value="#elementid"/>"><s:property
							value="#val" /></textarea> <input type="checkbox"
							name="cattrId<s:property value="#elementid"/>"
							<s:if test="#flag == true">checked="checked"</s:if>> <input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="TextArea"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="<s:property value="attrName"/>"> <input
							type="hidden" name="attrId<s:property value="#elementid"/>"
							value="<s:property value="attrId"/>"></td>
					</tr>


					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
			</s:iterator>

			<!-- Date -->
			<s:iterator value="getTemplateDtlForLeafNodes">

				<s:if test="attrType == 'Date'">

					<s:set name="val" value="" />
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttribForLeafNode">

						<s:if test="#outterAttrName == attrName">

							<s:set name="flag" value="true" />
							<s:set name="val" value="attrValue" />

						</s:if>
					</s:iterator>

					<tr>
						<td class="title">${attrName }</td>
						<td><input type="text"
							name="attrValueId<s:property value="#elementid"/>"
							id="attrValueId<s:property value="#elementid"/>"
							ReadOnly="readonly" size="12" value="<s:property value="#val"/>">
						&nbsp;<IMG
							onclick="popUpCalendar(this,'attrValueId<s:property value="#elementid"/>','yyyy/mm/dd');"
							src="<%=request.getContextPath() %>/images/Calendar.png"
							align="middle"> <input type="checkbox"
							name="cattrId<s:property value="#elementid"/>"
							<s:if test="#flag == true">checked="checked"</s:if>> <input
							type="hidden" name="attrType<s:property value="#elementid"/>"
							value="Date"> <input type="hidden"
							name="attrName<s:property value="#elementid"/>"
							value="<s:property value="attrName"/>"> <input
							type="hidden" name="attrId<s:property value="#elementid"/>"
							value="<s:property value="attrId"/>"></td>
					</tr>


					<s:set name="elementid" value="#elementid + 1"></s:set>
				</s:if>
			</s:iterator>

			<!-- combo box -->
			<s:set name="prevattrid" value="-1" />
			<s:iterator value="getTemplateDtlForLeafNodes">

				<s:set name="combovalue" value="" />
				<s:if test="attrType == 'Combo'">
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttribForLeafNode">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="combovalue" value="attrValue" />
						</s:if>

					</s:iterator>


					<s:if test="#prevattrid == '-1' || #prevattrid != attrId">

						<tr>
							<td class="title">${attrName }</td>
							<td><select
								name="attrValueId<s:property value="#elementid"/>">
								<s:set name="outterAttrId" value="attrId" />
								<s:iterator value="getTemplateDtlForLeafNodes">

									<s:if test="#outterAttrId == attrId">

										<OPTION value="<s:property value="attrValue"/>"
											<s:if test="#combovalue == attrValue">selected="selected"</s:if>>
										<s:property value="attrValue" /></OPTION>
									</s:if>
								</s:iterator>

							</select> </td>
							<td><input type="hidden" name="attrType" value="Combo"> <input
								type="checkbox" name="cattrId<s:property value="#elementid"/>"
								<s:if test="#flag == true">checked="checked"</s:if>> <input
								type="hidden" name="attrName<s:property value="#elementid"/>"
								value="<s:property value="attrName"/>"> <input
								type="hidden" name="attrId<s:property value="#elementid"/>"
								value="<s:property value="attrId"/>"></td>
						</tr>

						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>
            <!-- Multi Selection combo box -->
			
			<s:set name="prevattrid" value="-1" />
			<s:iterator value="getTemplateDtlForLeafNodes">

				<s:set name="combovalue" value="" />
				<s:if test="attrType == 'MultiSelectionCombo'">
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttribForLeafNode">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="combovalue" value="attrValue" />
						</s:if>

					</s:iterator>


					<s:if test="#prevattrid == '-1' || #prevattrid != attrId">

						<tr>
							<td class="title">${attrName }</td>
							<td>
							
							<select multiple="multiple" class="commentUsers"
								name="attrValueId<s:property value="#elementid"/>">
							
								<s:set name="outterAttrId" value="attrId" />
								<s:iterator value="getTemplateDtlForLeafNodes">

									<s:if test="#outterAttrId == attrId">

										<OPTION value="<s:property value="attrValue"/>"
											<s:if test="#combovalue == attrValue">selected="selected"</s:if>>
										<s:property value="attrValue" /></OPTION>
									</s:if>
								</s:iterator>

							</select> 
							 <input type="hidden" name="attrType" value="MultiSelectionCombo"> <input
								type="checkbox" name="cattrId<s:property value="#elementid"/>"
								<s:if test="#flag == true">checked="checked"</s:if>> <input
								type="hidden" name="attrName<s:property value="#elementid"/>"
								value="<s:property value="attrName"/>"> <input
								type="hidden" name="attrId<s:property value="#elementid"/>"
								value="<s:property value="attrId"/>"></td>
						</tr>

						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>
			
			<!-- Dynamic -->
			<s:iterator value="getTemplateDtlForLeafNodes">
				<s:set name="dynamicvalue" value="" />
				<s:if test="attrType == 'Dynamic'">
					<s:set name="flag" value="false" />
					<s:set name="outterAttrName" value="attrName" />
					<s:iterator value="templateAttribForLeafNode">

						<s:if test="#outterAttrName == attrName">
							<s:set name="flag" value="true" />
							<s:set name="dynamicvalue" value="attrValue" />
						</s:if>

					</s:iterator>
					<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<tr>
							<td class="title">${attrName }</td>
							<td><s:iterator value="filterDynamicList">
								<s:if test="top[0] == attrId">

									<s:select list="top[1]" headerKey=""
										headerValue="Please Select Value" value="%{attrValue}"
										name="attrValueId%{#elementid}">
									</s:select>
								</s:if>

							</s:iterator> <input type="hidden"
								name="attrType<s:property value="#elementid"/>" value="Dynamic">
							<input type="checkbox"
								name="cattrId<s:property value="#elementid"/>"
								<s:if test="#flag == true">checked="checked"</s:if>> <input
								type="hidden" name="attrName<s:property value="#elementid"/>"
								value="<s:property value="attrName"/>"> <input
								type="hidden" name="attrId<s:property value="#elementid"/>"
								value="<s:property value="attrId"/>"></td>

						</tr>

						<s:set name="elementid" value="#elementid + 1"></s:set>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>

			<input type="hidden" name="attrCount"
				value="<s:property value="#elementid"/>">
			<tr align="left">
				<td></td>
				<td><s:submit onclick="return validate();" value="Save"
					cssClass="button" /></td>
			</tr>
		</table>

	</s:else>
	
	</s:form>
	<div style="height: 20px;"></div>
</body>
</html>
