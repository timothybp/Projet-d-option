package controllers.adminControllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.globalControllers.FileController;
import controllers.globalControllers.RedirectController;
import models.Course;
import models.Project;
import models.Teacher;
import net.sf.json.JSONObject;
import services.CourseService;
import services.ProjectService;
import services.TeacherService;

/**
 * Servlet implementation class allocateProjectServlet
 */
public class AllocateProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private List<String> listPermutationResult;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllocateProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
        listPermutationResult = new ArrayList<String>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedCourse = request.getParameter("selectedCourse");
		String host = request.getParameter("host_1");
		
		String message = "";
		String attachment = "";
		if(!selectedCourse.equals("")){
			String courseName = selectedCourse.split("#")[0].trim();
			String courseSchoolYear = selectedCourse.split("#")[1];
			
			CourseService courseService = new CourseService();
			ProjectService ProjectService = new ProjectService();
			Course course = courseService.searchCourses("nom+schoolYear", courseName.replace("'", "''") + "_" + courseSchoolYear).get(0);
			List<Project> listProject = ProjectService.searchProjectsForOneCourse("idCourse", String.valueOf(course.getIdCourse()));
			
			File filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/choice/");
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + courseName + "[" + courseSchoolYear + "]_etu.txt";
			
			FileController fileCtrl = new FileController();
			List<List<String>> listChoice = fileCtrl.readChoiceFile(filename);
			
			List<String> listGroup = new ArrayList<String>();
			List<String> listFirstChoice = new ArrayList<String>();
			List<String> listSecondChoice = new ArrayList<String>();
			List<String> listThirdChoice = new ArrayList<String>();
			
			for(List<String> listChoiceForEachGroup: listChoice){
				listGroup.add(listChoiceForEachGroup.get(0));
				listFirstChoice.add(listChoiceForEachGroup.get(2).split(";")[0]);
				listSecondChoice.add(listChoiceForEachGroup.get(2).split(";")[1]);
				listThirdChoice.add(listChoiceForEachGroup.get(2).split(";")[2]);
			}
			
			List<List<String>> listSolution = generateSolution(listGroup, listFirstChoice, listSecondChoice, listThirdChoice, listProject);
			filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/solutions/" + courseName + "[" + courseSchoolYear + "]/");
			if(!filepath.exists())
				filepath.mkdirs();
			fileCtrl.recordSolutions(listSolution, filepath);
			
			message = "Succ¨¨s: Les solutions d'affectation sont g¨¦n¨¦r¨¦es!";
			attachment = "sol#" + filepath  + "@" + courseName + "@" + courseSchoolYear;
		}
		else{
			message = "Error: Il faut choisi un cours!";
		}
		TeacherService teacherService = new TeacherService();
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "solutions", attachment,request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public List<List<String>> generateSolution(List<String> listGroup, List<String> listFirstChoice, 
			List<String> listSecondChoice, List<String> listThirdChoice, List<Project> listProject){
		
		List<String> listWaitingGroupIndex = new ArrayList<String>();
		List<String>  listWaitingProjectId = new ArrayList<String>();
		for(Project project: listProject) {
			listWaitingProjectId.add(String.valueOf(project.getIdProject()));
		}
		List<String> oneResult = new ArrayList<String>();
		List<List<String>> listSolution = new ArrayList<List<String>>();
		
		//R¨¦f¨¦rer le premier voeu
		HashMap<String, Integer> hashMapFirstChoice = new HashMap<String, Integer>();
        for (String firstChoiceId : listFirstChoice) {
            if (hashMapFirstChoice.get(firstChoiceId) != null) {
                Integer value = hashMapFirstChoice.get(firstChoiceId);
                hashMapFirstChoice.put(firstChoiceId, value+1);
            } else {
            	hashMapFirstChoice.put(firstChoiceId, 1);
            }
        }
        for(int i = 0; i < listGroup.size(); i++){
        	String firstChoiceId = listFirstChoice.get(i);
        	if(listWaitingProjectId.indexOf(firstChoiceId) != -1) {
        		if(hashMapFirstChoice.get(firstChoiceId) == 1){
            		String resultLine = listGroup.get(i) + "\t" + firstChoiceId;
            		oneResult.add(resultLine);
            		listWaitingProjectId.remove(firstChoiceId);
            		listSecondChoice.set(i, "null");
            		listThirdChoice.set(i,"null");
            	}
            	else{
            		listWaitingGroupIndex.add(String.valueOf(i));
            	}
        	}
        	else{
        		listWaitingGroupIndex.add(String.valueOf(i));
        	}
        }
        
        //R¨¦f¨¦rer le deuxi¨¨me voex
        HashMap<String, Integer> hashMapSecondChoice = new HashMap<String, Integer>();
        for (String secondChoiceId : listSecondChoice) {
            if (hashMapSecondChoice.get(secondChoiceId) != null) {
                Integer value = hashMapSecondChoice.get(secondChoiceId);
                hashMapSecondChoice.put(secondChoiceId, value+1);
            } else {
            	hashMapSecondChoice.put(secondChoiceId, 1);
            }
        }
        for(int j = 0; j < listWaitingGroupIndex.size(); j++){
        	int waitingGroupIndex = Integer.parseInt(listWaitingGroupIndex.get(j));
        	String secondChoiceId = listSecondChoice.get(waitingGroupIndex);
        	if(listWaitingProjectId.indexOf(secondChoiceId) != -1) {
        		if(hashMapSecondChoice.get(secondChoiceId) == 1){
            		String resultLine = listGroup.get(waitingGroupIndex) + "\t" + secondChoiceId;
            		oneResult.add(resultLine);
            		listWaitingProjectId.remove(secondChoiceId);
            		listWaitingGroupIndex.remove(j);
            		listThirdChoice.set(waitingGroupIndex, "null");
            	}
        	}
        }
        
      //R¨¦f¨¦rer le deuxi¨¨me voex
        HashMap<String, Integer> hashMapThirdChoice = new HashMap<String, Integer>();
        for (String thirdChoiceId : listThirdChoice) {
            if (hashMapThirdChoice.get(thirdChoiceId) != null) {
                Integer value = hashMapThirdChoice.get(thirdChoiceId);
                hashMapThirdChoice.put(thirdChoiceId, value+1);
            } else {
            	hashMapThirdChoice.put(thirdChoiceId, 1);
            }
        }
        for(int k = 0; k < listWaitingGroupIndex.size(); k++){
        	int waitingGroupIndex = Integer.parseInt(listWaitingGroupIndex.get(k));
        	String thirdChoiceId = listThirdChoice.get(waitingGroupIndex);
        	if(listWaitingProjectId.indexOf(thirdChoiceId) != -1) {
        		if(hashMapThirdChoice.get(thirdChoiceId) == 1){
            		String resultLine = listGroup.get(waitingGroupIndex) + "\t" + thirdChoiceId;
            		oneResult.add(resultLine);
            		listWaitingProjectId.remove(thirdChoiceId);
            		listWaitingGroupIndex.remove(k);
            	}
        	}
        }
        
        //traiter les groupes restants
        permuteAlgo("", listWaitingGroupIndex.size(),listWaitingProjectId);
        for(String solution: listPermutationResult){
        	String [] permutedProjectIds = solution.split(";");
        	for(int i = 0; i < permutedProjectIds.length; i++){
        		int waitingGroupIndex = Integer.parseInt(listWaitingGroupIndex.get(i));
        		String resultLine = listGroup.get(waitingGroupIndex) + "\t" + permutedProjectIds[i];
        		oneResult.add(resultLine);
        	}
        	listSolution.add(oneResult);
        }
       return listSolution;
	}
	
	public void permuteAlgo(String permutationStr, int totalToTake, List<String> listWaitingProjectId){
		for (int i = 0; i < listWaitingProjectId.size(); i++) {   
            List <String> tempListWaitingProjectId = new ArrayList<String>(listWaitingProjectId);  
            String element =  (String)tempListWaitingProjectId.remove(i);
            String tempPermutationStr = "";
            if(!permutationStr.equals(""))
            	tempPermutationStr = permutationStr + ";" + element;
            else
            	tempPermutationStr = permutationStr + element;
            if (totalToTake == 1) {  
            	listPermutationResult.add(tempPermutationStr);
              
            } else {  
                int tempTotalToTake = totalToTake - 1;
                permuteAlgo(tempPermutationStr, tempTotalToTake, tempListWaitingProjectId);
            }  
        }  
	}
}
