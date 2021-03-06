var MonInstGraphExt = (function() {

    function render(params) {
    	var instanceId = $('input#field_row_id')[0].value;
    	$ui.loadCharts(function(){
    		getHealthData(instanceId, function cb(opts){
    			$ui.charts.chart($("#health-graph"), opts);
    		});
		});
    }
    
    function getHealthData(instanceId, cb){
		var health = $ui.getAjax().getBusinessObject("MonHealth");
		health.search(function(rows){
			var diskData = [];
			var heapData = [];
			rows.forEach(row => {
				diskData.push({
					x: row.monHeaDate,
					y: row.monHeaDiskUsage*100
				});
				heapData.push({
					x: row.monHeaDate,
					y: row.monHeaHeapUsage*100
				});
			});
			cb({
				type: 'line',
				data: {
					datasets:[{
						label: 'Disk',
						data: diskData,
						borderColor: 'rgba(75, 192, 192, 1)',
						backgroundColor: 'rgba(75, 192, 192, 0.2)',
						pointRadius: 1,
						pointHitRadius: 5,
						tension: 0.1
					},{
						label: 'Heap',
						data: heapData,
						borderColor: 'rgba(54, 162, 235, 1)',
						backgroundColor: 'rgba(54, 162, 235, 0.2)',
						pointRadius: 1,
						pointHitRadius: 5,
						tension: 0.1
					}]
				},
				options: {
					scales: {
						yAxes: [{
							scaleLabel:{
								display: true,
								labelString: 'Usage (%)'
							},
							ticks: {
								min: 0,
								max: 100,
								beginAtZero:true
							}
						}],
						xAxes: [{
							type: 'time'
						}]
					}
				}
			});
		}, {monHeaInstId: instanceId, order__monHeaDate: 1});
    }

    return { render: render };
})();