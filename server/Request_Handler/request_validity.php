<?php
  function validateDate($date, $format = 'Y-m-d')
  {
      $d = DateTime::createFromFormat($format, $date);
      return $d && $d->format($format) === $date;
  }


  function test_validity(){
    $symbol = strtolower($_GET['symbol']);
    $mode = strtolower($_GET['mode']);
    $date_start = strtolower($_GET['date_start']);
    $date_end = strtolower($_GET['date_end']);

    if(isset($symbol)){
      if($symbol<>"rfem" & $symbol<>"googl" & $symbol<>"aapl"){
        return "symbol must be rfem, googl or aapl";
      }

      if(isset($mode)){
        if($mode<>"aggregate" & $mode<>"30dayreport" & $mode<>"companyinfo" & $mode<>"popsearch"){
          return "mode must be aggregate, 30dayreport, companyinfo or popsearch";
        }
        if($mode=='aggregate'){
          if(isset($date_start) & isset($date_end)){
            if(!validateDate($date_start) || !validateDate($date_end)){
              return "date_start and date_end must be in Y-m-d format";
            }
            if($date_start < $date_end){
              return "success";
            }else{
              return "date_start must be less than date_end";
            }
          } else {
            return "this request requires date_start and date_end parameters";
          }
        }else{
          return "success";
        }
      } else {
        if(isset($date_start) & isset($date_end)){
          if($date_start <= $date_end){
            return "success";
          }else{
            return "date_start must be less than or equal to date_end";
          }
        } else {
          return "this request requires date_start and date_end parameters";
        }
      }

    } else{
      return 'symbol paramater cannot be empty';
    }
  }


?>
