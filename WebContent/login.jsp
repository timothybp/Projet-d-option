<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Accueil</title>
	<style>
    *{
        margin: 0;
        padding: 0;
    }
    html,body { height: 100%;}
    .wrap{
        min-height: 100%;
    }
    .min{
        padding-bottom: 60px;
    }
    header { 
        height: 60px; 
        background: #1381cc; 
        color: #FFF; 
        
    }
    section { 
        background: #fff; 
    }
    footer { 
        height:25px; 
        margin-top: -25px; 
        background: #0c4367; 
        color: #FFF;
        text-align:center;
    }
    
</style>
</head>

<body>
    <div class="wrap">
        <div class="min">
            <header>
            	<img src="images/logo.png" height="100%" width="20%" />
            	<font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            </header>
            <section class="content">
                <div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:100px;text-align:center;">
                	<h1>Login</h1>
        		</div>
        		
        		<div id="u1"
        			class="loginRectangle" 
        			style="position:relative;top:140px;border:2px solid;border-radius:25px;
        			      margin:auto;width:400px;height:200px">
        			<form action="login" method="post">
        				<p style="position:relative; top:20px; text-align:center;">Vous êtes :
        				<input type="radio" name="role">Etudiant.e
						<input type="radio" name="role">Professeur
						<input type="radio" name="role">Administrateur
						</p>
						<br><br>
						<p style="text-align:center;">Nom d'utilisateur :
						<input type="text" name="username">
						</p>
						<br>
						<p style="text-align:center;">Mot de passe :&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<input type="text" name="password">
						</p>
						<br><br>
						<p style="text-align:center;"> <input type="submit" value="Se connecter" style="width:120px;height:30px;"></p>
					</form> 
        		</div>
            </section>
        </div>
    </div>
    <footer class="footer"><p>© 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
</body>
</html>