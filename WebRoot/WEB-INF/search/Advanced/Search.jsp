<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head theme="ajax" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/jquery/CrossSelect/jquery.crossSelect.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/CrossSelect/jQuery.crossSelect-0.5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>

<script type="text/javascript">
		function test()
		{
			document.getElementById('AttrDtlDiv').style.display = 'none';
			document.getElementById('aUpdateAttr').style.display = 'none';
		}
		</script>
<SCRIPT type="text/javascript">
		var selectId;
			function btnBlockStart()
			{
				if(selectId != null)
				{
					if(document.getElementById('attrSetValue'+selectId).value != null)
						document.getElementById('attrSetValue'+selectId).value= '';
				}
				var queryString = document.getElementById("queryStringDiv").innerHTML;
				//alert("123"+queryString+"132");
				var lastString = queryString.substring(queryString.lastIndexOf("<label"));
				//alert("123"+lastString+"123");
				
				if((lastString.match("OR")) || (lastString.match("AND")) || (lastString.match(/\(/g)) || (lastString.match("<br>")) || !(queryString) )
				{
					document.getElementById("queryStringDiv").innerHTML += "<label > ( </label>";	
				}
			}
			function btnBlockEnd()
			{

					
				if(selectId != null)
				{
					if(document.getElementById('attrSetValue'+selectId).value != null)
						document.getElementById('attrSetValue'+selectId).value= '';
				}
				var queryString = document.getElementById("queryStringDiv").innerHTML;
				//alert("123"+queryString+"132");
				var lastString = queryString.substring(queryString.lastIndexOf("<label"));
				//alert("123"+lastString+"123");
				
				if(!((lastString.match("OR")) || (lastString.match("AND"))  || !(queryString) ))
				{
					document.getElementById("queryStringDiv").innerHTML += "<label > ) </label>";	
				}

				
			}
			function btnOr()
			{
				if(selectId != null)
				{
					if(document.getElementById('attrSetValue'+selectId).value != null)
						document.getElementById('attrSetValue'+selectId).value= '';
				}
				document.getElementById("queryStringDiv").innerHTML += "<label > <b>OR</b> </label>";
			}
			function btnAnd()
			{
				if(selectId != null)
				{
					if(document.getElementById('attrSetValue'+selectId).value != null)
						document.getElementById('attrSetValue'+selectId).value= '';
				}
				document.getElementById("queryStringDiv").innerHTML += "<label > <b>AND</b> </label>";
			}
			function btnNewLine()
			{
				if(selectId != null)
				{
					if(document.getElementById('attrSetValue'+selectId).value != null)
						document.getElementById('attrSetValue'+selectId).value= '';
				}
				document.getElementById("queryStringDiv").innerHTML += "<label > <br> </label>";
			}	
			function btnEndQuery()
			{
				var queryString = document.getElementById("queryStringDiv").innerHTML;
				var lastString = queryString.substring(queryString.lastIndexOf("<label"));
				//alert("123"+lastString+"123");						
				if ((lastString == "<label> <b>AND</b> </label>") || (lastString == "<label> <b>OR</b> </label>"))
				{
					//alert("kam bandh kar");
					var forRemoveString = queryString.substring(0,queryString.lastIndexOf("<label"));
					//alert(forRemoveString);
					document.getElementById("queryStringDiv").innerHTML =forRemoveString; 
						
				}
				var startBracket=-1;
				var endBracket=-1;
				if(queryString.match(/\(/g))
					startBracket = (queryString.match(/\(/g).length);
				if(queryString.match(/\)/g))
					endBracket = (queryString.match(/\)/g).length);

				if(startBracket != endBracket)
				    alert("Invalid query created!");
				else
				    alert("Valid Query created.");
				//alert(queryString);
			}	
			 function btnSubmitQuery()	
			{
				if(selectId != null)
				{
					if(document.getElementById('attrSetValue'+selectId).value != null)
						document.getElementById('attrSetValue'+selectId).value= '';
				}
				var qtstring = document.getElementById("queryStringDiv").innerHTML;
				//alert(qtstring.match("</label>"));
				//boolean pass = qtstring.match("<label id=\"lblAttrValue");
	
				//alert(pass+"<label id=\"lblAttrValue mathchsadfnk nksjdhfkj h");
				if(!qtstring)
					return false;
				/*
			    var index = qtstring.indexOf("<br>");
			        while(index != -1){
			        	qtstring = qtstring.replace("<br>","\n");
			            index = qtstring.indexOf("<br>");
			        }
			        */
			        document.getElementById("queryStringForSend").value = qtstring;
			        $.ajax(
			        		{	
			        			type: "POST",		
			        			url: 'AdvancedSearchQueryResult_ex.do',
			        			data: "queryStringForSend="+qtstring,
				        		beforeSend:function()
				        		{
									//alert(qtstring);
					        	},	
			        	  		success: function(data) 
			        	  		{
				        	  		//alert(data);
				        	  		document.getElementById("resultDiv").innerHTML = data;
			        	    	}	  		
			        		});	
					return true;
			}
			function selectedAttr(attrName,selectedId,attrType)
			{
				
				if(selectId)
				{
					//alert(selectId);
					var removeAttribute = document.getElementById("queryStringDiv").innerHTML;
					var compareString = removeAttribute.substring(removeAttribute.lastIndexOf("<label id=\"lblAttrValue"+selectId+"\">"));
					//alert(compareString);
					if(removeAttribute.substring(removeAttribute.lastIndexOf("<label")) == ("<label id=\"lblAttrValue"+selectId+"\">''</label>"))
					{
						var newQueryString = removeAttribute.substring(0,removeAttribute.lastIndexOf("<label id=\"lblAttrId"+selectId+"`~`\">"));
						//alert(newQueryString);
						document.getElementById("queryStringDiv").innerHTML =newQueryString;
					}
					/*for and or ( ) new line condition*/
					//else 
					//{
						var lastString = removeAttribute.substring(removeAttribute.lastIndexOf("<label"));
						//alert("123"+lastString+"123");						
						if (!((lastString == "<label> <b>AND</b> </label>") || (lastString == "<label> <b>OR</b> </label>") || (lastString == "<label> <br> </label>") || (lastString == "<label> ) </label>") || (lastString == "<label> ( </label>") ))
						{
							//alert("kam bandh kar");
							var forRemoveString = removeAttribute.substring(0,removeAttribute.lastIndexOf(" <label id=\"lblAttrId"+selectId+"`~`\">'"));
							//alert(forRemoveString);
							document.getElementById("queryStringDiv").innerHTML =forRemoveString; 
								
						}
						 
						
					//}
				}
					
				selectId = selectedId;
				
				document.getElementById("queryStringDiv").innerHTML += " <label id=\"lblAttrId"+selectId+"`~`\">'"+attrName+"'</label>";
				document.getElementById("queryStringDiv").innerHTML += " <label id=\"lblAttrType"+selectId+"\">"+document.getElementById(attrType+selectId).options[document.getElementById(attrType+selectId).selectedIndex].value+"</label>";
				document.getElementById("queryStringDiv").innerHTML +=" <label id=\"lblAttrValue"+selectId+"\">''</label>";
				
			}	
					
			function selectedOper(selectedId,attrType)
			{
				selectId = selectedId;
				//alert(document.getElementById(selectId).checked);
				if(document.getElementById(selectId).checked)
				{
					var qtString = document.getElementById("queryStringDiv").innerHTML;
					var oldString = qtString.substring(qtString.lastIndexOf("<label id=\"lblAttrType"));
					//alert(oldString);
					var newString = " <label id=\"lblAttrType"+selectId+"\">"+document.getElementById(attrType+selectId).options[document.getElementById(attrType+selectId).selectedIndex].value+"</label>";
					newString += " <label id=\"lblAttrValue"+selectId+"\">''</label>";
					qtString = qtString.replace(oldString,newString);
					document.getElementById("queryStringDiv").innerHTML = qtString;
				}
			}
			function setingValue(attrValueId,selectedId)
			{
				selectId = selectedId;
				if(document.getElementById(selectId).checked)
				{
					var setingvalue = document.getElementById(attrValueId).value;
					/*
					alert(setingvalue);
					if(setingvalue.match("[a-zA-Z0-9]*'[a-zA-Z0-9]*"))
					{
						setingvalue = setingvalue.replace("'","''");
						alert(setingvalue);
					}
					if(setingvalue.match("[a-zA-Z0-9]*%[a-zA-Z0-9]*"))
					{
						setingvalue = setingvalue.replace("%","/%");
						alert(setingvalue);
					}
					*/
					var qtString = document.getElementById("queryStringDiv").innerHTML;
					var oldString = qtString.substring(qtString.lastIndexOf("<label "));
					//alert(oldString);
					var newString = " <label id=\"lblAttrValue"+selectId+"\">'"+setingvalue+"'</label>";
					qtString = qtString.replace(oldString,newString);
					document.getElementById("queryStringDiv").innerHTML = qtString;
				}
			}
			function selectValue(attrValueId,selectedId)
			{
				selectId = selectedId;
				if(document.getElementById(selectId).checked)
				{
					
					var setingvalue = document.getElementById(attrValueId).value;
					var qtString = document.getElementById("queryStringDiv").innerHTML;
					var oldString = qtString.substring(qtString.lastIndexOf("<label "));
					var newString = " <label id=\"lblAttrValue"+selectId+"\">'"+setingvalue+"'</label>";
					qtString = qtString.replace(oldString,newString);
					document.getElementById("queryStringDiv").innerHTML = qtString;
				}

			}
			function selectDate(attrValueId,selectedId)
			{
				selectId = selectedId;
				if(document.getElementById(selectId).checked)
				{
					var setingvalue = document.getElementById(attrValueId).value;
					var qtString = document.getElementById("queryStringDiv").innerHTML;
					var oldString = qtString.substring(qtString.lastIndexOf("<label "));
					var newString = " <label id=\"lblAttrValue"+selectId+"\">'"+setingvalue+"'</label>";
					qtString = qtString.replace(oldString,newString);
					document.getElementById("queryStringDiv").innerHTML = qtString;
				}
			}
		
			function cancelString()
			{
				var str = document.getElementById("queryStringDiv").innerHTML;
				 str = str.substring(0,str.lastIndexOf("<label"));
				 document.getElementById("queryStringDiv").innerHTML = str;
			}
		</SCRIPT>
</head>
<body>
<br>

<div align="center"><img
	src="images/Header_Images/Search/Advanced_Attribute_Search.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">
<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>

<div><s:url id="ShowAttrUrl" action="AdvancedSearchShowAttr_ex"></s:url>
<s:if test="userSavedAttrList.size() == 0 ">
	<s:div id="LinksDiv" theme="ajax" autoStart="true"
		href="%{ShowAttrUrl}" executeScripts="true"
		indicator="imgLoadingforAttrbute" separateScripts="true"></s:div>
	<img src="images/loading.gif" id="imgLoadingforAttrbute"
		style="display: none;">
</s:if> <s:else>
	<div align="center" style="width: 95%">
	<div id="AttrSelectionDiv"></div>
	<s:url id="ShowAttrDtlUrl" action="AdvancedSearchShowAttrDtl_ex"></s:url>
	<s:a href="%{ShowAttrUrl}" title="Update Attribute" id="aUpdateAttr"
		targets="AttrSelectionDiv" theme="ajax" executeScripts="true"
		onclick="test();">Update Selected Attribute</s:a> <s:div
		indicator="imgLoadingforQuery" id="AttrDtlDiv" theme="ajax"
		autoStart="true" href="%{ShowAttrDtlUrl}" executeScripts="true"
		separateScripts="true"></s:div> <img src="images/loading.gif"
		id="imgLoadingforQuery" style="display: none;"></div>
</s:else></div>
</div>
</div>

</body>
</html>