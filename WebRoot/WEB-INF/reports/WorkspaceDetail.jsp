<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>

<table>

	<s:if test="wsDtl.size == 0">
		<tr>
			<td><font color='red' face='Arial' size='2'>No Project
			Found</font></td>
		</tr>

	</s:if>
	<s:else>

		<tr>
			<td><s:select list="wsDtl" name="workSpaceId" headerKey="-1"
				headerValue="Select Project Name" listKey="workSpaceId"
				listValue="workSpaceDesc">
			</s:select> <ajax:event ajaxRef="showUserDtl/getUserDtl" /></td>

		</tr>

	</s:else>

</table>




