<?php

class DbConnect {
	private $conn;
	
	function __construct() {
		
	}
	
	function connect() {
		include_once dirname(__FILE__) . '/Config.php';
		
		// connect to mysql
		$this->conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
		
		// check for database connection error
		if(mysqli_connect_errno()) {
			echo 'Failed to connect to MySQL: ' . mysqli_connect_error();
		}
		
		return $this->conn;
	}
}

?>