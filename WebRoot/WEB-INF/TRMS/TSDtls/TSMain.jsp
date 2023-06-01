<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/datepair.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/jquery.timepicker.js"></script>
<SCRIPT type="text/javascript">
		function subinfo()
		{
			var tRNo=$('#tRNo').val();
			$.ajax
			({			
					url: "ShowTSDtl_ex.do?trainingRecordNo="+tRNo,
					beforeSend: function()
					{
						$('#TSDtlsDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
					},
					success: function(data) 
			  		{
						$('#TSDtlsDiv').html(data);
					}	  		
			});
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
				$( "#tRNo" ).combobox();
			});

			function deleteTRDtl(trainingRecordNo)
			{
				$.ajax({			
					url: 'deleteTRDtl_ex.do?trainingRecordNo=' + trainingRecordNo,
			  		success: function(data) 
			  		{
				  		alert(data);
						location.reload();
					}										
				});
			}
			
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
</div>
<div align="center"><img
	src="images/Header_Images/Project/Training_Scheduling.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">
<table align="center" width="100%" cellspacing="0">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 6px;">Training Record
		No&nbsp;</td>
		<td align="left"><s:select list="tRDtlsList" headerKey="-999"
			headerValue="" listKey="trainingRecordNo" listValue="trainingId"
			id="tRNo" name="tRNo" theme="simple" onchange="subinfo();"
			ondblclick="subinfo();"></s:select></td>
	</tr>
</table>
<div align="center" id="TSDtlsDiv"></div>
</div>
</div>
</div>

</body>
</html>