<?php 

require_once '../include/DbHandler.php';
require_once '../include/Utils.php';
require_once '../libs/Slim/Slim.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

$user_id = NULL;

/**
 * Create user
 * url - /create-user
 * method - POST
 * params - 'nome', 'endereco', 'telefone', 'email', 'empresa', 'tipo_entrega'
 */
$app->post('/create-user', function() use ($app) {
    // check for required params
    verifyRequiredParams(array('nome', 'endereco', 'telefone', 'email', 'empresa', 'tipo_entrega'));
    
    $response = array();
    
    // reading post params
    $nome = $app->request->post('nome');
    $endereco = $app->request->post('endereco');
    $telefone = $app->request->post('telefone');
    $email = $app->request->post('email');
    $empresa = $app->request->post('empresa');
    $tipo_entrega = $app->request->post('tipo_entrega');

    $db = new DbHandler();
    $res = $db->createUser($nome, $endereco, $telefone, $email, $empresa, $tipo_entrega);
    
    if ($res == USER_CREATED_SUCCESSFULLY) {
        $response["error"] = false;
        $response["message"] = "Usuário cadastrado com sucesso.";
        echoRespnse(201, $response);
    } else if ($res == USER_CREATE_FAILED) {
        $response["error"] = true;
        $response["message"] = "Ocorreu um erro ao registrar o usuário.";
        echoRespnse(200, $response);
    } else if ($res == USER_ALREADY_EXISTED) {
        $response["error"] = true;
        $response["message"] = "Usuário já existente.";
        echoRespnse(200, $response);
    }
});

/**
 * Login
 * url - /login
 * method - POST
 * params - email
 */
$app->post('/login', function() use ($app) {
    // check for required params
    verifyRequiredParams(array('email'));
    
    // reading post params
    $email = $app->request()->post('email');
    $response = array();
    
    $db = new DbHandler();
    // check for correct email and password
    if ($db->isUserExists($email)) {
        // get the user by email
        $user = $db->getUserByEmail($email);
        
        if ($user != NULL) {
            $response["error"] = false;
            $response["cd_usuario"] = $user["cd_usuario"];
            $response["nome"] = $user["nome"];
            $response["email"] = $user["email"];
            $response["endereco"] = $user["endereco"];
            $response["tipo_usuario"] = $user["tipo_usuario"];
            $response["horario_almoco"] = $user["horario_almoco"];
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
    
    echoRespnse(200, $response);
});

/**
 * Get all users
 * url - /usuarios
 * method - GET
 * params - none
 */
$app->get('/usuarios', function() {
    $response = array();
    $db = new DbHandler();
    
    $result = $db->getAllUsers();
    
    $response["error"] = false;
    $response["users"] = array();
    
    if(count($result) > 0) {
        foreach ($result as $user) {
            $tmp = array();
            $tmp["cd_usuario"] = $user["cd_usuario"];
            $tmp["nome"] = $user["nome"];
            $tmp["email"] = $user["email"];
            $tmp["endereco"] = $user["endereco"];
            $tmp["tipo_usuario"] = $user["tipo_usuario"];
            $tmp["horario_almoco"] = $user["horario_almoco"];
            $tmp["api_key"] = $user["api_key"];
            array_push($response["users"], $tmp);
        }
    }

    echoRespnse(200, $response);
});

/**
 * Get user by email
 * url - /usuarios/:email
 * method - GET
 * params - none
 */
$app->get('/usuarios/:email', function($email) use ($app) {
    $response = array();
    $db = new DbHandler();

    $result = $db->getUserByEmail($email);

    $response["error"] = false;
    
    if(count($result) > 0) {
        $response["user"] = $result;
    } else {
        $response["error"] = true;
        $response["message"] = "Usuário não encontrado.";
    }
    
    echoRespnse(200, $response);
});

/**
 * Create pedido
 * url - /create-pedido
 * method - POST
 * params - 'nome', 'email', 'endereco', 'tipo_usuario', 'horario_almoco'
 */
$app->post('/create-pedido', function() use ($app) {
    verifyRequiredParams(array('usuario_id', 'endereco', 'data', 'itens'));

    $response = array();

    $usuario_id = $app->request->post('usuario_id');
    $endereco = $app->request->post('endereco');
    $data = $app->request->post('data');
    $itens = $app->request->post('itens');

    $db = new DbHandler();
    $res = $db->createPedido($usuario_id, $endereco, $data, $itens);
    
    if ($res == PEDIDO_CREATED_SUCCESSFULLY) {
        $response["error"] = false;
        $response["message"] = "Pedido cadastrado com sucesso.";
        echoRespnse(201, $response);
    } else if ($res == PEDIDO_CREATED_FAILED) {
        $response["error"] = true;
        $response["message"] = "Ocorreu um erro ao cadastrar o pedido.";
        echoRespnse(200, $response);
    } else if ($res == PEDIDO_ALREADY_EXISTED) {
        $response["error"] = true;
        $response["message"] = "Pedido já existente.";
        echoRespnse(200, $response);
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
    
    echoRespnse(200, $response);
});

$app->run();
?>