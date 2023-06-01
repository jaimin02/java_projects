<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
$(document).ready(function() {
		debugger;
		var DashboardURL  = document.getElementById('message').innerHTML	;
		window.open(DashboardURL, '_self');
		window.close();
	});
	</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
   <div style="visibility: hidden;" id="message">${dashBoardURL }</div>

</body>
</html>