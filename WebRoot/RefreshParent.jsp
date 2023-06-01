<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<html>
<body onload="win();">

<SCRIPT type="text/javascript">
			
			function win()
			{
				 window.opener.parent.history.go(0);
				 self.close(); 
				 return true;
			}
		
		</SCRIPT>

</body>

</html>

