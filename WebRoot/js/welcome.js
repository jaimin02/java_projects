	/*function validate()
	{
			if(document.forms["defaultWorkSpace"].workSpaceId.value=="-1")
				{
					alert("Please select Project Name..");
					document.forms["defaultWorkSpace"].workSpaceId.style.backgroundColor="#FFE6F7"; 
			    	document.forms["defaultWorkSpace"].workSpaceId.focus();
		    		return false;
		    	}
		return true;	
	}
*/

var commentDispOption=1;
var flag=0;
function check(str,chr)
{
	for (var i=0;i<str.length;i++)
	{
		if (str.charAt(i)==chr)
			return true;
	}
	return false;
}
/**************** For Cal.jsp **************************************************************************/
	function getMonth(month,year)
	{
		var calURL = "cal_b.do?month=" + month + "&year=" + year;			
		$.ajax(
		{			
			url: calURL,
	  		success: function(data) 
	  		{
	    		$('#calDiv').html(data); 		    		
			}	  		
		});		
		return false;	
	}
	function handleSelectClick()
	{
		var selMonth=document.getElementById("selMonth");
		var selIndex = selMonth.selectedIndex;
		var month = selMonth.options[selIndex].value;
		
		var selYear=document.getElementById("selYear");
		selIndex = selYear.selectedIndex;
		var year = selYear.options[selIndex].value;
		
		return getMonth(month,year);			
	}
	function toggle(divid,linkid,closdivid,closlink)
	{		
		var td=document.getElementById(divid);
		var closediv=document.getElementById(closdivid);
		var ds=td.style.display;
		var tl=document.getElementById(linkid);
		var closelnk=document.getElementById(closlink);
		if (ds=='none')
		{
			closediv.style.display='none';
			closelnk.innerHTML='( + )';
			td.style.display='block';
			tl.innerHTML='( - )';
		}
		else
		{
			td.style.display='none';
			tl.innerHTML='( + )';
		}		
	}
	function collect()
	{		
		var frm = document.getElementById('calsetevtattr_b');
		var str='';
		for (var i=0;i<frm.evtattr.length;i++)
		{
			if (frm.evtattr[i].checked==true)
			{
				if (str=='')
					str=frm.evtattr[i].value;
				else
					str=str+","+frm.evtattr[i].value;
			}
		}
		document.getElementById('attrlist').value=str;	
	}
	function collect1()
	{		
		var frm = document.getElementById('calsetremattr_b');
		var str='';
		for (var i=0;i<frm.remattr.length;i++)
		{
			if (frm.remattr[i].checked==true)
			{
				if (str=='')
					str=frm.remattr[i].value;
				else
					str=str+","+frm.remattr[i].value;
			}
		}
		document.getElementById('attrRemlist').value=str;	
	}
	function selectall()
	{
		var frm = document.getElementById('calsetevtattr_b');		
		for (var i=0;i<frm.evtattr.length;i++)
		{
			frm.evtattr[i].checked=true;			
		}
	}
	function unselectall()
	{
		var frm = document.getElementById('calsetevtattr_b');		
		for (var i=0;i<frm.evtattr.length;i++)
		{
			frm.evtattr[i].checked=false;			
		}
	}
	function selectall1()
	{
		var frm = document.getElementById('calsetremattr_b');		
		for (var i=0;i<frm.remattr.length;i++)
		{
			frm.remattr[i].checked=true;			
		}
	}
	function unselectall1()
	{
		var frm = document.getElementById('calsetremattr_b');		
		for (var i=0;i<frm.remattr.length;i++)
		{
			frm.remattr[i].checked=false;			
		}
	}
	function showComments(targetDiv,recUsrCode,commentParent,showWhat,fromWhere,dispType)
	{
		commentDispOption=10;
		var divstat = document.getElementById(commentParent);
		var divdisstyle = divstat.style.display;
		if(divdisstyle == 'none' && fromWhere==1)
		{
			var commURL = "ShowComments_ex.do?dispType="+dispType+"&receiverUserCode=" + recUsrCode + "&showWhat=" + showWhat;			
			$.ajax(
			{			
				url: commURL,
		  		success: function(data) 
		  		{
		    		$(targetDiv).html(data);	
				}	  		
			});			
		}
		else
		{
			if(fromWhere == 2)
			{
				var commURL = "ShowComments_ex.do?dispType="+dispType+"&receiverUserCode=" + recUsrCode + "&showWhat=" + showWhat;			
				$.ajax(
				{			
					url: commURL,
			  		success: function(data) 
			  		{
			    		$(targetDiv).html(data);	
					}	  		
				});
			}
		}
		return false;
	}
	
	function showComment(targetDiv,allCount,subId)
	{
		commentDispOption=10;
		var URL="MarkAsRead_ex.do?subjectId=" + subId;
		$.ajax(
		{			
			url: URL,
	  		success: function(data) 
	  		{
				for (var i=1;i<=allCount;i++)
				{
					if (i==targetDiv)
					{				
						document.getElementById('row'+i).style.display='';
						document.getElementById('row'+i).className='even';
						document.getElementById('rowShow'+i).style.display='';
						document.getElementById('rowReply'+i).style.display='none';
					}
					else
					{
						document.getElementById('row'+i).style.display='none';
						document.getElementById('rowShow'+i).style.display='none';						
					}
				}
			}	  		
		});		
		return false;		
	}
	function hideComment(allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			document.getElementById('rowShow'+i).style.display='none';
			document.getElementById('rowReply'+i).style.display='none';
			document.getElementById('row'+i).style.display='';			
		}
	}
	function showComment1(targetDiv,allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			if (i==targetDiv)
			{				
				document.getElementById('srow'+i).style.display='';
				document.getElementById('srowShow'+i).style.display='';
			}
			else
			{
				document.getElementById('srow'+i).style.display='none';
				document.getElementById('srowShow'+i).style.display='none';
			}
		}
		return false;		
	}
	function hideComment1(allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			document.getElementById('srowShow'+i).style.display='none';
			document.getElementById('srow'+i).style.display='';			
		}
	}
	function showComment2(targetDiv,allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			if (i==targetDiv)
			{				
				document.getElementById('drow'+i).style.display='';
				document.getElementById('drowShow'+i).style.display='';
			}
			else
			{
				document.getElementById('drow'+i).style.display='none';
				document.getElementById('drowShow'+i).style.display='none';
			}
		}
		return false;		
	}
	function hideComment2(allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			document.getElementById('drowShow'+i).style.display='none';
			document.getElementById('drow'+i).style.display='';			
		}
	}
	function reply(wsId,ndId,usrCode,desc,sId,trId)
	{	debugger;
		
		var URL="ReplyComment_ex.do?workspaceId=" + wsId + "&nodeId=" + ndId + "&userCode=" + usrCode + "&message=" + document.getElementById(desc).value + "&subjectId=" + sId;
		if (document.getElementById(desc).value.length==0)
		{
			alert('Please add some text ...');
			return false;
		}
		if (check(document.getElementById(desc).value,'\''))
		{
			alert('Invalid Characters (\') ...');
			return false;
		}
		$.ajax(
		{			
			url: URL,
	  		success: function(data) 
	  		{
	  		//	alert(data);
				$('#'+trId).html(data);
				document.getElementById(desc).value = '';
	  		}
		});
	}
	function getStructure(wsId,ndId,tarDiv)
	{
		var URL="GetNodePath_ex.do?workspaceId=" + wsId + "&nodeId=" + ndId;
		$.ajax(
		{			
			url: URL,
	  		success: function(data) 
	  		{
				$('#'+tarDiv).html(data);
	  		}
		});
	}
	function showStructure(tarDiv)
	{
		var tarDivStyle = document.getElementById(tarDiv).style.display;
		if(tarDivStyle == 'none')
		{
			$('#'+tarDiv).show('slow', function() {});
		}
		else
		{
			$('#'+tarDiv).hide('slow', function() {});
		}
	}
	
	function showReply(targetDiv,allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			if (i==targetDiv)
			{				
				document.getElementById('row'+i).style.display='';
				document.getElementById('rowReply'+i).style.display='';
				document.getElementById('rowShow'+i).style.display='none';
			}
			else
			{
				document.getElementById('row'+i).style.display='none';
				document.getElementById('rowReply'+i).style.display='none';				
			}
		}
		return false;		
	}
	function hideReply(targetDiv,allCount)
	{
		for (var i=1;i<=allCount;i++)
		{
			if (i==targetDiv)
			{								
				document.getElementById('rowShow'+i).style.display='';
				document.getElementById('rowReply'+i).style.display='none';
			}
		}				
	}
	var bgColor="#B1C7D5";
	function f(tabDivCount,tabDivInd)
	{
		for (var i=1;i<=tabDivCount;i++)
		{
			document.getElementById('tabDiv'+i).style.display="none";	
		}
		document.getElementById('tabDiv'+tabDivInd).style.display="block";
		for (var i=1;i<=tabDivCount;i++)
		{
			document.getElementById('th'+i).style.background=bgColor;	
		}
		document.getElementById('th'+tabDivInd).style.background="white";		
	}
	function delCom(subId)
	{
		var URL = "DeleteComment_ex.do?subjectId=" + subId;			
		$.ajax(
		{			
			url: URL,
	  		success: function(data) 
	  		{
				showComments('#commentsDiv',data,'commentParent');	    		
			}	  		
		});		
		return false;
	}
	function getRecComments(pageNo,recUsrCode,targetDiv,selId)
	{
		var sel=document.getElementById(selId);
		var noOfRecs=sel.options[sel.selectedIndex].value;
		var commURL = "GetRecComments_ex.do?receiverUserCode=" + recUsrCode + "&pageNo=" + pageNo + "&noOfRecords=" + noOfRecs;
		//alert(commURL);
		$.ajax(
		{			
			url: commURL,
	  		success: function(data) 
	  		{
	    		$("#"+targetDiv).html(data);	    		
			}	  		
		});		
		return false;
	}
	function markReminderAsDone(id)
	{
		var ndnm=document.getElementById('ndnm'+id).value;		
		var atnm=document.getElementById('atnm'+id).value;
		var wsnm=document.getElementById('wsnm'+id).value;
		var atvl=document.getElementById('atvl'+id).value;
		if (!confirm("Do you surely want to mark the reminder as 'Done'?" + "\n\n" +
				"Project Name: " + wsnm + "\n" + 
				"Node Name: " + ndnm + "\n" + 
				"Event: " + atnm + "\n" +
				"Due Date: " + atvl))
			return;		
		var URL = "MarkReminderAsDone_ex.do?vWorkspaceId=" + document.getElementById('ws'+id).value + "&iNodeId=" + document.getElementById('nd'+id).value + "&iAttrId=" + document.getElementById('at'+id).value + "&userCode=" + document.getElementById('us'+id).value;			
		$.ajax(
		{			
			url: URL,
			beforeSend: function()
			{				
				document.getElementById('imgD'+id).style.display='';
				document.getElementById('butD'+id).style.display='none';
				document.getElementById('butI'+id).style.display='none';
			},
	  		success: function(data) 
	  		{
				document.getElementById('imgD'+id).style.display='none';
				document.getElementById('rsD'+id).innerHTML=data;
			}	  		
		});		
		return false;
	}
	function markReminderAsIgnore(id)
	{
		var igVal;
		var i=1;
		$('#igUpto').each(function()
		{
			if (i==1)
				igVal=$(this).val();
			i=i+1;
		});
		if (igVal == '')
		{
			alert('Select Ignore Upto Date...!');
			return;
		}
		var ndnm=document.getElementById('ndnm'+id).value;		
		var atnm=document.getElementById('atnm'+id).value;
		var wsnm=document.getElementById('wsnm'+id).value;
		var atvl=document.getElementById('atvl'+id).value;
		if (!confirm("Do you surely want to mark the reminder as 'Ignore'?" + "\n\n" +
				"Project Name: " + wsnm + "\n" + 
				"Node Name: " + ndnm + "\n" + 
				"Event: " + atnm + "\n" +
				"Due Date: " + atvl))
			return;		
		var URL = "MarkReminderAsIgnore_ex.do?vWorkspaceId=" + document.getElementById('ws'+id).value + "&iNodeId=" + document.getElementById('nd'+id).value + "&iAttrId=" + document.getElementById('at'+id).value + "&userCode=" + document.getElementById('us'+id).value+"&ignoreUpto=" + igVal;			
		$.ajax(
		{			
			url: URL,
			beforeSend: function()
			{				
				document.getElementById('imgI'+id).style.display='';
				document.getElementById('butD'+id).style.display='none';
				document.getElementById('butI'+id).style.display='none';
			},
	  		success: function(data) 
	  		{
				document.getElementById('imgI'+id).style.display='none';
				document.getElementById('rsI'+id).innerHTML=data;
			}	  		
		});		
		return false;
	}
	function markReminderAsUnIgnore(id)
	{
		var ndnm=document.getElementById('ndnm'+id).value;		
		var atnm=document.getElementById('atnm'+id).value;
		var wsnm=document.getElementById('wsnm'+id).value;
		var atvl=document.getElementById('atvl'+id).value;
		if (!confirm("Do you surely want to mark the reminder as 'UnIgnore'?" + "\n\n" +
				"Project Name: " + wsnm + "\n" + 
				"Node Name: " + ndnm + "\n" + 
				"Event: " + atnm + "\n" +
				"Due Date: " + atvl))
			return;
		var URL = "MarkReminderAsUnIgnore_ex.do?vWorkspaceId=" + document.getElementById('ws'+id).value + "&iNodeId=" + document.getElementById('nd'+id).value + "&iAttrId=" + document.getElementById('at'+id).value + "&userCode=" + document.getElementById('us'+id).value;			
		$.ajax(
		{			
			url: URL,
			beforeSend: function()
			{				
				document.getElementById('imgI'+id).style.display='';
				document.getElementById('butI'+id).style.display='none';
			},
	  		success: function(data) 
	  		{
				document.getElementById('imgI'+id).style.display='none';
				document.getElementById('rsI'+id).innerHTML=data;				
			}	  		
		});		
		return false;
	}
	function showReminders(remDiv,showWhat)
	{		
		var URL='ShowReminders_ex.do?showWhat=' + showWhat;
		var opacH = '0.4';
		var opacS = '1';
		var FilterOpacH = 'alpha(opacity=65)';
		var FilterOpacS = 'alpha(opacity=100)';
		$.ajax(
		{			
			url: URL,
			beforeSend: function()
			{	
				if (showWhat==2)
					$('#sWat').parent().text('Active Reminders');					
				else if (showWhat==3)
					$('#sWat').parent().text('Ignored Reminders');
				else if (showWhat==4)
					$('#sWat').parent().text('Done Reminders');
				var w=$('#disDataRem').css('width');
				var h=$('#disDataRem').css('height');
				$('#disDataRem').html('');
				$('#disDataRem').css('border','0px');
				$('#disDataRem').html('<img border="0px" title="Loading" alt="Loading" src="images/loading.gif" height="40px" width="40px" >');
				
			},
	  		success: function(data) 
	  		{
				$('#'+remDiv).html(data);
			}	  		
		});
	}	
	function commonJQeruAJAXCall(actionName,paraList,targetId,pexist)
	{
		var commURL = actionName;
		if(pexist == 'Y')
		{
			var pList= document.getElementById(paraList).value;
			pList = String(pList);
			if(pList.length > 0)
			{
				commURL+=pList
			}
		}
		$.ajax
		({			
				url: commURL,
				beforeSend: function()
				{
	    		$("#"+targetId).html('<img border="0px" title="Loading" alt="Loading" src="images/loading.gif" height="40px" width="40px" >');				
				},
		  		success: function(data) 
		  		{
		    		$("#"+targetId).html(data);	    		
				}	  		
		});	
	}
	function onPopupClose()
	{
		var urlDTL =  $(location).attr('href');
		urlDTL = String(urlDTL);
		var newURL = urlDTL.split("?");
		$(location).attr('href',newURL[0]);
	}
	