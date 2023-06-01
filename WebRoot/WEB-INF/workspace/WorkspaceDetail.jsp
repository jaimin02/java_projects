<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WorkSpace Detail</title>
<link rel="stylesheet"
	href='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.css'>
<script
	src="<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.ui.all.js"></script>

<style>
.filterinput {
	width: 100%;
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
}
</style>
<script>
(function ($) {
	  // custom css expression for a case-insensitive contains()
	  jQuery.expr[':'].Contains = function(a,i,m){
	      return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
	  };

	  function listFilter(list) { // header is any element, list is an unordered list
		    // create and add the filter form to the header
		   var input = $("#autotext");
		    $(input).change( function () {
		        var filter = $(this).val();
		        if(filter) {
		          // this finds all links in a list that contain the input,
		          // and hide the ones not containing the input while showing the ones that do
			        $(list).find("a:not(:Contains(" + filter + "))").parent().slideUp(100);
			        $(list).find("a:Contains(" + filter + ")").parent().slideDown(100);
		        } else {
		          $(list).find("li").slideDown();
		        }
		        return false;
		      })
		    .keyup( function () {
		        // fire the above change event after every letter
		        $(this).change();
		    });
		  }
	  //ondomready
	  $(function () {
		  var totallist = $("#list");
	    listFilter(totallist);
	  });
	}(jQuery));

</script>

</head>
<body>
<input id="autotext" class="filterinput" type="text" style="width:196px;"></input>
<ul id="list">
	<s:iterator value="getWorkSpaceDetail">
		<li><a style="text-decoration: none; color: #FFFFFF;"
			href="WorkspaceOpen.do?ws_id=${workSpaceId}"><s:property
			id="workSpaceId" value="workSpaceDesc" /></a></li>
	</s:iterator>
</ul>

</body>
</html>