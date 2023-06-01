<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adding Hyper Links</title>

<script src="<%=request.getContextPath()%>/js/newtree/jquery.cookie.js"
	type="text/javascript"></script>
<link
	href="<%=request.getContextPath()%>/js/newtree/skin/ui.dynatree.css"
	rel="stylesheet" type="text/css">

<script
	src="<%=request.getContextPath()%>/js/newtree/jquery.dynatree.js"
	type="text/javascript"></script>

<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">

 var path="";
	


	function subpath() {

		if(path!=null && path!="")
		{
			$.ajax( {
				url : 'AutoCorrectPdfPropertiesMain_ex.do?path=' + path,
				beforeSend : function() {
					
				},
				success : function() {
				alert("Hi");	
				},
			});
		}
		else
		{
			alert("Please select file");
		}
	}
</script>

<STYLE type="text/css">
#treeLeft,#treeRight{
	margin-top: 10px;
	margin-bottom : 10px;
	border: 1px; 
	border-color: #5A8AA9;
	border-style: solid;
	height: 432px;
	width: 402px;
	overflow: auto;
}

#hiddenAddress{
	position:fixed;
	bottom:-10px;
	right:-10px;
	width:1px;
	height:1px;
}

</STYLE>
<SCRIPT type="text/javascript">

	function recompileSubmission(submissionPath,workSpaceId,submissionInfo__DtlId)
	{
		str="RecompileSubmission.do?submissionPath="+submissionPath+"&workSpaceId="+workSpaceId+"&submissionInfo__DtlId="+submissionInfo__DtlId;
		win3=window.open(str,'ThisWindow','height=300,width=450,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
		win3.moveTo(screen.availWidth/2-(450/2),screen.availHeight/2-(300/2));
	}

</SCRIPT>
</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:actionerror />
</div>
<B><s:actionmessage /></B>
<br />
<div align="center"><img
	src="images/Header_Images/Project/Add_Links_Files.png"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><br>
<input type="text" value="CopyLink" id="hiddenAddress" />

<div style=" margin-left: 20px; margin-right: 20px;">
		<s:form cssStyle="text-align: left;">

		<table class="paddingtable" cellspacing="0" width="100%;"
			bordercolor="#EBEBEB">

			<tr class="headercls" onclick="hide('ProjectDetailBox')">
				<td width="95%">Project Detail</td>
				
			</tr>
		</table>

		<div id="ProjectDetailBox" style="border: 1px solid #669;">
		<table class="paddingtable" width="100%;">
			<tr>
				<td width="30%" class="title"><b>Project Name</b></td>
				<td width="70%"><font color="#c00000"><b>${project_name}</b></font>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Project Type</b></td>
				<td width="70%"><font color="#c00000"><b>${project_type}</b></font>
				</td>
				
			</tr>

			<tr>
				<td width="30%" class="title"><b>Client Name</b></td>
				<td width="70%"><font color="#c00000"><b>${client_name}</b></font>
				</td>
			</tr>

		</table>
		</div>
		</s:form></div>

<s:if test="project_type != 'NeeS'  && project_type != 'vNeeS' ">

	<div style="text-align:center; margin-top:5px;">
		<input type="Submit" value="Recompile Submission" class="button" onclick="recompileSubmission('<s:property value="publishPath"/>'+'/'+'<s:property value="sequenceNumber"/>','<s:property value="workSpaceId"/>','<s:property value="subId"/>');"/>
	</div>
</s:if>
<div style="width:900px;">
	<label for="sourceTree" class="title" style="float:left;margin-top:10px; margin-left: 150px;">Source Tree </label> 
	<label for="Select Reference File" class="title" style="float: right; margin-top:10px; margin-right: 120px;">Select Reference File </label>
</div>
<table align="center" width="900px" style="margin-top:20px; margin-left: 20px; margin-right: 20px; margin-bottom: -10px">

<tr>	
<td id="treeLeft"
		style="float: left; "><s:property
		value="dTreeFirst" /></td>


	<td id="treeRight"
		style="float: right; margin-right:-10px;">
		Select file and press CTRL+C to copy file path
		<s:property
		value="dTreeSecond" /></td>
</tr>


<tr>
<td class="left" style="float: left; margin-top:-40px;margin-left:5px;">
<a href="#" id="autoCorrect" onclick="subpath();">Auto Correct Pdf Properties</a>


</td>
<td class="right" style="float: right; margin-right:-6px;margin-top:-40px; width:150px;text-align:center">
<a href="#" id="openFile">Open Selected File</a>
</td>
</tr>
</table>




<!--<h6>
<a target="_blank" href="RecompileSubmission.do?submissionPath=<s:property
		value="publishPath" />/<s:property value="sequenceNumber"/>&workSpaceId=<s:property value="workSpaceId"/>&submissionInfo__DtlId=<s:property value="subId"/>">Republish</a>
	
	
<a title="Republish"
   href="javascript:void(0);"
   onclick="recompileSubmission('<s:property value="publishPath"/>'+'/'+'<s:property value="sequenceNumber"/>','<s:property value="workSpaceId"/>','<s:property value="subId"/>');">
   
	Republish final</a>								

<s:property
		value="publishPath" />/<s:property value="sequenceNumber"/></h6>
--></div>

<script>

	$(document).ready(function(){
			    	
	});
	$('#treeLeft').html($("#treeLeft").text());
	$('#treeRight').html($("#treeRight").text());

	$("#treeLeft").dynatree( {
		title : "sourceTree",
		rootVisible : true,

		onActivate : function(dtnode) {
			var str = dtnode.data.href.replace("file:", "localexplorer:");

			window.open(str,"_self");

		},
		onDeactivate : function(dtnode) {

		},

		onRender : function(node, nodeSpan) {

		},

		onClick : function(node, event) {
			path=node.data.href;

		},
		onFocus : function(dtnode) {

		},
		onBlur : function(dtnode) {

		}
	});

	$("#treeRight").dynatree( {
		title : "Select Reference File",
		rootVisible : true,

		onActivate : function(dtnode) {
			return;
		},
		
		onDeactivate : function(dtnode) {

		},

		onRender : function(node, nodeSpan) {
		//var str = node.data.href.replace("file:", "localexplorer:");
			if ($(nodeSpan).find("a").attr('href')) {
			var newpath = $(nodeSpan).find("a").attr('href');
			setTimeout(function() {

				$(nodeSpan).find("a").attr('href', newpath);

			},1500);

			}
		 
			
		},

		onClick : function(node, event) {
			if(!node.data.isFolder)
			{
				$("#hiddenAddress").val(node.data.href);
				if($("#hiddenAddress").val(node.data.href).find('LocalExplorer:'))
						$("#hiddenAddress").val($("input").val().replace('LocalExplorer:',''));
				setTimeout(function() {
					$("#hiddenAddress").select();
					$("#hiddenAddress").focus();
					$("#openFile").attr('href',"LocalExplorer:"+node.data.href);
					
				},500);
			}
		},
		onFocus : function(dtnode) {

		},
		onBlur : function(dtnode) {

		}
		
	});
</script></div>
</div>


</body>
</html>
