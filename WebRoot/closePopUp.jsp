<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<html>
<body onload="win();">

<SCRIPT type="text/javascript">
			
			function win()
			{
				opener.history.go(0);
				window.opener.location.reload();
				self.close(); 
				
				return true;
			}
		
		</SCRIPT>

</body>

</html>

