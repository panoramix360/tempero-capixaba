<?php
require_once '../include/Log.php';
require_once '../include/DbHandler.php';
require_once '../include/Utils.php';
require_once '../libs/Slim/Slim.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

$log = new Log();

/**
 * Create user
 * url - /create-user
 * method - POST
 * params - 'nome', 'endereco', 'telefone', 'email', 'empresa', 'tipo_entrega'
 */
$app->post('/create-user', function() use ($app, $log) {
    // check for required params
    verifyRequiredParams(array('nome', 'endereco', 'telefone', 'tipo_entrega'));

    $response = array();

    // reading post params
    $nome = $_REQUEST["nome"];
    $endereco = $_REQUEST["endereco"];
    $telefone = $_REQUEST["telefone"];
    $email = $_REQUEST["email"];
    $empresa = $_REQUEST["empresa"];
    $tipo_entrega = $_REQUEST["tipo_entrega"];

    $db = new DbHandler();
    $res = $db->createUser($nome, $endereco, $telefone, $email, $empresa, $tipo_entrega);

    if ($res > 0) {
        $response["error"] = false;
        $response["cd_usuario"] = $res;
        echoResponse(201, $response);
    } else {
        $response["error"] = true;
        echoResponse(200, $response);
    }
});

/**
 * Create user
 * url - /update-user
 * method - POST
 * params - 'nome', 'endereco', 'telefone', 'email', 'empresa', 'tipo_entrega'
 */
$app->post('/update-user', function() use ($app, $log) {
    // check for required params
    verifyRequiredParams(array('cd_usuario', 'nome', 'endereco', 'telefone', 'tipo_entrega'));

    $response = array();

    // reading post params

    $cd_usuario = $_REQUEST["cd_usuario"];
    $nome = $_REQUEST["nome"];
    $endereco = $_REQUEST["endereco"];
    $telefone = $_REQUEST["telefone"];
    $email = $_REQUEST["email"];
    $empresa = $_REQUEST["empresa"];
    $tipo_entrega = $_REQUEST["tipo_entrega"];

    $db = new DbHandler();
    $res = $db->updateUser($cd_usuario, $nome, $endereco, $telefone, $email, $empresa, $tipo_entrega);

    if ($res) {
        $response["error"] = false;
        $response["cd_usuario"] = $res;
        echoResponse(201, $response);
    } else {
        $response["error"] = true;
        echoResponse(200, $response);
    }
});

/**
 * Get user
 * url - /get-user
 * method - POST
 * params - email
 */
$app->post('/get-user', function() use ($app) {
    // check for required params
    verifyRequiredParams(array('nome', 'telefone'));

    // reading post params
    $nome = $_REQUEST["nome"];
    $telefone = $_REQUEST["telefone"];
    $response = array();

    $db = new DbHandler();
    if ($db->isUserExists($nome, $telefone)) {
        // get the user by email
        $user = $db->getUserByTelefone($email);

        if ($user != NULL) {
            $response["error"] = false;
            $response["cd_usuario"] = $user["cd_usuario"];
            $response["nome"] = $user["nome"];
            $response["email"] = $user["email"];
            $response["endereco"] = $user["endereco"];
            $response["telefone"] = $user["telefone"];
            $response["empresa"] = $user["empresa"];
            $response["tipo_entrega"] = $user["tipo_entrega"];
            $response["api_key"] = $user["api_key"];
        } else {
            // unknown error occurred
            $response['error'] = true;
            $response['message'] = "Um erro ocorreu. Por favor tente novamente.";
        }
    } else {
        // user credentials are wrong
        $response['error'] = true;
        $response['message'] = 'Login falho, credenciais incorretas.';
    }

    echoResponse(200, $response);
});

/**
 * Get user by email
 * url - /usuarios/:email
 * method - GET
 * params - none
 */
$app->get('/get-pedido/:user_id', function($user_id) use ($app) {
    $response = array();
    $db = new DbHandler();

    $result = $db->getPedidoByUserAndDate($user_id, date("Y-m-d"));

    $response["error"] = false;

    if(count($result) > 0) {
        $response["pedidos"] = $result;
        $response["vazio"] = false;
    } else if(count($result) == 0) {
        $response["error"] = false;
        $response["vazio"] = true;
    } else {
        $response["error"] = true;
        $response["message"] = "Pedido não encontrado.";
    }

    echoResponse(200, $response);
});

/**
 * Create pedido
 * url - /create-pedido
 * method - POST
 * params - 'nome', 'email', 'endereco', 'tipo_usuario', 'horario_almoco'
 */
$app->post('/create-pedido', function() use ($app, $log) {
    verifyRequiredParams(array('nome', 'telefone', 'endereco'));

    $response = array();

    $nome = $_REQUEST["nome"];
    $telefone = $_REQUEST["telefone"];
    $endereco = $_REQUEST["endereco"];
    $data = date("Y-m-d");
    $itens = $_REQUEST["itens"];
    $itensDePedido = array();

    foreach($itens as $item) {
        $itemObj = json_decode(stripslashes($item));
        array_push($itensDePedido, $itemObj);
    }

    $db = new DbHandler();
    $res = $db->createPedido($nome, $telefone, $endereco, $data, $itensDePedido);

    if ($res) {
        $response["error"] = false;
        $response["cd_pedido"] = $res;
        echoResponse(201, $response);
    } else {
        $response["error"] = true;
        echoResponse(200, $response);
    }
});

$app->get('/getCardapio/:contador', function($contador) {
    $response = array();
    $db = new DbHandler();

    $result = $db->getCardapioByContador($contador);

    $response["error"] = false;
    $response["pratos"] = array();

    if(count($result["pratos"]) > 0) {
        foreach($result["pratos"] as $prato) {
            $tmp = array();
            $tmp["cd_prato"] = $prato["cd_prato"];
            $tmp["contador"] = $prato["contador"];
            $tmp["nome"] = $prato["nome"];
            array_push($response["pratos"], $tmp);
        }
    }

    echoResponse(200, $response);
});

$app->run();
?>
