package controllers.adminControllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.globalControllers.RedirectController;
import models.Course;
import models.Teacher;
import services.CourseService;
import services.TeacherService;

/**
 * Servlet implementation class CreateCourseServlet
 */
public class CreateCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idCourse = request.getParameter("idCourse").trim();
		String nameCourse = request.getParameter("nameCourse").trim();
		String startDate = request.getParameter("startDate").trim();
		String endDate = request.getParameter("endDate").trim();
		String deadline = request.getParameter("deadline").trim();
		String semester = request.getParameter("semester").trim();
		String department = request.getParameter("department").trim();
		String responsableName = request.getParameter("responsable").trim();
		String memberAmount = request.getParameter("memberAmount").trim();
		String hours = request.getParameter("hours").trim();
		String weights = request.getParameter("weights").trim();
		String host = request.getParameter("host");
		
		String message = "";
		CourseService courseService = new CourseService();
		TeacherService teacherService = new TeacherService();
		
		if(!idCourse.equals("") && !nameCourse.equals("") && !startDate.equals("") &&
				!endDate.equals("") && !deadline.equals("") && !responsableName.equals("") && 
				!memberAmount.equals("") && !hours.equals("") && !weights.equals("")){
			if(!department.equals("default")){
				if(!semester.equals("default")) {
					if(courseService.isNumeric(memberAmount) &&
							courseService.isNumeric(hours) && courseService.isNumeric(weights)){
						if(courseService.judgeDateFormat(startDate) == true && 
								courseService.judgeDateFormat(endDate) == true &&
								courseService.judgeDateFormat(deadline) == true) {
							List<Teacher> listTeacher = teacherService.getTeacherInfo("wholeName", responsableName);
							Teacher responsable = new Teacher();
							if(listTeacher.size() == 1) {
								responsable = listTeacher.get(0);
								List<Course> listCourse = courseService.searchCourses("idCourse", idCourse);
								if(listCourse.size() == 0){
									Course course = new Course();
									course.setIdCourse(idCourse);
									course.setNom(nameCourse);
								
									course.setBeginDate(courseService.recoverDateFormat(startDate + " " + "00:00:00"));
									course.setEndDate(courseService.recoverDateFormat(endDate + " " + "00:00:00"));
									course.setChoosingDeadline(courseService.recoverDateFormat(deadline + " " + "23:59:59"));
									
									course.setDepartment(department);
									course.setSemester(Integer.parseInt(semester));
									course.setSchoolYear(courseService.judgeSchoolYear());
									course.setHours(Integer.parseInt(hours));
									course.setWeights(Integer.parseInt(weights));
									course.setMembreAmount(Integer.parseInt(memberAmount));
									course.setTeacher(responsable);
									courseService.save(course);
									message = "Succ¨¨s: Le cours est bien cr¨¦¨¦!";
								}
								else{
									message = "Erreur: L'id de cours est existant!";
								}
							}
							else{
								message = "Erreur: Le responsable " + responsableName + " n'existe pas!";
							}
						}
						else{
							message = "Erreur: La format de certaine date n'est pas correcte!";
						}
					}
					else{
						message = "Erreur: Les champs [Nombre de membre de groupe], "
								+ "[Heures Totales] et [Poid UE] n'acceptent que des chiffres!";
					}
				}
				else{
					message = "Erreur: Il faut choisir une semestre pour ce cours!";
				}
			}
			else{
				message = "Erreur: Il faut choisir un departement pour ce cours!";
			}
		}
		else{
			message = "Erreur: Il faut remplir tous les champs de texte!";
		}
		
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "admin_home", "",request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
