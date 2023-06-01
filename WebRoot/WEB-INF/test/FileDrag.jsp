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

<div class="filedragdialog">

<div id="dropArea" />
<p style="padding: 50px;">Drag File here</p>
</div>

<div class="info">

<h2>Result:</h2>
<div id="result"></div>
<canvas width="10" height="10"></canvas></div>
</div>



<script src="js/FileDragscript.js"></script>
</body>
</html>