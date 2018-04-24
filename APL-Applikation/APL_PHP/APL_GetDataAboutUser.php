<?php

/*
Beskrivning:
funktion för att uppdatera information om användare

Indata:
får in användarnamn

Utdata
skickar allt om användaren till applikationen som json

*/

include 'APL_databasConnect.php';

$Anvandarnamn = $_POST["Anvandarnamn"]; 

$sql_query = "SELECT * FROM Anvandare Where Anvandare.Anvandarnamn='$Anvandarnamn'";
 
$result = mysqli_query($conn,$sql_query);  
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
       $emparray[] = $row;
    }
    echo json_encode($emparray);

}  

?>