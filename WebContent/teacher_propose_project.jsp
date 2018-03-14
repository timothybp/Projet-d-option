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
		
		JSONArray jsonArrayCourse = JSONArray.fromObject(jsonObj.getString("listCourse"));
		
		String jsonStrEnc = URLEncoder.encode(jsonStrDec, java.nio.charset.StandardCharsets.UTF_8.toString());
    %>
            		
    <div id="container" class="wrap">
    	<header>
            <img src="images/logo.png" height="100%" width="15%" />
            <font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            <input type="button" value="Se déconnecter" onclick="window.location='/DistributionDeProjets/login.jsp'" style="position:relative; top:23px; float:right;" />
            <img src="<%=photoPath %>" height="40" width="40" 
            		style="position:relative; float:right; margin-right:280px;top:12px;"/>
            <font size="3" color="white" style="position:relative;float:right; margin-right:-240px; top:23px;">
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
                <h1>Proposition d'un nouveau projet (<%=schoolYear %>)</h1>
        	</div>
        	
        	<div id="u1" style="position:relative;top:120px;text-align:center;">
        		<form action="propose_new_project" method="post">
        			<font style="color: red;">*</font>
        			Sujet: &nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="subject" style="width:520px">
        			<br><br>
        			Description: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="description" style="width:490px">
        			<br><br>
        			<font style="color: red;">*</font>
        			Encadrants: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="supervisor" style="width:485px"
        														value=<%=name+"_"+surname %>>
        			<br><br>
        			<p>
        			<font style="color: red;">*</font>
        			Cours: &nbsp;&nbsp;&nbsp;&nbsp;
        			<select style="width:150px" name="courseName">
        			<option value ="Default">Cours disponibles</option>
        			<%
        				for(int i=0; i < jsonArrayCourse.size(); i++){ 
        					String courseName = jsonArrayCourse.getJSONObject(i).getString("nom");
        			%>
  						<option value ="<%=courseName %>"><%=courseName %></option>
  					<% } %>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
        			Entreprise: &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="enterprise" style="width:250px">
        			</p>
        			<br><br>
        			<input type="submit" value="Soumettre" style="height:25px;width:100px">
        			<input type="text" name="host" value="<%=name+"_"+surname+"_" %>>" hidden="hidden">
        		</form>
        		<br><br>
        	</div>
        	
        	<div id="u3" class="remindMessage" style="position:relative;top:100px;left:340px;
        		width:47%;background:#F0F8FF">
        	<font size="3" style="font-weight:bold;font-style:italic;">Attention:<br></font><br>
        	<font size="3" style="font-style:italic;">1) Les champs de texte marqués</font>
        	<font size="3" style="color:red; font-weight:bold;font-style:italic;">*</font>
        	<font size="3" style="font-style:italic;">sont obligatoires de remplir<br><br></font>
        	<font size="3" style="font-style:italic;">2) Le format de noms des encadrants: </font>
        	<font size="3" style="color: red; font-weight:bold;font-style:italic;">Prénom1_NOM1; Prénom2_NOM2; ...<br><br></font>
			<font size="3" style="font-style:italic;">3) Les noms des encadrants se séparent par</font>
        	<font size="3" style="color: red; font-weight:bold;font-style:italic;">";"<br></font>
        	</div>
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>