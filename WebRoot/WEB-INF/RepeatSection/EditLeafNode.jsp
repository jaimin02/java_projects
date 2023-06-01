<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">
/****************************************************************/
/*global variables used throout the page*/
/****************************************************************/
	var fixedPart = '<%=request.getParameter("fixedPart")%>';
	var rest = fixedPart.substring(0, fixedPart.lastIndexOf("-"));
	var fileName = '<%=request.getAttribute("fileName")%>';
	var fileExt = '<%=request.getAttribute("fileExt")%>';
	var editNodeId = '<%=request.getParameter("editNodeId")%>';
	var fileNameMaxSize=64;
	var appMode;

	
	
	
/****************************************************************/	
</script>
<style type="text/css">
.valid {
	color: blue;
	font-size: 12px;
}

.invalid {
	color: red;
	font-size: 12px;
}
</style>

<!-- -------needed for ajax call---------------------------------- -->

</head>
<body>
<div class="errordiv" align="center"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<SCRIPT type="text/javascript">

	
function trim(obj)
{
   	obj.value = obj.value.replace( /^\s+/g, "" );// strip leading
	obj.value = obj.value.replace( /\s+$/g, "" );// strip trailing
	return obj;
}
function check(obj)
{
	obj=trim(obj);
   	var strString = obj.value;
   	var lbl_nodeName = '<s:property value="lbl_nodeName"/>';
   	 if(strString.indexOf("\\")!=-1){alert("Invalid "+lbl_nodeName+" name. \\ is not allowed");return false;}
	    
	    var strInvalidChars = "/\^$#:~%@&*`!;'\"";
    	var strChar;
   	  	var blnResult = true;
   	  	 
	if (strString.length== 0) {alert ("Please enter "+lbl_nodeName+"."); return false;}
      	for (i = 0; i < strString.length && blnResult == true; i++)
     	 {
 			strChar = strString.charAt(i);
			 if (strInvalidChars.indexOf(strChar)!= -1)
			 {
      			blnResult = false;
      			if(strChar == ' ')
      			{
      				alert("Invalid "+lbl_nodeName+" name." + " 'space' is not allowed." );
      			}
      			else
      			{
       				alert("Invalid "+lbl_nodeName+" name." + " " + strChar + "  is not allowed." );
       			}
 			}
      	}
		return blnResult;
 }
	function validate()
	{	
		//debugger;
		if(document.getElementById('VarPart').value==''){
			alert("Please enter variable part.");
			document.getElementById('VarPart').focus();
			document.editLeafNodeForm.VarPart.style.backgroundColor="#FFE6F7"; 
			return false;
		}
		if (appMode=='0002')
		{
			str=document.getElementById("status").innerHTML;			
			if (str=="")
				return false;
		}	
		if(check(document.forms['editLeafNodeForm'].nodeDisplayName) == false)
		{			
			document.forms['editLeafNodeForm'].nodeDisplayName.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editLeafNodeForm'].nodeDisplayName.focus();
	     	return false;
	    }
		if(document.forms['editLeafNodeForm'].fileName.value == "")
		{
			alert("Please enter"+'<s:property value="lbl_folderName"/>'+" name.");
			document.forms['editLeafNodeForm'].fileName.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editLeafNodeForm'].fileName.focus();
	     	return false;
		}			
		if(appMode=='0001' && document.forms['editLeafNodeForm'].fileName.value.match(/[^a-zA-Z0-9-]+/))
		{
			alert("Only alphabets, digits and '-' are allowed.");
			document.forms['editLeafNodeForm'].fileName.style.backgroundColor="#FFE6F7"; 
	     	document.forms['editLeafNodeForm'].fileName.focus();
	     	return false;
		}						
		/********************************************************************/
		/*if the filename is not available then dont allow the user to save*/		
		if (appMode=='0002')
		{
			str=document.getElementById("status").innerHTML;		
			//alert(str);
	   		if (str.search('not available')!=-1)
				return false;
		}
		/**************************************************************/		
		/*remove the extension from the filename*/
		if (appMode=='0002')
		{
			str=document.forms['editLeafNodeForm'].fileName.value;
			str=str.substring(0,str.lastIndexOf('.'));
			document.forms['editLeafNodeForm'].fileName.value=str;
		}
		/**************************************************************/
		return true;
	}	

	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return false;
  		} 
	} 

	document.onkeypress = detectReturnKey;
	
	/*this function generates the file name according to user input*/
	/* function makeFileName()
	{	
		var str=document.editLeafNodeForm.varPart.value;
		 if(str.charAt(0)=='-'){
			str="";
		}		
		 else{
			 str=str.replace(/[^a-z0-9-]/,"");
		}			
		document.editLeafNodeForm.varPart.value=str;
		if (document.editLeafNodeForm.varPart.value=='')
			document.editLeafNodeForm.fileName.value=fixedPart + document.editLeafNodeForm.varPart.value + fileExt;
		else
			document.editLeafNodeForm.fileName.value=fixedPart + "-" + document.editLeafNodeForm.varPart.value + fileExt;
		document.getElementById("noc").innerHTML="(" + (fileNameMaxSize-document.editLeafNodeForm.fileName.value.length) + " characters)";
		
		if (fileNameMaxSize-document.editLeafNodeForm.fileName.value.length<=0)
		{
			document.getElementById("noc").className="invalid";					
		}
		else
		{
			document.getElementById("noc").className="valid";			
		}
	} */
	function makeFileName()
	{	
		debugger;
		//alert(rest);
		var str=document.editLeafNodeForm.varPart.value;
		 if(str.charAt(0)=='-'){
			str="";
		}		
		 else{
			 str=str.replace(/[^a-z0-9-]/,"");
		}			
		document.editLeafNodeForm.varPart.value=str;
		if (document.editLeafNodeForm.varPart.value==''){
			if(str==''){
				//rest = rest.substring(0, rest.lastIndexOf("-"));
				document.editLeafNodeForm.fileName.value=fixedPart + document.editLeafNodeForm.varPart.value + fileExt;
			}else{
			document.editLeafNodeForm.fileName.value=fixedPart + "-"+ document.editLeafNodeForm.varPart.value + fileExt;}
		}
		else
			document.editLeafNodeForm.fileName.value=fixedPart + "-"+ document.editLeafNodeForm.varPart.value + fileExt;
		document.getElementById("noc").innerHTML="(" + (fileNameMaxSize-document.editLeafNodeForm.fileName.value.length) + " characters)";
		
		if (fileNameMaxSize-document.editLeafNodeForm.fileName.value.length<=0)
		{
			document.getElementById("noc").className="invalid";					
		}
		else
		{
			document.getElementById("noc").className="valid";			
		}
	}
	/************************************************/
	/*check the filename in the database whether already present*/
	function checkStatus()
	{		
		document.editLeafNodeForm.varPart.readOnly=true;
		$.ajax(
		{			
			url: 'CheckRepeatNodes_b.do?fileName=' + document.forms['editLeafNodeForm'].fileName.value + '&editNodeId=' + editNodeId,
	  		success: function(data) 
	  		{
	    		$('#status').html(data); 
	    		/*check the result returned by the action*/
	    		var str=document.getElementById("status").innerHTML;
	    		if (str.search('not available')==-1)
					document.getElementById("status").className="valid";
				else
					document.getElementById("status").className="invalid";
				
				document.getElementById("status").style.visibility="visible";
				
				document.editLeafNodeForm.varPart.readOnly=false;
			}	  		
		});		
		return false;
	}
</SCRIPT>

<div class="container-fluid">
<div class="col-md-12">
<br>
<!-- <div align="center" class="maindiv"><br>
<div align="center" class="headercls"> -->
<div class="boxborder" style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="all_title"><center><b> Edit <s:property value="lbl_nodeName"/> </b></center></div>


<s:form action="UpdateSection" method="post" name="editLeafNodeForm">

	<table style="align:center; width:95%;">

		<tr>
			<td colspan="2" align="center">
			<div id="status" class="valid" style="visibility: hidden;">not
			available</div>
			</td>
		</tr>

		<tr>
			<td class="title" align="right" width="40%" style="padding: 2px;padding-right: 19px;"><s:property value="lbl_nodeName"/></td>
			<td align="left"><s:textfield name="nodeDisplayName" size="40"></s:textfield>
			<s:hidden name="attriValues"></s:hidden></td>

		</tr>
		<s:if test="appMode == 'yes'">
			<!-- this means application mode is ectd -->
			<tr>
				<td  class="title" align="right" width="40%" style="padding: 2px;padding-right: 19px;">Particulars 
				</td>
					<td align="left"><s:textfield id="VarPart" name="varPart"
					value="%{#attr.fileName}" onkeyup="makeFileName();" /> <s:hidden
					name="fileExt"></s:hidden></td>
					

			</tr>

			<tr>
				<td class="title" align="right" width="40%" style="padding: 2px;padding-right: 19px;"><s:property value="lbl_folderName"/> Name</td>
				<td align="left"><s:textfield name="fileName" size="40"
					readonly="true"></s:textfield> <script type="text/javascript">
					var str=document.editLeafNodeForm.varPart.value;
						document.editLeafNodeForm.varPart.value=str.substring(str.lastIndexOf('-')+1);  
						fixedPart = fixedPart.replace(/-\d$/, '');
						document.editLeafNodeForm.fileName.value=str + fileExt;
  					</script></td>
			</tr>
			<tr>
				<td></td>
				<td align="left">
				<div id="noc" class="valid" style="font-size: xx-small;">(<script
					type="text/javascript">
	  							document.write(fileNameMaxSize-document.editLeafNodeForm.fileName.value.length);
	  							document.editLeafNodeForm.varPart.maxLength=fileNameMaxSize-document.editLeafNodeForm.fileName.value.length+1;	  							
	  						</script> &nbsp;characters)</div>
				</td>
			</tr>

			<tr>
				<td align="center" colspan="2"><s:submit name="btnCheckStatus"
					value="Check Availability" cssClass="button"
					onclick="return checkStatus();"></s:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:submit name="submitBtn"
					value="Save" cssClass="button" onclick="return validate();" /></td>
			</tr>
			<script type="text/javascript">
  					appMode="0002";
  				</script>
		</s:if>

		<s:else>
			<!-- this means application mode is dms -->
			<script type="text/javascript">
  					appMode="0001";
  				</script>
			<tr>
				<td class="title" align="left"><s:property value="lbl_folderName"/> Name</td>
				<td align="left"><s:textfield name="fileName" size="40"></s:textfield>
				<s:hidden name="fileExt"></s:hidden></td>

			</tr>
			<tr>
				<td align="center" colspan="2"><s:submit name="submitBtn"
					value="Save" cssClass="button" onclick="return validate();" /></td>
			</tr>
		</s:else>
	</table>
	<s:hidden name="editNodeId">
	</s:hidden>
</s:form></div>
</div></div>
<script>

if(fixedPart=="common-cover")
{
		//alert("comnmon");
		//alert(document.forms['editLeafNodeForm'].fileName.value);

		document.forms['editLeafNodeForm'].fileName.readOnly=false;
		document.forms['editLeafNodeForm'].varPart.value="";
		document.forms['editLeafNodeForm'].fileName.value=document.forms['editLeafNodeForm'].fileName.value+""+document.forms['editLeafNodeForm'].varPart.value;
		document.forms['editLeafNodeForm'].fileName.value=fileName+fileExt;
		//alert(document.forms['editLeafNodeForm'].fileName.value);
}
</script>
</body>


</html>
