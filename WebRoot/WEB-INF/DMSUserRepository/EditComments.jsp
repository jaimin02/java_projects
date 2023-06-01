<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
 		$(document).ready(function() {
			$('#popupContactClose').click(function(){
				disablePopup();
			});
 		});
	</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center"><a id="popupContactClose"><img alt="Close"
	title="Close" src="images/Common/Close.png" /></a>
<div align="left"
	style="font-family: verdana; font-size: 15px; font-weight: bold; color: #5B89AA; margin-bottom: 5px;">
<s:if test="stageId == 10">
						Reviewer 
					</s:if> <s:elseif test="stageId == 20">
						Approver 
					</s:elseif> Comments</div>
<hr color="#5A8AA9" size="3px"
	style="width: 100%; border-bottom: 2px solid #CDDBE4;" align="left">
<div style="width: 100%; height: 95px;"><br>
<div align="left"><s:form name="editCommentsForm" method="post"
	action="SaveComments_ex">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Comments&nbsp;</td>
			<td align="left"><input name="userComments" value=''></td>
		</tr>
		<tr>
			<td align="right" style="padding: 2px; padding-right: 6px;"><input
				type="checkbox" id="nextStage" name="nextStage" /></td>
			<td class="title" align="left"><label for="nextStage"><s:property
				value="stageDesc" /> Forward in NEXT stage..&nbsp;</label></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="button" value="Save Comments" class="button"
				onclick="saveComments('<s:property value="stageId"/>','<s:property value="nodeId"/>');">
			</td>
		</tr>

	</table>
</s:form> <br>
</div>
</div>
</div>
</body>
</html>