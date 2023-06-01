<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>
<html>
<head>
<s:head theme="ajax" />

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet"
	type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript"
	src="js/jquery/DataTable/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$('.dtpicker').datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		var todayDate = new Date(); 
		var month = todayDate.getMonth()+1;

		if(month<10){
			month= "0"+month;
		}
		$('.dtpicker').each(function(){
				this.value = todayDate.getFullYear()+"/"+month+"/"+todayDate.getDate();
		});
	});
	
</script>

<script type="text/javascript">
	function checkAll()
	{
		var chkBox = document.forms['FullSearch'].selectedAttributes;
		
		if(document.forms['FullSearch'].chkBtn.value == 'Check All')
		{
			for(i = 0; i < chkBox.length; i++)
			{
				chkBox[i].checked = 'checked';
			}
			
			document.forms['FullSearch'].chkBtn.value = 'Uncheck All';
		}
		else
		{
			for(i = 0; i < chkBox.length; i++)
			{
				chkBox[i].checked = '';
			}
			
			document.forms['FullSearch'].chkBtn.value = 'Check All';
		}		
	}
	function check(){
		if(document.forms['FullSearch'].output.value == 'C'){
				document.getElementById('hidematch').style.display='none';
				document.forms['FullSearch'].searchBy.checked = '';
		}
		if(document.forms['FullSearch'].output.value == 'F')
		{
			document.forms['FullSearch'].searchBy.checked = '';
			document.forms['FullSearch'].searchBy.disabled = 'disabled';
			document.getElementById('wsFilterRow').style.display = '';
			document.getElementById('ndFilterRow').style.display = '';
			document.getElementById('stFilterRow').style.display = '';
		}
		else
		{
			document.getElementById('wsFilterRow').style.display = 'none';
			document.getElementById('ndFilterRow').style.display = 'none';
			document.getElementById('stFilterRow').style.display = 'none';
			document.getElementById('hidematch').style.display='';
			document.forms['FullSearch'].searchBy.disabled = '';
		}
	}
	function validate()
	{
		if(document.forms['FullSearch'].keyword.value.length < 2){
			alert('Phrase should be more than two character');
			document.getElementById('subbtn').disabled = 'disabled';
		}
			else
			{		document.getElementById('subbtn').disabled = '';}
		
	}
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
 	  		return document.FullSearch.subbtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><s:if test="searchFor == 'D'">
	<img src="images/Header_Images/Search/timeline_search.jpg"
		style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
</s:if> <s:elseif test="searchFor == 'K'">
	<img src="images/Header_Images/Search/Content_Search.jpg"
		style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
</s:elseif>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>

<div align="center"><s:form name="FullSearch"
	action="SearchforDateAndKeyword_b">
	<input type="hidden" name="searchFor"
		value="<s:property value="searchFor"/>" />
	<s:if test="searchFor == 'D'">
		<div id="datewiseDiv" align="center">
		<table align="center" width="90%" cellspacing="0" id="dateCriteria">
			<tr>
				<td class="title" align="right" style="padding: 6px;"><input
					type="radio" name="selectdate" value="Y" checked="checked"
					id="selectdate"></td>
				<td align="left" style="padding: 6px;"><label class="title"
					for="selectdate" style="padding-left: 5px;">Search</label>&nbsp;&nbsp;
				<select name="search" style="min-width: 0px;">
					<option value="O">On</option>
					<option value="B">Before</option>
					<option value="A">After</option>
				</select>&nbsp;&nbsp; <input type="text" name="firstDate" readonly="readonly"
					class="dtpicker" size="10"> (YYYY/MM/DD)</td>
			</tr>
			<tr>
				<td class="title" align="right" style="padding: 6px;"><input
					type="radio" name="selectdate" value="N" id="fromtodate"></td>
				<td align="left" style="padding: 6px;"><label class="title"
					for="fromtodate" style="padding-left: 5px;">From</label>&nbsp;&nbsp;
				<input type="text" name="secondDate" readonly="readonly"
					class="dtpicker" size="10">&nbsp;&nbsp; <label
					class="title" for="fromtodate" style="padding-left: 3px;">To</label>&nbsp;&nbsp;
				<input type="text" name="thirdDate" readonly="readonly"
					class="dtpicker" size="10"> (YYYY/MM/DD)</td>
			</tr>
			<tr>
				<td class="title" align="right">Select Attributes &nbsp;</td>
				<td valign="middle" align="left" style="padding: 2px;">
				<div align="right" style="padding-bottom: 2px; width: 630px;">
				<input type="button" name="chkBtn" value="Check All" class="button"
					onclick="checkAll();"></div>
				<div align="left"
					style="height: 85px; width: 630px; overflow-y: auto; border: solid 1px; padding: 2px; text-align: left;">
				<table border="0" style="color: black;" width="100%">
					<s:iterator value="attributeList" status="status">
						<s:if test="#status.count % 3 == 1">
							<tr align="left" valign="middle">
						</s:if>
						<td style="padding-left: 6px;" valign="middle"><input
							type="checkbox" name="selectedAttributes"
							value="<s:property value="attrId"/>"> <s:property
							value="attrName" /></td>
						<s:if test="#status.count % 3 == 0">
							</tr>
						</s:if>
					</s:iterator>
				</table>
				</div>
				</td>
			</tr>
		</table>
		</div>
	</s:if>
	<s:elseif test="searchFor == 'K'">
		<div id="keywordDiv" align="center">
		<table width="80%">
			<tr>
				<td align="right" width="30%"><label class="title"
					style="padding: 5px;">Content Search</label></td>
				<td align="left" width="32%"><input type="text" name="keyword"
					id="keyword" size="26" align="right" onchange="validate();">
				</td>
				<td align="left" width="38%" id="hidematch"><input
					type="checkbox" name="searchBy" value="match" id="searchBy"
					style="padding: 5px;">&nbsp;&nbsp;Match Phrase</td>
			</tr>
			<tr>
				<td align="right" width="30%"><label class="title"
					style="padding: 5px;">Search On</label></td>
				<td align="left" width="32%"><select name="output"
					style="min-width: 0px;" onchange="check();">
					<option value="P">Project Name</option>
					<option value="N">Node Name</option>
					<option value="C">Comments</option>
					<option value="A">All Attributes</option>
					<option value="F">PDF Contents</option>
				</select>&nbsp;&nbsp;</td>
				<td width="38%"></td>
			</tr>
			<tr id="wsFilterRow" style="display: none;">
				<td align="right" width="30%"><label class="title"
					style="padding: 5px;">Project</label></td>
				<td align="left" width="32%"><select name="searchWorkspaceID">
					<option value="0000">All Projects</option>
					<s:iterator value="workspaceList">
						<option value="<s:property value='workSpaceId'/>"><s:property
							value='workSpaceDesc' /></option>
					</s:iterator>
				</select></td>
				<td></td>
			</tr>
			<tr id="ndFilterRow" style="display: none;">
				<td align="right" width="30%"><label class="title"
					style="padding: 5px;">Node Name</label></td>
				<td align="left" width="32%"><input type="text"
					name="searchWorkspaceNodeName" /></td>
				<td></td>
			</tr>
			<tr id="stFilterRow" style="display: none;">
				<td align="right" width="30%"><label class="title"
					style="padding: 5px;">Stage</label></td>
				<td align="left" width="32%"><select
					name="searchWorkspaceStageID">
					<option value="0">All Stages</option>
					<s:iterator value="stageList">
						<option value="<s:property value='stageId'/>"><s:property
							value='stageDesc' /></option>
					</s:iterator>
				</select></td>
				<td></td>
			</tr>
		</table>
		</div>
	</s:elseif>
	<br />
	<s:submit cssClass="button" value="  Go  " name="subbtn" id="subbtn"
		align="center" theme="ajax" targets="searchResult"
		executeScripts="true"></s:submit>
	<br />
</s:form></div>
<div id="searchResult" align="center" style="width: 95%"></div>
</div>
</div>
</body>
</html>
