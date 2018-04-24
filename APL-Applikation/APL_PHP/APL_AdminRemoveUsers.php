<?php
include 'APL_databasConnect.php';

$user_name = $_POST["AnvandarID"]; 

$ChangeUser = "DELETE FROM anvandare WHERE anvandare.AnvandarID=$user_name"; 

$result = mysqli_query($conn, $ChangeUser); 

?>