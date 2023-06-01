<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>

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
<script src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"
	type="text/javascript"></script>


<STYLE>
BODY {
	FONT: 10pt Verdana, sans-serif;
	COLOR: olive;
}

.trigger {
	CURSOR: hand
}

.branch {
	DISPLAY: none;
	MARGIN-LEFT: 16px
}
</STYLE>

<SCRIPT type="text/javascript">

		$(document).ready(function() { 
			var options = 
			{
				target: '#showFilesDiv',
				url: 'ListFiles.do'
			};
			$('#EditLinksForm').submit(function()
			{
				$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');	
				$(this).ajaxSubmit(options);
				return false;									
			});
		});
		
		


	function openFileWithPath(filewithpath)
	{
		var fileWindow = "openfile.do?fileWithPath="+filewithpath;
    	win3=window.open(fileWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height="+ screen.availHeight + ",width=" + (screen.availWidth/2) + ",resizable=yes,titlebar=no")	
    	win3.moveTo(0,0);
    	
    	return true;
	}
	
	var openImg = new Image();
	openImg.src = "images/open.gif";
	var closedImg = new Image();
	closedImg.src = "images/closed.gif";
	var SourceNodeId;
	var DestinationNodeId;
	var srclastcheckedbox=1;
	var destlastcheckedbox=1;
	
	function showBranch(branch,nodeId)
	{
		var objBranch = document.getElementById(branch).style;
		if(objBranch.display=="block")
			objBranch.display="none";
		else
			objBranch.display="block";
	}

	function swapFolder(img)
	{
		objImg = document.getElementById(img);
		if(objImg.src.indexOf('closed.gif')>-1)
			objImg.src = openImg.src;
		else
			objImg.src = closedImg.src;
	}



	function filledHiddenValue(srcnodeid)
	{
		boxx = eval("document.SetLinkForm.CHK_" + srcnodeid);
		box = eval("document.SetLinkForm.CHK_" + srclastcheckedbox); 
	
		if(srclastcheckedbox != 1)
		box.checked = false;
		if(boxx.checked ==true){		
			document.SetLinkForm.sourceNodeId.value = "" + srcnodeid;
		}
		else
			document.SetLinkForm.sourceNodeId.value = "";
		srclastcheckedbox=srcnodeid;
	}



	function setSelectedLink(pageno,linkOriginalSeqno,linkDisplayNo)
	{
		document.SetLinkForm.selectedLink.value = pageno +'&&'+ linkOriginalSeqno;
		document.SetLinkForm.selectedlinkno.value = 'Page' + pageno + ' : ' + linkDisplayNo;
	}

	function setSelectedFile(filename,filepath,depth,imgpathObj)
	{
		var imgId = $(imgpathObj).attr("id");
		document.SetLinkForm.selectedfile.value = ' ' + filename;
		document.SetLinkForm.selectedfilepath.value = filepath;
		document.SetLinkForm.selectedFileDepth.value = depth;
		document.SetLinkForm.selectedImagepathId.value = imgId;
		document.SetLinkForm.workspaceid.value = $('#workspaceid').selected().val();
		$.ajax
		({			
				url: 'ShowLinks.do?fileWithPath='+filepath,
				success: function(data) 
		  		{
					$("#LinksDiv").html(data);
				}	  		
		});
	}
	
	function setLinks()
	{
		if(document.SetLinkForm.selectedfilepath.value == '')
		{
			alert('Please Select File To Edit Links...');
			return false;
		}
		if(document.SetLinkForm.selectedLink.value == '')
		{
			alert('Please Select Link To Edit...');
			return false;
		}
		if(document.SetLinkForm.sourceNodeId.value == '')
		{
			alert('Please Select Reference File Name...');
			return false;
		}
		
		document.getElementById('showFilesDiv').style.display = 'none';
		document.getElementById('messageDiv').style.display = '';
		
		return true;
		/*
		document.EditLinksForm.action = "SetLink.do";
		document.EditLinksForm.target = "messageDiv";
		document.EditLinksForm.submit();
		*/

	}
	
	function showFiles()
	{
		var imgid=document.SetLinkForm.selectedImagepathId.value;
		document.getElementById(imgid).onclick(); 
		document.getElementById('showFilesDiv').style.display = '';
		document.getElementById('messageDiv').style.display = 'none';
	}
	
	/*function stopRKey(evt) 
	{ 
 		var evt = (evt) ? evt : ((event) ? event : null); 
 		var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  		if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
	} 

	document.onkeypress = stopRKey;*/


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
		$( "#workspaceid" ).combobox();
		$( "#workspaceid" ).click(function() {
				$( "#toggle" ).toggle();
		
			});
	});

</SCRIPT>

</head>
<body class="yui-skin-sam">

<div class="errordiv" align="center" style="color: red"><!-- <s:fielderror ></s:fielderror>
		<s:actionerror/> --></div>
<br />
<div align="center"><img
	src="images/Header_Images/Project/Editing_Links_Files.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left">
<form id="EditLinksForm" name="EditLinksForm">

<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Project Name</td>
		<td align="left"><s:select list="getAllWorkSpace"
			name="workspaceid" id="workspaceid" headerKey="-1" headerValue=""
			listKey="workSpaceId" listValue="workSpaceDesc" theme="simple">
		</s:select></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" value="Search Files" class="button">
		</td>
	</tr>
</table>
<br />
</form>
</div>
<div align="center" id="showFilesDiv" style="width: 95%"></div>
<br>
<br>
<br>
<center>
<div id="messageDiv" style="display: none;"></div>
</center>
</div>
</div>
</body>
</html>


