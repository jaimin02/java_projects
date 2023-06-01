<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" media="screen" />
<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css"
	rel="stylesheet" type="text/css" media="screen" />
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"
	type="text/javascript"></script>
<%-- <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> --%>


<style>
.Progress {
  width: 85%;
  background-color: #d2abab;
  height: 30px;
  margin-bottom: 10px;
}

.Bar {
  /* width: 45%; */
  height: 30px;
  background-color: #4CAF50;
 /*  padding-left: 10px;
  padding-right: 10px; */
  line-height: 30px;
  color: black;
  display: block;
}

.bg-reviewed
{
background-color: #4fa4ce;
}

.bg-approved
{
background-color: #278166c9;
}

.name {
  float: left;
}

.pct {
 /*  float: right; */
}

#getProjectCompletionDetail thead th{
 	position: sticky;
	top: -1px; 
 	z-index: 1;
 } 
</style>
<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

	/* function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	} */

</script>
</head>
<body>



<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 5px; border-top: none;"
	align="center">
	<!-- <div class="headercls" align="center" style="width: 100%">Client Detail History</div> -->
	<div class="boxborder"><div class="all_title"><b style="float: left;">Project Progress Status</b></div>
<!-- <div style='text-align: right; padding-right: 12px; margin-bottom: 2px; margin-top: 3px;'>
	<label style="background-color: #73cfe4 ; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Reviewed
	<label style="background-color: #4fa4ce; height: 20px; width: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>&nbsp;Approved</div> -->
<s:if test="getProjectCompletionDetail.size() == 0">
			<tr class="odd">
				<td colspan="8" style="text-align:center;"><label>No details available.</label></td>
			</tr>
		</s:if>
		<s:else>
	<div style=" max-height: calc(100vh - 200px); border: 1px solid #175279; background: #E3EAF0; overflow-y: auto;">
<table id = "getProjectCompletionDetail" class="datatable paddingtable audittrailtable" style="width:98%; margin-top:5px; overflow: auto; z-index: 1; align:center;">
	<thead>
		<tr>
			<th>#</th>
			<th>Project</th>
			<th style="width:80px;">Total Files</th>
			<!-- <th>Approved Files</th> -->
			 <th>Reviewed &nbsp;<label style="background-color: #278166c9 ; height: 15px; width: 15px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>
			 /Approved Files&nbsp; <label style="background-color: #4fa4ce; height: 15px; width: 15px;">&nbsp;&nbsp;&nbsp;&nbsp;</label>
			 </th>
			<!--<th>Approved Files</th> 
			 <th>Reviewed/Approved Files</th> -->
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getProjectCompletionDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="workspacedesc" /></td>
				<td><s:property value="totalNode" /></td>
				<%-- <td><s:property value="uploadedNode" /></td>  --%>
				 <td>
					<s:if test='reviewPer==null'>
					<div class="progress" style="width: 85%;">
  							<div class="progress-bar progress-bar-striped bg-approved" role="progressbar" style="width:0%;" aria-valuenow="10" 
  							aria-valuemin="0" aria-valuemax="100">0% </div>
						</div>
					</s:if>
					<s:else>
						<%-- <div class="Progress">
  						<div class="Bar" style="width:<s:property value="reviewPer" />%;" data-value='<s:property value="reviewPer" />'>
    						<!-- <div class="name">Reviewed</div> -->
   							 <div class="pct"><s:property value="reviewPer" />%</div>
 						 </div>
						</div> --%>
					<span style="float: right; background: #448b75;padding: 0px 2px;border-radius: 4px;color: #fff;">
					[<s:property value="reviewFile" />/<s:property value="totalNode" />]</span> 
					<div class="progress" style="width: 85%;">
  							<div class="progress-bar progress-bar-striped bg-approved" role="progressbar" style="width:<s:property value="reviewPer" />%;" aria-valuenow="10" 
  								aria-valuemin="0" aria-valuemax="100"><s:property value="reviewPer" />% </div></div>
					</s:else>
				<s:if test='approvePer==null'>
					<div class="progress" style="width: 85%;">
  							<div class="progress-bar progress-bar-striped bg-reviewed" role="progressbar" style="width:0%;" 
  								aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">0% </div>
						</div>
				</s:if>
				<s:else>
						<%-- <div class="Progress">
  						<div class="Bar" style="width:<s:property value="approvePer" />%;"data-value='<s:property value="approvePer" />'>
    						<!-- <div class="name">Reviewed</div> -->
   							 <div class="pct"><s:property value="approvePer" />%</div>
 						 </div>
						</div> --%>
					 <span style="float: right;background: #4fa4ce;padding: 0px 2px;border-radius: 4px;color: #fff;">
					 [<s:property value="uploadedNode" />/<s:property value="totalNode" />]</span> 
					 <div class="progress" style="width: 85%;">
  							<div class="progress-bar progress-bar-striped bg-reviewed" role="progressbar" style="width:<s:property value="approvePer" />%;" 
  								aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
  								<s:property value="approvePer" />% 
  								</div>
						</div>
					</s:else>
				</td>  
				<!-- <td>
				<div class="progress">
  <div class="progress-bar progress-bar-striped bg-approved" role="progressbar" style="width: 30%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">50%</div>
</div>
<div class="progress">
  <div class="progress-bar progress-bar-striped bg-reviewed" role="progressbar" style="width: 75%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">30%</div>
</div>

				</td> -->
				<%-- <td>
					<s:if test='totalPer==null'>-</s:if>
					<s:else>
						<div class="Progress">
  						<div class="Bar" style="width:<s:property value="totalPer" />%;" data-value='<s:property value="totalPer" />'>
    						<!-- <div class="name">Reviewed</div> -->
   							 <div class="pct"><s:property value="totalPer" />%</div>
 						 </div>
						</div>
					</s:else>
				</td>  --%>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>	
</s:else>
</div>
</div>

</body>
</html>