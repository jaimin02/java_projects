<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>



<html>
<head>

<script type="text/javascript">
var popUpEnableOnStartup='No';
</script>
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
#popupContact {
top:10px !important;
}

</style>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<SCRIPT type="text/javascript">

/* $(document).ready(function() {
	$("#reConfPass").on("keydown", function (e) {
	    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
	    	e.preventDefault();
	        document.getElementById("Verify").click();
	    }
}); */

	function valButton(btn) 
		{
			//alert(btn);
		    var cnt = -1;
		    if(btn.length != null)
		    {
		    	//alert("if");
		    	for (var i=btn.length-1; i > -1; i--) 
		    	{
		        	if (btn[i].checked) 
		        	{
			    	    cnt = i; 
			  	      i = -1;
		        	}
		    	}
		    }
		    else
		    {
		    	//alert("else");
				if(btn.checked)
				{
					cnt = 0;
				}
		    }
		
		    if (cnt > -1)
		     return cnt;
		    else
		     return null;
		}
	function chkIfAnyApproved()
	{
		var ifAnyApproved=false;
		if (document.PendingWorkResultForm.selectedNodeId.length != null){
			
			for(i=0; i<document.PendingWorkResultForm.selectedNodeId.length; i++){
				
				if(document.PendingWorkResultForm.selectedNodeId[i].checked == true)
				{
					var str = 'document.PendingWorkResultForm.select_'+document.PendingWorkResultForm.selectedNodeId[i].value;
					if(eval(str).value == "100")
					{
						ifAnyApproved=true;
					}					
				}
 				
			}			
		}
		else
		{				
			if(document.PendingWorkResultForm.selectedNodeId.checked == true)
			{
				var str = 'document.PendingWorkResultForm.select_'+document.PendingWorkResultForm.selectedNodeId.value;
				if(eval(str).value == "100")
				{
					ifAnyApproved=true;
				}				
			}
 			
		}
		return ifAnyApproved;		
	}
	
	function chgStatus1()
	{
		//debugger;
		//alert("chgStatus1");
		var elecSig=document.getElementById('elecSig').value;
		var currStage=chkIfAnyApproved();
		document.getElementById('reConfPass').value = '';
		//alert(currStage);
		//alert(elecSig);
		if (currStage==true && elecSig=='Yes')
			return false;
		else
			return true;
	}

	function chgStatus()
	{
		//alert('here');
		//alert(document.getElementById('workspaceNodeAttr_stageCode').value);
		//alert(document.getElementById('sessPass').value);			
		var sessPass=document.getElementById('sessPass').value;
		var pass=document.getElementById('reConfPass').value;
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
				setSelectValues();
				//document.forms['PendingWorkResultForm'].submit();
				disablePopup();
				checkData();
				return true;
			}
			else
			{
				alert("Incorrect Password !!!");
				document.getElementById('reConfPass').value = '';
				return false;
			}				
	}
	
	
	function checkData(workSpaceId,selectValues,targetDivId)
	{
		
			//debugger;
			
			var arr=[];
			
			
			var workSpaceId=$('#workSpaceId').val();
			var strSelect="";
			
			var checkboxes = document.getElementsByName('selectedNodeId');
			for(var i=0;i<checkboxes.length;i++){   
			    arr[i]=checkboxes[i].value;
			  } 
			
			for(i=0; i<document.PendingWorkResultForm.selectedNodeId.length; i++){
			
				if(document.PendingWorkResultForm.selectedNodeId[i].checked == true)
				{
					
					var str = 'document.PendingWorkResultForm.select_'+document.PendingWorkResultForm.selectedNodeId[i].value;
					if(strSelect == "")
					{
						strSelect = document.PendingWorkResultForm.selectedNodeId[i].value + '_'	+ eval(str).value ;
					}
					else
					{
						strSelect = strSelect + ','
									+ document.PendingWorkResultForm.selectedNodeId[i].value 
									+ '_' + eval(str).value;
					}
				}
 				
			}
			
			var selectedNodeId="";
			
			for(var i=0;i<arr.length;i++){
				selectedNodeId+=arr[i]+",";
			}
			 
			selectedNodeId=selectedNodeId.slice(0,-1);
			var selectValues=document.PendingWorkResultForm.selectValues.value;
			targetDivId=null;
			$.ajax(
					{	
						url: "CompleteWork_ex.do?workSpaceId=" + workSpaceId+"&temp="+selectValues+
								"&targetDivId="+null+"&selectedNodeId="+arr,
								
						success: function(data) 
				  		{
							 
							if(data=="true")
							{
							 alert("Stage change Successfully...!");
						 	}
							window.location.href = "PendingWorks.do";
							//window.location.href = "ShowPendingWorks.do?workSpaceId=" + workSpaceId;
						},
			            error: function (data) {
			            	alert("Something went wrong");
			            },
			            async: false
					});
	}
	
	
	function validate()
	{
		//debugger;
		//alert('here0');
		//alert(document.forms['PendingWorkResultForm'].selectedNodeId);
		var btn = valButton(document.forms['PendingWorkResultForm'].selectedNodeId);
		//alert('here1');
		if (btn == null) 
		{
			alert('Please Select File(s)');
			return false;
		}
		//alert('here2');
		var res=chgStatus1();
		//alert('res'+res);
		/* if (res==true)
		{
			setSelectValues();
			debugger;
			checkData();
			//document.forms['PendingWorkResultForm'].submit();
			//alert("File Status has been changed successfully..!!");
			return true;
		}
		else
		{ */
		var userName = "<s:property value='#session.username'/>";
		document.getElementById('userName').innerHTML = userName;
		
		var  months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
		var currentdate = new Date();
		var datetime =  currentdate.getDate()+ "-" 
	    +  months[currentdate.getMonth()] + "-" 
	    + currentdate.getFullYear() + " "
	    //+ currentdate.getHours() + ":"  
	    //+ currentdate.getMinutes();
		document.getElementById("date").innerHTML = datetime;
			centerPopup();				
			loadPopup();			
			return false;
		//}
		
	}
	function cls()
	{
		document.getElementById('reConfPass').value = '';
		disablePopup();
	}
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13))  
  		{
  			return false;
  		} 
	} 
	document.onkeypress = detectReturnKey;
	function selectAll(){
		
		var chkBox = document.PendingWorkResultForm.selectedNodeId;
		if (document.PendingWorkResultForm.selectAllButton.value == 'Check All'){
			for(i=0; i<chkBox.length; i++)
			{
				chkBox[i].checked = 'checked';
		 	}
			document.PendingWorkResultForm.selectAllButton.value = 'Uncheck All';
		 }else
		  {
			  
			 for(i=0; i<chkBox.length; i++)
			 {
				 chkBox[i].checked = '';
			 }
			 document.PendingWorkResultForm.selectAllButton.value = 'Check All';
		 }	

	/*	if (document.PendingWorkResultForm.selectedNodeId.length != null){
			for(i=0; i<document.PendingWorkResultForm.selectedNodeId.length; i++){
				str="document.PendingWorkResultForm.selectedNodeId["+i+"].checked=true";
	 			eval(str);
			}
		}
		else
		{				
			str="document.PendingWorkResultForm.selectedNodeId.checked=true";
	 		eval(str);
		}
		
		return true;*/
	}

function selectedAll(){
		//debugger;
		var chkBox = document.PendingWorkResultForm.selectedNodeId;
		if (document.PendingWorkResultForm.selectAllButton1.value == 'Check All'){
		/* 	for(i=0; i<chkBox.length; i++)
			{
				chkBox[i].checked = 'checked';
		 	} */
			 if($('[name="selectedNodeId"]').length==1){
				 chkBox.checked = 'checked';
			   } else{
					
				   for(i=0; i<chkBox.length; i++)
					{
						chkBox[i].checked = 'checked';
				 	} 
				}
			document.PendingWorkResultForm.selectAllButton1.value = 'Uncheck All';
		 }else
		  {
			 /*  
			 for(i=0; i<chkBox.length; i++)
			 {
				 chkBox[i].checked = '';
			 } */
			 if($('[name="selectedNodeId"]').length==1){
				 chkBox.checked = '';
			   } else{
					
				   for(i=0; i<chkBox.length; i++)
					{
						chkBox[i].checked = '';
				 	} 
				}
			 document.PendingWorkResultForm.selectAllButton1.value = 'Check All';
		 }	

	/*	if (document.PendingWorkResultForm.selectedNodeId.length != null){
			for(i=0; i<document.PendingWorkResultForm.selectedNodeId.length; i++){
				str="document.PendingWorkResultForm.selectedNodeId["+i+"].checked=true";
	 			eval(str);
			}
		}
		else
		{				
			str="document.PendingWorkResultForm.selectedNodeId.checked=true";
	 		eval(str);
		}
		
		return true;*/
	}

	
	function setSelectValues()
	{
		var strSelect = "";
			if (document.PendingWorkResultForm.selectedNodeId.length != null){
				
				for(i=0; i<document.PendingWorkResultForm.selectedNodeId.length; i++){
					
					if(document.PendingWorkResultForm.selectedNodeId[i].checked == true)
					{
						var str = 'document.PendingWorkResultForm.select_'+document.PendingWorkResultForm.selectedNodeId[i].value;
						if(strSelect == "")
						{
							strSelect = document.PendingWorkResultForm.selectedNodeId[i].value + '_'	+ eval(str).value ;
						}
						else
						{
							strSelect = strSelect + ','
										+ document.PendingWorkResultForm.selectedNodeId[i].value 
										+ '_' + eval(str).value;
						}
					}
	 				
				}
				document.PendingWorkResultForm.selectValues.value = strSelect;
			}
			else
			{				
				if(document.PendingWorkResultForm.selectedNodeId.checked == true)
				{
					var str = 'document.PendingWorkResultForm.select_'+document.PendingWorkResultForm.selectedNodeId.value;
					document.PendingWorkResultForm.selectValues.value = document.PendingWorkResultForm.selectedNodeId.value
																+ '_' + eval(str).value;
				}
	 			
			}
			
		//alert(document.PendingWorkResultForm.selectValues.value);
	}
	
	function submitForm()
	{
		document.completeForm.submitBtn.click();
	}

	function subinfo()
	{	
		var workSpaceId=$('#workSpaceId').val();
		$.ajax(
		{			
			url: 'ShowPendingWorks.do?workSpaceId=' + workSpaceId,
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
				$( "#ViewPendingWorks" ).toggle();
		
			});
	});
	$("#reConfPass").focus();
	$("#reConfPass").on("keydown", function (e) {
	    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
	    	e.preventDefault();
	        document.getElementById("Verify").click();
	    }
	});

	
</SCRIPT>


</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div class="container-fluid">
<div class="col-md-12">

<div align="center"> <!-- <img
	src="images/Header_Images/Report/Pending_Work_Report.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">My Pending Work Report</b></div></div>

<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div align="left"><s:form action="ShowPendingWorks" method="post"
	name="completeForm" onsubmit="return false;">
	<br>
	<table width="100%">
		<tr>
			<td class="title" align="left" width="8%"
				style="padding: 2px; padding-right: 8px; font-size:14px">Project Name</td>
			<td><s:select list="getAllWorkspace" name="workSpaceId"
				id="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="-1" headerValue="" theme="simple">
			</s:select> <%-- <s:submit cssClass="button" value="Show" name="submitBtn"
				theme="ajax" targets="ViewPendingWorks"
				cssStyle="visibility: hidden;"></s:submit> --%></td>
		</tr>

	</table>
</s:form>
<table align="center" width="100%">
	<%-- <tr>
		<td align="center"><s:if
			test="workSpaceId != NULL && workSpaceId != ''">
			<SCRIPT type="text/javascript">
			jQueryOnchangeAutocompleter();
  			</SCRIPT>
		</s:if></td>
	</tr> --%>
</table>
</div>
<div id="popupContact" style="height: 225px;"><img
	alt="Close" title="Close" src="images/Common/Close.png"
	onclick='cls();' class='popupCloseButton' />
<h3>Electronic Signature</h3>
<table>
	<tr><td><b>User Name</b></td><td><b>:</b></td><td><span id="userName"></span></td></tr>
	<tr><td><b>Date</b></td><td><b>:</b></td><td><span id="date"></span></td></tr>
	<%-- <tr><td><b>Action</b></td><td><b>:</b></td><td><span id="nextStageDesc"></span></td></tr> --%>
	<tr><td><b>Enter password</b></td><td><b>:</b></td><td><input type="password" name="reConfPass"id="reConfPass"></input></td></tr>
</table><br>
<p align="center">
<span><b>I hereby confirm signing of this document electronically.</b></span><br><br>
<input type="button" class="button" value="Sign"
	name="buttonName" id="Verify" onclick="return chgStatus();"></input></p>
</div>
<div id="backgroundPopup"></div>

<div id="ShowSubInfoDiv"></div>
</div>


</div>
</div>
</div>
</body>
</html>

