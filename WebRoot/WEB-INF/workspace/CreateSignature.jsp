<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style type="text/css">
#usertable_filter input{
background-color: #fff;
color:#000;
}
#usertable_length select {
background-color: #fff;
color:#000;
}

.container {
      /* margin: 20px auto; */
      max-width: 500px !important;
      height: 150px !important;
    }
</style>

<link type="text/css"
	href="<%=request.getContextPath()%>/css/cropper.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/cropper.js"></script> 

<script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>

<script type="text/javascript">
var image;
var data;
var cropBoxData;
var button;
var result;
var cropper;
var replc=false;
$(document).ready(function() { 
	replc = false;
} );


	var loadFile = function(event) {
    var output = document.getElementById('output');
    debugger;
    output.src = URL.createObjectURL(event.target.files[0]);
    
    if(replc == true){
    	 var crpImg = document.getElementById('cropImg');
    	    crpImg.src =  output.src;
    	    var previewImg = document.getElementById('previewImg');
    	    previewImg.src =  output.src;
    }
   
    output.onload = function() {
      URL.revokeObjectURL(output.src) // free memory
      
    } 
        debugger;  
        image = document.querySelector('#output');
        data = document.querySelector('#data');
        cropBoxData = document.querySelector('#cropBoxData');
        button = document.getElementById('button');
        result = document.getElementById('result');  
        var option ="";
        option = {
                ready: function (event) {
                    // Zoom the image to its natural size
                    cropper.zoomTo(1);
                  },

                  crop: function (event) {
                    data.textContent = JSON.stringify(cropper.getData());
                    cropBoxData.textContent = JSON.stringify(cropper.getCropBoxData());
                  },

                  zoom: function (event) {
                    // Keep the image in its natural size
                    if (event.detail.oldRatio === 1) {
                      event.preventDefault();
                    }
                  },
                };
        
    cropper = new Cropper(image, option);
    replc = true;
    button.onclick = function () {
    	debugger;
      /*  result.innerHTML = '';
      result.appendChild(cropper.getCroppedCanvas()); */
      	result.innerHTML = '';
    	var tbl = document.createElement("table");
    	var selectedOption = $("input:radio[name=headerType]:checked").val();
    	
    	var imgurl =  cropper.getCroppedCanvas().toDataURL();
    	var img = document.createElement("img");
        img.src = imgurl;
        
        var userName = '<s:property value="userName"/>';
        var row = document.createElement("tr");
    	var col = document.createElement("td");
        
    	var span = document.createElement("span");
        span.innerHTML = userName;
        
        if(selectedOption != "" || selectedOption != null){
        
        	if(selectedOption=="Bold"){
        		span.style.fontWeight = 'bold';
        	}else if(selectedOption=="Underline"){
        		span.style.textDecoration = "underline";
        	}
        	else if(selectedOption=="Italic"){
        		span.style.fontStyle = "italic";
        	}
        }
        col.appendChild(span);
        row.appendChild(col);
        tbl.appendChild(row);
        
        result.appendChild(img);
        result.appendChild(tbl);
  };
	}
  
function saveFile(){
		debugger;
		var fileInput = document.getElementById('uploadFiles');
	    var file = fileInput.files[0];
		var uploadFile = document.SignatureForm.uploadFiles.value;
	   	var uploadedFile= uploadFile.replace(/^.*[\\\/]/, '');
    	var sp = uploadFile.split('\\');
		var fname = sp[sp.length - 1].indexOf(".");
		var fname1=sp[sp.length-1];
		var index=uploadedFile.lastIndexOf('.');
    	var strInvalidChars = "()/\^$#:~%@&;,!*<>?";
    	var fileext = uploadedFile.substring(index+1).toLowerCase();   
    	var selectedOption = $("input:radio[name=headerType]:checked").val();

    	if(uploadFile=="")
		{
			alert("Please Select File");
			document.SignatureForm.uploadFiles.style.backgroundColor="#FFE6F7"; 
	     	document.SignatureForm.uploadFiles.focus();
			return false;
		}
    	if(fileext !="png" && fileext !="jpg" && fileext !="svg"){
			alert("Please upload valid extension(e.g. .png, .jpg & .svg) File.")
			return false;
		}
    	if(selectedOption=="" || selectedOption==null){
    		alert("Please Select Name Style.");
    		return false;
    	}

    	/*  var formData_FileUploading=new FormData();
         formData_FileUploading.append('uploadFile',file);
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
	  			xhr.send(formData_FileUploading); */
	 	  			
	  			cropper.getCroppedCanvas().toBlob(function (blob) {
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
   
	return true;
	}
	
</script>

</head>
<body>
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
				<td class="title" align="right" width="" style="padding: 2px; padding-right: 8px;">User Name:</td>
				<td align="left">
					<s:textfield name="userName" size="80%" disabled="true"></s:textfield>
				</td>
			</tr>
			
			<tr>
			<td class="title" align="right" width="" style="padding: 2px; padding-right: 8px;">Select Style: </td>
			<td id="lbl">
				<input type="radio" id="Bold" name="headerType" value="Bold">
				<b><span><s:property value="userName"/></span></b><br>
				<input type="radio" id="Italic" name="headerType" value="Italic">
				<i><span><s:property value="userName"/></span></i><br>
				<input type="radio" id="Underline" name="headerType" value="Underline">
				<u><span><s:property value="userName"/></span></u>
			</td>
			<td>
				<!-- <input type="file" name="uploadFile"  > -->
				<input type="file" id="uploadFiles"  style="margin-right: -140px;" name="uploadFiles" accept=".jpg,.jpeg,.png,.bmp,.svg" onchange="loadFile(event)">
			</td>
			<td>
			<div class="container">
				<img id="output" style="display:none"/>
			</div>
			</td>
			</tr>
			<tr>
			<td colspan="3">
			<%-- <p>Data: <span id="data"></span></p>
    		<p>Crop Box Data: <span id="cropBoxData"></span></p> --%>
				<div id="result"></div>
			</td>
			</tr>
			<tr>
			<td colspan="4" align="center">
				<input type="button" value="Save" id="SaveImg" class="button" onclick="return saveFile();"/>
				
				 <input type="button" class="button" value="Priview" id="button"/>
			</td>
			</tr>
		</table>
	</s:form>
	<s:if test="dto.size > 0">
<div style="border: 1px; margin-top:65px; overflow: auto; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px;
 border-top: none; width: 99%; margin-left: 5px;" align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Signature History</b></div>
<br>
<div style="max-height: 342px; overflow: auto;">
<table class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<th>Document Name</th>
			<!-- <th>Size</th> -->
			<th>Modified By</th>
			<th>Modified On</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="dto" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="#status.count" /></td>
				<td>
					<s:if test="fileName == ''">&nbsp;-</s:if>
					<s:else>
					 <s:property value="fileName" />
					</s:else>
				</td>
				<%-- <td><s:if test="historyDocumentSize.sizeMBytes>0">
					<s:property value="historyDocumentSize.sizeMBytes" default="--" /> MB
								</s:if> <s:else>
					<s:property value="historyDocumentSize.sizeKBytes" default="--" /> KB
								</s:else></td> --%>
				<td><s:property value="userName" /></td>
				<td><s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:property value="remark" /></td>
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