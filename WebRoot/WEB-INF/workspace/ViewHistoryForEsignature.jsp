<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<style>
a.tooltip {
	outline: none;
	border: none;
}

a.tooltip strong {
	line-height: 20px;
	border: none;
	outline: none;
}

a.tooltip:hover {
	text-decoration: none;
}

a.tooltip span {
	z-index: 10;
	display: none;
	padding: 14px 20px;
	margin-top: 10px;
	margin-left: -160px;
	width: 240px;
	line-height: 16px;
	background-color: black;
}

a.tooltip:hover span {
	display: inline;
	position: absolute;
	border: 2px solid #FFF;
	color: #EEE;
	right: 20%;
}

img {
	outline: none;
	border: 0;
}

/*CSS3 extras*/
a.tooltip span {
	border-radius: 2px;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
	-moz-box-shadow: 0px 0px 8px 4px #666;
	-webkit-box-shadow: 0px 0px 8px 4px #666;
	box-shadow: 0px 0px 8px 4px #666;
	opacity: 0.8;
}
</style>
<s:head theme="ajax" />
<script type="text/javascript">
    		function fileOpen(actionName)
    		{   
    			win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
    			win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
    		}
    		
    		function docFileOpen(actionName)
    		{   
    			window.open(actionName, '_newtab');
    		}
    		
    		window.onerror = function (){return true;};
    	</script>

<script language="javascript" TYPE="text/javascript"
	src="js/wz_tooltip.js"></script>
</head>
<body>
<br>
<div align="left" class="bdycls">

<div class="container-fluid">
<div class="col-md-8">
<div style="border: 1px; overflow: auto; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">
<div class="boxborder"><div class="all_title"><b style="float: left;">Document History</b></div>
<br>
<div style="width:100%; overflow:auto">
<table class="datatable paddingtable audittrailtable" width="98%" align="center">
	<thead>
		<tr>
			<th>#</th>
			<!-- <th>Uploading Version</th> -->
			<th>Document</th>
			<th>Assigned Users</th>
			<th>Stage Assigned</th>
			<th>Status</th>
			<th>User Role</th>
			<!-- <th>Sent for</th> -->
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Reason for Change</th>
			<!--<th><img src="images/remark.jpg" width="20px" height="20px" /></th>
			 <th title="Attributes"><img alt="Attributes"
				src="images/AttributeSymbol.jpg"></th> -->
			<!-- <th>Seq. No</th> -->
			<!-- <th>Revert</th> -->
		
		</tr>
	</thead>
	<tbody>
		<s:iterator value="fullNodeHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="#status.count" /></td>
				<%-- <td><s:if test="userDefineVersionId == ''">
									&nbsp;
								</s:if> <s:property value="userDefineVersionId" /></td> --%>
								
				<td><s:if test="fileName == ''"></s:if> 
					<s:if test="fileName == 'No File'"> 
					<s:property value="fileName" default="-" />
				</s:if> 
				<s:else>
					<s:if test = "#session.usertypename == 'WA'">
						<s:if test="fileExt=='PDF' || fileExt=='pdf'">
						<a href="javascript:void(0);"
							onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
						<s:property value="fileName" default="-" /> </a>
						</s:if>
						<s:else>
							<a href="javascript:void(0);"
							onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
						<s:property value="fileName" default="-" /> </a>
						</s:else>
					</s:if>
					<s:else>
					 <s:if test="fileExt=='PDF' || fileExt=='pdf'"> 
					  <s:if test="OpenFileAndSignOff=='Yes'"> 
							<a href="javascript:void(0);"
							onclick="return fileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
							<s:property value="fileName" default="-"/> </a>
					  </s:if>
					  <s:else>
					  <a href="javascript:void(0);"
							onclick="return docFileOpen('SignOffOpenfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
							<s:property value="fileName" default="-"/> </a>
					  </s:else>
					  </s:if>
					  <s:else> 
						 	<s:if test="fileExt=='PDF' || fileExt=='pdf'">
						<a href="javascript:void(0);"
							onclick="return fileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
						<s:property value="fileName" default="-" /> </a>
						</s:if>
						<s:else>
							<a href="javascript:void(0);"
							onclick="return docFileOpen('openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="baseWorkFolder"/>');">
						<s:property value="fileName" default="-" /> </a>
						</s:else>
					  </s:else>
					</s:else>
				</s:else>
				</td>
				
				<td>
					<s:property value="userName"  default="-" />
				</td>
				
				<td>
					<%-- <s:if test ="stageDesc=='Authorised' ">Create</s:if> --%>
					<s:property value="stageDesc"  default="-" />
				</td>
					
				<td>
				<s:if test="fileName == null"> -</s:if>
					<%-- <s:elseif test ="completedStageId==0">Send Back</s:elseif>
					<s:elseif test ="completedStageId==10">Created</s:elseif>
					<s:elseif test ="completedStageId==20">Approved</s:elseif>
					<s:elseif test ="completedStageId==30">Rejected</s:elseif>
					<s:elseif test ="completedStageId==50">Approved</s:elseif>
					<s:elseif test ="completedStageId==100">Authorised</s:elseif> --%>
					<s:else>Sign Off Completed</s:else> 
				</td>
				
				<td><s:if test="roleName == ''">-</s:if> 
					<s:property value="roleName"  default="-" />
				</td>
				<td><s:if test="modifyOn == ''">
									&nbsp;
								</s:if> <!-- <s:date name="modifyOn" format="MMM dd, yyyy hh:mm:ss a" />-->
							<!-- 	<s:date name="modifyOn" format="dd-MMM-yyyy HH:mm"/></td> -->
					<s:property value="ISTDateTime"  default="-" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime"  default="-" /></td>
			</s:if>
				<td><s:if test="remark == ''">
									&nbsp;
					</s:if> 
					<s:else>
						<s:property value="remark"   default="-"/>
					<!-- <a href="#" class="tooltip"> <img src="images/remark.jpg"
						width="20px" height="20px" /> <span> <strong><s:property
						value="remark" /></strong></span> </a> -->
				</s:else></td>
		</tr>
		</s:iterator>

	</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
</div>

</body>

</html>