<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
#source_tree {
	float: left;
	width: 420px;
	height: 400px;
	overflow: auto;
	border: 1px solid;
	font: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: black;
	margin-left: 10px;
	margin-top: 0px;
}

#dest_tree {
	float: right;
	width: 420px;
	height: 400px;
	overflow: auto;
	border: 1px solid;
	font: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: black;
	margin-right: 10px;
	margin-top: 0px;
}

.copy_field1 {
	width: 6.5ex;
	margin: 20px 0px 0px 0px;
}

.clickTree {
	background-color: #F0F8FF;
	padding: 0px 2px;
	border: 1px dotted #444;
	color: black;
}
</style>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/loading.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/jquery.treeview.css" />
<script src="<%=request.getContextPath()%>/js/jquery_tree.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.treeview.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.cookie.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/loading_bar.js"
	type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function(){
		$("input[@name=sorceWorkspaceId]").val("");
		$("input[@name=destWorkspaceId]").val("");
	
		$("label.srcTreeItem").click(function(event,ui){
			var sourceid=$(this).attr("id");
			var previousSrcId = $("input[@name=sorceWorkspaceId]").val();
			var prevImageId="img."+previousSrcId;
			
			if(event.target.className=='srcTreeItem')
			{
				$(this).add(document.getElementById(sourceid)).addClass('clickTree');
				var imageid="img."+sourceid;
				//alert(imageid);
				$(imageid).attr({  
				 src:"images/jquery_tree_img/blue-tick.gif",
				 style:"display:inline"
				}); 
			
				$(this).add(document.getElementById(sourceid)).text($(imageid));
				var sourceId=$("input[@name=sorceWorkspaceId]").val(sourceid);
			}
			else
			{
				$(this).add(document.getElementById(sourceid)).removeClass('clickTree');
				var imageid="img."+sourceid;
				//alert(imageid);
				$(imageid).attr({  
					 style:"display:none"
				}); 
				var sourceId=$("input[@name=sorceWorkspaceId]").val("");
			}
			
			/*Remove previous Source Node selection*/
			/*	$("#sTree label").each(function(event){
				var label_class=$(this).attr("class");
				var sourceid=$(this).attr("id");
				
				if(label_class!='srcTreeItem')
				{
					var imageid="img."+sourceid;
					
					$(this).removeClass('clickTree');
					$(imageid).attr({  
						 style:"display:none"
					});
				}
			});*/
			$(document.getElementById(previousSrcId)).removeClass('clickTree');
			$(prevImageId).attr({  
				 style:"display:none"
			});				
		});
			
		$("label.destTreeItem").click(function(event,ui){
			var targetId=$(this).attr("id");
			var previousTgtId = $("input[@name=destWorkspaceId]").val();
			var prevTrgImageId="img."+previousTgtId;
			
			if(event.target.className=='destTreeItem')
			{
				$(this).add(document.getElementById(targetId)).addClass('clickTree');
				var imageid="img."+targetId;
				//alert(imageid);
				$(imageid).attr({  
				 src:"images/jquery_tree_img/blue-tick.gif",
				 style:"display:inline"
				}); 
				
				$(this).add(document.getElementById(targetId)).text($(imageid));
				$("input[@name=destWorkspaceId]").val(targetId);
			}
			else
			{
				$(this).add(document.getElementById(targetId)).removeClass('clickTree');
				var imageid="img."+targetId;
				//alert(imageid);
				$(imageid).attr({  
					 style:"display:none"
				}); 
				$("input[@name=destWorkspaceId]").val("");
			}
			
			/*Remove previous Destination Node selection*/
			/*$("#dTree label").each(function(event){
				var label_class=$(this).attr("class");
				var targetId=$(this).attr("id");
				if(label_class!='destTreeItem'){
					var imageid="img."+targetId;
					$(this).removeClass('clickTree');
					$(imageid).attr({  
						 style:"display:none"
					});
				}
			});*/
			$(document.getElementById(previousTgtId)).removeClass('clickTree');
			$(prevTrgImageId).attr({  
				 style:"display:none"
			});
		});

		$("#copy").click(function(){
			var souceIdCopy=$("input[@name=sorceWorkspaceId]").val();
			var destIdCopy=$("input[@name=destWorkspaceId]").val();
			//$("#"+destIdCopy+"sNode").css('background','url(../images/jquery_tree_img/file.gif) no-repeat scroll 0 0 transparent');
			//$("#"+destIdCopy+"sNode").css('background-color','red');	
			
			
			if((souceIdCopy==""))
			{
				alert("Please Select Source Project Node...");
				return false;
			}
			if((destIdCopy==""))
			{
				alert("Please Select Destination Project Node...");
				return false;
			}
				
			var splitesrc_id= souceIdCopy.split("_");
			var sourceWsId=splitesrc_id[1];
			var sourceNodeId=splitesrc_id[2];
		
			
			var splitedest_id=destIdCopy.split("_");
			var destinationWsId=splitedest_id[1];
			var destinationNodeId=splitedest_id[2];
			
			var urlOfAction="CopyFile.do";
			var dataofAction="srcNodeId="+sourceNodeId+"&destNodeId="+destinationNodeId+"&srcWsId="+sourceWsId+"&destWsId="+destinationWsId+"";
			var content3 = "<img src='images/jquery_tree_img/loading_bar.gif'/>";		
			$.ajax({ 
   				type: "GET", 
   				url: urlOfAction, 
   				data: dataofAction, 
   				dataType:'html',
   				beforeSend: function(){ 
					showHideLoading('show');
					//TINY.box.show(content3,0,0,0,0,3)
				},
				success: function(){
					showHideLoading('hide');
					$("#"+destIdCopy+"sNode").removeClass('sNode');
					$("#"+destIdCopy+"sNode").addClass('file');
				}
			});
 		});
	});
	function showHideLoading(showOrHide){
 		if(showOrHide == 'show'){
 			document.getElementById("waterMarkdiv").style.display = '';
			document.getElementById("imgId").style.display = '';
		}else{
			document.getElementById("waterMarkdiv").style.display = 'none';
			document.getElementById("imgId").style.display = 'none';
		}			
 }
</script>
<div id="waterMarkdiv"
	style="display: none; position: absolute; z-index: 900; width: 100%; height: 100%; top: 0; left: 0; background: black; opacity: 0.50; filter: alpha(opacity =             50);">

</div>
<div align="center"
	style="display: none; position: absolute; z-index: 1000; width: 100%; height: 100%; top: 100; left: 0"
	id="imgId"><br>
<img src="images/jquery_tree_img/loading_bar.gif" align="middle" /></div>
<center>
<table height="400px" width="900px;" align="center" cellpadding="5px"
	cellspacing="5px;">
	<tr>
		<td valign="top" width="420px;">
		<div id="source_tree" align="left">${srcTreehtmlCode}</div>
		</td>
		<td class="copy_field" id="loading" align="center" valign="middle"
			width="70px;"><input type="button" class="button" name="copy"
			id="copy" value="   >>   " /></td>
		<td valign="top" width="420px;">
		<div id="dest_tree" align="left">${decTreehtmlCode }</div>
		</td>
	</tr>
</table>
</center>
<!-- 				
<div id="source_tree">				
	${srcTreehtmlCode}
</div>
<div class="copy_field" id="loading" align="center">
	<input type="button" class="button" name="copy" id="copy" value=" >> "/>
</div>
<div id="dest_tree">
	${decTreehtmlCode }
</div>
 -->
<div id="msg"></div>



<s:hidden name="sorceWorkspaceId" id="sorceWorkspaceId"></s:hidden>
<s:hidden name="destWorkspaceId" id="destWorkspaceId"></s:hidden>



