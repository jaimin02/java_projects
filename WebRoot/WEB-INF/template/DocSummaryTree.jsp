<%@ page language="java"%>
<%@ page contentType="text/html"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="com.docmgmt.server.webinterface.beans.*"%>


<jsp:useBean id="nIndex" scope="page"
	class="com.docmgmt.server.webinterface.beans.nIndex" />


<html>
<body>


<%
	int totalEmptyNodes = 0;
	int totalFilledNodes = 0;
	int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	 
		
%>
<% String checkDate = request.getParameter("checkDate"); %>
<% String workspaceId = request.getParameter("workSpaceId"); %>
<% String modifyAfBf = request.getParameter("modifyAfBf"); %>
<% String searchOn = request.getParameter("opvalue"); %>
<% String status = request.getParameter("status");%>
<% String bgcolor = "white" ; %>
<% 
   String fieldName = null ; 
   String fieldValue = null;
   String userName = null;
   String errorFlag = "false";
%>
<% 
   Date mdate=null;
   SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
   Date cDate = null;
%>

<% 
if(workspaceId==null || workspaceId.equals("-1"))
	errorFlag=" Project Not Selected....";
else if(searchOn.equals("D")) { 
   if(modifyAfBf.equals("1")) { 
      				if(!checkDate.equals("")){
      							fieldName=" last modify date after " ;   
								cDate = df.parse(checkDate); 
								fieldValue = checkDate;
					}
					else	
							errorFlag = " Modify Date Not Selectd....";
						} 
   else if(modifyAfBf.equals("2")) { 
   				if(!checkDate.equals("")){
   									fieldName=" last modify date before " ; 
   								    cDate = df.parse(checkDate); 
									fieldValue = checkDate;
						}
				else	
					errorFlag = " Modify Date Not Selectd....";
	}						
   else {
   			errorFlag = "Modify After / Before Not selected....";
   		}								  
}else if(searchOn.equals("U")){    
   		userName = nIndex.getUserName(Integer.parseInt(request.getParameter("userCode")));
   		fieldName= " last modify by ";
   		if(userName!=null){
	   		fieldValue = userName;
	   	}else{
	   		fieldValue="All";
	   	}
}else if(searchOn.equals("S")){
	if(!status.equals("0"))
	{
		fieldName= " status ";
		fieldValue = status;
	}
	else
		errorFlag = "Status Value Not Selected...";
}		   
%>

<table width="100%" border="0">
	<tr>
		<td align="right"><a href="javascript:self.close();"> Back </td>
	</tr>
</table>

<table width="100%" border="0" style="border: 1 solid #C0C0C0">
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="1" cellpadding="2">
			<tr>
				<td width="100%" bgcolor="#CBCBE4"><b> <font size="2"
					face="Verdana"> &nbsp;Report Structure </font> </b></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table bgcolor="#E5E5E5">
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/open2.gif"></td>
				<td valign="center"><b><font face="verdana" size="2"
					color="#C00000"> Project Name </font></b></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>
		<table border="0" width="100%">
			<tr>
				<td width="2%"></td>
				<td width="2%" bgcolor="#E5E5E5"><img
					src="<%=request.getContextPath()%>/images/open2.gif"></td>
				<td width="10%" bgcolor="#E5E5E5"><b><font face="verdana"
					size="2" color="#C00000"> Parent Node </font></b></td>
				<td width="86%"></td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td>
		<table border="0" width="100%">
			<tr>
				<td width="4%"></td>
				<td width="2%"><img
					src="<%=request.getContextPath()%>/images/file.png"></td>
				<td width="10%"><b><font face="verdana" size="2"
					color="black"> Leaf Node </font></b></td>
				<td width="84%"></td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td>
		<table>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><b><font face="verdana" size="2" color="blue">|_</font></b>
				</td>
				<td>
				<table border="0" bgcolor="#F8F8F8" style="border: 1 solid #C0C0C0">
					<tr>
						<td><font face="verdana" size="2" color="blue"> File
						Name | </font></td>
						<td><font face="verdana" size="2" color="blue">
						&nbsp;Version | </font></td>
						<td><font face="verdana" size="2" color="blue">
						&nbsp;User Name | </font></td>
						<td><font face="verdana" size="2" color="blue">
						&nbsp;Last Modify Date | </font></td>
						<td><font face="verdana" size="2" color="blue">
						&nbsp;Status | </font></td>
						<td><font face="verdana" size="2" color="blue">
						&nbsp;Effective Date </font></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<br>
<% if(errorFlag.equals("false")) { %>
<table width="100%" border="0" style="border: 1 solid #C0C0C0">
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="1" cellpadding="2">
			<tr>
				<td width="100%" bgcolor="#CBCBE4"><b> <font size="2"
					face="Verdana"> &nbsp;Search on&nbsp;<%=fieldName%> </font> <font
					size="2" face="Verdana" color="#C00000"> &nbsp;<%=fieldValue%>
				</font> <%   if(searchOn.equals("D")) { %> <font size="2" face="Verdana">
				&nbsp;date </font> <% } %> </b></td>
			</tr>
		</table>
		</td>
	</tr>

</table>
<% } else { %>
<table bgcolor="#FCE0E3" border="0" width="100%"
	style="border: 1 solid #FF0000">
	<tr bgcolor="#FCE0E3" align="center">
		<td width="100%">
		<table bgcolor="#FCE0E3" width="100%">
			<tr>
				<td width="20%" align="left">&nbsp;&nbsp;<img
					src="<%=request.getContextPath()%>/images/stop_round.gif"></td>
				<td width="75%" valign="center" align="left"><b><font
					face="System" color="red" size="2">&nbsp;&nbsp;Error Message
				:</font></b> <b><font face="System" color="black" size="2"><%=errorFlag%>
				</font></b></td>
				<td width="5%">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<% } %>

<br>

<br>
<%	Vector getDtl = nIndex.getTreeNodes(workspaceId,userGroupCode,userCode); %>

<% if(errorFlag.equals("false")) { %>
<table width="100%" border="0" style="border: 1 solid #C0C0C0">
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="1" cellpadding="2">
			<tr>
				<td width="100%" bgcolor="#CBCBE4"><b> <font size="2"
					face="Verdana"> &nbsp;Report Detail </font> </b></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<%	


	int PreviousSpace = 0;

	
	for(int i=0;i<getDtl.size();i++)
	{
		bgcolor="white"; 	
		Object[] getRec = (Object[])getDtl.get(i);
		if(getRec[3]==null){
			getRec[3]=(Object)new String();
		}
		Integer space=new Integer(0);
		space = (Integer)getRec[7];
		String type = getRec[8].toString();
		//out.print(getRec[7]);
%>
		<table>
			<tr>
				<%	for(int j=0;j<space.intValue()*2;j++) { %>
				<td>&nbsp;</td>
				<%	}if(type.equals("P")){ %>
				<td>
				<table bgcolor="#E5E5E5">
					<tr>
						<td valign="top"><img
							src="<%=request.getContextPath()%>/images/open2.gif"></td>
						<td valign="center"><b><font face="verdana" size="2"
							color="#C00000"> <%	out.print( getRec[1] );%> </font></b></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<% } else { if(!getRec[3].equals("-")) {  
	if(getRec[6].toString()!="" && getRec[6].toString()!="-"){
		mdate = df.parse(getRec[6].toString()); 
	}
	if(searchOn.equals("D") && mdate!=null){
	 if(modifyAfBf.equals("1"))
		{
		 if(mdate.getTime() > cDate.getTime())
		 	  bgcolor="#FFDCFF";
    	} 
	 else if(modifyAfBf.equals("2"))
		{
		 if(mdate.getTime() < cDate.getTime())
		  	bgcolor="#FFDCFF";
		}
	else
		bgcolor="white";
	}
	else if(searchOn.equals("U")){
		if(getRec[5] !=null){
			if(userName.equals(getRec[5].toString()))
				bgcolor="#FFDCFF";
		}
	}
	else if(searchOn.equals("S")){
	if(getRec[9]!=null)
		if(status.equals(getRec[9].toString()))
			bgcolor="#FFDCFF";
	}		
 }

%>
		<td>
		<table bgcolor="<%=bgcolor%>">
			<tr>
				<td valign="top">
				<% String fname = (String)getRec[3] ;%> <% String fext[] = fname.replace('.','-').split("-",0);%>

				<% if(getRec[3].equals("-")) { %> <img
					src="<%=request.getContextPath()%>/images/file.png"> <% } else {
			if(fext.length > 0)
			{
				if(fext[fext.length-1].equals("doc")) { %> <img
					src="<%=request.getContextPath()%>/images/3.gif"> <% } else	if(fext[fext.length-1].equals("pdf")) { %>
				<img src="<%=request.getContextPath()%>/images/1.gif"> <% } } } %>

				</td>
				<td><b><font face="verdana" size="2"> <% out.print(getRec[1]); %>
				</font></b></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table>
	<%		
		for(int j=0;j<(space.intValue()*2)+3;j++)
		{
%>
	<td>&nbsp;</td>
	<%	} %>
	<td><b><font face="verdana" size="2" color="blue">|_</font></b></td>
	<td>
	<table border="0" bgcolor="#F8F8F8" style="border: 1 solid #C0C0C0">
		<% if(!getRec[3].equals("-")) { totalFilledNodes++; %>
		<tr>
			<td><font face="verdana" size="2" color="blue"> <%	
						if(getRec[3]!=null){
							out.print(getRec[3]); 
						}		
					%> </font></td>
			<td><font face="verdana" size="2" color="blue">
			&nbsp;&nbsp; <%
						if(getRec[4]!=null){					
					 		out.print(getRec[4]); 
						} 		
					%> </font></td>
			<td><font face="verdana" size="2" color="blue">
			&nbsp;&nbsp; <%
					if(getRec[5]!=null){
					 	out.print(getRec[5]);
					}
				 %> </font></td>
			<td><font face="verdana" size="2" color="blue">
			&nbsp;&nbsp; <% 
							if(getRec[6]!=null){
								out.print( getRec[6] ); 
							}
						%> </font></td>
			<td><font face="verdana" size="2" color="blue"> <% if(getRec[9]!=null) {%>
			&nbsp;&nbsp;<% out.print( getRec[9] ); %> <%} else { %> &nbsp;&nbsp;- <% } %>
			</font></td>
			<td><font face="verdana" size="2" color="blue"> <% if(getRec[12]!=null) {%>
			&nbsp;&nbsp; <% 
						String eDate=getRec[12].toString().replace('/','-');		
						out.print( eDate ); %> <%} else { %> &nbsp;&nbsp;- <% } %> </font></td>
		</tr>
		<% } else { totalEmptyNodes++ ; %>
		<tr>
			<td><font face="verdana" size="2" color="#C00000"> File
			Not Attached </font></td>
		</tr>
		<% } %>
	</table>
	</td>
	</tr>
</table>
<% } %>
<%		
	PreviousSpace = space.intValue();
%>
<%		
	}	
%>
</td>
</tr>
</table>
<% } %>

<% if(totalEmptyNodes > 0 ) { %>
<table border="0" width="100%" cellspacing="1" cellpadding="2"
	height="5%">
	<tr>
		<td width="100%" bgcolor="#c00000" align="center"><b> <font
			size="2" face="Verdana" color="white"> <% int total = totalFilledNodes + totalEmptyNodes;%>
		Total Empty Nodes :-- <%=totalEmptyNodes%> / <%=total%> </font> </b></td>
	</tr>
</table>
<% } %>

</body>
</html>