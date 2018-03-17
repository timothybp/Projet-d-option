<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="net.sf.json.*" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
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
		
		String selectedCourseName = "";
		String selectedCourseSchoolYear = "";
		String solutionFilePath = "";
		String solutionFileNames = "";
		JSONArray jsonArraySolution = JSONArray.fromObject(jsonObj.getString("listSolution"));
		if(jsonArraySolution.size() != 0){
			selectedCourseName = jsonArraySolution.getJSONObject(0).getString("selectedCourseName");
			selectedCourseSchoolYear = jsonArraySolution.getJSONObject(0).getString("selectedCourseSchoolYear");
			solutionFileNames = jsonArraySolution.getJSONObject(0).getString("solutionFileNames");
			solutionFilePath = jsonArraySolution.getJSONObject(0).getString("solutionFilePath");
		}
		String [] listSolutionName = solutionFileNames.split(";");
		
		List<String> memberNames = new ArrayList<String>();
		List<String> projectNames = new ArrayList<String>();
		List<String> supervisorNames = new ArrayList<String>();
		List<String> enterprise = new ArrayList<String>();
		String selectedSolution = "";
		JSONArray jsonArrayRecord = JSONArray.fromObject(jsonObj.getString("listRecord"));
		if(jsonArrayRecord.size() != 0){
			for(int i = 0; i<jsonArrayRecord.size(); i++){
				memberNames.add(jsonArrayRecord.getJSONObject(i).getString("memberNames"));
				projectNames.add(jsonArrayRecord.getJSONObject(i).getString("projectNames"));
				supervisorNames.add(jsonArrayRecord.getJSONObject(i).getString("supervisorNames"));
				enterprise.add(jsonArrayRecord.getJSONObject(i).getString("enterprise"));
			}
			selectedCourseName = jsonArrayRecord.getJSONObject(0).getString("selectedCourseName");
			selectedCourseSchoolYear = jsonArrayRecord.getJSONObject(0).getString("selectedCourseSchoolYear");
			selectedSolution = jsonArrayRecord.getJSONObject(0).getString("selectedSolution");
		}
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
			<div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:50px;text-align:center;">
                <h1>Solutions d'affectation pour les projects [<%=selectedCourseName %>]</h1>
        	</div>
		
        	<div id="u1" style="position:relative;top:100px;text-align:center;">
        		<form action="load_solution_files" method="post">
        			<p>
        			Regarder les solutions d'affectation: &nbsp;&nbsp;&nbsp;&nbsp;
        			<select style="width:250px" name="solution">
        				<option value ="default"
        				<%if(selectedSolution.equals("")){ %> selected = "selected" <%} %> >
        				Solutions disponibles à valider</option>
        				
        				<%
        					for(int i = 0; i < listSolutionName.length; i++){
        						String solutionName = listSolutionName[i];
        				%>
        				<option value ="solution_<%=i+1%>"
  						<%if(solutionName.equals(selectedSolution)){ %> selected = "selected" <%} %> >
  						<%=solutionName %></option>
						<% } %>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="Rechercher" style="height:25px;width:100px">
					</p><br>
					<input hidden="hidden" type="text" name="selectedCourseName" 
        						value="<%=selectedCourseName  %>">
        			<input hidden="hidden" type="text" name="selectedCourseSchoolYear" 
        						value="<%=selectedCourseSchoolYear  %>">
        			<input hidden="hidden" type="text" name="filepath" 
        						value="<%=solutionFilePath  %>">
					<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
				</form>
				<form action="valid_solution" method="">
					<table class="gridtable" style="position:relative; width: 95%;left:30px;">
        				<tr class="header">
        					<th width="50">Numéro</th>
        					<th width="250">Etudiant.e.s</th>
        					<th>Sujet</th>
        					<th>Encadrant.e.s</th>
        					<th width="150">Entreprise</th>
        				</tr>
        				<%if(jsonArrayRecord.size() == 0) { %>
        				<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
        				<%}
        				else {
        					for(int j = 0; j < jsonArrayRecord.size(); j++){
        				%>
        				<tr>
        					<td><%=j+1 %></td>
        					<td><%=memberNames.get(j) %></td>
        					<td><%=projectNames.get(j) %></td>
        					<td><%=supervisorNames.get(j) %></td>
        					<td><%=enterprise.get(j) %></td>
        				</tr>
        				<%}} %>
        			</table>
        			<br><br><br>
        			<p>
        				Vous voulez valider cette solution? &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<input type="submit" value="Valider" style="height:25px;width:100px">
        				<input hidden="hidden" type="text" name="host" 
        						value="<%=name+"_"+surname  %>">
        				<input hidden="hidden" type="text" name="selectedCourseName" 
        						value="<%=selectedCourseName  %>">
        				<input hidden="hidden" type="text" name="selectedCourseSchoolYear" 
        						value="<%=selectedCourseSchoolYear  %>">
        				<input hidden="hidden" type="text" name="selectedSolution" 
        						value="<%=selectedSolution  %>">
        			</p>
        		</form>
        	</div>		
        </section>
        <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
    </div>
    
</body>
</html>