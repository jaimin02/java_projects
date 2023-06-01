<%@page import="com.docmgmt.dto.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>


<html>
<head>
<s:head />

<script language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/CalendarPopup.js"></SCRIPT>
<script language="javascript">
	function setDescription()
	{
		var keyword = document.workspaceNodeAttrForm.keyWord.value;
		document.workspaceNodeAttrForm.description.value = keyword; 
		var newstring = keyword.split("/");
		document.workspaceNodeAttrForm.versionPrefix.value = newstring[0];
		return true;		
	}
	function validate()
	{
		var takeVersion = document.workspaceNodeAttrForm.takeVersion.value;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
	
		var takeVersion = document.workspaceNodeAttrForm.takeVersion.value;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
		
		var sp = uploadFile.split('\\');
		var fname = sp[sp.length - 1].indexOf(".");
		var fname1=sp[sp.length-1];
		var oas = new ActiveXObject("Scripting.FileSystemObject");
		var d = document.workspaceNodeAttrForm.uploadFile.value;
		var e = oas.getFile(d);
		var f = e.size;
		
		if(f>104857600)
		{
			alert("File size must not be greater then 100MB");
			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     	document.workspaceNodeAttrForm.uploadFile.focus();
	     	return false;
		}	
		var strInvalidChars = "()/\^$#:~%@&";
    	var strChar;
   		if(fname>50)
	    {
			alert("File name must not be greater then 50 char.");
			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     	document.workspaceNodeAttrForm.uploadFile.focus();
	     	return false;
	    }	
       	for (i = 0; i < fname1.length; i++)
    	{
 			strChar = fname1.charAt(i);
		 	if (strInvalidChars.indexOf(strChar)!= -1)
				{
	      			alert("Invalid project name." + " " + strChar + "  is not allowed" );
	      			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	      			return false;  			
 				}
	   	}
		if(uploadFile=="")
		{
			alert("Please upload new sop version.");
			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     	document.workspaceNodeAttrForm.uploadFile.focus();
			return false;
		}
		else if(takeVersion=="true")
		{
			var versionPrefix = document.workspaceNodeAttrForm.versionPrefix.value;
			var versionSuffix = document.workspaceNodeAttrForm.versionSuffix.value;
			if(versionPrefix=="")
			{
				alert("Please enter sop number prefix.");
				document.workspaceNodeAttrForm.versionPrefix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionPrefix.focus();
				return false;
			}
			else if(versionSuffix=="")
			{
				alert("Please enter sop number suffix(initial version).");
				document.workspaceNodeAttrForm.versionSuffix.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.versionSuffix.focus();
				return false;
			}
		}
		return true;
	}
	function callAddMore(attrId)
	{
   		strAction = "addMoreAttribute.do?attrId="+attrId;
   		window.open(strAction,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=130,width=500,resizable=no,titlebar=no");	
   		return true;
	}
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
</script>

</head>

<body>
<%
 	Vector attrDtlVect=(Vector)request.getAttribute("attrDtl");
 	Vector attrHistoryVect=(Vector)request.getAttribute("attrHistory");
 	%>
<%String takeVersion=request.getAttribute("takeVersion").toString(); %>

<s:form name="workspaceNodeAttrForm" method="post"
	action="SaveNodeAttrAction" enctype="multipart/form-data">
	<s:hidden name="nodeId"></s:hidden>

	<div class="headercls">Edit Node Detail</div>
	<div class="bdycls">

	<table width="100%" align="center">
		<!-- ------------dynamic display of attribute started here ----------------->



		<%
	int elementid=1;
	
	DTOAttributeValueMatrix objDTO=new DTOAttributeValueMatrix();
	DTOWorkSpaceNodeAttrHistory objDTOvalue=new DTOWorkSpaceNodeAttrHistory();
	
	int attrdtlvectsize=attrDtlVect.size();
	//TextArea
	String TextData="";
	
	for(int i=0;i<attrdtlvectsize;i++)
	{
		objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
		if(objDTO.getAttrType().equals("TextArea") /*&& objDTO.getUserInput()=='Y'*/)
		{
			String attrname=objDTO.getAttrName();
	%>
		<tr>
			<td class="title"><%=attrname %></td>
			<td>
			<%for(int k=0;k<attrHistoryVect.size();k++)
			{
				objDTOvalue=(com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory)attrHistoryVect.get(k);
				if(objDTOvalue.getAttrId()==objDTO.getAttrId())
				{
					TextData=objDTOvalue.getAttrValue().trim(); 
				}
				
			}
		%> <TEXTAREA NAME="<%="attrValueId"+elementid%>" COLS=40 ROWS=6><%=TextData%></TEXTAREA>

			<input type="hidden" name="<%="attrType"+elementid%>"
				value="TextArea"> <input type="hidden"
				name="<%="attrName"+elementid %>" value="<%=objDTO.getAttrName() %>">
			<input type="hidden" name="<%="attrId"+elementid %>"
				value="<%=objDTO.getAttrId() %>"></td>
		</tr>
		<%	
			elementid++;
		}
		
		
	}%>
		<!-- Text Box -->
		<%
	
	for(int i=0;i<attrdtlvectsize;i++)
	{
	
			String attrvalue="";
			
			objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
			if(objDTO.getAttrType().equals("Text") /*&& objDTO.getUserInput()=='Y'*/)
			{
				
				
				
				for(int k=0;k<attrHistoryVect.size();k++)
				{
					objDTOvalue=(com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory)attrHistoryVect.get(k);
				
					if(objDTOvalue.getAttrId()==objDTO.getAttrId())
					{
						attrvalue=objDTOvalue.getAttrValue();
					}
				
				}
				
	%>
		<tr>
			<td class="title"><%=objDTO.getAttrName() %></td>
			<td>
			<%String textvalue="";
					if(objDTOvalue.getAttrValue()==null)
						{	textvalue="";}
						else{textvalue=objDTOvalue.getAttrValue();}%> <input type="text"
				name="<%="attrValueId"+elementid%>" value="<%=textvalue %>">
			<input type="hidden" name="<%="attrType"+elementid %>" value="Text">
			<input type="hidden" name="<%="attrName"+elementid %>"
				value="<%=objDTO.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=objDTO.getAttrId() %>">
			</td>
		</tr>


		<%
	
	elementid++; 
		}
	
	}
	
	%>
		<!-- date -->

		<% 
	for(int i=0;i<attrdtlvectsize;i++)
	{
		
		objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
		if(objDTO.getAttrType().equals("Date") /*&& objDTO.getUserInput()=='Y'*/)
		{
			
			
	%>
		<tr>
			<td class="title"><%=objDTO.getAttrName() %></td>
			<td><input type="text" name="<%="attrValueId"+elementid %>"
				size="12" value=""> &nbsp; <img
				onclick="onclick=popUpCalendar(this,<%="attrValueId"+elementid %>,'yyyy/mm/dd');"
				src="<%=request.getContextPath()%>images/Calendar.png"
				align="middle"> <input type="hidden"
				name="<%="attrType"+elementid %>" value="Date"> <input
				type="hidden" name="<%="attrName"+elementid %>"
				value="<%=objDTO.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=objDTO.getAttrId() %>">
			</td>
		</tr>


		<% 
		elementid++;
		}
	}%>

		<!-- file -->
		<%for(int i=0;i<attrdtlvectsize;i++)
	{
		objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
		if(objDTO.getAttrType().equals("File") /*&& objDTO.getUserInput()=='Y'*/)
		{
			
	%>
		<tr>
			<td class="title"><%=objDTO.getAttrName() %></td>
			<td><input type="file" name="<%="attrValueId"+elementid %>">
			<input type="hidden" name="<%="attrType"+elementid %>" value="File">
			<input type="hidden" name="<%="attrName"+elementid %>"
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
	DTOAttributeMst objTempDTO=new DTOAttributeMst();
	
	for(int i=0;i<attrdtlvectsize;i++)
	{	
		String combovalue="";
		
			objDTO=(com.docmgmt.dto.DTOAttributeValueMatrix)attrDtlVect.get(i);
	
			if(objDTO.getAttrType().equals("Combo") /*&& objDTO.getUserInput()=='Y'*/)
			{
				if(i==0 || prevattrid == -1 || prevattrid !=objDTO.getAttrId() )
				{
		%>
		<tr>
			<td class="title"><%=objDTO.getAttrName() %></td>
			<td><select name="<%="attrValueId"+(elementid)%>">

				<% 		for(int k=0;k<attrdtlvectsize;k++)
				{
					objTempDTO=(com.docmgmt.dto.DTOAttributeMst)attrDtlVect.get(k);
					if(objDTO.getAttrId()==objTempDTO.getAttrId())
					{	
						for(int j=0;j<attrHistoryVect.size();j++)
						{
							objDTOvalue=(com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory)attrHistoryVect.get(j);
						
							if(objDTOvalue.getAttrId()==objDTO.getAttrId())
							{
								combovalue=objDTOvalue.getAttrValue();
							}
						
						}
						out.print("<OPTION value="+objTempDTO.getAttrValueId());
						if(combovalue.equals(objTempDTO.getAttrMatrixValue()))
						{	
							out.print(" selected='selected'"); 
						}
						out.print(">");
						out.print(objTempDTO.getAttrMatrixValue());
						out.print("</OPTION> \n");
					}
				}
		%>
			</select> <input type="hidden" name="<%="attrType"+elementid %>" value="Combo">
			<input type="hidden" name="<%="attrName"+elementid %>"
				value="<%=objDTO.getAttrName() %>"> <input type="hidden"
				name="<%="attrId"+elementid %>" value="<%=objDTO.getAttrId() %>">
			</td>
		</tr>
		<%		
		elementid++;
		}	
		prevattrid=objDTO.getAttrId();
		}
	}
	
	
	%>
		<!--------------- dynamic display of attribute ended here ------------------->

		<!----------------------------pre defined fields -------------------------->
		<tr>
			<td class="title">New Document</td>
			<td align="left"><s:file name="uploadFile"></s:file></td>

		</tr>
		<tr>
			<td class="title">Select Users</td>
			<td><select name="userCodedtl" multiple="multiple">
				<s:iterator value="users">
					<option value="<s:property value="userCode"/>"><s:property
						value="loginName" /></option>
				</s:iterator>
			</select></td>
		</tr>
		<input type="hidden" name="takeVersion" value="<%=takeVersion%>">
		<% if(takeVersion.equals("true")) { %>
		<!-- Check user already entered user defined version or not -->
		<tr>
			<td class="title">File Version:</td>
			<td><input type="text" size="7" name="versionPrefix"
				onblur="callonBlur(this);" value="A"></td>
		</tr>
		<tr>
			<td class="title"></td>
			<td><SELECT NAME="VersionSeperator" class="comboFontSize"
				title="Seperator">
				<option value="none">none</option>
				<option value="/">/</option>
				<option value="-">-</option>
				<option value=":">:</option>
			</Select> <font class="title">Start With&nbsp;&nbsp;</font><input type="text"
				name="versionSuffix" value="1" onblur="callonBlur(this);"></td>

		</tr>
		<% } %>

		<tr>
			<td><s:submit onclick="return validate();"
				value="Save & Send File Comments" cssClass="button" /></td>
			<td></td>
		</tr>

	</table>

	</div>
</s:form>






</body>
</html>
