<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<%@ page import="java.util.*"%>

<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<s:head theme="ajax" />
<script type="text/javascript">
	var checkDatasetsPath="";
	$(document).ready(function() 
	{
		//debugger;
		$('#datasetsSel').change(function() {
		    // $(this).val() will work here
		
		  //alert( this.value );
		   if($(this).val()=='analysis'){
				  document.getElementById('analysis').style.display = '';
				  document.getElementById('tabulations').value = '-1';
				  document.getElementById('tabulations').style.display = 'none';
		   }
		   else if($(this).val()=='tabulations'){
				  document.getElementById('tabulations').style.display = '';
				  document.getElementById('analysis').value = '-1';
				  document.getElementById('analysis').style.display = 'none';
				  document.getElementById('adamlegacy').value = '-1';
				  document.getElementById('adamlegacy').style.display = 'none';
		   }
		   else{
			   document.getElementById('analysis').value = '-1';
			   document.getElementById('tabulations').value = '-1';
			   document.getElementById('adamlegacy').value = '-1';
			   document.getElementById('analysis').style.display = 'none';
			   document.getElementById('tabulations').style.display = 'none';
			   document.getElementById('adamlegacy').style.display = 'none';
	       }
		    
		  
		});

		$('#analysis').change(function() {
			if($(this).val()=='adam' || $(this).val()=='legacy'){
				 document.getElementById('adamlegacy').style.display = '';
				 document.getElementById('tabulations').value = '-1';
				 document.getElementById('tabulations').style.display = 'none';
			}else{
			document.getElementById('adamlegacy').value = '-1';
			document.getElementById('adamlegacy').style.display = 'none';
			}
			
		});
		
	});
	function validateSTF(){
		
		if(document.forms["STFForm"].studytitle.value=="")
		{
			alert("Please enter Study Title");
			document.forms["STFForm"].studytitle.style.backgroundColor="#FFE6F7";
			document.forms["STFForm"].studytitle.focus();
			return false;
		}
		else if(document.forms["STFForm"].STFXMLNodeName.value=="")
		{
			alert("Please enter STF Node Title");
			document.forms["STFForm"].STFXMLNodeName.style.backgroundColor="#FFE6F7";
			document.forms["STFForm"].STFXMLNodeName.focus();
			return false;
		}
		else if(check(document.forms["STFForm"].uniqueStudyIdentifier.value))
		{
			document.forms["STFForm"].uniqueStudyIdentifier.style.backgroundColor="#FFE6F7";
			document.forms["STFForm"].uniqueStudyIdentifier.focus();
			return false;
		}
		
		
		return true;
		
	}
	function check(str)
	{
		
		if(str == "" || str == null)
		{
			alert("Please enter Study Id...");
			return true;
		}
		
		/*if(str.match(/^[^a-zA-Z]+$/))
		{
			alert("Study Id should be an Alphanumeric value..");
			return true;
		}

		if(str.match(/^[^0-9]+$/))
		{
			alert("Study Id should be an Alphanumeric value..");
			return true;
		}*/
		
		if(str.match(/[^a-z0-9-]+/))
		{
			alert("Invalid Study Id... \n\nOnly Alphabets(small letter), Digits and '-' are allowed.");
			return true; 
		}
		
		return false;
	}
	
	function validateCategory()
	{
		if(document.forms["STFForm"].categoryNameId.value == -1)
		{
			alert('Please Select Category Name...');
			document.forms["STFForm"].categoryNameId.style.backgroundColor="#FFE6F7"; 
			return false;
		}
		else if(document.forms["STFForm"].categoryValue.value == -1)
		{
			alert('Please Selectt Category Value...');
			document.forms["STFForm"].categoryValue.style.backgroundColor="#FFE6F7"; 
			return false;
		}
	}

	
	function validateSTFNode()
	{
		//debugger;
		if(document.forms["STFForm"].STFNodeId.value == "-1")
		{
			alert("Please select STF Node...");
			document.forms["STFForm"].STFNodeId.style.backgroundColor="#FFE6F7";
			return false;
		}
		else
		{
			var STFNodeArr = document.forms["STFForm"].STFNodeId.value.split(",");
			if(STFNodeArr[2] == 'Y')
			{
				var stfEdit='<s:property value="STFOperation" />';
				console.log(stfEdit);
				if(document.forms["STFForm"].multipleTypeNodeName.value=="")
				{
					alert("Please enter STF Node Description..");
					document.forms["STFForm"].multipleTypeNodeName.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].multipleTypeNodeName.focus();
					return false;
				}
				else if(!/^[a-z0-9-]+$/.test(document.forms["STFForm"].multipleTypeNodeName.value))
				{	
					alert("Invalid Node Title... \n\nOnly Alphabets(small letter), Digits and '-' are allowed.");
					document.forms["STFForm"].multipleTypeNodeName.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].multipleTypeNodeName.focus();
					return false;
					
				}
				else if(document.forms["STFForm"].siteId.value=="")
				{
					alert("Please enter Site Identifier for selected STF Node..");
					document.forms["STFForm"].siteId.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].siteId.focus();
					return false;
				}
				else if(document.forms["STFForm"].numberOfRepetitions.value=="")
				{
					alert("Please enter Number of Repetitions for selected STF Node..");
					document.forms["STFForm"].numberOfRepetitions.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].numberOfRepetitions.focus();
					return false;
				}
				else if(!document.forms["STFForm"].numberOfRepetitions.value.match(/^([1-9])([0-9])*$/) && parseInt(document.forms["STFForm"].numberOfRepetitions.value) < 1)
				{
					alert("Number of Repetitions should be a number greater than zero..");
					document.forms["STFForm"].numberOfRepetitions.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].numberOfRepetitions.focus();
					return false;
				}
				else if(document.forms["STFForm"].suffixStart.value=="")
				{
					alert("Please enter Suffix Start for selected STF Node..");
					document.forms["STFForm"].suffixStart.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].suffixStart.focus();
					return false;
				}
				else if(!document.forms["STFForm"].suffixStart.value.match(/^([1-9])([0-9])*$/) && parseInt(document.forms["STFForm"].suffixStart.value) < 1)
				{
					alert("Suffix Start should be a number greater than zero..");
					document.forms["STFForm"].suffixStart.style.backgroundColor="#FFE6F7";
					document.forms["STFForm"].suffixStart.focus();
					return false;
				}else if(checkDatasetsPath=="" && (STFNodeArr[3] == "datasets" || STFNodeArr[3] == "data-tabulation-dataset" || STFNodeArr[3] == "analysis-dataset" || STFNodeArr[3] == "data-listing-dataset" ||
						STFNodeArr[3] == "data-tabulation-dataset-sdtm"|| STFNodeArr[3] == "data-tabulation-dataset-send" || STFNodeArr[3] == "analysis-dataset-adam"
							|| STFNodeArr[3] == "analysis-data-definition" || STFNodeArr[3] == "data-tabulation-data-definition" || STFNodeArr[3] == "data-listing-data-definition")){
					if(document.forms["STFForm"].datasetsSel.value == -1){
						alert("Please select publish path..");
						document.forms["STFForm"].datasetsSel.style.backgroundColor="#FFE6F7";
						return false;

					}
				}
				
				
			}
		}
	}
	
	function validateRemoveSTF()
	{
		return confirm("You are about to remove the STF.\nIt will remove the STF with all its details including STF Nodes.\n\nDo you really want to remove the STF?");
	}
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return false;
  		}
  	} 
  	document.onkeypress = detectReturnKey;
		
	function copyTitle()
	{
		document.forms['STFForm'].STFXMLNodeName.value = document.forms['STFForm'].studytitle.value;
	}

	function setNodeType(stfNodeId)
	{
		//debugger;
		console.log(stfNodeId);
		var STFNodeArr = document.forms['STFForm'].STFNodeId.value.split(',');
		if(STFNodeArr[1] != null)
		{
			document.forms['STFForm'].nodeType.value = STFNodeArr[1];
			if(STFNodeArr[3] == "datasets" || STFNodeArr[3] == "data-tabulation-dataset" || STFNodeArr[3] == "analysis-dataset" || STFNodeArr[3] == "data-listing-dataset" ||
				STFNodeArr[3] == "data-tabulation-dataset-sdtm"|| STFNodeArr[3] == "data-tabulation-dataset-send" || STFNodeArr[3] == "analysis-dataset-adam"
				|| STFNodeArr[3] == "analysis-data-definition" || STFNodeArr[3] == "data-tabulation-data-definition" || STFNodeArr[3] == "data-listing-data-definition"){
				$.ajax(
						{			
							url: 'GetDatasetsPublishPath_ex.do?stfNodeTag='+STFNodeArr[3]+'&stfNodeId='+stfNodeId,
							beforeSend: function() {
								document.getElementById('STFPath').innerHTML ="";
						    },
					  		success: function(data) 
					  		{
								if(data!=null || data!=""){

									 console.log(data);
									 if(data.length > 0)
								    	{
									 var datapart = data.split(':');
									 
									
								  	 document.getElementById('STFPath').innerHTML ="Selected publish path is : "+datapart[1].substring(datapart[1].indexOf("/")+1);
									 checkDatasetsPath=data;
								    	}
								} 
						  	}	  		
						});	
						
				document.getElementById('datasetsSelection').style.display = '';
			}else{
				document.getElementById('datasetsSelection').style.display = 'none';
			}
			
			if(STFNodeArr[2] == 'Y')
			{
				document.getElementById('multipleTypeNodeDiv').style.display = '';
				if(STFNodeArr[3] == "case-report-forms"){
					document.forms["STFForm"].multipleTypeNodeName.value="case-report-form";
				}
				else if(STFNodeArr[3] == "datasets"){
					document.forms["STFForm"].multipleTypeNodeName.value="datasets";
				}
				else if(STFNodeArr[3] == "data-tabulation-dataset"){
					document.forms["STFForm"].multipleTypeNodeName.value="data-tabulation-dataset";
				}
				else if(STFNodeArr[3] == "data-listing-dataset"){
					document.forms["STFForm"].multipleTypeNodeName.value="data-listing-dataset";
				}
				else if(STFNodeArr[3] == "analysis-dataset"){
					document.forms["STFForm"].multipleTypeNodeName.value="analysis-dataset";
				}
				else if(STFNodeArr[3] == "data-tabulation-dataset-sdtm"){
					document.forms["STFForm"].multipleTypeNodeName.value="data-tabulation-dataset-sdtm";
				}
				else if(STFNodeArr[3] == "data-tabulation-dataset-send"){
					document.forms["STFForm"].multipleTypeNodeName.value="data-tabulation-dataset-send";
				}
				else if(STFNodeArr[3] == "analysis-dataset-adam"){
					document.forms["STFForm"].multipleTypeNodeName.value="analysis-dataset-adam";
				}
				else if(STFNodeArr[3] == "analysis-data-definition"){
					document.forms["STFForm"].multipleTypeNodeName.value="analysis-data-definition";
				}
				else if(STFNodeArr[3] == "data-tabulation-data-definition"){
					document.forms["STFForm"].multipleTypeNodeName.value="data-tabulation-data-definition";
				}
				
				else if(STFNodeArr[3] == "data-listing-data-definition"){
					document.forms["STFForm"].multipleTypeNodeName.value="data-listing-data-definition";
				}
				else if(STFNodeArr[3] == "image"){
					document.forms["STFForm"].multipleTypeNodeName.value="image";
				}
				else if(STFNodeArr[3] == "subject-profiles"){
					document.forms["STFForm"].multipleTypeNodeName.value="subject-profiles";
				}
				else {
					document.forms["STFForm"].multipleTypeNodeName.value="";
				}
				document.forms["STFForm"].siteId.value="";
			}
			else
			{
				document.getElementById('multipleTypeNodeDiv').style.display = 'none';
			}
		}
		else
		{
			document.forms['STFForm'].nodeType.value = '-';
		}
	}
	
	function editMultipleTypeSTFNode(editNodeId,editNodeDisplayName,editFolderName,siteId)
	{
		strAction = "EditMultipleTypeSTFNode.do?editNodeId=" + editNodeId +"&multipleTypeSTFNodeName="+ editFolderName +"&multipleTypeSTFNodeDisplayName="+editNodeDisplayName+"&siteId="+ siteId;
   		win=window.open(strAction,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=no,height=250,width=500,resizable=yes,titlebar=no");
   		win.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(250/2));
   		return true;
   	}
	function editNode(editNodeId)
	{
		strAction = "EditProjectNodes.do?isSTF=true&nodeId=" + editNodeId;
   		win=window.open(strAction,"ThisWindow1","toolbar=no,directories=no,menubar=no,scrollbars=no,height=400,width=800,resizable=yes,titlebar=no");
   		win.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(400/2));
   		return true;
	//	EditProjectNodes.do?nodeId=364
	}
	
	
   	
   	

</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<s:if test="STFOperation == 'None'">
	<div class="title" align="center"><br>
	<br>
	 <br>
		Cannot attach STF on this node.
	</div>
</s:if>
<s:else>
	<s:form action="AttachSTFNode" name="STFForm" method="post">
		<br>
		<div align="center" class="headercls"><b> Study - Document</b></div>
		<div align="left" class="bdycls" style="height: 10%;">
		<table align="left" width="100%">
			<tr>
				<td class="title" width="18%">Study Title :</td>
				<td align="left" width="30%"><s:textfield name="studytitle"
					cssStyle="padding-left: 3px;" onkeypress="copyTitle();"
					onkeyup="copyTitle();" onblur="copyTitle();" /></td>
				<td class="title" width="12%">Version :</td>
				<td class="title" align="left"
					style="font-weight: lighter; font: x-small; color: black">STF
				Version 2.2</td>
			</tr>
			<tr>
				<td class="title" width="18%">STF Node Title :</td>
				<td align="left" width="30%"><s:textfield name="STFXMLNodeName"
					readonly="true" cssStyle="padding-left: 3px;" /></td>
				<td class="title" width="12%">Study Id :</td>
				<td align="left"><s:textfield name="uniqueStudyIdentifier"
					cssStyle="padding-left: 3px;" /></td>

				<s:if test="STFOperation == 'Edit'">
					<td align="right" style="padding-right: 10px;"><s:submit
						name="stfSubmit" value="Edit" onclick="return validateSTF();"
						cssClass="button" align="right"></s:submit></td>
				</s:if>
			</tr>
		</table>
		</div>


		<s:if test="STFOperation == 'Create'">
			<br>
			<center><s:submit name="stfSubmit"
				onclick="return validateSTF();" value="Create STF" cssClass="button"></s:submit></center>
		</s:if>
		<!-- Add below span to work stf in chrome -->
		<span> &nbsp;</span>
		<s:if test="STFOperation == 'Edit'">
			<div style="height: 500px; overflow: auto;">
			<div align="center"
				style="height: auto; border: 1 solid; overflow-x: auto; overflow-y: auto">
			<div align="center" class="headercls"><b> STF Categories</b></div>
			<div align="left" class="bdycls" style="height: 5%;">
			<table width="100%">
				<tr>
					<td class="title" width="30px">Name :</td>
					<td width="170px"><s:select list="getSTFCategoryDtl"
						name="categoryNameId" id="categoryNameId" headerKey="-1"
						headerValue="Select Category Name" listKey="id"
						listValue="categoryName">
					</s:select> <ajax:event ajaxRef="STFCategory/getSTFCategoryValues" /></td>
					<td class="title" width="30px">Value :</td>
					<td width="200px">
					<div id="STFCategoryValueDiv"><select name="categoryValue">
						<option value="-1">Select Category Value</option>
					</select></div>
					</td>
					<td align="right" width="35px"><s:submit name="stfSubmit"
						value="Add" onclick="return validateCategory();" cssClass="button"></s:submit>
					</td>
				</tr>
			</table>
			<div id="categoryDiv"><s:if test="STFCategories.size != 0">
				<table class="datatable paddingtable" width="95%" align="center">
					<tr>
						<th>#</th>
						<th>Category Name</th>
						<th>Category Value</th>
						<th>Attribute Name</th>
						<th>Attribute Value</th>
						<th>Remove</th>
					</tr>
					<% int srNo =1;%>
					<s:set name="STFParentNodeId" value="%{nodeId}"></s:set>
					<s:iterator value="STFCategories" status="status">
						<s:if test="#status.odd">
							<tr
								class="<s:if test="#status.count % 4 == 3">even</s:if><s:else>odd</s:else>">
								<td><%= srNo++  %></td>
								<td><s:property value="attrValue" /></td>
								<td><s:property value="nodeContent" /></td>
						</s:if>
						<s:else>
							<td><s:property value="attrName" /></td>
							<td><s:property value="attrValue" /></td>
							<td><a
								href="AttachSTFNode.do?nodeId=${STFParentNodeId}&removeCategoryNodeId=${STFXMLNodeId}&removetagId=<s:property value="tagId"/>">Remove</a></td>
							</tr>
						</s:else>
					</s:iterator>
				</table>
			</s:if></div>
			</div>
			</div>
			<br>
			<div align="center" class="headercls"><b> STF Nodes </b></div>
			<br>
			<div>
			<table width="95%">
				<tr>
					<td class="title">STF Node :</td>
					<td><s:select list="getSTFNodes" name="STFNodeId"
						headerKey="-1" headerValue="Select STF Node Name"
						listKey="id+','+nodeCategory+','+multiple+','+nodeName"
						listValue="nodeName" onchange="setNodeType('%{nodeId}');">
					</s:select></td>
					<td class="title">Type:</td>
					<td><s:textfield name="nodeType" size="3"
						cssStyle="padding-left: 3px;" value="-" readonly="true"></s:textfield>
					</td>
				</tr>
			</table>
			<s:div id="multipleTypeNodeDiv" cssStyle="display: none;">
				<table width="95%">
					<tr>
						<td class="title">Node Title/Description:</td>
						<td colspan="3"><s:textfield name="multipleTypeNodeName"
							cssStyle="padding-left: 3px;" size="60"></s:textfield></td>

					</tr>
				</table>
				<table width="95%">
					<tr>
						<td class="title" width="37%">Site Identifier: <s:textfield
							name="siteId" size="10" cssStyle="padding-left: 3px;"></s:textfield>
						</td>
						<td class="title" width="37%">Number of Repetitions: <s:textfield
							id="numberOfRepetitions" name="numberOfRepetitions" size="2"
							maxlength="2" cssStyle="padding-left: 3px;" value="1"></s:textfield>
						</td>
						<td class="title">Suffix Start: <s:textfield
							name="suffixStart" size="2" maxlength="2"
							cssStyle="padding-left: 3px;" value="1"></s:textfield></td>

					</tr>
					
				</table>
			</s:div>
			<div id="datasetsSelection" style="display: none;">
				
			<span class="title">Select publish path:</span>
				<select name="datasetsSel" id="datasetsSel">
							<option value="-1">Select Value</option>
							<option value="analysis">analysis</option>
							<option value="misc">misc</option>
							<option value="profiles">profiles</option>
							<option value="tabulations">tabulations</option>
							
				</select>
				<select name="analysis" id="analysis" style="display:none;">
							<option value="-1">Select Value</option>
							<option value="adam">adam</option>
							<option value="legacy">legacy</option>
				</select>
				<select name="tabulations" id="tabulations" style="display:none;">
							<option value="-1">Select Value</option>
							<option value="legacy">legacy</option>
							<option value="sdtm">sdtm</option>
							<option value="send">send</option>
				</select>
				<select name="adamlegacy" id="adamlegacy" style="display:none;">
							<option value="-1">Select Value</option>
							<option value="datasets">datasets</option>
							<option value="programs">programs</option>
							
				</select>
				<br>
				<span class="title" id="STFPath"></span>
			</div>
			 <br>
			<center><s:submit name="stfSubmit" value="Add STF Node"
				onclick="return validateSTFNode();" cssClass="button"></s:submit></center>
			<br>

			<s:if test="STFChildNodes.size > 0">

				<table class="datatable paddingtable" width="95%">
					<tr>
						<th>#</th>
						<th>Node Name</th>
						<th>Type</th>
						<th>Site Identifier</th>
						<th>Edit</th>
						<th>Remove</th>
					</tr>
					<s:set name="STFParentNodeId" value="%{nodeId}"></s:set>
					<s:set name="classValue" value="1"></s:set>
					<s:iterator value="STFChildNodes" status="status">
						<s:set name="outterNodeName" value="%{nodeName}"></s:set>
						<s:set name="outterStatusCount" value="#status.count"></s:set>
						<tr
							class="<s:if test="#classValue == 0">even</s:if><s:else>odd</s:else>">
							<td>${status.count }</td>
							<td><s:property value="nodeDisplayName" /></td>
							<td><s:property value="stfNodeCategoryName" /></td>
							<td>-</td>
							<td><a href="#"
										onclick="return editNode('<s:property value="nodeId"/>')">Edit </a></td>
							<td><a
								href="AttachSTFNode.do?nodeId=${STFParentNodeId}&removeNodeId=<s:property value="nodeId"/>">Remove</a></td>
						</tr>
						<s:if test="%{childNodes}">
							<!--if node has children then display them -->
							<s:iterator value="%{childNodes}" status="status">
								<s:if test="#classValue == 1">
									<s:set name="classValue" value="0"></s:set>
								</s:if>
								<s:else>
									<s:set name="classValue" value="1"></s:set>
								</s:else>
								<tr
									class="<s:if test="#classValue == 0">even</s:if><s:else>odd</s:else>">
									<td><s:property value="#outterStatusCount" />.${status.count
									}</td>
									<td><s:property value="nodeDisplayName" /></td>
									<td>-</td>
									<td><s:property value="stfNodeSiteIdentifier" /></td>
									<td>
									
									<a href="#"
										onclick="return editMultipleTypeSTFNode('<s:property value="nodeId"/>','<s:property value="nodeDisplayName"/>','<s:property value="folderName"/>','<s:property value="stfNodeSiteIdentifier"/>')">Edit</a></td>
									<td><a
										href="AttachSTFNode.do?nodeId=${STFParentNodeId}&removeNodeId=<s:property value="nodeId"/>">Remove</a></td>
								</tr>

							</s:iterator>
						</s:if>
						<s:if test="#classValue == 1">
							<s:set name="classValue" value="0"></s:set>
						</s:if>
						<s:else>
							<s:set name="classValue" value="1"></s:set>
						</s:else>
					</s:iterator>
				</table>

			</s:if></div>



			<br>
			</div>
			<table width="100%" style="padding-top: 10px;">
				<tr>
					<td width="50%">
					<center><s:submit name="stfSubmit" onclick="" value="Done"
						cssClass="button"></s:submit></center>
					</td>
					<td>
					<center><s:submit name="stfSubmit"
						onclick="return validateRemoveSTF();" value="Remove STF"
						cssClass="button"></s:submit></center>
					</td>
				</tr>
			</table>



		</s:if>

		<s:hidden name="attachedMultipleTypeSTFChildNodeIds"></s:hidden>
		<s:hidden name="STFXMLNodeId"></s:hidden>
		<s:hidden name="nodeId"></s:hidden>

		<ajax:enable />
	</s:form>

</s:else>
</body>
</html>
