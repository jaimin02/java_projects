<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
	padding: 0px;
	margin: 0px;
}

.odd {
	BACKGROUND: #E3EAF0;
	COLOR: black;
	FONT-FAMILY: verdana;
	FONT-SIZE: 11px;
	FONT-WEIGHT: normal;
	align: left;
}

.even {
	BACKGROUND: #FFFFFF;
	COLOR: black;
	FONT-FAMILY: verdana;
	FONT-SIZE: 11px;
	FONT-WEIGHT: normal;
	align: left;
}

/******datatable****************/
.datatable {
	border: 1px solid #0C3F62;
	font-size: 12px;
}

.datatable a,.datatable tbody,.datatable tfoot,.datatable tr,.datatable th,.datatable td
	{
	font-family: Verdana;
	text-align: left;
}

.datatable td a:link {
	color: black;
	text-decoration: none;
}

.datatable td a:visited {
	background: inherit;
	color: black;
	text-decoration: none;
}

.datatable td a:active {
	color: #5B89AA;
}

.datatable td a:hover {
	background: inherit;
	color: blue;
	position: relative;
	top: 1px;
	left: 1px;
	text-decoration: none;
	cursor: pointer;
}

.datatable td,.datatable th {
	padding-left: 3px;
	padding-right: 3px;
	height: 20px;
}

.datatable th {
	BACKGROUND: #5B89AA;
	COLOR: #FFFFFF;
	FONT-SIZE: 4pt;
	FONT-WEIGHT: bold;
	align: left;
}

.datatable tr.none:hover {
	background-color: white;
	COLOR: black;
}

.datatable tr:hover {
	background-color: red;;
	COLOR: black;
}

.datatable tr:hover a:link,.datatable tr:hover a:visited {
	color: blue;
}

.datatable tr.blockedFound a {
	color: black;
}

.datatable tr:hover tr.blockedFound a {
	color: red;
}

.datatable tr:nth-child(even) {
	background: none repeat scroll 0% 0% rgb(255, 255, 255);
}

.datatable tr:nth-child(odd) {
	background: none repeat scroll 0% 0% rgb(227, 234, 240);
}

/***************Remarks***************/
.remarkcss {
	BACKGROUND: #FFFFFF;
	COLOR: #000066;
	FONT-FAMILY: verdana;
	FONT-SIZE: 8pt;
	FONT-WEIGHT: normal;
	align: left;
}

/**************Attribute Display Table**************/
.attrDisplay table {
	
}

.attrDisplay th {
	font-size: 8pt;
	background: #ffcccc;
	height: 8pt;
	font-weight: bold;
	color: black;
	padding-top: 1px;
	padding-bottom: 1px;
	padding-left: 3px;
}

.attrDisplay td {
	font-size: 8pt;
	background: #ffffff;
	height: 8pt;
	font-weight: bold;
	color: black;
	padding-top: 1px;
	padding-bottom: 1px;
	padding-left: 3px;
}

.lnkButton {
	text-decoration: none;
	padding: 3px;
	position: relative;
	top: 2px;
}

/******paddingtable****************/
.paddingtable td {
	padding-left: 5px;
	text-align: left;
}

/******doubleheight****************/
.doubleheight td,.doubleheight th {
	white-space: nowrap;
	height: 30px;
}

#oldSequences a {
	color: blue;
}

#oldSequences a:hover {
	color: red;
}

.relseqdetail {
	display: none;
	position: absolute;
	border: 1px solid black;
	border-radius: 10px;
	background-color: rgba(0, 0, 0, 0.6);
	padding: 5px;
	color: black;
	z-index: 99999999;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function refseqdetail(e)
{
	debugger;
	$("#"+$(e).attr("class")).show();
	
	var left=0;
	if(e.offsetLeft>=235)
		left=e.offsetLeft-200;
	$("#"+$(e).attr("class")).css("left",left);
	
}
function hiderefseqdetail(e)
{
	$("#"+$(e).attr("class")).hide();	
}
function checkExt(e)
{
	//var ext=e.href.substring(e.href.lastIndexOf('.'));
	var filewithpath=e.href.substring(e.href.indexOf('fileWith'));

	var ext=filewithpath.substring(filewithpath.lastIndexOf('.'));
	if(ext!='.pdf' && ext.length<=8)
	{

		debugger;		  				
		var host = "http://"+window.location.hostname;
		var port=window.document.location.port;
		var serverpath=host+":"+port;
		//alert(serverpath);
//window.open(serverpath+"/docmgmtandpub"+filewithpath.substring(38),'_blank');

		var fullurl="chrome-extension://hehijbfgiekmjfkfjpbkbammjbdenadd/iecontainer.html#url="+serverpath+"/docmgmtandpub"+filewithpath.substring(38);	
		window.open(serverpath+"/docmgmtandpub"+filewithpath.substring(38),'_blank');

		e.href=serverpath+"/docmgmtandpub"+filewithpath.substring(38);
		return false;
			
	}

}

</script>
</head>
<body>
<table width="100%" class="datatable">
	<tbody>

	</tbody>
	<tr>
		<td><b>Current Sequence </b></td>
		<td align="left"><s:property value="sequenceNo" /></td>
	</tr>
	<s:if test="!allOperationDetails.equalsIgnoreCase('')">
		<tr>
			<td><b>Related Sequence </b></td>
			<td align="left">
			<div id="oldSequences"><s:property value="allOperationDetails" />
			</div>
			</td>

		</tr>
	</s:if>
	<tr>
		<td><b>Operation </b></td>
		<td align="left"><s:property value="ectdOperation" /></td>
	</tr>

	<tr>
		<td><b>Node Id </b></td>
		<td align="left"><s:property value="ectdNodeId" /></td>
	</tr>
	<!--	<tr class="even">-->
	<!--		<td><b>App. Version</b></td>-->
	<!--		<td align="left"><s:property value="ectdAppVersion" /></td>-->
	<!--	</tr>-->
	<tr>
		<td><b>CheckSum </b></td>
		<td align="left"><s:if test="ectdXlinkHref.equalsIgnoreCase('')"> NA</s:if><s:else>
			<s:property value="ectdCheckSum" />
		</s:else></td>
	</tr>
	<tr>
		<td><b>CheckSum Type </b></td>
		<td align="left"><s:if test="ectdXlinkHref.equalsIgnoreCase('')"> NA</s:if><s:else>
			<s:property value="ectdCheckSumType" />
		</s:else></td>
	</tr>

	<!--	<tr class="odd">-->
	<!--		<td><b>Link </b></td>-->
	<!--		<td align="left"><s:if test="ectdXlinkHref.equalsIgnoreCase('')"> NA</s:if><s:else>-->
	<!--			<s:property value="ectdXlinkHref" />-->
	<!--		</s:else></td>-->
	<!--	</tr>-->

	<script>
	
	
	$("#oldSequences").html($("#oldSequences").text());
	
	</script>

</table>
</body>
</html>