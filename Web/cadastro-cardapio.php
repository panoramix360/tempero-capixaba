<?php
require_once 'rest/include/DbHandler.php';

function saveCardapio($db, $pratos, $contador) {
    if(isset($pratos)) {
        $pratos_array = array();
        foreach($pratos as $key => $n) {
            if(isset($pratos[$key]) && !empty($pratos[$key])) {
                array_push($pratos_array, $pratos[$key]);
            }
        }
        
        return $db->createCardapioPorDia($pratos_array, $contador);
    }
}

$db = new DbHandler();

$result = null;

// Save cardapio
if(isset($_POST["salvar"])) {
    
    $prato_segunda = $_POST['prato_segunda'];
    $prato_terca = $_POST['prato_terca'];
    $prato_quarta = $_POST['prato_quarta'];
    $prato_quinta = $_POST['prato_quinta'];
    $prato_sexta = $_POST['prato_sexta'];
    
    $result = saveCardapio($db, $prato_segunda, 1);
    $result = saveCardapio($db, $prato_terca, 2);
    $result = saveCardapio($db, $prato_quarta, 3);
    $result = saveCardapio($db, $prato_quinta, 4);
    $result = saveCardapio($db, $prato_sexta, 5);
}

// Get cardapio
$cardapio_segunda = $db->getCardapioByContador(1);
$cardapio_terca = $db->getCardapioByContador(2);
$cardapio_quarta = $db->getCardapioByContador(3);
$cardapio_quinta = $db->getCardapioByContador(4);
$cardapio_sexta = $db->getCardapioByContador(5);

?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Tempero Capixaba - Home</title>
    <link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>

<body>

<div id="container-cardapio"> <!-- CONTAINER -->
    
    <h1>Cardápio Semanal</h1>
    
    <?php if($result): ?>
    <h2>Salvo com sucesso!</h2>
    <?php endif; ?>
    
    <form name="cadastro-cardapio" id="cadastro-cardapio" method="POST">
        <div id="segunda-feira" class="dia-da-semana">
            <h3>Segunda-feira</h3>
            <input name="prato_segunda[]" type="text" value="<?php echo $cardapio_segunda["pratos"][0]["nome"] ?>" /><br />
            <input name="prato_segunda[]" type="text" value="<?php echo $cardapio_segunda["pratos"][1]["nome"] ?>" /><br />
            <input name="prato_segunda[]" type="text" value="<?php echo $cardapio_segunda["pratos"][2]["nome"] ?>" /><br />
            <input name="prato_segunda[]" type="text" value="<?php echo $cardapio_segunda["pratos"][3]["nome"] ?>" /><br />
        </div>
        
        <div id="terca-feira" class="dia-da-semana">
            <h3>Terça-feira</h3>
            <input name="prato_terca[]" type="text" value="<?php echo $cardapio_terca["pratos"][0]["nome"] ?>" /><br />
            <input name="prato_terca[]" type="text" value="<?php echo $cardapio_terca["pratos"][1]["nome"] ?>" /><br />
            <input name="prato_terca[]" type="text" value="<?php echo $cardapio_terca["pratos"][2]["nome"] ?>" /><br />
            <input name="prato_terca[]" type="text" value="<?php echo $cardapio_terca["pratos"][3]["nome"] ?>" /><br />
        </div>
        
        <div id="quarta-feira" class="dia-da-semana">
            <h3>Quarta-feira</h3>
            <input name="prato_quarta[]" type="text" value="<?php echo $cardapio_quarta["pratos"][0]["nome"] ?>" /><br />
            <input name="prato_quarta[]" type="text" value="<?php echo $cardapio_quarta["pratos"][1]["nome"] ?>" /><br />
            <input name="prato_quarta[]" type="text" value="<?php echo $cardapio_quarta["pratos"][2]["nome"] ?>" /><br />
            <input name="prato_quarta[]" type="text" value="<?php echo $cardapio_quarta["pratos"][3]["nome"] ?>" /><br />
        </div>
        
        <div id="quinta-feira" class="dia-da-semana">
            <h3>Quinta-feira</h3>
            <input name="prato_quinta[]" type="text" value="<?php echo $cardapio_quinta["pratos"][0]["nome"] ?>" /><br />
            <input name="prato_quinta[]" type="text" value="<?php echo $cardapio_quinta["pratos"][1]["nome"] ?>" /><br />
            <input name="prato_quinta[]" type="text" value="<?php echo $cardapio_quinta["pratos"][2]["nome"] ?>" /><br />
            <input name="prato_quinta[]" type="text" value="<?php echo $cardapio_quinta["pratos"][3]["nome"] ?>" /><br />
        </div>
        
        <div id="sexta-feira" class="dia-da-semana">
            <h3>Sexta-feira</h3>
            <input name="prato_sexta[]" type="text" value="<?php echo $cardapio_sexta["pratos"][0]["nome"] ?>" /><br />
            <input name="prato_sexta[]" type="text" value="<?php echo $cardapio_sexta["pratos"][1]["nome"] ?>" /><br />
            <input name="prato_sexta[]" type="text" value="<?php echo $cardapio_sexta["pratos"][2]["nome"] ?>" /><br />
            <input name="prato_sexta[]" type="text" value="<?php echo $cardapio_sexta["pratos"][3]["nome"] ?>" /><br />
        </div>
        
        <br />
        <div class="clear"></div>
        
        <input type="submit" name="salvar" value="Salvar" />
    </form>
    
</div> <!-- FIM CONTAINER -->

</body>

</html>