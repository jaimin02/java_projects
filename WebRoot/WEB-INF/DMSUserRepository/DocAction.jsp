<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
 		$(document).ready(function() {
			var options = {	
					success: showResponse 
				};
			$("#saveDocAction").click(function(){
					var comments = $("#comments").val();
					comments = trim(comments);
					if (comments.length > 0)
						$("#editDocActionForm").ajaxSubmit(options);
					
					else
						alert("Please Enter The Comments.");
					
					
				return false;			
			});
 					
 	 		
			$('#popupContactClose').click(function(){
				disablePopup();
			});
 		});
 		function showResponse(responseText, statusText) 
		{
			alert(responseText);
			disablePopup();
		}
 		function trim(str)
		{
		   	str = str.replace( /^\s+/g, "" );// strip leading
			return str.replace( /\s+$/g, "" );// strip trailing
		}
	</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center"><a id="popupContactClose"><img alt="Close"
	title="Close" src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">
Document Action</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div style="width: 100%; height: 95px;"><br>
<div align="left"><s:form name="editDocActionForm" method="post"
	action="SaveDocAction_ex" id="editDocActionForm">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Comments&nbsp;</td>
			<td align="left"><input name="comments" value='' id="comments"
				type="text"></td>
		</tr>
		<tr>
			<td class="title" align="right"
				style="padding: 2px; padding-right: 6px;">Action&nbsp;</td>
			<td align="left"><input type="radio" name="statusIndi" value="P"
				id="print"><label for="print">&nbsp;Re-Print</label> <input
				type="radio" name="statusIndi" checked="checked" value="R"
				id="Returned"><label for="Returned">&nbsp;Returned</label> <input
				type="hidden" name="workspaceId"
				value="<s:property value="workspaceId"/>"> <input
				type="hidden" name="nodeId" value="<s:property value="nodeId"/>">
			<input type="hidden" name="filePath"
				value="<s:property value="filePath"/>"> <input type="hidden"
				name="docId" value="<s:property value="docId"/>"> <input
				type="hidden" name="stage" value="<s:property value="stage"/>">
			<input type="hidden" name="docVersion"
				value="<s:property value="docVersion"/>"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="button" value="Save Action" class="button"
				id="saveDocAction"></td>
		</tr>
	</table>
</s:form> <br>
</div>
</div>
</div>
</body>
</html>