<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.Vector"%>
<%@page import="com.docmgmt.server.db.*"%>
<%@page import="com.docmgmt.dto.DTOAttributeGroupMst"%>
<%@page import="com.docmgmt.dto.DTOAttributeGroupMatrix"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<s:set name="getAttrDtl" id="getAttrDtl"></s:set>
<s:if test="getAttrDtl.size > 0">
	<%int srNo = 1; %>

	<br>
	<table align="center" width="95%" class="datatable">
		<s:iterator value="getAttrDtl" id="getAttrDtl" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><%=srNo++ %></td>
				<td><s:property value="attrName" /></td>
				<td><s:property value="attrType" /></td>

				<td><input type="checkbox" name="multiCheckAttr"
					value="<s:property value="attrId"/>" /></td>
			</tr>
		</s:iterator>
	</table>
</s:if>
<s:else>




	<%int srNo = 1; String divId="";%>

	<table border="0" width="100%">

		<tr>
			<td align="left" colspan="2">
			<table align="center" width="95%" class="datatable">
				<tr>
					<td width="5%" class="title">SrNo</td>
					<td width="40%" class="title">Group Name</td>
					<td width="40%" class="title">Project Type</td>
					<td width="10%" class="title">Check</td>
					<td width="5%" class="title">View</td>
				</tr>
				<tr>
					<td colspan="5" width="100%">
					<table align="center" width="100%">

						<%Vector getAttrGroupDtl = (Vector)request.getAttribute("getAttrGroupDtl");
												for(int i=0;i<getAttrGroupDtl.size();i++){
												DTOAttributeGroupMst dto1 = (DTOAttributeGroupMst)getAttrGroupDtl.get(i);
												
											 %>
						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
							<% divId="div_"+srNo; %>

							<td width="5%" align="left"><%=srNo++%></td>
							<td width="40%" align="left"><%= dto1.getAttrGroupName()%></td>
							<td width="40%" align="left"><%= dto1.getProjectCode()%></td>

							<td width="10%" align="left"><input type="checkbox"
								name="multiCheckAttrGroup" value="<%= dto1.getAttrGroupId()%>" /></td>
							<td width="5%" align="left"><img
								src="<%=request.getContextPath()%>/images/view.gif"
								onclick="return showHide('<%=divId%>');" /></td>
						</tr>

						<tr bgcolor="white">
							<td colspan="5" align="left">
							<table id="<%=divId%>" style="display: none;">

								<%	
																	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
																	Vector groupWiseAttributes = docMgmtImpl.getAttributeGroupMatrixValues(dto1.getAttrGroupId());
																		if(groupWiseAttributes.size()==0){%>
								<tr bgcolor="white">
									<td align="center" width="100%"><font color="red"><b>No
									Attributes Found</font></td>
								</tr>
								<%}else{
																		int index = 1;
																	for(int j=0;j<groupWiseAttributes.size();j++)
																		{	
																			DTOAttributeGroupMatrix dto = (DTOAttributeGroupMatrix)groupWiseAttributes.get(j);
																		
																		%>
								<tr bgcolor="white">
									<td width="5%"><font color="red"><%=index++ %></font></td>
									<td width="40%"><font color="red"><%=dto.getAttrName() %></font></td>
									<td width="40%"><font color="red"><%=dto.getAttrType() %></font></td>
									<td width="15%"></td>
								</tr>
								<%}
																	}
																	
																	%>


							</table>
							</td>
						</tr>

						<%}%>


					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>



</s:else>
</body>
</html>
