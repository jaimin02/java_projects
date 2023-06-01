<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"
	type="text/javascript"></script>

<style>
#confirmDialog {
	background: white;
	color: #5B89AA;
	position: fixed;
	left: 35%;
	margin-top: 20px;
	width: 30%;
	top: 100px;
	border: 3px solid white;
	font-size: 17px;
}

#confirmDialog table td {
	
}

.customDialogButoon {
	background: white;
	color: black;
	padding: 7px;
	border-radius: 5px;
}

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
<SCRIPT type="text/javascript">

		
		function donotproceed()
		{
			$("#backgroundPopup").css('opacity','0');
			$("#backgroundPopup").hide(1000);	
			$("#confirmDialog").hide(1000);
			//$("#submitBtn").hide();
		}
		function proceed()
		{
			$("#backgroundPopup").css('opacity','0');
			$("#backgroundPopup").hide(1000);	
			$("#confirmDialog").hide(2000);
			//$("#submitBtn").show();		
		}
		function callForProjDocStatus(wsid,wname)
		{
			
			$("#backgroundPopup").css('opacity','0.8');
			$("#backgroundPopup").show();
			$("#confirmDialog").show(1000);
			
			 $.ajax({
				 
				 url : "ProjDocStatus_ex.do?workspaceId="+wsid,
				 beforeSend: function()
					{ 
					
						$('#statusDetails').html('');								
					},
				 success:function(data)
				 {
						var str="<br/>";
						var leafCount="";
						var historyCount="";
						var d = data.split('#');
						var projectname=wname;
						var tblstr="";
						tblstr="<table border=1 class=attrDisplay cellspacing=2 width=100%><th colspan=2>Project Name:"+projectname+"</th>";
							
						for(var i=0;i<d.length;i++)
						{
							tblstr+="<tr>";		
							if (d[i].match("TotalNodes")) 
							{
								leafCount = d[i].split('=')[1]; 
							}
							if(d[i].match("PerLeafNodes"))
							{
								$('#labelDiv').html($('#labelDiv').html()+""+d[i].split('=')[2]+"%");
								historyCount = d[i].split(',')[0].split('=')[1];
							}
							else if(d[i].match("Per"))
							{
								var splstr = d[i].split(',');
								var finalStr = splstr[0].replace("Per","")+"%";
								var perval = splstr[0].split('=')[1];	
								finalStr+= " [ "+ splstr[1].split('=')[1] + "/"+leafCount + " ] <br/><br/>" ;
								console.log(finalStr.split('=')[0]);
								console.log(finalStr.split('=')[1]);
								tblstr+="<td>"+finalStr.split('=')[0]+"</td><td>"+finalStr.split('=')[1]+"</td>";
								str+=finalStr;
							}
							tblstr+="</tr>";
						}
						
						str+="&nbsp;&nbsp;&nbsp;Total Nodes with files = "+ historyCount+"<br/>";
						str+="&nbsp;&nbsp;&nbsp;Total Nodes without files = "+ (leafCount-historyCount)+"<br/>";
						tblstr+="<tr><td>Total Nodes with files</td><td>"+historyCount+"</td></tr>";
						tblstr+="<tr><td>Total Nodes without files</td><td>"+(leafCount-historyCount)+"</td></tr>";
						tblstr+="</table>";
						$('#statusDetails').html(tblstr);	
							

				 }
				 });
		}
			function subinfoOfDiv()
			{	
				
					callForProjDocStatus($('#WsId').val(),$('#WsId option:selected').html());
				
				var WsId=$('#WsId').val();
					$.ajax({			
						url: 'PublishAndSubmitDMS_ex.do?WsId=' + WsId,
						beforeSend: function()
						{ 
							$('#ShowSubInfoDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
						},
				  		success: function(data) 
				  		{			
							$("#ShowSubInfoDiv").html(data);							
						},
						async: true							 
					});
					return true;
				}

			function jQueryOnchangeAutocompleter()
			{
				subinfoOfDiv();
				
				
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
									//subinfoOfDiv();
									
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
				$( "#WsId" ).combobox();
				$( "#WsId" ).click(function() {
						$( "#ShowSubInfoDiv" ).toggle();
				
					});
			});
		</SCRIPT>
</head>
<body>
<br />

<div align="center"><!--  popup for displaying project status -->
<div id="confirmDialog"
	style="position: fixed; top: 100px; z-index: 100000; display: none;">
<div id="statusDetails"></div>
<br>
<input type="button" value="Proceed" class="button" id="btnProceed" style="margin-right: 50px;"
	onclick="proceed();" /> <!-- <input type="button" value="Do Not Proceed"
	class="button" id="btnDoNotProceed" onclick="donotproceed()" /> -->
	<form action="ExportToXls.do" method="post" id="myform" style="margin-left: 5px;margin-top: -24px"><input
			type="hidden" name="fileName" value="Project_Completion_Report.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableTextArea"
			style="visibility: hidden;"></textarea> <input type="button" value="Print" class="button"
			onclick="document.getElementById('tableTextArea').value=document.getElementById('statusDetails').innerHTML;;document.getElementById('myform').submit()">
		</form>
	</div>



<div id="backgroundPopup" onclick="donotproceed();"></div>


</div>
<div align="center"><img
	src="images/Header_Images/Submission/Project_Publish_Submission.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
<div
	style="width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="center"><br>


<table width="900px;">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Project Name</td>
		<td align="left" width="75%"><select id="WsId">
			<option value="-1"></option>

			<s:iterator value="wsMstList">
				<option value="<s:property value="workSpaceId"/>"
					<s:if test="workSpaceId == WsId">selected="selected"</s:if>><s:property
					value="workSpaceDesc" /></option>
			</s:iterator>
		</select> <s:if test="WsId != NULL && WsId.trim() != ''">
			<SCRIPT type="text/javascript">
						  				jQueryOnchangeAutocompleter();
									</script>

		</s:if></td>
	</tr>
</table>
<table width="900px;">
	<tr>
		<td align="center">
		<div id="ShowSubInfoDiv" align="center"></div>
		</td>
	</tr>
</table>



</div>
</div>
</body>
</html>