google.load("visualization", "1", {
	packages : [ "corechart" ]
});

var availableFunds = [{id:"0", name:"Average Australian Fund", percentageFee:"1.2" ,fixedFee:"0", returnPercentage:"7.5"},
		{id:"customFund", name:"Custom Fund", percentageFee:"" ,fixedFee:"", returnPercentage:"7.5"},
		{id:"1", name:"Asgard eWRAP Super", percentageFee:"0.605" ,fixedFee:"1000", returnPercentage:"7.5"},
		{id:"1", name:"AMP WealthView", percentageFee:"0.914" ,fixedFee:"1128", returnPercentage:"7.5"},
		{id:"2", name:"AMP MySuper Balanced", percentageFee:"0.65" ,fixedFee:"101", returnPercentage:"7.5"},
		{id:"2", name:"AMP Signature Super", percentageFee:"1.44" ,fixedFee:"240", returnPercentage:"7.5"},
		{id:"1", name:"Aust Ethical MySuper - Balanced", percentageFee:"2.294" ,fixedFee:"97", returnPercentage:"7.5"},
		{id:"1", name:"AustralianSuper", percentageFee:"0.59" ,fixedFee:"78", returnPercentage:"7.5"},
		{id:"5", name:"BT Business Super - MySuper Lifestage Fund", percentageFee:"1.52" ,fixedFee:"60", returnPercentage:"7.5"},
		{id:"5", name:"BT SuperWrap - Personal", percentageFee:"1.772" ,fixedFee:"0", returnPercentage:"7.5"},
		{id:"5", name:"BT Super for Life - MySuper", percentageFee:"1.05" ,fixedFee:"60", returnPercentage:"7.5"},
		{id:"3", name:"CareSuper MySuper", percentageFee:"1.12" ,fixedFee:"78", returnPercentage:"7.5"},
		{id:"4", name:"Colonial First State FirstChoice - MySuper", percentageFee:"1.08" ,fixedFee:"60", returnPercentage:"7.5"},
		{id:"4", name:"Colonial First State FirstChoice - Personal", percentageFee:"1.95" ,fixedFee:"0", returnPercentage:"7.5"},
		{id:"5", name:"First State Super - Employer Sponsored Division", percentageFee:"0.57" ,fixedFee:"52", returnPercentage:"7.5"},
		{id:"5", name:"IOOF PS - Personal", percentageFee:"1.9" ,fixedFee:"84", returnPercentage:"7.5"},
		{id:"5", name:"HESTA - Industry", percentageFee:"0.82" ,fixedFee:"65", returnPercentage:"7.5"},
		{id:"5", name:"OnePath OA Frontier", percentageFee:"0.95" ,fixedFee:"115", returnPercentage:"7.5"},
		{id:"5", name:"Mercer Super Trust - Personal", percentageFee:"2.105" ,fixedFee:"78", returnPercentage:"7.5"},
		{id:"5", name:"MLC Masterkey - Super", percentageFee:"2.06" ,fixedFee:"78", returnPercentage:"7.5"},
		{id:"5", name:"MLC Wrap Super Series 2", percentageFee:"0.75" ,fixedFee:"750", returnPercentage:"7.5"},
		{id:"5", name:"Prime Super - MySuper", percentageFee:"1.28" ,fixedFee:"72.8", returnPercentage:"7.5"},
		{id:"5", name:"REST Super", percentageFee:"0.8" ,fixedFee:"57", returnPercentage:"7.5"},
		{id:"5", name:"Sunsuper for Life", percentageFee:"0.62" ,fixedFee:"65", returnPercentage:"7.5"},
		{id:"5", name:"TelstraSuper Corp Plus", percentageFee:"0.86" ,fixedFee:"78", returnPercentage:"7.5"},
		{id:"5", name:"UniSuper Accum (1)", percentageFee:"0.61" ,fixedFee:"115", returnPercentage:"7.5"},
];

google.setOnLoadCallback(init);

function init() {
	setupFundChoice();
	AreaChart = new initAreaChart();
	sendRequest();

	var button = document.getElementById('calculateButton');
	// onlick works in < IE9 (addEventHandler does not - allows multiple
	// handlers per event)
	button.onclick = function() {
		sendRequest();
	}
}

function setupFundChoice() {

	for(var i = 0;i<availableFunds.length;i++) {
		var opt = document.createElement('option');
		opt.value = availableFunds[i].id;
		opt.text = availableFunds[i].name;
		opt.title = availableFunds[i].name;
		if(i==0) {
			opt.selected = true;
		}
		fundDetailsForm.superfund.add(opt);
	}

	var optgroup = document.createElement('optgroup');
	optgroup.label = "Super Funds:";
	fundDetailsForm.superfund.add(optgroup, fundDetailsForm.superfund[2]);

	var selects = fundDetailsForm.superfund;
	setFeesFields(true, availableFunds[selects.selectedIndex].percentageFee, availableFunds[selects.selectedIndex].fixedFee);

 	jQuery("#superfund").change(function(){
		if(selects.options[selects.selectedIndex].value == "customFund") {
			// editable fees
			setFeesFields(false, "", "");
		} else {
			// read only fees
			setFeesFields(true, availableFunds[selects.selectedIndex].percentageFee, availableFunds[selects.selectedIndex].fixedFee);
		}
	});
}

function setFeesFields(disableFields, percentageFee, fixedFee) {
	jQuery("#percentageFeesPerYear").prop("disabled",disableFields);
	jQuery("#fixedFeesPerYear").prop("disabled",disableFields);
	fundDetailsForm.percentageFeesPerYear.value = percentageFee;
	fundDetailsForm.fixedFeesPerYear.value = fixedFee;
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
		//jQuery('#initial').number(true);
		//jQuery('#contribution').number(true);

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
	var options = {
		animation : {
			duration : '1000',
			easing : 'out'
		},
		legend : {
			position : 'top',
			alignment : 'end',
			textStyle: {color: 'grey', 
						fontSize: '14', 
						bold: 'true', 
						fontName: 'Arial'}
		},
		chartArea : {
			left : '15%',
			top : '10%',
			width : '100%',
			height : '80%',
			backgroundColor : {
				fill: '#e7e7e5'
			}
		},
		colors : ['#1F355E','#1F7B86'],
		areaOpacity : '0.9',
		fontSize : '14',
		fontName : 'Arial',
		hAxis : {
			title: 'Your Age', 
			titleTextStyle : {
				color: '#1F7B86', 
				italic: 'false', 
				bold: 'true'
			},
			gridlines : {
				color : 'white'
			}
		},
		vAxis : {
			title: 'Accumulation ($)', 
			titleTextStyle : {
				color: '#1F7B86', 
				italic: 'false', 
				bold: 'true'
			},
			gridlines : {
				color : 'white'
			}
		}
	};

	var chart = new google.visualization.AreaChart(document
			.getElementById('chart_div'));

	var internalData;

	function drawAreaChart(funds) {
		//internalData = dataTable;

		console.log(JSON.stringify(funds, undefined, 2));

		var fund1 = funds[0];
		var fund2 = funds[1];

		var dataTable = [['Age', fund1.name, fund2.name]]

		console.log(JSON.stringify(fund1, undefined, 2));

		var accumulations = fund1.fundAccumulations;
		var len = accumulations.length;

		for(var i=0;i<accumulations.length;i++) {
			var col = [accumulations[i].year, accumulations[i].accumulationAmount, fund2.fundAccumulations[i].accumulationAmount];
			dataTable[i+1] = col;
		}


		console.log(JSON.stringify(dataTable, undefined, 2));

		var data = google.visualization.arrayToDataTable(dataTable);
		chart.draw(data, options);

		var yourTotal = fund2.fundAccumulations[len-1].accumulationAmount;
		var lowCostTotal = fund1.fundAccumulations[len-1].accumulationAmount;
		var difference = lowCostTotal - yourTotal;		

		new countUp('totalWithFees', 0, yourTotal).start();
		new countUp('totalWithoutFees', 0, lowCostTotal).start();
		new countUp('difference', 0, difference).start();
	}

	function redrawAreaChart() {
		drawAreaChart(internalData);
	}

	var AreaChartObj = new Object();
	AreaChartObj.drawAreaChart = drawAreaChart;
	AreaChartObj.redrawAreaChart = redrawAreaChart;
	return AreaChartObj;
}

function sendRequest() {

	var selected = 0;
	if (fundDetailsForm.superfund.selectedIndex != -1) {
		selected = fundDetailsForm.superfund.selectedIndex;
	}	

	var fundReturnCalculatorRequest = {
		initialInvestmentAmount:fundDetailsForm.initialInvestmentAmount.value, 
		salary:fundDetailsForm.salary.value, 
		contribution:fundDetailsForm.contribution.value, 
		startAge:fundDetailsForm.startAge.value, 
		endAge:fundDetailsForm.endAge.value, 
		funds:[{name:"Low Cost Fund", percentageFeePa:0.04, fixedFeePa:78, yieldPa:7.5}, 
 			{name:"Your Fund", 
			percentageFeePa:fundDetailsForm.percentageFeesPerYear.value, 
			fixedFeePa:fundDetailsForm.fixedFeesPerYear.value, 
			yieldPa:availableFunds[selected].returnPercentage}]}

	jQuery.ajax({
				url : "http://fundfeecalculator.herokuapp.com/services/fundreturncalculator/compareSuperannuationFunds",
				//be good to detect locahost here and switch to local web service!
				//url : "/services/fundreturncalculator/compareSuperannuationFunds",
				type : "POST",
				data : JSON.stringify(fundReturnCalculatorRequest),
				dataType: "json",
				contentType : "application/json",
				context	 : document.body,
				success : function(data, textStatus, jqXHR) {
					data = data;
					// console.log(JSON.stringify(data, undefined, 2));
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

	//google analytics
	if(typeof __gaTracker == 'function') {
		__gaTracker('send', 'event', 'calculate button', 'click');
	}
}

jQuery(window).smartresize(function() {
	console.log("draw charts");
	//PieChart.redrawChart();
	AreaChart.redrawAreaChart();
});
