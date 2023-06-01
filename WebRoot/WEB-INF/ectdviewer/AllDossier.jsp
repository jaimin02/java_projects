<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>KnowledgeNET eCTD-Viewer</title>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/tablefilter/jquery.filtertable.js"></script>
<style>

.progress {
	position: relative;
	width: 400px;
	border: 1px solid #ddd;
	padding: 1px;
	border-radius: 3px;
}

.bar {
	background-color: black;
	width: 0%;
	height: 20px;
	border-radius: 3px;
}
.percent {
	position: absolute;
	display: inline-block;
	top: 3px;
	left: 48%;
}

#frmUploadSequence {
	margin-top: 10px;
}

#imgLoader {
	position: relative;
	left: 10px;
	top: 8px;
	display: none;
}

#status {
	color: white;
	font-size: 18px;
}

.project_title {
	background-color: darkcyan;
	width: 100%;
}

#nextSequence {
	z-index: 99999999;
	width: 50%;
	height: 20%;
	border-radius: 1px;
	color: white;
	background-color: rgba(121, 120, 120, 1);
	box-shadow: 0px 0px 0px 8px rgba(0, 0, 0, 0.3);
	position: fixed;
	top: 30%;
	bottom: 10%;
	left: 20%;
	display: none;
}

.error {
	color: red;
	font-size: 18px;
}
</style>

<SCRIPT type="text/javascript">
	function uploadSequence(path) {

		$("#nextSequence").show(100);
		$("#location").val(path);
		$("#replaceSequence").val("no");
		$(".project_title").html(path);
		$("#imgLoader").hide();
		$("#uploadFile").val("");
		$("#status").html("");
	}

	function deleteFolder(path, divId, imgId, linkId,obj) {

		//debugger;
		
		if (!confirm("Are you sure?")) {
			return false;
		}
		divId = '#' + divId;
		$.ajax( {
			url : path,
			beforeSend : function() {
				document.getElementById(imgId).style.display = '';
				document.getElementById(linkId).style.display = 'none';
			},
			success : function(data) {
				obj.parentElement.parentElement.remove();

			}
		});
	}
	function getSize(path, divId, imgId, linkId) {

		divId = '#' + divId;
		$.ajax( {
			url : path,
			beforeSend : function() {
				document.getElementById(imgId).style.display = '';
				document.getElementById(linkId).style.display = 'none';
			},
			success : function(data) {
				document.getElementById(imgId).style.display = 'none';
				$(divId).html(data);

			}
		});
	}
	function viewDossierSize(dossierURL, Obj) {
		//debugger;

		$.ajax( {
			url : dossierURL,
			beforeSend : function() {
				Obj.innerHTML = 'Getting...';
			},
			success : function(data) {

				Obj.innerHTML = data;
			},
			error : function(a, b, c) {
			},
			async : false
		});

	}

	$(document).ready(
			function() {

				$("#closeForm").click(function() {
					$("#nextSequence").hide(100);
				});
				var stripeTable = function(table) { //stripe the table (jQuery selector)
					table.find('tr').removeClass('striped').filter(
							':visible:even').addClass('striped');
				};
				$('#table1').filterTable( {
					//ss
						callback : function(term, table) {
							stripeTable(table);
						} //call the striping after every change to the filter term
					});
				stripeTable($('#table1')); //stripe the table for the first time
				$('#table2').filterTable( {
					//ss
						callback : function(term, table) {
							stripeTable(table);
						} //call the striping after every change to the filter term
					});
				stripeTable($('#table2'));

			});
</SCRIPT>
<style type="text/css">
a {
	text-decoration: none;
}

a img {
	border: none;
}

.filter-table {
	margin-top: 10px !important;
}

table {
	margin-top: 10px;
}

#table1 {
	width: 99%;
}

.tabs {
	position: relative;
	right: 0px;
}

.tabs a {
	background: #5B89AA;
	cursor: pointer;
	padding: 8px;
	padding-left: 20px;
	padding-right: 20px;
	color: #fff;
	border: 1px solid #666;
	border-bottom: 0;
	-webkit-border-radius: 0px 0px 4px 4px;
}

.tabs a:hover {
	position: relative;
	top: -1px;
	-webkit-box-shadow: -1px 1px 15px #888; /* WebKit */
	-moz-box-shadow: -1px 1px 15px #888; /* Firefox */
	-o-box-shadow: -1px 1px 15px #888; /* Opera */
	box-shadow: -1px 1px 15px #888; /* Standard */
}

.
.tabContent {
	border: 1px solid #aaa;
	margin: 8px 0;
	padding: 5px;
}

.tabs a.active {
	font-weight: bold;
	cursor: default;
	border-bottom: 1px solid pink;
	background: white;
	color: black;
	position: relative;
	top: -3px;
	border-radius: 0 0 -10px 0;
	-webkit-box-shadow: -1px 1px 15px #888; /* WebKit */
	-moz-box-shadow: -1px 1px 15px #888; /* Firefox */
	-o-box-shadow: -1px 1px 15px #888; /* Opera */
	box-shadow: -1px 1px 15px #888; /* Standard */
	border-bottom: 0px;
}
</style>
</head>
<body>
<center>
<div align="center"><img
	src="images/Header_Images/Report/eCTD_viewer.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<span class="error"> </span>
<div class="tabs" align="right"><a data-toggle="tab1">EU</a> <a
	data-toggle="tab2">US</a></div>

<div align="left">


<div class="tabContent"><span id="tab1">
<table id="table1" class="datatable" align="center" style="width: 100%;">
	<thead>
		<tr>
			<th scope="col" title="Number" width="15">#</th>
			<th scope="col">Dossier Name</th>
			<th scope="col" width="45px">No. Of Sequence</th>
			<th scope="col" width="45px">Upload Sequence</th>
			<th scope="col" width="85px" style="text-align: center;">Size</th>
			<th scope="col" width="45px">Delete</th>
		</tr>
	</thead>

	<tbody>
		<s:iterator value="allProjectDetailEU" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>"
				style="text-align: center;">
				<td>${status.count}</td>
				<td><a href="<s:property  value="dossierURL"/>"><s:property
					value="dossierName" /></a></td>
				<td><s:property value="availableSequence" /></td>
				<td style="text-align: center"><img style="cursor: pointer;"
					src="images/item-add.png" title='<s:property value="dossierName"/>'
					onclick="uploadSequence(this.title);" alt="Upload Sequence" /></td>
				<td style="text-align: center"
					id='sub<s:property value="#status.count"/>'><img
					style="cursor: pointer;" title="Get Size"
					src="images/Common/open.png"
					id='link<s:property value="#status.count"/>'
					onclick="getSize('<s:property value="viewDossierSizzeURL"/>','sub<s:property value="#status.count"/>','imgLoading<s:property value="#status.count"/>','link<s:property value="#status.count"/>');return false;" />
				<img src="images/loading.gif"
					id="imgLoading<s:property value="#status.count"/>"
					alt="loading ..." style="display: none;" /></td>

				<td style="text-align: center"
					id='subdel<s:property value="#status.count"/>' align="center">

				<img style="cursor: pointer;" title="Delete"
					src="images/Common/delete.png"
					id='linkdel<s:property value="#status.count"/>'
					onclick="deleteFolder('<s:property value="projectPath"/>','subdel<s:property value="#status.count"/>','imgLoadingdel<s:property value="#status.count"/>','linkdel<s:property value="#status.count"/>',this);return false;" />
				<img src="images/loading.gif"
					id="imgLoadingdel<s:property value="#status.count"/>"
					alt="loading ..." style="display: none;"></td>

			</tr>

		</s:iterator>
	</tbody>

</table>
</span> 
<span id="tab2">
<table id="table2" class="datatable" align="center" style="width: 100%;">
	<thead>
		<tr>
			<th scope="col" title="Number" width="15">#</th>
			<th scope="col">Dossier Name</th>
			<th scope="col" width="45px">No. Of Sequence</th>
			<th scope="col" width="45px">Upload Sequence</th>
			<th scope="col" width="85px" style="text-align: center;">Size</th>
			<th scope="col" width="45px">Delete</th>
		</tr>
	</thead>

	<tbody>
		<s:iterator value="allProjectDetailUS" status="status">


			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>"
				style="text-align: center;">
				<td>${status.count}</td>
				<td><a href="<s:property  value="dossierURL"/>"><s:property
					value="dossierName" /></a></td>
				<td><s:property value="availableSequence" /></td>
				<td style="text-align: center"><img style="cursor: pointer;"
					src="images/item-add.png" title='<s:property value="dossierName"/>'
					onclick=uploadSequence(this.title);;; alt="Upload Sequence" /></td>
				<td style="text-align: center"
					id='subus<s:property value="#status.count"/>'><img
					style="cursor: pointer;" title="Get Size"
					src="images/Common/open.png"
					id='linkus<s:property value="#status.count"/>'
					onclick="getSize('<s:property value="viewDossierSizzeURL"/>','subus<s:property value="#status.count"/>','imgLoadingus<s:property value="#status.count"/>','linkus<s:property value="#status.count"/>');return false;" />
				<img src="images/loading.gif"
					id="imgLoadingus<s:property value="#status.count"/>"
					alt="loading ..." style="display: none;" /></td>

				<td style="text-align: center"
					id='subdelus<s:property value="#status.count"/>' align="center">

				<img style="cursor: pointer;" title="Delete"
					src="images/Common/delete.png"
					id='linkdelus<s:property value="#status.count"/>'
					onclick="deleteFolder('<s:property value="projectPath"/>','subdelus<s:property value="#status.count"/>','imgLoadingdelus<s:property value="#status.count"/>','linkdelus<s:property value="#status.count"/>',this);return false;" />
				<img src="images/loading.gif"
					id="imgLoadingdelus<s:property value="#status.count"/>"
					alt="loading ..." style="display: none;"></td>

			</tr>

		</s:iterator>
	</tbody>

</table>
</span></div>


<script>
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
	var region = getParameterByName('rg');
	(function($) {
		$.fn.html5jTabs = function(options) {
			return this.each(function(index, value) {
				var obj = $(this), objFirst = obj.eq(index), objNotFirst = obj
						.not(objFirst);
		if(region=='EU' || region=='')
		{	
				$("#" + objNotFirst.attr("data-toggle")).hide();
				$(this).eq(index).addClass("active");
		}
		else
		{
			$("#" + objFirst.attr("data-toggle")).hide();
			$(this).next().eq(index).addClass("active");	
		}
				obj.click(function(evt) {

					toggler = "#" + obj.attr("data-toggle");
					togglerRest = $(toggler).parent().find("span");

					togglerRest.hide().removeClass("active");
					$(toggler).show().addClass("active");

					$(this).parent("div").find("a").removeClass("active");
					$(this).addClass("active");
					return false;
				});
			});
		};
	}(jQuery));
	

	$(function() {
		$(".tabs a").html5jTabs();
	});

	
</script></div>
</div>
</center>
<div id="nextSequence" style="padding: 1px; border: 1px solid black;"
	align="center">
<div class="project_title"
	style="color: white; font-size: 20px; margin-bottom: 15px;"></div>
<form id="frmUploadSequence" name="frmUploadSequence"
	action="uploadNextSequence_ex.do" enctype="multipart/form-data"
	method="post"><input type="file" id="uploadFile"
	name="uploadFile" /> <input name="location" id="location"
	type="hidden" value="" /> <input name="replaceSequence" value="no"
	id="replaceSequence" type="hidden" /> <input id="btnUploadSequence"
	type="submit" value="Upload" class="button" /> <img id="imgLoader"
	src="images/loading.gif" alt="Uploading.." /> <br>
<!-- <div class="progress">--> <!--        <div class="bar"></div >--> <!--        <div class="percent">0%</div >-->
<!--    </div>--> <!--    -->
<div id="status"></div>

</form>
<br>
<input id="closeForm" type="button" value="Close" class="button"
	style="margin-top: 10px;" /></div>


<script>

	var bar = $('.bar');
	var percent = $('.percent');
	var status = $('#status');
	var options = {
		complete : function(response) {
			$("#imgLoader").hide();

			if (response.responseText == "replace") {
				if (confirm("Sequence already exist, Do you want to replace it?")) {
					$("#replaceSequence").val("yes");
					$("#imgLoader").show();
					$("#frmUploadSequence").submit();
				} else {

				}
			} else {
				$("#replaceSequence").val("no");
				$("#status").html(response.responseText);

			}
		},
		beforeSubmit : function() {
			var filename = document.getElementById("uploadFile").value;
			var fname = filename.substring(filename.lastIndexOf('.') - 4)
					.toLowerCase();

			ext = filename.substring(filename.lastIndexOf('.') + 1)
					.toLowerCase();
			if (filename == "") {
				alert("Please Browse sequence");
				return false;
			}
			if (ext != "zip") {
				alert("You can upload only zip File ");
				return false;
			}

			if (fname.length != 8) {
				alert("Invalid Sequence number");
				return false;
			}
			if (isNaN(fname.substring(0, 4))) {
				alert("Invalid Sequence number");
				return false;
			}
			$("#imgLoader").show();

		},
		uploadProgress : function(event, position, total, percentComplete) {
			var percentVal = percentComplete + '%';
			bar.width(percentVal);
			percent.html(percentVal);
			//console.log(percentVal, position, total);
	},
	success : function() {
		var percentVal = '100%';
		bar.width(percentVal);
		percent.html(percentVal);
		$("#imgLoader").hide();
	},
	error : function() {
		alert("ERROR: unable to upload file");
	}
	};

	$("#frmUploadSequence").ajaxForm(options);
</script>
</body>
</html>