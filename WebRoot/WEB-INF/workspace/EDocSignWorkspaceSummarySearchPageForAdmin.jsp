<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>
<style id="jsbin-css">
::selection {
	background: #a4dcec;
}

::-moz-selection {
	background: #a4dcec;
}

::-webkit-selection {
	background: #a4dcec;
}

::-webkit-input-placeholder { /* WebKit browsers */
	color: #ccc;
	font-style: italic;
}

:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: #ccc;
	font-style: italic;
}

::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: #ccc;
	font-style: italic;
}

:-ms-input-placeholder { /* Internet Explorer 10+ */
	color: #ccc !important;
	font-style: italic;
}

#topbar {
	background: #4f4a41;
	padding: 10px 0 10px 0;
	text-align: center;
}

#topbar a {
	color: #fff;
	font-size: 1.3em;
	line-height: 1.25em;
	text-decoration: none;
	opacity: 0.5;
	font-weight: bold;
}

#topbar a:hover {
	opacity: 1;
}

#searchfield {
	display: block;
	width: 100%;
	text-align: center;
	margin-bottom: 35px;
}

#searchfield form {
	display: inline-block;
	background: #5B89AA;
	padding: 0;
	margin: 0;
	padding: 3px;
	border-radius: 3px;
	margin: 5px 0 0 0;
}

#searchfield form .biginput {
	width: 600px;
	height: 40px;
	padding: 0 10px 0 10px;
	background-color: #fff;
	border: 1px solid #c8c8c8;
	border-radius: 3px;
	color: #aeaeae;
	font-weight: normal;
	font-size: 1.5em;
	-webkit-transition: all 0.2s linear;
	-moz-transition: all 0.2s linear;
	transition: all 0.2s linear;
}

#searchfield form .biginput:focus {
	color: #858585;
}

ul.ui-autocomplete {
	display: inline-block;
	width: 910px !important;
	border: 1px solid #999;
	-webkit-box-shadow: 0px 8px 14px 6px rgba(97, 91, 97, 1);
	-moz-box-shadow: 0px 8px 14px 6px rgba(97, 91, 97, 1);
	box-shadow: 0px 8px 14px 6px rgba(97, 91, 97, 1);
	padding: 5px;
}

.ui-menu .ui-menu-item a {
	overflow-x: hidden;
	background-color: #fff;
	border: 1px solid #c8c8c8;
	border-radius: 3px;
	color: #aeaeae;
	font-weight: normal;
	font-size: 1.5em;
	-webkit-transition: all 0.9s ease;
	-moz-transition: all 0.9s ease;
	transition: all 0.9s ease;
	color: #858585;
	cursor: pointer;
}

.ui-menu .ui-menu-item a:hover {
	color: #fff;
	background: #5B89AA;
	position: relative;
	top: -1px;
	border-color: white;
	-webkit-box-shadow: -1px 1px 15px #888; /* WebKit */
	-moz-box-shadow: -1px 1px 15px #888; /* Firefox */
	-o-box-shadow: -1px 1px 15px #888; /* Opera */
	box-shadow: -1px 1px 15px #888; /* Standard */
}

.autocomplete-suggestions {
	border: 1px solid #999;
	background: #fff;
	cursor: default;
	overflow: auto;
}

.autocomplete-suggestion {
	padding: 10px 5px;
	font-size: 1.2em;
	white-space: nowrap;
	overflow: hidden;
}

.autocomplete-selected {
	background: #f0f0f0;
}

.tabs a {
	background: #5B89AA;
	cursor: pointer;
	padding: 8px;
	padding-left: 20px;
	padding-right: 20px;
	color: #fff;
	border: 1px solid #666;
	border-bottom: 0;
	-webkit-border-radius: 4px 4px 0 0;
}

.tabs a:hover {
	position: relative;
	top: -1px;
	-webkit-box-shadow: -1px 1px 15px #888; /* WebKit */
	-moz-box-shadow: -1px 1px 15px #888; /* Firefox */
	-o-box-shadow: -1px 1px 15px #888; /* Opera */
	box-shadow: -1px 1px 15px #888; /* Standard */
}

.tabs a.active {
	font-weight: bold;
	cursor: default;
	border-bottom: 1px solid pink;
	background: white;
	color: black;
	position: relative;
	top: -3px;
	border-radius: 0 0 -10px 0;
	-webkit-box-shadow: -1px 1px 15px #888; /* WebKit */
	-moz-box-shadow: -1px 1px 15px #888; /* Firefox */
	-o-box-shadow: -1px 1px 15px #888; /* Opera */
	box-shadow: -1px 1px 15px #888; /* Standard */
	border-bottom: 0px;
}

.tabContent {
	border: 1px solid #aaa;
	margin: 8px 0;
	padding: 5px;
}

.autocomplete-suggestions strong {
	font-weight: normal;
	color: #3399ff;
}

#jquery_datatable_filter input{
background-color: #2e7eb9;
color:#ffffff;
}
#jquery_datatable_length select {
background-color: #2e7eb9;
color:#ffffff;
}
</style>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<SCRIPT type="text/javascript">
	

	$(document).ready(function ()
	{
		document.getElementById("desctable").style.display="none";
		
		$(".biginput").val("Quick Search [ Enter Project Name ]");
		$(".biginput").focus(function(){
			$(this).val("");
			$(this).animate({width: 900},"slow");
		}).blur(function () {
			if($(this).val()=="")
				$(this).val("Quick Search [ Enter Project Name ]");
		    $(this).animate({width: 600},"slow");
		});
		
			var options = {target: '#workspaceSummary',
				beforeSubmit: showRequest,
				success: showResponse };
		$('#SummaryForm').submit(function()
		{
			$(this).ajaxSubmit(options);
			return false;
		});
		
		//debugger;
		var temp = {target: '#workspaceAllSummary',
				 beforeSubmit: showRequest,
				success: showResponse}; 
		 $('#SummaryForm').ajaxSubmit(temp);
	});

	/* function getAllProject(){
		//debugger;
		$.ajax({
			type: "POST",
			url: 'ShowESignDocWorkspaceSummay.do',
			sucess: function(data)
			{
				$("#workspaceAllSummary").html(data);
			}
		});
	}  */
	function getDefaultProject()
	{
		//debugger;
		var mode = "<s:property value='mode'/>";
		$.ajax({
			type: "POST",
			url: 'ShowEDocSignWorkspaceSummay_ex.do?defaultWorkSpace=true&projectCode=-1&clientCode=-1&locationCode=-1&workSpaceDesc='+'&mode='+mode,
			beforeSend: function()
			{ 
				$("#workspaceSummary").html("");
				//$("#workspaceSummary").html("loading...");
				$("#workspaceSummary").html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
			},		
	  		success: function(data)
	  		{
				$("#workspaceSummary").html("");
				$("#workspaceSummary").html(data);
					  		
	  		}
		});
			
	}
	function showRequest(formData, jqForm, options)
	{
		$(options.target).html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
		return true;
	}
	function showResponse(responseText, statusText) 
	{
		//debugger;
		 $('#autocomplete').autocomplete({
			    source: allWorkspace,
			    select: function( event, ui ) {
					//working
					//debugger;
			    	window.location="WorkspaceOpen.do?ws_id="+ui.item.data;
				}
				
		});
				
	}
	
	function openlinkEdit(WorkspaceId)
    {
    	var editLocationWindow = "EditESignDocWorkspace.do?workSpaceId="+WorkspaceId;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=350,width=600,resizable=no,titlebar=no");
    	win3.moveTo(screen.availWidth/2-(600/2),screen.availHeight/2-(350/2));
    	return true;
    }
	function createProject()
	{
		var desc=document.SummaryForm.workSpaceDesc.value;
		if(document.getElementById("mode").value=="<s:property value="@com.docmgmt.server.webinterface.entities.Workspace.ProjectType@DMS_SKELETON"/>")
			window.location.href = 'CreateSkeletonProject.do';
		else 
			window.location.href = 'CreateWorkspace.do?workSpaceDesc='+desc;		
			
	}
	function authenticate(wsName,wsId)
	{

		var okCancel = confirm("Do you really want to delete '" + wsName + "' ?");
		if(okCancel == true)
		{
			$.ajax({			
				url: 'DeleteWorkspace_ex.do?workspaceId=' + wsId,
				beforeSend: function()
				{},
		  		success: function(data) 
		  		{
					alert(data);
					document.SummaryForm.submitBtn.click();
				}										
			});
		}
		else
			return false;
	}
	/*function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return true;
  		} 
	} 

	document.onkeypress = detectReturnKey;*/
	

	function tableshow(){
		document.getElementById("desctable").style.display="";	
	}
	
</SCRIPT>

</head>
<body class="yui-skin-sam">

<div class="container-fluid">
<div class="col-md-12">

<div id="searchfield">
<form><input type="text" name="currency" class="biginput"
	id="autocomplete"></form>
</div>
<!-- @end #searchfield -->


<!--<div class="tabs"><a data-toggle="tab1">Search</a></div>  <a
	data-toggle="tab2">EU</a> <a data-toggle="tab3">US</a> <a
	data-toggle="tab4">CA</a> <a data-toggle="tab4">GCC</a> <a
	data-toggle="tab4">SA</a> <a data-toggle="tab4">CH</a></div> -->
<div class="tabContent"><span id="tab1">

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><s:if
	test="mode.charAt(0)==@com.docmgmt.server.webinterface.entities.Workspace.ProjectType@DMS_SKELETON">
	<!-- <img src="images/Header_Images/Project/Manage_Skeleton_Project.jpg"
		style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
		<div class="boxborder"><div class="all_title"><b style="float: left;">Manage Project</b></div>
</s:if> <s:else>
	<!-- <img src="images/Header_Images/Project/Manage_Project.jpg"
		style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
		<div class="boxborder"><div class="all_title"><b style="float: left;">Manage Project</b></div>
</s:else>
<div
	style="padding-left: 3px; width: 100%;  padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="ShowEDocSignWorkspaceSummay_ex" method="post" id="SummaryForm" name="SummaryForm">

	<table width="100%">
		<tr>
			<td class="title" align="right" width="48%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><s:select list="projectTypes"
				name="projectCode" headerKey="-1" headerValue="Select Project Type"
				listKey="projectCode" listValue="projectName" theme="simple">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="48%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left"><s:select list="clients" name="clientCode"
				headerKey="-1" headerValue="Select Client Name" listKey="clientCode"
				listValue="clientName" theme="simple">
			</s:select></td>
		</tr>
		<%-- <tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><s:select list="locations" name="locationCode"
				headerKey="-1" headerValue="Select Region Name"
				listKey="locationCode" listValue="locationName" theme="simple">
			</s:select></td>
		</tr> --%>
		<tr>
			<td class="title" align="right" width="48%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td align="left"><input type="text" name="workSpaceDesc"
				id="autoWorkspace" size="20">&nbsp;&nbsp; <!-- <input
				type="button" value="Create" Class="button"
				onclick="return createProject();"></td> -->
		</tr>
	</table>
	<br>
	<div align="center"><input
		type="submit" class="button" align="left" id="submitBtn"
		name="submitBtn" value="Search" onclick="tableshow();">
		<s:if test ="eTMFCustomization == 'yes'"> 
		   <table align="right" style="padding-right:15px;" id="desctable">
    	    <tbody>
   			  <tr>
       		    <%-- <td><span style="background:#0000FF; padding:0px 5px;">&nbsp;</span></td>
        		<td style="padding-right:10px;">Locked Project</td> --%>
        		<s:if test="userTypeName == 'Archivist'">
        		<td><span style="background:#006400; padding:0px 5px;">&nbsp;</span></td>
                <td>Archive Project</td>
                </s:if>
            </tr>
           </tbody>
          </table>
       </s:if>
		</div>
	<input type="hidden" name="mode" id="mode"
		value="<s:property value="mode"/>" />
	<br>
</s:form>
<div align="center"><s:if
	test="#session.defaultWorkspace == null || #session.defaultWorkspace.trim() == ''">
	<div id="workspaceSummary"></div>
</s:if> <s:else>

	<div id="workspaceSummary"></div>
	<script type="text/javascript">
		getDefaultProject();
	</script>
</s:else></div>
<div align="center"><s:if
	test="#session.defaultWorkspace == null || #session.defaultWorkspace.trim() == ''">
<div id="workspaceAllSummary" style="display: none;"></div>
</s:if></div>
<div id="loadImgDiv" align="center"></div>
</div>
</div>
</div>
</span> <!-- End of First Tab --> <%-- <span id="tab2">

<h5>Tab2</h5>

</span>  --%><!--  End of Second Tab --><%--  <span id="tab3">
<h5>Tab3</h5>

</span> --%> <!-- End of Third Tab --> <%-- <span id="tab4">



</span> --%><!-- End of Fourth Tab --></div>


<script>
;(function($){
  $.fn.html5jTabs = function(options){
    return this.each(function(index, value){
      var obj = $(this),
      objFirst = obj.eq(index),
      objNotFirst = obj.not(objFirst);
      
      $("#" +  objNotFirst.attr("data-toggle")).hide();
      $(this).eq(index).addClass("active");
      
      obj.click(function(evt){
        
        toggler = "#" + obj.attr("data-toggle");
        togglerRest = $(toggler).parent().find("span");
        
        togglerRest.hide().removeClass("active");
        $(toggler).show().addClass("active");
        
        $(this).parent("div").find("a").removeClass("active");
$(this).addClass("active");
        return false;
      });
    });
  };
}(jQuery));

$(function() {
  $(".tabs a").html5jTabs();
});
</script>

</div>
</div>

</body>
</html>