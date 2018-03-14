<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="net.sf.json.*" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="models.Course" %>
<%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Etudiant</title>
	<link href="css/header_footer.css" rel="stylesheet" type="text/css" />
	<link href="css/table.css" rel="stylesheet" type="text/css" />
	<script src="js/student_choose_project.js" type="text/javascript" ></script>
</head>

<body>

	<% 
		String jsonStrDec = URLDecoder.decode(request.getParameter("jsonStrEnc"), java.nio.charset.StandardCharsets.UTF_8.toString());
		JSONObject jsonObj = JSONObject.fromObject(jsonStrDec); 
		String name = jsonObj.getString("name");
		String surname = jsonObj.getString("surname");
		String department = jsonObj.getString("department");
		String grade = jsonObj.getString("grade");
		String photoPath = jsonObj.getString("photoPath");
		String firstSemester = jsonObj.getString("firstSemester");
		String secondSemester = jsonObj.getString("secondSemester");
		
		JSONArray jsonArrayCourseFirstSemester = JSONArray.fromObject(jsonObj.getString("courseListForFirstSemester"));
		JSONArray jsonArrayCourseSecondSemester = JSONArray.fromObject(jsonObj.getString("courseListForSecondSemester"));
    %>
    <div class="wrap">
            <header>
            	<img src="images/logo.png" height="100%" width="15%" />
            	<font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            	<input type="button" value="Se déconnecter" onclick="window.location='/DistributionDeProjets/login.jsp'" style="position:relative; top:23px; float:right;" />
            	<img src="<%=photoPath %>" height="40" width="40" 
            			style="position:relative; float:right; margin-right:280px;top:12px;"/>
            	<font size="3" color="white" style="position:relative;float:right; margin-right:-240px; top:23px;">
            		<%=name %>&nbsp;
            		<%=surname %>&nbsp;
            		[<%=department+grade %>]
            	</font>
            </header>
            
            <section class="content">
                <div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:30px;text-align:center;">
                	<h1>Choisir votre projet (<%=jsonArrayCourseFirstSemester.getJSONObject(0).getString("schoolYear") %>)</h1>
        		</div>
        		
        		<div id="u1"
        			class="courseList" 
        			style="position:relative;top:70px; width:270px;height:300px;background:#C0C0C0;">
        			<font style="font-weight:bold;font-style:italic;">
						&hearts; Projets disponibles pour le [<%=department+grade %>]
					</font>
        			<hr style="height:1px;border:none;border-top:1px solid #000000;" />
        			
        			&nbsp;&nbsp;&nbsp;&nbsp;&diams; S<%=firstSemester %>
        			<br>
        			<% 
        				for(int i = 0; i < jsonArrayCourseFirstSemester.size(); i++){
        					String jsonStr = jsonArrayCourseFirstSemester.get(i).toString();
        					jsonStr = jsonStr.replace("\"", "$");
        					jsonStr = jsonStr.replace("'", "^");
        			%>
        			<label id="courseFirstSemester<%=i %>" 
        					onClick="courseName_clicked('<%=jsonStr %>')">
       					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&bull; 
       					<%=jsonArrayCourseFirstSemester.getJSONObject(i).getString("nom") %>
       				</label>
        			<br>
        			<% } %>
        			<br>
        			&nbsp;&nbsp;&nbsp;&nbsp;&diams; S<%=secondSemester %>
        			<br>
        			<% 	
        				for(int i = 0; i < jsonArrayCourseSecondSemester.size(); i++){
        					String jsonStr = jsonArrayCourseSecondSemester.get(i).toString();
        					jsonStr = jsonStr.replace("\"", "$");
        					jsonStr = jsonStr.replace("'", "^");
       				%>
       				<label id="courseSecondSemester<%=i %>" 
       					onClick="courseName_clicked('<%=jsonStr %>')">
       					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&bull;
       					<%=jsonArrayCourseSecondSemester.getJSONObject(i).getString("nom") %>
       				</label>
        			<br>
        			<% } %>
        		</div>
        		
        	<label style="position:relative;top:-230px;left:300px;">Description du cours:</label>
        	<div id="u2"
        		class="discriptionRectagle" 
        		style="position:relative;top:-225px;left:300px; width:400px;height:145px;border:5px solid;">
        	</div>
        			
        	<label style="position:relative;top:-400px;left:800px;">Vos voeux:</label>
        	<div id="u4" class="choiceField" style="position:relative;top:-395px;left:800px;width:450px;">
        		<form action="choose_project" method="post">
        			<p>
        				<font size="2"> 
        				Cours de projet:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				</font>
        				<input type="text" id="courseName" name="course" style="width:278px" readonly="true">
        			</p>
        			<br>
        			<p>
        				<font size="2"> Noms des membres du groupe:</font>
        				<input type="text" name="members" 
        						value="<%=name+"_"+surname  %>" 
        						style="width:278px">
        			</p>
        			<br>
        			<p>
        				<font size="2"> Voeu 1:</font>
        				<input type="text" name="choice1" style="width:80px">
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<font size="2"> Voeu 2:</font>
        				<input type="text" name="choice2" style="width:80px">
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font size="2"> Voeu 3:</font>
        				<input type="text" name="choice3" style="width:80px">      				
        			</p>
        			<p>
        				<font size="2" style="font-weight:bold;font-style:italic;">Attention : <br></font>
        				<font size="2" style="font-style:italic;">1) Le format de noms des membres: </font>
        				<font size="2" style="color: red; font-weight:bold;font-style:italic;">Prénom1_NOM1; Prénom2_NOM2; ...<br></font>
						<font size="2" style="font-style:italic;">2) Les noms des membres se séparent par</font>
        				<font size="2" style="color: red; font-weight:bold;font-style:italic;">";"<br></font>
						<font size="2" style="font-style:italic;">3) Il faut saisir l'</font>
        				<font size="2" style="color: red; font-weight:bold;font-style:italic;">Id</font>
        				<font size="2" style="font-style:italic;">du sujet pour chaque voeu</font>
        				<input type="submit" value="Soumettre" style="margin-right:2px;float:right;">
        			</p>
        			<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
        		</form>
        	</div>
        		
        	<label style="position:relative;top:-370px;left:300px;">Liste du sujets:</label>
        	<div id="u3" class="projectTable" style="position:relative;top:-365px;left:300px;width:950px;">
        		<table class="gridtable" style="width: 100%">
        			<tr class="header">
        				<th width="50">Numéro</th>
        				<th width="80">Id</th>
        				<th width="200">Sujet</th>
        				<th>Description</th>
        				<th width="120">Encadrant.e.s</th>
        				<th width="70">Entreprise</th>
        			</tr>
        			<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
        		</table>
        	</div>
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>  
</body>
</html>