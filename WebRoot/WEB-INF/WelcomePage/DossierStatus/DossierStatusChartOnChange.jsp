<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery/charts/highcharts.src.js"></script>
<script type="text/javascript">			
		var s ='<s:property value="dataNull"/>';
		if(s=='yes'){
			$("#piechartcontainerdata").hide();
			$("#nodata").show();
		}else{						
			var chart;
			$("#nodata").hide();
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'piechartcontainerdata'
					},
					title: {
						text: ''
					},
					plotArea: {
						shadow: null,
						borderWidth: null,
						backgroundColor: null
					},
					tooltip: {
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					},
					
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								formatter: function() {
									return "<b>"+this.y+"%</b>("+this.point.count+")";									
								}
							},
							
							showInLegend: true
						}
					},
					legend:{
						align:"horizontal"
					},
				    series: [{
						type: 'pie',
						name: 'Browser share',
						data: [
								<s:property value="allGraphDetail"/>																
						      ]
					}]
				}); 
			});
		}		
		</script>
</head>
<body>
<div id="piechartcontainerdata"
	style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="nodata" align="center"
	style="width: 550px; height: 300px; margin: 0 auto;">
<table width="100%" height="100%">
	<tr>
		<td width="40%" align="right"><img alt="No Data Found"
			src="images/ectd/warning.gif" /></td>
		<td width="60%" align="left" style="color: #3E576F">&nbsp;&nbsp;No
		Data Found</td>
	</tr>
</table>
</div>
</body>
</html>