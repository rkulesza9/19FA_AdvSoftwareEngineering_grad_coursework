<?php include 'dbconfig.php'; ?>

<?php
	$last_5_yrs = 'https://cloud.iexapis.com/v1/stock/xsymbolx/chart/5y/?token=xtokenx';

	$tokens = ['pk_91f2e09132844385a30c289ccd870ccd',
			   'pk_6b6dab54a2114991b557f1f6f8440f51',
			   'pk_cab3a047dc5145d5a21261e2a37a521d'];
	$token_index = 0;

	$symbols = ['rfem','googl','aapl'];
	//$symbols = ['rfem'];
?>

<?php
	function fetch_json($url){
		$curl = curl_init();
		curl_setopt_array($curl, [
			CURLOPT_RETURNTRANSFER => 1,
			CURLOPT_URL => $url
		]);

		$resp = curl_exec($curl);
		curl_close($curl);

		if($resp == "You have exceeded your allotted message quota. Please enable pay-as-you-go to regain access"){
			return "expired_token";
		}

		return json_decode($resp);
	}

	function build_url($base,$token,$symbol){
		$replace_symbol = str_replace("xsymbolx",$symbol,$base);
		$replace_token = str_replace("xtokenx",$token,$replace_symbol);

		return $replace_token;
	}
?>

<?php
	//write sql procedure to update the db if new data exists (the procedure should check for same-company, same-date) [only need latest date, i think]
	$response = 'expired_token';
	for($a=0;$a<count($symbols);$a++){
		//while($response == "expired_token"){
		//	$token_index = ($token_index + 1) % count($tokens);
		//	$response = fetch_json(build_url($last_5_yrs,$tokens[$token_index],$symbols[$a]));
		//}
		$response = fetch_json(build_url($last_5_yrs,$tokens[$token_index],$symbols[$a]));
		if($response <> 'expired_token') {
		for($x=0;$x<count($response);$x++){

			$close = $response[$x]->close;
			$company = $symbols[$a];
			$date = $response[$x]->date;
			$high = $response[$x]->high;
			$low = $response[$x]->low;
			$open = $response[$x]->open;
			$volume = $response[$x]->volume;

			$query = "select date from TRADING_DAYS where date=? and company=?";
			$stmt = $conn->prepare($query);
			$stmt->bind_param("ss",$date,$company);
			$stmt->bind_result($result);
			$stmt->execute();
			$stmt->fetch();
			$stmt->close();



			if(isset($result)) continue;


			echo "new record inserted: \n";
			echo "close: $close\n";
			echo "company: $company\n";
			echo "date: $date\n";
			echo "high: $high\n";
			echo "low: $low\n";
			echo "open: $open\n";
			echo "volume: $volume\n\n";

			$query = "insert into v2_TRADING_DAYS (close,cid,date,high,low,open,volume,last_update) values (?,f_companyID(?),?,?,?,?,?,now())";
			$stmt = $conn->prepare($query);
			$stmt->bind_param("dssdddd",$close,$company,$date,$high,$low,$open,$volume);

			$stmt->execute();
			$stmt->close();
		}
	}else {
		echo $response;
		break;
	}
	}
	//after confirming this works, write bash script to run this file
	//record update_timestamp so I can check that it is working
?>
