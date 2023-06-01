<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
<script type="text/javascript">
	$(document).ready(function() { 
		$('#attrDetails').change(function(){
			var attrId = document.getElementById('attrDetails').value;
			$.ajax(
				{			
					url: 'GetAttrValue.do?attrId='+ attrId,
					beforeSend: function()
					{},
					success: function(data) 
					{
				   		$('#attrValueDiv').html(data);    		
					}	  		
				}
			);
		});

		
	});

	function getResult(){
		
		var attrId = document.getElementById('attrDetails').value;
		//debugger;
		var attrVal = document.getElementsByName('attrVal')[0].value;

		if(attrVal=="")
		{
				alert("Please enter attribute value to search...");
				document.getElementsByName('attrVal')[0].focus();
				return false;
		}
		
		$.ajax(
			{			
				url: 'ShowAttributeSearchResult.do?attrId='+ attrId+'&attrValue='+attrVal,
				beforeSend: function()
				{
					$("#process").show();
				},
				success: function(data) 
				{
					$("#process").hide();
					document.getElementById('ShowAttributeSearchResultDiv').innerHTML=data;		
				}	  		
			}
		);
	}
</script>



<script language="javascript">

	function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none';
			else
				document.getElementById(str).style.display = 'inline';
		}	

	
	
</script>

<STYLE>
.cstyle {
	cursor: hand;
}

.initial {
	background-color: #DDDDDD;
	color: #000000
}

.normal {
	background-color: #F2F2F2
}

.highlight {
	background-color: #CBCBE4
}
</style>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Search/Attribute_Search.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><br>
<table width="100%">
	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Attribute Name</td>
		<td align="left"><s:select list="attrDetail" name="attrId"
			listKey="attrId" listValue="attrName" id="attrDetails">
		</s:select></td>
	</tr>


	<tr>
		<td class="title" align="right" width="25%"
			style="padding: 2px; padding-right: 8px;">Attribute Value</td>
		<td colspan="5" width="75%" align="left">
		<div id="attrValueDiv"></div>
		</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td></td>
		<td align="left"><input type="button" class="button"
			value="Perform Search" id="performSearch" onclick="getResult();" />
		</td>
	</tr>

</table>

<br>
<center>
<div id="process" style="display:none;">Searching...</div>
</center>
<div id="ShowAttributeSearchResultDiv"></div>

</div>
</div>
</div>

</body>
</html>
