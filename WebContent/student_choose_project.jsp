<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" import="models.Course" %>
<%@ page language="java" import="models.Project" %>
<%@ page language="java" import="models.Teacher" %>
<%@ page language="java" import="java.text.SimpleDateFormat" %>
<%@ page language="java" import="net.sf.json.*" %>

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
    <div class="wrap">
            <header>
            	<img src="images/logo.png" height="100%" width="15%" />
            	<font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            	<input type="button" value="Se déconnecter" onclick="window.location='/DistributionDeProjets/login.jsp'" style="position:relative; top:23px; float:right;" />
            	<img src="<%=(String)request.getAttribute("photoPath")%>" height="40" width="40" 
            			style="position:relative; float:right; margin-right:200px;top:12px;"/>
            	<font size="3" color="white" style="position:relative;float:right; margin-right:-150px; top:23px;">
            		<%=(String)request.getAttribute("name") %>&nbsp;
            		<%=(String)request.getAttribute("surname") %>&nbsp;
            		[<%=(String)request.getAttribute("department")+(String)request.getAttribute("grade") %>]
            	</font>
            </header>
            
            <section class="content">
            	<% 
            		List<Course> listCourseForFirstSemester = (ArrayList<Course>)request.getAttribute("courseListForFirstSemester");
            		List<Course> listCourseForSecondSemester = (ArrayList<Course>)request.getAttribute("courseListForSecondSemester");
            	%>
                <div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:30px;text-align:center;">
                	<h1>Choisir votre projet (<%=listCourseForFirstSemester.get(0).getSchoolYear() %>)</h1>
        		</div>
        		
        		<div id="u1"
        			class="courseList" 
        			style="position:relative;top:70px; width:270px;height:300px;background:#C0C0C0;">
        			<font style="font-weight:bold;font-style:italic;">
						&hearts; Projets disponibles pour le [<%=(String)request.getAttribute("department")+(String)request.getAttribute("grade") %>]
					</font>
        			<hr style="height:1px;border:none;border-top:1px solid #000000;" />
        			
        			&nbsp;&nbsp;&nbsp;&nbsp;&diams; S<%=(String)request.getAttribute("firstSemester") %>
        			<br>
        			<% 
        				Course course = new Course();
        				for(int i = 0; i < listCourseForFirstSemester.size(); i++){
        					 course = listCourseForFirstSemester.get(i);
         					 JSONObject jsonObj = new JSONObject();
         					 jsonObj.put("nom", course.getNom().replace("'", "^"));
         					 jsonObj.put("responsable", course.getTeacher().getName() + " " + course.getTeacher().getSurname());
        					 
        					 SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        					 SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        					 jsonObj.put("beginDate", sdf1.format(course.getBeginDate()));
        					 jsonObj.put("endDate", sdf1.format(course.getEndDate()));
        					 jsonObj.put("choosingDeadline", sdf2.format(course.getChoosingDeadline()));
        					 
        					 jsonObj.put("heures", String.valueOf(course.getHours()));
        					 jsonObj.put("poids", String.valueOf(course.getWeights()));
        					 jsonObj.put("memberAmount",String.valueOf(course.getMembreAmount()));
  							
        					 JSONArray jsonArrayProject = new JSONArray();
        					 	for(Project project : course.getListProject()){
        					 		JSONObject jsonObjProject = new JSONObject();
        					 		jsonObjProject.put("idProject", String.valueOf(project.getIdProject()));
        					 		jsonObjProject.put("subject",project.getSubject());
        					 		jsonObjProject.put("description", project.getDescription());
        					 		jsonObjProject.put("enterprise", project.getEnterprise());
        					 		String supervisors = "";
        					 		for(Teacher teacher: project.getListTeacher()){
        					 			supervisors += teacher.getName() + " " + teacher.getSurname() + ";";
        					 		}
        					 		if(supervisors.length() != 0)
        					 			jsonObjProject.put("supervisors", supervisors.substring(0, supervisors.length()-1));
        					 		else
        					 			jsonObjProject.put("supervisors", supervisors);
        					 		jsonArrayProject.add(jsonObjProject);
        					 	}
        					 	jsonObj.put("projects", jsonArrayProject);
        					 	String jsonStr = String.valueOf(jsonObj).replace("\"", "$");
        				%>
        			<label id="courseFirstSemester<%=i %>" 
        					onClick="courseName_clicked('<%=jsonStr %>')">
       					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&bull; <%=course.getNom() %>
       				</label>
        			<br>
        			<% } %>
        			<br>
        			&nbsp;&nbsp;&nbsp;&nbsp;&diams; S<%=(String)request.getAttribute("secondSemester") %>
        			<br>
        			<% 	
        				for(int i = 0; i < listCourseForSecondSemester.size(); i++){
        					course = listCourseForSecondSemester.get(i);
        					JSONObject jsonObj = new JSONObject();
        					jsonObj.put("nom", course.getNom().replace("'", "^"));
        					jsonObj.put("responsable", course.getTeacher().getName() + " " + course.getTeacher().getSurname());
       					 
       					 	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
       					 	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       					 	jsonObj.put("beginDate", sdf1.format(course.getBeginDate()));
       					 	jsonObj.put("endDate", sdf1.format(course.getEndDate()));
       					 	jsonObj.put("choosingDeadline", sdf2.format(course.getChoosingDeadline()));
       					 
       					 	jsonObj.put("heures", String.valueOf(course.getHours()));
       					 	jsonObj.put("poids", String.valueOf(course.getWeights()));
       					 	jsonObj.put("memberAmount",String.valueOf(course.getMembreAmount()));
 							
       					 	JSONArray jsonArrayProject = new JSONArray();
       					 	for(Project project : course.getListProject()){
       					 		JSONObject jsonObjProject = new JSONObject();
       					 		jsonObjProject.put("idProject", String.valueOf(project.getIdProject()));
       					 		jsonObjProject.put("subject",project.getSubject());
       					 		jsonObjProject.put("description", project.getDescription());
       					 		jsonObjProject.put("enterprise", project.getEnterprise());
       					 		String supervisors = "";
       					 		for(Teacher teacher: project.getListTeacher()){
       					 			supervisors += teacher.getName() + " " + teacher.getSurname() + ";";
       					 		}
       					 		if(supervisors.length() != 0)
       					 			jsonObjProject.put("supervisors", supervisors.substring(0, supervisors.length()-1));
       					 		else
       					 		jsonObjProject.put("supervisors", supervisors);
       					 		jsonArrayProject.add(jsonObjProject);
       					 	}
       					 	jsonObj.put("projects", jsonArrayProject);
       					 	String jsonStr = String.valueOf(jsonObj).replace("\"", "$");
       				%>
       				<label id="courseSecondSemester<%=i %>" 
       					onClick="courseName_clicked('<%=jsonStr %>')">
       					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&bull; <%=course.getNom() %>
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
        						value="<%=(String)request.getAttribute("name")+"_"+(String)request.getAttribute("surname")  %>" 
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
        						value="<%=(String)request.getAttribute("name")+"_"+(String)request.getAttribute("surname")  %>">
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