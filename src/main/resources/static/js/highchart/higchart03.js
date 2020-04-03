$(function(){
	$.ajax({
		type:"post",
		url:"../main/chartline",
		dataType:"json",
		success:function(res){
			//alert(JSON.stringify(res));
			//{"data":["1","5"],"categories":["03","04"]}
			var chart = Highcharts.chart('chart3', {
			    chart: {
			        type: 'line'
			    },
			    title: {
			        text: '月出借申请单据数量统计',
			        style:{"color": "#FFFFFF", "fontSize": "18px"}
			       
			    },
			    subtitle: {
			        text: '数据来源: 华信智原'
			    },
			    xAxis: {
			        categories: res.categories
			    },
			    yAxis: {
			        title: {
			            text: '申请单数量'
			        }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                // 开启数据标签
			                enabled: true          
			            },
			            // 关闭鼠标跟踪，对应的提示框、点击事件会失效
			            enableMouseTracking: false
			        }
			    },
			    series: [{
			        name: 'p2p',
			        data: res.data
			    }]
			});
		}
	})
})

