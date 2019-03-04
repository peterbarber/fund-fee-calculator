jQuery(function(){
      jQuery("#calculator").load("http://fundfeecalculator.herokuapp.com/calculator.html", function() {
    	  init();
      });
});

google.load("visualization", "1", {
	packages : [ "corechart" ]
});

//google.setOnLoadCallback(init);

function init() {
	PieChart = new initPieChart();
	AreaChart = new initAreaChart();
	//PieChart.showCalculatingSpinner();
	//sendGetRequest();
	initToolTip();

	var button = document.getElementById('calculateButton');
	// onlick works in < IE9 (addEventHandler does not - allows multiple
	// handlers per event)
	button.onclick = function() {
//		PieChart.showCalculatingSpinner();
		sendGetRequest();
	}
}

function initToolTip() {
	jQuery(document).ready(function(){
		jQuery('[data-toggle="tooltip"]').tooltip({placement : 'bottom'}); 
	});
}

function initPieChart() {
	var drawCount = 0;
	var options = {
		is3D : true,
		chartArea : {
			'top' : '0',
			'width' : '80%',
			'height' : '88%'
		},
		pieStartAngle : 0,
		slices : {
			1 : {
				offset : 0.2
			}
		},
		colors : ['#1F355E','#1F7B86'],
		fontSize : '22',
		fontName : 'Crete Round',
		legend : {
			position : 'bottom',
			textStyle: {color: 'grey', fontSize: 14, bold: true, fontName: 'Arial'}
		}
	};

	function showCalculatingSpinner() {
		var newHTML = "<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-cog fa-spin fa-5x'></i> Calculating fees...</p>";
		jQuery('#piechart_3d').html(newHTML);
	}

	var internalData;

	function drawChart(accumulationData) {
		showWhatToDoAboutHighFeesText(drawCount);
		drawCount++;
		internalData = accumulationData;
		// console.log(accumulationData.finalAccumulationAmountWithFees);
		// console.log(accumulationData.totalFeesAmount);

		var data = google.visualization.arrayToDataTable([ [ 'Fees', 'Fees' ],
				[ 'Fund', accumulationData.finalAccumulationAmountWithFees ],
				[ 'Fees', accumulationData.totalFeesAmount ] ]);
		var chart = new google.visualization.PieChart(document
				.getElementById('piechart_3d'));
		chart.draw(data, options);

		// format input field numbers
		jQuery('#initial').number(true);
		jQuery('#contribution').number(true);

		// populate totals
		jQuery('#totalFees').html(jQuery.number(accumulationData.totalFeesAmount, 2));
		jQuery('#totalFeesPercent').html(accumulationData.totalFeesPercent);
		jQuery('#totalWithoutFees').html(jQuery.number(accumulationData.finalAccumulationAmountWithoutFees, 2));
		jQuery('#totalWithFees').html(jQuery.number(accumulationData.finalAccumulationAmountWithFees, 2));
		jQuery('#feeValueText').html(jQuery.number(accumulationData.fee, 2));
		jQuery('#endAgeText').html(accumulationData.endAge);

		drawCar(accumulationData.totalFeesAmount);
	}

	function redrawChart() {
		drawChart(internalData);
	}
	
	function showWhatToDoAboutHighFeesText(drawCount) {
		if(drawCount <= 0) {
			jQuery("#whatToDoABoutHighFees").hide();
		} else {
			jQuery("#whatToDoABoutHighFees").show();
		}
	} 

	function drawCar(fees) {
		var imageUrl = "http://www.spoonfedinvestor.com/wordpress/wp-content/themes/spoonfed_investor/calculator/img/"
		var fundText = "You have paid for your fund manager's new car <img id='carImage' /> with fees of "
		jQuery('#feeText').html(fundText);

		if (fees < 20000) {
			jQuery('#feeText').html("Fees paid ");
			return;
		} else if (fees >= 20000 & fees < 30000) {
			imageUrl = imageUrl+"ToyotaRAV4.png";
		} else if (fees >= 30000 & fees < 50000) {
			imageUrl = imageUrl+"mazdacx5.png";
		} else if (fees >= 50000 & fees < 100000) {
			imageUrl = imageUrl+"LexusGS.png";
		} else if (fees >= 100000 & fees < 150000) {
			imageUrl = imageUrl+"BMWX6.png";
		} else if (fees >= 150000 & fees < 200000) {
			imageUrl = imageUrl+"AudiS6.png";
		} else if (fees >= 200000) {
			imageUrl = imageUrl+"Lamborghini.png";
		}
		document.getElementById('carImage').src = imageUrl;
	}

	var PieChartObj = new Object();
	PieChartObj.drawChart = drawChart;
	PieChartObj.showCalculatingSpinner = showCalculatingSpinner;
	PieChartObj.redrawChart = redrawChart;
	return PieChartObj;
}

function initAreaChart() {
	//hide chart display
	jQuery("#resultsSection").hide();

	console.log("initAreaChart()");

	var options = {
		animation : {
			duration : '1000',
			easing : 'out'
		},
		/*legend: {position: 'right'},*/

		/*chartArea : {
			left : '15%',
			top : '10%',
			width : '100%',
			height : '80%',
			backgroundColor : {
				fill: '#e7e7e5'
			}
		},
		*/
		colors : ['#ADD8E6','orange'],
		areaOpacity : '0.9',
		fontSize : '10',
		fontName : 'Arial',
		hAxis : {
			title: 'Years', 
			titleTextStyle : {
				color: '#1F7B86', 
				italic: 'false', 
				bold: 'false'
			},
			gridlines : {
				color : 'white'
			}
		},

	};

	var chart = new google.visualization.ColumnChart(document
			.getElementById('chart_div'));

	var internalData;

	function drawAreaChart(response) {
		console.log("drawAreaChart()");
		jQuery("#resultsSection").show();

		internalData = response.dataTable;
		// console.log(JSON.stringify(dataTable, undefined, 2));
		var data = google.visualization.arrayToDataTable(response.dataTable);
		chart.draw(data, options);

		new countUp('assetFeeBalance', 0, response.assetFeeFund.finalBalance).start();
		new countUp('flatFeeBalance', 0, response.flatFeeFund.finalBalance).start();

		new countUp('assetFeeCost', 0, response.assetFeeFundFeeCost).start();
		new countUp('flatFeeCost', 0, response.flatFeeFundFeeCost).start();
	}

	function redrawAreaChart() {
		console.log("redrawAreaChart()");
		drawAreaChart(internalData);
	}

	var AreaChartObj = new Object();
	AreaChartObj.drawAreaChart = drawAreaChart;
	AreaChartObj.redrawAreaChart = redrawAreaChart;
	return AreaChartObj;
}

function scrollDown(id) {
	var offset = jQuery("#"+id).offset().top;
	console.log("scrollDown() id="+id+" offset="+offset);
	jQuery('body, html').animate({
		scrollTop: offset},
		'slow');
}

function sendGetRequest() {

	if (!adviceCostForm[0].checkValidity()) {
		return;
	}

	scrollDown('calculator');

	var initialFee = +adviceCostForm.statementOfAdviceFee.value +  +adviceCostForm.implementationFee.value;
	var initialInvestment = +adviceCostForm.initialInvestment.value -  +initialFee;
	var assetPercentageFee = +adviceCostForm.ongoingAdviceFee.value + 
+adviceCostForm.platformAdministrationFees.value +  +adviceCostForm.investmentManagementFees.value;

	var advisorCostRequest = {
		initialInvestment:initialInvestment,
		yearlyInvestment:adviceCostForm.yearlyInvestment.value, 
		years:20, 
		funds:[{name:"Zero Cost Fund", percentageFeePa:0.00, fixedFeePa:0, yieldPa:8.0}, 
 			{name:"Flat Fee", percentageFeePa:0.30, fixedFeePa:500,yieldPa:8.0},
 			{name:"Asset Based Fee", percentageFeePa:assetPercentageFee, fixedFeePa:0.00,yieldPa:8.0}]
	}


	jQuery.ajax({
				url : "http://fundfeecalculator.herokuapp.com/services/advisercostcalculator/compare",
				//be good to detect localhost here and switch to local web service!
				type : "POST",
				//url : "/services/advisercostcalculator/compare",
				data : JSON.stringify(advisorCostRequest),
				dataType:'json',
				contentType : "application/json",
				context : document.body,
				success : function(data, textStatus, jqXHR) {
					data = data;
					//console.log(JSON.stringify(data, undefined, 2));
					//PieChart.drawChart(data);
					AreaChart.drawAreaChart(data);
				},
				error : function(qXHR, textStatus, errorThrown) {
					if (errorThrown instanceof SyntaxError) {
						errorThrown = errorThrown.message;
					}
					console.log(errorThrown);
				}
			});
}

jQuery(window).smartresize(function() {
	console.log("draw charts");
	//PieChart.redrawChart();
	AreaChart.redrawAreaChart();
});
