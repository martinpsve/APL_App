<?php

/*
Beskrivning:
funktion för att hämta all information om användaren beroende användareID

Indata:
får in ett användarID från app

Utdata
skickar all information om en användare beroende på användareID som json.

*/

include 'APL_databasConnect.php';

$user_name = $_POST["AnvandarID"]; 

$sql_query = "SELECT * FROM Anvandare Where Anvandare.AnvandarID=$user_name";
 
$result = mysqli_query($conn,$sql_query);  
if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);

}  
?>
