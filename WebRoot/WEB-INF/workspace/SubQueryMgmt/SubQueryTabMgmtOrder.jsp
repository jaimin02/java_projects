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
	background: #E3EAF0 url(js/jquery/grid/header_bkg.png) repeat-x scroll
		left;
	color: #fff;
	padding: 0px 0px;
	text-align: left;
}

#report td {
	background: #FFF;
	color: #000;
	padding: 0px;
	margin: 0px;
}

#report tr.odd td {
	background: url(js/jquery/grid/row_bkg_new.png) repeat-x left;
	cursor: pointer;
}

#report div.arrow {
	background: transparent url(js/jquery/grid/arrows.png) no-repeat scroll
		0px -16px;
	width: 16px;
	height: 16px;
	display: block;
}

#report div.up {
	background-position: 0px 0px;
}

#report tr.innertable td {
	background: url(js/jquery/grid/row_bkg.png) repeat-x left;
	margin: 0px;
	padding: 0px;
	text-align: left;
}

.textboxstyle {
	background-image: url(js/jquery/grid/view1.gif);
	background-repeat: no-repeat;
	background-position: center left;
	padding-left: 18px;
}
</style>

<script type="text/javascript">  
        $(document).ready(function()
		{      
    		                  
            $("#report tr:first-child").show();            
            $('.evenRowdata').css('display','none');            		
            $("#report tr.odd").click(function(){
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

<div style="width: 100%; height: 100%">

<div
	style="min-height: 50px; max-height: 350px; border: 1px solid #175279; background: #E3EAF0; overflow-y: auto;"
	align="left">
<table id="report" width="100%">
	<thead>
		<tr style="margin: 0px; padding: 0px; font: bold 11px verdana;">
			<th width="15px" style="padding-left: 2px;">#</th>
			<th width="120px" title="Project">Project</th>
			<th width="70px" title="Country">Country</th>
			<th width="120px" title="QueryTitle">QueryTitle</th>
			<th width="40px" title="Total">Total</th>
			<th width="40px" align="center" style="text-align: center"
				title="Unresolved">Un <br />
			Res.</th>
			<th width="60px">Change Status</th>
			<th width="16px"></th>
		</tr>
	</thead>
	<tbody style="padding-right: 10px;">
		<s:if test="subqueryMsg.size() == 0">
			<tr class="odd">
				<td colspan="7" align="center"><label class="title">No
				details found.</label></td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="subqueryMsg" status="status1">
				<tr class="odd"
					style="margin: 0px; padding: 0px; font: 10px verdana;">
					<td style="padding-left: 2px; height: 15px;"><s:property
						value="#status1.count" /></td>
					<td><s:if test="Project.length()>30">
						<s:property value="Project.substring(0,26)" />...</s:if> <s:else>
						<s:property value="Project" />
					</s:else></td>
					<td><s:property value="Country" /></td>
					<td title="<s:property value="QueryTitle"/>"><s:if
						test="QueryTitle.length()>35">
						<s:property value="QueryTitle.substring(0,31)" />...</s:if> <s:else>
						<s:property value="QueryTitle" />
					</s:else></td>
					<td align="center"><s:property value="Total" /></td>
					<td align="center"><s:property value="UnResolved" /></td>

					<td align="center">
					<div align="center"><a href="javascript:void(0);"
						onclick="ChangeStatusQuery('<s:property value="QueryId"/>','<s:property value="wid"/>','<s:property value="Project" />');"
						title="Change Status"> <img border="0px" alt="Change Status"
						src="images/Common/view.png" height="18px" width="18px"> </a></div>
					</td>
					<td>
					<div class="arrow"></div>
					</td>
				</tr>
				<tr class="evenRowdata">
					<td colspan="7" align="right"
						style="text-align: right; padding-bottom: 5px; margin-bottom: 5px; background: #E3EAF0; padding-left: 5px; padding-right: 5px;">
					<table width="100%" align="right"
						style="border-collapse: collapse; text-align: right; border: 1px solid #175279">
						<tr align="left"
							style="text-align: center; font: bold 11px verdana;">
							<th width="20PX;"
								style="padding-left: 2px; padding-bottom: 10px;">#</th>
							<th width="120px" title="AffectedNode">AffectedNode</th>
							<th width="80px;" title="Status">Status</th>
							<th width="80px" title="ResolvedBy">ResolvedBy</th>
							<th width="80px;" title="ResolvedOn">ResolvedOn</th>

						</tr>
						<s:iterator value="queryDtl" status="status">
							<tr class="innertable" style="font: 10px verdana;">
								<td style="padding-left: 2px; height: 10px;"><s:property
									value="#status1.count" />.<s:property value="#status.count" /></td>
								<td title="<s:property value="nodeName"/>"><s:if
									test="nodeName.length()>30">
									<s:property value="nodeName.substring(0,26)" />...</s:if> <s:else>
									<s:property value="nodeName" />
								</s:else></td>
								<td><s:property value="queryStatus" /></td>
								<td><s:property value="resolvedByName" /></td>
								<td><s:date name="resolvedDate" format="MMM-dd-yyyy" /></td>

								</td>
							</tr>
						</s:iterator>
					</table>
					</td>
				</tr>
			</s:iterator>
		</s:else>
	</tbody>
</table>
</div>
<div class="title" style="border: 1px solid #175279; display: none;">For
Paging</div>



</div>


</body>

</html>

