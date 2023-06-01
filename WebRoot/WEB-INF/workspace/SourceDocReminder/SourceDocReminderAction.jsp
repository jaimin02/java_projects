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
	background:#2e7eb9;
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
	/* background: #E3EAF0; */
	/*background: url(js/jquery/grid/row_bkg_new.png) repeat-x left;*/
	cursor: pointer;
	padding-left: 8px;
    padding-right: 3px;
    height: 25px;
    font-family: calibri;
    text-align: left;
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
	/*background: url(js/jquery/grid/row_bkg.png) repeat-x left;*/
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
 #reminder tr td:nth-last-child(2) {      
    width:80px;
 }
  
#reminder thead th{
  position: sticky;
  top: -1px; 
  z-index: 1;
} 
 
</style>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

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
    		$('#reminder').dataTable( {
    			
    			/* aoColumns: [
   	           		{mData: 'UserName' }], */
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			
			columnDefs: [{
			    "defaultContent": "-",
			    "targets": "_all"
			  }]
		 } );           
            /*$("#report tr:first-child").show();            
            $('.evenRowdata').css('display','none');            		
             $("#report tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            }); */
            //$("#report").jExpand();
        });
        
        
        function fileOpen(fileName,filePath)
    	{
    		debugger;
    		win3=window.open(filePath+"/"+filePath,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
    		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
    	}
        
        
	</script>
</head>
<body>

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 5px; border-top: none;"
	align="center">
	<!-- <div class="headercls" align="center" style="width: 100%">Client Detail History</div> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">Doc Reminder/s</b></div>
	<s:if test="getSrcDocReminder.size() == 0">
			<tr class="odd">
				<td colspan="6" style="text-align: center;"><label class="title">No	details available.</label></td>
			</tr>
		</s:if>
		<s:else>
<br>
<div style="width: 99%; height: 100%"> 

<div
	style="max-height: calc(100vh - 200px); border: 1px solid #175279; background: #E3EAF0; margin-top: -13px; overflow-y: auto;"
	align="left">
<table id="reminder" class="datatable paddingtable audittrailtable" style="width:100%; overflow: auto; z-index: 1; align:center;">
	<thead>
		<tr style="margin: 0px; padding: 0px; font: bold 16px calibri;">
			 <th style="padding-left: 2px;">#</th> 
			<th>Project</th>
			<th>${ lbl_nodeName }/${ lbl_folderName }</th>
			<!-- <th>Document Path</th> -->
			<th>Status</th>
			<th>Modified by</th>
			<th>Modified on</th>
			<s:if test ="#session.countryCode != 'IND'">
				<th>Eastern Standard Time</th>
			</s:if>
		</tr>
	</thead>
	<tbody style="padding-right: 10px;">
			<s:iterator value="getSrcDocReminder" status="status1">
				<s:set name="curnodeid" value="nodeId" />
				<tr class="<s:if test="#status1.even">even</s:if><s:else>odd</s:else>">
					 <td style="padding-left: 2px; height: 15px;"><s:property
						value="#status1.count" /></td> 
					<td>
						<s:property value="workSpaceDesc"/>
					</td>
					
					<td style="break-word:break-word;"title="<s:property value="nodeName"/>">
						<s:property value="nodeName" /> 
						<%-- <a href="#"	onclick="return fileOpen('<s:property value="filePath" />','<s:property value="folderName" />');">
						[<s:property value="folderName" />] </a> --%>
						[<s:property value="folderName" />]
						
					</td>
					
					<td>
						New
					</td>
					
					<td>
						<s:property value="userName"/>
					</td>
					
					<td>
						<s:property value="ISTDateTime" />
					</td>
					<s:if test ="#session.countryCode != 'IND'">
						<td><s:property value="ESTDateTime" /></td>
					</s:if>
				</tr>
			</s:iterator>
	</tbody>
</table>
</div>
<!-- <div class="title" style="border: 1px solid #175279; display: none;">For
Paging</div> -->
</div>
</s:else>
</div>
</div>
</body>

</html>

