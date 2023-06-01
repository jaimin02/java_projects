<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%	
	response.addHeader("Pragma","no-cache"); 
	response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
	response.addHeader("Cache-Control","pre-check=0,post-check=0"); 
	response.setDateHeader("Expires",0);
%>
<html>
<head>

<link
	href='<%=request.getContextPath() %>/js/jquery/jquery-ui-1.8.0.min.css'
	rel='stylesheet' type='text/css' />

<script
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'
	type='text/javascript'></script>

<style>
#nodeFrame {
	width: 100%;
	height: 100%;
	border: none;
}

#framBodyDiv {
	/*width: 1000px;*/
	height: 100%;
	border: 0px;
}

#framBodyDiv>div {
	display: inline-block;
}

#resizable {
	/*width: 325px;*/
	height: 543px;
	border: 1px solid #ccc;
}

#resizable:hover {
	border-right-color: #304040;
}

#resizable2 {
	/*width: 658px;*/
	height: 543px;
	border-radius: 1px;
	border: 1px solid #ccc;
}

#resizable2:hover {
	
}

.ui-state-default,.ui-widget-content .ui-state-default {
	background-color: red !   importatnt;
}

#attributeFrame {
	width: 100%;
	height: 100%;
	border: none;
}

#slider {
	cursor: pointer;
	border: none;
	height: 1px;
	color: gray;
	background-color: gray;
}

.seperator {
	left: 1px;
	font-size: 20px;
	position: relative;
	top: 50%;
	height: 150px;
	text-decoration: none;
	color: gray;
	content: '';
	cursor: col-resize;
	letter-spacing: -5px;
	z-index: 2000000;
}

.handlenew {
	width: 50px;
	height: 50px;
	background-color: gray;
	z-index: 3000000;
}

#allWorkspace {
	background: gray;
	padding: 0px;
	position: absolute;
	color: blue;
	border: 2px solid gray;
	    z-index: 10;
    left: 16px;
    width: 200px;
    top: 153px !important;
}


#allWorkspace:hover {
	
}

#allWorkspace ul {
	max-height: 350px;
	width: 300px;
	overflow: auto;
	background: #e7e7e7;
	padding: 1px;
	width: auto;
	color: blue;
	cursor: pointer;
}

#allWorkspace ul li {
	background: #5B89AA;
	padding: 5px;
	border: 1px solid white;
	color: white;
}

#allWorkspace ul li:hover {
	position: relative;
	top: -1px;
	-webkit-box-shadow: 0px 0px 87px 6px rgba(223, 226, 230, 1);
	-moz-box-shadow: 0px 0px 87px 6px rgba(223, 226, 230, 1);
	box-shadow: 0px 0px 87px 6px rgba(223, 226, 230, 1);
	background: #5B89BB;
	border: 1px solid white;
	color: white;
}

#openAllProjects {
	cursor: pointer;
}


a {
color:#000 !important;
text-decoration:none; 
} 

.datatable td a:hover{
color:blue !important;
}


</style>
</head>
<body>

 <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script>
   var $q = jQuery.noConflict(true);
</script> 
<!-- <div id="slider" style="height: 4px; width: 99%;"></div> -->
<div id="allWorkspace"></div>
<script>
$(document).ready(function(){
	
	 if (window.IsDuplicate()) {
		 //debugger;
        swal ( "Oops" ,  "This is duplicate window\n\n Closing..." ,  "error" );
        window.close();
        var out="Welcome.do";
		location.href=out; 
      /*  var style = document.createElement("style");
		    style.innerHTML="body { display:none !important; }";
		    document.getElementsByTagName("HEAD")[0].appendChild(style)*/  
		 
//         window.stop();
    }   
	//debugger;
	var newtop = $('#allWorkspace').position().top + 20;
	$('#allWorkspace').css('top', newtop + 'px');	

	$('#allWorkspace').hide();
	$('#attributeFrame').load(function(){
       var iframe = $('#attributeFrame').contents();
       
       iframe.find("#unlockbtn").click(function(){
            
             /*  var iframe = document.getElementById('nodeFrame');
              iframe.src = iframe.src; */
       
       });
	//debugger;
//var iframe = $('#attributeFrame').contents();

     /* iframe.find("#SaveNodeAttrAction_submitBtn").click(function(){
       	
       	setTimeout(function(){
       		var iframe = document.getElementById("nodeFrame");
       		iframe.src = iframe.src;
       	}, 2000);
       	
       });*/
   }); 


});
$(function() {

$( "#slider" ).slider({

 range: "min",
 min: 0,
 max: 1000,
 value: 332,     
 slide: function( event, ui ) {
	$("#resizable").css('width',ui.value-9);
	$("#resizable2").css('width',992-ui.value);
	// $(".ui-slider-handle").css("cursor","-webkit-grabbing");
 },
 change:function(event,ui){
		
     
		$("#resizable").css('width',ui.value-9);
		$("#resizable2").css('width',992-ui.value);
  },
  start:function( event, ui ) {
	  
	  //$(this).css("-webkit-box-shadow","0 0 10px 4px #457E8E");
	   //$(".ui-slider-handle").css("-webkit-box-shadow","0 0 10px 4px black");
	  
  },
  stop:function( event, ui ) {
	   
	//   $(this).css("-webkit-box-shadow","");
	 //  $(".ui-slider-handle").css("-webkit-box-shadow","");
	  // $(".ui-slider-handle").css("cursor","");
	  // $(".ui-slider-handle").css("width","7px");
	  // $(".ui-slider-handle").find( "span:last" ).remove();
	   
	  
	 
  }
});


$(".ui-slider-handle").css("cursor","-webkit-grabbing");
$(".ui-slider-handle").css("outline",0);

$(".ui-slider-handle").css("text-decoration","none");

});
(function ($q) {
	$q.fn.DuplicateWindow = function () {
        var localStorageTimeout = (5) * 1000; // 15,000 milliseconds = 15 seconds.
        var localStorageResetInterval = ((1/2) * 1000); // 10,000 milliseconds = 10 seconds.
        var localStorageTabKey = 'my-application-browser-tab';
        var sessionStorageGuidKey = 'browser-tab-guid';

        var ItemType = {
            Session: 1,
            Local: 2
        };

        function setCookie(name, value, days) {
            var expires = "";
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                expires = "; expires=" + date.toUTCString();
            }
            document.cookie = name + "=" + (value || "") + expires + "; path=/";
        }
        function getCookie(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1, c.length);
                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
            }
            return null;
        }

        function GetItem(itemtype) {
            var val = "";
            switch (itemtype) {
                case ItemType.Session:
                    val = window.name;
                    break;
                case ItemType.Local:
                    val = decodeURIComponent(getCookie(localStorageTabKey));
                    if (val == undefined)
                        val = "";
                    break;
            }
            return val;
        }

        function SetItem(itemtype, val) {
            switch (itemtype) {
                case ItemType.Session:
                    window.name = val;
                    break;
                case ItemType.Local:
                    setCookie(localStorageTabKey, val);
                    break;
            }
        }

        function createGUID() {
            this.s4 = function () {
                return Math.floor((1 + Math.random()) * 0x10000)
                  .toString(16)
                  .substring(1);
            };
            return this.s4() + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + this.s4() + this.s4();
        }

        /**
         * Compare our tab identifier associated with this session (particular tab)
         * with that of one that is in window name Storage (the active one for this browser).
         * This browser tab is good if any of the following are true:
         * 1.  There is no cookie Storage Guid yet (first browser tab).
         * 2.  The window name Storage Guid matches the cookie Guid.  Same tab, refreshed.
         * 3.  The window name Storage timeout period has ended.
         *
         * If our current session is the correct active one, an interval will continue
         * to re-insert the window name Storage value with an updated timestamp.
         *
         * Another thing, that should be done (so you can open a tab within 15 seconds of closing it) would be to do the following (or hook onto an existing onunload method):
         *      window.onunload = () => {
                        localStorage.removeItem(localStorageTabKey);
              };
         */
        function TestIfDuplicate() {
            //console.log("In testTab");
            var sessionGuid = GetItem(ItemType.Session) || createGUID();
            SetItem(ItemType.Session, sessionGuid);

            var val = GetItem(ItemType.Local);
            var tabObj = (val == "" ? null : JSON.parse(val)) || null;
            console.log(val);
            console.log(sessionGuid);
            console.log(tabObj);

            // If no or stale tab object, our session is the winner.  If the guid matches, ours is still the winner
            if (tabObj === null || (tabObj.timestamp < (new Date().getTime() - localStorageTimeout)) || tabObj.guid === sessionGuid) {
                function setTabObj() {
                    //console.log("In setTabObj");
                    var newTabObj = {
                        guid: sessionGuid,
                        timestamp: new Date().getTime()
                    };
                    SetItem(ItemType.Local, JSON.stringify(newTabObj));
                }
                setTabObj();
                setInterval(setTabObj, localStorageResetInterval);//every x interval refresh timestamp in cookie
                return false;
            } else {
                // An active tab is already open that does not match our session guid.
                return true;
            }
        }

        window.IsDuplicate = function () {
            var duplicate = TestIfDuplicate();
            //console.log("Is Duplicate: "+ duplicate);
            return duplicate;
        };

        $q(window).on("beforeunload", function () {
            if (TestIfDuplicate() == false) {
                SetItem(ItemType.Local, "");
            }
        })   
	}
	$q(window).DuplicateWindow();
}(jQuery));

window.onload = function exampleFunction() { 
	
	document.getElementById("nodeFrame").contentWindow.document.body.onclick = 
    	function() {
    	document.getElementById("loadingpopup").style.display='block';
    	checkLoaded();
    	}

	$.ajax
	 ({
		 url: 'Welcome_ex.do',
			success: function(data) 
	  		{	
				//debugger;
				if(data=='false'){
	  				alert("You don't have any rights to work on this project.");
	  	  			var out="Welcome.do";
	  	  			location.href=out;
	  	  		}	
	  		}	
	  }); 
} 

function checkLoaded() {
//	debugger;
	  if(document.readyState === "complete" || document.readyState === "interactive"){
		  document.getElementById("loadingpopup").style.display='none';
	  }
	}

	
  
  function projectRights(){
	  alert("You don't have permission to open projects.");
	  var out="Welcome.do";
	  location.href=out;
  }

  </script>
<%-- <%
 ActionContext.getContext().getSession().put("ws_id",request.getParameter("ws_id").toString());
 %> --%>
    <%String str=request.getParameter("ws_id");%>
 <s:if test ="#session.usertypename == 'BD' || #session.usertypename == 'Archivist' 
 				|| #session.usertypename == 'IT' || #session.usertypename == 'Inspector'">
 		<script type="text/javascript">
 			projectRights();
		</script>
 </s:if>
 <s:else>
 <div id="loadingpopup" style="width: 50px;z-index: 1;position: absolute;top: 262.5px;left: 838.5px;
			padding-top: 0px;display:none">
   <center><img style="margin-top:100px" src="images/loading.gif" alt="loading ..." /></center>
		</div>
 <div class="container-fluid">
          
<div id="framBodyDiv">
<div class="col-md-4" id="resizable" style="padding:0px;"><iframe name="nodeFrame" id="nodeFrame"
	frameborder="0" align="left" scrolling="no"> </iframe></div>

<div class="col-md-8" id="resizable2" style="padding:0px;"><iframe name="attributeFrame"
	id="attributeFrame" scrolling="yes" frameborder="0"
	src="workspaceNodeAttrAction.do" height="530px" align="left">
</iframe></div>
</div>
</div>

</s:else>


<script type="text/javascript">

/* document.getElementById("nodeFrame").src='WorkspaceOpenNodes.do'; */
 
 document.getElementById("nodeFrame").src='WorkspaceOpenNodes.do?wsid=<%=str%>'; 

 </script>


<script>
  $(function() {
	  //debugger;
  	$.ajax
	 ({
		 url: 'getUserWorkspaceDetail_b.do',
			success: function(data) 
	  		{	
			  	$('#allWorkspace').html(data);	
	  		}	
 	  });
	 var totalWidth=1000; 
    $('#resizable1').resizable({
      start: function(event, ui) {
        $(this).css('pointer-events','none');
        
         },
      resize:function(event,ui){

        	 if($(this).width()>=500)
 			 {
 				$(this).css('width','500px');
				return;
 	 		  }
             var secifrmawidth=975-$(this).width();
			$("#resizable2").css('width',secifrmawidth);
			$("#resizable2").css('height',$(this).height());
			$("#tree").css('height',$(this).height());
			
			var temp = parent.document.getElementsByTagName("iframe");
			var innerDoc = temp[0].contentDocument || temp[0].contentWindow.document;

		//	innerDoc.getElementById("ui-dynatree-id-"+gNodeId).style.backgroundColor="#990011";

			var nid=innerDoc.getElementById("tree");
			nid.style.height=($(this).height()-90) +"px";
		
			
      },   
      stop: function(event, ui) {
        $(this).css('pointer-events','auto');
      }
  });
  });


	  </script>


</body>
</html>