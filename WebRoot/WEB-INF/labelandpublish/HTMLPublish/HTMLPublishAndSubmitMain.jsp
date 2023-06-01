<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>

<html>
<head>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>
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
	width: 35%;
}
.ui-autocomplete{
  	width: 250px !important;
 }
</style>
<s:head theme="ajax" />
<script type="text/javascript">
	function fun()
		{						
			if(document.forms['HTMLPublishAndSubmitForm'].logoSelection[0].checked)
			{
				document.getElementById('logoSelectionTR').style.display = 'none';
				document.getElementById('UploadLogoTR').style.display = 'block';				
			}
			else
			{
				document.getElementById('UploadLogoTR').style.display = 'none';
				document.getElementById('logoSelectionTR').style.display = 'block';								
			}
				
		}
		function subinfo()
		{
			//debugger;
			
			$('#ShowSubInfoDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
			document.forms['PublishAndSubmitFormMain'].submain.click();
			$('#ShowSubInfoDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
		}

		function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none';
			else
				document.getElementById(str).style.display = 'inline';
		}
		
		function downloadPdf(path,id){
	     	//debugger;
	     	var wsId=document.getElementById('workSpaceId').value;
	     	$.ajax(
	     	{			
	     		url: 'CreateZipFolderForHTML_ex.do?DownloadFile='+path+'&workSpaceId='+wsId,
	     		beforeSend: function()
	     		{
	     		  $('#imgPdf'+id).hide();
	     		  $('#downloading'+id).show();
	     		},
	     		success: function(data) 
	       		{
	     		  window.location = 'DownloadZipFolderForHTML_ex.do?DownloadFolderPath='+data;
	     		  $('#imgPdf'+id).show();
	     		  $('#downloading'+id).hide();
	     		  alert("Dossier downloaded successfully.");
	     		},
	     		//async: false,
	             error: function (data) {
	             	alert("Something went wrong");
	             	$('#imgPdf'+id).show();
	     			$('#downloading'+id).hide();
	                },		
	     	});
	     	return true;
	     }
		
	function validateFiles(){
		
		var fileelement = document.getElementById('uploadLogo');
				var err = false;
				if(fileelement.value == ''){
					return false;
				}else{
					var fileName = fileelement.value.substring(fileelement.value.lastIndexOf('\\')+1)
					var fileExt = fileName.substring(fileName.lastIndexOf('.')+1);
					if(fileExt != 'jpg' && fileExt != 'jpeg' && fileExt != 'gif'){
						err = true;
					}
				}
				if(err){
					
					alert('Please Upload a Valid Logo File..\r\nOnly .jpg.gif and .jpeg allowed');
					fileelement.style.backgroundColor="#FFE6F7"; 
//					document.HTMLPublishAndSubmitForm.reset();
					return false; 
				}
		return true;
	}
			
		
		function validate()
		{
			if(document.forms["HTMLPublishAndSubmitForm"].subDesc.value == "")
			{
				alert('Please enter Publish Description.');
				document.forms["HTMLPublishAndSubmitForm"].subDesc.style.backgroundColor="#FFE6F7";
				document.forms["HTMLPublishAndSubmitForm"].subDesc.focus();
				return false;
     	    }
     	    if(!validateFiles() && document.forms['HTMLPublishAndSubmitForm'].logoSelection[0].checked)
			{
				alert("Please Upload the Logo File");
				document.getElementById('uploadLogo').style.backgroundColor="#FFE6F7"; 
     			document.getElementById('uploadLogo').focus();
     			return false;
			}
			else if(document.forms.HTMLPublishAndSubmitForm.uploadLogo.value == "" && document.forms['HTMLPublishAndSubmitForm'].logoSelection[0].checked)
			{
				alert('Please Upload File');
				return false;
			}
			return true;
		}
		function publishSub(){
				
			if(validate()){
				
					document.forms["HTMLPublishAndSubmitForm"].PublishBtn.onclick();
					document.forms["HTMLPublishAndSubmitForm"].displayBtn.disabled='disabled';					
				}
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
								//jQueryOnchangeAutocompleter();
								subinfo();
								
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
						.addClass( "ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon" )
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
		


		
</script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
</head>
<body>
<div style="font-size: medium; color: green; display: none;"
	align="center"><s:actionmessage /></div>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->

<br />

<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div align="center"><!-- <img
	src="images/Header_Images/Submission/Project_Publish_Submission.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
<div class="boxborder"><div class="all_title"><b style="float:left">Project Publish </b></div>
<div
	style="padding-left: 3px; width: 100% ; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="PublishAndSubmitFormMain"
	action="ShowHTMLPublishAndSubmitForm" method="post">
	<br>
	<table Style="width:100%">
		<tr>
			<td class="title" align="right" width="50%"
				style="padding: 2px; padding-right: 8px;">Project</td>
			<td ><s:select list="getWorkspaceDetail" name="workSpaceId"
				id="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="-1" headerValue="" theme="simple" onchange="subinfo();"
				ondblclick="subinfo();">

			</s:select></td>
		</tr>

	</table>

	<s:submit name="submain" value="Show Details" cssClass="button"
		theme="ajax" targets="ShowSubInfoDiv" align="center"
		cssStyle="visibility: hidden" executeScripts="true"></s:submit>
</s:form>
<table align="center" style="width:100%">
	<tr>
		<td align="center"><s:if
			test="workSpaceId != NULL && workSpaceId.trim() != ''">
			<s:url action="ShowHTMLPublishAndSubmitForm"
				id="ShowSubmissionInfoURL">
				<s:param name="workSpaceId" value="%{workSpaceId}"></s:param>
			</s:url>
			<s:div id="ShowSubInfoDiv" theme="ajax" autoStart="true"
				href="%{ShowSubmissionInfoURL}" executeScripts="true">
				<s:if test="getWorkspaceDetail.size==0">
					<div>No details available.</div>
				</s:if>

			</s:div>
		</s:if> <s:else>
			<s:div id="ShowSubInfoDiv">
			</s:div>

		</s:else></td>
	</tr>
</table>
</div>
</div>
</div>

</div>
</div>
</div>
</div>
</body>
</html>

