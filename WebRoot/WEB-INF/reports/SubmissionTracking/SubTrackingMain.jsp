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

<s:head theme="ajax" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<SCRIPT type="text/javascript">
			$(document).ready(function() { 
				$(".dateValue").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
				//Setting for Current quater value set in datepicker control.. --Start Here
				var d = new Date();
				var quarter = Math.floor((d.getMonth() / 3));
		      	var firstDate = new Date(d.getFullYear(), quarter * 3, 1);
		      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
		      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 3, 0));
				//--End Here
		      	
				var options = {	target: '#SubTrackingReport',beforeSubmit: showRequest};
				$('#myForm').submit(function() {
					// This is for updating the hidden value for workspaceid.
					var sendDataCnt = 0;
					var sendData = "";
					var projectDtl = document.getElementById('projectData').options;
					//alert($('#projectData').selected().val());
					var selectdProjectData = $('#projectData').selected().val();
					if(selectdProjectData == "All##All##All")
					{
						for ( var i = 0; i < projectDtl.length; i++) {
							var idPMST = projectDtl[i].value;
							if(document.getElementById(idPMST).style.display == "block" && i!= 0)
							{
								var getData = document.getElementById(idPMST).value;
								var forSend = getData.split('##');
								sendData += forSend[0]+",";
								sendDataCnt = sendDataCnt + 1;
							}
						}
					}
					else
					{
						var forSend = selectdProjectData.split('##');
						//alert(forSend[0]);
						sendData = forSend[0]+",";
					}
					document.getElementById('selectedProjectList').value = sendData.substring(0, sendData.length-1);
					//alert(document.getElementById('selectedProjectList').value);
					$(this).ajaxSubmit(options);
					return false;
				});
				$('#regionData').change(function(){
					checkProjectData();
				});
				$('#clientData').change(function(){
					checkProjectData();
				});
				function checkProjectData()
				{
					var selectedClient = $('#clientData').selected().val();
					var selectedRegion = $('#regionData').selected().val();
					var commURL = "SubmissionTrackingGetWorkspaces_ex.do?client="+selectedClient+"&region="+selectedRegion;
					$.ajax
					({			
							url: commURL,
							success: function(data) 
					  		{
				  				$('#projectData').html("");				  				
				  				$('.ui-autocomplete-input').val('');
								$('#projectData').html(data);	    		
							}	  		
					});
				}
				
				$('#durationType').change(function() {
					var id = this.value;
					//alert(id);
					d = new Date();
					//d = new Date('2010','00','03');
					//alert(d);
				 	switch (id) 
				 	{
					    case "CQ":
				    		var quarter = Math.floor((d.getMonth() / 3));
					      	var firstDate = new Date(d.getFullYear(), quarter * 3, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 3, 0));
					    break;
					    case "CH":
					    	var half = Math.floor((d.getMonth() / 6));
					      	var firstDate = new Date(d.getFullYear(), half * 6, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 6, 0));
					    break;
					    case "CY":
					    	var year = Math.floor((d.getMonth() / 12));
					      	var firstDate = new Date(d.getFullYear(), year * 12, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 12, 0));
					    break;
					    case "LQ":
					    	var quarter = Math.floor((d.getMonth() / 3));
					      	var firstDate = new Date(d.getFullYear(), quarter * 3 - 3, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
						  	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 3, 0));
					    break;
					    case "LH":
					    	var half = Math.floor((d.getMonth() / 6));
					      	var firstDate = new Date(d.getFullYear(), half * 6 - 6, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 6, 0));
					    break;
					    case "LY":
					    	var year = Math.floor((d.getMonth() / 12));
					      	var firstDate = new Date(d.getFullYear(), year * 12 - 12, 1);
					      	$("#fromSuibmissionDate").datepicker("setDate", firstDate);
					      	$("#toSuibmissionDate").datepicker("setDate", new Date(firstDate.getFullYear(), firstDate.getMonth() + 12, 0));
					    break;
				    }
				});
			});

			function showRequest(formData, jqForm, options)
			{
				$(options.target).html('<img border="0px" title="Loading" alt="Loading" src="images/loading.gif" height="40px" width="40px" >');
				return true;
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
				$( "#projectData" ).combobox();
				$( "#projectData" ).click(function() {
						$( "#toggle" ).toggle();
				
					});
			});


			
		</script>
</head>
<body>
<br>
<div align="center"><img
	src="images/Header_Images/Report/Submission_Tracking_Report.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div style="width: 95%"><s:form
	action="SubmissionTrackingSearch_ex" method="post" id="myForm">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region</td>
			<td align="left" width="85%"><s:select list="regionMstList"
				name="regionMst" listKey="locationCode" listValue="locationName"
				headerKey="-1" headerValue="All Regions" id="regionData">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Client</td>
			<td align="left" width="85%"><s:select list="clientMstList"
				name="clientMst" listKey="clientCode" listValue="clientName"
				headerKey="-1" headerValue="All Clients" id="clientData">
			</s:select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td align="left" width="85%"><select id='projectData'
				name="workspaceMst">
				<option id="All##All##All" value="All##All##All"
					style="display: block;"></option>
				<s:iterator value="workspaceMstList">
					<option
						id="<s:property value="workSpaceId"/>##<s:property value="locationCode"/>##<s:property value="clientCode"/>"
						value="<s:property value="workSpaceId"/>##<s:property value="locationCode"/>##<s:property value="clientCode"/>"
						style="display: block;"><s:property value="workSpaceDesc" /></option>
				</s:iterator>
			</select></td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Submission Date</td>
			<td align="left" width="85%" class="title"><SELECT
				name="durationType" id="durationType">
				<option value="CQ">Current Quarter</option>
				<option value="CH">Current Half Year</option>
				<option value="CY">Current Year</option>
				<option value="LQ">Last Quarter</option>
				<option value="LH">Last Half Year</option>
				<option value="LY">Last Year</option>
			</SELECT> <input type="text" value="<s:property value="currentDate"/>"
				id="currDateFromServer" style="display: none;"></td>
		</tr>
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
			<div align="left" class="displayDuration">From <input
				type="text" name="fromSubmissionDate" class="dateValue"
				id="fromSuibmissionDate"> (YYYY/MM/DD) &nbsp; <IMG
				onclick="
										if(document.getElementById('fromSuibmissionDate').value != '' && confirm('Are you sure?'))
			   								document.getElementById('fromSuibmissionDate').value = '';"
				src="<%=request.getContextPath() %>/images/clear.jpg" align="middle"
				title="Clear Date"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left" width="85%" class="title">
			<div align="left" class="displayDuration">
			&nbsp;&nbsp;&nbsp;&nbsp;To <input type="text" name="toSubmissionDate"
				class="dateValue" id="toSuibmissionDate"> (YYYY/MM/DD)
			&nbsp; <IMG
				onclick="
										if(document.getElementById('toSuibmissionDate').value != '' && confirm('Are you sure?'))
			   								document.getElementById('toSuibmissionDate').value = '';"
				src="<%=request.getContextPath() %>/images/clear.jpg" align="middle"
				title="Clear Date"></div>
			</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Report Type</td>
			<td align="left" width="85%"><SELECT name="reportType"
				id="reportType">
				<option value="trackReport">Tracking Report</option>
				<option value="fullReport">Full Report</option>
			</SELECT></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit" value="Search"
				title="Search Date" id="searchData" class="button"> <input
				type="hidden" value="" name="selectedProjectList"
				id="selectedProjectList"></td>
		</tr>
	</table>
</s:form> <br />
<br />
<div id="SubTrackingReport"></div>
<br />
</div>
</div>
</div>
</body>
</html>