<?php

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

socket_listen($sock);
/* Accept incoming requests and handle them as child processes */
$client = socket_accept($sock);
// Read the input from the client &#8211; 1024 bytes
$input = socket_read($client, 1024);
// Strip all white spaces from input
echo $input;



//$output = "bbbb";//ereg_replace("[ \t\n\r]","",$input).chr(0);

$output = strrev($input) . "\n"; 

// Display output back to client
socket_write($client, $output, strlen ($output)) or die("Could not write output\n");
// Close the client (child) socket
socket_close($client);
// Close the master sockets
socket_close($sock);

?>