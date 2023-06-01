<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta charset="utf-8" />
<title>HTML5 Drag and Drop Multiple File Uploader | Script
Tutorials</title>
<link href="css/FileDragmain.css" rel="stylesheet" type="text/css" />

<!--[if lt IE 9]>
          <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
</head>
<body>
<header tabindex="0">
<h2>HTML5 Drag and Drop Multiple File Uploader</h2>

</header>

<s:form action="resultAction" method="POST" name="fileuploadForm"
	id="fileuploadForm" enctype="multipart/form-data">
	<div class="container">
	<div class="contr">

	<h2>Drag and Drop your images to 'Drop Area' (up to 5 files at a
	time, size - under 256kb)</h2>
	</div>
	<div class="upload_form_cont"><s:file id="dropArea"
		name="fileUpload" /> <s:submit value="submit" id="btnsubmit" />
	<div class="info">
	<div>Files left: <span id="count">0</span></div>
	<div>Destination url: <input id="url" value="UploadFileSave.do" /></div>
	<h2>Result:</h2>
	<div id="result"></div>
	<canvas width="500" height="20"></canvas></div>

	</div>
	</div>
</s:form>
<script src="js/FileDragscript.js"></script>
</body>
</html>