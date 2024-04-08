var MonInstGraphExt = (function() {

    function render(params) {
    	var instanceId = $('input#field_row_id')[0].value;
    	$ui.loadCharts(function(){
    		getHealthData(instanceId, function cb(opts){
    			$ui.charts.chart($("#health-graph"), opts);
    		});
		});
    }
    
    // configured for https://www.chartjs.org/docs/3.9.1
    function getHealthData(instanceId, cb){
		var health = $ui.getAjax().getBusinessObject("MonHealth");
		health.search(function(rows){
			var sessionData = [];
			var usersData = [];
			var attributesData = [];
			var heapData = [];
			rows.forEach(row => {
				sessionData.push({
					x: row.monHeaDate,
					y: row.monHeaSessions
				});
				usersData.push({
					x: row.monHeaDate,
					y: row.monHeaTotalUsers
				});
				attributesData.push({
					x: row.monHeaDate,
					y: row.monHeaAttributes
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
						label: 'Sessions',
						data: sessionData,
						yAxisID: 'y1',
						borderColor: 'rgba(75, 192, 192, 1)',
						backgroundColor: 'rgba(75, 192, 192, 0.2)',
						pointRadius: 1,
						pointHitRadius: 5,
						tension: 0.1
					},{
						label: 'Total Users',
						data: sessionData,
						yAxisID: 'y1',
						borderColor: 'rgba(232, 142, 237, 1)',
						backgroundColor: 'rgba(232, 142, 237, 0.2)',
						pointRadius: 1,
						pointHitRadius: 5,
						tension: 0.1
					},{
						label: 'Configured attributes',
						data: attributesData,
						yAxisID: 'y1',
						borderColor: 'rgba(187, 68, 48, 1)',
						backgroundColor: 'rgba(187, 68, 48, 0.2)',
						pointRadius: 1,
						pointHitRadius: 5,
						tension: 0.1
					},{
						label: 'Heap',
						data: heapData,
						yAxisID: 'y2',
						borderColor: 'rgba(54, 162, 235, 1)',
						backgroundColor: 'rgba(54, 162, 235, 0.2)',
						pointRadius: 1,
						pointHitRadius: 5,
						tension: 0.1,
						tooltip: {
							callbacks: {
								label: (i)=>i.dataset.label+': '+i.formattedValue+'%'
							}
						}
					}]
				},
				options: {
					scales: {
						x: {
							type: 'time'
						},
						y1: {
							position: 'left',
							min: 0,
							beginAtZero:true
						},
						y2: {
							position: 'right',
							min: 0,
							suggestedMax: 100,
							beginAtZero: true
						}
					}
				}
			});
		}, {monHeaInstId: instanceId, order__monHeaDate: 1});
    }

    return { render: render };
})();