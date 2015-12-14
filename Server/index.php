<?php
include "get_product.php";
include "get_all_products.php";
include "get_recipes.php";
include "get_all_recipes.php";
include "get_recipes_by_ccal.php";

// Set time limit to indefinite execution
set_time_limit (0);

// Set the ip and port we will listen on
$address = '192.168.1.35';
$port = 8888;

// Create a TCP Stream socket
$sock = socket_create(AF_INET, SOCK_STREAM, 0);

// Bind the socket to an address/port
socket_bind($sock, $address, $port) or die('Could not bind to address');

// Start listening for connections

while(true) {
    socket_listen($sock);
    //Accept incoming requests and handle them as child processes
    $client = socket_accept($sock);

    // Read the input from the client &#8211; 1024 bytes
    $input = socket_read($client, 1024);

    $rest = substr($input, 2);
    $JsonInput = json_decode($rest, true);
    $command = $JsonInput['command'];
    switch ($command) {
        case 1: // вернуть список всех продуктов
            $output = get_all_products();
            break;
        case 2: // вернуть рецепты по полученному списку ключей продуктов
            $id_products = json_decode($JsonInput['id_products'], true);
            $output = get_recipes($id_products);
            echo "\n";
            echo $output;
            break;
        case 3: // вернуть рецепты по калорийности
            $ccal = $JsonInput['ccal'];
            $output = get_recipes_by_ccal($ccal);
            echo "\n";
            echo $output;
            break;
        case 4: // вернуть все рецепты из БД
            $output = get_all_recipes();
            echo $output;
            break;
        default:
            $output = "Невозможно найти";
            break;
    }

    // Display output back to client
    socket_write($client, $output) or die("Could not write output\n");
    // Close the client (child) socket
    socket_close($client);
}
    // Close the master sockets
    socket_close($sock);

?>