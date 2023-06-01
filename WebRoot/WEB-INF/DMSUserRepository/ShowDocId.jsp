<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
		 $(document).ready(function() {

			 $("#fromId").blur( function(e){
				 var repetno =$("#fromId").val(); 
				  var patt=/\D/g;
				  if ((patt.test(repetno)))
					  $("#fromId").val(repetno.replace(/\D/g, ''));
				});
			 
			 $("#fromId").keypress(function(event) {
				  // Backspace, tab, enter, end, home, left, right
				  // We don't support the del key in Opera because del == . == 46.
				  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
				  // IE doesn't support indexOf
				  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
				  // Some browsers just don't raise events for control keys. Easy.
				  // e.g. Safari backspace.
				  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
				      (49 <= event.which && event.which <= 57) || // Always 1 through 9
				      (48 == event.which && $(this).attr("value")) || // No 0 first digit
				      isControlKey) { // Opera assigns values for control keys.
					  return;
				  } else {
					 //alert(event.which+"--else");
				    event.preventDefault();
				  }
				});



			 $("#toId").blur( function(e){
				 var repetno =$("#toId").val(); 
				  var patt=/\D/g;
				  if ((patt.test(repetno)))
					  $("#toId").val(repetno.replace(/\D/g, ''));
				});
			 
			 $("#toId").keypress(function(event) {
				  // Backspace, tab, enter, end, home, left, right
				  // We don't support the del key in Opera because del == . == 46.
				  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
				  // IE doesn't support indexOf
				  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
				  // Some browsers just don't raise events for control keys. Easy.
				  // e.g. Safari backspace.
				  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
				      (49 <= event.which && event.which <= 57) || // Always 1 through 9
				      (48 == event.which && $(this).attr("value")) || // No 0 first digit
				      isControlKey) { // Opera assigns values for control keys.
					  return;
				  } else {
					 //alert(event.which+"--else");
				    event.preventDefault();
				  }
				});
		    });
		</script>
</head>
<body>
<div class="errordiv" align="center" style="color: red;" id="message">
<s:fielderror></s:fielderror> <s:actionerror /></div>
<div align="center" style="width: 100%"><s:if
	test="projList.size() > 0">
	<table width="100%">
		<tr align="left">
			<td class="title" align="right"
				style="padding: 2px; padding-right: 6px;" width="25%">Document
			Id.</td>
			<td><s:select title="Project Prefix" list="projList"
				id="projList"></s:select>&nbsp;-&nbsp;<s:select
				title="Category Prefix" list="categoryList" id="categoryList"></s:select>&nbsp;-&nbsp;<s:select
				title="Year" list="yearList" id="yearList"></s:select>
			&nbsp;&nbsp;&nbsp;&nbsp; <label class="title">From&nbsp;</label> <input
				type="text" id="fromId" name="fromId" size="3px"
				style="width: 40px;"> &nbsp; <label class="title">To&nbsp;</label>
			<input type="text" id="toId" name="toId" size="3px"
				style="width: 40px;"></td>
		</tr>
		<tr align="left">
			<td></td>
			<td><input type="button" class="button" value="Show"
				onclick="getDetails();"></td>
		</tr>
	</table>
</s:if> <s:else>
	<label class="title"> No Document Attached/Released.</label>
</s:else></div>
</body>
</html>