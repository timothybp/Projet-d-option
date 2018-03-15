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
		
		JSONArray jsonArrayCourseForChoice = JSONArray.fromObject(jsonObj.getString("courseForChoice"));
		
		JSONArray jsonArrayChoice = JSONArray.fromObject(jsonObj.getString("listChoice"));
		String selectedCourseName = "";
		if(jsonArrayChoice.size() != 0)
			selectedCourseName = jsonArrayChoice.getJSONObject(0).getString("selectedCourseName");
		
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
                <h1>Affectation des projets (<%=schoolYear %>)</h1>
        	</div>
			
			<div id="u1" style="position:relative;top:120px;text-align:center;">
        		<form action="load_student_choice" method="post">
        			<p>
        			Regarder la liste de choix des étudiants en [<%=department %>]: &nbsp;&nbsp;&nbsp;&nbsp;
        			<select style="width:220px" name="course">
        				<option value ="default"
        				<%if(selectedCourseName.equals("")){ %> selected = "selected" <%} %> >
        				Cours disponibles à affecter</option>
        				
  						<%
        					for(int i = 0; i < jsonArrayCourseForChoice.size(); i++) {
        						String courseName = jsonArrayCourseForChoice.getJSONObject(i).getString("courseName");
        				%>
  						<option value ="<%=courseName %>"
  						<%if(courseName.equals(selectedCourseName)){ %> selected = "selected" <%} %> >
  						<%=courseName %></option>
						<% } %>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="Rechercher" style="height:25px;width:100px">
					<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
					</p><br>
				</form>
				<form>
					<table class="gridtable" style="position:relative; width: 95%;left:30px;">
        				<tr class="header">
        					<th width="50">Numéro</th>
        					<th width="250">Etudiant.e.s</th>
        					<th >Sujet 1</th>
        					<th>Sujet 2</th>
        					<th>Sujet 3</th>
        				</tr>
        				
						<%if(jsonArrayChoice.size() == 0) { %>
        				<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
        				<%}
        				else {
        					for(int j = 0; j < jsonArrayChoice.size(); j++){
        				%>
        				<tr>
        					<td><%=j+1 %></td>
        					<td><%=jsonArrayChoice.getJSONObject(j).getString("memberWholeNames") %></td>
        					<td><%=jsonArrayChoice.getJSONObject(j).getString("choiceSubject").split(";")[0] %></td>
        					<td><%=jsonArrayChoice.getJSONObject(j).getString("choiceSubject").split(";")[1] %></td>
        					<td><%=jsonArrayChoice.getJSONObject(j).getString("choiceSubject").split(";")[2] %></td>
        				</tr>
        				<%}} %>
        			</table>
        		</form>
        		<form action="allocate_project" method="post">
        			<input type="submit" value="Affecter" style="position: relative; top:30px;height:25px;width:100px">
        			<input hidden="hidden" type="text" name="host_1" 
        				value="<%=name+"_"+surname  %>">
        			<input hidden="hidden" type="text" name="selectedCourse" 
        				value="<%=selectedCourseName  %>">
        		</form>
        	</div>		
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>