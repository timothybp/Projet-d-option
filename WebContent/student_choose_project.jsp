<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" import="models.Course" %>
<%@ page language="java" import="java.text.SimpleDateFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Etudiant</title>
	<link href="css/header_footer.css" rel="stylesheet" type="text/css" />
	<script src="js/student_choose_project.js" type="text/javascript" ></script>
</head>

<body>
    <div class="wrap">
        <div class="min">
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
        					 String nom = course.getNom();
        					 String responsable = course.getTeacher().getName() + " " + course.getTeacher().getSurname();
        					 
        					 SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        					 SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        					 String beginDate = sdf1.format(course.getBeginDate());
        					 String endDate = sdf1.format(course.getEndDate());
        					 String choosingDeadline = sdf2.format(course.getChoosingDeadline());
        					 
        					 String heures = String.valueOf(course.getHours());
        					 String poids = String.valueOf(course.getWeights());
        					 String memberAmount = String.valueOf(course.getMembreAmount());
        			%>
        			<label id="courseFirstSemester<%=i %>" 
        					onClick="courseName_clicked('<%=nom.replace("'", "^") %>', '<%=responsable %>', '<%=beginDate %>', '<%=endDate %>',
        												'<%=choosingDeadline %>',	'<%=heures %>', '<%=poids %>', '<%=memberAmount %>')">
        					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&bull; <%=nom %>
        			</label>
        			<br>
        			<% } %>
        			<br>
        			&nbsp;&nbsp;&nbsp;&nbsp;&diams; S<%=(String)request.getAttribute("secondSemester") %>
        			<br>
        			<% 
        				for(int i = 0; i < listCourseForSecondSemester.size(); i++){
        					course = listCourseForSecondSemester.get(i);
        					String nom = course.getNom();
       					 	String responsable = course.getTeacher().getName() + " " + course.getTeacher().getSurname();
       					 
       					 	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
       					 	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       					 	String beginDate = sdf1.format(course.getBeginDate());
       					 	String endDate = sdf1.format(course.getEndDate());
       					 	String choosingDeadline = sdf2.format(course.getChoosingDeadline());
       					 
       					 	String heures = String.valueOf(course.getHours());
       					 	String poids = String.valueOf(course.getWeights());
       					 	String memberAmount = String.valueOf(course.getMembreAmount());
       				%>
       				<label id="courseSecondSemester<%=i %>" 
       					onClick="courseName_clicked('<%=nom.replace("'", "^") %>', '<%=responsable %>', '<%=beginDate %>', '<%=endDate %>',
       												'<%=choosingDeadline %>',	'<%=heures %>', '<%=poids %>', '<%=memberAmount %>')">
       					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&bull; <%=nom %>
       				</label>
        			<br>
        			<% } %>
        		</div>
        		
        		<div id="u2"
        			class="discriptionRectagle" 
        			style="position:relative;top:-225px;left:300px; width:400px;height:145px;border:5px solid">
        		</div>
            </section>
        </div>
    </div>
    <footer class="footer"><p>&copy; 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
</body>
</html>