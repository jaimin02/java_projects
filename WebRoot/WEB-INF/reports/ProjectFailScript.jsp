<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.button.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.position.js"></script>
<script src="js/jquery/autocompleter/js/jquery.ui.autocomplete.js"></script>

<script type='text/javascript'
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'></script>
<style>
.ui-draggable {
	z-index: 10000 !important;
	height: 1000px !important;
	width: 1000px !important;
	top: -2808px;
	left: 179px !important;
}

.fileInfoDialog {
	position: fixed;
	background-color: #103050;
	color: white;
	display: none;
	height: 420px;
	margin-top: 160px;
	width: 780px;
}

#fileDiv {
	margin-top: 15px;
	margin-bottom: 15px;
	height: 925px;
	width: 950px;
}

.ui-dialog-title {
	float: left;
	padding: 10px;
}

.ui-icon {
	float: right;
	margin: 10px;
}

.ui-button {
	margin-left: -1px;
}

.ui-button-icon-only .ui-button-text {
	padding: 0.24em;
}

.ui-autocomplete-input {
	margin: 0;
	padding: 0.23em 0.23em 0.22em;
	width: 35%;
}
.ui-autocomplete{
  	width: 250px !important;
 }
 
 #ScriptTable tr td:nth-last-child(3) {      
      width:100px;
    }

 #ScriptTable  tr td:first-child {      
      width:60px;
    }
 /*   
    .remarktbl tr td:nth-child(2) {      
      width:100px;
    } */
 
</style>
<link href="js/jquery/DataTable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/jquery/DataTable/css/demo_table_jui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery/DataTable/js/jquery.dataTables.min.js"></script>

<script type='text/javascript'
	src='<%=request.getContextPath()%>/js/jquery/jquery-ui-1.8.0.min.js'></script>

<script type="text/javascript">
	var currSearchSpan;

	$(document).ready(
			function() {
				//Setting search text box for IE 7. Its works fine in case of IE 8 and FF 3.6
				if (navigator.appName.indexOf('Microsoft') != -1) {
					if (navigator.appVersion.indexOf('MSIE 7') != -1) {

						var blanketDiv = document
								.getElementById('searchFormBlanketDiv');
						blanketDiv.style.width = '';
						blanketDiv.style.left = 0;
						blanketDiv.style.right = 0;

						var blanketDiv = document.getElementById('searchForm');
						blanketDiv.style.width = '';
						blanketDiv.style.left = 0;
						blanketDiv.style.right = 0;

					}
				}
			});

	function fileopen(actionName) {
		//debugger;
		var url = actionName;
		var encodeurl = encodeURIComponent(url);
		$("#dialog-confirm").dialog(
				{
					resizable : false,
					height : 450,
					width : "auto",
					modal : true,

					open : function(ev, ui) {
						$('#fileDiv').attr('src',
								'PdfDowloadRestrict_ex.do?file=' + encodeurl);
					}
				});
	}

	function getProjectSnapshot(reportType) {
		//debugger;
		//reportType='TableView';
		var workspaceId = document.getElementById('workSpaceId').value;
		if (workspaceId == '-1') {
			$('#ProjectSnapshotDiv').html('');
			return false;
		}
		var mode = "<s:property value='mode'/>";
		var urlOfAction = "ShowProjectNodeHistorySnapshot_ex.do?mode=" + mode;//mode=1  for latest  
		var dataofAction = "workSpaceId=" + workspaceId;
		if (reportType == 'TableView') {
			dataofAction += '&isTableView=true';
		}
		$
				.ajax({
					type : "GET",
					url : urlOfAction,
					data : dataofAction,
					cache : false,
					dataType : 'html',
					beforeSend : function() {
						$('#ProjectSnapshotDiv')
								.html(
										"<img src=\"images/loading.gif\" alt=\"loading ...\" />");
					},
					success : function(data) {
						var javascript = "";
						$('#ProjectSnapshotDiv').html(data);
						if (reportType != 'TableView') {
							if (mode == '1' || mode == '2') // collaps all tree node attribute tables for mode = 1 and mode = 2 
								showHideAllAttributesTable('hide');
						}
					}
				});
		return true;

	}

	function subinfo() {
		debugger;
		var workSpaceId = $('#workSpaceId').val();
		$.ajax({
					url : 'ShowProjectFailScript_ex.do?workSpaceId='+ workSpaceId,
					beforeSend : function() {
						$('#ProjectSnapshotDiv')
								.html(
										"<img src=\"images/loading.gif\" alt=\"loading ...\" />");
					},
					success : function(data) {
						$('#ProjectSnapshotDiv').html(data);
					}
				});
		return true;
	}

	function showAttributes(attrTableId, imgEle) {
		var tableEle = document.getElementById(attrTableId);

		if (tableEle.style.display == 'none') {
			tableEle.style.display = '';
			imgEle.src = "images/jquery_tree_img/minus.gif";
		} else {
			tableEle.style.display = 'none';
			imgEle.src = "images/jquery_tree_img/plus.gif";
		}
	}
	function showHideAllAttributesTable(mode) {
		if (mode == 'show') {
			$('.attrTable').each(function() {
				this.style.display = '';
			});
			$('.AttrToggleImg').each(function() {
				this.src = "images/jquery_tree_img/minus.gif";
			});
		} else if (mode == 'hide') {
			$('.attrTable').each(function() {
				this.style.display = 'none';
			});
			$('.AttrToggleImg').each(function() {
				this.src = "images/jquery_tree_img/plus.gif";
			});
		}
	}

	//Main search call 
	function callSearch() {
		//Remove Previous search results
		$('.currsearchword').each(function() {
			this.className = 'searchword';
		});
		unhighlight(document.getElementById('ProjectSnapshotDiv'));

		//New Search
		localSearchHighlight('?h=' + document.searchhi.h.value, document
				.getElementById('ProjectSnapshotDiv'));
		var matchFound = false;
		//Scroll To First Search Term
		$('.searchword').each(function() {
			ScrollToElement(this);
			currSearchSpan = this;
			currSearchSpan.className = "searchword currsearchword";
			matchFound = true;
			return false;
		});
		//Find all found pharses within the AttrTables   
		if (matchFound) {
			$('.attrTable SPAN.searchword')
					.each(
							function() {
								$(this)
										.parents(".attrTable")
										.each(
												function() {
													this.style.display = '';
													var nodeIdArr = this.id
															.split('_');
													var nodeId = nodeIdArr[nodeIdArr.length - 1];
													document
															.getElementById('attrTable_img_'
																	+ nodeId).src = "images/jquery_tree_img/minus.gif";
												});
							});
		} else {
			document.searchhi.h.style.color = 'red';
		}
	}
	function ScrollToElement(theElement) {

		var selectedPosX = 0;
		var selectedPosY = 0;

		while (theElement != null) {
			selectedPosX += theElement.offsetLeft;
			selectedPosY += theElement.offsetTop - 50;
			theElement = theElement.offsetParent;
		}
		window.scrollTo(selectedPosX, selectedPosY);
	}

	function showSubDtl(targetDivId, ele) {
		var srclinkEle = ele;
		var targetDiv = document.getElementById(targetDivId);
		var popupleft = popuptop = 0;
		popuptop = (getY(srclinkEle) + 5);
		popupleft = (getX(srclinkEle) - 200);
		targetDiv.style.display = 'block';
		targetDiv.style.position = 'absolute';
		targetDiv.style.top = popuptop + 'px';
		targetDiv.style.left = popupleft + 'px';
		targetDiv.style.paddingTop = '20px';
		//targetDiv.style.paddingBottom = '10px';
		targetDiv.style.width = 400 + 'px';
		targetDiv.style.Height = 90 + 'px';

		document.getElementById('closeLinkDiv').style.display = 'block';
		document.getElementById('closeLinkDiv').style.position = 'absolute';
		document.getElementById('closeLinkDiv').style.top = popuptop + 2 + 'px';
		document.getElementById('closeLinkDiv').style.left = (popupleft + 383)
				+ 'px';

		popupDivEle = document.getElementById(targetDivId);
		/*if (srclinkEle.offsetParent) {
			do {
				curleft += srclinkEle.offsetLeft;
				curtop += srclinkEle.offsetTop;
			} while (srclinkEle = srclinkEle.offsetParent);
			
			alert(srclinkEle.style.width+','+srclinkEle.style.left);
			alert(curleft+','+ curtop );
		}*/
	}

	function getY(oElement) {
		var iReturnValue = 0;
		while (oElement != null) {
			iReturnValue += oElement.offsetTop;
			oElement = oElement.offsetParent;
		}
		return iReturnValue;
	}
	function getX(oElement) {
		var iReturnValue = 0;
		while (oElement != null) {
			iReturnValue += oElement.offsetLeft;
			oElement = oElement.offsetParent;
		}
		return iReturnValue;
	}
	var popupDivEle = null;
	function closePopupDiv() {
		popupDivEle.style.display = 'none';
		document.getElementById('closeLinkDiv').style.display = 'none';
	}

	(function($) {
		var calltheData = false;
		$
				.widget(
						"ui.combobox",
						{

							_create : function() {
								var self = this, select = this.element.hide(), selected = select
										.children(":selected"), value = selected
										.val() ? selected.text() : "";
								var input = this.input = $("<input>")
										.insertAfter(select)
										.val(value)
										.autocomplete(
												{
													delay : 0,
													minLength : 0,
													source : function(request,
															response) {
														var matcher = new RegExp(
																$.ui.autocomplete
																		.escapeRegex(request.term),
																"i");
														response(select
																.children(
																		"option")
																.map(
																		function() {
																			var text = $(
																					this)
																					.text();
																			if (this.value
																					&& (!request.term || matcher
																							.test(text)))
																				return {
																					label : text
																							.replace(
																									new RegExp(
																											"(?![^&;]+;)(?!<[^<>]*)("
																													+ $.ui.autocomplete
																															.escapeRegex(request.term)
																													+ ")(?![^<>]*>)(?![^&;]+;)",
																											"gi"),
																									"<strong>$1</strong>"),
																					value : text,
																					option : this
																				};
																		}));
													},
													select : function(event, ui) {
														ui.item.option.selected = true;
														//jQueryOnchangeAutocompleter();
														subinfo();

														self
																._trigger(
																		"selected",
																		event,
																		{
																			item : ui.item.option
																		});
													},
													change : function(event, ui) {
														if (!ui.item) {
															var matcher = new RegExp(
																	"^"
																			+ $.ui.autocomplete
																					.escapeRegex($(
																							this)
																							.val())
																			+ "$",
																	"i"), valid = false;
															select
																	.children(
																			"option")
																	.each(
																			function() {
																				if ($(
																						this)
																						.text()
																						.match(
																								matcher)) {
																					this.selected = valid = true;
																					return false;
																				}
																			});
															if (!valid) {
																// remove invalid value, as it didn't match anything
																$(this).val("");
																select.val("");
																input
																		.data("autocomplete").term = "";
																return false;
															}
														}
													}

												})

										.addClass(
												"ui-widget ui-widget-content ui-corner-left");

								input.data("autocomplete")._renderItem = function(
										ul, item) {
									return $("<li></li>").data(
											"item.autocomplete", item).append(
											"<a>" + item.label + "</a>")
											.appendTo(ul);
								};

								this.button = $(
										"<button type='button'>&nbsp;</button>")
										.attr("tabIndex", -1)
										.attr("title", "Show All Items")
										.insertAfter(input)
										.button(
												{
													icons : {
														primary : "ui-icon-triangle-1-s"
													},
													text : false
												})
										.removeClass("ui-corner-all")
										.addClass(
												"ui-corner-right ui-button-icon")
										.click(
												function() {
													// close if already visible
													if (input.autocomplete(
															"widget").is(
															":visible")) {
														input
																.autocomplete("close");
														return;
													}

													// pass empty string as value to search for, displaying all results
													input.autocomplete(
															"search", "");
													input.focus();
												});
							},

							destroy : function() {
								this.input.remove();
								this.button.remove();
								this.element.show();
								$.Widget.prototype.destroy.call(this);
							}
						});
	})(jQuery);

	$(function() {
		$("#workSpaceId").combobox();
		$("#workSpaceId").click(function() {
			$("#toggle").toggle();

		});
	});
//-->
</script>
<script type="text/javascript"
	src="js/jquery/ScrollTop/scrolltopcontrol.js"></script>
<style>
<!--
SPAN.searchword {
	background-color: yellow;
}

SPAN.currsearchword {
	background-color: green;
	color: white;
}

TABLE.attrTable {
	margin-left: 18px;
}
//
-->
</style>
<script src="js/find/searchhi.js" type="text/javascript"
	language="JavaScript"></script>

</head>
<body>
	<div class="container-fluid">
		<div class="col-md-12">
			<div class="fileInfoDialog">
				<div id="dialog-confirm" title="OpenFile" align="center">
					<iframe id="fileDiv" name="fileDiv" src=""></iframe>
				</div>
			</div>
			<div class="boxborder">
				<div class="all_title">
					<b style="float: left">Project Fail Script</b>
				</div>
			</div>
			<!-- <img
	src="images/Header_Images/Report/Project_Node_History_Snapshot.jpg"
	style="margin-bottom: -5px; padding-bottom: -5px; border-bottom: 1px solid #5A8AA9;"> -->

			<div
				style="border: 1px; border-color: #5A8AA9; border-style: solid; padding-bottom: 15px; border-top: none;"
				align="center">
				<br>
				<div align="center">
					<br>
					<s:hidden id="mode" name="mode" />
					<table Style="width: 100%">
						<tr>
							<td class="title" align="right" width="50%"
								style="padding: 2px; padding-right: 8px;">Project</td>
							<td><s:select id="workSpaceId" name="workSpaceId"
									list="getWorkspaceDetail" listKey="workSpaceId"
									listValue="workSpaceDesc" headerKey="-1" headerValue="">
								</s:select></td>
						</tr>
					</table>

				</div>

					<div id="AttrHistoryDiv" style="display: none; z-index: 2000;"></div>

				<div id="closeLinkDiv" style="z-index: 2100; display: none;">
					<img alt="Close" title="Close" src="images/Common/Close.png"
						id="closeLink" onclick="closePopupDiv();">
				</div>
				<div id="ProjectSnapshotDiv"
					style="padding-left: 20px; padding-right: 20px;"></div>
				<br />


			</div>
		</div>
	</div>
</body>
</html>