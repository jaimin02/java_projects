<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head theme="ajax" />
<script type="text/javascript">
	function getUsrAuthors()
	{
		
		$.ajax(
				{	url: 'SelectUsers_ex.do?usrType=' + document.getElementById('usrType').value,
					beforeSend: function()
					{ 
						if (document.getElementById('usrType').value=='none')
						{
							$('#usrList').html("<b>Please select User Type</b>");
							return false;
						}
							
					},
			  		success: function(data) 
			  		{
			    		$('#usrList').html(data);
			    	}	  		
				});
	}
	function selUsrColumn(str,colNo,noOfCols)
	{
		//alert(colNo);
		//alert(noOfCols);
		var cnt=document.getElementById(str).value;
		var sel=document.getElementById(str + "-" + colNo).checked;
		for (var i=0;i<cnt;i++)
		{		
			if (i%noOfCols==colNo)
			{
				//alert(i);
				//alert(i%noOfCols);			
				document.getElementById(str + '_' + (i+1)).checked=sel;
			}
		}
	}
	function selAlUsr(str)
	{
		var cnt=document.getElementById(str).value;
		var sel=document.getElementById(str).checked;
		for (var i=1;i<=cnt;i++)
		{
			document.getElementById(str + '_' + i).checked=sel;
		}
	}
	function showUsers()
	{
		$.ajax(
				{	url: 'ShowUsers_ex.do?',
					
					beforeSend: function()
					{ 
						$("#showUsersDiv").html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
					},
					success: function(data) 
			  		{
						$("#showUsersDiv").html(data);	    		
					}	   		
				});
	}
	</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center"><img
	src="images/Header_Images/Project/Create_Workspace.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"></img>
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><br>
<s:form name="frmAddUsers">
	<table align="center" width="100%" cellspacing="0">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 6px;">Select User
			Type&nbsp;</td>
			<td align="left"><select name="usrType" id="usrType"
				style="min-width: 0px;" onchange="getUsrAuthors();">
				<option value="none">Select Type</option>
				<s:iterator value="userTypes">
					<option value="<s:property value="userTypeCode"/>"><s:property
						value="userTypeName" /></option>
				</s:iterator>
			</select></td>
		</tr>
	</table>
	<div id="usrList" align="center"></div>
</s:form></div>
</div>
</div>
</body>
</html>