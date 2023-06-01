<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/datepair.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/timepicker/jquery.timepicker.js"></script>
<SCRIPT type="text/javascript">
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
				$( "#trainingRecordNo" ).combobox();
			});

			
			
			$(document).ready(function() {

				$('.dtpicker').datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
				var todayDate = new Date(); 
				var month = todayDate.getMonth()+1;

				if(month<10){
					month= "0"+month;
				}
				var date = todayDate.getDate();
				if (date < 10) {
					date = "0"+date;
				}
				$('.dtpicker').each(function(){
						this.value = todayDate.getFullYear()+"/"+month+"/"+date;
				});
			});

			$(document).ready(function() { 
				var options = {success: function(data)	{
					$('#TARptDiv').html(data);
				}};
				$('#AttRpt').submit(function(){
					$('#TARptDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
					$(this).ajaxSubmit(options);
					return false;
				});
			});
			
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
<div align="left"><s:form name="AttRpt" id="AttRpt"
	action="getAttRpt_ex">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Record
			No&nbsp;</td>
			<td align="left"><s:select list="tRDtlsList" headerKey="-999"
				headerValue="All Training Records" listKey="trainingRecordNo"
				listValue="trainingId" id="trainingRecordNo" name="trainingRecordNo"
				theme="simple"></s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" style="padding: 6px;"><input
				type="radio" name="selectdate" value="Y" checked="checked"
				id="selectdate"></td>
			<td align="left" style="padding: 6px;"><label class="title"
				for="selectdate" style="padding-left: 5px;">Search</label>&nbsp;&nbsp;
			<select name="search" style="min-width: 0px;">
				<option value="O">On</option>
				<option value="B">Before</option>
				<option value="A">After</option>
			</select>&nbsp;&nbsp; <input type="text" name="trainingDate"
				readonly="readonly" class="dtpicker" size="10"> (YYYY/MM/DD)
			</td>
		</tr>
		<tr>
			<td class="title" align="right" style="padding: 6px;"><input
				type="radio" name="selectdate" value="N" id="fromtodate"></td>
			<td align="left" style="padding: 6px;"><label class="title"
				for="fromtodate" style="padding-left: 5px;">From</label>&nbsp;&nbsp;
			<input type="text" name="trainingStartDate" readonly="readonly"
				class="dtpicker" size="10">&nbsp;&nbsp; <label class="title"
				for="fromtodate" style="padding-left: 3px;">To</label>&nbsp;&nbsp; <input
				type="text" name="trainingEndDate" readonly="readonly"
				class="dtpicker" size="10"> (YYYY/MM/DD)</td>
		</tr>
		<tr>
			<td class="title" align="right" style="padding: 6px;">Criteria
			On :</td>

			<td align="left" style="padding: 6px;"><select
				name="conditionOn" style="min-width: 0px;">
				<option value="A">Training Start Or End Date</option>
				<option value="S">Training Start Date</option>
				<option value="E">Training End Date</option>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="  Go  " name="subbtn"
				id="subbtn" class="button"></td>
		</tr>
	</table>
</s:form> <br />
<br />
<div align="center" id="TARptDiv"></div>
<br />
</div>
</div>
</div>
</body>
</html>