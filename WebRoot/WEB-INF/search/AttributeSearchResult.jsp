<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.docmgmt.dto.DTOWorkSpaceNodeDetail"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>



<head>
<s:head />

<script language="javascript">
	function hide(str)
	{
		if(document.getElementById(str).style.display=="inline")
	   		document.getElementById(str).style.display = 'none';
		else
			document.getElementById(str).style.display = 'inline';
	}
</script>
</head>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div id="ShowAttributeSearchResult" align="center">
<br>
<center>
<s:if test="nodeDetail.size == 0  ">
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"> <br>
			</td>
			<td align="center" width="90%"><font size="2" color="#c00000"><b><br>
			No records found for selected search criteria<br>
			&nbsp;</b></font> <br>
			</td>
		</tr>
	</table>
</s:if>
<s:else>
<table class="datatable" width="95%">
	<tr>
      <th> Sr No.
      </th>
      <th> Project Name
      </th>
      <th> Node Name
      </th>
      <th> Attribute Value
      </th>
      <th> Modified By
      </th>
      <th> Modified On
      </th>
	</tr>
 <s:iterator value="nodeDetail" status="status">
	<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
	   <td> 
 	   		<s:property value="#status.count"/>
      </td>
 	  <td> 
 	   		<s:property value="workSpaceDesc"/>
      </td>
 	  <td> 
 	  		<s:property value="nodeDisplayName"/> 
      </td>
      <td> 
     	 <s:property value="attrValue"/> 
      </td>
        <td> 
     	 <s:property value="userName"/> 
      </td>
       <td> 
     	 <s:date name="modifyOn" format="dd-MMM-yyyy HH:mm"/> 
      </td>
      
	</tr>
 </s:iterator>
</table>
</s:else>

</center>
</div>
