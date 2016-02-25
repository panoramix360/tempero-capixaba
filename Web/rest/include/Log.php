<?php

class Log {
    private $filename;
    
    function __construct() {
        $this->filename = "log.txt";
    }
    
    function write($str) {
        file_put_contents($this->filename, $str . "\n", FILE_APPEND | LOCK_EX);
    }
}

?>