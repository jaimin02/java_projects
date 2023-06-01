<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<script type="text/javascript"
	src="js/jquery/DataTable/js/jquery.dataTables.js"></script>
<script type="text/javascript">
			$(document).ready(function()
			{
				$('table.datatable tr:odd').addClass('odd');
				$('table.datatable tr:even').addClass('even');
			});
			function trim(str)
			{
			   	str = str.replace( /^\s+/g, "" );// strip leading
				return str.replace( /\s+$/g, "" );// strip trailing
			}
			function selTable()
			{
				var tbName=document.getElementById('tableName').value;
				if (tbName==null) 
					return;
				if (tbName=='-1')
				{
					$("#resDiv").css('display','none');
					$("#resDiv").html('');
					$("#grid").css('display','none');
					$("#grid").html('');
					return;
				}
				tbName=trim(tbName);
				$.ajax({			
					url: 'GetTableDetails_ex.do?tableName=' + tbName,
					beforeSend: function()
					{ 
						$("#img").css('display','');
						$("#resDiv").css('display','none');
						$("#resDiv").html('');
						$("#grid").html('');								
					},
			  		success: function(data) 
			  		{
						$("#img").css('display','none');
						$("#resDiv").html(data);
						$('table.datatable tr:odd').addClass('odd');
						$('table.datatable tr:even').addClass('even');
						$("#resDiv").slideDown('slow');												
					}										
				});
			}
			function displayGrid()
			{
				var tbName=document.getElementById('tableName').value;
				if (tbName==null || tbName=='-1')
					return;
				tbName=trim(tbName);
				$.ajax({			
					url: 'GetTableData_ex.do?tableName=' + tbName,
					beforeSend: function()
					{ 
						$("#img").css('display','');	
						$("#grid").css('display','none');							
					},
			  		success: function(data) 
			  		{
						$("#img").css('display','none');
						$("#grid").html(data);						
						$('table.datatable tr:odd').addClass('odd');
						$('table.datatable tr:even').addClass('even');						
						$("#grid").slideDown('slow');
						var lastPg=($('#tableGrid tr').size()-1)/2;		
						if (parseInt(lastPg/5)!=lastPg/5)		
							lastPg=parseInt(lastPg/5)+1;
						else
							lastPg=parseInt(lastPg/5);
						$('#totPgs').text(lastPg);
						var optionsText='';
						var index=1;
						for (index=1;index<=lastPg;index++)
						{
							optionsText+="<option value='" + index + "'>" + index + "</option>";
						}
						$('#pgSel').html(optionsText);						
					}							 
				});
			}
			function showRow(trId)
			{
				$('#editRow' + trId).css('display','');
				$('#dataRow' + trId).css('display','none');				
			}
			function hideRow(trId)
			{
				var paramStr="?";
				var newVals = new Array();				
				$('#dataRow' + trId + ' td').each(function()
				{
					var tdText=$(this).text();
					if (tdText!=null && typeof(tdText)!='undefined' && tdText!='')
						paramStr=paramStr+"oldValues="+tdText+"&";
					else
						paramStr=paramStr+"oldValues=&";
				});
				var index=0;				
				$("#editRow" + trId + " td input[name=newValues]").each(function()
				{
					newVals[index]=$(this).val();
					paramStr = paramStr + "newValues=" + newVals[index] + "&";
					index++;
				});
				var tbName=document.getElementById('tableName').value;
				if (tbName==null || tbName=='-1')
					return;
				tbName=trim(tbName);
				paramStr=paramStr+"tableName="+tbName;
				//paramStr=paramStr.substr(0,paramStr.length-1);
				//alert(paramStr);
				$.ajax
				({			
					url: 'SaveTableRecord_ex.do' + paramStr,
					beforeSend: function()
					{ 
						$("#img"+trId).css('display','');
					},
			  		success: function(data) 
			  		{
						$("#img"+trId).css('display','none');
						alert(data);
						if (data.indexOf('Successful')!=-1)
						{
							index=0;
							$('#dataRow' + trId + ' td.data').each(function()
							{
								if (newVals[index].length>10)
									$(this).text(newVals[index].substring(0,10) + '...');
								else
									$(this).text(newVals[index]);
								$(this).attr('title',newVals[index]);
								index++;								
							});
							index=0;
							$("#editRow" + trId + " td input[name=newValues]").each(function()
							{
								$(this).attr('title',newVals[index]);
								index++;
							});
						}
						$('#editRow' + trId).css('display','none');
						$('#dataRow' + trId).css('display','');						
			  		}							 
				});						
			}
			function hideRowWithoutSave(trId)
			{	
				$('#editRow' + trId).css('display','none');
				$('#dataRow' + trId).css('display','');			  						
			}
			function tglDiv(divId,sgnId)
			{
				$('#'+divId).slideToggle('slow');
				if ($('#'+sgnId).html()=='[+]')
					$('#'+sgnId).html('[=]');
				else
					$('#'+sgnId).html('[+]');
			}
			function addRecord()
			{
				var trString='';
				var trString1='';
				var paramString='?';
				var count=0;
				$('#tableGrid tr').each(function()
				{					
					var str=$(this).attr('id');
					if (str != null && typeof(str)!='undefined' && str.indexOf('dataRow')!=-1)
						count++;
				});
				count++;
				$('#addRow td input[type=text]').each(function()
				{
					trString+='<td  class="data" title="' + $(this).val() + '">' + $(this).val() + '</td>';
					trString1+="<td><input type='text' name='newValues' value='" + $(this).val() + "' style='width:90px;' title='" + $(this).val() + "'/></td>";
					paramString+='newValues=' + $(this).val() + '&';
					$(this).val('');
				});
				//trString+="<td><img src='images/Common/edit.png' alt='Edit' onclick='showRow(" + count + ");'><input style='display:none;' type='button' class='button' value='Edit' onclick='showRow(" + count + ");'></td>";
				trString1+="<td><input type='button' class='button' value='Save' onclick='hideRow(" + count + ");'/>&nbsp;<input type='button' class='button' value='Cancel' onclick='hideRowWithoutSave(" + count + ");' /><img src='images/loading.gif' alt='loading ...' style='display: none;' id='img" + count + "' /></td>";
				var lastPg=$('#totPgs').text();
				$('#tableGrid tr:last').after("<tr></tr>");
				$('#tableGrid tr:last').addClass("Page-" + lastPg);
				$('#tableGrid tr:last').after("<tr></tr>");								
				$('#tableGrid tr:last').prev().addClass("odd");
				$('#tableGrid tr:last').prev().html(trString);
				$('#tableGrid tr:last').prev().attr('id','dataRow'+count);
				
				$('#tableGrid tr:last').addClass("even");
				$('#tableGrid tr:last').html(trString1);
				$('#tableGrid tr:last').attr('id','editRow'+count);
				$('#tableGrid tr:last').css('display','none');

				var tbName=document.getElementById('tableName').value;
				if (tbName==null || tbName=='-1')
					return;
				tbName=trim(tbName);
				paramString=paramString+"tableName="+tbName;
				$.ajax
				({			
					url: 'AddTableRecord_ex.do' + paramString,
					beforeSend: function()
					{ 
						$("#addimg").css('display','');
					},
			  		success: function(data) 
			  		{
						$("#addimg").css('display','none');
						alert(data);
			  		}
				});							
			}

			function pgChng()
			{
				var newPg=$('#pgSel').val();
				$('tr[class*=Page]').each(function()
				{
					$(this).hide();
				});
				$('tr.Page-' + newPg).each(function()
				{
					$(this).show();
				});
			}
			
			function nextPage()
			{
				var currPg=$('#currPg').text();
				var pgNo=currPg.substr(currPg.indexOf('-')+1);
				var nxtPgClass='Page-' + (parseInt(pgNo)+1);
				var lastPg=$('#totPgs').text();				
				if (parseInt(pgNo) == lastPg)
					return;
				$('tr[class*=Page]').each(function()
				{
					$(this).hide();
				});
				$('tr.' + nxtPgClass).each(function()
				{
					$(this).show();
				});
				$('#currPg').text(nxtPgClass);				
			}

			function prevPage()
			{
				var currPg=$('#currPg').text();
				var pgNo=currPg.substr(currPg.indexOf('-')+1);
				var nxtPgClass='Page-' + (parseInt(pgNo)-1);
				if (parseInt(pgNo) == 1)
					return;
				$('tr[class*=Page]').each(function()
				{
					$(this).hide();
				});
				$('tr.' + nxtPgClass).each(function()
				{
					$(this).show();
				});
				$('#currPg').text(nxtPgClass);
			}
		</script>
</head>
<body>
<div align="center">Please Select Table <select name="tableName"
	onchange="selTable();" id="tableName">
	<option value="-1">Please Select Table</option>
	<s:iterator value="xlsTableList" status="status">
		<option value="<s:property value="vTableName"/>"><s:property
			value="vTableName" /></option>
	</s:iterator>
</select></div>
<br>
<div align='center'><img align="middle" id="img"
	style="text-align: center; display: none;" src="images/loading.gif"></div>
<br>
<div id="resDiv" style="display: none;"></div>
<div align='center'>
<div id="grid"
	style="display: none; width: 800px; overflow: auto; text-align: center"
	align="center"></div>
</div>
</body>
</html>