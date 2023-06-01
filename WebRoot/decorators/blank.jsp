<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page"
	prefix="pages"%>
<%@ taglib prefix="knet" uri="/knet-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/Common/favicon.ico" />
<decorator:head />
<title><knet:title /></title>
<link href="<%= request.getContextPath() %>/decorators/maincss/main.css"
	rel="stylesheet" type="text/css">
</head>
<body>

<decorator:body />
</body>
</html>