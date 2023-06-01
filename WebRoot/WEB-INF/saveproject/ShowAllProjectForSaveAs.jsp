<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link rel="stylesheet"
	href="js/jquery/autocompleter/css/jquery.ui.all.css">
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

<SCRIPT type="text/javascript">
	
	
	function trim(str)
	{
	   	str = str.replace( /^\s+/g, "" );// strip leading
		return str.replace( /\s+$/g, "" );// strip trailing
	}
	function check(strString)
	{
	     strString=trim(strString);
	   	 if(strString.indexOf("\\")!=-1){alert("Invalid destination project name." + " " + "\\ is not allowed");return false;}
		    
		    var strInvalidChars ="/\^$#:~%@&*`!;'\"\+\=({}<>?)[]";
	    	var strChar;
	   	  	var blnResult = true;
	   	  	 
	   	if(strString.length < 5)
		{
		
	   		alert("Destination project name must be greater then 5 char.");
			document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
	     	document.saveProjectForm.sourceWorkSpaceDesc.focus();
			return false;
		}
		if(strString.length > 60)
		{
			alert("Destination project name must be less then 60 char.");
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
      					return false;
      				}
      				else
      				{
       					alert("Invalid destination project name." + " " + strChar + "  is not allowed" );
       					return false;
       				}
	 			}
	      	}
	      	if(!strString.match(/^([a-zA-Z0-9])/) || !strString.match(/([a-zA-Z0-9])$/))
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
     	else if(check(document.saveProjectForm.sourceWorkSpaceDesc.value)==false) {
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
     	else if(document.saveProjectForm.docTypeCode.value==-1)
		{
			alert("Please select document type name..");
			document.saveProjectForm.docTypeCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.docTypeCode.focus();
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
     	else if(document.saveProjectForm.defaultAdminCode.value==-1)
		{
			alert("Please select default Admin ..");
			document.saveProjectForm.defaultAdminCode.style.backgroundColor="#FFE6F7"; 
     		document.saveProjectForm.defaultAdminCode.focus();
     		return false;
     	}
     	
     	else if(document.saveProjectForm.remark.value.length>250)
		{
			alert("Reason for Change cannot be of more then 250 charactars..");
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
  	
 /* 	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.saveProjectForm.submitBtn.onclick();
  		} 
	} */

	
	
	function clearDiv()
	{		
		document.getElementById('message').innerHTML='';		
	}
	//document.onkeypress = detectReturnKey;
	$(document).ready(function()
			{
				
					$('.target').change(function(){
					
					var correct=true;
					if(check(document.saveProjectForm.sourceWorkSpaceDesc.value)==false) 
					{
				     	document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
			     		document.saveProjectForm.sourceWorkSpaceDesc.focus();
						correct=false;
					}
					
			     	else if(document.saveProjectForm.sourceWorkSpaceDesc.value.length>255)
					{
						alert("Project name cannot be of more than 255 charactars..");
						document.saveProjectForm.sourceWorkSpaceDesc.style.backgroundColor="#FFE6F7"; 
			     		document.saveProjectForm.sourceWorkSpaceDesc.focus();
			     		correct=false;
			     	}	
					var workspace=document.getElementById('sourceWorkSpaceDesc').value;
					document.getElementById('sourceWorkSpaceDesc').value =trim(workspace);
					workspace=document.getElementById('sourceWorkSpaceDesc').value; 
					var urlOfAction="WorkSpaceExistsForUpdate.do";
					var dataofAction="workSpaceDesc="+workspace;
					if (correct==true)
					{
						$.ajax({
							type: "GET", 
			   				url: urlOfAction, 
			   				data: dataofAction, 
			   				cache: false,
			   				dataType:'text',	   				
							success: function(response){
								$('#message').html(response);
								if(response.indexOf('Available') == -1){							
									document.getElementById('saveProjectbtn').disabled='disabled';
									return false;
								}
								else if(response.indexOf('Available') != -1)
								{
									document.getElementById('saveProjectbtn').disabled='';
									return true;
								}
								//alert($('#htmlRole').html(response));
								//addMessage($('#htmlRole').html(response));
							}
						});
					}
				});
			});


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
							//jQueryOnchangeAutocompleter();
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

<div class="errordiv" align="center" style="color: red" id="message">
<b> <s:fielderror></s:fielderror> <s:actionerror /> <s:actionmessage />
</b>
<div align="center" style="color: green; size: 50px"></div>
</div>
<br />
<div align="center"><img
	src="images/Header_Images/Project/Replicate_Project.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="saveAsProject"
	name="saveProjectForm" method="post">
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
				id="sourceWorkSpaceDesc" cssClass="target" onkeypress="clearDiv();"></s:textfield>
			</td>
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
			<td align="left"><select name="locationCode">
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
			<td align="left"><select name="deptCode">
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
			<td align="left"><select name="clientCode">
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
			<td align="left"><select name="projectCode">
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
				style="padding: 2px; padding-right: 8px;" id="remark">Reason for Change</td>
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
			<td align="left"><s:submit name="submitBtn"
				onclick="return validate();" value="Copy" cssClass="button"
				id="saveProjectbtn"></s:submit></td>
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

