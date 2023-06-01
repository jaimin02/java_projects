<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>

<style>
.ui-button {
	margin-left: -1px;
}

.ui-button-icon-only .ui-button-text {
	padding: 0.24em;
}

.ui-autocomplete-input {
	margin: 0;
	padding: 0.23em 0.23em 0.22em;
}
</style>

<s:head />
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<SCRIPT type="text/javascript">
	$(document).ready(function() 
		{
			if(document.getElementById("workSpaceId") != "-1")
			{ 	
				subinfo();
			}	
		});
	function subinfo()
	{
		var workSpaceId=$('#workSpaceId').val();
		var locationObj=document.getElementById("locationCode");
		var deptObj=document.getElementById("deptCode");
		var clientObj=document.getElementById("clientCode");
		var projectObj=document.getElementById("projectCode");

			var urlOfAction="GetWorkSpaceDetail_ex.do";
		var dataofAction="workSpaceId="+workSpaceId;				
		$.ajax({
			type: "GET", 
				url: urlOfAction, 
				data: dataofAction, 
				cache: false,
				dataType:'text',
			success: function(response){	
			
					var code_array=response.split(":");		
					
					setSelectOptionValue(locationObj,code_array[0]);
					setSelectOptionValue(deptObj,code_array[1]);
					setSelectOptionValue(clientObj,code_array[2]);
					setSelectOptionValue(projectObj,code_array[3]);
			
			}
		});
				
	}	
	function setSelectOptionValue(selectObj,setValue)
	{
		var optionFound = false;
		for(itrOption = 0; itrOption < selectObj.options.length; itrOption++)
		{
			if(selectObj.options[itrOption].value == setValue)
			{
				optionFound = true;
			}
		}

		if(optionFound)
		{
			selectObj.value = setValue;
		}			
		else
		{
			selectObj.value = '-1';
		}
	}
	function checkWorkSpaceName()
	{		
		var workspace=document.getElementById('sourceWorkSpaceDesc').value;
		document.getElementById('sourceWorkSpaceDesc').value =trim(workspace);
		workspace=document.getElementById('sourceWorkSpaceDesc').value; 
		if(check(document.saveProjectForm.sourceWorkSpaceDesc.value)!=false)
		{
			var urlOfAction="WorkSpaceExistsForUpdate.do";
			var dataofAction="workSpaceDesc="+workspace;				
			$.ajax({
				type: "GET", 
					url: urlOfAction, 
					data: dataofAction, 
					cache: false,
					dataType:'text',
				success: function(response){								
						$('#error').html('');				
						$('#message').html('');
					if(response.indexOf('Available') == -1){
						$('#error').html(response);	
						document.getElementById("btncopy").disabled=true;																		
						return false;
					}
					else if(response.indexOf('Available') != -1)
					{
						$('#message').html(response);
						document.getElementById("btncopy").disabled=false;
						return true;
					}
					//alert($('#htmlRole').html(response));
					//addMessage($('#htmlRole').html(response));
				}
			});
		}
		else
		{}
	}

	function trim(str)
	{
	   	str = str.replace( /^\s+/g, "" );// strip leading
		return str.replace( /\s+$/g, "" );// strip trailing
	}
	function check(strString)
	{
	     strString=trim(strString);
	   	 if(strString.indexOf("\\")!=-1){alert("Invalid destination project name." + " " + "\\ is not allowed");return false;}
		    
		    var strInvalidChars = "/\^$#:~%@&*`!;'\" ><?";
	    	var strChar;
	   	  	var blnResult = true;
	   	  	 
		if(strString.length < 5)
		{
			alert("Destination project name must be greater then 5 char..");
			document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
	     	document.saveProjectForm.sourceWorkSpaceDesc.focus();
			return false;
		}
		if(strString.length > 60)
		{
			alert("Destination project name must be less then 60 char..");
			document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
	     	document.saveProjectForm.sourceWorkSpaceDesc.focus();
			return false;
		}	
	   	if (strString.length== 0) {alert ("Please specify destination project name.."); return false;}
	      	for (i = 0; i < strString.length && blnResult == true; i++)
	     	 {
	 			strChar = strString.charAt(i);
				 if (strInvalidChars.indexOf(strChar)!= -1)
				 {
	      			blnResult = false;
	       			if(strChar == ' ')
      				{
      					alert("Invalid destination project name." + " 'Space' is not allowed" );
      					blnResult= false;
      				}
      				else
      				{
       					alert("Invalid destination project name." + " " + strChar + "  is not allowed" );
       					blnResult = false;
       				}
	 			}
	      	}
	      	if((!strString.match(/^([a-zA-Z0-9])/) || !strString.match(/([a-zA-Z0-9])$/)) && blnResult != false )
   			{			       			
				alert("First or Last character can not be a special character..");
				blnResult = false;
       		}	
			return blnResult;
	 }
	function validate()
	{
		if(document.saveProjectForm.workSpaceId.value=="-1")
		{
			alert("Please select source project name..");
			document.saveProjectForm.workSpaceId.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.workSpaceId.focus();
     		return false;
     	}
		
		else if(document.saveProjectForm.sourceWorkSpaceDesc.value=="")
		{
			alert("Please specify destination project name..");
			document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.sourceWorkSpaceDesc.focus();
     		return false;
     	}
     	else if(document.saveProjectForm.sourceWorkSpaceDesc.value.length>255)
		{
			alert("Destination Project name cannot be of more then 255 charactars..");
			document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.sourceWorkSpaceDesc.focus();
     		return false;
     	}
     	else if(check(document.saveProjectForm.sourceWorkSpaceDesc.value)==false) 
        {
       	   	document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.sourceWorkSpaceDesc.focus();
			return false;
		}
		else if(document.saveProjectForm.locationCode.value==-1)
		{
			alert("Please select region name..");
			document.saveProjectForm.locationCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.locationCode.focus();
     		return false;
     	}
     	else if(document.saveProjectForm.deptCode.value==-1)
		{
			alert("Please select department name..");
			document.saveProjectForm.deptCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.deptCode.focus();
     		return false;
     	}
     	else if(document.saveProjectForm.clientCode.value==-1)
		{
			alert("Please select client name..");
			document.saveProjectForm.clientCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.clientCode.focus();
     		return false;
     	}
     	else if(document.saveProjectForm.projectCode.value==-1)
		{
			alert("Please select project type name..");
			document.saveProjectForm.projectCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.projectCode.focus();
     		return false;
     	}
     	else if(document.saveProjectForm.projectFor.value==-1)
		{
			alert("Please select type name..");
			document.saveProjectForm.projectFor.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.projectFor.focus();
     		return false;
     	}
     	else if(document.saveProjectForm.userCode.value==-1)
		{
			alert("Please select default Admin ..");
			document.saveProjectForm.userCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.userCode.focus();
     		return false;
     	}
		
     	else if(document.saveProjectForm.remark.value.length>250)
		{
			alert("Remarks cannot be of more then 250 charactars..");
			document.saveProjectForm.remark.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.remark.focus();
     		return false;
     	}
		
     	return true;
	}
	
	function callonBlur(t)
  	{
  		t.style.backgroundColor='white';
  	}
  	
  /*	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.saveProjectForm.submitBtn.onclick();
  		} 
	} */
	
	$(document).ready(function()
	{
			var options = {	target: '#message', 
					beforeSubmit: showRequest,
					success: showResponse 
				  };
			// bind to the form's submit event
			$('#saveProjectForm').submit(function() {
				$(this).ajaxSubmit(options);
				return false;
			});			
	 });
	// pre-submit callback
	function showRequest(formData, jqForm, options) {
		if(validate()){
			$(options.target).html('Loading...');
			return true;
		}	
		else 
			return false;						
	}
	function showResponse(responseText, statusText) {		
	}
	function clearDiv()
	{		
		document.getElementById('message').innerHTML='';		
	}
	
	//document.onkeypress = detectReturnKey;

	function jQueryOnchangeAutocompleter()
	{
		subinfo();
	}
	(function( $ ) {
		var calltheData=false;
		$.widget( "ui.combobox", {
			
			_create: function() {
				var self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					value = selected.val() ? selected.text() : "";
				var input = this.input = $( "<input>" )
					.insertAfter( select )
					.val( value )
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
							var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
							response( select.children( "option" ).map(function() {
								var text = $( this ).text();
								if ( this.value && ( !request.term || matcher.test(text) ) )
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: text,
										option: this
									};
							}) );
						},
						select: function( event, ui ) {
							ui.item.option.selected = true;
							jQueryOnchangeAutocompleter();
							//subinfo();
							
							self._trigger( "selected", event, {
								item: ui.item.option
							});
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
										this.selected = valid = true;
										return false;
									}
								});
								if ( !valid ) {
									// remove invalid value, as it didn't match anything
									$( this ).val( "" );
									select.val( "" );
									input.data( "autocomplete" ).term = "";
									return false;
								}
							}
						}
						
					})
					
					.addClass( "ui-widget ui-widget-content ui-corner-left" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
				};

				this.button = $( "<button type='button'>&nbsp;</button>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.insertAfter( input )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-button-icon" )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
			},

			destroy: function() {
				this.input.remove();
				this.button.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
		});
	})( jQuery );

	$(function() {
		$( "#workSpaceId" ).combobox();
		$( "#workSpaceId" ).click(function() {
				$( "#toggle" ).toggle();
		
			});
	});
	

	
</SCRIPT>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="error">
<s:fielderror></s:fielderror> <s:actionerror /></div>

<div align="center" style="color: green; size: 50px; width: 100%"
	id="message"><B><s:actionmessage /></B></div>
<br />

<div align="center"><img
	src="images/Header_Images/skeleton/replicate_from_skeleton_project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="saveAsSkeletonProject_ex"
	name="saveProjectForm" id="saveProjectForm" method="post">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Source Project</td>
			<td align="left"><s:select list="getWorkspace"
				name="workSpaceId" id="workSpaceId" headerKey="-1" headerValue=""
				listKey="workSpaceId" listValue="workSpaceDesc">

			</s:select></td>
		</tr>


		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Destination Project</td>
			<td align="left"><s:textfield name="sourceWorkSpaceDesc"
				id="sourceWorkSpaceDesc" onkeypress="clearDiv();"
				onblur="checkWorkSpaceName();"></s:textfield></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Publish Type</td>
			<td align="left"><select name="projectFor">
				<option value="-1">Select Type (eCTD/docmgmt)</option>
				<option value="P">eCTD</option>
				<option value="E">Document Management</option>
			</select></td>
		</tr>

		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left"><select name="locationCode" id="locationCode">
				<option value="-1">Select Region</option>
				<s:iterator value="getLocationDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="locationCode"/>"><s:property
							value="locationName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Department</td>
			<td align="left"><select name="deptCode" id="deptCode">
				<s:set name="statusIndi" value="statusIndi" id="statusIndi"></s:set>
				<option value="-1">Select Department</option>
				<s:iterator value="getDeptDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="deptCode"/>"><s:property
							value="deptName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left"><select name="clientCode" id="clientCode">
				<option value="-1">Select Client</option>
				<s:iterator value="getClientDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="clientCode"/>"><s:property
							value="clientName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Type</td>
			<td align="left"><select name="projectCode" id="projectCode">
				<option value="-1">Select Project Type</option>
				<s:iterator value="getProjectDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="projectCode"/>"><s:property
							value="projectName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<!-- 			<tr>	
  					<td   class="title" align="right" width="25%" style="padding: 2px; padding-right: 8px;">Document Type</td>
  					<td align="left" >
  					
  					<select name="docTypeCode">
  						<option value="-1">Select Document Type</option>
  						<s:iterator  value="getDocTypeDtl">
  							<s:if test="statusIndi != 'D'">
  								<option value="<s:property value="docTypeCode"/>"><s:property value="docTypeName"/> </option>	
  							</s:if>
  						</s:iterator>
  					</select>
  					
  					
  					</td>
  					
  				</tr>
  -->


		<tr height="2px" align="center">
			<td colspan="2" height="1">
			<hr color="#5A8AA9" size="3px"
				style="width: 85%; border-bottom: 2px solid #CDDBE4;">
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;" id="remark">Remarks</td>
			<td align="left"><s:textfield name="remark" size="30%"></s:textfield></td>
		</tr>


		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Default Admin</td>
			<td align="left"><select name="userCode">
				<option value="-1">Select Default Admin</option>
				<s:iterator value="getUserDtl">
					<s:if test="statusIndi != 'D'">
						<option value="<s:property value="userCode"/>"
							<s:if test="#session.username == userName">selected="selected"</s:if>><s:property
							value="userName" /></option>
					</s:if>
				</s:iterator>
			</select></td>
		</tr>

		<tr>
			<td></td>
			<td align="left"><input type="submit" value="Copy"
				class="button" id="btncopy" /></td>
		</tr>
	</table>
	<br>



</s:form></div>
</div>

</div>
<s:if test="noOfFilesCopied>0">
	<script type="text/javascript">
		alert(<s:property value="noOfFilesCopied"/> + ' file(s) copied.');
	</script>
</s:if>

</body>
</html>