$(function(){
	$.ajax({
		type:"post",
		url:"../main/chartcolumn",
		dataType:"json",
		success:function(res){
			//alert(JSON.stringify(res));
			Highcharts.chart('chart1', {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: '周划扣最大金额统计',
			        style:{"color": "#FFFFFF", "fontSize": "18px"}
			    },
			    subtitle: {
			        text: '点击可查看具体的版本数据，数据来源',
			        style:{"color": "#FFFFFF"}
			    },
			    xAxis: {
			        type: 'category'
			    },
			    yAxis: {
			        title: {
			            text: '最大金额',
			            style:{"color": "#FFFFFF"}
			        }
			    },
			    legend: {
			        enabled: false
			    },
			    plotOptions: {
			        series: {
			            borderWidth: 0,
			            dataLabels: {
			                enabled: true,
			                //format: '{point.y:.1f}%'
			            }
			        }
			    },
			    tooltip: {
			        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
			        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b> <br/>'
			    },
			    series: [{
			        name: '最大出借金额',
			        colorByPoint: true,
			        data:res
			    }]
			    
			});
		}
	})
})

