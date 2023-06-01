<%@ taglib prefix="s" uri="/struts-tags"%>

<style>
#ShowNodeDropDown {
	background: white;
	opacity: 1;
	position: fixed;
	right: 10px;
	bottom: 0px;
	width: 90%;
	height: 30px;
	z-index: 999;
}

#nlist {
	margin-top: 2px;
	border: solid 2px #991010;
	padding: 4px;
	border-radius: 4px;
	box-shadow: 0 10px 10px 10px 10px red;
}
</style>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/ui/js/jquery.ui.datepicker.js"></script>
<script src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function() { 
	
		var options = 
		{
			target: '#msgDiv',
			url: "SavePublishAndSubmitForm.do",
			beforeSubmit: showRequest,
			success: showResponse
		};
		$('#TreeForPublish').submit(function(){	
			$(this).ajaxSubmit(options);
			return false;									
		});
	});
	
	function showRequest(formData, jqForm, options) {
		var queryString = $.param(formData);
		var formElement = jqForm[0];
	
		$(options.target).html('<img src=\"images/loading.gif\" alt=\"loading ...\" />');
		
	}
	function showResponse(responseText, statusText) 
	{
	
	}

	var ref_textbox_id;
	function getRefId(nodetofind,inSequenceNo,nm)
	{
		var nodetofind =nodetofind.replace(/::/g , " ");
		var res=nm.substring(7); // res is nodeid
		
		//alert(nodetofind);
		//alert(seqno);
			
		
		if(inSequenceNo==null || inSequenceNo=="")
		{
			$("#refID_"+ref_textbox_id).val("");
			$("#refID_"+ref_textbox_id).css('background','white');
			return false;
		}

		var leafnodetofind=$("#leaf_"+res).html();
		var workSpaceId=$('#wsId').val();
		
		$("#refLoaderID_"+res).html("");
		$("#refID_"+res).val("");

	//	alert(leafnode);
		$("#refID_"+ref_textbox_id).css('background','white');

		//var userTypeCode = $('#userTypeCodeList').val();
		$.ajax(
		{			
			
			url: 'NodeReferenceId.do?workspaceId='+workSpaceId+'&nodetofind='+nodetofind+'&leafnodetofind='+ leafnodetofind +'&inSequenceNo='+inSequenceNo,
			beforeSend: function()
			{
				//	$('#ShowReferenceNo').html("<img src=\"images/loading5.gif\" alt=\"loading ...\" width=\"50px\" height=\"50px\" />");								
					$("#refLoaderID_"+res).html("<img src=\"images/loading.gif\" alt=\"loading ...\" />");
			},
			success: function(data) 
	  		{
				$('#ShowNodeDropDown').html("");
				$("#refLoaderID_"+res).html("");
				$('#ShowReferenceNo').html("");
				$('#ShowNodeDropDown').append("<span style='color:black;font-size:15px;'>Currently Selected Node :<font color='blue'> "+leafnodetofind+"</font></span>&nbsp");
				//$('#ShowReferenceNo').html(data);
				 $(data).find('.displayReferenceNodeId').each(function(){
		                $('#ShowNodeDropDown').append($(this).html());
		          });
			//	$("#refID_"+res).val($('#ReferenceNo').html());
			
				$("#refID_"+res).css('background','#ffffff');
				ref_textbox_id=res;
			}	  				    		
		});
		
		return true;
	}

	function changenodereference(ref_node_id)
	{
		
		if(ref_node_id!=null && ref_node_id!="null" )
		{
			$("#refID_"+ref_textbox_id).val(ref_node_id);
			$("#refID_"+ref_textbox_id).css('background','white');
		
		
			$("#refID_"+ref_textbox_id).animate({ 
				backgroundColor: "#ffffff",
				borderRadius: '10px',
				border:'5px solid black',
				color:'#fff' 
			}, 'slow');

		

		/*
			$("#refID_"+ref_textbox_id).animate({
				 backgroundColor: "#fff" 
			}, 'slow');
		*/


		/*
			$("#refID_"+ref_textbox_id).animate({
				color: 'rgba(242, 47, 76, 0.9)'
			},'slow');
		*/

		
			$("#refID_"+ref_textbox_id).animate({
				borderRadius: '0px',
				border:'1px solid black',
				color:'#000'
			},'slow');
	

		}
		
	}
</script>

<div align="center"
	style="width: 95%; margin-left: 3%; padding-bottom: 50px; padding-top: 50px;">
<div class="headercls" align="center" id="HeaderDiv" style="width: 100%">
Select Files To Create Submission Sequence</div>


<div align="right"
	style="width: 100%; height: 600px; border: 1px solid #5A8AA9; padding-bottom: 10px;"><input
	id="wsId" type="hidden" value="<s:property value="workSpaceId"/> " />
<br>
<div id="treeDiv" style="display: none;" align="center"></div>
<div id="msgDiv" style="display: none;"></div>
<center><s:form id="TreeForPublish" name="TreeForPublish"
	method="post" action="SavePublishAndSubmitForm">
	<s:hidden name="manualMode" value="manualMode"></s:hidden>
	<div Style="padding-bottom: 15px; padding-right: 8px"><s:submit
		name="submitBtn" id="submitBtn" value="Publish" cssClass="button"
		align="right" /></div>
	<div align="center"
		style="width: 100%; height: 550px; overflow: auto; border-top: 1px solid #5A8AA9;">
	${treeHtml}</div>
	<s:hidden name="projectPublishType"></s:hidden>
</s:form></center>
</div>

<div id="ShowNodeDropDown"></div>

</div>
