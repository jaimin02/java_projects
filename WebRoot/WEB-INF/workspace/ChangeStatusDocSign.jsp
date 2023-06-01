<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style>

#popupContact {
top: -25.5px !important; 
width: 560px !important;
}
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgb(0 0 0 / 73%); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  /*padding: 20px;*/
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
@font-face {
	    font-family: 'password';
	    font-style: normal;
	    font-weight: 400;
	   /*  src: url('images/password.ttf'); */
		src: url(data:font/woff;charset:utf-8;base64,d09GRgABAAAAAAusAAsAAAAAMGgAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAABHU1VCAAABCAAAADsAAABUIIslek9TLzIAAAFEAAAAPgAAAFZjRmM5Y21hcAAAAYQAAAgCAAArYmjjYVVnbHlmAAAJiAAAAEEAAABQiOYj2mhlYWQAAAnMAAAALgAAADYOxVFUaGhlYQAACfwAAAAcAAAAJAqNAyNobXR4AAAKGAAAAAgAAAAIAyAAAGxvY2EAAAogAAAABgAAAAYAKAAAbWF4cAAACigAAAAeAAAAIAEOACJuYW1lAAAKSAAAAUIAAAKOcN63t3Bvc3QAAAuMAAAAHQAAAC5lhHRpeJxjYGRgYOBiMGCwY2BycfMJYeDLSSzJY5BiYGGAAJA8MpsxJzM9kYEDxgPKsYBpDiBmg4gCACY7BUgAeJxjYGScwDiBgZWBgSGVtYKBgVECQjMfYEhiYmFgYGJgZWbACgLSXFMYHIAq/rNfAHK3gEmgASACAIekCT4AAHic7dhl0zDVmUXh5+XFHYK7E0IguFtwt4QQgmtwd3d3d7cED+4SXIO7u7vbsNfaUzU1fyGcu66u1adOf+6uHhgYGGpgYGDwL37/iyEHBoZZcWDQLzUw9NK/7A5if/DA8OwPOfQknBky+0P8/PPPOcd1UJ785frr/Dq/zq/z6/w3zsCgoX/xX74GRsxbcYpRB1iDB/7PGvT/DFGDenBwe8hKD1XpoSs9TKWHrfRwlR6+0iNUesRKj1TpkSs9SqVHrfRolR690r+p9BiVHrPSY1V67EqPU+lxKz1epcev9ASVnrDSE1V64kpPUulJKz1ZpSev9BSVnrLSU1V66kr/ttLTVPp3lZ62/KJSerpKT1/pP1R6hkrPWOmZKj1zpWep9KyVnq3Ss1d6jkrPWem5Kj13peep9LyVnq/S81d6gUr/sdILVnqhSi9c6UUqvWilF6v04pVeotJLVnqpSi9d6WUqvWyll6v08pVeodIrVvpPlf5zpVeq9F8qvXKl/1rpVSr9t0qvWunVKr16pdeo9JqVXqvSa1d6nUqvW+n1Kr1+pTeo9N8rvWGlN6r0xpXepNKbVnqzSm9e6S0qvWWlt6r01pXeptLbVnq7Sm9f6R0qvWOld6r0zpXepdK7Vnq3Su9e6T0qvWel96r03pXep9L7Vnq/Su9f6QMqfWClD6r0wZU+pNKHVvqwSh9e6SMqfWSlj6r00ZU+ptLHVvq4Sh9f6RMqfWKlT6r0yZU+pdKnVvq0Sp9e6TMqfWalz6r02ZU+p9LnVvq8Sp9f6QsqfWGl/1Hpf1b6okpfXOlLKn1ppS+r9OWVvqLS/6r0lZW+qtJXV/qaSl9b6esqfX2lb6j0jZW+qdI3V/qWSt9a6dsqfXul76j0vyt9Z6XvqvTdlb6n0vdW+r5K31/pByr9YKUfqvTDlX6k0v+p9KOVfqzSj1f6iUo/WemnKv10pZ+p9LOVfq7Sz1f6hUq/WOmXKv1ypV+p9KuVfq3Sr1f6jUq/Wem3Kv12pd+p9LuVfq/S71f6g0p/WOmPKv1xpT+p9KeV/qzSn1f6i0p/WemvKv11pb+p9LeV/q7S31f6h0r/WOmfKv1zDfI26KKHED1Y9JCihxI9tOhhRA8rejjRw4seQfSIokcSPbLoUUSPKno00aOL/o3oMUSPKXos0WOLHkf0uKLHEz2+6AlETyh6ItETi55E9KSiJxM9uegpRE8peirRU4v+rehpRP9O9LSify96OtHTi/6D6BlEzyh6JtEzi55F9KyiZxM9u+g5RM8pei7Rc4ueR/S8oucTPb/oBUT/UfSCohcSvbDoRUQvKnox0YuLXkL0kqKXEr206GVELyt6OdHLi15B9Iqi/yT6z6JXEv0X0SuL/qvoVUT/TfSqolcTvbroNUSvKXot0WuLXkf0uqLXE72+6A1E/130hqI3Er2x6E1Ebyp6M9Gbi95C9JaitxK9tehtRG8rejvR24veQfSOoncSvbPoXUTvKno30buL3kP0nqL3Er236H1E7yt6P9H7iz5A9IGiDxJ9sOhDRB8q+jDRh4s+QvSRoo8SfbToY0QfK/o40ceLPkH0iaJPEn2y6FNEnyr6NNGniz5D9JmizxJ9tuhzRJ8r+jzR54u+QPSFov8h+p+iLxJ9sehLRF8q+jLRl4u+QvS/RF8p+irRV4u+RvS1oq8Tfb3oG0TfKPom0TeLvkX0raJvE3276DtE/1v0naLvEn236HtE3yv6PtH3i35A9IOiHxL9sOhHRP9H9KOiHxP9uOgnRD8p+inRT4t+RvSzop8T/bzoF0S/KPol0S+LfkX0q6JfE/266DdEvyn6LdFvi35H9Lui3xP9vugPRH8o+iPRH4v+RPSnoj8T/bnoL0R/Kfor0V+L/kb0t6K/E/296B9E/yj6J9E/K/2/v/npoocQPVj0kKKHEj206GFEDyt6ONHDix5B9IiiRxI9suhRRI8qejTRo4v+jegxRI8peizRY4seR/S4oscTPb7oCURPKHoi0ROLnkT0pKInEz256ClETyl6KtFTi/6t6GlE/070tKJ/L3o60dOL/oPoGUTPKHom0TOLnkX0rKJnEz276DlEzyl6LtFzi55H9Lyi5xM9v+gFRP9R9IKiFxK9sOhFRC8qejHRi4teQvSSopcSvbToZUQvK3o50cuLXkH0iqL/JPrPolcS/RfRK4v+q+hVRP9N9KqiVxO9uug1RK8pei3Ra4teR/S6otcTvb7oDUT/XfSGojcSvbHoTURvKnoz0ZuL3kL0lqK3Er216G1Ebyt6O9Hbi95B9I6idxK9s+hdRO8qejfRu4veQ/SeovcSvbfofUTvK3o/0fuLPkD0gaIPEn2w6ENEHyr6MNGHiz5C9JGijxJ9tOhjRB8r+jjRx4s+QfSJok8SfbLoU0SfKvo00aeLPkP0maLPEn226HNEnyv6PNHni75A9IWi/yH6n6IvEn2x6EtEXyr6MtGXi75C9L9EXyn6KtFXi75G9LWirxN9vegbRN8o+ibRN4u+RfStom8TfbvoO0T/W/Sdou8Sfbfoe0TfK/o+0feLfkD0g6IfEv2w6EdE/0f0o6IfE/246CdEPyn6KdFPi35G9LOinxP9vOgXRL8o+iXRL4t+RfSrol8T/broN0S/Kfot0W+Lfkf0u6LfE/2+6A9Efyj6I9Efi/5E9KeiPxP9uegvRH8p+ivRX4v+RvS3or8T/b3oH0T/KPon0T9rYND/AOaSEScAAHicY2BiAAKmPSy+QEqUgYFRUURcTFzMyNzM3MxEXU1dTYmdjZ2NccK/K5oaLm6L3Fw0NOEMZoVAFD6IAQD4PA9iAAAAeJxjYGRgYADirq+zjOP5bb4ycLNfAIowXCttkUWmmfaw+AIpDgYmEA8ANPUJwQAAeJxjYGRgYL/AAATMCiCSaQ8DIwMqYAIAK/QBvQAAAAADIAAAAAAAAAAoAAB4nGNgZGBgYGIQA2IGMIuBgQsIGRj+g/kMAArUATEAAHicjY69TsMwFIWP+4doJYSKhMTmoUJIqOnPWIm1ZWDq0IEtTZw2VRpHjlu1D8A7MPMczAw8DM/AifFEl9qS9d1zzr3XAK7xBYHqCHTdW50aLlj9cZ1057lBfvTcRAdPnlvUnz23mXj13MEN3jhBNC6p9PDuuYYrfHquU//23CD/eG7iVnQ9t9ATD57bWIgXzx3ciw+rDrZfqmhnUnvsx2kZzdVql4Xm1DhVFsqUqc7lKBiemjOVKxNaFcvlUZb71djaRCZGb+VU51ZlmZaF0RsV2WBtbTEZDBKvB5HewkLhwLePkhRhB4OU9ZFKTCqpzems6GQI6Z7TcU5mQceQUmjkkBghwPCszhmd3HWHLh+ze8mEpLvnT8dULRLWCTMaW9LUbanSGa+mUjhv47ZY7l67rgITDHiTf/mAKU76BTuXfk8AAHicY2BigAARBuyAiZGJkZmBJSWzOJmBAQALQwHHAAAA) format("woff");

	   
	}          
	#reConfPass {
            font-family: 'password';
        }
</style>
 <script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<link
	href="<%=request.getContextPath()%>/js/jquery/modalPopup/general.css"
	rel="stylesheet" type="text/css" media="screen" />
<script
	src="<%=request.getContextPath()%>/js/jquery/modalPopup/popup.js"
	type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
$(document).ready(function() { 
	$("#reConfPass").focus();
	
	var logoname = '<s:property value="signImg"/>';
	if(logoname!==""){
	document.getElementById('PreviewImg').src='ShowImage_b.do?logoFileName='+logoname;
	}
	$("#reConfPass").on("keydown", function (e) {
	    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
	    	e.preventDefault();
	    	 document.getElementById("Verify").click();
	    }
	});
	});
	function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	}
	function chgStatus1(buttonName)
	{
		debugger;
		var nodeId = '<s:property value="recordId"/>';
		var wsId = '<s:property value="workspaceID"/>';
		var docType ='<s:property value="docType"/>';
		var temp="true";
		var checkFileOpenSignOff = '<s:property value="OpenFileAndSignOff"/>';
		var stage = document.getElementById("stageCode");
		var selectedStage = stage.options[stage.selectedIndex].value;
		var manualSignatureConfig = '<s:property value="ManualSignatureConfig"/>';
		var applicationUrl='<s:property value="ApplicationUrl"/>';
		var tranNo='<s:property value="tranNo"/>';
		
		
		if(checkFileOpenSignOff=="Yes"){
		 $.ajax({		
			  url: "checkfileopenforsign_ex.do?nodeId="+nodeId+"&workspaceID="+wsId,
			  success: function(data) 
			  {
				 //debugger;
				 temp = data;
				 if(data=="false"){
				  	alert("Please review document before sign off.");
				  	 var out="ChangeStatusDocSign.do?docId="+wsId+"&recordId="+nodeId+"&docType="+docType;
					 location.href=out;
				  	return false;
				 }
			  },
			  error: function(data) 
			  {
				alert("Something went wrong while fetching data from server.");
			  },
				async: false
			});
		}
	if(temp !="false"){
		
		if(manualSignatureConfig=="Yes" && selectedStage!=0){
			var signatureFlag = "<s:property value='signatureFlag'/>";
			if(signatureFlag=="false"){
				alert("Please create signature before proceeding sign off.");
				return false;
			}
			var win = window.open(applicationUrl+"ManualSignature.do?workSpaceId="+'<s:property value="workspaceID"/>'+
					"&nodeId="+nodeId+"&selectedStage="+selectedStage+"&tranNo="+tranNo, "_blank");
			var timer = setInterval(function() {
		        if (win.closed) {
		            clearInterval(timer);
		            location.reload();
		        }
		    }, 500);
			
			return false;
		}
		else{
		var userName = "<s:property value='#session.username'/>";
		document.getElementById('userName').innerHTML = userName;
	
		var  months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
		var monthName; // current month in mmm formate

		var currentdate = new Date();
		var datetime =  currentdate.getDate()+ "-" 
   	 					+  months[currentdate.getMonth()] + "-" 
   					    + currentdate.getFullYear() + " "
   						//+ currentdate.getHours() + ":"  
    					//+ currentdate.getMinutes();
		document.getElementById("date").innerHTML = datetime;
		if(buttonName=='Change'){
			var stage = document.getElementById("stageCode");
			var selectedStage = stage.options[stage.selectedIndex].text;
			document.getElementById('stage').innerHTML = selectedStage;
			document.getElementById("Verify").style.display='block';

			if(selectedStage!="Send Back"){
				var signatureFlag = "<s:property value='signatureFlag'/>";
				if(signatureFlag=="false"){
					alert("Please create signature before proceeding sign off.");
					return false;
				}
			}
			
			}
		centerPopup();				
		loadPopup();
		return false;
		}
	}
	} 
	function fileOpen(actionName)
	{
		//debugger;
		win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
		win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
	}
	function docFileOpen(actionName)
	{
		window.open(actionName, '_newtab');
	}
	function verifyPass()
	{
		//alert("test");
		debugger;
		
		var nodeId = '<s:property value="recordId"/>';
		var wsId = '<s:property value="workspaceID"/>';
		var docType ='<s:property value="docType"/>';
		var userCode ="<s:property value='#session.userid'/>";
		var selectedStageId = document.getElementById("stageCode").value;
		
		
		var sessPass="<s:property value='#session.password'/>";
		var pass=document.getElementById('reConfPass').value;
		var sessAdUser="<s:property value='#session.adUser'/>";
		var sessAdUserPass="<s:property value='#session.adUserPass'/>";
		if (!pass || pass == '')
		{						
			alert("Please enter Password.");
			$( '#reConfPass').focus(); 
			return false;
		}
		if(sessAdUser=='Y'){
			if(sessAdUserPass!=pass){
				alert("You have AD User rights. Please enter correct password.");
				document.getElementById('reConfPass').value = '';
				$( '#reConfPass').focus(); 
				return false;
			}
		}
	if(sessAdUser!='Y'){
			if(sessPass!=pass){
			alert("Incorrect Password !!!");
			document.getElementById('reConfPass').value = '';
			$( '#reConfPass').focus(); 
			return false;
			}
		}
		
	if(sessPass==pass || sessAdUserPass==pass){
			//debugger;
			var currStage= $('#stageCode').val();
			if(currStage==0){
				var remark = prompt("Please specify reason for change.");
		    	 remark = remark.trim();
				 if(remark != null || remark != ""){ 
			 //$('#remark').val(remark);		
		$.ajax({		
			  url: "ChangeStatusDocSign_ex.do?docId="+wsId+"&nodeId="+nodeId+"&stageCode="+selectedStageId+"&remark="+remark,
			  beforeSend: function()
			  { 
				 // debugger;
				 
				 document.getElementById("popupContact").style.display='none';
				 document.getElementById("loadingpopup").style.display='block';
			  },
			  success: function(data) 
			  {
				  //debugger;
				  document.getElementById("loadingpopup").style.display='none';
				  var out="ChangeRequest.do?docId="+wsId+"&docType="+docType;
				  location.href=out;
			  },
			  error: function(data) 
			  {
				  //debugger;
				  alert("Something went wrong while fetching data from server.");
			  },
				async: false				
			});
		 }
		 else{
			 alert("Please specify reason for change.");
			 return false;
		 }
			}else{
	
				$.ajax({		
					  url: "ChangeStatusDocSign_ex.do?docId="+wsId+"&nodeId="+nodeId+"&stageCode="+selectedStageId+"&eSign=Y",
					  beforeSend: function()
					  { 
						 // debugger;
						 
						 document.getElementById("popupContact").style.display='none';
						 document.getElementById("loadingpopup").style.display='block';
					  },
					  success: function(data) 
					  {
						  //debugger;
						  document.getElementById("loadingpopup").style.display='none';
						  var out="ChangeRequest.do?docId="+wsId+"&docType="+docType;
						  location.href=out;
					  },
					  error: function(data) 
					  {
						  //debugger;
						  alert("Something went wrong while fetching data from server.");
					  },
						async: false				
					});
			}
		}
		
	}
	function cls()
	{
		document.getElementById('reConfPass').value = '';
		disablePopup();
	}

</script>
</head>
<body>
<br>
<div class="container-fluid">
<div class="col-md-12">

<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
	<div class="boxborder"><div class="all_title"><b style="float:left">Change Status</b></div>

<br>
<div align ="center">
<s:form name="workspaceNodeAttrForm" method="post" enctype="multipart/form-data" >
		<table width="100%" align="center">
		<tr id="fileTr">
		<td align="right" width="50%" style="padding: 2px; padding-right: 8px;">
		<span class="title">Change Status</span>
		</td>
		<td align="left">
		 <s:if test="stageuserdtl.size > 0"> 
			<s:select list="stageuserdtl" name="stageCode" listKey="stageId" id="stageCode"
				listValue="stageDesc" value="%{selectedStage}">
			</s:select>
 		</s:if>
		<s:else>
		<select name="stageCode">
				<option value="-1">None</option>
			</select>
		</s:else>
 		</td>
 		<td></td>		
		</tr>
		</table>
		<br>
<s:if test="stageuserdtl.size>0">
	<input type="button" value="Change Status" id="Change" class="button" onclick="return chgStatus1(this.id);" />&nbsp;
</s:if>
	<input type="button" value="Close" class="button" onclick="window.location='ChangeRequest.do?docId=<s:property value="workspaceID"/>&docType=<s:property value="docType"/>';">
	<s:hidden name="recordId"></s:hidden>
	<s:hidden name="fullPathName"></s:hidden>
	<s:hidden name="nodeFolderName"></s:hidden>
</s:form>
<div id="loadingpopup" style="width: 50px;z-index: 1;position: absolute;top: 180.5px;left: 392.5px;
			padding-top: 0px;display:none">
   <center><img style="margin-top: 27px; margin-left: 255px;" src="images/loading.gif" alt="loading ..." /></center>
		</div>

<div id="popupContact" class="popcuswidth" style="height: 450px;">
<img
			alt="Close" title="Close" src="images/Common/Close.svg" height="25px" width="25px"
			onclick='cls();' class='popupCloseButton' />
		<h3 style="margin-top: 2px;">Electronic Signature</h3>
		<!-- <div style="width: 100%; height: 90px; overflow: auto;"> -->
		<table id="tble" style="width: 100%">
		<tr>
		<td class="popUpTdHdr"><b>Username: &nbsp;</b>
		</td><td class="popUpTd"><span id="userName"></span></td>
		</tr>
		<tr>
		<td class="popUpLbl"><b>Date: &nbsp;</b></td>
		<td class="popUpTd"><span id="date"></span></td>
		</tr>
		<tr>
		<td class="popUpLbl"><b>Action: &nbsp;</b></td>
		<td class="popUpTd"><span id="stage"></span></td>
		</tr>
		<tr><td class="popUpLbl"><b>Password: &nbsp;</b></td>
		<td>
		<!-- <input type="password" name="reConfPass"id="reConfPass"></input> -->
		<input class="popUpTd" type="text" name="reConfPass"id="reConfPass" autocomplete="off"></input>
		</td>
		</tr>
		</table>
				<!-- Enter password:&nbsp;<input type="password" name="reConfPass"
			id="reConfPass"></input> <br> -->
		<hr style="margin-top: 10px; margin-bottom: 10px;">
					<h3 style="margin-top: 2px;">Preview Signature</h3>
					
					<table id="tble" style="width: 100%">
					<tr>
						<td colspan="2" style="border: 1px solid #222; text-align: center; font-weight: bold; font-size: 18px; 
										background-color: gray;	color: black;">Signature</td>
					</tr>
					<tr style="border: 1px solid #222;">
					<td style="vertical-align: top; padding:10px  10px;">
					<table id="tble" style="width: 100%">
						<tr >
							<td>
							<s:if test="signImg !=''">	
							<img src="" id="PreviewImg" height="90px" width="200px" name="PreviewImg" alt="Picture">
							</s:if>
							<s:else>
							<label style="height: 90px;width: 200px; text-align: center; padding-top: 20px; font-size: 24px;
									font-family: <s:property value="signStyle"/>; color: black;">
							<span><s:property value ="userNameForPreview"/></span></label>
							</s:else>
							</td>
						</tr>
						</table>
					</td>
					
					<td>
					<table id="tble" style="width: 100%;align:left;">
						
						<tr>
							<td style="color: #000000; width: 55px; padding: 0 !important; text-align: left; vertical-align: top;"><b>Name:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important;  vertical-align: top;"><s:property value ="userNameForPreview"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important;padding: 0; text-align: left; vertical-align: top;"><b>Role:&nbsp;</b></td>
							<td style="color: #000000; padding: 0 !important; vertical-align: top;"><s:property value ="roleName"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0!important; text-align: left; vertical-align: top;"><b>Date:&nbsp;</b></td>
							<td style="color: #000000; padding: 0!important; vertical-align: top;"><s:property value ="dateForPreview"/></td>
						</tr>
						<tr>
							<td style="color: #000000; padding: 0 !important; text-align: left; vertical-align: top;"><b>Sign Id:&nbsp;</b></td>
							<td style="color: #000000; padding: 0!important; vertical-align: top;"><s:property value ="signId"/></td>
						</tr>
					</table>
					</td>					
					</tr>					
					
					</table>
		 <br> 
		<p align="center">
		   <span style="color:#000000"><b>I hereby confirm signing of this document electronically.</b></span><br><br>
				<input type="submit" class="button"
				value="Sign" id="Verify" name="buttonName" onclick="return verifyPass();"style="display:none"></input>
		</p>
		<!-- </div> -->
		</div>
		<div id="backgroundPopup"></div>
</div>
<br><br>
<s:if test ="fullNodeHistory.size==0">
<span style="font-size:16px;">No details available.</span>
</s:if>
<s:else>
<table class="datatable paddingtable" width="98%" align="center">
	<thead>
		<tr>
			<th style="border: 1px solid black;">#</th>
			<th style="border: 1px solid black;">Document</th>
			<th style="border: 1px solid black;">Size</th>
			<th style="border: 1px solid black;">Status</th>
			<th style="border: 1px solid black;">Sent for</th>
			<th style="border: 1px solid black;">Modified by</th>
			<th style="border: 1px solid black;">User Role</th>
			<th style="border: 1px solid black;">Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th style="border: 1px solid black;">Eastern Standard Time</th>
		</s:if>
			<th style="border: 1px solid black;">Reason for Change</th>
		<%-- <th>Certified</th> 
		<th>Voided By</th>
		<th>Voided On</th>
	<s:if test ="#session.countryCode != 'IND'">
		<th>Eastern Standard Time</th>
	</s:if> --%>
		</tr>
	</thead>
	<tbody>
	
		<s:iterator value="fullNodeHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td style="border: 1px solid black;"><s:property value="#status.count" /></td>
				<td style="border: 1px solid black;"><s:if test="fileName == ''">
									&nbsp;
								</s:if> <s:if test="fileName == 'No File'">
					<s:property value="fileName" />
				</s:if> <s:else>
					<s:if test = "#session.usertypename == 'WA'">
						 <s:if test="fileExt=='PDF' || fileExt=='pdf'">
							<a href="javascript:void(0);"
								onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
								<s:property value="fileName" /> </a>
						</s:if>
						<s:else>
							<a href="javascript:void(0);"
								onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
								<s:property value="fileName" /> </a>
						</s:else>
					</s:if>
					<s:else>
						<s:if test="OpenFileAndSignOff=='Yes'">
						<s:if test="fileExt=='PDF' || fileExt=='pdf'">
							<a href="javascript:void(0);"
								onclick="return fileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
								<s:property value="fileName" /> </a>
						</s:if>
						<s:else>
						<a href="javascript:void(0);"
								onclick="return docFileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
								<s:property value="fileName" /> </a>
						</s:else>
						</s:if>
						<s:else>
							<s:if test="fileExt=='PDF' || fileExt=='pdf'">
							<a href="javascript:void(0);"
								onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
								<s:property value="fileName" /> </a>
							</s:if>
							<s:else>
							<a href="javascript:void(0);"
								onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
								<s:property value="fileName" /> </a>
							</s:else>
						</s:else>
					</s:else>
				</s:else></td>
				<td style="border: 1px solid black;"><s:if test="historyDocumentSize.sizeMBytes>0">
					<s:property value="historyDocumentSize.sizeMBytes" default="--" /> MB
								</s:if> <s:else>
					<s:property value="historyDocumentSize.sizeKBytes" default="--" /> KB
								</s:else></td>
				<td style="border: 1px solid black;"><s:if test ="stageId==10 && FileType !='SR'">Uploaded</s:if>
					<s:else>
						<s:if test="stageDesc == ''">&nbsp;</s:if> 
						<s:property value="stageDesc" default=" " />
					</s:else>
				</td>
					
				<td style="border: 1px solid black;">
					<s:if test="stageId==10 && FileType ==''">-</s:if>
					<s:elseif test ="stageId==10 && FileType =='SR'">Review</s:elseif>
					<s:elseif test ="stageId==20">Approve</s:elseif>
					<s:elseif test ="stageId==0">Create</s:elseif>
					<s:else>-</s:else> 
				</td>
				<td style="border: 1px solid black;">
					<s:if test="userName == ''">&nbsp;</s:if> 
					<s:property value="userName" />
				</td>
				<td style="border: 1px solid black;">
					<s:if test="roleName == ''">&nbsp;</s:if> 
					<s:property value="roleName" />
				</td>
				<td style="border: 1px solid black;"><s:if test="modifyOn == ''">&nbsp;
					</s:if> 
					<s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td style="border: 1px solid black;"><s:property value="ESTDateTime" /></td>
			</s:if>
				<td style="border: 1px solid black;"><s:if test="remark == ''">
									&nbsp;
					</s:if> 
					<s:else>
						<s:property value="remark" />
				</s:else></td>
			<%--  <td>
				<s:property value="defaultFileFormat"/>
			</td> 
			<td><s:property value="voidBy" default="-"/></td>
			<td><s:property value="voidISTDateTime" default="-"/></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="voidESTDateTime" default="-"/></td>
			</s:if> --%>
			</tr>
		</s:iterator>
	</tbody>
</table>
</s:else>

</div>
</div>
</div>
</div>
</body>
</html>