<?php
/*
Template Name: supercalc
*/

// Register stylesheets
	
	wp_register_style( 'calculator-bootstrap', 'https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css');
	wp_enqueue_style( 'calculator-bootstrap' ); 
	
	wp_register_style( 'calculator-font-awesome', 'http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css' , array('calculator-bootstrap'));
	wp_enqueue_style( 'calculator-font-awesome' );
	
	wp_register_style( 'calculator', get_template_directory_uri().'/css/supercomparecalc.css' , array('calculator-bootstrap', 'calculator-font-awesome'));
	wp_enqueue_style( 'calculator' );


// Register javascript
	
	wp_register_script( 'calculator-bootstrap', 'https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js', array('jquery'));	
	wp_enqueue_script( 'calculator-bootstrap' );
	
	wp_register_script( 'calculator-google-jsapi', 'https://www.google.com/jsapi', array('jquery','calculator-bootstrap'));	
	wp_enqueue_script( 'calculator-google-jsapi' );
		
	wp_register_script( 'jquery-number', 'http://fundfeecalculator.herokuapp.com/js/jquery.number.min.js', array('jquery','calculator-bootstrap','calculator-google-jsapi'));	
	wp_enqueue_script( 'jquery-number' );
	
	wp_register_script( 'calculator-debounce', 'http://fundfeecalculator.herokuapp.com/js/debounce.js', array('jquery','calculator-bootstrap','calculator-google-jsapi','jquery-number'));	
	wp_enqueue_script( 'calculator-debounce' );
	
	wp_register_script( 'count-up', 'http://fundfeecalculator.herokuapp.com/js/countUp.min.js', array('jquery','calculator-bootstrap','calculator-google-jsapi','jquery-number','calculator-debounce'));	
	wp_enqueue_script( 'count-up' );	

	wp_register_script( 
		'superCalculator', 
		'http://fundfeecalculator.herokuapp.com/js/superfundcalc.js', 
		array('jquery','calculator-bootstrap','calculator-google-jsapi','jquery-number', 'calculator-debounce', 'count-up')
	);	
	wp_enqueue_script( 'superCalculator' );
 
 
get_header(); ?>

<!--<div id="main-content">-->

   <div id="overallWidth" style="">
    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h2>How does yours measure up?</h2>
        <p>Compare your superfund with the best low cost fund in the market today.</p>
      </div>
    </div>
    <div class="container">
      <!-- Begin form -->
      <div class="row">

        <div class="col-sm-6 col-md-6"> <!-- start input -->
	
		  <form id="fundDetailsForm" class="form-horizontal" role="form" onsubmit="return false;">
		  	<div class="form-group">
				<label for="superfund" class="col-md-4 control-label">Your Superfund</label>
				<div class="col-md-8">
					<select id="superfund" name="superfund" class="form-control">
					</select>
		      		</div>
			</div>
		
			<div class="form-group">
		      		<label for="howMuch" class="col-xs-6 control-label">Total Fees per Year %</label>
		      		<div class="col-xs-6">
		      			<input type="text" class="form-control" name="percentageFeesPerYear" id="percentageFeesPerYear" value="" required>
		      		</div>
		    	</div>

		    	<div class="form-group">
		      		<label for="howMuch" class="col-xs-6 control-label">Fixed Fee Per Year $</label>
		      		<div class="col-xs-6">
		      			<input type="text" class="form-control" name="fixedFeesPerYear" id="fixedFeesPerYear" value="" required>
		      		</div>
		    	</div>

		    	<div class="form-group">
		      		<label for="howMuch" class="col-xs-6 control-label">Initial Investment $</label>
		      		<div class="col-xs-6">
		      			<input type="text" class="form-control" name="initialInvestmentAmount" id="initialInvestmentAmount" value="20000" required>
		      		</div>
		    	</div>

		    	<div class="form-group">
		      		<label for="salary" class="col-xs-6 control-label">Yearly Salary $</label>
		      		<div class="col-xs-6">
		      			<input type="text" class="form-control" name="salary" id="salary" value="60000" required>
		      		</div>
		    	</div>

		    	<div class="form-group">
			      <label for="extra" class="col-xs-6 control-label">Additional Yearly Contribution $</label>
			      <div class="col-xs-6">
			      	<input type="text" class="form-control" name="contribution" id="contribution" value="0" required>
			      </div>
   			</div>

			<div class="form-group">
			      <label for="years" class="col-xs-6 control-label">Your Current Age</label>
			      <div class="col-xs-6">
			      	<input type="number" class="form-control" name="startAge" id="startAge" value="30" required min="0" max="100" step="1">
			      </div>
		    	</div>

		    	<div class="form-group">
		      		<label for="years" class="col-xs-6 control-label">Your Retirement Age</label>
		      		<div class="col-xs-6">
			      		<input type="number" class="form-control" name="endAge" id="endAge" value="67" required min="0" max="100" step="1">
				</div>
			</div>

		    	<div class="form-group">
		    		<div class="col-md-offset-6 col-md-6">
				        <button type="submit" class="btn btn-primary btn-lg btn-block" id="calculateButton">Compare</button>
				</div>
			</div>
		  </form>

		<div class="row">		

			<div class="col-md-6 col-lg-4">
				<p class="calculatorBox">
				Your fund size
				<span class="calculatorAmount">$<span id="totalWithFees"></span></span>
				</p>
			</div>

			<div class="col-md-6 col-lg-4">
				<p class="calculatorBox">
				Low cost fund size<span class="calculatorAmount">$<span id="totalWithoutFees"></span></span>
				</p>
			</div>

			<div class="col-md-6 col-lg-4">
				<p class="calculatorBox">
				Have this much more<span class="calculatorAmount">$<span id="difference"></span></span>
				</p>
			</div>


		</div>
	
	</div> <!-- end input -->


        <div class="col-sm-6 col-md-6"> <!-- start chart -->
		<div id="chart_div" style="height: 500px;"></div>
       </div><!-- end chart -->

      </div><!-- end row -->

      <div class="row">
	&nbsp;
      </div>


      <div class="row">
	<div class="col-sm-6 col-md-6">
		<h3>How much more could you have in retirement?</h3>	
		<p>
		Now you can clearly see the difference between your fund and the best low cost fund available today, we think you'll agree that Aussie's are being ripped off to the tune of hundreds of thousands of dollars.
		</p>
		<p>
		It is quite simply disgraceful that the superannuation funds take so much from your nest egg for doing so little. 
		</p>
	</div>
	<div class="col-sm-6 col-md-6">	
		<p>
		<h3>What can you do about it?</h3>
		The good news is that you don't have to take it. By switching to a low cost fund like the one we have used in our comparison here, you can easily give yourself a much bigger sum to keep you and your family into old age. Switching superfunds takes a matter of hours!
		</p>
		<h3>Which is the lowest cost fund in the market today?</h3>
		<p>
		Checkout our <a href="/superripoff">Super Ripoff illustration</a> for free and save $100,000s!
		</p>

		<h3>Register to be kept in the know</h3>
		<p>
		Create your free <a href="/membership-account/membership-checkout/?level=2">account with MensMoneyHealth</a> and we will keep you updated with the best superfunds in the market.
		</p>
      </div>
    </div>

      <div class="row">
	&nbsp;
      </div>

    <!-- /container -->
    </div>

<!--</div>--> <!-- #main-content -->

<?php get_footer(); ?>
