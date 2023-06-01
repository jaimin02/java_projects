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
<div class="headercls" style="width: 100%"><font color="white"
	size="2"> <b>${nodeDisplayName}</b> </font></div>
<div align="left" class="bdycls">
<div align="right" style="padding-right: 32px; margin-top: 5px; margin-bottom: 5px;"><input
	type="button" class="button" value="Back" onclick="history.go(-1);">
</div>
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
			<th>Size</th>
			<th>Status</th>
			<th>Sent for</th>
			<th>Modified by</th>
			<th>User Role</th>
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
		<th>Certified</th> 
		<th>Voided by</th>
		<th>Voided on</th>
	<s:if test ="#session.countryCode != 'IND'">
		<th>Eastern Standard Time</th>
	</s:if>
		<th>Reason For Void</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="fullNodeHistory" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td><s:property value="#status.count" /></td>
				<%-- <td><s:if test="userDefineVersionId == ''">
									&nbsp;
								</s:if> <s:property value="userDefineVersionId" /></td> --%>
				<td><s:if test="fileName == ''">
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
				 <s:if test="fileExt=='PDF' || fileExt=='pdf'">
				  <s:if test="OpenFileAndSignOff=='Yes'">
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
				<td><s:if test="historyDocumentSize.sizeMBytes>0">
					<s:property value="historyDocumentSize.sizeMBytes" default="--" /> MB
								</s:if> <s:else>
					<s:property value="historyDocumentSize.sizeKBytes" default="--" /> KB
								</s:else></td>
				<td><s:if test ="stageId==10 && FileType !='SR'">Uploaded</s:if>
					<s:else>
						<s:if test="stageDesc == ''">&nbsp;</s:if> 
						<s:property value="stageDesc" default=" " />
					</s:else>
				</td>
					
				<td>
					<s:if test="stageId==10 && FileType ==''">-</s:if>
					<s:elseif test ="stageId==10 && FileType =='SR'">Review</s:elseif>
					<s:elseif test ="stageId==20">Approve</s:elseif>
					<s:elseif test ="stageId==0">Create</s:elseif>
					<s:else>-</s:else> 
				</td>
				<td><s:if test="userName == ''">
									&nbsp;
								</s:if> <s:property value="userName" /></td>
				<td><s:if test="roleName == ''">
									&nbsp;
								</s:if> <s:property value="roleName" /></td>
				<td><s:if test="modifyOn == ''">
									&nbsp;
								</s:if> <!-- <s:date name="modifyOn" format="MMM dd, yyyy hh:mm:ss a" />-->
							<!-- 	<s:date name="modifyOn" format="dd-MMM-yyyy HH:mm"/></td> -->
					<s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td><s:if test="remark == ''">
									&nbsp;
					</s:if> 
					<s:else>
						<s:property value="remark" />
					<!-- <a href="#" class="tooltip"> <img src="images/remark.jpg"
						width="20px" height="20px" /> <span> <strong><s:property
						value="remark" /></strong></span> </a> -->
				</s:else></td>
				<!-- <td><s:if test="attrHistory.size > 0">

					<s:set name="attrToolTip"
						value="'<table border=1 class=attrDisplay cellspacing=2><th colspan=2>Attribute History For Version: '+userDefineVersionId+'</th>'" />

					<s:iterator value="attrHistory">
						<s:if test="AttrValue.length() > 0">
							<s:set name="attrToolTip"
								value="#attrToolTip+'<tr><td>'+AttrName+'</td><td>'+AttrValue.replaceAll('\r\n','<br>').replaceAll('\t','&nbsp;').replaceAll('\\'','&#39;')+'</td></tr>'" />
						</s:if>
						<s:else>
							<s:set name="attrToolTip"
								value="#attrToolTip+'<tr><td>'+AttrName+'</td><td>&nbsp;&nbsp;&nbsp;</td></tr>'" />
						</s:else>
					</s:iterator>

					<s:set name="attrToolTip" value="#attrToolTip+'</table>'" />

					<img alt="Attributes" src="images/AttributeSymbol.jpg"
						onmouseout="UnTip();"
						onmouseover="Tip('<s:property value="#attrToolTip"/>');">
				</s:if> <s:else>
									&nbsp;
								</s:else></td>
				 -->
				<%-- <td><s:if test="currentSeqNumber == ''">
									&nbsp;
								</s:if> <s:property value="currentSeqNumber" /></td> --%>
				<!-- <td align="center"><s:if
					test="lastClosed != 'Y' && !fileName.equalsIgnoreCase('No File')">
					<a
						href="RevertBackNodeHistory.do?workspaceID=${workspaceID }&nodeId=${nodeId }&tranNoToRevert=<s:property value="tranNo"/>"
						onclick="return confirm('Are you sure?');">Revert</a>
				</s:if> <s:else>
						  Revert
						</s:else>
				</td> -->
			 <td>
				<s:property value="defaultFileFormat"/>
			</td> 
			<td><s:property value="voidBy" default="-"/></td>
			<td><s:property value="voidISTDateTime" default="-"/></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="voidESTDateTime" default="-"/></td>
			</s:if>
			<td><s:if test="voidRemark == ''">
					&nbsp;
				</s:if> 
				<s:else>
					<s:property value="voidRemark" />
				</s:else>
			</td>
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