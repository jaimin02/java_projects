<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/jquery/jquery-1.4.2.js" type="text/javascript"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="s" uri="/struts-tags"%>
<title>KnowledgeNET eCTD-Viewer</title>
<style type="text/css">
a {
	text-decoration: none;
}

* {
	margin: 0;
	padding: 0;
}

a {
	text-decoration: none;
}

body {
	background: rgb(255, 255, 255);
	background: -moz-linear-gradient(89deg, rgb(255, 255, 255) 62%,
		rgb(247, 180, 250) 100% );
	background: -webkit-linear-gradient(89deg, rgb(255, 255, 255) 62%,
		rgb(247, 180, 250) 100% );
	background: -o-linear-gradient(89deg, rgb(255, 255, 255) 62%,
		rgb(247, 180, 250) 100% );
	background: -ms-linear-gradient(89deg, rgb(255, 255, 255) 62%,
		rgb(247, 180, 250) 100% );
	background: linear-gradient(179deg, rgb(255, 255, 255) 62%,
		rgb(247, 180, 250) 100% );
	border: 0px solid #D3D3D3;
	font-family: Verdana, Geneva, sans-serif;
	font-size: 12pt;
	color: #888888;
	text-align: center;
}

.mainLoginPage {
	position: relative;
}

.file-input-wrapper {
	border-radius: 5px;
	width: 330px;
	overflow: hidden;
	position: relative;
	border: 1px solid gray;
	border-spacing: 50px 50px;
}

.file-input-wrapper>input[type="file"] {
	font-size: 230px;
	position: absolute;
	top: 0;
	right: 0;
	opacity: 1;
}

.file-input-wrapper>.btn-file-input {
	width: 330px;
	color: gray;
	font-size: 30px;
}

.file-input-wrapper:hover>.btn-file-input {
	background-color: white;
	color: gray;
}

.newbutton {
	width: 280px;
	font-size: 20px;
	border-radius: 5px;
	padding: 20px;
	height: 20px;
	text-align: center;
	color: gray;
	border: 2px solid gray;
	margin-top: 15px;
	display: block !important;
}

.file-input-wrapper button {
	height: 150px !important;
	border: 1px solid;
	display: block !important;
}

.tbluploder {
	border-radius: 5px;
	background: white url('images/viewerbg.jpg') 90px 0px no-repeat;
	margin-top: 20px;
	background-size: 50%;
	padding-top: 170px;
	padding-bottom: 25px;
}
</style>


<script type="text/javascript">
$(document).ready(function(){

	
});
function uploadDossier(this1)
{

	var ext = document.getElementById(this1.id).value;

	ext=ext.substring(ext.lastIndexOf('.') + 1).toLowerCase();

  if(ext!="zip"){
	 alert("You can upload only Zip File ");
		return false;
	}
	$(".btn-file-input").html("Uploading...<br><font size='3px'>This may take time</font>");
	this1.form.submit();
}
</script>

</head>
<body>
<form enctype="multipart/form-data" action="uploadDossierFileAction.do"
	method="post">
<div align="center" id="container">



<div>
<td><font size="2px">eCTD Viewer</font></td>
<table class="tbluploder" align="center" width="400px"
	style="border: 1px solid;">

	<tr>
		<td>
		<div align="center">
		<div>
		<div class="file-input-wrapper">
		<button class="btn-file-input">Drag or upload Dossier</button>
		<input type="file" id="uploadFile" name="uploadFile"
			onchange="uploadDossier(this);" /></div>
		</div>
		<table>
			<tr>
				<td colspan="2">
				<div align="center"><font color="red" size="2"><b></b>
				</font></div>
				</td>
			</tr>
			<tr>
				<td><a href="ViewAllDossier.do" class="newbutton">View All
				Dossier</a></td>
			</tr>
		</table>


		</div>


		</td>
	</tr>

</table>

</div>

</div>

</form>
</body>
</html>