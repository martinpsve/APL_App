<?php

/*
Beskrivning:
funktion för att hämta användare som finns i "Narvaro" tabelen och en viss klass

Indata:
får in ett KlassID ifrån Applikationen

Utdata
skickar Enamn, Fnamn, AnvanadreID till applikationen som json

*/

include 'APL_databasConnect.php';

$KlassID = $_POST["KlassID"];

$Sokning= "SELECT DISTINCT Enamn, Fnamn, Anvandare.AnvandarID FROM Anvandare, Narvaro where Anvandare.KlassID='$KlassID' and Anvandare.AnvandarID=Narvaro.AnvandarID";

$resultstart = mysqli_query($conn, $Sokning); 
if(mysqli_num_rows($resultstart) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($resultstart)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);
} 

?>