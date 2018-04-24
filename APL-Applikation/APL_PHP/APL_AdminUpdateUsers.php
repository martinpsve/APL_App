<?php

/*
Beskrivning:
funktion för att uppdatera information om användare

Indata:
får in användareID, för och efternamn, Lösenord, Telefonnummer, Email, Roll och användarnamn

Utdata
skickar användareID, för och efternamn, Lösenord, Telefonnummer, Email, Roll och användarnamn till databasenqdqqaq2tq

*/

include 'APL_databasConnect.php';
$AnvandarID = $_POST["AnvandarID"];
$Fname = $_POST["Fnamn"];
$Ename = $_POST["Enamn"]; 
$Losenord = $_POST["Losenord"];  
$Telefonnummer = $_POST["Telefonnummer"];  
$Email = $_POST["Email"];  
$Role  = $_POST["Role"];   
$Anvandarnamn =   substr($Fname, 0, 3).substr($Ename, 0, 2);

if ($Role == "Admin")
{
    
$ChangeUser = "UPDATE Anvandare SET Anvandarnamn='$Anvandarnamn', Losenord='$Losenord', Fnamn='$Fname', Enamn='$Ename', Telefonnummer='$Telefonnummer', Email='$Email', 
AdminID=1, HandledareID=0, ElevID=0, LarareID=0, KansliID=0 Where Anvandare.AnvandarID='$AnvandarID'"; 

$result = mysqli_query($conn, $ChangeUser); 
}

if ($Role == "Handledare")
{

$ArbetsplatsID = $_POST["ArbetsplatsID"];    

$ChangeUser = "UPDATE Anvandare SET Losenord='$Losenord', Fnamn='$Fname', Enamn='$Ename', Telefonnummer='$Telefonnummer', Email='$Email', ArbetsplatsID='$ArbetsplatsID', 
AdminID=0, HandledareID=1, ElevID=0, LarareID=0, KansliID=0 Where Anvandare.AnvandarID='$AnvandarID'"; 

$result = mysqli_query($conn, $ChangeUser); 
}

if ($Role == "Elev")
{

$Onskan = $_POST["Onskan"];
$matchning = $_POST["matchning"];
$KlassID = $_POST["KlassID"];

$ChangeUser = "UPDATE Anvandare SET Losenord='$Losenord', Fnamn='$Fname', Enamn='$Ename', Telefonnummer='$Telefonnummer', Email='$Email', 
Onskan='$Onskan', matchning='$matchning', KlassID='$KlassID', 
AdminID=0, HandledareID=0, ElevID=1, LarareID=0, KansliID=0 Where Anvandare.AnvandarID='$AnvandarID'"; 

$result = mysqli_query($conn, $ChangeUser); 
}

if ($Role == "Lärare")
{    

$Undervisar = $_POST["Undervisar"];

$ChangeUser = "UPDATE Anvandare SET Losenord='$Losenord', Fnamn='$Fname', Enamn='$Ename', Telefonnummer='$Telefonnummer', Email='$Email', Undervisar='$Undervisar', 
AdminID=0, HandledareID=0, ElevID=0, LarareID=1, KansliID=0 Where Anvandare.AnvandarID='$AnvandarID'"; 

$result = mysqli_query($conn, $ChangeUser); 
}

if ($Role == "Kansli")
{
    
$ChangeUser = "UPDATE Anvandare SET Losenord='$Losenord', Fnamn='$Fname', Enamn='$Ename', Telefonnummer='$Telefonnummer', Email='$Email', 
AdminID=0, HandledareID=0, ElevID=0, LarareID=0, KansliID=1 Where Anvandare.AnvandarID='$AnvandarID'"; 

$result = mysqli_query($conn, $ChangeUser); 
}

?>