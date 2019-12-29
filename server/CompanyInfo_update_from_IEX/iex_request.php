<!-- arguments for IEX request -->
<?php
  $tokens = ['pk_91f2e09132844385a30c289ccd870ccd',
         'pk_6b6dab54a2114991b557f1f6f8440f51',
         'pk_cab3a047dc5145d5a21261e2a37a521d'];
  $symbols = ['rfem','googl','aapl'];
?>

<!-- base url for IEX request -->
<?php
  function companyInfo_url($symbol, $token){
    $iexreq_base = 'https://cloud.iexapis.com/v1/stock/xsymbolx/company?token=xtokenx';
    $symbol_fixed = str_replace("xsymbolx",$symbol,$iexreq_base);
    $token_fixed = str_replace("xtokenx",$token, $symbol_fixed);
    return $token_fixed;
  }
?>

<!-- curl request -->
<?php
  function companyInfo_request($symbol,$token){
    $url = companyInfo_url($symbol,$token);
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
?>

<!-- sql insert statement -->
<?php
  function companyInfo_insertdb($symbol,$companyName,$exchange,$industry,$website,$description,$ceo,$securityName,$issueType,$sector,$primarySicCode,$employees,$address,$address2,$state,$city,$zip,$country,$phone,$tags){
    $sql = "call p_insertNewCompanyInfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    $sql_params = "ssssssssssiisssssss";
    $sql2 = "call p_addCompanyTag(?,?)";
    $sql2_params = "ss";

    include 'dbconfig.php';
    $stmt = $conn->prepare($sql);
    $stmt->bind_param($sql_params,$symbol,$companyName,$exchange,$industry,$website,$description,$ceo,$securityName,$issueType,$sector,$primarySicCode,$employees,$address,$address2,$state,$city,$zip,$country,$phone);
    $stmt->execute();
    $stmt->close();

    foreach($tags as $tag){
      $stmt = $conn->prepare($sql2);
      $stmt->bind_param($sql2_params,$symbol,$tag);
      $stmt->execute();
      $stmt->close();
    }
  }
?>

<!-- make request based off of curl response ->
<?php
  foreach($symbols as $symbol){
    $json = companyInfo_request($symbol,$tokens[0]);
    $companyName = $json->companyName;
    $exchange = $json->exchange;
    $industry = $json->industry;
    $website = $json->website;
    $description = $json->description;
    $ceo = $json->ceo;
    $securityName = $json->securityName;
    $issueType = $json->issueType;
    $sector = $json->sector;
    $primarySicCode = $json->primarySicCode;
    $employees = $json->employees;
    $address = $json->address;
    $address2 = $json->address2;
    $state = $json->state;
    $city = $json->city;
    $zip = $json->zip;
    $country = $json->country;
    $phone = $json->phone;
    $tags = $json->tags;

    companyInfo_insertdb($symbol,$companyName,$exchange,$industry,$website,$description,$ceo,$securityName,$issueType,$sector,$primarySicCode,$employees,$address,$address2,$state,$city,$zip,$country,$phone,$tags);
  }
?>
