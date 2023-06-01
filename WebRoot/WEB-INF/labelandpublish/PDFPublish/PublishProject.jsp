<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>

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

<script type="text/javascript">
/* function callForProjDocStatus(wsid)
{
	//debugger;
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
							
				for(var i=0;i<d.length;i++)
				{
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
						str+=finalStr;
					}
				}
				str+="&nbsp;&nbsp;&nbsp;Total Nodes with files = "+ historyCount+"<br/>";
				str+="&nbsp;&nbsp;&nbsp;Total Nodes without files = "+ (leafCount-historyCount)+"<br/>";
				$('#statusDetails').html(str);	
					

		 }
		 });
}
 */
function callForProjDocStatus(wsid,wname)
{
	 
	 //debugger;
	
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
			 debugger;
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

	//function subinfoOfDiv()
	//{	
		//debugger;
	//	callForProjDocStatus($('#workSpaceId').val());
		
		/* var WsId=$('#WsId').val();
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
			return true; */
	//	}

   function jQueryOnchangeAutocompleter()
	{
		//debugger;
		subinfo();
		
		
	} 		
	function donotproceed()
	{
		$("#backgroundPopup").css('opacity','0');
		$("#backgroundPopup").hide(1000);	
		$("#confirmDialog").hide(1000);
		//$("#submitBtn").hide();
	}
	function proceed()
	{
		debugger;
		$("#backgroundPopup").css('opacity','0');
		$("#backgroundPopup").hide(1000);	
		$("#confirmDialog").hide(2000);
		//$("#submitBtn").show();		
	}

	function subinfo()
	{ 
		//callForProjDocStatus($('#workSpaceId').val());
		 callForProjDocStatus($('#workSpaceId').val(),$('#workSpaceId option:selected').html()); 

		//debugger;
		var wsId=document.getElementById('workSpaceId').value;
		
		$.ajax({			
			url: 'MergePDFDisplayDocs.do?workSpaceId=' + wsId,
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
}

	function validate()
	{
		var chkPDFList = document.forms['PDFDocList'].documents;
		var chkNodeList = document.forms['PDFDocList'].nodes;
		
/* 		var docCount=0;
		for(i=0;i<chkPDFList.length;i++)
		{
			if(chkPDFList[i].checked ==true)
			{
				
					docCount=docCount+1;
			}
		}
		if(docCount<1)
		{
			alert('Please select atleast one file.');
			return false;
		} */
		 if($('[name="documents"]:checked').length<1){
				alert('Please select atleast one file.');
				return false;
				} 
		var addLogo = document.forms['PDFDocList'].headerSettings;
		if(addLogo[1].checked)
		{
		//	var method=document.forms['PDFDocList'].logoSelection;
/*
			if(method.value=="Y")
			{
				var uploadLogo=document.forms['PDFDocList'].uploadLogo;
				//debugger;
				if(uploadLogo.value=="")
				{
					
					uploadLogo.style.background='rgb(255, 230, 247)';
					alert('Please browse logo file.');
					return false;
				}
				else if(!isValidImageExt(uploadLogo.value.substring(uploadLogo.value.length-4,uploadLogo.value.length)))
				{
					uploadLogo.style.background='rgb(255, 230, 247)';
					alert("Please upload valid logo file. \nOnly JPG and PNG are allowed");
					return false;
				}
			}
			else
			{
				*/
				var logoFileName=document.forms['PDFDocList'].logoFileName;
				if(logoFileName.value==-1)
				{
					logoFileName.style.background='rgb(255, 230, 247)';
					alert('Please select logo file.');
					return false;
				}
			//}		
		}	
		return true;
	}
	function isValidImageExt(ext)
	{
		if(ext!='.png' || ext!='.jpg' || ext!='.bmp' || ext!='jpeg')
			return false;
		return true;
	}
	function chkAll()
	{
		var chkall = document.forms['PDFDocList'].chk;

		var chkPDF = document.forms['PDFDocList'].documents;
		var chkNodes = document.forms['PDFDocList'].nodes;
		var chkNodeDispList = document.forms['PDFDocList'].nodeDisplayName;

		var allowHeaders = document.forms['PDFDocList'].allowHeaderPdfs;
		var allowFooters = document.forms['PDFDocList'].allowFooterPdfs;
		var allowShrink = document.forms['PDFDocList'].allowShrinkPdfs;
		

		var chkHeaderFiles = document.forms['PDFDocList'].chkAllHeaderFilesList;
		var chkFooterFiles = document.forms['PDFDocList'].chkAllFooterFilesList;
		var chkShrinkFiles = document.forms['PDFDocList'].chkAllShrinkFilesList;

		//debugger;
		if(chkall.checked==true)
		{
			chkHeaderFiles.checked=true;
			chkFooterFiles.checked=true;
				for(i=0;i<chkPDF.length;i++)
				{
					chkPDF[i].checked=true;
					chkNodes[i].checked=true;
					chkNodeDispList[i].checked=true;
					allowHeaders[i].checked=true;
					allowFooters[i].checked=true;
				}	
		}
		else
		{
			 chkHeaderFiles.checked=false;
			 chkFooterFiles.checked=false;
			 chkShrinkFiles.checked=false;
				for(i=0;i<chkPDF.length;i++)
				{
					chkPDF[i].checked=false;
					chkNodes[i].checked=false;
					chkNodeDispList[i].checked=false;
					allowHeaders[i].checked=false;
					allowFooters[i].checked=false;
					allowShrink[i].checked=false;
					
				}	
						
		}
		return true;
	}

	function chkAllFiles(list)
	{	
		//debugger;
		var chkFiles;
		var listPdfs;
		var chkPDF = document.forms['PDFDocList'].documents;
		if(list=="header")
		{

			chkFiles = document.forms['PDFDocList'].chkAllHeaderFilesList;
			listPdfs = document.forms['PDFDocList'].allowHeaderPdfs;
		}
		else if(list=="footer")
		{
			chkFiles = document.forms['PDFDocList'].chkAllFooterFilesList;
			listPdfs = document.forms['PDFDocList'].allowFooterPdfs;
			
		}
		else if(list=="shrink")
		{
			chkFiles = document.forms['PDFDocList'].chkAllShrinkFilesList;
			listPdfs = document.forms['PDFDocList'].allowShrinkPdfs;
			
		}
		
		if(chkFiles.checked==true){
			
			for(i=0;i<listPdfs.length;i++)
			{
				if(chkPDF[i].checked==true)
					listPdfs[i].checked=true;
			}
		}else{
			for(i=0;i<listPdfs.length;i++)
				listPdfs[i].checked=false;
		}
		return true;

		
	}

	function chkAllHeader()
	{
		var chkallHeader = document.forms['PDFDocList'].chkallHeader;
		var chkHeaderSetting = document.forms['PDFDocList'].headerSettings;
		if(chkallHeader.checked==true){
				for(i=0;i<chkHeaderSetting.length;i++)
					chkHeaderSetting[i].checked=true;
		}else{
			for(i=0;i<chkHeaderSetting.length;i++)
				chkHeaderSetting[i].checked=false;
		}
		return true;
	}
	function chkAllFooter()
	{
		var chkallFooter = document.forms['PDFDocList'].chkallFooter;
		var chkFooterSetting = document.forms['PDFDocList'].footerSettings;
		if(chkallFooter.checked==true){
			for(i=0;i<chkFooterSetting.length;i++)
					chkFooterSetting[i].checked=true;
			
		}else{
			for(i=0;i<chkFooterSetting.length;i++)
					chkFooterSetting[i].checked=false;
		}
		return true;
	}
   
	</script>
<script type="text/javascript">
	function showErrorFile(nodeId){
		var trEle = document.getElementById('tr_'+nodeId);
		trEle.style.background='#FFDDDD';
		trEle.focus();
		window.scroll(0,trEle.offsetTop); 
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
		$( "#workSpaceId" ).combobox();
		$( "#workSpaceId" ).click(function() {
				$( "#toggle" ).toggle();
		
			});
	});
	


	
</script>
</head>

<!-- <div align="center"> popup for displaying project status
<div id="confirmDialog"
	style="position: fixed; top: 100px; z-index: 100000; display: none;">
<div id="statusDetails"></div>
<br></br>
<input type="button" value="Proceed" class="button" id="btnProceed"
	onclick="proceed();" /> <input type="button" value="Do Not Proceed"
	class="button" id="btnDoNotProceed" onclick="donotproceed()" /></div>



<div id="backgroundPopup" onclick="donotproceed();"></div>
</div> -->
<body>
<div id="backgroundPopup" onclick="proceed();"></div>
<div align="center"><!--  popup for displaying project status -->
<div id="confirmDialog"
	style="position: fixed; top: 100px; z-index: 100000; display: none;">
<div id="statusDetails"></div>
<br>
<br>
	 <!-- <input type="button" value="Do Not Proceed"
	class="button" id="btnDoNotProceed" onclick="donotproceed()" /> -->
	<form action="ExportToXls.do" method="post" id="myform" style="margin-left: 5px;margin-top: -24px">
	<input
			type="hidden" name="fileName" value="Project_Completion_Report.xls">
		<textarea rows="1" cols="5" name="tabText" id="tableTextArea"
			style="visibility: hidden;"></textarea> <input type="button" value="Print" class="button"
			onclick="document.getElementById('tableTextArea').value=document.getElementById('statusDetails').innerHTML;;document.getElementById('myform').submit()">
			<input type="button" value="Proceed" class="button" id="btnProceed" style="margin-right: 50px;"
	onclick="proceed();" />
		</form>
	
	
	</div> 



<!-- 
<div id="backgroundPopup" onclick="proceed();"></div> -->
</div>
<div class="errordiv" align="center" style="color: red"><s:actionerror />
</div>
<B><s:actionmessage /></B>

<br />
<div align="center"><img
	src="images/Header_Images/Submission/Pdf_Publish.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><br>
<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Project Name</td>
		<td><s:select list="getWorkspaceDetail" name="workSpaceId"
			id="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
			headerKey="-1" headerValue="" theme="simple" onchange="subinfo();">

		</s:select></td>

	</tr>


</table>

<div align="center" style="color: green; size: 50px"></div>

<table align="center" width="100%">
	<tr>
		<td align="center"><s:div id="ShowSubInfoDiv">
		</s:div> <s:if test="workSpaceId != NULL && workSpaceId.trim() != ''">
				<script type="text/javascript">
					<!--
					subinfo();
					//-->
				</script>
		</s:if></td>
	</tr>
</table>
</div>
</div>
</div>

</body>


</html>