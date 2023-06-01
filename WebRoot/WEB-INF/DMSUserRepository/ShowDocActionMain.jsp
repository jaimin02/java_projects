<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head theme="ajax" />
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
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

<script type="text/javascript">
			function getCategories()
			{
				var selectedProject = document.getElementById('workSpaceId').options[document.getElementById('workSpaceId').selectedIndex].value;
				if (selectedProject != -1) {
					var commURL = "getCatDtl_ex.do?workspaceId="+selectedProject;
					$.ajax
					({			
							url: commURL,
							beforeSend: function()
							{
								$('#showDocDiv').html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
								$("#showCategory").html("");
							},
							success: function(data) 
					  		{
								$('#showDocDiv').html("");		  			
								$("#showCategory").html(data);	    		
							}	  		
					});	
				}
				else
				{
					$("#showCategory").html("");
					data = "<option id='All##All' value='All##All' style='display: block;'></option>";
					$("#showDocDiv").html("");
					$("#showCategory").html(data);
				}
				
			}


		function showDocIDDtls()
		{
			
			var ndId = "";
			var selectedProject = document.getElementById('workSpaceId').options[document.getElementById('workSpaceId').selectedIndex].value;
			
			var selectdCategoryData = $('#showCategory').selected().val();
			var sendDataCnt = 0;
			
			var forSend = selectdCategoryData.split('##');
			ndId = forSend[0];
			if (selectedProject != '-1' && ndId != 'All') {
				var commURL = "DocIdDtls_ex.do?workSpaceId="+selectedProject+"&nodeId="+ndId;
				$.ajax
				({			
						url: commURL,
						beforeSend: function()
						{
							$('#showDocDiv').html("");
							$('#showDocDocIdDiv').html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
						},
						success: function(data) 
				  		{
							$("#showDocDocIdDiv").html(data);
							$('#showDocDiv').html("");	    		
						}	  		
				});	
			}
			else
			{
				$('#showDocDiv').html("");
				$("#showDocDocIdDiv").html("");
			}
			
			
		}


		
		function getDetails()
		{
			var selectedProject = document.getElementById('workSpaceId').options[document.getElementById('workSpaceId').selectedIndex].value;
			var selectdCategoryData = $('#showCategory').selected().val();
			var sendDataCnt = 0;
			var docId = $('#projList').selected().val()+"-"+$('#categoryList').selected().val()+"-"+$('#yearList').selected().val();
			var fromId = $('#fromId').val();
			var toId = $('#toId').val();
			if(fromId.length == 0 || toId.length == 0)
			{
				alert("Please Enter The 'From' and 'To' Value.");
				return false;
			}
			fromId = parseInt(fromId);
			toId = parseInt(toId);
			if (fromId 	> toId ) 
			{
				alert("'From' Value Cannot Be Greater Then 'To' Value.");
				return false;
			}
			var forSend = selectdCategoryData.split('##');
			ndId = forSend[0]
			if (selectedProject != -1) {
				var commURL = "DocActionDtl_ex.do?workspaceId="+selectedProject+"&nodeId="+ndId+"&folderName="+docId+"&fromId="+fromId+"&toId="+toId;
				$.ajax
				({			
						url: commURL,
						beforeSend: function()
						{
							$('#showDocDiv').html("<br/><img class=\"test\" src=\"images/loading.gif\" alt=\"loading ...\" />");
						},
						success: function(data) 
				  		{
							$("#showDocDiv").html(data);
						}	  		
				});
				
			}
			else
			{
				data = "<option id='All##All' value='All##All' style='display: block;'></option>";
				$("#showDocDiv").html("");
			}
		}
			
			function trim(str)
			{
			   	str = str.replace( /^\s+/g, "" );// strip leading
				return str.replace( /\s+$/g, "" );// strip trailing
			}

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
										else if(this.previousSibling.id === 'showCategory')
										{
											showDocIDDtls()
											
										}
										else
										{
											getDetails()
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
				$( "#workSpaceId, #showCategory" ).combobox();
			});			


		</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center"><img
	src="images/Header_Images/DMSUserRepository/DocActionDetails.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="showDocForm" method="post"
	action="ShowDocDtl_ex">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Select Project&nbsp;</td>
			<td><s:select list="workspaceMstList" name="workSpaceId"
				id="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="-1" headerValue="" theme="simple">
			</s:select></td>
		</tr>
		<tr>

			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Select Category&nbsp;</td>
			<td><select id='showCategory' name="categoryMst">
				<option id="All##All" value="All##All" style="display: block;"></option>
			</select></td>
		</tr>
	</table>
</s:form> <br>
<div id="showDocDocIdDiv" align="center"></div>
<div id="showDocDiv" align="center"></div>
</div>
</div>
</div>
</body>
</html>