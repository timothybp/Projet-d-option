<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="net.sf.json.*" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Admin</title>
    <link href="css/header_footer.css" rel="stylesheet" type="text/css" />
    <link href="css/menuBar.css" rel="stylesheet" type="text/css" />
    <link href="css/table.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<% 
		String jsonStrDec = URLDecoder.decode(request.getParameter("jsonStrEnc"), java.nio.charset.StandardCharsets.UTF_8.toString());
		JSONObject jsonObj = JSONObject.fromObject(jsonStrDec); 
		String name = jsonObj.getString("name");
		String surname = jsonObj.getString("surname");
		String department = jsonObj.getString("department");
		String photoPath = jsonObj.getString("photoPath");
		
		String jsonStrEnc = URLEncoder.encode(jsonStrDec, java.nio.charset.StandardCharsets.UTF_8.toString());
    %>
            		
    <div id="container" class="wrap">
    	<header>
            <img src="images/logo.png" height="100%" width="15%" />
            <font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            <input type="button" value="Se déconnecter" onclick="window.location='/DistributionDeProjets/login.jsp'" style="position:relative; top:23px; float:right;" />
            <img src="<%=photoPath %>" height="40" width="40" 
            		style="position:relative; float:right; margin-right:380px;top:12px;"/>
            <font size="3" color="white" style="position:relative;float:right; margin-right:-340px; top:23px;">
            	<%=name %>&nbsp;
            	<%=surname %>&nbsp;
            	[<%=department %>&nbsp;Administrateur]
            </font>
        </header>
        <section class="content">
        	<ul>
  				<li>
  					<a class="active" href="admin_home.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Accueil</a>
  				</li>
  				<li>
  					<a href="modify_course_info.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Modifier un cours</a>
  				</li>
  				<li>
  					<a href="distribute_project.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Distribuer les projets</a>
  				</li>
  				<li>
  					<a href="allocate_project.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Affecter les projet</a>
  				</li>
  				<li>
  					<a href="modify_admin_info.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Gérer l'administrateur</a>
  				</li>
			</ul>
			
			<div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:80px;text-align:center;">
                <h1>Gestion d'administrateur</h1>
        	</div>
			
			<div id="u1" style="position:relative;top:120px;text-align:center;">
				<form action="modify_admin_info" method="post">
        				<p style="position:relative; top:20px; text-align:center;">Sélectionner une opération:
        				<input type="radio" name="privilege" value="add" checked>Ajouter un administrateur
						<input type="radio" name="privilege" value="delete">Supprimer un administrateur
						</p>
						<br><br>
						<p style="text-align:center;">Identifiant de professeur :
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="idTeacher">
						</p>
						<br>
						<p style="text-align:center;">Prénom et Nom de professeur:&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="wholeName">
						</p>
						<br><br>
						<p style="text-align:center;"> <input type="submit" value="Confirmer" style="width:120px;height:30px;"></p>
						<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
					</form> 
			</div>
			<div id="u2" 
                	class="remindMessage" 
                	style="position:relative;top:150px;text-align:center;">
                	<font size="2.5" color="red">
                	<i>( Le format de prénom et nom de professeur: Prénom_NOM )</i></font>
        	</div>
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>