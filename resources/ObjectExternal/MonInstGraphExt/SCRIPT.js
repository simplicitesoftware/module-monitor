var MonInstGraphExt = (function() {

    function render(params) {
    	$ui.loadCharts(function(){
    		getHealthData(function cb(opts){
    			console.log(opts);
    			$ui.charts.chart($("#health-graph"), opts);
    		});
		});
    }
    
    function getHealthData(cb){
		var health = $ui.getAjax().getBusinessObject("MonHealth");
		health.search(function(rows){
			var datesData = [];
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
				datesData.push(row.monHeaDate);
				/*diskData.push(row.monHeaDiskUsage);
				heapData.push(row.monHeaHeapUsage);*/
			});
			cb({
				type: 'line',
				data: {
					//labels: datesData,
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
							type: 'time'/*,
                			distribution: 'linear',
                			time: {
                				round: 'day'
                			}*/
						}]
					}
				}
			});
		}, {monHeaInstId: 503, order__monHeaDate: 1});
    }
    
    function bouchon(cb){
    	cb({
		    type: 'line',
		    data: {
		        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
		        datasets: [{
		            label: '# of Votes',
		            data: [12, 19, 3, 5, 2, 3],
		            backgroundColor: [
		                'rgba(255, 99, 132, 0.2)',
		                'rgba(54, 162, 235, 0.2)',
		                'rgba(255, 206, 86, 0.2)',
		                'rgba(75, 192, 192, 0.2)',
		                'rgba(153, 102, 255, 0.2)',
		                'rgba(255, 159, 64, 0.2)'
		            ],
		            borderColor: [
		                'rgba(255,99,132,1)',
		                'rgba(54, 162, 235, 1)',
		                'rgba(255, 206, 86, 1)',
		                'rgba(75, 192, 192, 1)',
		                'rgba(153, 102, 255, 1)',
		                'rgba(255, 159, 64, 1)'
		            ],
		            borderWidth: 1
		        }]
		    },
		    options: {
		        scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero:true
		                }
		            }]
		        }
		    }
		});
    }

    return { render: render };
})();