<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<s:head />


<SCRIPT type="text/javascript">
		
		function showtab(radiovalue)
		{
			if(radiovalue.value == 'eu')
			{
				document.getElementById('detailBoxEu').style.display='';
				document.getElementById('detailBoxUS').style.display='none'
							
			}
			else
			{
				document.getElementById('detailBoxEu').style.display='none';
				document.getElementById('detailBoxUS').style.display=''
							
			}
		}
		
		function setWidth()
    	{
   			window.resizeBy(200,340);
   			document.all.titleBox2.style.display='inline';
   			document.all.titleBox1.style.display='none';
   			window.moveTo(100,50);
    	}
    	
    	function showDetailBoxEurope()
    	{
    		document.all.detailBoxEu.style.display='inline';
    		document.all.titleBox2.style.display='inline';
    		document.all.titleBox1.style.display='none';
    		return true;
    	}
    	
    	function showDetailBoxUS()
    	{
    		document.all.detailBoxUS.style.display='inline';
    		document.all.titleBox2.style.display='none';
    		document.all.titleBox1.style.display='inline';
    		return true;
    	}
    	
    	function hideDetailBox()
    	{
    		document.all.detailBox.style.display='none';
    		document.all.titleBox2.style.display='none';
    		document.all.titleBox1.style.display='inline';
	    	window.resizeBy(0,-340);
	    	window.moveTo(100,200);
    		return true;
    	}
    	
    	function showReport()
    	{
    		var wsId = document.publishAttrForm.workSpaceId.value;
    		var status = "New";
    		var opvalue = "S";
    		
    		str="docsummaryTree.do?workSpaceId="+wsId+"&opvalue="+opvalue+"&status="+status;
    		win3=window.open(str,'ThisWindow','height=650,width=800,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,titlebar=no');	
			win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(650/2));
			return true;
    	}  	
    	
    	function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none'
			else
				document.getElementById(str).style.display = 'inline'
		}	
		
		function valButton(btn) 
		{
			    var cnt = -1;
			    for (var i=btn.length-1; i > -1; i--) {
			        if (btn[i].checked) {cnt = i; i = -1;}
			    }
			    if (cnt > -1) return btn[cnt].value;
			    else return null;
		}
		
		function checklength()
		{
			if(document.publishAttrForm.applicationNumber.value.length < 6)
		    {
		    	alert(" Please Enter Atleast six digit Application Number..");
		    	document.publishAttrForm.applicationNumber.style.backgroundColor="#FFE6F7"; 
		    	document.publishAttrForm.applicationNumber.focus();
		    	return false;
		    }
		}
		
		function validate()
		{
	        var btn = valButton(publishAttrForm.submissionFlag);
			
			if(document.publishAttrForm.applicationNumber.value=="")
			{
				alert("Please specify Application Number..");
				document.publishAttrForm.applicationNumber.style.backgroundColor="#FFE6F7"; 
		    	document.publishAttrForm.applicationNumber.focus();
		    	return false;
		    }
		    else if(document.publishAttrForm.applicationNumber.value.length < 6)
		    {
		    	alert(" Please Enter six digit Application Number..");
		    	document.publishAttrForm.applicationNumber.style.backgroundColor="#FFE6F7"; 
		    	document.publishAttrForm.applicationNumber.focus();
		    	return false;
		    }
		    else if(document.publishAttrForm.agencyName.value==-1)
		    {
		    	alert(" Please Select Agency Name..");
		    	document.publishAttrForm.agencyName.style.backgroundColor="#FFE6F7"; 
		    	document.publishAttrForm.agencyName.focus();
		    	return false;
		    }
		    
		    else if(document.getElementById("seqId").style.visibility=='visible')
		    {
			    if(document.publishAttrForm.relatedSeqNumber.value=="")
				{
					alert("Please specify Related Sequence Number..");
					document.publishAttrForm.relatedSeqNumber.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.relatedSeqNumber.focus();
			    	return false;
			    }
		    }
		    else if (btn == null) 
			{
				alert('No radio button selected..');
				return false;
			}
			
		
			else if(btn == 'us')
			{
				
				if(document.publishAttrForm.prodName.value=='')
				{
					alert("Please specify Product Name..");
					document.publishAttrForm.prodName.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.prodName.focus();
			    	return false;
				
				}
				else if(document.publishAttrForm.companyName.value=='')
				{
					alert("Please specify Company Name..");
					document.publishAttrForm.companyName.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.companyName.focus();
			    	return false;
				
				}
				else if(isBackDated())
				{
					alert('Submission date should not be less than current date..');
					document.publishAttrForm.dos.style.backgroundColor="#FFE6F7"; 
					return false;
				}
				else if(document.publishAttrForm.productType.value == -1)
				{
					alert("Please Select Product Type..");
					document.publishAttrForm.productType.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.productType.focus();
			    	return false;
				
				}
				else if(document.publishAttrForm.applicationType.value==-1)
				{
					alert("Please Select Application Type..");
					document.publishAttrForm.applicationType.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.applicationType.focus();
			    	return false;
				
				}
				else if(document.publishAttrForm.submissionType_us.value==-1){
					alert("Please Select Submission Type..");
					document.publishAttrForm.submissionType_us.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.submissionType_us.focus();
			    	return false;
				
				}
			
				
			}
			else if(btn=='eu')
			{
		 	if(document.publishAttrForm.countryId.value==-1)
				{
					alert("Please specify Country Name..");
					document.publishAttrForm.countryId.style.backgroundColor="#FFE6F7"; 
			    	document.publishAttrForm.countryId.focus();
		    		return false;
		    	}
		    }
			    
		}
		
		function isBackDated()
		{
			var subDate = document.publishAttrForm.dos.value;
			
			var mydate= new Date()
			var yr=mydate.getFullYear();
			var mnth=mydate.getMonth()+1;
			var date=mydate.getDate();
			
			var arr = subDate.split("/");
			if(arr[0] < yr)
			{
				return true;
			}
			else if(arr[0] == yr)
			{
				if(arr[1] < mnth)
				{
					return true;
				}
				else if(arr[1] == mnth)
				{
					if(arr[2] < date)
					{
						return true;
					}
				}
			}
			
			return false;
		
		}
		
		function  textBoxEnable()
		{
			if(document.publishAttrForm.submissionType_us.value !='original-application')
				document.getElementById("seqId").style.visibility='visible';  
			else
				document.getElementById("seqId").style.visibility='hidden';   
		}
		  
		function detectReturnKey(evt) 
		{ 
			if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
			{
				return document.publishAttrForm.submitBtn.onclick();
			} 
		} 

		document.onkeypress = detectReturnKey;
    </script>

<SCRIPT language="javascript" TYPE="text/javascript"
	src="<%=request.getContextPath()%>/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="<%=request.getContextPath()%>/js/CalendarPopup.js"></SCRIPT>


</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<div class="titlediv">Publish Workspace</div>
<div align="center"><s:form action="WorkspacePublish"
	method="post" enctype="multipart/form-data" name="publishAttrForm">

	<table class="channelcontent" border="0" cellpadding="2" width="95%"
		bordercolor="#EBEBEB">

		<tr class="headercls" onclick="hide('ProjectDetailBox')">
			<td width="95%">Project Detail</td>
			<td width="5%" align="center"><img
				src="<%=request.getContextPath()%>/images/Darrow.gif"></td>
		</tr>


	</table>
	<br>
	<div id="ProjectDetailBox" style="display: none">
	<table cellpadding="2" width="95%"
		style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
		<tr>
			<td width="30%" class="title"><b>Project name:</b></td>
			<td width="70%"><font color="#c00000"><b>${project_name}</b></font>
			</td>
		</tr>

		<tr>
			<td width="30%" class="title"><b>Project Type:</b></td>
			<td width="70%"><font color="#c00000"><b>${project_type}</b></font>
			</td>
		</tr>

		<tr>
			<td width="30%" class="title"><b>Client Name:</b></td>
			<td width="70%"><font color="#c00000"><b>${client_name}</b></font>
			</td>
		</tr>

	</table>
	</div>

	<div id='titleBox1' style="display: none">
	<table class="channelcontent" width="95%">
		<tr>
			<td class="Lable">
			<center><u><b>US-Regional</b></u></center>
			</td>
		</tr>
	</table>
	</div>

	<div id='titleBox2' style="display: none">
	<table class="channelcontent" width="95%">
		<tr>
			<td class="Lable">
			<center><u><b>EU-Regional</b></u></center>
			</td>
		</tr>
	</table>
	</div>

	<div id="GenericdetailBox">
	<table cellpadding="2" width="95%"
		style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">

		<tr class="headercls">
			<td width="30%">Field</td>
			<td width="70%">Entry</td>
		</tr>

		<s:if test="lastPublishedVersion != '-999'">

			<tr>
				<td width="30%" class="title"><b>Application Number</b></td>
				<td width="70%"><s:textfield name="applicationNumber"
					value="%{applNo}" readonly="true"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Agency Name</b></td>
				<td width="70%"><SELECT name="agencyName">
					<option value="${agency}">${agency}</option>
				</SELECT></td>
			</tr>

			<tr>
				<td class="title"><input type="radio" name="submissionFlag"
					value="eu" onclick="showtab(this);"
					<s:if test="UsSubmission == true">disabled="disabled"</s:if>
					<s:else>checked="checked"</s:else> /> <b>Europe Submission</b></td>
			</tr>
			<tr>
				<td class="title"><input type="radio" name="submissionFlag"
					value="us" onclick="showtab(this);"
					<s:if test="UsSubmission == true">checked="checked"</s:if>
					<s:else>disabled="disabled"</s:else> /> <b>US Submission</b></td>

			</tr>
		</s:if>
		<s:else>

			<tr>
				<td width="30%" class="title"><b>Application Number</b></td>
				<td width="70%"><s:textfield name="applicationNumber"
					value="%{applNo}" onchange="checklength();"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Agency Name</b></td>
				<td width="70%"><s:select list="getAgencyDetail"
					name="agencyName" headerKey="-1" headerValue="Select Agency Name"
					listKey="agencyName" listValue="agencyName">
				</s:select></td>
			</tr>

			<tr>
				<td class="title"><input type="radio" name="submissionFlag"
					value="eu" onclick="showtab(this);" /> <b>Europe Submission</b></td>
			</tr>
			<tr>
				<td class="title"><input type="radio" name="submissionFlag"
					value="us" onclick="showtab(this);" /> <b>US Submission</b></td>

			</tr>
		</s:else>


	</table>
	</div>

	<div id="detailBoxEu" style="display: none;">
	<table cellpadding="2" width="95%"
		style="border-top: 1px solid #669; border-left: 1px solid #669; border-right: 1px solid #669; border-bottom: 1px solid #669;">
		<tr>
			<td align="center" colspan="2"><b><u><font
				color="#c00000">Specify following detail for eu-Regional</font></u></b></td>
		</tr>
		<tr></tr>
		<tr class="headercls">
			<td width="30%">Field</td>
			<td width="70%">Entry</td>
		</tr>
		<tr>
			<td class="title" width="30%"><b>Country</b></td>
			<td width="70%"><s:select list="getCountryDetail"
				name="countryId" headerKey="-1" headerValue="Select Country Name"
				listKey="countryCode" listValue="countryName">
			</s:select></td>

		</tr>
		<tr>
			<td width="30%" class="title"><b>Applicant</b></td>
			<td width="70%"><s:textfield name="applicant"></s:textfield></td>
		</tr>


		<tr>
			<td width="30%" class="title"><b>atc</b></td>
			<td width="70%"><s:textfield name="atc"></s:textfield></td>
		</tr>

		<tr>
			<td width="30%" class="title"><b>procedureType</b></td>
			<td width="70%"><SELECT name="procedureType">

				<option value="centralised" selected="selected">centralised</option>
				<option value="national">national</option>
				<option value="mutual-recognition">mutual-recognition</option>
				<option value="decentralised">decentralised</option>
			</SELECT></td>
		</tr>
		<tr>
			<td width="30%" class="title"><b>submissionType</b></td>
			<td width="70%"><SELECT name="submissionType_eu">
				<option value="initial-maa" selected="selected">initial-maa</option>
				<option value="supplemental-info">supplemental-info</option>
				<option value="fum">fum</option>
				<option value="specific-obligation">specific-obligation</option>
				<option value="var-type1a">var-type1a</option>
				<option value="var-type1b">var-type1b</option>
				<option value="var-type2">var-type2</option>
				<option value="extension">extension</option>
				<option value="psur">psur</option>
				<option value="renewal">renewal</option>
				<option value="asmf">asmf</option>
				<option value="referral">referral</option>
				<option value="annual-reassessment">annual-reassessment</option>
				<option value="usr">usr</option>
				<option value="article-58">article-58</option>
				<option value="notification-61-3">notification-61-3</option>
				<option value="baseline">baseline</option>
			</SELECT></td>
		</tr>
		<tr>
			<td width="30%" class="title"><b>Invented Name</b></td>
			<td width="70%"><s:textfield name="inventedName"></s:textfield>
			</td>
		</tr>
		<tr>
			<td width="30%" class="title"><b>inn</b></td>
			<td width="70%"><s:textfield name="inn"></s:textfield></td>
		</tr>
		<tr>
			<td width="30%" class="title"><b>Submission Description</b></td>
			<td width="70%"><s:textfield name="submissionDescription"></s:textfield>
			</td>
		</tr>

	</table>
	</div>

	<div id="detailBoxUS" style="display: none;">
	<table cellpadding="2" width="95%"
		style="border-top: 1px solid #ddf; border-left: 1px solid #ddf; border-right: 1px solid #669; border-bottom: 1px solid #669;">
		<tr>
			<td align="center" colspan="2"><b><u><font
				color="#c00000">Specify following Admin detail for
			us-Regional</font></u></b></td>
		</tr>


		<s:if test="lastPublishedVersion != '-999'">

			<tr class="headercls">
				<td colspan="2">
				<center>Applicant Information</center>
				</td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Company Name</b></td>
				<td width="70%"><s:textfield name="companyName"
					value="%{companyName}" readonly="true"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Date Of Submission</b></td>
				<td width="70%"><input type="text" name="dos" id="dos"
					ReadOnly="readonly" size="12"
					value="<s:date name="now" format="yyyy/MM/dd"/>"> &nbsp;<IMG
					onclick="popUpCalendar(this,dos,'yyyy/mm/dd');"
					src="<%=request.getContextPath() %>/images/Calendar.png"
					align="middle"></td>
			</tr>


			<tr class="headercls">
				<td colspan="2">
				<center>Product Description</center>
				</td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Product Name</b></td>
				<td width="70%"><s:textfield name="prodName"
					value="%{prodName}" readonly="true"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Product Type</b></td>
				<td width="70%"><select name="productType">
					<option value="${prodType}">${prodType}</option>
				</select></td>
			</tr>

			<tr class="headercls">
				<td colspan="2">
				<center>Application Information</center>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Application Type</b></td>
				<td width="70%"><select name="applicationType">
					<option value="${applicationType}">${applicationType}</option>
				</select></td>
			</tr>

		</s:if>
		<s:else>
			<tr class="headercls">
				<td colspan="2">
				<center>Applicant Information</center>
				</td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Company Name</b></td>
				<td width="70%"><s:textfield name="companyName"
					value="%{companyName}"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Date Of Submission</b></td>
				<td width="70%"><input type="text" name="dos" id="dos"
					ReadOnly="readonly" size="12"
					value="<s:date name="now" format="yyyy/MM/dd"/>"> &nbsp;<IMG
					onclick="popUpCalendar(this,dos,'yyyy/mm/dd');"
					src="<%=request.getContextPath() %>/images/Calendar.png"
					align="middle"></td>
			</tr>


			<tr class="headercls">
				<td colspan="2">
				<center>Product Description</center>
				</td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Product Name</b></td>
				<td width="70%"><s:textfield name="prodName"
					value="%{prodName}"></s:textfield></td>
			</tr>
			<tr>
				<td width="30%" class="title"><b>Product Type</b></td>
				<td width="70%"><select name="productType">
					<option value="-1">Please Select Product Type</option>
					<option value="established">established</option>
					<option value="proprietary">proprietary</option>
					<option value="chemical">chemical</option>
					<option value="code">code</option>
				</select></td>
			</tr>

			<tr class="headercls">
				<td colspan="2">
				<center>Application Information</center>
				</td>
			</tr>

			<tr>
				<td width="30%" class="title"><b>Application Type</b></td>
				<td width="70%"><select name="applicationType">
					<option value="-1">Please Select Application Type</option>
					<option value="nda">nda</option>
					<option value="anda">anda</option>
					<option value="bla">bla</option>
					<option value="dmf">dmf</option>
					<option value="dmf">ind</option>
					<option value="dmf">master-file</option>
				</select></td>
			</tr>
		</s:else>
		<tr>
			<td width="30%" class="title"><b>Submission Type</b></td>
			<td width="70%"><select name="submissionType_us"
				<s:if test="lastPublishedVersion != '-999'">onchange="textBoxEnable();"</s:if>>
				<option value="-1">Please Select Submission Type</option>
				<option value="original-application">original-application</option>
				<s:if test="lastPublishedVersion != '-999'">
					<option value="amendment">amendment</option>
					<option value="resubmission">resubmission</option>
				</s:if>
				<option value="presubmission">presubmission</option>
				<option value="annual-report">annual-report</option>
				<option value="establishment-description-supplement">establishment-description-supplement</option>
				<option value="efficacy-supplement">efficacy-supplement</option>
				<option value="labeling-supplement">labeling-supplement</option>
				<option value="chemistry-manufacturing-controls-supplement">chemistry-manufacturing-controls-supplement</option>
				<option value="other">other</option>
			</select></td>
		</tr>

		<tr id="seqId" style="visibility: hidden;">
			<td width="30%" class="title"><b>related-sequence-number</b></td>
			<td width="70%"><s:textfield name="relatedSeqNumber"
				value="%{relatedSeqNo}"></s:textfield></td>
		</tr>
	</table>
	</div>

	<table cellpadding="2" width="927" height="50">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td width="30%" align="right">&nbsp;</td>

			<td width="15%" align="left"><s:submit name="submitBtn"
				value="Publish" cssClass="button" onclick="return validate();" /></td>

			<td width="60%" align="left"><s:hidden name="labelId">
			</s:hidden> <s:hidden name="labelNo">
			</s:hidden> <s:hidden name="workSpaceId">
			</s:hidden> <input type="button" class="button" value="Validate" name="Validate"
				onclick="return showReport();"></td>

		</tr>
	</table>


</s:form> <s:if test="lastPublishedVersion != '-999'">
	<s:if test="UsSubmission == true">
		<script type="text/javascript" language="JavaScript">       
			  		document.getElementById('detailBoxEu').style.display='none';
					document.getElementById('detailBoxUS').style.display=''    
				</script>
	</s:if>
	<s:else>
		<script type="text/javascript" language="JavaScript">       
			  		document.getElementById('detailBoxEu').style.display='';
					document.getElementById('detailBoxUS').style.display='none'    
				</script>
	</s:else>
</s:if></div>
</body>
</html>
