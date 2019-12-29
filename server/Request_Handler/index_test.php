<?php

	//BASE_URL+"/?symbol=?&date_start=X&date_end=Y where X is 3 months ago and Y is the current day.
	$symbol = $_GET['symbol'];
	$date_start = $_GET['date_start'];
	$date_end = $_GET['date_end'];
	
	//send request to DB
	
	//write DB data as JSON

?>

<h1>TESTING</h1>
<h2>http://fproject-se.ddns.net/?symbol=<?php echo $symbol; ?>&date_start=<?php echo $date_start; ?>&date_end=<?php echo $date_end; ?></h2>
<p>PARAMETER LIST:
	
	<UL>
		<LI>symbol = <?php echo $symbol; ?></LI>
		<li>date_start = <?php echo $date_start; ?></li>
		<li>date_end = <?php echo $date_end; ?></li>
	</UL>

</p>