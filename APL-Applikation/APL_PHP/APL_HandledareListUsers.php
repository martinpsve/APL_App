<?php

/*
Beskrivning:
funktion för att hämta användareID ifrån "narvaro" tabellen

Indata:
får inte in någon data

Utdata
skickar användareID till applikationen som json

*/

include 'APL_databasConnect.php';

$sql_query = "SELECT distinct narvaro.AnvandarID FROM Anvandare, Narvaro Where Anvandare.ArbetsplatsID=Narvaro.ArbetsplatsID";
 
$result = mysqli_query($conn,$sql_query);  
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);
                        
}  
?>