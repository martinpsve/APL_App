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

$AnvandarID = $_POST["AnvandarID"];

$GetStartdag = "SELECT DISTINCT Week(startdag, 3), Week(slutdag, 3) from Narvaro, aplperiod WHERE Narvaro.AnvandarID='$AnvandarID' and Narvaro.PeriodID=aplperiod.PeriodID"; 

$resultstart = mysqli_query($conn, $GetStartdag); 
if(mysqli_num_rows($resultstart) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($resultstart)){
                        
                                $emparray[] = $row;
                            }
                            echo json_encode($emparray);
} 

?>