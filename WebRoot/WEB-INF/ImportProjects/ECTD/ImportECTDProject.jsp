<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head theme="ajax" />


<script type="text/javascript" src="js/jquery/jquery.form.js"></script>

<script>

	// prepare the form when the DOM is ready
	$(document).ready(function() {
		var options = {	target: '#fileDiv', 
						beforeSubmit: showRequest,
						success: showResponse 
						//url: 'test.php'
						//type: post
						//dataType: null // 'xml', 'script', or 'json' (expected server response type)
						//clearForm: true // clear all form fields after successful submit
						//resetForm: true // reset the form after successful submit
						
						// $.ajax options can be used here too, for example:
						//timeout: 3000
					  };
						
		// bind to the form's submit event
		$('#jSubmitFrm').submit(function() {
			// inside event callbacks 'this' is the DOM element so we first
			// wrap it in a jQuery object and then invoke ajaxSubmit
			$(this).ajaxSubmit(options);
		
			// !!! Important !!!
			// always return false to prevent standard browser submit and page navigation
			return false;
		});
	
	});

	// pre-submit callback
	function showRequest(formData, jqForm, options) {
			// formData is an array; here we use $.param to convert it to a string to display it
			// but the form plugin does this for you automatically when it submits the data
		var queryString = $.param(formData);
		var formElement = jqForm[0];
		
		//validate
		/*if($('[name=templateId]').val() == '-1'){
			alert('Please Select eCTD Template...');
			$('[name=templateId]').attr({
  				style: 'background-color:#FFE6F7'
 			}); 
 			return false;
		}
		if(!validateFile($('[name=upload]').val())){
			$('[name=upload]').attr({
  				style: 'background-color:#FFE6F7'
 			}); 
			return false;
		}*/
		$(options.target).html('Loading...');
		//formElement.submitBtn.disabled='true';
		// jqForm is a jQuery object encapsulating the form element. To access the
		// DOM element for the form do this:
		

		
		//alert('About to submit: \n\n' + queryString);
	
			// here we could return false to prevent the form from being submitted;
			// returning anything other than false will allow the form submit to continue
		return true;
	}
	
	// post-submit callback
	function showResponse(responseText, statusText) {
			// for normal html responses, the first argument to the success callback
			// is the XMLHttpRequest object's responseText property
			
			// if the ajaxForm method was passed an Options Object with the dataType
			// property set to 'xml' then the first argument to the success callback
			// is the XMLHttpRequest object's responseXML property
			
			// if the ajaxForm method was passed an Options Object with the dataType
			// property set to 'json' then the first argument to the success callback
			// is the json data object returned by the server
	
		/*alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +
		'\n\nThe output div should have already been updated with the responseText.');*/
	}
	
	function validateFile(filePath){
		var err = false;
		if(filePath == ''){
			alert('Please Upload Sequence Zip File..');
			return false;
		}else{
			var fileName = filePath.substring(filePath.lastIndexOf('\\')+1)
			var fileExt = fileName.substring(fileName.lastIndexOf('.')+1);
			if(fileExt != 'zip'){
				err = true;
			}
		}
		if(err){
			alert('Please Upload a Zip File..');
			return false; 
		}
		return true;
	}

</script>

</head>
<body>


<br />
<div align="center"><img
	src="images/Header_Images/Project/Import_eCTD_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form id="jSubmitFrm"
	action="uploadECTDProject_b" name="ectdUploadFrm"
	enctype="multipart/form-data" method="post">
	<br />
	<br />
	<table width="100%">
		<tr>

			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><label class="title">Template</label></td>
			<td colspan="2" align="left"><s:select name="templateId"
				list="getTemplateDtl" headerKey="-1" headerValue="Select Template"
				listKey="templateId" listValue="templateDesc">
			</s:select></td>
		</tr>
		<tr>

			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><input type="radio"
				name="srcType" value="file" id="srcTypeFile" checked="checked">
			<label class="title" for="srcTypeFile">Upload eCTD Zip</label></td>
			<td align="left"><s:file name="upload" id="upload" size="45"></s:file></td>
		</tr>
		<tr>

			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><input type="radio"
				name="srcType" value="path" id="srcTypePath"> <label
				class="title" for="srcTypePath">eCTD Zip Path</label></td>
			<td align="left"><s:textfield name="path" id="path" size="45"></s:textfield></td>
		</tr>

		<tr>

			<td></td>
			<td align="left"><s:submit value="Upload" name="submitBtn"
				cssClass="button"></s:submit></td>
		</tr>
	</table>
	<br />

</s:form> <br>
<div id="fileDiv"></div>
</div>

</div>
</div>

</body>

</html>