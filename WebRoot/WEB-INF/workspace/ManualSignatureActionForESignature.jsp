<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link href="/KnowledgeNET-Test/decorators/maincss/main.css"
	rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="images/Common/favicon.ico" />
<title>DoQStack Document & Life Cycle Management</title>
<script type="text/javascript">

       function PageLoad(){
    		
      	history.go(1);       	
      	if (window.PageOnLoad)
          	PageOnLoad();
       	history.go(1);       	
       	if (window.PageOnLoad)
           	PageOnLoad();
       }
         window.onbeforeunload = function() {
            if ((window.event.clientX >65 && window.event.clientY < 0) || (event.altKey == true && event.keyCode == 0)) {
                parwin = window.opener;
                if (parwin == null && typeof (parwin) == 'undefined') {
                    HandleLogOutEvent();
                }
            }
        };
        function HandleLogOutEvent() {
            var link = document.getElementById('logoutLink');
            $(location).attr('href',String(link));
        }       
 </script>
 
  <style>
  .modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgb(0 0 0 / 73%); /* Black w/ opacity */
}
.drag-drop.can-drop {
left:0px;
}
 
 #popupContact {
width: 510px !important;
top: 100px !important;
}

/* span{
font-size: 13px !important;
} */

td{
font-size: 12px !important;
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  /*padding: 20px;*/
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
  
.imageClass {
  width:350px;
  display:table;
}
.imageClass > div {
    width:40%;
    display:table-cell;
  }
.imageClass > div p {
      margin:0;
    }
.imageClass .left {
      vertical-align:top;
    }
.imageClass .right {
      text-align:left;
      font-size: xx-small;
    }
  
   #canvas_container {
          width: 800px;
          height: 450px;
          overflow: auto;
      }
 
      #canvas_container {
        background: #333;
        text-align: center;
        border: solid 3px;
      }
      
  #messageContainer{
	display:none;
}

#outer-dropzone {
  height: 140px;

  touch-action: none;
}

#inner-dropzone {
  height: 80px;
}

.dropzone {
  background-color: #ccc;
  border: dashed 4px transparent;
  border-radius: 4px;
  margin: 10px auto 30px;
  padding: 10px;
  width: 100%;
  transition: background-color 0.3s;
}

.drop-active {
  border-color: #aaa;
}

.drop-target {
  background-color: #29e;
  border-color: #fff;
  border-style: solid;
}

.drag-drop {
  display: inline-block;
  position:absolute;
  z-index:999;
  min-width: 40px;
  padding: 0em 0.5em;
  padding-left:0;
  color: #fff;
  background-color: #29e;
  border: none;

  -webkit-transform: translate(0px, 0px);
          transform: translate(0px, 0px);

  transition: background-color 0.3s;
  line-height: 10px;
  padding-right: 0 !important;
  padding-left: 5px !important;
}

.drag-drop.can-drop {
  color: #000;
  background-color: transparent;
  opacity:0.9;
  /* IE 8 */
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=90)";

  /* IE 5-7 */
  filter: alpha(opacity=90);

  /* Netscape */
  -moz-opacity: 0.9;

  /* Safari 1.x */
  -khtml-opacity: 0.9;
}

.can-drop  {
	width:100px !important;
}

.can-drop .right {
    text-align: left !important;
    font-size: 3px !important;
    line-height: initial !important;}

.nopadding {
   padding: 0 !important;
   margin: 0 !important;
}

.circle {
	width: 10px;
    height: 10px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    background: #323c3c;
    float: left;
	display: inline-block;
    margin-top: 1px;
    margin-right: 2px;
}

.dropped-out{
	display: block;
    padding: .75rem 1.25rem;
    margin-bottom: -1px;
    background-color: #fff;
    border: 1px solid rgba(0,0,0,.125);
    width:200px;
    color: black;
}

.col-fixed-240{
    width:240px;
    /* height:100%; */
    z-index:1;
}

.col-fixed-605{
    /* margin-left:240px; */
    width:605px;
    height:100%;
    z-index:1;
}

.page-item{
	cursor:pointer;
}

.pager{
	margin-bottom:30px !important;
	margin-top:0px !important;
	margin-bottom: -31px !important;
}

.drag-drop.dropped-out .descrizione {
    font-size: 12px !important;
}

/* #the-canvas{
  height:792px;
  width: 612px;
} */
  
 .button {
/* background: url(bg_btn.gif) repeat-x; */
background: #2e7eb9;
border: none;
padding: 5px 10px;
font-size: 14px;
font-weight: bold;
color: #FFF;
font-family: calibri;
text-align: center;
cursor: pointer;
border-radius: 15px;
}

b{
font-size: 14px;
}
  .can-drop.drag-drop span {
    font-size: 7px !important;
    margin-top: -19px;
    float: left;}
  </style>
 
	  <!-- <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css'> -->
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script> --%>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<style>
a {
	color: #000 !important;
	text-decoration: none;
}
</style>
<script type="text/javascript">

	var currSearchSpan;
	
	
	$(document).ready(function() {
		debugger;
		
		
		/* 
		
	
		
		$("#copyright").text("\u00A9"+date +" Sarjen Systems Pvt Ltd.");
		
		//Setting search text box for IE 7. Its works fine in case of IE 8 and FF 3.6
		if(navigator.appName.indexOf('Microsoft') != -1){
			if(navigator.appVersion.indexOf('MSIE 7') != -1){
				
				var blanketDiv = document.getElementById('searchFormBlanketDiv');
				blanketDiv.style.width = '';
				blanketDiv.style.left = 0;
				blanketDiv.style.right = 0;

				var blanketDiv = document.getElementById('searchForm');
				blanketDiv.style.width = '';
				blanketDiv.style.left = 0;
				blanketDiv.style.right = 0;
				
			} 
		} */
	});
	
function closePopupDiv()
{
	popupDivEle.style.display = 'none';
	document.getElementById('closeLinkDiv').style.display = 'none';
}


</script>
<%-- <script type="text/javascript"
	src="js/jquery/ScrollTop/scrolltopcontrol.js"></script> --%>
<style>
<!--
SPAN.searchword {
	background-color: yellow;
}

SPAN.currsearchword {
	background-color: green;
	color: white;
}

TABLE.attrTable {
	margin-left: 18px;
}
//
-->
</style>

</head>

<body onload="PageLoad();" link="#0E4569" alink="#0E4569"
	vlink="#0E4569">

<div align="center" style="min-height: calc(100vh - 36px);">
<div id="wrap" style="width: 100%" align="center">



</div>
<!-- This is for display the current user name [end here] --> <!-- content-wrap starts here -->
<div id="content-wrap">
<div id="main" style="margin-top: 10px;">
<div class="container-fluid">
<div class="col-md-12">

<div class="boxborder"><div class="all_title"><b style="float:left">Manual Signature</b></div></div><!-- <img
	src="images/Header_Images/Report/Project_Node_History_Snapshot.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div align="center">
<div id="popupContact" class="popcuswidth" style="height: 460px;">
					<img alt="Close" title="Close" src="images/Common/Close.svg"
						style="height: 25px; width: 25px;" onclick='cls();'
						class='popupCloseButton' />
					<h3 style="margin-top: 2px;">Electronic Signature</h3>
					<!-- <div style="width: 100%; height: 90px; overflow: auto;"> -->
					<table id="tble" style="width: 100%">
						<tr>
							<td class="popUpTdHdr"><b>Username:&nbsp;</b></td>
							<td class="popUpTd"><span id="userName"><s:property value="uName"/></span></td>
						</tr>
						<tr>
							<td class="popUpLbl"><b>Date:&nbsp;</b></td>
							<td class="popUpTd"><span id="date"><s:property value="dateForPreview"/></span></td>
						</tr>
						<tr>
							<td class="popUpLbl"><b>Action:&nbsp;</b></td>
							<td class="popUpTd"><span id="stage">
								 <s:if test="stageuserdtl.size > 0"> 
									<s:select list="stageuserdtl" name="stageId" listKey="stageIdForESIgn" id="stageId"
										listValue="stageDesc" value="%{selectedStage}">
									</s:select>
 								</s:if>
							</span></td>
						</tr>
						<br/>
						<tr>
							<td class="popUpLbl"><b>Password:&nbsp;</b></td>
							<td class="popUpTd"><input type="password" name="reConfPass" id="reConfPass"></input></td>
						</tr>
					</table>
					<hr style="margin-top: 10px; margin-bottom: 10px;">
					<h3 style="margin-top: 2px;">Preview Signature</h3>
					
					<table id="tble" style="width: 100%">
					<tr>
						<td colspan="2" style="border: 1px solid #222; text-align: center; font-weight: bold; font-size: 18px; 
										background-color: gray;	color: black;">Signature</td>
					</tr>
					<tr style="border: 1px solid #222;">
					<td style="vertical-align: top; padding:10px  10px;">
					<table id="tble" style="width: 100%">
						<tr >
							<td>
							<s:if test="imgFilename !=''">	
							<img src="" id="PreviewImg" name="files" height="90px" width="200px" alt="No signature found" style="width: 95%;">
							</s:if>
							<s:else>
							<label style="width: 95%; text-align: center; padding-top: 20px; font-size: 24px;
									font-family: <s:property value="signStyle"/>; color: black;">
							<span><s:property value ="uName"/></span></label>
							</s:else>
							</td>
						</tr>
						</table>
					</td>
					
					<td>
					<table id="tble" style="width: 100%;align:left;">
						
						<tr>
							<td style="color: #000000; width: 55px; padding: 0 !important; text-align: left; vertical-align: top;"><b>Name:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important;  vertical-align: top;"><s:property value ="uName"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important;padding: 0; text-align: left; vertical-align: top;"><b>Role:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important; vertical-align: top;"><s:property value ="role"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0!important; text-align: left; vertical-align: top;"><b>Date:&nbsp;</b></td>
							<td style="color: #000000; padding: 0!important; vertical-align: top;"><s:property value ="dateForPreview"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important; text-align: left; vertical-align: top;"><b>Sign Id:&nbsp;</b></td>
							<td style="color: #000000; padding: 0!important; vertical-align: top;"><s:property value ="signId"/></td>
						</tr>
					</table>
					</td>					
					</tr>					
					
					</table>					
					<br>
					<!-- Enter password:&nbsp;<input type="password" name="reConfPass"
			id="reConfPass"></input> <br> -->
					<!-- <br> -->
					<p align="center">
					<s:if test="projectType=='0004' ">
						<span style="color: #000000; font-size: 14px;">
							<b>I hereby confirm signing of this document electronically as an
							<s:if test="useRightStage==10">Creator.</s:if>
							<s:if test="useRightStage==20">Reviewer.</s:if>
							<s:if test="useRightStage==100">Approver.</s:if></b>
						</span>
						</s:if>
						
						<s:else>
							<span style="color: #000000; font-size: 14px;">
								<b>I hereby confirm signing of this document electronically.</b>
							</span>
						</s:else>
						<br><br>
						 
						<s:if test="lockSeqFlag == true ">
							<input type="button" class="button" value="Change"
								name="buttonName" onclick="lockSeq();"></input>
						</s:if>
						<s:else>
							<s:if test="#session.usertypename != 'Inspector' ">
							
							<%-- <input type="button" class="button" value="Crop"
									id="sighPdf" name="sinPdf"
									onclick="return fileOpenForCrop('openfileForSignature.do?workSpaceId=<s:property value="workspaceID"/>&nodeId=<s:property value="nodeId"/>');"
									style="display: block;float: left;"> --%>
							
								<input type="submit" class="button" value="Sign"
									id="VerifyCertified" name="buttonName"
									onclick="return chgStatus('<s:property value="nodeId"/>');"
									style="display: none"></input>
								<input type="button" class="button" value="Sign"
									id="VerifyVoideFile" name="buttonName"
									onclick="return test1();" style="display: none"></input>
								<input type="button" class="button" value="Sign"
									id="VerifyReview" name="VerifyReview"
									onclick="sendForReview('<s:property value="stageId"/>');"
									style="display: none"></input>
								<input type="submit" class="button" value="Sign" id="Verify"
									name="buttonName" onclick="return verifyPass();"
									style="display: none"></input>
							</s:if>
						</s:else>
					</p>
					<!-- </div> -->
				</div>
				<div id="backgroundPopup"></div><div align="center" id="userforprojectlist"></div>
				
				
				<div class="container">
  
   <br>
  
  <div class="row">
  
					<div class="col-md-12" id="pdfManager" style="display:none">	
						<div class="row" id="selectorContainer">
					<%-- <p class="drag-drop dropped-out" style="width: 95%;">
						<img align="left"  src="" id="files" name="files" alt="Picture" style="width: 12%;"> 
						Name : <s:property value="uName"/><br><br>
  					  	Role : <s:property value="role"/><br><br>
  					    Date : <s:property value="date"/><br><br>
  					    Sign Id : <s:property value="signId"/>
					</p> --%> 	
					
			<div class="row">
  
      <div class="col-md-12" style="padding:10px">
          <div id="navigation_controls" style="">
	            <button id="go_previous" class="button">Previous</button>
	            <input id="current_page" value="1" type="number"/>
	            <button id="go_next" class="button">Next</button>
	            <br><br>
	            <div id="imgDiv">
  					
  				</div>			
			</div>
			
      </div>
  </div>   
  
  <!-- <button class="btn btn-primary btn-block" onClick="showCoordinates()">Show PDF Placeholders Coordinates</button> -->
  			<div id="createUploading" style="height: 30px; width =: 40px; float: left; left: 600px; top: 590px;">
				<img src="images/loading.gif" alt="loading ..." />
			</div>
           <!-- <input type="button" class="btn btn-primary" value="Sign" id="cropBtn" name="CropButton" style="margin-top: 20px;"
			onclick="return cropSign();"
			style="display: block"> -->
			
				<div style="text-align: left;">
					<input type="button" class="btn btn-primary" value="Sign" id="cropBtn" name="CropButton" 
					style="margin-bottom: 5px;" onclick="openPopup()">
   						</div>
  				<div id="signatureDiv" class="imageClass drag-drop dropped-out">
					
						<div class="left">
						
						<s:if test="imgFilename !=''">	
							<img src="" id="files" name="files" alt="No signature found" style="width: 95%;">
							</s:if>
							<s:else>
							<label style="width: 95%; text-align: center; padding-top: 20px; font-size: 16px;
									font-family: <s:property value="signStyle"/>; color: black;">
							<span><s:property value ="uName"/></span></label>
							</s:else>	
							<!-- <img src="" id="files" name="files" alt="No signature found" style="width: 95%;"> -->
  						</div>
	  					<div class="right">
						    <p>
						       Name : <s:property value="uName"/><br>
	  					  	   Role : <s:property value="role"/><br>
	  					       Date : <s:property value="dateForPreview"/><br>
	  					       Sign Id : <s:property value="signId"/>
		    				</p>
	  					</div>
					</div>
					
  							<div class="col-fixed-240" id="parametriContainer">
					
							</div>
							<div class="col-fixed-605">
								<div id="pageContainer" class="pdfViewer singlePageView dropzone nopadding" style="background-color:transparent">
									<canvas id="the-canvas" style="border:1px  solid black"></canvas>
								</div>
							</div>
							<%-- <input type="hidden" value="${pdfPageWidth}" name="pdfPageWidth">
							<input type="hidden" value="${pdfPageHeight}" name="pdfPageHeight"> --%>
						</div>
					</div>
				</div>
</div>



<!-- parameters showed on the left sidebar -->
<input id="parameters" type="hidden"/>
<!-- Below the pdf base 64 rapresentation -->
<input id="pdfBase64" type="hidden" value="" />
<%-- <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script> --%>
<script  src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>

<%-- <script src='https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.0.943/pdf.min.js'></script> --%>
<script  src="<%=request.getContextPath()%>/js/pdf.min.js"></script>

<%-- <script src='https://cdnjs.cloudflare.com/ajax/libs/interact.js/1.2.9/interact.min.js'></script> --%>
<script  src="<%=request.getContextPath()%>/js/interact.min.js"></script>

<%-- <script src='https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.0.943/pdf.worker.min.js'></script> --%>
<script  src="<%=request.getContextPath()%>/js/pdf.worker.min.js"></script>





				
</div>


</div>
</div>
</div>
</div>
</div>
<!-- content-wrap ends here --></div>

</div>
<!-- wrap ends here --></div>


 <a id="logoutLink" href="/KnowledgeNET-Test/Logout.do"></a>
<div style="visibility: hidden;"><a href="http://apycom.com/">s</a></div>




<script type="text/javascript">

//debugger;

document.getElementById('imgDiv').style.display="none";
var fileName = '<s:property value="fileName"/>';
var wsId='<s:property value="workSpaceId"/>';
var nodeId='<s:property value="nodeId"/>';

var b64='<s:property value="b64"/>';
document.getElementById('pdfBase64').value=b64;



</script>
<script  src="<%=request.getContextPath()%>/js/index.js"></script>
<script type="text/javascript">
//debugger; 
$("#createUploading").hide();
var logoname = '<s:property value="imgFilename"/>';
  if(logoname!=''){
	document.getElementById('imgDiv').style.display="block";
  	document.getElementById('files').src='ShowImage_b.do?logoFileName='+logoname;
  	document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;
  }
  else{
	 document.getElementById('imgDiv').innerHTML="No signature found."; 
  }
  debugger;
  document.getElementById('pageContainer').style.width=document.getElementById('the-canvas').width+"px";
  document.getElementById('pageContainer').style.height=document.getElementById('the-canvas').height+"px";
  
  $.ajax({		
	  url: "checkFileSatgeForESignature_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&usercode="+userCode,
	  success: function(data) 
	  {
		  if(data=="true"){
			  document.getElementById('cropBtn').style.display='none'
			  document.getElementById('signatureDiv').style.display='none';
		  	//return false;
		  	//window.close();
		  }
		  
	  },
	  error: function(data) 
	  {
		  //debugger;
		  alert("Something went wrong while fetching data from server.");
	  },
		async: false				
	});

</script>

<script>

 function cropSign(){/* 
	 debugger;
	 var fileName = '<s:property value="fileName"/>';
	 var wsId='<s:property value="workSpaceId"/>';
	 var nodeId='<s:property value="nodeId"/>';
	 // var x=document.getElementById('x').value;
	 var w= document.getElementById("widthValue").value;
	 var h=document.getElementById("heightValue").value;



	 /* var x2=document.getElementById('x2').value;
	 var y2=document.getElementById('y2').value; */



	 //var w=document.getElementById('w').value;
	 /* var x  = 10
	 var y  =10 
	 var w= 10
	 var h=10 */
	 debugger;
	 var pageNo=document.getElementById('current_page').value;
	 

 	 var maxHTMLx = $('#the-canvas').width();
 	 var maxHTMLy = $('#the-canvas').height();
     var paramContainerWidth = $('#parametriContainer').width();
	 var x,y,valore,descrizione,pdfY,posizioneY,posizioneX;
	  $('.drag-drop.can-drop').each(function( index ) {
  		  x = parseFloat($(this).data("x"));
  		  y = parseFloat($(this).data("y"));
  		    
  		  pdfY = y * maxPDFy / maxHTMLy;
  		  posizioneY = maxPDFy - offsetY - pdfY;	  
  		  posizioneX =  (x * maxPDFx / maxHTMLx)  - paramContainerWidth;
  		  
  		  var val = {"descrizione": descrizione, "posizioneX":posizioneX,   "posizioneY":posizioneY, "valore":valore};
  		
  	  });
 	  if(posizioneX==undefined || posizioneY==undefined){
	 alert("Please select signature to sign.");
	 return false;
	 } 
	 $.ajax({
	 url: "CropPdfSign_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&xCordinates="+posizioneX+"&yCordinates="+posizioneY+"&pageNo="+pageNo,
	 beforeSend: function()
	  { 
		  debugger;
		 $('#cropBtn').hide();
		$('#createUploading').show();
	 },
	 success: function(data)
	 {
	 		//debugger;
	 	if(data=="true")
	 		alert("Document signed successfully.");
	 	else
	 		alert(data);
	 	$('#cropBtn').show();
	 	$('#createUploading').hide();
	 },
	 error: function(data)
	 {
	 	alert("Something went wrong while fetching data from server.");
	 	$('#cropBtn').hide();
		$('#createUploading').show();
	 },
	 //async: false
	 });


	 }
 function loadImage(){
	 debugger; 
	 $("#createUploading").hide();
 	var logoname = '<s:property value="imgFilename"/>';
 	  if(logoname!=''){
 		document.getElementById('imgDiv').style.display="block";
 	  	document.getElementById('files').src='ShowImage_b.do?logoFileName='+logoname;
 	  	document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;
 	  }
 	  else{
 		 document.getElementById('imgDiv').innerHTML="No signature found."; 
 	  }
 }
 //debugger;
/*  document.getElementById ("cropBtn").addEventListener ("click", openPopup, false); */
 function openPopup(){
	 debugger;
	 
	 
	 var pageNo=document.getElementById('current_page').value;
	 

 	 var maxHTMLx = $('#the-canvas').width();
 	 var maxHTMLy = $('#the-canvas').height();
     var paramContainerWidth = $('#parametriContainer').width();
	 var x,y,valore,descrizione,pdfY,posizioneY,posizioneX;
	  $('.drag-drop.can-drop').each(function( index ) {
  		  x = parseFloat($(this).data("x"));
  		  y = parseFloat($(this).data("y"));
  		    
  		  pdfY = y * maxPDFy / maxHTMLy;
  		  posizioneY = maxPDFy - offsetY - pdfY;	  
  		  posizioneX =  (x * maxPDFx / maxHTMLx)  - paramContainerWidth;
  		  
  		  var val = {"descrizione": descrizione, "posizioneX":posizioneX,   "posizioneY":posizioneY, "valore":valore};
  		
  	  });
 	  if(posizioneX==undefined || posizioneY==undefined){
	 alert("Please select signature to sign.");
	 return false;
	 }
 	  
	//checks if signatre peroformed
	/* var checkFlag;
	var userCode ='<s:property value="usercode"/>';
	var tranNo =<s:property value="tranNo"/>;
	tranNo=tranNo+1;
 	 $.ajax({
 		 url: "CheckSignature_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&usercode="+userCode+"&tranNo="+tranNo,
 		 success: function(data)
 		 {
 		 	if(data=="true")
 		 		checkFlag=true;
 		 },
 		 error: function(data)
 		 {
 		 	alert("Something went wrong while fetching data from server.");
 		 },
 		 async: false
 		 });
 	//checking ends
 	
 	 if(checkFlag==true){
 		 alert("You can not performe signature. Please try after sometime");
 		 return false;
 		 } */
 	  
 	var srFlag = "<s:property value='srFlag'/>";
	 var userName = "<s:property value='#session.username'/>";
	 var stageId = <s:property value='stageId'/>;
		document.getElementById('userName').innerHTML = userName;
	
		var  months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
		var monthName; // current month in mmm formate

		var currentdate = new Date();
		var datetime =  currentdate.getDate()+ "-" 
	 					+  months[currentdate.getMonth()] + "-" 
					    + currentdate.getFullYear() + " "
						//+ currentdate.getHours() + ":"  
 					//+ currentdate.getMinutes();
		document.getElementById("date").innerHTML = datetime;
		var signatureFlag = "<s:property value='signatureFlag'/>";
		
		var url_string = window.location.href;
		var url = new URL(url_string);
		var selectedStage = url.searchParams.get("selectedStage");
		
		//if(stageId==10 && srFlag==""){
			isVerifyReview=true;
			document.getElementById("Verify").style.display='none';
			document.getElementById("VerifyCertified").style.display='none';
			document.getElementById("VerifyVoideFile").style.display='none';
			document.getElementById("VerifyReview").style.display='block';
			//document.getElementById('stage').innerHTML = "Created";
			var a=document.getElementById('stageId');
			a.selectedIndex=0;
			
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			
		/* }
		
		if (stageId==10 && srFlag=="SR"){
			isVerifyReview=true;
			document.getElementById("Verify").style.display='none';
			document.getElementById("VerifyCertified").style.display='none';
			document.getElementById("VerifyVoideFile").style.display='none';
			document.getElementById("VerifyReview").style.display='block';
			if(selectedStage=="20")			
				document.getElementById('stage').innerHTML = "Reviewed";
			if(selectedStage=="0")
				document.getElementById('stage').innerHTML = "Send Back";
			
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			
		}
		if (stageId==20 && srFlag=="SR"){
			isVerifyReview=true;
			document.getElementById("Verify").style.display='none';
			document.getElementById("VerifyCertified").style.display='none';
			document.getElementById("VerifyVoideFile").style.display='none';
			document.getElementById("VerifyReview").style.display='block';
			if(selectedStage=="100")			
				document.getElementById('stage').innerHTML = "Approve";
			if(selectedStage=="0")
				document.getElementById('stage').innerHTML = "Send Back";
			else
				document.getElementById('stage').innerHTML = "Approve";
			
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			
		}
		
		if (stageId==100 && srFlag=="SR"){
			isVerifyReview=true;
			document.getElementById("Verify").style.display='none';
			document.getElementById("VerifyCertified").style.display='none';
			document.getElementById("VerifyVoideFile").style.display='none';
			document.getElementById("VerifyReview").style.display='block';
			if(selectedStage=="100")			
				document.getElementById('stage').innerHTML = "Approve";
			if(selectedStage=="0")
				document.getElementById('stage').innerHTML = "Send Back";
			else
				document.getElementById('stage').innerHTML = "Approve";
			
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			
		} */
	/* 	if(buttonName=='Change'){
			var stage = document.getElementById("stageCode");
			var selectedStage = stage.options[stage.selectedIndex].text;
			document.getElementById('stage').innerHTML = selectedStage;	
			document.getElementById("VerifyCertified").style.display='none';
			document.getElementById("VerifyReview").style.display='none';
			document.getElementById("VerifyVoideFile").style.display='none';
			document.getElementById("Verify").style.display='block';
			
			if(selectedStage!="Send Back" || selectedStage!="Send back" ){
				if(signatureFlag=="false"){
					alert("Please create signature before proceeding sign off.");
					return false;
				}
			}
			
		} */
		centerPopup();				
		loadPopup();
	}
 
 function cls()
	{
		disablePopup();
	}
 
 
 function sendForReview(stageId)
	{
		//alert("test");
		debugger;
		
		
		var nodeId = '<s:property value="nodeId"/>';
		var wsId = '<s:property value="workSpaceId"/>';
		var userCode ='<s:property value="usercode"/>';
		
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var signatureFlag = "<s:property value='signatureFlag'/>";
		
		var urlParams = new URLSearchParams(window.location.search);
		var deviationRemark = urlParams.get('deviationRemark');
		var srFlag = "<s:property value='srFlag'/>";
		
		
 		var pageNo=document.getElementById('current_page').value;
		 

	 	 var maxHTMLx = $('#the-canvas').width();
	 	 var maxHTMLy = $('#the-canvas').height();
	     var paramContainerWidth = $('#parametriContainer').width();
		 var x,y,valore,descrizione,pdfY,posizioneY,posizioneX;
		  $('.drag-drop.can-drop').each(function( index ) {
	  		  x = parseFloat($(this).data("x"));
	  		  y = parseFloat($(this).data("y"));
	  		    
	  		  pdfY = y * maxPDFy / maxHTMLy;
	  		  posizioneY = maxPDFy - offsetY - pdfY;
	  		  posizioneX =  (x * maxPDFx / maxHTMLx)  - paramContainerWidth;
	  		  
	  		  var val = {"descrizione": descrizione, "posizioneX":posizioneX,   "posizioneY":posizioneY, "valore":valore};
	  		
	  	  });
	 	  if(posizioneX==undefined || posizioneY==undefined){
		 alert("Please select signature to sign.");
		 return false;
		 } 
		
		
		var flag=false;
		if(signatureFlag=="true"){
		eSign = "Y";
		if(sessPass==pass || sessAdUserPass==pass){
			$.ajax({		
				  url: "checkFileSatge_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&usercode="+userCode,
				  success: function(data) 
				  {
					  if(data=="true"){
					  	alert("Signature already performed.");
					  	flag=true;
					  	//return false;
					  	window.close();
					  }
					  
				  },
				  error: function(data) 
				  {
					  //debugger;
					  alert("Something went wrong while fetching data from server.");
				  },
					async: false				
				});
		}
		if(flag==false){
			var sessAdUser="<s:property value='#session.adUser'/>";
			var sessAdUserPass="<s:property value='#session.adUserPass'/>";
			if (!pass || pass == '' )
			{						
				alert("Please enter Password.");
				$( '#reConfPass').focus(); 
				return false;
			}
			if(sessAdUser=='Y'){
				if(sessAdUserPass!=pass){
					alert("You have AD User rights. Please enter correct password.");
					document.getElementById('reConfPass').value = '';
					$( '#reConfPass').focus(); 
					return false;
				}
			}
		if(sessAdUser!='Y'){
				if(sessPass!=pass){
				alert("Incorrect Password !!!");
				document.getElementById('reConfPass').value = '';
				$( '#reConfPass').focus(); 
				return false;
				}
			}
			
		if(sessPass==pass || sessAdUserPass==pass){
				var url_string = window.location.href;
				var url = new URL(url_string);
				//var selectedStage = url.searchParams.get("selectedStage");
				var stage = document.getElementById("stageId");
				var selectedStage = stage.options[stage.selectedIndex].value;
				var action="";
				var tranNo =<s:property value="tranNo"/>;
				tranNo=tranNo+1;
				var sendToNextStageFlag=false;
				var projectType ='<s:property value="projectType"/>';
				
				
				//if (stageId==100 && srFlag=="SR"){
					var a=document.getElementById('stageId');
					var selectedOption=a.options[a.selectedIndex].text;
			
					$.ajax({		
						beforeSend: function()
						  { 
							 // debugger;
							  //$("#popupContact").html("<center><img style=\"margin-top:100px\" src=\"images/loading.gif\" alt=\"loading ...\" /></center>");
							 document.getElementById("popupContact").style.display='none';
							 document.getElementById("backgroundPopup").style.display='none';
						  },
					 	 //$.ajax({
					 		 url: "CheckSignature_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&usercode="+userCode+"&tranNo="+tranNo,
					 		 success: function(data)
					 		 {
					 		 	if(data=="false")
					 		 	{
							$.ajax({		
						  /* url: "sendForReviewToApprove_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&usercode="+userCode
								  +"&remark="+deviationRemark+"&stageId="+100+"&eSign="+eSign+"&xCordinates="+posizioneX+"&yCordinates="+posizioneY+"&pageNo="+pageNo, */
							url: "sendForReviewToApproveForESignature_ex.do?workSpaceId="+wsId+"&nodeId="+nodeId+"&usercode="+userCode
								  +"&remark="+deviationRemark+"&stageId="+selectedStage+"&eSign="+eSign+"&xCordinates="+posizioneX
								  +"&yCordinates="+posizioneY+"&pageNo="+pageNo+"&actionDesc="+selectedOption+"&tranNo="+tranNo,
						  beforeSend: function()
						  { 
							 // debugger;
							  //$("#popupContact").html("<center><img style=\"margin-top:100px\" src=\"images/loading.gif\" alt=\"loading ...\" /></center>");
							 document.getElementById("popupContact").style.display='none';
							 document.getElementById("backgroundPopup").style.display='none';
						  },
						  success: function(data) 
						  {
							  //debugger;
							  //document.getElementById("loadingpopup").style.display='none';
							  //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
							  alert("Signature successful.");
							  //if(projectType=='0004')
								  window.location.reload();
							  /* else
								  window.close(); */
						  },
						  error: function(data) 
						  {
							  //debugger;
							  alert("Something went wrong while fetching data from server.");
						  },
							async: false				
						});
					 		 	}
					 		 	else{
					 		 		sendToNextStageFlag=true;
				 		 			alert("Please try again after sometime.")
				 		 			window.location.reload();
				 		 		}
					 		},
					 		 error: function(data)
					 		 {
					 		 	alert("Something went wrong while fetching data from server.");
					 		 },
					 		 async: false
					 	});				
					}
				//}
		}
		else{
			alert("Please create signature.");
			return false;
			}
		}	
	}

 

</script>

</body>
</html>