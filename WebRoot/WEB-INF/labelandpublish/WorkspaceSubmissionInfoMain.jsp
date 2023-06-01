<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>

<style>
.ui-button {
	margin-left: -1px;
}

.ui-button-icon-only .ui-button-text {
	padding: 0.24em;
}

.ui-autocomplete-input {
	margin: 0;
	padding: 0.23em 0.23em 0.22em;
}
</style>
<SCRIPT type="text/javascript">
	
	
		var currentRegionId = ''; //Globel variable for this page
		var regionalDTDVersion = '';
		function showRegionDiv()
		{					
			if(currentRegionId != '')
			{
				var divElement = document.getElementById('detailBox_'+currentRegionId);
				if(divElement != null)
				{
					divElement.style.display='none';
					document.forms["submissionInfoForm"].submitBtn.disabled = 'disabled';
				}
			}
			
			var countrycodearr = document.forms["submissionInfoForm"].countryId.value.split(',');
			if(countrycodearr.length == 2)
			{
				
				currentRegionId = countrycodearr[1];
				regionalDTDVersion = document.forms["submissionInfoForm"].regionalDTDVersion.value;
				var divElement = document.getElementById('detailBox_'+currentRegionId);


				
				if(divElement != null)
				{
				
						divElement.style.display='';
					
					document.forms["submissionInfoForm"].submitBtn.disabled = '';
					var regVersion=document.getElementById("regionalDTDVersion");
					var highLvlNo=document.getElementById("highLvlNo");
					var UUIDNo=document.getElementById("UUID");	
					var atcRow=document.getElementById("atcRow");	
					var appNoTd=document.getElementById("appNoTd");		
							
					if (currentRegionId == 'eu' && ( regVersion.value=='14' || regVersion.value=='20'))
					{						
						highLvlNo.style.display='';
						atcRow.style.display='none';
						UUIDNo.style.display='none';
						appNoTd.innerHTML="Tracking Number";
						
					}
					else if (currentRegionId == 'eu' &&  regVersion.value=='301')
					{						
						highLvlNo.style.display='';
						UUIDNo.style.display='';
						atcRow.style.display='none';
						appNoTd.innerHTML="Tracking Number";
						
					}
					else if(currentRegionId=='au' || currentRegionId=='th'){

						appNoTd.innerHTML="eSubmissionId";
					}
					else if(currentRegionId=='ca'){

						appNoTd.innerHTML="Dossier Identifier";
					}
					else
					{
						highLvlNo.style.display='none';
						UUIDNo.style.display='none';
						atcRow.style.display='';
						
						appNoTd.innerHTML="Application Number";
					}
		
			
					if(currentRegionId == 'us' && regVersion.value=='23')
					{
						document.getElementById("tr_applicationId").style.display="";
						document.getElementById("tr_submissionDescription_US").style.display="";
						document.getElementById("tr_groupSubmission_US").style.display="";
						document.getElementById("tr_submissionId_US").style.display="";
						document.getElementById("tr_applicationType_23").style.display="";
						document.getElementById("tr_applicationType_21").style.display="none";
						
					}
					else if(currentRegionId == 'us' && regVersion.value!='23')
					{
						document.getElementById("tr_applicationId").style.display="none";
						document.getElementById("tr_submissionDescription_US").style.display="none";
						document.getElementById("tr_groupSubmission_US").style.display="none";
						document.getElementById("tr_submissionId_US").style.display="none";
						document.getElementById("tr_applicationType_23").style.display="none";
						document.getElementById("tr_applicationType_21").style.display="";
					}

					
				/*
					if(currentRegionId == 'gcc')
					{
						
						atcRow.style.display='';
						highLvlNo.style.display='';
						appNoTd.innerHTML="Application Number";	
						document.getElementById('lblDetail').innerHTML="Specify following details for GCC-Regional";
					}*/					
				}
			}
			// added for gcc on 21/3/2013
		
			//added condition on 27-06-2013 for za region
		 	
			var euRegOpts=document.getElementById('euRegionalRow');
			var gccRegOpts=document.getElementById('gccRegionalRow');
			var zaRegOpts=document.getElementById('zaRegionalRow');
			var chRegOpts=document.getElementById('chRegionalRow');
			var auRegOpts=document.getElementById('auRegionalRow');
			var usRegOpts=document.getElementById('usRegionalRow');
			var thRegOpts=document.getElementById('thRegionalRow');
			var caRegOpts=document.getElementById('caRegionalRow');
			
			
			var zaRegDetails=document.getElementById('detailBox_za');
			var gccRegDetails=document.getElementById('detailBox_gcc');
			var euRegDetails=document.getElementById('detailBox_eu');
			var chRegDetails=document.getElementById('detailBox_ch');
			var usRegDetails=document.getElementById('detailBox_us');
			var auRegDetails=document.getElementById('detailBox_au');
			var thRegDetails=document.getElementById('detailBox_th');
			var caRegDetails=document.getElementById('detailBox_ca');
			
			if (currentRegionId=='eu')
			{	
						
				auRegOpts.style.display="none";				
				euRegOpts.style.display="";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				chRegOpts.style.display="none";
				usRegOpts.style.display="none";

				thRegOpts.style.display="none";
				thRegDetails.style.display="none";
				auRegDetails.style.display="none";
				chRegDetails.style.display="none";
				zaRegDetails.style.display="none";
				gccRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";
				euRegDetails.style.display="";
			
			}
			else if(currentRegionId=='gcc')
			{
				auRegOpts.style.display="none";
				gccRegOpts.style.display="";
				zaRegOpts.style.display="none";
				euRegOpts.style.display="none";
				chRegOpts.style.display="none";
				usRegOpts.style.display="none";

				thRegOpts.style.display="none";
				thRegDetails.style.display="none";
				auRegDetails.style.display="none";
				zaRegDetails.style.display="none";
				chRegDetails.style.display="none";
				gccRegDetails.style.display="";
				euRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";
				
			}
			else if(currentRegionId=='za')
			{
				auRegOpts.style.display="none";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="";
				chRegOpts.style.display="none";
				euRegOpts.style.display="none";
				usRegOpts.style.display="none";

				thRegOpts.style.display="none";
				thRegDetails.style.display="none";
				auRegDetails.style.display="none";
				zaRegDetails.style.display="";
				chRegDetails.style.display="none";
				gccRegDetails.style.display="none";
				euRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";
			}
			else if(currentRegionId=='ch')
			{
				auRegOpts.style.display="none";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				chRegOpts.style.display="";
				euRegOpts.style.display="none";
				usRegOpts.style.display="none";

				thRegOpts.style.display="none";
				thRegDetails.style.display="none";
				auRegDetails.style.display="none";
				chRegDetails.style.display="";
				gccRegDetails.style.display="none";
				euRegDetails.style.display="none";
				zaRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";
			}
			else if(currentRegionId=='us')
			{
				auRegOpts.style.display="none";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				usRegOpts.style.display="";
				euRegOpts.style.display="none";
				chRegOpts.style.display="none";

				thRegOpts.style.display="none";
				thRegDetails.style.display="none";
				usRegDetails.style.display="";
				auRegDetails.style.display="none";
				chRegDetails.style.display="none";
				gccRegDetails.style.display="none";
				euRegDetails.style.display="none";
				zaRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";	
			}			
			else if(currentRegionId=='au')
			{
				auRegOpts.style.display="";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				chRegOpts.style.display="none";
				euRegOpts.style.display="none";
				usRegOpts.style.display="none";

				thRegOpts.style.display="none";
				thRegDetails.style.display="none";
				auRegDetails.style.display="";
				zaRegDetails.style.display="none";
				chRegDetails.style.display="none";
				gccRegDetails.style.display="none";
				euRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";
			}
			else if(currentRegionId=='th')
			{
				thRegOpts.style.display="";
				auRegOpts.style.display="none";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				chRegOpts.style.display="none";
				euRegOpts.style.display="none";
				usRegOpts.style.display="none";

				
				thRegDetails.style.display="";
				auRegDetails.style.display="none";
				zaRegDetails.style.display="none";
				chRegDetails.style.display="none";
				gccRegDetails.style.display="none";
				euRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="none";
				caRegDetails.style.display="none";
			}
			else if(currentRegionId=='ca')
			{
				thRegOpts.style.display="none";
				auRegOpts.style.display="none";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				chRegOpts.style.display="none";
				euRegOpts.style.display="none";
				usRegOpts.style.display="none";

				
				thRegDetails.style.display="none";
				auRegDetails.style.display="none";
				zaRegDetails.style.display="none";
				chRegDetails.style.display="none";
				gccRegDetails.style.display="none";
				euRegDetails.style.display="none";
				usRegDetails.style.display="none";
				caRegOpts.style.display="";
				caRegDetails.style.display="";
			}
			else
			{
				euRegOpts.style.display="none";
				gccRegOpts.style.display="none";
				zaRegOpts.style.display="none";
				chRegOpts.style.display="none";
				usRegOpts.style.display="none";
				auRegDetails.style.display="none";

				thRegOpts.style.display="none";
				caRegOpts.style.display="none";
				
				
			}
		}
		
		
		function hide(str)
		{
			if(document.getElementById(str).style.display=="inline")
				document.getElementById(str).style.display = 'none';
			else
				document.getElementById(str).style.display = 'inline';
		}	
		
		function validate()
		{
				
			var countryofSubmission = (document.forms["submissionInfoForm"]).countryId.options[document.forms["submissionInfoForm"].countryId.selectedIndex].text;
			if(document.forms["submissionInfoForm"].countryId.value=="-1")
			{
				alert("Please select a country for submission..");
				document.forms["submissionInfoForm"].countryId.style.backgroundColor="#FFE6F7"; 
		    	document.forms["submissionInfoForm"].countryId.focus();
		    	return false;
		    }
		    else if(document.forms["submissionInfoForm"].agencyName.value=="-1")
			{
				alert("Please select an agency for submission..");
				document.forms["submissionInfoForm"].agencyName.style.backgroundColor="#FFE6F7"; 
		    	document.forms["submissionInfoForm"].agencyName.focus();
		    	return false;
		    }
		    else if( (countryofSubmission == 'Australia' || countryofSubmission == 'Thailand') && document.forms["submissionInfoForm"].applicationNumber.value=="")
			{
				alert("Please specify eSubmissionId Number..");
				document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
		    	document.forms["submissionInfoForm"].applicationNumber.focus();
		    	return false;
		    }
		    else if(countryofSubmission == 'Canada'  && document.forms["submissionInfoForm"].applicationNumber.value=="")
			{
				alert("Please specify dossier identifier..");
				document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
		    	document.forms["submissionInfoForm"].applicationNumber.focus();
		    	return false;
		    }
	        else if(countryofSubmission != 'EMEA' && countryofSubmission != 'South Africa' && document.forms["submissionInfoForm"].applicationNumber.value=="" )
			{
				alert("Please specify Application Number..");
				document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
		    	document.forms["submissionInfoForm"].applicationNumber.focus();
		    	return false;
		    }
		    else{
			    var applicationNum = document.forms["submissionInfoForm"].applicationNumber.value;
			   // var esubmissionNum = document.forms["submissionInfoForm"].esubmissionid.value;
			    /*if(countryofSubmission == 'Canada')
		    	{	

		    		if(applicationNum.length != 7)
		    		{
		    			alert("Dossier Identifier Must Be of 7 Characters...");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
			    	}
		    		if(applicationNum.charAt(0) != 'e')
		    		{
		    			alert("Dossier Identifier starts with character \'e\'");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
		    		}	
		    		var appno = applicationNum.substring(1);//match last 6 characters
		    		if(!appno.match(/^[0-9]*$/))
		    		{	
		    			alert("Last SIX characters must be digits for CA-HC-v2.2 submission..");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
			    	}
		     	}*/
			    if(countryofSubmission == 'Australia')
		    	{	

		    		if(applicationNum.length != 7)
		    		{
		    			alert("eSubmissionId Must Be of 7 Characters...");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
			    	}
		    		if(applicationNum.charAt(0) != 'e')
		    		{
		    			alert("eSubmissionId starts with character \'e\'");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
		    		}	
		    		var appno = applicationNum.substring(1);//match last 6 characters
		    		if(!appno.match(/^[0-9]*$/))
		    		{	
		    			alert("Last SIX characters must be digits for AU-TGA-v3.0 submission..");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
			    	}
		     	}
			   
			    if(countryofSubmission == 'Thailand')
		    	{	

		    		if(applicationNum.length != 8)
		    		{
		    			alert("eSubmissionId Must Be of 8 Characters...");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
			    	}
		    		if(applicationNum.charAt(0) != 'e')
		    		{
		    			alert("eSubmissionId starts with character \'e\'");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
		    		}	
		    		var appno = applicationNum.substring(1);//match last 6 characters
		    		if(!appno.match(/^[0-9]*$/))
		    		{	
		    			alert("Last SEVEN characters must be digits..");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].applicationNumber.focus();
				    	return false;
			    	}
		     	}
		    	if(countryofSubmission == 'USA')
		    	{
		    		if(applicationNum.length != 6 )
		    		{
			    		alert("Please enter six digit Application Number for US submission..");
			    		document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
			    		document.forms["submissionInfoForm"].applicationNumber.focus();
					   	return false;
		    		}
			    	if(!applicationNum.match(/^[0-9]*$/))
		    		{
		    			alert("Only digits are allowed in Application Number for US Submission..");
		    			document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
		    			document.forms["submissionInfoForm"].applicationNumber.focus();
					   	return false;
		    		}
		    	}
		    	
		    	if(!applicationNum.match(/^([a-zA-Z0-9\/\u002D\u002C\s])*$/))
				{//For EU
					alert("Only digits, Alphabets, '-' , '/' and ',' are allowed.");
					document.forms["submissionInfoForm"].applicationNumber.style.backgroundColor="#FFE6F7"; 
				   	document.forms["submissionInfoForm"].applicationNumber.focus();
				   	return false;
			    }
			}
			if(currentRegionId == 'us')
			{
				if(document.forms["submissionInfoForm"].companyName.value=='')
				{
					alert("Please specify Company Name..");
					document.forms["submissionInfoForm"].companyName.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].companyName.focus();
			    	return false;
				}
				else if(document.forms["submissionInfoForm"].prodName.value=='')
				{
					alert("Please specify Product Name..");
					document.forms["submissionInfoForm"].prodName.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].prodName.focus();
			    	return false;
				} 
				else if(document.forms["submissionInfoForm"].productType.value == -1)
				{
					alert("Please Select Product Type..");
					document.forms["submissionInfoForm"].productType.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].productType.focus();
			    	return false;
				}
				
				if(document.forms["submissionInfoForm"].regionalDTDVersion.value=="23" && document.forms["submissionInfoForm"].applicationType_23.value== -1)
				{
					alert("Please Select Application Type..");
					document.forms["submissionInfoForm"].applicationType_23.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].applicationType_23.focus();
			    	return false;
				}				
				else if(document.forms["submissionInfoForm"].applicationType.value==-1)
				{
					alert("Please Select Application Type..");
					document.forms["submissionInfoForm"].applicationType.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].applicationType.focus();
			    	return false;
				}

				
			}
			
			if(currentRegionId == 'eu')
			{
				var regVersion=document.getElementById("regionalDTDVersion");

				if(regVersion.value=='301' && document.forms["submissionInfoForm"].uuid.value==""){
					alert("Please speficy UUID..");
					document.forms["submissionInfoForm"].uuid.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].uuid.focus();
		    		return false;
				}
				else if(document.forms["submissionInfoForm"].applicant.value=="")
				{
					alert("Please specify Applicant Name..");
					document.forms["submissionInfoForm"].applicant.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].applicant.focus();
		    		return false;
		    	}
		 		
		    	else if(document.forms["submissionInfoForm"].atc.value=="")
				{
					
		    	}
		    	else if(document.forms["submissionInfoForm"].inventedName.value=="")
				{
					alert("Please specify Invented Name..");
					document.forms["submissionInfoForm"].inventedName.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].inventedName.focus();
		    		return false;
		    	}
		    	/*else if(document.forms["submissionInfoForm"].inn.value=="")
				{
					alert("Please specify Inn..");
					document.forms["submissionInfoForm"].inn.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].inn.focus();
		    		return false;
		    	}*/

		    	if(document.forms["submissionInfoForm"].procedureType.value=="-1")
				{
					alert("Please select Procedure Type..");
					document.forms["submissionInfoForm"].procedureType.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].procedureType.focus();
		    		return false;
		    	}
			}
			
		   if(currentRegionId == 'ca')
			{
				if(document.forms["submissionInfoForm"].prodName_ca.value=='')
				{
					alert("Please specify Product Name..");
					document.forms["submissionInfoForm"].prodName_ca.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].prodName_ca.focus();
			    	return false;
				}
				else if(document.forms["submissionInfoForm"].applicant_ca.value=="")
				{
					alert("Please specify Applicant Name..");
					document.forms["submissionInfoForm"].applicant_ca.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].applicant_ca.focus();
		    		return false;
		    	}
				else if(document.forms["submissionInfoForm"].dossier_type.value=="-1")
				{
					alert("Please select dossier type..");
					document.forms["submissionInfoForm"].dossier_type.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].dossier_type.focus();
		    		return false;
		    	}
				else if(document.forms["submissionInfoForm"].regactlead_ca.value=="-1")
				{
					alert("Please select regulatory activity lead..");
					document.forms["submissionInfoForm"].regactlead_ca.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].regactlead_ca.focus();
		    		return false;
		    	}
			}
		   if(currentRegionId == 'au')
			{
		 		if(document.forms["submissionInfoForm"].applicant_au.value=="")
				{
					alert("Please specify Applicant Name..");
					document.forms["submissionInfoForm"].applicant_au.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].applicant_au.focus();
		    		return false;
		    	}
		    	
		    	else if(document.forms["submissionInfoForm"].regactlead.value=="-1")
				{
					alert("Please select regulatory activity lead..");
					document.forms["submissionInfoForm"].regactlead.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].regactlead.focus();
		    		return false;
		    	}
		    	else if (tbodyAAN == "0") {
		    		alert("Please Specify AAN..");
		    		
			    	return false;
			    	
		    	}
		    	
		    	else if (tbodyProduct =="0") {
		    		alert("Please Specify Product..");
		    		document.forms["submissionInfoForm"].product_au.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].product_au.focus();
			    	return false;
			    	
		    	}
		    	if(tbodyAAN > 0 && document.forms["submissionInfoForm"].MultipleAAN.checked==false){

						alert("Please Specify AAN..");
		    		
			    	return false;
			    }
		    	if(tbodyProduct > 0 && document.forms["submissionInfoForm"].MultipleProduct.checked==false){
					alert("Please Checked Product..");
		    		
			    	return false;
			 	}
		    	
			}
		   if(currentRegionId == 'ch')
			{
		 		if(document.forms["submissionInfoForm"].applicant_ch.value=="")
				{
					alert("Please specify Applicant Name..");
					document.forms["submissionInfoForm"].applicant_ch.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].applicant_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].dmfNumber_ch.value=="")
				{
					alert("Please specify DMF Number..");
					document.forms["submissionInfoForm"].dmfNumber_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].dmfNumber_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].dmfHolder_ch.value=="")
				{
					alert("Please specify DMF Holder..");
					document.forms["submissionInfoForm"].dmfHolder_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].dmfHolder_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].pmfNumber_ch.value=="")
				{
					alert("Please specify PMF Number..");
					document.forms["submissionInfoForm"].pmfNumber_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].pmfNumber_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].pmfHolder_ch.value=="")
				{
					alert("Please specify PMF Holder..");
					document.forms["submissionInfoForm"].pmfHolder_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].pmfHolder_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].inventedName_ch.value=="")
				{
					alert("Please specify Invented Name..");
					document.forms["submissionInfoForm"].inventedName_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].inventedName_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].inn_ch.value=="")
				{
					alert("Please specify INN..");
					document.forms["submissionInfoForm"].inn_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].inn_ch.focus();
		    		return false;
		    	}
		 		else if(document.forms["submissionInfoForm"].submissionDescription_ch.value=="")
				{
					alert("Please specify Submission Description..");
					document.forms["submissionInfoForm"].submissionDescription_ch.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].submissionDescription_ch.focus();
		    		return false;
		    	}
		 		
			}
		   if(currentRegionId == 'th')
			{

				
				if(document.forms["submissionInfoForm"].licensee.value=="")
				{
					alert("Please specify Licensee..");
					document.forms["submissionInfoForm"].licensee.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].licensee.focus();
		    		return false;
		    	}
				else if(document.forms["submissionInfoForm"].licensee_Name.value=="")
				{
					alert("Please specify Licensee Name..");
					document.forms["submissionInfoForm"].licensee_Name.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].licensee_Name.focus();
		    		return false;
		    	}
				else if(document.forms["submissionInfoForm"].licensee_Type.value=="-1")
				{
					alert("Please select Licensee Type..");
					document.forms["submissionInfoForm"].licensee_Type.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].licensee_Type.focus();
		    		return false;
		    	}
				else if(document.forms["submissionInfoForm"].regactlead_th.value=="-1")
				{
					alert("Please select regulatory activity lead..");
					document.forms["submissionInfoForm"].regactlead_th.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].regactlead_th.focus();
		    		return false;
		    	}
		    	else if (tbodyINN == "0") {
		    		alert("Please Specify INN..");
		    		
			    	return false;
			    	
		    	}
		    	
		    	else if (tbodyProduct_th =="0") {
		    		alert("Please Specify Product..");
		    		document.forms["submissionInfoForm"].product_th.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].product_th.focus();
			    	return false;
			    	
		    	}
		    	if(tbodyINN > 0 && document.forms["submissionInfoForm"].MultipleINN.checked==false){

						alert("Please Specify INN..");
		    		
			    	return false;
			    }
		    	if(tbodyProduct_th > 0 && document.forms["submissionInfoForm"].MultipleProduct_th.checked==false){
					alert("Please Checked Product..");
		    		
			    	return false;
			 	}
		    	
			}
		   	if(currentRegionId == 'za')
		   	{
		   		if(document.forms["submissionInfoForm"].applicant_za.value=="")
				{
					alert("Please specify Applicant Name..");
					document.forms["submissionInfoForm"].applicant_za.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].applicant_za.focus();
		    		return false;
		    	}
		   	 	if (tbodyApplicationNumber =="0") {
		    		alert("Please Specify Application number..");
		    		document.forms["submissionInfoForm"].applicationNumber.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].applicationNumber.focus();
			    	
			    	return false;
			    	
		    	}
		    	
		   		if(tbodyApplicationNumber > 0 && document.forms["submissionInfoForm"].MultipleApplicationNumber.checked==false){
					alert("Please Checked Application number..");
		    		return false;
			 	}
		   		 if (tbodyProprietary_Name == "0") {
		    		alert("Please Specify Proprietary Name..");
		    		document.forms["submissionInfoForm"].proprietary_za.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].proprietary_za.focus();
			    	return false;
			    	
		    	}
		   		if(tbodyProprietary_Name > 0 && document.forms["submissionInfoForm"].MultipleProprietary_Name.checked==false){

					alert("Please Checked Proprietary Name..");
	    		
		    	return false;
		    	}
		    	
		    	 if (tbodyDosageForm_ZA =="0") {
		    		alert("Please Specify Dosage Form..");
		    		document.forms["submissionInfoForm"].dosageForm_za.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].dosageForm_za.focus();
			    	
			    	return false;
			    	
		    	}
		    	
		    	if(tbodyDosageForm_ZA > 0 && document.forms["submissionInfoForm"].MultipleDosageForm_ZA.checked==false){
					alert("Please Checked Dosage Form..");
		    		
			    	return false;
			 	}

		    	 if (tbodyInn_ZA =="0") {
			    		alert("Please Specify Inn..");
			    		document.forms["submissionInfoForm"].inn_za.style.backgroundColor="#FFE6F7"; 
				    	document.forms["submissionInfoForm"].inn_za.focus();
				    	
				    	return false;
				    	
			    	}
			    	
			    if(tbodyInn_ZA > 0 && document.forms["submissionInfoForm"].MultipleInn_ZA.checked==false){
					alert("Please Checked Inn..");
			    		
				    return false;
				 }
				if(document.forms["submissionInfoForm"].submissionDescription_za.value=="")
				{
					alert("Please specify submission descriptino..");
					document.forms["submissionInfoForm"].submissionDescription_za.style.backgroundColor="#FFE6F7"; 
					document.forms["submissionInfoForm"].submissionDescription_za.focus();
		    		return false;
		    	}
			   
		   	}
		    if(currentRegionId=='gcc')
			{
				//validating if current regionID=gcc
			
				$("#validation_dialog").css({
					"height":"50px",
					"font-size":"130%",
					"display":"block",
					"background-color":"gray",
					"width":"300px",
					"border":"5px solid",
					"borderRadius":"10",
					"borderColor":"red",
					"color":"white",
					"text-align":"center"
						
				});
				
				if(document.forms["submissionInfoForm"].atc_gcc.value=="")
				{
					
					alert("Please specify ATC...");														
					document.forms["submissionInfoForm"].atc_gcc.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].atc_gcc.focus();
		    		return false;
			    	
		    	}
		    	else if(document.forms["submissionInfoForm"].applicant_gcc.value=="")
				{
					alert("Please specify Applicant Name..");
					document.forms["submissionInfoForm"].applicant_gcc.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].applicant_gcc.focus();
		    		return false;
		    	}
		    	else if(document.forms["submissionInfoForm"].inventedName_gcc.value=="")
				{
					alert("Please specify Invented Name..");
					document.forms["submissionInfoForm"].inventedName_gcc.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].inventedName_gcc.focus();
		    		return false;
		    	}
		    	else if(document.forms["submissionInfoForm"].inn_gcc.value=="")
				{
					alert("Please specify Inn..");
					document.forms["submissionInfoForm"].inn_gcc.style.backgroundColor="#FFE6F7"; 
			    	document.forms["submissionInfoForm"].inn_gcc.focus();
		    		return false;
		    	}
		    	
			}
			
		   return showMsg();
		}
		
		function showMsg()
		{
			
			document.getElementById("submissionInfoFormDiv").style.display = 'none';
			document.getElementById("msgDiv").style.display = '';
			// prepare the form when the DOM is ready
			$(document).ready(function() {
				var options = {	
								target: '#msgDiv'
							  };								
				// bind to the form's submit event
				$('#submissionInfoForm').submit(function() 
				{
					$(this).ajaxSubmit(options);
					return false;
				});			
			});
		}

	/*	function detectReturnKey(evt) 
		{ 
			if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
			{
				return document.forms["submissionInfoForm"].submitBtn.click();
			} 
		} 

		document.onkeypress = detectReturnKey;*/
		
		function subinfo()
		{
			
			var workSpaceId=$('#workSpaceId').val();
			$.ajax(
			{			
				url: 'ShowSubmissionInfo.do?workSpaceId=' + workSpaceId,
				beforeSend: function()
				{
						$('#ShowSubInfoDiv').html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");								
				},
				success: function(data) 
		  		{
						$('#ShowSubInfoDiv').html(data);
				}	  				    		
			});
			return true;
		}		
		
		function goBack()
		{
			document.getElementById("submissionInfoFormDiv").style.display = '';
			document.getElementById("msgDiv").style.display = 'none';
		}

		function openWorkspaceApplicationDetail(wsId){
			
			updateGried(wsId);

	 		//centering with css
			centerPopup();
			//load popup
			loadPopup();
			
		}


		function updateGried(wsId)
		{
			//alert("In updateGried");
			$.ajax({
					
				url: 'AddWorkspaceApplication_ex.do?workSpaceId='+wsId,
				
				success: function(data) 
		  		{
			  		
			  		//alert(data);
			  		//popupContact
					$('#popupContact').html(data);
				}							 
			});
		}

		function showHideWorkspaceApplicationDetailLink()
		{
			var groupSub = document.forms["submissionInfoForm"].isGroupSub.value;
			
			//alert(groupSub);
			
			if(groupSub == 'Yes')
			{
				
				document.getElementById('LinkId').style.visibility = '';
				
			}
			else
			{
				document.getElementById('LinkId').style.visibility = 'hidden';
				
			}
		}

		
		function openCMSWindow(wsId)
		{
			addCmsDtl(wsId);

		 		//centering with css
				centerPopup();
				//load popup
				loadPopup();
   		}
		
		function addCmsDtl(wsId)
		{
			var selectedCountry = document.forms['submissionInfoForm'].countryId.value;
			var countryDtl = selectedCountry.split(',');
			var regionId = countryDtl[1]; 
			var countryId =countryDtl[0];
			var dtdVersion=document.getElementById("regionalDTDVersion").value;
			if(regionId=="gcc")
			{		
				dtdVersion="";	
			}
			/*else
			{
				dtdVersion="";	
			}*/
			//alert("regionId="+regionId);
			//alert("CountyId="+countryId);
			$.ajax({			
						//url: 'AddCMS.do?workSpaceId='+wsid,
						url: 'AddCMS_ex.do?dtdVersion='+dtdVersion+'&workSpaceId='+wsId+"&regionId="+regionId+"&countryId="+countryId,
				  		success: function(data)
				  		{
				  			$('#popupContact').html(data);
				  			
				  		}
			});
		}
		
		function showHideCMSLink()
		{
			var procType = document.forms["submissionInfoForm"].procedureType.value;
			var procType_gcc = document.forms["submissionInfoForm"].procedureType_gcc.value;
			
			if(procType == 'mutual-recognition' || procType == 'decentralised' || procType_gcc == 'gcc')
			{
				
				document.getElementById('CMSLinkId').style.visibility = '';
				document.getElementById('CMSLinkId_gcc').style.visibility = '';
			}
			else
			{
				document.getElementById('CMSLinkId').style.visibility = 'hidden';
				document.getElementById('CMSLinkId_gcc').style.visibility = 'hidden';
			}
		}
		function jQueryOnchangeAutocompleter()
		{
			subinfo();
		}
		(function( $ ) {
			var calltheData=false;
			$.widget( "ui.combobox", {
				
				_create: function() {
					var self = this,
						select = this.element.hide(),
						selected = select.children( ":selected" ),
						value = selected.val() ? selected.text() : "";
					var input = this.input = $( "<input>" )
						.insertAfter( select )
						.val( value )
						.autocomplete({
							delay: 0,
							minLength: 0,
							source: function( request, response ) {
								var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
								response( select.children( "option" ).map(function() {
									var text = $( this ).text();
									if ( this.value && ( !request.term || matcher.test(text) ) )
										return {
											label: text.replace(
												new RegExp(
													"(?![^&;]+;)(?!<[^<>]*)(" +
													$.ui.autocomplete.escapeRegex(request.term) +
													")(?![^<>]*>)(?![^&;]+;)", "gi"
												), "<strong>$1</strong>" ),
											value: text,
											option: this
										};
								}) );
							},
							select: function( event, ui ) {
								ui.item.option.selected = true;
								jQueryOnchangeAutocompleter();
								
								self._trigger( "selected", event, {
									item: ui.item.option
								});
							},
							change: function( event, ui ) {
								if ( !ui.item ) {
									var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
										valid = false;
									select.children( "option" ).each(function() {
										if ( $( this ).text().match( matcher ) ) {
											this.selected = valid = true;
											return false;
										}
									});
									if ( !valid ) {
										// remove invalid value, as it didn't match anything
										$( this ).val( "" );
										select.val( "" );
										input.data( "autocomplete" ).term = "";
										return false;
									}
								}
							}
							
						})
						
						.addClass( "ui-widget ui-widget-content ui-corner-left" );

					input.data( "autocomplete" )._renderItem = function( ul, item ) {
						return $( "<li></li>" )
							.data( "item.autocomplete", item )
							.append( "<a>" + item.label + "</a>" )
							.appendTo( ul );
					};

					this.button = $( "<button type='button'>&nbsp;</button>" )
						.attr( "tabIndex", -1 )
						.attr( "title", "Show All Items" )
						.insertAfter( input )
						.button({
							icons: {
								primary: "ui-icon-triangle-1-s"
							},
							text: false
						})
						.removeClass( "ui-corner-all" )
						.addClass( "ui-corner-right ui-button-icon" )
						.click(function() {
							// close if already visible
							if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
								input.autocomplete( "close" );
								return;
							}

							// pass empty string as value to search for, displaying all results
							input.autocomplete( "search", "" );
							input.focus();
						});
				},

				destroy: function() {
					this.input.remove();
					this.button.remove();
					this.element.show();
					$.Widget.prototype.destroy.call( this );
				}
			});
		})( jQuery );

		$(function() {
			$( "#workSpaceId" ).combobox();
			$( "#workSpaceId" ).click(function() {
					$( "#ShowSubInfoDiv" ).toggle();
			
				});
		});
</SCRIPT>
</head>

<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<!-- 
<div style="font-size: medium;color: green;display: none;" align="center">
	<s:actionmessage/>
</div> -->

<br />
<div align="center"><img
	src="images/Header_Images/Submission/Project_Submission_Detail.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left"><s:form name="SubInfoForm"
	action="ShowSubmissionInfo" method="post" onsubmit="return false;">
	<br>
	<table width="100%">
		<tr>
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Project Name</td>
			<td><s:select list="getWorkspaceDetail" name="workSpaceId"
				id="workSpaceId" listKey="workSpaceId" listValue="workSpaceDesc"
				headerKey="-1" headerValue="" theme="simple">
			</s:select></td>
		</tr>
	</table>
  			&nbsp;
		  	&nbsp;
  
</s:form>

<table align="center" width="100%">
	<tr>
		<td align="center"><s:if
			test="workSpaceId != NULL && workSpaceId.trim() != ''">
			<SCRIPT type="text/javascript">
	  			jQueryOnchangeAutocompleter();
			</script>

		</s:if>
		<div id="ShowSubInfoDiv" align="center"></div>
		</td>
	</tr>
</table>
</div>
</div>
</div>
<div id="popupContact"
	style="width: 500px; height: 410px; position: relative;"></div>
<div id="backgroundPopup"></div>
</body>
</html>
