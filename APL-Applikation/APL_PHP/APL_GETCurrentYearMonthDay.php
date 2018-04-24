<?php

/*
Beskrivning:
funktion för att hämta nuvarande år, månad och dag.

Indata:
får inte in någon data

Utdata
skickar nuvarande år, månad och dag till applikation som json

*/

include 'APL_databasConnect.php';

$setNarvaro = "SELECT DISTINCT Date_Format(Datum,'%d %M %Y')as Datum  from dag WHERE day(Datum)=day(CURRENT_TIMESTAMP) and month(Datum)=month(CURRENT_TIMESTAMP)";

$result = mysqli_query($conn, $setNarvaro); 
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);

} 

?>