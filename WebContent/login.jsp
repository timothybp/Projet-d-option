<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Connexion</title>
    <link href="css/header_footer.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div id="container" class="wrap">
    	<header>
            	<img src="images/logo.png" height="100%" width="15%" />
            	<font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            </header>
            <section class="content">
                <div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:100px;text-align:center;">
                	<h1>Connexion</h1>
        		</div>
        		
        		<div id="u1"
        			class="loginRectangle" 
        			style="position:relative;top:140px;border:2px solid;border-radius:25px;
        			      margin:auto;width:400px;height:200px">
        			<form action="login" method="post">
        				<p style="position:relative; top:20px; text-align:center;">Vous êtes :
        				<input type="radio" name="role" value="student" checked>Etudiant.e
						<input type="radio" name="role" value="teacher">Professeur
						<input type="radio" name="role" value="admin">Administrateur
						</p>
						<br><br>
						<p style="text-align:center;">Nom d'utilisateur :
						<input type="text" name="username">
						</p>
						<br>
						<p style="text-align:center;">Mot de passe :&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<input type="password" name="password">
						</p>
						<br><br>
						<p style="text-align:center;"> <input type="submit" value="Se connecter" style="width:120px;height:30px;"></p>
					</form> 
        		</div>
        		
        		<div id="u2" 
                	class="remindMessage" 
                	style="position:relative;top:150px;text-align:center;">
                	<font size="2" color="red"><i>( Le nom d'utilisateur est votre numéro avec la lettre "t", ex: 21800000t )</i></font>
        		</div>
            </section>
            <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>