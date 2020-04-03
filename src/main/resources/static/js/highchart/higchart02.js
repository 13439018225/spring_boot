$(function(){
	$.ajax({
		type:"post",
		url:"../main/chartpie",
		dataType:"json",
		success:function(res){
			//{"title":"各省份投资者数量统计","list":[{"y":16.67},{"y":16.67,"name":"黑龙江省"},{"y":66.67,"name":"北京市"}]}
			//alert(JSON.stringify(res));
			Highcharts.chart('chart2', {
				chart: {
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						type: 'pie'
				},
				title: {
						text: res.title,
						style:{"color": "#FFFFFF", "fontSize": "18px"}
				},
				tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
				},
				plotOptions: {
						pie: {
								allowPointSelect: true,
								cursor: 'pointer',
								dataLabels: {
										enabled: true,
										format: '<b>{point.name}</b>: {point.percentage:.2f} %',
										style: {
												color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
										}
								}
						}
				},
				series: [{
						name: '投资者数量百分比',
						colorByPoint: true,
						data: res.list
				}]
		});
		}
	})
})
