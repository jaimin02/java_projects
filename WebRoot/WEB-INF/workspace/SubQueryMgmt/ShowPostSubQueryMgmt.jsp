<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
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
		$(document).ready(function() 
				{
					if(document.getElementById("workspaceId") != "-1")
					{ 	
						subinfo();
					}	
				});
		
		var openImg = new Image();
		openImg.src = "images/open.gif";
		var closedImg = new Image();
		closedImg.src = "images/closed.gif";
			 $(document).ready(function() {
				 $("#addNewQuery").click(function(){
					var ddl = document.getElementById("workspaceId");
					var wsid = ddl.value;
					var wsdesc = ddl.options[ddl.selectedIndex].text;
					 $.ajax({			
						url: 'PostSubQueryMgmt_b.do?workSpaceId='+wsid+"&workspaceDesc="+wsdesc+"&mode=Add"+"&mi=<s:property value='mi'/>",
						success: function(data) 
				  		{
							//$('#addQueryDtl').html(data);
							$('#popupContact').html(data);
				  			
						}							 
					});
				    //centering with css
				   centerPopup();
					//load popup
					loadPopup();
				 });

				/* $("#workspaceId").change(function(){
					if(this.value != '-1')
					{
						$('#addNewQuery').css('display','block');
						$.ajax({			
							url: 'ShowQueryDetails_ex.do?workSpaceId='+this.value+'&mi=<s:property value="mi"/>',
							success: function(data) 
					  		{
						  		//alert(data);
									$('#workspaceDetail').html(data);
							}							 
						});
					}
					else
						$('#addNewQuery').css("display",'none');
				});*/
			});
			 function showBranch(branch,nodeId)
			{
				if(document.getElementById(branch))
				{
					var objBranch = document.getElementById(branch).style;
				
					if(objBranch.display=="block")
						objBranch.display="none";
					else
						objBranch.display="block";
				}
			}
	
			function swapFolder(img)
			{
			  if(document.getElementById(img))
			  {	
				  var objImg = document.getElementById(img);
				  if(objImg)
				  {
					if(objImg.src.indexOf('closed.gif')>-1)
						objImg.src = openImg.src;
					else
						objImg.src = closedImg.src;
				  }
			  }
			}
			function openFileWithPath(filewithpath)
			{
				var fileWindow = "openfile.do?fileWithPath="+filewithpath;
		    	win3=window.open(fileWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height="+ screen.availHeight + ",width=" + (screen.availWidth/2) + ",resizable=yes,titlebar=no")	
		    	win3.moveTo(0,0);
		    	return true;
			}
			function  openInEditMode(queryId)
			{
				//alert(queryId);
				var ddl = document.getElementById("workspaceId");
				var wsid = ddl.value;
				var wsdesc = ddl.options[ddl.selectedIndex].text;
				$.ajax({			
					url: 'PostSubQueryMgmt_b.do?workSpaceId='+wsid+"&workspaceDesc="+wsdesc+"&mode=edit&queryId="+queryId+"&mi=<s:property value='mi'/>",
					success: function(data) 
			  		{
						//$('#addQueryDtl').html(data);
						$('#popupContact').html(data);
			  			
					}		
	 			});					 
				//centering with css
				centerPopup();
				//load popup
				loadPopup();
			}
			function openInChangeMode(queryId)
			{
				
				var ddl = document.getElementById("workspaceId");
				var wsid = ddl.value;
				var wsdesc = ddl.options[ddl.selectedIndex].text;
				$.ajax({			
					url: 'PostSubQueryMgmt_b.do?workSpaceId='+wsid+"&workspaceDesc="+wsdesc+"&mode=ChangeStatus&queryId="+queryId+"&mi=<s:property value='mi'/>",
					success: function(data) 
			  		{
						//$('#addQueryDtl').html(data);
						$('#popupContact').html(data);
			  			
					}		
	 			});					 
				//centering with css
				centerPopup();
				//load popup
				loadPopup();
				
			}


			function jQueryOnchangeAutocompleter()
			{
				subinfo();
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
				$( "#workspaceId" ).combobox();
				$( "#workspaceId" ).click(function() {
						$( "#workspaceDetail" ).toggle();
				
					});
			});

			function subinfo()
			{
				var workSpaceId=$('#workspaceId').val();
				if(workSpaceId != '-1')
				{
					$('#addNewQuery').css('display','block');
					$.ajax({			
						url: 'ShowQueryDetails_ex.do?workSpaceId=' + workSpaceId+'&mi=<s:property value="mi"/>',
						beforeSend: function()
						{
								$('#workspaceDetail').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
						},
						success: function(data) 
				  		{
					  			//alert(data);
								$('#workspaceDetail').html(data);
						}							 
					});
				}
				else
					$('#addNewQuery').css("display",'none');
			}

		
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<br>
<div align="center"><img
	src="images/Header_Images/Submission/post_submission_query_management.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="ShowPostSubQueryMgmt"
	method="post" name="ShowSubQueryMgmt" onsubmit="return false;">
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project&nbsp;</td>
			<td align="left"><s:select list="workspaces" name="workSpaceId"
				id="workspaceId" headerKey="-1" headerValue="" listKey="workSpaceId"
				listValue="workSpaceDesc">
			</s:select></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><s:hidden name="mi" /> <s:if
				test="mi.trim().equalsIgnoreCase('a')">
				<input type="button" style="display: none;" title="Add New Query"
					value="Add New Query" id="addNewQuery" class="button" />
			</s:if></td>
		</tr>

	</table>
</s:form> <!-- Add New Query Div -->
<div id="backgroundPopup"></div>
<div id="popupContact" style="width: 700px; height: 450px;"></div>

<!-- Show Query Detail Div -->
<div id='workspaceDetail' style="padding-top: 16px; width: 100%;"
	align="left"></div>
</div>
</div>
</div>

</body>
</html>