<?php
 
class DB_Connect {
  
    function __construct() {    
        $this->connect();
    }
    
    function __destruct() {
        $this->close();
    }
 
    function connect() {
        require 'Config.php';
 
        $con = mysql_connect(DB_HOST, DB_USERNAME, DB_PASSWORD) or die(mysql_error());

		//mysql_set_charset('utf8', $con);
 
		/*mysql_query("SET NAMES 'utf8'");
		mysql_query("SET CHARACTER SET 'utf8'");
		mysql_query("SET SESSION collation_connection = 'utf8_general_ci';"); */
		
        $db = mysql_select_db(DB_NAME) or die(mysql_error()) or die(mysql_error());
        
        return $con;
    }
    
    function close() {
        mysql_close();
    }
 
}
 
?>