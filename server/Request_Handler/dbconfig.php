<?php

  function connect(){
    $servername = "servername";
    $username = "username";
    $password = "password";
    $db = 'kean_2019Fall';


    $conn = new mysqli($servername,$username,$password,$db);
    return $conn;
  }
	$conn = connect();
?>
