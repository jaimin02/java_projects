<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style>
#ScriptTable tr td:nth-last-child(2) {      
      width:100px;
    }
</style>

<%-- <script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script> --%>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script type="text/javascript">

$(document).ready(function() { 
	/*  $('#ScriptTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } ); */
} );
</script>
</head>
<body>
<br>

<s:if test="getUserForProjectDeatil.size>0">
	<form id="myform" action="ExportToXls.do" method="post" style="width: 25px; float: right; margin: -14px 14px 14px 0; cursor: pointer;">
		<input type="hidden" name="fileName" value="Attribute Detail.xls">					
			<textarea rows="1" cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
				<td style="width:25%;">
				<img alt="Export To Excel" title ="Export To Excel" src="images/Common/Excel.png" 
				onclick="document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()"/>
				</td>
	</form>
</s:if>

<s:if test="getUserAllocationDetails.size > 0">
			
</s:if>
<br>
<s:if test="getUserAllocationDetails.size>0">
<s:form action="CompleteBulkuserAllocation_ex" name="PendingWorkResultForm" method="post">
<div id="divTabText" style="width:100%">
<div class="grid_clr">
<!-- <table id = "ScriptTable" class="datatable paddingtable audittrailtable" width="100%" align="center"> -->
<table width="100%" align="center">
				<tr>
					<td align="right"><!-- <input type="button" name="selectAllButton"
						class="button" value="Check All" onclick="return selectAll();" /> -->
								&nbsp;&nbsp;&nbsp; <input type="button" value="Assign Rights" class="button" name="buttonName"
						onclick="return validate('Update Rights');" />
					</td>
				</tr>
			</table>
<table width="100%" align="center" class="datatable">
	<thead>
		<tr align="left" class="title">
			<!-- <th style="border: 1px solid; border-color:black;">#</th> -->
			<th style="border: 1px solid; border-color:black;" width="5.5%">Sr No.</th>
			<th style="border: 1px solid; border-color:black;" width="44.5%">${ lbl_nodeName }/${ lbl_folderName }</th>
			<th style="border: 1px solid; border-color:black;" width="20%">Assign Stage</th>
			<th style="border: 1px solid; border-color:black;" width="5%">Hours</th>
			<th style="border: 1px solid; border-color:black;" width="5%">
				<input type="checkbox" name="selectAllButton"
						class="button" value="Check All" onclick="return selectAll();" />
			</th>									
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getUserAllocationDetails" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td style="border: 1px solid; border-color:black;">${status.count }</td>
				<%-- <td style="border: 1px solid; border-color:black;"><s:property value="WorkSpaceId" /></td> --%>
				<td style="border: 1px solid; border-color:black;">
					<s:property value="nodeName" /> [ <s:property value="folderName" /> ]
				</td>
				<td style="border: 1px solid; border-color:black;">
					<s:if test='StageId != "" '>
						Assigned
					</s:if>
					<s:else>
						<select name="userId" id="select_<s:property value="nodeId"/>" class="userList" style="width: 35%;" onchange ="return stageSelection(this)">
							<option value="-1" selected >Select Stage</option> 
					        <s:iterator value="getStageDetail">
					            <option value="<s:property value="stageId"/>" title="<s:property value="StageDesc"/>">
					                <s:property value="StageDesc"/>
					            </option>
					        </s:iterator>
						</select> 
						</s:else>						
				</td> 
				<td style="border: 1px solid; border-color:black;">
					<s:if test='StageId != "" '>
						<label style="width:45px">
							<s:property value="hours"/>
						</label>
					</s:if>
					<s:else>
						<input type="text" id="hours_<s:property value="nodeId"/>" name= "enteredhours" style = "width: 45px;"/>
					</s:else>
				
				</td>
				
				<td style="border: 1px solid; border-color:black;">
					<s:if test='StageId != "" '>
						<input type="checkbox" <%= "checked='checked'"  %> disabled="disabled"/>
					</s:if>
					<s:else>
						<%-- <input type="checkbox"value="<s:property value="nodeId"/>" name="selectedNodeId" /> --%>
						<input type="checkbox"value="<s:property value="nodeId"/>" id="nodeid_<s:property value="nodeId"/>" name="selectedNodeId" />
					</s:else>					
				</td>
			</tr>
		</s:iterator>
		
	</tbody>
</table>	
</div>
</div>
	
		<table width="95%" align="center">
			<tr>
				<td align="right"><input type="hidden"
					value="<s:property value='elecSig'/>" name="elecSig" id="elecSig"
					style="display: none;"> <input type="hidden"
					value="<s:property value='#session.password'/>" name="sessPass"
					id="sessPass" style="display: none;"><!--  <input type="button"
					name="selectAllButton1" class="button" value="Check All"
					onclick="return selectedAll();" /> --> &nbsp;&nbsp;&nbsp; <input
					value="Assign Rights" class="button"
					onclick="return validate();" type="button"></input> <s:hidden
					name="selectValues" value=""></s:hidden> <s:hidden
					name="workSpaceId"></s:hidden></td>

			</tr>
		</table>
</s:form>
</s:if>

		<s:else>No details available.</s:else>
<br>
<!-- <div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
</div>
</div>
</div> -->
</body>
</html>