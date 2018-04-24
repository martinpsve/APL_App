<?php

/*
Beskrivning:
funktion för att skapa Närvaro

Indata:
får in Närvarvande, användarID, DagarID, PeriodID och ArbetsplatsID från app

Utdata
skickar Närvarvande, användarID, DagarID, PeriodID och ArbetsplatsID till databasen

*/

include 'APL_databasConnect.php';

$Närvarande = $_POST["Narvarande"];
$AnvandarID = $_POST["AnvandarID"]; 
$DagarID = $_POST["DagarID"];  
$PeriodID = $_POST["PeriodID"];  
$ArbetsplatsID = $_POST["ArbetsplatsID"];
   
    $sql_query = "insert into narvaro(AnvandarID, DagarID, PeriodID, ArbetsplatsID) 
    VALUES ('$AnvandarID', '$DagarID', '$PeriodID', '$ArbetsplatsID')";
    $result = mysqli_query($conn,$sql_query);
    
    ?>