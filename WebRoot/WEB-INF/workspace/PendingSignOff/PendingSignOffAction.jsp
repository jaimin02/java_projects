<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>

<script> 
		
			
</script>
<title></title>
<style type="text/css">
#report {
	border-collapse: collapse;
}

#report th {
	background:#b1b1b1;
	/*background: #E3EAF0 url(js/jquery/grid/header_bkg.png) repeat-x scroll left;*/
	color: #fff;
	padding-left: 8px;
    padding-right: 3px;
    height: 25px;
    font-family: calibri;
    text-align: left;
}

#report td {
	/* background: #FFF; */
	color: #000;
	padding-left: 8px;
    padding-right: 3px;
    height: 25px;
    font-family: calibri;
    text-align: left;	
}

#report tr.odd td {
	/* background: #E3EAF0;; */
	/*background: url(js/jquery/grid/row_bkg_new.png) repeat-x left;*/
	cursor: pointer;
	padding-left: 8px;
    padding-right: 3px;
    height: 25px;
    font-family: calibri;
    text-align: left;
}

#reports div.arrow {
	/* background: transparent url(js/jquery/grid/arrows.png) no-repeat scroll
		0px -16px; */
	background: transparent url(images/Common/details.svg);
	width: 25px;
	height: 25px;
	display: block;
}

/* #reports div.up {
	background-position: 0px 0px;
} */

#report tr.innertable td {
	/*background: url(js/jquery/grid/row_bkg.png) repeat-x left;*/
	padding-left: 8px;
    padding-right: 3px;
    height: 25px;
    font-family: calibri;
    text-align: left;
}

.textboxstyle {
	background-image: url(js/jquery/grid/view1.gif);
	background-repeat: no-repeat;
	background-position: center left;
	padding-left: 18px;
}

#reports thead th{
 	position: sticky;
	top: -1px; 
 	z-index: 1;
 } 
</style>

<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>


<script type="text/javascript">  
        $(document).ready(function()
		{      
        	//debugger;
    		                  
            $("#reports tr:first-child").show();            
            $('.evenRowdata').css('display','none');            		
            $("#reports tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
            $("#reports tr.even").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
            //$("#report").jExpand();
        });
	</script>
</head>
<body>
<!--
<div style="height:28px; border: 1px solid; background:url(js/jquery/grid/row_bkg.png) repeat-x left;" align="right">
<table>
 <tr>
  <td>
  <select id="SearchBy">
	   <option>Project</option>
	   <option>QueryTitle</option>
	   <option>Country</option>	   
  </select>
  </td>
  <td>
  <input type="text" name="search_block_form" />
  </td>
  </tr>
  </table>
</div>
  -->

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 5px; border-top: none;"
	align="center">
	<!-- <div class="headercls" align="center" style="width: 100%">Client Detail History</div> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">Ongoing Task/s</b></div>
<div style="width: 99%; height: 100%">
<s:if test="subqueryMsg.size() == 0">
			<tr class="odd">
				<td colspan="8" style="text-align:center;"><label>No details available.</label></td>
			</tr>
		</s:if>
		<s:else>
<br>
<div
	style="max-height: calc(100vh - 200px); border: 1px solid #175279; margin-top: -13px; background: #E3EAF0; overflow-y: auto;"
	align="left">
<table id="reports" class="datatable paddingtable audittrailtable" style="width:100%;">
	<thead>
		<tr style="margin: 0px; padding: 0px; font: bold 14px calibri;">
			 <th width="5%" style="padding-left: 2px;">#</th> 
			<th width="15%" title="Project">Project</th>
			<th width="15%">${ lbl_nodeName }/${ lbl_folderName }</th>
			<!-- <th width="15%" title="FileName">Document Name</th> -->
			<th colspan="3" width="15%" title="CurrentStage">Action</th>
			<!-- <th width="15%">Change Status</th>
			<th width="15%">Sign Off History</th> -->
			<th width="10%" title="CurrentStage">Current Stage</th>
		</tr>
	</thead>
	<tbody style="padding-right: 10px;">
			<s:iterator value="subqueryMsg" status="status1">
				<s:set name="curnodeid" value="nodeId" />
				<tr class="<s:if test="#status1.even">even</s:if><s:else>odd</s:else>"
					 id="<s:property value="nodeId" />"
					style="margin: 0px; padding: 0px; font: 14px calibri;">
					 <td style="padding-left: 2px; height: 15px;"><s:property
						value="#status1.count" /></td> 
					<td>
						<s:property value="Project" />
					</td>
					<td title="<s:property value="nodeName"/>">
						<s:property value="nodeName" /> [
					<%-- <td title="<s:property value="FileName"/>"> --%>
				<s:if test="OpenFileAndSignOff=='Yes'">
					<a href="SignOffOpenfile.do?workSpaceId=<s:property value="wid"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="FileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>"
							target="_blank">
						<s:property value="FileName" /></a>]
				</s:if>
				<s:else>
					<a href="openfile.do?workSpaceId=<s:property value="wid"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="FileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>"
							target="_blank">
						<s:property value="FileName" /></a>]
				</s:else>
						</td>
					<%-- <td>
						<input type="submit" style="margin-top:2px; margin-bottom:3px" value="Update User" class="button" name="buttonName"
						onclick="return openlinkEdit('Change Rights','<s:property value="wid" />','<s:property value="nextStageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />');"  />	
					</td> --%>
					<td>
						<s:if test='projectType=="D"'>
							<input type="image" title="Delegate User" alt="Submit" src="images/Common/update_user.svg" height="25px" width="25px"
							onclick="return openlinkEditForCR('Change Rights','<s:property value="wid" />','<s:property value="nextStageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />');"  />	
						</s:if>
						<s:else>
							<input type="image" title="Delegate User"  alt="Submit" src="images/Common/update_user.svg" height="25px" width="25px"
							onclick="return openlinkEdit('Change Rights','<s:property value="wid" />','<s:property value="nextStageId" />','<s:property	value="userCode" />','<s:property	value="nodeId" />');"  />
						</s:else>
					</td>
					 <td>
					<%-- <div align="center"><a href="javascript:void(0);"
						onclick="ChangeStatusQuery('<s:property value="QueryId"/>','<s:property value="wid"/>','<s:property value="Project" />');"
						title="Change Status"> <img border="0px" alt="Change Status"
						src="images/Common/view.png" height="18px" width="18px"> </a></div> --%>
						<div><a href="javascript:void(0);"
						onclick="ChangeFileStatus('<s:property value="wid"/>','<s:property value="nodeId"/>','<s:property value="userCode" />','<s:property value="nextStageId" />'
						                           ,'<s:property value="nextStageDesc" />','<s:property value="userName" />','<s:property value="OpenFileAndSignOff" />'
						                           ,'<s:property value="roleName" />','<s:property value="signId" />',
						                           '<s:property value="signImg" />','<s:property value="signStyle" />',
						                            '<s:property value="dateForPreview" />','<s:property value="manualSignatureConfig" />','<s:property value="applicationUrl" />');"
						title="eSign"> <img border="0px" alt="eSign"
						src="images/Common/change_stage.svg" height="25px" width="25px"> </a></div>
					</td> 
					<td>
					<div class="arrow"></div>
					</td>
					<td><s:property value="CurrentStage" /></td>
				</tr>
				<tr class="evenRowdata">
					<td colspan="8" align="right"
						style="text-align: right; background: #E3EAF0;">
					<table style="width:100%;" align="right" id="child_<s:property value="nodeId" />"
						style="border-collapse: collapse; text-align: right; border: 1px solid #175279">
						<tr align="left"
							style="text-align: center; font: bold 11px verdana;">
							<th width="20PX;"
								style="padding-left: 2px; padding-bottom: 10px;">#</th>
							<th width="120px" title="fileName">Document Name</th>
							<th width="80px;" title="Stage">Stages</th>
							<th width="80px" title="SignOffBy">Modified By</th>
							<th width="80px" title="User Role">User Role</th>
							<th width="80px;" title="SignOffOn">Modified On</th>
						<s:if test ="#session.countryCode != 'IND'">
							<th width="80px;" title="SignOffOnEST">Eastern Standard Time</th>
						</s:if>	

						</tr>
						<s:iterator value="queryDtl" status="status">
							<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
								<td style="padding-left: 2px; height: 10px;"><s:property
									value="#status1.count" />.<s:property value="#status.count" /></td>
								<td title="<s:property value="fileName"/>"><%-- <s:if
									test="fileName.length()>30">
									<s:property value="fileName.substring(0,26)" />...</s:if> <s:else> --%>
									<s:property value="fileName" />
								<%-- </s:else> --%></td>
								<td><s:property value="stageHistory" /></td>
								<td><s:property value="userName" /></td>
								<td><s:property value="roleName" /></td>
								<%-- <td><s:date name="modifyOn" format="dd-MM-yyyy hh:mm" /></td> --%>
								<td><s:property value="ISTDateTime" /></td>
							<s:if test ="#session.countryCode != 'IND'">
								<td><s:property value="ESTDateTime" /></td>
							</s:if>
							</tr>
						</s:iterator>
					</table>
			</s:iterator>
	</tbody>
</table>

</div>
</s:else>
<div class="title" style="border: 1px solid #175279; display: none;">For
Paging</div>
</div>
</div>
</div>
</body>

</html>

