<%@ page language="java"%>
<%@ page contentType="text/html"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>



<jsp:useBean id="nIndex" scope="page"
	class="com.docmgmt.server.webinterface.beans.nIndex" />


<html>
<head>
<s:head />
<script>
	
		function changeDirColor(val,nodeId)
		{
			if(document.getElementById(val).style.backgroundColor == "#ffffa6" || document.getElementById(val).style.backgroundColor == "rgb(255, 255, 166)")
			{
				document.getElementById(val).style.backgroundColor = ""
				var selectedNodes = document.saveProjectForm.selectedNodes.value;
				var newSelectedNodes = "";
				var selectedNodes_array = selectedNodes.split("#");
				for(i=0;i<selectedNodes_array.length;i++)
				{
					if(selectedNodes_array[i]!="" && selectedNodes_array[i]!=nodeId)
						newSelectedNodes+=selectedNodes_array[i]+"#";
				}
				document.saveProjectForm.selectedNodes.value = newSelectedNodes;
			}	
			else
			{
				document.getElementById(val).style.backgroundColor = "#ffffa6";
				document.saveProjectForm.selectedNodes.value += nodeId + "#";
			}	
			return true;
		}
		
		function changeFileColor(val,filename,nodeId)
		{
			//debugger;
			if(filename!="File Not Found")
			{				
				if(document.getElementById(val).style.backgroundColor == "#ffffd9" || document.getElementById(val).style.backgroundColor == "rgb(255, 255, 217)")
				{
					
					document.getElementById(val).style.backgroundColor = ""
					var selectedNodes = document.saveProjectForm.selectedNodes.value;
					var newSelectedNodes = "";
					var selectedNodes_array = selectedNodes.split("#");
					for(i=0;i<selectedNodes_array.length;i++)
					{
						if(selectedNodes_array[i]!="" && selectedNodes_array[i]!=nodeId)
							newSelectedNodes+=selectedNodes_array[i]+"#";
					}
					document.saveProjectForm.selectedNodes.value = newSelectedNodes;
				}	
				else
				{
					
					document.getElementById(val).style.backgroundColor = "#ffffd9";
					document.saveProjectForm.selectedNodes.value += nodeId + "#";
				}
			}
			return true;
		}
	
		function validate()
		{
			var selectedNodes = document.saveProjectForm.selectedNodes.value;
			
			if(selectedNodes=="")
			{
				alert("Not a single node selected for copy. Please select node.");
				return false;
			}
			return true;
		}
		
		function hideErrorBox()
		{
			
		}
		
		function openFile(filePath)
		{
		
			var str="file:\\"+filePath+"#toolbar=false"
			alert(str);
			window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=900,width=900,resizable=yes,titlebar=no");
		}
	</script>
</head>

<body>
<s:form action="CopyProjectFiles" name="saveProjectForm">
	<br>

	<%
	int totalEmptyNodes = 0;
	int totalFilledNodes = 0;
	int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	String workspaceId = request.getParameter("workSpaceId");
	String ws_desc = request.getParameter("sourceWorkSpaceDesc");
	String dest_ws_id = (String)request.getAttribute("destWsId");
	String fileName = "";
	String bgcolor = "black" ;
	Vector getDtl = nIndex.getTreeNodes(workspaceId,userGroupCode,userCode); 
%>

	<hr>
	<table width="100%" border="0">
		<tr>
			<td align="center"><b><u><font face="verdana" size="2"
				color="Black"><%=ws_desc%></font></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center"><s:submit onclick="return validate();"
				value="Copy Files" cssClass="button" /></td>
		</tr>
		<tr>
			<td>
			<hr>
			</td>
		</tR>
		<tr>
			<td>
			<%	

	int PreviousSpace = 0;
	String tableId = "";
	String hiddenId = "";
	
	for(int i=0;i<getDtl.size();i++)
	{
		Object[]getRec = (Object[])getDtl.get(i);
		Integer space = (Integer)getRec[7];
		Integer nodeIdValue = (Integer)getRec[0];
		String type = getRec[8].toString();
		tableId = "tableId_" + i;
		hiddenId = "hiddenId_"+i;
%>
			<table>
				<tr>
					<%	for(int j=0;j<space.intValue()*2;j++) { %>
					<td>&nbsp;</td>
					<%	}if(type.equals("P")){ %>
					<td>
					<table id="<%=tableId%>">
						<tr>
							<td valign="top"><img
								src="<%=request.getContextPath()%>/images/open2.gif"
								onclick="return changeDirColor('<%=tableId%>','<%=getRec[0].toString()%>')">
							</td>
							<td valign="middle"><b><font face="verdana" size="2"
								color="#C00000"> <%	out.print( getRec[1] );%> &nbsp;&nbsp;
							</font></b></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<% } else { %>
			<td>
			<%if(!getRec[3].equals("-")) { bgcolor="black"; totalFilledNodes++; fileName = getRec[3].toString();} else { bgcolor="blue";  fileName = "File Not Found" ; totalEmptyNodes++; }%>
			<table id="<%=tableId%>">
				<tr>
					<td valign="top"><img
						src="<%=request.getContextPath()%>/images/file.png"
						alt="<%=fileName%>"
						onclick="return changeFileColor('<%=tableId%>','<%=fileName%>','<%=getRec[0].toString()%>')">
					</td>
					<td><b><font face="verdana" size="2" color="<%=bgcolor%>">
					<%if("File Not Found".equals(fileName)){ %> <font size="2"> <% out.print(getRec[1]); %>
					</font><font size="2" color="red"><b>(File not Found)</b></font> <%}else {%>
					<a href="#"
						onclick="return openFile('<% out.print(getRec[1]); %>');"><font
						size="2" color="green"> <% out.print(getRec[1]); %>
					</a>&nbsp;&nbsp; <%}%> </font></b></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<% } PreviousSpace = space.intValue(); } %>
	</td>
	</tr>
	</table>

	<% if(totalEmptyNodes > 0 ) { %>
	<table border="0" width="100%" cellspacing="1" cellpadding="2"
		height="30">
		<tr>
			<td width="100%" bgcolor="#c00000" align="center"><b> <font
				size="2" face="Verdana" color="white"> <% int total = totalFilledNodes + totalEmptyNodes;%>
			Total Empty Nodes :-- <%=totalEmptyNodes%> / <%=total%> </font> </b></td>
		</tr>
	</table>
	<% } %>

	<input type="hidden" id="selectedNodes" name="selectedNodes" value="">
	<input type="hidden" id="sourceWsId" name="sourceWsId"
		value="<%=workspaceId%>">
	<input type="hidden" id="destWsId" name="destWsId"
		value="<%=dest_ws_id%>">


</s:form>
</body>
</html>