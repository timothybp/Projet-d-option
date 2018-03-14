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
		String schoolYear = jsonObj.getString("schoolYear");
		
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
  					<a href="distribute_project.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Distribuer les projets</a>
  				</li>
  				<li>
  					<a href="allocate_project.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Affecter un cours de projet</a>
  				</li>
  				<li>
  					<a href="modify_admin_info.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Gestion d'administrateur</a>
  				</li>
			</ul>
			
			<div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:80px;text-align:center;">
                <h1>Création d'un cours de projet (<%=schoolYear %>)</h1>
        	</div>
			
			<div id="u1" style="position:relative;top:100px;text-align:center;">
        		<form action="create_new_course" method="post">
        			<p>
        				<font style="color: red;">*</font>
        				Id de cours: &nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="idCourse" style="width:220px">&nbsp;&nbsp;&nbsp;&nbsp;
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<font style="color: red;">*</font>
        				Nom de cours: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="nameCourse" style="width:220px">
        			</p>
        			<br><br>
        			<p>
        				<font style="color: red;">*</font>
        				Date de début: &nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="startDate" style="width:150px">&nbsp;&nbsp;&nbsp;&nbsp;
        				<font style="color: red;">*</font>
        				Date de fin: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="endDate" style="width:150px">&nbsp;&nbsp;&nbsp;&nbsp;
        				<font style="color: red;">*</font>
        				Délai de choix: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="deadline" style="width:150px">
        			</p>
        			<br><br>
        			<p>
        				<font style="color: red;">*</font>
        				Semester: &nbsp;&nbsp;&nbsp;&nbsp;
        				<select style="width:180px" name="semester">
        					<option value ="default">-- Semetre --</option>
  							<option value ="5">S5</option>
  							<option value ="6">S6</option>
  							<option value ="7">S7</option>
  							<option value ="8">S8</option>
  							<option value ="9">S9</option>
  							<option value ="10">S10</option>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<font style="color: red;">*</font>
        				Departement: &nbsp;&nbsp;&nbsp;&nbsp;
        				<select style="width:130px" name="department">
        					<option value ="default">-- Departement --</option>
  							<option value ="DI">DI</option>
  							<option value ="DEE">DEE</option>
  							<option value ="DMS">DMS</option>
  							<option value ="DAE">DAE</option>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<font style="color: red;">*</font>
        				Responsable: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="responsable" style="width:160px">
        			</p>
        			<br><br>
        			<P>
        				<font style="color: red;">*</font>
        				Nombre de membre de groupe: &nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="memberAmount" style="width:40px">
        				&nbsp;&nbsp;&nbsp;&nbsp;
        				<font style="color: red;">*</font>
        				Heures Totales: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="hours" style="width:120px">&nbsp;&nbsp;&nbsp;&nbsp;
        				<font style="color: red;">*</font>
        				Poid UE: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="weights" style="width:190px">
        			</P>
        			<br><br>
        			<input type="submit" value="Créer" style="height:25px;width:100px">
        			<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
        		</form>
        	</div>
        	
        	<div id="u3" class="remindMessage" style="position:relative;top:120px;left:340px;
        		width:47%;background:#F0F8FF">
        	<font size="3" style="font-weight:bold;font-style:italic;">Attention:<br></font><br>
        	<font size="3" style="font-style:italic;">1) Les champs de texte marqués</font>
        	<font size="3" style="color:red; font-weight:bold;font-style:italic;">*</font>
        	<font size="3" style="font-style:italic;">sont obligatoires de remplir<br><br></font>
        	<font size="3" style="font-style:italic;">2) Le format de dates: </font>
        	<font size="3" style="color: red; font-weight:bold;font-style:italic;">jj/MM/aaaa<br><br></font>
        	<font size="3" style="font-style:italic;">3) Le format de nom de responsable: </font>
        	<font size="3" style="color: red; font-weight:bold;font-style:italic;">Prénom_NOM</font>
        	</div>
        	
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>