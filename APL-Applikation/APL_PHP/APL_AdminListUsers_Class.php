<?php

/*
Beskrivning:
funktion för att hämta all information om användaren beroende på klass

Indata:
får in vilken klass en elev går i från app

Utdata
skickar all information om en användare beroende vilken klass som json.

*/

include 'APL_databasConnect.php';

$user_class = $_POST["Class"];

if($user_class == "El2"){
    
    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.KlassID='1'";

}

if($user_role == "El3"){
    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.KlassID='2'";

}

if($user_role == "Te3"){

    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.KlassID='3'";

}

if($user_role == "Te4"){

    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.KlassID='4'";

}

$result = mysqli_query($conn,$sql_query);  

if(mysqli_num_rows($result) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($result)){                
        $emparray[] = $row;
    }
    echo json_encode($emparray);

}  

?>