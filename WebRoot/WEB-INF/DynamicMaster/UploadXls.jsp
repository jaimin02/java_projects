<html>
<head>
<title>Upload Excel File</title>
<script type="text/javascript" src="js/jquery/jquery.form.js"></script>
<script type="text/javascript">
			$(document).ready(function()
			{
				$('table.datatable tr:odd').addClass('odd');
				$('table.datatable tr:even').addClass('even');
				$("#updButton").click(function()
				{
					$("#result").html('');
					$("#result1").html('');
					var data=$('input[type=file]').val();
					if (data==null || data.length==0 || data.indexOf('xls')==-1)
					{
						alert('Please select a xls file!');
						return false;
					}					
					$("#iniUpdDiv").slideUp(1000);
					return true;					
				});
				var options = 
				{
					target: '#result',
					beforeSubmit: showRequest					
				};
				$('#frmIniUpld').submit(function()
				{		
					$(this).ajaxSubmit(options);
					return false;
				});
				var options1 = 
				{
					target: '#result1',
					beforeSubmit: showRequest						
				};							
			});	
			function showRequest(formData, jqForm, options)
			{
				//alert($.param(formData));
				return true;
			}		
		</script>

<script type="text/javascript">
			function checkTableName()
			{
				var tbName=document.getElementById('tableName').value;
				if (tbName==null)
					return;
				tbName=trim(tbName);
				if (tbName=='')
					return;
				$.ajax({			
							url: 'CheckTableExists_ex.do?tableName=' + tbName,
							beforeSend: function()
							{ 
								$("#img").css('display','');								
							},
					  		success: function(data) 
					  		{
								$("#img").css('display','none');
								$("#resDiv").css('display','');
								$("#resDiv").html(data);							
							},
							async: false							 
					});
			}

			function validate()
			{
				checkTableName();
				var data=document.getElementById("resDiv").innerHTML;			
				var noErr=true;
				if (data.length==0)
					noErr=false;
				else
					if (data.indexOf('Error')!=-1)
						noErr=false;			
				if (noErr==false)
				{
					if (data.length==0)
						alert('First check whether the table is present or not!');
					else
						alert('Table already exists, please choose another name!');				
				}
				var noofcols=$("input[name=columnNames]").length;
				$("input[name=columnNames]").each(function()
				{
					if (noErr)
					{
						var colnm=$(this).val();
						if (colnm==null || colnm.length==0)
						{
							alert("One of the column names is blank!");
							noErr=false;					
						}
					}
				});
				if (noErr==true)	
					subForm();				
				return false;	
			}

			function trim(str)
			{
			   	str = str.replace( /^\s+/g, "" );// strip leading
				return str.replace( /\s+$/g, "" );// strip trailing
			}	

			function subForm()
			{				
				var str="";			
				$("input[name=columnNames]").each(function()
				{
					var colNm=$(this).val();
					if (!isNaN(parseInt(colNm)))
					{
						alert('Column name (' + colNm + ') is incorrect!!!');
						return false;
					}
					str=str+'columnNames=' + colNm + "&";					
				});
				str=str+"tableName="+$("#tableName").val();
				str=str+"&uploadedFilePath="+$("input[name=uploadedFilePath]").val();				
				$.ajax
				({			
					url: 'DBImport_ex.do?' + str,
					success: function(data) 
			  		{
						$("#result1").html(data);							
					}										
				});
				$("#confirmMetadataDiv").slideUp(1000);				
			}

			function func1()
			{
				$("#iniUpdDiv").slideDown(1000);
				$("#errorDiv").slideUp(1000);
				$("#confirmMetadataDiv").slideUp(1000);
			}
		</script>
</head>
<body>
<div align='center' id="iniUpdDiv">
<form action="InitialUpload_ex.do" method="post"
	enctype="multipart/form-data" name='frmIniUpld' id="frmIniUpld"
	onsubmit="validate();">
<table class='datatable'>
	<tr>
		<th colspan='2' style="text-align: center;">Upload XLS File</th>
	</tr>
	<tr class='odd'>
		<td><label for="xlsDoc">Select File</label></td>
		<td><input type="file" name="xlsDoc" id="updFile" /></td>
	</tr>
	<tr class='even'>
		<td colspan="2" align="center" style='text-align: center;'><input
			type="submit" value="Upload" class='button' id='updButton' /></td>
	</tr>
</table>
</form>
</div>
<br>
<br>
<div align='center' id="result"></div>
<br>
<br>
<div align='center' id="result1"></div>
</body>
</html>