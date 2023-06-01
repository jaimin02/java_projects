<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>

<script type="text/javascript"
	src="js/jquery/jquery-1.9.1.min.js"></script>
	
<%-- <script type="text/javascript"
	src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script> --%>

<style>

 .attrDisplay tr:NTH-CHILD(1) th, .attrDisplay tr:NTH-CHILD(2) td {
	BACKGROUND: #31708f;
    COLOR: #FFFFFF;
    FONT-SIZE: 12px;
    FONT-WEIGHT: bold;
    text-align:center;
    
}

.attrDisplay tr td, .attrDisplay tr th {
  padding:5px;
}


.attrDisplay tr:NTH-CHILD(2) td {
  BACKGROUND: #86b9da;
  color:#000;
  text-align:left;
}


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

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

/* $(document).ready(function() {

	$('#Nodedetailhistory').dataTable( {
	"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
	"bJQueryUI": true,
    "sPaginationType": "full_numbers"
	 } );
}); */

	function refreshParent() 
	{
		//window.opener.parent.history.go(0);
		self.close();
	}

</script>
</head>
<body>
<script language="javascript" TYPE="text/javascript"
	src="js/wz_tooltip.js"></script>
<br>
<div class="container-fluid">
<div class="col-md-12">
<div class="boxborder">
<div style="border: 1px; border-radius: 10px 10px 0px 0px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
	align="center">

	<div class="all_title"><b style="float: left;">Compliance Work Flow</b></div>
<br>
<div class="grid_clr">
<table id = "DeviationNodeHistory" class="datatable paddingtable audittrailtable" style="width:98%; align:center;">
	<thead>
		<tr>
			<th>#</th>
			<th>${ lbl_nodeName }/${ lbl_folderName }</th>
			<!-- <th>Document Name</th> -->
			<th>Modified by</th>
			<th>Modified on</th>
		<s:if test ="#session.countryCode != 'IND'">
			<th>Eastern Standard Time</th>
		</s:if>
			<th>Deviation ${ lbl_folderName }s</th>
			<th>Deviation Reason</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="getDeviationDetail" status="status">
			<tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
				<td>${status.count }</td>
				<td><s:property value="nodeName" /> [<s:property value="folderName" />]</td>
				<%-- <td><s:property value="folderName" /></td> --%>
				<td><s:property value="userName" /></td>
				<td><s:property value="ISTDateTime" /></td>
			<s:if test ="#session.countryCode != 'IND'">
				<td><s:property value="ESTDateTime" /></td>
			</s:if>
				<td>
					<s:set name="attrToolTip"
						value="'<table border=1 class=attrDisplay cellspacing=2><th colspan=3>Deviation Documents:</th> <tr><td>Unit</td><td>Document</td></tr>'" />
					 <s:iterator value="refNodeIdFileName">						
							<s:set name="attrToolTip"
								value="#attrToolTip+'<tr><td>'+nodeDisplayName+'</td><td>'+fileName+'</tr>'" />
					</s:iterator> 

					<s:set name="attrToolTip" value="#attrToolTip+'</table>'" />

					<img alt="Attributes" src="images/AttributeSymbol1.svg" height="25px" width="25px"
						onmouseout="UnTip();"
						onmouseover="Tip('<s:property value="#attrToolTip"/>');">
				</td> 
				<td><s:property value="remark"/></td>
			</tr>
		</s:iterator>
	</tbody>
</table>	
</div>
</div>
<br>
<div align ="center">
	<input type="button" value="Close" class="button" onclick="refreshParent();">
</div>
</div>
</div>
<br>
</div>

</body>
</html>