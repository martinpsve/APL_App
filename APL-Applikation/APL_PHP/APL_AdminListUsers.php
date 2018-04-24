<?php

/*
Beskrivning:
funktion för att hämta all information om alla användare

Indata:
ingen indata

Utdata
skickar all information om alla användare som json.

*/

include 'APL_databasConnect.php';

$sql_query = "SELECT * FROM Anvandare";

$result = mysqli_query($conn,$sql_query);  
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);

}  
?>