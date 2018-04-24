<?php

/*
Beskrivning:
funktion för att hämta veckorna som ingår i en specefik apl-period

Indata:
får in användarID från app

Utdata
skickar startvecka och slutvecka till app som json.

*/

include 'APL_databasConnect.php';

$Week = $_POST["week"];
$AnvandarID = $_POST["AnvandarID"];
$setNarvaro = "SELECT Date_Format(Datum,'%d %M %Y')as Datumm, NarvaroRaknare, Narvarande from narvaro,dag Where narvaro.DagarID=dag.DagarID and narvaro.AnvandarID='$AnvandarID' and Week(Datum,3)='$Week'";

$result = mysqli_query($conn, $setNarvaro); 
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
            
        $emparray[] = $row;
    }
    echo json_encode($emparray);
} 

?>




