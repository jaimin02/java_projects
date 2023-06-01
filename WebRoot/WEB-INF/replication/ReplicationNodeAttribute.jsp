<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.Vector"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.docmgmt.dto.*"%>
<html>
<head>
<s:head />

<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>

</head>
<body>

<div align="center"><br>

<s:form action="SaveReplicatedWorkspaceNodeAttribute" method="post">

	<font color="blue violet" size=2> <u><b>${displayName}</b></u> </font>
	<table>

		<!-- ------------dynamic display of attribute started here ----------------->




		<s:if test="attrDtl.size == 0">

			<table width="95%">

				<tr>
					<td>
					<center><b> <font color="navy">Currently No
					Attribute Found</font></b></center>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td width="100%" align="center"><s:submit value="Extend Only"
						cssClass="button">
					</s:submit></td>
				</tr>

			</table>
		</s:if>

		<s:else>
			<%int elementid=1;%>
			<!-- TextArea -->

			<s:iterator value="attrDtl">

				<s:if test="attrType == 'TextArea'">

					<tr>
						<td class="title">${attrName }</td>
						<td><TEXTAREA NAME="<%="attrValueId"+elementid%>" COLS=30
							ROWS=3>${attrValue }</TEXTAREA> <input type="hidden"
							name="<%="attrType"+elementid%>" value="TextArea"> <input
							type="hidden" name="<%="attrName"+elementid %>"
							value="${attrName }"> <input type="hidden"
							name="<%="attrId"+elementid %>" value="${attrId }"></td>
					</tr>
					<%elementid++;%>
				</s:if>
			</s:iterator>

			<!-- Text -->

			<s:iterator value="attrDtl">

				<s:if test="attrType == 'Text'">

					<tr>
						<td class="title">${attrName }</td>
						<td><input type="text" name="<%="attrValueId"+elementid%>"
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
			<s:iterator value="attrDtl">

				<s:if test="attrType == 'Date'">

					<tr>
						<td class="title">${attrName }</td>
						<td><input type="text" name="<%="attrValueId"+elementid %>"
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
						<td class="title">${attrName }</td>
						<td><input type="file" name="<%="attrValueId"+elementid %>">
						<input type="hidden" name="<%="attrType"+elementid %>"
							value="File"> <input type="hidden"
							name="<%="attrName"+elementid %>" value="${attrName }"> <input
							type="hidden" name="<%="attrId"+elementid %>" value="${attrId }">
						</td>
					</tr>

				</s:if>
			</s:iterator>


			<!-- ////combo -->

			<s:set name="prevattrid" value="-1" />

			<s:iterator value="attrDtl">

				<s:if test="attrType == 'Combo'">

					<s:if test="#prevattrid == -1 || #prevattrid != attrId">
						<tr>
							<td class="title">${attrName }</td>
							<td><select name="<%="attrValueId"+(elementid)%>">
								<s:set name="outterattrid" value="attrId" />
								<s:set name="outterattrValue" value="attrValue" />
								<s:iterator value="attrDtl">

									<s:if test="#outterattrid == attrId">
										<OPTION value="<s:property value="attrMatrixValue"/>"
											<s:if test="#outterattrValue == attrMatrixValue">selected="selected"</s:if>>
										<s:property value="attrMatrixValue" /></OPTION>
									</s:if>

								</s:iterator>

							</select> <input type="hidden" name="<%="attrType"+elementid %>"
								value="Combo"> <input type="hidden"
								name="<%="attrName"+elementid %>" value="${attrName}"> <input
								type="hidden" name="<%="attrId"+elementid %>" value="${attrId}">

							</td>

						</tr>
						<%elementid++;%>
					</s:if>
					<s:set name="prevattrid" value="attrId" />
				</s:if>
			</s:iterator>
			<!-- ------------dynamic display of attribute ended here ----------------->

			<tr>
				<td></td>
				<td><s:submit value="Save" cssClass="button"></s:submit></td>
			</tr>
	</table>

	<input type="hidden" name="attrCount" value="<%=elementid%>">


	</s:else>


	<input type="hidden" name="WsnodeId" value="${nodeId}">
	<input type="hidden" name="workspaceID" value="${workspaceID}">

</s:form>
</body>
</html>
