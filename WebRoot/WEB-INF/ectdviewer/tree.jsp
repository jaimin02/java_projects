<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
	margin: 0px;
	padding: 0px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type='text/javascript'>	

		  var NodeUrl="";						  
		  $(document).ready(function()
		  {			 
		  	$("#newtree").dynatree(
		  	{
		  		persist: false,
		      	checkbox: false,
		        selectMode: 0,
		  	  	clickFolderMode: 3,
		  	  	minExpandLevel: 1,		  	      	
		       	onActivate: function(dtnode) 
		       	{		
		       		if(dtnode.data.title.indexOf('<')==-1)
		       		    $(".nodedetail").html(dtnode.data.title);
		       		else
		  				$(".nodedetail").html(dtnode.data.title.substring(0,dtnode.data.title.indexOf('<')));	
		  			NodeUrl='';
					$("#resultDiv").height(0);
		  			$("#nopreview").height("100%");
		  			$("#tooltipdata").html('');
		       		//debugger;	       			       				       		
		  			if(dtnode.data.url)
		  			{	
		  				NodeUrl='';
		  				var filewithpath=dtnode.data.url.substring(dtnode.data.url.indexOf('fileWith'));

		  				var ext=filewithpath.substring(filewithpath.lastIndexOf('.'));
						var isSPL=false;	
						if(ext=='.xml' && dtnode.parent.data.title=='m1-14-1-3-draft-labeling-text22')
						{
							    NodeUrl=dtnode.data.url;	
								isSPL=true;
								return false;
							
						}
		  											
		  				if(ext!='.pdf' && ext.length<=8 && !isSPL)
		  				{
		  					//$("#tooltipdata").show();
		  					//$("#resultmainDiv").animate({"height": "72%", "width": "100%px"},"slow", function(){});	
		  						  				
		  					var host = "http://"+window.location.hostname;
		  					var port=window.document.location.port;
		  					var serverpath=host+":"+port;
		  					//alert(serverpath);
		//window.open(serverpath+"/docmgmtandpub"+filewithpath.substring(38),'_blank');
								
							var fullurl="chrome-extension://hehijbfgiekmjfkfjpbkbammjbdenadd/iecontainer.html#url="+serverpath+"/docmgmtandpub"+filewithpath.substring(38);	
							window.open(serverpath+"/docmgmtandpub"+filewithpath.substring(38),'_blank');
							
		  					return false;
			  				
		  				}
		  				
			  			if(fullscreen)
			  			{
			  				fullscreen=false;
			  				$("#tooltipdata").animate({"height" : "toggle"}, { duration:0});
			  				if($("#resultmainDiv").css("height")=="72%")
			  				{
			  					$("#resultmainDiv").animate({"height": "93%", "width": "100%px"},0, function(){});
			  				}
			  				else
			  				{
			  					$("#resultmainDiv").animate({"height": "72%", "width": "100%px"},0, function(){});														
			  				}
			  				
			  			}
			  			$("#nopreview").height(0);
		  				$("#resultDiv").height("100%");		
		  				//$(".nodedetail").append(dtnode.parent.data.title);			  			
						window.frames['resultDiv'].location=dtnode.data.url;
		  				$("#echoActive").text(dtnode.data.title);
		  			}		  			
					dtnode.toggleSelect();
		  		}
		      });
		  });

		  String.prototype.replaceAll = function (targetString, subString) {
			  var inputString = this;
			  inputString = inputString.replace(new RegExp(targetString, 'g'), subString); //replace a string globally and case sensitive
			  
			 return (inputString);
			 };
		  //Code For Tooltip		  
		  $(document).ready(function(){
				//create Bubble Popups for each "button" element with a loading image...
				/*$('.button').CreateBubblePopup({
												position: 'bottom',
												align: 'left',
												innerHtml: '<img src="images/loading.gif" style="border:0px;" />loading!',
												innerHtmlStyle: { color:'#FFFFFF', 'text-align':'center' },
												themeName: 'all-black',
												themePath: 'images/jquerybubblepopup-theme'
										  	  });

				*/	
				$('.button').click(function(){
						//$('#tooltipdata').html('');						
						var button = $(this);									
						//var putData=this.name;	
						//debugger;				
							
						putData=button.attr("action");																				
						$.get(putData, function(data) {
								//var seconds_to_wait = 1;
								/*function pause(){
									var timer = setTimeout(function(){
										seconds_to_wait--;
										if(seconds_to_wait > 0){
											pause();
										}else{					
											$('#tooltipdata').html(data);								
											button.SetBubblePopupInnerHtml(data, false);			
										};
									},500);
								};pause();
								*/							

							$('#tooltipdata').html(data);
						
							NodeUrl = NodeUrl.replaceAll("/", "\\");
							NodeUrl=NodeUrl.replace("fileWithPath","File Location");
							$('#tooltipdata').append("<font color='blue'>"+NodeUrl.substring((NodeUrl.indexOf('&')+1))+"</font>");
						
						}); 
				}); 

			});
</script>
</head>
<body>
<div id="newtree"><s:property value="treeHtml" escape="false" /></div>
</body>