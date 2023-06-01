<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('form input[type=""]').click(function() {
			$(this).select().unbind('click');
		});
		$('.demo').crossSelect({dblclick: true,select_txt: ">",remove_txt: "<",selectAll_txt: ">>",removeAll_txt: "<<",rows: 9.5,horizontal:"scroll",listWidth:200});
		$('form select:not(".demo"), form input').change(function() {
			$('.demo').clone().appendTo('#demo');
			$('.jqxs').fadeOut('slow',recreate);			
		});
	});
	function recreate() {
		$('.jqxs').remove();			
				var string = "$('.demo').crossSelect({";
				if( $('form [name="vertical"]').val().length > 0){string += 'vertical: "'+$('form [name="vertical"]').val()+'",';}
				if( $('form [name="horizontal"]').val().length > 0){string+='horizontal: "'+$('form [name="horizontal"]').val()+'",';}
				if( $('form [name="listWidth"]').val().length > 0){string+='listWidth: '+$('form [name="listWidth"]').val()+',';}
				if( $('form [name="font"]').val().length > 0){string+='font: '+$('form [name="font"]').val()+',';}
				if( $('form [name="rows"]').val().length > 0){string+='rows: '+$('form [name="rows"]').val()+',';}
				if( $('form #dblclick1')[0].checked || $('form #dblclick2')[0].checked){string+='dblclick: '+$('form [name="dblclick"]:checked').val()+',';}
				if( $('form #clickSelects1')[0].checked || $('form #clickSelects2')[0].checked){string+='clickSelects: '+$('form [name="clickSelects"]:checked').val()+',';}
				if( $('form #clicksAccumulate1')[0].checked || $('form #clicksAccumulate2')[0].checked){string+='clicksAccumulate: '+$('form [name="clicksAccumulate"]:checked').val()+',';}
				if( $('form [name="select_txt"]').val().length > 0){string+='select_txt: "'+$('form [name="select_txt"]').val()+'",';}
				if( $('form [name="remove_txt"]').val().length > 0){string+='remove_txt: "'+$('form [name="remove_txt"]').val()+'",';}
				if( $('form [name="selectAll_txt"]').val().length > 0){string+='selectAll_txt: "'+$('form [name="selectAll_txt"]').val()+'",';}
				if( $('form [name="removeAll_txt"]').val().length > 0){string+='removeAll_txt: "'+$('form [name="removeAll_txt"]').val()+'"';}
				if(string.substr(string.length-1,1) ===',')
				{
					string = string.substr(0, string.length-1);
				}
				string += "});";
				eval(string);
				$('#demo').append('<div style="clear:left;"></div>');
				$('#demo > .alert').html('<p>The code to create a crossSelect looking and behaving like this is:</p><pre>'+string+'</pre>');
		}
	</script>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			var options = {	success: showResponse};
				$('#attrListForm').submit(function(){
					//this is call when the form is submit after editing data on form.
					//if we put the before submit function it will not work properly.
						var sel = document.getElementById("select");
						var val = sel.length;
						//alert(val);
						var i=0;
						var queryString="";
						for(i=0;i<val;i++)
						{
							if(sel.options[i].selected == true)
							{
								queryString += sel.options[i].value+',';
							}
						}
						document.getElementById("savedAttrList").value='';
						if(queryString!=null)
							document.getElementById("savedAttrList").value = queryString;
						//alert("123:"+document.getElementById("savedAttrList").value+":456");
					$(options.target).html('<img src="images/loading.gif"/>');
					
					// !!! Important !!!
					// always return false to prevent standard browser submit and page navigation
					return true;
				});
		});
		// post-submit callback
		function showResponse(responseText, statusText) {
			//document.getElementById("AttrDisplayValueMain").style.display = 'block';
			//alert(responseText);
			//alert(statusText);
		}
	</script>
<br>
<div id="attrListDiv" align="center">
<form>
<div id="demo" style="width: 500px;" align="center"><select
	class="demo" multiple="multiple" id='select'>
	<s:iterator value="attrList">
		<option
			<s:if test="savedUserAttr == true" >
							 selected="selected" 
							 </s:if>
			value="<s:property value="attrId"/>"><s:property
			value="attrName" /></option>
	</s:iterator>
</select>
<div style="clear: left;"></div>
</div>
</form>
<form action="SaveUserAttrList_ex.do" id="attrListForm" method="post">
<input type="hidden" id="savedAttrList" name="savedAttrList" /> <input
	type="submit" class="button" id="btnAttrList"
	value="Save Attribute List" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form>
</div>
