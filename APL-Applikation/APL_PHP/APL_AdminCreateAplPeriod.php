<?php

/*
Beskrivning:
funktion för att skapa apl-perioder

Indata:
får in föroch efternamn från app

Utdata
skickar lösenord för och efternamn, telefonnummer och email till databasen

*/

include 'APL_databasConnect.php';

$Fname = $_POST["Fnamn"];
$Ename = $_POST["Enamn"]; 

    $sql_query = "insert into Anvandare(Losenord, Fnamn, Enamn, Telefonnummer, Email) 
    VALUES ('$Losenord', '$Fname', '$Ename', '$Telefonnummer', '$Email')";
    $result = mysqli_query($conn,$sql_query);
?>