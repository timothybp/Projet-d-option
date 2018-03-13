package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Teacher;
import services.TeacherService;

/**
 * Servlet implementation class VisualizeServlet
 */
public class VisualizeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String indicatorType = request.getParameter("indicatorType");
		String indicatorValue = request.getParameter("indicatorValue");
		String host = request.getParameter("host");
		
		String message = "";
		TeacherService teacherService = new TeacherService();
		
		if(indicatorType.equals("all") || (!indicatorType.equals("all") && !indicatorValue.equals(""))){
			if(indicatorType.equals("idProject") || indicatorType.equals("semester") ||
					indicatorType.equals("startYear") || indicatorType.equals("endYear")){
				if(teacherService.isNumeric(indicatorValue) == false) {
					message = "Erreur: Le texte de cette option n'accepte que des chiffres!";
					indicatorValue = "000000";
				}
			}
		}
		else{
			message = "Erreur: Il faut saisir le mot-cl¨¦ dans le champ de texte!";
			indicatorValue = "000000";
		}
		RedirectController redirectCtrl = new RedirectController();
		
		Teacher teacher = new Teacher();
		teacher = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		String attechement = "";
		if(indicatorType.equals("all"))
			attechement = indicatorType + "_null";
		else
			attechement = indicatorType + "_" + indicatorValue;
		redirectCtrl.redirectToTeacherPage(teacher, message, "teacher_home", attechement, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
