<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.Vector"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.docmgmt.dto.*"%>
<html>
<head>
<s:head />
<style type="text/css">
body {
	scrollbar-arrow-color: blue;
	scrollbar-face-color: #e7e7e7;
	scrollbar-3dlight-color: #a0a0a0;
	scrollbar-darkshadow-color: #888888;
}
</style>
</head>
<body>

<div class="headercls">Workspace node Rights</div>

<div align="left" class="bdycls"><br>

<s:form action="saveWorkspaceEctdAttrValue" method="post">

	<font color="blue violet" size=2> <u><b>${displayName}</b></u> </font>
	<table>

		<!-- ------------dynamic display of attribute started here ----------------->

		<%Vector attrDtl=(Vector) request.getAttribute("attrDtl"); %>


		<%if(attrDtl.size() ==0) 
       {
    	   out.print("currently No Attribute found");
       }
       else
       {	
    	   int elementid=1;
    	   DTOWorkSpaceNodeAttribute obj = new DTOWorkSpaceNodeAttribute();
    	   int attrdtlvectsize=attrDtl.size();
    		//TextArea
    		
    		for(int i=0;i<attrdtlvectsize;i++)
    		{
    			obj=(com.docmgmt.dto.DTOWorkSpaceNodeAttribute)attrDtl.get(i);
    			if(obj.getAttrType().equals("TextArea"))
    			{
    				
    		%>
		<tr>
			<td class="title"><%=obj.getAttrName()%></td>
			<td><TEXTAREA NAME="<%="attrValueId"+elementid%>" COLS=40 ROWS=6><%=obj.getAttrValue()%></TEXTAREA>

			<input type="hidden" name="<%="attrType"+elementid%>"
				value="TextArea"> <input type="hidden"
				name="<%="attrName"+elementid %>" value="<%=obj.getAttrName() %>">
			<input type="hidden" name="<%="attrId"+elementid %>"
				value="<%=obj.getAttrId() %>"></td>
		</tr>


		<%	elementid++;
    			}
    		}
    		
    		///Text
    		
    		for(int i=0;i<attrdtlvectsize;i++)
    		{
    			obj=(com.docmgmt.dto.DTOWorkSpaceNodeAttribute)attrDtl.get(i);
    			if(obj.getAttrType().equals("Text"))
    			{
    	   %>

		<tr>
			<td class="title"><%=obj.getAttrName() %></td>
			<td><input type="text" name="<%="attrValueId"+elementid%>">
			<input type="hidden" name="<%="attrType"+elementid %>" value="Text">
			<input type="hidden" name="<%="attrName"+elementid %>"
				value="<%=obj.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=obj.getAttrId() %>">
			</td>
		</tr>

		<%	elementid++;
    	   
    			}
    			
    		}
    		
    		/////date
				for(int i=0;i<attrdtlvectsize;i++)
    		{
    			obj=(com.docmgmt.dto.DTOWorkSpaceNodeAttribute)attrDtl.get(i);
    			if(obj.getAttrType().equals("Date"))
    			{
    		%>

		<tr>
			<td class="title"><%=obj.getAttrName() %></td>
			<td><input type="text" name="<%="attrValueId"+elementid %>"
				id="<%="attrValueId"+elementid %>" ReadOnly="readonly" size="12"
				value="<s:date name="obj.attrValue" format="yyyy/M/dd"/>">
			&nbsp;<IMG
				onclick="popUpCalendar(this,<%="attrValueId"+elementid %>,'yyyy/mm/dd');"
				src="<%=request.getContextPath() %>/images/Calendar.png"
				align="middle"> <input type="hidden"
				name="<%="attrType"+elementid %>" value="Date"> <input
				type="hidden" name="<%="attrName"+elementid %>"
				value="<%=obj.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=obj.getAttrId() %>">
			</td>
		</tr>

		<%	elementid++;
    	   
    			}
    			
    		}
			
    		
    		////file
    			for(int i=0;i<attrdtlvectsize;i++)
    			{
    			obj=(com.docmgmt.dto.DTOWorkSpaceNodeAttribute)attrDtl.get(i);
    			if(obj.getAttrType().equals("Date"))
    			{
    		
    		%>

		<tr>
			<td class="title"><%=obj.getAttrName() %></td>
			<td><input type="file" name="<%="attrValueId"+elementid %>">
			<input type="hidden" name="<%="attrType"+elementid %>" value="File">
			<input type="hidden" name="<%="attrName"+elementid %>"
				value="<%=obj.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=obj.getAttrId() %>">
			</td>
		</tr>



		<%	}
    		
    		}
    		
    		////combo
    		
    			int prevattrid=-1;
    		
    			for(int i=0;i<attrdtlvectsize;i++)
        		{
        			obj=(com.docmgmt.dto.DTOWorkSpaceNodeAttribute)attrDtl.get(i);
        			if(obj.getAttrType().equals("Combo"))
        			{	
    					if(i==0 || prevattrid == -1 || prevattrid !=obj.getAttrId() )
    					{	
    		%>
		<tr>
			<td class="title"><%=obj.getAttrName() %></td>
			<td><select name="<%="attrValueId"+(elementid)%>">

				<%
  					
  					for(int j=0;j<attrdtlvectsize;j++)
  	        		{
  						DTOWorkSpaceNodeAttribute tempobj=new DTOWorkSpaceNodeAttribute();
  						tempobj=(com.docmgmt.dto.DTOWorkSpaceNodeAttribute)attrDtl.get(j);
  						
  						if(tempobj.getAttrId()==obj.getAttrId())
  						{
  							out.print("<OPTION value="+tempobj.getAttrId());
  							  							
  							out.print(">");
  							out.print(tempobj.getAttrValue());
  							out.print("</OPTION> \n");  							
  						}
  						
  	        		}	
  					%>



			</select> <input type="hidden" name="<%="attrType"+elementid %>" value="Combo">
			<input type="hidden" name="<%="attrName"+elementid %>"
				value="<%=obj.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=obj.getAttrId() %>">

			</td>

		</tr>
		<% elementid++;
    			}
  			prevattrid=obj.getAttrId();
        	  }
    		}	%>
		<!-- ------------dynamic display of attribute ended here ----------------->

		<tr>
			<td></td>
			<td><s:submit value="Save" cssClass="button"></s:submit></td>
		</tr>

	</table>

	<input type="hidden" name="attrCount" value="<%=elementid%>">


	<% }//end of else
       %>


	<input type="hidden" name="WsnodeId" value="${nodeId}">
	<input type="hidden" name="workspaceID" value="${workspaceID}">
</s:form>

<div id="attribvalueList" align="center">
<%
 Vector attrDtl1=(Vector) request.getAttribute("attrDtl"); 
 int srNo = 1;
 if(attrDtl1.size() !=0) 
       {%>


<table class="datatable" width="50%" align="left">
	<thead>
		<tr>
			<th>#</th>
			<th>Name</th>
			<th>Value</th>

		</tr>
	</thead>
	<tbody>
		<s:iterator value="attributevalueList" status="status">
			<s:hidden value="statusIndi" id="statusIndi" />
			<s:if test="statusIndi == 'D'">
				<tr class="matchFound">
			</s:if>
			<s:else>
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			</s:else>
			<td><%=srNo++ %></td>
			<td><s:property value="attrName" /></td>
			<td><s:property value="attrValue" /></td>
			</tr>
		</s:iterator>

	</tbody>
</table>
</div>
<%} %>
</div>
</body>
</html>
