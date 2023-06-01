<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/Common/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KnowledgeNET eCTD-Viewer</title>

<!-- css for tree  -->
<link href="<%=request.getContextPath()%>/css/tree/skin/ui.dynatree.css"
	rel='stylesheet' type='text/css' />

<!-- css for tooltip -->
<link
	href="<%=request.getContextPath()%>/css/tooltip/jquery.bubblepopup.v2.3.1.css"
	rel='stylesheet' type='text/css' />

<!-- js for tree  -->
<script src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"
	type='text/javascript'></script>
<script src="<%=request.getContextPath()%>/js/tree/ui.core_tree.js"
	type='text/javascript'></script>
<script src="<%=request.getContextPath()%>/js/tree/jquery.cookie.js"
	type='text/javascript'></script>
<script src="<%=request.getContextPath()%>/js/tree/jquery.dynatree.js"
	type='text/javascript'></script>

<link
	href='<%=request.getContextPath() %>/js/jquery/jquery-ui-1.8.0.min.css'
	rel='stylesheet' type='text/css' />

<script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>

<style>
@
-webkit-keyframes test { 0%{
	-webkit-perspective-origin: 10px;
	-webkit-transform: rotateZ(120deg) skewX(0deg);
	margin-left: 0px;
	opacity: 0;
}
	25%{
margin-top
:
-5px;

		
margin-left
:
2px;

		
	
}
75%{
margin-top

:-15px;

		
margin-left
:
-12px;

	
}
100%{
margin-left
:
0px;

		
margin-top
:
0px;

		
opacity
:
1;

		
-webkit-transform
:rotateZ
(360deg)
 
skewX
(360deg);

		
	
}
}
.animClass {
	-webkit-perspective: 150px;
	-webkit-animation: test 3s;
}

.backbtn {
	width: 24px;
	height: 24px;
	background-image: url('images/Common/Back.png');
	background-repeat: no-repeat;
}

#slider {
	cursor: pointer;
	border: none;
	height: 1px;
}

#allWorkspace {
	padding: 0px;
	position: absolute;
	color: blue;
	border: 0px solid gray;
	margin-top: 25px;
	margin-left: 5px;
	overflow: hidden;
}

#allWorkspace:hover {
	
}

#allWorkspace ul {
	height: auto;
	width: 300px;
	overflow: auto;
	padding: 0px;
	width: auto;
	color: blue;
	cursor: pointer;
	overflow: hidden;
}

#allWorkspace ul li {
	width: 230px;
	background: #5B89AA;
	padding: 5px;
	border: 1px solid white;
	color: white;
	overflow: hidden;
}

#allWorkspace ul li:hover {
	position: relative;
	top: -1px;
	-webkit-box-shadow: 0px 0px 87px 6px rgba(223, 226, 230, 1);
	-moz-box-shadow: 0px 0px 87px 6px rgba(223, 226, 230, 1);
	box-shadow: 0px 0px 87px 6px rgba(223, 226, 230, 1);
	background: #5B89BB;
	border: 1px solid white;
	color: white;
}

#openAllProjects {
	cursor: pointer;
}
</style>


<!-- js for ToolTip  -->
<script
	src="<%=request.getContextPath()%>/js/tooltip/jquery.bubblepopup.v2.3.1.min.js"
	type="text/javascript"></script>
<script type='text/javascript'><!--
		debugger;
	   var fullscreen=true;
		var winwidth=$(window).width();
		var openproject=false;
		$(document).ready(function(){
			(function() {
				
				var lis = $('#allWorkspace ul li').hide();
				
				$('#openAllProjects').click(function() {
					if(openproject)
						openproject=false;
					else
						openproject=true;
					var i = 0;
					(function displayProjects() {
						if(openproject){
							lis.eq(i).removeClass("animClass");
							lis.eq(i++).fadeOut(100, displayProjects);	
					    }else{
							lis.eq(i).addClass("animClass");
							lis.eq(i++).fadeIn(100, displayProjects);
						}
					})();
				});
			
			})();
			
			$("#allWorkspace ul li").click(function(){
				
				var query=this.getAttribute("data");
				
				window.location=query;
							
			});
			debugger;
			$("#parentdiv").width(winwidth);
			$("#dragableleft").css('width',280);
			$("#rightdiv").css('width',winwidth-290);
		
			$( "#slider" ).slider({
				   
			      range: "min",
			      min: 0,
			      max: winwidth,
			      value: 280,     
			      slide: function( event, ui ) {
					
			    	$("#dragableleft").css('width',ui.value-2);
					$("#rightdiv").css('width',winwidth-7-ui.value);
					// $(".ui-slider-handle").css("cursor","-webkit-grabbing");
			      },
			      change:function(event,ui){
			    		$("#dragableleft").css('width',ui.value-2);
			    		$("#rightdiv").css('width',winwidth-7-ui.value);
			       },
			       start:function( event, ui ) {},
			       stop:function( event, ui ) {
			    	}
			});
			
			$(".ui-slider-handle").css("background","rgba(0,0,0,0.4)");
			$(".ui-slider-handle").css("width","17px");
			$(".ui-slider-handle").css("top","159px");
			$(".ui-slider-handle").css("cursor","pointer");
			$(".ui-slider-handle").attr("title","Resize Frame");
					 	 		
			$('#dragableleft1').resizable({
			      start: function(event, ui) {
			        $(this).css('pointer-events','none');
			        	
			         },
			      resize:function(event,ui){
						 $("#rightdiv").width($("#parentdiv").width()-$(this).width()-15);
			 	 		  
			      },   
			      stop: function(event, ui) {
			        $(this).css('pointer-events','auto');
			      }
			  });
		});
		function generateTreeByValue()
		{		
			
			var sequenceObj=document.getElementById("sequence");
			var moduleObj=document.getElementById("module");
			var folderPath=document.getElementById("folderPath");													
			var sequenceValue=sequenceObj.options[sequenceObj.selectedIndex].value;
			var moduleValue=moduleObj.options[moduleObj.selectedIndex].value;
			var folderPathValue=folderPath.value;			
			var m1Flag;
			if(moduleValue=="M1")
				m1Flag="yes";
			else if(moduleValue=="M2-M5")
				m1Flag="no";		
			else if(moduleValue=="Full")
				m1Flag="all";	
			else{ 
				alert("Please Select AtLeast One Module");
				return false;
			}	

			debugger;
			
			$("#resultDiv").height(0);
  			$("#nopreview").height("100%");
  			
  			$("#tooltipdata").html('');
			$( "#slider" ).slider('value',280);							
			if(sequenceValue=="All Sequence")
			{						
				generateNetViewForTree(m1Flag,folderPathValue);			
				return false;
			}				
			reGenerateTree(m1Flag,sequenceValue,folderPathValue);
		}		 	 
		function reGenerateTree(m1Flag,sequenceValue,folderPathValue)
		{			  				
			  $.ajax({			
					url: 'generateTree_ex.do?displayM1='+m1Flag+'&sequenceNo='+sequenceValue+'&folderPath='+folderPathValue,
					beforeSend: function()
					{ 																  																	  		
				  		$('#tree').html('Loadding...');
					},
			  		success: function(data) 
			  		{	
				  		
				  		$('#tree').html(data);		
				  		if(data=="" || data==null)
				  		{
				  			$('#tree').html("No Data Found");
				  		}

				  		debugger;
						$("#dragableleft").css('width',280);
						$("#rightdiv").css('width',winwidth-290);
					},
					async: true							 
				  });
			
		}
		function generateNetViewForTree(m1Flag,folderPathValue)
		{			
			  $.ajax({			
					url: 'generateFullTreeView_ex.do?folderPath='+folderPathValue+'&displayM1='+m1Flag,
					beforeSend: function()
					{ 																  																	  		
				  		$('#tree').html('Loadding...');
					},
			  		success: function(data) 
			  		{		
				  						  		
						$('#tree').html(data);		
						debugger;
						$("#dragableleft").css('width',280);
						$("#rightdiv").css('width',winwidth-290);					
					},
					async: true							 
				  });			
		}
		function viewFillPdf(imgObj)
		{	
			fullscreen=false;
			$("#tooltipdata").animate({"height" : "toggle"}, { duration: "slow" });
			if($("#resultmainDiv").css("height")=="72%")
			{
				$("#resultmainDiv").animate({"height": "93%", "width": "100%px"},"slow", function(){});
			}
			else
			{
				$("#resultmainDiv").animate({"height": "72%", "width": "100%px"},"slow", function(){});														
			}
		}
	</script>
</head>
<body>
<center>
<div id="slider"
	style="height: 1px; width: 100%; margin-top: 5px; background-color: white; color: white"></div>
<div id="allWorkspace">
<ul>

</ul>
</div>
<div id="parentdiv"
	style="height: 628px; border-bottom: 1px solid black; overflow: hidden;"
	align="center">
<div id="dragableleft"
	style="width: 25%; float: left; height: 100%; border-right: 1px solid black; border-top: 1px solid black; margin-right: 6px;"
	align="left"><span style="height: 30px;">
<table width="100%">
	<tr id="openAllProjects">
		<td align="center" colspan="2"
			style="background: #5B89AA; color: white;"><s:property
			value="region" /> <s:if test="procedure_type!=''">
									:&nbsp; <s:property value="procedure_type" />
		</s:if></td>
	</tr>
	<tr>
		<td align="center"><select style="width: 100%;" id="sequence"
			onchange="generateTreeByValue();">
			<option>All Sequence</option>
			<s:iterator value="sequenceDirectoryName">
				<option value="<s:property value="top"/>"><s:property
					value="top" /></option>
			</s:iterator>
		</select></td>
		<td align="center"><select style="width: 100%" id="module"
			onchange="generateTreeByValue();">
			<option value="Full" selected="selected">Full</option>
			<option value="M1">M1</option>
			<option value="M2-M5">M2-M5</option>
		</select></td>
	</tr>
</table>
</span>
<div id="tree" style="overflow: auto; height: 93%; border: none;">
<s:property value="treeHtml" escape="false" /></div>
</div>
<div id="rightdiv"
	style="height: 100%; width: 73%; margin-left: 0px; border: 1px solid; overflow-y: auto; position: relative;"
	align="left">
<div id="tooltipdataDiv"
	style="margin-left: 1px; margin-right: 1px; background: ">
<div style="background: #5B89AA; height: 25px; width: 100%;text-align:center;"><img
	title="Back to Home" alt="Back" name="BackBtn" width="20px"
	height="20px"
	style="margin-top: 3px; float: left; cursor: pointer; background-color: white; margin-left: 4px;"
	src="images/Common/back_off_disabled.png"
	onclick="window.location='ViewAllDossier.do?rg=<s:property value='region' />'"> <span
	style="width: 100%; font-size: 20px; color: white"
	class="nodedetail">Node Details</span> <img
	title="View PDF in full screen" alt="FullView" name="FullView"
	width="20px" height="20px"
	style="margin-top: 3px; float: right; cursor: pointer; background-color: white;"
	src="images/Common/btn_full_view.png" onclick="viewFillPdf(this)">&nbsp;&nbsp;
</div>
<div id="tooltipdata"
	style="overflow: auto; width: 100%; height: 138px; border-top: 1px solid; border-bottom: 1px solid">
</div>
</div>
<div style="height: 8px;" class="rightcenter"></div>
<div id="resultmainDiv"
	style="height: 72%; width: 100%; border-left: 0px;"><img
	src="images/Common/nopreviewpng.png" width="100%" height="100%"
	id="nopreview" /> <iframe id="resultDiv" name="resultDiv"
	scrolling="yes" height="0%" width="99.90%;" align="left"
	style="border: none; overflow: hidden"></iframe></div>
</div>
</div>
<input type="hidden" name="folderPath" id="folderPath"
	value="<s:property value="folderPath"/>"></center>
</body>
<script type="text/javascript">
	generateTreeByValue();
	$("#tooltipdata").animate({"height" : "toggle"}, { duration:0 });
	if($("#resultmainDiv").css("height")=="72%")
	{
		$("#resultmainDiv").animate({"height": "93%", "width": "100%px"},0, function(){});
	}
	else
	{
		$("#resultmainDiv").animate({"height": "72%", "width": "100%px"},0, function(){});														
	}
</script>
</html>