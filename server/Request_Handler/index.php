<?php
	include 'dbconfig.php';
	include 'request_validity.php';
	header("content-type: application/json; charset=utf-8");

	$is_valid = test_validity();
	if($is_valid=='success'){

	function search_log($company,$type){
		$conn = connect();
		$query = "INSERT INTO v2_SEARCH (date,type_id,company_id) ".
						 "VALUES (now(),(select id from v2_SEARCH_TYPES WHERE short_name='$type'),f_companyID(?))";
		$stmt = $conn->prepare($query);
		$stmt->bind_param('s',$company);
		$stmt->execute();
		$stmt->close();
	}

	//BASE_URL+"/?symbol=?&date_start=X&date_end=Y where X is 3 months ago and Y is the current day.
	$symbol = strtolower($_GET['symbol']);
	$mode = strtolower($_GET['mode']);
	$date_start = strtolower($_GET['date_start']);
	$date_end = strtolower($_GET['date_end']);

	if($mode == '' || $mode=='30dayreport'){ //DEFAULT REQUEST = 30 DAY REPORT
		//send request to DB
		if($mode=='30dayreport'){
			$date_end = date("Y-m-d");
			$date_start = date('Y-m-d', strtotime('-30 days'));
		}
		$query = "call pv2_TRADING_DAYS_W_CHANGE(?,?,?)";
		$stmt = $conn->prepare($query);
		$stmt->bind_param("sss",$symbol,$date_start,$date_end);
		$stmt->execute();
		$result = $stmt->get_result();
		$num_rows = $result->num_rows;

		//write DB data as JSON
		echo "[";
		if($num_rows == 0) echo '{"error_msg" : "No Records Exist"}';
		while($row = $result->fetch_assoc()){
			// foreach($row as $key => $value){
			// 	if($value == Null || $value == ''){
			// 		$row[$key] = "Null";
			// 	}
			// }
			?>

				{
					"date" : "<?php echo $row['date']; ?>",
					"open" : <?php echo $row['open']; ?>,
					"close": <?php echo $row['close']; ?>,
					"high": <?php echo $row['high']; ?>,
					"low" : <?php echo $row['low']; ?>,
					"volume" : <?php echo $row['volume']; ?>,
					"change" : <?php echo $row['change']; ?>,
					"changePercent" : <?php echo $row['changePercent']; ?>,
					"changeOverTime" : <?php echo $row['changeOverTime']; ?>,
					"volumeChange" : <?php echo $row['volumeChange']; ?>,
					"volumeChangePercent" : <?php echo $row['volumeChangePercent']; ?>,
					"volumeChangeOverTime" : <?php echo $row['volumeChangeOverTime']; ?>
				}

			<?php
			$num_rows = $num_rows - 1;
			 if($num_rows<>0) echo ",";
		}

		echo "]";
		search_log($symbol,"30DAY");

		$stmt->close();
		$conn->close();
	} elseif($mode == 'aggregate'){
		$query = 'call pv2_aggregate_REPORT(?,?,?)';
		$stmt = $conn->prepare($query);
		$stmt->bind_param('sss',$symbol,$date_start,$date_end);
		$stmt->execute();
		$result = $stmt->get_result();
		$num_rows = $result->num_rows;

		echo '[';
		if($num_rows == 0) echo '{"error_msg" : "No Records Exist"}';
		while($row = $result->fetch_assoc()){
						?>

				{
					"min" : <?php echo $row['min']; ?>,
					"max" : <?php echo $row['max']; ?>,
					"avg" : <?php echo $row['avg']; ?>,
					"median" : <?php echo $row['median']; ?>
				}

		<?php
			$num_rows = $num_rows - 1;
			if($num_rows<>0) echo ' , ';
		}
		echo ']';

		search_log($symbol,"AGRGT");
		$stmt->close();
		$conn->close();
	} elseif($mode == "companyinfo"){
		include 'REST_CompanyInfo.php';
		search_log($symbol,"CINFO");
	} elseif($mode == "popsearch"){
		$query = 'call pv2_SEARCH_REPORT(?)';
		$stmt = $conn->prepare($query);
		$stmt->bind_param('s',$symbol);
		$stmt->execute();
		$result = $stmt->get_result();
		$num_rows = $result->num_rows;

		echo '[';
		if($num_rows == 0) echo '{"error_msg" : "No Records Exist"}';
		while($row = $result->fetch_assoc()){
						?>

				{
					"LastSearchedDate" : "<?php echo $row['last_searched_date']; ?>",
					"NumSearches" : <?php echo $row['num_searches']; ?>,
					"NumSearchesPercent" : <?php echo $row['num_searches_percent']; ?>,
					"AvgSearchesPerWeek" : <?php echo $row['avg_searches_per_week']; ?>,
					"AvgSearchesPerMonth" : <?php echo $row['avg_searches_per_month']; ?>
				}

		<?php
			$num_rows = $num_rows - 1;
			if($num_rows<>0) echo ' , ';
		}
		echo ']';

		search_log($symbol,"SRCHP");
		$stmt->close();
		$conn->close();
	}
} else {
	echo '[{"error" : "'.$is_valid.'"}]';
}

?>
