<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/tabs/js/ajax.js"></script>
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
		var openImg = new Image();
		openImg.src = "images/open.gif";
		var closedImg = new Image();
		closedImg.src = "images/closed.gif";
		
		function selUsrColumn(str,colNo,noOfCols)
		{
			
			var cnt=document.getElementById(str).value;
			var sel=document.getElementById(str + "-" + colNo).checked;
			for (var i=0;i<cnt;i++)
			{		
				if (i%noOfCols==colNo)
				{
					document.getElementById(str + '_' + (i+1)).checked=sel;
				}
			}
		}
		function selAlUsr(str)
		{
			var cnt=document.getElementById(str).value;
			var sel=document.getElementById(str).checked;
			for (var i=1;i<=cnt;i++)
			{
				document.getElementById(str + '_' + i).checked=sel;
			}
		}
		function getUsrs()
		{	
			var wsId = '';
			var args = '';
			var usrType = '';
			var nodeId = '';
			
			wsId = document.getElementById('workSpaceId').options[document.getElementById('workSpaceId').selectedIndex].value;
			usrType = document.getElementById('usrTyp').value;
			nodeId = document.getElementById("nodeId").value;
			args = "usrTyp="+usrType+"&workSpaceId="+wsId+"&nodeId="+nodeId;

			$.ajax(
			{	
				url: 'GetUsers_ex.do?' + args,
				beforeSend: function()
				{ 
					
					if (document.getElementById('usrTyp').value=='none')
					{
						$('#usrGrpList').html("<b>Please select User Type</b>");
						return false;
					}
					$('#usrGrpList').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
						
				},
		  		success: function(data) 
		  		{
		    		$('#usrGrpList').html(data);
		    		     		
				}	  		
			});
		}
		
		function chkAll()
		{

			var selectAll = document.getElementsByName('selectAll');
			var chkBox = document.getElementsByName('selectedAttributes');
			var i;
			if(selectAll[0].checked)
			{
				for(i=0;i<chkBox.length;i++)
					chkBox[i].checked = true;
			}
			else
			{
				for(i=0;i<chkBox.length;i++)
					chkBox[i].checked = false;
			}
		}

		function submitForm()
		{

			$.ajax(
					{
						url: 'ShowCategorySummary_ex.do?workSpaceId=' + document.getElementById('workSpaceId').value,
						
						beforeSend: function()
						{ 
							$('#loadImgDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
							$('#categorySummaryDiv').html('');							
							if (document.getElementById('workSpaceId').value=='-1')
							{
								$('#categorySummaryDiv').html("");
								$('#loadImgDiv').html("");
								return false;
							}
								
						},
				  		success: function(data) 
				  		{	
							$('#loadImgDiv').html("");	  							
				    		$('#categorySummaryDiv').html(data);				    		
				    		    		     		
						}	  		
					});
			
		}
		function validate()
		{
			var str='';
			var attrCheck = '';
			var wsId = '';
			var args = '';
			var nodeNm = '';
			var folderNm = '';
			var nodeVal = document.forms['saveCategoryForm'].nodeName.value.length;
			var nodeValStr = document.forms['saveCategoryForm'].nodeName.value;
			
				if(nodeVal < 5)
				{
					alert("Category Name must be greater then 5 characters..");
					document.forms['saveCategoryForm'].nodeName.style.backgroundColor="#FFE6F7"; 
					document.forms['saveCategoryForm'].nodeName.focus();
					return false;
				}	
				else if(nodeVal > 60)
				{
					alert("Category Name must be less then 60 characters..");
					document.forms['saveCategoryForm'].nodeName.style.backgroundColor="#FFE6F7"; 
					document.forms['saveCategoryForm'].nodeName.focus();
					return false;
				}
			 var strInvalidChars = "/\^$#:~%@&*`!;'\"\+\=({}<>?)[]-";
		     var strChar;
		   	 var blnResult = true;

		   	 for ( var k = 0; k < nodeValStr.length && blnResult == true; k++)
	     	 {
	        	strChar = nodeValStr.charAt(k);
				 if (strInvalidChars.indexOf(strChar)!= -1)
				 {
	      			blnResult = false;
	       			if(strChar == ' ')
      				{
      					alert("Invalid Category Name," + " 'Space' is not allowed.." );
      					return false;
      				}
      				else
      				{
       					alert("Invalid Category Name," + " 'special characters' are not allowed.." );
       					return false;
       				}
       				break;
	 			}
	      	 }

			
			for (var j=0;document.forms['saveCategoryForm'].selectedAttributes.length!=null && j<document.forms['saveCategoryForm'].selectedAttributes.length;j++)
			{
				
				if (document.forms['saveCategoryForm'].selectedAttributes[j].checked==true)
				{
					
					if (attrCheck=='')
						attrCheck=document.forms['saveCategoryForm'].selectedAttributes[j].value;
					else
						attrCheck+=","+document.forms['saveCategoryForm'].selectedAttributes[j].value;
					
				}
				
			}
	
			if (document.forms['saveCategoryForm'].selectedAttributes.length==null)
			{
				if (document.forms['saveCategoryForm'].selectedAttributes.checked==true)
				{
					attrCheck=document.forms['saveCategoryForm'].selectedAttributes.value;			
				}
			}
			var usrSelect = document.getElementsByName("usrSelect");
			for (var i=0;usrSelect.length!=null && i<usrSelect.length;i++)
			{
				
				if (usrSelect[i].checked==true)
				{
					if (str=='')
						str=usrSelect[i].value;
					else
						str+=","+usrSelect[i].value;
					
				}
			}
			
			if (usrSelect.length==null)
			{
				if (usrSelect.checked==true)
				{
					str=usrSelect.value;			
				}
			}
			document.getElementById("userDetails").value=str;
			document.getElementById("selAttributes").value=attrCheck;
			if(!$("#userCodeListDtl").length)
			{
				alert("Please select user type..");
				return false;
			}
			document.forms['saveCategoryForm'].userCodeList.value=document.getElementById("userCodeListDtl").value;
			return true;
		} 
		function showData()
		{
			$.ajax (
					{							
					url: 'ShowCategorySummary_ex.do?workSpaceId=' + document.getElementById('workSpaceId').value,
					beforeSend: function()
					{ 
						$('#categorySummaryDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
					},		
			  		success: function(data)
			  		{
						
			  			$('#categorySummaryDiv').html(data);	  		
			  		}
			  			  		
				});
			
		}	
  			
		
		function  openInEditMode(workSpaceId,nodeId,mode)
		{
			var args = '';
			args = "workSpaceId="+workSpaceId+"&nodeId="+nodeId+"&mode="+mode;
			
			$.ajax({			
				url: 'AddEditCategory_ex.do?' + args,
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

		function  showHistory(workSpaceId,nodeId)
		{
			var args = '';
			args = "workSpaceId="+workSpaceId+"&nodeId="+nodeId;
			
			$.ajax({			
				url: 'ShowCatDocHistory_ex.do?' + args,
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

		function jQueryOnchangeAutocompleter()
		{
			submitForm();
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
			$( "#workSpaceId" ).combobox();
			$( "#workSpaceId" ).click(function() {
					$( "#categorySummaryDiv" ).toggle();
			
				});
		});

		</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center"><img
	src="images/Header_Images/DMSUserRepository/Manage_Categories.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="CategoryForm">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Select Project&nbsp;</td>
			<td><s:select list="workspaceMstList" name="workSpaceId"
				id="workSpaceId" listKey="workSpaceId"
				listValue="rootNodeDtl.nodeName" headerKey="-1" headerValue=""
				theme="simple">
			</s:select></td>
		</tr>
	</table>
</s:form>

<div id="addEditCategoryDiv"></div>


<div id="backgroundPopup"></div>
<div id="popupContact" style="width: 700px; height: 450px;"></div>
</div>
<div id="categorySummaryDiv" align="center"></div>
<div id="loadImgDiv" align="center"></div>

</div>
</div>
</body>
</html>