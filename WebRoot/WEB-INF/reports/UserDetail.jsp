<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>

<table>

	<s:if test="userDtl.size ==0">
		<tr>
			<td><font color='red' face='Arial' size='2'>No User Found</font>

			</td>
		</tr>
	</s:if>
	<s:else>


		<tr>

			<td><s:select list="userDtl" name="userCode" headerKey="0"
				headerValue="All" listKey="userCode" listValue="loginName">
			</s:select></td>

		</tr>
	</s:else>

</table>




