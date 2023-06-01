<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style>

#popupContact {
top:-35px !important;
width: 570px !important;
}
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
@font-face {
	    font-family: 'password';
	    font-style: normal;
	    font-weight: 400;
	   /*  src: url('images/password.ttf'); */
		src: url(data:font/woff;charset:utf-8;base64,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) format("woff");

	   
	}          
	#reConfPass {
            font-family: 'password';
        }
        
dojoLayoutContainer{ position: relative; display: block; }
body .dojoAlignTop, body .dojoAlignBottom, body .dojoAlignLeft, body .dojoAlignRight { position: absolute; overflow: hidden; }
body .dojoAlignClient { position: absolute }
.dojoAlignClient { overflow: auto; }
.dojoTabContainer {
	position : relative;
}

.dojoTabPaneWrapper {
	border : 1px solid #6290d2;
	_zoom: 1; /* force IE6 layout mode so top border doesnt disappear */
	display: block;
	clear: both;
}

.dojoTabLabels-top {
	position : relative;
	top : 0px;
	left : 0px;
	overflow : visible;
	margin-bottom : -1px;
	width : 100%;
	z-index: 2;	/* so the bottom of the tab label will cover up the border of dojoTabPaneWrapper */
}

.dojoTabNoLayout.dojoTabLabels-top .dojoTab {
	margin-bottom: -1px;
	_margin-bottom: 0px; /* IE filter so top border lines up correctly */
}

.dojoTab {
	position : relative;
	float : left;
	padding-left : 9px;
	border-bottom : 1px solid #6290d2;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_left.gif) no-repeat left top;
	cursor: pointer;
	white-space: nowrap;
	z-index: 3;
}

.dojoTab div {
	display : block;
	padding : 4px 15px 4px 6px;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_top_right.gif) no-repeat right top;
	color : #333;
	font-size : 90%;
}

.dojoTab .close {
	display : inline-block;
	height : 12px;
	width : 12px;
	padding : 0 12px 0 0;
	margin : 0 -10px 0 10px;
	cursor : default;
	font-size: small;
}

.dojoTab .closeImage {
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_close.gif) no-repeat right top;
}

.dojoTab .closeHover {
	background-image : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_close_h.gif);
}

.dojoTab.current {
	padding-bottom : 1px;
	border-bottom : 0;
	background-position : 0 -150px;
}

.dojoTab.current div {
	padding-bottom : 5px;
	margin-bottom : -1px;
	background-position : 100% -150px;
}

/* bottom tabs */

.dojoTabLabels-bottom {
	position : relative;
	bottom : 0px;
	left : 0px;
	overflow : visible;
	margin-top : -1px;
	width : 100%;
	z-index: 2;
}

.dojoTabNoLayout.dojoTabLabels-bottom {
	position : relative;
}

.dojoTabLabels-bottom .dojoTab {
	border-top :  1px solid #6290d2;
	border-bottom : 0;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_bot_left.gif) no-repeat left bottom;
}

.dojoTabLabels-bottom .dojoTab div {
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_bot_right.gif) no-repeat right bottom;
}

.dojoTabLabels-bottom .dojoTab.current {
	border-top : 0;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_bot_left_curr.gif) no-repeat left bottom;
}

.dojoTabLabels-bottom .dojoTab.current div {
	padding-top : 4px;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_bot_right_curr.gif) no-repeat right bottom;
}

/* right-h tabs */

.dojoTabLabels-right-h {
	overflow : visible;
	margin-left : -1px;
	z-index: 2;
}

.dojoTabLabels-right-h .dojoTab {
	padding-left : 0;
	border-left :  1px solid #6290d2;
	border-bottom : 0;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_bot_right.gif) no-repeat right bottom;
	float : none;
}

.dojoTabLabels-right-h .dojoTab div {
	padding : 4px 15px 4px 15px;
}

.dojoTabLabels-right-h .dojoTab.current {
	border-left :  0;
	border-bottom :  1px solid #6290d2;
}

/* left-h tabs */

.dojoTabLabels-left-h {
	overflow : visible;
	margin-right : -1px;
	z-index: 2;
}

.dojoTabLabels-left-h .dojoTab {
	border-right :  1px solid #6290d2;
	border-bottom : 0;
	float : none;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_top_left.gif) no-repeat left top;
}

.dojoTabLabels-left-h .dojoTab.current {
	border-right : 0;
	border-bottom :  1px solid #6290d2;
	padding-bottom : 0;
	background : url(/KnowledgeNET-Test/struts/dojo/src/widget/templates/images/tab_top_left.gif) no-repeat 0 -150px;
}

.dojoTabLabels-left-h .dojoTab div {
	background : 0;
	border-bottom :  1px solid #6290d2;
}
        
        
        
        
</style>
<s:head theme="ajax" />
 <script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>

<%--  <script type="text/javascript" src="/KnowledgeNET-Test/struts/dojo/dojo.js"></script>
<script type="text/javascript" src="/KnowledgeNET-Test/struts/dojo/src/debug.js"></script>
<script type="text/javascript" src="/KnowledgeNET-Test/struts/dojo/src/browser_debug.js"></script>
<script type="text/javascript" src="/KnowledgeNET-Test/struts/simple/dojoRequire.js"></script>
<script type="text/javascript">dojo.hostenv._global_omit_module_check = false;</script>
<script type="text/javascript" src="/KnowledgeNET-Test/struts/ajax/dojoRequire.js"></script> --%>
	
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
$(document).ready(function() {	
	
	$("#reConfPass").focus();
	$("#savesendbtn").hide();
 	$("#saveandsendReview").hide();
 	
 	
 	var logoname = '<s:property value="signImg"/>';
 	
	if(logoname!==""){
	document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;
	}
	$("#reConfPass").on("keydown", function (e) {
	    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
	    	e.preventDefault();
	    	 document.getElementById("VerifyReview").click();
	    }
	});
	debugger;
	$('#uploadFile').change(
            function(){
                if ($(this).val()) {
                	 $("#savesendbtn").show();
                	 $("#saveandsendReview").show();
                	 $("#sendReview").hide();
                } 
            }
            );
	
	$("#downloading").hide();
	$("#downloading_approved").hide();
	$("#uploading").hide();
	$("#createUploading").hide();
	$("#validateUploading").hide();
	$('#Spdownloading').hide();
	$('#SaveNContinue').hide();
	$('#comment').hide();
	$('#reviewSPDoc').hide();
	$('#uploadingSourceDoc').hide();
	$('#uploadDocBtn').hide();
	$('#uploadSrcDocspn').hide();
	$('#uploadDoc').hide();
	$('#createDocBtn').hide();
	$('#SrcValidateButtonId').hide();
	
	//debugger;
	var right=${isCreate};
	var CnF=${confirmAndUpload};
	var isDownload=${IsDownload};
	var isComment=${IsComment};
	var isValidate=${IsValidate};		
	
	if(right==true){
	var span_Text = document.getElementById("uploadDocBtn").value;
	var uploadBtnTextSelection=document.getElementById('srcDocOption').value;
	
		if(uploadBtnTextSelection=="createSourceDocument"){
			//document.getElementById("uploadDocBtn").value="Create";
			$('#uploadDocBtn').hide();
			$('#uploadSrcDocspn').hide();
			$('#uploadDoc').hide();
		}
	}	
	if(isDownload==true){
		$('#optionID').hide();
		$('#createOrUpload').hide();
	}
	if(right!=true){
		$('#optionID').hide();
		$('#createOrUpload').hide();
	}
	var IsUpload=${IsUpload};
	var IsDownload=${IsDownload};
	var isComment=${IsComment};
	
	if(IsUpload==true ){
		$('#SrcValidateButtonId').show();
		$('#reviewSPDoc').show();
		//$('#SaveNContinue').show();
	}
	if(IsDownload==true){
		$('#SaveNContinue').show();
		$('#comment').show();
		
		//$('#SaveNContinue').hide();
	}
	
	
	});
	
	
	
function changeBtnName(){
	//alert("test");
	debugger;
	var span_Text = document.getElementById("uploadDocBtn").value;
	var uploadBtnTextSelection=document.getElementById('srcDocOption').value;
	
	if(uploadBtnTextSelection=="createSourceDocument"){
		//document.getElementById("uploadDocBtn").value="Create"
		$('#createDocBtn').show();
		$('#uploadSrcDocspn').hide();
		$('#uploadDoc').hide();
		$('#uploadDocBtn').hide();
	}else{
		//document.getElementById("uploadDocBtn").value="Upload";
		$('#createDocBtn').hide();
		$('#uploadDocBtn').show();
		$('#uploadSrcDocspn').show();
		$('#uploadDoc').show();
	}
}

	
	
	function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	}
	
	function DMSvalidate()
	{
		//debugger;
		var uploadFile = document.workspaceNodeAttrForm.uploadFile.value;
		//var remarkvalue = document.workspaceNodeAttrForm.remark.value;
    	//var hiddenFieldvalue = document.workspaceNodeAttrForm.secret.value;
    	//var deltefilevalue = $('input[name="deleteFile"]:checked').length
    	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
    	var index=uploadFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
    	var fileext = uploadFile.substring(index+1).toLowerCase();
    	var strChar;
    	if(uploadFile=="")
		{
				alert("Please Select Document");
				document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	     		document.workspaceNodeAttrForm.uploadFile.focus();
				return false;
		}
    	
    	if((fileext !="pdf" && fileext!="doc" && fileext!="docx") ){
			alert("Please upload valid extension(e.g. .pdf, .doc & .docx) Document.")
			return false;
		}
    	
     	for (i = 0; i < uploadFile.length; i++)
    	{
 			strChar = uploadFile.charAt(i);
		 	if (strInvalidChars.indexOf(strChar)!= -1)
				{
		 		alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
	      			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	      			return false;  			
 				}
	   	}
   /*  	
		if(remarkvalue=="" && hiddenFieldvalue!=0)
			{
			alert("Please specify Reason for Change..");
			document.workspaceNodeAttrForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.remark.focus();
			return false;
			} */
    		
    	
    	/* var elecSig='Yes';
		document.getElementById('reConfPass').value = '';
		if (elecSig=='Yes')
			{
			centerPopup();				
			loadPopup();
			return false;
			}	 */
			
		return true;
	}
	
	
	function DMSvalidateForSource()
	{
		debugger;
		var uploadFile = document.getElementById('uploadDoc').value;
		//var remarkvalue = document.workspaceNodeAttrForm.remark.value;
    	//var hiddenFieldvalue = document.workspaceNodeAttrForm.secret.value;
    	//var deltefilevalue = $('input[name="deleteFile"]:checked').length
    	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
    	var index=uploadFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
    	var fileext = uploadFile.substring(index+1).toLowerCase();
    	var strChar;
    	if(uploadFile=="")
		{
				alert("Please Select Document");
				document.getElementById('uploadDoc').style.backgroundColor="#FFE6F7"; 
				document.getElementById('uploadDoc').focus();
				return false;
		}
    	
    	if((fileext !="pdf" && fileext!="doc" && fileext!="docx") ){
			alert("Please upload valid extension(e.g. .pdf, .doc & .docx) Document.")
			return false;
		}
    	
     	for (i = 0; i < uploadFile.length; i++)
    	{
 			strChar = uploadFile.charAt(i);
		 	if (strInvalidChars.indexOf(strChar)!= -1)
				{
		 		alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
	      			document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
	      			return false;  			
 				}
	   	}
     	
   	return true;
	}
	
	function saveFile(){
		//debugger;
		
		//var nodeId = '<s:property value="recordId"/>';
		var nodeId = document.getElementById('recordId').value
		var wsId = '<s:property value="workspaceID"/>';
		var nodeHistory = '<s:property value="fullNodeHistory.size"/>';
		var docType = '<s:property value="docType"/>';
		var remark = document.getElementById("remark").value;
		if(nodeHistory>0){
			 remark = remark.trim();
			 if(remark=="" || remark==null){
				 alert("Please specify Reason for Change.");
				 document.getElementById("remark").style.backgroundColor="#FFE6F7"; 
				 document.getElementById("remark").focus();
				 return false;	 
			 }
			
		}
    	
    	var fd = new FormData();
        var file = $('#uploadFile')[0].files[0];
        fd.append('uploadFile', file);
        fd.append('recordId', nodeId);
        fd.append('workspaceID',wsId);
        fd.append('docType',docType);
        if(remark !="" || remark != null){
        	fd.append('remark',remark);
        }
        if(DMSvalidate()){
    		//debugger;
        $.ajax({
            url: 'SaveFileForDocSign_ex.do',
            data: fd,
            type: "POST",
            contentType: false,
            processData: false,
            success: function(data) {
              //alert(data);
              if(data=="true"){
            	  var out="uploadFileForDocSign.do?docId="+wsId+"&recordId="+nodeId+"&docType="+docType;
          		  location.href=out;
              }
              else{
            	  alert(data);
              }
            },	
			  error: function(data) 
			  {
				 alert("Something went wrong while fetching data from server.");
			  },
				async: false				
			});
    	}
	}
	function fileOpen(actionName)
	{
		//debugger;
		win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
	}
	function docFileOpen(actionName)
	{
		window.open(actionName, '_newtab');
	}
	function SaveandSendReview(){//var nodeId = '<s:property value="recordId"/>';
		debugger;
		var nodeId = document.getElementById('recordId').value;
		var wsId = '<s:property value="workspaceID"/>';		
		var nodeHistory = '<s:property value="fullNodeHistory.size"/>';
		var docType = '<s:property value="docType"/>';
		var remark = document.getElementById("remark").value;
		var manualSignatureConfig = '<s:property value="ManualSignatureConfig"/>';
		var applicationUrl='<s:property value="ApplicationUrl"/>';
		
		
		if(nodeHistory>0){
			 remark = remark.trim();
			 if(remark=="" || remark==null){
				 alert("Please specify Reason for Change.");
				 document.getElementById("remark").style.backgroundColor="#FFE6F7"; 
				 document.getElementById("remark").focus();
				 return false;	 
			 }
		}
		
		
		
    	var fd = new FormData();
        var file = $('#uploadFile')[0].files[0];
        fd.append('uploadFile', file);
        fd.append('recordId', nodeId);
        fd.append('workspaceID',wsId);
        fd.append('docType',docType);
        if(remark !="" || remark != null){
        	fd.append('remark',remark);
        }
        if(DMSvalidate()){
    		//debugger;
        $.ajax({
            url: 'SaveFileForDocSign_ex.do',
            data: fd,
            type: "POST",
            contentType: false,
            processData: false,
            success: function(data) {
              //alert(data);
              if(data=="true"){
            	  chgStatus1('sendReview');
              }
              else{
            	  alert(data);
              }
            },	
			  error: function(data) 
			  {
				  alert("Something went wrong while fetching data from server.");
			  },
				async: false				
			});
    	}
        /* if(manualSignatureConfig=="Yes"){
			var signatureFlag = "<s:property value='signatureFlag'/>";
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			if(remark==undefined)
				remark="";
			 var win = window.open(applicationUrl+"ManualSignature.do?workSpaceId="+'<s:property value="workspaceID"/>'+"&nodeId="+nodeId+"&deviationRemark="+remark, "_blank");
			var timer = setInterval(function() {
		        if (win.closed) {
		            clearInterval(timer);
		            //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
		            location.reload();
		        }
		    }, 500);  
			
		} */
	}
	
	function chgStatus1(buttonName)
	{
		debugger;
		//var nodeId = '<s:property value="recordId"/>';
		var nodeId = document.getElementById('recordId').value
		var docType = '<s:property value="docType"/>';
		var wsId = '<s:property value="workspaceID"/>';
		var temp="true";
		var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
		var manualSignatureConfig = '<s:property value="ManualSignatureConfig"/>';
		var applicationUrl='<s:property value="ApplicationUrl"/>';
		var tranNo =<s:property value="TranNo"/>;
		tranNo=tranNo+1;
		
		if(checkFileOpenSignOff=="Yes"){
		 $.ajax({		
			  url: "checkfileopenforsign_ex.do?nodeId="+'<s:property value="recordId"/>'+"&workspaceID="+wsId,
			  success: function(data) 
			  {
				 //debugger;
				 temp = data;
				 if(data=="false"){
				  	alert("Please review document before sign off.");
				  	var out="uploadFileForDocSign.do?docId="+wsId+"&recordId="+nodeId+"&docType="+docType;
	          		location.href=out; 
				  	return false;				  	
				 }
			  },
			  error: function(data) 
			  {
				alert("Something went wrong while fetching data from server.");
			  },
				async: false
			});
		}
			if(temp !="false"){
				
				if(manualSignatureConfig=="Yes"){
					var signatureFlag = "<s:property value='signatureFlag'/>";
					if(signatureFlag=="false"){
						alert("Please create signature before proceeding sign off.");
						return false;
					}
					//debugger;
					var win = window.open(applicationUrl+"ManualSignature.do?workSpaceId="+'<s:property value="workspaceID"/>'+
							"&nodeId="+nodeId+"&tranNo="+tranNo, "_blank");
					var timer = setInterval(function() {
				        if (win.closed) {
				            clearInterval(timer);
				            //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
				            location.reload();
				        }
				    }, 500);
					
					return false;
				}else{
					var userName = "<s:property value='#session.username'/>";
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
					if(buttonName=='sendReview'){
						isVerifyReview=true;
						document.getElementById("Verify").style.display='none';
						document.getElementById("VerifyCertified").style.display='none';
						document.getElementById("VerifyVoideFile").style.display='none';
						document.getElementById("VerifyReview").style.display='block';
						document.getElementById('stage').innerHTML = "Send For Review";
						
						if(signatureFlag=="false"){
							alert("Please create signature before proceeding sign off.");
							return false;
						}
						
					}
					if(buttonName=='Change'){
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
						
					}
					centerPopup();				
					loadPopup();
					return false;
					
				}
				

		}
	} 
	
	
	function sendForReview()
	{
		//alert("test");
		debugger;		
		//var nodeId = '<s:property value="recordId"/>';
		var nodeId = document.getElementById('recordId').value
		var wsId = '<s:property value="workspaceID"/>';
		var userCode ="<s:property value='#session.userid'/>";
		var docType ='<s:property value="docType"/>';
		
		var sessPass="<s:property value='#session.password'/>";
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		
		var pass=document.getElementById('reConfPass').value;
		
		if (!pass || pass == '' )
		{						
			alert("Please Enter Password !!!");
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
		
		$.ajax({		
			  url: "sendDocForReview_ex.do?workspaceID="+wsId+"&nodeId="+nodeId+"&usercode="+userCode+"&eSign=Y",
			  beforeSend: function()
			  { 
				 // debugger;
				 
				 document.getElementById("popupContact").style.display='none';
				 document.getElementById("loadingpopup").style.display='block';
			  },
			  success: function(data) 
			  {
				  //debugger;
				  document.getElementById("loadingpopup").style.display='none';
				  var out="ChangeRequest.do?docId="+wsId+"&docType="+docType;
					location.href=out;
			  },
			  error: function(data) 
			  {
				  //debugger;
				  alert("Something went wrong while fetching data from server.");
			  },
				async: false				
			});
		}
		
	}
	
	function cls()
	{
		document.getElementById('reConfPass').value = '';
		disablePopup();
	}
	
	
	 function CreateBlankFileToSharePoint(path)
	 {
	 	//alert('test');
	 	//debugger;
	 	
	 	var workspaceID =  '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		var DocURL = '<s:property value="DocURLForOffice365"/>';
		//var nodeId=document.workspaceNodeAttrForm.elements['recordId'].value;
		var nodeId = document.getElementById('recordId').value
		var singleDocFlag="true";
		$.ajax({		
			  //url: "UploadFileInSharepoint_ex.do?path="+path,
			  url: "checkReviewFile_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&path="+path+"&userCodeForOffice="+userCode,
			  beforeSend: function()
			  { 
				 // debugger;
				 $('#reviewSPDoc').hide();
				 //$('#uploading').show();
			  },
			  success: function(data) 
			  {
				 // debugger;
				  if(data=='false'){
	 			$.ajax({		
				  //url: "UploadFileInSharepoint_ex.do?path="+path,
				  url: "CreateBlankFileInSharePoint_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&userCodeForOffice="+userCode+"&singleDocFlag="+singleDocFlag,
				  beforeSend: function()
				  { 
					 // debugger;
					 $('#createDocBtn').hide();
					//$('#createUploading').css('background-color', '#E3EAF0');
					$('#createUploading').show();
			 	 },
			 	 success: function(data) 
			 	 {
				 //debugger;
				 if(data=="false"){
					 alert("File is stil open in the browser.. Please close it and Upload again.");
						$('#createUploading').hide();
						 $('#createDocBtn').show();
				 }
				 else if(data.includes("File Not Found")){
					 var str = data.split(",")[1];
					 alert("Document not found in below path. \n"+ str);
						$('#createUploading').hide();
						 $('#createDocBtn').show();
					 
				 }else{
				 //alert("File Created Successfully..");
				 $('#createUploading').hide();
				 $('#createDocBtn').show();
				 var str = data.split("/");
				 var fileToUpload = str[str.length - 1];
				// window.open('https://sarjensys.sharepoint.com/sites/eCSVTest/Shared%20Documents/'+fileToUpload+'?WEB=1', '_newtab');
				 var win = window.open(DocURL+fileToUpload+'?WEB=1', fileToUpload);
					    var timer = setInterval(function() {
					        if (win.closed) {
					            clearInterval(timer);
					            //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
					            window.location.reload();
					        }
					    }, 500);
				 
				 }
				
			  },
			  error: function(data) 
			  {
				  //debugger;
				  $('#createUploading').hide();
				  alert("Something went wrong while fetching data from server.");
			  },
				//async: false				
			});
			}else{
					  var arr=data.split(":")
					  $('#uploading').hide();
					  alert("Document is open by "+arr[1]+", Please close and try again.");
				  }
				  
			  },
			  error: function(data) 
			  {
				  //debugger;
				  alert("Something went wrong while fetching data from server...");
				  $('#createUploading').hide();
			  },
				//async: false				
			});
	 }
	 
	 
	 
	function UploadFileToSharePoint(path)
	 {
		debugger;
	 	
	 	var workspaceID =  '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		//var nodeId=document.workspaceNodeAttrForm.elements['recordId'].value;
		var nodeId = document.getElementById('recordId').value
		var DocURL = '<s:property value="DocURLForOffice365"/>';
	 	
		
		$.ajax({		
			  //url: "UploadFileInSharepoint_ex.do?path="+path,
			  url: "checkReviewFile_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&path="+path+"&userCodeForOffice="+userCode,
			  beforeSend: function()
			  { 
				 // debugger;
				 $('#reviewSPDoc').hide();
				$('#uploading').show();
			  },
			  success: function(data) 
			  {
				  //debugger;
				  if(data=='false'){
					  $.ajax({		
						  //url: "UploadFileInSharepoint_ex.do?path="+path,
						  url: "UploadFileInSharepoint_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&path="+path+"&userCodeForOffice="+userCode,
						  beforeSend: function()
						  { 
							 // debugger;
							 $('#reviewSPDoc').hide();
							//$('#uploading').css('background-color', '#E3EAF0');
							$('#uploading').show();
						  },
						  success: function(data) 
						  {
							  //debugger;
							  if(data=="true"){
									 $('#uploading').hide();
									 $('#reviewSPDoc').show();
									 var str = path.split("/");
									 var fileToUpload = str[str.length - 1];
									 //window.open('https://sarjensys.sharepoint.com/sites/eCSVTest/Shared%20Documents/'+fileToUpload+'?WEB=1', '_newtab');
									 //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
									 var win = window.open(DocURL+fileToUpload+'?WEB=1', fileToUpload);
									    //var timer = setInterval(function() {
									      //  if (win.closed) {
									        //    clearInterval(timer);
									            //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
									            window.location.reload();
									        //}
									    //}, 500);
									}
									 else{
										alert("Document is stil open in the browser. Please close it and upload again.");
										$('#uploading').hide();
										 $('#reviewSPDoc').show();
									 }
							  },
							  error: function(data) 
							  {
								  //debugger;
								  alert("Something went wrong while fetching data from server.");
								  $('#reviewSPDoc').hide();
							  },
								//async: false				
						});
				  }else{
					  var arr=data.split(":")
					  $('#uploading').hide();
					  alert("Document is open by "+arr[1]+", Please close and try again.");
				  }
				  
			  },
			  error: function(data) 
			  {
				  //debugger;
				  alert("Something went wrong while fetching data from server...");
			  },
				//async: false				
			});
		
	 }
	 
	 
	function DownloadFileFromSharePoint(path)
	{
		//alert('test');
		//debugger;
		
		var workspaceID =  '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		//var nodeId=document.workspaceNodeAttrForm.elements['recordId'].value;
		var nodeId = document.getElementById('recordId').value;
		
		
		//alert("Please make sure to file is closed before save & countinue.");
		
		if (confirm('Please make sure to close the file before save and continue.')) {
			$.ajax({		
				  //url: "UploadFileInSharepoint_ex.do?path="+path,
				  url: "DownloadFileFromSharePoint_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&path="+path,
				  beforeSend: function()
				  { 
					 // debugger;
					 $('#SaveNContinue').hide();
					 $('#comment').hide();
					//$('#uploading').css('background-color', '#E3EAF0');
					$('#uploading').show();
				  },
				  success: function(data) 
				  {
					  //debugger;
					 alert(data);
					 $('#uploading').hide();
					 $('#SaveNContinue').show();
					 //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
					 window.location.reload();
					 /* var str = path.split("/");
					 var fileToUpload = str[str.length - 1];
					 window.open('https://sarjensys.sharepoint.com/sites/eCSVTest/Shared%20Documents/'+fileToUpload+'?WEB=1', '_newtab'); */
				  },
				  error: function(data) 
				  {
					  //debugger;
					  $('#uploading').hide();
					  alert("Something went wrong while fetching data from server.");
				  },
				//async: false				
				});
	  
			}
	}

	function CommentFileToSharePoint(path)
	{
		//alert('test');
		//debugger;
		
		var workspaceID =  '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		//var nodeId=document.workspaceNodeAttrForm.elements['recordId'].value;
		var nodeId = document.getElementById('recordId').value
		var DocURL = '<s:property value="DocURLForOffice365"/>';
		 var str = path.split("/");
		 var fileToUpload = str[str.length - 1];
		 //window.open('https://sarjensys.sharepoint.com/sites/eCSVTest/Shared%20Documents/'+fileToUpload+'?WEB=1', '_newtab');
		 //parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
		 var win = window.open(DocURL+fileToUpload+'?WEB=1', fileToUpload);

	  
		}


	function uploadSourceDocument()
	{
		//alert('test');
		debugger;
		
		var workspaceID =  '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		//var nodeId=document.workspaceNodeAttrForm.elements['recordId'].value;
		var nodeId = document.getElementById('recordId').value;
		//var nodeFolderName=document.workspaceNodeAttrForm.elements['nodeFolderName'].value;
		var nodeFolderName=document.getElementById('nodeFolderName').value;
		//var nodeFolderName='<s:property value="nodeDocHistory.get(0).getDocName()"/>';
		var srcDocPath='<s:property value="srcDocPath"/>';
		var docPath='<s:property value="nodeDocHistory.get(0).getDocPath()"/>';
		var getDocContentType='<s:property value="nodeDocHistory.get(0).getDocContentType()"/>';
		var lbl_nodeName='<s:property value="lbl_nodeName"/>';
		var docName;
		var singleDocFlag="true";
		if(getDocContentType!="")
			docName='<s:property value="nodeDocHistory.get(0).getDocName()"/>';
		else
			docName='${fileNameToShow}' ;
		var uploadFile=srcDocPath+docPath+"/"+docName;
		
		$.ajax({		
			  //url: "UploadFileInSharepoint_ex.do?path="+path,
			  url: "checkReviewFile_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&userCodeForOffice="+userCode,
			  beforeSend: function()
			  { 
				 // debugger;
				  $('#uploadingSourceDoc').show();
			  },
			  success: function(data) 
			  {
				  //debugger;
				  if(data=='false'){
		$.ajax({		
			url: "UploadSourceDocument_ex.do?workspaceID="+workspaceID+"&nodeId="+nodeId+"&nodeFolderName="+nodeFolderName+"&userCode="+userCode+
					"&uploadFileFileName="+docName+"&fileToUpload="+uploadFile+"&singleDocFlag="+singleDocFlag,
			  beforeSend: function()
			  { 
				 // debugger;
				  $('#uploadingSourceDoc').show();
			  },
			  success: function(data) 
			  {
				  //debugger;
				  //alert(data);
				  if(data=="false"){
					  $('#uploadingSourceDoc').hide();
					  alert("Document is already locked by user, please unlock the same and try again.");
				  }else{
					  alert("Document uploaded successfully.");
						 $('#uploadingSourceDoc').hide();
						 window.location = window.location.href;
						 
						 var temp = parent.document.getElementsByTagName("iframe");
						 var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;
						 
						 var treeiframe = parent.document.getElementById("nodeFrame");
				         treeiframe.src = treeiframe.src;
				  }
			  },
			  error: function(data) 
			  {
				  //debugger;
				  $('#uploadingSourceDoc').hide();
				  alert("Something went wrong while fetching data from server.");
			  },
				async: false				
			});
		}else{
			  var arr=data.split(":")
			 $('#uploadingSourceDoc').hide();
			  alert("Document is open by "+arr[1]+", Please close and try again.");
		  }
		}
			  });
	}

	
	function uploadSourceDoc()
	{
		//alert("test");
		//debugger;		
		//var nodeId = '<s:property value="recordId"/>';
		var nodeId = document.getElementById('recordId').value;
		var wsId = '<s:property value="workspaceID"/>';
		//var nodeHistory = '<s:property value="fullNodeHistory.size"/>';
		var docType = '<s:property value="docType"/>';
		//var remark = document.getElementById("remark").value;
		var fd = new FormData();
        var file = $('#uploadDoc')[0].files[0];
        fd.append('uploadDoc', file);
        fd.append('nodeId', nodeId);
        fd.append('workspaceID',wsId);
        fd.append('docType',docType);
        if(DMSvalidateForSource()){
    		//debugger;
        $.ajax({
            url: 'uploadSrcDocForSingleDocument_ex.do',
            data: fd,
            type: "POST",
            contentType: false,
            processData: false,
            beforeSend: function()
			  { 
				 // debugger;
				$('#uploadDocBtn').hide();
				$('#createUploading').show();
			  },
            success: function(data) {
              //alert(data);
              if(data=="true"){
            	  var out="uploadFileForDocSign.do?docId="+wsId+"&recordId="+nodeId+"&docType="+docType;
          		  location.href=out;
              }
              else{
            	  alert(data);
              }
            },	
			  error: function(data) 
			  {
				 alert("Something went wrong while fetching data from server.");
			  },
				async: false				
			});
    	}
	}
	
	
	
</script>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float:left">Upload Document</b></div>

<br>
<div align ="center">



<s:form name="workspaceNodeAttrForm" method="post" enctype="multipart/form-data" >
<div id="imp" style="float: right; margin-right: 10px; margin-top: -10px;">
	Fields with (<span style="color: red;" >*</span>) are mandatory.
</div><s:if test="isCreate==true && #session.usertypename=='WU' && ((stageId==10 && sendForReviewFlag==true) || stageId==0) ">
		<table width="100%" align="center">
		<tr id="fileTr">
			<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
				<span class="title">New Document</span>
				<span style="font-size:20px;color:red">*</span> </td>
				<td align="left"><input type="file" id="uploadFile"></td>
		</tr>
		<tr>
			<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
				<span class="title">Reason for Change</span></td>
				<td align="left"><textarea rows="3" cols="30" id = "remark" name="remark"></textarea></td>
		</tr>
		</table>
		</s:if>
		<br>
	<input type="button" value="Save Document" id="savesendbtn"
					class="button" onclick="return saveFile();" />&nbsp;
	<input type="button" value="Send for Review" id="saveandsendReview"class="button" onclick="SaveandSendReview();">&nbsp;
	<s:if test ="stageId == 10 && sendForReviewFlag==true && userTypeName =='WU'">
	<input type="button" class="button" id="sendReview" value="Send for Review" onclick="chgStatus1(this.id);">&nbsp;
	</s:if>
	<input type="button" value="Close" class="button" onclick="window.location='ChangeRequest.do?docId=<s:property value="workspaceID"/>&docType=<s:property value="docType"/>';">
	
</s:form>


<s:hidden name="recordId"></s:hidden>
	<s:hidden name="nodeFolderName"></s:hidden>
<div id="loadingpopup" style="width: 50px;z-index: 1;position: absolute;top: 180.5px;left: 392.5px;
			padding-top: 0px;display:none">
   <center><img style="margin-top: 27px; margin-left: 255px;" src="images/loading.gif" alt="loading ..." /></center>
		</div>

<div id="popupContact" class="popcuswidth" style="height: 435px; top: 39.997px;">
<img alt="Close" title="Close" src="images/Common/Close.svg" height="25px" width="25px";
			onclick='cls();' class='popupCloseButton' />
		<h3 style="margin-top: 0px;">Electronic Signature</h3>
		<!-- <div style="width: 100%; height: 90px; overflow: auto;"> -->
		<table id="tble" style="width: 100%" >
		<tr><td class="popUpTdHdr"><b>Username: &nbsp;</b></td>
		<td class="popUpTd"><span id="userName"></span></td></tr>
		<tr><td class="popUpLbl"><b>Date: &nbsp;</b></td>
		<td class="popUpTd"><span id="date"></span></td></tr>
		<tr><td class="popUpLbl"><b>Action: &nbsp;</b></td>
		<td class="popUpTd"><span id="stage"></span></td></tr>		
		<tr><td class="popUpLbl"><b>Password: &nbsp;</b></td>
		<td class="popUpTd">
		<input type="text" name="reConfPass"id="reConfPass" autocomplete="off"></input>
		<!-- <input type="password" name="reConfPass"id="reConfPass"></input> -->
		
		</td>
		</tr>
		</table>
		<hr style="margin-top: 10px; margin-bottom: 10px;"><h3 style="margin-top: 2px;">Preview Signature</h3>
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
							
							<s:if test="signImg !=''">	
							<img src="" id="PreviewImg" height="90px" width="200px" name="PreviewImg" alt="Picture">
							</s:if>
							<s:else>
							<label style="height: 90px;width: 200px; text-align: center; padding-top: 20px; font-size: 24px;
									font-family: <s:property value="signStyle"/>; color: black;">
							<span><s:property value ="userNameForPreview"/></span></label>
							</s:else>
							</td>
						</tr>
						</table>
					</td>
					
					<td>
					<table id="tble" style="width: 100%;align:left;">
						
						<tr>
							<td style="color: #000000; width: 55px; padding: 0 !important; text-align: left; vertical-align: top;"><b>Name:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important;  vertical-align: top;"><s:property value ="userNameForPreview"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important;padding: 0; text-align: left; vertical-align: top;"><b>Role:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important; vertical-align: top;"><s:property value ="roleName"/></td>
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
		
			<span style="color: #000000; font-size: 14px;">
				<b>I hereby confirm signing of this document electronically as an
				<s:if test="useRightStage==10">Creator.</s:if>
				<s:if test="useRightStage==20">Reviewer.</s:if>
				<s:if test="useRightStage==100">Approver.</s:if></b>
			</span>
		
		   <%-- <span style="color:#000000"><b>I hereby confirm signing of this document electronically.</b></span> --%><br><br>
			<s:if test="lockSeqFlag == true ">
				<input type="button" class="button"
				value="Change" name="buttonName" onclick="lockSeq();"></input>
			</s:if>
			<s:else>
				<s:if test="#session.usertypename != 'Inspector' ">
				<input type="submit" class="button"
				value="Sign" id="VerifyCertified" name="buttonName" onclick="return chgStatus('<s:property value="nodeId"/>');" style="display:none"></input>
				<input type="button" class="button"
				value="Sign" id="VerifyVoideFile" name="buttonName" onclick="return test1();" style="display:none"></input> 
				<input type="button" class="button"
				value="Sign" id="VerifyReview" name="VerifyReview" onclick="sendForReview();" style="display:none"></input>
				<input type="submit" class="button"
				value="Sign" id="Verify" name="buttonName" onclick="return verifyPass();"style="display:none"></input>
				</s:if>
			</s:else>
		</p>
		<!-- </div> -->
		</div>
		<div id="backgroundPopup"></div>
</div>
<br><br>
<s:if test ="fullNodeHistory.size==0">
<span style="font-size:16px;">No details available.</span>
</s:if>
<s:else>
<div style="overflow-y: auto;max-height: 197px;">
<table class="datatable paddingtable" width="98%" align="center">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Document</th>
			<th style="border: 1px solid black;">Size</th>
			<th style="border: 1px solid black;">Status</th>
			<th style="border: 1px solid black;">Sent for</th>
			<th style="border: 1px solid black;">Modified by</th>
			<th style="border: 1px solid black;">User Role</th>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>
			<th style="border: 1px solid black;">Reason for Change</th>
		<%-- <th>Certified</th> 
		<th>Voided By</th>
		<th>Voided On</th>
	<s:if test ="#session.countryCode != 'IND'">
		<th>Eastern Standard Time</th>
	</s:if> --%>
		</tr>
	</thead>
	<tbody>
	
		<s:iterator value="fullNodeHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td style="border: 1px solid black;"><s:property value="#status.count" /></td>
				<td style="border: 1px solid black;"><s:if test="fileName == ''">
									&nbsp;
								</s:if> <s:if test="fileName == 'No File'">
					<s:property value="fileName" />
				</s:if> <s:else>
					 <s:if test="OpenFileAndSignOff=='Yes'">
					 <s:if test="fileExt=='PDF' || fileExt=='pdf'">
					 	<a href="javascript:void(0);"
						onclick="return fileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
					<s:property value="fileName" /> </a>
					 </s:if>
					 <s:else>
					 	<a href="javascript:void(0);"
						onclick="return docFileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
					<s:property value="fileName" /> </a>
					 </s:else>
					 </s:if>
					 <s:else>
					  <s:if test="fileExt=='PDF' || fileExt=='pdf'">
						<a href="javascript:void(0);"
							onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
							<s:property value="fileName" /> </a>
					</s:if>
					<s:else>
						<a href="javascript:void(0);"
							onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
							<s:property value="fileName" /> </a>
					</s:else>
					</s:else>
					</s:else>
				</td>
				<td style="border: 1px solid black;"><s:if test="historyDocumentSize.sizeMBytes>0">
					<s:property value="historyDocumentSize.sizeMBytes" default="--" /> MB
								</s:if> <s:else>
					<s:property value="historyDocumentSize.sizeKBytes" default="--" /> KB
								</s:else></td>
				<td style="border: 1px solid black;"><s:if test ="stageId==10 && FileType !='SR'">Uploaded</s:if>
					<s:else>
						<s:if test="stageDesc == ''">&nbsp;</s:if> 
						<s:property value="stageDesc" default=" " />
					</s:else>
				</td>
					
				<td style="border: 1px solid black;">
					<s:if test="stageId==10 && FileType ==''">-</s:if>
					<s:elseif test ="stageId==10 && FileType =='SR'">Review</s:elseif>
					<s:elseif test ="stageId==20">Approve</s:elseif>
					<s:elseif test ="stageId==0">Create</s:elseif>
					<s:else>-</s:else> 
				</td>
				<td style="border: 1px solid black;">
					<s:if test="userName == ''">&nbsp;</s:if> 
					<s:property value="userName" />
				</td>
				<td style="border: 1px solid black;">
					<s:if test="roleName == ''">&nbsp;</s:if> 
					<s:property value="roleName" />
				</td>
				<td style="border: 1px solid black;"><s:if test="modifyOn == ''">&nbsp;
					</s:if> 
					<s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
			</s:if>
				<td style="border: 1px solid black;"><s:if test="remark == ''">
									&nbsp;
					</s:if> 
					<s:else>
						<s:property value="remark" />
				</s:else></td>
			<%--  <td>
				<s:property value="defaultFileFormat"/>
			</td> 
			<td><s:property value="voidBy" default="-"/></td>
			<td><s:property value="voidISTDateTime" default="-"/></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="voidESTDateTime" default="-"/></td>
			</s:if> --%>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
</s:else>
<br>
<%-- <s:if test="isCreate!=true || userTypeName !='WU' ">
<input type="button" value="Close" class="button" onclick="window.location='ChangeRequest.do?docId=<s:property value="workspaceID"/>&docType=<s:property value="docType"/>';">
</s:if>
 --%>
<s:if test='userTypeName =="WU" && appType != "0003"'>
 <s:tabbedPanel id="panel_1" cssStyle="padding: 5px;margin-top:20px;width: 99.4%;">
			   <s:if test="isCreate!=true && nodeDocHistory.size()<=0">
			   	<center style="margin-top: 10px;"><span> No details available.</span></center>
				</s:if>
				
				<s:if test="srcDocPath != 'No_Path_Found'">
				
					<s:div id="sourceDoc" label="Source Document" theme="ajax"
						labelposition="top" cssStyle="max-height: 240px;overflow-y: auto;/* height: 242px; */margin-left: 5px;padding-bottom: 10px;">
						
						<s:if test="canEditFlag == 'Y'">
						<%-- <s:property value="confirmAndUpload"/> --%>
						<%-- <s:if test="isCreate==true || wsNodeHistory.size==0">
						<s:if test="wsNodeHistory.get(0).getStageId()==0 || wsNodeHistory.size<=0"> --%>
						
						<s:if test="isCreate==true || wsNodeHistory.size==0">
						
						<div  id="optionID" style="margin-left: -938px; margin-top: 23px;">
							<b class="title">Select Option : </b>
							&nbsp;&nbsp;&nbsp;&nbsp;
								<select id="srcDocOption" name="srcDocOption" headerKey="-1" headerValue="Select Option" onchange="changeBtnName()">
									<option value="-1">Please Select</option>
  									<option value="createSourceDocument">Create</option>
  									<option value="uploadSourceDocument">Upload</option>
								</select>
						</div>
						
							<div id="createOrUpload" style="padding: 10px;display: flex;">
							<span id="uploadSrcDocspn" class="title">Upload Source Document:</span> 
							
								&nbsp;<s:file name="uploadDoc" required="true" id="uploadDoc"></s:file>&nbsp; 
								<s:submit value="Upload" cssClass="button" id="uploadDocBtn" name="buttonName" onclick="return uploadSourceDoc();"></s:submit> 
								<!-- <input type="submit" value="Upload" class="button" id="uploadDocBtn" name="buttonName" onclick="return DMSvalidate();"/> -->								
							
						<div id="createUploading" style="height:30px; width=:40px; float: right; left:600px; top:590px;">
						<img src="images/loading.gif" alt="loading ..." /></div>	
							<input type="button" class="button" value="Create" id="createDocBtn" name="buttonName" 
								onclick="CreateBlankFileToSharePoint();"/>
								</div>
								</s:if>
							<%-- </s:if>
						</s:if> --%>
						</s:if>
						<s:if test="nodeDocHistory.size()>0">
							<s:set name="srcDoc" value="nodeDocHistory.get(0)"></s:set>
							<div style="padding-top: 10px;">
							<table width="99%" class="datatable" style="border: none;">
								<thead>
									<tr>
										<th style="border: 1px solid;">Document</th>
										<th style="border: 1px solid;">Uploaded by</th>
										<th style="border: 1px solid;">Uploaded on</th>
										<s:if test ="#session.countryCode != 'IND'">
											<th style="border: 1px solid;">Eastern Standard Time</th>
										</s:if>
										<!-- <th>Edit</th> -->
										<!-- <th>Remark</th> -->
										<th style="border: 1px solid;">Modified by</th>
										<th style="border: 1px solid;">Modified on</th>
										<s:if test ="#session.countryCode != 'IND'">
											<th style="border: 1px solid;">Eastern Standard Time</th>
										</s:if>
										<th colspan="3"style="border: 1px solid;">Action</th>
									</tr>
								</thead>
								<tbody>

									
									<tr class="odd">
										<!-- <td><s:date name="#srcDoc.uploadedOn" format="dd-MMM-yyyy HH:mm"/></td> -->
										<td style="border: 1px solid;">
											<s:if test="#srcDoc.docContentType==null">
										<a href="openfile.do?fileWithPath=<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/
										${fileNameToShow}"
											target="_blank">${newFileName}</a>
										 </s:if>
										<s:else>
										<a href="openfile.do?fileWithPath=<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>"
											target="_blank">${newFileName}	</a>
										</s:else >
										</td>
										<td style="border: 1px solid;"><s:property value="#srcDoc.UploadedByUser" /></td>
										<td style="border: 1px solid;"><s:property value="#srcDoc.ISTDateTime" /></td> 
									<s:if test ="#session.countryCode != 'IND'">
										<td style="border: 1px solid;"><s:property value="#srcDoc.ESTDateTime" /></td>
									</s:if>
										<%-- <td><s:if test="canEditFlag == 'Y'">
											<s:a
												href="editSrcDoc.do?workspaceId=%{#srcDoc.workspaceId}
													&nodeId=%{#srcDoc.nodeId}
													&fullDocPath=%{srcDocPath}%{#srcDoc.docPath}/%{#srcDoc.docName}
													&nodeDisplayName=%{workSpaceNodeDtl.nodeDisplayName}">
											Edit
										</s:a>
										</s:if> <s:else>
										-
									</s:else></td> --%>
									<td style="border: 1px solid;"><s:property value="#srcDoc.modifyByUser" /></td>
										<td style="border: 1px solid;"><s:property value="#srcDoc.ISTDateTime" /></td> 
									<s:if test ="#session.countryCode != 'IND'">
										<td style="border: 1px solid;"><s:property value="#srcDoc.ESTDateTime" /></td>
									</s:if>
									<td style="border: 1px solid;" id="rsButton">
										<div height=30px; width=40px; id="uploading" style="float: left;"><img src="images/loading.gif" alt="loading ..." /></div>
										<s:if test="#srcDoc.docContentType==null">
										<input type="button" id="reviewSPDoc" style="font-size: 12px;" value="Edit" class="button" 
										onclick="UploadFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}');" >
										</s:if>
										<s:else>
											<input type="button" id="reviewSPDoc" style="font-size: 12px;" value="Edit" class="button" 
										onclick="UploadFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');" >
										</s:else>
										
									<s:if test="#srcDoc.docContentType==null">
										<input type="button" id="SaveNContinue" style="font-size: 12px;" value="Save & Continue" class="button" 
										onclick="DownloadFileFromSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}');" >
									</s:if>
									<s:else>
									<input type="button" id="SaveNContinue" style="font-size: 12px;" value="Save & Continue" class="button" 
										onclick="DownloadFileFromSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');" >
									</s:else>
									</td>
									
									<td style="border: 1px solid;" id="rsButton">
									<s:if test="IsComment==true">
									
									<div id="Spdownloading" style="float: left; height:30px; width:40px;"><img src="images/loading.gif" alt="loading ..." /></div>
									<br/>
									<s:if test="#srcDoc.docContentType==null">
										<input type="button" id="comment" style="font-size: 12px;" value="View" class="button" 
										onclick="CommentFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}');" >
										<br/>
									</s:if>
									<s:else>
									<input type="button" id="comment" style="font-size: 12px;" value="View" class="button"  
										onclick="CommentFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');" >
										<br/>
									</s:else>
										<br/>
									</s:if>		
								<s:else><span style="padding-right: 10px;">-</span></s:else>
									</td>
									<td style="border: 1px solid;" id="validateButton">

									<s:if test="PQPreApprovalPopup=='Yes' && isRequiredValidate == true && IsUpload==true" >
									<div id="validateUploading" style=" height:30px; width:40px; float: left;"><img src="images/loading.gif" alt="loading ..." /></div>
									<s:if test="#srcDoc.docContentType==null">
									<input type="button" id="SrcValidateButtonId" style="font-size: 12px;" value="Extract" class="button" 
										onclick="openPreApprovalPopupFromSrc('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}')" >
									</s:if>
									<s:else>
									<input type="button" id="SrcValidateButtonId" style="font-size: 12px;" value="Extract" class="button" 
										onclick="openPreApprovalPopupFromSrc('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>')" >
									</s:else>
									</s:if>
										<s:else><span style="padding-right: 10px;">-</span></s:else>
									</td>								
									</tr>
								</tbody>
							</table>

							</div>
						</s:if>
					</s:div>
					</s:if>
						</s:tabbedPanel> 
					<s:if test="wSOfficeHistory.size>0">
							<!-- <b class="title">&nbsp;&nbsp;Activities : </b> -->
							<div
								style="width: 99%; border: 1px solid #6290d2; padding-top: 10px; border-top: none; margin-left: 5px; max-height: 200px; overflow: auto;">
								<!-- <b class="title">&nbsp;&nbsp;Worked Users : </b> -->
								<table width="99%" class="datatable audittrailtable"
									style="border: none; margin-left: 5px;" cellspacing="1">
									<thead>
										<tr>
											<th>Username</th>
											<th>Document</th>
											<th>Edited on</th>
											<s:if test="#session.countryCode != 'IND'">
												<th>Eastern Standard Time</th>
											</s:if>
											<th>Saved on</th>
											<s:if test="#session.countryCode != 'IND'">
												<th>Eastern Standard Time</th>
											</s:if>
											<!-- <th>Uploaded By</th> -->
										</tr>
									</thead>
									<s:iterator value="assignWorkspaceRightsDetails"
										id="assignWorkspaceRightsDetails" status="status">

										<tbody>
											<tr class="odd">
												<!-- <td><s:date name="#srcDoc.uploadedOn" format="dd-MMM-yyyy HH:mm"/></td> -->
												<td><s:property value="loginName" /></td>
												<td><s:if test="clsDownload=='Y' ">
														<a
															href="openfile.do?fileWithPath=<s:property value="filePath"/>/<s:property value="fileName"/>"
															target="_blank"> <%-- <s:property value="fileName" /> --%>
															${newFileName}
														</a>
													</s:if> <s:else>
										 ${newFileName}
										</s:else></td>
												<td><s:if test="clsUpload == 'Y' ">
														<s:property value="ISTUploadOn" default="-" />

													</s:if> <s:else>
												-
											</s:else> <%-- <s:property value="clsUpload" /> --%></td>
												<s:if test="#session.countryCode != 'IND'">
													<td><s:if test="clsUpload == 'Y' ">
															<s:property value="ESTUploadOn" default="-" />
														</s:if> <s:else>-</s:else></td>
												</s:if>
												<td><s:if test="clsDownload == 'Y' ">
														<s:property value="ISTDownloadOn" default="-" />
													</s:if> <s:else>
														<%-- 			<s:if test="clsDownload == 'N' ">
											<input type="button" style="font-size: 12px;" value="Save" class="button" 
										onclick="DownloadFileFromSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');" >
											</s:if>
											<s:else>
												-
											</s:else> --%>
											-
											</s:else> <%-- <s:property value="clsDownload" /> --%></td>
												<s:if test="#session.countryCode != 'IND'">
													<td><s:if test="clsDownload == 'Y' ">
															<s:property value="ESTDownloadOn" default="-" />
														</s:if> <s:else>
															<%-- 
													<s:if test="clsDownload == 'N' ">
											<input type="button" style="font-size: 12px;" value="Save" class="button" 
										onclick="DownloadFileFromSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');" >
											</s:if>
											<s:else>
												-
											</s:else> --%>
											-
											</s:else> <%-- <s:property value="clsDownload" /> --%></td>
												</s:if>
											</tr>

										</tbody>
									</s:iterator>

								</table>
								<!-- <input type="button" cssClass="button" value="Upload Source Document"/> -->
								
								<s:if
									test="(stageId == 10 && userWiseStageFlag==false) || stageId == 0 && userWiseStageFlag==true">
									<s:if
										test="isCreate==true && (wsNodeHistory.size>=0 && confirmAndUpload==true)">
										<div style="padding: 10px; display: flex;">
											<div id="uploadingSourceDoc"
												style="height: 30px; width: 40px; float: right; left: 600px; top: 590px;">
												<img src="images/loading.gif" alt="loading ..." />
											</div>
											<input type="button" class="button" value="Confirm to Upload"
												name="buttonName" onclick="uploadSourceDocument();"></input>
										</div>
									</s:if>
								</s:if>
							</div>
						</s:if>
						</s:if>
					
</div>
</div>
</div>
</div>
</body>
</html>