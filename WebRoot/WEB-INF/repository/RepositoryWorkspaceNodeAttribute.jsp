<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.Vector"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.docmgmt.dto.*"%>
<html>
<head>
<s:head />
<style type="text/css">
.smalltd {
	height: 12px;
	font-size: 12px;
	color: navy;
}
</style>
</head>
<body>

<s:if test="fileExist == false">
	<div align="center"
		style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669; width: 100%; height: 100%">

	<table align="center">


		<tr>
			<td><br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<font color="red">
			<h3>File Not Found On Selected Node</h3>
			</font></td>
		</tr>
	</table>
	</div>
</s:if>

<s:else>

	<div align="center">
	<div class="titlediv">Attributes</div>
	<br>


	<s:form action="CopyFile" method="post">

		<table width="100%" align="center">

			<%Vector attrDtlVect=(Vector)request.getAttribute("attrDtl"); %>
			<!-- ------------dynamic display of attribute started here ----------------->
			<%
	int elementid=1;
	
	DTOAttributeValueMatrix objDTO=new DTOAttributeValueMatrix();
	int attrdtlvectsize=attrDtlVect.size();
 	%>
			<!-- Text Box -->
			<%
	
	for(int i=0;i<attrdtlvectsize;i++)
	{
	
			String attrvalue="";
			
			objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
			if(objDTO.getAttrType().equals("Text") && objDTO.getUserInput()=='Y')
			{%>

			<tr>
				<td class="title"><%=objDTO.getAttrName() %></td>
				<td><input type="text" name="<%="attrValueId"+elementid%>"
					value="<%=objDTO.getAttrValue() %>"> <input type="hidden"
					name="<%="attrType"+elementid %>" value="Text"> <input
					type="hidden" name="<%="attrName"+elementid %>"
					value="<%=objDTO.getAttrName() %>"> <input type="hidden"
					name="<%="attrId"+elementid %>" value="<%=objDTO.getAttrId() %>">
				</td>
			</tr>


			<%	
	elementid++; 
		}
	}%>

			<!-- combo  -->

			<%int prevattrid=-1;
	DTOAttributeValueMatrix objTempDTO=new DTOAttributeValueMatrix();
	
	for(int i=0;i<attrdtlvectsize;i++)
	{	
		String combovalue="";
		
			objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
	
			if(objDTO.getAttrType().equals("Combo") && objDTO.getUserInput()=='Y')
			{
				if(i==0 || prevattrid == -1 || prevattrid !=objDTO.getAttrId() )
				{
		%>
			<tr>
				<td class="title"><%=objDTO.getAttrName() %></td>
				<td><select name="<%="attrValueId"+(elementid)%>">

					<% 		for(int k=0;k<attrdtlvectsize;k++)
				{
					objTempDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(k);
					if(objDTO.getAttrId()==objTempDTO.getAttrId())
					{	
						
						out.print("<OPTION value="+"\""+objTempDTO.getAttrMatrixValue()+"\"");
						out.print(">");
						out.print(objTempDTO.getAttrMatrixValue());
						out.print("</OPTION> \n");
					}
				}
		%>
				</select> <input type="hidden" name="<%="attrType"+elementid %>"
					value="Combo"> <input type="hidden"
					name="<%="attrName"+elementid %>"
					value="<%=objDTO.getAttrName() %>"> <input type="hidden"
					name="<%="attrId"+elementid %>" value="<%=objDTO.getAttrId() %>">
				</td>
			</tr>
			<%		
		elementid++;
		}	
		prevattrid=objDTO.getAttrId();
		}
	}%>

			<!-- ------------dynamic display of attribute ended here ----------------->

			<tr>
				<td></td>
				<td><s:submit value="Save" cssClass="button"></s:submit></td>
			</tr>

		</table>

		<input type="hidden" name="attrCount" value="<%=elementid -1%>">





		<s:hidden name="srcNodeId"></s:hidden>
		<s:hidden name="srcWsId"></s:hidden>
		<s:hidden name="destWsId"></s:hidden>
		<s:hidden name="destNodeId"></s:hidden>



		<div class="headercls">User Defined Attributes</div>
		<div class="bdycls">
		<table>

			<s:if test="attrHistory.size == 0">
				No Attributes Found
				
				</s:if>

			<s:iterator value="attrHistory">

				<tr>
					<td class="smalltd"><b> <s:property value="attrName" /></b></td>
					<td class="smalltd">&nbsp;&nbsp;<s:set name="attrValue"
						id="attrValue" /> <s:if test="attrValue == ''">
						<font color="red"> </font>
					</s:if> <s:else>
						<s:property value="attrValue" />
					</s:else></td>
				</tr>

			</s:iterator>
		</table>
		</div>



	</s:form></div>
</s:else>


</body>
</html>
