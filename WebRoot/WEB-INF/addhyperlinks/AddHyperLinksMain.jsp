<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>
<script src="<%=request.getContextPath()%>/js/newtree/jquery.cookie.js"
	type="text/javascript"></script>
<link
	href="<%=request.getContextPath()%>/js/newtree/skin/ui.dynatree.css"
	rel="stylesheet" type="text/css">

<script
	src="<%=request.getContextPath()%>/js/newtree/jquery.dynatree.js"
	type="text/javascript"></script>

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
</style>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var popUpEnableOnStartup='No';
</script>
<SCRIPT type="text/javascript">

		function subinfo()
		{
		
			var workSpaceId=$('#workSpaceId').val();

			//callForProjDocStatus(workSpaceId); // if want to show dossier status just uncommen 
			//alert(workSpaceId);
			$.ajax
			({			
					url: "ShowProjectDetails_ex.do?workSpaceId="+workSpaceId,
					beforeSend: function()
					{
						
						$('#ShowSubInfoDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
						
														
					},
					success: function(data) 
			  		{
				  		
				  	
						$('#ShowSubInfoDiv').html(data);
					}	  		
			});
		}

		
		
		var currentRegionId = '';
		
		function recompileSubmission(submissionPath,workSpaceId,submissionInfo__DtlId)
  		{
  			str="RecompileSubmission.do?submissionPath="+submissionPath+"&workSpaceId="+workSpaceId+"&submissionInfo__DtlId="+submissionInfo__DtlId;
			win3=window.open(str,'ThisWindow','height=300,width=450,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
			win3.moveTo(screen.availWidth/2-(450/2),screen.availHeight/2-(300/2));
  		}
  		
		var workspaceId,submissionPath,currentSeqNumber,subId;
	

		
		function viewDetails(workspaceId,subId,clickedid)
		{
			
			var queryString = "?workSpaceId="+workspaceId+"&submissionInfo__DtlId="+subId;
			$.ajax
			({			
					url: "ViewDetailsOfSubmission.do"+queryString,
					beforeSend: function()
					{
						$('#viewSubDtl').html("");
						showSubDtl('viewSubDtl',clickedid);
						$('#viewSubDtl').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
					},
					success: function(data) 
			  		{
						$('#viewSubDtl').html(data);
					}	  		
			});
		}
		
		function checkBrokenLinks(workspaceId,submissionPath,currentSeqNumber,subId)
		{
			var queryString = "?workSpaceId="+workspaceId+"&submissionPath="+submissionPath+"&currentSeqNumber="+currentSeqNumber+"&submissionInfo__DtlId="+subId;
			$.ajax
			({			
					url: "CheckLinksInSubmission.do"+queryString,
					beforeSend: function()
					{
						showMsg();
						$('#msgDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
					},
					success: function(data) 
			  		{
				  	
						$('#msgDiv').html(data);
					}	  		
			});
		}

		function validate(submissionPath,currentSeqNumber,region)
		{
			
			var queryString = "?submissionPath="+submissionPath+"&currentSeqNumber="+currentSeqNumber+"&countryRegion="+region;
			$.ajax
			({			
					url: "ValidateEctdSubSeq_b.do"+queryString,
					beforeSend: function()
					{
						showMsg();
						$('#msgDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
					},
					success: function(data) 
			  		{
						$('#msgDiv').html(data);
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
	$( "#workSpaceId" ).combobox();
	$( "#workSpaceId" ).click(function() {
			$( "#ShowSubInfoDiv" ).toggle();
	
		});
});


</script>

</head>

<body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/wz_tooltip.js"> </script>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->

<br />
<div align="center"><img
	src="images/Header_Images/Project/Add_Links_Files.png"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px;width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><br>
<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Project Name</td>
		<td align="left"><s:select list="workspacesWithSubmissionInfo"
			id="workSpaceId" name="workSpaceId" listKey="workspaceId"
			listValue="workspaceDesc" headerKey="-1" headerValue=""
			theme="simple" onchange="subinfo();" ondblclick="subinfo();">
		</s:select></td>
	</tr>

</table>



<div id="blanket" class="blanket" style="display: none;"></div>
<div id="popUpDiv" class="popUpDiv" style="display: none;">
<div style="text-align: right;"><a href="javascript:void(0);"
	onclick="popupdiv('popUpDiv')">Close</a>&nbsp;</div>
<div id="subdtl" style="overflow-y: auto; height: 280px;"></div>
</div>
<div id="viewSubDtl"
	style="display: none; background-color: #eeeeee; border: 1 solid; z-index: 2000;">

</div>
<div id="closeLinkDiv" style="z-index: 2100; display: none;"><a
	id="closeLink" href="javascript:void(0);" onclick="closePopupDiv();">Close</a>
</div>


<table align="center" width="900px" style=" margin-left: 20px; margin-right: 20px; margin-bottom: -20px">
	<tr>
		<td align="center"><s:set name="callDefaultWorkspace"
			value="false"></s:set> <s:iterator
			value="workspacesWithSubmissionInfo">
			<s:if test="workspaceId == workSpaceId">
				<s:set name="callDefaultWorkspace" value="true"></s:set>
			</s:if>
		</s:iterator> <s:if
			test="workSpaceId != NULL && workSpaceId != '' && #callDefaultWorkspace == true">
			<script>
  					subinfo();
  				</script>
			<s:div id="ShowSubInfoDiv" cssStyle="align:center">

			</s:div>
			<s:if test="workspacesWithSubmissionInfo.size==0">
				<div>No Projects Found With Submission Details</div>
			</s:if>

		</s:if> <s:else>

			<s:div id="ShowSubInfoDiv" cssStyle="align:center">
			</s:div>
		</s:else></td>
	</tr>
</table>

</div>

</div>
</div>
</body>
</html>

