package controllers.adminControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.globalControllers.RedirectController;

import java.util.List;
import models.Teacher;
import services.TeacherService;

/**
 * Servlet implementation class ManageAdminServlet
 * Cette classe est pour faire l'action de bouton "Confirmer" dans la page "modify_admin_info.jsp"
 */
public class ManageAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation = request.getParameter("privilege");
		String idTeacherToModify = request.getParameter("idTeacher").trim();
		String wholeNameTeacherToModify = request.getParameter("wholeName").trim();
		String host = request.getParameter("host");
		
		String message = "";
		TeacherService teacherService = new TeacherService();
		
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		
		if(teacherService.isNumeric(idTeacherToModify) == true) {
			if(!String.valueOf(teacherHost.getIdTeacher()).equals(idTeacherToModify)){
				if(!idTeacherToModify.equals("") && !wholeNameTeacherToModify.equals("")) {
					if(wholeNameTeacherToModify.split("_").length == 2) {
						String nomTeacherToModify = wholeNameTeacherToModify.split("_")[0];
						String surnameTeacherToModify = wholeNameTeacherToModify.split("_")[1];
						List<Teacher> listTeacherToModify = teacherService.getTeacherInfo("idTeacher", idTeacherToModify);
						if(listTeacherToModify.size() != 0){
							Teacher teacherToModify = listTeacherToModify.get(0);
							if(teacherToModify.getName().equals(nomTeacherToModify) && 
									teacherToModify.getSurname().equals(surnameTeacherToModify)){
								if(operation.equals("add")){
									if(teacherToModify.getRole() == null || !teacherToModify.getRole().equals("admin")){
										teacherToModify.setRole("admin");
										teacherService.modifyTeacherRole(teacherToModify);
										message = "Succ¨¨s: vous authorisez le privil¨¨ge d'administrateur au professeur "
										+ teacherToModify.getName() + " " + teacherToModify.getSurname() 
										+ " [id=" + teacherToModify.getIdTeacher() + "]!";
									}
									else{
										message = "Erreur: Le professeur " + teacherToModify.getName() + " " + teacherToModify.getSurname() 
												+ " [id=" + teacherToModify.getIdTeacher() + "] est d¨¦j¨¤ l'administrateur! Vous ne pouvez pas faire l'ajoute!";
									}
								}
								if(operation.equals("delete")){
									if(teacherToModify.getRole() != null && teacherToModify.getRole().equals("admin")){
										teacherToModify.setRole("");
										teacherService.modifyTeacherRole(teacherToModify);
										message = "Succ¨¨s: vous annulez le privil¨¨ge d'administrateur au professeur "
										+ teacherToModify.getName() + " " + teacherToModify.getSurname() 
										+ " [id=" + teacherToModify.getIdTeacher() + "]!";
									}
									else{
										message = "Erreur: Le professeur " + teacherToModify.getName() + " " + teacherToModify.getSurname() 
												+ " [id=" + teacherToModify.getIdTeacher() + "] n'est pas l'administrateur! Vous ne pouvez pas faire la suppression!";
									}
								}
							}
							else{
								message = "Erreur: Le nom de professeur ne correspond pas ¨¤ son id!";
							}
						}
						else{
							message = "Erreur: Le professeur dont id=["+idTeacherToModify+"] n'existe pas!";
						}
					}
					else{
						message = "Erreur: Le format de pr¨¦nom et nom de professeur: Pr¨¦nom_NOM!";
					}
				}
				else {
					message = "Erreur: L'identifiant et le nom de professeur ne peuvent pas ¨ºtre vides!";
				}
			}
			else{
				message = "Erreur: Vous n'avez pas droit de changer votre propre privil¨¨ge!";
			}
		}
		else{
			message = "Erreur: L'identifiant ne peut contenir que des chiffres!";
		}
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "modify_admin_info", "", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
