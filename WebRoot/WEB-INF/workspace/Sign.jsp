<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">
.btn {
  padding-left: .75rem;
  padding-right: .75rem;
}

label.btn {
  margin-bottom: 0;
}

.d-flex > .btn {
  flex: 1;
}

.carbonads {
  border-radius: .25rem;
  border: 1px solid #ccc;
  font-size: .875rem;
  overflow: hidden;
  padding: 1rem;
}

.carbon-wrap {
  overflow: hidden;
}

.carbon-img {
  clear: left;
  display: block;
  float: left;
}

.carbon-text,
.carbon-poweredby {
  display: block;
  margin-left: 140px;
}

.carbon-text,
.carbon-text:hover,
.carbon-text:focus {
  color: #fff;
  text-decoration: none;
}

.carbon-poweredby,
.carbon-poweredby:hover,
.carbon-poweredby:focus {
  color: #ddd;
  text-decoration: none;
}

@media (min-width: 768px) {
  .carbonads {
    float: right;
    margin-bottom: -1rem;
    margin-top: -1rem;
    max-width: 360px;
  }
}

.footer {
  font-size: .875rem;
}

.heart {
  color: #ddd;
  display: block;
  height: 2rem;
  line-height: 2rem;
  margin-bottom: 0;
  margin-top: 1rem;
  position: relative;
  text-align: center;
  width: 100%;
}

.heart:hover {
  color: #ff4136;
}

.heart::before {
  border-top: 1px solid #eee;
  content: " ";
  display: block;
  height: 0;
  left: 0;
  position: absolute;
  right: 0;
  top: 50%;
}

.heart::after {
  background-color: #fff;
  content: "♥";
  padding-left: .5rem;
  padding-right: .5rem;
  position: relative;
  z-index: 1;
}

.img-container,
.img-preview {
  background-color: #f7f7f7;
  text-align: center;
  width: 100%;
}

.img-container {
  margin-bottom: 1rem;
  max-height: 497px;
  min-height: 200px;
}

@media (min-width: 768px) {
  .img-container {
    min-height: 163px;
  }
}

.img-container > img {
  max-width: 100%;
}

.docs-preview {
  /* margin-right: 65px; */
}

.img-preview {
  float: left;
  margin-bottom: .5rem;
 /*  margin-right: .5rem; */
  overflow: hidden;
}

.img-preview > img {
  max-width: 100%;
}

 .preview-lg {
  height: 122px;
  width: 217px !important;;
} 

.preview-md {
  height: 4.5rem;
  width: 8rem;
}

.preview-sm {
  height: 2.25rem;
  width: 4rem;
}

.preview-xs {
  height: 1.125rem;
  margin-right: 0;
  width: 2rem;
}

.docs-data > .input-group {
  margin-bottom: .5rem;
}

.docs-data > .input-group > label {
  justify-content: center;
  min-width: 5rem;
}

.docs-data > .input-group > span {
  justify-content: center;
  min-width: 3rem;
}

.docs-buttons > .btn,
.docs-buttons > .btn-group,
.docs-buttons > .form-control {
  margin-bottom: .5rem;
  margin-right: .25rem;
}

.docs-toggles > .btn,
.docs-toggles > .btn-group,
.docs-toggles > .dropdown {
  margin-bottom: .5rem;
}

.docs-tooltip {
  display: block;
  margin: -.5rem -.75rem;
  padding: .5rem .75rem;
}

.docs-tooltip > .icon {
  margin: 0 -.25rem;
  vertical-align: top;
}

.tooltip-inner {
  white-space: normal;
}

.btn-upload .tooltip-inner,
.btn-toggle .tooltip-inner {
  white-space: nowrap;
}

.btn-toggle {
  padding: .5rem;
}

.btn-toggle > .docs-tooltip {
  margin: -.5rem;
  padding: .5rem;
}

@media (max-width: 400px) {
  .btn-group-crop {
    margin-right: -1rem!important;
  }
  .btn-group-crop > .btn {
    padding-left: .5rem;
    padding-right: .5rem;
  }
  .btn-group-crop .docs-tooltip {
    margin-left: -.5rem;
    margin-right: -.5rem;
    padding-left: .5rem;
    padding-right: .5rem;
  }
}

.docs-options .dropdown-menu {
  width: 100%;
}

.docs-options .dropdown-menu > li {
  font-size: .875rem;
  padding-left: 1rem;
  padding-right: 1rem;
}

.docs-options .dropdown-menu > li:hover {
  background-color: #f7f7f7;
}

.docs-options .dropdown-menu > li > label {
  display: block;
}

.docs-cropped .modal-body {
  text-align: center;
}

.docs-cropped .modal-body > img,
.docs-cropped .modal-body > canvas {
  max-width: 100%;
}

.light-blackbg {
	position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1000;
    background-color: #000;
    opacity: .5;
}|
</style>

<link type="text/css"
	href="<%=request.getContextPath()%>/css/cropper.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/cropper.js"></script> 

 <script src='<%=request.getContextPath()%>/js/jquery-3.2.1' type='text/javascript'></script> 
 
<%-- <script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>
 <script>
   var $j = jQuery.noConflict(true);
</script> --%>

<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
 

<script type="text/javascript">
var Cropper;
var cropper;
$(document).ready(function() { 
	
	 $('#signTable').dataTable( {
			"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
			"bJQueryUI": true,
			"sPaginationType": "full_numbers"
		 } );
	 
	 var dto = <s:property value="dto.size"/>;
	 if(dto>0){
	 	document.getElementById('temp').style.display='block';
	 }

	  'use strict';
	  //debugger;
	  $('.toggleDiv').hide();
	  Cropper = window.Cropper;
	  var URL = window.URL || window.webkitURL;
	  var logoname = '<s:property value="fname"/>';
	  if(dto>0){
	  	document.getElementById('files').src='ShowImage_b.do?logoFileName='+logoname;
	  }
	  var container = document.querySelector('.img-container');
	  var image = container.getElementsByTagName('img').item(0);
	  var download = document.getElementById('download');
	  var actions = document.getElementById('actions');
	  var dataX = document.getElementById('dataX');
	  var dataY = document.getElementById('dataY');
	  var dataHeight = document.getElementById('dataHeight');
	  var dataWidth = document.getElementById('dataWidth');
	  var dataRotate = document.getElementById('dataRotate');
	  var dataScaleX = document.getElementById('dataScaleX');
	  var dataScaleY = document.getElementById('dataScaleY');
	  var options = {
	    aspectRatio: 321 / 110,
	    preview: '.img-preview',
	    ready: function(e) {
	      console.log(e.type);
	    },
	    cropstart: function(e) {
	      console.log(e.type, e.detail.action);
	    },
	    cropmove: function(e) {
	      console.log(e.type, e.detail.action);
	    },
	    cropend: function(e) {
	      console.log(e.type, e.detail.action);
	    },
	    crop: function(e) {
	      var data = e.detail;

	      console.log(e.type);
	      dataX.value = Math.round(data.x);
	      dataY.value = Math.round(data.y);
	      dataHeight.value = Math.round(data.height);
	      dataWidth.value = Math.round(data.width);
	      dataRotate.value = typeof data.rotate !== 'undefined' ? data.rotate : '';
	      dataScaleX.value = typeof data.scaleX !== 'undefined' ? data.scaleX : '';
	      dataScaleY.value = typeof data.scaleY !== 'undefined' ? data.scaleY : '';
	    },
	    zoom: function(e) {
	      console.log(e.type, e.detail.ratio);
	    }
	  };
	  cropper = new Cropper(image, options);
	  var originalImageURL = image.src;
	  var uploadedImageType = 'image/jpeg';
	  var uploadedImageURL;

	  // Tooltip
	// $j('[data-toggle="tooltip"]').tooltip();

	  // Buttons
	  if (!document.createElement('canvas').getContext) {
	    $('button[data-method="getCroppedCanvas"]').prop('disabled', true);
	  }

	  if (typeof document.createElement('cropper').style.transition === 'undefined') {
	    $('button[data-method="rotate"]').prop('disabled', true);
	    $('button[data-method="scale"]').prop('disabled', true);
	  }

	  // Download
	  if (typeof download.download === 'undefined') {
	    download.className += ' disabled';
	  }

	  // Options
	  actions.querySelector('.docs-toggles').onchange = function(event) {
	    var e = event || window.event;
	    var target = e.target || e.srcElement;
	    var cropBoxData;
	    var canvasData;
	    var isCheckbox;
	    var isRadio;

	    if (!cropper) {
	      return;
	    }

	    if (target.tagName.toLowerCase() === 'label') {
	      target = target.querySelector('input');
	    }

	    isCheckbox = target.type === 'checkbox';
	    isRadio = target.type === 'radio';

	    if (isCheckbox || isRadio) {
	      if (isCheckbox) {
	        options[target.name] = target.checked;
	        cropBoxData = cropper.getCropBoxData();
	        canvasData = cropper.getCanvasData();

	        options.ready = function() {
	          console.log('ready');
	          cropper.setCropBoxData(cropBoxData).setCanvasData(canvasData);
	        };
	      } else {
	        options[target.name] = target.value;
	        options.ready = function() {
	          console.log('ready');
	        };
	      }

	      // Restart
	      cropper.destroy();
	      cropper = new Cropper(image, options);
	    }
	  };

	  // Methods
	  actions.querySelector('.docs-buttons').onclick = function(event) {
	    var e = event || window.event;
	    var target = e.target || e.srcElement;
	    var cropped;
	    var result;
	    var input;
	    var data;

	    if (!cropper) {
	      return;
	    }

	    while (target !== this) {
	      if (target.getAttribute('data-method')) {
	        break;
	      }

	      target = target.parentNode;
	    }

	    if (target === this || target.disabled || target.className.indexOf('disabled') > -1) {
	      return;
	    }

	    data = {
	      method: target.getAttribute('data-method'),
	      target: target.getAttribute('data-target'),
	      option: target.getAttribute('data-option') || undefined,
	      secondOption: target.getAttribute('data-second-option') || undefined
	    };

	    cropped = cropper.cropped;

	    if (data.method) {
	      if (typeof data.target !== 'undefined') {
	        input = document.querySelector(data.target);

	        if (!target.hasAttribute('data-option') && data.target && input) {
	          try {
	            data.option = JSON.parse(input.value);
	          } catch (e) {
	            console.log(e.message);
	          }
	        }
	      }

	      switch (data.method) {
	        case 'rotate':
	          if (cropped) {
	            cropper.clear();
	          }

	          break;

	        case 'getCroppedCanvas':
	          try {
	            data.option = JSON.parse(data.option);
	          } catch (e) {
	            console.log(e.message);
	          }

	          if (uploadedImageType === 'image/jpeg') {
	            if (!data.option) {
	              data.option = {};
	            }

	            data.option.fillColor = '#fff';
	          }

	          break;
	      }

	      result = cropper[data.method](data.option, data.secondOption);

	      switch (data.method) {
	        case 'rotate':
	          if (cropped) {
	            cropper.crop();
	          }

	          break;

	        case 'scaleX':
	        case 'scaleY':
	          target.setAttribute('data-option', -data.option);
	          break;

	        case 'getCroppedCanvas':
	          if (result) {
	            // Bootstrap's Modal
	            $('#getCroppedCanvasModal').modal().find('.modal-body').html(result);

	            if (!download.disabled) {
	              download.href = result.toDataURL(uploadedImageType);
	            }
	          }

	          break;

	        case 'destroy':
	          cropper = null;

	          if (uploadedImageURL) {
	            URL.revokeObjectURL(uploadedImageURL);
	            uploadedImageURL = '';
	            image.src = originalImageURL;
	          }

	          break;
	      }

	      if (typeof result === 'object' && result !== cropper && input) {
	        try {
	          input.value = JSON.stringify(result);
	        } catch (e) {
	          console.log(e.message);
	        }
	      }
	    }
	  };

	  document.body.onkeydown = function(event) {
	    var e = event || window.event;

	    if (!cropper || this.scrollTop > 300) {
	      return;
	    }

	    switch (e.keyCode) {
	      case 37:
	        e.preventDefault();
	        cropper.move(-1, 0);
	        break;

	      case 38:
	        e.preventDefault();
	        cropper.move(0, -1);
	        break;

	      case 39:
	        e.preventDefault();
	        cropper.move(1, 0);
	        break;

	      case 40:
	        e.preventDefault();
	        cropper.move(0, 1);
	        break;
	    }
	  };

	  // Import image
	  var inputImage = document.getElementById('inputImage'); 
	 		
	  if (URL) {
	    inputImage.onchange = function() {
	    //debugger;
	    document.getElementById('temp').style.display='block';
	    
	    var radios = document.getElementsByName("headerType");
	    
	    for(var i=0;i<radios.length;i++){
	    	radios[i].checked = false;
	    }
	    
	    for (var i=0, iLen=radios.length; i<iLen; i++) {
	      radios[i].disabled = true;
	    } 
	    
	      var files = this.files;
	      var file;

	      if (cropper && files && files.length) {
	        file = files[0];

	        if (/^image\/\w+/.test(file.type)) {
	          uploadedImageType = file.type;

	          if (uploadedImageURL) {
	            URL.revokeObjectURL(uploadedImageURL);
	          }

	          image.src = uploadedImageURL = URL.createObjectURL(file);
	          cropper.destroy();
	          cropper = new Cropper(image, options);
	          //inputImage.value = null;
	        } else {
	          window.alert('Please choose an image file.');
	        }
	      }
	    };
	  } else {
	    inputImage.disabled = true;
	    inputImage.parentNode.className += ' disabled';
	  }
	  $('input[type=radio][name=headerType]').change(function() {
			
			//debugger;
			var selectedOption =  $("input:radio[name=headerType]:checked").val();
			
			 var span = document.getElementById('uName');
			 span.style.fontFamily = selectedOption;
			 document.getElementById("inputImage").disabled = true;
		});
	});
function saveFile(){
	//debugger;
	
	var fileInput = document.getElementById('inputImage');
    var file = fileInput.files[0];
	var uploadFile = document.SignatureForm.inputImage.value;
   	var uploadedFile= uploadFile.replace(/^.*[\\\/]/, '');
	var sp = uploadFile.split('\\');
	var fname = sp[sp.length - 1].indexOf(".");
	var fname1=sp[sp.length-1];
	var index=uploadedFile.lastIndexOf('.');
	var strInvalidChars = "()/\^'$#:~%@&;,!*<>?";
	var fileext = uploadedFile.substring(index+1).toLowerCase();   
	var selectedOption = $("input:radio[name=headerType]:checked").val();
	var strChar;
	if(uploadFile=="" && (selectedOption=="" || selectedOption==null))
	{
		alert("Please select file or style.");
		document.SignatureForm.inputImage.style.backgroundColor="#FFE6F7"; 
     	document.SignatureForm.inputImage.focus();
		return false;
	}
	if(uploadFile != ""){
		if(fileext !="png" && fileext !="jpg" && fileext !="jpeg"){
			alert("Please upload valid extension(e.g. .png, .jpg) File.")
			return false;
		}
	for (i = 0; i < uploadedFile.length; i++)
	{
		strChar = uploadedFile.charAt(i);
	 	if (strInvalidChars.indexOf(strChar)!= -1)
		{
	 	alert("Invalid Document Name. \n\nOnly Alphabets, Digits,Dot,Underscore and Dash are allowed.");
      	document.workspaceNodeAttrForm.uploadFile.style.backgroundColor="#FFE6F7"; 
      	return false;  			
		}
	}
	}
	if(selectedOption == undefined){
		selectedOption ="";
	}
	/* if(selectedOption=="" || selectedOption==null){
		alert("Please select style.");
		return false;
	} 	 */ 
	//debugger;
	if(uploadFile == ""){
		var formData_FileUploading=new FormData();
        formData_FileUploading.append('uploadFile', "");
        formData_FileUploading.append('uploadFileName',fname1);
	   	 formData_FileUploading.append('fontStyle',selectedOption);

			 var xhr = new XMLHttpRequest();
		
	   		 xhr.open('POST', 'SaveSignature_ex.do', true);

			 xhr.onload = function () {

	  			if (xhr.status == 200) {
	  				alert("Signature successfully created.")
	  				location.reload();
	  			}
	  			};
	  			xhr.send(formData_FileUploading);
	}
	else{
  			cropper.getCroppedCanvas().toBlob(function (blob){
             var formData_FileUploading=new FormData();
             formData_FileUploading.append('uploadFile', blob);
             formData_FileUploading.append('uploadFileName',fname1);
     	   	 formData_FileUploading.append('fontStyle',selectedOption);

    			 var xhr = new XMLHttpRequest();
    		
    	   		 xhr.open('POST', 'SaveSignature_ex.do', true);

    			 xhr.onload = function () {
     
    	  			if (xhr.status == 200) {
    	  				alert("Signature successfully created.")
    	  				location.reload();
    	  			}
    	  			};
    	  			xhr.send(formData_FileUploading);
        });
	}

return true;
}

function reset1(){
	 //debugger;
	 document.getElementById("inputImage").disabled = false;
	 document.getElementById('inputImage').value= null;
	 
	var radios = document.getElementsByName("headerType");

	for(var i=0;i<radios.length;i++){
    	radios[i].checked = false;
    }
	
    for (var i=0, iLen=radios.length; i<iLen; i++) {
      radios[i].disabled = false;
    } 
	
}
function showImg(userId,uuId,fileName)
{
	 $('#toggleDiv_'+userId+"_"+uuId+"_"+fileName).show();
	 //$('.light-blackbg').style.display="block";
	 document.getElementById('light-blackbg').style.display='block';
	 
	 //debugger;
	 document.getElementById('showSign_'+userId+"_"+uuId+"_"+fileName).src='ShowImageHistory_b.do?logoFileName='+fileName+'&uuId='+uuId;	
	 document.getElementById('toggleDiv_'+userId+"_"+uuId+"_"+fileName).style.display='block';
	 
}
function refreshParent(userId,uuId,fileName) 
{
 //debugger;
 document.getElementById("toggleDiv_"+userId+"_"+uuId+"_"+fileName).style.display="none";
 document.getElementById('light-blackbg').style.display='none';
}
	
</script>

</head>
<body>

<div id="light-blackbg" class="light-blackbg" style="display:none;"></div>

<div class="container-fluid">
<div class="col-md-12">

<div class="errordiv" style="color: red;" align="center"><s:fielderror></s:fielderror></div>
<div class="msgdiv" style="color: green;" align="center"><s:actionmessage /></div>
<br />
<div align="center">
<div class="boxborder"><div class="all_title"><b style="float:left;">Create Signature</b></div>

<div class="datatablePadding" style="border: 1px; border-radius: 0px 0px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">
<s:form method="post" id="SignatureForm" name="SignatureForm">
		<table style="width: 100%">
			<tr>
				<td class="title" align="right" width="20%" style="padding: 2px; padding-right: 8px;">Username:</td>
				<td align="left" width="30%">
					<s:textfield name="userName" size="80%" disabled="true"></s:textfield>
				</td>
				<td><label class="title" style="float: left; padding-right: 15px;">Signature:</label>
				
				<input type="file" id="inputImage" name="inputImage" value="Upload" accept=".jpg,.jpeg,.png">
				
				</td>
			</tr>
			
			<tr>
			<td class="title" align="right" width="20%" style="padding: 2px; padding-right: 8px;">Select Style: </td>
			<td id="lbl" width="30%" >
				<input type="radio" id="Times New Roman" name="headerType" value="Times New Roman">
				<span style="font-family:Times New Roman;"><s:property value="userName"/></span><br>
				<input type="radio" id="Verdana" name="headerType" value="Verdana">
				<span style="font-family:Verdana;"><s:property value="userName"/></span><br>
				<input type="radio" id="Calibri" name="headerType" value="Calibri">
				<span style="font-family:Calibri;"><s:property value="userName"/></span><br>
				<input type="radio" id="Brush Script MT" name="headerType" value="Brush Script MT">
				<span style="font-family:Brush Script MT;"><s:property value="userName"/></span><br>
				<%-- <input type="radio" id="Trattatello, fantasy" name="headerType" value="Trattatello, fantasy">
				<span style="font-family:Trattatello, fantasy;"><s:property value="userName"/></span><br>
				<input type="radio" id="Snell Roundhand, cursive" name="headerType" value="Snell Roundhand, cursive">
				<span style="font-family:Snell Roundhand, cursive;"><s:property value="userName"/></span> --%>
				
			</td>
			<td>
			<div id="temp" style="display:none;">
				<div class="container" style="width:auto !important; margin-top: 25px;">
    <div class="row">
      <div class="col-xs-6" style="width:250px; padding-left: 0px; margin-top: 16px;">
          <!-- <h3>Demo:</h3> -->
        <div class="img-container">
          <img src="" id="files" name="files" alt="Picture">
        </div>
      </div>
      
      
      <div class="col-xs-6" style="width:250px;margin-top: -10px; margin-left: 50px;">
        <!-- <h3>Preview:</h3> -->
        <div class="title">Preview:</div>
        
        <div class="row" id="actions">
      <div class="docs-buttons" style="display:none">
        <!-- <h3>Toolbar:</h3> -->
        <div class="btn-group"><table>
        <tr><td>
         	<!-- <input type="file" id="inputImage" name="inputImage" value="Upload" accept=".jpg,.jpeg,.png"> -->         	
         	</td></tr>
           <%--  <span class="button">Reset</span> --%>
         <!--  <label class="btn btn-primary btn-upload" for="inputImage" title="Upload image file"> -->
            
            </table>
          <!-- </label> -->
        </div>
        <!-- Show the cropped image in modal -->
        <div class="modal fade docs-cropped" id="getCroppedCanvasModal" role="dialog" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" tabindex="-1">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="getCroppedCanvasTitle">Cropped</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body"></div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <a class="btn btn-primary" id="download" href="javascript:void(0);" download="cropped.jpg">Download</a>
              </div>
            </div>
          </div>
        </div><!-- /.modal --> 

      </div><!-- /.docs-buttons -->

      <div class="col-md-3 docs-toggles">
        <!-- <h3>Toggles:</h3> -->
        <div class="btn-group d-flex flex-nowrap" data-toggle="buttons">
        </div>
      </div><!-- /.docs-toggles -->
    </div>
        <div class="docs-preview clearfix" style="border:1px solid; text-align: center;">
          <div class="img-preview preview-lg" style="width: 258px !impotant;">
          </div>
        	&nbsp;<span id="uName"><s:property value="userName"/></span><br>
        	<s:if test ="#session.countryCode == 'IND'">
        	&nbsp;<span><s:property value="ISTCurrentTime"/></span>
        	</s:if>
        	<s:else>
        	&nbsp;<span><s:property value="ESTCurrentTime"/></span>
        	</s:else>
          
        </div>
        <br>
    
  </div>
  </div>
  </div>
  </div>
  </td></tr>
  <tr>
  <td colspan="2">
  </td>
  <td style="float:left;">
  
             <!-- <input type="button" value="Reset" class="button" data-method="reset" title="Reset"/> &nbsp; -->
            <%-- <span class="button">Upload</span> --%>
            <input type="button" value="Save" id="SaveImg" class="button" onclick="return saveFile();"/>
            <input type="button" value="Reset" id="Reset1" class="button" onclick="return reset1();"/>
  
            </td>
            </tr>
  </table>
  </s:form>
  <s:if test="dto.size > 0">
<div style="border: 1px; margin-top:30px; overflow: auto; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px;
 border-top: none; width: 99%; margin-left: 5px;" align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Signature History</b></div>
<br>
<div style="width:99%">
<table id="signTable" class="datatable paddingtable audittrailtable" width="100%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<!-- <th>Document Name</th> -->
			<th>Sign Id</th>
			<th>Is AdUser</th>  
			<th>Created by</th>
			<th>Created on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<!-- <th>Reason for Change</th> -->
			 <th>View Sign</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="dto" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="#status.count" /></td>
				<%-- <td>
					<s:if test="fileName == ''">&nbsp;-</s:if>
					<s:else>
					 <s:property value="fileName" />
					</s:else>
				</td> --%>
				<%-- <td><s:if test="historyDocumentSize.sizeMBytes>0">
					<s:property value="historyDocumentSize.sizeMBytes" default="--" /> MB
								</s:if> <s:else>
					<s:property value="historyDocumentSize.sizeKBytes" default="--" /> KB
								</s:else></td> --%>
				<td><s:property value="folderName.split('/')[2]" /></td>
				<td>
					<s:if test="isAdUser=='Y'"><s:property value="isAdUser" /></s:if>
					<s:else>-</s:else> 
				</td>
				<td><s:property value="userName" /></td>
				<td><s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<%-- <td><s:property value="remark" /></td> --%>
				 <td>
				<s:if test="#status.first == true ">-</s:if>
				<s:else>
				<img border="0px" alt="Edit"  src="images/Common/edit.svg" title="View Signature" height="25px" width="25px"
					onclick ="return showImg('<s:property value="userCode"/>','<s:property value="uuId"/>','<s:property value="fileName"/>');">
					<div style="background:rgba(0, 0, 0, 0.6)">
			         <div id="toggleDiv_<s:property value="userCode"/>_<s:property value="uuId"/>_<s:property value="fileName"/>" 
			         	class="toggleDiv" style="max-height: 300px; text-align: center;overflow: auto;position: fixed; left: 38%; top:20%;
			         	background: rgb(255, 255, 255);border: 3px solid rgb(177, 210, 251); z-index: 1040;display: none;">
 					 <img border="0px" style="float:right;" title="Close" src="images/Common/Close.svg" height="25px" width="25px"
 								 onclick="refreshParent('<s:property value="userCode"/>','<s:property value="uuId"/>','<s:property value="fileName"/>');">
						<div id="statusDetails_<s:property value="userCode"/>','<s:property value="uuId"/>','<s:property value="fileName"/>'" style="padding-left: 4px; color: #fff;
    								font: calibri; font-weight: bold;">
    						<img border="0px" style="margin: 5px 15px; border: 1px solid #898989;" 
    							 src="" id="showSign_<s:property value="userCode"/>_<s:property value="uuId"/>_<s:property value="fileName"/>" 
    							 height="150px" width="300px">
    				</div>
    				 <span style="font-family:<s:property value="fileType"/>;"><s:property value="userName"/></span><br>
    				 <s:if test ="#session.countryCode != 'IND'">
    				 	<span><s:property value="ESTDateTime"/></span>
    				 </s:if>
    				 <s:else>
    				 	<span><s:property value="ISTDateTime"/></span>
    				 </s:else>
					</div>
					</div>
				</s:else>
				</td>
			</tr>
		</s:iterator>

	</tbody>
</table>
</div>
</div>
</div>
</s:if>
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>