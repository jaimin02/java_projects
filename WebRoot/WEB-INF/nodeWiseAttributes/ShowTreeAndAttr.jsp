<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>

<html>
<head>

<script language="javascript">
		
	function openFile(BaseWorkFolder,NodeId,TranNo,FileName)
    {
    	var newWindow = "openfile.do?workSpaceId="+document.getElementById('sourceWsId').value+"&nodeId="+NodeId.toString()+"&tranNo="+TranNo+"&fileName="+FileName+"&baseWorkFolder="+BaseWorkFolder;
    	win3=window.open(newWindow);	
    	win3.moveTo(0,0);
   		win3.resizeTo(screen.availWidth, screen.availHeight);
    	return true;
    }
    function changeDirColor(val,nodeId)
	{
    	
		if(document.getElementById('selectionType').value != "Onlychilds")
		{
			if(document.getElementById(val).style.backgroundColor == "#ffffa6")
			{
				document.getElementById(val).style.backgroundColor = "";
				var selectedNodes = document.nodeWiseAttributeForm.selectedNodes.value;
				var newSelectedNodes = "";
				var selectedNodes_array = selectedNodes.split("#");
				for(i=0;i<selectedNodes_array.length;i++)
				{
					if(selectedNodes_array[i]!="" && selectedNodes_array[i]!=nodeId)
					newSelectedNodes+=selectedNodes_array[i]+"#";
				}
				document.nodeWiseAttributeForm.selectedNodes.value = newSelectedNodes;
			}	
			else
			{
				document.getElementById(val).style.backgroundColor = "#ffffa6";
				document.nodeWiseAttributeForm.selectedNodes.value += nodeId + "#";
			}
		}
		return true;
	}
	
	function changeFileColor(val,filename,nodeId)
	{
		if(document.getElementById('selectionType').value != "OnlyParent")
		{
			if(document.getElementById(val).style.backgroundColor == "#ffffd9")
			{
				document.getElementById(val).style.backgroundColor = "";
				var selectedNodes = document.nodeWiseAttributeForm.selectedNodes.value;
				var newSelectedNodes = "";
				var selectedNodes_array = selectedNodes.split("#");
				for(i=0;i<selectedNodes_array.length;i++)
				{
					if(selectedNodes_array[i]!="" && selectedNodes_array[i]!=nodeId)
						newSelectedNodes+=selectedNodes_array[i]+"#";
				}
				document.nodeWiseAttributeForm.selectedNodes.value = newSelectedNodes;
			}	
			else
			{
				document.getElementById(val).style.backgroundColor = "#ffffd9";
				document.nodeWiseAttributeForm.selectedNodes.value += nodeId + "#";
			}
		}
		return true;
	}

	function clearselection()
	{
		var selectedNodes_array=document.getElementById('selectedNodes').value.split("#");
		var newNodes = "";
		for(var icount=0;icount<selectedNodes_array.length;icount++)
		{
			if(selectedNodes_array[icount] != "")
			{	
				if(document.getElementById('tableId_'+ selectedNodes_array[icount]).style.backgroundColor == "#ffffa6" &&
					document.getElementById('selectionType').value == "Onlychilds")
				{
					document.getElementById('tableId_'+ selectedNodes_array[icount]).style.backgroundColor="";
				}
				else if(document.getElementById('tableId_'+ selectedNodes_array[icount]).style.backgroundColor == "#ffffd9" &&
					document.getElementById('selectionType').value == "OnlyParent")
				{
					document.getElementById('tableId_'+ selectedNodes_array[icount]).style.backgroundColor="";
				}
				else
				{
					newNodes += selectedNodes_array[icount]+"#";
				}
			}
		}
		document.getElementById('selectedNodes').value = newNodes;
		return true;
	}

	function showHide(divId)
	{		
		if(document.getElementById(divId).style.display=="none")
			document.getElementById(divId).style.display='block';
		else document.getElementById(divId).style.display='none';
			return true;	
	}
	function validate()
	{
		if(document.getElementById('selectedNodes').value=="")
		{
			alert("please select nodes");
			return false;
		}
		
		if(document.nodeWiseAttributeForm.attrOrAttrGroup.value=="-1")
		{
			alert("Please select Attributes or Attribute Group");
			document.nodeWiseAttributeForm.attrOrAttrGroup.style.backgroundColor="#FFE6F7";
			document.nodeWiseAttributeForm.attrOrAttrGroup.focus();
			return false;
		}
		if(document.nodeWiseAttributeForm.attrOrAttrGroup.value=="attrs")
		{
			
			for (counter = 0; counter < nodeWiseAttributeForm.multiCheckAttr.length; counter++)
			{
				if (nodeWiseAttributeForm.multiCheckAttr[counter].checked)
				return true;
			}
			alert("please select Attribute(s)");
			return false;
		}
		
		if(document.nodeWiseAttributeForm.attrOrAttrGroup.value=="attrgroup")
		{
			
			var chkAttrGroup = document.getElementsByName('multiCheckAttrGroup');
			for (counter = 0; counter < chkAttrGroup.length; counter++)
			{
				if (chkAttrGroup[counter].checked)
				return true;
			}
			alert("please select AttributeGroup(s)");
			return false;
		}
		
		return true;
	}
	</script>

<s:head />
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br>


<div align="center">
<div class="headercls">Attach Attributes On Node</div>
<br>
<s:form action="SaveAttributesOnNode" method="post"
	name="nodeWiseAttributeForm">


	<table width="95%" border="0" cellpadding="0px;" cellspacing="0px;">

		<tr>
			<td colspan="2">
			<table class="channelcontent" width="100%" border="0">
				<tr valign="top">
					<td class="title" align="right" width="25%"
						style="padding: 2px; padding-right: 8px;">project Name</td>
					<td align="left" valign="top">
					${workSpaceDesc}&nbsp;&nbsp;&nbsp;&nbsp; <img
						src="${pageContext.request.contextPath}/images/Darrow.gif"
						onclick="return showHide('projectInfo');" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<div id="projectInfo" style="">

			<table class="channelcontent" width="100%" border="0">
				<tr>
					<td class="title" align="right" width="25%"
						style="padding: 2px; padding-right: 8px;">Client</td>
					<td align="left">${Client}</td>
				</tr>
				<tr>
					<td class="title" align="right" width="25%"
						style="padding: 2px; padding-right: 8px;">Location</td>
					<td align="left">${Location}</td>
				</tr>
				<tr>
					<td class="title" align="right" width="25%"
						style="padding: 2px; padding-right: 8px;">Project Type</td>
					<td align="left">${ProjectType}</td>
				</tr>
			</table>
			</div>
			</td>
		</tr>

		<tr>
			<td width="50%" align="left" class="title">
			&nbsp;&nbsp;&nbsp;&nbsp;Selection &nbsp;&nbsp;&nbsp;&nbsp; <select
				id="selectionType" onchange="return clearselection();">
				<option value="Any">Any</option>
				<option value="OnlyParent">Only Parent</option>
				<option value="Onlychilds">Only child</option>
			</select></td>
			<td width="50%" align="right" class="title">
			Attributes/Group&nbsp;&nbsp;&nbsp;&nbsp; <select
				name="attrOrAttrGroup">
				<option value="-1">Select Attribute/Group</option>
				<option value="attrs">Attributes</option>
				<option value="attrgroup">Attribute Group</option>
			</select><ajax:event ajaxRef="showAttrsOrGroup/getAttrsOrGroup" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:submit onclick="return validate();" value="Attach"
				cssClass="button"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>

	</table>
	<table width="100%" border="0">

		<tr>
			<td width="50%" align="left" valign="top">
			<div
				style="width: 100%; height: 300px; overflow-y: scroll; scrollbar-arrow-color: blue; scrollbar-face-color: #e7e7e7; scrollbar-3dlight-color: #a0a0a0; scrollbar-darkshadow-color: #888888; border: solid 1px #669">
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2"><s:iterator value="getDtl" status="stat">


						<table>
							<tr>

								<s:iterator value="spaceArray[#stat.index]">
									<!-- for spacing -->
									<td>&nbsp;&nbsp;&nbsp;</td>
								</s:iterator>


								<s:if test="top[8]==symbols[1]">
									<td>
									<table id="tableId_<s:property value="top[0]"/>">
										<tr>
											<td valign="top"><img
												src="${pageContext.request.contextPath}/images/open2.gif"
												onclick="return changeDirColor('tableId_<s:property value="top[0]"/>','<s:property value="top[0]"/>')">
											</td>
											<td valign="middle"><b><font face="verdana" size="2"
												color="#C00000"> <s:property value="top[1]" />
											&nbsp;&nbsp; </font></b></td>
										</tr>
									</table>
									</td>
								</s:if>
								<s:else>
									<td>

									<table id="tableId_<s:property value="top[0]"/>">
										<tr>
											<td valign="top"><img
												src="${pageContext.request.contextPath}/images/file.png"
												alt="<s:if test="top[3]==symbols[0]"><s:property value="symbols[2]"/></s:if><s:else><s:property value="top[3]"/></s:else>"
												onclick="return changeFileColor('tableId_<s:property value="top[0]"/>','<s:property value="top[3]"/>','<s:property value="top[0]"/>')">
											</td>
											<td><b><font face="verdana" size="2"> <s:if
												test="top[3] == symbols[0]">
												<font size="2"><s:property value="top[1]" /></font>
												<font size="2" color="red"><b>(File not Found)</b></font>
											</s:if> <s:else>
												<font size="2" color="blue"><s:property
													value="top[1]" /></font>&nbsp;&nbsp;
															</s:else> </font></b></td>
										</tr>
									</table>
									</td>
								</s:else>
							</tr>
						</table>
					</s:iterator></td>
				</tr>
			</table>

			<s:if test="totalEmptyNodes > 0">
				<table border="0" width="100%" cellspacing="1" cellpadding="2"
					height="30">
					<tr>
						<td width="100%" bgcolor="#c00000" align="center"><b> <font
							size="2" face="Verdana" color="white"> Total Empty Nodes
						:-- <s:property value="totalEmptyNodes" />/<s:property
							value="total" /> </font> </b></td>
					</tr>
				</table>
			</s:if></div>
			</td>

			<td width="50%" align="center" valign="top">
			<div
				style="width: 100%; height: 300px; overflow-y: scroll; scrollbar-arrow-color: blue; scrollbar-face-color: #e7e7e7; scrollbar-3dlight-color: #a0a0a0; scrollbar-darkshadow-color: #888888; border: solid 1px #669">
			<table border="0" width="100%">
				<tr>
					<td colspan="2">
					<div id="showattrorgroup"></div>
					</td>
				</tr>
			</table>
			</div>
			</td>
		</tr>
	</table>


	<input type="hidden" id="selectedNodes" name="selectedNodes" value="">
	<input type="hidden" id="sourceWsId" name="sourceWsId" value="${ws_id}">



	<ajax:enable />
</s:form></div>

<div id="MessageOfAdd"></div>
</body>
</html>

