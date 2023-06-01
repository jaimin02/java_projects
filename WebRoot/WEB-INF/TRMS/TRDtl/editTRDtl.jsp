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
<script type="text/javascript">
			$(document).ready(function() {
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
					$( "#refWorkspaceIdEd" ).combobox();
				});
	
				$('#popupContactClose').click(function(){
					disablePopup();
				});
	
				var options = {success: function(data)	{
					alert(data);
					location.reload();
				}};
				$('#editTRDtlForm').submit(function(){
					$(this).ajaxSubmit(options);
					return false;
				});
			});
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;"
	id="editMessage"></div>
<div><a id="popupContactClose"><img alt="Close" title="Close"
	src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">Edit
Training Record Detail</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div
	style="width: 100%; height: 300px; overflow-y: auto; border: 1px solid #5A8AA9; margin-top: 10px;"
	align="center"><s:form action="SaveTRDtl_ex" method="post"
	name="editTRDtlForm" id="editTRDtlForm">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Id&nbsp;</td>
			<td align="left"><input type="text" name="trainingId"
				id="trainingId" size="25"
				value="<s:property value='dtoTrainingRecordDtls.trainingId'/>"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Topic&nbsp;</td>
			<td align="left"><input type="text" name="trainingHdr"
				id="trainingHdr" size="25"
				value="<s:property value='dtoTrainingRecordDtls.trainingHdr'/>"></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training
			Details&nbsp;</td>
			<td align="left"><textarea name="trainingDtl" id="trainingDtl"
				cols="26" rows="5"><s:property
				value="dtoTrainingRecordDtls.trainingDtl" /></textarea></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Total Training
			Duration&nbsp;</td>
			<td align="left"><input type="text" name="totalTrainingDuration"
				id="totalTrainingDuration" size="25" size="25" maxLength="8"
				value="<s:property value='dtoTrainingRecordDtls.totalTrainingDuration'/>">&nbsp;&nbsp;
			(In Min.)</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Reference
			Project&nbsp;</td>
			<td align="left"><select id="refWorkspaceIdEd"
				name="refWorkspaceId">
				<option value="-999">No Project</option>
				<s:iterator value="workspaceMstList">
					<option
						<s:if test="dtoTrainingRecordDtls.refWorkspaceId == workSpaceId">
													selected="selected"
												</s:if>
						value="<s:property value="workSpaceId"/>"><s:property
						value="workSpaceDesc" /></option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><textarea name="remark" id="remark1" cols="26"
				rows="5"><s:property value="dtoTrainingRecordDtls.remark" /></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit"
				value="Update Training Record" class="button"></td>
		</tr>
	</table>
	<s:hidden name="trainingRecordNo"></s:hidden>
	<s:hidden name="update" value="true"></s:hidden>
</s:form></div>
<br>
</div>
</body>
</html>