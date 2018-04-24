<?php

/*
Beskrivning:
funktion för att hämta information om användare

Indata:
får in användareID

Utdata
skickar all information om användaren till applikationen som json

*/

include 'APL_databasConnect.php';

$AnvandarID = $_POST["AnvandarID"]; 

$sql_query = "SELECT * FROM Anvandare WHERE Anvandare.AnvandarID='$AnvandarID'";
 
$result = mysqli_query($conn,$sql_query);  
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);

}  
?>