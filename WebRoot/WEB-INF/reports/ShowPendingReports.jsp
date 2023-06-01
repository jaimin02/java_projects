<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
</head>
<body>

<div class="errordiv" align="center" style="color: red"><s:fielderror></s:fielderror>
<s:actionerror /></div>

<s:if test="getPendingWorks.size==0">
	<br>
	<center>
	<table style="border: 1 solid black" width="100%" bgcolor="silver">
		<tr>
			<%-- <td width="10%" align="right"><img
				src="<%=request.getContextPath()%>/images/stop_round.gif"></td> --%>
			<td align="center" width="100%"><font size="4px" color="#c00000"><b><br>
			No Match Found In Your WorkSpace.<br>
			&nbsp;</b></font></td>
		</tr>
	</table>
	</center>

</s:if>
<s:else>
	<s:form action="CompleteWork_ex" name="PendingWorkResultForm"
		method="post">
		<s:if test="getPendingWorks.size > 15">
			<table width="95%" align="center">
				<tr>
					<td align="right"><input type="button" name="selectAllButton"
						class="button" value="Check All" onclick="return selectAll();" />
					&nbsp;&nbsp;&nbsp;<s:submit value="Change File(s) Status"
						cssClass="button" onclick="return validate();"></s:submit></td>

				</tr>
			</table>

		</s:if>
		<br>
		<table width="95%" align="center" class="datatable">

			<thead>
				<tr>
					<th>#</th>
					<th>Node Name</th>
					<th>File Name</th>
					<th>Current Stage</th>
					<th>Pending Works</th>
					<th align="left" style="width: 70px;">Check</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="getPendingWorks" id="getPendingWorks"
					status="status">
					<s:set name="curnodeid" value="nodeId" />
					<s:set name="stageId" value="stageId" />
					<s:set name="stageDesc" value="stageDesc" />
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td>${status.count }</td>
						<td><s:property value="nodeName" /></td>
						<td><a
							href="openfile.do?workSpaceId=<s:property value="workSpaceId"/>&nodeId=<s:property value="nodeId"/>&tranNo=<s:property value="tranNo"/>&fileName=<s:property value="fileName"/>&baseWorkFolder=<s:property value="remark"/>"
							target="_blank"> <s:property value="fileName" /></a></td>
						<td><s:property value="stageDesc" /></td>

						<td><select name="select_<s:property value="nodeId"/>">
							
							
									<option value="<s:property value="nextStageId"/>"><s:property
										value="nextStageDesc" /></option>
								
							
						</select> <!-- <s:iterator value="getNextStageId">
							  <s:hidden name="nextStageId" />
								<s:if test="#curnodeid == nodeId">
									<select name="select_<s:property value="nodeId"/>">
										<option value="<s:property value="nextStageId"/>"><s:property value="nextStageDesc"/></option>
									</select>
								
									<s:if test="#stageDesc == 'Created' ">
										<s:if test="nextStageDesc == 'Reviewed'">
											<s:property value="nextStageDesc"/>
										</s:if>		
									</s:if>
								
									<s:if test="#stageDesc == 'Reviewed' ">
										<s:if test="nextStageDesc == 'Authorized'">
												<s:property value="nextStageDesc"/>
										</s:if>		
									</s:if>									
		
									<s:if test="#stageDesc == 'Authorized' ">
										<s:if test="nextStageDesc == 'Approved'">
											<s:property value="nextStageDesc"/>
										</s:if>		
									</s:if>
								</s:if>
							</s:iterator> --></td>
						<td align="center"><input type="checkbox"
							value="<s:property value="nodeId"/>" name="selectedNodeId" /></td>
					</tr>
				</s:iterator>
			</tbody>

		</table>
		<br>
		<table width="95%" align="center">
			<tr>
				<td align="right"><input type="hidden"
					value="<s:property value='elecSig'/>" name="elecSig" id="elecSig"
					style="display: none;"> <input type="hidden"
					value="<s:property value='#session.password'/>" name="sessPass"
					id="sessPass" style="display: none;"> <input type="button"
					name="selectAllButton1" class="button" value="Check All"
					onclick="return selectedAll();" /> &nbsp;&nbsp;&nbsp; <input
					value="Change File(s) Status" class="button"
					onclick="return validate();" type="button"></input> <s:hidden
					name="selectValues" value=""></s:hidden> <s:hidden
					name="workSpaceId"></s:hidden></td>

			</tr>
		</table>

	</s:form>
</s:else>
</body>
</html>