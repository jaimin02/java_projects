<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript">
    		function fileOpen(actionName)
    		{   
    			win3=window.open(actionName,"ThisWindow","toolbar=no,directories=no,menubar=no,scrollbars=no,height=600,width=800,resizable=yes,titlebar=no");
    			win3.moveTo(screen.availWidth/2-(800/2),screen.availHeight/2-(600/2));
    		}
    		
    		window.onerror = function (){return true;};
    	</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div style="width: 100%" align="center"><s:if
	test="submissionQueryMsts.size != 0">
	<table width="95%" align="center" class="datatable"
		style="overflow: auto;">
		<thead>
			<tr>
				<th>#</th>
				<th>Seq. No.</th>
				<th>Query Title</th>
				<th>Start Date</th>
			<s:if test ="#session.countryCode != 'IND'">
				<th>Eastern Standard Time</th>
			</s:if>
				<th>End Date</th>
			<s:if test ="#session.countryCode != 'IND'">
				<th>Eastern Standard Time</th>
			</s:if>
				<th title="Reference Document">Ref Doc.</th>
				<th>Description</th>
				<s:if test="mi.trim().equalsIgnoreCase('a')">
					<th>Edit</th>
				</s:if>
				<s:if test="mi.trim().equalsIgnoreCase('c')">
					<th>Change Status</th>
				</s:if>
				<!-- <th>Node Name [Folder Name]</th> -->
			</tr>
		</thead>
		<tbody>
			<s:iterator value="submissionQueryMsts" status="statusMst">
				<tr
					class="<s:if test="#statusMst.even">odd</s:if><s:else>even</s:else>">
					<td><s:property value="#statusMst.count" /></td>
					<td><s:property value="submissionQueryDtls.get(0).SeqNo" /></td>
					<td><s:property value="queryTitle" /></td>
					<!--  <td><s:date name="startDate" format="MMM-dd-yyyy" /></td> -->
					<td><s:property value="ISTDateTime" /></td> 
				<s:if test ="#session.countryCode != 'IND'">
					<td><s:property value="ESTDateTime" /></td>
				</s:if>
					<!-- <td><s:date name="endDate" format="MMM-dd-yyyy" /></td>-->
					<td><s:property value="EndISTDateTime" /></td> 
				<s:if test ="#session.countryCode != 'IND'">
					<td><s:property value="EndESTDateTime" /></td>
				</s:if>
					<td align="center">
					<div align="center"><s:if
						test="refDoc != null && refDoc.trim() != ''">
						<!-- <a title="<s:property value="refDoc"/>"
							href="openfile.do?fileWithPath=<s:property value="subQuery"/>/<s:property value="queryId"/>/<s:property value="refDoc"/>"
							target="_blank"><img alt="Open File"
							src="<%=request.getContextPath()%>/images/file.png"
							style="border: none;"></a>-->
							<a href="javascript:void(0);"
						onclick="return fileOpen('openfile.do?fileWithPath=<s:property value="subQuery"/>/<s:property value="queryId"/>/<s:property value="refDoc"/>');">
					<img alt="Open File" src="<%=request.getContextPath()%>/images/file.png" style="border: none;"></a>
					</s:if></div>
					</td>
					<td><s:property value="queryDesc" /></td>
					<s:if test="mi.trim().equalsIgnoreCase('a')">
						<td align="center">
						<div align="center"><a href="javascript:void(0);"
							onclick="openInEditMode('<s:property value="queryId"/>');"
							title="Edit"> <img border="0px" alt="Edit"
							src="images/Common/edit.png" height="18px" width="18px"> </a></div>
						</td>
					</s:if>
					<s:if test="mi.trim().equalsIgnoreCase('c')">
						<td align="center">
						<div align="center"><a href="javascript:void(0);"
							onclick="openInChangeMode('<s:property value="queryId"/>');"
							title="Change Status"> <img border="0px" alt="Change Status"
							src="images/Common/view.png" height="18px" width="18px"> </a></div>
						</td>
					</s:if>

				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:else>
	<div style="width: 95%; text-align: center" align="center"><label
		class="title">No Query Submitted for this project.</label></div>
</s:else></div>
</body>
</html>