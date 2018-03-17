<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="net.sf.json.*" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="models.Course" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Etudiant</title>
	<link href="css/header_footer.css" rel="stylesheet" type="text/css" />
	<link href="css/menuBar.css" rel="stylesheet" type="text/css" />
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
		
		String selectedCourse = "";
		List<String> memberNames = new ArrayList<String>();
		List<String> supervisorNames = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		List<String> enterprise = new ArrayList<String>();
		JSONArray jsonArraySelectedCourse = JSONArray.fromObject(jsonObj.getString("selectedCourseList"));
		if(jsonArraySelectedCourse.size() != 0 ){
			for(int i =0; i < jsonArraySelectedCourse.size(); i++){
				memberNames.add(jsonArraySelectedCourse.getJSONObject(i).getString("memberNames"));
				supervisorNames.add(jsonArraySelectedCourse.getJSONObject(i).getString("supervisorNames"));
				subject.add(jsonArraySelectedCourse.getJSONObject(i).getString("subject"));
				enterprise.add(jsonArraySelectedCourse.getJSONObject(i).getString("subject"));
			}
			selectedCourse = jsonArraySelectedCourse.getJSONObject(0).getString("selectedCourse");
		}
				
		String jsonStrEnc = URLEncoder.encode(jsonStrDec, java.nio.charset.StandardCharsets.UTF_8.toString());
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
            <ul>
  				<li>
  					<a class="active" href="student_home.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Accueil</a>
  				</li>
  				<li>
  					<a href="student_visualize_allocation.jsp?jsonStrEnc=<%=jsonStrEnc %>">
  					Visualiser le résultat d'affectation</a>
  				</li>
			</ul>
			
                <div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:30px;text-align:center;">
                	<h1>Visualiser le résultat d'affectation de projets (<%=jsonArrayCourseFirstSemester.getJSONObject(0).getString("schoolYear") %>)</h1>
        		</div>
        		
        		<div id="u1" style="position:relative;top:80px;text-align:center;">
        		<form action="visualize_allocation_result" method="post">
        			<p>
        			Choisir un course en [<%=department+grade %>]: &nbsp;&nbsp;&nbsp;&nbsp;
        			<select style="width:280px" name="course">
        				<option value ="default"
        				<%if(selectedCourse.equals("")){ %> selected = "selected" <%} %> >
        				-- cours #id --</option>
        				
  						<%
  							int j = 0;
        					for(int i = 0; i < jsonArrayCourseFirstSemester.size() + jsonArrayCourseSecondSemester.size(); i++) {
        						String courseName = "";
        						if(i<jsonArrayCourseFirstSemester.size()){
        							courseName = jsonArrayCourseFirstSemester.getJSONObject(i).getString("nom") + 
        									" #[" + jsonArrayCourseFirstSemester.getJSONObject(i).getString("idCourse") + "]";
        						}
        						else{
        							courseName = jsonArrayCourseSecondSemester.getJSONObject(j).getString("nom") + 
        									" #[" + jsonArrayCourseSecondSemester.getJSONObject(j).getString("idCourse") + "]";
        							j++;
        						}
        				%>
  						<option value ="<%=courseName %>"
  						<%if(courseName.equals(selectedCourse)){ %> selected = "selected" <%} %> >
  						<%=courseName %></option>
						<% } %>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="Rechercher" style="height:25px;width:100px">
					<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
					</p><br>
					
					<table class="gridtable" style="position:relative; width: 95%;left:30px;">
        				<tr class="header">
        					<th width="50">Numéro</th>
        					<th width="250">Etudiant.e.s</th>
        					<th>Sujet</th>
        					<th>Encadrant.e.s</th>
        					<th width="150">Entreprise</th>
        				</tr>
        				<%if(jsonArraySelectedCourse.size() == 0) { %>
        				<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
        				<%}
        				else {
        					for(int k = 0; k < jsonArraySelectedCourse.size(); k++){
        				%>
        				<tr>
        					<td><%=k+1 %></td>
        					<td><%=memberNames.get(k) %></td>
        					<td><%=subject.get(k) %></td>
        					<td><%=supervisorNames.get(k) %></td>
        					<td><%=enterprise.get(k) %></td>
        				</tr>
        				<%}} %>
        			</table>
        			<br><br><br>
        			
				</form>
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>  
</body>
</html>