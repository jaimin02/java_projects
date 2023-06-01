<!DOCTYPE html>


<html lang="en">
<head>
<style type="text/css">
/* Essential FileDrop element configuration: */
.fd-zone {
	position: relative;
	overflow: hidden;
	width: 15em;
	text-align: center;
}

/* Hides <input type="file" /> while simulating "Browse" button: */
.fd-file {
	opacity: 0;
	font-size: 118px;
	position: absolute;
	right: 0;
	top: 0;
	z-index: 1;
	padding: 0;
	margin: 0;
	cursor: pointer;
	filter: alpha(opacity =             0);
	font-family: sans-serif;
}

.uploadFile {
	opacity: 0.2;
	width: 100%;
	height: 200px;
}

#aaa {
	width: 100%;
	height: 200px;
	background-color: gray;;
}

/* Provides visible feedback when use drags a file over the drop zone: */
.fd-zone.over {
	border-color: maroon;
}
</style>
<title></title>
<script>

	function uploadFile1()
	{
		//alert("ff");
		
		 $.ajax({
	           type: "POST",
	           url: "UploadFileSave.do",
	           data: $("#aaa").serialize(), // serializes the form's elements.
	           success: function(data)
	           {
	               alert(data); // show response from the php script.
	           }
	      });
	}
</script>

</head>
<body>

<form id="aaa" enctype="multipart/form-data"><input type="file"
	name="uploadFile" class="uploadFile"
	onchange="javascript:uploadFile1();"></input></form>

</body>
</html>