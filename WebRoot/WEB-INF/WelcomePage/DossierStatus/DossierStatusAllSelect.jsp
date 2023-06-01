<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery/charts/highcharts.src.js"></script>
<script type="text/javascript">	
		
		var chart;						
		$(document).ready(function() {
			
			<s:iterator value="allStachBarGraphDetail" status="status">				
				chart = new Highcharts.Chart({
				chart: {
					renderTo: 'stackbarcontainer<s:property value="#status.count"/>',
					defaultSeriesType: 'bar'
				},
				title: {
					text: ''
				},
				xAxis: {
	categories: <s:property value="xaxis"/>
				},
				yAxis: {
					min: 0,
					title: {
						text: ' '
					}
				},
				legend: {
					backgroundColor: '#FFFFFF',
					reversed: true
				},
				tooltip: {
					formatter: function() {
						return ''+
							 this.series.name +': '+ this.y +'';
					}
				},
				plotOptions: {
					series: {
						stacking: 'normal'
					}
				},
			       series: [<s:property value="region"/>]
			});
				</s:iterator>
		});		
		
		</script>
</head>
<body>

<table>
	<tr>
		<td colspan="3"><s:iterator value="allStachBarGraphDetail"
			status="status">
			<div id="stackbarcontainer<s:property value="#status.count"/>"
				align="center"
				style="  width: 550px; height:<s:property value='height'/>px; margin: 0 auto "></div>
		</s:iterator></td>
	</tr>
</table>
</body>
</html>
