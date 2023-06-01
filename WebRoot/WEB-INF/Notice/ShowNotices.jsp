<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript">
	$(document).ready(function() {
		$("#startDate1").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		$("#endDate1").datepicker({minDate: 0, maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
		});
	
</script>

<script type="text/javascript">
	function PageOnLoad()
	{	
		document.getElementById('abc').contentEditable='true';
	}
	var vs;
	vs=0;
	function toggleVs()
	{		
		if (vs==0)
		{
			document.getElementById('description').value=document.getElementById('abc').innerHTML;
			document.getElementById('abc').style.display='none';
			document.getElementById('description').style.display='block';
			for (var i=0;i<document.ctrls.length;i++)
				document.ctrls[i].style.display='none';
			document.getElementById('fname').style.display='none';
			document.getElementById('fsize').style.display='none';
			document.getElementById('fcolor').style.display='none';
			vs=1;
		}
		else
		{
			document.getElementById('abc').innerHTML=document.getElementById('description').value;
			document.getElementById('description').style.display='none';
			document.getElementById('abc').style.display='block';
			for (var i=0;i<document.ctrls.length;i++)
				document.ctrls[i].style.display='';
			document.getElementById('fname').style.display='';
			document.getElementById('fsize').style.display='';
			document.getElementById('fcolor').style.display='';
			vs=0;
		}	
	}
	function bold()
	{
		document.execCommand('Bold',false,'');
	}
	function italic()
	{
		document.execCommand('Italic',false,'');
	}
	function underline()
	{
		document.execCommand('Underline',false,'');
	}
	function left()
	{
		document.execCommand('justifyleft',false,'');
	}
	function center()
	{	
		document.execCommand('justifycenter',false,'');
	}
	function right()
	{
		document.execCommand('justifyright',false,'');
	}
	function undo()
	{
		document.execCommand('undo',false,'');
	}
	function redo()
	{
		document.execCommand('redo',false,'');
	}
	function link()
	{
		var u=prompt('Enter URL for the link?','http://');
		document.execCommand('CreateLink',false,u);
	}
	function foreColor()
	{
		var col=document.getElementById('fcolor').value;
		document.execCommand('foreColor',false,col);
	}
	function fontSize()
	{
		var col=document.getElementById('fsize').value;
		document.execCommand('fontSize',false,col);
	}
	function fontName()
	{
		var col=document.getElementById('fname').value;
		document.execCommand('fontName',false,col);
	}
	</script>
<script type="text/javascript">	
	function check(str,chr)
	{
		for (var i=0;i<str.length;i++)
		{
			if (str.charAt(i)==chr)
				return true;
		}
		return false;
	}
	function trim(str)
	{
	   	str = str.replace( /^\s+/g, "" );// strip leading
		return str.replace( /\s+$/g, "" );// strip trailing
	}
	var editmode=0;
	var usertypes='<s:iterator value="userTypes"><s:property value="userTypeName"/>,</s:iterator>';
	function edit(noticeno,subject,description,sDate,eDate,isActive,usertypecodenames)	
	{			
		editmode=1;		
		var frm=document.forms['AddNotice'];
		frm.New.style.visibility='visible';
		frm.submit.value="Save";
		frm.action="SaveNotice.do";
		frm.noticeno.value=noticeno;
		frm.subject.value=subject;					
		document.getElementById('startDate1').value=sDate;
		document.getElementById('endDate1').value=eDate;			
		if (isActive=='Y')
		{			
			frm.active.checked='checked';
		}
		else
		{			
			frm.active.checked='';
		}		
		document.getElementById('description').value=document.getElementById(description).value;				
		document.getElementById('abc').innerHTML=document.getElementById(description).value;
		document.getElementById('NotAll').checked='checked';
		var arrusertypecodenames=usertypes.split(",");		
		for (var i=0;i<arrusertypecodenames.length-1;i++)
		{
			document.getElementById(arrusertypecodenames[i]).checked='';						
		}		
		arrusertypecodenames=usertypecodenames.split("<br>");
		for (var i=0;i<arrusertypecodenames.length;i++)
		{
			document.getElementById(arrusertypecodenames[i]).checked='checked';			
		}		
		document.getElementById('attach').style.display='block';		
		return false;
	}
	
	function validate()
	{
		document.getElementById('description').value=document.getElementById('abc').innerHTML;
		var str=document.getElementById('description').value;		
		str=trim(str);
		//alert(str);
		str=str.replace("<a href","<a target=\"_blank\" href");
		//alert(str);
		document.getElementById('description').value=str;
		var frm=document.forms['AddNotice'];		
		if (frm.subject.value=='')
		{
			alert('Please Insert Subject...');
			frm.subject.style.backgroundColor="#FFE6F7";
			frm.subject.focus();
			return false;
		}		
		if (str=='')
		{
			alert('Please Insert Description...');			
			return false;
		}						
		if (check(str,'\'') == true)
		{
			alert('(\') is an illegal character in description...');			
			return false;
		}	
		if (document.getElementById('startDate1').value=='')
		{
			alert('Please Insert Start Date...');
			//frm.startDate.style.backgroundColor="#FFE6F7";			
			return false;
		}		
		if (document.getElementById('endDate1').value=='')
		{
			alert('Please Insert End Date...');
			//frm.endDate.style.backgroundColor="#FFE6F7";
			return false;
		}
		if (document.forms.AddNotice.AllUserTypes[1].checked==true)
		{						
			var arrusertypecodenames=usertypes.split(",");
			var sel=false;		
			for (var i=0;i<arrusertypecodenames.length-1;i++)
			{
				if (document.getElementById(arrusertypecodenames[i]).checked==true)
					sel=true;						
			}
			if (sel==false)
			{
				alert('Please select user type...');
				return false;
			}
		}		
		return true;
	}	
	
	function backtonew()
	{		
		editmode=0;
		document.getElementById('attach').style.display='none';
		document.getElementById('All').checked='checked';	
		document.getElementById('abc').innerHTML='';
		document.getElementById('description').value='';
		document.getElementById('active').checked='checked';	
		var frm=document.forms['AddNotice'];		
		frm.New.style.visibility='hidden';
		frm.submit.value="Add";
		frm.action="AddNotice.do";
		frm.noticeno.value='';
		frm.subject.value='';
		frm.active.checked='';			
		var arrusertypecodenames=usertypes.split(",");		
		for (var i=0;i<arrusertypecodenames.length-1;i++)
		{
			document.getElementById(arrusertypecodenames[i]).checked='';						
		}		
	}
	</script>
</head>
<body>
<div id="errors"><s:actionerror /> <s:actionmessage /></div>
<br />
<div align="center"><img
	src="images/Header_Images/Master/Notice.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;">

<div
	style="padding-left: 3px; width: 950px; border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center"><br>
<div align="left" id="form"><s:form action="AddNotice"
	method="post" enctype="multipart/form-data" name="AddNotice">
	<input type="hidden" name="noticeno">
	<table width="100%" cellpadding="0px" cellspacing="0px;">
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Subject</td>
			<td><input type="text" name="subject"></td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Description</td>
			<td>
			<div id="toolbar"
				style="width: 500px; height: 50px; border: 1px solid;"><img
				name="ctrls" alt="bold" onclick="bold();" src="images/bold.gif">
			<img name="ctrls" alt="italic" onclick="italic();"
				src="images/italic.gif"> <img name="ctrls" alt="underline"
				onclick="underline();" src="images/underline.gif"> <img
				name="ctrls" alt="left" onclick="left();" src="images/j_left.gif">
			<img name="ctrls" alt="center" onclick="center();"
				src="images/j_center.gif"> <img name="ctrls" alt="right"
				onclick="right();" src="images/j_right.gif"> <img name="ctrls"
				alt="undo" onclick="undo();" src="images/undo.gif"> <img
				name="ctrls" alt="redo" onclick="redo();" src="images/redo.gif">
			<img name="ctrls" alt="hyperlink" onclick="link();"
				src="images/link.png"> <img alt="view source"
				onclick="toggleVs();" src="images/mode.png"> <select
				style="min-width: 20px;" id="fcolor" onchange="foreColor();">
				<option value="black">black</option>
				<option value="silver">silver</option>
				<option value="gray">gray</option>
				<option value="maroon">maroon</option>
				<option value="red">red</option>
				<option value="purple">purple</option>
				<option value="green">green</option>
				<option value="lime">lime</option>
				<option value="olive">olive</option>
				<option value="navy">navy</option>
				<option value="blue">blue</option>
				<option value="teal">teal</option>
				<option value="white">white</option>
				<option value="fuchsia">fuchsia</option>
				<option value="yellow">yellow</option>
				<option value="aqua">aqua</option>
			</select> <select style="min-width: 5px;" id="fsize" onchange="fontSize();">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
			</select> <select style="min-width: 20px;" id="fname" onchange="fontName();">
				<option value="Arial">Arial</option>
				<option value="Comic Sans MS">Comic Sans MS</option>
				<option value="Courier New">Courier New</option>
				<option value="Georgia">Georgia</option>
				<option value="Tahoma">Tahoma</option>
				<option value="Times New Roman">Times New Roman</option>
				<option value="Verdana">Verdana</option>
			</select></div>
			<textarea id="description" name="description"
				style="display: none; width: 500px; height: 200px; border: 1px solid;">
						</textarea>
			<div id="abc"
				style="width: 500px; height: 200px; border: 1px solid; overflow: auto;">
			</div>
			</td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Attachment</td>
			<td><input type="file" name="attachment"></td>
		</tr>
		<tr align="left">
			<td width="25%"></td>
			<td align="left">
			<div id="attach" style="display: none"><input type="radio"
				name="attach" value="remove" id="removeRadio"><label
				for="removeRadio">Remove</label> <input type="radio" name="attach"
				value="upload" id="uploadRadio"><label for="uploadRadio">Upload
			Again</label> <input type="radio" name="attach" value="keep"
				checked="checked" id="keepRadio"><label for="keepRadio">Keep
			Existing</label></div>
			</td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Start Date</td>
			<td><input type="text" name="startDate" readonly="readonly"
				id="startDate1"> (YYYY/MM/DD)</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">End Date</td>
			<td><input type="text" name="endDate" readonly="readonly"
				id="endDate1"> (YYYY/MM/DD)</td>
		</tr>
		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">User Types</td>
			<td class="title"><input type="radio" name="AllUserTypes"
				value="All" id="All" checked="checked"><label for="All">All
			User Types</label></td>
		</tr>
		<tr align="left">
			<td></td>
			<td class="title"><input type="radio" name="AllUserTypes"
				value="NotAll" id="NotAll"><label for="NotAll">Select
			User Types</label> <s:iterator value="userTypes">
				<input type="checkbox" name="<s:property value="userTypeName"/>"
					id="<s:property value="userTypeName"/>" value="Y">
				<s:property value="userTypeName" />
			</s:iterator></td>
		</tr>

		<tr align="left">
			<td class="title" align="right" width="25%"
				style="padding: 2px; padding-right: 8px;">Active</td>
			<td class="title"><input type="checkbox" name="active"
				id="active" value="active" checked="checked"></td>
		</tr>
		<tr align="left">
			<td></td>
			<td><input type="submit" value="Add" class="button" id="submit"
				onclick="return validate();">&nbsp; <input type="reset"
				class="button" value="New" id="New" onclick="backtonew();"
				style="visibility: hidden"></td>
		</tr>
	</table>
</s:form></div>
<br>
<br>
<div style="border: 1px solid black; width: 940px;">
<div id="grid"
	style="overflow: auto; width: 940px; border: none; height: 400px;">
<%int srno=1;%>
<table align="center" width="100%" class="datatable">
	<tr>
		<th>Sr.No.</th>
		<th>Subject</th>
		<th>Description</th>
		<th>Attachment</th>
		<th>Start date</th>
		<th>End date</th>
		<th>User Types</th>
		<th>Active</th>
		<th>Edit</th>
	</tr>
	<s:iterator value="allNotices" status="status">
		<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
			<td><%=srno++%></td>
			<td><s:property value="Subject" /></td>
			<td style="display: none"><textarea id='<%="desc" + srno%>'><s:property
				value="Description" /></textarea></td>
			<td><s:property value="Description.replaceAll('\r\n','<br>')"
				escape="false" /></td>
			<td><s:if test="Attachment.trim() != ''">
				<a target="_blank"
					href="openfile.do?fileWithPath=<s:property value="attachmentsFolderPath"/>/<s:property value="Attachment"/>"><s:property
					value="Attachment" /></a>
			</s:if></td>
			<td><s:date name="Startdate" format="dd-MMM-yyyy HH:MM" /></td>
			<td><s:date name="EndDate" format="dd-MMM-yyyy HH:MM" /></td>
			<td><s:property value="UserTypeCodeNames" escape="yes" /></td>
			<td style="text-align: center"><s:property value="IsActive" /></td>
			<td>
			<div align="center"><a href="#" title="Edit"
				onclick="return edit('<s:property value="NoticeNo"/>','<s:property value="Subject"/>','<%="desc"+srno%>','<s:date name="Startdate" format="yyyy/MM/dd" />','<s:date name="EndDate" format="yyyy/MM/dd" />','<s:property value="IsActive"/>','<s:property value="UserTypeCodeNames"/>');">
			<img border="0px" alt="Edit" src="images/Common/edit.png"
				height="18px" width="18px"> </a></div>
			</td>
		</tr>
	</s:iterator>
</table>
</div>
</div>
</div>
</div>
</body>
</html>