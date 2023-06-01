<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>


<script type="text/javascript">
		function selUsrColumn(str,colNo,noOfCols)
		{
			
			var cnt=document.getElementById(str).value;
			var sel=document.getElementById(str + "-" + colNo).checked;
			for (var i=0;i<cnt;i++)
			{		
				if (i%noOfCols==colNo)
				{
					document.getElementById(str + '_' + (i+1)).checked=sel;
				}
			}
		}
		function selAlUsr(str)
		{
			var cnt=document.getElementById(str).value;
			var sel=document.getElementById(str).checked;
			for (var i=1;i<=cnt;i++)
			{
				document.getElementById(str + '_' + i).checked=sel;
			}
		}

		function getUsrs(objUser)
		{	
		
			var args = '';
			var usrType = '';
			var str='';
			var value = objUser.options[objUser.selectedIndex].value;  
			
			divId="#"+objUser.getAttribute("name");			
			var wsId = '';
			wsId = document.getElementById("workSpaceId").value;
			var ndId = '';
			ndId = document.getElementById("nodeId").value;

			var selUsers = '';
			
			
			var modeVal='';
			modeVal=objUser.getAttribute("name");
			if(modeVal == "Authors")
			{
				//selUsers = document.getElementById("authUsers").value;
				
			}
			else if(modeVal == "Reviewers")
			{
				//selUsers = document.getElementById("revUsers").value;
			}
			else if(modeVal == "Approvers")
			{
				//selUsers = document.getElementById("approvUsers").value;
				
			}
			//usrType = document.getElementById('usrTyp').value;
			//var disableUserList = document.getElementById("disableUserList").value;
			
			args = "usrTyp="+value+"&workSpaceId="+wsId+"&nodeId="+ndId+"&mode="+modeVal;
		
			$.ajax({	
				url: 'SelectUsers_ex.do?' + args,
				beforeSend: function()
				{ 
					if (objUser.options[objUser.selectedIndex].value == '-1')
					{
						$(divId).html("<b>Please select User Type</b>");
						return false;
					}
					$(divId).html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
				},
		  		success: function(data) 
		  		{
			  		$(divId).html(data);
		    	}	 
					  	 		
			});
		}
		
		function checkData(mode)
		{
			var str='';
			var wsId = '';
			var ndId = '';
			var disableUser='';
			var selUsers='';
			if(mode == "Authors")
			{
				selUsers = document.getElementById("authUsers").value;
				
			}
			else if(mode == "Reviewers")
			{
				selUsers = document.getElementById("revUsers").value;
			}
			else if(mode == "Approvers")
			{
				selUsers = document.getElementById("approvUsers").value;
				
			}
			for (var i=0;document.forms['frmUsrSelectForTab'].usrSelect.length!=null && i<document.forms['frmUsrSelectForTab'].usrSelect.length;i++)
			{
				
				if (document.forms['frmUsrSelectForTab'].usrSelect[i].checked==true)
				{
					if (str=='')
						str=document.forms['frmUsrSelectForTab'].usrSelect[i].value;
					else
						str+=","+document.forms['frmUsrSelectForTab'].usrSelect[i].value;
					
				}
			}
			
			if (document.forms['frmUsrSelectForTab'].usrSelect.length==null)
			{
				if (document.forms['frmUsrSelectForTab'].usrSelect.checked==true)
				{
					str=document.forms['frmUsrSelectForTab'].usrSelect.value;			
				}
			}
			/*
			if (str=='')
			{
				alert('Please select users ...');
				return;
			}
			*/
			wsId = document.getElementById("workSpaceId").value;
			ndId = document.getElementById("nodeId").value;
			var userType = $("#authorTyp").val();
			args="userDetails="+str+"&workSpaceId="+wsId+"&nodeId="+ndId+"&modeVal="+mode+"&selctdUsers="+selUsers+"&userType="+userType;

			$.ajax (
					{							
					url:'SaveAuthors_ex.do?'+ args,
					beforeSend: function()
					{ 
						$(divId).html("<br/><img src=\"images/loading.gif\" alt=\"loading ...\" />");
					},		
			  		success: function(data) 
			  		{
				  		alert(data);
						$(divId).html("");
						document.getElementById("authorTyp").value = "-1";
						$('#'+mode.toLowerCase()+'Tab').click();
					}	  		
				});
		}
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<br />
<div align="center">
<div align="center">
<div align="center"><s:if test="datamode.equalsIgnoreCase('user')">
	<s:form id="showUsersForm" name="showUsersForm" action="SaveAuthors_ex">
		<input type="hidden" id="mode" name="mode"
			value="<s:property value="mode"/>"></input>
		<input type="hidden" id="workSpaceId" name="workSpaceId"
			value="<s:property value="workSpaceId"/>"></input>
		<input type="hidden" id="nodeId" name="nodeId"
			value="<s:property value="nodeId"/>"></input>
		<table align="center" width="100%" cellspacing="0">
			<tr>
				<td class="title" align="right" width="25%"
					style="padding: 2px; padding-right: 6px;">Select User
				Type&nbsp;</td>
				<td align="left"><select name="<s:property value="mode"/>"
					id="authorTyp" style="min-width: 0px;" onchange="getUsrs(this);">
					<option value="-1">Select Type</option>
					<s:iterator value="userTypes">
						<option id="<s:property value="userTypeCode"/>"
							value="<s:property value="userTypeCode"/>"><s:if
							test="userTypeName == 'WA'">Workspace Admin</s:if> <s:if
							test="userTypeName == 'WU'">Workspace User</s:if></option>
					</s:iterator>
				</select></td>
			</tr>
		</table>
	</s:form>
	<div id="<s:property value="mode"/>" align="center"></div>
	<br />
	<s:if test="wsUsrAdtTrlLst.size() > 0">
		<div align="center" style="width: 95%;">
		<div align="center"
			style="width: 100%; max-height: 200px; overflow: auto; border: 1px solid #5A8AA9;">
		<table align="center" width="100%" class="datatable">
			<tr>
				<th>No.</th>
				<th>Assigned To</th>
				<th>Assigned On</th>
				<th>Assigned By</th>
			</tr>

			<s:iterator value="wsUsrAdtTrlLst" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>
														<s:if test="statusIndi == 'D'"> matchFound</s:if>
														">
					<td><s:property value="#status.count" /></td>
					<td><s:property value="assignedTo" /></td>
					<td><s:date name="modifyOn" format="MMM dd, yyyy" /></td>
					<td><s:property value="assignedBy" /></td>
				</tr>
			</s:iterator>
		</table>
		</div>
		</div>
	</s:if>
</s:if> <br />
<s:if test="!datamode.equalsIgnoreCase('user')">
	<div id="showDocHistory" align="center"><s:if
		test="userDocStageCommentsList.size() != 0">
		<table class="datatable" width="95%">
			<thead>
				<tr>
					<th>#</th>
					<th><s:property value="mode" /> Name</th>
					<th>Doc Version</th>
					<th>Document</th>
					<th>Comments</th>
					<th>Comments On</th>
				</tr>
			</thead>
			<s:if test="mode.equals('Authors')">
				<tbody>
					<s:iterator status="status" value="userDocStageCommentsList">

						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
							<td><s:property value="#status.count" /></td>
							<td><s:property value="userName" /></td>
							<td><s:property value="userDefineVersionId" /></td>
							<td align="center" style="text-align: center;"><s:if
								test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
								<a title="<s:property value="fileName"/>"
									href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>"
									target="_blank"> <s:if test="fileName.contains(\".\")">
									<s:if
										test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
										<img alt="Open File"
											src="<%=request.getContextPath()%>/js/jquery/tree/skin/pdf_icon_14x14.gif"
											style="border: none;">
									</s:if>
									<s:elseif
										test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('doc') || fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('docx') ">
										<img alt="Open File"
											src="<%=request.getContextPath()%>/js/jquery/tree/skin/icon-doc-14x14.gif"
											style="border: none;">
									</s:elseif>
									<s:else>
										<img alt="Open File"
											src="<%=request.getContextPath()%>/images/file.png"
											style="border: none;">
									</s:else>
								</s:if> <s:else>
									<img alt="Open File"
										src="<%=request.getContextPath()%>/images/file.png"
										style="border: none;">
								</s:else> </a>
							</s:if> <s:else>-</s:else></td>
							<td><s:property value="userComments" /></td>
							<td><s:date name="modifyOn" format="MMM dd, yyyy" /></td>

						</tr>
					</s:iterator>
				</tbody>
			</s:if>
			<s:if test="mode.equals('Reviewers')">
				<tbody>
					<s:iterator status="status" value="userDocStageCommentsList">
						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
							<td><s:property value="#status.count" /></td>
							<td><s:property value="userName" /></td>
							<td><s:property value="userDefineVersionId" /></td>
							<td align="center" style="text-align: center;"><s:if
								test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
								<a title="<s:property value="fileName"/>"
									href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>"
									target="_blank"> <s:if test="fileName.contains(\".\")">
									<s:if
										test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
										<img alt="Open File"
											src="<%=request.getContextPath()%>/js/jquery/tree/skin/pdf_icon_14x14.gif"
											style="border: none;">
									</s:if>
									<s:elseif
										test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('doc') || fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('docx') ">
										<img alt="Open File"
											src="<%=request.getContextPath()%>/js/jquery/tree/skin/icon-doc-14x14.gif"
											style="border: none;">
									</s:elseif>
									<s:else>
										<img alt="Open File"
											src="<%=request.getContextPath()%>/images/file.png"
											style="border: none;">
									</s:else>
								</s:if> <s:else>
									<img alt="Open File"
										src="<%=request.getContextPath()%>/images/file.png"
										style="border: none;">
								</s:else> </a>
							</s:if> <s:else>-</s:else></td>
							<td><s:property value="userComments" /></td>
							<td><s:date name="modifyOn" format="MMM dd, yyyy" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</s:if>
			<s:if test="mode.equals('Approvers')">
				<tbody>
					<s:iterator status="status" value="userDocStageCommentsList">
						<tr
							class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
							<td><s:property value="#status.count" /></td>
							<td><s:property value="userName" /></td>
							<td><s:property value="userDefineVersionId" /></td>
							<td align="center" style="text-align: center;"><s:if
								test="fileName != null && fileName !='' && !fileName.equalsIgnoreCase('No File')">
								<a title="<s:property value="fileName"/>"
									href="openfile.do?fileWithPath=<s:property value="baseWorkFolder"/><s:property value="folderName"/>/<s:property value="fileName"/>"
									target="_blank"> <s:if test="fileName.contains(\".\")">
									<s:if
										test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('pdf')">
										<img alt="Open File"
											src="<%=request.getContextPath()%>/js/jquery/tree/skin/pdf_icon_14x14.gif"
											style="border: none;">
									</s:if>
									<s:elseif
										test="fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('doc') || fileName.substring(fileName.indexOf(\".\")+1).equalsIgnoreCase('docx') ">
										<img alt="Open File"
											src="<%=request.getContextPath()%>/js/jquery/tree/skin/icon-doc-14x14.gif"
											style="border: none;">
									</s:elseif>
									<s:else>
										<img alt="Open File"
											src="<%=request.getContextPath()%>/images/file.png"
											style="border: none;">
									</s:else>
								</s:if> <s:else>
									<img alt="Open File"
										src="<%=request.getContextPath()%>/images/file.png"
										style="border: none;">
								</s:else> </a>
							</s:if> <s:else>-</s:else></td>
							<td><s:property value="userComments" /></td>
							<td><s:date name="modifyOn" format="MMM dd, yyyy" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</s:if>
		</table>
	</s:if> <s:else>
		<label class="title" style="text-align: center;">No Comments
		Found.</label>
	</s:else></div>
</s:if></div>
</div>
</div>
</body>
</html>