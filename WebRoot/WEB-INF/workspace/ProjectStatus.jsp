<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery/charts/highcharts.src.js"></script>


<script type="text/javascript">		

		var chart;
		$(document).ready(function() {
			chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
					
				},
				legend:{
					enabled:true
				},
				title: {
					text: ''
				},
				
				tooltip: {
					formatter: function() {
						return '<b>'+ this.point.name +'</b>: '+ this.y +'%'+'('+this.point.count+')';;
					}
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						size:220,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							color: '#000000',
							connectorColor: '#000000',
							formatter: function() {
								return '<b>'+ this.point.name +'</b>: '+ this.y +'%'+'('+this.point.count+')';
							}
						}
					}					
				},
			    series: [{
					type: 'pie',
					name: 'Document Status',
					data: [
							<s:property value="htmlContentAll"/>
						  ]
				}]
			});
		});
	
		</script>
</head>
<body>
<div id="container"
	style="width: 578px; height: 340px; margin: 0 auto; overflow: auto; z-index: inherit;">
</div>
</body>
</html>
