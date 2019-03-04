<?php
/*
Template Name: adviser-cost-calc
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
		'http://fundfeecalculator.herokuapp.com/js/advisercostcalculator.js', 
		array('jquery','calculator-bootstrap','calculator-google-jsapi','jquery-number', 'calculator-debounce', 'count-up')
	);	
	wp_enqueue_script( 'superCalculator' );
 
 
get_header(); ?>


	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-md-6">
				<div id="calculator"></div>
			</div>
		</div>
	</div>



<?php get_footer(); ?>
