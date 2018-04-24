<?php
include 'APL_databasConnect.php';

$AnvandarID = $_POST["AnvandarID"];

$Sokning= "SELECT Narvarande, Datum FROM Narvaro, dag where narvaro.DagarID=dag.DagarID and Narvaro.AnvandarID='$AnvandarID'";

$resultstart = mysqli_query($conn, $Sokning); 
if(mysqli_num_rows($resultstart) > 0 )  
{  

    while ($row = mysqli_fetch_assoc($resultstart)){
        $emparray[] = $row;
    }
    echo json_encode($emparray);
} 

?>