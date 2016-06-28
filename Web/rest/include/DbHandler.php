<?php

class DbHandler {
	private $conn;
    private $log;

	function __construct() {
		require_once dirname(__FILE__) . '/DbConnect.php';
		// opening db connection
		$db = new DbConnect();
		$this->conn = $db->connect();
        $this->log = new Log();
	}

    /*
    ** Usuário
    */
	public function createUser($nome, $endereco, $telefone, $email, $empresa, $tipo_entrega) {
        $response = array();

        // First check if user already existed in db
        if (!$this->isUserExists($nome, $telefone)) {
            // Generating API key
            $api_key = $this->generateApiKey();

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO tc_usuario(nome, endereco, telefone, email, empresa, tipo_entrega, api_key) values(?, ?, ?, ?, ?, ?, ?)");
            $stmt->bind_param("sssssis", $nome, $endereco, $telefone, $email, $empresa, $tipo_entrega, $api_key);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return $this->conn->insert_id;
            } else {
                // Failed to create user
                return 0;
            }
        } else {
            return 1;
        }

        return $response;
    }

    public function updateUser($cd_usuario, $nome, $endereco, $telefone, $email, $empresa, $tipo_entrega) {
        $response = array();

        if ($cd_usuario > 0) {
            // update query
            $stmt = $this->conn->prepare("UPDATE tc_usuario set nome = ?, endereco = ?, telefone = ?, email = ?, empresa = ?, tipo_entrega = ? where cd_usuario = ?");
            $stmt->bind_param("sssssii", $nome, $endereco, $telefone, $email, $empresa, $tipo_entrega, $cd_usuario);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return $cd_usuario;
            } else {
                // Failed to create user
                return 0;
            }
        } else {
            return 0;
        }

        return $response;
    }

    public function isUserExists($nome, $telefone) {
        if($stmt = $this->conn->prepare("SELECT cd_usuario from tc_usuario WHERE nome = ? and telefone = ?")) {
            $stmt->bind_param("ss", $nome, $telefone);
            $stmt->execute();
            $stmt->store_result();
            $num_rows = $stmt->num_rows;
            $stmt->close();
            return $num_rows > 0;
        } else {
            $this->log->write("SQL ERROR: " . $this->conn->error);
            exit;
        }
    }

    public function getUserByNameAndTelefone($nome, $telefone) {
        $stmt = $this->conn->prepare("SELECT cd_usuario, nome, email, endereco, telefone, empresa, tipo_entrega, api_key FROM tc_usuario WHERE nome = ? and telefone = ?");
        $stmt->bind_param("ss", $nome, $telefone);
        if ($stmt->execute()) {
            $stmt->bind_result($id, $nome, $email, $endereco, $telefone, $empresa, $tipo_entrega, $api_key);
            $stmt->fetch();
            $user = array();
            $user["cd_usuario"] = $id;
            $user["nome"] = $nome;
            $user["email"] = $email;
            $user["endereco"] = $endereco;
            $user["telefone"] = $telefone;
            $user["empresa"] = $empresa;
            $user["tipo_entrega"] = $tipo_entrega;
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
    public function createPedido($nome, $telefone, $endereco, $data, $itens) {
        $response = array();

        $user = $this->getUserByNameAndTelefone($nome, $telefone);

        $stmt = $this->conn->prepare("INSERT INTO tc_pedido(cd_usuario, endereco, data, status) values(?, ?, ?, 0)");
        $stmt->bind_param("iss", $user["cd_usuario"], $endereco, $data);
        $result = $stmt->execute();

        $pedido_id = $this->conn->insert_id;

        $stmt->close();

        foreach($itens as $item) {
            $stmt = $this->conn->prepare("INSERT INTO tc_item_pedido(cd_pedido, cd_prato, qtd_pequena, qtd_grande) values(?, ?, ?, ?)");
            $stmt->bind_param("iiii", $pedido_id, $item->cd_prato, $item->qtd_pequena, $item->qtd_grande);
            $result = $stmt->execute();
        }

        $stmt->close();

        if ($result) {
            return $pedido_id;
        } else {
            return 0;
        }

        return $response;
    }

    public function getPedidoByUserAndDate($usuario_id, $data) {
        $pedidos = array();
        $stmt = $this->conn->prepare("SELECT cd_pedido, cd_usuario, endereco, data, status FROM tc_pedido WHERE cd_usuario = ? and data = ?");
        $stmt->bind_param("ss", $usuario_id, $data);
        if ($stmt->execute()) {
            $stmt->bind_result($id, $usuario_id, $endereco, $data, $status);
            while($stmt->fetch()) {
                $pedido = array();
                $pedido["cd_pedido"] = $id;
                $pedido["cd_usuario"] = $usuario_id;
                $pedido["endereco"] = $endereco;
                $pedido["data"] = $data;
                $pedido["status"] = $status;
                array_push($pedidos, $pedido);
            }
            $stmt->close();

            if(count($pedidos) > 0) {
                foreach($pedidos as $key => $field) {
                    $itensPedido = array();

                    $stmt = $this->conn->prepare("SELECT cd_item_pedido, cd_pedido, cd_prato, qtd_pequena, qtd_grande FROM tc_item_pedido WHERE cd_pedido = ?");
                    $stmt->bind_param("i", $pedidos[$key]["cd_pedido"]);

                    if($stmt->execute()) {
                        $stmt->bind_result($item_pedido_id, $pedido_id, $prato_id, $qtd_pequena, $qtd_grande);
                        while($stmt->fetch()) {
                            $item = array();
                            $item["cd_item_pedido"] = $item_pedido_id;
                            $item["cd_pedido"] = $pedido_id;
                            $item["cd_prato"] = $prato_id;
                            $item["qtd_pequena"] = $qtd_pequena;
                            $item["qtd_grande"] = $qtd_grande;
                            array_push($itensPedido, $item);
                        }
                    }
                    $pedidos[$key]["itens"] = $itensPedido;
                }
                $stmt->close();
            }

            return $pedidos;
        } else {
            return NULL;
        }
    }

    /*
    ** Cardapio
    */
    public function createCardapioPorDia($pratos, $contador_cardapio) {
        $response = array();

        $this->deletePratos($contador_cardapio);

        $stmt = $this->conn->prepare("INSERT INTO tc_prato(contador, nome) values(?, ?)");

        foreach($pratos as $prato) {
            $stmt->bind_param("is", $contador_cardapio, $prato);
            $result = $stmt->execute();
        }

        $stmt->close();

        if ($result) {
            return PRATO_CREATED_SUCCESSFULLY;
        } else {
            return PRATO_CREATE_FAILED;
        }

        return $response;
    }

    public function deletePratos($contador) {
        $stmt = $this->conn->prepare("delete from tc_prato WHERE contador = ?");
        $stmt->bind_param("i", $contador);
        $stmt->execute();
        $stmt->close();
    }

    public function isPratoExists($nome, $contador) {
        $stmt = $this->conn->prepare("SELECT cd_prato from tc_prato WHERE nome = ? and contador = ?");
        $stmt->bind_param("ss", $nome, $contador);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    public function getCardapioByContador($contador) {
        $stmt = $this->conn->prepare("SELECT cd_cardapio, contador FROM tc_cardapio WHERE contador = ?");
        $stmt->bind_param("i", $contador);
        if ($stmt->execute()) {
            $stmt->bind_result($id, $contador_cardapio);
            $stmt->fetch();
            $cardapio = array();
            $cardapio["cd_cardapio"] = $id;
            $cardapio["contador"] = $contador_cardapio;
            $stmt->close();

            $cardapio["pratos"] = array();

            $stmt = $this->conn->prepare("SELECT cd_prato, contador, nome FROM tc_prato WHERE contador = ? ORDER BY nome");
            $stmt->bind_param("i", $contador);

            if($stmt->execute()) {
                $stmt->bind_result($prato_id, $contador, $nome);
                while($stmt->fetch()) {
                    $item = array();
                    $item["cd_prato"] = $prato_id;
                    $item["contador"] = $contador;
                    $item["nome"] = $nome;
                    array_push($cardapio["pratos"], $item);
                }
            }

            $stmt->close();

            return $cardapio;
        } else {
            return NULL;
        }
    }
}

?>
