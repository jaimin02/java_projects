<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<html>
<head>

<script language="javascript">

	 function callOpenTree(templateId)
	 {
		strAction = "templateOpen.do?templateId="+templateId;
		document.structureForm.action = strAction;
		document.structureForm.submit();
	 	return true; 
	 }
     function pagingSubmit()
     {
            var strOrder = document.structureForm.SortOrder.value;
	 	    strAction = "ShowStructure.do?SortOrder="+strOrder;
     	    document.structureForm.action = strAction;
			document.structureForm.submit();
   			return true;
     }
     function sortRecord()
     {
     		
     		var strOrder = document.structureForm.SortOrder.value;
     	    alert(strOrder);
     	    strAction = "ShowStructure.do?SortOrder="+strOrder;
	 		document.structureForm.action = strAction;
			document.structureForm.submit();
   			return true;
     }
     
</script>
<s:head />


<STYLE>
.cstyle {
	cursor: hand;
}

<!--
.initial {
	background-color: #DDDDDD;
	color: #000000
}

.normal {
	background-color: #F2F2F2
}

.highlight {
	background-color: #CBCBE4
}
//
-->
</style>

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Structure/Show_Structure.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="ShowStructure" method="post"
	name="structureForm">
	<%    	
	       int pageNumber = Integer.parseInt(request.getAttribute("givenPageNumber").toString());
			 
	       int TotalRecords = Integer.parseInt(request.getAttribute("TotalRecords").toString());
		   int startRecNo = pageNumber;
		   if(startRecNo==0)
			  startRecNo=1;
		   startRecNo = (startRecNo*20)-19;
		   int endRecNo = 1;
		   if((endRecNo = startRecNo + 19) > TotalRecords)
		   		 endRecNo = TotalRecords;
		   if(TotalRecords ==0 )
		   		startRecNo=0;			 
		%>

	<% 
			
			String strOrder = (String)request.getAttribute("Order");
			String displayOrder = "Sort ";
			String imageName=null;
			if(strOrder.equals("Asc")) 
			{
				strOrder = "Desc";
				imageName = "Uarrow.gif";
			}	
			else
			{
				strOrder = "Asc";			
				imageName = "Darrow.gif";
			}	
			
			displayOrder += strOrder;
		%>


	<table width="100%" style="border: 1 solid #000080" align="center">
		<tr>
			<td>
			<table width="100%" align="">
				<tr>
					<!-- Page Number Combo Box -->
					<td width="4%">&nbsp;</td>
					<td width="30%">&nbsp;</td>
					<td width="36%" align="center"><b> <font face="Verdana"
						color="#000080" size="2">Listing Of Templates</font></b></td>
					<td width="30%">&nbsp;</td>
				</tr>
				<tr>
					<td width="4%" align="left"><b> <font face="Verdana"
						color="c00000" size="2">Page:</font></b></td>
					<td width="30%" align="left"><s:select list="pageDtl"
						name="givenPageNumber" listKey="pageNumber" listValue="pageNumber"
						onchange="return pagingSubmit();">
					</s:select></td>
					<td width="36%" align="center"><b> <font face="Verdana"
						color="#000080" size="2"> Sort Order: </font> <font face="Verdana"
						color="c00000" size="2"> ${Order} </font> <font face="Verdana"
						color="#000080" size="2"> Field: </font> <font face="Verdana"
						color="c00000" size="2"> Description </font> </b></td>
					<td width="30%" align="right"><b> <font face="Verdana"
						color="c00000" size="2"> Total Records: <%=startRecNo%> - <%=endRecNo%>/<%=TotalRecords%>
					</font></b></td>
				</tr>
				<!-- Total Records Display -->
			</table>
			</td>
		</tr>
		<tr>
			<td align="center">
			<%int srNo = 1; %> <br>
			<table align="center" width="98%" class="datatable">
				<thead>
					<tr>

						<th>#</th>
						<th>Description</th>
						<th>Remarks</th>
						<th>ModifyOn</th>
						<th>Open</th>

					</tr>
				</thead>
				<tbody>
					<s:iterator value="getTemplateDtl" id="getTemplateDtl"
						status="status">
						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
							<td><%=startRecNo%></td>
							<td><s:property value="templateDesc" /> &nbsp;-&nbsp;<s:property
								value="templateId" /></td>

							<td><s:property value="remark" /></td>
							<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
							<td>
							<div align="center"><a title="Open" href="#"
								onclick=" callOpenTree('<s:property value="templateId"/>');">
							<img border="0px" alt="Activate" src="images/Common/open.png"
								height="18px" width="18px"> </a></div>
							</td>
							<!--   	<td>
					       		<a href="DeleteClient.do?clientCode=<s:property value="clientCode" />&statusIndi=<s:property value="statusIndi" />" onClick="return authenticate();">Revert</a>
					   	</td> -->
						</tr>
						<%startRecNo=startRecNo+1;%>
					</s:iterator>

				</tbody>
			</table>

			</td>
		</tr>

	</table>
	<input type="hidden" name="SortOrder" value="<%=strOrder%>">
</s:form> <br>
</div>
</div>
</div>
</body>
</html>
