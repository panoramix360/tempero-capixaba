<?php
	if(isset($_POST['enviar'])){
		
		$nome		=	trim($_POST['nome']);
		$email		=	trim($_POST['email']);
		$assunto	=	trim($_POST['assunto']);
		$mensagem	=	trim($_POST['mensagem']);
		
		$email_remetente = 'contato@temperocapixaba.com.br';
						
		$data = date("d/m/y");
		
		$hora = date("H:i");
		
		$assunto_email = 'Formulário Tempero Capixaba';
			
		$corpo = "<h3>Data e hora de envio: Email enviado no dia {$data} no horário de {$hora}</h3><br/><br/>
		  <h1>Formulário Tempero Capixaba</h1><br/><br/>
		  <strong>Nome</strong>: {$nome}<br/>
		  <strong>Email</strong>: {$email}<br/>
		  <strong>Assunto</strong>: {$assunto}<br/><br/>
		  <strong>Mensagem</strong>: {$mensagem}<br/>";
		  
		$headers = implode ( "\n",array ( "From: $email_remetente", "Return-Path: $email_remetente","MIME-Version: 1.0","X-Priority: 3","Content-Type: text/html; charset=iso-8859-1" ) );
				
		if(mail( 'contato@temperocapixaba.com.br', $assunto_email, $corpo, $headers ) ){         //aqui verifica se foi enviado com sucesso ou não
			$message = '<p class="vermelho">Mensagem enviada com sucesso!</p>';
		}else{
			$message = '<p class="vermelho">Erro ao enviar mensagem...</p>';
		}

	}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Tempero Capixaba - Contato</title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>

<body>

<div id="container"> <!-- CONTAINER -->

	<div id="topo"> <!-- TOPO -->
    
    	<div id="logo"> <!-- LOGOTIPO -->
        <a href="index.html"><img src="images/logo.gif" width="353" height="100" alt="Logotipo" /></a>
        </div> <!-- FIM LOGOTIPO -->
    
    	<div id="menu"> <!-- MENU -->
        
        	<ul class="menu">
            
            	<li><a href="index.html">Home</a></li>
                <li>|</li>
                <li><a href="cardapio.html">Cardápio</a></li>
                <li>|</li>
                <li><a href="sobre.html">Quem Somos</a></li>
                <li>|</li>
                <li><a href="#" id="selecionado">Contato</a></li>            
            
            </ul>
        
        </div> <!-- FIM MENU -->         
    
    
    </div> <!-- FIM TOPO -->
    
  <div id="conteudo"> <!-- CONTEUDO -->
    
    <div id="titulo"> <h1>Contato</h1> </div> <!-- TITULO -->
    
    <div id="texto"> <!-- TEXTO -->
        
    <hr />
    
    <div id="texto_contato">
    
    	<p><br />Mantenha contato conosco e tire suas dúvidas aqui mesmo no site, ou pelo nosso telefone:<br /><br />
        	<b>(21) 2507-3574</b><br />
           	<b>(21) 8608-9132</b><br/>
			<b>(21) 8804-8545</b><br/>
			
	<iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com.br/maps?f=q&amp;source=s_q&amp;hl=pt-BR&amp;geocode=&amp;q=Largo+da+Carioca+RJ&amp;aq=&amp;sll=-22.906835,-43.17724&amp;sspn=0.002634,0.005252&amp;vpsrc=6&amp;gl=br&amp;ie=UTF8&amp;hq=&amp;hnear=Largo+da+Carioca+-+Centro,+Rio+de+Janeiro,+20050-020&amp;ll=-22.90742,-43.178081&amp;spn=0.002634,0.005252&amp;t=m&amp;z=14&amp;output=embed"></iframe><br /><small><a href="http://maps.google.com.br/maps?f=q&amp;source=embed&amp;hl=pt-BR&amp;geocode=&amp;q=Largo+da+Carioca+RJ&amp;aq=&amp;sll=-22.906835,-43.17724&amp;sspn=0.002634,0.005252&amp;vpsrc=6&amp;gl=br&amp;ie=UTF8&amp;hq=&amp;hnear=Largo+da+Carioca+-+Centro,+Rio+de+Janeiro,+20050-020&amp;ll=-22.90742,-43.178081&amp;spn=0.002634,0.005252&amp;t=m&amp;z=14" style="color:#0000FF;text-align:right;">Exibir mapa ampliado</a></small>		</p>
    
    </div>
    
    <form method="post" action="contato.php" name="contato">
	<?php 
		if (isset($message)) { 
			echo $message;
		}
	?>	
    <br />
    	<label>Nome:<br />
        <input type="text" name="nome" size="30" /></label><br /><br />
        
    	<label>Email:<br />
        <input type="text" name="email" size="30" /></label><br /><br />
        
    	<label>Assunto:<br />
        <input type="text" name="assunto" size="30" /></label><br /><br />
        
        <label>Mensagem:<br />
        <textarea cols="30" rows="8" name="mensagem"></textarea></label><br /><br />
    
    	<input type="submit" name="enviar" value="Enviar" /><br /><br />
    
    </form>    
    
    
    <!-- FIM CONTATO -->
    
    <div class="clear"></div>
    </div> 
    <!-- FIM TEXTO -->
    
    
    <div id="lapis"></div> <!-- LAPIS -->   
    
    
    
    
  </div> 
  <!-- FIM CONTEUDO -->
  
  <div id="rodape_conteudo"></div> <!-- RODAPE CONTEUDO -->

</div> <!-- FIM CONTAINER -->


<div id="rodape"> <!-- RODAPE -->
	
	<div id="rodape2">
    
    <div id="direitos">© Copyright 2011 - Todos os direitos reservados.</div>
	
	<div id="cartoes">Aceitamos:<br/><img src="images/cartoes.png" width="400" height="22" alt="Cartões" /></div>
    
    <div id="marca">Desenvolvido por Lucas Oliveira.<br />
    				Contato: weboliveira3000@gmail.com</div>
    </div>
	
</div> <!-- FIM RODAPE -->

</body>

</html>
