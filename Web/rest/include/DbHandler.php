<?php

class DbHandler {
	private $conn;
	
	function __construct() {
		require_once dirname(__FILE__) . '/DbConnect.php';
		// opening db connection
		$db = new DbConnect();
		$this->conn = $db->connect();
	}
	
    /*
    ** Usuário
    */
	public function createUser($nome, $email, $endereco, $tipo_usuario, $horario_almoco) {
        $response = array();
 
        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
            // Generating API key
            $api_key = $this->generateApiKey();
 
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO tc_usuario(nome, email, endereco, tipo_usuario, horario_almoco, api_key) values(?, ?, ?, ?, ?, ?)");
            $stmt->bind_param("sssiss", $name, $email, $endereco, $tipo_usuario, $horario_almoco, $api_key);
 
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }
 
        return $response;
    }
    
    public function isUserExists($email) {
        $stmt = $this->conn->prepare("SELECT cd_usuario from tc_usuario WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
    
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT cd_usuario, nome, email, endereco, tipo_usuario, horario_almoco, api_key FROM tc_usuario WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            $stmt->bind_result($id, $nome, $email, $endereco, $tipo_usuario, $horario_almoco, $api_key);
            $stmt->fetch();
            $user = array();
            $user["cd_usuario"] = $id;
            $user["nome"] = $nome;
            $user["email"] = $email;
            $user["endereco"] = $endereco;
            $user["tipo_usuario"] = $tipo_usuario;
            $user["horario_almoco"] = $horario_almoco;
            $user["api_key"] = $api_key;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

    public function getAllUsers() {
        $stmt = $this->conn->prepare("SELECT cd_usuario, nome, email, endereco, tipo_usuario, horario_almoco, api_key FROM tc_usuario");
        $stmt->execute();
        $users = array();
        $stmt->bind_result($id, $nome, $email, $endereco, $tipo_usuario, $horario_almoco, $api_key);
        while($stmt->fetch()) {
            $user = array();
            $user["cd_usuario"] = $id;
            $user["nome"] = $nome;
            $user["email"] = $email;
            $user["endereco"] = $endereco;
            $user["tipo_usuario"] = $tipo_usuario;
            $user["horario_almoco"] = $horario_almoco;
            $user["api_key"] = $api_key;
            array_push($users, $user);
        }
        $stmt->close();
        return $users;
    }
    
    public function getUserByApiId($api_key) {
        $stmt = $this->conn->prepare("SELECT cd_usuario FROM tc_usuario WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($id);
            $stmt->fetch();
            $user_id = $id;
            $stmt->close();
            return $user_id;
        } else {
            return NULL;
        }
    }
    
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT cd_usuario from tc_usuario WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }
    
    /*
    ** Pedido
    */
    public function createPedido($usuario_id, $endereco, $data, $itens) {
        $response = array();
 
        // First check if user already existed in db
        if (!$this->isPedidoExists($usuario_id, $data)) {
 
            $stmt = $this->conn->prepare("INSERT INTO tc_pedido(cd_usuario, endereco, data) values(?, ?, ?)");
            $stmt->bind_param("iss", $usuario_id, $endereco, $data);
            $result = $stmt->execute();
            
            $pedido_id = $stmt->lastInsertId();
            
            foreach($itens as $item) {
                $stmt = $this->conn->prepare("INSERT INTO tc_item_pedido(cd_pedido, cd_prato, qtd_pequena, qtd_grande) values(?, ?, ?, ?)");
                $stmt->bind_param("iiii", $pedido_id, $item["cd_prato"], $item["qtd_pequena"], $item["qtd_grande"]);
                $result = $stmt->execute();
            }
 
            $stmt->close();
 
            if ($result) {
                return PEDIDO_CREATED_SUCCESSFULLY;
            } else {
                return PEDIDO_CREATE_FAILED;
            }
        } else {
            return PEDIDO_ALREADY_EXISTED;
        }
 
        return $response;
    }
    
    public function isPedidoExists($usuario_id, $data) {
        $stmt = $this->conn->prepare("SELECT cd_usuario from tc_pedido WHERE cd_ususario = ? and data = ?");
        $stmt->bind_param("ss", $email, $data);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
    
    public function getPedidoByUserAndDate($usuario_id, $data) {
        $stmt = $this->conn->prepare("SELECT cd_pedido, cd_usuario, endereco, data FROM tc_pedido WHERE cd_usuario = ? and data = ?");
        $stmt->bind_param("ss", $email, $data);
        if ($stmt->execute()) {
            $stmt->bind_result($id, $usuario_id, $endereco, $data);
            $stmt->fetch();
            $pedido = array();
            $pedido["cd_pedido"] = $id;
            $pedido["cd_usuario"] = $usuario_id;
            $pedido["endereco"] = $endereco;
            $pedido["data"] = $data;

            $pedido["itens"] = array();
            
            $stmt = $this->conn->prepare("SELECT cd_item_pedido, cd_pedido, cd_prato, qtd_pequena, qtd_grande FROM tc_item_pedido WHERE cd_pedido = ?");
            $stmt->bind_param("i", $id);
            
            if($stmt->execute()) {
                $stmt->bind_result($item_pedido_id, $pedido_id, $prato_id, $qtd_pequena, $qtd_grande);
                while($stmt->fetch()) {
                    $item = array();
                    $item["cd_item_pedido"] = $item_pedido_id;
                    $item["cd_pedido"] = $pedido_id;
                    $item["cd_prato"] = $prato_id;
                    $item["qtd_pequena"] = $qtd_pequena;
                    $item["qtd_grande"] = $qtd_grande;
                    array_push($pedido["itens"], $item);
                }
            }
            
            $stmt->close();
            return $pedido;
        } else {
            return NULL;
        }
    }
}

?>