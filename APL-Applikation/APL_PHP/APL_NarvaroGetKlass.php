<?php

/*
Beskrivning:
funktion för att hämta klass och namn

Indata:
får inte in någon data

Utdata
skickar klassID och Namn till applikationen som json

*/

include 'APL_databasConnect.php';

$Sokning= "SELECT KlassID, Namn FROM klass";

$resultstart = mysqli_query($conn, $Sokning); 
if(mysqli_num_rows($resultstart) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($resultstart)){
        $emparray[] = $row;
        }
    echo json_encode($emparray);
} 

?>