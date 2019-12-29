<?php
  $symbol = $_GET['symbol'];
?>


<?php
  $sql = "select * from v2_COMPANY_INFO where cid = f_companyID(?)";
  $sql2 = "select a.tag from v2_TAG a, v2_COMPANY b, v2_TAGGED c where a.tid=c.tid and b.company=? and b.cid=c.cid";
  $params = "s";

  $stmt = $conn->prepare($sql);
  $stmt->bind_param($params,$symbol);
  $stmt->execute();
  $result = $stmt->get_result();

  while($row = $result->fetch_assoc()){
    foreach($row as $key => $value){
      if($value == null || $value=''){
        $row[$key] = "Null";
      }
    }
    $companyName = $row['companyName'];
    $exchange = $row['exchange'];
    $industry = $row['industry'];
    $website = $row['website'];
    $description = $row['description'];
    $ceo = $row['ceo'];
    $securityName = $row['securityName'];
    $issueType = $row['issueType'];
    $sector = $row['sector'];
    $primarySicCode = $row['primarySicCode'];
    $employees = $row['employees'];
    $address = $row['address'];
    $address2 = $row['address2'];
    $state = $row['state'];
    $city = $row['city'];
    $zip = $row['zip'];
    $country = $row['country'];
    $phone = $row['phone'];

    $stmt2 = $conn->prepare($sql2);
    $stmt2->bind_param($params,$symbol);
    $stmt2->execute();
    $tags = $stmt2->get_result();
    $num_rows = $result->num_rows;
    $tags_str =  $tags->fetch_assoc()['tag'];
    while($tag = $tags->fetch_assoc()){
      $tags_str = $tags_str.", ".$tag['tag'];
    }
    if($num_rows > 0){?>
    [
      {
        "companyName" : "<?php echo $companyName; ?>",
        "exchange" : "<?php echo $exchange; ?>",
        "industry" : "<?php echo $industry; ?>",
        "website" : "<?php echo $website; ?>",
        "description" : "<?php echo $description; ?>",
        "ceo" : "<?php echo $ceo; ?>",
        "securityName" : "<?php echo $securityName; ?>",
        "issueType" : "<?php echo $issueType; ?>",
        "sector" : "<?php echo $sector; ?>",
        "primarySicCode" : "<?php echo $primarySicCode; ?>",
        "employees" : "<?php echo $employees; ?>",
        "address" : "<?php echo $address; ?>",
        "address2" : "<?php echo $address2; ?>",
        "state" : "<?php echo $state; ?>",
        "city" : "<?php echo $city; ?>",
        "zip" : "<?php echo $zip; ?>",
        "country" : "<?php echo $country; ?>",
        "phone" : "<?php echo $phone; ?>",
        "tags" : "<?php echo $tags_str; ?>"
      }
    ]

<?php } else { ?>
    [
      {"error_msg" : "No Records Exist"}
    ]
<?php    }

  }
  $stmt->close();
  $conn->close();
?>
