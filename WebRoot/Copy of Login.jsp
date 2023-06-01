<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>

<script src="<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/parsley.js/1.2.2/parsley.min.js"></script>

<script src="http://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>


<link href="<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.css"
	rel="stylesheet" type="text/css" media="screen" />


<link href="http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300"
	rel="stylesheet" type="text/css" media="screen" />


<style>

body{
  background:#b4b4b4;
}

#registration-form {
  font-family:'Open Sans Condensed', sans-serif;
	width: 400px;
  min-width:250px;
	margin: 20px auto;
	position: relative;
}

#registration-form .fieldset {
	background-color:#d5d5d5;

	border-radius: 3px;
  
}

 #registration-form legend {
	text-align: center;
	/*
	background: #364351;
	*/
	background:rgba(54,67,81,0.9); 
	width: 100%;
	padding: 30px 0;
	border-radius: 3px 3px 0 0;
	color: white;
font-size:2em;
opacity:0.5;
}

.fieldset form{
  border:1px solid #2f2f2f;
  margin:0 auto;
  display:block;
  width:100%;
  padding:30px 20px;
  box-sizing:border-box;
  border-radius:0 0 3px 3px;
}
.placeholder #registration-form label {
	display: none;
}
 .no-placeholder #registration-form label{
margin-left:5px;
  position:relative;
  display:block;
  color:grey;
  text-shadow:0 1px white;
  font-weight:bold;
}
/* all */
::-webkit-input-placeholder { text-shadow:1px 1px 1px white; font-weight:bold; }
::-moz-placeholder { text-shadow:0 1px 1px white; font-weight:bold; } /* firefox 19+ */
:-ms-input-placeholder { text-shadow:0 1px 1px white; font-weight:bold; } /* ie */
#registration-form input[type=text] {
	padding: 15px 20px;
	border-radius: 1px;
  margin:5px auto;
  background-color:#f7f7f7;
	border: 1px solid silver;

	-webkit-box-shadow: inset 0 1px 5px rgba(0,0,0,0.2);
	box-shadow: inset 0 1px 5px rgba(0,0,0,0.2), 0 1px rgba(255,255,255,.8);
	width: 100%;

	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
    -webkit-transition:background-color .5s ease;
	-moz-transition:background-color .5s ease;
	-o-transition:background-color .5s ease;
	-ms-transition:background-color .5s ease;
	transition:background-color .5s ease;
}
.no-placeholder #registration-form input[type=text] {
	padding: 10px 20px;
}

#registration-form input[type=text]:active, .placeholder #registration-form input[type=text]:focus {
	outline: none;
	border-color: silver;
  background-color:white;
}

#registration-form input[type=submit] {
  font-family:'Open Sans Condensed', sans-serif;
  text-transform:uppercase;
  outline:none;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
	width: 100%;
	background-color: #5C8CA7;
	padding: 10px;
	color: white;
	border: 1px solid #3498db;
	border-radius: 3px;
	font-size: 1.5em;
	font-weight: bold;
	margin-top: 5px;
	cursor: pointer;
    position:relative;
    top:0;
}

#registration-form input[type=submit]:hover {
	background-color: #2980b9;
}

#registration-form input[type=submit]:active {
	background:#5C8CA7;
}


.parsley-error-list{
	background-color:#C34343;
	padding: 5px 11px;
	margin: 0;
	list-style: none;
	border-radius: 0 0 3px 3px;
	margin-top:-5px;
	  margin-bottom:5px;
	  color:white;
	  border:1px solid #870d0d;
	  border-top:none;
	    -webkit-box-sizing: border-box;
	  -moz-box-sizing: border-box;
	  box-sizing: border-box;
	  position:relative;
	  top:-1px;
	  text-shadow:0px 1px 1px #460909;
	    font-weight:700;
	  font-size:12px;
}
.parsley-error{
	border-color:#870d0d!important;
  border-bottom:none;
}
#registration-form select{
	width:100%;
	padding:5px;
	border-radius: 1px;
	background-color:#f7f7f7;
	
	-webkit-box-shadow: inset 0 1px 5px rgba(0,0,0,0.2);
	box-shadow: inset 0 1px 5px rgba(0,0,0,0.2), 0 1px rgba(255,255,255,.8);
	width: 100%;
	
	height:40px;

	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
  -webkit-transition:background-color .5s ease;
-moz-transition:background-color .5s ease;
-o-transition:background-color .5s ease;
-ms-transition:background-color .5s ease;
transition:background-color .5s ease;
	
}
::-moz-focus-inner {
  border: 0;
}
</style>

<SCRIPT type="text/javascript">
function placeholderIsSupported() {
	test = document.createElement('input');
	return ('placeholder' in test);
}

$(document).ready(function(){
p	laceholderSupport = placeholderIsSupported() ? 'placeholder' : 'no-placeholder';
$('html').addClass(placeholderSupport);  
});
</SCRIPT>
</head>

<body>
<div id="container">



</div>

<div id="registration-form">
	<div class='fieldset'>
    <legend>New User?</legend>
		<form action="#" method="post" data-validate="parsley">
			<div class='row'>
				<label for='firstname'>First Name</label>
				<input type="text" placeholder="First Name" name='firstname' id='firstname' data-required="true" data-error-message="Your First Name is required">
			</div>
			<div class='row'>
				<label for="email">E-mail</label>
				<input type="text" placeholder="E-mail"  name='email' data-required="true" data-type="email" data-error-message="Your E-mail is required">
			</div>
			<div class='row'>
				<label for="cemail">Confirm your E-mail</label>
				<input type="text" placeholder="Confirm your E-mail" name='cemail' data-required="true" data-error-message="Your E-mail must correspond">
			</div>
			
			<div class='row'>
				<label for="submissiontype">Submission Type</label>
				<select name='submissiontype' data-required="true" data-error-message="Please Select Submission Type">
					<option value="ectd">ECTD</option>
					<option value="dms">DMS</option>
					<option value="pader">PADER</option>
				</select>
			</div>
			
			<input type="submit" value="Register">
		</form>
	</div>
</div>


</body>
</html>

