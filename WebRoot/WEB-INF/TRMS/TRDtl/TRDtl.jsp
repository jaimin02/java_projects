<%@page contentType="text/html; charset=UTF-8" language="java"%>
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
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
				$( "#refWorkspaceId" ).combobox();
			});

			
			$(document).ready(function() { 
				var options = {success: function(data)	{
					alert(data);
					location.reload();
				}};
				$('#saveTRDtlForm').submit(function(){
					$(this).ajaxSubmit(options);
					return false;
				});
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
			function isNumberKey(evt)
		    {
		         var charCode = (evt.which) ? evt.which : event.keyCode;
		         if (charCode > 31 && ((charCode < 48) || (charCode > 57)))
		            return false;
		         return true;
		    }
			function editTRDtl(trainingRecordNo)
			{
				$.ajax({			
					url: 'editTRDtl_ex.do?trainingRecordNo=' + trainingRecordNo,
			  		success: function(data) 
			  		{
						$('#popupContact').html(data);
			  		}										
				});
				//centering with css
				centerPopup();
				//load popup
				loadPopup();
			}
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
</div>
<div align="center"><img
	src="images/Header_Images/Project/Training_Details.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="saveTRDtlForm" id="saveTRDtlForm"
	method="post" action="SaveTRDtl_ex">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Record
			No&nbsp;</td>
			<td align="left"><s:textfield name="trainingId" id="trainingId"
				size="25"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training Topic&nbsp;</td>
			<td align="left"><s:textfield name="trainingHdr"
				id="trainingHdr" size="25"></s:textfield></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Training
			Details&nbsp;</td>
			<td align="left"><s:textarea name="trainingDtl" id="trainingDtl"
				cols="26" rows="5"></s:textarea></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Exp. Training
			Duration&nbsp;</td>
			<td align="left"><s:textfield name="totalTrainingDuration"
				onkeypress="return isNumberKey(event)" id="totalTrainingDuration"
				size="25" maxLength="8"></s:textfield>&nbsp;&nbsp; (In Min.)</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Reference
			Project&nbsp;</td>
			<td align="left"><s:select list="workspaceMstList"
				headerKey="-999" headerValue="No Project" listKey="workSpaceId"
				listValue="workSpaceDesc" id="refWorkspaceId" name="refWorkspaceId"
				theme="simple"></s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Remarks&nbsp;</td>
			<td align="left"><s:textarea name="remark" id="remark" cols="26"
				rows="5"></s:textarea></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit"
				value="Generate Training Record" class="button"></td>
		</tr>
	</table>
	<br>
</s:form></div>
<s:if test="trainngRecordDetailsList.size() > 0 ">
	<div>
	<table class="datatable" width="95%" cellspacing="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Training Record No</th>
				<th>Training Topic</th>
				<th>Training Details</th>
				<th>Exp. Training Duration</th>
				<th>Reference Project</th>
				<th>Remarks</th>
				<th>Modify On</th>
				<th>Modify By</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="trainngRecordDetailsList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="trainingId" /></td>
					<td><s:property value="trainingHdr" /></td>
					<td><s:property value="trainingDtl" /></td>
					<td><s:property value="totalTrainingDuration" /> &nbsp;Min.</td>
					<td><s:property value="refWorkspaceDesc" /></td>
					<td><s:property value="remark" /></td>
					<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
					<td><s:property value="modifyByUser" /></td>
					<td>
					<div align="center"><img border="0px" alt="Edit"
						src="images/Common/edit.png" height="18px" width="18px"
						onclick="editTRDtl('<s:property value="trainingRecordNo"/>')">
					</div>
					</td>
					<td>
					<div align="center"><img border="0px" alt="Delete"
						src="images/Common/delete.png" height="18px" width="18px"
						onclick="deleteTRDtl('<s:property value="trainingRecordNo"/>')">
					</div>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br />
	</div>
</s:if></div>
</div>
<div id="backgroundPopup"></div>
<div id="popupContact" style="width: 550px; height: 350px;"></div>
</body>
</html>