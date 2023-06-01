<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="type == 'Combo'">

	<s:select list="attrValueMatrixDtl" name="attrVal" listKey="attrValue"
		listValue="attrValue">
	</s:select>

</s:if>
<s:if test="type == 'AutoCompleter'">

	<s:select list="attrValueMatrixDtl" name="attrVal" listKey="attrValue"
		listValue="attrValue">
	</s:select>

</s:if>

<s:if test="type == 'Text'">

	<s:textfield name="attrVal"></s:textfield>

</s:if>

<s:if test="type == 'Date'">

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.widget.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>

	<script type="text/javascript">
					$(document).ready(function() { 
						$("#attrValueDate").datepicker({minDate: '', maxDate:  '',showAnim: 'slideDown',dateFormat: 'yy/mm/dd'});
					});
				</script>
	<input type="text" name="attrVal" class="attrValue" id="attrValueDate"> (YYYY/MM/DD)
  				&nbsp;<IMG
		onclick="
  	  								if(document.getElementById('attrValueDate').value != '' && confirm('Are you sure?'))
									   document.getElementById('attrValueDate').value = '';"
		src="<%=request.getContextPath() %>/images/clear.jpg" align="middle"
		title="Clear Date">
</s:if>

<s:if test="type == 'TextArea'">

	<TEXTAREA rows="3" cols="30" name="attrVal"></TEXTAREA>

</s:if>



