<?php

/*
Beskrivning:
funktion för att hämta all information om användaren beroende på roll

Indata:
får in vilken roll en elev användare har från app

Utdata
skickar all information om en användare beroende vilken roll hen har som json.

*/

include 'APL_databasConnect.php';

$user_role = $_POST["Roller"];

if($user_role == "Admin"){
    
    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.AdminID='1'";

}

if($user_role == "Handledare"){
    
    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.HandledareID='1'";

}

if($user_role == "Elever"){

    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.ElevID='1'";

}

if($user_role == "Lärare"){

    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.LarareID='1'";

}

if($user_role == "Kansli"){

    $sql_query = "SELECT * FROM Anvandare WHERE Anvandare.KansliID='1'";

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