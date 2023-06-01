<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<s:head theme="ajax" />

<script language="javascript">
	$(document).ready(function()
	{
	// pre-submit callback
			$('.target').change(function(){
			var userType=document.getElementById('userTypeCode').value;				
			var urlOfAction="DisplayTreeAction_ex.do";
			var dataofAction="userTypeCode="+userType;
			//alert(userType);
			$.ajax({
				type: "GET", 
   				url: urlOfAction, 
   				data: dataofAction, 
   				cache: false,
   				dataType:'html',
   				beforeSend: function(){ 
					showHideLoading('show');
				
				},
				success: function(response){
					$('#htmlRole').html(response);
					showHideLoading('hide');
				}
			});
		});
	});
		function showHideLoading(showOrHide){
 		if(showOrHide == 'show'){
 			var wtrMrkDiv = document.getElementById("waterMarkdiv");
 			var imgDiv = document.getElementById("imgId");
 			
 			wtrMrkDiv.style.display = '';
 			wtrMrkDiv.style.height = window.screen.height;
 			wtrMrkDiv.style.width = window.screen.width;
			
			imgDiv.style.display = '';
			imgDiv.style.height = window.screen.height;
 			imgDiv.style.width = window.screen.width; 
			
		}else{
			document.getElementById("waterMarkdiv").style.display = 'none';
			document.getElementById("imgId").style.display = 'none';
		}			
 }
 	function validate(){
 		if(document.RoleOperationForm.userTypeCode.value==-1){
 		alert('Please Select the User Type Code');
 		return false;
 		}
 		else {
 			return true;
 		}
 	}
 	
 	
</script>

<script src="<%=request.getContextPath()%>/js/jquery.treeview.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.cookie.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/loading_bar.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/jquery.treeview.css" />
</head>
<body>
<div id="waterMarkdiv"
	style="display: none; position: absolute; z-index: 900; width: 100%; height: 100%; top: 0; left: 0; background: black; opacity: 0.50; filter: alpha(opacity =             50);">
</div>
<div align="center"
	style="display: none; position: absolute; z-index: 1000; width: 100%; height: 100%; top: 150; left: 0"
	id="imgId"><img src="images/jquery_tree_img/loading_bar.gif"
	style="" align="middle" /></div>

<div id="targetClass" align="center"
	style="padding: 2px; height: 5px; width: 100%;"><s:actionmessage /></div>


<br />
<div align="center"><img
	src="images/Header_Images/Master/Role_Operation_Matrix.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; border-top: none;"
	align="center"><br>
<div align="left" id="OuterDiv"><s:form
	action="CopyRoleOperationAction" method="post" id="RoleOperationClass"
	name="RoleOperationForm">

	<div align="center">
	<div style="width: 600px; padding: 10px;"><label
		style="font: bold;" class="title">User Type &nbsp;&nbsp;</label><s:select
		list="allUserType" headerKey="-1" headerValue="Select User Type"
		listKey="userTypeCode" listValue="userTypeName" cssClass="target"
		name="userTypeCode" id="userTypeCode"></s:select></div>
	<br>

	<DIV
		STYLE="width: 600px; border: 1px solid rgb(0, 0, 0); height: 250px; overflow: auto;"
		align="left" id="htmlRole">${htmlRole}</DIV>
	<br>
	<s:submit name="submitBtn" value="Save Operation" cssClass="button"
		onclick="return validate();" align="center" theme="ajax"
		targets="targetClass"></s:submit> <br>
	</div>
</s:form></div>
</div>
</div>
</body>
</html>

