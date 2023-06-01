<%@ taglib uri="/struts-tags" prefix="s"%>

<link href="<%=request.getContextPath()%>/js/jquery/jquery.alerts.css"
	rel="stylesheet" type="text/css" media="screen" />
<script src="<%=request.getContextPath()%>/js/jquery/jquery.alerts.js"
	type="text/javascript"></script>

<script src="js/jquery/autocompleter/js/jquery.ui.core.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.widget.js"></script>
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
    //if($.browser.mozilla && parseFloat($.browser.version) >= 1.9)
      //      $("body").addClass("firefox3");
            		$("#submitBtn").click(function(){
            					var test = document.forms["defaultWorkSpace"].DefaultworkSpace;
						for (i=0;i<test.length;i++)
						{
							if(test[i].checked == true && document.forms["defaultWorkSpace"].workSpaceId.value != "-1")
							{
								var wsId = document.getElementById("comboID");
								var wsdesc = wsId.options[wsId.selectedIndex].text;
								jAlert('Default Project has been set As '+wsdesc, 'Set Project');
								
								var wsidval = document.forms["defaultWorkSpace"].workSpaceId.value;
								if(document.forms["defaultWorkSpace"].workSpaceId.value != -1)
								{
									var aElement = 	document.getElementById('openLink');
									aElement.setAttribute('href','WorkspaceOpen.do?ws_id='+wsidval);
									aElement.style.color = "blue";
									aElement.style.display = '';
									
								}
								subinfo();
								return true;
							}
							else if(test[i].checked == false && document.forms["defaultWorkSpace"].workSpaceId.value != "-1")
							{
								jAlert('Default Project has been removed.', 'Set Project');
								var aElement = 	document.getElementById('openLink');
								aElement.setAttribute('href','#');
								aElement.style.display = 'none';
								document.forms["defaultWorkSpace"].workSpaceId.value = -1;
								subinfo();
								return true;
							}
							else if(document.forms["defaultWorkSpace"].workSpaceId.value == "-1" &&  test[1].checked == false)
							{
								jAlert('Please select Project Name..','Set Project');
							}
						}
        });
//Below Code is used to Show Hide Table row which has ID "showhideTR" 
//        $("#radio2").click(function(){
//        			 $("#showhideTR").hide("fast");
//					return true; 
//       });
//        $("#radio1").click(function(){
//        			 $("#showhideTR").show("slow");
//					return true; 
//       });

(function( $ ) {
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
		$( "#comboID" ).combobox();
	});
});

function subinfo()
{	
	
	var workSpaceId=$('#comboID').val();
	var tempWorkSpaceId;
	if(workSpaceId==-1)
		tempWorkSpaceId="No";
	$.ajax(
	{			
		url: 'SaveDefaultWorkSpace.do?workSpaceId='+workSpaceId+'&DefaultworkSpace='+tempWorkSpaceId,
		beforeSend: function()
		{
		},
		success: function(data) 
  		{
			$( "#comboID" ).combobox();
		}	  
	});		
}		
</script>

<div style="color: green;"><s:actionmessage /></div>
<s:form action="SaveDefaultWorkSpace" method="post"
	name="defaultWorkSpace" onsubmit="return false;">
	<table align="center" width="100%">
		<tr align="left">
			<td colspan="2"><s:if
				test="workSpaceId != null && workSpaceId.trim() != '' ">
				<span><a id="openLink"
					href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>"
					style="text-decoration: none; color: blue;">Open Default
				Project</a></span>
			</s:if> <s:else>
				<a id="openLink"
					href="WorkspaceOpen.do?ws_id=<s:property value="workSpaceId"/>"
					style="text-decoration: none; display: none;">Open Set Project
				</a>
			</s:else></td>
		</tr>
		<tr align="left">
			<td width="20%"><input type="radio" name="DefaultworkSpace"
				checked="checked" id="radio1" value="Y" /><label for="radio1">&nbsp;Set
			Project</label></td>
			<td><s:select list="getWorkSpaceDetail" id="comboID"
				name="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="-1" headerValue="Select Project" theme="simple"
				cssClass="target">
			</s:select></td>
		</tr>

		<tr id="showhideTR">
			<td align="left" colspan="2"><input type="radio"
				name="DefaultworkSpace" id="radio2" value="N" /><label for="radio2">&nbsp;Remove
			Project</label></td>
			<td align="right" colspan="2"><s:submit name="submitBtn"
				id="submitBtn" value="  Set  " cssClass="button" theme="ajax"
				align="center" /></td>

		</tr>
	</table>


</s:form>
