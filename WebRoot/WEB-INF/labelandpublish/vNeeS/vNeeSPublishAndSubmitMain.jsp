<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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


	
		function hide(str)
		{
			$('#'+str).slideToggle("slow");
		}	
		function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}

		function subinfo()
		{
			$('#statusDetails').html('');
			var workSpaceId=$('#workSpaceId').val();

			//callForProjDocStatus(workSpaceId); // if want to show dossier status just uncommen 
			//alert(workSpaceId);
			$.ajax
			({			
					url: "ShowvNeeSPublishAndSubmitForm.do?workSpaceId="+workSpaceId,
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

		var tarDivId ='default';
		function showMsg()
		{
			document.getElementById("PublishAndSubmitFormDiv").style.display = 'none';
			if(tarDivId != 'default'){
				document.getElementById(tarDivId).style.display = '';
			}else{
				document.getElementById("msgDiv").style.display = '';
			}
			return true;
		}

		function publishSequence()
		{
//validation
			return true;
			var publishType = document.forms["PublishAndSubmitForm"].projectPublishType;
			var stype = document.forms["PublishAndSubmitForm"].subType.value;
			var region = document.forms["PublishAndSubmitForm"].countryRegion.value;
			
			if(stype =="-1")
			{
				alert('Please Select Submission Type..');
				document.forms["PublishAndSubmitForm"].subType.style.backgroundColor="#FFE6F7";
				return false;
			}
			else if(!document.forms["PublishAndSubmitForm"].currentSeqNumber.value.match(/^\d{4}$/))
			{
				alert('Please enter 4 digit sequence number..');
				document.forms["PublishAndSubmitForm"].currentSeqNumber.style.backgroundColor="#FFE6F7";
				document.forms["PublishAndSubmitForm"].currentSeqNumber.focus();
				return false;
			}
			else if(publishType != null && publishType.value== 'M')
			{
				if(!document.forms["PublishAndSubmitForm"].relatedSeqNo.value.match(/^(\d{4}(,\d{4})*)?$/))
				{
					alert('Please enter 4 digit related sequence numbers separated by comma..');
					document.forms["PublishAndSubmitForm"].relatedSeqNo.style.backgroundColor="#FFE6F7";
					document.forms["PublishAndSubmitForm"].relatedSeqNo.focus();
					return false;
				}
			}
			else if(document.forms['PublishAndSubmitForm'].subDesc != null)
			{
				if(document.forms['PublishAndSubmitForm'].subDesc.value == "")
				{
					alert('Please enter Submission Description..');
					document.forms["PublishAndSubmitForm"].subDesc.style.backgroundColor="#FFE6F7";
					document.forms["PublishAndSubmitForm"].subDesc.focus();
					return false;
				}
			}

			
			
			if(region =='eu')
	    	{
				var cmsCnt = $('#cmsSize').val();
				if(cmsCnt > 0)
				{
					var selectBox = document.forms['PublishAndSubmitForm'].selectedCMS;
					if(cmsCnt == 1)
						selectBox = new Array(document.forms['PublishAndSubmitForm'].selectedCMS);
					var selCMScnt = 0;
					//validation for check the cms tracking number. cms tracking number can not be blanck.
					for(var i=0;i<selectBox.length;i++)
					{
						var strId = selectBox[i].id;
						var workspaceCMSId = strId.substring('selectedCMS'.length,strId.length);
						if($('#selectedCMS'+workspaceCMSId).attr('checked'))
						{
							selCMScnt++;
							var txtValue = 	trim($('#trackCMS'+workspaceCMSId).val());
							$('#trackCMS'+workspaceCMSId).val(txtValue); 
							if(txtValue.length == 0)
							{
								alert("CMS Tracking Number can not be blank...");		
								$('#trackCMS'+workspaceCMSId).focus();
								$('#trackCMS'+workspaceCMSId).css('background-color', '#FFE6F7');
								return false;
							}
							else if(!txtValue.match(/^([a-zA-Z0-9\/\u002D\u002C])*$/))
							{
								alert("Only digits, Alphabets, '-' , '/' and ',' are allowed.");
								$('#trackCMS'+workspaceCMSId).focus();
								$('#trackCMS'+workspaceCMSId).css('background-color', '#FFE6F7');
								return false;
							}
						}
					}
					// Validation for tracking table... atlease one cms or rms country seleted then 
					// tracking table added.
					var isRMS = trim($('.RMSSel:checked').val());
					var addTT = $('#addTT').attr('checked');
					if(isRMS == 'N' && selCMScnt == 0 && addTT == true  && cmsCnt == 0)
					{
						alert("Please select RMS or atleast one CMS country..");
						return false;
						
					}
				}
	    	}
			
			return true;
		}
							
		function goBack()
		{
			document.getElementById("PublishAndSubmitFormDiv").style.display = '';
			document.getElementById("msgDiv").style.display = 'none';
		}
		
		var currentRegionId = '';
		function showRelatedSequenceNoTr()
		{
			var trElement = document.getElementById('relatedSequenceNoTr');
			var subTypeIndi = document.forms['PublishAndSubmitForm'].subType.value.split(',');
			if(subTypeIndi[1]=='R')
				trElement.style.display='';
			else
				trElement.style.display='none';			
		}
		
		function recompileSubmission(submissionPath,workSpaceId,submissionInfo__DtlId)
  		{
  			str="RecompileSubmission.do?submissionPath="+submissionPath+"&workSpaceId="+workSpaceId+"&submissionInfo__DtlId="+submissionInfo__DtlId;
			win3=window.open(str,'ThisWindow','height=300,width=450,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,titlebar=no');	
			win3.moveTo(screen.availWidth/2-(450/2),screen.availHeight/2-(300/2));
  		}
  		
  		function openFileWithPath(filewithpath)
		{
			var fileWindow = "openfile.do?fileWithPath="+filewithpath;
    		win3=window.open(fileWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height="+ screen.availHeight + ",width=" + (screen.availWidth/2) + ",resizable=yes,titlebar=no");	
    		win3.moveTo(0,0);
    		return true;
		}
		
		function popupdiv(popUpDiv)
		{
			if(isPopUpOn)
			{
				isPopUpOn = false;
				window.document.body.scroll = "yes";
			}
			else
			{
				isPopUpOn = true;
				window.document.body.scroll = "no";
			}
			popup(popUpDiv);
			document.getElementById('blanket').style.width = window.screen.width+'px';
			document.getElementById('blanket').style.height = document.body.scrollHeight+'px';
			document.getElementById('popUpDiv').style.top = ((window.screen.height/2+document.body.scrollTop)-300);
		}
		
		var isPopUpOn = false;/*used to popup sub details div*/
		
		function showSubDtl(targetDivId,clickedid)
		{
			var srclinkEle = document.activeElement;
			var targetDiv = document.getElementById(targetDivId);
			var popupleft = popuptop = 0;

			
			
			var bottomspace=$(document).height() - $("#"+clickedid).position().top;

			
			if(bottomspace>=400)
			{
				popuptop =$("#"+clickedid).position().top;
			}
			else
			{
				popuptop =$("#"+clickedid).position().top-400;
			}
			
			
			
			popupleft = $("#"+clickedid).position().left;
			targetDiv.style.display = 'block';
			targetDiv.style.position = 'absolute';
			targetDiv.style.width = 350+'px';
			targetDiv.style.height = 400+'px';
			targetDiv.style.top = popuptop+'px';
			targetDiv.style.left = popupleft+'px';
			targetDiv.style.paddingTop = '10px';

/*
			 offs = $("#"+targetDivId).offset(); // <-- caching for better performance.
			    var highlight_top = offs.top;
			    var highlight_left = offs.left;
			    alert(highlight_left);
			
	*/		
			document.getElementById('closeLinkDiv').style.display = 'block';
			document.getElementById('closeLinkDiv').style.position = 'absolute';
			document.getElementById('closeLinkDiv').style.top = popuptop+'px';
			document.getElementById('closeLinkDiv').style.left = (popupleft + 350-38)+'px';
			popupDivEle = document.getElementById(targetDivId);
			return true;
			/*if (srclinkEle.offsetParent) {
				do {
					curleft += srclinkEle.offsetLeft;
					curtop += srclinkEle.offsetTop;
				} while (srclinkEle = srclinkEle.offsetParent);
				
				alert(srclinkEle.style.width+','+srclinkEle.style.left);
				alert(curleft+','+ curtop );
			}*/
		}
		
		function getY( oElement )
		{
			var iReturnValue = 0;
			while( oElement != null ) {
				iReturnValue += oElement.offsetTop;
				oElement = oElement.offsetParent;
			}
			return iReturnValue;
		}
		function getX( oElement )
		{
			var iReturnValue = 0;
			while( oElement != null ) {
				iReturnValue += oElement.offsetLeft;
				oElement = oElement.offsetParent;
			}
			return iReturnValue;
		}
		
		function closePopupDiv()
		{
			popupDivEle.style.display = 'none';
			document.getElementById('closeLinkDiv').style.display = 'none';
		}
		
		function selectAllCMS()
		{
			var selectBox = document.forms['PublishAndSubmitForm'].selectedCMS;
			//alert($('.selCMSCls').attr('checked'));
			for(var i=0;i<selectBox.length;i++)
			{
				if($('#chkAllCMS').attr('checked'))
				{
					selectBox[i].checked = 'checked';
				}
				else
				{
					selectBox[i].checked = '';
				}
				var strId = selectBox[i].id;
				toggleTxtTrackNo(strId.substring('selectedCMS'.length,strId.length));
				
			}
		}
		function selectAllDataType()
		{
			var selectBox = document.forms['PublishAndSubmitForm'].datatype;
			//alert($('.selCMSCls').attr('checked'));
			for(var i=0;i<selectBox.length;i++)
			{
			
				if($('#chkAllEfficacy').attr('checked'))
				{
					selectBox[i].checked = 'checked';
					
					$("#efficacyDescription").show();
				}
				else
				{
					selectBox[i].checked = '';
					
					$("#efficacyDescription").hide();
				}
			//	var strId = selectBox[i].id;
			//	toggleTxtTrackNo(strId.substring('selectedCMS'.length,strId.length));
				
			}
		}
		
		function toggleTxtTrackNo(workspaceCMSId)
		{
			//var selectedTextBox = document.forms['PublishAndSubmitForm'].trackCMS;
			//alert($('#chkAllCMS').attr('checked'));
			if($('#selectedCMS'+workspaceCMSId).attr('checked'))
				{
					$('#trackCMS'+workspaceCMSId).attr('disabled','');
				}
			else
				{
					$('#trackCMS'+workspaceCMSId).attr('disabled','disabled');
				}
		}
		
		function showTree(divId)
		{
			tarDivId = divId;
			showMsg();
			//return publishSequence(); 
		}

		var workspaceId,submissionPath,currentSeqNumber,subId;
		function confirmSubmission(workspaceId,submissionPath,currentSeqNumber,subId,eleSign)
		{
			if(eleSign == 'Yes')
			{
				centerPopup();				
				loadPopup();
				self.scrollTo(0,0);
				document.getElementById("workspaceIdH").Value=workspaceId;
				document.getElementById("submissionPathH").Value=submissionPath;
				document.getElementById("currentSeqNumberH").Value=currentSeqNumber;
				document.getElementById("subIdH").Value=subId;
				return false;
			} 
			else
			{
				
				
				
				if(confirm('Are you sure?')) 
				{
					confirmSeq(workspaceId,submissionPath,currentSeqNumber,subId,eleSign);	
				} 
				
			}
			
		}

		function confirmSeq(workspaceId,submissionPath,currentSeqNumber,subId,eleSign)
		{
			var queryString = "?workSpaceId="+workspaceId+"&submissionPath="+submissionPath+"&currentSeqNumber="+currentSeqNumber+"&submissionInfo__DtlId="+subId;

			$.ajax
			({			
					url: "ConfirmvNeeSSubmission.do"+queryString,
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

		function extract(workspaceId,submissionPath,currentSeqNumber,tarDiv)
		{
			if(confirm('Are you sure?'))
			{
				var queryString = "?submissionPath="+submissionPath+"&currentSeqNumber="+currentSeqNumber;
				$.ajax
				({			
						url: "extractSeq.do"+queryString,
						beforeSend: function()
						{
							$('#'+tarDiv).html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
						},
						success: function(data) 
				  		{
							$('#'+tarDiv).html(data);
						}	  		
				});
			};
		}

		function checkPasswords()
		{
			var pass=document.getElementById('reConfPass').value;
			var sessPass = document.getElementById('sessPass').value;
			var sessAdUser="<s:property value='#session.adUser'/>";
			var sessAdUserPass="<s:property value='#session.adUserPass'/>";
			if (!pass || pass == '')
			{						
				alert("Please Enter Password !!!");
				return false;
			}
			if(sessAdUser=='Y'){
				if(sessAdUserPass!=pass){
					alert("You have AD User rights. Please enter correct password.");
					document.getElementById('reConfPass').value = '';
					$( '#reConfPass').focus(); 
					return false;
				}
			}
		if(sessAdUser!='Y'){
				if(sessPass!=pass){
				alert("Incorrect Password !!!");
				document.getElementById('reConfPass').value = '';
				$( '#reConfPass').focus(); 
				return false;
				}
			}
			
		if(sessPass==pass || sessAdUserPass==pass){
					var workspaceId = document.getElementById("workspaceIdH").Value;
					var submissionPath = document.getElementById("submissionPathH").Value;
					var currentSeqNumber = document.getElementById("currentSeqNumberH").Value;
					var subId = document.getElementById("subIdH").Value;
					confirmSeq(workspaceId,submissionPath,currentSeqNumber,subId,'No');
					
					cls();
					return true;
				}
				else
				{
					alert("Incorrect Password !!!");
					return false;
				}	
		}
		function cls()
		{
			document.getElementById('reConfPass').value = '';
			disablePopup();
		}
		
</SCRIPT>


<script type="text/javascript">
	function hidshowdiv(nodeId){
		var checkbox_id = document.getElementById('CHK_'+nodeId).checked;
		var div_id = document.getElementById('userDetail_'+nodeId);
		if(checkbox_id==true){
			div_id.style.display="block";
		}
		else{
			div_id.style.display="none";
		}
	}
	
	function showManualpubMsg()
	{
		document.getElementById("treeDiv").style.display = 'none';
		document.getElementById("msgDiv").style.display = '';
		tarDivId ='default';
		return true;
	}
</script>

<script type="text/javascript">
	var oWatermark = document.getElementById("divWatermark");
	var oButtonDiv = document.getElementById("divButton");
			
	var targetHeight = 0;  
            
         /* window.document.click = function () {
            	alert(mouseX(event)+','+mouseY(event));
            }*/
            
function mouseX(evt) 
{
	if (evt.pageX) return evt.pageX;
	else if (evt.clientX)
		return evt.clientX + (document.documentElement.scrollLeft ?
	document.documentElement.scrollLeft :
	   document.body.scrollLeft);
	else return null;
}
            
function mouseY(evt)
{
	if (evt.pageY) return evt.pageY;
	else if (evt.clientY)
	   return evt.clientY + (document.documentElement.scrollTop ?
	   document.documentElement.scrollTop :
	   document.body.scrollTop);
	else return null;
}

function getSize(path,divId,imgId,linkId)
{
	divId='#'+divId;
	$.ajax(
	{			
		url: 'folderSizeAction_ex.do?folderPath=' + path,
		beforeSend: function()
		{ 			
			document.getElementById(imgId).style.display='';
			document.getElementById(linkId).style.display='none';			
		},
  		success: function(data) 
  		{
			document.getElementById(imgId).style.display='none';
    		$(divId).html(data);    		
		}	  		
	});
}

function delFolder(path,divId,imgId,linkId,rowId)
{
	divId1='#'+divId;
	$.ajax(
	{			
		url: 'deleteFolderAction_ex.do?folderPath=' + path,
		beforeSend: function()
		{ 			
			document.getElementById(imgId).style.display='';
			document.getElementById(linkId).style.display='none';			
		},
  		success: function(data) 
  		{
			document.getElementById(imgId).style.display='none';
    		$(divId1).html(data);
    		var str=document.getElementById(divId).innerHTML;
    		if (str.search('error')==-1)
    		{
        		var dRow=document.getElementById(rowId);
        		if (dRow.cells)
        		{
        			for (var i=6;i<dRow.cells.length-1;i++)
        				dRow.cells[i].innerHTML='';
        		}
				dRow.className="matchFound";
    		}
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


function hideShowdatatypeDesciption()
{
	$("#efficacyDescription").toggle();

}
var totalMultipleApp=0;
function addMultipleApplication()
{
	
	
	
	if($("#propriateryName").val()=="" || $("#dos").val()=="")
	{
		return false;
	}
	
	
	totalMultipleApp++;
	var clValue="";
	if(totalMultipleApp%2==0)
	{
			clValue="odd";
	}
	else
	{
		clValue="even";
	}

	//var vName=$("#propriateryName").val();
	//var vDate$("#dos").val();
	//var temp=vName+":"+vDate;


	
	var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='MultipleApplication' value='"+$("#propriateryName").val()+":"+$("#dos").val()+"'/></td><td>"+$("#propriateryName").val()+"</td><td>"+$("#dos").val()+"</td></tr>";
	//$("#tblMultipleApplication").html($("#tblMultipleApplication").html()+"" +row);
	$("#tblMultipleApplication tbody").append(row);
	$("#propriateryName").val("");
	$("#dos").val("");
}

var totalGalenic=0;
function addGanelic()
{
	
	
	
	if($("#galenicForm").val()=="" || $("#galenicNum").val()=="" || $("#galenicName").val()=="")
	{
		return false;
	}
	
	
	totalGalenic++;
	var clValue="";
	if(totalGalenic%2==0)
	{
			clValue="odd";
	}
	else
	{
		clValue="even";
	}

	//var vName=$("#propriateryName").val();
	//var vDate$("#dos").val();
	//var temp=vName+":"+vDate;


	
	var row="<tr class="+clValue+"><td><input type='checkbox' checked='checked' name='GanelicForm' value='"+$("#galenicForm").val()+":"+$("#galenicNum").val()+":"+$("#galenicLang").val()+":"+$("#galenicName").val()+"'/></td><td>"+$("#galenicForm").val()+"</td><td>"+$("#galenicNum").val()+"</td><td>"+$("#galenicLang option:selected").text()+"</td><td>"+$("#galenicName").val()+"</td></tr>";
	//$("#tblMultipleApplication").html($("#tblMultipleApplication").html()+"" +row);
	$("#tblGalenic tbody").append(row);
	$("#galenicForm").val("");
	$("#galenicNum").val("");
	$("#galenicName").val("");
	
}


</script>

<style>
.blanket {
	background-color: #111;
	opacity: 0.65;
	filter: alpha(opacity =             65);
	position: absolute;
	z-index: 9001;
	top: 0px;
	left: 0px;
	width: 100%;
}

.popUpDiv {
	position: absolute;
	background-color: #eeeeee;
	width: 350px;
	height: 300px;
	z-index: 9002;
}
</style>
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
	src="images/Header_Images/Submission/Project_Publish_Submission.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="width: 953px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="center"><br>

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


<table align="center" width="900px">
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

