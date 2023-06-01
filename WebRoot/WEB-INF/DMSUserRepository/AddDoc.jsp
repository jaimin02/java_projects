<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
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
<script type="text/javascript">
		/*
		$(document).ready(function() 
		{	
			$("#submitBtn").hide();
			var options = {	
					success: showResponse 
				};
			$("#saveFormButton").click(function(){
				if(checkData())					
					$("#saveDocForm").ajaxSubmit(options);
				return false;			
			});
		});
		function showResponse(responseText, statusText) 
		{
			alert(responseText);
			getDocDtl();
		}*/
		var dropClick=0;
		function getCategories()
		{
			
			var selectedProject = document.getElementById('workSpaceId').options[document.getElementById('workSpaceId').selectedIndex].value;
			
			var commURL = "ShowDtlsShowCategory_ex.do?workSpaceId="+selectedProject;
			
			$.ajax
			({			
					url: commURL,
					beforeSend: function()
					{
						$("#showCategoryDiv").html("");
						$("#nodeId").html("");
						$('#loadImgDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
														
					},
					success: function(data) 
			  		{
						$('#loadImgDiv').html("");
						$("#nodeId").html(data);	    		
					}	  		
			});
		}
		function getDocDtl()
		{
			var ndId = $('#nodeId').val();
			var wsId = document.getElementById('workSpaceId').options[document.getElementById('workSpaceId').selectedIndex].value;
			var sendDataCnt = 0;
			var mtype = '<s:property value="mtype"/>';
			if (ndId != 'All##All') {
				var commURL = "ShowDtls_ex.do?nodeId="+ndId+"&workSpaceId="+wsId+'&mtype='+mtype;
				$.ajax
				({			
						url: commURL,
						beforeSend: function()
						{
							$("#showCategoryDiv").html("");
							$('#loadImgDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
						},
						success: function(data) 
				  		{
					  		
							$('#loadImgDiv').html("");				  		
							$("#showCategoryDiv").html(data);
	
						}	  		
				});
			}
			else
			{
				$('#loadImgDiv').html("");
				$("#showCategoryDiv").html("");
			}
		}

		function openFile(path)
		{
			var newWindow = "openfile.do?fileWithPath="+path;
			var win3=window.open(newWindow);
		}
		/*
		function checkData()
		{
			var nodevalue = $("#nodeName").val();
			var documentsource = $("#documentSrc").val();
			var ret=true;
			if(nodevalue == "" )
			{
				alert("You must have to enter Document Title..");
				ret=false;
				return false;
			}
			if(!nodevalue.match(/^([a-zA-Z0-9\/\u002D\u002C-_])*$/))
			{
				alert("Only digits, Alphabets, '-' , '_', '/' and ',' are allowed.");
				ret=false;
			   	return false;
			}

			if(documentsource == "New")
			{
				if(document.getElementById("attachment").value == "")
				{
					alert("You must have to attach document..");
					ret=false;
					return false;
				}
			}		
			return ret;
		}
		*/
		function jQueryOnchangeAutocompleter()
		{
			getCategories();
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
									if (this.previousSibling.id === 'workSpaceId')
									{							
										jQueryOnchangeAutocompleter();
									}
									else 
									{
										getDocDtl();
									}
								
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
			$( "#workSpaceId, #nodeId" ).combobox();
		});			
		
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center"><s:if test="mtype.equalsIgnoreCase('n')">
	<img src="images/Header_Images/DMSUserRepository/Add_Edit_Document.jpg"
		style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
</s:if> <s:if test="!mtype.equalsIgnoreCase('n')">
	<img src="images/Header_Images/DMSUserRepository/Release_Dcument.jpg"
		style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
</s:if>

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">

<table align="center" width="100%" cellspacing="0">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 6px;">Select Project&nbsp;</td>
		<td><input type="hidden" name="mtype"
			value="<s:property value="mtype"/>"> <s:hidden name="mode"
			id="mode" value="add"></s:hidden> <s:select list="workspaceMstList"
			name="workSpaceId" id="workSpaceId" listKey="workSpaceId"
			listValue="workSpaceDesc" headerKey="-1" headerValue=""
			theme="simple">
		</s:select></td>
	</tr>
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 6px;">Select Category&nbsp;</td>
		<td><select id='nodeId' name="nodeId">
			<option id="All##All" value="All##All" style="display: block;"></option>
		</select></td>
	</tr>
</table>
<div id="showCategoryDiv"></div>
<div id="loadImgDiv" align="center"></div>
</div>
</div>
</div>
</body>
</html>