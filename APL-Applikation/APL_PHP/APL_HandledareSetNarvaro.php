<?php

/*
Beskrivning:
funktion för att Rapportera närvaro

Indata:
får in Narvarande som visar om man är närvarande eller inte och NarvaroRakanare

Utdata
skickar data in i databasen

*/

include 'APL_databasConnect.php';
$Narvarande = $_POST["Narvarande"];
$NarvaroRaknare = $_POST["NarvaroRaknare"];


if ($Narvarande == "Närvarande"){
    
    $setNarvaro = "UPDATE Narvaro SET Narvarande='1' Where NarvaroRaknare='$NarvaroRaknare'";
    
    $result = mysqli_query($conn, $setNarvaro); 

}
if ($Narvarande == "EjNärvarande"){
    
    $setNarvaro = "UPDATE Narvaro SET Narvarande='0' Where NarvaroRaknare='$NarvaroRaknare'";
    
    $result = mysqli_query($conn, $setNarvaro); 

}

?>



