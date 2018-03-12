<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="net.sf.json.*" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Professeur</title>
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
            <font size="6" color="white" style="position:relative;top:-20px;">Le syst�me de choisir les projets</font>
            <input type="button" value="Se d�connecter" onclick="window.location='/DistributionDeProjets/login.jsp'" style="position:relative; top:23px; float:right;" />
            <img src="<%=photoPath %>" height="40" width="40" 
            		style="position:relative; float:right; margin-right:200px;top:12px;"/>
            <font size="3" color="white" style="position:relative;float:right; margin-right:-170px; top:23px;">
            	<%=name %>&nbsp;
            	<%=surname %>&nbsp;
            	[<%=department %>]
            </font>
        </header>
        <section class="content">
        	<ul>
  				<li>
  					<a class="active" href="teacher_home.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Accueil</a>
  				</li>
  				<li>
  					<a href="teacher_propose_project.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Proposer un nouveau projet</a>
  				</li>
			</ul>
			
			<div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:80px;text-align:center;">
                <h1>Visualisation de vos projets</h1>
        	</div>
        	
        	<div id="u1" style="position:relative;top:120px;text-align:center;">
        		<form action="visualize_my_project" method="post">
        		<p>
        			<label>Consulter vos projets par:&nbsp;&nbsp;&nbsp;&nbsp;</label>
					<select style="width:200px" name="indicatorType">
  						<option value ="all">Tous</option>
  						<option value ="idProject">Id de projet</option>
  						<option value="nom">Nom de sujet</option>
  						<option value="semester">Semestre</option>
  						<option value="startYear">Ann�e de d�but</option>
  						<option value="endYear">Ann�e de fin</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" name="indicatorValue" style="width:250px">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="Rechercher" style="width:100px">
				</p>
			</form>
        	</div>
			
			<div id="u3" style="position:relative;text-align:center;left:12px;top:150px;width:98%;">
				<table class="gridtable" style="width: 100%;">
        			<tr class="header">
        				<th width="50">Num�ro</th>
        				<th width="50">Id</th>
        				<th width="200">Sujet</th>
        				<th>Description</th>
        				<th width="100">Encadrant.e.s</th>
        				<th width="70">Entreprise</th>
        				<th width="60">Nom de course</th>
        				<th width="60">Semester</th>
        				<th width="60">Ann�e scolaire</th>
        				<th width="60">Date de d�but</th>
        				<th width="60">Date de fin</th>
        				<th width="80">Etudiant.e.s</th>
        			</tr>
        			<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
        				<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
        	</table>
			</div>
			
        </section>
        <footer class="footer"><p>&copy; 2018 D�velopp� par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>