<?php

/*
Beskrivning:
funktion för att skapa Användare

Indata:
får in för och efternamn, Anvandarnamn, lösenord, telefonnummer, Email och roll från app

Utdata
skickar för och efternamn, Anvandarnamn, lösenord, telefonnummer, Email och roll till databasen

*/

include 'APL_databasConnect.php';

$Fname = $_POST["Fnamn"];
$Ename = $_POST["Enamn"]; 
$Anvandarnamn = $_POST["Anvandarnamn"];
$Losenord = $_POST["Losenord"];  
$Telefonnummer = $_POST["Telefonnummer"];  
$Email = $_POST["Email"];  
$Role  = $_POST["Role"];   

if ($Role == "Admin")
{
    
    $sql_query = "insert into Anvandare(Anvandarnamn, Losenord, Fnamn, Enamn, Telefonnummer, Email, AdminID, HandledareID, ElevID, LarareID, KansliID) 
    VALUES ('$Anvandarnamn', '$Losenord', '$Fname', '$Ename', '$Telefonnummer', '$Email', 1, 0, 0, 0, 0)";
    $result = mysqli_query($conn,$sql_query);
}

if ($Role == "Handledare")
{   

    $sql_query = "insert into Anvandare(Losenord, Fnamn, Enamn, Telefonnummer, Email, AdminID, HandledareID, ElevID, LarareID, KansliID) 
    VALUES ('$Losenord', '$Fname', '$Ename', '$Telefonnummer', '$Email', 0, 1, 0, 0 , 0)";  
    $result = mysqli_query($conn,$sql_query);

}

if ($Role == "Elev")
{
    
    $sql_query = "insert into Anvandare(Losenord, Fnamn, Enamn, Telefonnummer, Email, AdminID, HandledareID, ElevID, LarareID, KansliID) 
    VALUES ('$Losenord', '$Fname', '$Ename', '$Telefonnummer', '$Email', 0, 0, 1, 0 , 0)";  
    $result = mysqli_query($conn,$sql_query);

}

if ($Role == "Lärare")
{
    
    $sql_query = "insert into Anvandare(Losenord, Fnamn, Enamn, Telefonnummer, Email, AdminID, HandledareID, ElevID, LarareID, KansliID) 
    VALUES ('$Losenord', '$Fname', '$Ename', '$Telefonnummer', '$Email', 0, 0, 0, 1 , 0)";  
    $result = mysqli_query($conn,$sql_query);

}

if ($Role == "Kansli")
{
    
    $sql_query = "insert into Anvandare(Losenord, Fnamn, Enamn, Telefonnummer, Email, AdminID, HandledareID, ElevID, LarareID, KansliID) 
    VALUES ('$Losenord', '$Fname', '$Ename', '$Telefonnummer', '$Email', 0, 0, 0, 0, 1)";  
    $result = mysqli_query($conn,$sql_query);

}

?>