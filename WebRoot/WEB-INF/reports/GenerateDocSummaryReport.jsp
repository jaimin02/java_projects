<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ajax" uri="javawebparts/taglib/ajaxtags"%>
<% 
        response.addHeader("Pragma","no-cache"); 
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
        response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
        response.setDateHeader("Expires",0); 
%>

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
<script type="text/javascript">
	$(document).ready(function() {

		$("#checkDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy-mm-dd'});
		});
	
</script>

<SCRIPT type="text/javascript">
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
		$( "#workSpaceId" ).combobox();
		
	});

	function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
	}
	function generateTable()
	{
		
		if(validate()){
		    document.getElementById("workspace").value=document.getElementById("workSpaceId").value;
			var wsDesc=document.reportForm.workSpaceId.options[document.reportForm.workSpaceId.selectedIndex].text;
			document.getElementById("wsDesc").value=wsDesc;
			document.reportForm.action="DocSummaryTable.do"/*?workspaceDesc=+wsDesc*/;
			document.reportForm.submit();
			return true;
		}
		return false;
					
	}
	
	function validate()
	{
		var opval = getCheckedValue(document.reportForm.opvalue);
		 if(document.reportForm.workSpaceId.value=="-1")
		{
			alert("Please select a Project..");
			document.reportForm.workSpaceId.style.backgroundColor="#FFE6F7";
			document.reportForm.workSpaceId.focus();
			return false;
		}
		/*
		if(document.reportForm.locationCode.value =="-1")
		{
			alert("Please select a Region..");
			document.reportForm.locationCode.style.backgroundColor="#FFE6F7";
			document.reportForm.locationCode.focus();
			return false;
		}
		else if(document.reportForm.deptCode.value=="-1")
		{
			alert("Please select a Department..");
			document.reportForm.deptCode.style.backgroundColor="#FFE6F7";
			document.reportForm.deptCode.focus();
			return false;
		}
		else if(document.reportForm.workSpaceId == null)
		{
			document.reportForm.locationCode.value = "-1";
			document.reportForm.deptCode.value = "-1";
			document.reportForm.locationCode.style.backgroundColor="#FFE6F7";
			document.reportForm.deptCode.style.backgroundColor="#FFE6F7";
			alert("Project Name not found!!! \n\nPlease select Region and Department again..");
			document.reportForm.locationCode.focus();
			return false;
		}
		else if(document.reportForm.workSpaceId.value=="-1")
		{
			alert("Please select a Project..");
			document.reportForm.workSpaceId.style.backgroundColor="#FFE6F7";
			document.reportForm.workSpaceId.focus();
			return false;
		}
		else if(opval=="U" && document.reportForm.userCode.value=="-1")
		{
			alert("Please select a User..");
			document.reportForm.userCode.style.backgroundColor="#FFE6F7";
			document.reportForm.userCode.focus();
			return false;
		}
		else if(opval=="D" && document.reportForm.modifyAfBf.value=="0")
		{
			alert("Please select After/Before..");
			document.reportForm.modifyAfBf.style.backgroundColor="#FFE6F7";
			document.reportForm.modifyAfBf.focus();
			return false;
		}
		else if(opval=="D" && document.reportForm.checkDate.value=="")
		{
			alert("Please select Date from Calender..");
			document.reportForm.checkDate.style.backgroundColor="#FFE6F7";
			document.reportForm.cal.focus();
			return false;
		}
		else if(opval=="S" && document.reportForm.status.value=="0")
		{
			alert("Please select Status..");
			document.reportForm.status.style.backgroundColor="#FFE6F7";
			document.reportForm.status.focus();
			return false;
		}
		*/
		return true;
	}
	
	function showCombos()
	{ 
		if(document.reportForm.workSpaceId == null)
		{
			return document.reportForm.locationCode.onmousemove();
		}
	}
	
</SCRIPT>

</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Report/Document_Summary_Report.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form action="docsummaryTree" method="post"
	name="reportForm">
	<br>

	<table width="100%">
		<!--  
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Region Name</td>
			<td><select name="locationCode">
				<option value="-1">Select Region Name</option>
				<s:iterator value="LocationDtl">
					<option value="<s:property value="locationCode"/>"><s:property
						value="locationName" /></option>
				</s:iterator>
			</select><ajax:event ajaxRef="showProjectDtl/getProjectDtl" /></td>
		</tr>


		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Dept Name</td>
			<td><select name="deptCode">
				<option value="-1">Select Department Name</option>
				<s:iterator value="DeptDtl">
					<option value="<s:property value="deptCode"/>"><s:property
						value="deptName" /></option>
				</s:iterator>
			</select><ajax:event ajaxRef="showProjectDtl/getProjectDtl1" /></td>
		</tr>

		-->
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td><s:select list="getAllWorkspace" name="workSpaceId"
				id="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="-1" headerValue="" theme="simple">
				</s:select> 
			</td>
		</tr>
		
		<tr style="display: none;">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><input type="radio"
				name="opvalue" value="U" checked="checked" id="username"> <label
				for="username">User Name </label></td>
		</tr>
		<!-- 
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><input type="radio"
				name="opvalue" value="D" id="modifieddate"> <label
				for="modifieddate">Modify Date</label></td>
			<td width="88%" align="left" style="min-width: 100px;"><select
				name="modifyAfBf">
				<option value="0">Select One</option>
				<option value="1">After</option>
				<option value="2">Before</option>
			</select> <input type="text" name="checkDate" readonly="readonly"
				id="checkDate"> (YYYY-MM-DD)</td>
		</tr>
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><input type="radio"
				name="opvalue" value="S" id="status"> <label for="status">Status</label>
			</td>
			<td><select name="status">
				<option value="All">All</option>
				<option value="Created">Created</option>
				<option value="Reviewed">Reviewed</option>
				<option value="Authorized">Authorized</option>
				<option value="Approved">Approved</option>
			</select></td>
		</tr>
 			-->
		<tr>
			<td></td>
		</tr>
		<tr>
			<!-- <td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;"><s:submit
				value="Tree View" cssClass="button" onclick="return validate()"></s:submit>

			</td> -->
			
			<td colspan="2" width="70%" align="center"><input type="button"
				value="Submit" onclick="return generateTable()" class="button" />
			<s:hidden name="searchOn" value="opvalue.value"></s:hidden></td>
		</tr>

	</table>

	<ajax:enable />
	<input type="hidden" name="workspace" id="workspace" value="">
	<input type="hidden" id="wsDesc" name="workspaceDesc" value="">
</s:form></div>
</div>
</div>

</body>
</html>

