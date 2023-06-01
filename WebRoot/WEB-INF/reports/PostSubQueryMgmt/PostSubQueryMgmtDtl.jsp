<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<SCRIPT type="text/javascript">
			$(document).ready(function() {
				$('.close').css('display','none');
			});
			function openDetails(trId)
			{
				$('.close').css('display','none');
				$('#'+trId).css('display','block');
			}
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div style="width: 900px;" align="center"><s:if
	test="submissionQueryMsts.size != 0">
	<table width="100%" align="center" class="datatable">
		<thead>
			<tr>
				<th>#</th>
				<th>Project Name</th>
				<th>Seq. No.</th>
				<th>Query Title</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th title="Reference Document">Ref Doc.</th>
				<th>Description</th>
				<th>Nodes Affected</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="submissionQueryMsts" status="statusMst">
				<tr class="none">
					<td align="left"><s:property value="#statusMst.count" /></td>
					<td><s:property
						value="submissionQueryDtls.get(0).workSpaceDesc" /></td>
					<td><s:property value="submissionQueryDtls.get(0).SeqNo" /></td>
					<td><s:property value="queryTitle" /></td>
					<td><s:date name="startDate" format="MMM-dd-yyyy" /></td>
					<td><s:date name="endDate" format="MMM-dd-yyyy" /></td>
					<td align="center">
					<div align="center"><s:if
						test="refDoc != null && refDoc.trim() != ''">
						<a title="<s:property value="refDoc"/>"
							href="openfile.do?fileWithPath=<s:property value="subQuery"/>/<s:property value="queryId"/>/<s:property value="refDoc"/>"
							target="_blank"><img alt="Open File"
							src="<%=request.getContextPath()%>/images/file.png"
							style="border: none;"></a>
					</s:if></div>
					</td>
					<td><s:property value="queryDesc" /></td>
					<td style="text-align: center;" align="center"><a
						onclick="openDetails('<s:property value="#statusMst.count"/>')"><s:property
						value="submissionQueryDtls.size()" /></a></td>
				</tr>
				<s:if test="submissionQueryDtls.size()>0">
					<tr class="none close" id="<s:property value="#statusMst.count"/>">
						<td>&nbsp;</td>
						<td colspan="8">
						<table width="100%" class="none">
							<tr>
								<th width="1%">#</th>
								<th width="50%">Node Name</th>
								<th width="15%">Status</th>
								<th width="15%">Modify By</th>
								<th width="15%">Modify On</th>
							</tr>
							<s:iterator value="submissionQueryDtls" status="statusDtl">
								<tr
									class="<s:if test="#statusDtl.even">even</s:if><s:else>odd</s:else>">
									<td><s:property value="#statusMst.count" />.<s:property
										value="#statusDtl.count" /></td>
									<td><s:property value="nodeDisplayName" /></td>
									<td><s:property value="queryStatus" /></td>
									<td><s:property value="modifyByName" /></td>
									<td><s:date name="modifyOn" format="MMM-dd-yyyy" /></td>
								</tr>
							</s:iterator>
						</table>
						</td>
					</tr>
					<tr height="2px" class="none">
					  <s:if test ="#session.countryCode != 'IND'">
						<td colspan="11" height="1">
						<hr width="100%" color="#5A8AA9" size="3px"
							style="border-bottom: 2px solid #CDDBE4;">
						</td>
					 </s:if>
					 <s:else>
						<td colspan="9" height="1">
						<hr width="100%" color="#5A8AA9" size="3px"
							style="border-bottom: 2px solid #CDDBE4;">
						</td>
					 </s:else>
					</tr>
				</s:if>
			</s:iterator>
		</tbody>
	</table>
</s:if> <s:else>
	<div style="width: 95%; text-align: center" align="center"><label
		class="title">No Query Details Found.</label></div>
</s:else></div>
</body>
</html>