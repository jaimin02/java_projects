<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="isFileForPublish">
	<s:if test="workspaceNodeAttrList.size() > 0">
		<script type="text/javascript">
			$(document).ready(function() {
				
				//$('#showAttr').hide();
				$('#editAttrVal').click(function()
				{
					$('#showAttr').slideToggle();
				});


				var options = 
				{
					url: 'saveAttrDtl_ex.do',
					beforeSubmit: showRequest,
					success: showResponse
				};

				$('#attrForm').submit(function()
				{	
					$(this).ajaxSubmit(options);
					return false;									
				});
			});



			function showRequest(formData, jqForm, options) {
				var queryString = $.param(formData);
				var formElement = jqForm[0];
				$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');
				return true;
			}
			function showResponse(responseText, statusText) 
			{
				alert(responseText);
				subinfo();
			}
		</script>
		<div align="left" class="headercls">Attribute Validation</div>
		<div id="showAttr" align="center" style="padding-top: 5px;">
		<form action="saveAttrDtl_ex.do" method="post" id="attrForm">
		<table width="100%" align="center" class="datatable">
			<tr>
				<th></th>
				<th>Node Name</th>
				<th>Attribute Name</th>
				<th>Attribute Value</th>
			</tr>
			<s:iterator value="workspaceNodeAttrList" status="status">
				<tr class="none">
					<td align="right"><s:if
						test="attrValue.trim().equalsIgnoreCase('') || attrValue.trim() == null">
						<img align="right" border="0px" alt="Error"
							src="images/Common/error.png" height="18px" width="18px">
					</s:if> <s:else>
						<img align="right" border="0px" alt="Correct"
							src="images/Common/correct.png" height="18px" width="18px">
					</s:else></td>
					<td><s:set name="totalAttr" value="#status.count"></s:set> <SPAN
						title="<s:property value='nodeDisplayName'/>"><s:property
						id="nodeName_%{#status.count}" value="nodeName" /></SPAN> <s:hidden
						name="nodeId_%{#status.count}" value="%{nodeId}" /></td>
					<td><s:property id="attrName_%{#status.count}"
						value="attrName" /> <s:hidden name="attrId_%{#status.count}"
						value="%{attrId}" /></td>
					<td><input type="text" value="<s:property value="attrValue"/>"
						id="attrValue_<s:property value="#status.count"/>"
						name="attrValue_<s:property value="#status.count"/>"></td>
				</tr>
			</s:iterator>
			<tr class="none">
				<td colspan="3"></td>
				<td align="right"><s:hidden name="workspaceId" /> <s:hidden
					name="totalAttr" /> <input type="submit" value="Save"
					id="saveAttr" class="button"></td>
			</tr>
		</table>
		</form>

		<%--
			<s:iterator value="workspaceNodeAttrList">
				workspaceId:::<s:property value="workspaceId"/><br/>
				workSpaceDesc:::<s:property value="workSpaceDesc"/><br/>
				nodeId:::<s:property value="nodeId"/><br/>
				attrId:::<s:property value="attrId"/><br/>
				attrName:::<br/>
				attrValue:::<br/>
				nodeName:::<br/>
				nodeDisplayName:::<s:property value="nodeDisplayName"/><br/>
				folderName:::<s:property value="folderName"/><br/>
				attrForIndi:::<s:property value="attrForIndi"/><br/><br/><br/>----
			</s:iterator>
			 --%></div>
	</s:if>
	<s:else>
	remove
	</s:else>
</s:if>
<s:else>
no
</s:else>