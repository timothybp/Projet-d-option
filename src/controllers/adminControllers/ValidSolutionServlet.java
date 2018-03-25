package controllers.adminControllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.util.MailSSLSocketFactory;

import controllers.globalControllers.FileController;
import controllers.globalControllers.RedirectController;
import models.Course;
import models.Project;
import models.Student;
import models.Teacher;
import services.CourseService;
import services.ProjectService;
import services.TeacherService;

/**
 * Servlet implementation class ValidSolutionServlet
 * Cette classe est pour faire l'action de bouton "Valider" dans la page "solutions.jsp"
 */
public class ValidSolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidSolutionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedCourseName = request.getParameter("selectedCourseName");
		String selectedCourseSchoolYear = request.getParameter("selectedCourseSchoolYear");
		String selectedSolution = request.getParameter("selectedSolution");
		String host = request.getParameter("host");
		
		System.out.println(selectedCourseName);
		System.out.println(selectedCourseSchoolYear);
		
		FileController fileCtrl = new FileController();
		//chercher le fichier de solution d'affectation temporaire pour ce cours
		File filepathSolution = new File(this.getClass().getClassLoader().getResource("/").getPath()+ 
						"/temp/solutions/" + selectedCourseName + "[" + selectedCourseSchoolYear + "]");
		String filenameSolution = filepathSolution + "/" + selectedSolution + ".txt";
		
		List<List<String>> listRecord = fileCtrl.readSolutionRecordFile(filenameSolution);
		ProjectService projectService = new ProjectService();
		for(List<String> record: listRecord) {
			Project project = projectService.searchProjectsForOneCourse("idProject", record.get(2)).get(0);
			String [] studentIds = record.get(0).split(";");
			String [] studentNames = record.get(1).split(";");
			List<Student> listStudent = new ArrayList<Student>();
			for(int i = 0 ; i < studentIds.length; i++){
				Student student = new Student();
				student.setIdStudent(Long.parseLong(studentIds[i]));
				student.setName(studentNames[i].split(" ")[0]);
				student.setSurname(studentNames[i].split(" ")[1]);
				listStudent.add(student);
			}
			project.setListStudent(listStudent);
			//Inserer la solution dans la base de données
			projectService.upadateProjectInfo(project);
		}
		
		File filepathChoice = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/choice/") ;
		String filenameChoice = filepathChoice + "/" + selectedCourseName + "[" + selectedCourseSchoolYear + "]_etu.txt";
		fileCtrl.deleteFile(filenameChoice);
		
		CourseService courseService = new CourseService();
		Course course = courseService.searchCourses("nom+schoolYear", selectedCourseName.replace("'", "''") + "_" + selectedCourseSchoolYear).get(0);
		String department = course.getDepartment();
		int grade = 0;
		if(course.getSemester() %2 != 0)
			grade = (course.getSemester() + 1) / 2;
		else
			grade = course.getSemester() / 2;
		
		TeacherService teacherService = new TeacherService();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		String senderEmailAddress =teacherHost.getEmail();
		String receiverEmailAddress = department.toLowerCase() + String.valueOf(grade) + "@etu.univ-tours.fr";
		//sendEmail(senderEmailAddress, teacherHost.getPassword(), receiverEmailAddress, filenameSolution, selectedCourseName);
		System.out.println(senderEmailAddress);
		System.out.println(receiverEmailAddress);
		String message = "Vous valide la [" + selectedSolution + "] avec succès!";
		RedirectController redirectCtrl = new RedirectController();
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "allocate_project", "",request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	//cette méthode est pour envoyer le fichier de solution aux étudiants par mail
	public void sendEmail(String senderEmailAddress, String senderEmailPassword, 
			String receiverEmailAddress, String attechedFilename, String courseName){
		 
		String host = "smtp.univ-tours.fr";
		  
		Properties properties = System.getProperties(); 
		properties.setProperty("mail.smtp.host", host); 
		properties.put("mail.smtp.auth", "true"); 
		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true); 
			properties.put("mail.smtp.ssl.enable", "true"); 
			properties.put("mail.smtp.ssl.socketFactory", sf);
		
			Session session = Session.getDefaultInstance(properties,new Authenticator(){ 
				public PasswordAuthentication getPasswordAuthentication() 
				{
					return new PasswordAuthentication(senderEmailAddress, senderEmailPassword); //发件人邮件用户名、密码 
				} 
			}); 
			
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(senderEmailAddress)); 
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailAddress));
		   message.setSubject("Solution d'affectation de [" + courseName + "]"); 
		  
		   BodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setText("Bonjour\nVeillez trouver en pièce jointe la solution d'affectation de [" + courseName 
				   					+ "]\n Cordialement" ); 
		   Multipart multipart = new MimeMultipart(); 
		   multipart.addBodyPart(messageBodyPart); 
		   
		   messageBodyPart = new MimeBodyPart(); 
		   DataSource source = new FileDataSource(attechedFilename); 
		   messageBodyPart.setDataHandler(new DataHandler(source)); 
		   messageBodyPart.setFileName(MimeUtility.encodeText(attechedFilename)); 
		   multipart.addBodyPart(messageBodyPart); 
		   
		   message.setContent(multipart ); 
		   
		   Transport.send(message); 
		   System.out.println("Sent message successfully...."); 
		}catch (MessagingException mex) { 
			mex.printStackTrace(); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}
