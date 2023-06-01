<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style>
#popupContact {
	top: 35px !important;
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
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgb(0 0 0/ 73%); /* Black w/ opacity */
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

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

a {
	color: #000 !important;
	text-decoration: none;
}

.datatable td a:hover {
	color: blue !important;
}

.fs-dropdown {
    width: 75% !important;
}

</style>

<s:head theme="ajax" />




<script type="text/javascript">
    	var popUpEnableOnStartup='No';
    	</script>
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script> --%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.1.js"></script>

<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/ui/themes/base/jquery.ui.all.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>

<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/css/fSelect.css"
	rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/fSelect.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>

<script type="text/javascript">
var isVerifyReview=false;
var isVerifyCertified=false;
var isVoidFile=false;
var deviationRemark;

	$(document).ready(function() {
		//debugger;
		var status='<s:property value="isNodeLocked"/>';
		var sciptCodeFlag='<s:property value="scriptFlag"/>';
		var ScriptCodeAutoGenrate = '<s:property value="isScriptCodeAutoGenrate"/>';
		if(sciptCodeFlag=='true' && ScriptCodeAutoGenrate=='Yes'){
			var scriptCodeId = document.getElementById("Script Code").id;
			document.getElementById(scriptCodeId).readOnly = true; 
		}
		
		var docRequired=document.getElementById('Type of Signature');
		if(docRequired != null){
			docRequired=docRequired.value;
		if(docRequired =='Digital'){
			document.getElementById('IDReason For Type of Signature').style.display='none';
			document.getElementById('Reason For Type of Signature').style.display='none';
		}
		}
		
		//Automate URS-FS code
		debugger;
		var workSpaceId = '<s:property value="workspaceID"/>';
			//var nodeId = ${workSpaceNodeDtl.nodeId };
			var nodeId='<s:property value="nodeId"/>'
			//var tranNo = ${tranNoForDtl };//tranNoForDtl
		/* $.ajax({			
						url: 'showURSFSInfo_ex.do?&workspaceID='+workSpaceId+'&nodeId='+nodeId,//+'&tranNoForDtl='+tranNo,
						beforeSend: function()
						{
			  			 opt = "No Users available";		
						 document.getElementById('showUserDtl').innerHTML = opt;
						},
						success: function(data) 
					  	{
						 var opt = "  ";			  		
					       if(data.length > 0)
						   {
					    	//debugger;
					    	var usersArr = data.split(',');
					    	 for(i=0; i<usersArr.length;i++)
						       {
					    		var keyValuePair = usersArr[i];
					    		var keyValuePairArr = keyValuePair.split('::');
					    		var key = keyValuePairArr[0];
					    		var value = keyValuePairArr[1];
					    		opt += "<option value='"+key+"'>"+value+"</option>";				    				
					    		}	
					    		opt = '<select name="URSInfo" class="commentUsers" multiple="multiple">'+
					    		opt +
				  				  '</select> &nbsp;';
					    		   document.getElementById('showUserDtl').innerHTML = opt;
					    		   
					    		   window.fs_test= $('.commentUsers').multiselect({
				    		            columns: 4,
				    		            placeholder: 'Select User',
				    		            search: true,
				    		            searchOptions: {
				    		                'default': 'Search User'
				    		            },
				    		            selectAll: true
				    					
				    		        });
					    			//window.fs_test=$j('.3col active').fSelect();
							    	
					    			//window.fs_test = $j('.commentUsers').fSelect();
							}	
					  	}
			});
		 */
		
		//code ends
		
		
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
		var right=${iscreatedRights};
		var CnF=${confirmAndUpload};
		var isDownload=${IsDownload};
		var isComment=${IsComment};
		var isValidate=${IsValidate};		
		debugger;
		var isLeaf = ${isLeafNode};
		if(right==true && isLeaf==1){
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
		var CnF=${confirmAndUpload};
		if(status=="true"){
			//refreshTree();
		}
				var options = 
				{
					success: showResponse
				};
				$("#saveFormButton").click(function(){
					if(saveandsendCommentsasRemarks())
					{
						$("#workspaceNodeAttrForm").ajaxsubmit(options);
						alert("Jaspreet");						
					}
					return false;			
				});
				
				$("#animate").click(function(){
					setTimeout(function(){
						//$("#commentsDiv2").fadeOut();
						//$("#commentParent").toggle("slide","",5000);
						//$("#commentParent").fadeIn("slow");
					}, 1000);
			    });
		
		
		var logoname = '<s:property value="signImg"/>';
		if(document.getElementById('PreviewImg')!=null)
			document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;
		//document.getElementById("myModalValidate").style.display='none';
		(function($) {
		    $(function() {
		        /*window.fs_test = $('.commentUsers').fSelect({
		            placeholder: 'Select User',
		            numDisplayed: 3,
		            overflowText: '{n} selected',
		            noResultsText: 'No results found',
		            searchText: 'Search',
		            showSearch: false
		        });*/
		    	$('.commentUsers').fSelect({
		    	    placeholder: 'Select value',
		    	    numDisplayed: 3,
		    	    overflowText: '{n} selected',
		    	    noResultsText: 'No results found',
		    	    searchText: 'Search',
		    	    showSearch: true, 
		    	});
		        
		        
		        $('.assignUsers').fSelect({
		    	    placeholder: 'Select value',
		    	    numDisplayed: 3,
		    	    overflowText: '{n} selected',
		    	    noResultsText: 'No data found',
		    	    searchText: 'Search',
		    	    showSearch: true,
		    	    title : 'test'
		    	    
		    	});
		        
		       
		        window.onclick = function(event) {
		          if (event.target == document.getElementById("myModal")) {
		        	  document.getElementById("myModal").style.display = "none";
		          }
		        }
		    });
		    
		})(jQuery);
		//$("#milestone option:selected").attr('disabled','disabled').siblings().removeAttr('disabled');
		$('#milestoneId').on('change', function(e) {
			$('#milestoneId').off('change');
});
		//$('#milestoneId').attr("disabled", "disabled");
		$("#reConfPass").focus();
		$("#reConfPass").on("keydown", function (e) {
		    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
		    	e.preventDefault();
		     if(isVerifyReview==true){
		    	 document.getElementById("VerifyReview").click();
		     }else if(isVerifyCertified==true){
		        document.getElementById("VerifyCertified").click();
		     }else if(isVoidFile==true){
		        document.getElementById("VerifyVoideFile").click();
		     }
		     else{
		        document.getElementById("Verify").click();
		     }
		    }
		});
		
		
		$(".attrValueIdDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		//debugger; 
		/* var logoname = '<s:property value="signPath"/>';
		var signatureFlag = "<s:property value='signatureFlag'/>";
		if(signatureFlag=="true"){
		document.getElementById('logoImg').src='ShowImage_b.do?logoFileName='+logoname;
		 var span = document.getElementById('uName');
		 span.style.fontFamily = '<s:property value="fontStyle"/>';
		} */
		
		//var status='<s:property value="fileUploadStatus"/>';
		//alert($("#selectedID").val());
		});
		
</script>

<script type="text/javascript">  
function test()
{
	alert(innerDoc.getElementById("1").innerHTML);
}
var temp = parent.document.getElementsByTagName("iframe");
var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;

	function refreshTree(){
          var temp = parent.document.getElementsByTagName("iframe");
    	  if(temp != null && typeof(temp)!='undefined')
    		{
    		for(var i=0;i<temp.length;i++){
    			if(temp[i].name=="nodeFrame"){
    				var innerDoc = temp[i].contentDocument || temp[i].contentWindow.document;
    				//alert(innerDoc.getElementById("1").innerHTML);
    				temp[i].src=temp[i].src;
    			}
    		}
    		}
    	}
    	
    	function HideContent(d) {
			if(d.length < 1) { return; }
			document.getElementById(d).style.display = "none";
		}
		
		function ShowContent(d) {
			if(d.length < 1) { return; }
			document.getElementById(d).style.display = "block";
		}
		function ReverseContentDisplay(d) {
			if(d.length < 1) { return; }
			if(document.getElementById(d).style.display == "none") { document.getElementById(d).style.display = "block"; }
			else { document.getElementById(d).style.display = "none"; }
		}
	    
	    function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none';
			else
				document.getElementById(str).style.display = 'inline';
		}
    	
    	function callDownloadFile()
		{
			var str = document.workspaceNodeAttrForm.drootPath.value;

			var workSpaceId = document.workspaceNodeAttrForm.workSpaceId.value;
			var nodeId = document.workspaceNodeAttrForm.nid.value;
			var tranNo = document.workspaceNodeAttrForm.tNo.value;
			var fileName = document.workspaceNodeAttrForm.fileName.value;
			var baseWorkFolder = document.workspaceNodeAttrForm.baseWorkFolder.value;
			var fileExt = document.workspaceNodeAttrForm.fileExt.value;
			
			if(str=="Not Found")
			{
				alert("Template Not Found.");
				return false;
			}	
			else
			{
				var rootPath ="file:\\\\" + str;
				var str1 = "downloadFile.do?workSpaceId="+workSpaceId+"&nodeId="+nodeId+"&tranNo="+tranNo+"&fileName="+fileName+"&baseWorkFolder="+baseWorkFolder+"&fileExt="+fileExt;
				window.open(str1,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=900,width=900,resizable=yes,titlebar=no")	;
				return true;
			}			
		}
		
    	function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}
		
		function saveandsendCommentsasRemarks()
		{
			var fTArea = document.workspaceNodeAttrForm.forumTextArea.value;
			var sUser = document.workspaceNodeAttrForm.userCodedtl.value;
			var fUpload = document.getElementById("uploadDocComment").value;
			fTArea = trim(fTArea);
			if(sUser == "null" || sUser == "")
				{
				alert('Please Select The User!');
				return false;
				}
			if ( (fTArea == "null" || fTArea == "" || fTArea == "Type Your Comments Here...") && (fUpload == "null" || fUpload == "") )
				{
				alert("Please enter comments in box or Upload the File");
				return false;				
				}

			if ( (fUpload != "null" && fUpload != "") || (fTArea != "null" && fTArea != "" && fTArea != "Type Your Comments Here..."))
				{
					if(fTArea == "Type Your Comments Here...")
					{
						document.workspaceNodeAttrForm.forumTextArea.value = "";
					}
					return true;				
				}
			else
				{
				alert("Please enter comments in box or Upload the File");
					return false;
				}
			return true;
		}
		
		function showComments()
		{
			if (document.workspaceNodeAttrForm.nodeId.value=="null")
			{
				alert("Please Select Node");
				return false;
			}else{
			    str="ShowAllComments.do?nodeId="+document.workspaceNodeAttrForm.nodeId.value;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=250,width=800,resizable=yes,titlebar=no");
				win3.moveTo((screen.availWidth/2)-(800/2),screen.availHeight/2-(250/2));
				return true;
			}		
		}

		function showSentComments()
		{
			if (document.workspaceNodeAttrForm.nodeId.value=="null")
			{
				alert("Please Select ");
				return false;
			}else{
			//button.nodeattr.showallcomments=ShowAll Comments in resource file
				var wsId = '<s:property value="workspaceID"/>';
				str="ShowSentComments_b.do?nodeId="+document.workspaceNodeAttrForm.nodeId.value+"&workspaceId="+wsId;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=250,width=800,resizable=yes,titlebar=no");
				win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(250/2));
				return true;
			}		
		}
		function ShowCommentHistory()
    	{
		
			if (document.workspaceNodeAttrForm.nodeId.value=="null")
			{
				alert("Please Select Node");
				return false;
			}
			else
				{
				var wsId = '<s:property value="workspaceID"/>';
				str="ShowCommentHistory_b.do?nodeId="+document.workspaceNodeAttrForm.nodeId.value+"&workspaceId="+wsId;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=250,width=800,resizable=yes,titlebar=no");
				win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(250/2));
				return true;
				}
    	}
	
		function ShowReplyComments(targetDiv,recUsrCode,commentParent,showWhat,dispType,nodeId)
		{
			
			var wsId = '<s:property value="workspaceID"/>';
			commentDispOption=10;
			var hideReply=false;
			var divstat = document.getElementById(commentParent);
			var divdisstyle = divstat.style.display;
			if(divdisstyle == 'none' && hideReply==false)
			{
				var commURL = "ShowComments_ex.do?dispType="+dispType+"&receiverUserCode=" + recUsrCode + "&showWhat=" + showWhat+"&nodeId="+nodeId+"&workspaceId="+wsId;			
				$.ajax(
				{			
					url: commURL,
			  		success: function(data) 
			  		{
			  			//alert("Hi1");
			  			$("#commentsDiv2").fadeIn();
			  			document.getElementById("commentParent").style.display = 'block';
			    		$(targetDiv).html(data);	
			    	}	  		
				});			
			}
			else
			{
				/* document.getElementById("commentParent").style.display = 'none';
				$("#commentsDiv2").fadeOut(); */
				hideReply=true;
				if(document.getElementById("commentParent").style.display == 'block')
					{
					$("#commentsDiv2").fadeOut();
					document.getElementById("commentParent").style.display = 'none';
					}
				else
					{
					$("#commentsDiv2").fadeIn();
					document.getElementById("commentParent").style.display = 'block';
					}
			}
			  
			return false;
		}
		 
		  function Animate()
		  {
			 // alert("Animate");
			  ShowReplyComments('#commentsDiv2','<s:property value="#session.userid"/>','commentParent',1,'3',${nodeId});
		  } 
		
		function getRecComments(pageNo,recUsrCode,targetDiv,selId)
		{
			//debugger;
			var sel=document.getElementById(selId);
			var noOfRecs=sel.options[sel.selectedIndex].value;
			var nodeId = '<s:property value="nodeId"/>';
			var wsId = '<s:property value="workspaceID"/>';
			var commURL;
			if(nodeId==null && wsId==null)
				 commURL = "GetRecComments_ex.do?receiverUserCode=" + recUsrCode + "&pageNo=" + pageNo + "&noOfRecords=" + noOfRecs;	
			 
			else
				commURL = "GetRecComments_ex.do?receiverUserCode=" + recUsrCode + "&pageNo=" + pageNo + "&noOfRecords=" + noOfRecs+"&nodeId="+nodeId+"&workspaceId="+wsId;
			
			
			//alert(commURL);
			$.ajax(
			{			
				url: commURL,
		  		success: function(data) 
		  		{
		    		$("#"+targetDiv).html(data);	    		
				}	  		
			});		
			return false;
		}
		function showComment(targetDiv,allCount,subId)
		{
			commentDispOption=10;
			var URL="MarkAsRead_ex.do?subjectId=" + subId;
			$.ajax(
			{			
				url: URL,
		  		success: function(data) 
		  		{
					for (var i=1;i<=allCount;i++)
					{
						if (i==targetDiv)
						{				
							document.getElementById('row'+i).style.display='';
							document.getElementById('row'+i).className='even';
							document.getElementById('rowShow'+i).style.display='';
							document.getElementById('rowReply'+i).style.display='none';
						}
						else
						{
							document.getElementById('row'+i).style.display='none';
							document.getElementById('rowShow'+i).style.display='none';						
						}
					}
				}	  		
			});		
			return false;		
		}
		function getStructure(wsId,ndId,tarDiv)
		{
			//debugger;
			var URL="GetNodePath_ex.do?workspaceId=" + wsId + "&nodeId=" + ndId;
			$.ajax(
			{			
				url: URL,
		  		success: function(data) 
		  		{
					$('#'+tarDiv).html(data);
		  		}
			});
		}
		function showStructure(tarDiv)
		{
			var tarDivStyle = document.getElementById(tarDiv).style.display;
			if(tarDivStyle == 'none')
			{
				$('#'+tarDiv).show('slow', function() {});
			}
			else
			{
				$('#'+tarDiv).hide('slow', function() {});
			}
		}
		  function hideReply(targetDiv,allCount)
			{
			 	for (var i=1;i<=allCount;i++)
				{
					if (i==targetDiv)
					{								
						document.getElementById('rowShow'+i).style.display='';
						document.getElementById('rowReply'+i).style.display='none';
					}
				}				
			}
		  function hideComment(allCount)
			{
				for (var i=1;i<=allCount;i++)
				{
					document.getElementById('rowShow'+i).style.display='none';
					document.getElementById('rowReply'+i).style.display='none';
					document.getElementById('row'+i).style.display='';			
				}
			}
		  function showReply(targetDiv,allCount)
			{
				for (var i=1;i<=allCount;i++)
				{
					if (i==targetDiv)
					{				
						document.getElementById('row'+i).style.display='';
						document.getElementById('rowReply'+i).style.display='';
						document.getElementById('rowShow'+i).style.display='none';
					}
					else
					{
						document.getElementById('row'+i).style.display='none';
						document.getElementById('rowReply'+i).style.display='none';				
					}
				}
				return false;		
			}
		  function reply(wsId,ndId,usrCode,desc,sId,trId)
			{	
			 
			 	var URL="ReplyComment_ex.do?workspaceId=" + wsId + "&nodeId=" + ndId + "&userCode=" + usrCode + "&message=" + document.getElementById(desc).value + "&subjectId=" + sId;
				if (document.getElementById(desc).value.length==0)
				{
					alert('Please add some text ...');
					return false;
				}
				/*    if (check(document.getElementById(desc).value,'\''))
				{
					alert('Invalid Characters (\') ...');
					return false;
				}   */
					if(document.getElementById(desc).value.indexOf("\'")!=-1)
					{
						alert('Invalid Characters (\').');
						return false;
					}
				$.ajax(
				{			
					url: URL,
			  		success: function(data) 
			  		{
			  			$('#'+trId).html(data);
						document.getElementById(desc).value = '';
			  		}
				});
				
				setTimeout(function() {
					Animate();
					Animate();
					}, 200);
			}
		function saveComments()
		{	
			if(document.NodeAttrForm.forumTextArea.value==""){
				alert("Please add Comments");
			}
		}
		
		function right(e) {
		if (navigator.appName == 'Netscape' &&
			(e.which == 3 || e.which == 2))
				return false;
			else if (navigator.appName == 'Microsoft Internet Explorer' &&
				(event.button == 2 || event.button == 3)) {
				alert("Sorry, you do not have permission to right click.");
				return false;
			}
			return true;
		}
		
		document.onmousedown=right;
		document.onmouseup=right;
		if (document.layers) window.captureEvents(Event.MOUSEDOWN);
		if (document.layers) window.captureEvents(Event.MOUSEUP);
		window.onmousedown=right;
		window.onmouseup=right;
    
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
    	
    	var charExp = /^[a-zA-Z1-9-.]+$/;
    	var charSpaceExp = /^[a-zA-Z1-9 ]+$/;
    	//var tempArr = document.workspaceNodeAttrForm.nodeName.value.split('.');
    	
    	function check(field)
    	{
    		if(field == document.workspaceNodeAttrForm.nodeDisplayName)
    		{
    			if(!field.value.match(charSpaceExp))
    			{
    				alert('Only alphabets, digits and spaces are allowed in \'Node Display Name.\'');
    				return true;
    			}
    		}
    		else
    		{
    			if(!field.value.match(charExp))
    			{
    				alert('Only alphabets, digits \' - \' and \' . \' are allowed.');
    				return true;
    			}
    			var tempArr = field.value.split('.');
    			if(tempArr.length > 2)
    			{
    				alert('Multiple \' . \' are not allowed.');
    				return true;
    			}
    		}
    		return false;
    	}
    	
    	function validate(buttonName)
    	{
    		if(buttonName == 'Extend')
    		{
    			if(document.workspaceNodeAttrForm.nodeName.value == '')
    			{
    				alert('Please specify Node Name.');
    				document.workspaceNodeAttrForm.nodeName.style.backgroundColor="#FFE6F7"; 
	     			document.workspaceNodeAttrForm.nodeName.focus();
    				return false;
    			}
    			else if(check(document.workspaceNodeAttrForm.nodeName))
    			{
    				document.workspaceNodeAttrForm.nodeName.style.backgroundColor="#FFE6F7"; 
	     			document.workspaceNodeAttrForm.nodeName.focus();
    				return false;
    			}
    			else if(document.workspaceNodeAttrForm.nodeDisplayName.value == '')
    			{
    				alert('Please specify Node Display Name.');
    				document.workspaceNodeAttrForm.nodeDisplayName.style.backgroundColor="#FFE6F7"; 
	     			document.workspaceNodeAttrForm.nodeDisplayName.focus();
    				return false;
    			}
    			else if(check(document.workspaceNodeAttrForm.nodeDisplayName))
    			{
    				document.workspaceNodeAttrForm.nodeDisplayName.style.backgroundColor="#FFE6F7"; 
	     			document.workspaceNodeAttrForm.nodeDisplayName.focus();
    				return false;
    			}
    			else if(document.workspaceNodeAttrForm.folderName.value == '')
    			{
    				alert('Please specify Folder Name.');
    				document.workspaceNodeAttrForm.folderName.style.backgroundColor="#FFE6F7"; 
	     			document.workspaceNodeAttrForm.folderName.focus();
    				return false;
    			}
    			else if(check(document.workspaceNodeAttrForm.folderName))
    			{
    				document.workspaceNodeAttrForm.folderName.style.backgroundColor="#FFE6F7"; 
	     			document.workspaceNodeAttrForm.folderName.focus();
    				return false;
    			}
    			return true;
    		}
    		return true;
    	}
    
  		function goToNodeActivity(nodeId)
  		{
  			var selectedActivity = document.forms['workspaceNodeAttrForm'].nodeActivity.value;
  			if(selectedActivity == '-1')
  			{
  				alert('Please select activity');
  				return false;
  			}
  			else if(selectedActivity == 'Add STF' || selectedActivity == 'Edit/Remove STF')
  			{
  				str="AttachSTF.do?nodeId="+nodeId;
				win3=window.open(str,'ThisWindow','height=660,width=650,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
				win3.moveTo(screen.availWidth/2-(650/2),screen.availHeight/2-(660/2));
				return true;
  			}
  			else if(selectedActivity == 'Repeat Section' || selectedActivity == 'Repeat/Delete Section') 
  			{
  				/* alert(<s:property value="nodeHistorysize" />); */
  				var nodeHistorySize = <s:property value="nodeHistorysize" />;
  				str="RepeatSection.do?repeatNodeId="+nodeId+"&nodeHistorySize="+nodeHistorySize+"&nId="+<s:property value="nodeId"/>;
				win3=window.open(str,'ThisWindow','height=380,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
				win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(350/2));
				return true; 
  			}
  		}

  		function editNode(nodeId)
		{
			str="EditProjectNodes.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");	
			win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));
			return true;
		}
		
		function addLeafNodeParent(nodeId)
		{
			//alert("addLeafNodeParent");
			var str = "AddChildNodes.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=350,width=800,resizable=no,titlebar=no");				
			win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(300/2));
			return true;
		}

		function assignRights(nodeId)
		{
			if (nodeId == null)
			{
				alert("Please Select Node");
				return false;
			}else{	
				
				str="AssignWorkspaceNodeRights.do?nodeId="+nodeId;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
				win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
				return true;
			}
		}
		
		function nodeDetailHistory(nodeId)
		{
			if (nodeId == null)
			{
				alert("Please Select Node");
				return false;
			}else{	
				
				str="NodeDetailHistory_b.do?nodeId="+nodeId;
				win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=1000,resizable=no,titlebar=no");
			 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
				return true;
			}
		}
		
		function assignRightsParent(nodeId)
		{
			var str="AssignWorkspaceNodeRights.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=420,resizable=no,titlebar=no");	
			win3.moveTo(screen.availWidth/2-(420/2),screen.availHeight/2-(500/2));
			return true;			
		}
  		
  	    function overlay()
		{
			xyz = document.parentWindow.parent.document.getElementById("overlay");
			xyz.style.visibility = (xyz.style.visibility == "visible") ? "hidden" : "visible";
			xyz1 = document.getElementById("overlayInnerDiv");
			xyz1.style.visibility = (xyz1.style.visibility == "visible") ? "hidden" : "visible";
		}
	window.onerror = function (){return true;};

function fileUploadSeq(buttonName){
	//debugger;
	var buttonName = buttonName;
	var nodeId = '<s:property value="nodeId"/>';
	var WorkspaceNodeDaviation='<s:property value="WorkspaceNodeDaviation"/>';
	var popUp = '<s:property value="WorkspaceNodeDaviationPopUp"/>';
	var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
	var wsId = '<s:property value="workspaceID"/>';
	var ClientId='<s:property value="clientCode"/>';
	var PQPreApprovalPopup = '<s:property value="PQPreApprovalPopup"/>';
	var confirmBtn = '<s:property value="IsConfirmBtn"/>';
	var isRequiredValidateBtn= '<s:property value="isRequiredValidate"/>';
	var Path;
	if(checkFileOpenSignOff=="Yes"){
	 $.ajax({		
		  url: "checkfileopenforsign_ex.do?nodeId="+nodeId+"&workspaceID="+wsId,
		  success: function(data) 
		  {
			 //debugger;
			 if(data=="false"){
			  	alert("Please review document before sign-off.");
			  	modal.style.display = "none";
			  	parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
	 if(PQPreApprovalPopup=="No"){
		 showDeviationPopup();
	 }
	 else if(PQPreApprovalPopup=="Yes" && confirmBtn=="false"){
		 showDeviationPopup();
	 }
	 else{
		 
		/* str="validatePreApproval_b.do?workspaceID="+wsId+"&nodeId="+nodeId+"&clientCode="+ClientId+"&Confirmflag=true";
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
		return true; */
		if(PQPreApprovalPopup=="Yes" && isRequiredValidateBtn=="true"){
		 $.ajax({		
			  url: "checkValidateApproval_ex.do?workspaceID="+wsId+"&nodeId="+nodeId+"&clientCode="+ClientId,
			  success: function(data) 
			  {
				 //debugger;
				 if(data=="true"){
					 showDeviationPopup();
				 }
				 else{
					 alert("Please validate document before sending for review.");
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
		else{
			showDeviationPopup();
		}
		
	 }
	
}
function showPreApprovalPopup(){
	//debugger;
	//document.getElementById("myModalValidate").style.display = "none"; 
	var buttonName = "sendReview";
	var nodeId = '<s:property value="nodeId"/>';
	var WorkspaceNodeDaviation='<s:property value="WorkspaceNodeDaviation"/>';
	var popUp = '<s:property value="WorkspaceNodeDaviationPopUp"/>';
	var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
	var wsId = '<s:property value="workspaceID"/>';
	var ClientId='<s:property value="clientCode"/>';
	 $.ajax({
		  	
		 url: "savePQPreApproval_ex.do?workspaceID="+wsId+"&nodeId="+nodeId+"&clientCode="+ClientId,

		 	success: function(data) 
		  {
			  //debugger;
			  alert("PQ-PreApproval data saved successfully.");
			/*  if(data=="true"){	
				 showDeviationPopup();
			 } */
		  },
		  error: function(data) 
		  {
			alert("Something went wrong while data saved.");
			parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
		  },
			//async: false
		});
	
	
}
function showDeviationPopup(){
	var buttonName = "sendReview";
	var nodeId = '<s:property value="nodeId"/>';
	var WorkspaceNodeDaviation='<s:property value="WorkspaceNodeDaviation"/>';
	var popUp = '<s:property value="WorkspaceNodeDaviationPopUp"/>';
	var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
	var PQPreApprovalPopup = '<s:property value="PQPreApprovalPopup"/>';
	var confirmBtn = '<s:property value="IsConfirmBtn"/>';
	 if(WorkspaceNodeDaviation != "None"){
			$.ajax({		
				  url: "checkFileUploadSeq_ex.do?nodeId="+nodeId,
				  success: function(data) 
				  {
					  //alert(data);
					  if(data=="true"){
					  	chgStatus1(buttonName);
					  }
					  else{
						  $.ajax({		
							  url: "showFileUploadSeq_ex.do?nodeId="+nodeId,
							  success: function(data) 
							  {
								  //debugger;
								 if(data=="true"){
								  	chgStatus1(buttonName);
								 }
								 else{
								 	 //alert(data);
								 	var htmlObject = document.createElement('div');
									 htmlObject.innerHTML = data;
									 var modal = document.getElementById("myModal");
									if(popUp=='Soft'){
									 	document.getElementById("deviationRemark").value="";
									 	document.getElementById("deviationRemark").style.backgroundColor="#fff";
									}
								 	$('#viewSubDtl').html(htmlObject);
								 	modal.style.display = "block";
							  		//chgStatus1(buttonName)
								 }
							  },
							  error: function(data) 
							  {
								alert("Something went wrong while fetching data from server.");
							  },
								async: false
							});	
					  }
				  },
				  error: function(data) 
				  {
					  alert("Something went wrong while fetching data from server.");
				  },
					async: false
				});
			}
			else{
				chgStatus1(buttonName);
			}
}
function deviationFile(){
	//debugger;
	var buttonName = 'sendReview';
	var nodeId = '<s:property value="nodeId"/>';	
	deviationRemark= document.getElementById("deviationRemark").value;
	var deviationLength = deviationRemark.length;
	var manualSignatureConfig = '<s:property value="ManualSignatureConfig"/>';
	var applicationUrl='<s:property value="ApplicationUrl"/>';
	
	if(deviationRemark!=""){
		
		if(deviationLength>=1000){
	    	alert("Reason for Deviation cannot be of more then 1000 characters.");
	    	return false;
	    }
		if(manualSignatureConfig=="Yes"){
			var signatureFlag = "<s:property value='signatureFlag'/>";
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			if(deviationRemark==undefined)
				deviationRemark="";
			var win = window.open(applicationUrl+"ManualSignature.do?workSpaceId="+'<s:property value="workspaceID"/>'+"&nodeId="+nodeId+"&deviationRemark="+deviationRemark, "_blank");
			var timer = setInterval(function() {
		        if (win.closed) {
		            clearInterval(timer);
		            parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
		        }
		    }, 500);
			
		}
		else{chgStatus1(buttonName);}
	}
	else{
		alert("Please specify Reason for Deviation.");
		document.getElementById("deviationRemark").style.backgroundColor="#FFE6F7"; 
		document.getElementById("deviationRemark").focus();
		return false;
	}
}

function dvCloseButton(){
	document.getElementById("myModal").style.display = "none";;
}
/* function dvCloseButtonValidate(){
	document.getElementById("myModalValidate").style.display = "none";
} */
function chgStatus1(buttonName)
	{
	debugger;
	var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
	var manualSignatureConfig = '<s:property value="ManualSignatureConfig"/>';
	var applicationUrl='<s:property value="ApplicationUrl"/>';
	var nodeId = '<s:property value="nodeId"/>';	
	var stage = document.getElementById("stageCode");
	if(stage!=null){
	var selectedStage = stage.options[stage.selectedIndex].value;}
	if(checkFileOpenSignOff=="Yes"){
	 $.ajax({		
		  url: "checkfileopenforsign_ex.do?nodeId="+nodeId+"&workspaceID="+'<s:property value="workspaceID"/>',
		  success: function(data) 
		  {
			 //debugger;
			 if(data=="false"){
			  	alert("Please review document before sign-off.");
			  	modal.style.display = "none";
			  	parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
		//debugger;
		/* var elecSig=document.getElementById('elecSig').value;
		var currStage=document.getElementById('workspaceNodeAttr_stageCode').value;
		document.getElementById('reConfPass').value = '';
		if (currStage=='100' && elecSig=='Yes')
		{ */
			//alert(clickedButton);
			if(manualSignatureConfig=="Yes" && selectedStage!=0){
				var signatureFlag = "<s:property value='signatureFlag'/>";
				if(signatureFlag=="false"){
					alert("Please create signature before proceeding sign off.");
					return false;
				}
				
				if(deviationRemark==undefined)
					deviationRemark="";
				var win = window.open(applicationUrl+"ManualSignature.do?workSpaceId="+'<s:property value="workspaceID"/>'+
						"&nodeId="+nodeId+"&deviationRemark="+deviationRemark+"&selectedStage="+selectedStage, "_blank");
				var timer = setInterval(function() {
			        if (win.closed) {
			            clearInterval(timer);
			            parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
			        }
			    }, 500);
				
				return false;
			}
			else{
				//chgStatus1(buttonName);
				//return false;
			
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
		/* }
		else
			return true; */
	} 
function verifyPass()
{
    // debugger;
	//var sessPass=document.getElementById('sessPass').value;
	var sessPass="<s:property value='#session.password'/>";
	var pass=document.getElementById('reConfPass').value;
	var sessAdUser="<s:property value='#session.adUser'/>";
	var sessAdUserPass="<s:property value='#session.adUserPass'/>";
	if (!pass || pass == '')
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
		var currStage= $('#stageCode').val();
		if(currStage==0){
			 var remark = prompt("Please specify reason for change.");
			 if (remark != null && remark != ""){ 
				 remark = remark.trim();
				 if (remark != null && remark != ""){ 
				 $('#remark').val(remark);
				 return true;
				 }
				 else{
					 alert("Please specify reason for change.");
					 return false;
				 }
			}
			 else{
				 alert("Please specify reason for change.");
				 return false;
			 }
		}
		
		 return true;
		}	
}
	function chgStatus(nodeId)
	{
		debugger;
		//var sessPass=document.getElementById('sessPass').value;
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		if (!pass || pass == '')
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
					
				$.ajax({		
					  url: "certifiedFile_ex.do?nodeId="+nodeId,
					  success: function(data) 
					  {
						  cls();
						  alert(data);
					  },
					  error: function(data) 
					  {
						alert("Something went wrong while fetching data from server.");
					  },
						async: false
					});
			}
			else
			{
				alert("Incorrect Password !!!");
				document.getElementById('reConfPass').value = '';
				return false;
			}				
	}

	function cls()
	{
		//debugger;
		document.getElementById('reConfPass').value = '';
		disablePopup();
		var nodeId = <s:property value="nodeId"/>;
		parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
	}
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13))  
  		{
  			return false;
  		} 
	} 

	document.onkeypress = detectReturnKey;

/*	function callForAjaxSubmit()
	{
		 $.ajax({
			 
			 url : "SendRemarksToUser.do?nodeId="+document.workspaceNodeAttrForm.nodeId.value+"&txtmsg="+document.workspaceNodeAttrForm.forumTextArea.value,
			 beforeSend: function()
				{ 
				 $("#CommentLoadDiv").html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
				},
			 success:function(data)
			 {
					alert("Data Added Successfully..");
					$("#CommentLoadDiv").html("");
			 }
			 });
	}
*/
	/* $(document).ready(function() 
	{	
		var status='<s:property value="isNodeLocked"/>';
		debugger;
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
		var right=${iscreatedRights};
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
		var CnF=${confirmAndUpload};
		if(status=="true"){
			//refreshTree();
		}
				var options = 
				{
					success: showResponse
				};
				$("#saveFormButton").click(function(){
					if(saveandsendCommentsasRemarks())
					{
						$("#workspaceNodeAttrForm").ajaxsubmit(options);
						alert("Jaspreet");						
					}
					return false;			
				});
				
				$("#animate").click(function(){
					setTimeout(function(){
						//$("#commentsDiv2").fadeOut();
						//$("#commentParent").toggle("slide","",5000);
						//$("#commentParent").fadeIn("slow");
					}, 1000);
			    });
			}); */
	function showResponse(responseText, statusText) 
	{
		alert(responseText);
		
	}
	
	function lockSeq(){
		alert("You can not do any activity in locked project.");
	}
	function certifiedfile(nodeId,buttonName){
		var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
		if(checkFileOpenSignOff=="Yes"){
		 $.ajax({		
			  url: "checkfileopenforsign_ex.do?nodeId="+nodeId+"&workspaceID="+'<s:property value="workspaceID"/>',
			  success: function(data) 
			  {
				 //debugger;
				 if(data=="false"){
				  	alert("Please review document before sign-off.");
				  	modal.style.display = "none";
				  	parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
		$.ajax({			
			  url: "chekcertifiedFile_ex.do?nodeId="+nodeId,
			  success: function(data) 
			  {
				  //debugger;
				  
				 // var workspaceId="<s:property value='#session.ws_id'/>";
				  if(data=="true")
				  {
					  alert("File Already Certified.");
				  }
				  else{
					  //var elecSig=document.getElementById('elecSig').value;
					    var elecSig='Yes';
						document.getElementById('reConfPass').value = '';
						if (elecSig=='Yes' && buttonName=='certifiedFile')
						{
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
							
							isVerifyCertified=true;
							document.getElementById("VerifyReview").style.display='none';
							document.getElementById("Verify").style.display='none';
							document.getElementById("VerifyVoideFile").style.display='none';
							document.getElementById("VerifyCertified").style.display='block';
							document.getElementById('stage').innerHTML = "Certified";							
							centerPopup();				
							loadPopup();
							return false;
						}
						else
							return true;
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
	function certifiedfileForAttr(nodeId,buttonName){
		//debugger;
		var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
		if(checkFileOpenSignOff=="Yes"){
		 $.ajax({		
			  url: "checkfileopenforsign_ex.do?nodeId="+nodeId+"&workspaceID="+'<s:property value="workspaceID"/>',
			  success: function(data) 
			  {
				 //debugger;
				 if(data=="false"){
				  	alert("Please review document before sign-off.");
				  	modal.style.display = "none";
				  	parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
					  //var elecSig=document.getElementById('elecSig').value;
			var elecSig='Yes';
			document.getElementById('reConfPass').value = '';
			if (elecSig=='Yes' && buttonName=='certifiedFile')
			{
				var userName = "<s:property value='#session.username'/>";
				document.getElementById('userName').innerHTML = userName;
				
				var  months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
				var monthName; // current month in mmm formate

				var currentdate = new Date();
				var datetime =  currentdate.getDate()+ "-" +  months[currentdate.getMonth()] + "-" 
					   					    + currentdate.getFullYear() + " "
					   						//+ currentdate.getHours() + ":"  
					    					//+ currentdate.getMinutes();
				document.getElementById("date").innerHTML = datetime;
							
				isVerifyCertified=true;			
				document.getElementById("VerifyReview").style.display='none';
				document.getElementById("Verify").style.display='none';
				document.getElementById("VerifyVoideFile").style.display='none';
				document.getElementById("VerifyCertified").style.display='block';
				document.getElementById('stage').innerHTML = "Certified";							
				centerPopup();				
				loadPopup();
				return false;
			}
			else{
				return true;
			}
				
	}
function verifyVoidFile(buttonName){
		//debugger;
		//var elecSig=document.getElementById('elecSig').value;
		var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
		if(checkFileOpenSignOff=="Yes"){
		 $.ajax({		
		  url: "checkfileopenforsign_ex.do?nodeId="+'<s:property value="nodeId"/>'+"&workspaceID="+'<s:property value="workspaceID"/>',
		  success: function(data) 
		  {
			 //debugger;
			 if(data=="false"){
			  	alert("Please review document before sign-off.");
			  	modal.style.display = "none";
			  	parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
		var elecSig='Yes';
		document.getElementById('reConfPass').value = '';
		if (elecSig=='Yes' && buttonName=='voidFile')
			{
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
			
			 isVoidFile=true;
			document.getElementById("VerifyReview").style.display='none';
			document.getElementById("Verify").style.display='none';
			document.getElementById("VerifyCertified").style.display='none';
			document.getElementById("VerifyVoideFile").style.display='block';
			document.getElementById('stage').innerHTML = "Void";		
			centerPopup();				
			loadPopup();
			return false;
			}
			else
			return true;
	}

	function nodeAttrDetailHistory(nodeId)
	{
		//debugger;
		if (nodeId == null)
		{
			alert("Please Select Node");
			return false;
		}else{	
			
			str="NodeAttrDetailHistory_b.do?nodeId="+nodeId;
			win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
		 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
			return true;
		}
	}
	
	function showRemark(){
		//debugger;
		
		var docReqReason=document.getElementById('Reason For Type of Signature');
		var docRequired=document.getElementById('Type of Signature');
		 if(docRequired!=null && docRequired.value == "Physical" && docReqReason.value == ''){
			alert('Please specify Reason For Type of Signature.');
			docReqReason.style.backgroundColor="#FFE6F7"; 
			docReqReason.focus();
			return false;
		}
		
		if(document.workspaceNodeAttrForm.attrRemark.value == '' ||document.workspaceNodeAttrForm.attrRemark.value == null)
		{
			alert('Please specify Reason for Change.');
			document.workspaceNodeAttrForm.attrRemark.style.backgroundColor="#FFE6F7"; 
			document.workspaceNodeAttrForm.attrRemark.focus();
			return false;
		}
	}
	function ShowHideDiv() {
		//debugger;
		var docRequired=document.getElementById('Type of Signature').value;
		if(docRequired=="Physical"){
			document.getElementById('IDReason For Type of Signature').style.display='block';
			document.getElementById('Reason For Type of Signature').style.display='block';
		}
		else{
			document.getElementById('IDReason For Type of Signature').style.display='none';
			document.getElementById('Reason For Type of Signature').style.display='none';
		}
	}
	function test1(){
		//debugger;
		var nodeId = '<s:property value="nodeId"/>';
		var wsId = '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		if (!pass || pass == '')
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
				debugger;
		
		 var remark = prompt("Please specify reason for change.");
		 if (remark != null && remark != ""){ 
			 remark = remark.trim();
			 if (remark != null && remark != ""){ 
			 $('#remark').val(remark);
			 $.ajax({		
				  url: "sendForVoid_ex.do?workspaceID="+wsId+"&nodeId="+nodeId+"&usercode="+userCode+"&remark="+remark,
				  beforeSend: function()
					{ 
					  //debugger;
					  //$("#popupContact").html("<center><img style=\"margin-top:100px\" src=\"images/loading.gif\" alt=\"loading ...\" /></center>");
						document.getElementById("popupContact").style.display='none'; 
						document.getElementById("loadingpopup").style.display='block';
					},
				  success: function(data) 
				  {
					  //debugger;
					  document.getElementById("loadingpopup").style.display='none';
					  parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
				 alert("Please specify reason for change.");
				 return false;
			 }
		}
		 else{
			 alert("Please specify reason for change.");
			 return false;
		 }
		
		
			}
			else
			{
				alert("Incorrect Password !!!");
				document.getElementById('reConfPass').value = '';
				$( '#reConfPass').focus(); 
				return false;
			}
		}
	function sendForReview(stageId)
	{
		//alert("test");
		debugger;
		
		var nodeId = '<s:property value="nodeId"/>';
		var wsId = '<s:property value="workspaceID"/>';
		var userCode ='<s:property value="usercode"/>';
		
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var signatureFlag = "<s:property value='signatureFlag'/>";
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		
		if(signatureFlag=="true"){
		eSign = "Y";
			
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
		
		$.ajax({		
			  url: "sendForReview_ex.do?workspaceID="+wsId+"&nodeId="+nodeId+"&usercode="+userCode
					  +"&remark="+deviationRemark+"&stageId="+stageId+"&eSign="+eSign,
			  beforeSend: function()
			  { 
				 // debugger;
				  //$("#popupContact").html("<center><img style=\"margin-top:100px\" src=\"images/loading.gif\" alt=\"loading ...\" /></center>");
				 document.getElementById("popupContact").style.display='none';
				 document.getElementById("myModal").style.display='none';
				 document.getElementById("loadingpopup").style.display='block';
			  },
			  success: function(data) 
			  {
				  //debugger;
				  document.getElementById("loadingpopup").style.display='none';
				  parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
		else{
			alert("Please create signature.");
			return false;
		}
		
	}
	
	 function change_pos() { 
         var x = 600; 
         var y = 596; 
         var el = document.getElementById('downloading'); 
         el.style.position = "absolute"; 
         el.style.left = x + 'px'; 
         el.style.top = y + 'px'; 
   } 

   function showCoords(event) {
     	//debugger;
     	  var x = event.clientX;
     	  var y = event.clientY;
     	  var coords = "X coords: " + x + ", Y coords: " + y;
     	  alert(coords);
     	  //document.getElementById("demo").innerHTML = coords;
     }
   function downloadPdfForCertified(data){
   	debugger;
   	//var workSpaceId=$('#workSpaceId').val();
   	var workspaceID =  '<s:property value="workspaceID"/>';
   	var nodeId=document.workspaceNodeAttrForm.nodeId.value
   	//alert("Type is :"+type);
   	//var URL='Download_ex.do?workSpaceId='+workSpaceId+"&nodeId="+nodeId ;
   	var URL='Download_ex.do';
   	
   	$.ajax(
   	{			
   		url: 'certifiedAndDownload_ex.do?workspaceID='+workspaceID+"&nodeId="+nodeId,
   		 		
   		beforeSend: function()
   		{
   			//debugger;
   			if(data=='approved'){
				$('#imgPdf').hide();
				//$('#downloading_approved').css('background-color', '#E3EAF0');
				$('#downloading').show();}
			else{
				$('#imgPdf').hide();
				//$('#downloading').css('background-color', '#E3EAF0');
				$('#downloading_approved').show();	
				}
   		},
   		success: function(data) 
     		{
				if(data.length>0){
					if(data.includes("/")){
						window.location = URL+'?DownloadFile='+data; 
					}
					else{
						alert(data);
					}
				}
				
   			$('#downloading').hide();
   			$('#downloading').hide();
   			$('#imgPdf').show();
   		},
   		/* complete: function(){
   			
   	      }, */
   		//async: false,
           error: function (data) {
           	alert("Something went wrong");
               },		
   		
   	});
   	return true;
   }
function downloadPdf(data){
     	debugger;
     	//var workSpaceId=$('#workSpaceId').val();
     	var workspaceID =  '<s:property value="workspaceID"/>';
     	var nodeId=document.workspaceNodeAttrForm.nodeId.value
     	
     	//alert("Type is :"+type);
     	//var URL='Download_ex.do?workSpaceId='+workSpaceId+"&nodeId="+nodeId ;
     	//var URL='Download_ex.do';
     	var URL = 'openfile.do';
     	var tranNo = '<s:property value="tranNoForPublishing"/>';
     	var baseWorkFolder='<s:property value="baseWorkFolderForPublishing"/>';
     	var fileExt='pdf';
     	
     	$.ajax(
     	{			
     		url: 'convertAndDownload_ex.do?workspaceID='+workspaceID+"&nodeId="+nodeId,
     		 		
     		beforeSend: function()
     		{
     			if(data=='approved'){
				$('#imgPdf').hide();
				//$('#downloading_approved').css('background-color', '#E3EAF0');
				$('#downloading_approved').show();}
			else{
				$('#imgPdf').hide();
				//$('#downloading').css('background-color', '#E3EAF0');
				$('#downloading').show();	
				}
     		},
     		success: function(data) 
       		{
     			//url: 'Download_ex.do?DownloadFile='+data;
     			//debugger;
     			
     			//alert(data);
     			
     			/* if(data == 'false'){
  					alert("Something went wrong While Converting Document File...");
  				}
  				else if(data == 'ReadOnly'){
  					alert("File is already open, Plese close and try again...");
  				}
  				else{
  					window.location = URL+'?DownloadFile='+data; 
  				} */
  				//alert(data);
  				//debugger;
  				//if(data.length>0){
  					if(!data.includes('PLease contact or send this Error to your administrator')){
  					//if(data.includes("/")){
  					if(data.includes("\\") || data.includes("/") ){
  						//window.location = URL+'?DownloadFile='+data;
  						window.open (URL+'?workSpaceId='+workspaceID+'&nodeId='+nodeId+'&tranNo='+tranNo+'&fileName='+data.split('/').pop()+'&baseWorkFolder='+baseWorkFolder+'&fileExt='+fileExt,"_blank");
  						
  					}
  					else{
  						alert(data);
  					}
  				}
  				
     			$('#downloading').hide();
     			$('#downloading_approved').hide();
     			$('#imgPdf').show();
     		},
     		/* complete: function(){
     			
     	      }, */
     		//async: false,
             error: function (data) {
             	alert("Something went wrong");
                 },		
     		
     	});
     	return true;
     }	
 function sectionAttributes(nodeId){
	//alert("test");
	//debugger;
	var workspaceID =  '<s:property value="workspaceID"/>';
	str="getAttributeDataForCSV_b.do?workspaceID="+workspaceID+"&nodeId="+nodeId;
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
}
 
 function loader(){
	// $("#tble").html("<center><img style=\"margin-top: 35px; margin-bottom: 30px; margin-left: 162px;\" src=\"images/loading.gif\" alt=\"loading ...\" /></center>");
	//debugger;
	document.getElementById("popupContact").style.display='none'; 
	document.getElementById("loadingpopup").style.display='block'; 
 }
 
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
 
 function CreateBlankFileToSharePoint(path)
 {
 	//alert('test');
 	//debugger;
 	
 	var workspaceID =  '<s:property value="workspaceID"/>';
	var userCode ='<s:property value="usercode"/>';
	var DocURL = '<s:property value="DocURLForOffice365"/>';
	var nodeId=document.workspaceNodeAttrForm.elements['nodeId'].value;
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
			  url: "CreateBlankFileInSharePoint_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&userCodeForOffice="+userCode,
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
				            parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
	//debugger;
 	
 	var workspaceID =  '<s:property value="workspaceID"/>';
	var userCode ='<s:property value="usercode"/>';
	var nodeId=document.workspaceNodeAttrForm.elements['nodeId'].value;
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
								            parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
	var nodeId=document.workspaceNodeAttrForm.elements['nodeId'].value;
	
	
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
				 parent.document.getElementById("attributeFrame").src='workspaceNodeAttrAction.do?nodeId='+nodeId;
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
	var nodeId=document.workspaceNodeAttrForm.elements['nodeId'].value;
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
	//debugger;
	
	var workspaceID =  '<s:property value="workspaceID"/>';
	var userCode ='<s:property value="usercode"/>';
	var nodeId=document.workspaceNodeAttrForm.elements['nodeId'].value;
	var nodeFolderName=document.workspaceNodeAttrForm.elements['nodeFolderName'].value;
	var srcDocPath='<s:property value="srcDocPath"/>';
	var docPath='<s:property value="nodeDocHistory.get(0).getDocPath()"/>';
	var getDocContentType='<s:property value="nodeDocHistory.get(0).getDocContentType()"/>';
	var lbl_nodeName='<s:property value="lbl_nodeName"/>';
	var docName;
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
				"&uploadFileFileName="+docName+"&fileToUpload="+uploadFile,
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

function DMSvalidate()
{
	//debugger;
	var workspaceID =  '<s:property value="workspaceID"/>';
	var userCode ='<s:property value="usercode"/>';
	var nodeId=document.workspaceNodeAttrForm.elements['nodeId'].value;
	var uploadFile = document.workspaceNodeAttrForm.uploadDoc.value;
	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
	var index=uploadFile.lastIndexOf('.');
	var strInvalidChars = "()/\^'$#:~%@&;,!*<>?";
	var fileext = uploadFile.substring(index+1).toLowerCase();
	if(uploadFile=="")
	{
			alert("Please Select Document.");
			document.workspaceNodeAttrForm.uploadDoc.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.uploadDoc.focus();
			return false;
	}
	
	if((fileext!="doc" && fileext!="docx")){
		alert("Please upload valid extension (e.g. .doc & .docx) Document.")
		return false;
	}
	for (i = 0; i < uploadFile.length; i++)
	{
			strChar = uploadFile.charAt(i);
	 	if (strInvalidChars.indexOf(strChar)!= -1 && deltefilevalue==0)
			{
	 		alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
      		return false;  			
			}
	}
	$.ajax({		
		  //url: "UploadFileInSharepoint_ex.do?path="+path,
		  url: "checkReviewFile_ex.do?OfficeWsId="+workspaceID+"&OfficeNodeId="+nodeId+"&OfficeUserCode="+userCode+"&userCodeForOffice="+userCode,
		  success: function(data) 
		  {
			 // debugger;
			  if(data=='false'){
	
	/* var uploadFile = document.workspaceNodeAttrForm.uploadDoc.value;
	uploadFile= uploadFile.replace(/^.*[\\\/]/, '');
	var index=uploadFile.lastIndexOf('.');
	var strInvalidChars = "()/\^'$#:~%@&;,!*<>?";
	var fileext = uploadFile.substring(index+1).toLowerCase(); */
	if(uploadFile=="")
	{
			alert("Please Select Document.");
			document.workspaceNodeAttrForm.uploadDoc.style.backgroundColor="#FFE6F7"; 
     		document.workspaceNodeAttrForm.uploadDoc.focus();
			return false;
	}
	
	if((fileext!="doc" && fileext!="docx")){
		alert("Please upload valid extension (e.g. .doc & .docx) Document.")
		return false;
	}
	for (i = 0; i < uploadFile.length; i++)
	{
			strChar = uploadFile.charAt(i);
	 	if (strInvalidChars.indexOf(strChar)!= -1 && deltefilevalue==0)
			{
	 		alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
      		return false;  			
			}
	}
			  }else{
				  var arr=data.split(":")
				  $('#uploading').hide();
				  alert("Document is open by "+arr[1]+", Please close and try again.");
			  }
			  
		  },
		  async:false,
		  error: function(data) 
		  {
			  //debugger;
			  alert("Something went wrong while fetching data from server...");
		  },
			//async: false				
		});
	return true;
}
function openPreApprovalPopup(filePath){
	//debugger;
	var wsId='<s:property value="workspaceID"/>';
	var nodeId='<s:property value="nodeId"/>';
	var ClientId='<s:property value="clientCode"/>';
	var Path;
	
	str="validatePreApproval_b.do?workspaceID="+wsId+"&nodeId="+nodeId+"&clientCode="+ClientId+"&Path="+filePath+"&Confirmflag=true";
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
	
	//centerPopup();				
	//loadPopup();
}

function openAutomatePopup(filePath){
	debugger;
	var wsId='<s:property value="workspaceID"/>';
	var nodeId='<s:property value="nodeId"/>';
	var ClientId='<s:property value="clientCode"/>';
	var Path;
	
	str="automateDocument_b.do?workspaceID="+wsId+"&nodeId="+nodeId+"&Path="+filePath+"&Confirmflag=true";
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
	
	//centerPopup();				
	//loadPopup();
}

function openTracebilityMatrix(){
	debugger;
	var showAlert=document.getElementById('traceblityAlert').value;
	if(showAlert=="true"){
		alert("Please select URS / FS points");
		return false;
	}
	else{
		var workspaceID =  '<s:property value="workspaceID"/>';
		var nodeId =  '<s:property value="nodeId"/>';
		str="getTracebilityMatrixData_b.do?workspaceID="+workspaceID+"&nodeId="+nodeId;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
	 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
		return true;
	}
}


function openPreApprovalPopupFromSrc(filePath){
	//debugger;
	var wsId='<s:property value="workspaceID"/>';
	var nodeId='<s:property value="nodeId"/>';
	var ClientId='<s:property value="clientCode"/>';
	var Path;
	
	str="SrcDocValidatePreApproval_b.do?workspaceID="+wsId+"&nodeId="+nodeId+"&clientCode="+ClientId+"&Path="+filePath;
	win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=800,resizable=no,titlebar=no");
 	win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(500/2));	
	return true;
	
	//centerPopup();				
	//loadPopup();
}
 
function RepeatFailedScript(nodeId){
	debugger;
	var wsId='<s:property value="workspaceID"/>';
	var nodeId='<s:property value="nodeId"/>';

	$.ajax({		
		  //url: "UploadFileInSharepoint_ex.do?path="+path,
		  url: "RepeatFailedScript_ex.do?workspaceID="+wsId+"&nodeId="+nodeId,
		  success: function(data) 
		  {
			 //debugger;
			  var treeiframe = parent.document.getElementById("nodeFrame");
			  treeiframe.src = treeiframe.src;
			  window.location.reload();
		},
		  //async:false,
		  error: function(data) 
		  {
			  //debugger;
			  alert("Something went wrong while fetching data from server...");
		  },
			//async: false				
		});
}
 </script>

<s:if test="actionMessages!=null && actionMessages.size > 0">
	<script>
        var actionMessages;
        <s:iterator value="actionMessages" >
            // Iterate the messages, and build the JS String
            actionMessages =  '<s:property />';
            actionMessages=actionMessages.substring(1, actionMessages.length-1);
        </s:iterator>   
        if(actionMessages=="pdfUpload"){
        	alert("Access denied !, File upload unsuccessful. Server refuses the active connection");
        	history.go(-2);
        }else if(actionMessages=="docUpload"){
        	alert("Access denied !, File upload unsuccessful. Server refuses the active connection");
        	history.go(-1);
        }
        else if(actionMessages=="EncryptedFile"){
        	alert("You are try to upload password protected file. \nPlease remove password protection security and re-upload the same.");
        	history.go(-2);
        }
        else if(actionMessages=="FileUploadingSize"){
        	alert("You can't upload file size greate than 100 MB.");
        	history.go(-2);
        }
        
    </script>
</s:if>
</head>

<body>
	<s:if test="workSpaceNodeDtl.nodeDisplayName != null">
		<div class="headercls">
			<b>Name: ${finalString}</b>
		</div>
	</s:if>
	<div align="left" class="bdycls" style="padding: 5px;">
		<s:if test="nodeId != 0">
			<s:form name="workspaceNodeAttrForm" id="workspaceNodeAttrForm"
				action="workspaceNodeAttr" enctype="multipart/form-data"
				method="post" onsubmit="return loader();">
				<%-- <table>
			<tr>
				<td class="remarkcss" style="height: 0px;">
				<div class="liDisk"><s:if
					test="workSpaceNodeDtl.remark.trim() != ''">
					
					<TEXTAREA rows="4" cols="203" name="remark" style="BACKGROUND: #FFFFFF;" disabled><s:property value="workSpaceNodeDtl.remark" escape="false" /></TEXTAREA>

				</s:if> <s:else>
								<!-- Guidelines not attached. -->
							</s:else></div>
				<input type="hidden" name="workspacedesc"
					value="<s:property value="workspacedesc"/>" /></td>
			</tr>
		</table> 
		<table width="100%" align="center">
			<tr>
				<td align="center">
				<hr color="#5A8AA9" size="1px"
					style="width: 100%; border-bottom: 2px solid #CDDBE4;">
				</td>
			</tr>
		</table>--%>


				<s:if test="showEditStructure.equalsIgnoreCase('Yes')">
					<table width="100%" class="datatable" style="border: none;">
						<tr>
							<th style="background: #2e7eb9; color: #fff;" width="100%"
								colspan="5"><b>Edit Project</b></th>
						</tr>

						<tr class="none">
							<s:if test="lockSeqFlag == true ">
								<td width="25%"><a href="#" onclick="lockSeq();">Edit
										${ lbl_nodeName } Detail</a></td>
								<td width="25%"><a href="#" onclick="lockSeq();">Add
										Leaf ${ lbl_nodeName }</a></td>
								<td width="25%"><a href="#" onclick="lockSeq();">Assign
										Rights</a></td>
							</s:if>
							<s:else>
								<s:if test="#session.usertypename == 'WA'">
									<td width="20%"><a href="#"onclick="return editNode(${nodeId});">
										Edit ${ lbl_nodeName } Detail</a>
									</td>
									<%-- <td width="20%"><a href="#"
						onclick="return addLeafNodeParent(${nodeId});">Add Leaf Node</a></td> --%>
									<s:if test="#session.usertypename == 'WA' && isLeafNode==1 ">
										<td width="20%">
											<a href="#"onclick="return assignRights(${nodeId});">Assign Rights</a>
										</td>
									</s:if>
								</s:if>
								<%-- <s:elseif test="#session.usertypename == 'WU' && isLeafNode==1 ">
									<td width="20%"><a href="#"
										onclick="return assignRights(${nodeId});">Assign Rights</a></td>
								</s:elseif> --%>
							</s:else>
							<td width="20%"><a href="#"
								onclick="return nodeDetailHistory(${nodeId});">Audit Trail</a></td>
							<%-- <s:if test="(#session.usertypename == 'WA' ||(#session.usertypename == 'WU' && iscreatedRights==true)) &&  isLeafParent == 0">
					<form id="myform" action="ExportToXls.do" method="post"><input type="hidden" name="fileName" value="Comment_Snapshot_Report.xls">					
					<textarea rows="1" cols="1" name="tabText" id="tableTextArea" style="visibility: hidden;height: 10px;"></textarea>
					<td width="20%"><a href="#" onclick="return sectionAttributes(${nodeId});">Section Attributes</a></td>
				</form>
				</s:if> --%>

							 <s:if
								test="nodeIdForFlags == 1 && (#session.usertypename == 'WA' ||(#session.usertypename == 'WU' && iscreatedRights==true))">
								<form id="myform" action="ExportToXls.do" method="post">
									<input type="hidden" name="fileName"
										value="Comment_Snapshot_Report.xls">
									<textarea rows="1" cols="1" name="tabText" id="tableTextArea"
										style="visibility: hidden; height: 10px;"></textarea>
									<td width="20%"><a href="#"
										onclick="return sectionAttributes(${nodeIdForFirstChild});">Section
											Attributes</a></td>
									<%-- <td width="20%"><a href="#" onclick="return RepeatParentSection(${nodeId});">Repeat Section</a></td> --%>
								</form>
							</s:if> 


						</tr>
					</table>
					<table width="100%" align="center">
						<tr>
							<td align="center">
								<hr color="#5A8AA9" size="1px"
									style="width: 100%; border-bottom: 2px solid #CDDBE4;">
							</td>
						</tr>
					</table>
				</s:if>
				<%-- <s:else>
			<table width="100%" class="datatable" style="border: none;">
				<tr>
					<th class="title" width="100%" colspan="3"><b>Edit Project
					Structure</b></th>
				</tr>
				<tr class="none">
				<s:if test="#session.usertypename == 'Initiator'">
					<s:if test = "lockSeqFlag == true ">
						<td><a href="#" onclick="lockSeq();">Edit Node Detail</a></td>
					</s:if>
					<s:else>
						<td><a href="#"	onclick="return editNode(${nodeId});">Edit Node Detail</a></td>
					</s:else>
				</s:if>
					<td><a href="#" onclick="return nodeDetailHistory(${nodeId});">Audit Trail</a></td>
				</tr>
			</table>
			<table width="100%" align="center">
				<tr>
					<td align="center">
					<hr color="#5A8AA9" size="1px"
						style="width: 100%; border-bottom: 2px solid #CDDBE4;">
					</td>
				</tr>
			</table>
		</s:else> --%>

				<s:if
					test="attrDtl.size != 0 && workSpaceNodeDtl.requiredFlag != 'S'">
					<s:if
						test="#session.usertypename == 'WA' || (#session.usertypename == 'WU' && iscreatedRights==true)">

						<table width="100%" class="datatable" style="border: none;">
							<tr>
								<th><b>Add Reference Value</b></th>
							</tr>
							<tr class="none">
								<td class="none">
									<div id="AttributeValueAdd">
										<table class="doubleheight">
											<s:if test="attrDtl.size == 0">
												<tr>
													<td style='color: navy; font-size: 12px;'>No details
														available.</td>
												</tr>
											</s:if>
											<s:else>

												<s:set name="elementid" value="1"></s:set>

												<s:set name="prevattrid" value="-1" />

												<s:iterator value="attrDtl">

													<s:if test="attrType == 'Text'">
														<s:if test="Automated_TM_Required=='Yes' && showAutomateButton==false 
														&& (attrName=='URS Reference Number' || attrName=='FS Reference Number') ">
														<%-- <s:set name="elementid" value="#elementid - 1"></s:set> --%>
														
														</s:if>
														<s:else>
														<tr class="none">
															<td id="ID${attrName }" class="title">${attrName }</td>
															<td><input type="text"
																name="attrValueId<s:property value="#elementid"/>"
																id="${attrName }"
																value="<s:property value="attrValue"/>"> <input
																type="hidden"
																name="attrType<s:property value="#elementid"/>"
																value="Text"> <input type="hidden"
																name="attrName<s:property value="#elementid"/>"
																value="${attrName }"> <input type="hidden"
																name="attrId<s:property value="#elementid"/>"
																value="${attrId }"></td>
														</tr>
															
														<s:set name="elementid" value="#elementid + 1"></s:set>
														</s:else>
														
														
													
													</s:if>
													<s:elseif test="attrType == 'TextArea'">

														<tr class="none">
															<td class="title">${attrName}</td>
															<td><textarea rows="3" cols="30"
																	name="attrValueId<s:property value="#elementid"/>"><s:property
																		value="attrValue" /></textarea> <input type="hidden"
																name="attrType<s:property value="#elementid"/>"
																value="TextArea"> <input type="hidden"
																name="attrName<s:property value="#elementid"/>"
																value="${attrName }"> <input type="hidden"
																name="attrId<s:property value="#elementid"/>"
																value="${attrId }"></td>
														</tr>
														<s:set name="elementid" value="#elementid + 1"></s:set>
													</s:elseif>
													<s:elseif test="attrType == 'Date'">

														<tr class="none">
															<td class="title">${attrName }</td>
															<td><input type="hidden"name="attrType<s:property value="#elementid"/>"value="Date"> 
															<input type="hidden"name="attrName<s:property value="#elementid"/>"value="${attrName }"> 
															<input type="hidden" name="attrId<s:property value="#elementid"/>"value="${attrId }"> <%-- <s:if test="isSendForReview==false && isTimeLineNodeCount==true" > --%>
															<s:if test="isSendForReview==false && isStartDate ==true && #session.usertypename == 'WA'">
																<input type="text" name="attrValueId<s:property value="#elementid"/>"
																	   class="attrValueIdDate" readonly="readonly"
																	   id="attrValueId<s:property value="#elementid"/>"
																	   value="<s:property value="attrValue"/>">
											
											 (YYYY/MM/DD) &nbsp;<IMG
																		onclick="if(document.getElementById('attrValueId<s:property value="#elementid"/>').value != '' 
														&& confirm('Are you sure?'))
														document.getElementById('attrValueId<s:property value="#elementid"/>').value = '';
																					"
																		src="<%=request.getContextPath()%>/images/clear.svg"
																		height="25px" width="25px" align="middle"
																		title="Clear Date">
																</s:if> <s:elseif
																	test="isSendForReview==true && isStartDate ==false && #session.usertypename == 'WA'">
																	<input type="text"
																		name="attrValueId<s:property value="#elementid"/>"
																		class="attrValueIdDate" readonly="readonly"
																		id="attrValueId<s:property value="#elementid"/>"
																		value="<s:property value="attrValue"/>">
											
											 (YYYY/MM/DD) &nbsp;<IMG
																		onclick="if(document.getElementById('attrValueId<s:property value="#elementid"/>').value != '' 
														&& confirm('Are you sure?'))
														document.getElementById('attrValueId<s:property value="#elementid"/>').value = '';
																					"
																		src="<%=request.getContextPath()%>/images/clear.svg"
																		height="25px" width="25px" align="middle"
																		title="Clear Date">
																</s:elseif> <s:elseif
																	test="isSendForReview==false && isStartDate ==false && #session.usertypename == 'WA'">
																	<input type="text"
																		name="attrValueId<s:property value="#elementid"/>"
																		class="attrValueIdDate" readonly="readonly"
																		id="attrValueId<s:property value="#elementid"/>"
																		value="<s:property value="attrValue"/>">
											
											 (YYYY/MM/DD) &nbsp;<IMG
																		onclick="if(document.getElementById('attrValueId<s:property value="#elementid"/>').value != '' 
														&& confirm('Are you sure?'))
														document.getElementById('attrValueId<s:property value="#elementid"/>').value = '';
																					"
																		src="<%=request.getContextPath()%>/images/clear.svg"
																		height="25px" width="25px" align="middle"
																		title="Clear Date">
																</s:elseif> <s:else>
																	<input type="text"
																		name="attrValueId<s:property value="#elementid"/>"
																		class="attrValueIdDate" disabled="disabled"
																		readonly="readonly"
																		id="attrValueId<s:property value="#elementid"/>"
																		value="<s:property value="attrValue"/>">
																</s:else></td>
														</tr>
														<s:set name="elementid" value="#elementid + 1"></s:set>
													</s:elseif>
													<s:elseif test="attrType == 'Combo'">
													<s:if test="#session.usertypename == 'WA' ">
													<s:if test="attrName != 'ManualSignature'">
														<s:if test="#prevattrid == -1 || #prevattrid != attrId">
															<tr class="none">
																<td class="title">${attrName }</td>
																<td><select  id="${attrName }" onchange = "ShowHideDiv()"
																	name="attrValueId<s:property value="#elementid"/>">
																		<s:set name="outterAttrId" value="attrId" />
																		<s:set name="outterAttrValue" value="attrMatrixValue" />
																		<s:if test="attrValue==''">
																			<OPTION value="">Please select value</OPTION>
																		</s:if>
																		<s:iterator value="attrDtl">
																			<s:if test="#outterAttrId == attrId">
																				<OPTION
																					value="<s:property value="attrMatrixValue" />"
																					<s:if test="attrMatrixValue == attrValue">selected="selected"</s:if>>
																					<s:property value="attrMatrixValue" /></OPTION>
																			</s:if>
																		</s:iterator>
																</select> <input type="hidden"
																	name="attrType<s:property value="#elementid"/>"
																	value="Combo"> <input type="hidden"
																	name="attrName<s:property value="#elementid"/>"
																	value="${attrName }"> <input type="hidden"
																	name="attrId<s:property value="#elementid"/>"
																	value="${attrId }"></td>
															</tr>
															<s:set name="elementid" value="#elementid + 1"></s:set>
														</s:if>
													</s:if>
													</s:if>
													<s:else>
													 <s:if test="attrName != 'ManualSignature' && attrName != 'Automated TM Required' "> 
														<s:if test="#prevattrid == -1 || #prevattrid != attrId">
															<tr class="none">
																<td class="title">${attrName }</td>
																<td><select  id="${attrName }" onchange = "ShowHideDiv()"
																	name="attrValueId<s:property value="#elementid"/>">
																		<s:set name="outterAttrId" value="attrId" />
																		<s:set name="outterAttrValue" value="attrMatrixValue" />
																		<s:if test="attrValue==''">
																			<OPTION value="">Please select value</OPTION>
																		</s:if>
																		<s:iterator value="attrDtl">
																			<s:if test="#outterAttrId == attrId">
																				<OPTION
																					value="<s:property value="attrMatrixValue" />"
																					<s:if test="attrMatrixValue == attrValue">selected="selected"</s:if>>
																					<s:property value="attrMatrixValue" /></OPTION>
																			</s:if>
																		</s:iterator>
																</select> <input type="hidden"
																	name="attrType<s:property value="#elementid"/>"
																	value="Combo"> <input type="hidden"
																	name="attrName<s:property value="#elementid"/>"
																	value="${attrName }"> <input type="hidden"
																	name="attrId<s:property value="#elementid"/>"
																	value="${attrId }"></td>
															</tr>
															<s:set name="elementid" value="#elementid + 1"></s:set>
														</s:if>
														 </s:if> 
														 </s:else>
														<s:set name="prevattrid" value="attrId" />
													</s:elseif>
													<s:elseif test="attrType == 'MultiSelectionCombo'">
														<s:if test="#prevattrid == -1 || #prevattrid != attrId">
														
															 <s:if test="attrName == 'URS Reference No.' ||	
																attrName=='FS Reference No.' ">
																<s:if test="URSTracebelityMatrixDtl.size>0" >
																	<s:if test="attrName == 'URS Reference No.' ">
																		<tr class="none">
																		<td class="title">${attrName }</td>
																		<td><select multiple="multiCheckUser"
																			class="assignUsers" id="userWiseGroupCode"
																			name="attrValueId<s:property value="#elementid"/>">
																				<s:set name="outterAttrId" value="attrId" />
																				<s:set name="outterAttrValue" value="attrMatrixValue" />
																				<s:set name="outterValue" value="attrValue" />
																				
																				<s:iterator value="URSTracebelityMatrixDtl">
																					<s:if test="#outterAttrId == attrId">
																						<s:if test="fileName == 'URS' ">
																							<option value="<s:property value="IDNo"/>"
																							title="<s:property value="nodeName"/>"
																								<s:if test="%{#outterValue.contains(IDNo)}">selected="selected"</s:if>>
							                													<s:property value="URSNo"/> <s:property value="URSDescription"/>
								            												</option>
																						</s:if>
																					</s:if>
																				</s:iterator>
																		</select> <input type="hidden"
																			name="attrType<s:property value="#elementid"/>"
																			value="MultiSelectionCombo"> <input
																			type="hidden"
																			name="attrName<s:property value="#elementid"/>"
																			value="${attrName }"> <input type="hidden"
																			name="attrId<s:property value="#elementid"/>"
																			value="${attrId }"></td>
																	</tr>
																	 <s:set name="elementid" value="#elementid + 1"></s:set> 
																	
																	</s:if>
																
																<s:if test="attrName == 'FS Reference No.' ">
																	 <tr class="none">
																		<td class="title">${attrName }</td>
																		<td><select multiple="multiCheckUser"
																			class="assignUsers" id="userWiseGroupCode"
																			name="attrValueId<s:property value="#elementid"/>">
																				<s:set name="outterAttrId" value="attrId" />
																				<s:set name="outterAttrValue" value="attrMatrixValue" />
																				<s:set name="outterValue" value="attrValue" />
																				
																				 <s:iterator value="URSTracebelityMatrixDtl">
																				 	<s:if test="#outterAttrId == attrId">
																						<s:if test="fileName == 'FS' ">
																							<option value="<s:property value="IDNo"/>"
																							title="<s:property value="nodeName"/>"
																							<s:if test="%{#outterValue.contains(IDNo)}">selected="selected"</s:if>>
							                													 <s:property value="FRSNo"/> <s:property value="FSDescription"/>
								            												</option>
																						</s:if>
																					</s:if>
																				</s:iterator> 
																		</select> <input type="hidden"
																			name="attrType<s:property value="#elementid"/>"
																			value="MultiSelectionCombo"> <input
																			type="hidden"
																			name="attrName<s:property value="#elementid"/>"
																			value="${attrName }"> <input type="hidden"
																			name="attrId<s:property value="#elementid"/>"
																			value="${attrId }"></td>
																	</tr>
																	 <s:set name="elementid" value="#elementid + 1"></s:set> 
																	 </s:if>
																 
																 </s:if>
																 																 
															 </s:if>
															 
															<s:else>
															 
															 	<tr class="none">
																	<td class="title">${attrName }</td>

																	<td><select multiple="multiple"
																		class="commentUsers" id="milestoneId"
																		name="attrValueId<s:property value="#elementid"/>">
																			<s:set name="outterAttrId" value="attrId" />
																			<s:set name="outterAttrValue" value="attrMatrixValue" />
																			<s:iterator value="attrDtl">
																				<s:if test="#outterAttrId == attrId">
																					<OPTION
																						value="<s:property value="attrMatrixValue" />"
																						<s:if test="%{attrValue.contains(attrMatrixValue)}">selected="selected"</s:if>>
																						<s:property value="attrMatrixValue" /></OPTION>
																				</s:if>
																			</s:iterator>
																	</select> <input type="hidden"
																		name="attrType<s:property value="#elementid"/>"
																		value="MultiSelectionCombo"> <input
																		type="hidden"
																		name="attrName<s:property value="#elementid"/>"
																		value="${attrName }"> <input type="hidden"
																		name="attrId<s:property value="#elementid"/>"
																		value="${attrId }"></td>
																</tr>
																 <s:set name="elementid" value="#elementid + 1"></s:set> 
															 
															 </s:else>
															
															<%-- <s:set name="elementid" value="#elementid + 1"></s:set> --%>
														</s:if>
														<s:set name="prevattrid" value="attrId" />
													</s:elseif>
													<s:elseif test="attrType == 'AutoCompleter'">
														<s:if test="#prevattrid == -1 || #prevattrid != attrId">
															<tr class="none">
																<td class="title">${attrName }</td>
																<td><s:iterator value="filterAutoCompleterList">
																		<s:if test="top[0] == attrId">
																			<s:autocompleter name="attrValueId%{#elementid}"
																				list="top[1]" listKey="top" listValue="top"
																				autoComplete="false"
																				cssStyle="margin: 0; padding: 2px; width:400px;"
																				dropdownHeight="145" loadOnTextChange="false"
																				resultsLimit="all" forceValidOption="true"
																				value="%{attrValue}">
																			</s:autocompleter>
																		</s:if>
																	</s:iterator> <input type="hidden"
																	name="attrType<s:property value="#elementid"/>"
																	value="AutoCompleter"> <input type="hidden"
																	name="attrName<s:property value="#elementid"/>"
																	value="${attrName }"> <input type="hidden"
																	name="attrId<s:property value="#elementid"/>"
																	value="${attrId }"></td>
															</tr>
															<s:set name="elementid" value="#elementid + 1"></s:set>
														</s:if>
														<s:set name="prevattrid" value="attrId" />
													</s:elseif>
													<s:elseif test="attrType == 'Dynamic'">
														<s:if test="#prevattrid == -1 || #prevattrid != attrId">
															<tr class="none">
																<td class="title">${attrName }</td>
																<td><s:iterator value="filterDynamicList">
																		<s:if test="top[0] == attrId">
																			<s:select list="top[1]" headerKey=""
																				headerValue="Please Select Value"
																				value="%{attrValue}" name="attrValueId%{#elementid}">
																			</s:select>
																		</s:if>
																	</s:iterator> <input type="hidden"
																	name="attrType<s:property value="#elementid"/>"
																	value="Dynamic"> <input type="hidden"
																	name="attrName<s:property value="#elementid"/>"
																	value="${attrName }"> <input type="hidden"
																	name="attrId<s:property value="#elementid"/>"
																	value="${attrId }"></td>
															</tr>
															<s:set name="elementid" value="#elementid + 1"></s:set>
														</s:if>
														<s:set name="prevattrid" value="attrId" />
													</s:elseif>
													
													
												</s:iterator>
												<s:if test="attrFlag==true">
													<tr class="none">
														<td class="title">Reason for Change</td>
														<td><input type="text" name="attrRemark"
															id="attrRemark"></input></td>
													</tr>
												</s:if>
													<%-- <br/>total :<s:property value="#elementid"/> --%>
												<tr class="none">
													<td><input type="hidden" name="attrCount"
														value="<s:property value="#elementid"/>"> <s:if
															test="lockSeqFlag == true ">
															<input type="button" class="button" value="Save"
																name="buttonName" onclick="lockSeq();"></input>
															<input type="button" class="button" value="Audit Trail"
																name="buttonName" style='margin-left: 16px'
																onclick="lockSeq();"></input>
														</s:if> <s:else>
															<s:submit cssClass="button" value="Save"
																name="buttonName" onclick="return showRemark();"></s:submit>
															<input type="button" class="button" value="Audit Trail"
																name="buttonName" style='margin-left: 16px'
																onclick="return nodeAttrDetailHistory(${nodeId});"></input>
																</s:else></td>
												</tr>
											</s:else>
											
										</table>
									</div>
									
								</td>
								
							</tr>			</table>
					</s:if>
					<s:else>
						<table width="100%" class="datatable" style="border: none;">
							<tr class="none">
								<th><b>Attribute Value</b></th>
							</tr>
							<tr class="none">
								<td><s:set name="attributeValue" value="%{''}" />
									<table class="doubleheight" id="AttributeValueShow">
										<s:iterator value="attrHistory">
											<s:if test="#attributeValue!=attrValue">
												<tr>
													<td class="title" align="left" style="padding-left: 8px;"
														width="20%">${attrName}</td>
													<td align="left" style="padding-left: 8px;"><s:if
															test="attrValue == ''">
														</s:if> ${attrValue }</td>
												</tr>
												<%-- <s:set name="attributeValue" value="%{attrValue}"></s:set> --%>
											</s:if>
										</s:iterator>
									</table></td>
							</tr>
							<tr class="none">
								<td><input type="button" class="button" value="Audit Trail"
									name="buttonName" style='margin-left: 16px'
									onclick="return nodeAttrDetailHistory(${nodeId});"></input></td>
							</tr>
						</table>
					</s:else>
					<s:if
						test="nodeActivities.size != 0 && workSpaceNodeDtl.requiredFlag != 'F'">
						<table width="100%" align="center">
							<tr>
								<td align="center">
									<hr color="#5A8AA9" size="1px"
										style="width: 100%; border-bottom: 2px solid #CDDBE4;">
								</td>
							</tr>
						</table>
					</s:if>
				</s:if>
				<s:if test="nodeActivities.size != 0 && iscreatedRights==true">
					<s:if test="workSpaceNodeDtl.requiredFlag != 'F'">
						<div id="selectActivity">
							<table>
								<tr>
									<s:if test="workSpaceNodeDtl.requiredFlag != 'A'"> 
										<td><b class="title">Iterate</b> &nbsp;&nbsp;&nbsp;&nbsp;
											<s:select list="nodeActivities" name="nodeActivity"
												headerKey="-1" headerValue="Select Activity" listKey="top"
												listValue="top">
											</s:select></td>

										<td style="padding-left: 20px;"><s:if
												test="lockSeqFlag == true ">
												<input type="button" value="  Go  " class="button"
													onclick="lockSeq();">
											</s:if> <s:else>
												<input type="button" value="  Go  " class="button"
													onclick="goToNodeActivity('${workSpaceNodeDtl.nodeId }');">
												<s:if test="isFailedScriptBtn==true">
															<input type="button" id="failScriptBtn" value="Add Failed Script" class="button"
																		onclick="RepeatFailedScript('<s:property value="nodeId"/>');">
												</s:if>
												&nbsp;&nbsp;
												<s:if test="traceblityButton==true && (#session.usertypename == 'WA' || iscreatedRights==true) ">
														<input type="button" style="background: green;" id="failScriptBtn" value="Traceability Matrix" class="button"
													onclick="return openTracebilityMatrix();">
												<input type="hidden" value="<s:property value='traceblityAlert'/>"
													name="traceblityAlert" id="traceblityAlert" style="display: none;">													
												</s:if>
											</s:else></td>
									 </s:if> 
								</tr>
							</table>
						</div>
					</s:if>
					<s:if test="workSpaceNodeDtl.requiredFlag != 'F'">
						<s:if test="workSpaceNodeDtl.requiredFlag != 'A'">
							<table width="100%" align="center">
								<tr>
									<td align="center">
										<hr color="#5A8AA9" size="1px"
											style="width: 100%; border-bottom: 2px solid #CDDBE4;">
									</td>
								</tr>
							</table>
						</s:if>
					</s:if>
				</s:if>
				<s:set name="nodeFileName" value="nodeHistory.get(0).fileName"></s:set>
				<s:set name="isStfXmlFile" value="false"></s:set>
				<s:set name="isNoFile" value="false"></s:set>
				<s:set name="isSTFChildNode" value="false"></s:set>
				<s:if test="#nodeFileName.equalsIgnoreCase('No File')">
					<s:set name="isNoFile" value="true"></s:set>
				</s:if>
				<s:if
					test="#nodeFileName.substring(0,4).equalsIgnoreCase('STF-') && #nodeFileName.substring(#nodeFileName.lastIndexOf('.')).equalsIgnoreCase('.xml')">
					<s:set name="isStfXmlFile" value="true"></s:set>
				</s:if>
				<s:if test="workSpaceNodeDtl.nodeTypeIndi == 'S'">
					<s:set name="isSTFChildNode" value="true"></s:set>
				</s:if>

				<div id="popupContact" class="popcuswidth" style="height: 475px;">
					<img alt="Close" title="Close" src="images/Common/Close.svg"
						style="height: 25px; width: 25px;" onclick='cls();'
						class='popupCloseButton' />
					<h3 style="margin-top: 2px;">Electronic Signature</h3>
					<!-- <div style="width: 100%; height: 90px; overflow: auto;"> -->
					<table id="tble" style="width: 100%">
						<tr>
							<td class="popUpTdHdr"><b>Username:&nbsp;</b></td>
							<td class="popUpTd"><span id="userName"></span></td>
						</tr>
						<tr>
							<td class="popUpLbl"><b>Date:&nbsp;</b></td>
							<td class="popUpTd"><span id="date"></span></td>
						</tr>
						<tr>
							<td class="popUpLbl"><b>Action:&nbsp;</b></td>
							<td class="popUpTd"><span id="stage"></span></td>
						</tr>
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
						<span style="color: #000000; font-size: 14px;"><b>I
								hereby confirm signing of this document electronically.</b></span><br>
						<br>
						<s:if test="lockSeqFlag == true ">
							<input type="button" class="button" value="Change"
								name="buttonName" onclick="lockSeq();"></input>
						</s:if>
						<s:else>
							<s:if test="#session.usertypename != 'Inspector' ">
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
				<div id="backgroundPopup"></div>
				<div id="loadingpopup"
					style="width: 50px; z-index: 1; position: absolute; top: 180.5px; left: 392.5px; padding-top: 0px; display: none">
					<center>
						<img style="margin-top: 100px" src="images/loading.gif"
							alt="loading ..." />
					</center>
				</div>
				<s:if
					test="isLeafNode !=0 && nodeHistory.size != 0 && #isNoFile == false">
					<s:if test="dtoWsNodeHistory != null">
						<table width="100%" class="datatable audittrailtable"
							style="border: none;" cellspacing="1" style="margin-left: 10px;">
							<thead>
								<tr class="none" style="background: none;">
									<td colspan="5" class="title"
										style="padding-left: 3px; border: none;">Latest <s:property
											value="dtoWsNodeHistory.stageDesc" /> Document
									</td>
								</tr>
								<tr>
									<th>Last Modified Date</th>
									<s:if test="#session.countryCode != 'IND'">
										<th>Eastern Standard Time</th>
									</s:if>
									<th>${ lbl_folderName }</th>
									<th>Size</th>
									<!-- <th>Version</th> -->
									<th>Username</th>
									<th>User Role</th>
									<th>Publish</th>
								</tr>
							</thead>
							<tbody>

								<tr class="odd">
									<!-- <td><s:date name="dtoWsNodeHistory.modifyOn" format="dd-MMM-yyyy HH:mm"/></td> -->

									<td><s:property value="dtoWsNodeHistory.ISTDateTime" /></td>
									<s:if test="#session.countryCode != 'IND'">
										<td><s:property value="dtoWsNodeHistory.ESTDateTime" /></td>
									</s:if>

									<td><s:if
											test="#isStfXmlFile == true || #isNoFile == true">
											<s:property value="dtoWsNodeHistory.fileName" default="-" />
										</s:if> <s:else>
											<s:if test="#session.usertypename == 'WA'">
												<s:if test="dtoWsNodeHistory.fileExt=='PDF' || dtoWsNodeHistory.fileExt=='pdf'">
												<a href="#"
													onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="dtoWsNodeHistory.workSpaceId"/>&nodeId=<s:property value="dtoWsNodeHistory.nodeId"/>&tranNo=<s:property value="dtoWsNodeHistory.tranNo"/>&fileName=<s:property value="dtoWsNodeHistory.fileName"/>&baseWorkFolder=<s:property value="dtoWsNodeHistory.baseWorkFolder"/>&fileExt=<s:property value="dtoWsNodeHistory.fileExt"/>');">
													<s:property value="dtoWsNodeHistory.fileName" default="-" />
												</a>
												</s:if>
												<s:else>
												<a href="#"
													onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="dtoWsNodeHistory.workSpaceId"/>&nodeId=<s:property value="dtoWsNodeHistory.nodeId"/>&tranNo=<s:property value="dtoWsNodeHistory.tranNo"/>&fileName=<s:property value="dtoWsNodeHistory.fileName"/>&baseWorkFolder=<s:property value="dtoWsNodeHistory.baseWorkFolder"/>&fileExt=<s:property value="dtoWsNodeHistory.fileExt"/>');">
													<s:property value="dtoWsNodeHistory.fileName" default="-" />
												</a>
												</s:else>
												
												
											</s:if>
											<s:else>
												<s:if test="OpenFileAndSignOff=='Yes'">
													<s:if test="dtoWsNodeHistory.fileExt=='PDF' || dtoWsNodeHistory.fileExt=='pdf'">
													<a href="#"
														onclick="return fileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="dtoWsNodeHistory.workSpaceId"/>&nodeId=<s:property value="dtoWsNodeHistory.nodeId"/>&tranNo=<s:property value="dtoWsNodeHistory.tranNo"/>&fileName=<s:property value="dtoWsNodeHistory.fileName"/>&baseWorkFolder=<s:property value="dtoWsNodeHistory.baseWorkFolder"/>&fileExt=<s:property value="dtoWsNodeHistory.fileExt"/>');">
														<s:property value="dtoWsNodeHistory.fileName" default="-" />
													</a>
													</s:if>
													<s:else>
													<a href="#"
														onclick="return docFileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="dtoWsNodeHistory.workSpaceId"/>&nodeId=<s:property value="dtoWsNodeHistory.nodeId"/>&tranNo=<s:property value="dtoWsNodeHistory.tranNo"/>&fileName=<s:property value="dtoWsNodeHistory.fileName"/>&baseWorkFolder=<s:property value="dtoWsNodeHistory.baseWorkFolder"/>&fileExt=<s:property value="dtoWsNodeHistory.fileExt"/>');">
														<s:property value="dtoWsNodeHistory.fileName" default="-" />
													</a>
													</s:else>
												</s:if>
												<s:else>
													<s:if test="dtoWsNodeHistory.fileExt=='PDF' || dtoWsNodeHistory.fileExt=='pdf'">
												<a href="#"
													onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="dtoWsNodeHistory.workSpaceId"/>&nodeId=<s:property value="dtoWsNodeHistory.nodeId"/>&tranNo=<s:property value="dtoWsNodeHistory.tranNo"/>&fileName=<s:property value="dtoWsNodeHistory.fileName"/>&baseWorkFolder=<s:property value="dtoWsNodeHistory.baseWorkFolder"/>&fileExt=<s:property value="dtoWsNodeHistory.fileExt"/>');">
													<s:property value="dtoWsNodeHistory.fileName" default="-" />
												</a>
												</s:if>
												<s:else>
												<a href="#"
													onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="dtoWsNodeHistory.workSpaceId"/>&nodeId=<s:property value="dtoWsNodeHistory.nodeId"/>&tranNo=<s:property value="dtoWsNodeHistory.tranNo"/>&fileName=<s:property value="dtoWsNodeHistory.fileName"/>&baseWorkFolder=<s:property value="dtoWsNodeHistory.baseWorkFolder"/>&fileExt=<s:property value="dtoWsNodeHistory.fileExt"/>');">
													<s:property value="dtoWsNodeHistory.fileName" default="-" />
												</a>
												</s:else>
												</s:else>
											</s:else>
										</s:else></td>
									<td><s:if
											test="dtoWsNodeHistory.historyDocumentSize.sizeMBytes>0">
											<s:property
												value="dtoWsNodeHistory.historyDocumentSize.sizeMBytes"
												default="--" /> MB
								</s:if> <s:else>
											<s:property
												value="dtoWsNodeHistory.historyDocumentSize.sizeKBytes"
												default="--" /> KB
								</s:else></td>

									<%-- <td><s:property value="dtoWsNodeHistory.userDefineVersionId"
								default="-" /></td> --%>

									<td><s:property value="dtoWsNodeHistory.userName"
											default="-" /></td>
									<td><s:property value="dtoWsNodeHistory.roleName"
											default="-" /></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<div height=30px; width=40px; id="downloading_approved"
												style="float: right; left: 600px; top: 590px;">
												<img src="images/loading.gif" alt="loading ..." />
											</div>
											<s:if test=" #isNoFile == true ">
												<center>-</center>
											</s:if>
											<s:else>
												<center>
													<s:if test="isCertified==false">
													<a><img id="imgPdf" height="25px" width="25px"
														src="images/Common/open.svg" title="Publish"
														onclick="downloadPdfForCertified('approved');" /></a>
												</s:if>
												<s:else>
													<a><img id="imgPdf" height="25px" width="25px"
														src="images/Common/open.svg" title="Publish"
														onclick="downloadPdf('approved');" /></a>
												</s:else>
												</center>
											</s:else>
										</s:subset></td>
								</tr>
							</tbody>
						</table>
						<br />
					</s:if>
					<s:if test="#isSTFChildNode == false">
						<table width="100%">
							<tr>
								<td><b class="title">Change<s:if test="appType == 0001"> Document&nbsp;</s:if>
										Status &nbsp;
								</b> <s:if test="stageuserdtl.size != 0">

										<input type="hidden" value="<s:property value='elecSig'/>"
											name="elecSig" id="elecSig" style="display: none;">
										<input type="hidden"
											value="<s:property value='#session.password'/>"
											name="sessPass" id="sessPass" style="display: none;">

										<input type="hidden" name="remark" id="remark"
											style="display: none;">
										<s:select list="stageuserdtl" name="stageCode"
											listKey="stageId" id="stageCode" listValue="stageDesc"
											value="%{selectedStage}">

										</s:select>
					&nbsp;&nbsp;&nbsp;
						 <s:if test="nodeHistory.size==0">
											<s:submit cssClass="button" value="Change" name="buttonName"
												disabled="true"></s:submit>
						</s:if>
						<s:else>
											<!-- <input type="hidden" value="<s:property value='elecSig'/>"
									name="elecSig" id="elecSig" style="display: none;">
								<input type="hidden"
									value="<s:property value='#session.password'/>" name="sessPass"
									id="sessPass" style="display: none;">-->
											<s:if test="lockSeqFlag == true ">
												<input type="button" class="button" value="Change"
													name="buttonName" onclick="lockSeq();"></input>
											</s:if>
											<s:else>
												<s:if test="isVoidFlag == true">
													<input type="button" class="button" value="Change"
														id="Change" name="buttonName"
														onclick="return chgStatus1(this.id);"></input>
												</s:if>
											</s:else>
						</s:else>

						</s:if> 
						<s:else>
							<select name="stageCode">
								<option value="-1">None</option>
							</select>&nbsp;&nbsp;&nbsp;
						</s:else></td>
								<td><s:if
										test="stageuserdtl.size > 1 && isVoidFlag == true">
										<input type="button" value="Void" class="button" name="void"
											id="voidFile" onclick="verifyVoidFile(this.id);"
											style="padding-left: 7px;" />
									</s:if></td>
								<td style="padding-right: 240px !important;"><s:if
										test="lockSeqFlag == true ">
										<input type="button" value="Certified File" class="button"
											name="Certified File" id="certifiedFile" onclick="lockSeq();"
											style="padding-left: 7px;" />
									</s:if> <s:else>
										<s:if
											test="stampFlag==true && stageId == 10 && #isNoFile==false && userWiseStageFlag==false && appType == '0003' && createandApprovedRights==true">
											<input type="button" value="Certified File" class="button"
												name="Certified File" id="certifiedFile"
												onclick="certifiedfile('<s:property value="nodeId"/>',this.id)"
												style="padding-left: 7px;" />
										</s:if>
										<s:if
											test="stageId == 10 && #isNoFile==false && userWiseStageFlag==false && signatureReqCrtifiedBtn==true && isCertified == true">
											<input type="button" value="Certified File" class="button"
												name="Certified File" id="certifiedFile"
												onclick="certifiedfileForAttr('<s:property value="nodeId"/>',this.id)"
												style="padding-left: 7px;" />
										</s:if>
									</s:else></td>

							</tr>
						</table>
					</s:if>
				</s:if>
				<s:if
					test="isLeafNode !=0 && !(workSpaceNodeDtl.requiredFlag == 'S' && workSpaceNodeDtl.cloneFlag == 'N')">
					<table>
						<s:if test="isNodeLocked==true">
							<!-- <tr>
						<s:iterator value="checkedOutBy">
							<td><font color="red" size="2"> <b>This Node is
							Checked Out By <s:property value="userName" /></b> </font></td>
							<td><font color="red" size="2"> ,Since <s:date
								name="modifyOn" format="dd-MMM-yyyy HH:mm" /> </font></td>
						</s:iterator>

					</tr>-->


							<tr>
								<s:iterator value="checkedOutBy">
									<td><font color="red" size="3"> <b>This <s:property
													value="lbl_folderName" /> is Checked Out By <s:property
													value="userName" /></b> at <s:property value="ISTDateTime" />
									</font></td>
									<%-- <td>
						<font color="red" size="2"> 
						   ,Since <s:property value="ISTDateTime" />
						 </font>
					 </td> --%>
								</s:iterator>

							</tr>
							<tr>
								<s:if test="#session.countryCode != 'IND'">
									<s:iterator value="checkedOutBy">
										<td colspan="2" style="float: right;"><font color="red"
											size="3"> <s:property value="ESTDateTime" />
										</font></td>
									</s:iterator>
								</s:if>
							</tr>
						</s:if>
						<s:if
							test="iscreatedRights==true && isNodeLocked==false && canEditFlag == 'Y' && #isStfXmlFile == false">
							<tr>
								<td><input type="hidden" name="projectStatus"
									value="${projectStatus}">
									<table>
										<tr>
											<s:if
												test="(stageId == 20 || stageId == 30 ||stageId == 100) && (currentSeqNo == '' || currentSeqNo == null )">
											</s:if>
											<s:else>
												<td style="padding-right: 10px;"><s:if
														test="lockSeqFlag == true ">
														<input type="button" class="button" value="Lock Document"
															name="buttonName" onclick="lockSeq();"></input>
													</s:if> <s:else>
														<%-- <s:if test="projectTimeLine == 'Yes' && nextStageFlag==true && #session.usertypename == 'WU' && isStartDate==true">
								    	<s:submit value="Lock Node" cssClass="button" name="buttonName"></s:submit>
								    </s:if> --%>
														<s:if
															test="projectTimeLine == 'Yes' && nextStageFlag==true && #session.usertypename == 'WU' && appType != '0003'">
															<s:submit value="Lock Document" cssClass="button"
																name="buttonName"></s:submit>
														</s:if>
														<s:if
															test="nextStageFlag==true && #session.usertypename == 'WU' && projectTimeLine == 'No'">
															<s:submit value="Lock Document" cssClass="button"
																name="buttonName"></s:submit>
														</s:if>
														<s:if
															test="nextStageFlag==true && #session.usertypename == 'WU' && appType == '0003'">
															<s:submit value="Lock Document" cssClass="button"
																name="buttonName"></s:submit>
														</s:if>
														<%--   <s:if test="nextStageFlag==true && #session.usertypename == 'WA' && appType == 'Archive'">
								  	<s:submit value="Lock Node" cssClass="button" name="buttonName"></s:submit>
								  </s:if> --%>
													</s:else></td>
											</s:else>
											<td style="padding-right: 10px;"><s:if
													test="nodeHistory.size > 0">
													<s:submit value="View History" cssClass="button"
														name="buttonName"></s:submit>
												</s:if></td>
											<s:if
												test="nodeType==true && stageId == 10 && #isNoFile==false && userWiseStageFlag==false && appType == '0002' && showSendForReviewBtn==true ">
												
												<td style="padding-right: 10px;"><input type="button"
													class="button" id="sendReview" value="Send for Review"
													onclick="fileUploadSeq(this.id);"></td>
											</s:if>
											<s:if
												test="PQPreApprovalPopup=='Yes' && appType != '0003' && isRequiredValidate == true && #isNoFile==false && showValidateBtn==true">
												<td><input type="button" id="validateButtonId"
													value="Extract" class="button"
													onclick="openPreApprovalPopup('<s:property value="PreApprovalDocPath"/>')">
												</td>
											</s:if>
											
											<!-- Button for automated TM  -->
										<s:if test="iscreatedRights==true || #session.usertypename == 'WA' ">
											<s:if
												test="Automated_TM_Required=='Yes' && (Automated_Doc_Type=='URS' || Automated_Doc_Type=='FS')
													 && showAutomateButton==true && stageId != 0">
												<td><input type="button" id="validateButtonId"
													value="<s:property value="Automated_Doc_Type"/> Extraction" class="button"
													onclick="openAutomatePopup('<s:property value="PreApprovalDocPath"/>')">
												</td>
											</s:if>
										</s:if>
											
											<!-- Button for automated TM ends -->
											
										</tr>
									</table></td>
							</tr>
						</s:if>
						<s:if test="isNodeLocked==true">
							<tr>
								<s:if test="checkedOutBy.size !=0 || checkedOutBy !=null">
									<s:subset source="checkedOutBy" count="1" start="0">
										<s:iterator>
											<s:set name="checkedOutUser" value="userName" />
										</s:iterator>
									</s:subset>
									<s:if test="#checkedOutUser == #session.username">
										<input type="hidden" value="${session.username}"
											name="actualuserName">
										<td>
											<table>
												<tr>
													<s:if
														test="appType == '0002' && alloweTMFCustomization == 'yes' ">
														<s:if
															test="(stageId == 100 || stageId == 30) && (currentSeqNo == '' || currentSeqNo == null )">

														</s:if>

														<s:elseif
															test="(stageId == 100 || stageId == 30) && (currentSeqNo != '' || currentSeqNo != null )">

															<td style="padding-right: 10px;"><s:submit
																	value="Unlock Document" cssClass="button"
																	name="buttonName" id="unlockbtn"></s:submit></td>
														</s:elseif>
														<s:else>
															<td style="padding-right: 10px;"><s:submit
																	value="Unlock Document" cssClass="button"
																	name="buttonName" id="unlockbtn"></s:submit></td>
														</s:else>
													</s:if>
													<s:else>
														<td style="padding-right: 10px;"><s:submit
																value="Unlock Document" cssClass="button"
																name="buttonName"></s:submit></td>
													</s:else>
													<td><s:if test="nodeHistory.size > 0">
															<s:submit value="View History" cssClass="button"
																name="buttonName"></s:submit>
														</s:if></td>
													<td style="padding-right: 15px; padding-left: 5px;"><s:if
															test="nodeType==true && stageId == 10 && #isNoFile==false && userWiseStageFlag==false && appType == '0002' && showSendForReviewBtn==true">
															<!-- 2nd if -->
															<input type="button" class="button" id="sendReview"
																value="Send for Review"
																onclick="fileUploadSeq(this.id);">
														</s:if> <!-- </td> --> <s:if
															test="PQPreApprovalPopup=='Yes' && appType != '0003' && isRequiredValidate == true && #isNoFile==false && showValidateBtn==true">
															<!-- <td> -->
															<input type="button" id="validateButtonId"
																value="Extract" class="button"
																onclick="openPreApprovalPopup('<s:property value="PreApprovalDocPath"/>')">
														</s:if>
														
														<!-- Button for automated TM  -->
											<s:if test="iscreatedRights==true || #session.usertypename == 'WA' "> 
											<s:if
												test="Automated_TM_Required=='Yes' && (Automated_Doc_Type=='URS' || Automated_Doc_Type=='FS')
												 && showAutomateButton==true && stageId != 0 ">
												<td><input type="button" id="validateButtonId"
													value="<s:property value="Automated_Doc_Type"/> Extraction" class="button"
													onclick="openAutomatePopup('<s:property value="PreApprovalDocPath"/>')">
												</td>
											</s:if>
										</s:if>
											
											<!-- Button for automated TM ends -->
														
														</td>	
												</tr>
												
											</table>
										</td>
									</s:if>
									<s:else>
										<td><s:if test="nodeHistory.size > 0">
												<s:submit value="View History" cssClass="button"
													name="buttonName"></s:submit>
											</s:if> <!-- </td> --> <s:if
												test="PQPreApprovalPopup=='Yes' && appType != '0003' && isRequiredValidate == true && #isNoFile==false && showValidateBtn==true">
												<!-- <td style="padding-left: 10px;"> -->
												<input type="button" id="validateButtonId" value="Extract"
													class="button"
													onclick="openPreApprovalPopup('<s:property value="PreApprovalDocPath"/>')">

											</s:if>
											
											<!-- Button for automated TM  -->
											<s:if test="iscreatedRights==true || #session.usertypename == 'WA' "> 
											<s:if
												test="Automated_TM_Required=='Yes' && (Automated_Doc_Type=='URS' || Automated_Doc_Type=='FS')
												 && showAutomateButton==true && stageId != 0 ">
												<td><input type="button" id="validateButtonId"
													value="<s:property value="Automated_Doc_Type"/> Extraction" class="button"
													onclick="openAutomatePopup('<s:property value="PreApprovalDocPath"/>')">
												</td>
											</s:if>
										</s:if>
											
											<!-- Button for automated TM ends -->
											
											</td>
									</s:else>
									
								</s:if>
							</tr>
						</s:if>
						<%-- <s:else>
				   <td><s:if test="nodeHistory.size > 0">
					   <s:submit value="View History" cssClass="button" name="buttonName"></s:submit>
						</s:if>
				   </td>
				</s:else> --%>
						<s:if test="iscreatedRights!=true && isNodeLocked==false">
							<td><s:if test="nodeHistory.size > 0">
									<s:submit value="View History" cssClass="button"
										name="buttonName"></s:submit>
								</s:if> <!-- </td> --> <s:if
									test="PQPreApprovalPopup=='Yes' && appType != '0003' && isRequiredValidate == true && #isNoFile==false && showValidateBtn==true">
									<!-- <td style="padding-left: 10px;"> -->
									<input type="button" id="validateButtonId" value="Extract"
										class="button"
										onclick="openPreApprovalPopup('<s:property value="PreApprovalDocPath"/>')">

								</s:if>
								
								<!-- Button for automated TM  -->
											<s:if test="iscreatedRights==true || #session.usertypename == 'WA' "> 
											<s:if
												test="Automated_TM_Required=='Yes' && (Automated_Doc_Type=='URS' || Automated_Doc_Type=='FS')
												 && showAutomateButton==true && stageId != 0 ">
												<td><input type="button" id="validateButtonId"
													value="<s:property value="Automated_Doc_Type"/> Extraction" class="button"
													onclick="openAutomatePopup('<s:property value="PreApprovalDocPath"/>')">
												</td>
											</s:if>
										</s:if>
											
											<!-- Button for automated TM ends -->
								</td>
						</s:if>

					</table>
					<%-- showSendForReviewBtn : <s:property value="showSendForReviewBtn"/>
					isVoidFlag : <s:property value="isVoidFlag"/>
					nodeType : <s:property value="nodeType"/>
					stageId : <s:property value="stageId"/>
					#isNoFile : <s:property value="#isNoFile"/>
					userWiseStageFlag : <s:property value="userWiseStageFlag"/>
					appType : <s:property value="appType "/> --%>
					
					<s:if test="nodeType==true && stageId == 10 && #isNoFile==false && userWiseStageFlag==false && 
						appType == '0002' && showSendForReviewBtn==true">
						<%-- <s:if test="nextstageuserdtl.size>0"> --%> 
							<font color="green" size="3">
								<b> Reviewer: </b> 
								<s:property value="nextstageuserdtl" /> 
							</font>
								<br/>
						<%-- </s:if> --%>
					 </s:if> 
					<%-- isVoidFlag : <s:property value="isVoidFlag "/>  --%>
					 <s:if test="userWiseStageFlag == true && stageuserdtl.size > 0"> 
						<s:if test="nextstageuserdtl != null && !nextstageuserdtl.isEmpty()">
						<%-- <s:if test="nextstageuserdtl.size>0"> --%>
							<font color="green" size="3">
								<b> Approver: </b> 
								<s:property value="nextstageuserdtl" /> 
							</font>
						<%-- </s:if> --%>
						</s:if>
							<br/>
					</s:if>
					
					<s:if test="nodeHistory.get(0).getStageId() != 100">
						<br />
						<table width="100%" class="datatable paddingtable audittrailtable"
							style="border: none;" cellspacing="1" style="margin-left: 10px;">
							<thead>
								<!-- <tr class="none" style="background: none;">
							<td colspan="6" class="title label">Current Document</td>
						</tr> -->
								<tr>
									<th>Last Modified Date</th>
									<s:if test="#session.countryCode != 'IND'">
										<th>Eastern Standard Time</th>
									</s:if>
									<th>${ lbl_folderName }</th>
									<th>Size</th>
									<!--  <th>Version</th> -->
									<th>Modified by</th>
									<th>User Role</th>
									<th>Status</th>
									<th>Sent for</th>
									<th>Certified</th>
									<th>Publish</th>
							</thead>
							<tbody>
								<tr class="odd">
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<!-- <s:date name="modifyOn" format="dd-MMM-yyyy HH:mm" /> -->
												<s:property value="ISTDateTime" />
											</s:iterator>
										</s:subset></td>
									<s:if test="#session.countryCode != 'IND'">
										<td><s:subset source="nodeHistory" count="1" start="0">
												<s:iterator>
													<s:property value="ESTDateTime" />
												</s:iterator>
											</s:subset></td>
									</s:if>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:if test="#isStfXmlFile == true || #isNoFile == true">
													<s:property value="fileName" default="-" />
												</s:if>
												<s:else>
													<s:if test="#session.usertypename == 'WA'">
														<s:if test="fileExt =='PDF' || fileExt =='pdf'">
														<a href="#"
															onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
															<s:property value="fileName" default="-" />
														</a>
														</s:if>
														<s:else>
														<a href="#"
															onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
															<s:property value="fileName" default="-" />
														</a>
														</s:else>
													</s:if>
													<s:else>
														<s:if test="OpenFileAndSignOff=='Yes'">
														<s:if test="fileExt =='PDF' || fileExt =='pdf'">
															<a href="#"
																onclick="return fileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
																<s:property value="fileName" default="-" />
															</a>
														</s:if>
														<s:else>
														<a href="#"
																onclick="return docFileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
																<s:property value="fileName" default="-" /></a>
														</s:else>
														</s:if>
														<s:else>
															<s:if test="fileExt =='PDF' || fileExt =='pdf'">
														<a href="#"
															onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
															<s:property value="fileName" default="-" />
														</a>
														</s:if>
														<s:else>
														<a href="#"
															onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>&fileExt=<s:property value="fileExt"/>');">
															<s:property value="fileName" default="-" />
														</a>
														</s:else>
														</s:else>
													</s:else>
												</s:else>
											</s:iterator>
										</s:subset></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:if test="historyDocumentSize.sizeMBytes>0">
													<s:property value="historyDocumentSize.sizeMBytes"
														default="--" /> MB
								</s:if>
												<s:else>
													<s:property value="historyDocumentSize.sizeKBytes"
														default="--" /> KB
								</s:else>
											</s:iterator>
										</s:subset></td>
									<%--  <td>${userDefineId}</td>  --%>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:property value="userName" default="-" />
											</s:iterator>
										</s:subset></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:property value="roleName" default="-" />
											</s:iterator>
										</s:subset></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:if test="stageId==10 && FileType !='SR'">Uploaded</s:if>
												<s:else>
													<s:property value="stageDesc" default="--" />
												</s:else>
											</s:iterator>
										</s:subset></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:if test="stageId==10 && FileType ==''">-</s:if>
												<s:elseif test="stageId==10 && FileType =='SR'">Review</s:elseif>
												<s:elseif test="stageId==20">Approve</s:elseif>
												<s:elseif test="stageId==0">Create</s:elseif>
												<s:else>-</s:else>

												<%-- <s:property value="FileType" default="--" /> --%>
											</s:iterator>

										</s:subset></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:property value="defaultFileFormat" default="-" />
											</s:iterator>
										</s:subset></td>
									<td><s:subset source="nodeHistory" count="1" start="0">
											<s:iterator>
												<s:if test="stageId==0 && isCertified==true">-</s:if>
												<s:elseif test="stageId==10 && FileType =='' && isCertified==true">-</s:elseif>
												<s:else>
													<div id="downloading"
														style="height: 30px; width: 40px; float: right; left: 590px; top: 490px;">
														<img src="images/loading.gif" alt="loading ..." />
													</div>
													<s:if test=" #isNoFile == true ">
														<center>-</center>
													</s:if>
													<s:else>
														<center>
														<s:if test="isCertified==false">
															<a><img id="imgPdf" height="25px" width="25px"
																src="images/Common/open.svg" title="Publish"
																onclick="downloadPdfForCertified('approved');" /></a>
														</s:if>
														<s:else>
															<a><img id="imgPdf" height="25px" width="25px"
																src="images/Common/open.svg" title="Publish"
																onclick="downloadPdf('other');" /></a>
														</s:else>
														</center>
													</s:else>
													</s:else>
											</s:iterator>
										</s:subset></td>
								</tr>
							</tbody>
						</table>
					</s:if>
					<s:if test="nodeHistory.size() == 0">
						<br />
						<table width="100%" class="datatable paddingtable audittrailtable"
							style="border: none;" cellspacing="1" style="margin-left: 10px;">
							<thead>
								<!-- <tr class="none" style="background: none;">
							<td colspan="6" class="title label">Current Document</td>
						</tr> -->
								<tr>
									<th>Last Modified Date</th>
									<s:if test="#session.countryCode != 'IND'">
										<th>Eastern Standard Time</th>
									</s:if>
									<th>${ lbl_folderName }</th>
									<th>Size</th>
									<!-- <th>Version</th> -->
									<th>Username</th>
									<th>User Role</th>
									<th>Status</th>
									<th>Sent for</th>
									<th>Publish</th>
									<!-- <th>Certified</th> -->
								</tr>
							</thead>
							<tbody>
								<tr class="odd">
									<td>-</td>
									<s:if test="#session.countryCode != 'IND'">
										<td>-</td>
									</s:if>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<!-- <td>-</td> -->
									<td>-</td>
									<td>-</td>
									<!-- <td>-</td> -->
									<td>-</td>
									<td>-</td>
								</tr>
							</tbody>
						</table>
					</s:if>
					<br />
					<table width="100%" align="center">
						<tr>
							<td align="center">
								<hr color="#5A8AA9" size="1px"
									style="width: 100%; border-bottom: 2px solid #CDDBE4;">
							</td>
						</tr>
					</table>
					<s:if test='userTypeName =="WU" && appType != "0003"'>
						<s:tabbedPanel id="panel_1" cssStyle="padding: 5px;">
							<s:if test="iscreatedRights!=true && nodeDocHistory.size()<=0">
								<center style="margin-top: 10px;">
									<span> No details available.</span>
								</center>
							</s:if>
							<s:if test="srcDocPath != 'No_Path_Found'">
								<s:div id="sourceDoc" label="Source Document" theme="ajax"
									labelposition="top"
									cssStyle="max-height: 240px;overflow-y: auto;/* height: 242px; */margin-left: 5px;padding-bottom: 10px;">
									<s:if test="canEditFlag == 'Y'">
										<%-- <s:property value="confirmAndUpload"/> --%>
										<%-- <s:if test="iscreatedRights==true || wsNodeHistory.size==0">
						<s:if test="wsNodeHistory.get(0).getStageId()==0 || wsNodeHistory.size<=0"> --%>
										<s:if test="iscreatedRights==true || wsNodeHistory.size==0">
											<div id="optionID"
												style="margin-left: 12px; margin-top: 10px;">
												<b class="title">Select Option : </b>
												&nbsp;&nbsp;&nbsp;&nbsp; <select id="srcDocOption"
													name="srcDocOption" headerKey="-1"
													headerValue="Select Option" onchange="changeBtnName()">
													<option value="-1">Please Select</option>
													<option value="createSourceDocument">Create</option>
													<option value="uploadSourceDocument">Upload</option>
												</select>
											</div>

											<div id="createOrUpload"
												style="padding: 10px; display: flex;">
												<span id="uploadSrcDocspn" class="title">Upload
													Source Document:</span> &nbsp;
												<s:file name="uploadDoc" required="true" id="uploadDoc"></s:file>
												&nbsp;
												<s:submit value="Upload" cssClass="button" id="uploadDocBtn"
													name="buttonName" onclick="return DMSvalidate();"></s:submit>
												<!-- <input type="submit" value="Upload" class="button" id="uploadDocBtn" name="buttonName" onclick="return DMSvalidate();"/> -->

												<div id="createUploading"
													style="height: 30px; width =: 40px; float: right; left: 600px; top: 590px;">
													<img src="images/loading.gif" alt="loading ..." />
												</div>
												<input type="button" class="button" value="Create"
													id="createDocBtn" name="buttonName"
													onclick="CreateBlankFileToSharePoint();" />
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
														<s:if test="#session.countryCode != 'IND'">
															<th style="border: 1px solid;">Eastern Standard Time</th>
														</s:if>
														<!-- <th>Edit</th> -->
														<!-- <th>Remark</th> -->
														<th style="border: 1px solid;">Modified by</th>
														<th style="border: 1px solid;">Modified on</th>
														<s:if test="#session.countryCode != 'IND'">
															<th style="border: 1px solid;">Eastern Standard Time</th>
														</s:if>
														<th colspan="3" style="border: 1px solid;">Action</th>
													</tr>
												</thead>
												<tbody>


													<tr class="odd">
														<!-- <td><s:date name="#srcDoc.uploadedOn" format="dd-MMM-yyyy HH:mm"/></td> -->
														<td style="border: 1px solid;"><s:if
																test="#srcDoc.docContentType==null">
																<a
																	href="openfile.do?fileWithPath=<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/
										${fileNameToShow}"
																	target="_blank">${newFileName}</a>
															</s:if> <s:else>
																<a
																	href="openfile.do?fileWithPath=<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>"
																	target="_blank">${newFileName} </a>
															</s:else></td>
														<td style="border: 1px solid;"><s:property
																value="#srcDoc.UploadedByUser" /></td>
														<td style="border: 1px solid;"><s:property
																value="#srcDoc.ISTDateTime" /></td>
														<s:if test="#session.countryCode != 'IND'">
															<td style="border: 1px solid;"><s:property
																	value="#srcDoc.ESTDateTime" /></td>
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
														<td style="border: 1px solid;"><s:property
																value="#srcDoc.modifyByUser" /></td>
														<td style="border: 1px solid;"><s:property
																value="#srcDoc.ISTDateTime" /></td>
														<s:if test="#session.countryCode != 'IND'">
															<td style="border: 1px solid;"><s:property
																	value="#srcDoc.ESTDateTime" /></td>
														</s:if>
														<td style="border: 1px solid;" id="rsButton">
															<div height=30px; width=40px; id="uploading"
																style="float: left;">
																<img src="images/loading.gif" alt="loading ..." />
															</div> <s:if test="#srcDoc.docContentType==null">
																<input type="button" id="reviewSPDoc"
																	style="font-size: 12px;" value="Edit" class="button"
																	onclick="UploadFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}');">
															</s:if> <s:else>
																<input type="button" id="reviewSPDoc"
																	style="font-size: 12px;" value="Edit" class="button"
																	onclick="UploadFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');">
															</s:else> <s:if test="#srcDoc.docContentType==null">
																<input type="button" id="SaveNContinue"
																	style="font-size: 12px;" value="Save & Continue"
																	class="button"
																	onclick="DownloadFileFromSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}');">
															</s:if> <s:else>
																<input type="button" id="SaveNContinue"
																	style="font-size: 12px;" value="Save & Continue"
																	class="button"
																	onclick="DownloadFileFromSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');">
															</s:else>
														</td>

														<td style="border: 1px solid;" id="rsButton"><s:if
																test="IsComment==true">

																<div id="Spdownloading"
																	style="float: left; height: 30px; width: 40px;">
																	<img src="images/loading.gif" alt="loading ..." />
																</div>
																<br />
																<s:if test="#srcDoc.docContentType==null">
																	<input type="button" id="comment"
																		style="font-size: 12px;" value="View" class="button"
																		onclick="CommentFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}');">
																	<br />
																</s:if>
																<s:else>
																	<input type="button" id="comment"
																		style="font-size: 12px;" value="View" class="button"
																		onclick="CommentFileToSharePoint('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>');">
																	<br />
																</s:else>
																<br />
															</s:if> <s:else>
																<span style="padding-right: 10px;">-</span>
															</s:else></td>
														<td style="border: 1px solid;" id="validateButton"><s:if
																test="PQPreApprovalPopup=='Yes' && isRequiredValidate == true && IsUpload==true">
																<div id="validateUploading"
																	style="height: 30px; width: 40px; float: left;">
																	<img src="images/loading.gif" alt="loading ..." />
																</div>
																<s:if test="#srcDoc.docContentType==null">
																	<input type="button" id="SrcValidateButtonId"
																		style="font-size: 12px;" value="Extract"
																		class="button"
																		onclick="openPreApprovalPopupFromSrc('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/${fileNameToShow}')">
																</s:if>
																<s:else>
																	<input type="button" id="SrcValidateButtonId"
																		style="font-size: 12px;" value="Extract"
																		class="button"
																		onclick="openPreApprovalPopupFromSrc('<s:property value="srcDocPath"/><s:property value="#srcDoc.docPath"/>/<s:property value="#srcDoc.docName"/>')">
																</s:else>
															</s:if> <s:else>
																<span style="padding-right: 10px;">-</span>
															</s:else></td>
													</tr>
												</tbody>
											</table>

										</div>
									</s:if>
								</s:div>
							</s:if>
							<%-- <s:div id="comments" label="Comments" theme="ajax"
					labelposition="top" cssStyle="height: 200px;">
					<div style="padding: 10px;">
					<div id="CommentLoadDiv" align="center"></div>
					<table>
						<tr>
							<td><textarea rows="6" cols="55" name="forumTextArea"
								onclick="if(this.value=='Type Your Comments Here...'){this.value=''; this.style.color='#000';}"
								onblur="if(this.value==''){this.value='Type Your Comments Here...'; this.style.color='#555';}"
								onkeypress="this.style.color='#000';">Type Your Comments Here...</textarea>
							</td>
							<td align="left" width="75%"
								style="padding-left: 7px; padding-bottom: 58px"><font
								style="font-style: normal; font-size: 12px; font-weight: bold;"
								class="title label">Select Users: </font> <s:select list="users"
								name="userCodedtl" size="6" cssStyle="width:75%;"
								multiple="true" listKey="UserCode" listValue="userName" >
							</s:select>
							
						<select name="userCodedtl"  size="6" Style="width:75%;" multiple="multiple" class="commentUsers">
					        <s:iterator value="users">
					            <option value="<s:property value="UserCode"/>" title="<s:property value="userTypeName"/>">
					                <s:property value="userName"/>
					            </option>
					        </s:iterator>
					        </select></td>
						</tr>
						<tr>
							<s:if test="srcReferenceDocPath != 'No_Path_Found'">
								<td colspan="2" align="center" style ="float:left; margin-top: 10px";><span class="title">Reference
								Document:</span> <input type="file" name="uploadDocComment"
									id="uploadDocComment"></input></td>
							</s:if>
						</tr>
					</table>
					</div>
				<s:if test="alloweTMFCustomization=='yes'">
				  <div style="padding-left: 10px;">
					
					<s:if test = "lockSeqFlag == true ">
					  <input type="button" value="Send Comments" name="buttonName" class="button" onclick="lockSeq();">
					</s:if>
					<s:else>
					   <input type="submit" id="saveFormButton" value="Send Comments" name="buttonName" class="button">
					</s:else>
					   
					   <input type="button" value="Show Received Comments" onclick="return showComments();" class="button">
					  
					   <input type="button" value="Show Sent Comments" onclick="return showSentComments();" class="button"> 
					   
					   <input type="button" value="Comment History" onclick="return ShowCommentHistory();"	class="button">
					
					<s:if test = "lockSeqFlag == true ">
						<input type="button" id="animate" value="Reply Comment" class="button" onclick="lockSeq();">
					</s:if>
					<s:else>
						<input type="button" id="animate" value="Reply Comment" onclick="return Animate(); " class="button">
					</s:else>
				  </div>
				</s:if>
				<s:else>
					<div style="padding-left: 100px;"><input type="submit"
						id="saveFormButton" value="Send Comments" name="buttonName"
						class="button"> <input type="button"
						value="Show Received Comments" onclick="return showComments();"
						class="button"> <input type="button"
						value="Show Sent Comments" onclick="return showSentComments();" 
						class="button">
					</div>
				</s:else>
				</s:div> 
				
				<s:if test="nodeHistory.size !=0">
					<s:div id="userDefineAttr" label="User Define Attributes"
						theme="ajax" labelposition="top"
						cssStyle=" height: 150px;overflow: auto;">
						<br />
						<table width="100%" style="padding-left: 20px;">
							<tr>
								<th class="title" align="left">User Define Attributes</th>
							</tr>
							<tr id="userDefinedAttr">
								<td>
								<div>
								<table class="datatable" style="border: none;">
									<s:if test="attrHistory.size == 0">
							No Attributes Found							
							</s:if>
									<s:iterator value="attrHistory" status="stat">
										<tr
											class="<s:if test='#stat.even'>even</s:if> <s:else>odd</s:else> ">
											<td><b><s:property value="attrName" /></b></td>

											<td>&nbsp;&nbsp;<s:property
												value="attrValue.replaceAll('\n','<br>&nbsp;&nbsp;')"
												escape="false" /></td>
										</tr>
									</s:iterator>
								</table>
								</div>
								</td>
							</tr>
						</table>
						<br />
					</s:div>
				</s:if>--%>
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
										test="iscreatedRights==true && (wsNodeHistory.size>=0 && confirmAndUpload==true)">
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
				</s:if>
				<input type="hidden" value="${nodeId}" name="nodeId">
				<input type="hidden" value="${workSpaceNodeDtl.folderName }"
					name="nodeFolderName">
				<input type="hidden" value="${wSOfficeHistorySize}"
					name="wSOfficeHistorySize">

				<s:hidden name="nodeDisplayName"
					value="%{workSpaceNodeDtl.nodeDisplayName}"></s:hidden>
				<s:hidden name="workSpaceNodeDtl"></s:hidden>
				<input type="hidden" value="Y" name="eSign">

			</s:form>
		</s:if>
	</div>
	<!-- <div style="width: 100%; height: 240px;" align="center" 
		             id="commentsDiv2"></div>
				<div align="center" style="z-index: 1000;">
				<div id="commentParent" align="center"
	style="width: 560px; float: right; height: 250px; padding-right: 10px; display: none"></div>
	</div> -->
	<div id="myModal" class="modal"
		style="font-size: 12px; padding-top: 50px;">

		<div class="modal-content"
			style="max-height: 450px; overflow-y: scroll;">
			<span
				style="color: #fff; font-size: 20px; background: #2e7eb9; font-family: Calibri; display: inline-block; width: 100%; text-align: left; padding-left: 20px; padding-bottom: 2px;"><b>Deviation
					Documents</b></span> <img alt="Close" title="Close"
				src="images/Common/Close.svg" style="height: 25px; width: 25px;"
				onclick='dvCloseButton()' class='popupCloseButton'
				style="margin-top: -4px;" />
			<div id="viewSubDtl"></div>
			<br>
			<s:if test="WorkspaceNodeDaviationPopUp =='Soft'">
				<table>
					<tr>
						<td colspan="2"
							style="padding-left: 20px; font-size: 14px; font-weight: bold;">Above
							documents are pending for approval to proceed send for review by
							specifying reason for deviation.</td>
					</tr>

					<tr>

						<td style="width: 187px;"><b style="margin-left: 20px;"
							class="title">Reason for Deviation:</b></td>
						<td><TEXTAREA rows="3"
								style="height: 50px; margin-left: 5px; font-size: 16px; width: 215px;"
								id="deviationRemark"></TEXTAREA></td>
					</tr>
				</table>
				<input type="button" id="deviationButton" value="Ok"
					onclick="deviationFile();" class="button"
					style="margin-bottom: 5px; margin-top: 5px; margin-left: 330px;">
			</s:if>
			<s:if test="WorkspaceNodeDaviationPopUp =='Hard'">
				<table style="margin-top: -10px;">
					<tr>
						<td
							style="padding-left: 20px; font-size: 14px; font-weight: bold;">Please
							approve above documents to proceed send for review.</td>
					</tr>
				</table>
			</s:if>
		</div>
	</div>
	<%-- <div id="myModalValidate" class="modal" style="font-size: 12px;padding-top:50px;">
   
  <div class="modal-content" style="max-height: 450px; overflow-y: scroll;">
  <span style="color: #fff; font-size: 20px; background: #2e7eb9; font-family:Calibri; display: inline-block; 
  width: 100%;text-align: left; padding-left: 20px; padding-bottom: 2px;"><b>Pre-Approval</b></span>
  <img alt="Close" title="Close" src="images/Common/Close.svg" style="height:25px; width:25px;" onclick='dvCloseButtonValidate()' 
  class='popupCloseButton' style="margin-top: -4px;"/>
  	<div id="viewSubDtlValidate"></div>
  	<br>
  	<input type="button" id="cnfrm" value="Confirm" onclick="showPreApprovalPopup();" class="button" 
  						style="margin-bottom: 5px;margin-top: 5px;margin-left: 250px; display:none;">
  	<input type="button" id="cancel" value="Cancel" onclick="dvCloseButtonValidate();" class="button"style="margin-bottom: 5px;">
	
  	
  </div>
</div> --%>
</body>
</html>