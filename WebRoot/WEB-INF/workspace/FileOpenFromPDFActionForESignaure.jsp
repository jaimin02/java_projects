<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style type="text/css">
/* #clientTable_filter input{
background-color: #2e7eb9;
color:#ffffff;
}
#clientTable_length select {
background-color: #2e7eb9;
color:#ffffff;
} */

.row {
  display: flex;
  margin-left:-5px;
  margin-right:-5px;
}

.column {
  flex: 50%;
  padding: 5px;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

span{
	color: #2e7eb9;
    font-weight: bolder;
}
</style>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<s:head />

<script type="text/javascript">



function trim(str)
{
   	str = str.replace( /^\s+/g, "" );// strip leading
	return str.replace( /\s+$/g, "" );// strip trailing
}
	

	function validate()
	{
		var cname = document.masterAdminForm.clientName.value;
		cname=trim(cname);
	
		if(cname=="")
		{
			alert("Please add Client.");
			document.masterAdminForm.clientName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.clientName.focus();
     		return false;
     	}
     	
     	if(document.masterAdminForm.clientName.value.length>50)
		{
			alert("Client name cannot be of more then 50 charactars.");
			document.masterAdminForm.clientName.style.backgroundColor="#FFE6F7"; 
     		document.masterAdminForm.clientName.focus();
     		return false;
     	}
     	return true;
	}
	
	function openlinkEdit(clientCode,clientName)
    {
    	
    	var editLocationWindow = "EditClient.do?clientCode="+clientCode+"&clientName="+clientName;
    	win3=window.open(editLocationWindow,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=150,width=400,resizable=no,titlebar=no")	
    	win3.moveTo(screen.availWidth/2-(500/2),screen.availHeight/2-(150/2));
    	return true;
    }
    
    
    function authenticate(clientCode,status)
	{
		if(status == 'D'){
			var okCancel = confirm("Do you want to active selected Client.");
		}else{
			var okCancel = confirm("Do you want to inactive selected Client.");
		}
		if(okCancel == true)
		{
			var remark = prompt("Please specify reason for change.");
			remark = remark.trim();
			if (remark != null && remark != ""){
				var revertWindow = "DeleteClient.do?clientCode="+clientCode+"&statusIndi="+status+"&remark="+remark;
		   		window.location.href=revertWindow;
				return true;	
			}
			else if(remark==""){
				//debugger;
				alert("Please specify reason for change.");
				return false;
			}
		}
		else
			return false;
	}
	
	function detectReturnKey(evt) 
	{ 
 		if ((event.keyCode == 13) && (event.srcElement.type=="text"))  
  		{
  			return document.masterAdminForm.submitBtn.onclick();
  		} 
	} 

	document.onkeypress = detectReturnKey;
	
	function clientHistory(clientCode)
	{
		str="showClientDetailHistory_b.do?clientCode=" + clientCode;
		win3=window.open(str,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=yes,height=500,width=900,resizable=no,titlebar=no");
	 	win3.moveTo(screen.availWidth/2-(900/2),screen.availHeight/2-(500/2));	
		return true;
	}
	
</script>

</head>
<body>
<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>
<br />

<div class="container-fluid">
<div class="col-md-12">
<div align="center"><!-- <img
	src="images/Header_Images/Master/Client_Master.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->
	<input type="button" id="create_pdf" class="button" value="Download" style="margin-left: 900px;"><br/><br/>
   		<form class="form" style="max-width: none; width: 1005px;">
   		<div><img style="float: left; padding:8px 30px; height: 43px;width:150px;margin-top: -7px;" src="decorators/maincss/ecsvlogo.png"></div>
	<div class="boxborder"><div class="all_title" style="background: #54acd2;"><b style="margin-right:150px;">Signature Certificate</b></div>
	
	<div class="datatablePadding" style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
  		<br/>
<div align="left">

<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 10px; padding-left: 10px;
	border-radius: 10px 10px 10px 10px;"><br/><b style="float:left">Document Details</b>
	
	<br/>
	<div class="row">
  <div class="column">
    <table>     
      <tr><td><span>ID :</span> ${uuId}</td></tr>
      <tr><td><span>Name :</span> ${fileName }</td></tr>
      <tr><td><span>Created By :</span> ${createdBy }</td></tr>
      <s:if test="#session.countryCode != 'IND'">
		<tr><td><span>Created On :</span> ${createdOnIST }</td></tr>
	</s:if>
	<s:else>
		<tr><td><span>Created On : </span>${createdOnIST }</td></tr>
	</s:else> 
	<tr><td colspan="5" style="word-break: break-all;"><span>Block Chain Hash:</span>${hashCode }</td></tr>
    </table>
  </div>
  <div class="column">
    <table>
      <tr><td><span>Total Pages : </span>${totalPages }</td></tr>
      <tr><td><span>Signatories : </span>${signatories }</td></tr>
      <tr><td>
      		<span>Status : </span>
      		<s:if test="status=='false' ">
      		<span style="color:red;">Pending signatures</span>
      		</s:if>
      		<s:else>
      		<span style="color:green;">Signoff completed.</span>
      		</s:else>
      		
      	  </td>
      </tr>
        
    </table>
  </div>
</div>
</div>

</div>
<br/>
<div align="left">
<div style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 10px; padding-left: 10px;
	border-radius: 10px 10px 10px 10px;"><br/><b style="float:left">Signature Details</b>
	
	<br/>
	<div class="row">
  <div class="column">
    <table>     
      <tr><td><span>ID : </span>${userSignId}</td></tr>
      <tr><td><span>Name : </span>${userSignatureName }</td></tr>
      <tr><td><span>Role : </span>${userRoleName }</td></tr>
    </table>
  </div>
  <div class="column">
    <table>
     <s:if test="#session.countryCode != 'IND'">
		<tr><td><span>Signed On : </span>${dateForPreview }</td></tr>
	</s:if>
	<s:else>
		<tr><td><span>Signed On : </span>${dateForPreview }</td></tr>
	</s:else>
      <tr><td><span>Action  : </span>${actionPerformed }</td></tr>
      <tr><td><span>Email ID : </span>${userEmail }</td></tr>
    </table>
  </div>
</div>
</div>

</div>
<div style="width:100%; float:right;">
<span id="copyright" style="float:right;padding-left:10px; font-family: calibri; font-size: 14px; font-weight: bold;"></span>
	<%-- <span class="plink" style="margin-top:4px; float:right; padding-right:10px; font-family: calibri; font-size: 14px; color: #0D4264;font-weight: bold;"><a href="https://knowledgenet.sarjen.com" target="_blank">Copyright 2022 Sarjen Systems Pvt. Ltd.</a></span> --%>
</div>
<br/>
</form>
</div>






</div>

</div>
</div>
</div>

<script src="https://code.jquery.com/jquery-1.12.4.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<script>
var date = (new Date().getFullYear()).toString();
$("#copyright").text("\u00A9"+date +" Sarjen Systems Pvt. Ltd.");
    (function () {
        var
         form = $('.form'),
         cache_width = form.width(),
         a4 = [800.28, 841.89]; // for a4 size paper width and height

        $('#create_pdf').on('click', function () {
            $('body').scrollTop(0);
            createPDF();
        });
        //create pdf
        function createPDF() {
            debugger;
        	getCanvas().then(function (canvas) {
                var
                 img = canvas.toDataURL("image/png"),
                 doc = new jsPDF({
                     unit: 'px',
                     format: 'a3'
                 });
                doc.addImage(img, 'JPEG', 20, 20);
                doc.save('Signture Certificate.pdf');
                form.width(cache_width);
            });
        }

        // create canvas object
        function getCanvas() {
            form.width((a4[0] * 1.33333) - 80).css('max-width', 'none');
            return html2canvas(form, {
                imageTimeout: 2000,
                removeContainer: true
            });
        }

    }());
</script>
<script>
    (function ($) {
        $.fn.html2canvas = function (options) {
            debugger;
        	var date = new Date(),
            $message = null,
            timeoutTimer = false,
            timer = date.getTime();
            html2canvas.logging = options && options.logging;
            html2canvas.Preload(this[0], $.extend({
                complete: function (images) {
                    var queue = html2canvas.Parse(this[0], images, options),
                    $canvas = $(html2canvas.Renderer(queue, options)),
                    finishTime = new Date();

                    $canvas.css({ position: 'absolute', left: 0, top: 0 }).appendTo(document.body);
                    $canvas.siblings().toggle();

                    $(window).click(function () {
                        if (!$canvas.is(':visible')) {
                            $canvas.toggle().siblings().toggle();
                            throwMessage("Canvas Render visible");
                        } else {
                            $canvas.siblings().toggle();
                            $canvas.toggle();
                            throwMessage("Canvas Render hidden");
                        }
                    });
                    throwMessage('Screenshot created in ' + ((finishTime.getTime() - timer) / 1000) + " seconds<br />", 4000);
                }
            }, options));

            function throwMessage(msg, duration) {
                window.clearTimeout(timeoutTimer);
                timeoutTimer = window.setTimeout(function () {
                    $message.fadeOut(function () {
                        $message.remove();
                    });
                }, duration || 2000);
                if ($message)
                    $message.remove();
                $message = $('<div ></div>').html(msg).css({
                    margin: 0,
                    padding: 10,
                    background: "#000",
                    opacity: 0.7,
                    position: "fixed",
                    top: 10,
                    right: 10,
                    fontFamily: 'Tahoma',
                    color: '#fff',
                    fontSize: 12,
                    borderRadius: 12,
                    width: 'auto',
                    height: 'auto',
                    textAlign: 'center',
                    textDecoration: 'none'
                }).hide().fadeIn().appendTo('body');
            }
        };
    })(jQuery);
</script>
</body>
</html>

