<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />
<script language="javascript">
	
function editNodeDetail(nodeId)
{
	if (nodeId== null)
	{
		alert("Please Select Corresponding Node");
		return false;
	}
	
	var str="EditProjectLeafNodes.do?nodeId="+nodeId;
	win3=window.open(str,"ThisWindow","scrollbars=1,height=500,width=800");	
	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));
	return true;
}
	
	function addSTF(nodeId){
		
		if (nodeId == null)
		{
			alert("Please Select Node");
			return false;
		}else{
			str="AttachSTF.do?nodeId="+nodeId;
			win3=window.open(str,'ThisWindow','height=660,width=650,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
			win3.moveTo(screen.availWidth/2-(650/2),screen.availHeight/2-(660/2));
			return true;
		}
	}

	//debugger;
	function editNode(nodeId)
		{
		//debugger;
			str="EditProjectNodes.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");	
			win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));
			return true;
		}
		
	function addLeafNodeParent(nodeId)
	{
		
		var str = "AddChildNodes.do?nodeId="+nodeId;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=350,width=800,resizable=no,titlebar=no");				
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(300/2));
		return true;
	}
		
	function assignRightsParent(nodeId)
		{
			
			var str="AssignWorkspaceNodeRights.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=420,resizable=no,titlebar=no");	
			win3.moveTo(screen.availWidth/2-(420/2),screen.availHeight/2-(500/2));
			return true;			
		}
	function addLeafNode(nodeId)
	{
		if (nodeId == null)
			{
				alert("Please Select Corresponding Node");
				return false;
		}
		var str = "AddLeafNodes.do?nodeId="+nodeId;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=350,width=800,resizable=no,titlebar=no");			
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(300/2));
		return true;
	}
	
	function versioning(nodeId)
	{
		if (nodeId == null)
		{
			alert("Please Select Node");
			return false;
		}else{
			str="WorkspaceNodeVersion.do?nodeId="+nodeId;			
			win3=window.open(str,'ThisWindow','height=320,width=520,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,titlebar=no');	
			win3.moveTo(screen.availWidth/2-(520/2),screen.availHeight/2-(320/2));
			return true;
		}	
	}
	
	function showDocumentProperty(nodeId)
	{
		if (nodeId == null)
		{
			alert("Please Select Node");
			return false;
		}else{
			str="ShowDocumentProperties.do?nodeId="+nodeId;
			win3=window.open(str,'ThisWindow','height=600,width=750,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,titlebar=no');	
		 	win3.moveTo(screen.availWidth/2-(750/2),screen.availHeight/2-(600/2));
			return true;
		}	
	}
	
	function assignRights(nodeId)
	{
		if (nodeId == null)
		{
			alert("Please Select Node");
			return false;
		}else{	
			
			str="AssignWorkspaceNodeRights.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=420,resizable=no,titlebar=no");
		 	win3.moveTo(screen.availWidth/2-(420/2),screen.availHeight/2-(500/2));	
			return true;
		}
	}
</script>
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

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<div class="headercls">WorkspaceNodeRights</div>
<div align="center" class="bdycls"><br>

<% int LeafNode = (Integer)request.getAttribute("leafNode");%> <%if(LeafNode==0){%>
<table align="center" width="95%">
	<tr>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td width="33%"><a href="#"
			onclick="return assignRightsParent(${nodeId});"><b><font
			size="2" color="#c00000">Assign Rights</font></b></a></td>
		<td width="33%"><a href="#" onclick="return editNode(${nodeId});"><b><font
			size="2" color="#c00000">Edit Node Detail</font></b></a></td>
		<td><a href="#" onclick="return addLeafNodeParent(${nodeId})"><b><font
			size="2" color="#c00000">Add Leaf Node</font></b></a></td>
	</tr>
	<tr>
		<td><a href="#" onclick="return addSTF(${nodeId});"><b><font
			size="2" color="#c00000">Add STF</font></b></a></td>
	</tr>

</table>

<%} else{%> <s:form name="workspacenoderights" method="post">
	<table bgcolor="white" border="1" cellpadding="2" width="100%"
		bordercolor="#EBEBEB">
		<tr>
			<td width="50%" valign="top" bgcolor="#EBEBEB"><font size="2"
				face="Verdana"> <a href="#"
				onclick="return versioning(${nodeId});">Version Maintenance</a></font></td>

			<td width="50%" valign="top" bgcolor="#EBEBEB"><font size="2"
				face="Verdana"> <a href="#"
				onclick="return showDocumentProperty(${nodeId});">Document
			Properties</a></font></td>

		</tr>
		<tr>
			<td width="50%" valign="top" bgcolor="#EBEBEB"><font size="2"
				face="Verdana"> <a href="#"
				onclick="return addSTF(${nodeId});">Add STF</a></font></td>
			<td width="50%" valign="top" bgcolor="#EBEBEB"><font size="2"
				face="Verdana"> <a href="#"
				onclick="return editNodeDetail(${nodeId});">Edit Node</a></font></td>
		</tr>

		<tr>
			<td width="50%" valign="top" bgcolor="#EBEBEB"><font size="2"
				face="Verdana"> <a href="#"
				onclick="return addLeafNode(${nodeId});">Add Leaf Node</a></font></td>
			<td width="50%" valign="top" bgcolor="#EBEBEB"><font size="2"
				face="Verdana"> <a href="#"
				onclick="return assignRights(${nodeId});">Assign Rights</a></font></td>
		</tr>

	</table>
</s:form> <%} %>
</div>
</body>
</html>

